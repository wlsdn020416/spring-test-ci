package org.example.springtestci.app;

import org.example.springtestci.domain.Flower;

import java.util.List;

public interface FlowerUseCase {
    long count();
    Flower save(Flower flower);
    List<Flower> findAll();
}
