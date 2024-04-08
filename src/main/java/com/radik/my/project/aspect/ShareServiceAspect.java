package com.radik.my.project.aspect;
import com.radik.my.project.entity.Menu;
import com.radik.my.project.entity.enums.ShareType;
import com.radik.my.project.utils.exceptions.NotCorrectDataServices;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Aspect
@Component
public class ShareServiceAspect {

    @Before("execution(* com.radik.my.project.services.ShareService.saveShare(..)) && args(resTitle, description,  type, menuList, discount)")
    public void method_saveShare_validArgs(String resTitle, String description, ShareType type, List<Menu> menuList, Integer discount) {
        if (Objects.isNull(resTitle) || Objects.isNull(description) || Objects.isNull(type) || Objects.isNull(menuList) || Objects.isNull(discount))
            throw new NotCorrectDataServices("Данные не могут быть null");

        if (resTitle.trim().isEmpty() || description.trim().isEmpty() || menuList.isEmpty() || discount < 1)
            throw new NotCorrectDataServices("Не корректные данные");
    }

}
