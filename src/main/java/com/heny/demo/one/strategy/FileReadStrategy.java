package com.heny.demo.one.strategy;

import com.heny.demo.one.entity.Location;
import org.jetbrains.annotations.Nullable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileReadStrategy {
    List<Location> readFile(MultipartFile file);

    /**
     * 长度为4的数组构建Location
     *
     * @param parts
     * @return
     */
    @Nullable
    static Location buildLocationBy4Array(String[] parts) {
        if (parts.length != 4) {
            return null;
        }
        Location location = new Location();
        location.setIndex(parts[0]);
        location.setX(Double.parseDouble(parts[1]));
        location.setY(Double.parseDouble(parts[2]));
        location.setZ(Double.parseDouble(parts[3]));
        return location;
    }
}