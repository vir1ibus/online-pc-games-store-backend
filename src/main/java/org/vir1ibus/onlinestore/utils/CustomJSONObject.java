package org.vir1ibus.onlinestore.utils;

import org.json.JSONObject;

public class CustomJSONObject {
    public JSONObject toJSONObject() { return new JSONObject(this); }

    public JSONObject toMinimalJSONObject() {
        return new JSONObject(this);
    }

    public JSONObject toSelectMinimalJSONObject() { return new JSONObject(this); }
}
