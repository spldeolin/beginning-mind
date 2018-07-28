package com.spldeolin.beginningmind.core.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.spldeolin.beginningmind.core.holder.RequestMethodDefinitionsHolder;
import com.spldeolin.beginningmind.core.util.RequestContextUtils;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

/**
 * 过滤器：统一返回值包装
 *
 * @author Deolin 2018/06/17
 */
@Log4j2
@Component
public class GlobalReturnFilter implements Filter {

    @Autowired
    private RequestMethodDefinitionsHolder requestMethodDefinitionsHolder;

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    @SneakyThrows
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String uri = ((HttpServletRequest) request).getRequestURI();
        if (!requestMethodDefinitionsHolder.matchAnyMappings(uri)) {
            chain.doFilter(request, response);
            return;
        }

        ResponseWrapper wrapperResponse = new ResponseWrapper(httpServletResponse);
        chain.doFilter(request, wrapperResponse);
        String rawContent = new String(wrapperResponse.getContent());
        String wrappedContent = wrapRequestResult(rawContent);
        ServletOutputStream out = response.getOutputStream();
        byte[] bytes = wrappedContent.getBytes();
        out.write(bytes);
        response.setContentLengthLong(bytes.length);
        out.flush();
    }

    @Override
    public void destroy() {
    }

    private static class ResponseWrapper extends HttpServletResponseWrapper {

        private ByteArrayOutputStream buffer;

        private ServletOutputStream out;

        public ResponseWrapper(HttpServletResponse httpServletResponse) {
            super(httpServletResponse);
            buffer = new ByteArrayOutputStream();
            out = new WrapperOutputStream(buffer);
        }

        @Override
        public ServletOutputStream getOutputStream() {
            return out;
        }

        @Override
        public void flushBuffer() throws IOException {
            if (out != null) {
                out.flush();
            }
        }

        public byte[] getContent() throws IOException {
            flushBuffer();
            return buffer.toByteArray();
        }

        private static class WrapperOutputStream extends ServletOutputStream {

            private ByteArrayOutputStream bos;

            public WrapperOutputStream(ByteArrayOutputStream bos) {
                this.bos = bos;
            }

            @Override
            public void write(int b) {
                bos.write(b);
            }

            @Override
            public boolean isReady() {
                return false;

            }

            @Override
            public void setWriteListener(WriteListener arg0) {
            }
        }
    }

    private String wrapRequestResult(String content) {
        if (content.length() > 0) {
            if (!content.startsWith("{" + quote("code") + ":") || !content.endsWith("}")) {
                if (!isWrappedWithBrace(content) && !isWrappedWithBracket(content) && !"null".equals(content)) {
                    content = quote(content) + ",";
                }
                // code + data + insignia
                return "{" + quote("code") + ":200," + quote("data") + ":" + content + "," + generateInsigniaEndPart()
                        + "}";
            }
        }
        // code + data
        return "{\"code\":200," + generateInsigniaEndPart() + "}";
    }

    private boolean isWrappedWithBrace(String content) {
        return content.startsWith("{") && content.endsWith("}");
    }

    private boolean isWrappedWithBracket(String content) {
        return content.startsWith("[") && content.endsWith("]");
    }

    private String quote(String string) {
        return "\"" + string + "\"";
    }

    private String generateInsigniaEndPart() {
        return "\"insignia\":\"" + RequestContextUtils.getInsignia() + "\"";
    }

}
