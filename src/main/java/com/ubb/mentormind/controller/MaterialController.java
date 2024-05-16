package com.ubb.mentormind.controller;

import com.ubb.mentormind.model.*;
import com.ubb.mentormind.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/material")
@CrossOrigin
public class MaterialController {
    @Autowired
    MaterialRepository materialRepository;

    @Autowired
    LectureRepository lectureRepository;

    @Autowired
    UserAccountRepository userAccountRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @GetMapping("/{lectureId}")
    public Set<Material> findMaterials(@PathVariable Long lectureId) {
        return lectureRepository.findById(lectureId).map(Lecture::getMaterials).orElse(Collections.emptySet());
    }

    @PostMapping("/save/{lectureId}")
    public Material saveMaterial(@PathVariable Long lectureId, @RequestBody Material material) {
        Optional<Lecture> lecture = lectureRepository.findById(lectureId);
        Material m = null;
        if (lecture.isPresent()) {
            m = materialRepository.save(material);
            Lecture actualLecture = lecture.get();
            Set<Material> materials = actualLecture.getMaterials();
            materials.add(m);
            actualLecture.setMaterials(materials);
            lectureRepository.save(actualLecture);
        }
        return m;
    }

    @DeleteMapping("/delete/{materialId}")
    public void deleteMaterial(@PathVariable Long materialId) {
        materialRepository.deleteById(materialId);
    }

    @PostMapping("/{materialId}/accept/{accountId}")
    public void acceptMaterial(@PathVariable Long materialId, @PathVariable Long accountId) {
        Optional<Material> material = materialRepository.findById(materialId);
        if(material.isPresent()) {
            Material actualMaterial = material.get();
            Set<Subject> subjects = subjectRepository.findAll().stream().filter(subject -> subject.getTeacher().getId().equals(accountId)).collect(Collectors.toSet());
            for (Subject s : subjects) {
                for (Lecture l : s.getLectures()) {
                    if (l.getMaterials().contains(actualMaterial)){
                        actualMaterial.setIsAccepted(true);
                        materialRepository.save(actualMaterial);
                    }
                }
            }
        }
    }

    @DeleteMapping("/{materialId}/reject/{accountId}")
    public void rejectMaterial(@PathVariable Long materialId, @PathVariable Long accountId) {
        Optional<Material> material = materialRepository.findById(materialId);
        if(material.isPresent()) {
            Material actualMaterial = material.get();
            Set<Subject> subjects = subjectRepository.findAll().stream().filter(subject -> subject.getTeacher() != null && subject.getTeacher().getId().equals(accountId)).collect(Collectors.toSet());
            for (Subject s : subjects) {
                for (Lecture l : s.getLectures()) {
                    if (l.getMaterials().contains(actualMaterial)){
                        actualMaterial.setIsAccepted(false);
                        materialRepository.save(actualMaterial);
                    }
                }
            }
        }
    }

    @PostMapping("/{materialId}/done/{accountId}")
    public void doneMaterial(@PathVariable Long materialId, @PathVariable Long accountId) {
        Optional<Material> material = materialRepository.findById(materialId);
        Optional<UserAccount> account = userAccountRepository.findById(accountId);
        if(material.isPresent() && account.isPresent()) {
            Material actualMaterial = material.get();
            UserAccount actualAccount = account.get();
            Set<UserAccount> completedBy = actualMaterial.getCompletedBy();
            completedBy.add(actualAccount);
            actualMaterial.setCompletedBy(completedBy);
            materialRepository.save(actualMaterial);
        }
    }
}
