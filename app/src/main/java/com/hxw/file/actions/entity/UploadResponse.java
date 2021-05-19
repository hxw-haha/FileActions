package com.hxw.file.actions.entity;

import java.util.List;

public class UploadResponse extends BaseResponse {
    public String rootFilePath;
    public List<String> succeedFileNames;

    @Override
    public String toString() {
        return "UploadResponse{" +
                "rootFilePath='" + rootFilePath + '\'' +
                ", succeedFileNames=" + succeedFileNames +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
