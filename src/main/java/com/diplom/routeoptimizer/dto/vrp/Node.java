package com.diplom.routeoptimizer.dto.vrp;

import com.diplom.routeoptimizer.services.geocode.Location;
import com.diplom.routeoptimizer.services.geocode.address.Addressable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Node {
    private int id;
    private Addressable address;
    private Location location;
    private int load;
    private int supplied;
    private int demand;
}
