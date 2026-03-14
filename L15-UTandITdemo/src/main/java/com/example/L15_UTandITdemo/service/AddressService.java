package com.example.L15_UTandITdemo.service;


import com.example.L15_UTandITdemo.entity.Address;
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
