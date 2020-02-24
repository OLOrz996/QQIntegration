package icu.olorz.qq_integration.core.qqBackend;

import icu.olorz.qq_integration.MessageFormatter;
import icu.olorz.qq_integration.api.IQQBackend;
import icu.olorz.qq_integration.api.IQQProxy;
import icu.olorz.qq_integration.config.QQIntegrationConfig;
import net.mamoe.mirai.event.Listener;
import net.mamoe.mirai.japt.BlockingBot;
import net.mamoe.mirai.japt.BlockingContacts;
import net.mamoe.mirai.japt.BlockingGroup;
import net.mamoe.mirai.japt.Events;
import net.mamoe.mirai.message.GroupMessage;

public class MiraiBot implements IQQBackend {

  private BlockingBot bot =
      BlockingBot.newInstance(Long.parseLong(QQIntegrationConfig.qqId), QQIntegrationConfig.qqPw);
  private Listener<GroupMessage> grouplistener;
  private IQQProxy qqProxyInstance;
  private BlockingGroup group;
  private MessageFormatter messageFormatter = new MessageFormatter();

  public MiraiBot(IQQProxy qqProxyInstance) {
    this.qqProxyInstance = qqProxyInstance;
  }

  @Override
  public void startBackend() {
    bot.login();
    group = bot.getGroup(Long.parseLong(QQIntegrationConfig.groupId));
    grouplistener =
        Events.subscribeAlways(
            GroupMessage.class,
            (GroupMessage message) -> {
              final BlockingGroup group = BlockingContacts.createBlocking(message.getGroup());
              if (group.getId() == Long.parseLong(QQIntegrationConfig.groupId)) {
                String groupName = QQIntegrationConfig.groupName;
                String color =
                    messageFormatter.getColor(message.getPermission().toString().toLowerCase());
                String player = message.getSenderName();
                String msg = message.getMessage().toString();
                qqProxyInstance.receiveMessage(
                    messageFormatter.fromQQ(groupName, color, player, msg));
              }
            });
  }

  @Override
  public void stopBackend() {
    if (grouplistener != null) {
      grouplistener.complete();
    }
  }

  @Override
  public void msgToQQ(String msg) {
    group.sendMessage(msg);
  }
}
