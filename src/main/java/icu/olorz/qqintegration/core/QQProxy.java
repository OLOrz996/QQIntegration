package icu.olorz.qqintegration.core;

import icu.olorz.qqintegration.Constants;
import icu.olorz.qqintegration.QQIntegration;
import icu.olorz.qqintegration.api.IMinecraftAdapter;
import icu.olorz.qqintegration.api.IQQBackend;
import icu.olorz.qqintegration.api.IQQProxy;
import icu.olorz.qqintegration.config.QQIntegrationConfig;
import icu.olorz.qqintegration.core.qqBackend.CoolQWSServer;
import icu.olorz.qqintegration.core.qqBackend.MiraiBot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetSocketAddress;

// import icu.olorz.qq_integration.core.Mirai.MiraiBot;
// 中间层，隔离MC与QQ后端程序
public class QQProxy implements IQQProxy {

  private static final Logger LOGGER = LogManager.getLogger(QQIntegration.MODID + " QQProxy");
  private final IMinecraftAdapter mcAdapter;
  private IQQBackend backendInstance;
  private Thread proxyThread;

  public QQProxy(IMinecraftAdapter mcAdapter) {
    // 创建后端服务对象
    if (QQIntegrationConfig.backendType == Constants.BACKEND_TYPE.COOLQ) {
      LOGGER.debug(
          "CoolQServer host port: " + QQIntegrationConfig.host + " " + QQIntegrationConfig.port);
      this.backendInstance =
          new CoolQWSServer(
              new InetSocketAddress(QQIntegrationConfig.host, QQIntegrationConfig.port), this);
    } else if (QQIntegrationConfig.backendType == Constants.BACKEND_TYPE.MIRAI) {
      LOGGER.debug("MiraiBot 初始化");
      this.backendInstance = new MiraiBot(this);
    } else {
      this.backendInstance = new MiraiBot(this);
    }
    // this.backendInstance =
    //     new CoolQWSServer(
    //         new InetSocketAddress(QQIntegrationConfig.host, QQIntegrationConfig.port), this);
    this.mcAdapter = mcAdapter;
  }

  @Override
  public void startProxy() {
    if (proxyThread == null) {
      proxyThread =
          new Thread(
              () -> {
                backendInstance.startBackend();
                LOGGER.debug("服务启动成功");
              });
    }
    if (!proxyThread.isAlive()) {
      proxyThread.start();
    }
  }

  @Override
  public void stopProxy() {
    if (proxyThread.isAlive()) {
      backendInstance.stopBackend();
      proxyThread.interrupt();
      LOGGER.debug("服务关闭成功");
    }
  }

  @Override
  public void restartProxy() {
    stopProxy();
    startProxy();
  }

  @Override
  public void statusProxy() {}

  @Override
  public void sendMessage(String message) {
    backendInstance.msgToQQ(message);
  }

  @Override
  public void receiveMessage(String message) {
    mcAdapter.sendMessage(message);
  }
}
