package net.seb234.dandysworld.dataholder;

import net.seb234.dandysworld.MyGame;

import java.util.HashMap;

public class GameData {
    public HashMap<String, PlayerData> playerList = new HashMap<>();

    public GameData(MyGame game) {
        for (String id : game.state.playerList.keySet()) {
            playerList.put(id, game.state.playerList.get(id).getPlayerData());
        }
    }
}
