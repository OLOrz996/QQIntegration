package icu.olorz.qqintegration.api;

// 代理后端不同的实现方式
public interface IQQProxy {
  void startProxy();

  void stopProxy();

  void restartProxy();

  void statusProxy();

  void sendMessage(String message);

  void receiveMessage(String message);
}
