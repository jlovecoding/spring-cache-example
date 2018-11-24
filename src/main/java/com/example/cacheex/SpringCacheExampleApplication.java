package com.example.cacheex;

import com.example.cacheex.model.Computer;
import com.example.cacheex.repository.ComputerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

import static java.time.LocalDate.of;

@SpringBootApplication
@EnableCaching
@EnableAutoConfiguration
public class SpringCacheExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCacheExampleApplication.class, args);
    }

    @Component
    class AppRunner implements CommandLineRunner {

        private final ComputerRepository computerRepository;

        public AppRunner(final ComputerRepository computerRepository) {
            this.computerRepository = computerRepository;
        }

        @Override
        public void run(final String... args) {
            Stream.of(Computer.builder().computerId("1").creationDate(of(2015, 3, 8))
                            .model("model123").build(),
                    Computer.builder().computerId("2").creationDate(of(2017, 8, 8))
                            .model("model456").build(),
                    Computer.builder().computerId("3").creationDate(of(2016, 4, 8))
                            .model("model456").build())
                    .forEach(computerRepository::saveAndFlush);
        }

    }
}
