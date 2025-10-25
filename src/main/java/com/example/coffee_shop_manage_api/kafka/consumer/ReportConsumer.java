package com.example.coffee_shop_manage_api.kafka.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.SocketIOServer;

@Component
public class ReportConsumer {
 private static final Logger logger = LoggerFactory.getLogger(ReportConsumer.class);

 private final SocketIOServer socketIOServer;

 public ReportConsumer(SocketIOServer socketIOServer) {
  this.socketIOServer = socketIOServer;
  logger.info("Report initialized with SocketIOServer");
 }

 @KafkaListener(topics = "report-status", groupId = "coffee-shop-group")
 public void listen(String message) {
  try {
   socketIOServer.getBroadcastOperations().sendEvent("updateReport", message);
  } catch (Exception e) {
   logger.error("Error processing Kafka message: {}", message, e);
  }
 }
}
