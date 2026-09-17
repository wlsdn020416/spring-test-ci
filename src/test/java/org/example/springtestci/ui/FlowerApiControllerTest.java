package org.example.springtestci.ui;

import org.example.springtestci.app.FlowerUseCase;
import org.example.springtestci.domain.Flower;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// UI 계층: FlowerApiController의 웹 계약을 검증
@WebMvcTest(FlowerApiController.class)
class FlowerApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FlowerUseCase flowerUseCase;

    @Test
    @DisplayName("전체 목록을 조회하면 200과 목록을 반환한다")
    void findAllReturnsList() throws Exception {
        // given: 두 송이를 반환하도록 가정
        given(flowerUseCase.findAll()).willReturn(List.of(
                new Flower("장미", "빨강", 3000),
                new Flower("튤립", "노랑", 2000)
        ));

        // when: 전체 목록을 조회하면
        mockMvc.perform(get("/api/flowers"))
                // then: 200과 목록을 반환한다
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("장미"))
                .andExpect(jsonPath("$[1].name").value("튤립"));
    }

    @Test
    @DisplayName("저장된 꽃이 없으면 200과 빈 목록을 반환한다")
    void findAllReturnsEmptyList() throws Exception {
        // given: 빈 목록을 반환하도록 가정
        given(flowerUseCase.findAll()).willReturn(List.of());

        // when: 전체 목록을 조회하면
        mockMvc.perform(get("/api/flowers"))
                // then: 200과 빈 목록을 반환한다
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    @DisplayName("꽃을 등록하면 201과 생성된 꽃을 반환한다")
    void saveReturnsCreatedFlower() throws Exception {
        // given: 저장 결과를 가정
        given(flowerUseCase.save(new Flower("장미", "빨강", 3000)))
                .willReturn(new Flower("장미", "빨강", 3000));

        // when: 꽃을 등록하면
        mockMvc.perform(post("/api/flowers")
                        .contentType(APPLICATION_JSON)
                        .content("{\"name\":\"장미\",\"color\":\"빨강\",\"price\":3000}"))
                // then: 201과 생성된 꽃을 반환한다
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("장미"))
                .andExpect(jsonPath("$.color").value("빨강"))
                .andExpect(jsonPath("$.price").value(3000));
    }

    @Test
    @DisplayName("개수를 조회하면 200과 개수를 반환한다")
    void countReturnsCount() throws Exception {
        // given: 개수를 2로 가정
        given(flowerUseCase.count()).willReturn(2L);

        // when: 개수를 조회하면
        mockMvc.perform(get("/api/flowers/count"))
                // then: 200과 개수를 반환한다
                .andExpect(status().isOk())
                .andExpect(content().string("2"));
    }
}
