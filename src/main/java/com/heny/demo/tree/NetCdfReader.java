package com.heny.demo.tree;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class NetCdfReader {

    public static void main(String[] args) {
        String filePath = "file/3/题三附件_NMC_HENAN_RH-AVG_L2M_G005_20240312000000_H24_H1.NC";
        List<String> fileNameList = NetCdfFileNameGen.genFileName(filePath);
        String outputPath = "file/3/";
        for (String fileName : fileNameList) {
            NetCdfDataHandler netCdfDataHandler = new NetCdfDataHandler();
            netCdfDataHandler.readAndWriteToExcel(filePath, outputPath + fileName);
        }
    }
}