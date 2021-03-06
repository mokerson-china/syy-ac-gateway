package com.syy.ac.gateway.util;

import com.syy.ac.gateway.config.AgentConfig;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Arrays;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;

public class MqttFileUtils {
    private static final String PROPERTY_FILE_PATH = "iotagentXIANHONG.properties";

    private static final Logger logger = LoggerFactory.getLogger(MqttFileUtils.class);
    public MqttFileUtils() {
    }

    /**
     * 设置备份目录
     *
     * @param config 配置信息
     */
    private static void initialBackupPath(AgentConfig config) {
        String backupFolder = config.getBackupFolder();
        if (backupFolder == null) {
            throw new RuntimeException("Can not locate backup folder");
        } else {
            File backupFolderDir = new File(backupFolder);
            if (!backupFolderDir.exists() || !backupFolderDir.isDirectory()) {
                if (backupFolderDir.exists() && !backupFolderDir.isDirectory()) {
                    logger.error("Backup folder is not a directory, agent can not use");
                    throw new RuntimeException("Failed to create backup folder");
                } else {
                    try {
                        MqttFileUtils.createFolders(backupFolder);
                    } catch (IOException exception) {
                        logger.error("Backup folder create failed.", exception);
                        throw new RuntimeException("Failed to create backup folder", exception);
                    }
                }
            }
        }
    }

    /**
     * 读取配置文件
     *
     * @return 配置内容
     */
    public static Properties readAgentProperty(String fileName) {
        InputStreamReader propertyStream = null;
        Properties properties =null;
        try {
            propertyStream = new InputStreamReader(Objects.requireNonNull(MqttFileUtils.class.getClassLoader().getResourceAsStream(fileName)),"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            properties = new Properties();

            properties.load(propertyStream);
        } catch (IOException e) {
            logger.error("Failed to load property", e);
            throw new RuntimeException("Property load failed");
        } finally {
            if (propertyStream != null) {
                try {
                    propertyStream.close();
                } catch (IOException e) {
                    logger.error("Failed to close property stream", e);
                }
            }

        }
        return properties;
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
