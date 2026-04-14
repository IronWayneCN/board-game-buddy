package com.wayne.board_game_buddy.controller;

import com.wayne.board_game_buddy.models.Answer;
import com.wayne.board_game_buddy.models.Question;
import com.wayne.board_game_buddy.service.IBoardGameService;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class askController {
    private final IBoardGameService boardGameService;
    public askController(IBoardGameService boardGameService) {
        this.boardGameService = boardGameService;
    }
    @PostMapping(value = "/ask",consumes = "application/json", produces = "application/json")
    public Answer ask(@RequestBody Question question){
        return boardGameService.askQuestion(question);
    }

}
