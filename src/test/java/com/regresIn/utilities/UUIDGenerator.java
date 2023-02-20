package com.regresIn.utilities;

import java.util.UUID;

public class UUIDGenerator {

    public static UUID generateUuid() {
        return UUID.randomUUID();
    }

    public static String generateTransactionId() {
        return generateUuid() + "-0001";
    }

    public static String generateIdempotencyKey() {
        return generateUuid() + "-0001";
    }

    public static void main(String[] args) {
        System.out.println("trans İd="+ generateTransactionId());
    }

}

