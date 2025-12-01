package com.petta.catui.core;

import processing.core.PApplet;

public class AppletMain extends PApplet {

    private CatCharacter cat;
    private float scale = 1.0f;

    @Override
    public void settings() {
        fullScreen();
    }

    @Override
    public void setup() {
        frameRate(60);
        cat = new CatCharacter(this, scale);
    }

    @Override
    public void draw() {
        background(0);
        if (cat != null) {
            cat.update();
            cat.draw();
        }
    }

    @Override
    public void keyPressed() {
        if (cat == null) return;

        switch (key) {
            case '1': cat.setExpression("NORMAL"); break;
            case '2': cat.setExpression("QUESTION"); break;
            case '3': cat.setExpression("HAPPY"); break;
            case '4': cat.setExpression("SLEEPING"); break;
            case '5': cat.setExpression("SMILE"); break;
        }
    }
}
