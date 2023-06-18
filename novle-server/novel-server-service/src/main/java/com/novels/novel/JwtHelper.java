package com.novels.novel;

/**
 * 拦截
 * @author 王兴
 * @date 2023/6/12 7:05
 */
public class JwtHelper {

    private static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<>();

    public static void addUser(String user) {
        THREAD_LOCAL.set(user);
    }

    public static String getUser() {
        return THREAD_LOCAL.get();
    }

    public static void clearUser(String user) {
        THREAD_LOCAL.remove();
    }

}
