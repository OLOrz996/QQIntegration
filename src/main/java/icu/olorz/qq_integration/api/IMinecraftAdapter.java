package icu.olorz.qq_integration.api;

import java.util.Set;

public interface IMinecraftAdapter {

    void sendMessage(String message);

    void sendMessageToPlayer(String message, String player);

    int[] getAllDimensions();

    double getTickTime(int dimension);

    Set<String> getOnlinePlayers();

    void executeCommand(String command);

    long getWorldTime(int dimension);
}
