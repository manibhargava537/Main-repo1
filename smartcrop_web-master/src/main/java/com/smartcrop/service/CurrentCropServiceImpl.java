package com.smartcrop.service;

import com.smartcrop.entity.CurrentCrop;
import com.smartcrop.repository.CurrentCropRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrentCropServiceImpl implements CurrentCropService{

    @Autowired
    private CurrentCropRepository currentCropRepository;

    @Override
    public CurrentCrop save(CurrentCrop currentCrop) {
        return currentCropRepository.save(currentCrop);
    }
}
