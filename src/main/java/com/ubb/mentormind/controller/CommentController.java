package com.ubb.mentormind.controller;

import com.ubb.mentormind.model.Comment;
import com.ubb.mentormind.model.Material;
import com.ubb.mentormind.repository.CommentRepository;
import com.ubb.mentormind.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/comment")
@CrossOrigin
public class CommentController {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    MaterialRepository materialRepository;

    @GetMapping("/{materialId}")
    public Collection<Comment> findComments(@PathVariable Long materialId) {
        Optional<Material> material = materialRepository.findById(materialId);
        return material.isPresent() ? material.get().getComments() : Collections.emptySet();
    }

    @PostMapping("/save/{materialId}")
    public Comment saveComment(@RequestBody Comment comment, @PathVariable Long materialId) {
        Optional<Material> material = materialRepository.findById(materialId);
        Comment c = null;
        if (material.isPresent()) {
            c = commentRepository.save(comment);
            Material actualMaterial = material.get();
            Set<Comment> comments = actualMaterial.getComments();
            comments.add(c);
            actualMaterial.setComments(comments);
            materialRepository.save(actualMaterial);
        }
        return c;
    }

    @DeleteMapping("/delete/{commentId}")
    public void deleteComment(@PathVariable Long commentId) {
        deleteCommentRecursive(commentId);
    }

    private void deleteCommentRecursive(Long commentId) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        if (commentOptional.isPresent()) {
            Comment comment = commentOptional.get();
            for (Material m : materialRepository.findAll()) {
                Set<Comment> comments = m.getComments();
                comments.remove(comment);
                m.setComments(comments);
                materialRepository.save(m);
            }
            commentRepository.findAll().stream().filter(c -> comment.equals(c.getReplyTo())).forEach(child -> deleteCommentRecursive(child.getId()));
            commentRepository.delete(comment);
        }
    }
}
