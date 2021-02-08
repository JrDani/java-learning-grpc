package com.github.jrdani.service;

import com.github.jrdani.models.*;
import io.grpc.stub.*;

public class BankService extends BankServiceGrpc.BankServiceImplBase {

    @Override
    public void getBalance(BalanceCheckRequest request, StreamObserver<Balance> responseObserver) {
        int accountNumber = request.getAccountNumber();
        Balance balance = Balance.newBuilder()
                .setAmount(accountNumber * 100)
                .build();

        responseObserver.onNext(balance);
        responseObserver.onCompleted();
    }
}
