package org.example.springtestci.app;

import org.example.springtestci.domain.Flower;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// 통합 테스트: 실제 스프링 컨텍스트와 H2 JPA로 검증
@SpringBootTest
@Transactional
class FlowerServiceIntegrationTest {

    @Autowired
    private FlowerUseCase flowerUseCase;

    @Test
    @DisplayName("저장된 꽃이 없으면 개수는 0이다")
    void countIsZeroWhenEmpty() {
        // given: 빈 저장소
        // when: 전체 개수를 조회하면
        long count = flowerUseCase.count();

        // then: 0을 반환한다
        assertThat(count).isZero();
    }

    @Test
    @DisplayName("꽃을 저장하면 개수가 1 증가한다")
    void saveIncreasesCount() {
        // given: 저장할 꽃을 준비
        Flower flower = new Flower("장미", "빨강", 3000);

        // when: 꽃을 저장하면
        flowerUseCase.save(flower);

        // then: 개수가 1이 된다
        assertThat(flowerUseCase.count()).isEqualTo(1L);
    }

    @Test
    @DisplayName("저장한 꽃을 전체 조회에서 확인한다")
    void findAllContainsSavedFlower() {
        // given: 꽃 한 송이를 저장
        Flower flower = new Flower("장미", "빨강", 3000);
        flowerUseCase.save(flower);

        // when: 전체 목록을 조회하면
        List<Flower> flowers = flowerUseCase.findAll();

        // then: 저장한 꽃을 포함한다
        assertThat(flowers).containsExactly(flower);
    }

    @Test
    @DisplayName("여러 송이를 저장하면 모두 조회된다")
    void findAllReturnsAllSavedFlowers() {
        // given: 두 송이를 저장
        Flower rose = new Flower("장미", "빨강", 3000);
        Flower tulip = new Flower("튤립", "노랑", 2000);
        flowerUseCase.save(rose);
        flowerUseCase.save(tulip);

        // when: 전체 목록을 조회하면
        List<Flower> flowers = flowerUseCase.findAll();

        // then: 저장한 두 송이를 모두 반환한다
        assertThat(flowerUseCase.count()).isEqualTo(2L);
        assertThat(flowers).containsExactlyInAnyOrder(rose, tulip);
    }
}
