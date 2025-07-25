package net.seb234.dandysworld;

import net.seb234.dandysworld.dataholder.GameData;

import java.util.HashMap;

public class GameState {

    public HashMap<String, PlayerToon> playerList = new HashMap<>();


    public void refresh (GameData data) {
        for (String id : data.playerList.keySet()) {
            if (playerList.containsKey(id)) {
                playerList.get(id).updateData(data.playerList.get(id));
            } else {
                playerList.put(id, new PlayerToon());
                playerList.get(id).updateData(data.playerList.get(id));
            }
        }


    }
}
