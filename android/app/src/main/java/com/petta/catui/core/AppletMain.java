package com.petta.catui.core;

import processing.core.PApplet;

public class AppletMain extends PApplet {

    private CatCharacter cat;
    private float scale = 1.0f;

    // 基本の基準サイズ（縦画面や正方形のとき用）
    // この値を小さくすると、全体的にキャラが大きくなります。
    private final float DESIGN_REF_SIZE = 600.0f; 

    // ★追加: 横画面の時に「さらに」どれくらい大きくするか（倍率）
    // 1.0だと変化なし、1.5だと1.5倍になります。
    // お好みで 1.2f ～ 1.8f くらいの間で調整してください。
    private final float LANDSCAPE_SCALE_FACTOR = 2.0f;

    @Override
    public void settings() {
        fullScreen(); 
    }

    @Override
    public void setup() {
        frameRate(60);

        // 画面の短い方の辺（縦持ちなら幅、横持ちなら高さ）を取得
        float shorterSide = min(width, height);

        // 基本のサイズ計算
        scale = shorterSide / DESIGN_REF_SIZE;

        // ★追加: 横画面（幅 > 高さ）の場合の特別処理
        if (width > height) {
            // 横画面なら、さらに拡大倍率をかける
            scale *= LANDSCAPE_SCALE_FACTOR;
            println("Landscape mode detected! Applying extra scale: x" + LANDSCAPE_SCALE_FACTOR);
        }

        println("Detected Screen Size: " + width + "x" + height);
        println("Calculated Scale: " + scale);

        cat = new CatCharacter(this, scale);
    }

    @Override
    public void draw() {
        background(0);
        
        // CatCharacter側で画面中央(width/2, height/2)を使う計算になっているため
        // ここでのtranslateは不要です。

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