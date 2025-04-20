package com.javaproject;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

import com.javaproject.beans.BoardGame;
import com.javaproject.beans.Review;
import com.javaproject.database.DatabaseAccess;

@SpringBootTest
@AutoConfigureMockMvc
class TestController {

    @Mock
    private DatabaseAccess da;  // Mock the DatabaseAccess class

    @InjectMocks
    private TestController controller;  // Inject mocks into the controller

    @Autowired
    private MockMvc mockMvc;  // MockMvc is auto-configured

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks
    }

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

        // Mock the behavior of getBoardGames()
        List<BoardGame> mockedBoardGames = new ArrayList<>();
        mockedBoardGames.add(new BoardGame("onecard", 1, 2, "+", "Party Game"));
        when(da.getBoardGames()).thenReturn(mockedBoardGames);

        int origSize = da.getBoardGames().size();
        mockMvc.perform(post("/boardgameAdded").params(requestParams))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/"))
                .andDo(print());
        int newSize = da.getBoardGames().size();
        assertEquals(newSize, origSize + 1);
    }

    @Test
    public void testEditReview() throws Exception {
        // Mock the behavior of getReviews()
        List<BoardGame> boardGames = new ArrayList<>();
        boardGames.add(new BoardGame("Game 1", 1, 2, "+", "Strategy"));
        Long boardgameId = boardGames.get(0).getId();

        List<Review> reviews = new ArrayList<>();
        reviews.add(new Review(boardgameId, "Good game!"));
        Review review = reviews.get(0);
        Long reviewId = review.getId();

        when(da.getReviews(boardgameId)).thenReturn(reviews);

        review.setText("Edited text");

        mockMvc.perform(post("/reviewAdded").flashAttr("review", review))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/" + review.getGameId() + "/reviews"));

        review = da.getReview(reviewId);
        assertEquals(review.getText(), "Edited text");
    }

    @Test
    public void testDeleteReview() throws Exception {
        // Mock the behavior of getReviews() and deleteReview()
        List<BoardGame> boardGames = new ArrayList<>();
        boardGames.add(new BoardGame("Game 1", 1, 2, "+", "Strategy"));
        Long boardgameId = boardGames.get(0).getId();

        List<Review> reviews = new ArrayList<>();
        reviews.add(new Review(boardgameId, "Good game!"));
        Long reviewId = reviews.get(0).getId();

        when(da.getReviews(boardgameId)).thenReturn(reviews);

        int origSize = reviews.size();

        mockMvc.perform(get("/deleteReview/{id}", reviewId))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/" + boardgameId + "/reviews"));

        // Verify the deleteReview method is called
        verify(da, times(1)).deleteReview(reviewId);

        int newSize = da.getReviews(boardgameId).size();

        assertEquals(newSize, origSize - 1);
    }
}
