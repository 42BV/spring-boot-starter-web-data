package nl._42.boot.web.data.error;

import nl._42.boot.web.data.domain.UserForm;
import nl._42.boot.web.data.domain.UserResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/error")
public class ErrorController {

    @PostMapping("/binding")
    public UserResult binding(@Valid @RequestBody UserForm form) {
        return new UserResult(form.name);
    }

    @GetMapping("/exception")
    public void exception() {
        throw new IllegalStateException("Some exception");
    }

}
