package com.ecommerce.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@ToString
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;

    @NotBlank
    @Size(min = 5, message = "Street name should be atleast 5 characters")
    private String street;

    @NotBlank
    @Size(min = 5, message = "Building name should be atleast 5 characters")
    private String buildingName;

    @NotBlank
    @Size(min = 5, message = "City name should be atleast 5 characters")
    private String city;

    @NotBlank
    @Size(min = 2, message = "State name should be atleast 2 characters")
    private String state;

    @NotBlank
    @Size(min = 5, message = "Street name should be atleast 5 characters")
    private String country;

    @NotBlank
    @Size(min = 6, max = 6, message = "Pincode should be 6 characters")
    private String pincode;

    @ToString.Exclude
    @ManyToMany(mappedBy = "addresses")
    private List<User> users = new ArrayList<>();

    public Address(String street, String buildingName, String city, String state, String country, String pincode) {
        this.street = street;
        this.buildingName = buildingName;
        this.city = city;
        this.state = state;
        this.country = country;
        this.pincode = pincode;
    }
}
