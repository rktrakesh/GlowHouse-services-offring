package com.glowhouse.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Data@AllArgsConstructor@NoArgsConstructor
public class SalonDTO {
    @Schema(hidden = true)
    private Long id;
    private String name;
    private List<String> images;
    private String address;
    private String mobile;
    private String email;
    private String city;
    private Long ownerId;
    private LocalTime openTime;
    private LocalTime closeTime;
}
