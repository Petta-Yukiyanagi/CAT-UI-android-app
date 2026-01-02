package com.petta.catui.comm;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SocketReceiver extends Thread {

    private final int port;
    private final UiEventController controller;
    private volatile boolean running = true;

    public SocketReceiver(int port, UiEventController controller) {
        this.port = port;
        this.controller = controller;
    }

    @Override
    public void run() {
        try (ServerSocket server = new ServerSocket(port)) {

            while (running) {
                Socket client = server.accept();

                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(
                                client.getInputStream(),
                                StandardCharsets.UTF_8))) {

                    String line;

                    // ★ ここが重要：null まで読む
                    while ((line = br.readLine()) != null) {

                        // ★ 到達確認（UIに必ず出る）
                        controller.onEvent(
                            JsonUiEventParser.parse(line)
                        );
                    }
                }

                client.close();
            }

        } catch (Exception e) {
            // adbなし環境なので「黙って死なない」ため
            e.printStackTrace();
        }
    }

    public void shutdown() {
        running = false;
        interrupt();
    }
}
