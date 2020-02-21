package icu.olorz.qq_integration.config;

import icu.olorz.qq_integration.Constants;

public class QQIntegrationConfig {

  // 后端配置
  public static String groupId;
  public static String groupName;
  public static Constants.BACKEND_TYPE backendType;
  // Websocket服务配置
  public static String host;
  public static int port;
  public static String token;
  // Mirai服务配置
  public static String qqId;
  public static String qqPw;

  // 信息格式配置
  public static String achievement;
  public static String command;
  public static String death;
  public static String fromQq;
  public static String fromQqPrivate;
  public static String formMc;
  public static String fromMcPrivate;
  public static String join;
  public static String leave;
  public static String ownerColor;
  public static String adminColor;
  public static String memberColor;
  // 信息过滤配置
  public static String filter;
  public static Constants.FILTER_MODE_CODE filterMode;
  public static Boolean stripFilter;
  // 信息模块配置
  public static Boolean moduleAchievements;
  public static Boolean moduleDeaths;
  public static Boolean moduleJoinLeave;
  public static Boolean moduleOnlineOffline;
}
