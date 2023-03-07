package com.rabbitmq.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioMQProducer {

    @Value("${rabbitmq.exchange.name}") private String exchange;
    @Value("${rabbitmq.routing.key}")   private String routingKey;
    private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioMQProducer.class);
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public UsuarioMQProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendMessage() throws JsonProcessingException {
        List<String> messages = new ArrayList<>();
        UsuarioDTO usuario1 = new UsuarioDTO();
        UsuarioDTO usuario2 = new UsuarioDTO();

        usuario1.setId(1L);
        usuario1.setNome("BRENDSON");

        usuario2.setId(2L);
        usuario2.setNome("BRENDSON");

        messages.add(objectMapper.writeValueAsString(usuario1));
        messages.add(objectMapper.writeValueAsString(usuario2));

        LOGGER.info(String.format("Mensagem enviada -> %s", messages));
        rabbitTemplate.convertAndSend(exchange, routingKey, messages);
    }
}
