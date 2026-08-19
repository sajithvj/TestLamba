package org.example;

import com.amazonaws.services.lambda.runtime.Context;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public Response handleRequest(Object input, Context context) {
        return new Response("Hello from MainCode!", 200);
    }
}
