package com.diplom.routeoptimizer.dto;

import com.diplom.routeoptimizer.services.geocoding.address.impl.OneStringAddress;
import lombok.Data;


import java.util.List;

@Data
public class AddressesDTO {
    private List<OneStringAddress> oneStringAddresses;
}
