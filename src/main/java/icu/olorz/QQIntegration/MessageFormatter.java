package icu.olorz.QQIntegration;

import icu.olorz.QQIntegration.config.QQIntegrationConfig;

public class MessageFormatter {

  public String fromMC(String sender, String message) {
    return QQIntegrationConfig.formMc.replace("$1", sender).replace("$2", message);
  }

  public String fromQQ(String channel, String senderColor, String sender, String message) {
    return QQIntegrationConfig.fromQq
        .replace("$1", channel)
        .replace("$2", sender)
        .replace("$3", message)
        .replace("$4", senderColor);
  }

  public String fromMCPrivate(String sender, String message) {
    return QQIntegrationConfig.fromMcPrivate.replace("$1", sender).replace("$2", message);
  }

  public String fromQQPrivate(String sender, String message) {
    return QQIntegrationConfig.fromQqPrivate.replace("$1", sender).replace("$2", message);
  }

  public String death(String player, String message) {
    return QQIntegrationConfig.death.replace("$1", player).replace("$2", message);
  }

  public String achievement(String player, String achievement) {
    return QQIntegrationConfig.achievement.replace("$1", player).replace("$2", achievement);
  }

  public String join(String player) {
    return QQIntegrationConfig.join.replace("$1", player);
  }

  public String leave(String player) {
    return QQIntegrationConfig.leave.replace("$1", player);
  }

  public String command(String message) {
    return QQIntegrationConfig.command.replace("$1", message);
  }

  public String filterMCMessage(String message) {
    switch (QQIntegrationConfig.filterMode) {
      case PREFIX:
        if (!message.startsWith(QQIntegrationConfig.filter)) {
          return null;
        } else {
          if (QQIntegrationConfig.stripFilter) {
            return message.substring(QQIntegrationConfig.filter.length());
          } else {
            return message;
          }
        }
      case SUFFIX:
        if (!message.endsWith(QQIntegrationConfig.filter)) {
          return null;
        } else {
          if (QQIntegrationConfig.stripFilter) {
            return message.substring(0, message.length() - QQIntegrationConfig.filter.length());
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
        return QQIntegrationConfig.ownerColor;
      case Constants.ADMIN:
        return QQIntegrationConfig.adminColor;
      case Constants.MEMBER:
        return QQIntegrationConfig.memberColor;
      default:
        return "§f";
    }
  }
}
