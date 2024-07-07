import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class WeatherReportServiceTests {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WeatherReportService weatherReportService;

    @Test
    public void testGetWeatherReport() {
        // Mock data for testing
        double latitude = 37.8267;
        double longitude = -122.4233;
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude +
                "&lon=" + longitude + "&appid=318f8d93739d6b4bc91378a6dbbdcd57";

        WeatherApiResponse mockApiResponse = new WeatherApiResponse();
        WeatherApiResponse.Main mockMain = new WeatherApiResponse.Main();
        mockMain.setTemperature(20.0);
        mockMain.setHumidity(80.0);
        mockApiResponse.setMain(mockMain);

        // Mocking restTemplate's behavior
        when(restTemplate.getForObject(apiUrl, WeatherApiResponse.class)).thenReturn(mockApiResponse);

        // Call the method to be tested
        WeatherReport weatherReport = weatherReportService.getWeatherReport(latitude, longitude);

        // Verify the result
        assertNotNull(weatherReport);
        assertEquals(20.0, weatherReport.getTemperature());
        assertEquals(80.0, weatherReport.getHumidity());

        // Verify that restTemplate's getForObject method was called once with the correct URL
        verify(restTemplate, times(1)).getForObject(apiUrl, WeatherApiResponse.class);
    }
}
