package com.peoplebox;

public class Profit {
    public ProfitEnum type;
    int amount;

    public Profit(ProfitEnum type, int amount) {
        this.type = type;
        this.amount = amount;
    }
}
