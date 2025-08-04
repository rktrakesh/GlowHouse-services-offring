package com.glowhouse.controller;

import com.glowhouse.dto.CategoryDTO;
import com.glowhouse.dto.SalonDTO;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/owner/service-offering")
public class OwnerServiceController {

    private static final Logger logger = LoggerFactory.getLogger(OwnerServiceController.class);

    private final ServiceManager serviceManager;

    @PostMapping("/addNewServices")
    public ResponseEntity<?> addNewServices (@RequestBody ServiceOfferingDto serviceDto) {
        try {
            if (serviceDto == null) {
                logger.warn("Those fields can't be null, please provide valid data.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Those fields can't be null, please provide valid data.");
            }
            logger.info("addNewServices:: starts");
            SalonDTO salonDto = new SalonDTO();
            salonDto.setId(1L);
            CategoryDTO categoryDto = new CategoryDTO();
            categoryDto.setId(6L);
            ServiceOffering serviceOffering = serviceManager.addNewService(salonDto, serviceDto, categoryDto);
            if (serviceOffering != null) {
                logger.info("addNewServices:: ends");
                ServiceOfferingDto serviceOfferingDto = ServiceOfferingMapper.mapToDto(serviceOffering);
                return ResponseEntity.status(HttpStatus.OK)
                        .body(serviceOfferingDto);
            }
            logger.warn("Unable to add new services.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Unable to add new services.");
        } catch (Exception e) {
            logger.error("Exception while addNewServices: {}", e.getMessage());
            Map<String, String> response = new HashMap<>();
            response.put("error","Exception while adding new services.");
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
    }

    @PutMapping("/updateService/{id}")
    public ResponseEntity<?> updateServicesById (@RequestBody ServiceOfferingDto serviceDto,
                                                 @PathVariable ("id") Long serviceId ) {
        try {
            if (serviceId == null) {
                logger.warn("Unable to update the services, service id is null.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Unable to update the services, service id is null");
            }
            logger.info("updateServicesById:: starts");
            ServiceOffering serviceOffering = serviceManager.updateService(serviceId, serviceDto);
            if (serviceOffering != null) {
                ServiceOfferingDto updatedService = ServiceOfferingMapper.mapToDto(serviceOffering);
                logger.info("updateServicesById:: ends");
                return ResponseEntity.status(HttpStatus.OK)
                        .body(updatedService);
            }
            logger.warn("Unable to update the services for serviceId: {}",serviceId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Unable to update the services for serviceId: " + serviceId);
        } catch (Exception e) {
            logger.error("Exception while updating services Id {}", e.getMessage());
            Map<String, String> response = new HashMap<>();
            response.put("error","Exception while updating services Id.");
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
    }

}
