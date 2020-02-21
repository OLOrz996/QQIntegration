package icu.olorz.qq_integration.api;

// 后端接口
public interface IQQBackend {
  // 启动
  void startBackend();
  // 停止
  void stopBackend();
  // 发送信息
  void msgToQQ(String msg);
}
