package com.smartcrop.service;

import com.smartcrop.entity.Address;
import com.smartcrop.entity.Employee;
import com.smartcrop.entity.SmartCropUser;
import com.smartcrop.model.RegistrationModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public interface EmployeeService {
//    Employee registerEmployee(RegistrationModel model);

    Employee findEmployeeByMail(String email);

    Map<String, Object> getAllRegisteredEmployees(int pageStart, int pageSize);

    Optional<Employee> findEmployeeById(Long id);

    Employee saveEmployee(RegistrationModel userModel, Address addressEntity, SmartCropUser smartCropUser);

    List<Employee> getAllEmployeesPendingForApproval();

    List<Employee> findAll();

}