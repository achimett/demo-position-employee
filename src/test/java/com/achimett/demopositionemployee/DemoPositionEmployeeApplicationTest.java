package com.achimett.demopositionemployee;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;
import java.sql.Date;

@SpringBootTest
@AutoConfigureMockMvc
class DemoPositionEmployeeApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PositionRepository positionRepository;

    private final Position cashier = new Position("Cashier", "Works at the cash desk");
    private final Position manager = new Position("Manager", "Manages the business");

    private final Employee sarah = new Employee("Sarah", "Baker",Date.valueOf("1990-10-10"), Gender.FEMALE, null);
    private final Employee john = new Employee("John", "Gulliver", Date.valueOf("1995-09-03"), Gender.MALE, null);

    @BeforeEach
    void setUp() {
        employeeRepository.deleteAll();
        positionRepository.deleteAll();

        positionRepository.save(cashier);
        positionRepository.save(manager);

        employeeRepository.save(sarah);
        employeeRepository.save(john);
    }

    @Test
    void whenPushNewEmployee_code201() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post(URI.create("/employees"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Martha\",\"surname\":\"Sutherland\",\"birthDate\":\"2000-07-30\",\"gender\":\"FEMALE\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void whenPatchEmployee_code204AndPatchedEmployee() throws Exception {
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get(URI.create("/employees/search/findBySurname"))
                        .param("surname", "Baker"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        URI employeeURI = URI.create(JsonPath.read(result.getResponse().getContentAsString(),
                "$._embedded.employees[0]._links.self.href"));

        mockMvc.perform(
                MockMvcRequestBuilders.patch(employeeURI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Anna\"}"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        mockMvc.perform(MockMvcRequestBuilders.get(employeeURI))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Anna"));
    }

    @Test
    void whenPutEmployeePosition_code204AndPositionReferenceAvailable() throws Exception {
        MvcResult employeeResult = mockMvc.perform(
                MockMvcRequestBuilders.get(URI.create("/employees/search/findBySurname"))
                        .param("surname", "Baker"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        MvcResult positionResult = mockMvc.perform(
                MockMvcRequestBuilders.get(URI.create("/positions/search/findByName"))
                        .param("name", "Manager"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        URI employeePositionURI = URI.create(JsonPath.read(employeeResult.getResponse().getContentAsString(),
                "$._embedded.employees[0]._links.position.href"));

        URI positionURI = URI.create(JsonPath.read(positionResult.getResponse().getContentAsString(),
                "$._embedded.positions[0]._links.self.href"));

        mockMvc.perform(
                MockMvcRequestBuilders.put(employeePositionURI)
                        .contentType("text/uri-list")
                        .content(positionURI.getPath()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        mockMvc.perform(MockMvcRequestBuilders.get(employeePositionURI))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Manager"));
    }
}
