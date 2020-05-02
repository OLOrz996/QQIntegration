package icu.olorz.QQIntegration;

import icu.olorz.QQIntegration.api.IConfig;
import icu.olorz.QQIntegration.api.IQQProxy;
import icu.olorz.QQIntegration.core.Config;
import icu.olorz.QQIntegration.core.MinecraftAdapter;
import icu.olorz.QQIntegration.core.QQProxy;
import lombok.Getter;
import lombok.Setter;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

@Mod(modid = QQIntegration.MODID, acceptableRemoteVersions = "*")
public final class QQIntegration {

  public static final String MODID = "qq_integration";

  private static final Logger LOGGER = LogManager.getLogger(QQIntegration.MODID);
  @Getter @Setter private static IQQProxy qqProxy;
  public static IConfig config;

  @Mod.EventHandler
  public void preInit(FMLPreInitializationEvent event) throws IOException {
    LOGGER.debug("Hello from QQ Integration!");
    File configFile = new File(event.getModConfigurationDirectory(), "QQIntegration/");

    if (!configFile.exists()) {
      configFile.mkdirs();
    }

    config = new Config(new File(configFile, "QQIntegration.conf"));
    qqProxy = new QQProxy(new MinecraftAdapter(), config);
    MinecraftForge.EVENT_BUS.register(new ModEventHandler(config));
  }

  @Mod.EventHandler
  private void serverSetup(final FMLServerStartedEvent event) {
    LOGGER.debug("QQ Integration setup!");
    QQIntegration.getQqProxy().startProxy();
  }

  @Mod.EventHandler
  private void serverStopping(FMLServerStoppedEvent event) {
    if (QQIntegration.getQqProxy() != null) {
      QQIntegration.getQqProxy().stopProxy();
    }
  }
}
