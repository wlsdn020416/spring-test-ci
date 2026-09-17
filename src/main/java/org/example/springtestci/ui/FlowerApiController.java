package org.example.springtestci.ui;

import lombok.RequiredArgsConstructor;
import org.example.springtestci.app.FlowerUseCase;
import org.example.springtestci.domain.Flower;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/flowers")
@RequiredArgsConstructor
public class FlowerApiController {

    private final FlowerUseCase flowerUseCase;

    @GetMapping
    public List<FlowerDto> findAll() {
        return flowerUseCase.findAll().stream()
                .map(FlowerApiController::toDto)
                .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FlowerDto save(@RequestBody FlowerDto request) {
        Flower saved = flowerUseCase.save(toDomain(request));
        return toDto(saved);
    }

    @GetMapping("/count")
    public long count() {
        return flowerUseCase.count();
    }

    private static Flower toDomain(FlowerDto dto) {
        return new Flower(dto.name(), dto.color(), dto.price());
    }

    private static FlowerDto toDto(Flower flower) {
        return new FlowerDto(flower.name(), flower.color(), flower.price());
    }
}
