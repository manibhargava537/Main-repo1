package com.smartcrop.service;

import com.smartcrop.entity.CropDetails;
import com.smartcrop.repository.CropDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CropDetailsServiceImpl implements CropDetailsService {

    @Autowired
    private CropDetailsRepository cropDetailsRepository;

    @Override
    public List<CropDetails> getAll() {
        return cropDetailsRepository.findAll();
    }

    @Override
    public List<CropDetails> getAllActive(boolean active) {
        return cropDetailsRepository.findAllByActive(active);
    }
}
