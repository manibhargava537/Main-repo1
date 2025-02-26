package com.smartcrop.service;

import com.smartcrop.entity.BankDetails;
import org.springframework.stereotype.Service;

@Service
public interface BankDetailsService {

    BankDetails save(BankDetails bankDetails);
}
