package com.diplom.routeoptimizer.services.reportbuilder.impl;

import com.diplom.routeoptimizer.dto.vrp.Node;
import com.diplom.routeoptimizer.dto.vrp.Route;
import com.diplom.routeoptimizer.dto.vrp.VrpSolution;
import com.diplom.routeoptimizer.exceptions.ReportFileBuildingException;
import com.diplom.routeoptimizer.services.UserService;
import com.diplom.routeoptimizer.services.reportbuilder.ReportFileBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class ExcelReportFileBuilder implements ReportFileBuilder {

    private static final int COUNT_OF_COLUMNS = 4;

    @Autowired
    private UserService userService;

    private static void createSheetWithData(Workbook workbook, String sheetName, Object[][] data) {
        Sheet sheet = workbook.createSheet(sheetName);
        sheet.setColumnWidth(1, 20000);
        sheet.setColumnWidth(2, 5000);
        sheet.setColumnWidth(3, 5000);

        CellStyle defaultStyle = workbook.createCellStyle();
        defaultStyle.setBorderTop(BorderStyle.THIN);
        defaultStyle.setBorderBottom(BorderStyle.THIN);
        defaultStyle.setBorderLeft(BorderStyle.THIN);
        defaultStyle.setBorderRight(BorderStyle.THIN);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.cloneStyleFrom(defaultStyle);
        Font boldFont = workbook.createFont();
        boldFont.setBold(true);
        headerStyle.setFont(boldFont);

        for (int i = 0; i < data.length; i++) {
            Row row = sheet.createRow(i);
            for (int j = 0; j < data[i].length; j++) {
                Cell cell = row.createCell(j);

                if (data[i][j] instanceof String) {
                    cell.setCellValue((String) data[i][j]);
                } else {
                    cell.setCellValue(Double.parseDouble(String.valueOf(data[i][j])));
                }
                cell.setCellStyle(i == 0 ? headerStyle : defaultStyle);
            }
        }
    }

    @Override
    public byte[] createReportFile(VrpSolution solution) {
        try (Workbook workbook = new XSSFWorkbook()) {
            List<Route> routes = solution.getRoutes();
            for (int i = 0; i < routes.size(); i++) {
                List<Node> nodes = routes.get(i).getNodes();
                Object[][] sheetData = createSheetData(nodes);
                createSheetWithData(workbook, String.format("ТС №%d", i + 1), sheetData);
            }
            return convertExcelToByteArray(workbook);
        } catch (IOException e) {
            throw new ReportFileBuildingException(e.getMessage(), e);
        }
    }

    private byte[] convertExcelToByteArray(Workbook workbook) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);
        return bos.toByteArray();
    }

    private static Object[][] createSheetData(List<Node> nodes) {
        Object[][] sheetData = new Object[nodes.size() + 1][COUNT_OF_COLUMNS];
        sheetData[0] = new String[]{"№", "Адрес", "Загрузка", "Поставлено"};
        for (int j = 0; j < nodes.size(); j++) {
            Node node = nodes.get(j);
            Object[] rowData = {
                    j + 1,
                    node.getAddress().oneStringAddress(),
                    node.getLoad(),
                    node.getSupplied()
            };
            sheetData[j + 1] = rowData;
        }
        return sheetData;
    }

}

