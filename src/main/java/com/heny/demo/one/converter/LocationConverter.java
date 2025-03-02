package com.heny.demo.one.converter;

import com.heny.demo.one.entity.Location;
import com.heny.demo.one.entity.TurnPoint;
import com.heny.demo.one.utils.CoordinateConverter;
import org.locationtech.jts.geom.Point;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface LocationConverter {
    LocationConverter INSTANCE = Mappers.getMapper(LocationConverter.class);

    TurnPoint toTurnPoint(Location location);

    static List<TurnPoint> toLocationList(List<Location> locationList) {
        return locationList.stream()
                .map(location -> {
                    TurnPoint turnPoint = INSTANCE.toTurnPoint(location);
                    Point point = CoordinateConverter.convertCGCS2000ToPostGIS(location.getX(), location.getY(), location.getZ());
                    turnPoint.setCoordinate(point);
                    return turnPoint;
                })
                .collect(Collectors.toList());
    }

}