package com.javierlnc.back_app.service.jwt;

import com.javierlnc.back_app.exception.UserNotFoundException;
import com.javierlnc.back_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServicesImpl implements UserService{
    private final UserRepository userRepository;

    public UserDetailsService userDetailsService(){
        return new UserDetailsService(){

            public UserDetails loadUserByUsername(String username){
                return userRepository.findByUsername(username).orElseThrow(()-> new UserNotFoundException("Usuario no encontrado"));
            }
        };
    }

}
