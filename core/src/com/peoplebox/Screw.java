package com.peoplebox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Screw {
    public int id; //global id
    public boolean isWorking = false;
    public double efficiency = 0.50;
    HashMap<TransmitItem, Integer> transmit; //товары, выдаваемые винтом и их кол-во на смену, receives are already in TransmitItem
    List<Integer> etiquette;
    List<List<Integer>> hours = new ArrayList<>(); //0-6:day(mo-su), 0-24:start hour, 0-24:work hours count

    public Screw(int id, HashMap<TransmitItem, Integer> transmit, List<Integer> etiquette) {
        this.id = id;
        this.transmit = transmit;
        this.etiquette = etiquette;
    }

    public Screw(int id, HashMap<TransmitItem, Integer> transmit, List<Integer> etiquette, List<List<Integer>> hours) {
        this(id, transmit, etiquette);
        this.hours = hours;
    }
}
