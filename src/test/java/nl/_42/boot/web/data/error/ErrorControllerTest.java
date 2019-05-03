package nl._42.boot.web.data.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl._42.boot.web.data.AbstractWebIntegrationTest;
import nl._42.boot.web.data.domain.UserForm;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ErrorControllerTest extends AbstractWebIntegrationTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void binding_valid() throws Exception {
        UserForm form = new UserForm();
        form.name = "John";

        webClient.perform(post("/error/binding").content(objectMapper.writeValueAsString(form)))
          .andExpect(status().isOk())
          .andExpect(jsonPath("name").value(form.name));
    }

    @Test
    public void binding_invalid() throws Exception {
        UserForm form = new UserForm();

        webClient.perform(post("/error/binding").content(objectMapper.writeValueAsString(form)))
          .andExpect(status().is4xxClientError())
          .andExpect(jsonPath("fieldErrors.UserForm.name[0]").value("must not be null"));
    }

    @Test
    public void exception() throws Exception {
        webClient.perform(get("/error/exception"))
          .andExpect(status().is5xxServerError())
          .andExpect(jsonPath("errorCode").value(ExceptionControllerAdvice.SERVER_GENERIC_ERROR));
    }

}
