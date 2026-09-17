package org.example.springtestci.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.then;

// 도메인 계층: FlowerRepository 포트의 상호작용(위임)만 검증
@ExtendWith(MockitoExtension.class)
class FlowerRepositoryTest {

    @Mock
    private FlowerRepository flowerRepository;

    @Test
    @DisplayName("save는 전달받은 꽃으로 위임된다")
    void saveDelegatesWithFlower() {
        // given: 저장할 꽃을 준비
        Flower flower = new Flower("장미", "빨강", 3000);

        // when: 꽃을 저장하면
        flowerRepository.save(flower);

        // then: 전달받은 꽃으로 save를 호출한다
        then(flowerRepository).should().save(flower);
    }

    @Test
    @DisplayName("count는 저장소에 위임된다")
    void countDelegates() {
        // when: 전체 개수를 조회하면
        flowerRepository.count();

        // then: count를 한 번 호출한다
        then(flowerRepository).should().count();
    }

    @Test
    @DisplayName("findAll은 저장소에 위임된다")
    void findAllDelegates() {
        // when: 전체 목록을 조회하면
        flowerRepository.findAll();

        // then: findAll을 한 번 호출한다
        then(flowerRepository).should().findAll();
    }
}
