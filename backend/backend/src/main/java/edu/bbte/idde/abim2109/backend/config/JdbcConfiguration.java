package edu.bbte.idde.abim2109.backend.config;

import lombok.Data;

@Data
public class JdbcConfiguration {
    private String url;
    private String userName;
    private String password;
    private String driverClassName;
    private Integer poolSize;
    private String daoType;
}
