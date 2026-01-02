package com.petta.catui.comm;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.petta.catui.core.CatCharacter;
import com.petta.catui.core.ExpressionState;
import com.petta.catui.text.TextDisplay;

public class UiEventController {

    private final CatCharacter character;
    private final TextDisplay textDisplay;
    private final ConcurrentLinkedQueue<Runnable> uiQueue;
    private final Timer timer = new Timer(true);

    private TimerTask resetTask;

    // ★ 修正：uiQueue を受け取る
    public UiEventController(
            CatCharacter character,
            TextDisplay textDisplay,
            ConcurrentLinkedQueue<Runnable> uiQueue) {

        this.character = character;
        this.textDisplay = textDisplay;
        this.uiQueue = uiQueue;
    }

    public void onEvent(UiEvent event) {

        uiQueue.add(() -> {
            textDisplay.showMessage("UiEventController.onEvent() CALLED");
        });

        // ★ UI操作はキュー経由
        uiQueue.add(() -> {
            if (event.face != null) {
                character.setExpression(event.face);
            }
            if (event.text != null && !event.text.isEmpty()) {
                textDisplay.showMessage(event.text);
            }
        });

        // reset_after 処理
        if (resetTask != null) {
            resetTask.cancel();
        }

        if (event.resetAfter > 0) {
            resetTask = new TimerTask() {
                @Override
                public void run() {
                    uiQueue.add(() -> character.setExpression(ExpressionState.NORMAL));
                }
            };
            timer.schedule(resetTask, (long) (event.resetAfter * 1000));
        }
    }

    public void shutdown() {
        timer.cancel();
    }
}
