package com.petta.catui.core;

import processing.core.PApplet;

public class AppletMain extends PApplet {

    private CatCharacter cat;
    private float scale = 1.0f;

    // もっと大きく表示するために、基準サイズを小さくしました。
    // 1000 -> 600 に変更したため、計算される倍率が約1.6倍大きくなります。
    private final float DESIGN_REF_SIZE = 600.0f; 

    @Override
    public void settings() {
        fullScreen(); 
    }

    @Override
    public void setup() {
        frameRate(60);

        float shorterSide = min(width, height);

        // 自動サイズ調整ロジック
        scale = shorterSide / DESIGN_REF_SIZE;

        println("Detected Screen Size: " + width + "x" + height);
        println("Calculated Scale: " + scale);

        cat = new CatCharacter(this, scale);
    }

    @Override
    public void draw() {
        background(0);
        
        // 【修正】translateを削除しました。
        // CatCharacter側ですでに画面中央(width/2, height/2)を使う計算になっている場合、
        // ここでtranslateすると二重に移動して右下にずれてしまうためです。
        // translate(width / 2, height / 2); 

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