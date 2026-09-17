package org.example.springtestci.infra;

import org.example.springtestci.domain.Flower;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

// 인프라 계층: JPA 저장소와의 매핑/위임을 검증
@ExtendWith(MockitoExtension.class)
class FlowerRepositoryImplTest {

    @Mock
    private FlowerJpaRepository flowerJpaRepository;

    @InjectMocks
    private FlowerRepositoryImpl flowerRepository;

    @Test
    @DisplayName("개수를 조회하면 JPA 저장소 개수를 반환한다")
    void countDelegatesToJpaRepository() {
        // given: JPA 저장소 개수를 2로 가정
        given(flowerJpaRepository.count()).willReturn(2L);

        // when: 전체 개수를 조회하면
        long count = flowerRepository.count();

        // then: JPA 저장소의 개수를 반환한다
        assertThat(count).isEqualTo(2L);
        then(flowerJpaRepository).should().count();
    }

    @Test
    @DisplayName("꽃을 저장하면 엔티티로 변환해 저장한다")
    void saveConvertsDomainToEntity() {
        // given: 저장할 꽃을 준비
        Flower flower = new Flower("장미", "빨강", 3000);
        given(flowerJpaRepository.save(any(FlowerJpaEntity.class)))
                .willReturn(FlowerJpaEntity.from(flower));

        // when: 꽃을 저장하면
        Flower saved = flowerRepository.save(flower);

        // then: 저장한 꽃을 도메인으로 반환한다
        assertThat(saved).isEqualTo(flower);

        // then: 엔티티로 변환해 JPA 저장소에 위임한다
        ArgumentCaptor<FlowerJpaEntity> captor = ArgumentCaptor.forClass(FlowerJpaEntity.class);
        then(flowerJpaRepository).should().save(captor.capture());
        assertThat(captor.getValue().toDomain()).isEqualTo(flower);
    }

    @Test
    @DisplayName("전체 목록을 조회하면 도메인 목록으로 변환한다")
    void findAllConvertsEntitiesToDomain() {
        // given: 두 송이를 저장소가 보관한다고 가정
        Flower rose = new Flower("장미", "빨강", 3000);
        Flower tulip = new Flower("튤립", "노랑", 2000);
        given(flowerJpaRepository.findAll())
                .willReturn(List.of(FlowerJpaEntity.from(rose), FlowerJpaEntity.from(tulip)));

        // when: 전체 목록을 조회하면
        List<Flower> flowers = flowerRepository.findAll();

        // then: 도메인 목록으로 변환해 반환한다
        assertThat(flowers).containsExactly(rose, tulip);
    }

    @Test
    @DisplayName("저장된 꽃이 없으면 빈 목록을 반환한다")
    void findAllReturnsEmptyWhenEmpty() {
        // given: 저장소가 빈 상태
        given(flowerJpaRepository.findAll()).willReturn(List.of());

        // when: 전체 목록을 조회하면
        List<Flower> flowers = flowerRepository.findAll();

        // then: 빈 목록을 반환한다
        assertThat(flowers).isEmpty();
    }
}
