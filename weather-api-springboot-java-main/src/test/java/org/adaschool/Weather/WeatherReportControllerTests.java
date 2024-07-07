import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class WeatherReportControllerTests {

    private MockMvc mockMvc;

    @Mock
    private WeatherReportService weatherReportService;

    @InjectMocks
    private WeatherReportController weatherReportController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(weatherReportController).build();
    }

    @Test
    public void testGetWeatherReport() throws Exception {
        // Mock data for testing
        WeatherReport mockWeatherReport = new WeatherReport();
        mockWeatherReport.setTemperature(20.0);
        mockWeatherReport.setHumidity(80.0);

        // Mocking service method call
        when(weatherReportService.getWeatherReport(anyDouble(), anyDouble())).thenReturn(mockWeatherReport);

        // Perform GET request and verify response
        mockMvc.perform(get("/v1/api/weather-report")
                        .param("latitude", "37.8267")
                        .param("longitude", "-122.4233")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.temperature").value(20.0))
                .andExpect(jsonPath("$.humidity").value(80.0));

        // Verify that the service method was called once with the correct parameters
        verify(weatherReportService, times(1)).getWeatherReport(37.8267, -122.4233);
    }
}
