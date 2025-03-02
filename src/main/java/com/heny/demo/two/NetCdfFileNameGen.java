package com.heny.demo.two;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import ucar.ma2.Array;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;
import ucar.nc2.dataset.NetcdfDatasets;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 没有搞明白按照时间生产的文件和经纬度数值直接的关系，可能是我是用代码读取nc文件后看的内容，没有用
 * 专业软件打开导致的，但是先不考虑这个了,但是考虑到代码和逻辑的易读性，应该把文件名字和数据读取解耦
 *
 * @date 2025年2月25日21:40:01
 */
@Slf4j
public class NetCdfFileNameGen {
    @SneakyThrows
    public static List<String> genFileName(String fileName) {
        try (NetcdfFile netcdfFile = NetcdfDatasets.openFile(fileName, null)) {
            Variable member = netcdfFile.findVariable("member");
            Variable time = netcdfFile.findVariable("time");
            Variable level = netcdfFile.findVariable("level");
            Variable dtime = netcdfFile.findVariable("dtime");
            Array memberArray = member.read();
            Array timeArray = time.read();
            Array levelArray = level.read();
            Array dtimeArray = dtime.read();
            List<String> fileNameList = buildFileNameList(dtimeArray, memberArray, timeArray, levelArray);
            return fileNameList;
        }
    }

    @NotNull
    private static List<String> buildFileNameList(Array dtimeArray, Array memberArray, Array timeArray, Array levelArray) {
        String prefix = "NMC_HENAN_";
        Object[] memberObjArray = (Object[]) memberArray.get1DJavaArray(String.class);
        Object[] levelObjArray = (Object[]) levelArray.get1DJavaArray(String.class);
        String timeStr = formatTime(timeArray);
        int[] shape = dtimeArray.getShape();
        List<String> fileNameList = IntStream.range(0, dtimeArray.getShape()[0] - 1)
                .mapToObj(index -> {
                    return prefix + memberObjArray[0] + "_"
                            + levelObjArray[0] + "_"
                            + "G005_"
                            + timeStr + "_"
                            + "H24_H1_"
                            + dtimeArray.getInt(index) + ".xlsx";
                }).collect(Collectors.toList());
        return fileNameList;
    }

    private static String formatTime(Array timeArray) {
        Object[] timeObjArray = (Object[]) timeArray.get1DJavaArray(String.class);
        DateTime time = DateUtil.parse((String) timeObjArray[0]);
        String timeStr = DateUtil.format(time, "yyyyMMddHHmmss");
        return timeStr;
    }
}
