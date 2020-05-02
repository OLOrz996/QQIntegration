package icu.olorz.QQIntegration.core.qqBackend.mirai;

import net.mamoe.mirai.message.data.MessageChain;

public class MiraiUtils {

  public static void messageChainTransform(MessageChain messageChain) {
    messageChain.forEachContent(
        message -> {
          message.toString();
          return null;
        });
  }
}
