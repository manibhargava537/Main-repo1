package com.smartcrop.controllers;


import com.poiji.bind.Poiji;
import com.poiji.option.PoijiOptions;
import com.smartcrop.entity.*;
import com.smartcrop.model.*;
import com.smartcrop.repository.*;
import com.smartcrop.service.CropDetailsService;
import com.smartcrop.service.FarmerService;
import com.smartcrop.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/farmer")
@Slf4j
public class FarmerController {
    private static Logger LOG = LogManager.getLogger(EmployeeController.class);
    @Autowired
    private FarmerService farmerService;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private CropDetailsService cropDetailsService;
    @Autowired
    private SoilTypeRepository soilTypeRepository;
    @Autowired
    private StateRepository stateRepository;
    @Autowired
    private DistrictOrCityRepository districtOrCityRepository;
    @Autowired
    private MandalRepository mandalRepository;
    @Autowired
    private VillageRepository villageRepository;

    private String responseView = "";
/*
    @PostMapping(path = "/farmerRegister")
    public String register(@ModelAttribute("formData") Farmer newFarmer,
                           Errors errors, Model model,
                           @RequestParam(name="next",required = false) String submitType,
                           @RequestParam(name="aadhar",required = false) MultipartFile aadhar,
                           @RequestParam(name="pancard",required = false) MultipartFile pancard,
                           @RequestParam(name="passbook",required = false) MultipartFile passbook,
                           @RequestParam(name="others",required = false) MultipartFile others) throws MessagingException, IOException {
        System.out.println(errors.getAllErrors());
        byte[] aadharData = aadhar.getBytes();
        byte[] passbookData = passbook.getBytes();
        byte[] pancardData = pancard.getBytes();
        byte[] othersData = others.getBytes();
        newFarmer.setAadharCard(aadharData);
        newFarmer.setPancardFile(pancardData);
        newFarmer.setPassbookFile(passbookData);
        newFarmer.setOtherFile(othersData);
        System.out.println(newFarmer);
        if (errors.hasErrors())
            responseView = "farmer-register";
        else {
            if(submitType!=null&&submitType.equals("Save")) {
                newFarmer.setSubmitStatus("DRAFT");
            }else{
                newFarmer.setSubmitStatus("SUBMITTED");
            }
            Farmer farmer = farmerService.registerFarmer(newFarmer);
            responseView = "farmer-register";
            model.addAttribute("formData", farmer);
        }
            //emailService.sendMail(employee.getEmail(), "Onboarding success", template);}
        return responseView;
    }*/

    @GetMapping(path = "/error")
    public String showError(Model model, Farmer farmer) {
        return "error";
    }

    @GetMapping(path = "/allFarmers")
    public String showListOfRegisteredFarmers(Model model) {
        model.addAttribute("farmers", farmerService.getAllRegisteredFarmers());
        return "farmers";
    }

    @GetMapping(path = "/{id}")
    public String showDetails(@PathVariable("id") Long id, Model model) {
        Optional<Farmer> farmerModel = farmerService.getById(id);
        if (farmerModel.isPresent()) {
            Farmer farmerObj = farmerModel.get();
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
        }
        return "";
    }

    @PostMapping(path = "/update/{id}")
    public String updateEmployeeDetails(
            @PathVariable("id") Long id,
            @ModelAttribute("registrationModel") RegistrationModel registrationModel,
            Errors errors, Model model) throws ParseException {
        Optional<Farmer> farmerModel = userService.updateFarmer(registrationModel, id);
        if (farmerModel.isPresent()) {
            Farmer farmerObj = farmerModel.get();
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
        }
        return "";
    }

    @GetMapping(path = "/registerFarmer")
    public String showFarmerRegistration(Model model) {
        FarmerModel farmerModel = FarmerModel.builder().build();
        model.addAttribute("farmerModel", farmerModel);
        model.addAttribute("countries", countryRepository.findAllByActive(true));
        model.addAttribute("crops", cropDetailsService.getAllActive(true));
        model.addAttribute("soilTypes", soilTypeRepository.findAllByActive(true));
        return "farmer-register";
    }

    @PostMapping(path = "/registerFarmer")
    public String registerFarmer(@Valid @ModelAttribute("farmerModel") FarmerModel farmerModel,
                                 //@RequestParam("fm_doc_photo") MultipartFile farmerProfileDoc,
                                 @RequestParam("ppb_doc") MultipartFile ppbDoc,
                                 @RequestParam("aadhar_doc") MultipartFile panCardDoc,
                                 @RequestParam("pancard_doc") MultipartFile aadharDoc,
                                 @RequestParam("passbook_doc") MultipartFile bankDetailDoc,
                                 @RequestParam("other_doc") MultipartFile otherDoc,
                                 Errors errors, Model model) {
        try {
            log.info("new farmer {}", farmerModel);

            if (errors.hasErrors()) {
                log.error("Errors {} ", errors.getAllErrors());
                model.addAttribute("errorMessage", errors.getAllErrors().get(0).getDefaultMessage());
                model.addAttribute("countries", countryRepository.findAllByActive(true));
            } else {
                List<UserDocDTO> userDocDTOList = new ArrayList<>();

                userDocDTOList.add(UserDocDTO.builder()
                        .docExtension(StringUtils.getFilenameExtension(panCardDoc.getOriginalFilename()))
                        .documentType(DocumentType.PAN_CARD.getValue())
                        .document(panCardDoc.getBytes())
                        .build());
                userDocDTOList.add(UserDocDTO.builder()
                        .docExtension(StringUtils.getFilenameExtension(ppbDoc.getOriginalFilename()))
                        .documentType(DocumentType.PPB.getValue())
                        .document(ppbDoc.getBytes())
                        .build());
                userDocDTOList.add(UserDocDTO.builder()
                        .docExtension(StringUtils.getFilenameExtension(otherDoc.getOriginalFilename()))
                        .documentType(DocumentType.OTHER.getValue())
                        .document(otherDoc.getBytes())
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


                userService.registerFarmer(farmerModel, userDocDTOList);
                model.addAttribute("userAdded", true);
                log.info("Saved farmer: {}", farmerModel);
            }
        } catch (Exception e) {
            model.addAttribute("farmerModel", farmerModel);
            model.addAttribute("countries", countryRepository.findAllByActive(true));
            model.addAttribute("crops", cropDetailsService.getAllActive(true));
            model.addAttribute("soilTypes", soilTypeRepository.findAllByActive(true));
            log.error("Exception occurred while registerUser, exception={}", e.getMessage());
        }
        return "farmer-register";
    }

    @PostMapping(path = "/bulk/upload")
    public ResponseEntity<List<FarmerModel>> bulkUploadFarmers(@RequestParam("file") MultipartFile file) throws IOException, ParseException {
        String fileExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        if (fileExtension.equals("xlsx")) {
            // Create a temporary file to store the uploaded content
            File tempFile = File.createTempFile("temp", ".xlsx");
            file.transferTo(tempFile);
            PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings().sheetIndex(0).build(); // Assuming the sheet index is 0
            List<FarmerModel> farmers = Poiji.fromExcel(tempFile, FarmerModel.class, options);

            // Now you have the list of farmer objects, and you can work with the data as needed
            for (FarmerModel farmer : farmers) {
                Country country = countryRepository.findByName(farmer.getProposedAreaCountry())
                        .orElseThrow(() -> new InputMismatchException("Country with name :" + farmer.getProposedAreaCountry()));

                farmer.setProposedAreaCountry(String.valueOf(country.getId()));

                State state = stateRepository.findByCountry_IdAndName(country.getId(), farmer.getProposedAreaState())
                        .orElseThrow(() -> new InputMismatchException("State with name :" + farmer.getProposedAreaState() + " not present"));

                farmer.setProposedAreaState(String.valueOf(state.getId()));

                District district = districtOrCityRepository.findByState_IdAndName(state.getId(), farmer.getProposedAreaDistrict())
                        .orElseThrow(() -> new InputMismatchException("District with name :" + farmer.getProposedAreaDistrict() + " not present"));

                farmer.setProposedAreaDistrict(String.valueOf(district.getId()));

                Mandal mandal = mandalRepository.findByDistrict_IdAndName(district.getId(), farmer.getProposedAreaMandal())
                        .orElseThrow(() -> new InputMismatchException("Mandal with name :" + farmer.getProposedAreaMandal() + " not present"));

                farmer.setProposedAreaMandal(String.valueOf(mandal.getId()));

                Village village = villageRepository.findByMandal_IdAndName(mandal.getId(), farmer.getProposedAreaVillage())
                        .orElseThrow(() -> new InputMismatchException("Village with name :" + farmer.getProposedAreaVillage() + " not present"));

                farmer.setProposedAreaVillage(String.valueOf(village.getId()));

                SoilType soilType = soilTypeRepository.findByName(farmer.getSoilType())
                        .orElseThrow(() -> new InputMismatchException("Soil type with name :" + farmer.getSoilType() + " not present"));

                farmer.setSoilType(String.valueOf(soilType.getId()));

                userService.registerFarmer(farmer, List.of());
            }
            return new ResponseEntity<>(farmers, HttpStatus.OK);
        }
        return new ResponseEntity("Please upload a xlsx file", HttpStatus.BAD_REQUEST);
    }
}