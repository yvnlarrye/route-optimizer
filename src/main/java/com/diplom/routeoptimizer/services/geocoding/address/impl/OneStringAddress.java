package com.diplom.routeoptimizer.services.geocoding.address.impl;

import com.diplom.routeoptimizer.services.geocoding.address.Addressable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OneStringAddress implements Addressable {
    private String address;

    @Override
    public String oneStringAddress() {
        return address;
    }

    @Override
    public String toString() {
        return address;
    }
}
