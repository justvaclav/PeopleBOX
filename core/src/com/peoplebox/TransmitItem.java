package com.peoplebox;

import java.util.HashMap;

/** Класс, экземпляры которого будут храниться в ArrayList стоимости
 *  всех экономических ресурсов (трансмитов) в данной цивилизации */
public class TransmitItem {
    ProfitEnum name;
    String nameString;
    HashMap<ProfitEnum, Integer> receives; //ресурсы, которые нужны для производства этого TransmitItem
    int price; //value in money;

    public TransmitItem(ProfitEnum name, HashMap<ProfitEnum, Integer> receives, int price) {
        this.name = name;
        this.receives = receives;
        this.price = price;
        nameString = name.name();
    }

    public TransmitItem(ProfitEnum name, String nameString, HashMap<ProfitEnum, Integer> receives, int price) {
        this(name, receives, price);
        this.nameString = nameString;
    }
}
