package io.split.splitapiexample.client.endpoints;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonpatch.JsonPatch;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import io.split.splitapiexample.dtos.ResultExternal;
import io.split.splitapiexample.dtos.SplitDefinitionExternal;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

public class ExternalSplitDefinitionEndpoint extends AbstractResourceEndpoint {

    public ExternalSplitDefinitionEndpoint(Client client, String baseURL) {
        super(client, baseURL);
    }

    public SplitDefinitionExternal get(String environmentNameOrId, String name) {
        checkNotNull(name);
        checkNotNull(environmentNameOrId);
        String target = String.format("%s/v1/splits/%s/environments/%s", baseURL(), name, environmentNameOrId);
        String message = "Getting Split Definition " + name + " on environment " + environmentNameOrId;
        printMessage(message, "GET", target);

        return response(client().target(target)
                .request()
                .header(HttpHeaders.AUTHORIZATION, credentials())
                .get(), SplitDefinitionExternal.class);
    }

    public SplitDefinitionExternal configure(String environmentNameOrId,
                                             String name, SplitDefinitionExternal splitExternal) {
        checkNotNull(splitExternal);
        checkNotNull(name);
        checkNotNull(environmentNameOrId);
        String target = String.format("%s/v1/splits/%s/environments/%s", baseURL(), name, environmentNameOrId);
        String message = "Configuring Split Definition " + name + " on environment " + environmentNameOrId;
        printMessage(message, "POST", target, new Gson().toJson(splitExternal));

        return response(client().target(target)
                .request()
                .header(HttpHeaders.AUTHORIZATION, credentials())
                .post(Entity.json(splitExternal)), SplitDefinitionExternal.class);
    }

    public Boolean unconfigure(String environmentNameOrId, String name) {
        checkNotNull(name);
        checkNotNull(environmentNameOrId);
        String target = String.format("%s/v1/splits/%s/environments/%s", baseURL(), name, environmentNameOrId);
        String message = "Unconfiguring Split Definition " + name + " on environment " + environmentNameOrId;
        printMessage(message, "DELETE", target);

        return response(client().target(target)
                .request()
                .header(HttpHeaders.AUTHORIZATION, credentials())
                .delete(), Boolean.class);
    }

    public ResultExternal<SplitDefinitionExternal> list(String environmentNameOrId,
                                                        Optional<Integer> offset, Optional<Integer> limit) {
        checkNotNull(offset);
        checkNotNull(environmentNameOrId);
        checkNotNull(limit);
        String queryParams = (offset.isPresent() ? String.format("offset=%s", offset.get()) : "")
                + (limit.isPresent() ? String.format("&limit=%s", limit.get()): "");
        String target = String.format("%s/v1/splits/environments/%s?%s", baseURL(), environmentNameOrId, queryParams);
        String message = "Listing Split Definitions";
        printMessage(message, "GET", target);

        return responseResultExternal(client().target(target)
                .request()
                .header(HttpHeaders.AUTHORIZATION, credentials())
                .get(), SplitDefinitionExternal.class);
    }

    public SplitDefinitionExternal update(String environmentNameOrId,
                                String name,
                                JsonNode modify) throws IOException {
        Preconditions.checkNotNull(environmentNameOrId);
        Preconditions.checkNotNull(name);
        Preconditions.checkNotNull(modify);

        String target = String.format("%s/v1/splits/%s/environments/%s", baseURL(), name, environmentNameOrId);
        String message = "Updating Split Definition " + name + " on environment " + environmentNameOrId;
        printMessage(message, "PATCH", target, modify.toString());
        JsonPatch jsonPatch = JsonPatch.fromJson(modify);
        return response(client().target(target)
                .request()
                .header(HttpHeaders.AUTHORIZATION, credentials())
                .method("PATCH", Entity.json(jsonPatch)), SplitDefinitionExternal.class);
    }

    public void kill(String environmentNameOrId, String name) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(environmentNameOrId));
        Preconditions.checkArgument(!Strings.isNullOrEmpty(name));
        List<String> emptyKeys = Lists.newArrayList();
        String target = String.format("%s/v1/splits/%s/environments/%s/kill", baseURL(), name, environmentNameOrId);
        String message = "Killing Split Definition " + name + " on environment " + environmentNameOrId;
        printMessage(message, "PUT", target);
        Response response = client()
                .target(target)
                .request()
                .header(HttpHeaders.AUTHORIZATION, credentials())
                .put(Entity.entity(emptyKeys, MediaType.APPLICATION_JSON_TYPE));
        response(response, Boolean.class);
    }

    public void restore(String environmentNameOrId, String name) {
        checkNotNull(environmentNameOrId);
        checkNotNull(name);
        String target = String.format("%s/v1/splits/%s/environments/%s/restore", baseURL(), name, environmentNameOrId);
        String message = "Restoring Split Definition " + name + " on environment " + environmentNameOrId;
        printMessage(message, "PUT", target);

        response(client()
                .target(target)
                .request()
                .header(HttpHeaders.AUTHORIZATION, credentials())
                .put(Entity.json(null)), Boolean.class);
    }
}
