package com.wayne.board_game_buddy.service;

import com.wayne.board_game_buddy.models.Answer;
import com.wayne.board_game_buddy.models.Question;

public interface IBoardGameService {
    Answer askQuestion(Question question);
}
