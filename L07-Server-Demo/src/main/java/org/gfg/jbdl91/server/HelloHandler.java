package org.gfg.jbdl91.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class HelloHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            System.out.println("Processing request");
            String msg = "Hello from :"+Thread.currentThread().getName();
            exchange.sendResponseHeaders(200,msg.length());
            exchange.getResponseBody().write(msg.getBytes());
            exchange.close();
        }
        catch (Exception ex){
            System.out.println("Exception:"+ex);
        }

    }
}
