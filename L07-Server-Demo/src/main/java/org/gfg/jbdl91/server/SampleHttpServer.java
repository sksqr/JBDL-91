package org.gfg.jbdl91.server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class SampleHttpServer {

    public static void main(String[] args) throws IOException {
        System.out.println("Starting server..");
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost",9090),10);
        server.createContext("/hello",new HelloHandler());
        server.setExecutor(Executors.newFixedThreadPool(4));
        server.start();
    }
}
