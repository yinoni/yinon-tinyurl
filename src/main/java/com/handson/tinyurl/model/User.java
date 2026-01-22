package com.handson.tinyurl.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "users")
public class User {
    @Id
    private String id;

    private String username;

    private Integer allUserClicks;

    private Map<String, ClickData> shorts;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAllUserClicks() {
        return allUserClicks;
    }

    public void setAllUserClicks(Integer allUserClicks) {
        this.allUserClicks = allUserClicks;
    }

    public Map<String, ClickData> getShorts() {
        return shorts;
    }

    public void setShorts(Map<String, ClickData> shorts) {
        this.shorts = shorts;
    }
}
