package icu.olorz.QQIntegration;

import icu.olorz.QQIntegration.api.IConfig;

public class MessageFormatter {

  private final IConfig config;
  public MessageFormatter(IConfig config) {
    this.config = config;
  }

  public String fromMC(String sender, String message) {
    return config.formMc().replace("$1", sender).replace("$2", message);
  }

  public String fromQQ(String channel, String senderColor, String sender, String message) {
    return config.fromQq()
        .replace("$1", channel)
        .replace("$2", sender)
        .replace("$3", message)
        .replace("$4", senderColor);
  }

  public String fromMCPrivate(String sender, String message) {
    return config.fromMcPrivate().replace("$1", sender).replace("$2", message);
  }

  public String fromQQPrivate(String sender, String message) {
    return config.fromQqPrivate().replace("$1", sender).replace("$2", message);
  }

  public String death(String player, String message) {
    return config.death().replace("$1", player).replace("$2", message);
  }

  public String achievement(String player, String achievement) {
    return config.achievement().replace("$1", player).replace("$2", achievement);
  }

  public String join(String player) {
    return config.join().replace("$1", player);
  }

  public String leave(String player) {
    return config.leave().replace("$1", player);
  }

  public String command(String message) {
    return config.command().replace("$1", message);
  }

  public String filterMCMessage(String message) {
    switch (config.filterMode()) {
      case PREFIX:
        if (!message.startsWith(config.filter())) {
          return null;
        } else {
          if (config.stripFilter()) {
            return message.substring(config.filter().length());
          } else {
            return message;
          }
        }
      case SUFFIX:
        if (!message.endsWith(config.filter())) {
          return null;
        } else {
          if (config.stripFilter()) {
            return message.substring(0, message.length() - config.filter().length());
          } else {
            return message;
          }
        }
      default:
      case NONE:
        return message;
    }
  }

  public String getColor(String role) {
    switch (role) {
      case Constants.OWNER:
        return config.ownerColor();
      case Constants.ADMIN:
        return config.adminColor();
      case Constants.MEMBER:
        return config.memberColor();
      default:
        return "§f";
    }
  }
}
