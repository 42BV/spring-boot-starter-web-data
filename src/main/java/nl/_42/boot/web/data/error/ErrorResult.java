package nl._42.boot.web.data.error;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.capitalize;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResult {

    private final List<String> globalErrors = new ArrayList<>();
    private final Map<String, Map<String, List<String>>> fieldErrors = new HashMap<>();

    ErrorResult(String globalError) {
        this.globalErrors.add(globalError);
    }

    ErrorResult(BindingResult bindingResult) {
        bindingResult.getGlobalErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .forEach(globalError -> this.globalErrors.add(globalError));

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String entityType = capitalize(fieldError.getObjectName());
            Map<String, List<String>> errorsForEntityType = this.fieldErrors.get(entityType);
            if (errorsForEntityType == null) {
                errorsForEntityType = new HashMap<>();
                this.fieldErrors.put(entityType, errorsForEntityType);
            }
            List<String> messages = new ArrayList<>();
            errorsForEntityType.put(fieldError.getField(), messages);
            messages.add(fieldError.getDefaultMessage());
        }
    }

}
