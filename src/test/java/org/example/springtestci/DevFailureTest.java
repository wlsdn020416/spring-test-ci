package org.example.springtestci;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

// dev 브랜치 CI 실패 확인용 테스트
class DevFailureTest {

    @Test
    @DisplayName("의도적으로 실패하는 테스트")
    void intentionallyFails() {
        // given: 값 1을 준비
        int value = 1;

        // when: 2와 비교하면
        // then: 의도적으로 실패한다
        assertThat(value).isEqualTo(2);
    }
}
