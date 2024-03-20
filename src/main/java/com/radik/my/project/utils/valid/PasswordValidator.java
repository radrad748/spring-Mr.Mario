package com.radik.my.project.utils.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<Password, String> {
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$");
    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println(password);
        boolean valid = password != null && PASSWORD_PATTERN.matcher(password).matches();
        System.out.println(valid);
        return valid;
    }
}
