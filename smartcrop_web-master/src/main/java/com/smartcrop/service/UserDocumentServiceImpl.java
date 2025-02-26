package com.smartcrop.service;

import com.smartcrop.entity.SmartCropUserDoc;
import com.smartcrop.model.UserDocDTO;
import com.smartcrop.repository.SmartCropUserDocRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class UserDocumentServiceImpl implements UserDocumentService{

    @Autowired
    SmartCropUserDocRepository smartCropUserDocRepository;
    @Override
    public SmartCropUserDoc saveUserDocument(SmartCropUserDoc smartCropUserDoc) {
        return smartCropUserDocRepository.save(smartCropUserDoc);
    }

    @Override
    @Transactional
    public void saveAllUserDocument(List<SmartCropUserDoc> smartCropUserDocList) {
        smartCropUserDocRepository.saveAll(smartCropUserDocList);
    }

    @Override
    public UserDocDTO getEmployeeDocumentByIdAndDocumentType(Long employeeId, String documentType) {
        SmartCropUserDoc smartCropUserDoc =  smartCropUserDocRepository.findByEmployee_IdAndDocumentType(employeeId,documentType);
        return UserDocDTO.builder()
                .document(smartCropUserDoc.getDocument())
                .documentType(smartCropUserDoc.getDocumentType())
                .docExtension(smartCropUserDoc.getDocExtension())
                .build();
    }

    @Override
    public UserDocDTO getFarmerDocumentByIdAndDocumentType(Long employeeId, String documentType) {
        SmartCropUserDoc smartCropUserDoc =  smartCropUserDocRepository.findByFarmer_IdAndDocumentType(employeeId,documentType);
        return UserDocDTO.builder()
                .document(smartCropUserDoc.getDocument())
                .documentType(smartCropUserDoc.getDocumentType())
                .docExtension(smartCropUserDoc.getDocExtension())
                .build();
    }
}
