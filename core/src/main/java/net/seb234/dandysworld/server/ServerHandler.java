package net.seb234.dandysworld.server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.seb234.dandysworld.MyGame;
import net.seb234.dandysworld.PlayerToon;
import net.seb234.dandysworld.dataholder.ClientIDRequest;
import net.seb234.dandysworld.dataholder.MessageWrapper;
import net.seb234.dandysworld.dataholder.PlayerData;
import net.seb234.dandysworld.screens.GameScreen;

public class ServerHandler extends SimpleChannelInboundHandler<String> {

    private final Gson gson = new Gson();
    private final MyGame game;

    public ServerHandler(MyGame game) {
        super();
        this.game = game;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        try {
            JsonObject json = gson.fromJson(msg, JsonObject.class);
            if (json == null || json.isEmpty()) {
                return;
            }
            String type = json.get("type").getAsString();

            switch (type) {
                case "playerdata":
                    PlayerData data = gson.fromJson(json, PlayerData.class);
                    PlayerToon toon = game.state.playerList.get(data.playerId);
                    if (toon != null) {
                        toon.updateData(data);
                    } else {
                        GameScreen.console.error("ServerHandler", "¡ID not registered: " + data.playerId + "!");
                    }
                    break;
            }
        } catch (JsonSyntaxException e) {
            GameScreen.console.error("ServerHandler", "Invalid JSON message received: " + msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        GameScreen.console.log("ServerHandler", "Client Online: " + ctx.channel().remoteAddress());

        String newId = getNewId();
        NettyServer.channels.put(newId, ctx.channel());

        game.state.playerList.put(newId, new PlayerToon());
        game.state.playerList.get(newId).setId(newId);

        String msg = gson.toJson(
            new MessageWrapper("clientidrequest",
                new ClientIDRequest(newId))) + "\n";
        ctx.channel().writeAndFlush(msg);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        String id = getId(ctx.channel());
        if (id != null) {
            NettyServer.channels.remove(id);
            game.state.playerList.remove(id);
            GameScreen.console.log("ServerHandler", "Client offline: " + id);
        }
    }

    public String getNewId() {
        int id = 0;
        while (NettyServer.channels.containsKey(String.valueOf(id))) {
            id++;
        }
        return String.valueOf(id);
    }

    public String getId(Channel ch) {
        for (String id : NettyServer.channels.keySet()) {
            if (NettyServer.channels.get(id) == ch) {
                return id;
            }
        }
        return null;
    }
}
