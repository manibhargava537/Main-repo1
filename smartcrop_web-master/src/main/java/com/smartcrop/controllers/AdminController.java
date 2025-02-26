package com.smartcrop.controllers;

import com.smartcrop.entity.SmartCropUser;
import com.smartcrop.model.*;
import com.smartcrop.repository.CountryRepository;
import com.smartcrop.repository.CropDetailsRepository;
import com.smartcrop.service.UserService;
import com.smartcrop.util.AppCommonConfig;
import com.smartcrop.util.PasswordGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class AdminController {

    @Autowired
    AppCommonConfig appCommonConfig;

    @Autowired
    UserService userService;

    @Autowired
    PasswordGenerator passwordGenerator;

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    CropDetailsRepository cropDetailsRepository;

    @GetMapping(path = "/register")
    public String showUserRegistration(Model model) {
        RegistrationModel registrationModel = RegistrationModel.builder().build();
        model.addAttribute("registrationModel", registrationModel);
        model.addAttribute("userType", UserType.values());
        model.addAttribute("countries", countryRepository.findAllByActive(true));
        model.addAttribute("crops", cropDetailsRepository.findAllByActive(true));
        return "registration";
    }

    @PostMapping(path = "/register")
    public String registerUser(@Valid @ModelAttribute("registrationModel") RegistrationModel registrationModel,
                               @RequestParam("emp_doc_photo") MultipartFile profileDoc,
                               @RequestParam("emp_doc_id") MultipartFile idDoc,
                               @RequestParam("fm_doc_photo") MultipartFile farmerProfileDoc,
                               @RequestParam("ppb_doc") MultipartFile ppbDoc,
                               @RequestParam("fm_doc_id") MultipartFile farmerIdDoc,
                               @RequestParam("aadhar_doc") MultipartFile aadharDoc,
                               @RequestParam("bank_detail") MultipartFile bankDetailDoc,
                               Errors errors, Model model) {
        try {
            //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            log.info("new User {}", registrationModel);

            if (errors.hasErrors()) {
                log.error("Errors {} ", errors.getAllErrors());
                model.addAttribute("errorMessage", errors.getAllErrors().get(0).getDefaultMessage());
                model.addAttribute("countries", countryRepository.findAllByActive(true));
                model.addAttribute("roles", Designation.values());
            } else {
                List<UserDocDTO> userDocDTOList = new ArrayList<>();
                if (registrationModel.getRole().equals("employee")) {
                    userDocDTOList.add(UserDocDTO.builder()
                            .docExtension(StringUtils.getFilenameExtension(profileDoc.getOriginalFilename()))
                            .documentType(DocumentType.PROFILE.getValue())
                            .description(registrationModel.getIdType())
                            .document(profileDoc.getBytes())
                            .build());
                    userDocDTOList.add(UserDocDTO.builder()
                            .docExtension(StringUtils.getFilenameExtension(idDoc.getOriginalFilename()))
                            .documentType(DocumentType.IDENTITY.getValue())
                            .document(idDoc.getBytes())
                            .build());
                } else if (registrationModel.getRole().equals("farmer")) {
                    userDocDTOList.add(UserDocDTO.builder()
                            .docExtension(StringUtils.getFilenameExtension(farmerProfileDoc.getOriginalFilename()))
                            .documentType(DocumentType.PROFILE.getValue())
                            .document(farmerProfileDoc.getBytes())
                            .description(registrationModel.getIdType())
                            .build());
                    userDocDTOList.add(UserDocDTO.builder()
                            .docExtension(StringUtils.getFilenameExtension(ppbDoc.getOriginalFilename()))
                            .documentType(DocumentType.PPB.getValue())
                            .document(ppbDoc.getBytes())
                            .build());
                    userDocDTOList.add(UserDocDTO.builder()
                            .docExtension(StringUtils.getFilenameExtension(farmerIdDoc.getOriginalFilename()))
                            .documentType(DocumentType.IDENTITY.getValue())
                            .document(farmerIdDoc.getBytes())
                            .build());
                    userDocDTOList.add(UserDocDTO.builder()
                            .docExtension(StringUtils.getFilenameExtension(aadharDoc.getOriginalFilename()))
                            .documentType(DocumentType.AADHAR.getValue())
                            .document(aadharDoc.getBytes())
                            .build());
                    userDocDTOList.add(UserDocDTO.builder()
                            .docExtension(StringUtils.getFilenameExtension(bankDetailDoc.getOriginalFilename()))
                            .documentType(DocumentType.BANK_DETAILS.getValue())
                            .document(bankDetailDoc.getBytes())
                            .build());
                }

                userService.registerUser(registrationModel, userDocDTOList);
                model.addAttribute("userAdded", true);
                model.addAttribute("userId", registrationModel.getUserId());
                log.info("Saved User: {}", registrationModel);
            }
        } catch (Exception e) {
            model.addAttribute("countries", countryRepository.findAllByActive(true));
            log.error("Exception occurred while registerUser, exception={}", e.getMessage());
        }
        return "registration";
    }

    @PostConstruct
    private void postConstruct() {
        //create a sequence table
        //  userService.createSequencTable();
        SmartCropUser existingUser = userService.getAdminUser();
        if (null == existingUser) {
            SmartCropUser smartCropUser = new SmartCropUser();
            smartCropUser.setUserName("admin");
            smartCropUser.setPassword(passwordGenerator.getBCryptPasswordEncoder("password"));
            smartCropUser.setEmail("smartcropp@gmail.com");
            smartCropUser.setUserId("1234");
            smartCropUser.setFirstName("Harish");
            smartCropUser.setLastName("Karanam");
            smartCropUser.setGender("Male");
            smartCropUser.setDefaultPassword("password");
            smartCropUser.setCountryCode("+91");
            smartCropUser.setPhoneNumber(123123123);
            smartCropUser.setRole(UserRole.ROLE_ADMIN.name());
            userService.saveAdminUser(smartCropUser);
            log.info("***********Admin user email= {}", "admin@dev.com");
        } else {
            log.info("***********Existing Admin user email= {}", existingUser.getEmail());
        }

    }
}
