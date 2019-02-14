package ru.gridr.springcloudexample.mailing.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.jsonrpc4j.ExceptionResolver;
import com.googlecode.jsonrpc4j.JsonRpcClient;
import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import com.googlecode.jsonrpc4j.spring.JsonProxyFactoryBean;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.util.CollectionUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JSON RPC client proxy factory that receives service url from eureka server before each call
 */
public class EurekaJsonProxyFactoryBean extends JsonProxyFactoryBean {
    private final Logger log = LoggerFactory.getLogger(EurekaJsonProxyFactoryBean.class);
    private final ObjectMapper objectMapper;
    private final DiscoveryClient discoveryClient;
    private final String eurekaServiceId;
    private final String rpcServiceName;

    private Map<String, String> extraHttpHeaders = new HashMap<>();
    private JsonRpcClient.RequestListener requestListener = null;
    private SSLContext sslContext = null;
    private HostnameVerifier hostNameVerifier = null;
    private String contentType;
    private ExceptionResolver exceptionResolver;

    public EurekaJsonProxyFactoryBean(ObjectMapper objectMapper, DiscoveryClient discoveryClient, String eurekaServiceId, String rpcServiceName) {
        this.objectMapper = objectMapper;
        setObjectMapper(objectMapper);
        this.discoveryClient = discoveryClient;
        this.eurekaServiceId = eurekaServiceId;
        this.rpcServiceName = rpcServiceName;
        initializeHttpClient();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        updateServiceUrl();
        return super.invoke(invocation);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setExtraHttpHeaders(Map<String, String> extraHttpHeaders) {
        this.extraHttpHeaders = extraHttpHeaders;
        super.setExtraHttpHeaders(extraHttpHeaders);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setRequestListener(JsonRpcClient.RequestListener requestListener) {
        this.requestListener = requestListener;
        super.setRequestListener(requestListener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSslContext(SSLContext sslContext) {
        this.sslContext = sslContext;
        super.setSslContext(sslContext);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setHostNameVerifier(HostnameVerifier hostNameVerifier) {
        this.hostNameVerifier = hostNameVerifier;
        super.setHostNameVerifier(hostNameVerifier);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setContentType(String contentType) {
        this.contentType = contentType;
        super.setContentType(contentType);
    }

    @Override
    public void setExceptionResolver(ExceptionResolver exceptionResolver) {
        this.exceptionResolver = exceptionResolver;
        super.setExceptionResolver(exceptionResolver);
    }

    private void updateServiceUrl() {
        ServiceInstance instance = getServerInstance();
        String newServiceUrl = instanceUrl(instance) + "/" + rpcServiceName;
        reinitializeHttpClient(newServiceUrl);
    }

    /**
     * Create a new JsonRpcHttpClient if the service url was updated
     */
    private void reinitializeHttpClient(String newServiceUrl) {
        String currentServiceUrl = getServiceUrl();
        if (newServiceUrl.equals(currentServiceUrl)) {
            return;
        }

        try {
            JsonRpcHttpClient jsonRpcHttpClient = new JsonRpcHttpClient(objectMapper, new URL(newServiceUrl), extraHttpHeaders);
            jsonRpcHttpClient.setRequestListener(requestListener);
            jsonRpcHttpClient.setSslContext(sslContext);
            jsonRpcHttpClient.setHostNameVerifier(hostNameVerifier);

            if (contentType != null) {
                jsonRpcHttpClient.setContentType(contentType);
            }

            if (exceptionResolver != null) {
                jsonRpcHttpClient.setExceptionResolver(exceptionResolver);
            }

            setServiceUrl(newServiceUrl);
            setJsonRpcHttpClient(jsonRpcHttpClient);
            log.info("'{}' URL reinitialized: {}", eurekaServiceId, newServiceUrl);
        } catch (MalformedURLException mue) {
            throw new RuntimeException(mue);
        }
    }

    /**
     * Get service instance url
     *
     * @param instance service instance
     * @return service instance url
     */
    private String instanceUrl(ServiceInstance instance) {
        String defaultScheme = "http";
        String scheme = (instance.getScheme() == null) ? defaultScheme : instance.getScheme();
        return scheme + "://" + instance.getHost() + ":" + instance.getPort();
    }

    /**
     * Load service instance from eureka server
     *
     * @return service instance
     */
    private ServiceInstance getServerInstance() {
        List<ServiceInstance> instances = discoveryClient.getInstances(eurekaServiceId);
        if (CollectionUtils.isEmpty(instances)) {
            throw new IllegalStateException("No instance found for service " + eurekaServiceId);
        }
        return instances.get(0);
    }

    private void initializeHttpClient() {
        try {
            updateServiceUrl();
        } catch (IllegalStateException e) {
            log.error("JsonRpcHttpClient initialization failed: {}. Initialization with fake client", e.getMessage());
            initializeFakeHttpClient();
        }
    }

    /**
     * Initialize fake HttpClient to prevent excess initialization
     */
    private void initializeFakeHttpClient() {
        String fakeServiceUrl = "http://localhost";
        setServiceUrl(fakeServiceUrl);

        JsonRpcHttpClient fakeClient = createInitialHttpClient(fakeServiceUrl);
        if (fakeClient != null) {
            setJsonRpcHttpClient(fakeClient);
        }
    }

    private JsonRpcHttpClient createInitialHttpClient(String fakeServiceUrl) {
        try {
            return new JsonRpcHttpClient(null, new URL(fakeServiceUrl), Collections.emptyMap());
        } catch (MalformedURLException e) {
            return null;
        }
    }
}
