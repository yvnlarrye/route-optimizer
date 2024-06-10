package com.diplom.routeoptimizer.services.reportbuilder;

import com.diplom.routeoptimizer.dto.vrp.VrpSolution;


public interface ReportFileBuilder {
    byte[] createReportFile(VrpSolution solution);
}
