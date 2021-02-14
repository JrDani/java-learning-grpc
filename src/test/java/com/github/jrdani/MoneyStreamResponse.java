package com.github.jrdani;

import com.github.jrdani.models.*;
import io.grpc.stub.*;

import java.util.concurrent.*;

public class MoneyStreamResponse implements StreamObserver<Money> {

    private CountDownLatch latch;

    public MoneyStreamResponse(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void onNext(Money money) {
        System.out.println("Received async: " + money.getValue());
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println(throwable.getMessage());
        latch.countDown();
    }

    @Override
    public void onCompleted() {
        System.out.println("Server is done!");
        latch.countDown();
    }
}
