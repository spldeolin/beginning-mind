package com.spldeolin.beginningmind.core;

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
import org.springframework.stereotype.Component;
import com.spldeolin.beginningmind.core.constant.CoupledConstant;
import com.spldeolin.beginningmind.core.util.Jsons;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2018/06/17
 */
@Log4j2
@Component
public class RequestResultFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    @SneakyThrows
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        String uri = ((HttpServletRequest) request).getRequestURI();
        if (uri.startsWith(CoupledConstant.SWAGGER_URL_PREFIXES[0]) ||
                uri.startsWith(CoupledConstant.SWAGGER_URL_PREFIXES[1]) ||
                uri.startsWith(CoupledConstant.SWAGGER_URL_PREFIXES[2])) {
            chain.doFilter(request, response);
            return;
        }
        ResponseWrapper wrapperResponse = new ResponseWrapper((HttpServletResponse) response);
        chain.doFilter(request, wrapperResponse);
        String content = new String(wrapperResponse.getContent());
        if (content.length() > 0) {
            // 认为不是RequestResult类型
            if (Jsons.getValue(content, "code") == null) {
                content = "{\"code\":200,\"data\":" + content + "}";
            }
        } else {
            content = "{\"code\":200}";
        }
        ServletOutputStream out = response.getOutputStream();
        out.write(content.getBytes());
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

        class WrapperOutputStream extends ServletOutputStream {

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

}
