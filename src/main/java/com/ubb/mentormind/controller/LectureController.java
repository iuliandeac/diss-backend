package com.ubb.mentormind.controller;

import com.ubb.mentormind.model.Lecture;
import com.ubb.mentormind.model.Material;
import com.ubb.mentormind.model.Subject;
import com.ubb.mentormind.model.UserAccount;
import com.ubb.mentormind.repository.LectureRepository;
import com.ubb.mentormind.repository.SubjectRepository;
import com.ubb.mentormind.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/lecture")
@CrossOrigin
public class LectureController {
    @Autowired
    UserAccountRepository userAccountRepository;
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    LectureRepository lectureRepository;

    @GetMapping("/all")
    public Collection<Lecture> findLectures() {
        return lectureRepository.findAll();
    }

    @GetMapping("/fromsubject/{subjectId}")
    public Collection<Lecture> findLecturesFromSubject(@PathVariable Long subjectId) {
        Optional<Subject> subject = subjectRepository.findById(subjectId);
        return subject.isPresent() ? subject.get().getLectures() : Collections.emptySet();
    }

    @GetMapping("/{lectureId}")
    public Lecture findLecture(@PathVariable Long lectureId) {
        return lectureRepository.findById(lectureId).orElse(null);
    }

    @GetMapping("{lectureId}/progress/{accountId}")
    public String findProgress(@PathVariable Long lectureId, @PathVariable Long accountId) {
        Optional<Lecture> lectureOptional = lectureRepository.findById(lectureId);
        Optional<UserAccount> accountOptional = userAccountRepository.findById(accountId);
        int total = 0;
        int done = 0;
        if (lectureOptional.isPresent() && accountOptional.isPresent()) {
            Lecture lecture = lectureOptional.get();
            UserAccount account = accountOptional.get();
            for (Material m : lecture.getMaterials()) {
                if (m.getCompletedBy().contains(account)) {
                    total += 1;
                    done += 1;
                } else {
                    total += 1;
                }
            }
        }
        if (total != 0) {
            return String.valueOf(done * 100 / total);
        }
        return "0";
    }

    @PostMapping("/save/{subjectId}")
    public Lecture saveLecture(@RequestBody Lecture lecture, @PathVariable Long subjectId) {
        Optional<Subject> subject = subjectRepository.findById(subjectId);
        Lecture l = null;
        if (subject.isPresent()) {
            l = lectureRepository.save(lecture);
            Subject actualSubject = subject.get();
            Set<Lecture> lectures = actualSubject.getLectures();
            lectures.add(l);
            actualSubject.setLectures(lectures);
            subjectRepository.save(actualSubject);
        }
        return l;
    }

    @DeleteMapping("/delete/{lectureId}")
    public void deleteLecture(@PathVariable Long lectureId) {
        Optional<Lecture> lectureOptional = lectureRepository.findById(lectureId);
        if (lectureOptional.isPresent()) {
            Lecture lecture = lectureOptional.get();
            for (Subject s : subjectRepository.findAll()) {
                Set<Lecture> lectures = s.getLectures();
                lectures.remove(lecture);
                s.setLectures(lectures);
                subjectRepository.save(s);
            }
        }
        lectureRepository.deleteById(lectureId);
    }
}
