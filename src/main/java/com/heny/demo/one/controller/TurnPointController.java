package com.heny.demo.one.controller;

import com.heny.demo.one.service.TurnPointService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class TurnPointController {

    private final TurnPointService turnPointService;

    /**
     * 上传点位文件
     *
     * todo 缺少包装类的异常处理
     *
     * @param turnPointFile
     * @return
     */
    @PostMapping("/upload")
    public Boolean uploadTurnPoint(MultipartFile turnPointFile) {
        return turnPointService.uploadTurnPoint(turnPointFile);
    }
}