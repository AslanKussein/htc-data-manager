package kz.dilau.htcdatamanager.util;

import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.argument.StructuredArgument;
import net.logstash.logback.argument.StructuredArguments;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class LogUtil {
    private static final List<MediaType> VISIBLE_TYPES = Arrays.asList(
            MediaType.APPLICATION_JSON
    );

    private LogUtil() {}

    @SuppressWarnings("squid:S3457")
    public static void logHttpRequest(Logger logger, String userName, ContentCachingRequestWrapper request, ContentCachingResponseWrapper response) {
        HttpStatus status = HttpStatus.resolve(response.getStatus());
        String queryString = request.getQueryString();
        if (!StringUtils.isEmpty(queryString)) {
            try {
                queryString = URLDecoder.decode(request.getQueryString(), StandardCharsets.UTF_8.displayName());
            }
            catch (UnsupportedEncodingException e) {
                log.error("Unsupported encoding {}", StandardCharsets.UTF_8.displayName());
            }
        }
        String requestUrl = request.getRequestURI() + (StringUtils.isEmpty(queryString) ? "" : ("?" + queryString));
        logger.debug("Processed HTTP request: {}, {}, {}",
                request.getMethod(),
                requestUrl,
                userName,
                kv("user", userName),
                kv("method", request.getMethod()),
                kv("requestUrl", request.getRequestURI()),
                kv("requestQuery", queryString),
                kv("requestBody", contentToStr(request.getContentAsByteArray(), request.getContentType(), request.getRequest().getCharacterEncoding())),
                kv("responseStatus", status == null ? "UNKNOWN" : status.toString()),
                kv("responseBody", contentToStr(response.getContentAsByteArray(), response.getContentType(), response.getResponse().getCharacterEncoding()))
        );
    }

    private static String contentToStr(byte[] content, String contentType, String contentEncoding) {
        if (content != null && content.length > 0) {
            MediaType mediaType = MediaType.valueOf(contentType);
            if (VISIBLE_TYPES.stream().anyMatch(visibleType -> visibleType.includes(mediaType))) {
                if (StringUtils.isEmpty(contentEncoding)) {
                    contentEncoding = StandardCharsets.UTF_8.displayName();
                }
                try {
                    return new String(content, contentEncoding);
                } catch (UnsupportedEncodingException e) {
                    log.error("Unsupported encoding {}", contentEncoding);
                }
            }
            return "Content length is " + content.length + " bytes";
        }
        return "";
    }

    private static StructuredArgument kv(String key, String value) {
        return StructuredArguments.kv(key, value);
    }
}
