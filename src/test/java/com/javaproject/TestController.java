package com.javaproject;

import com.javaproject.beans.BoardGame;
import com.javaproject.beans.Review;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(TestController.class)  // Focuses on testing only the web layer (controller)
public class TestController {

    // Endpoint to get a board game by name
    @GetMapping("/game")
    public String get(String name) {
        return "Game details for " + name;
    }

    // Endpoint to get status of the game
    @GetMapping("/status")
    public String status() {
        return "Game is active";
    }

    // Endpoint to get a view of the game
    @GetMapping("/view")
    public String view() {
        return "Viewing game";
    }

    // POST endpoint to create a new board game
    @PostMapping("/game")
    public String post(String name) {
        return "Created board game: " + name;
    }

    // POST endpoint to handle redirection
    @PostMapping("/redirect")
    public String redirect() {
        return "Redirected to new game page";
    }

    // Test Controller Test Class
    @WebMvcTest(TestController.class)  // Used to test only the controller
    public static class TestControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Test
        public void testGetMethod() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/game?name=chess"))
                   .andExpect(MockMvcResultMatchers.status().isOk())
                   .andExpect(MockMvcResultMatchers.content().string("Game details for chess"));
        }

        @Test
        public void testStatusMethod() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/status"))
                   .andExpect(MockMvcResultMatchers.status().isOk())
                   .andExpect(MockMvcResultMatchers.content().string("Game is active"));
        }

        @Test
        public void testPostMethod() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.post("/game")
                   .contentType(MediaType.APPLICATION_JSON)
                   .content("{\"name\":\"Monopoly\"}"))
                   .andExpect(MockMvcResultMatchers.status().isOk())
                   .andExpect(MockMvcResultMatchers.content().string("Created board game: Monopoly"));
        }

        @Test
        public void testRedirectMethod() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.post("/redirect"))
                   .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                   .andExpect(MockMvcResultMatchers.redirectedUrl("/newgame"));
        }
    }
}
