package com.demo.cache;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


@Entity
@Accessors(chain = true)
@Data
@NoArgsConstructor
public class Prouduct implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String code;
    private int quantity;
    private double price;
}
