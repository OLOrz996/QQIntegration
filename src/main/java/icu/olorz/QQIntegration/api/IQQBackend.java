package icu.olorz.QQIntegration.api;

// 后端接口
public interface IQQBackend {
  /** 启动 */
  void startBackend();
  /** 停止 */
  void stopBackend();

  /**
   * 发送信息
   *
   * @param msg
   */
  void msgToQQ(String msg);
}
