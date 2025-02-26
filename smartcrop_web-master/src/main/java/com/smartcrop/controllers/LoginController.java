package com.smartcrop.controllers;

import com.smartcrop.security.AppSecurityUserDetails;
import com.smartcrop.util.AppCommonConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

@Controller
@Slf4j
public class LoginController {
	@GetMapping(value = "/login")
	public String loginPage(Model model, Principal principal) {
		log.info("returning login page!");
		AppSecurityUserDetails appSecurityUserDetails =  AppCommonConfig.getAppSecurityUserDetails(model, (Authentication) principal);
		return appSecurityUserDetails !=null ? "index" : "login";
	}

	@GetMapping(value = {"/", "/index"})
	public String getWelcomePage(Model model, Principal principal) {

		AppSecurityUserDetails appSecurityUserDetails = AppCommonConfig.getAppSecurityUserDetails(model, (Authentication) principal);
		if(null!=appSecurityUserDetails)		{
			model.addAttribute("loggedInUser",appSecurityUserDetails.getUsername());
			return "index";
		}
		return "login";


	}

	@GetMapping(value = "/403")
	public String unauthorizedUser(Model model) {
		return "403";
	}


}