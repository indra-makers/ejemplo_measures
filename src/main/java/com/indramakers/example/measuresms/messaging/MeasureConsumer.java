package com.indramakers.example.measuresms.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.indramakers.example.measuresms.model.entities.Measure;
import com.indramakers.example.measuresms.services.MeasureService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MeasureConsumer {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MeasureService measureService;

    @RabbitListener(queues = "test_measures")
    public void listenerTestQueue(String mensaje) {
        System.out.println(mensaje);
        try {
            Measure measure = objectMapper.readValue(mensaje, Measure.class);
            measureService.saveMeasure(measure.getDeviceId(), measure.getValue());
        } catch (JsonProcessingException exc) {
            exc.printStackTrace();
        }

    }

}
