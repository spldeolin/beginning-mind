package com.spldeolin.beginningmind.app;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import org.springframework.data.redis.connection.util.ByteArraySet;
import org.springframework.ui.ConcurrentModel;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Deolin 2023-04-01
 */
@Data
@Accessors(chain = true)
public class RootJavabean {

    private Long a;

    private String b;

    private List<Integer> c;

    private ByteArraySet d;
    private Queue<Long> e;

    private Map<Long, String> f;
    private ConcurrentModel g;
    private String[] h;

    private Lay1Javabean lay1;

    private List<Lay1Javabean> lay1List;

}