package org.example.springtestci.infra;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.springtestci.domain.Flower;

@Table(name = "flowers")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class FlowerJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String color;

    private int price;

    public static FlowerJpaEntity from(Flower flower) {
        return FlowerJpaEntity.builder()
                .name(flower.name())
                .color(flower.color())
                .price(flower.price())
                .build();
    }

    public Flower toDomain() {
        return new Flower(name, color, price);
    }
}
