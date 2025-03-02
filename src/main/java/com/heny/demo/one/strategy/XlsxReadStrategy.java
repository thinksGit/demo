package com.heny.demo.one.strategy;

import com.heny.demo.one.entity.Location;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component("xlsx")
public class XlsxReadStrategy implements FileReadStrategy {
    @Override
    public List<Location> readFile(MultipartFile file) {
        List<Location> locations = new ArrayList<>();
        try (InputStream fis = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Location location = buildLocation(row);
                locations.add(location);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return locations;
    }

    @NotNull
    private Location buildLocation(Row row) {
        if (row.getLastCellNum() != 4) {
            return null;
        }
        Location location = new Location();
        location.setIndex(row.getCell(0).getStringCellValue());
        location.setX(row.getCell(1).getNumericCellValue());
        location.setY(row.getCell(2).getNumericCellValue());
        location.setZ(row.getCell(3).getNumericCellValue());
        return location;
    }
}