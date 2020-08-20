package com.peoplebox.additions;

import com.peoplebox.SocietyScreen;

import java.util.ArrayList;

public class Action {
    private String[] en, fr, de, it, es, se, fi, nl, da, pt, cz, he, el, jp, kr, ru, zh, pl, th, no, hu;
    private int delay /*in sec*/, animation, startMoney, addMoney;
    private boolean isSocial = false;
    private SocietyScreen.NeedsArray startNeeds, addNeeds; //if zero, then ignore
    private SocietyScreen.InterestsArray startInterests, addInterests; //if zero, then ignore
    private SocietyScreen.TalentsArray startTalents, addTalents; //if zero, then ignore
    private ArrayList<Integer> items; //type of furniture that can be needed
    private String holdObject;

    public Action(int delay, int animation, int startMoney, int addMoney, SocietyScreen.NeedsArray startNeeds, SocietyScreen.NeedsArray addNeeds, SocietyScreen.InterestsArray startInterests, SocietyScreen.InterestsArray addInterests, SocietyScreen.TalentsArray startTalents, SocietyScreen.TalentsArray addTalents, ArrayList<Integer> items, String holdObject) {
        this.delay = delay;
        this.animation = animation;
        this.startMoney = startMoney;
        this.addMoney = addMoney;
        this.startNeeds = startNeeds;
        this.addNeeds = addNeeds;
        this.startInterests = startInterests;
        this.addInterests = addInterests;
        this.startTalents = startTalents;
        this.addTalents = addTalents;
        this.items = items;
        this.holdObject = holdObject;
    }

    public Action() {}

    public String[] getEn() {
        return en;
    }

    public void setEn(String[] en) {
        this.en = en;
    }

    public String[] getFr() {
        return fr;
    }

    public void setFr(String[] fr) {
        this.fr = fr;
    }

    public String[] getDe() {
        return de;
    }

    public void setDe(String[] de) {
        this.de = de;
    }

    public String[] getIt() {
        return it;
    }

    public void setIt(String[] it) {
        this.it = it;
    }

    public String[] getEs() {
        return es;
    }

    public void setEs(String[] es) {
        this.es = es;
    }

    public String[] getSe() {
        return se;
    }

    public void setSe(String[] se) {
        this.se = se;
    }

    public String[] getFi() {
        return fi;
    }

    public void setFi(String[] fi) {
        this.fi = fi;
    }

    public String[] getNl() {
        return nl;
    }

    public void setNl(String[] nl) {
        this.nl = nl;
    }

    public String[] getDa() {
        return da;
    }

    public void setDa(String[] da) {
        this.da = da;
    }

    public String[] getPt() {
        return pt;
    }

    public void setPt(String[] pt) {
        this.pt = pt;
    }

    public String[] getCz() {
        return cz;
    }

    public void setCz(String[] cz) {
        this.cz = cz;
    }

    public String[] getHe() {
        return he;
    }

    public void setHe(String[] he) {
        this.he = he;
    }

    public String[] getEl() {
        return el;
    }

    public void setEl(String[] el) {
        this.el = el;
    }

    public String[] getJp() {
        return jp;
    }

    public void setJp(String[] jp) {
        this.jp = jp;
    }

    public String[] getKr() {
        return kr;
    }

    public void setKr(String[] kr) {
        this.kr = kr;
    }

    public String[] getRu() {
        return ru;
    }

    public void setRu(String[] ru) {
        this.ru = ru;
    }

    public String[] getZh() {
        return zh;
    }

    public void setZh(String[] zh) {
        this.zh = zh;
    }

    public String[] getPl() {
        return pl;
    }

    public void setPl(String[] pl) {
        this.pl = pl;
    }

    public String[] getTh() {
        return th;
    }

    public void setTh(String[] th) {
        this.th = th;
    }

    public String[] getNo() {
        return no;
    }

    public void setNo(String[] no) {
        this.no = no;
    }

    public String[] getHu() {
        return hu;
    }

    public void setHu(String[] hu) {
        this.hu = hu;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getAnimation() {
        return animation;
    }

    public void setAnimation(int animation) {
        this.animation = animation;
    }

    public int getStartMoney() {
        return startMoney;
    }

    public void setStartMoney(int startMoney) {
        this.startMoney = startMoney;
    }

    public int getAddMoney() {
        return addMoney;
    }

    public void setAddMoney(int addMoney) {
        this.addMoney = addMoney;
    }

    public boolean isSocial() {
        return isSocial;
    }

    public void setSocial(boolean social) {
        isSocial = social;
    }

    public SocietyScreen.NeedsArray getStartNeeds() {
        return startNeeds;
    }

    public void setStartNeeds(SocietyScreen.NeedsArray startNeeds) {
        this.startNeeds = startNeeds;
    }

    public SocietyScreen.NeedsArray getAddNeeds() {
        return addNeeds;
    }

    public void setAddNeeds(SocietyScreen.NeedsArray addNeeds) {
        this.addNeeds = addNeeds;
    }

    public SocietyScreen.InterestsArray getStartInterests() {
        return startInterests;
    }

    public void setStartInterests(SocietyScreen.InterestsArray startInterests) {
        this.startInterests = startInterests;
    }

    public SocietyScreen.InterestsArray getAddInterests() {
        return addInterests;
    }

    public void setAddInterests(SocietyScreen.InterestsArray addInterests) {
        this.addInterests = addInterests;
    }

    public SocietyScreen.TalentsArray getStartTalents() {
        return startTalents;
    }

    public void setStartTalents(SocietyScreen.TalentsArray startTalents) {
        this.startTalents = startTalents;
    }

    public SocietyScreen.TalentsArray getAddTalents() {
        return addTalents;
    }

    public void setAddTalents(SocietyScreen.TalentsArray addTalents) {
        this.addTalents = addTalents;
    }

    public ArrayList<Integer> getItems() {
        return items;
    }

    public void setItems(ArrayList<Integer> items) {
        this.items = items;
    }

    public String getHoldObject() {
        return holdObject;
    }

    public void setHoldObject(String holdObject) {
        this.holdObject = holdObject;
    }
}
