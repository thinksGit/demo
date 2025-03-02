package com.heny.demo.one.mapper;

import com.heny.demo.one.entity.TurnPoint;
import org.apache.ibatis.annotations.*;

@Mapper
public interface TurnPointMapper {

    @Insert("INSERT INTO turn_point (coordinate, index) VALUES (#{coordinate}, #{index})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(TurnPoint turnPoint);
}