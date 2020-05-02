package icu.olorz.QQIntegration.core.qqBackend;

import icu.olorz.QQIntegration.MessageFormatter;
import icu.olorz.QQIntegration.api.IConfig;
import icu.olorz.QQIntegration.api.IQQBackend;
import icu.olorz.QQIntegration.api.IQQProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.exceptions.InvalidDataException;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.handshake.ServerHandshakeBuilder;
import org.java_websocket.server.WebSocketServer;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CoolQWSServer extends WebSocketServer implements IQQBackend {

  private IConfig config;
  private static final Logger LOGGER = LogManager.getLogger();
  private IQQProxy qqProxyInstance;
  private MessageFormatter messageFormatter;

  public CoolQWSServer(InetSocketAddress inetSocketAddress, IQQProxy qqProxyInstance, IConfig config) {
    super(inetSocketAddress);
    this.config = config;
    this.qqProxyInstance = qqProxyInstance;
    this.messageFormatter = new MessageFormatter(config);
  }

  @Override
  public ServerHandshakeBuilder onWebsocketHandshakeReceivedAsServer(
      WebSocket conn, Draft draft, ClientHandshake request)
      throws InvalidDataException, InvalidDataException {
    ServerHandshakeBuilder builder =
        super.onWebsocketHandshakeReceivedAsServer(conn, draft, request);

    Map<String, String> data = new HashMap<String, String>();
    for (Iterator<String> iter = request.iterateHttpFields(); iter.hasNext(); ) {
      String key = (String) iter.next();
      String value = request.getFieldValue(key);
      data.put(key, value);
    }
    if (!config.token().isEmpty()
        && !data.get("Authorization").equals("Token " + config.token())) {
      LOGGER.error("Token not match!");
      throw new InvalidDataException(999, "Token not match!");
    }
    // if (data.get("X-Client-Role").equals("Event")) {
    //     event = conn;
    // } else if (data.get("X-Client-Role").equals("API")) {
    //     api = conn;
    // }

    return builder;
  }

  @Override
  public void onOpen(WebSocket conn, ClientHandshake handshake) {
    LOGGER.debug("new connection to " + conn.getRemoteSocketAddress());
  }

  @Override
  public void onClose(WebSocket conn, int code, String reason, boolean remote) {}

  @Override
  public void onMessage(WebSocket conn, String message) {
    LOGGER.debug("[receiveMessage]data is:" + message);
    JSONObject data = new JSONObject(message);

    // 配置群号的群聊消息
    if (data.get("message_type").toString().equals("group")
        && data.get("group_id").toString().equals(config.groupId())) {
      String msg = data.get("message").toString();

      JSONObject sender = (JSONObject) data.get("sender");
      String groupName = config.groupName();
      String color = messageFormatter.getColor(sender.get("role").toString());
      String player = sender.get("nickname").toString();

      qqProxyInstance.receiveMessage(messageFormatter.fromQQ(groupName, color, player, msg));
    }
  }

  @Override
  public void onError(WebSocket conn, Exception ex) {}

  @Override
  public void onStart() {}

  @Override
  public void startBackend() {
    this.run();
  }

  @Override
  public void stopBackend() {
    try {
      this.stop();
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void msgToQQ(String msg) {
    JSONObject data = new JSONObject();
    JSONObject params = new JSONObject();
    data.put("action", "send_group_msg");
    params.put("group_id", config.groupId());
    params.put("message", msg);
    data.put("params", params);
    LOGGER.debug("[sendMessage]data is:" + data);
    broadcast(data.toString());
  }
}
