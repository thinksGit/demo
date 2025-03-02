package com.heny.demo.one.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    /**
     * 地标索引，用于唯一标识一个地标的字符串
     */
    private String index;

    /**
     * X坐标，表示地标的X轴位置
     */
    private Double x;

    /**
     * Y坐标，表示地标的Y轴位置
     */
    private Double y;

    /**
     * Z坐标，表示地标的Z轴位置
     */
    private Double z;
}