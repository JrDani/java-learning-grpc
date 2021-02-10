package com.github.jrdani.server;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class AccountDatabase {
    /*
     This is a DB
     1 => 10
     2 => 20
     ...
     10 => 100
    */
    private static final Map<Integer, Integer> MAP = IntStream.rangeClosed(1, 10)
            .boxed()
            .collect(Collectors.toMap(
                Function.identity(),
                    v-> v*10)
            );

    public static int getBalance(int accountId) {
        return MAP.get(accountId);
    }

    public static Integer addBalance(int accountId, int amount) {
        return MAP.computeIfPresent(accountId, (k,v) -> v + amount);
    }

    public static Integer deductBalance(int accountId, int amount) {
        return MAP.computeIfPresent(accountId, (k,v) -> v - amount);
    }
}
