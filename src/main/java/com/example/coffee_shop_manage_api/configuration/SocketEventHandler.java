package com.example.coffee_shop_manage_api.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;

import jakarta.annotation.PostConstruct;

@Component
public class SocketEventHandler {

 private static final Logger logger = LoggerFactory.getLogger(SocketEventHandler.class);

 @Autowired
 private SocketIOServer socketIOServer;

 @PostConstruct
 public void setupEventListeners() {
  logger.info("Setting up Socket.IO event listeners");

  socketIOServer.addConnectListener(new ConnectListener() {
   @Override
   public void onConnect(SocketIOClient client) {
    logger.info("Client connected: {}", client.getSessionId());
    client.sendEvent("connected", "Welcome to Coffee Shop");
   }
  });

  socketIOServer.addDisconnectListener(new DisconnectListener() {
   @Override
   public void onDisconnect(SocketIOClient client) {
    logger.info("Client disconnected: {}", client.getSessionId());
   }
  });
}
}
