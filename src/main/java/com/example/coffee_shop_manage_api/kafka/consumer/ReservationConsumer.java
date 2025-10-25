package com.example.coffee_shop_manage_api.kafka.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.SocketIOServer;

@Component
public class ReservationConsumer {
 private static final Logger logger = LoggerFactory.getLogger(ReservationConsumer.class);

 private final SocketIOServer socketIOServer;

 public ReservationConsumer(SocketIOServer socketIOServer) {
  this.socketIOServer = socketIOServer;
  logger.info("Reservation initialized with SocketIOServer");
 }

 @KafkaListener(topics = "pending-orders", groupId = "coffee-shop-group")
 public void listen(String message) {
  try {
   socketIOServer.getBroadcastOperations().sendEvent("updateReservation", message);
  } catch (Exception e) {
   logger.error("Error processing Kafka message: {}", message, e);
  }
 }
}
