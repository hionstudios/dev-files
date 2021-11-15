package com.hionstudios;

import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

public interface ResponseUtil {
    static void noCache(HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Cache-Control", "no-store");
        response.setDateHeader("Expires", 0);
        response.setHeader("Pragma", "no-cache");
    }

    static void cache(HttpServletResponse response) {
        response.setHeader(HttpHeaders.CACHE_CONTROL, CacheControl.maxAge(30, TimeUnit.DAYS).getHeaderValue());
    }

    static void json(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }
}
