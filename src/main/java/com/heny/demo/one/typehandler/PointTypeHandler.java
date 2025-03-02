package com.heny.demo.one.typehandler;

import com.heny.demo.one.utils.CoordinateConverter;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.locationtech.jts.geom.Point;
import org.postgis.PGgeometry;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * mybatis 几何字段类型转换处理器 - PostGIS - Point
 */
@MappedTypes({Point.class})
public class PointTypeHandler extends BaseTypeHandler<Point> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Point parameter, JdbcType jdbcType) throws SQLException {
        PGgeometry pGgeometry = new PGgeometry(parameter.toText());
        ps.setObject(i, pGgeometry);
    }

    @Override
    public Point getNullableResult(ResultSet rs, String columnName) throws SQLException {
        PGgeometry pgGeometry = (PGgeometry) rs.getObject(columnName);
        if (pgGeometry == null) return null;
        org.postgis.Point firstPoint = pgGeometry.getGeometry().getFirstPoint();
        org.locationtech.jts.geom.Point point = CoordinateConverter.convertCGCS2000ToPostGIS(firstPoint.getX(), firstPoint.getY(), firstPoint.getZ());
        return point;
    }

    @Override
    public Point getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        PGgeometry pgGeometry = (PGgeometry) rs.getObject(columnIndex);
        if (pgGeometry == null) return null;
        org.postgis.Point firstPoint = pgGeometry.getGeometry().getFirstPoint();
        org.locationtech.jts.geom.Point point = CoordinateConverter.convertCGCS2000ToPostGIS(firstPoint.getX(), firstPoint.getY(), firstPoint.getZ());
        return point;
    }

    @Override
    public Point getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        PGgeometry pgGeometry = (PGgeometry) cs.getObject(columnIndex);
        if (pgGeometry == null) return null;
        org.postgis.Point firstPoint = pgGeometry.getGeometry().getFirstPoint();
        org.locationtech.jts.geom.Point point = CoordinateConverter.convertCGCS2000ToPostGIS(firstPoint.getX(), firstPoint.getY(), firstPoint.getZ());
        return point;
    }
}
