package com.smartcrop.service;

import com.smartcrop.entity.BankDetails;
import com.smartcrop.repository.BankDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankDetailsServiceImpl implements BankDetailsService{
    @Autowired
    private BankDetailsRepository bankDetailsRepository;
    @Override
    public BankDetails save(BankDetails bankDetails) {
        return bankDetailsRepository.save(bankDetails);
    }
}
