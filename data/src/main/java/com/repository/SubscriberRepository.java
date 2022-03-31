package com.repository;

import com.entity.SubscriberEntity;
import com.model.SubscriberModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriberRepository extends JpaRepository<SubscriberEntity, Integer> {
    SubscriberModel findByUsername(String username);
}
