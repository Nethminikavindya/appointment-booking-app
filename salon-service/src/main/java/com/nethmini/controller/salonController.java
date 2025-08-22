package com.nethmini.controller;

import com.nethmini.modal.Salon;
import com.nethmini.payload.dto.SalonDTO;
import com.nethmini.payload.dto.UserDTO;
import com.nethmini.service.SalonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/salons")
@RequiredArgsConstructor
public class salonController {

    private final SalonService salonService;

    public ResponseEntity<SalonDTO> createSalon(@RequestBody SalonDTO salonDTO){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        Salon salon = salonService.createSalon(salonDTO, userDTO);
        return null;
    }
}
