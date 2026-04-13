package com.wayne.board_game_buddy.service.implementation;

import com.wayne.board_game_buddy.models.Answer;
import com.wayne.board_game_buddy.models.Question;
import com.wayne.board_game_buddy.service.IBoardGameService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class BoardGameService implements IBoardGameService {
    private final ChatClient chatClient;
    public BoardGameService(ChatClient.Builder chatClientBuilder) {
        chatClient = chatClientBuilder.build();
    }
    @Override
    public Answer askQuestion(Question question) {
        var answerText = chatClient.prompt()
                .user(question.question())
                .call()
                .content();
        return new Answer(answerText);
    }
}
