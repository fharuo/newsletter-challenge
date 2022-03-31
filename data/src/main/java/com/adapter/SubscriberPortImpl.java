package com.adapter;

import com.entity.SubscriberEntity;
import com.model.SubscriberModel;
import com.port.SubscriberPort;
import com.repository.SubscriberRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

import static java.time.LocalDateTime.*;

@Component
public class SubscriberPortImpl implements SubscriberPort {

    private final SubscriberRepository repository;

    public SubscriberPortImpl(SubscriberRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<SubscriberModel> getAll() {
        return repository.findAll().stream().map(this::toModel).toList();
    }

    @Override
    public SubscriberModel findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public SubscriberModel save(SubscriberModel subscriberModel) {
        SubscriberEntity subscriberEntity = toEntity(subscriberModel);
        subscriberEntity.setCreatedAt(now());
        return toModel(repository.save(subscriberEntity));
    }

    private SubscriberEntity toEntity(SubscriberModel subscriberModel) {
        return SubscriberEntity.builder()
                .id(subscriberModel.getId())
                .username(subscriberModel.getUsername())
                .password(subscriberModel.getPassword())
                .email(subscriberModel.getEmail())
                .admin(subscriberModel.isAdmin())
                .emailSent(subscriberModel.isEmailSent())
                .createdAt(subscriberModel.getCreatedAt())
                .updatedAt(subscriberModel.getUpdatedAt())
                .build();
    }

    private SubscriberModel toModel(SubscriberEntity subscriberEntity) {
        return SubscriberModel.builder()
                .id(subscriberEntity.getId())
                .username(subscriberEntity.getUsername())
                .password(subscriberEntity.getPassword())
                .email(subscriberEntity.getEmail())
                .admin(subscriberEntity.isAdmin())
                .emailSent(subscriberEntity.isEmailSent())
                .createdAt(subscriberEntity.getCreatedAt())
                .updatedAt(subscriberEntity.getUpdatedAt())
                .build();
    }
}
