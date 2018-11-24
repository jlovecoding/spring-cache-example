package com.example.cacheex.controller;

import com.example.cacheex.model.Computer;
import com.example.cacheex.service.ComputerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController(value = "/computers")
@Slf4j
public class ComputerController {

    private final ComputerService computerService;

    public ComputerController(final ComputerService computerService) {
        this.computerService = computerService;
    }

    @GetMapping
    public List<Computer> findByModel(@RequestParam final String model) {
        log.info("GET findByModel-" + model);
        return computerService.findByModel(model);
    }

    @PostMapping
    public Computer save(@RequestBody final Computer computer) {
        log.info("POST Create computer with model-" + computer.getModel());
        return computerService.save(computer);
    }
}
