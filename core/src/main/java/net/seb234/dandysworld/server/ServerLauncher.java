package net.seb234.dandysworld.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import net.seb234.dandysworld.GameScreen;
import net.seb234.dandysworld.PlayerToon;
import net.seb234.dandysworld.dataholder.GameData;
import net.seb234.dandysworld.dataholder.NetUtils;
import net.seb234.dandysworld.dataholder.PlayerData;

import java.io.IOException;
import java.net.ConnectException;

public class ServerLauncher {

    private static Server server;

    public static void startServer() {
        server = new Server();
        NetUtils.registerClasses(server.getKryo());

        try {
            server.bind(54555, 54777);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        server.addListener(new Listener() {
            @Override
            public void connected(Connection connection) {
                System.out.println("Nuevo cliente conectado: " + connection.getID());

                GameScreen.state.playerList.put(connection.getID(), new PlayerToon(connection.getID()));

                server.sendToTCP(connection.getID(), GameScreen.state.playerList.get(connection.getID()).getPlayerData());

            }

            @Override
            public void disconnected(Connection connection) {
                System.out.println("Cliente desconectado: " + connection.getID());

                GameScreen.state.playerList.remove(connection.getID());
            }

            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof PlayerData) {
                    if (GameScreen.state.playerList.containsKey(connection.getID())) {
                        GameScreen.state.playerList.get(connection.getID()).updateData((PlayerData) object);
                    } else {
                        try {
                            throw new ConnectException("Could not identify client " + connection.getID());
                        } catch (ConnectException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });
    }

    public static void sendGameUpdate() {
        GameData data = new GameData();
        for (int id : GameScreen.state.playerList.keySet()) {
            data.playerList.put(id, GameScreen.state.playerList.get(id).getPlayerData());
        }
        server.sendToAllTCP(data);
    }
}
