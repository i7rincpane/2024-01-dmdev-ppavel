package ru.nvkz.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.nvkz.dto.UserCreateEditDto;
import ru.nvkz.entity.PersonalInfo;
import ru.nvkz.entity.User;
import ru.nvkz.repository.BasketRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserCreateEditMapper implements Mapper<UserCreateEditDto, User> {

    private final PasswordEncoder passwordEncoder;

    @Override
    public User map(UserCreateEditDto fromObject, User toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public User map(UserCreateEditDto object) {
        User user = new User();
        copy(object, user);
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

        Optional.ofNullable(object.getPassword())
                .filter(StringUtils::hasText)
                .map(passwordEncoder::encode)
                .ifPresent(user::setPassword);
    }
}