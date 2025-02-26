package com.smartcrop.service;

import com.smartcrop.entity.Employee;
import com.smartcrop.entity.Farmer;
import com.smartcrop.entity.SmartCropUser;
import com.smartcrop.model.FarmerModel;
import com.smartcrop.model.RegistrationModel;
import com.smartcrop.model.UserDocDTO;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface UserService {
    void registerUser(RegistrationModel userModel, List<UserDocDTO> userDocDTOList) throws ParseException;

    void registerFarmer(FarmerModel farmerModel, List<UserDocDTO> userDocDTOList) throws ParseException;

    SmartCropUser saveAdminUser(SmartCropUser smartCropUser);

    SmartCropUser getAdminUser();

    void updateUserStatus(String status, SmartCropUser smartCropUser, String remark);

    SmartCropUser isValidUser(String email);

    List<RegistrationModel> getAllRegisteredUsers();

    Optional<Employee> updateEmployee(RegistrationModel registrationModel, long id);

    Optional<Farmer> updateFarmer(RegistrationModel registrationModel, long id) throws ParseException;
}
