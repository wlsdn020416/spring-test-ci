package org.example.springtestci.infra;

import org.example.springtestci.domain.Flower;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FlowerRepositoryImplTest {

    private FlowerRepositoryImpl flowerRepository;

    @BeforeEach
    void setUp() {
        // given: 매 테스트마다 빈 저장소 준비
        flowerRepository = new FlowerRepositoryImpl();
    }

    @Test
    @DisplayName("저장된 꽃이 없으면 개수는 0이다")
    void countIsZeroWhenEmpty() {
        // given: 꽃이 하나도 없는 저장소
        // when: 전체 개수를 조회하면
        long count = flowerRepository.count();

        // then: 0을 반환한다
        assertThat(count).isZero();
    }

    @Test
    @DisplayName("저장된 꽃이 없으면 목록은 비어있다")
    void findAllIsEmptyWhenEmpty() {
        // given: 꽃이 하나도 없는 저장소
        // when: 전체 목록을 조회하면
        List<Flower> flowers = flowerRepository.findAll();

        // then: 빈 목록을 반환한다
        assertThat(flowers).isEmpty();
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
    @DisplayName("꽃을 한 송이 저장하면 개수는 1이다")
    void countIsOneAfterSingleSave() {
        // given: 꽃 한 송이를 저장
        flowerRepository.save(new Flower("장미", "빨강", 3000));

        // when: 전체 개수를 조회하면
        long count = flowerRepository.count();

        // then: 1을 반환한다
        assertThat(count).isEqualTo(1L);
    }

    @Test
    @DisplayName("꽃을 두 송이 저장하면 개수는 2이다")
    void countIsTwoAfterTwoSaves() {
        // given: 서로 다른 꽃 두 송이를 저장
        flowerRepository.save(new Flower("장미", "빨강", 3000));
        flowerRepository.save(new Flower("튤립", "노랑", 2000));

        // when: 전체 개수를 조회하면
        long count = flowerRepository.count();

        // then: 2를 반환한다
        assertThat(count).isEqualTo(2L);
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

    @Test
    @DisplayName("꽃을 저장한 순서대로 목록을 반환한다")
    void findAllKeepsSaveOrder() {
        // given: 두 송이를 순서대로 저장
        Flower rose = new Flower("장미", "빨강", 3000);
        Flower tulip = new Flower("튤립", "노랑", 2000);
        flowerRepository.save(rose);
        flowerRepository.save(tulip);

        // when: 전체 목록을 조회하면
        List<Flower> flowers = flowerRepository.findAll();

        // then: 저장한 순서대로 반환한다
        assertThat(flowers).containsExactly(rose, tulip);
    }

    @Test
    @DisplayName("같은 꽃을 두 번 저장하면 모두 보관한다")
    void findAllKeepsDuplicatedFlowers() {
        // given: 같은 꽃을 두 번 저장
        Flower flower = new Flower("장미", "빨강", 3000);
        flowerRepository.save(flower);
        flowerRepository.save(flower);

        // when: 전체 목록을 조회하면
        List<Flower> flowers = flowerRepository.findAll();

        // then: 두 송이 모두 반환한다
        assertThat(flowers).containsExactly(flower, flower);
    }
}
