package com.handson.tinyurl.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ClickData {

    private Map<String, Integer> clicks;

    public ClickData() {
        this.clicks = new HashMap<>();
    }

    public Map<String, Integer> getClicks() {
        return clicks;
    }

    public void setClicks(Map<String, Integer> clicks) {
        this.clicks = clicks;
    }
}
