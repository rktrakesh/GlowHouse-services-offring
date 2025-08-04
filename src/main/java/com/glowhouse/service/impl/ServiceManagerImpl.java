package com.glowhouse.service.impl;

import com.glowhouse.dto.CategoryDTO;
import com.glowhouse.dto.SalonDTO;
import com.glowhouse.dto.request.ServiceOfferingDto;
import com.glowhouse.entity.ServiceOffering;
import com.glowhouse.repository.ServiceOfferingRepo;
import com.glowhouse.service.ServiceManager;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceManagerImpl implements ServiceManager {

    private static final Logger logger = LoggerFactory.getLogger(ServiceManagerImpl.class);

    private final ServiceOfferingRepo serviceOfferingRepo;

    @Override
    public ServiceOffering addNewService(SalonDTO salonDo,
                                         ServiceOfferingDto serviceDTO,
                                         CategoryDTO categoryDTO) {
        try {
            ServiceOffering serviceOffering = new ServiceOffering();
            serviceOffering.setServiceName(serviceDTO.getServiceName());
            serviceOffering.setImage(serviceDTO.getImage());
            serviceOffering.setSalonId(salonDo.getId());
            serviceOffering.setPrice(serviceDTO.getPrice());
            serviceOffering.setDescription(serviceDTO.getDescription());
            serviceOffering.setDuration(serviceDTO.getDuration());
            serviceOffering.setCategoryId(categoryDTO.getId());
            return serviceOfferingRepo.save(serviceOffering);
        } catch (Exception e) {
            logger.error("Exception while addNewService: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public ServiceOffering updateService(Long serviceId,
                                         ServiceOfferingDto service) {
        try {
            ServiceOffering serviceOffering = serviceOfferingRepo.findById(serviceId).orElse(null);
            if (serviceOffering != null) {
                Optional.ofNullable(service.getServiceName()).ifPresent(serviceOffering::setServiceName);
                Optional.ofNullable(service.getImage()).ifPresent(serviceOffering::setImage);
                Optional.of(service.getPrice()).ifPresent(serviceOffering::setPrice);
                Optional.ofNullable(service.getDescription()).ifPresent(serviceOffering::setDescription);
                Optional.of(service.getDuration()).ifPresent(serviceOffering::setDuration);
                return serviceOfferingRepo.save(serviceOffering);
            }
            return null;
        } catch (Exception e) {
            logger.error("exception while updateService: {}" , e.getMessage());
            return null;
        }
    }

    @Override
    public Set<ServiceOffering> getAllServicesBySalonId(Long salonId, Long categoryId) {
        try {
            Set<ServiceOffering> services = serviceOfferingRepo.findBySalonId(salonId);
            if (categoryId != null && services != null && !services.isEmpty()) {
                services = services.stream().filter(
                        (service) -> service.getCategoryId() != null && service.getCategoryId().equals(categoryId)
                ).collect(Collectors.toSet());
            }
            return services;
        } catch (Exception e) {
            logger.error("Exception while getAllServicesBySalonId: {}",e.getMessage() );
            return null;
        }
    }

    @Override
    public Set<ServiceOffering> getServicesByServiceIds (Set<Long> serviceIds) {
        try {
            return new HashSet<>(serviceOfferingRepo.findAllById(serviceIds));
        } catch (Exception e) {
            logger.error("Exception while getServicesByServiceIds: {}",e.getMessage() );
            return null;
        }
    }

    @Override
    public ServiceOffering getServicesById(Long id) {
        try {
            return serviceOfferingRepo.findById(id).orElse(null);
        } catch (Exception e) {
            logger.error("Exception while getServicesByServiceId: {}",e.getMessage() );
            return null;
        }
    }


}
