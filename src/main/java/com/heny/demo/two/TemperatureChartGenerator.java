package com.heny.demo.two;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class TemperatureChartGenerator {


    public static List<HeatmapGenerator.DataPoint> readExcel(String filePath) {
        List<HeatmapGenerator.DataPoint> dataPoints = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0); // 第一个工作表
            for (Row row : sheet) {
                double lat = Double.valueOf(row.getCell(0).getStringCellValue());
                double lon = Double.valueOf(row.getCell(1).getStringCellValue());
                double temp = Double.valueOf(row.getCell(2).getStringCellValue());
                dataPoints.add(new HeatmapGenerator.DataPoint(lon, lat, temp));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataPoints;
    }

    public static void main(String[] args) {
        List<HeatmapGenerator.DataPoint> data = readExcel("file/3/第三题经纬度数值结果文件.xlsx");
        HeatmapGenerator.generateHeatmap(data, "temperature_map.tiff");
    }

}