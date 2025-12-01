// =============================================================================
// 本体 (Characterクラス)
// =============================================================================
package com.petta.catui.core;

import processing.core.PApplet;
import java.util.*;
import java.util.function.Function;

public class CatCharacter {
    private IExpressionState currentState;
    private ExpressionConfig currentConfig;
    final FacePartFactory factory;
    final ExpressionRegistry registry;

    public CatCharacter(PApplet app, float scale) {
        this.factory = new FacePartFactory(app, scale);
        this.registry = new ExpressionRegistry();
        initializeExpressions();
        setExpression("NORMAL");
    }

    private void initializeExpressions() {
        registry.register(new ExpressionConfig(
            "NORMAL", new String[]{"normal", "ノーマル", "通常"}, '1',
            f -> new ExpressionBuilder(f)
                .eyes("blinking")
                .ears("earTwitch")
                .mouth("normal")
                .build()
        ));
        
        registry.register(new ExpressionConfig(
            "QUESTION", new String[]{"question", "疑問", "thinking"}, '2',
            f -> new ExpressionBuilder(f)
                .eyes("blinking")
                .ears("earTwitch")
                .mouth("happy")
                .effect("questionMark")
                .build()
        ));

        registry.register(new ExpressionConfig(
            "HAPPY", new String[]{"happy", "嬉しい"}, '3',
            f -> new ExpressionBuilder(f)
                .eyes("sparkle")
                .ears("earFlap")
                .mouth("surprised")
                .build()
        ));

        registry.register(new ExpressionConfig(
            "SLEEPING", new String[]{"sleeping", "sleep", "寝る"}, '4',
            f -> new ExpressionBuilder(f)
                .eyes("sleepEyes")
                .ears("earDroop")
                .effect("zzz") 
                .build() 
        ));

        registry.register(new ExpressionConfig(
            "SMILE", new String[]{"smile", "笑顔"}, '5',
            f -> new ExpressionBuilder(f)
                .eyes("NikoEyes")
                .ears("earFlap")
                .mouth("nikoSmile")
                .build()
        ));

    }

    /**
     * 実行時に新しい表情を動的に追加します。
     * @param config 追加する表情の設定
     */
    public void addExpression(ExpressionConfig config) {
        registry.register(config);
    }

    /**
     * 文字列で表情を変更します。
     * @param name 表情の正式名または別名
     */
    public void setExpression(String name) {
        if (name == null) return;
        ExpressionConfig config = registry.findByName(name.trim());
        if (config != null) {
            setExpression(config);
        }
    }

    /**
     * 設定オブジェクトに基づいて表情を内部的に変更します。
     * @param config 適用する表情設定
     */
    private void setExpression(ExpressionConfig config) {
        if (config == null || config == currentConfig) return;
        currentState = config.stateFactory.apply(factory);
        currentConfig = config;
    }

    /**
     * 現在の表情の正式名を取得します。
     * @return 現在の表情名
     */
    public String getCurrentExpressionName() {
        return currentConfig != null ? currentConfig.name : "UNKNOWN";
    }

    /**
     * 現在の表情状態を更新します。
     */
    public void update() {
        if (currentState != null) {
            currentState.update();
        }
    }

    /**
     * 現在の表情状態を描画します。
     */
    public void draw() {
        if (currentState != null) {
            currentState.draw();
        }
    }
}