package com.javierlnc.back_app.service.auth;

import com.javierlnc.back_app.entity.Area;
import com.javierlnc.back_app.entity.User;
import com.javierlnc.back_app.enums.UserRole;
import com.javierlnc.back_app.repository.AreaRepository;
import com.javierlnc.back_app.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final AreaRepository areaRepository;

    @PostConstruct
    public void CreateAdminAccount() {
        if(areaRepository.count()== 0){
            Area tic = new Area();
            tic.setName("TIC");
            areaRepository.save(tic);
            Area gh = new Area();
            gh.setName("Gestion Humana");
            areaRepository.save(gh);
            Area dev = new Area();
            dev.setName("Desarrollo");
            areaRepository.save(dev);
        }
        Optional<User> adminAccount = userRepository.findByRole(UserRole.ADMIN);
        if (adminAccount.isEmpty()) {
            User user = new User();
            user.setName("admin");
            user.setUsername("administrador");
            user.setEmail("admin@test.com");
            user.setRole(UserRole.ADMIN);
            Optional<Area> adminArea = areaRepository.findByName("TIC");
            if (adminArea.isPresent()) {
                user.setArea(adminArea.get());
            } else {
                System.out.println("Error: El área TIC no fue encontrada.");
                return;
            }
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepository.save(user);
        } else {
            System.out.print("Cuenta Admin ya existe");
        }
    }
    public User getAuthenticatedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                return userRepository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
            }
        }
        throw new RuntimeException("No se pudo obtener el usuario autenticado");
    }

}
