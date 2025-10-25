package com.example.coffee_shop_manage_api.kafka.producer;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.example.coffee_shop_manage_api.model.Reservation;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ReservationProducer {
  private static final Logger logger = LoggerFactory.getLogger(ReservationProducer.class);
  private static final String TOPIC = "pending-orders";

  @Autowired
  private KafkaTemplate<String, String> kafkaTemplate;

  private final ObjectMapper objectMapper = new ObjectMapper();

  public void sendReservation(Reservation reservation) {
    try {
      if (reservation == null) {
        logger.warn("Reservation is null, cannot send message");
        return;
      }

      Map<String, Object> payload = new HashMap<>();

      payload.put("reservationId", reservation.getId());
      payload.put("tableId", reservation.getTable().getId());
      payload.put("userId", reservation.getUser().getId());
      payload.put("status", reservation.getStatus().name());
      payload.put("reservationTime",
          reservation.getReservationTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

      String message = objectMapper.writeValueAsString(payload);
      int i = 0;
      String key = "pending-orders-" + i++;
      kafkaTemplate.send(TOPIC, key, message);

      logger.info("Successfully sent reservation message for reservation: {}", key);

    } catch (Exception e) {
      logger.error("Error preparing reservation status update for reservation {}",
          reservation, e);
    }
  }
}
