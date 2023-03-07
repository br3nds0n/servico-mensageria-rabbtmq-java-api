package com.rabbitmq.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UsuarioController {

    private final UsuarioMQProducer producer;

    public UsuarioController(UsuarioMQProducer producer) {
        this.producer = producer;
    }

    @GetMapping("/usuarios")
    public ResponseEntity<String> sendMessage() throws JsonProcessingException {
        producer.sendMessage();
        return ResponseEntity.ok("enviando para o RabbitMQ ...");
    }
}
