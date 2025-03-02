package com.heny.demo.one.service;

import com.heny.demo.one.entity.Location;
import com.heny.demo.one.converter.LocationConverter;
import com.heny.demo.one.entity.TurnPoint;
import com.heny.demo.one.mapper.TurnPointMapper;
import com.heny.demo.one.strategy.FileReadContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TurnPointService {

    private final FileReadContext fileReadContext;

    private final TurnPointMapper turnPointMapper;

    public Boolean uploadTurnPoint(MultipartFile turnPointFile) {
        List<Location> locations = fileReadContext.executeStrategy(turnPointFile);
        List<TurnPoint> locationList = LocationConverter.toLocationList(locations);
        // 最好的方式，使用批量插入
        for (TurnPoint turnPoint : locationList) {
            turnPointMapper.insert(turnPoint);
        }
        return true;
    }
}
