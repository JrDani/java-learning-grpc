package com.github.jrdani.service.request;

import com.github.jrdani.models.*;
import com.github.jrdani.server.*;
import io.grpc.stub.*;

public class TransferStreamingRequest implements StreamObserver<TransferRequest> {

    private StreamObserver<TransferResponse> transferResponseStreamObserver;

    public TransferStreamingRequest(StreamObserver<TransferResponse> transferResponseStreamObserver) {
        this.transferResponseStreamObserver = transferResponseStreamObserver;
    }

    @Override
    public void onNext(TransferRequest transferRequest) {
        int fromAccount = transferRequest.getFromAccount();
        int amount = transferRequest.getAmount();
        int toAccount = transferRequest.getToAccount();
        int balance = AccountDatabase.getBalance(fromAccount);
        TransferStatus status = TransferStatus.FAILED;

        if (balance >= amount && fromAccount != toAccount) {
            AccountDatabase.deductBalance(fromAccount, amount);
            AccountDatabase.addBalance(toAccount, amount);
            status = TransferStatus.SUCCESS;
        }

        int fromAccountCurrentBalance = AccountDatabase.getBalance(fromAccount);
        int toAccountCurrentBalance = AccountDatabase.getBalance(toAccount);
        Account fromAccountInfo = Account.newBuilder().setAccountNumber(fromAccount).setAmount(fromAccountCurrentBalance).build();
        Account toAccountInfo = Account.newBuilder().setAccountNumber(toAccount).setAmount(toAccountCurrentBalance).build();

        TransferResponse response = TransferResponse.newBuilder()
                .setStatus(status)
                .addAccounts(fromAccountInfo)
                .addAccounts(toAccountInfo)
                .build();

        this.transferResponseStreamObserver.onNext(response);
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onCompleted() {
        AccountDatabase.printAccountDetails();
        this.transferResponseStreamObserver.onCompleted();
    }
}
