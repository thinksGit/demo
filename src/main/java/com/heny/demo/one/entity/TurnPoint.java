package com.heny.demo.one.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

/**
 * @author :Yozuru
 * @since :2024/9/14 17:19
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TurnPoint {
    private Integer id;
    private Point coordinate;
    private String index;
}
