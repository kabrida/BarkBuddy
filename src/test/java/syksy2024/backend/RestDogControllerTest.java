package syksy2024.backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RestDogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webAppContext;

    @BeforeEach
    public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
	}

    @Test
	public void statusOk() throws Exception {
		mockMvc.perform(get("/dogs")).andExpect(status().isOk());
	}

    @Test
	public void responseTypeApplicationJson() throws Exception {
		mockMvc.perform(get("/dogs"))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				// .andExpect(content().contentType(MediaType.APPLICATION_ATOM_XML_VALUE))
				.andExpect(status().isOk());
	}

    @Test
    public void getDogByIdTest() throws Exception {
        mockMvc.perform(get("/dogs/{id}", 3))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3));
    }

    @Test
    public void newDogTest() throws Exception {
        String newDogJson = "{\"name\": \"Buddy\", \"breed\": {\"id\": 1}, \"regNum\": \"FI007\", \"dateOfBirth\": \"2020-05-15\", \"owner\": {\"id\": 1}}";

        mockMvc.perform(MockMvcRequestBuilders.post("/dogs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newDogJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void editDogTest() throws Exception {
        Long dogId = 11L;
        String editedDogJson = "{\"name\": \"Edited Buddy\", \"breed\": {\"id\": 1}, \"regNum\": \"FI007\", \"dateOfBirth\": \"2020-05-15\", \"owner\": {\"id\": 1}}";

        mockMvc.perform(MockMvcRequestBuilders.put("/dogs/{id}", dogId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(editedDogJson))
            .andExpect(status().isOk());
}


    @Test
    public void deleteDogTest() throws Exception {
        Long dogId = 11L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/dogs/{id}", dogId))
            .andExpect(status().isNoContent());
}



}
