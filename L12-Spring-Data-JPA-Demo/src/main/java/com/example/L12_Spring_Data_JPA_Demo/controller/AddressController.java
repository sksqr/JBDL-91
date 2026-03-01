package com.example.L12_Spring_Data_JPA_Demo.controller;

import com.example.L12_Spring_Data_JPA_Demo.entity.Address;
import com.example.L12_Spring_Data_JPA_Demo.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/add")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping
    public ResponseEntity<Address> createAdd(@RequestBody Address address) {
        address = addressService.createAddress(address);
        return ResponseEntity.ok(address);
    }

}