package ru.gridr.springcloudexample.jsonrpc.exceptions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.googlecode.jsonrpc4j.DefaultExceptionResolver;
import com.googlecode.jsonrpc4j.ExceptionResolver;
import org.springframework.util.StringUtils;

/**
 * {@inheritDoc}
 */
public class JsonRpcClientExceptionResolver implements ExceptionResolver {
    private final ObjectMapper mapper;

    public JsonRpcClientExceptionResolver(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Throwable resolveException(ObjectNode response) {
        JsonNode error = response.get("error");
        if (error == null) {
            return defaultException(response);
        }
        String message = stringValue("message", error);
        String data = stringValue("data", error);

        if (StringUtils.isEmpty(data)) {
            return defaultException(response);
        }

        try {
            StackTraceElement[] stackTrace = mapper.readValue(data, StackTraceElement[].class);
            JsonRpcServerException cause = new JsonRpcServerException(message, stackTrace);
            return new JsonRpcClientException(cause); //TODO log both stack traces. Now the log contains only a stack trace of the cause.
        } catch (Exception e) {
            return defaultException(response);
        }
    }

    private String stringValue(String valueName, JsonNode node) {
        JsonNode data = node.get(valueName);
        if (data == null) {
            return "";
        }
        return data.textValue();
    }

    private Throwable defaultException(ObjectNode response) {
        return DefaultExceptionResolver.INSTANCE.resolveException(response);
    }
}
