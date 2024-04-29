package ru.nvkz.validation.validator;


import org.springframework.stereotype.Component;
import ru.nvkz.dto.OrderCreateEditDto;


@Component
public class OrderCreateEditValidator implements Validator<OrderCreateEditDto> {

    @Override
    public ValidationResult validate(OrderCreateEditDto object) {
        var validationResult = new ValidationResult();
//        if (!LocalDateFormatter.isValid(object.getBirthday())) {
//            validationResult.add(Error.of("invalid.birthday", "Birthday is invalid"));
//        }
//        if (Gender.findOpt(object.getGender()).isEmpty()) {
//            validationResult.add(Error.of("invalid.gender", "Gender is invalid"));
//        }
//        if (Role.findOpt(object.getRole()).isEmpty()) {
//            validationResult.add(Error.of("invalid.role", "Role is invalid"));
//        }
        return validationResult;
    }
}