package com.controller;

import com.dto.CredentialsDTO;
import com.dto.SubscriberDTO;
import com.dto.SubscriberResponseDTO;
import com.dto.TokenDTO;
import com.exception.InvalidPasswordException;
import com.model.SubscriberModel;
import com.security.JWTService;
import com.service.SubscriberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1/subscribers")
public class SubscriberController {

    private final SubscriberService service;
    private final JWTService jwtService;

    public SubscriberController(SubscriberService service, JWTService jwtService) {
        this.service = service;
        this.jwtService = jwtService;
    }

    @GetMapping
    public ResponseEntity<List<SubscriberDTO>> getAll() {
        List<SubscriberDTO> subscribers = service.getAll().stream().map(this::toDTO).toList();

        return ResponseEntity.ok(subscribers);
    }

    @PostMapping
    public ResponseEntity<SubscriberResponseDTO> create(@RequestBody SubscriberDTO subscriberDTO) {
        SubscriberResponseDTO subscriberResponseDTO = toSubscriberResponseDTO(service.create(toModel(subscriberDTO)));

        return ResponseEntity.created(buildLocation(subscriberResponseDTO.getId())).body(subscriberResponseDTO);
    }

    @PostMapping("/auth")
    public TokenDTO authenticate(@RequestBody CredentialsDTO credentials) {
        try {
            SubscriberDTO subscriberDTO = SubscriberDTO.builder().username(credentials.getUsername()).password(credentials.getPassword()).build();
            UserDetails authenticatedUser = service.authenticate(toModel(subscriberDTO));
            String token = jwtService.generateToken(toModel(subscriberDTO));
            return new TokenDTO(subscriberDTO.getUsername(), token);

        } catch (UsernameNotFoundException | InvalidPasswordException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    private SubscriberResponseDTO toSubscriberResponseDTO(SubscriberModel subscriberModel) {
        return SubscriberResponseDTO.builder()
                .id(subscriberModel.getId())
                .username(subscriberModel.getUsername())
                .email(subscriberModel.getEmail())
                .admin(subscriberModel.isAdmin())
                .build();
    }

    private SubscriberDTO toDTO(SubscriberModel subscriberModel) {
        return SubscriberDTO.builder()
                .id(subscriberModel.getId())
                .username(subscriberModel.getUsername())
                .password(subscriberModel.getPassword())
                .email(subscriberModel.getEmail())
                .build();
    }

    private SubscriberModel toModel(SubscriberDTO subscriberDTO) {
        return SubscriberModel.builder()
                .id(subscriberDTO.getId())
                .username(subscriberDTO.getUsername())
                .password(subscriberDTO.getPassword())
                .email(subscriberDTO.getEmail())
                .admin(subscriberDTO.isAdmin())
                .build();
    }

    private URI buildLocation(Integer id) {
        return  ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }
}
