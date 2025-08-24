package com.nethmini.repository;

import com.nethmini.modal.ServiceOffering;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;


public interface ServiceOfferingRepository extends JpaRepository<ServiceOffering, Long> {

    Set<ServiceOffering> findAllBySalonId(Long salonId);
}
