package com.smartcrop.controllers;

import com.smartcrop.entity.Employee;
import com.smartcrop.entity.SmartCropUser;
import com.smartcrop.model.RegistrationModel;
import com.smartcrop.repository.CountryRepository;
import com.smartcrop.service.EmployeeService;
import com.smartcrop.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/employee")
@Slf4j
public class EmployeeController {
    private @Autowired EmployeeService employeeService;
    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private UserService userService;

//    @GetMapping(path = "/register")
//    public String showEmployeeRegistration(Model model) {
//        RegistrationModel registrationModel = RegistrationModel.builder().build();
//        model.addAttribute("registrationModel", registrationModel);
//        model.addAttribute("userType", Role.values());
//        model.addAttribute("stateDistrictList", appCommonConfig.readStateDistrictFileAsModel().getStates());
//        return "registration";
//    }

//    @PostMapping(path = "/register")
//    public String registerEmployee(@Valid @ModelAttribute("registrationModel") RegistrationModel registrationModel, Errors errors, Model model) {
//        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//       log.error("Errors {} ",errors.getAllErrors());
//       log.info("new Employee {}", registrationModel);
//        if (errors.hasErrors()) {
//            model.addAttribute("errorMessage",errors.getAllErrors().get(0).getDefaultMessage());
//            model.addAttribute("stateDistrictList", appCommonConfig.readStateDistrictFileAsModel().getStates());
//            model.addAttribute("roles", Designation.values());
//        }
//        else {
//            Employee employee = employeeService.registerEmployee(registrationModel);
//            model.addAttribute("employeeAdded",true);
//            model.addAttribute("employeeId", employee.getId());
//            log.info("Saved Emp: {}", employee);
//        }
//        return "registration";
//
//    }

    @GetMapping(path = "/all")
    public String showListOfRegisteredEmployees(@RequestParam(defaultValue = "0") int pageStart,
                                                @RequestParam(defaultValue = "${employee.list.page.size:1000}") int pageSize,
                                                Model model) {
        Map<String, Object> response = employeeService.getAllRegisteredEmployees(pageStart, pageSize);
        model.addAttribute("employeesResponse", response);
        return "dashboard/employee-list";
    }

    @GetMapping(path = "/{id}")
    public String showEmployeeDetails(@PathVariable("id") Long id, Model model) {
        Optional<Employee> employeeModel = employeeService.findEmployeeById(id);
        if (employeeModel.isPresent()) {
            Employee employee = employeeModel.get();
            model.addAttribute("employee", employee);
            model.addAttribute("user", employee.getSmartCropUser());
            model.addAttribute("address", employee.getAddress());
            model.addAttribute("countries", countryRepository.findAllByActive(true));
            return "dashboard/single-employee";
        }
        return "";
    }

    @PostMapping(path = "/update/{id}")
    public String updateEmployeeDetails(
            @PathVariable Long id,
            @ModelAttribute("registrationModel") RegistrationModel registrationModel,
            Errors errors,
            Model model) {
        Optional<Employee> employeeModel = userService.updateEmployee(registrationModel, id);
        if (employeeModel.isPresent()) {
            Employee employeeObj = employeeModel.get();

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
            model.addAttribute("employee", employee);
            return "dashboard/single-employee";
        }
        return "";
    }
}