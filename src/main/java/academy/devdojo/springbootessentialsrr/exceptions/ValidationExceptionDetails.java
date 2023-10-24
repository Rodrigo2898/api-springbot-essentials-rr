package academy.devdojo.springbootessentialsrr.exceptions;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ValidationExceptionDetails extends ExceptionDetails {
    private final String field;
    private final String fieldsMessage;
}
