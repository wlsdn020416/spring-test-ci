package org.example.springtestci.app;

import lombok.RequiredArgsConstructor;
import org.example.springtestci.domain.Flower;
import org.example.springtestci.domain.FlowerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlowerService implements FlowerUseCase {

    private final FlowerRepository flowerRepository;

    @Override
    public long count() {
        return flowerRepository.count();
    }

    @Override
    public Flower save(Flower flower) {
        return flowerRepository.save(flower);
    }

    @Override
    public List<Flower> findAll() {
        return flowerRepository.findAll();
    }
}
