package com.glowhouse.repository;

import com.glowhouse.entity.ServiceOffering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ServiceOfferingRepo extends JpaRepository <ServiceOffering, Long> {

    Set<ServiceOffering> findBySalonId (Long salonId);

}
