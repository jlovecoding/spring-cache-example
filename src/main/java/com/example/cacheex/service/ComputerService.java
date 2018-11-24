package com.example.cacheex.service;

import com.example.cacheex.model.Computer;
import com.example.cacheex.repository.ComputerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ComputerService {

    private final ComputerRepository computerRepository;

    public ComputerService(final ComputerRepository computerRepository) {
        this.computerRepository = computerRepository;
    }

    @Cacheable("computers")
    public List<Computer> findByModel(final String model) {
        log.info("find computers with model-" + model);
        return computerRepository.findByModel(model);
    }

    @CacheEvict(value = "computers", key = "#computer.model")
    public Computer save(final Computer computer) {
        log.info("create computer with model-" + computer.getModel());
        return computerRepository.save(computer);
    }
}
