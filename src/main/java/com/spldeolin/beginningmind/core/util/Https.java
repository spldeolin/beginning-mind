package com.spldeolin.beginningmind.core.util;

import java.lang.reflect.Field;
import java.util.Map;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * HTTP请求工具类
 * <pre>
 * 支持服务端发送HTTP请求，包括Get请求，Body为form的Post请求，Body为json的Post请求，
 * 请求的回应将被转化为String类型作为返回值。
 *
 * 这个类推荐与JsonUtil结合使用。
 * </pre>
 *
 * @author Deolin
 */
@UtilityClass
public class Https {

    private static OkHttpClient client;

    static {
        client = new OkHttpClient();
    }

    /**
     * 发送一个GET请求
     * <pre>
     * e.g.: Https.get("http://spldeolin.com/reply/123?page=5");
     * </pre>
     */
    @SneakyThrows
    public static String get(String url) {
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        // response.body() return null impossibly
        return response.body().string();
    }

    /**
     * 发送一个POST请求，请求Body的格式是JSON
     * <pre>
     * e.g.: Https.post("http://spldeolin.com/post/start", Jsons.toJson(userDTO));
     * </pre>
     */
    @SneakyThrows
    public static String postJson(String url, String json) {
        okhttp3.RequestBody body = okhttp3.RequestBody.create(MediaType.parse("application/json"), json);
        Request request = new Request.Builder().url(url).post(body).build();
        Response response = client.newCall(request).execute();
        // response.body() return null impossibly
        return response.body().string();
    }

    /**
     * 发送一个POST请求，请求Body的格式是JSON
     * <pre>
     * e.g.: Https.post("http://spldeolin.com/post/start", userDTO);
     * </pre>
     */
    public static String postJson(String url, Object object) {
        return postJson(url, Jsons.toJson(object));
    }

    /**
     * 发送一个POST请求，请求Body的格式是Form表单
     * <pre>
     * e.g.: Https.post("http://spldeolin.com/post/like", userDTO);
     * </pre>
     */
    @SneakyThrows
    public static String postForm(String url, Object object) {
        FormBody.Builder form = new FormBody.Builder();
        if (object instanceof Map) {
            Map<?, ?> map = (Map) object;
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                form.add(entry.getKey().toString(), entry.getValue().toString());
            }
        } else {
            for (Field field : object.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                form.add(field.getName(), field.get(object).toString());
            }
        }
        okhttp3.RequestBody body = form.build();
        Request request = new Request.Builder().url(url).post(body).build();
        Response response = client.newCall(request).execute();
        // response.body() return null impossibly
        return response.body().string();
    }

}