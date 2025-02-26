package com.smartcrop.service;

import com.smartcrop.entity.SmartCropUserDoc;
import com.smartcrop.model.UserDocDTO;
import org.apache.catalina.User;

import java.util.List;

public interface UserDocumentService {

    SmartCropUserDoc saveUserDocument(SmartCropUserDoc smartCropUserDoc);

    void saveAllUserDocument(List<SmartCropUserDoc> smartCropUserDoc);

    UserDocDTO getEmployeeDocumentByIdAndDocumentType(Long employeeId, String documentType);

    UserDocDTO getFarmerDocumentByIdAndDocumentType(Long employeeId, String documentType);
}
