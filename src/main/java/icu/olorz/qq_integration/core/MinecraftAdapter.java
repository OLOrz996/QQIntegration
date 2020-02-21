package icu.olorz.qq_integration.core;

import icu.olorz.qq_integration.api.IMinecraftAdapter;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.util.Set;

public class MinecraftAdapter implements IMinecraftAdapter {

    @Override
    public void sendMessage(String message) {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (server != null) {
            server.getPlayerList().sendMessage(ForgeHooks.newChatWithLinks(message));
        }
    }

    @Override
    public void sendMessageToPlayer(String message, String player) {

    }

    @Override
    public int[] getAllDimensions() {
        return new int[0];
    }

    @Override
    public double getTickTime(int dimension) {
        return 0;
    }

    @Override
    public Set<String> getOnlinePlayers() {
        return null;
    }

    @Override
    public void executeCommand(String command) {

    }

    @Override
    public long getWorldTime(int dimension) {
        return 0;
    }
}
