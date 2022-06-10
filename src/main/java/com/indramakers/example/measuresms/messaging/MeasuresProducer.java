package com.indramakers.example.measuresms.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.indramakers.example.measuresms.model.entities.Measure;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MeasuresProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendMeasure(Measure measure) {
        try {
            String message = objectMapper.writeValueAsString(measure);
            rabbitTemplate.convertAndSend("test_measures", message);
        } catch (JsonProcessingException exc) {
            exc.printStackTrace();
        }
    }
}
