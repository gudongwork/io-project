package com.heima;

import org.junit.jupiter.api.Test;
import utils.IoUtil;

import java.util.List;

class IoCommentApplicationTests {
    @Test
    void contextLoads() {
        IoUtil ioUtil = new IoUtil();
        List<String> fileList = ioUtil.getFileNames("D:/1-test-dir/a");
        System.out.println(fileList);
    }
}
