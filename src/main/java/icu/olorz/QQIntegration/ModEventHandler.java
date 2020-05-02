package icu.olorz.QQIntegration;

import icu.olorz.QQIntegration.api.IConfig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModEventHandler {
  private IConfig config;
  private final MessageFormatter msgForIns;
  private static final Logger LOGGER =
      LogManager.getLogger(QQIntegration.MODID + " Mod Event Subscriber");

  public ModEventHandler(IConfig config){
    this.config = config;
    this.msgForIns = new MessageFormatter(config);
  }

  @SubscribeEvent
  public void onServerChat(ServerChatEvent event) {
    String message = msgForIns.filterMCMessage(event.getMessage());
    if (message != null) {
      QQIntegration.getQqProxy()
          .sendMessage(msgForIns.fromMC(event.getPlayer().getName(), message));
    }
  }

  @SubscribeEvent
  public void onLivingDeath(LivingDeathEvent event) {
    if (config.moduleDeaths()
        && event.getEntityLiving() instanceof EntityPlayer && !(event.getEntityLiving() instanceof FakePlayer) && !event.getEntity().world.isRemote) {
      EntityPlayer player = (EntityPlayer)event.getEntityLiving();
      QQIntegration.getQqProxy()
          .sendMessage(
              msgForIns.death(
                  player.getName(),
                  player.getCombatTracker().getDeathMessage().getUnformattedComponentText()));
    }
  }

  @SubscribeEvent
  public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
    if (config.moduleJoinLeave()) {
      QQIntegration.getQqProxy()
          .sendMessage(msgForIns.join(event.player.getName()));
    }
  }

  @SubscribeEvent
  public void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
    if (config.moduleJoinLeave()) {
      QQIntegration.getQqProxy()
          .sendMessage(msgForIns.leave(event.player.getName()));
    }
  }
}
