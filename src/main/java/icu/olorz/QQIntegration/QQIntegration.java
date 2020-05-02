package icu.olorz.QQIntegration;

import icu.olorz.QQIntegration.api.IQQProxy;
import icu.olorz.QQIntegration.config.ConfigHelper;
import icu.olorz.QQIntegration.config.ConfigHolder;
import icu.olorz.QQIntegration.core.MinecraftAdapter;
import icu.olorz.QQIntegration.core.QQProxy;
import lombok.Getter;
import lombok.Setter;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(QQIntegration.MODID)
public final class QQIntegration {

  public static final String MODID = "qq_integration";

  private static final Logger LOGGER = LogManager.getLogger(QQIntegration.MODID);
  @Getter @Setter private static IQQProxy qqProxy;

  public QQIntegration() {
    LOGGER.debug("Hello from QQ Integration!");

    final ModLoadingContext modLoadingContext = ModLoadingContext.get();
    // Config.loadConfig(
    //     Config.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve("qq_integration-common.toml"));
    modLoadingContext.registerConfig(ModConfig.Type.SERVER, ConfigHolder.SERVER_SPEC);
    ConfigHelper.loadConfig(
        ConfigHolder.SERVER_SPEC, FMLPaths.CONFIGDIR.get().resolve("qq_integration-server.toml"));
    ConfigHelper.bakeServer();
    qqProxy = new QQProxy(new MinecraftAdapter());
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::serverSetup);
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::serverStopping);
    MinecraftForge.EVENT_BUS.register(new ModEventHandler());
  }

  private void serverSetup(final FMLDedicatedServerSetupEvent event) {
    LOGGER.debug("QQ Integration setup!");
    QQIntegration.getQqProxy().startProxy();
  }

  private void serverStopping(FMLServerStoppingEvent event) {
    if (QQIntegration.getQqProxy() != null) {
      QQIntegration.getQqProxy().stopProxy();
    }
  }
}
