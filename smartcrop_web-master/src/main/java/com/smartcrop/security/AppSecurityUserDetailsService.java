package com.smartcrop.security;

import com.smartcrop.entity.SmartCropUser;
import com.smartcrop.repository.SmartCropUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class AppSecurityUserDetailsService implements UserDetailsService {

    @Autowired
    private SmartCropUserRepository smartCropUserRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        SmartCropUser appUser = smartCropUserRepository.findByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return AppSecurityUserDetails.build(appUser);
    }
}
