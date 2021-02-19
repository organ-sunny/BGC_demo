package com.sunny.entity;

import javax.persistence.*;

@Entity
@Table(name = "config")
public class ConfigEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "PROPERTY")
    private String property;

    @Column(name = "VALUE")
    private String value;

    @Override
    public String toString() {
        return "ConfigEntity{" +
                "id=" + id +
                ", property='" + property + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
