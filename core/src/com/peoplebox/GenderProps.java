package com.peoplebox;

public class GenderProps {
    public GenderProps() {
    }

    private int femaleAppetence;
    private int kidsWant;
    private int maleAppetence;

    public GenderProps(int maleAppetence, int femaleAppetence, int kidsWant) {
        this.maleAppetence = maleAppetence;
        this.femaleAppetence = femaleAppetence;
        this.kidsWant = kidsWant;
    }

    public int getMaleAppetence() {
        return this.maleAppetence;
    }

    public void setMaleAppetence(int maleAppetence) {
        this.maleAppetence = maleAppetence;
    }

    public int getFemaleAppetence() {
        return this.femaleAppetence;
    }

    public void setFemaleAppetence(int femaleAppetence) {
        this.femaleAppetence = femaleAppetence;
    }

    public int getKidsWant() {
        return this.kidsWant;
    }

    public void setKidsWant(int kidsWant) {
        this.kidsWant = kidsWant;
    }
}