package com.example.touristinfo.data.models;

import java.io.Serializable;

public class Location implements Serializable {
    private String name;
    private String description;
    private String contactPhone;
    private String workingHours;

    public Location(String name, String description, String contactPhone, String workingHours) {
        this.name = name;
        this.description = description;
        this.contactPhone = contactPhone;
        this.workingHours = workingHours;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    @Override
    public String toString() {
        return "Location {" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                ", workingHours='" + workingHours + '\'' +
                '}';
    }
}