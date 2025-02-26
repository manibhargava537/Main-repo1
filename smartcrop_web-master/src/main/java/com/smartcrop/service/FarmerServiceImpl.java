package com.smartcrop.service;

import com.smartcrop.entity.Farmer;
import com.smartcrop.repository.AddressRepository;
import com.smartcrop.repository.FarmerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FarmerServiceImpl implements FarmerService {

    @Autowired
    private FarmerRepository farmerRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Farmer registerFarmer(Farmer farmer) {
        farmer = farmerRepository.save(farmer);
        return farmer;
    }

    @Override
    public List<Farmer> getAllRegisteredFarmers() {
        return farmerRepository.findAll();
    }

    @Override
    public Optional<Farmer> getById(Long id) {
        return farmerRepository.findById(id);
    }

    @Override
    public List<Farmer> getAllFarmersPendingForApproval() {
        return farmerRepository.getAllRegisteredFarmerPendingForApproval();
    }

    @Override
    public Farmer saveFarmer(Farmer farmer) {
        return farmerRepository.save(farmer);
    }
}
