package utils;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

@Data
@Component
public class IoUtil {
    public List<String> getDirPaths(String currentDir){
        System.out.println("currentDir: " + currentDir);
        List<String> dirNames = new LinkedList<>();
        File root = new File(currentDir);
        if (!root.exists() || !root.isDirectory()) {
            System.out.println("目录不存在");
            return null;
        }
        File[] subDirs = root.listFiles(File::isDirectory);
        if(subDirs == null) return null;

        for (File dir : subDirs) {
            String FileName = dir.getAbsolutePath();
            if (FileName.contains("$RECYCLE.BIN")) continue;
            dirNames.add(FileName.replace("\\","/"));
        }
        return dirNames;
    }
    public List<String> getFileNames(String currentDir){
        List<String> fileNames = new LinkedList<>();
        try (Stream<Path> stream = Files.list(Paths.get(currentDir))) {
            stream.filter(Files::isRegularFile) // 过滤掉文件夹
                    .forEach(path -> fileNames.add(path.getFileName().toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(fileNames);
        return fileNames;
    }

}
