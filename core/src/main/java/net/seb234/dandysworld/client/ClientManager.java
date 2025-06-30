package net.seb234.dandysworld.client;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import net.seb234.dandysworld.GameScreen;
import net.seb234.dandysworld.PlayerToon;
import net.seb234.dandysworld.dataholder.GameData;
import net.seb234.dandysworld.dataholder.NetUtils;
import net.seb234.dandysworld.dataholder.PlayerData;

import java.io.IOException;

public class ClientManager {
    private static Client client;

    public static void startClient(String ip) {
        client = new Client();
        NetUtils.registerClasses(client.getKryo());
        client.start();

        try {
            client.connect(5000, ip, 54555, 54777);
        } catch (IOException e) {
            e.printStackTrace();
        }

        client.addListener(new Listener() {
            public void received(Connection connection, Object object) {
                if (object instanceof PlayerData) {
                    PlayerToon toon = new PlayerToon(((PlayerData) object).playerId);
                    toon.updateData((PlayerData) object);
                    GameScreen.player = toon;
                } else if (object instanceof GameData) {
                    for (int id : ((GameData) object).playerList.keySet()) {
                        GameScreen.state.playerList.get(id).updateData(((GameData) object).playerList.get(id));
                    }
                }
            }
        });
    }

    public static void sendInput() {
        client.sendTCP(GameScreen.player.getPlayerData());
    }
}

