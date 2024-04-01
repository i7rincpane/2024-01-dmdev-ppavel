package ru.nvkz.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nvkz.dto.UserCreateEditDto;
import ru.nvkz.entity.PersonalInfo;
import ru.nvkz.entity.User;

@Component
@RequiredArgsConstructor
public class UserCreateEditMapper implements Mapper<UserCreateEditDto, User> {

    @Override
    public User map(UserCreateEditDto fromObject, User toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public User map(UserCreateEditDto object) {
        User user = new User();
        copy(object, user);
        user.setPassword(object.getPassword());
        return user;
    }

    private void copy(UserCreateEditDto object, User user) {
        PersonalInfo personalInfo = new PersonalInfo();
        personalInfo.setName(object.getName());
        personalInfo.setPatronimic(object.getPatronimic());
        personalInfo.setSurname(object.getSurname());
        personalInfo.setTelephone(object.getTelephone());
        personalInfo.setBirthDate(object.getBirthDate());
        user.setRole(object.getRole());
        user.setEmail(object.getEmail());
        user.setPersonalInfo(personalInfo);
    }
}