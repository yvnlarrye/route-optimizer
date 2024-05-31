package com.diplom.routeoptimizer.dto.vrp;

import com.diplom.routeoptimizer.model.UniversalAddress;
import lombok.Data;
import java.util.List;

@Data
public class TspRequest {
    private List<UniversalAddress> addresses;
}
