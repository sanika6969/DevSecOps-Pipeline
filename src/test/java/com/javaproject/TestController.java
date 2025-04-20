package com.javaproject;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

import com.javaproject.beans.BoardGame;
import com.javaproject.beans.Review;
import com.javaproject.database.DatabaseAccess;

@WebMvcTest
class TestController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DatabaseAccess da;

    @Test
    public void testRoot() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void testAddBoardGame() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("name", "onecard");
        requestParams.add("level", "1");
        requestParams.add("minPlayers", "2");
        requestParams.add("maxPlayers", "+");
        requestParams.add("gameType", "Party Game");

        // Mock the behavior of da.getBoardGames()
        List<BoardGame> mockBoardGames = List.of(new BoardGame());
        when(da.getBoardGames()).thenReturn(mockBoardGames);

        mockMvc.perform(post("/boardgameAdded").params(requestParams))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/"))
                .andDo(print());

        // Verify that the method was called
        verify(da, times(1)).getBoardGames();
    }

    @Test
    public void testEditReview() throws Exception {
        List<BoardGame> boardGames = List.of(new BoardGame());  // Mocked data
        Long boardgameId = boardGames.get(0).getId();

        List<Review> reviews = List.of(new Review());  // Mocked data
        Review review = reviews.get(0);
        Long reviewId = review.getId();

        review.setText("Edited text");

        mockMvc.perform(post("/reviewAdded").flashAttr("review", review))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/" + review.getGameId() + "/reviews"));

        // Verify the mocked review update call
        verify(da, times(1)).getReview(reviewId);
    }

    @Test
    public void testDeleteReview() throws Exception {
        List<BoardGame> boardGames = List.of(new BoardGame());  // Mocked data
        Long boardgameId = boardGames.get(0).getId();

        List<Review> reviews = List.of(new Review());  // Mocked data
        Long reviewId = reviews.get(0).getId();

        int origSize = reviews.size();

        mockMvc.perform(get("/deleteReview/{id}", reviewId))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/" + boardgameId + "/reviews"));

        // Verify the mocked delete review call
        verify(da, times(1)).getReviews(boardgameId);
    }
}
