package com.ubb.mentormind.controller;

import com.ubb.mentormind.model.UserAccount;
import com.ubb.mentormind.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/account")
@CrossOrigin
public class UserAccountController {
    @Autowired
    UserAccountRepository userAccountRepository;

    @GetMapping("/all")
    public Collection<UserAccount> findUserAccounts(){
        return userAccountRepository.findAll();
    }

    @GetMapping("/{userAccountId}")
    public UserAccount findUserAccount(@PathVariable Long userAccountId){
        return userAccountRepository.findById(userAccountId).orElse(null);
    }

    @GetMapping("/login/{email}/{password}")
    public UserAccount findUserAccount(@PathVariable String email, @PathVariable String password){
        return userAccountRepository.findByEmailAndPassword(email,password).orElse(null);
    }

    @PostMapping("/save")
    public UserAccount saveUserAccount(@RequestBody UserAccount userAccount){
        return userAccountRepository.save(userAccount);
    }

    @DeleteMapping("/delete/{userAccountId}")
    public void deleteUserAccount(@PathVariable Long userAccountId){
        userAccountRepository.deleteById(userAccountId);
    }
}
