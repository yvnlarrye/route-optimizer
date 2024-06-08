package com.diplom.routeoptimizer.services.geocode.address.impl;

import com.diplom.routeoptimizer.services.geocode.address.Addressable;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OneStringAddress implements Addressable {
    @JsonProperty("full_address")
    private String address;

    private String type = "oneStringAddress";

    @Override
    public String oneStringAddress() {
        return address;
    }

    @Override
    public String toString() {
        return address;
    }
}
