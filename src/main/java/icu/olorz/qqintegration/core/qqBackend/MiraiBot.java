package icu.olorz.qqintegration.core.qqBackend;

import icu.olorz.qqintegration.MessageFormatter;
import icu.olorz.qqintegration.api.IQQBackend;
import icu.olorz.qqintegration.api.IQQProxy;
import icu.olorz.qqintegration.config.QQIntegrationConfig;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactoryJvm;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.Listener;
import net.mamoe.mirai.japt.Events;
import net.mamoe.mirai.message.GroupMessage;
import net.mamoe.mirai.utils.BotConfiguration;
import net.mamoe.mirai.utils.SystemDeviceInfoKt;

import java.io.File;

public class MiraiBot implements IQQBackend {

  // private final BlockingBot bot =
  //     BlockingBot.newInstance(Long.parseLong(QQIntegrationConfig.qqId),
  // QQIntegrationConfig.qqPw);
  // 使用自定义的配置
  private final Bot bot =
      BotFactoryJvm.newBot(
          Long.parseLong(QQIntegrationConfig.qqId),
          QQIntegrationConfig.qqPw,
          new BotConfiguration() {
            {
              setDeviceInfo(
                  context ->
                      SystemDeviceInfoKt.loadAsDeviceInfo(new File("deviceInfo.json"), context));
              // setLoginSolver();
              // setBotLoggerSupplier();
            }
          });
  private Listener<GroupMessage> grouplistener;
  private IQQProxy qqProxyInstance;
  private Group group;
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
            (GroupMessage event) -> {
              Group senderGroup = event.getGroup();
              if (senderGroup.getId() == Long.parseLong(QQIntegrationConfig.groupId)) {
                String groupName = QQIntegrationConfig.groupName;
                String color =
                    messageFormatter.getColor(event.getPermission().toString().toLowerCase());
                String player = event.getSenderName();
                String msg = event.getMessage().toString();
                qqProxyInstance.receiveMessage(
                    messageFormatter.fromQQ(groupName, color, player, msg));
              }
            });
    bot.join();
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
