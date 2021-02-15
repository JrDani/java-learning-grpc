package com.github.jrdani.service;

import com.github.jrdani.models.*;
import com.github.jrdani.server.*;
import com.github.jrdani.service.request.*;
import io.grpc.*;
import io.grpc.stub.*;

public class BankService extends BankServiceGrpc.BankServiceImplBase {

    @Override
    public void getBalance(BalanceCheckRequest request, StreamObserver<Balance> responseObserver) {
        int accountNumber = request.getAccountNumber();
        Balance balance = Balance.newBuilder()
                .setAmount(AccountDatabase.getBalance(accountNumber))
                .build();

        responseObserver.onNext(balance);
        responseObserver.onCompleted();
    }

    @Override
    public void withdraw(WithdrawRequest request, StreamObserver<Money> responseObserver) {
        int accountNumber = request.getAccountNumber();
        int amount = request.getAmount();
        int balance = AccountDatabase.getBalance(accountNumber);

        if (balance < amount) {
            Status status = Status.FAILED_PRECONDITION.withDescription("No Enough money. You have only " + balance);
            responseObserver.onError(status.asRuntimeException());
            return;
        }

        for (int i = 0; i < (amount/10); i++) {
            Money money = Money.newBuilder().setValue(10F).build();
            responseObserver.onNext(money);
            AccountDatabase.deductBalance(accountNumber, 10);
        }
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<DepositRequest> cashDeposit(StreamObserver<Balance> responseObserver) {
        return new CashStreamingRequest(responseObserver);
    }
}
