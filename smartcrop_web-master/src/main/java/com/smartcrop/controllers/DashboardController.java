package com.smartcrop.controllers;


import com.smartcrop.entity.Employee;
import com.smartcrop.entity.Farmer;
import com.smartcrop.entity.SmartCropUser;
import com.smartcrop.model.RegistrationModel;
import com.smartcrop.model.UserDocDTO;
import com.smartcrop.repository.CountryRepository;
import com.smartcrop.repository.SoilTypeRepository;
import com.smartcrop.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
@RequestMapping("/dashboard")
@Slf4j
public class DashboardController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private UserService userService;
    @Autowired
    private FarmerService farmerService;
    @Autowired
    private UserDocumentService userDocumentService;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CropDetailsService cropDetailsService;

    @Autowired
    private SoilTypeRepository soilTypeRepository;

    @GetMapping(path = "/user/all")
    public String showListOfRegisteredUsers(@RequestParam(defaultValue = "0") int pageStart,
                                            @RequestParam(defaultValue = "${employee.list.page.size:1000}") int pageSize,
                                            Model model) {
        List<RegistrationModel> response = userService.getAllRegisteredUsers();
        model.addAttribute("employeesResponse", response);
        return "dashboard/registered-users";
    }

    @GetMapping(path = "/role_employee/{id}")
    public String getEmployeeById(@PathVariable Long id, Model model) {

        Optional<Employee> response = employeeService.findEmployeeById(id);
        if (response.isPresent()) {
            Employee employeeObj = response.get();
            SmartCropUser smartCropUser = employeeObj.getSmartCropUser();
            RegistrationModel employee = RegistrationModel.builder()
                    .title(smartCropUser.getTitle())
                    .firstName(smartCropUser.getFirstName())
                    .lastName(smartCropUser.getLastName())
                    .email(smartCropUser.getEmail())
                    .phoneNumber(smartCropUser.getPhoneNumber())
                    .designation(employeeObj.getDesignation())
                    .dateOfBirth(smartCropUser.getDateOfBirth().toString())
                    .gender(smartCropUser.getGender())
                    .country(employeeObj.getAddress().getCountry())
                    .state(employeeObj.getAddress().getState())
                    .districtOrCity(employeeObj.getAddress().getDistrictOrCity())
                    .zipCode(employeeObj.getAddress().getZipCode())
                    .status(smartCropUser.getStatus())
                    .role("employee")
                    .build();
            model.addAttribute("user", employee);
            model.addAttribute("userEntityId", id);

            return "dashboard/registered-user-profile";
        } else {
            return "error";
        }
    }

    @GetMapping(path = "single/employee/{id}")
    public String getEmployeeDetailsById(@PathVariable Long id, Model model) {
        Optional<Employee> response = employeeService.findEmployeeById(id);
        if (response.isPresent()) {
            Employee employeeObj = response.get();
            SmartCropUser smartCropUser = employeeObj.getSmartCropUser();
            RegistrationModel employee = RegistrationModel.builder()
                    .title(smartCropUser.getTitle())
                    .firstName(smartCropUser.getFirstName())
                    .lastName(smartCropUser.getLastName())
                    .email(smartCropUser.getEmail())
                    .phoneNumber(smartCropUser.getPhoneNumber())
                    .designation(employeeObj.getDesignation())
                    .dateOfBirth(smartCropUser.getDateOfBirth().toString())
                    .gender(smartCropUser.getGender())
                    .country(employeeObj.getAddress().getCountry())
                    .state(employeeObj.getAddress().getState())
                    .districtOrCity(employeeObj.getAddress().getDistrictOrCity())
                    .zipCode(employeeObj.getAddress().getZipCode())
                    .status(smartCropUser.getStatus())
                    .role("employee")
                    .build();
            model.addAttribute("employee", employee);
            model.addAttribute("employeeEntityId", id);
            model.addAttribute("registrationModel", employee);
            model.addAttribute("countries", countryRepository.findAllByActive(true));
            return "dashboard/single-employee";
        } else {
            return "error";
        }
    }

    @GetMapping(path = "/role_farmer/{id}")
    public String getFarmerById(@PathVariable Long id, Model model) {

        Optional<Farmer> response = farmerService.getById(id);
        if (response.isPresent()) {
            Farmer farmerObj = response.get();
            SmartCropUser smartCropUser = farmerObj.getSmartCropUser();
            RegistrationModel employee = RegistrationModel.builder()
                    .entityId(smartCropUser.getId())
                    .title(smartCropUser.getTitle())
                    .firstName(smartCropUser.getFirstName())
                    .lastName(smartCropUser.getLastName())
                    .email(smartCropUser.getEmail())
                    .phoneNumber(smartCropUser.getPhoneNumber())
                    .dateOfBirth(smartCropUser.getDateOfBirth().toString())
                    .gender(smartCropUser.getGender())
                    .country(farmerObj.getAddress().getCountry())
                    .state(farmerObj.getAddress().getState())
                    .districtOrCity(farmerObj.getAddress().getDistrictOrCity())
                    .zipCode(farmerObj.getAddress().getZipCode())
                    .status(smartCropUser.getStatus())
                    .role("farmer")
                    .build();
            model.addAttribute("user", employee);
            model.addAttribute("userEntityId", id);

            return "dashboard/registered-user-profile";
        } else {
            return "error";
        }
    }

    @GetMapping(path = "single/farmer/{id}")
    public String getFarmerDetailsById(@PathVariable Long id, Model model) {

        Optional<Farmer> response = farmerService.getById(id);
        if (response.isPresent()) {
            Farmer farmerObj = response.get();
            SmartCropUser smartCropUser = farmerObj.getSmartCropUser();
            RegistrationModel farmer = RegistrationModel.builder()
                    .entityId(smartCropUser.getId())
                    .title(smartCropUser.getTitle())
                    .firstName(smartCropUser.getFirstName())
                    .lastName(smartCropUser.getLastName())
                    .email(smartCropUser.getEmail())
                    .phoneNumber(smartCropUser.getPhoneNumber())
                    .dateOfBirth(smartCropUser.getDateOfBirth().toString())
                    .gender(smartCropUser.getGender())
                    .country(farmerObj.getAddress().getCountry())
                    .state(farmerObj.getAddress().getState())
                    .districtOrCity(farmerObj.getAddress().getDistrictOrCity())
                    .zipCode(farmerObj.getAddress().getZipCode())
                    .status(smartCropUser.getStatus())
                    .mandal(farmerObj.getAddress().getMandal())
                    .village(farmerObj.getAddress().getVillage())
                    .bankName(farmerObj.getBankDetails().getBankName())
                    .accountNumber(farmerObj.getBankDetails().getAccountNumber())
                    .branchName(farmerObj.getBankDetails().getBranchName())
                    .ifscCode(farmerObj.getBankDetails().getIfscCode())
                    .surveyNos(farmerObj.getCurrentCrop().getSurveyNos())
                    .currentCrop(farmerObj.getCurrentCrop().getCropType())
                    .totalLandHolding(farmerObj.getCurrentCrop().getTotalLandHolding())
                    .netIncome(farmerObj.getCurrentCrop().getNetIncome())
                    .location(farmerObj.getCurrentCrop().getLocation())
                    .waterSource(farmerObj.getCurrentCrop().getWaterSource())
                    .normalDischarge(farmerObj.getCurrentCrop().getRegularDischarge())
                    .summerDischarge(farmerObj.getCurrentCrop().getSummerDischarge())
                    .plantationType(farmerObj.getProposedArea().getPlantationType())
                    .proposedArea(farmerObj.getProposedArea().getProposedArea())
                    .proposedAreaSurveyNos(farmerObj.getProposedArea().getSurveyNos())
                    .totalPlants(farmerObj.getProposedArea().getTotalPlants())
                    .numberOfPlantsPerAcre(farmerObj.getProposedArea().getPlantsPerAcre())
                    .role("farmer")
                    .build();
            model.addAttribute("farmer", farmer);
            model.addAttribute("farmerEntityId", id);
            model.addAttribute("registrationModel", farmer);
            model.addAttribute("countries", countryRepository.findAllByActive(true));
            model.addAttribute("crops", cropDetailsService.getAllActive(true));
            model.addAttribute("soilTypes", soilTypeRepository.findAllByActive(true));
            return "dashboard/single-farmer";
        } else {
            return "error";
        }
    }

    @PutMapping(value = "/role_employee/{id}")
    @ResponseBody
    public Map modifyEmployeeStatus(@PathVariable("id") Long id, @RequestBody Map<String, Object> formData) {
        Map<String, Object> responseMap = new HashMap<>();
        String status = String.valueOf(formData.get("status"));
        String remark = String.valueOf(formData.get("remark"));
        Optional<Employee> employeeModel = employeeService.findEmployeeById(id);
        if (employeeModel.isPresent() && employeeModel.get().getSmartCropUser() != null) {
            userService.updateUserStatus(status, employeeModel.get().getSmartCropUser(), remark);
            responseMap.put("status", "success");
            log.info("Employee status has been modified and an email is sent..");
        }
        return responseMap;
    }

    @PutMapping(value = "/role_farmer/{id}")
    @ResponseBody
    public Map modifyFarmerStatus(@PathVariable("id") Long id, @RequestBody Map<String, Object> formData) {
        Map<String, Object> responseMap = new HashMap<>();
        String status = String.valueOf(formData.get("status"));
        String remark = String.valueOf(formData.get("remark"));
        Optional<Farmer> farmerModel = farmerService.getById(id);
        if (farmerModel.isPresent() && farmerModel.get().getSmartCropUser() != null) {
            userService.updateUserStatus(status, farmerModel.get().getSmartCropUser(), remark);
            responseMap.put("status", "success");
            log.info("Employee status has been modified and an email is sent..");
        }
        return responseMap;
    }

    @GetMapping(path = "/employee/all")
    public String getAllEmployees(Model model) {

        List<Employee> employeeList = employeeService.findAll();

        List<RegistrationModel> registrationModelList = new ArrayList<>();
        if (employeeList != null && !employeeList.isEmpty()) {
            employeeList.forEach(employee -> {
                SmartCropUser smartCropUser = employee.getSmartCropUser();
                RegistrationModel employeeModel = RegistrationModel.builder()
                        .employeeId(smartCropUser.getUserId())
                        .entityId(employee.getId())
                        .fullName(smartCropUser.getFirstName() + " " + smartCropUser.getLastName())
                        .email(smartCropUser.getEmail())
                        .dateOfBirth(smartCropUser.getDateOfBirth().toString())
                        .districtOrCity(employee.getAddress().getDistrictOrCity())
                        .phoneNumber(smartCropUser.getPhoneNumber())
                        .status(smartCropUser.getStatus())
                        .onBoardDate(smartCropUser.getModifiedOn())
                        .role("employee")
                        .build();
                registrationModelList.add(employeeModel);
            });
        }
        model.addAttribute("employeeList", registrationModelList);
        return "dashboard/employee-list";
    }

    @GetMapping(path = "/farmer/all")
    public String getAllFarmers(Model model) {

        List<Farmer> farmerList = farmerService.getAllRegisteredFarmers();

        List<RegistrationModel> registrationModelList = new ArrayList<>();
        if (farmerList != null && !farmerList.isEmpty()) {
            farmerList.forEach(farmer -> {
                SmartCropUser smartCropUser = farmer.getSmartCropUser();
                RegistrationModel farmerModel = RegistrationModel.builder()
                        .entityId(farmer.getId())
                        .userId(smartCropUser.getUserId())
                        .fullName(smartCropUser.getFirstName() + " " + smartCropUser.getLastName())
                        .email(smartCropUser.getEmail())
                        .dateOfBirth(smartCropUser.getDateOfBirth().toString())
                        .districtOrCity(farmer.getAddress().getDistrictOrCity())
                        .mandal(farmer.getAddress().getMandal())
                        .status(smartCropUser.getStatus())
                        .build();
                registrationModelList.add(farmerModel);
            });
        }
        model.addAttribute("farmerList", registrationModelList);
        return "dashboard/farmers-list";
    }


    @GetMapping(path = "/employee/{id}/{documentType}")
    @ResponseBody
    public UserDocDTO getEmployeeDetailsById(@PathVariable Long id, @PathVariable String documentType, Model model) {
        UserDocDTO userDocDTO = userDocumentService.getEmployeeDocumentByIdAndDocumentType(id, documentType);
        return userDocDTO;
    }


    @GetMapping(path = "/farmer/{id}/{documentType}")
    @ResponseBody
    public UserDocDTO getFarmerDocumentsById(@PathVariable Long id, @PathVariable String documentType, Model model) {
        UserDocDTO userDocDTO = userDocumentService.getFarmerDocumentByIdAndDocumentType(id, documentType);
        return userDocDTO;
    }
}
