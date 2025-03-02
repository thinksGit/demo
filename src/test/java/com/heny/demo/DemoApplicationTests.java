package com.heny.demo;

import com.heny.demo.one.service.TurnPointService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private TurnPointService turnPointService;

    @Test
    void contextLoads() throws IOException {
        File file = new File("D:\\work\\idea\\heny-demo\\file\\J1-J8.CSV");
        byte[] fileContent = Files.readAllBytes(file.toPath());

        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "turnPointFile", // 文件名
                "J1-J8.CSV",     // 原始文件名
                MediaType.TEXT_PLAIN_VALUE, // 文件内容类型
                fileContent      // 文件内容的字节数组
        );
        turnPointService.uploadTurnPoint(mockMultipartFile);
    }

}
