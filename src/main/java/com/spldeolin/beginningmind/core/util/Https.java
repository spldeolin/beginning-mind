package com.spldeolin.beginningmind.core.util;

import java.lang.reflect.Field;
import java.util.Map;
import org.springframework.http.HttpStatus;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * HTTP请求工具类
 * <pre>
 * 支持服务端发送HTTP请求，包括Get请求，Body为form的Post请求，Body为json的Post请求，
 * 请求的回应将被转化为String类型作为返回值。
 *
 * 这个类推荐与Jsons结合使用。
 * </pre>
 *
 * @author Deolin
 */
@UtilityClass
public class Https {

    private static final String DISGUISED_USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
            "(KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36";

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
        Request request = new Request.Builder().url(url).header("User-Agent", DISGUISED_USER_AGENT).build();
        Response response = client.newCall(request).execute();
        if (HttpStatus.OK.value() != response.code()) {
            throw new RuntimeException(response.message());
        }
        ResponseBody responseBody = response.body();
        if (responseBody == null) {
            return null;
        }
        return responseBody.string();
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
        Request request = new Request.Builder().url(url).post(body).header("User-Agent", DISGUISED_USER_AGENT).build();
        Response response = client.newCall(request).execute();
        if (HttpStatus.OK.value() != response.code()) {
            throw new RuntimeException(response.message());
        }
        ResponseBody responseBody = response.body();
        if (responseBody == null) {
            return null;
        }
        return responseBody.string();
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
                Object key = entry.getKey();
                if (key == null) {
                    continue;
                }
                Object value = entry.getValue();
                if (value == null) {
                    continue;
                }
                form.add(key.toString(), value.toString());
            }
        } else {
            for (Field field : object.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(object);
                if (value != null) {
                    form.add(field.getName(), value.toString());
                }
            }
        }
        okhttp3.RequestBody body = form.build();
        Request request = new Request.Builder().url(url).post(body).header("User-Agent", DISGUISED_USER_AGENT).build();
        Response response = client.newCall(request).execute();
        if (HttpStatus.OK.value() != response.code()) {
            throw new RuntimeException(response.message());
        }
        ResponseBody responseBody = response.body();
        if (responseBody == null) {
            return null;
        }
        return responseBody.string();
    }

}