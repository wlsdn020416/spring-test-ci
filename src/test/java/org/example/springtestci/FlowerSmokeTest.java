package org.example.springtestci;

import org.example.springtestci.infra.FlowerJpaRepository;
import org.example.springtestci.ui.FlowerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.Assertions.assertThat;

// 스모크 테스트: 실제 서버 기동 후 전체 흐름을 검증
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FlowerSmokeTest {

    @LocalServerPort
    private int port;

    @Autowired
    private FlowerJpaRepository flowerJpaRepository;

    private RestClient restClient;

    @BeforeEach
    void setUp() {
        // given: 테스트 전 저장소를 비우고 클라이언트 준비
        flowerJpaRepository.deleteAll();
        restClient = RestClient.create();
    }

    private String url(String path) {
        return "http://localhost:" + port + path;
    }

    @Test
    @DisplayName("꽃을 등록하면 목록과 개수에 반영된다")
    void saveThenFindAllAndCount() {
        // given: 등록할 꽃을 준비
        FlowerDto request = new FlowerDto("장미", "빨강", 3000);

        // when: 꽃을 등록하면
        ResponseEntity<FlowerDto> created = restClient.post()
                .uri(url("/api/flowers"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .toEntity(FlowerDto.class);

        // then: 201과 생성된 꽃을 반환한다
        assertThat(created.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(created.getBody()).isEqualTo(request);

        // when: 전체 목록을 조회하면
        FlowerDto[] found = restClient.get()
                .uri(url("/api/flowers"))
                .retrieve()
                .body(FlowerDto[].class);

        // then: 등록한 꽃을 포함한다
        assertThat(found).containsExactly(request);

        // when: 개수를 조회하면
        Long count = restClient.get()
                .uri(url("/api/flowers/count"))
                .retrieve()
                .body(Long.class);

        // then: 1을 반환한다
        assertThat(count).isEqualTo(1L);
    }

    @Test
    @DisplayName("저장된 꽃이 없으면 빈 목록과 0을 반환한다")
    void emptyState() {
        // when: 전체 목록을 조회하면
        FlowerDto[] found = restClient.get()
                .uri(url("/api/flowers"))
                .retrieve()
                .body(FlowerDto[].class);

        // then: 빈 목록을 반환한다
        assertThat(found).isEmpty();

        // when: 개수를 조회하면
        Long count = restClient.get()
                .uri(url("/api/flowers/count"))
                .retrieve()
                .body(Long.class);

        // then: 0을 반환한다
        assertThat(count).isZero();
    }
}
