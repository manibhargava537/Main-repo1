package com.smartcrop.service;

import com.smartcrop.entity.CurrentCrop;
import org.springframework.stereotype.Service;

@Service
public interface CurrentCropService {

    CurrentCrop save(CurrentCrop currentCrop);
}
