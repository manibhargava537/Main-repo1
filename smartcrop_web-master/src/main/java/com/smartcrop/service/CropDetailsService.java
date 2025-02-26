package com.smartcrop.service;

import com.smartcrop.entity.CropDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CropDetailsService {
    List<CropDetails> getAll();

    List<CropDetails> getAllActive(boolean active);
}
