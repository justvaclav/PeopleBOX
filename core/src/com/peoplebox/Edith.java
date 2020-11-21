package com.peoplebox;

import java.util.HashMap;

public class Edith {
    //Edith #0 is the end of scenario
    int num, t=0, f=0, delay;
    HashMap<String, Object> states;

    public Edith(int num, int t, int f, int delay, HashMap<String, Object> states) {
        this.num = num;
        this.t = t;
        this.f = f;
        this.delay = delay;
        this.states = states;
    }
}
