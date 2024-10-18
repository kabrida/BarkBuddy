package syksy2024.backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BreedRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webAppContext;

        @BeforeEach
        public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
	    }

        @Test
        public void testStatusOk() throws Exception {
            mockMvc.perform(get("/breeds")).andExpect(status().isOk());
        }

        @Test
        public void testFindAllBreeds() throws Exception {
            mockMvc.perform(get("/breeds"))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
        }

    

}
