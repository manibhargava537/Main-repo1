package com.smartcrop.service;

import com.smartcrop.entity.Address;
import com.smartcrop.entity.Employee;
import com.smartcrop.entity.SmartCropUser;
import com.smartcrop.model.Designation;
import com.smartcrop.model.RegistrationModel;
import com.smartcrop.repository.AddressRepository;
import com.smartcrop.repository.EmployeeRepository;
import com.smartcrop.repository.SmartCropUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.*;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private @Autowired EmployeeRepository employeeRepository;
    private @Autowired SmartCropUserRepository userRepository;
    private @Autowired AddressRepository addressRepository;

    private @Autowired EmailTokenService confirmEmailTokenService;

    @Autowired
    EmailService emailService;

    @Value("${spring.application.siteURL}")
    private String siteURL;

    public Employee findEmployeeByMail(String email) {
        Employee emp = employeeRepository.findBySmartCropUser_Email(email);
        return emp;
    }

    @Override
    public Map<String, Object> getAllRegisteredEmployees(int pageStart, int pageSize) {
        Pageable paging = PageRequest.of(pageStart, pageSize);
        Page<Employee> employeePages = employeeRepository.findAll(paging);
        List<RegistrationModel> employeeModelList = new ArrayList<>();
        List<Employee> employeeList = employeePages.getContent();
        if (employeeList != null && !employeeList.isEmpty()) {
            employeeList.forEach(employee -> {
                SmartCropUser smartCropUser = employee.getSmartCropUser();
                RegistrationModel employeeModel = RegistrationModel.builder()
                        .entityId(employee.getId())
                        .fullName(smartCropUser.getFirstName() + " " + smartCropUser.getLastName())
                        .email(smartCropUser.getEmail())
                        .dateOfBirth(smartCropUser.getDateOfBirth().toString())
                        .districtOrCity(employee.getAddress().getDistrictOrCity())
                        .phoneNumber(smartCropUser.getPhoneNumber())
                        .status(smartCropUser.getStatus())
                        .build();
                employeeModelList.add(employeeModel);
            });
        }
        Map<String, Object> response = new HashMap<>();
        response.put("totalItems", employeePages.getTotalElements());
        response.put("totalPages", employeePages.getTotalPages());
        response.put("employeeList", employeeModelList);
        log.info("getAllRegisteredEmployees done!!");
        return response;
    }

    @Override
    public Optional<Employee> findEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public Employee saveEmployee(RegistrationModel userModel, Address addressEntity, SmartCropUser smartCropUser) {
        Employee newEmployee = Employee.builder()
                .address(addressEntity)
                .smartCropUser(smartCropUser)
                .designation(StringUtils.isEmpty(userModel.getDesignation()) ? Designation.AREA_MANAGER.name() : userModel.getDesignation())
                .build();
        return employeeRepository.save(newEmployee);
    }

    @Override
    public List<Employee> getAllEmployeesPendingForApproval() {
        return employeeRepository.getAllEmployeesPendingForApproval();
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }
}