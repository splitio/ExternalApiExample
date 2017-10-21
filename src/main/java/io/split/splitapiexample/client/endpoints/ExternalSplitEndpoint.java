package io.split.splitapiexample.client.endpoints;

import com.google.gson.Gson;
import io.split.splitapiexample.dtos.ResultExternal;
import io.split.splitapiexample.dtos.SplitExternal;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.HttpHeaders;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

public class ExternalSplitEndpoint extends AbstractResourceEndpoint {

    public ExternalSplitEndpoint(Client client, String baseURL) {
        super(client, baseURL);
    }

    public SplitExternal get(String name) {
        checkNotNull(name);
        String target = String.format("%s/v1/splits/%s", baseURL(), name);
        String message = "Getting Split " + name;
        printMessage(message, "GET", target);
        return response(client().target(target)
                .request()
                .header(HttpHeaders.AUTHORIZATION, credentials())
                .get(), SplitExternal.class);
    }

    public Boolean delete(String name) {
        checkNotNull(name);
        String target = String.format("%s/v1/splits/%s", baseURL(), name);
        String message = "Deleting Split " + name;
        printMessage(message, "DELETE", target);
        return response(client().target(target)
                .request()
                .header(HttpHeaders.AUTHORIZATION, credentials())
                .delete(), Boolean.class);
    }

    public SplitExternal create(String trafficTypeIdOrName, SplitExternal splitExternal) {
        checkNotNull(splitExternal);
        checkNotNull(trafficTypeIdOrName);
        String target = String.format("%s/v1/splits/trafficTypes/%s", baseURL(), trafficTypeIdOrName);
        String message = "Creating Split with Traffic " + trafficTypeIdOrName;
        printMessage(message, "POST", target, new Gson().toJson(splitExternal));
        return response(client().target(target)
                .request()
                .header(HttpHeaders.AUTHORIZATION, credentials())
                .post(Entity.json(splitExternal)), SplitExternal.class);
    }

    public ResultExternal<SplitExternal> list(Optional<Integer> offset, Optional<Integer> limit) {
        checkNotNull(offset);
        checkNotNull(limit);
        String queryParams = (offset.isPresent() ? String.format("offset=%s", offset.get()) : "")
                + (limit.isPresent() ? String.format("&limit=%s", limit.get()): "");
        String target = String.format("%s/v1/splits?" + queryParams, baseURL());
        String message = "Listing Splits";
        printMessage(message, "GET", target);
        return responseResultExternal(client().target(target)
                .request()
                .header(HttpHeaders.AUTHORIZATION, credentials())
                .get(), SplitExternal.class);
    }
}
