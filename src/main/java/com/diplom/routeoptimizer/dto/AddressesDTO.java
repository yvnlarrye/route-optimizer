package com.diplom.routeoptimizer.dto;

import com.diplom.routeoptimizer.model.UniversalAddress;
import lombok.Data;


import java.util.List;

@Data
public class AddressesDTO {
    private List<UniversalAddress> addresses;
}
