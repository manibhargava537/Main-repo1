package com.smartcrop.service;

import com.smartcrop.entity.Farmer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface  FarmerService {

    Farmer registerFarmer(Farmer farmer);


    List<Farmer> getAllRegisteredFarmers();

    Optional<Farmer> getById(Long id);

    List<Farmer> getAllFarmersPendingForApproval();

    Farmer saveFarmer(Farmer farmer);
}
