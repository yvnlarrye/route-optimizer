package com.diplom.routeoptimizer.dto.vrp;

import com.diplom.routeoptimizer.services.geocoding.address.impl.OneStringAddress;
import lombok.Data;

import java.util.List;

@Data
public class TspRequest {
    private List<OneStringAddress> addresses;
    private int depot;

    @Override
    public String toString() {
        return addresses.toString();
    }
}
