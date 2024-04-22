package ru.nvkz.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nvkz.dto.UserReadDto;
import ru.nvkz.entity.PersonalInfo;
import ru.nvkz.entity.User;

@Component
@RequiredArgsConstructor
public class UserReadMapper implements Mapper<User, UserReadDto> {

    @Override
    public UserReadDto map(User object) {
        PersonalInfo personalInfo = object.getPersonalInfo();
        return new UserReadDto(
                object.getId(),
                object.getEmail(),
                getField(personalInfo, PersonalInfo::getBirthDate),
                getField(personalInfo, PersonalInfo::getName),
                getField(personalInfo, PersonalInfo::getSurname),
                getField(personalInfo, PersonalInfo::getPatronimic),
                object.getRole(),
                getField(personalInfo, PersonalInfo::getTelephone)
        );
    }
}