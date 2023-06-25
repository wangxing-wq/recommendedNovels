package com.novels.common.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 王兴
 * @date 2023/6/24 15:31
 */
public class UserInfo  {

    public static final String USER_ID = "userId";
    public static final String USER_NAME = "username";
    public static final String ROLES = "roles";

    private String id;
    private String username;
    private String roles;
    private Map<String,Object> payload;

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

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public Map<String, Object> getPayload() {
        return toPayload();
    }

    public void setPayload(Map<String, Object> payload) {
        this.payload = payload;
    }

    public Map<String,Object> toPayload() {
        if (CollectionUtils.isEmpty(payload)) {
            Map<String,Object> payload = new HashMap<>();
            payload.put(USER_ID, id);
            payload.put(USER_NAME, username);
            payload.put(ROLES,roles);
            this.payload = payload;
            return Collections.unmodifiableMap(payload);
        }
        return payload;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", roles='" + roles + '\'' +
                ", payload=" + payload +
                '}';
    }
}
