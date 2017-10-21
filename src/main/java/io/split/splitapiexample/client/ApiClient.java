package io.split.splitapiexample.client;

import com.google.common.collect.Lists;
import io.split.splitapiexample.client.endpoints.ExternalSplitEndpoint;
import io.split.splitapiexample.client.util.ObjectMapperProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.HttpUrlConnectorProvider;
import org.glassfish.jersey.client.filter.EncodingFilter;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.message.GZipEncoder;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.util.List;

/**
 * Created on 10/20/17.
 */
public class ApiClient {

    private ExternalSplitEndpoint externalSplitEndpoint;
    private List<ExternalSplitEndpoint> resources;


    public ApiClient(String url) {
        Client client = client();

        client.property(ClientProperties.CONNECT_TIMEOUT, 60000);
        client.property(ClientProperties.READ_TIMEOUT, 60000);
        client.property(ClientProperties.SUPPRESS_HTTP_COMPLIANCE_VALIDATION, true);
        client.register(MultiPartFeature.class);

        initialize(client, url);
    }

    private void initialize(Client jerseyClient, String baseURL) {
        this.externalSplitEndpoint = new ExternalSplitEndpoint(jerseyClient, baseURL);
//        this.externalSplitResourceEndpoint= new ExternalSplitResourceEndpoint(jerseyClient, baseURL);
        this.resources = Lists.newArrayList(externalSplitEndpoint);
    }

    public ExternalSplitEndpoint externalSplit() {
        return externalSplitEndpoint;
    }

    public ApiClient withAdminApiToken(String token) {
        resources.forEach(resourceEndpoint -> resourceEndpoint.withAdminApiToken(token));
        return this;
    }

    private Client client() {
        return ClientBuilder.newClient(new ClientConfig()
                .property(HttpUrlConnectorProvider.SET_METHOD_WORKAROUND, true)
                .property(ClientProperties.SUPPRESS_HTTP_COMPLIANCE_VALIDATION, true)
                .register(ObjectMapperProvider.class)
                .register(GZipEncoder.class)
                .register(MultiPartFeature.class)
                .register(EncodingFilter.class));
    }
}
