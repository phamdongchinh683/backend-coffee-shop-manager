package com.example.coffee_shop_manage_api.kafka.producer;

import java.math.BigDecimal;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ReportProducer {
 private static final Logger logger = LoggerFactory.getLogger(ReportProducer.class);
 private static final String TOPIC = "report-status";

 private final KafkaTemplate<String, String> kafkaTemplate;
 private final ObjectMapper objectMapper = new ObjectMapper();

 public ReportProducer(KafkaTemplate<String, String> kafkaTemplate) {
  this.kafkaTemplate = kafkaTemplate;
 }

 public void sendTotalAmount(BigDecimal totalAmount) {
  sendReport(Map.of("total_amount", totalAmount));
 }

 public void sendTotalCustomer(Integer totalCustomer) {
  sendReport(Map.of("total_customer", totalCustomer));
 }

 private void sendReport(Map<String, Object> payload) {
  try {
   String message = objectMapper.writeValueAsString(payload);
   kafkaTemplate.send(TOPIC, "report-status-1", message)
     .whenComplete((result, ex) -> {
      if (ex != null) {
       logger.error("❌ Failed to send Kafka message", ex);
      } else {
       logger.info("✅ Sent Kafka message to {}-{} offset={}",
         result.getRecordMetadata().topic(),
         result.getRecordMetadata().partition(),
         result.getRecordMetadata().offset());
      }
     });
  } catch (Exception e) {
   logger.error("Error serializing report payload", e);
  }
 }

}
