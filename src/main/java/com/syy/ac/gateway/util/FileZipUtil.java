package com.syy.ac.gateway.util;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class FileZipUtil {
    /**
     * 原生代码
     * 将一个图片与一个视频文件压缩到 test.zip 中
     */
    public static void compressFile() throws IOException {
        //压缩文件名称与位置
        OutputStream outputStream = new FileOutputStream("f://test.zip");
        //获取压缩文件输出流
        ZipOutputStream out = new ZipOutputStream(outputStream);
        //被压缩的文件
        FileInputStream fis = new FileInputStream("F://video.mp4");
        //video.mp4在压缩包zip 中的音乐名称
        out.putNextEntry(new ZipEntry("视频.mp4"));
        int len;
        byte[] buffer = new byte[1024];
        // 读入需要下载的文件的内容，打包到zip文件
        while ((len = fis.read(buffer)) > 0) {
            //写入压缩文件输出流中(压缩文件zip中)
            out.write(buffer, 0, len);
        }
        fis.close();

        //第二个被压缩文件
        FileInputStream fis1 = new FileInputStream("F://picture.jpg");
        //picture.jpg在压缩包中的名称 图片.jpg
        out.putNextEntry(new ZipEntry("图片.jpg"));
        int len1;
        byte[] buffer1 = new byte[1024];
        // 读入需要下载的文件的内容，打包到zip文件
        while ((len1 = fis1.read(buffer1)) > 0) {
            out.write(buffer1, 0, len1);
        }
        //关闭流
        out.closeEntry();
        fis1.close();
        out.close();
    }

    /**
     * zip压缩文 解压
     */
    public static void deCompressionFile(String filePath) throws IOException {
        //读取压缩文件
        ZipInputStream in = new ZipInputStream(new FileInputStream(filePath));
        //zip文件实体类
        ZipEntry entry ;
        //遍历压缩文件内部 文件数量
        entry = in.getNextEntry();
        while(entry!= null){
            if(!entry.isDirectory()){
                //文件输出流
                FileOutputStream out = new FileOutputStream( filePath+entry.getName());
                BufferedOutputStream bos = new BufferedOutputStream(out);
                int len;
                byte[] buf = new byte[1024];
                while ((len = in.read(buf)) != -1) {
                    bos.write(buf, 0, len);
                }
                // 关流顺序，先打开的后关闭
                bos.close();
                out.close();
            }
            entry = in.getNextEntry();
        }
    }
}
