package com.glowhouse.mapper;

import com.glowhouse.dto.request.ServiceOfferingDto;
import com.glowhouse.entity.ServiceOffering;

public class ServiceOfferingMapper {

    public static ServiceOfferingDto mapToDto (ServiceOffering serviceOffering) {
        ServiceOfferingDto services = new ServiceOfferingDto();
        services.setId(serviceOffering.getId());
        services.setServiceName(serviceOffering.getServiceName());
        services.setImage(serviceOffering.getImage());
        services.setDuration(serviceOffering.getDuration());
        services.setPrice(serviceOffering.getPrice());
        services.setDescription(serviceOffering.getDescription());
        services.setSalonId(serviceOffering.getSalonId());
        services.setCategoryId(serviceOffering.getCategoryId());
        return services;
    }

}
