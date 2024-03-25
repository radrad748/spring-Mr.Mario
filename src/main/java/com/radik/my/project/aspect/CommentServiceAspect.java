package com.radik.my.project.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CommentServiceAspect {

    //@Pointcut("execution(*)")
    public void saveCommentPointcut(String text, String userEmail, String resTitle){}

    public void validateArgsMethodSaveComment() {

    }

}
