package by.baraznov.securityservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class SwaggerConfigTest {

    @InjectMocks
    private SwaggerConfig swaggerConfig;

    private final String devUrl = "http://localhost:8080";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(swaggerConfig, "devUrl", devUrl);
    }

    @Test
    void testMyOpenAPI() {
        OpenAPI openAPI = swaggerConfig.myOpenAPI();

        assertNotNull(openAPI);
        assertNotNull(openAPI.getInfo());
        assertEquals("Auth API", openAPI.getInfo().getTitle());
        assertEquals("1.0", openAPI.getInfo().getVersion());
        assertNotNull(openAPI.getServers());
        assertEquals(1, openAPI.getServers().size());
        assertEquals(devUrl, openAPI.getServers().get(0).getUrl());
    }
}
