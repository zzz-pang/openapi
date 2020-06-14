package com.play.openapi.web.master.util;

import java.util.HashMap;

public class R extends HashMap {
    private int code;
    private String msg;

    public R( int code, String msg ) {
        super.put("code", code);
        super.put("msg", msg);
    }

    public R( int code ) {
        super.put("code", code);
    }

    public R() {
    }

    public static R ok() {
        return new R(0);
    }

    public static R ok( String msg ) {
        return new R(0, msg);
    }

    public static R error() {
        return new R(1);
    }

    public static R error( String msg ) {
        return new R(1, msg);
    }

    public R put( String k, Object v ) {
        super.put(k, v);
        return this;
    }
}
