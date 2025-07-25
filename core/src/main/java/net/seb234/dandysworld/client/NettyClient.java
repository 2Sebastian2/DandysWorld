package net.seb234.dandysworld.client;

import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.seb234.dandysworld.MyGame;
import net.seb234.dandysworld.dataholder.ClientIDRequest;
import net.seb234.dandysworld.dataholder.GameData;
import net.seb234.dandysworld.dataholder.MessageWrapper;
import net.seb234.dandysworld.screens.GameScreen;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class NettyClient {

    private final int port;
    private final MyGame game;

    public NettyClient(int port, MyGame game) {
        this.port = port;
        this.game = game;
        GameScreen.console.log("Client", "Initialised Client Class [NettyClient]");
    }

    private static final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    private static final Gson gson = new Gson();

    private ScheduledFuture<?> sendingTask;

    private Socket socket;

    private BufferedReader reader;

    private BufferedWriter writer;

    public void start() {
        GameScreen.console.log("Client", "Starting Client...");
        game.stopWatch.start();

        String vmName = System.getProperty("java.vm.name").toLowerCase();

        if (vmName.contains("dalvik") || vmName.contains("art")) {
            GameScreen.console.log("Client", "Client logged into an Android Device");
        } else {
            GameScreen.console.log("Client", "Client logged into a Desktop Device");
        }

        try {

            socket = new Socket("192.168.1.3", port);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            sendingTask = executor.scheduleAtFixedRate(() -> {
                try {
                    if (Integer.parseInt(game.player.id) >= 0) {
                        String msg = gson.toJson(new MessageWrapper("playerdata", game.player.getPlayerData())) + "\n";
                        try {
                            writer.write(msg);
                            writer.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                            sendingTask.cancel(false);
                        }
                    }
                } catch (Exception e) {
                    GameScreen.console.error("ServerThread", "An error occurred, closing Thread...");
                    sendingTask.cancel(false);
                    executor.shutdown();
                }
            }, 0, 50, TimeUnit.MILLISECONDS);

            GameScreen.console.log("Client", "Client connected successfully.");
            GameScreen.console.log("Client", "Client took " + game.stopWatch.getElapsedTime() + " to initialize.");

            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    Gdx.app.log("Client", "Logging out...");
                    break;
                }

                JsonObject json = gson.fromJson(line, JsonObject.class);
                if (json != null && !json.isEmpty()) {
                    String type = json.get("type").getAsString();

                    switch (type) {
                        case "clientidrequest":
                            ClientIDRequest request = gson.fromJson(json, ClientIDRequest.class);
                            game.player.setId(request.id);
                            break;

                        case "gamedata":
                            GameData data = gson.fromJson(json, GameData.class);
                            game.state.refresh(data);
                            break;
                    }
                } else {
                    GameScreen.console.error("Server", "Inadequate json message: " + json);
                        break;
                }
            }
        } catch (IOException e) {
            GameScreen.console.error("Client", "Connection error.");
        } finally {
            try { if (reader != null) reader.close(); } catch (IOException ignored) {}
            try { if (writer != null) writer.close(); } catch (IOException ignored) {}
            try { if (socket != null) socket.close(); } catch (IOException ignored) {}
            sendingTask.cancel(false);
            executor.shutdown();
            GameScreen.console.error("Client", "Client disconnected.");
        }
    }
}

