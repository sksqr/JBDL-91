package com.example.L12_Spring_Data_JPA_Demo.service;

import com.example.L12_Spring_Data_JPA_Demo.entity.Address;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddressService {

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public Address createAddress(Address address){
        entityManager.persist(address);
        return address;
    }

}
