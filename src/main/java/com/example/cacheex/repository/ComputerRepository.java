package com.example.cacheex.repository;

import com.example.cacheex.model.Computer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComputerRepository extends JpaRepository<Computer, String> {

    List<Computer> findByModel(final String model);
}
