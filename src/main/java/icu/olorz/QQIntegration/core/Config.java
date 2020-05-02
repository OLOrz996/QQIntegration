package icu.olorz.QQIntegration.core;

import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigObject;
import com.typesafe.config.ConfigRenderOptions;
import icu.olorz.QQIntegration.Constants;
import icu.olorz.QQIntegration.api.IConfig;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Config implements IConfig {

  private File file;
  private com.typesafe.config.Config config;

  public Config(File file) throws IOException {
    this.file = file;
    load();
  }

  @Override
  public void load() throws IOException {
    config =
        ConfigFactory.load()
            .withFallback(ConfigFactory.parseFile(file))
            .withFallback(ConfigFactory.load("assets/QQIntegration/default.conf"));
    save();
  }

  @Override
  public void save() throws IOException {
    if (!file.exists()) {
      if (!file.getParentFile().exists()) {
        file.getParentFile().mkdirs();
      }
      file.createNewFile();
    }

    ConfigObject toRender = config.root().withOnlyKey("QQIntegration");
    String s =
        toRender.render(ConfigRenderOptions.defaults().setOriginComments(false).setJson(false));
    InputStream in = new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8));
    OutputStream out = new FileOutputStream(file);
    IOUtils.copy(in, out);
    in.close();
    out.close();
  }

  @Override
  public String groupId() {
    return config.getString("QQIntegration.backend.basic.groupId");
  }

  @Override
  public String groupName() {
    return config.getString("QQIntegration.backend.basic.groupName");
  }

  @Override
  public Constants.BACKEND_TYPE backendType() {
    return Constants.BACKEND_TYPE.valueOf(
        config.getString("QQIntegration.backend.basic.backendType").toUpperCase());
  }

  @Override
  public String host() {
    return config.getString("QQIntegration.backend.websocket.host");
  }

  @Override
  public int port() {
    return Integer.parseInt(config.getString("QQIntegration.backend.websocket.port"));
  }

  @Override
  public String token() {
    return config.getString("QQIntegration.backend.websocket.token");
  }

  @Override
  public String qqId() {
    return config.getString("QQIntegration.backend.mirai.qqId");
  }

  @Override
  public String qqPw() {
    return config.getString("QQIntegration.backend.mirai.qqPw");
  }

  @Override
  public String achievement() {
    return config.getString("QQIntegration.message.format.achievement");
  }

  @Override
  public String command() {
    return config.getString("QQIntegration.message.format.command");
  }

  @Override
  public String death() {
    return config.getString("QQIntegration.message.format.death");
  }

  @Override
  public String fromQq() {
    return config.getString("QQIntegration.message.format.fromQq");
  }

  @Override
  public String fromQqPrivate() {
    return config.getString("QQIntegration.message.format.fromQqPrivate");
  }

  @Override
  public String formMc() {
    return config.getString("QQIntegration.message.format.formMc");
  }

  @Override
  public String fromMcPrivate() {
    return config.getString("QQIntegration.message.format.fromMcPrivate");
  }

  @Override
  public String join() {
    return config.getString("QQIntegration.message.format.join");
  }

  @Override
  public String leave() {
    return config.getString("QQIntegration.message.format.leave");
  }

  @Override
  public String ownerColor() {
    return config.getString("QQIntegration.message.color.owner");
  }

  @Override
  public String adminColor() {
    return config.getString("QQIntegration.message.color.admin");
  }

  @Override
  public String memberColor() {
    return config.getString("QQIntegration.message.color.member");
  }

  @Override
  public String filter() {
    return config.getString("QQIntegration.message.filter.filter");
  }

  @Override
  public Constants.FILTER_MODE_CODE filterMode() {
    return Constants.FILTER_MODE_CODE.valueOf(
        config.getString("QQIntegration.message.filter.filterMode"));
  }

  @Override
  public Boolean stripFilter() {
    return Boolean.parseBoolean(config.getString("QQIntegration.message.filter.stripFilter"));
  }

  @Override
  public Boolean moduleAchievements() {
    return Boolean.parseBoolean(
        config.getString("QQIntegration.message.module.moduleAchievements"));
  }

  @Override
  public Boolean moduleDeaths() {
    return Boolean.parseBoolean(config.getString("QQIntegration.message.module.moduleDeaths"));
  }

  @Override
  public Boolean moduleJoinLeave() {
    return Boolean.parseBoolean(config.getString("QQIntegration.message.module.moduleJoinLeave"));
  }

  @Override
  public Boolean moduleOnlineOffline() {
    return Boolean.parseBoolean(
        config.getString("QQIntegration.message.module.moduleOnlineOffline"));
  }
}
