package com.peoplebox;

import java.util.HashMap;

/** Глобальный класс, экземпляры которого будут храниться в ArrayList стоимости
 *  всех экономических ресурсов (трансмитов) в данной цивилизации */
public class TransmitItem {
    ProfitEnum name;
    HashMap<ProfitEnum, Integer> receives;
    int value; //value in money;
}
