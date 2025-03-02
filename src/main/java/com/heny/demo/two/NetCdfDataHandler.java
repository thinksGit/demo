package com.heny.demo.two;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jetbrains.annotations.NotNull;
import ucar.ma2.Array;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;
import ucar.nc2.dataset.NetcdfDatasets;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
public class NetCdfDataHandler {

    @SneakyThrows
    public void readAndWriteToExcel(String ncFilePath, String excelFilePath) {
        try (NetcdfFile netcdfFile = NetcdfDatasets.openFile(ncFilePath, null);
             Workbook workbook = new XSSFWorkbook();
             FileOutputStream fileOut = new FileOutputStream(excelFilePath)) {

            Sheet sheet = workbook.createSheet("NC Data");
            writeDataToSheet(netcdfFile, sheet);
            workbook.write(fileOut);
        }
    }

    @SneakyThrows
    private void writeDataToSheet(NetcdfFile netcdfFile, Sheet sheet) {
        Variable latVar = netcdfFile.findVariable("lat");
        Variable lonVar = netcdfFile.findVariable("lon");
        Variable dataVar = netcdfFile.findVariable("data");

        if (latVar == null || lonVar == null || dataVar == null) {
            log.error("Required variables (lat, lon, data) not found in the NetCDF file.");
            return;
        }

        Array latArray = latVar.read();
        Array lonArray = lonVar.read();
        Array dataArray = dataVar.read();
        writeData(latArray, lonArray, dataArray, sheet);
    }

    private void writeData(Array latArray, Array lonArray, Array dataArray, Sheet sheet) {
        int latSize = latArray.getShape()[0];
        int lonSize = lonArray.getShape()[0];
        int rowNum = 0;

        for (int i = 0; i < latSize; i++) {
            for (int j = 0; j < lonSize; j++) {
                Row row = sheet.createRow(rowNum++);
                createCell(row, 0, formatValue(latArray, i));
                createCell(row, 1, formatValue(lonArray, j));
                createCell(row, 2, formatValue(dataArray, j));
            }
        }
    }

    @NotNull
    private static BigDecimal formatValue(Array lonArray, int j) {
        return BigDecimal.valueOf(lonArray.getDouble(j)).setScale(2, RoundingMode.HALF_UP);
    }

    private void createCell(Row row, int columnIndex, BigDecimal value) {
        Cell cell = row.createCell(columnIndex);
        cell.setCellValue(value.toPlainString());
    }
}