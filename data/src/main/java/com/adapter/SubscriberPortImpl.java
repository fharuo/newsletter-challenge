package com.adapter;

import com.entity.SubscriberEntity;
import com.model.SubscriberModel;
import com.port.SubscriberPort;
import com.repository.SubscriberRepository;
import org.springframework.stereotype.Component;

import java.util.List;

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
