package com.javaproject.beans;
 
public class BoardGame {
    private String name;
    private String level;
    private int minPlayers;
    private int maxPlayers;
    private String gameType;
    private Long id;
 
    // Add the getter methods
 public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
   
 public String getName() {
        return name;
    }
 
    public String getLevel() {
        return level;
    }
 
    public int getMinPlayers() {
        return minPlayers;
    }
 
    public int getMaxPlayers() {
        return maxPlayers;
    }
 
    public String getGameType() {
        return gameType;
    }
}
