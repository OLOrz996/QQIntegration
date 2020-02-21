package icu.olorz.qq_integration.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import icu.olorz.qq_integration.QQIntegration;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;

public class ConfigHelper {

  private static final Logger LOGGER = LogManager.getLogger(QQIntegration.MODID + " Config Helper");

  public static void bakeClient(final ModConfig config) {}

  public static void bakeServer(final ModConfig config) {
    // 后端配置
    QQIntegrationConfig.groupId = ConfigHolder.SERVER.GROUP_ID.get();
    QQIntegrationConfig.groupName = ConfigHolder.SERVER.GROUP_NAME.get();
    QQIntegrationConfig.backendType = ConfigHolder.SERVER.BACKEND_TYPE.get();
    // Websocket服务配置
    QQIntegrationConfig.host = ConfigHolder.SERVER.HOST.get();
    QQIntegrationConfig.port = ConfigHolder.SERVER.PORT.get();
    QQIntegrationConfig.token = ConfigHolder.SERVER.TOKEN.get();
    // Mirai服务配置
    QQIntegrationConfig.qqId = ConfigHolder.SERVER.QQ_ID.get();
    QQIntegrationConfig.qqPw = ConfigHolder.SERVER.QQ_PW.get();

    // 信息格式配置
    QQIntegrationConfig.achievement = ConfigHolder.SERVER.ACHIEVEMENT.get();
    QQIntegrationConfig.command = ConfigHolder.SERVER.COMMAND.get();
    QQIntegrationConfig.death = ConfigHolder.SERVER.DEATH.get();
    QQIntegrationConfig.fromQq = ConfigHolder.SERVER.FROM_QQ.get();
    QQIntegrationConfig.fromQqPrivate = ConfigHolder.SERVER.FROM_QQ_PRIVATE.get();
    QQIntegrationConfig.formMc = ConfigHolder.SERVER.FORM_MC.get();
    QQIntegrationConfig.fromMcPrivate = ConfigHolder.SERVER.FROM_MC_PRIVATE.get();
    QQIntegrationConfig.join = ConfigHolder.SERVER.JOIN.get();
    QQIntegrationConfig.leave = ConfigHolder.SERVER.LEAVE.get();
    QQIntegrationConfig.ownerColor = ConfigHolder.SERVER.OWNER_COLOR.get();
    QQIntegrationConfig.adminColor = ConfigHolder.SERVER.ADMIN_COLOR.get();
    QQIntegrationConfig.memberColor = ConfigHolder.SERVER.MEMBER_COLOR.get();
    // 信息过滤配置
    QQIntegrationConfig.filter = ConfigHolder.SERVER.FILTER.get();
    QQIntegrationConfig.filterMode = ConfigHolder.SERVER.FILTER_MODE.get();
    QQIntegrationConfig.stripFilter = ConfigHolder.SERVER.STRIP_FILTER.get();
    // 信息模块配置
    QQIntegrationConfig.moduleAchievements = ConfigHolder.SERVER.MODULE_ACHIEVEMENTS.get();
    QQIntegrationConfig.moduleDeaths = ConfigHolder.SERVER.MODULE_DEATHS.get();
    QQIntegrationConfig.moduleJoinLeave = ConfigHolder.SERVER.MODULE_JOIN_LEAVE.get();
    QQIntegrationConfig.moduleOnlineOffline = ConfigHolder.SERVER.MODULE_ONLINE_OFFLINE.get();
  }

  public static Boolean loadConfig(ForgeConfigSpec spec, Path path) {
    final CommentedFileConfig configData =
        CommentedFileConfig.builder(path)
            .sync()
            .autosave()
            .writingMode(WritingMode.REPLACE)
            .build();
    configData.load();
    try {
      spec.setConfig(configData);
    } catch (Exception e) {
      LOGGER.error("Config File error!");
      return false;
    }
    return true;
  }
}
