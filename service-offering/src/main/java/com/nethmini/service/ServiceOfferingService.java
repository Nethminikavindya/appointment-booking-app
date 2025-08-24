package com.nethmini.service;

import com.nethmini.dto.CategoryDTO;
import com.nethmini.dto.SalonDTO;
import com.nethmini.dto.ServiceDTO;
import com.nethmini.modal.ServiceOffering;

import java.util.Set;

public interface ServiceOfferingService {

    ServiceOffering createService(SalonDTO salonDto, ServiceDTO serviceDto , CategoryDTO categoryDto);

    ServiceOffering updateService(Long serviceId, ServiceOffering service) throws Exception;

    Set<ServiceOffering> getAllServiceBySalonId(Long salonId, Long categoryId );

    Set<ServiceOffering> getServicesByIds(Set<Long> ids );

    ServiceOffering getServiceById(Long id) throws Exception;


}
