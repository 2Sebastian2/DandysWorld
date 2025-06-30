package net.seb234.dandysworld.dataholder;

import com.esotericsoftware.kryo.Kryo;

import java.util.HashMap;
public class NetUtils {
    public static void registerClasses(Kryo kryo) {
        kryo.register(GameData.class);
        kryo.register(HashMap.class);
        kryo.register(PlayerData.class);
        kryo.register(Integer.class);
        kryo.register(String.class);
    }
}

