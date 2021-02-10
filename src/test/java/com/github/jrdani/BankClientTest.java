package com.github.jrdani;

import com.github.jrdani.models.*;
import io.grpc.*;
import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BankClientTest {

    private BankServiceGrpc.BankServiceBlockingStub blockingStub;

    @BeforeAll
    private void setup() {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 6565)
                .usePlaintext()
                .build();
        this.blockingStub = BankServiceGrpc.newBlockingStub(channel);
    }

    @Test
    public void balanceTest() {
        BalanceCheckRequest balanceCheckRequest = BalanceCheckRequest.newBuilder()
                .setAccountNumber(3)
                .build();
        Balance balance = this.blockingStub.getBalance(balanceCheckRequest);
        System.out.println("Received: " + balance.getAmount());
    }
}
