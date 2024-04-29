package ru.nvkz.service;

import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nvkz.dto.BasketCreateEditDto;
import ru.nvkz.dto.UserCreateEditDto;
import ru.nvkz.dto.CustomUserDetails;
import ru.nvkz.dto.UserReadDto;
import ru.nvkz.entity.PersonalInfo;
import ru.nvkz.entity.PersonalInfo_;
import ru.nvkz.entity.User;
import ru.nvkz.entity.User_;
import ru.nvkz.filter.UserFilter;
import ru.nvkz.mapper.UserCreateEditMapper;
import ru.nvkz.mapper.UserReadMapper;
import ru.nvkz.repository.CPredicate;
import ru.nvkz.repository.UserRepository;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final UserCreateEditMapper userCreateEditMapper;
    private final BasketService basketService;

    public Page<UserReadDto> findAll(UserFilter filter, Pageable pageable) {
        Specification<User> specification = (root, cq, cb) -> {
            Path<PersonalInfo> personalInfo = root.get(User_.PERSONAL_INFO);
            Predicate[] predicates = CPredicate.builder()
                    .add(filter.getName(), (param) -> cb.like(cb.lower(personalInfo.get(PersonalInfo_.NAME)), "%" + param.toLowerCase() + "%"))
                    .add(filter.getSurname(), (param) -> cb.like(cb.lower(personalInfo.get(PersonalInfo_.SURNAME)), "%" + param.toLowerCase() + "%"))
                    .add(filter.getBirthDate(), (param) -> cb.lessThan(personalInfo.get(PersonalInfo_.BIRTH_DATE), param))
                    .build();
            return cb.and(predicates);
        };
        return userRepository.findAll(specification, pageable)
                .map(userReadMapper::map);
    }

    public List<UserReadDto> findAll() {
        return userRepository.findAll().stream()
                .map(userReadMapper::map)
                .toList();
    }

    public Optional<UserReadDto> findById(Long id) {
        return userRepository.findById(id)
                .map(userReadMapper::map);
    }

    @Transactional
    public UserReadDto create(UserCreateEditDto userDto) {
        UserReadDto user = Optional.of(userDto)
                .map(userCreateEditMapper::map)
                .map(userRepository::save)
                .map(userReadMapper::map)
                .orElseThrow();
        basketService.create(this.buildDefaultBasket(user));
        return user;
    }

    @Transactional
    public Optional<UserReadDto> update(Long id, UserCreateEditDto userDto) {
        return userRepository.findById(id)
                .map(entity -> userCreateEditMapper.map(userDto, entity))
                .map(userRepository::saveAndFlush)
                .map(userReadMapper::map);
    }

    @Transactional
    public boolean delete(Long id) {
        return userRepository.findById(id)
                .map(entity -> {
                    userRepository.delete(entity);
                    userRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).map(user -> new CustomUserDetails(
                        user.getId(),
                        user.getEmail(),
                        user.getPassword(),
                        Collections.singleton(user.getRole())
                ))
                .orElseThrow(() -> new UsernameNotFoundException("Failed to retrieve user: " + username));

    }

    private BasketCreateEditDto buildDefaultBasket(UserReadDto user) {
        return new BasketCreateEditDto(user.getId(), new BigDecimal(0), 0);
    }
}