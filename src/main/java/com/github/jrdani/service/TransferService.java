package com.github.jrdani.service;

import com.github.jrdani.models.*;
import com.github.jrdani.service.request.*;
import io.grpc.stub.*;

public class TransferService extends TransferServiceGrpc.TransferServiceImplBase {

    @Override
    public StreamObserver<TransferRequest> transfer(StreamObserver<TransferResponse> responseObserver) {
        return new TransferStreamingRequest(responseObserver);
    }
}
