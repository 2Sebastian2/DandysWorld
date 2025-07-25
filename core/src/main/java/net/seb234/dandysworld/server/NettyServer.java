package net.seb234.dandysworld.server;

import com.google.gson.Gson;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import net.seb234.dandysworld.MyGame;
import net.seb234.dandysworld.dataholder.GameData;
import net.seb234.dandysworld.dataholder.MessageWrapper;
import net.seb234.dandysworld.screens.GameScreen;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class NettyServer {

    private final int port;

    private final MyGame game;

    public NettyServer(int port, MyGame game) {
        this.port = port;
        this.game = game;
    }

    private static final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private static final Gson gson = new Gson();

    public static HashMap<String, Channel> channels = new HashMap<>();

    private ScheduledFuture<?> sendingTask;

    public void start() throws Exception {
        game.stopWatch.start();
        EventLoopGroup boss = new NioEventLoopGroup(); // Connections
        EventLoopGroup worker = new NioEventLoopGroup(); // Server Traffic

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel ch) {
                        ch.pipeline().addLast(
                            new LineBasedFrameDecoder(1024),
                            new StringDecoder(StandardCharsets.UTF_8),
                            new StringEncoder(StandardCharsets.UTF_8),
                            new ServerHandler(game));
                    }
                });

            ChannelFuture future = bootstrap.bind("0.0.0.0", port).sync();

            game.player.setId("0");
            game.state.playerList.put("0", game.player);

            sendingTask = executor.scheduleAtFixedRate(() -> {
                try {
                    if (future.channel().isActive()) {
                        String json = gson.toJson(new MessageWrapper("gamedata", new GameData(game))) + "\n";
                        for (Channel channel : channels.values()) {
                            if (channel != null && channel.isActive()) {
                                channel.writeAndFlush(json);
                            }
                        }
                    } else {
                        sendingTask.cancel(false);
                        executor.shutdown();
                    }
                } catch (Exception e) {
                    GameScreen.console.error("Server", "An error occurred, closing thread...");
                    sendingTask.cancel(false);
                    executor.shutdown();
                }
            }, 0, 50, TimeUnit.MILLISECONDS);

            GameScreen.console.log("Server", "Server opened on port:" + port + ".");
            GameScreen.console.log("Server", "Server took " + game.stopWatch.getElapsedTime() + " to initialize.");
            future.channel().closeFuture().sync();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
            sendingTask.cancel(false);
            executor.shutdown();
            GameScreen.console.log("Server", "Server closed.");
        }
    }
}
