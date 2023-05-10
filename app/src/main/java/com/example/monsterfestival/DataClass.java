package com.example.monsterfestival;

public class DataClass {
    private String name;
    private String race;
    private String m_class;
    private String background;
    private String alignment;
    private String level;
    private String points;
    private String key;



    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getName() {
        return name;
    }
    public String getRace() {
        return race;
    }
    public String getM_class() {
        return m_class;
    }
    public String getBackground() {
        return background;
    }
    public String getAlignment() {
        return alignment;
    }
    public String getLevel() {
        return level;
    }
    public String getPoints() {
        return points;
    }

    public DataClass(String name, String race, String m_class, String background, String alignment, String level, String points) {
        this.name = name;
        this.race = race;
        this.m_class = m_class;
        this.background = background;
        this.alignment = alignment;
        this.level = level;
        this.points = points;
    }

    public DataClass(){
    }
}

