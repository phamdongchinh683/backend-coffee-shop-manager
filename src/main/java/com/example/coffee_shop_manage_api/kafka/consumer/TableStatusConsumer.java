package com.example.coffee_shop_manage_api.kafka.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.SocketIOServer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class TableStatusConsumer {
 private static final Logger logger = LoggerFactory.getLogger(TableStatusConsumer.class);

 private final SocketIOServer socketIOServer;
 private final ObjectMapper objectMapper;

 public TableStatusConsumer(SocketIOServer socketIOServer) {
  this.socketIOServer = socketIOServer;
  this.objectMapper = new ObjectMapper();
  logger.info("TableStatusConsumer initialized with SocketIOServer");
 }

 @KafkaListener(topics = "table-status", groupId = "coffee-shop-group")
 public void listen(String message) {
  try {

   JsonNode jsonNode = objectMapper.readTree(message);
   Short tableNumber = (short) jsonNode.get("tableNumber").asInt();
   String status = jsonNode.get("status").asText();
   String paymentStatus = jsonNode.get("paymentStatus").asText();

   socketIOServer.getBroadcastOperations().sendEvent("updateTable", message);

  } catch (Exception e) {
   logger.error("Error processing Kafka message: {}", message, e);
  }
 }
}
