package com.example.L23paymentgatewaydemo.repo;

import com.example.L23paymentgatewaydemo.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantRepo extends JpaRepository<Merchant, Long> {
}
