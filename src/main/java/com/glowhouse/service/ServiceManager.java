package com.glowhouse.service;

import com.glowhouse.dto.CategoryDTO;
import com.glowhouse.dto.SalonDTO;
import com.glowhouse.dto.request.ServiceOfferingDto;
import com.glowhouse.entity.ServiceOffering;

import java.util.Set;

public interface ServiceManager {

    ServiceOffering addNewService (SalonDTO salonDo,
                                   ServiceOfferingDto serviceDTO,
                                   CategoryDTO categoryDTO);

    ServiceOffering updateService (Long serviceId,
                                   ServiceOfferingDto service);

    Set<ServiceOffering> getAllServicesBySalonId (Long salonId,
                                                  Long categoryId);

    Set<ServiceOffering> getServicesByServiceIds (Set<Long> serviceIds);

    ServiceOffering getServicesById (Long id);

}
