package com.example.coffee_shop_manage_api.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.corundumstudio.socketio.SocketIOServer;

@Configuration
public class SocketIOConfig {

 @Value("${socketio.host:0.0.0.0}")
 private String host;

 @Value("${socketio.port:9095}")
 private Integer port;

 @Bean
 public SocketIOServer socketIOServer() {
  com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
  config.setHostname(host);
  config.setPort(port);
  config.setPingInterval(43200000);
  config.setPingTimeout(43200000);
  config.setAllowCustomRequests(true);
  config.setOrigin("*");
  return new SocketIOServer(config);
 }
}
