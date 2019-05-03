package nl._42.boot.web.data;

import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class NoContentControllerTest  extends AbstractWebIntegrationTest {

    @Test
    public void readUser_shouldSucceed() throws Exception {
        webClient.perform(get("/none"))
          .andExpect(status().isNoContent());
    }

}
