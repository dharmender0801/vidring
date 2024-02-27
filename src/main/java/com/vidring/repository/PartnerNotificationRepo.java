package com.vidring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vidring.model.PartnerNotificationModel;

public interface PartnerNotificationRepo extends JpaRepository<PartnerNotificationModel, Long> {

}
