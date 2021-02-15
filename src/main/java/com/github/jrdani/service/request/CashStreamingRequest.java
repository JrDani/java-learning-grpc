package com.github.jrdani.service.request;

import com.github.jrdani.models.*;
import com.github.jrdani.server.*;
import io.grpc.stub.*;

public class CashStreamingRequest implements StreamObserver<DepositRequest> {

    private StreamObserver<Balance> balanceStreamObserver;
    private int accountBalance;

    public CashStreamingRequest(StreamObserver<Balance> balanceStreamObserver) {
        this.balanceStreamObserver = balanceStreamObserver;
    }

    @Override
    public void onNext(DepositRequest depositRequest) {
        int accountNumber = depositRequest.getAccountNumber();
        int amount = depositRequest.getAmount();
        this.accountBalance = AccountDatabase.addBalance(accountNumber, amount);
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onCompleted() {
        Balance balance = Balance.newBuilder()
                .setAmount(this.accountBalance)
                .build();
        this.balanceStreamObserver.onNext(balance);
        this.balanceStreamObserver.onCompleted();
    }
}
