package icu.olorz.qq_integration;

import icu.olorz.qq_integration.config.ConfigHelper;
import icu.olorz.qq_integration.config.ConfigHolder;
import icu.olorz.qq_integration.config.QQIntegrationConfig;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// @Mod.EventBusSubscriber(modid = QQIntegration.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventHandler {

  private static MessageFormatter msgForIns = new MessageFormatter();

  private static final Logger LOGGER =
      LogManager.getLogger(QQIntegration.MODID + " Mod Event Subscriber");

  /*当配置变更时调用此方法*/
  @SubscribeEvent
  public void onModConfigEvent(final ModConfig.ModConfigEvent event) {
    final ModConfig config = event.getConfig();
    // Rebake the configs when they change
    // if (config.getSpec() == ConfigHolder.CLIENT_SPEC) {
    //   ConfigHelper.bakeClient(config);
    //   LOGGER.debug("Baked client config");
    // } else
    if (config.getSpec() == ConfigHolder.SERVER_SPEC) {
      // ConfigHelper.bakeServer(config);
      ConfigHelper.bakeServer();
      LOGGER.debug("Baked server config");
    }
  }

  @SubscribeEvent
  public void onServerChat(ServerChatEvent event) {
    String message = msgForIns.filterMCMessage(event.getMessage());
    if (message != null) {
      QQIntegration.getQqProxy()
          .sendMessage(msgForIns.fromMC(event.getPlayer().getName().getString(), message));
    }
  }

  @SubscribeEvent
  public void onLivingDeath(LivingDeathEvent event) {
    if (QQIntegrationConfig.moduleDeaths
        && event.getEntityLiving() instanceof PlayerEntity
        && !(event.getEntityLiving() instanceof FakePlayer)
        && !event.getEntity().world.isRemote) {
      PlayerEntity player = (PlayerEntity) event.getEntityLiving();
      QQIntegration.getQqProxy()
          .sendMessage(
              msgForIns.death(
                  player.getName().getString(),
                  player.getCombatTracker().getDeathMessage().getUnformattedComponentText()));
    }
  }

  @SubscribeEvent
  public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
    if (QQIntegrationConfig.moduleJoinLeave) {
      QQIntegration.getQqProxy()
          .sendMessage(msgForIns.join(event.getPlayer().getName().getString()));
    }
  }

  @SubscribeEvent
  public void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
    if (QQIntegrationConfig.moduleJoinLeave) {
      QQIntegration.getQqProxy()
          .sendMessage(msgForIns.leave(event.getPlayer().getName().getString()));
    }
  }
}
