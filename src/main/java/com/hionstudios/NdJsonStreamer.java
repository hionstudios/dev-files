package com.hionstudios;

import org.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class NdJsonStreamer {
    private PrintWriter out;

    public NdJsonStreamer(HttpServletResponse res) {
        try {
            this.out = res.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ResponseUtil.json(res);
        start();
    }

    public void stream(JSONObject json) {
        out.println(json);
        out.flush();
    }

    private void start() {
    }

    public void end() {
        out.close();
    }
}
