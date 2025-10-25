package com.example.coffee_shop_manage_api.kafka.producer;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TableStatusProducer {
 private static final Logger logger = LoggerFactory.getLogger(TableStatusProducer.class);
 private static final String TOPIC = "table-status";

 @Autowired
 private KafkaTemplate<String, String> kafkaTemplate;

 private final ObjectMapper objectMapper = new ObjectMapper();

 public void sendTableStatus(Short tableNumber, String status, String paymentStatus) {
  try {
   Map<String, Object> payload = new HashMap<>();
   payload.put("tableNumber", tableNumber);
   payload.put("status", status);
   payload.put("paymentStatus", paymentStatus);

   String message = objectMapper.writeValueAsString(payload);

   kafkaTemplate.send(TOPIC, "table-status-" + tableNumber, message);

  } catch (Exception e) {
   logger.error("Error preparing table status update for table {}", tableNumber, e);
  }
 }
}
