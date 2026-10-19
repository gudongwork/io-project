package com.heima.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utils.IoUtil;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/video")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class VideoController {
    private final IoUtil ioUtil;

    @Value("${dir.init.path}")
    private String initPath;

    @GetMapping("/default")
    public String getDefaultPath() {
        return initPath;
    }
    // 2. 获取文件夹里面所有视频文件名

    @GetMapping("/dirs")
    public List<String> getDirs(String path) {
        if(path ==null || path.isEmpty()){
            return ioUtil.getDirPaths(initPath);
        }
        List<String> dirs = ioUtil.getDirPaths(path);
        return dirs;
    }

    @GetMapping("/files")
    public List<String> getFiles(String path) {
        if(path ==null || path.isEmpty()){
            return ioUtil.getFileNames(initPath);
        }
        List<String> files = ioUtil.getFileNames(path);
        return files;
    }

    // 流式播放接口，支持拖拽进度条 HTTP 206
    @GetMapping("/stream")
    public ResponseEntity<Void> streamVideo(
            @RequestParam String path,
            @RequestHeader(value = "Range", required = false) String rangeHeader,
            HttpServletResponse response
    ) throws IOException {
        Path videoFile = null;
        if(path == null|| path.isEmpty()){
            videoFile = Paths.get(initPath);
        }else {
            videoFile = Paths.get(path);
        }
        if (!Files.exists(videoFile) || !Files.isRegularFile(videoFile)) {
            return ResponseEntity.notFound().build();
        }
        long fileSize = Files.size(videoFile);

        response.setContentType("video/mp4");
        // 无Range，返回完整视频
        if (rangeHeader == null) {
            Files.copy(videoFile, response.getOutputStream());
            response.getOutputStream().flush();
            return ResponseEntity.ok().build();
        }

        // 解析range头 bytes=start-end
        String[] rangeParts = rangeHeader.replace("bytes=", "").split("-");
        long start = Long.parseLong(rangeParts[0]);
        long end = rangeParts.length > 1 && !rangeParts[1].isEmpty()
                ? Long.parseLong(rangeParts[1])
                : fileSize - 1;
        end = Math.min(end, fileSize - 1);
        long contentLength = end - start + 1;

        // 设置206分段响应头
        response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
        response.setHeader(HttpHeaders.CONTENT_RANGE, "bytes " + start + "-" + end + "/" + fileSize);
        response.setHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(contentLength));

        // 流式读取，只读取需要的片段，不加载整个文件到内存
        try (RandomAccessFile raf = new RandomAccessFile(videoFile.toFile(), "r")) {
            raf.seek(start);
            byte[] buffer = new byte[8192]; // 8k缓冲区
            long remain = contentLength;
            int len;
            while (remain > 0 && (len = raf.read(buffer, 0, (int) Math.min(buffer.length, remain))) != -1) {
                response.getOutputStream().write(buffer, 0, len);
                remain -= len;
            }
            response.getOutputStream().flush();
        }
        return null;
    }
}
