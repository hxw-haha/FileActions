package com.hxw.file.actions.entity;

import java.io.Serializable;

public class BaseResponse implements Serializable {
    public static final int SUCCEED = 0;
    public static final int FAILED = -1;
    public int code;
    public String msg;
}
