package ru.gridr.springcloudexample.jsonrpc.exceptions;

/**
 * Exception used for passing JSON-RPC server exception stack trace to client
 */
public class JsonRpcServerException extends RuntimeException {
    public JsonRpcServerException(String message, StackTraceElement[] serverStackTrace) {
        super(message);
        setStackTrace(serverStackTrace);
    }
}
