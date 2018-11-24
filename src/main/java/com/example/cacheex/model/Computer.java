package com.example.cacheex.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "computers")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Computer implements Serializable {

    @Id
    private String computerId;

    private String model;

    private LocalDate creationDate;
}
