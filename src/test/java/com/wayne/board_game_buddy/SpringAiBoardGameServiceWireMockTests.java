package com.wayne.board_game_buddy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;

import com.wayne.board_game_buddy.models.Question;
import com.wayne.board_game_buddy.service.implementation.BoardGameService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;

import java.io.IOException;
import java.nio.charset.Charset;

@EnableWireMock(@ConfigureWireMock(baseUrlProperties = "deepseek.base.url"))
@SpringBootTest(properties = "spring.ai.deepseek.base-url=${deepseek.base.url}")
public class SpringAiBoardGameServiceWireMockTests {
    @Value("classpath:/test-deepseek-response.json")
    Resource responseResource;
    @Autowired
    ChatClient.Builder chatClientBuilder;
    @BeforeEach
    public void setup() throws IOException {
        var cannedResponse = responseResource.getContentAsString(Charset.defaultCharset());
        var mapper = new ObjectMapper();
        var responseNode = mapper.readTree(cannedResponse);
        WireMock.stubFor(WireMock.post("/chat/completions")
                .willReturn(ResponseDefinitionBuilder.okForJson(responseNode)));
    }

    @Test
    public void testAskQuestion() {
        var boardGameService =
                new BoardGameService(chatClientBuilder);
        var answer =
                boardGameService.askQuestion(
                        new Question("What is the capital of France?"));
        Assertions.assertThat(answer).isNotNull();
        Assertions.assertThat(answer.answer()).isEqualTo("Paris");
    }
}
