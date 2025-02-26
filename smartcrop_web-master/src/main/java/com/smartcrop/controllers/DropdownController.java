package com.smartcrop.controllers;

import com.smartcrop.entity.*;
import com.smartcrop.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dropdown")
public class DropdownController {

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

    @Autowired
    private CropDetailsRepository cropDetailsRepository;

    @Autowired
    private SoilTypeRepository soilTypeRepository;

    @GetMapping("/states")
    public @ResponseBody List<State> getAllActiveStatesByCountry(@RequestParam("countryId") Long countryId) {
        return stateRepository.findByCountry_IdAndActive(countryId, true);
    }

    @GetMapping("/districts")
    public @ResponseBody List<District> getAllActiveDistrictsOrCitiesByState(@RequestParam("stateId") Long stateId) {
        return districtOrCityRepository.findByState_IdAndActive(stateId, true);
    }

    @GetMapping("/mandals")
    public @ResponseBody List<Mandal> getAllActiveMandalsByDistrict(@RequestParam("districtId") Long districtId) {
        return mandalRepository.findByDistrict_IdAndActive(districtId, true);
    }

    @GetMapping("/villages")
    public @ResponseBody List<Village> getAllActiveVillagesByMandal(@RequestParam("mandalId") Long mandalId) {
        return villageRepository.findByMandal_IdAndActive(mandalId, true);
    }

    @PostMapping("/countries")
    public @ResponseBody List<Country> saveCountries(@RequestBody List<Country> countries){
        for (Country country: countries){
            countryRepository.save(country);
        }
        return countryRepository.findAll();
    }

    @PostMapping("/states")
    public @ResponseBody List<State> saveStates(@RequestBody List<State> states){
        for (State state: states){
            stateRepository.save(state);
        }
        return stateRepository.findAll();
    }

    @PostMapping("/districts")
    public @ResponseBody List<District> saveDistricts(@RequestBody List<District> districts){
        for (District district: districts){
            districtOrCityRepository.save(district);
        }
        return districtOrCityRepository.findAll();
    }

    @PostMapping("/mandals")
    public @ResponseBody List<Mandal> saveMandals(@RequestBody List<Mandal> mandals){
        for (Mandal mandal: mandals){
            mandalRepository.save(mandal);
        }
        return mandalRepository.findAll();
    }

    @PostMapping("/villages")
    public @ResponseBody List<Village> saveVillages(@RequestBody List<Village> villages){
        for (Village village: villages){
            villageRepository.save(village);
        }
        return villageRepository.findAll();
    }

    @GetMapping("/crops")
    public @ResponseBody List<CropDetails> getAllActiveCrops() {
        return cropDetailsRepository.findAll();
    }

    @PostMapping("/crops")
    public @ResponseBody List<CropDetails> saveCrops(@RequestBody List<CropDetails> crops){
        for (CropDetails crop: crops){
            cropDetailsRepository.save(crop);
        }
        return cropDetailsRepository.findAll();
    }

    @GetMapping("/soilTypes")
    public @ResponseBody List<SoilType> getAllActiveSoilTypes() {
        return soilTypeRepository.findAll();
    }

    @PostMapping("/soilTypes")
    public @ResponseBody List<SoilType> saveSoilTypes(@RequestBody List<SoilType> types){
        for (SoilType type: types){
            soilTypeRepository.save(type);
        }
        return soilTypeRepository.findAll();
    }
}
