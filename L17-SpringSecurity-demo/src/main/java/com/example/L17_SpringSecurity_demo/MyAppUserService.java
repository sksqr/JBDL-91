package com.example.L17_SpringSecurity_demo;

import com.example.L17_SpringSecurity_demo.dto.CreateUserRequestDto;
import com.example.L17_SpringSecurity_demo.entity.AppUser;
import com.example.L17_SpringSecurity_demo.repo.IAppUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MyAppUserService implements UserDetailsService {

//    Map<String,UserDetails> userStore = new HashMap<>();
//
//    @PostConstruct
//    public void init(){
//        userStore.put("rahul", User.builder().username("rahul").password("rahul123").roles("USER").build());
//        userStore.put("ravi", User.builder().username("ravi").password("ravi123").roles("USER").build());
//    }

    @Autowired
    private IAppUserRepo appUserRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails =  appUserRepo.findByEmail(username);
        if(userDetails == null){
            throw new UsernameNotFoundException(username);
        }
        return userDetails;
    }


    public Long createUser(CreateUserRequestDto createUserRequestDto) {
        AppUser appUser = new AppUser();
        appUser.setEmail(createUserRequestDto.getEmail());
        appUser.setPassword(passwordEncoder.encode(createUserRequestDto.getPassword()));
        appUser.setName(createUserRequestDto.getName());
        appUser.setRole(createUserRequestDto.getRole());

        appUser = appUserRepo.save(appUser);
        return appUser.getId();
    }

    public boolean changePassword(AppUser appUser, String newPassword){
        appUser.setPassword(passwordEncoder.encode(newPassword));
        appUser = appUserRepo.save(appUser);
        return true;
    }
}
