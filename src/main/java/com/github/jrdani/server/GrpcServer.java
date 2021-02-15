package com.github.jrdani.server;

import com.github.jrdani.service.*;
import io.grpc.*;

import java.io.*;

public class GrpcServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(6565)
                .addService(new BankService())
                .addService(new TransferService())
                .build();

        server.start();
        server.awaitTermination();
    }
}
