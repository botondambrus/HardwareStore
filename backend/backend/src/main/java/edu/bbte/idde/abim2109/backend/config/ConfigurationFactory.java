package edu.bbte.idde.abim2109.backend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class ConfigurationFactory {
    private static final Logger LOG = LoggerFactory.getLogger(ConfigurationFactory.class);

    private static final String CONFIG_FILE_PREFIX = "/application_";
    private static final String CONFIG_FILE_SUFFIX = ".json";
    private static String CONFIG_FILE;
    private static ObjectMapper objectMapper;

    @Getter
    private static MainConfiguration mainConfiguration = new MainConfiguration();

    static {
        objectMapper = new ObjectMapper();

        String environment = System.getenv("CONFIG_ENV");
        if (environment == null || environment.isEmpty()) {
            CONFIG_FILE = CONFIG_FILE_PREFIX + "prod" + CONFIG_FILE_SUFFIX;
        } else {
            CONFIG_FILE = CONFIG_FILE_PREFIX + environment + CONFIG_FILE_SUFFIX;
        }

        try (InputStream inputStream = ConfigurationFactory.class.getResourceAsStream(CONFIG_FILE)) {
            mainConfiguration = objectMapper.readValue(inputStream, MainConfiguration.class);
            LOG.info("Read following configuration: {}", CONFIG_FILE);
        } catch (IOException e) {
            LOG.error("Error loading configuration", e);
        }
    }

    public static JdbcConfiguration getJdbcConfiguration() {
        return mainConfiguration.getJdbcConfiguration();
    }
}
