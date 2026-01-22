package com.handson.tinyurl.model;

import java.util.Map;

public final class UserBuilder {
    private String id;
    private String username;
    private Integer allUserClicks;
    private Map<String, ClickData> shorts;

    private UserBuilder() {
    }

    public static UserBuilder anUser() {
        return new UserBuilder();
    }

    public UserBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public UserBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder withAllUserClicks(Integer allUserClicks) {
        this.allUserClicks = allUserClicks;
        return this;
    }

    public UserBuilder withShorts(Map<String, ClickData> shorts) {
        this.shorts = shorts;
        return this;
    }

    public User build() {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setAllUserClicks(allUserClicks);
        user.setShorts(shorts);
        return user;
    }
}
