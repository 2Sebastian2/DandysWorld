package net.seb234.dandysworld;

import net.seb234.dandysworld.dataholder.PlayerData;

import java.util.HashMap;

public class GameState {

    public HashMap<Integer, PlayerToon> playerList = new HashMap<>();

    public void refreshPlayer(PlayerData data) {
        if (playerList.containsKey(data.playerId)) {
            playerList.get(data.playerId).updateData(data);
        }
    }
}
