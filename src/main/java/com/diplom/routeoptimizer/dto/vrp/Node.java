package com.diplom.routeoptimizer.dto.vrp;

import com.diplom.routeoptimizer.model.Location;
import com.diplom.routeoptimizer.services.geocode.address.Addressable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class Node {
    private int id;
    private Addressable address;
    private Location location;
    private int load;
}
