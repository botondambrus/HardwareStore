package edu.bbte.idde.abim2109.backend.model;

import lombok.Data;

import java.io.Serializable;

@Data
public abstract class BaseEntity implements Serializable {
    Integer id;
}