package org.example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class ItemHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private static final Gson gson = new Gson();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {
        String httpMethod = event.getHttpMethod();

        return switch (httpMethod) {
            case "GET" -> handleGet(event, context);
            case "POST" -> handlePost(event, context);
            case null-> buildResponse(400, "Bad Request: HTTP method is null");
            default -> buildResponse(405, "Method Not Allowed");
        };
    }

    private APIGatewayProxyResponseEvent handleGet(APIGatewayProxyRequestEvent event, Context context) {
        Map<String, Object> body = new HashMap<>();
        body.put("items", new String[]{"item1", "item2", "item3"});
        return buildResponse(200, gson.toJson(body));
    }

    private APIGatewayProxyResponseEvent handlePost(APIGatewayProxyRequestEvent event, Context context) {
        // Parse the incoming JSON body
        String rawBody = event.getBody();
        Map<?, ?> requestBody = gson.fromJson(rawBody, Map.class);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", "Item created successfully");
        responseBody.put("received", requestBody);

        return buildResponse(201, gson.toJson(responseBody));
    }

    private APIGatewayProxyResponseEvent buildResponse(int statusCode, String body) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Access-Control-Allow-Origin", "*");

        return new APIGatewayProxyResponseEvent()
                .withStatusCode(statusCode)
                .withHeaders(headers)
                .withBody(body);
    }

}
