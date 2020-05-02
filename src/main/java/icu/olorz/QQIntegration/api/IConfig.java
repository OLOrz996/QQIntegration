package icu.olorz.QQIntegration.api;

import icu.olorz.QQIntegration.Constants;

import java.io.IOException;

public interface IConfig {

  void load() throws IOException;

  void save() throws IOException;

  // 后端配置
  String groupId();

  String groupName();

  Constants.BACKEND_TYPE backendType();
  // Websocket服务配置
  String host();

  int port();

  String token();
  // Mirai服务配置
  String qqId();

  String qqPw();

  // 信息格式配置
  String achievement();

  String command();

  String death();

  String fromQq();

  String fromQqPrivate();

  String formMc();

  String fromMcPrivate();

  String join();

  String leave();

  String ownerColor();

  String adminColor();

  String memberColor();
  // 信息过滤配置
  String filter();

  Constants.FILTER_MODE_CODE filterMode();

  Boolean stripFilter();
  // 信息模块配置
  Boolean moduleAchievements();

  Boolean moduleDeaths();

  Boolean moduleJoinLeave();

  Boolean moduleOnlineOffline();
}
