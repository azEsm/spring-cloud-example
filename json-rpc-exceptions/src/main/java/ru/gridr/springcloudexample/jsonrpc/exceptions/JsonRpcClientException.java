package ru.gridr.springcloudexample.jsonrpc.exceptions;

/**
 * Exception thrown in JSON-RPC client caused by JsonRpcServerException
 */
public class JsonRpcClientException extends RuntimeException {

    public JsonRpcClientException(JsonRpcServerException cause) {
        super("Unknown error", cause);
    }
}
