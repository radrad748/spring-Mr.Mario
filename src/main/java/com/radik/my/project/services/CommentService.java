package com.radik.my.project.services;

import com.radik.my.project.repositories.CommentDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentDao comDao;

    public void saveComment(String text, String userEmail, String resTitle) {

    }



}
