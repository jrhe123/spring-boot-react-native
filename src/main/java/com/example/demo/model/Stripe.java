package com.example.demo.model;

import lombok.Data;

@Data
public class Stripe {

    private int amount;
    private String stripePublicKey;
}
