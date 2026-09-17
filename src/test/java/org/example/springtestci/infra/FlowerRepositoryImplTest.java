package org.example.springtestci.infra;

import org.example.springtestci.domain.Flower;
import org.example.springtestci.domain.FlowerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FlowerRepositoryImplTest {

    private FlowerRepository flowerRepository;

    @BeforeEach
    void setUp() {
        flowerRepository = new FlowerRepositoryImpl();
    }

    @Test
    @DisplayName("저장된 꽃이 없으면 개수는 0이다")
    void countReturnsZeroWhenEmpty() {
        // given: 꽃이 하나도 없는 저장소
        // when: 전체 개수를 조회하면
        long count = flowerRepository.count();

        // then: 0을 반환한다
        assertThat(count).isZero();
    }

    @Test
    @DisplayName("꽃을 저장하면 저장된 꽃을 반환한다")
    void saveReturnsSavedFlower() {
        // given: 저장할 꽃을 준비
        Flower flower = new Flower("장미", "빨강", 3000);

        // when: 꽃을 저장하면
        Flower saved = flowerRepository.save(flower);

        // then: 저장한 꽃을 그대로 반환한다
        assertThat(saved).isEqualTo(flower);
    }

    @Test
    @DisplayName("꽃을 저장하면 개수가 1 증가한다")
    void countIncreasesAfterSave() {
        // given: 꽃 한 송이를 저장
        flowerRepository.save(new Flower("장미", "빨강", 3000));

        // when: 전체 개수를 조회하면
        long count = flowerRepository.count();

        // then: 1을 반환한다
        assertThat(count).isEqualTo(1L);
    }

    @Test
    @DisplayName("저장된 꽃이 없으면 목록은 비어있다")
    void findAllReturnsEmptyWhenEmpty() {
        // given: 꽃이 하나도 없는 저장소
        // when: 전체 목록을 조회하면
        List<Flower> flowers = flowerRepository.findAll();

        // then: 빈 목록을 반환한다
        assertThat(flowers).isEmpty();
    }

    @Test
    @DisplayName("꽃을 저장하면 전체 목록에 포함된다")
    void findAllContainsSavedFlower() {
        // given: 꽃 한 송이를 저장
        Flower flower = new Flower("장미", "빨강", 3000);
        flowerRepository.save(flower);

        // when: 전체 목록을 조회하면
        List<Flower> flowers = flowerRepository.findAll();

        // then: 저장한 꽃을 포함한다
        assertThat(flowers).containsExactly(flower);
    }
}
