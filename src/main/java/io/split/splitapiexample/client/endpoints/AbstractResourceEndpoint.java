package io.split.splitapiexample.client.endpoints;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import io.split.splitapiexample.client.util.APIMessage;
import io.split.splitapiexample.client.util.ResponseStatusError;
import io.split.splitapiexample.dtos.ResultExternal;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class AbstractResourceEndpoint {
    private final Client client;
    private final String baseURL;
    private String credentials;
    private static final ObjectMapper mapper = new ObjectMapper();

    public AbstractResourceEndpoint(Client client, String baseURL) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(baseURL));
        this.client = Preconditions.checkNotNull(client);
        this.baseURL = baseURL;
    }

    protected Client client() {
        return client;
    }

    protected String baseURL() {
        return baseURL;
    }

    public String credentials() {
        return credentials;
    }

    public AbstractResourceEndpoint withAdminApiToken(String token) {
        this.credentials = toApiBearer(token);
        return this;
    }

    private String toApiBearer(String token) {
        return "Bearer " + token;
    }

    protected <T> ResultExternal<T> responseResultExternal(Response response, Class<T> objectType) {
        if (response.getStatus() != 200) {
            throw new ResponseStatusError(response.readEntity(APIMessage.class));
        }

        try {
            JsonNode e = (JsonNode) mapper.readValue((String)response.readEntity(String.class), JsonNode.class);
            Iterator successfulNodes = ((JsonNode)e.findValues("objects").get(0)).elements();
            ArrayList successful = new ArrayList();

            while(successfulNodes.hasNext()) {
                JsonNode failedNodes = (JsonNode)successfulNodes.next();
                if(failedNodes.size() > 0) {
                    successful.add(mapper.readValue(failedNodes.toString(), objectType));
                }
            }
            long totalCount = e.get("totalCount").asLong();
            int offset = e.get("offset").asInt();
            int limit = e.get("limit").asInt();
            ResultExternal result = ResultExternal.builder(objectType).objects(successful).totalCount(totalCount).offset(offset).limit(limit).build();
            printResult(result);
            return result;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    protected <T> T response(Response response, Class<T> clazz) {
        if (response.getStatus() == 200) {
            T t = response.readEntity(clazz);
            printResult(t);
            return t;
        } else {
            throw new ResponseStatusError(response.readEntity(APIMessage.class));
        }
    }

    private void printResult(Object result) {
        System.out.println("RESULT: ");
        System.out.println(result);
    }
    protected void printMessage(String message, String httpCall, String target) {
        System.out.println("--------------------------------------------");
        System.out.println(message);
        System.out.println(httpCall + " " + target);
    }

    protected void printMessage(String message, String httpCall, String target, String payload) {
        System.out.println("--------------------------------------------");
        System.out.println(message);
        System.out.println("Payload: ");
        System.out.println(payload);
        System.out.println(httpCall + " " + target);
    }

}
