package com.ubb.mentormind.controller;

import com.ubb.mentormind.model.Lecture;
import com.ubb.mentormind.model.Subject;
import com.ubb.mentormind.repository.LectureRepository;
import com.ubb.mentormind.repository.SubjectRepository;
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
    LectureRepository lectureRepository;

    @Autowired
    SubjectRepository subjectRepository;

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

    @PostMapping("/save/{subjectId}")
    public Lecture saveLecture(@RequestBody Lecture lecture, @PathVariable Long subjectId) {
        Optional<Subject> subject = subjectRepository.findById(subjectId);
        Lecture l = null;
        if(subject.isPresent()){
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
        lectureRepository.deleteById(lectureId);
    }
}
