package com.zetagile.foodcart.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.math.BigInteger;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Property implements Serializable {

    private static final long serialVersionUID = -6528225050548079260L;


    protected String name;

    protected String value;

    protected BigInteger id;


    public String getName() {
        return name;
    }


    public void setName(String value) {
        this.name = value;
    }


    public String getValue() {
        return value;
    }


    public void setValue(String value) {
        this.value = value;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger value) {
        this.id = value;
    }

}
