package icu.olorz.qq_integration.config;

import icu.olorz.qq_integration.Constants;
import net.minecraftforge.common.ForgeConfigSpec;

final class ServerConfig {
  // Category name
  final String CATEGORY_BACKEND = "backend";
  final String CATEGORY_BASIC = "basic";
  final String CATEGORY_WEBSOCKET = "websocket";
  final String CATEGORY_MIRAI = "mirai";

  final String CATEGORY_MSG = "message";
  final String CATEGORY_FORMAT = "format";
  final String CATEGORY_COLOR = "color";
  final String CATEGORY_FILTER = "filter";
  final String CATEGORY_MODULE = "module";
  // 后端配置
  final ForgeConfigSpec.ConfigValue<String> GROUP_ID;
  final ForgeConfigSpec.ConfigValue<String> GROUP_NAME;
  final ForgeConfigSpec.ConfigValue<Constants.BACKEND_TYPE> BACKEND_TYPE;
  // Websocket服务配置
  final ForgeConfigSpec.ConfigValue<String> HOST;
  final ForgeConfigSpec.IntValue PORT;
  final ForgeConfigSpec.ConfigValue<String> TOKEN;
  // Mirai服务配置
  final ForgeConfigSpec.ConfigValue<String> QQ_ID;
  final ForgeConfigSpec.ConfigValue<String> QQ_PW;

  // 信息格式配置
  final ForgeConfigSpec.ConfigValue<String> ACHIEVEMENT;
  final ForgeConfigSpec.ConfigValue<String> COMMAND;
  final ForgeConfigSpec.ConfigValue<String> DEATH;
  final ForgeConfigSpec.ConfigValue<String> FROM_QQ;
  final ForgeConfigSpec.ConfigValue<String> FROM_QQ_PRIVATE;
  final ForgeConfigSpec.ConfigValue<String> FORM_MC;
  final ForgeConfigSpec.ConfigValue<String> FROM_MC_PRIVATE;
  final ForgeConfigSpec.ConfigValue<String> JOIN;
  final ForgeConfigSpec.ConfigValue<String> LEAVE;
  final ForgeConfigSpec.ConfigValue<String> OWNER_COLOR;
  final ForgeConfigSpec.ConfigValue<String> ADMIN_COLOR;
  final ForgeConfigSpec.ConfigValue<String> MEMBER_COLOR;
  // 信息过滤配置
  final ForgeConfigSpec.ConfigValue<String> FILTER;
  final ForgeConfigSpec.EnumValue<Constants.FILTER_MODE_CODE> FILTER_MODE;
  final ForgeConfigSpec.BooleanValue STRIP_FILTER;
  // 信息模块配置
  final ForgeConfigSpec.BooleanValue MODULE_ACHIEVEMENTS;
  final ForgeConfigSpec.BooleanValue MODULE_DEATHS;
  final ForgeConfigSpec.BooleanValue MODULE_JOIN_LEAVE;
  final ForgeConfigSpec.BooleanValue MODULE_ONLINE_OFFLINE;

  ServerConfig(final ForgeConfigSpec.Builder builder) {
    /* Backend begin */
    builder.push(CATEGORY_BACKEND);
    // Basic
    builder.comment("基础配置").push(CATEGORY_BASIC);
    GROUP_ID = builder.comment("群号").define("groupId", "");
    GROUP_NAME = builder.comment("群名称[展示用]").define("groupName", "服务姬");
    BACKEND_TYPE =
        builder.comment("后端服务类型: 酷Q，Mirai").defineEnum("backendType", Constants.BACKEND_TYPE.COOLQ);
    builder.pop();
    // 酷Q
    builder.comment("酷Q Websocket 服务器配置").push(CATEGORY_WEBSOCKET);
    HOST = builder.comment("服务器地址").define("host", "localhost");
    PORT = builder.comment("监听端口").defineInRange("port", 2333, 1, 65535);
    TOKEN = builder.comment("Access_token[选填]").define("token", "");
    builder.pop();
    // Mirai
    builder.comment("Mirai机器人设置").push(CATEGORY_MIRAI);
    QQ_ID = builder.comment("机器人QQ号").define("qqId", "");
    QQ_PW = builder.comment("机器人QQ密码").define("qqPw", "");
    builder.pop();
    builder.pop();
    /* Backend end */
    /* Message begin */
    builder.push(CATEGORY_MSG);
    // Format
    builder.push(CATEGORY_FORMAT);
    ACHIEVEMENT =
        builder
            .comment("成就显示信息", "$1-玩家 $2-成就内容")
            .define("achievement", "MC » $1 has just earned the achievement $2");
    COMMAND =
        builder
            .comment("所有指令返回信息的格式", "用于区别不同机器人发出的指令[目前似乎没啥用].", "$1 是消息内容")
            .define("command", "$1");
    DEATH = builder.comment("玩家死亡信息.", "$1-玩家 $2-死亡信息").define("death", "MC » $2");
    FROM_QQ =
        builder
            .comment("Q群发往MC的信息", "$1-群名称 $2-用户名 $3-发送信息 $4-颜色代码")
            .define("fromQq", "#$1 » <$4$2§f> $3");
    FROM_QQ_PRIVATE =
        builder.comment("QQ发往MC的私聊信息", "$1-用户名 $2-发送信息").define("fromQqPrivate", "QQ » <$1> $2");
    FORM_MC = builder.comment("MC发往Q群的信息", "$1-用户名 and $2-发送信息").define("formMc", "MC » <$1> $2");
    FROM_MC_PRIVATE =
        builder.comment("MC发往QQ的私聊信息", "$1-用户名 $2-发送信息").define("fromMcPrivate", "MC » <$1> $2");
    JOIN = builder.comment("加入游戏提示", "$1-用户名").define("join", "MC » $1 joined the game");
    LEAVE = builder.comment("离开游戏提示", "$1-用户名").define("leave", "MC » $1 left the game");
    builder.pop();
    // Color
    builder.push(CATEGORY_COLOR);
    OWNER_COLOR = builder.define("owner", "§6");
    ADMIN_COLOR = builder.define("admin", "§a");
    MEMBER_COLOR = builder.define("member", "§f");
    builder.pop();
    // Filter
    builder.comment("过滤器").push(CATEGORY_FILTER);
    FILTER = builder.comment("放行关键词").define("filter", "");
    FILTER_MODE = builder.comment("匹配模式").defineEnum("filterMode", Constants.FILTER_MODE_CODE.NONE);
    STRIP_FILTER = builder.comment("是否去掉信息中的关键词").define("stripFilter", false);
    builder.pop();
    // Module
    builder.push(CATEGORY_MODULE);
    MODULE_ACHIEVEMENTS = builder.define("moduleAchievements", true);
    MODULE_DEATHS = builder.define("moduleDeaths", true);
    MODULE_JOIN_LEAVE = builder.define("moduleJoinLeave", true);
    MODULE_ONLINE_OFFLINE = builder.define("moduleOnlineOffline", true);
    builder.pop();
    /* Message end */
    builder.pop();
  }
}
