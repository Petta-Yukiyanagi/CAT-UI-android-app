package com.petta.catui.ui;

import processing.core.PApplet;
import java.util.Timer;
import java.util.TimerTask;

import com.petta.catui.core.CatCharacter;
import com.petta.catui.text.TextDisplay;

public class CATUIApp extends PApplet {
  // フィールド
  private CatCharacter character;   // ← 自作のキャラクタークラス
  private TextDisplay textDisplay;
  private float globalScale;

  // タイマー（終了時に邪魔しないデーモン）
  private final Timer timer = new Timer(true);

  public static void main(String[] args) {
    PApplet.main(new String[] { CATUIApp.class.getName() });
  }

  // size()/fullScreen() は settings() で
  @Override
  public void settings() {
    fullScreen();
    smooth(8);
  }

  @Override
  public void setup() {
    

    // 元基準(400x300)に対する等倍スケール
    globalScale = min(width/400f, height/300f);

    // インスタンス化（PApplet参照は this）
    character   = new CatCharacter(this, globalScale);
    textDisplay = new TextDisplay(this, globalScale);

    // 自動送り設定
    textDisplay.setAutoAdvance(true, 2.5f); // 2.5秒後に自動送り
  }

  @Override
  public void draw() {
    background(0);
    character.update();
    character.draw();

    textDisplay.update();
    textDisplay.draw();
  }

  @Override
  public void keyPressed() {
    switch (key) {
      case '1':
        character.setExpression("NORMAL");
        textDisplay.showMessage("Hello,World! ,,^•ﻌ•^,,");
        break;
      case '2':
        character.setExpression("QUESTION");
        textDisplay.showMessage("うーん");
        break;
      case '3':
        character.setExpression("HAPPY");
        textDisplay.showMessage("嬉しい！");
        break;
      case '4':
        character.setExpression("SLEEPING");
        textDisplay.showMessage("おやすみ…");
        break;
      case '5':
        textDisplay.showMessage("Thank you for Leading ,,^•ﻌ•^,,");
        timer.schedule(new TimerTask() {
          @Override public void run() {
            character.setExpression("SMILE");
          }
        }, 2100);
        break;
      case ' ':
        textDisplay.nextPage();
        break;
    }
  }

  // 終了時にタイマーを止める
  @Override public void exit() {
    timer.cancel();
    super.exit();
  }
}
