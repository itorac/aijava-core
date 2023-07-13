package com.aijava.core.common.plugins;

import java.io.InputStream;
import java.util.List;

/**
 * @author Martin saysocool@foxmail.com
 * @version 1.0.0
 * @ClassName FilePluginInterface.java
 * @Description 文件插件接口
 * @createTime 2022 年01 月04 日 13:49
 */
public interface FilePluginInterface {

    /**
     * 文件路径上传
     *
     * @param filePath
     * @param key
     * @return
     */
    String pathUpload(String filePath, String key);

    /**
     * 文件流上传
     *
     * @param inputStream
     * @param key
     * @return
     */
    String inputStreamUpload(InputStream inputStream, String key);

    /**
     * 删除文件
     *
     * @param key
     */
    void deleteFile(List<String> key);
}
