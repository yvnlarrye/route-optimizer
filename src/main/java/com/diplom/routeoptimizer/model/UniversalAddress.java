package com.diplom.routeoptimizer.model;

import com.diplom.routeoptimizer.services.geocoding.Addressable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UniversalAddress implements Addressable {
    private String country;
    private String city;
    private String street;
    private String houseNumber;
}
