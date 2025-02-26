package com.smartcrop.controllers;

import com.smartcrop.util.AppCommonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("common")
public class CommonController {

    @Autowired
    AppCommonConfig appCommonConfig;

    @GetMapping(value = "/district-state-list")
    public ResponseEntity<?> getStateDistrictList() {
        return ResponseEntity.status(HttpStatus.OK).body(appCommonConfig.readStateDistrictFileAsModel());
    }
}
