package icu.olorz.QQIntegration.api;

// 代理后端不同的实现方式
public interface IQQProxy {
  void startProxy();

  void stopProxy();

  void restartProxy();

  void statusProxy();

  /**
   * 发送信息到QQ
   *
   * @param message 信息内容
   */
  void sendMessage(String message);

  /**
   * 从QQ接收信息
   *
   * @param message 信息内容
   */
  void receiveMessage(String message);
}
