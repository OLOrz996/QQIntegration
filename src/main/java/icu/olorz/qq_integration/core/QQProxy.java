package icu.olorz.qq_integration.core;

import icu.olorz.qq_integration.api.IMinecraftAdapter;
import icu.olorz.qq_integration.api.IQQBackend;
import icu.olorz.qq_integration.api.IQQProxy;
import icu.olorz.qq_integration.config.QQIntegrationConfig;
import icu.olorz.qq_integration.core.qqBackend.CoolQWSServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetSocketAddress;

// import icu.olorz.qq_integration.core.Mirai.MiraiBot;
// 中间层，隔离MC与QQ后端程序
public class QQProxy implements IQQProxy {

  private static final Logger LOGGER = LogManager.getLogger();
  private final IMinecraftAdapter mcAdapter;
  private IQQBackend backendInstance;
  private Thread proxyThread = new Thread(() -> backendInstance.startBackend());

  public QQProxy(IMinecraftAdapter mcAdapter) {
    this.mcAdapter = mcAdapter;
  }

  public void startProxy() {
    if (!proxyThread.isAlive()) {
      // 创建后端服务对象
      // if (QQIntegrationConfig.backendType == Constants.BACKEND_TYPE.COOLQ) {
      //   LOGGER.debug(
      //       "CoolQServer host port: " + QQIntegrationConfig.host + " " +
      // QQIntegrationConfig.port);
      //   this.backendInstance =
      //       new CoolQWSServer(
      //           new InetSocketAddress(QQIntegrationConfig.host, QQIntegrationConfig.port), this);
      // } else if (QQIntegrationConfig.backendType == Constants.BACKEND_TYPE.MIRAI) {
      //   LOGGER.debug("MiraiBot 初始化");
      //   this.backendInstance = new MiraiBot(this);
      // } else {
      //   this.backendInstance = new MiraiBot(this);
      // }
      this.backendInstance =
          new CoolQWSServer(
              new InetSocketAddress(QQIntegrationConfig.host, QQIntegrationConfig.port), this);
      proxyThread.start();
    }
  }

  public void stopProxy() {
    if (proxyThread.isAlive()) {
      backendInstance.stopBackend();
      proxyThread.interrupt();
    }
  }

  public void restartProxy() {
    stopProxy();
    startProxy();
  }

  public void statusProxy() {}

  public void sendMessage(String message) {
    backendInstance.msgToQQ(message);
  }

  public void receiveMessage(String message) {
    mcAdapter.sendMessage(message);
  }
}
