package com.heny.demo.one.strategy;

import com.heny.demo.one.entity.Location;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class FileReadContext {

    /**
     * key为文件后缀名，value为对应的策略
     */
    private final Map<String, FileReadStrategy> keyIsFileExtNameOfstrategyMap;

    public List<Location> executeStrategy(MultipartFile file) {
        String fileExtension = getFileExtension(file);
        FileReadStrategy strategy = keyIsFileExtNameOfstrategyMap.get(fileExtension.toLowerCase(Locale.ROOT));
        if (strategy == null) {
            throw new IllegalArgumentException("No strategy found for file extension: " + fileExtension);
        }
        return strategy.readFile(file);
    }

    private String getFileExtension(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            throw new IllegalArgumentException("File does not have an original filename");
        }
        int lastDotIndex = originalFilename.lastIndexOf('.');
        if (lastDotIndex == -1) {
            throw new IllegalArgumentException("File name does not contain an extension: " + originalFilename);
        }
        return originalFilename.substring(lastDotIndex + 1);
    }
}
