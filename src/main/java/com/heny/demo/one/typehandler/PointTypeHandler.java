package com.heny.demo.one.typehandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.io.WKTWriter;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PointTypeHandler extends BaseTypeHandler<Point> {

    private final WKTReader wktReader = new WKTReader();
    private final WKTWriter wktWriter = new WKTWriter();

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Point parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, wktWriter.write(parameter));
    }

    @Override
    public Point getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String wkt = rs.getString(columnName);
        if (wkt == null) {
            return null;
        }
        try {
            return (Point) wktReader.read(wkt);
        } catch (ParseException e) {
            throw new SQLException("Failed to parse WKT", e);
        }
    }

    @Override
    public Point getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String wkt = rs.getString(columnIndex);
        if (wkt == null) {
            return null;
        }
        try {
            return (Point) wktReader.read(wkt);
        } catch (ParseException e) {
            throw new SQLException("Failed to parse WKT", e);
        }
    }

    @Override
    public Point getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String wkt = cs.getString(columnIndex);
        if (wkt == null) {
            return null;
        }
        try {
            return (Point) wktReader.read(wkt);
        } catch (ParseException e) {
            throw new SQLException("Failed to parse WKT", e);
        }
    }
}