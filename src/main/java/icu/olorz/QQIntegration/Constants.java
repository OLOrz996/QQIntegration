package icu.olorz.QQIntegration;

/** @author OLOrz */
public class Constants {
  public static final String OWNER = "owner";
  public static final String ADMIN = "admin";
  public static final String MEMBER = "member";

  // Mod 指令
  public static final String CMD_MOD_PREFIX = "!bot";
  public static final String CMD_MOD_ONLINE_PLAYERS = "online";
  public static final String CMD_MOD_TPS = "tps";
  // Server 指令
  public static final String CMD_SERVER_PREFIX = "!cmd";

  public enum FILTER_MODE_CODE {
    /* 不过滤 */
    NONE,
    /* 前缀过滤 */
    PREFIX,
    SUFFIX
  }

  public enum BACKEND_TYPE {
    /* 酷q */
    COOLQ,
    /* Mirai */
    MIRAI
  }

  public enum MESSAGE_TYPE {
    /* 模组命令 */
    MOD_CMD,
    /* 模组命令 */
    SERVER_CMD,
    /* 错误命令 */
    ERROR_CMD,
    /* 聊天信息 */
    MSG
  }
}
