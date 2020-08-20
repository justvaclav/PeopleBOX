package com.peoplebox;

import java.util.ArrayList;
import java.util.List;

public class Screw {
    public int id; //global id
    public double efficiency = 0.50;
    List<Profit> receive, transmit;
    List<Integer> etiquette;
    List<List<Integer>> hours = new ArrayList<>(); //0-6-day(mo-su), 0-24-start hour, 0-24-work hours count

    public Screw(int id, List<Profit> receive, List<Profit> transmit, List<Integer> etiquette) {
        this.id = id;
        this.receive = receive;
        this.transmit = transmit;
        this.etiquette = etiquette;
    }

    public Screw(int id, List<Profit> receive, List<Profit> transmit, List<Integer> etiquette, List<List<Integer>> hours) {
        this.id = id;
        this.receive = receive;
        this.transmit = transmit;
        this.etiquette = etiquette;
        this.hours = hours;
    }
}
