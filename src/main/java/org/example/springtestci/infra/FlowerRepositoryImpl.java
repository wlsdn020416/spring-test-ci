package org.example.springtestci.infra;

import lombok.RequiredArgsConstructor;
import org.example.springtestci.domain.Flower;
import org.example.springtestci.domain.FlowerRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FlowerRepositoryImpl implements FlowerRepository {

    private final FlowerJpaRepository flowerJpaRepository;

    @Override
    public long count() {
        return flowerJpaRepository.count();
    }

    @Override
    public Flower save(Flower flower) {
        return flowerJpaRepository.save(FlowerJpaEntity.from(flower))
                .toDomain();
    }

    @Override
    public List<Flower> findAll() {
        return flowerJpaRepository.findAll().stream()
                .map(FlowerJpaEntity::toDomain)
                .toList();
    }
}
