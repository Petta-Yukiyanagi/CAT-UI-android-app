package com.petta.catui.core;

import processing.core.PApplet;

public class AppletMain extends PApplet {

    private CatCharacter cat;
    private float scale = 1.0f;

    // 【解説】
    // この値は「スマホの画面サイズ」の設定ではなく、
    // 「scale = 1.0 (等倍) の時に、描画が想定しているキャンバスの幅」です。
    //
    // 例えば、PCで width=800, height=800 で開発していて、
    // その時にちょうどいいサイズに見えるなら、ここを 800.0f にします。
    // そうすると、スマホの画面幅が 1080px なら 1080/800 = 1.35倍 に自動拡大されます。
    // キャラクターが小さすぎる場合はこの数値を下げ、大きすぎる場合は上げてください。
    private final float DESIGN_REF_SIZE = 1000.0f; 

    @Override
    public void settings() {
        // ★ここですでに自動検知しています
        // これを書くだけで、どんな端末でもその端末の最大解像度が
        // 自動的に width と height に設定されます。
        fullScreen(); 
    }

    @Override
    public void setup() {
        frameRate(60);

        // 端末の画面サイズ (width, height) は自動で検知されています。
        // 短い方の辺（縦持ちなら幅）を取得して計算に使います。
        float shorterSide = min(width, height);

        // ★自動サイズ調整ロジック
        // 「検知した端末の実際の幅」 ÷ 「デザイン時の基準幅」 = 「拡大率(scale)」
        // これにより、解像度が低い端末でも高い端末でも、
        // 画面に対するキャラクターの見た目の大きさ（比率）が同じになります。
        scale = shorterSide / DESIGN_REF_SIZE;

        // デバッグ表示: 実際の端末の解像度を確認できます
        println("Detected Screen Size: " + width + "x" + height);
        println("Calculated Scale: " + scale);

        cat = new CatCharacter(this, scale);
    }

    @Override
    public void draw() {
        background(0);
        
        // 画面中央を基準（0,0）にする
        translate(width / 2, height / 2);

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