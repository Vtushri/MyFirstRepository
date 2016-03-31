package com.zetagile.foodcart.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Category implements Serializable {

    private static final long serialVersionUID = 3933758886923273809L;

    protected List<Category> category;

    protected String id;

    protected String name;

    protected String level;

    protected String parentId;

    protected String imageUrl;


    public List<Category> getCategory() {
        if (category == null) {
            category = new ArrayList<Category>();
        }
        return this.category;
    }

    public String getId() {
        return id;
    }

    public void setId(String value) {
        this.id = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String value) {
        this.level = value;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String value) {
        this.parentId = value;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String value) {
        this.imageUrl = value;
    }

}
