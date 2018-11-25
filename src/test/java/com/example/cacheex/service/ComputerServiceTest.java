package com.example.cacheex.service;

import com.example.cacheex.SpringCacheExampleApplication;
import com.example.cacheex.model.Computer;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.testcontainers.containers.GenericContainer;

import java.util.List;

import static java.time.LocalDate.now;
import static java.time.LocalDate.of;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(initializers = ComputerServiceTest.Initializer.class,
        classes = SpringCacheExampleApplication.class)
public class ComputerServiceTest {

    @ClassRule
    public static GenericContainer redis = new GenericContainer("redis").withExposedPorts(6379);

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues values = TestPropertyValues.of(
                    "spring.cache.type=redis",
                    "spring.redis.host=" + redis.getContainerIpAddress(),
                    "spring.redis.port=" + redis.getMappedPort(6379));
            values.applyTo(configurableApplicationContext);
        }
    }

    @Autowired
    private ComputerService computerService;

    @Autowired
    private CacheManager cacheManager;

    private List<Computer> computers;

    @Before
    public void setUp() {
        computers = List.of(
                Computer.builder().computerId("1").creationDate(of(2018, 1, 6)).model("A")
                        .build(),
                Computer.builder().computerId("2").creationDate(of(2018, 1, 7)).model("A")
                        .build());
    }

    @Test
    public void shouldCacheComputers() {
        //Given
        computers.stream().forEach(computerService::save);

        //When
        List<Computer> cached = computerService.findByModel("A");

        //Then
        Cache computersCache = cacheManager.getCache("computers");
        assertThat(computersCache).isNotNull();
        Cache.ValueWrapper cacheValue = computersCache.get("A");
        assertThat(cacheValue).isNotNull();
        assertThat(cacheValue.get()).isEqualTo(computers);
    }

    @Test
    public void shouldEvictComputers() {
        //Given
        Cache computersCache = cacheManager.getCache("computers");
        computersCache.put("A", computers);
        assertThat(computersCache.get("A").get()).isEqualTo(computers);

        //When
        computerService.save(Computer.builder().computerId("2").creationDate(now()).model("A").build());

        //Then
        assertThat(computersCache.get("A")).isNull();
    }
}