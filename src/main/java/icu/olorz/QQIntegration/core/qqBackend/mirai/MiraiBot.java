package icu.olorz.QQIntegration.core.qqBackend.mirai;

import icu.olorz.QQIntegration.MessageFormatter;
import icu.olorz.QQIntegration.api.IConfig;
import icu.olorz.QQIntegration.api.IQQBackend;
import icu.olorz.QQIntegration.api.IQQProxy;
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

  private IConfig config;
  // private final BlockingBot bot =
  //     BlockingBot.newInstance(Long.parseLong(config.qqId(),
  // config.qqPw();
  // 使用自定义的配置
  private final Bot bot;
  private Listener<GroupMessage> grouplistener;
  private IQQProxy qqProxyInstance;
  private Group group;
  private final MessageFormatter messageFormatter;

  public MiraiBot(IQQProxy qqProxyInstance,IConfig config) {
    this.qqProxyInstance = qqProxyInstance;
    this.config = config;
    this.messageFormatter = new MessageFormatter(config);
    this.bot = BotFactoryJvm.newBot(
            Long.parseLong(config.qqId()),
            config.qqPw(),
            new BotConfiguration() {
              {
                setDeviceInfo(
                        context ->
                                SystemDeviceInfoKt.loadAsDeviceInfo(new File("deviceInfo.json"), context));
                // setLoginSolver();
                // setBotLoggerSupplier();
              }
            });
  }

  @Override
  public void startBackend() {
    bot.login();
    group = bot.getGroup(Long.parseLong(config.groupId()));
    grouplistener =
        Events.subscribeAlways(
            GroupMessage.class,
            (GroupMessage event) -> {
              Group senderGroup = event.getGroup();
              if (senderGroup.getId() == Long.parseLong(config.groupId())) {
                String groupName = config.groupName();
                String color =
                    messageFormatter.getColor(event.getPermission().toString().toLowerCase());
                String player = event.getSenderName();
                String msg = event.getMessage().contentToString();
                // TODO 增加对指令的支持
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
