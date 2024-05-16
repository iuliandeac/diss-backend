package com.ubb.mentormind.controller;

import com.ubb.mentormind.model.Subject;
import com.ubb.mentormind.model.UserAccount;
import com.ubb.mentormind.repository.SubjectRepository;
import com.ubb.mentormind.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/subject")
@CrossOrigin
public class SubjectController {
    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    UserAccountRepository userAccountRepository;

    @GetMapping("/all")
    public Collection<Subject> findSubjects() {
        return subjectRepository.findAll();
    }

    @GetMapping("/foraccount/{accountId}")
    public Collection<Subject> findSubjectsForAccount(@PathVariable Long accountId) {
        return userAccountRepository.findById(accountId).map(userAccount -> subjectRepository.findAll().stream().filter(subject -> subject.getParticipants().contains(userAccount)).collect(Collectors.toSet())).orElse(Collections.emptySet());
    }

    @GetMapping("/{subjectId}")
    public Subject findSubject(@PathVariable Long subjectId) {
        return subjectRepository.findById(subjectId).orElse(null);
    }

    @PostMapping("/save")
    public Subject saveSubject(@RequestBody Subject subject) {
        subject.setJoinCode(generateJoinCode());
        return subjectRepository.save(subject);
    }

    @DeleteMapping("/delete/{subjectId}")
    public void deleteSubject(@PathVariable Long subjectId) {
        subjectRepository.deleteById(subjectId);
    }

    @PostMapping("/{subjectId}/enroll/{accountId}")
    public void enroll(@PathVariable Long subjectId, @PathVariable Long accountId) {
        Optional<Subject> subject = subjectRepository.findById(subjectId);
        Optional<UserAccount> account = userAccountRepository.findById(accountId);
        if (subject.isPresent() && account.isPresent()) {
            Subject actualSubject = subject.get();
            Set<UserAccount> participants = actualSubject.getParticipants();
            participants.add(account.get());
            actualSubject.setParticipants(participants);
            subjectRepository.save(actualSubject);
        }
    }

    @PostMapping("/{joinCode}/join/{accountId}")
    public void enroll(@PathVariable String joinCode, @PathVariable Long accountId) {
        Optional<Subject> subject = subjectRepository.findByJoinCode(joinCode);
        Optional<UserAccount> account = userAccountRepository.findById(accountId);
        if (subject.isPresent() && account.isPresent()) {
            Subject actualSubject = subject.get();
            Set<UserAccount> participants = actualSubject.getParticipants();
            participants.add(account.get());
            actualSubject.setParticipants(participants);
            subjectRepository.save(actualSubject);
        }
    }

    @DeleteMapping("/{subjectId}/remove/{accountId}")
    public void remove(@PathVariable Long subjectId, @PathVariable Long accountId) {
        Optional<Subject> subject = subjectRepository.findById(subjectId);
        Optional<UserAccount> account = userAccountRepository.findById(accountId);
        if (subject.isPresent() && account.isPresent()) {
            Subject actualSubject = subject.get();
            Set<UserAccount> participants = actualSubject.getParticipants();
            participants.remove(account.get());
            actualSubject.setParticipants(participants);
            subjectRepository.save(actualSubject);
        }
    }

    private String generateJoinCode() {
        int leftLimit = 97;
        int rightLimit = 122;
        int targetStringLength = 8;
        Random random = new Random();


        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        while (subjectRepository.findAll().stream().map(Subject::getJoinCode).collect(Collectors.toSet()).contains(generatedString)){
            generatedString = random.ints(leftLimit, rightLimit + 1)
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
        }
        return generatedString;
    }
}
