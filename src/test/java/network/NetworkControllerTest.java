package network;

import network.controller.NetworkController;
import network.service.impl.DeviceServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NetworkController.class)
class NetworkControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Mock the dependency that will be injected into the controller
    @MockBean
    private DeviceServiceImpl deviceService;

    @Test
    void testRegisterDevice() throws Exception {
        String json = """
            {
              "deviceType": "GATEWAY",
              "macAddress": "AA:BB:CC:DD:EE:01",
              "uplinkMacAddress": null
            }
            """;

        mockMvc.perform(post("/api/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("Device registered successfully."));
    }

}
