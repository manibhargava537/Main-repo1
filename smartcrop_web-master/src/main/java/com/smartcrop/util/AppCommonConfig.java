package com.smartcrop.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartcrop.security.AppSecurityUserDetails;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;

import java.io.InputStream;
import java.util.Collection;
import java.util.Map;

@Component
@Data
@Slf4j
public class AppCommonConfig {

    @Value("${com.smart.crop.state-district.file.path:static/data/state_district.json}")
    private String filePath;

    public Map<String, Object> readStateDistrictFileAsModel() {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream input = classLoader.getResourceAsStream(filePath);
            byte[] binaryData = FileCopyUtils.copyToByteArray(input);
            ObjectMapper mapper = new ObjectMapper()
                    .configure(JsonParser.Feature.IGNORE_UNDEFINED, true)
                    .configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
            Map<String, Object> stateDistrictModel = mapper.readValue(binaryData, new TypeReference<>() {
            });
            return stateDistrictModel;
        } catch (Exception e) {
            log.error("Exception while reading state-district config file", e);
        }
        return null;
    }

    public static AppSecurityUserDetails getAppSecurityUserDetails(Model model, Authentication principal) {
        if(principal!=null) {
            AppSecurityUserDetails appSecurityUserDetails = (AppSecurityUserDetails) principal.getPrincipal();
            Collection<GrantedAuthority> authorityList = (Collection<GrantedAuthority>) appSecurityUserDetails.getAuthorities();
            authorityList.stream().forEach(role -> {
                if ("ROLE_ADMIN".equals(role.getAuthority())) {
                    model.addAttribute("isAdmin", true);
                }
            });
            return appSecurityUserDetails;
        }
        return null;
    }

}
