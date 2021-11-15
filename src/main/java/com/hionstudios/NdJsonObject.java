package com.hionstudios;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;

public class NdJsonObject extends JSONObject {
    public NdJsonObject(String type) {
        this.put("type", type);
    }

    @Override
    public NdJsonObject put(String key, int value) throws JSONException {
        super.put(key, value);
        return this;
    }

    @Override
    public NdJsonObject put(String key, Collection<?> value) throws JSONException {
        super.put(key, value);
        return this;
    }

    public NdJsonObject put(String key, Object value) throws JSONException {
        super.put(key, value);
        return this;
    }
}
