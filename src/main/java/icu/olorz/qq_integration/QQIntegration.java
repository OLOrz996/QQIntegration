package icu.olorz.qq_integration;

import icu.olorz.qq_integration.api.IQQProxy;
import icu.olorz.qq_integration.config.ConfigHelper;
import icu.olorz.qq_integration.config.ConfigHolder;
import icu.olorz.qq_integration.core.MinecraftAdapter;
import icu.olorz.qq_integration.core.QQProxy;
import lombok.Getter;
import lombok.Setter;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(QQIntegration.MODID)
public final class QQIntegration {

  public static final String MODID = "qq_integration";

  private static final Logger LOGGER = LogManager.getLogger(QQIntegration.MODID);
  @Getter @Setter private static IQQProxy qqAdopter = new QQProxy(new MinecraftAdapter());

  public QQIntegration() {
    LOGGER.debug("Hello from QQ Integration!");

    final ModLoadingContext modLoadingContext = ModLoadingContext.get();
    // Config.loadConfig(
    //     Config.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve("qq_integration-common.toml"));
    modLoadingContext.registerConfig(ModConfig.Type.SERVER, ConfigHolder.SERVER_SPEC);
    ConfigHelper.loadConfig(
        ConfigHolder.SERVER_SPEC, FMLPaths.CONFIGDIR.get().resolve("qq_integration-server.toml"));
    MinecraftForge.EVENT_BUS.register(new ModEventHandler());
  }
}
