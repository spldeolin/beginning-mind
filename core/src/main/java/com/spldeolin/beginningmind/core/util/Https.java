package com.spldeolin.beginningmind.core.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.http.HttpStatus;
import com.google.common.collect.Lists;
import com.spldeolin.beginningmind.core.common.BizException;
import lombok.extern.log4j.Log4j2;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * HTTP请求工具类
 * <pre>
 * 支持：
 * RESTful GET请求
 * RESTful POST请求
 * 提交form表单
 * 根据URL获取图片
 * </pre>
 *
 * @author Deolin
 */
@Log4j2
public class Https {

    private static final String[] FAKE_USER_AGENTS;

    private static OkHttpClient client = new OkHttpClient();

    static {
        Request request = new Request.Builder().url("https://fake-useragent.herokuapp.com/browsers/0.1.11").build();
        List<String> fake;
        try {
            Response response = doRequest(request);
            String responseBody = ensureJsonAndGetBody(response);

            Map<String, Map<String, List<String>>> responseBodyMap = Jsons.toObject(responseBody, Map.class);
            Map<String, List<String>> browserMap = responseBodyMap.get("browsers");

            fake = browserMap.get("chrome");
            fake.addAll(browserMap.get("opera"));
            fake.addAll(browserMap.get("firefox"));
            fake.addAll(browserMap.get("internetexplorer"));
            fake.addAll(browserMap.get("safari"));

            log.info("获取到 {}个fake User-Agent", fake.size());
        } catch (Exception e) {
            log.warn("获取fake User-Agent失败，使用默认fake User-Agent");
            fake = Lists.newArrayList("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like"
                    + " Gecko) Chrome/73.0.3683.86 Safari/537.36");
        }

        FAKE_USER_AGENTS = fake.toArray(new String[0]);
    }

    /**
     * 发送一个GET请求，获取JSON
     * <pre>
     * e.g.: Https.getJson("https://api.live.bilibili.com/ajax/feed/count");
     * response不是JSON时，将会抛出异常
     * </pre>
     */
    public static String get(String url) {
        log.info("发送GET请求 {}", url);
        try {
            Request request = buildGetRequest(url);
            Response response = doRequest(request);

            return ensureJsonAndGetBody(response);
        } catch (IOException e) {
            log.error("GET请求失败 {}", url);
            throw new BizException("GET请求失败");
        }
    }

    /**
     * 发送一个GET请求，获取图片
     * <pre>
     * e.g.: Https.getImage("https://spldeolin.com/images/favicon.png");
     * response不是图片时，将会抛出异常
     * </pre>
     */
    public static BufferedImage getAsImage(String url) {
        log.info("发送GET请求 {}", url);
        try {
            Request request = buildGetRequest(url);

            Response response = doRequest(request);
            if (!Nulls.toEmpty(response.header("Content-Type")).startsWith("image/")) {
                throw new RuntimeException("请求结果不是图片");
            }

            ResponseBody body = response.body();
            if (body == null) {
                throw new RuntimeException("图片不存在");
            }
            return ImageIO.read(body.byteStream());
        } catch (IOException e) {
            log.error("GET请求失败 {}", url);
            throw new BizException("GET请求失败");
        }
    }

    /**
     * 发送一个POST请求，获取JSON，请求Body是JSON
     * <pre>
     * e.g.: Https.post("http://spldeolin.com/post/start", Jsons.toJson(userDTO));
     * </pre>
     */
    public static String postJson(String url, String json) {
        try {
            Request request = buildJsonPostRequest(url, json);
            Response response = doRequest(request);

            return ensureJsonAndGetBody(response);
        } catch (IOException e) {
            log.error("POST请求失败 {}", url);
            throw new BizException("POST请求失败");
        }
    }

    /**
     * 发送一个POST请求，请求Body的格式是Form表单
     * <pre>
     * e.g.: Https.post("http://spldeolin.com/post/like", userDTO);
     * </pre>
     */
    public static String postForm(String url, Object object) {
        FormBody.Builder form = new FormBody.Builder();
        try {
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
        } catch (IllegalArgumentException | IllegalAccessException e) {
            log.error("解析对象失败", e);
            throw new BizException("解析对象失败");
        }

        try {
            Request request = new Request.Builder().url(url).post(form.build())
                    .header("UserEntity-Agent", fakeUserAgent())
                    .build();
            Response response = doRequest(request);

            return ensureJsonAndGetBody(response);
        } catch (IOException e) {
            log.error("解析对象失败", e);
            throw new BizException("解析对象失败");
        }
    }

    /**
     * 为GET请求，构造request对象
     */
    private static Request buildGetRequest(String url) {
        return new Request.Builder()
                .url(url)
                .header("UserEntity-Agent", fakeUserAgent())
                .build();
    }

    /**
     * 为POST请求，构造body是JSON的request对象
     */
    private static Request buildJsonPostRequest(String url, String json) {
        okhttp3.RequestBody body = okhttp3.RequestBody.create(MediaType.parse("application/json"), json);
        return new Request.Builder()
                .url(url)
                .post(body)
                .header("UserEntity-Agent", fakeUserAgent())
                .build();
    }

    /**
     * 确保response的body为JSON，并返回body的String形式
     */
    private static String ensureJsonAndGetBody(Response response) throws IOException {
        if (!Nulls.toEmpty(response.header("Content-Type")).startsWith("application/json")) {
            throw new RuntimeException("请求结果不是JSON");
        }
        ResponseBody body = response.body();
        if (body == null) {
            throw new RuntimeException("response body为空");
        }
        return body.string();
    }

    /**
     * 发送请求，获取response，非200则重试5次，第5次后依然非200则抛出异常
     */
    private static Response doRequest(Request request) throws IOException {
        // 发送请求，获取response，非200则重试5次
        Response response = null;
        for (int i = 0; i < 5; i++) {
            response = client.newCall(request).execute();
            if (HttpStatus.OK.value() == response.code()) {
                break;
            } else {
                // 5次后依然非200则抛出异常
                if (i == 5 - 1) {
                    throw new RuntimeException(response.message());
                }
            }
        }
        return response;
    }

    private static String fakeUserAgent() {
        return FAKE_USER_AGENTS[RandomUtils.nextInt(0, FAKE_USER_AGENTS.length)];
    }

}