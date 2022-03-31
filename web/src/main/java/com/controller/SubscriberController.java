package com.controller;

import com.dto.SubscriberDTO;
import com.model.SubscriberModel;
import com.service.SubscriberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/subscribers")
public class SubscriberController {

    private final SubscriberService service;

    public SubscriberController(SubscriberService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<SubscriberDTO>> getAll() {
        List<SubscriberDTO> subscribers = service.getAll().stream().map(this::toDTO).toList();

        return ResponseEntity.ok(subscribers);
    }

    @GetMapping("/status")
    public String healthChecker() {
        return "ok";
    }

    private SubscriberDTO toDTO(SubscriberModel subscriberModel) {
        return SubscriberDTO.builder()
                .id(subscriberModel.getId())
                .username(subscriberModel.getUsername())
                .email(subscriberModel.getEmail())
                .build();
    }
}
