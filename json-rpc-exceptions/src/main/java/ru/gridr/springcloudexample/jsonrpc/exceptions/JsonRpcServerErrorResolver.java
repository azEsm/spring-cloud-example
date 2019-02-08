package ru.gridr.springcloudexample.jsonrpc.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.jsonrpc4j.DefaultErrorResolver;
import com.googlecode.jsonrpc4j.ErrorResolver;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.List;

import static com.googlecode.jsonrpc4j.ErrorResolver.JsonError.ERROR_NOT_HANDLED;

/**
 * {@inheritDoc}
 */
public class JsonRpcServerErrorResolver implements ErrorResolver {

    private final ObjectMapper mapper;

    public JsonRpcServerErrorResolver(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * {@inheritDoc}
     * @return the {@link JsonError} that contains called method name and exception message in "message" field
     *                               and serialized stack trace in "data" field
     */
    @Override
    public JsonError resolveError(Throwable t, Method method, List<JsonNode> arguments) {
        try {
            String exceptionMessage = t.getClass().getSimpleName() + (StringUtils.isEmpty(t.getMessage()) ? "" : ":" + t.getMessage());
            String message = method.getDeclaringClass().getSimpleName() + "#" + method.getName() + " calling error: " + exceptionMessage;
            String errorStackTrace = mapper.writeValueAsString(t.getStackTrace());
            return new JsonError(ERROR_NOT_HANDLED.code, message, errorStackTrace);
        } catch (JsonProcessingException e) {
            return DefaultErrorResolver.INSTANCE.resolveError(t, method, arguments);
        }
    }
}
