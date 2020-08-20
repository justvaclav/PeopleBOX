package com.peoplebox;

public class GlobalNumIndi {
    String name, surname;
    int x,y,z, localNum, globalNum;
    boolean alive = true;

    public GlobalNumIndi(String name, String surname, int x, int y, int z, int localNum, int globalNum) {
        this.name = name;
        this.surname = surname;
        this.x = x;
        this.y = y;
        this.z = z;
        this.localNum = localNum;
        this.globalNum = globalNum;
    }
}
