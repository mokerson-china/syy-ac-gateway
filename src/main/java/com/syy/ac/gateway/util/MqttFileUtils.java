package com.syy.ac.gateway.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Arrays;
import java.util.Set;

public class MqttFileUtils {
    public MqttFileUtils() {
    }

    public static void createFolders(String folderName) throws IOException {
        File folder = new File(folderName);
        if (!folder.exists() || !folder.isDirectory()) {
            FileUtils.forceMkdir(folder);
            Path path = Paths.get(folderName);
            if (FilePermissionUtils.isPosixFileSystem()) {
                Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rwx------");
                Files.setPosixFilePermissions(path, perms);
            } else {
                FilePermissionUtils.adjustWindowsFilePermission(path);
            }

        }
    }

    public static void makeFile(String folderName, String fileName) throws IOException {
        if (fileName != null) {
            File file = new File(folderName, fileName);
            if (!file.exists()) {
                FileUtils.touch(file);
                Path path = Paths.get(file.toURI());
                if (FilePermissionUtils.isPosixFileSystem()) {
                    Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rw-------");
                    Files.setPosixFilePermissions(path, perms);
                } else {
                    FilePermissionUtils.adjustWindowsFilePermission(path);
                }

            }
        }
    }

    public static void writeLine(String folderName, String fileName, String content) throws IOException {
        if (fileName != null) {
            File file = new File(folderName, fileName);
            if (file.exists()) {
                FileUtils.writeLines(file, Arrays.asList(content), false);
            }
        }
    }

    public static void deleteFile(String folderName, String fileName) {
        File file = new File(folderName, fileName);
        if (file.exists()) {
            FileUtils.deleteQuietly(file);
        }
    }
}
