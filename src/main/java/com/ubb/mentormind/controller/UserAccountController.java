package com.ubb.mentormind.controller;

import com.ubb.mentormind.model.*;
import com.ubb.mentormind.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/account")
@CrossOrigin
public class UserAccountController {
    @Autowired
    UserAccountRepository userAccountRepository;
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    LectureRepository lectureRepository;
    @Autowired
    MaterialRepository materialRepository;
    @Autowired
    CommentRepository commentRepository;

    @GetMapping("/all")
    public Collection<UserAccount> findUserAccounts() {
        return userAccountRepository.findAll();
    }

    @GetMapping("/{userAccountId}")
    public UserAccount findUserAccount(@PathVariable Long userAccountId) {
        return userAccountRepository.findById(userAccountId).orElse(null);
    }

    @GetMapping("/login/{email}/{password}")
    public UserAccount findUserAccount(@PathVariable String email, @PathVariable String password) {
        return userAccountRepository.findByEmailAndPassword(email, password).orElse(null);
    }

    @PostMapping("/save")
    public UserAccount saveUserAccount(@RequestBody UserAccount userAccount) {
        return userAccountRepository.save(userAccount);
    }

    @DeleteMapping("/delete/{userAccountId}")
    public void deleteUserAccount(@PathVariable Long userAccountId) {
        Optional<UserAccount> accountOptional = userAccountRepository.findById(userAccountId);
        if (accountOptional.isPresent()) {
            UserAccount account = accountOptional.get();
            for (Subject s : subjectRepository.findAll()) {
                // remove user from participants
                Set<UserAccount> participants = s.getParticipants();
                participants.remove(account);
                s.setParticipants(participants);
                subjectRepository.save(s);
            }

            for (Subject s : subjectRepository.findAll()) {
                //remove the subject they teach
                if (account.equals(s.getTeacher())) {
                    subjectRepository.delete(s);
                }
            }

            for (Lecture l : lectureRepository.findAll()) {
                //remove the lecture they authored
                if (account.equals(l.getAuthor())) {
                    lectureRepository.delete(l);
                }
            }

            for (Material m : materialRepository.findAll()) {
                //remove the mark as completed for materials
                Set<UserAccount> completedBy = m.getCompletedBy();
                completedBy.remove(account);
                m.setCompletedBy(completedBy);
                materialRepository.save(m);

                //remove the material they authored
                if (account.equals(m.getAuthor())) {
                    materialRepository.delete(m);
                }
            }

            for (Comment c : commentRepository.findAll()) {
                //remove the comment they authored
                if (account.equals(c.getAuthor())) {
                    commentRepository.delete(c);
                }
            }
        }
        userAccountRepository.deleteById(userAccountId);
    }
}
