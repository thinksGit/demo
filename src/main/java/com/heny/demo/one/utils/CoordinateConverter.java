package com.heny.demo.one.utils;

import lombok.SneakyThrows;
import org.geotools.geometry.jts.JTS;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.referencing.CRS;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;

public class CoordinateConverter {

    private static final String CGCS2000_CRS = "EPSG:4490"; // CGCS2000坐标系
    private static final String POSTGIS_CRS = "EPSG:4326"; // PostgreSQL常用的WGS84坐标系

    private static GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();

    @SneakyThrows
    public static Point convertCGCS2000ToPostGIS(double x, double y, double z) {
        CoordinateReferenceSystem sourceCRS = CRS.decode(CGCS2000_CRS);
        CoordinateReferenceSystem targetCRS = CRS.decode(POSTGIS_CRS);

        MathTransform transform = CRS.findMathTransform(sourceCRS, targetCRS, true);

        Point point = geometryFactory.createPoint(new Coordinate(x, y, z));
        Point transformedPoint = (Point) JTS.transform(point, transform);

        return transformedPoint;
    }
}