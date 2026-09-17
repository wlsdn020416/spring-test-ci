package org.example.springtestci.app;

import org.example.springtestci.domain.Flower;
import org.example.springtestci.domain.FlowerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

// 애플리케이션 계층: FlowerUseCase가 저장소에 위임하는지 검증
@ExtendWith(MockitoExtension.class)
class FlowerServiceTest {

    @Mock
    private FlowerRepository flowerRepository;

    private FlowerUseCase flowerUseCase;

    @BeforeEach
    void setUp() {
        // given: 저장소를 주입한 유스케이스 준비
        flowerUseCase = new FlowerService(flowerRepository);
    }

    @Test
    @DisplayName("개수를 조회하면 저장소 개수를 반환한다")
    void countReturnsRepositoryCount() {
        // given: 저장소 개수를 2로 가정
        given(flowerRepository.count()).willReturn(2L);

        // when: 전체 개수를 조회하면
        long count = flowerUseCase.count();

        // then: 저장소 개수를 반환한다
        assertThat(count).isEqualTo(2L);
        then(flowerRepository).should().count();
    }

    @Test
    @DisplayName("꽃을 저장하면 저장된 꽃을 반환한다")
    void saveReturnsSavedFlower() {
        // given: 저장할 꽃과 저장 결과를 준비
        Flower flower = new Flower("장미", "빨강", 3000);
        given(flowerRepository.save(flower)).willReturn(flower);

        // when: 꽃을 저장하면
        Flower saved = flowerUseCase.save(flower);

        // then: 저장된 꽃을 반환한다
        assertThat(saved).isEqualTo(flower);
        then(flowerRepository).should().save(flower);
    }

    @Test
    @DisplayName("전체 목록을 조회하면 저장소 목록을 반환한다")
    void findAllReturnsRepositoryFlowers() {
        // given: 저장소가 두 송이를 보관한다고 가정
        List<Flower> flowers = List.of(
                new Flower("장미", "빨강", 3000),
                new Flower("튤립", "노랑", 2000)
        );
        given(flowerRepository.findAll()).willReturn(flowers);

        // when: 전체 목록을 조회하면
        List<Flower> found = flowerUseCase.findAll();

        // then: 저장소 목록을 반환한다
        assertThat(found).containsExactlyElementsOf(flowers);
        then(flowerRepository).should().findAll();
    }
}
