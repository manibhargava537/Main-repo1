package com.smartcrop.service;

import com.smartcrop.entity.*;
import com.smartcrop.model.*;
import com.smartcrop.repository.*;
import com.smartcrop.util.PasswordGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private EmailTokenRepository emailTokenRepository;
    private @Autowired EmployeeService employeeService;
    private @Autowired SmartCropUserRepository userRepository;
    private @Autowired AddressRepository addressRepository;

    private @Autowired EmailTokenService confirmEmailTokenService;
    @Autowired
    private EmailService emailService;

    @Autowired
    private FarmerService farmerService;

    @Autowired
    private CurrentCropService currentCropService;

    @Autowired
    private ProposedAreaService proposedAreaService;

    @Autowired
    private BankDetailsService bankDetailsService;
    @Autowired
    private UserDocumentService userDocumentService;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private StateRepository stateRepository;
    @Autowired
    private DistrictOrCityRepository districtOrCityRepository;
    @Autowired
    private MandalRepository mandalRepository;
    @Autowired
    private VillageRepository villageRepository;

    @Value("${com.smart.crop.admin.email}")
    private String adminEmail;


    @Override
    public void registerUser(RegistrationModel userModel, List<UserDocDTO> userDocDTOList) throws ParseException {
        Address address = buildAddressEntity(userModel);
        Address addressEntity = addressRepository.save(address);

        SmartCropUser user = buildUserEntity(userModel);
        SmartCropUser smartCropUser = userRepository.save(user);
        Employee employee = null;
        Farmer farmer = null;
        //employee  = employeeService.saveEmployee(userModel, addressEntity, smartCropUser);
        switch (UserType.valueOf(userModel.getRole().toUpperCase())) {
            case EMPLOYEE:
                employee = employeeService.saveEmployee(userModel, addressEntity, smartCropUser);
                if (null != userDocDTOList && !userDocDTOList.isEmpty()) {
                    Employee finalEmployee = employee;
                    List<SmartCropUserDoc> smartCropUserDocList = userDocDTOList.stream().map(entry ->
                            SmartCropUserDoc.builder()
                                    .documentType(entry.getDocumentType())
                                    .document(entry.getDocument())
                                    .docExtension(entry.getDocExtension())
                                    .documentDescription(entry.getDescription())
                                    .employee(finalEmployee)
                                    .build()
                    ).collect(Collectors.toList());
                    if (smartCropUserDocList != null && !smartCropUserDocList.isEmpty()) {
                        userDocumentService.saveAllUserDocument(smartCropUserDocList);
                        log.info("Employee documents saved successfully!!");
                    }
                }
                log.info("Employee record saved successfully!!");
                break;
            case FARMER:
                CurrentCrop currentCrop = buildCurrentCrop(userModel);
                currentCropService.save(currentCrop);
                ProposedArea proposedArea = buildProposedArea(userModel);
                proposedAreaService.save(proposedArea);
                BankDetails bankDetails = buildBankDetails(userModel);
                bankDetailsService.save(bankDetails);

                //first save all other details
                farmer = farmerService.registerFarmer(
                        Farmer.builder()
                                .address(addressEntity)
                                .currentCrop(currentCrop)
                                .proposedArea(proposedArea)
                                .bankDetails(bankDetails)
                                .smartCropUser(smartCropUser)
                                .build()
                );

                if (null != userDocDTOList && !userDocDTOList.isEmpty()) {
                    Farmer finalFarmer = farmer;
                    List<SmartCropUserDoc> smartCropUserDocList = userDocDTOList.stream().map(entry ->
                            SmartCropUserDoc.builder()
                                    .documentType(entry.getDocumentType())
                                    .document(entry.getDocument())
                                    .docExtension(entry.getDocExtension())
                                    .documentDescription(entry.getDescription())
                                    .farmer(finalFarmer)
                                    .build()
                    ).collect(Collectors.toList());

                    if (smartCropUserDocList != null && !smartCropUserDocList.isEmpty()) {
                        userDocumentService.saveAllUserDocument(smartCropUserDocList);
                        log.info("farmer documents saved successfully!!");
                    }
                }
                break;
            case NURSERY:
                log.info("NURSERY registration yet to be implemented!!");
                break;
            default:
                log.info("Default/Other registration yet to be implemented!!");
                break;
        }

        try {
//            Map<String, Object> templateModel = confirmEmailTokenService.saveAndGetConfirmEmailToken(userEntity);
//            emailService.sendVerificationMail(userModel.getEmail(), "Test Subject", templateModel);
            //send mail to registered user informing success
            Map<String, Object> templateModel = new HashMap<>();
            templateModel.put("recipientName", userModel.getFirstName() + " " + userModel.getLastName());
            emailService.sendAcknowledgementMail(userModel.getEmail(), templateModel);

            if (employee != null || farmer != null) {
                //send mail to admin for approval
                Map<String, Object> tempModel = new HashMap<>();
                tempModel.put("recipientName", "Admin");
                tempModel.put("email", userModel.getEmail());
                tempModel.put("userName", userModel.getFirstName() + " " + userModel.getLastName());
                if (employee != null) {
                    tempModel.put("userProfileLink", confirmEmailTokenService.generateUserProfileLink(employee.getId(), "employee"));
                } else if (farmer != null) {
                    tempModel.put("userProfileLink", confirmEmailTokenService.generateUserProfileLink(farmer.getId(), "farmer"));
                }
                emailService.sendApprovalMailToAdmin(adminEmail, tempModel);
            }


        } catch (MessagingException e) {
            log.error("Error while sending email..", e);
        }
    }

    @Override
    public void registerFarmer(FarmerModel farmerModel, List<UserDocDTO> userDocDTOList) throws ParseException {
        Address address = buildAddressEntityForFarmer(farmerModel);
        Address addressEntity = addressRepository.save(address);

        SmartCropUser user = buildUserEntityForFarmer(farmerModel);
        SmartCropUser smartCropUser = userRepository.save(user);
        CurrentCrop currentCrop = buildCurrentCropForFarmer(farmerModel);
        currentCropService.save(currentCrop);
        ProposedArea proposedArea = buildProposedAreaForFarmer(farmerModel);
        proposedAreaService.save(proposedArea);
        BankDetails bankDetails = buildBankDetailsForFarmer(farmerModel);
        if (bankDetails != null) {
            bankDetailsService.save(bankDetails);
        }

        //first save all other details
        Farmer farmer = farmerService.registerFarmer(
                Farmer.builder()
                        .aadharNumber(farmerModel.getAadharNumber())
                        .ppbNo(farmerModel.getPpbNo())
                        .careTaker(farmerModel.getCareTaker())
                        .caste(farmerModel.getCaste())
                        .address(addressEntity)
                        .currentCrop(currentCrop)
                        .proposedArea(proposedArea)
                        .bankDetails(bankDetails)
                        .smartCropUser(smartCropUser)
                        .build()
        );

        if (null != userDocDTOList && !userDocDTOList.isEmpty()) {
            Farmer finalFarmer = farmer;
            List<SmartCropUserDoc> smartCropUserDocList = userDocDTOList.stream().map(entry ->
                    SmartCropUserDoc.builder()
                            .documentType(entry.getDocumentType())
                            .document(entry.getDocument())
                            .docExtension(entry.getDocExtension())
                            .farmer(finalFarmer)
                            .build()
            ).collect(Collectors.toList());

            if (smartCropUserDocList != null && !smartCropUserDocList.isEmpty()) {
                userDocumentService.saveAllUserDocument(smartCropUserDocList);
                log.info("farmer documents saved successfully!!");
            }
        }

    }

    @Override
    public SmartCropUser saveAdminUser(SmartCropUser smartCropUser) {
        return userRepository.save(smartCropUser);
    }

    @Override
    public SmartCropUser getAdminUser() {
        SmartCropUser smartCropUser = userRepository.findTopByRole(UserRole.ROLE_ADMIN.name());
        return smartCropUser;
    }

    private SmartCropUser buildUserEntity(RegistrationModel userModel) throws ParseException {
        return SmartCropUser.builder().userId(generateUserId(userModel))
                .defaultPassword(PasswordGenerator.generateRandomPassword(PasswordGenerator.PASSWORD_LENGTH, PasswordGenerator.PASSWORD_CHARS))
                .email(userModel.getEmail())
                .gender(userModel.getGender())
                .title(userModel.getTitle())
                .firstName(userModel.getFirstName())
                .lastName(userModel.getLastName())
                .createOn(new Date())
                .modifiedOn(new Date())
                .countryCode(userModel.getCountryCode())
                .phoneNumber(userModel.getPhoneNumber())
                .role(userModel.getRole().equalsIgnoreCase("farmer") ? UserRole.ROLE_FARMER.name() : UserRole.ROLE_EMPLOYEE.name())
                .status(UserStatus.PENDING.getStatus())
                .dateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse(userModel.getDateOfBirth()))
                .build();
    }


    private SmartCropUser buildUserEntityForFarmer(FarmerModel farmerModel) throws ParseException {
        return SmartCropUser.builder().userId(generateUserIdForFarmer(farmerModel))
                .defaultPassword(PasswordGenerator.generateRandomPassword(PasswordGenerator.PASSWORD_LENGTH, PasswordGenerator.PASSWORD_CHARS))
                .email(farmerModel.getEmail())
                .gender(farmerModel.getGender())
                .title(farmerModel.getTitle())
                .firstName(farmerModel.getFirstName())
                .lastName(farmerModel.getLastName())
                .middleName(farmerModel.getMiddleName())
                .createOn(new Date())
                .modifiedOn(new Date())
                .countryCode("+91")
                .phoneNumber(farmerModel.getPhoneNumber())
                .role(UserRole.ROLE_FARMER.name())
                .status(UserStatus.PENDING.getStatus())
                .dateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse(farmerModel.getDateOfBirth()))
                .build();
    }

    private String generateUserIdForFarmer(FarmerModel model) {
        StringBuilder userId = new StringBuilder();
        userId.append("FAR");
        Optional<Country> country = countryRepository.findById(Long.valueOf(model.getProposedAreaCountry()));
        Optional<State> state = stateRepository.findById(Long.valueOf(model.getProposedAreaState()));
        Optional<District> district = districtOrCityRepository.findById(Long.valueOf(model.getProposedAreaDistrict()));

        //append 2 chars Country
        userId.append(country.isPresent() ? country.get().getShortName() : "");
        userId.append(state.isPresent() ? state.get().getShortName() : "");
        userId.append(district.isPresent() ? district.get().getShortName() : "");

        //append 4 digit number;
        //Long nextId = userRepository.getNextSeriesId();
        Random random = new Random();
        String seq = String.format("%04d", random.nextInt(10000));
        userId.append("_" + seq);

        return userId.toString();
    }

    private String generateUserId(RegistrationModel model) {
        StringBuilder userId = new StringBuilder();
        if (model.getRole().equalsIgnoreCase("Employee")) {
            //append EMP to userId
            userId.append("EMP");
        } else if (model.getRole().equalsIgnoreCase("Farmer")) {
            //append FAR to userId
            userId.append("FAR");
        } else if (model.getRole().equalsIgnoreCase("Nursery")) {
            //append NUR to userId
            userId.append("NUR");
        } else {
            //append OTH to userId
            userId.append("OTH");
        }

        Optional<Country> country = countryRepository.findById(Long.valueOf(model.getCountry()));
        Optional<State> state = stateRepository.findById(Long.valueOf(model.getState()));
        Optional<District> district = districtOrCityRepository.findById(Long.valueOf(model.getDistrictOrCity()));

        //append 2 chars Country
        userId.append(country.isPresent() ? country.get().getShortName() : "");
        userId.append(state.isPresent() ? state.get().getShortName() : "");
        userId.append(district.isPresent() ? district.get().getShortName() : "");

        //append 4 digit number;
        //Long nextId = userRepository.getNextSeriesId();
        Random random = new Random();
        String seq = String.format("%04d", random.nextInt(10000));
        userId.append("_" + seq);
        return userId.toString();
    }

    private Address buildAddressEntity(RegistrationModel userModel) {
        Optional<Country> country = countryRepository.findById(Long.valueOf(userModel.getCountry()));
        Optional<State> state = stateRepository.findById(Long.valueOf(userModel.getState()));
        Optional<District> district = districtOrCityRepository.findById(Long.valueOf(userModel.getDistrictOrCity()));
        Optional<Mandal> mandal = Optional.empty();
        if (StringUtils.isNotEmpty(userModel.getMandal())) {
            mandal = mandalRepository.findById(Long.valueOf(userModel.getMandal()));
        }
        Optional<Village> village = Optional.empty();
        if (StringUtils.isNotEmpty(userModel.getVillage())) {
            village = villageRepository.findById(Long.valueOf(userModel.getVillage()));
        }
        return Address.builder()
                .country(country.isPresent() ? country.get().getName() : "")
                .state(state.isPresent() ? state.get().getName() : "")
                .districtOrCity(district.isPresent() ? district.get().getName() : "")
                .mandal(mandal.isPresent() ? mandal.get().getName() : "")
                .village(village.isPresent() ? village.get().getName() : "")
                .zipCode(userModel.getZipCode())
                .build();
    }

    private Address buildAddressEntityForFarmer(FarmerModel farmerModel) {
        Optional<Country> country = countryRepository.findById(Long.valueOf(farmerModel.getProposedAreaCountry()));
        Optional<State> state = stateRepository.findById(Long.valueOf(farmerModel.getProposedAreaState()));
        Optional<District> district = districtOrCityRepository.findById(Long.valueOf(farmerModel.getProposedAreaDistrict()));
        Optional<Mandal> mandal = Optional.empty();
        if (StringUtils.isNotEmpty(farmerModel.getProposedAreaMandal())) {
            mandal = mandalRepository.findById(Long.valueOf(farmerModel.getProposedAreaMandal()));
        }
        Optional<Village> village = Optional.empty();
        if (StringUtils.isNotEmpty(farmerModel.getProposedAreaVillage())) {
            village = villageRepository.findById(Long.valueOf(farmerModel.getProposedAreaVillage()));
        }
        return Address.builder()
                .country(country.isPresent() ? country.get().getName() : "")
                .state(state.isPresent() ? state.get().getName() : "")
                .districtOrCity(district.isPresent() ? district.get().getName() : "")
                .mandal(mandal.isPresent() ? mandal.get().getName() : "")
                .village(village.isPresent() ? village.get().getName() : "")
                .zipCode(farmerModel.getZipCode())
                .build();
    }

    private static CurrentCrop buildCurrentCrop(RegistrationModel userModel) {
        return CurrentCrop.builder()
                .cropType(userModel.getCurrentCrop())
                .surveyNos(userModel.getSurveyNos())
                .totalLandHolding(userModel.getTotalLandHolding())
                .netIncome(userModel.getNetIncome())
                .waterSource(userModel.getWaterSource())
                .regularDischarge(userModel.getNormalDischarge())
                .summerDischarge(userModel.getSummerDischarge())
                .location(userModel.getLocation())
                .build();
    }

    private static CurrentCrop buildCurrentCropForFarmer(FarmerModel farmerModel) {
        return CurrentCrop.builder()
                .cropType(farmerModel.getCurrentCrop())
                .surveyNos(farmerModel.getCurrentCropSurveyNos())
                .totalLandHolding(farmerModel.getTotalLandHoldingInAcres())
                .netIncome(farmerModel.getNetIncome())
                .waterSource(farmerModel.getWaterSource())
                .regularDischarge(farmerModel.getNormalDischarge())
                .summerDischarge(farmerModel.getSummerDischarge())
                .location(farmerModel.getLocation())
                .build();
    }

    private static ProposedArea buildProposedArea(RegistrationModel userModel) {
        return ProposedArea.builder()
                .surveyNos(userModel.getProposedAreaSurveyNos())
                .proposedArea(userModel.getProposedArea())
                .plantationType(userModel.getPlantationType())
                .plantsPerAcre(userModel.getNumberOfPlantsPerAcre())
                .totalPlants(userModel.getTotalPlants())
                .build();
    }

    private static ProposedArea buildProposedAreaForFarmer(FarmerModel farmerModel) {
        return ProposedArea.builder()
                .surveyNos(farmerModel.getProposedAreaSurveyNos())
                .proposedArea(farmerModel.getProposedAreaInAcres())
                .plantationType(farmerModel.getPlantationMethod())
                .plantsPerAcre(farmerModel.getPlantsPerAcre())
                .totalPlants(farmerModel.getTotalPlants())
                .soilTested(farmerModel.isSoilTested())
                .soilType(farmerModel.getSoilType())
                .build();
    }

    private static BankDetails buildBankDetails(RegistrationModel userModel) {
        return BankDetails.builder()
                .bankName(userModel.getBankName())
                .accountNumber(userModel.getAccountNumber())
                .branchName(userModel.getBranchName())
                .ifscCode(userModel.getIfscCode())
                .build();
    }

    private static BankDetails buildBankDetailsForFarmer(FarmerModel userModel) {
        if (userModel.getBankName() != null && userModel.getBranchName() != null) {
            return BankDetails.builder()
                    .bankName(userModel.getBankName())
                    .accountNumber(userModel.getAccountNumber())
                    .branchName(userModel.getBranchName())
                    .ifscCode(userModel.getIfscCode())
                    .build();
        }
        return null;
    }

    @Transactional
    public void updateUserStatus(String status, SmartCropUser smartCropUser, String remark) {
        try {
            if (smartCropUser != null) {
                if (status.equalsIgnoreCase(UserStatus.APPROVED.getStatus())) {
                    //update user status..
                    userRepository.updateUserStatus(status, smartCropUser.getId());
                    //send password gen email
                    Map<String, Object> templateModel = confirmEmailTokenService.saveAndGetConfirmEmailToken(smartCropUser);
                    emailService.sendVerificationMail(smartCropUser.getEmail(), templateModel);
                } else if (status.equalsIgnoreCase(UserStatus.REJECTED.getStatus())) {
                    //update user status..
                    userRepository.updateUserStatusWithRemark(status, remark, smartCropUser.getId());
                    //send password gen email
                    Map<String, Object> templateModel = new HashMap<>();
                    templateModel.put("status", "rejected");
                    templateModel.put("comment", remark);
                    emailService.sendRejectionEmailToUser(smartCropUser.getEmail(), templateModel);
                } else if (status.equalsIgnoreCase(UserStatus.REFER_BACK.getStatus())) {
                    //update user status..
                    userRepository.updateUserStatusWithRemark(status, remark, smartCropUser.getId());
                    //send password gen email
                    Map<String, Object> templateModel = new HashMap<>();
                    templateModel.put("status", "referred back");
                    templateModel.put("comment", remark);
                    emailService.sendRejectionEmailToUser(smartCropUser.getEmail(), templateModel);

                } else {
                    log.error("user-status is invalid");
                }

            } else {
                log.error("user record not found in DB!!");
                throw new RuntimeException("user record not found in DB!!");
            }
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred while updating user status!!");
        }

    }

    public SmartCropUser isValidUser(String email) {
        Optional<SmartCropUser> smartCropUser = userRepository.findByEmailAndStatusIgnoreCase(email, UserStatus.APPROVED.getStatus());
        return smartCropUser.orElse(null);
    }

    @Override
    public List<RegistrationModel> getAllRegisteredUsers() {
        List<Employee> employeeList = employeeService.getAllEmployeesPendingForApproval();
        List<RegistrationModel> registrationModelList = new ArrayList<>();
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
                        .role(smartCropUser.getRole())
                        .build();
                registrationModelList.add(employeeModel);
            });
        }

        List<Farmer> farmerList = farmerService.getAllFarmersPendingForApproval();
        if (farmerList != null && !farmerList.isEmpty()) {
            farmerList.forEach(farmer -> {
                SmartCropUser smartCropUser = farmer.getSmartCropUser();
                RegistrationModel employeeModel = RegistrationModel.builder()
                        .entityId(farmer.getId())
                        .fullName(smartCropUser.getFirstName() + " " + smartCropUser.getLastName())
                        .email(smartCropUser.getEmail())
                        .dateOfBirth(smartCropUser.getDateOfBirth().toString())
                        .districtOrCity(farmer.getAddress().getDistrictOrCity())
                        .phoneNumber(smartCropUser.getPhoneNumber())
                        .status(smartCropUser.getStatus())
                        .role(smartCropUser.getRole())
                        .build();
                registrationModelList.add(employeeModel);
            });
        }
        return registrationModelList;
    }

    @Override
    public Optional<Employee> updateEmployee(RegistrationModel registrationModel, long id) {
        Optional<Employee> employeeModel = employeeService.findEmployeeById(id);
        if (employeeModel.isPresent()) {
            Employee employeeObj = employeeModel.get();
            Address address = employeeObj.getAddress();

            Optional<Country> country = countryRepository.findById(Long.valueOf(registrationModel.getCountry()));
            Optional<State> state = stateRepository.findById(Long.valueOf(registrationModel.getState()));
            Optional<District> district = districtOrCityRepository.findById(Long.valueOf(registrationModel.getDistrictOrCity()));
            Optional<Mandal> mandal = Optional.empty();
            if (StringUtils.isNotEmpty(registrationModel.getMandal())) {
                mandal = mandalRepository.findById(Long.valueOf(registrationModel.getMandal()));
            }
            Optional<Village> village = Optional.empty();
            if (StringUtils.isNotEmpty(registrationModel.getVillage())) {
                village = villageRepository.findById(Long.valueOf(registrationModel.getVillage()));
            }
            if (country.isPresent()) {
                address.setCountry(country.get().getName());
            }
            if (state.isPresent()) {
                address.setState(state.get().getName());
            }
            if (district.isPresent()) {
                address.setDistrictOrCity(district.get().getName());
            }
            if (mandal.isPresent()) {
                address.setMandal(mandal.get().getName());
            }
            if (village.isPresent()) {
                address.setVillage(village.get().getName());
            }
            if (registrationModel.getZipCode() != null) {
                address.setZipCode(registrationModel.getZipCode());
            }
            Address updatedAddress = addressRepository.save(address);

            SmartCropUser smartCropUser = employeeObj.getSmartCropUser();
            smartCropUser.setCountryCode(registrationModel.getCountryCode());
            smartCropUser.setFirstName(registrationModel.getFirstName());
            smartCropUser.setMiddleName(registrationModel.getMiddleName());
            smartCropUser.setLastName(registrationModel.getLastName());
            smartCropUser.setGender(registrationModel.getGender());
            smartCropUser.setPhoneNumber(registrationModel.getPhoneNumber());
            smartCropUser.setEmail(registrationModel.getEmail());
            smartCropUser.setStatus(registrationModel.getStatus());

            SmartCropUser updatedUser = userRepository.save(smartCropUser);

            return Optional.of(employeeService.saveEmployee(registrationModel, updatedAddress, updatedUser));
        }
        return employeeModel;
    }

    @Override
    public Optional<Farmer> updateFarmer(RegistrationModel registrationModel, long id) throws ParseException {
        Optional<Farmer> farmerOptional = farmerService.getById(id);
        if (farmerOptional.isPresent()) {
            Farmer farmer = farmerOptional.get();

            //TODO: optimize later
            //validate and update details
            //update address
            Address address = farmer.getAddress();
            if (StringUtils.isNotEmpty(registrationModel.getCountry())) {
                Optional<Country> country = countryRepository.findById(Long.valueOf(registrationModel.getCountry()));
                if (country.isPresent()) {
                    address.setCountry(country.get().getName());
                }
            }
            if (StringUtils.isNotEmpty(registrationModel.getState())) {
                Optional<State> state = stateRepository.findById(Long.valueOf(registrationModel.getState()));
                if (state.isPresent()) {
                    address.setCountry(state.get().getName());
                }
            }
            if (StringUtils.isNotEmpty(registrationModel.getDistrictOrCity())) {
                Optional<District> district = districtOrCityRepository.findById(Long.valueOf(registrationModel.getDistrictOrCity()));
                if (district.isPresent()) {
                    address.setCountry(district.get().getName());
                }
            }
            if (StringUtils.isNotEmpty(registrationModel.getMandal())) {
                Optional<Mandal> mandal = mandalRepository.findById(Long.valueOf(registrationModel.getMandal()));
                if (mandal.isPresent()) {
                    address.setCountry(mandal.get().getName());
                }
            }

            if (StringUtils.isNotEmpty(registrationModel.getVillage())) {
                Optional<Village> village = villageRepository.findById(Long.valueOf(registrationModel.getVillage()));
                if (village.isPresent()) {
                    address.setCountry(village.get().getName());
                }
            }
            if (StringUtils.isNotEmpty(registrationModel.getZipCode())) {
                address.setZipCode(registrationModel.getZipCode());
            }
            addressRepository.save(address);

            SmartCropUser smartCropUser = farmer.getSmartCropUser();
            if (registrationModel.getFirstName() != null) {
                smartCropUser.setFirstName(registrationModel.getFirstName());
            }
            if (registrationModel.getMiddleName() != null) {
                smartCropUser.setMiddleName(registrationModel.getMiddleName());
            }
            if (registrationModel.getLastName() != null) {
                smartCropUser.setLastName(registrationModel.getLastName());
            }
            if (registrationModel.getEmail() != null) {
                smartCropUser.setEmail(registrationModel.getEmail());
            }
            if (registrationModel.getTitle() != null) {
                smartCropUser.setTitle(registrationModel.getTitle());
            }
            if (registrationModel.getPhoneNumber() > 0) {
                smartCropUser.setPhoneNumber(registrationModel.getPhoneNumber());
            }
            if (registrationModel.getStatus() != null) {
                smartCropUser.setStatus(registrationModel.getStatus());
            }
            if (registrationModel.getCountryCode() != null) {
                smartCropUser.setCountryCode(registrationModel.getCountryCode());
            }
            if (registrationModel.getDateOfBirth() != null) {
                smartCropUser.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse(registrationModel.getDateOfBirth()));
            }
            if (registrationModel.getGender() != null) {
                smartCropUser.setGender(registrationModel.getGender());
            }

            userRepository.save(smartCropUser);

            //update bank details

            BankDetails bankDetails = farmer.getBankDetails();
            if (bankDetails != null) {
                if (registrationModel.getBankName() != null) {
                    bankDetails.setBankName(registrationModel.getBankName());
                }
                if (registrationModel.getBranchName() != null) {
                    bankDetails.setBranchName(registrationModel.getBranchName());
                }
                if (registrationModel.getIfscCode() != null) {
                    bankDetails.setIfscCode(registrationModel.getIfscCode());
                }
                if (registrationModel.getAccountNumber() > 0) {
                    bankDetails.setAccountNumber(registrationModel.getAccountNumber());
                }
            } else {
                if (registrationModel.getBankName() != null && registrationModel.getBranchName() != null) {
                    bankDetails = buildBankDetails(registrationModel);
                }
            }

            bankDetailsService.save(bankDetails);

            CurrentCrop currentCrop = farmer.getCurrentCrop();
            if (registrationModel.getCurrentCrop() != null) {
                currentCrop.setCropType(registrationModel.getCurrentCrop());
            }

            if (registrationModel.getSurveyNos() != null) {
                currentCrop.setSurveyNos(registrationModel.getSurveyNos());
            }
            if (registrationModel.getLocation() != null) {
                currentCrop.setLocation(registrationModel.getLocation());
            }
            if (registrationModel.getNetIncome() > 0) {
                currentCrop.setNetIncome(registrationModel.getNetIncome());
            }
            if (registrationModel.getTotalLandHolding() > 0) {
                currentCrop.setTotalLandHolding(registrationModel.getTotalLandHolding());
            }
            if (registrationModel.getNormalDischarge() != null) {
                currentCrop.setRegularDischarge(registrationModel.getNormalDischarge());
            }
            if (registrationModel.getSummerDischarge() != null) {
                currentCrop.setSummerDischarge(registrationModel.getSummerDischarge());
            }
            if (registrationModel.getWaterSource() != null) {
                currentCrop.setWaterSource(registrationModel.getWaterSource());
            }

            currentCropService.save(currentCrop);

            ProposedArea proposedArea = farmer.getProposedArea();
            if (registrationModel.getProposedArea() > 0) {
                proposedArea.setProposedArea(registrationModel.getProposedArea());
            }
            if (registrationModel.getNumberOfPlantsPerAcre() > 0) {
                proposedArea.setPlantsPerAcre(registrationModel.getNumberOfPlantsPerAcre());
            }
            if (registrationModel.getTotalPlants() > 0) {
                proposedArea.setTotalPlants(registrationModel.getTotalPlants());
            }
            if (registrationModel.getProposedAreaSurveyNos() != null) {
                proposedArea.setSurveyNos(registrationModel.getProposedAreaSurveyNos());
            }
            if (registrationModel.getPlantationType() != null) {
                proposedArea.setPlantationType(registrationModel.getPlantationType());
            }

            proposedAreaService.save(proposedArea);

            //update farmer table
            farmer.setAddress(address);
            if (bankDetails != null) {
                farmer.setBankDetails(bankDetails);
            }
            farmer.setProposedArea(proposedArea);
            farmer.setCurrentCrop(currentCrop);
            farmer.setSmartCropUser(smartCropUser);

            Farmer updated = farmerService.saveFarmer(farmer);

            return Optional.of(updated);
        }
        return farmerOptional;
    }
}
