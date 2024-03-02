package edu.bbte.idde.abim2109.backend.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MainConfiguration {
    @JsonProperty("jdbc")
    private JdbcConfiguration jdbcConfiguration = new JdbcConfiguration();

    public JdbcConfiguration getJdbcConfiguration() {
        return jdbcConfiguration;
    }

    public void setJdbcConfiguration(JdbcConfiguration jdbcConfiguration) {
        this.jdbcConfiguration = jdbcConfiguration;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MainConfiguration{");
        sb.append("jdbcConfiguration=").append(jdbcConfiguration);
        sb.append('}');
        return sb.toString();
    }
}

