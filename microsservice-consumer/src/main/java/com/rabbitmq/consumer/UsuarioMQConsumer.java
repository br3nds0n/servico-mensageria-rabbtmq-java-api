package com.rabbitmq.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioMQConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioMQConsumer.class);
    private final ObjectMapper objectMapper;

    public UsuarioMQConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void consume(List<String> mensagens) throws JsonProcessingException {
        List<UsuarioDTO> usuarios = new ArrayList<>();

        mensagens.forEach(mensagen -> {
            try {
                UsuarioDTO usuarioDTO = objectMapper.readValue(mensagen, UsuarioDTO.class);
                usuarios.add(usuarioDTO);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });

        LOGGER.info(String.format("Mensagem recebida -> %s", mensagens));
    }
}
