package com.example.coffee_shop_manage_api.configuration;

import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.SocketIOServer;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;


@Component
public class SocketServerRunner {
 private final SocketIOServer server;

 public SocketServerRunner(SocketIOServer server) {
  this.server = server;
 }

 @PostConstruct
 public void start() {
  server.start();
  System.out.println("Socket.IO server started on port 9095");
 }

 @PreDestroy
 public void stop() {
  server.stop();
 }
}
