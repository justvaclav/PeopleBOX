package com.peoplebox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class SocialSystem {
    public LinkedList<ArrayList<Screw>> screws;
    public HashMap<ProfitEnum, Integer> profits = new HashMap<>();
    public SocietyScreen.InterestsArray interests;
    public SocialSystemType type;
    public SocietyScreen.TalentsArray talents;
    public int id, consumerAmount = 0;
    public double popularity = 1.0;
    public String name;

    public SocialSystem(int id, String name, LinkedList<ArrayList<Screw>> screws, SocialSystemType type, SocietyScreen.InterestsArray interests, SocietyScreen.TalentsArray talents) {
        this.id = id;
        this.name = name;
        this.screws = screws;
        this.interests = interests;
        this.type = type;
        this.talents = talents;
    }
}
