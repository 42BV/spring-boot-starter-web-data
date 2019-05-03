package nl._42.boot.web.data;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoContentController {

    @GetMapping("/none")
    public void noContent() {
    }

}
