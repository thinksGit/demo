package com.heny.demo.one.strategy;

import com.heny.demo.one.entity.Location;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component("txt")
public class TxtReadStrategy implements FileReadStrategy {
    @Override
    public List<Location> readFile(MultipartFile file) {
        List<Location> locations = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                Location location = FileReadStrategy.buildLocationBy4Array(parts);
                if (location == null) continue;
                locations.add(location);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return locations;
    }
}