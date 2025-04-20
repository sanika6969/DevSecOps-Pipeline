package com.javaproject.beans;

import lombok.Data;

@Data
public class BoardGame {
    private String name;
    private String level;
    private int minPlayers;
    private int maxPlayers;
    private String gameType;
    private Long id;
}
