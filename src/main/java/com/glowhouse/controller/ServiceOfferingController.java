package com.glowhouse.controller;

import com.glowhouse.dto.request.ServiceOfferingDto;
import com.glowhouse.entity.ServiceOffering;
import com.glowhouse.mapper.ServiceOfferingMapper;
import com.glowhouse.service.ServiceManager;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/service-offering")
@RequiredArgsConstructor
public class ServiceOfferingController {

    private static final Logger logger = LoggerFactory.getLogger(ServiceOfferingController.class);

    private final ServiceManager serviceManager;

    @GetMapping("/getAllServicesBySalonId/{id}")
    public ResponseEntity<?> getAllServicesBySalonId (@PathVariable ("id") Long salonId,
                                                      @RequestParam(required = false) Long categoryId) {
        try {
            if (salonId == null) {
                logger.info("Salon Id must not be null.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Salon id must not be null, please provide valid salon id.");
            }
            logger.info("getAllServicesBySalonId:: starts.");
            Set<ServiceOffering> allServices = serviceManager.getAllServicesBySalonId(salonId, categoryId);
            if (allServices != null && !allServices.isEmpty()) {
                Set<ServiceOfferingDto> service = allServices.stream()
                        .map(ServiceOfferingMapper::mapToDto)
                        .collect(Collectors.toSet());
                logger.info("getAllServicesBySalonId:: ends.");
                return ResponseEntity.status(HttpStatus.OK)
                        .body(service);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("There are no services found for salonId: "+ salonId);
        } catch (Exception e) {
            logger.error("Exception while getAllServicesBySalonId: {}", e.getMessage());
            Map <String, String> response = new HashMap<>();
            response.put("error","Exception while get all services by salon id.");
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
    }

    @GetMapping("/getServicesById/{id}")
    public ResponseEntity<?> getServicesById (
            @PathVariable ("id") Long serviceId) {
        try {
            if (serviceId == null) {
                logger.info("Service Id must not be null.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Services id must not be null, please provide valid service id.");
            }
            logger.info("getServicesById:: starts.");
            ServiceOffering services = serviceManager.getServicesById(serviceId);
            if (services != null) {
                ServiceOfferingDto service = ServiceOfferingMapper.mapToDto(services);
                logger.info("getServicesById:: ends.");
                return ResponseEntity.status(HttpStatus.OK)
                        .body(service);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("There are no services found for Id: "+ serviceId);
        } catch (Exception e) {
            logger.error("Exception while getServicesById: {}", e.getMessage());
            Map <String, String> response = new HashMap<>();
            response.put("error","Exception while get all services by id.");
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
    }

    @GetMapping("/geAllServicesByIds/{ids}")
    public ResponseEntity<?> geAllServicesByIds (
            @PathVariable ("ids") Set<Long> serviceId) {

        try {
            if (serviceId == null) {
                logger.info("Service Ids field must not be null.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Services ids must not be null, please provide valid ids.");
            }
            logger.info("geAllServicesByIds:: starts.");
            Set<ServiceOffering> services = serviceManager.getServicesByServiceIds(serviceId);
            if (services != null && !services.isEmpty()) {
                Set<ServiceOfferingDto> servicesDto = services.stream()
                        .map(ServiceOfferingMapper::mapToDto)
                        .collect(Collectors.toSet());
                logger.info("geAllServicesByIds:: ends.");
                return ResponseEntity.status(HttpStatus.OK)
                        .body(servicesDto);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("There are no services found for provided Ids: "+ serviceId);
        } catch (Exception e) {
            logger.error("Exception while geAllServicesByIds: {}", e.getMessage());
            Map <String, String> response = new HashMap<>();
            response.put("error","Exception while get all services by ids.");
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
    }

}
