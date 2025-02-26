package com.smartcrop.service;

import com.smartcrop.entity.ProposedArea;
import com.smartcrop.repository.ProposedAreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProposedAreaServiceImpl implements ProposedAreaService{

    @Autowired
    private ProposedAreaRepository proposedAreaRepository;
    @Override
    public ProposedArea save(ProposedArea proposedArea) {
        return proposedAreaRepository.save(proposedArea);
    }
}
