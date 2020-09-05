package com.peoplebox;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.peoplebox.SocietyScreen.*;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.MissingResourceException;

import static com.peoplebox.SocietyScreen.*;

public class Addition {
    public static String jsonStr;
    DecimalFormat df = new DecimalFormat("#.##");
    static ArrayList<Dish> dishes = new ArrayList<Dish>(Arrays.asList(new Dish("Макароны с сыром", 10, 2), new Dish("Пирог с картофелем", 6, 4), new Dish("Панкейки", 7, 4), new Dish("Брауни", 12, 6), new Dish("Лазанья", 10, 5), new Dish("Пицца \"Маргарита\"", 8, 3), new Dish("Гаспачо", 15, 9), new Dish("Яблочный пирог", 7, 5)));
    static Json json = new Json();
    static float VOLUME = 1.0f;

    public static class Book {
        public String name;
        public String author;
        public String desc = "";
        public int token;

        public Book(String name, String author, String desc, int token) {
            this.name = name;
            this.author = author;
            this.desc = desc;
            this.token = token;
        }

        public Book(String name, String author, int token) {
            this.name = name;
            this.author = author;
            this.token = token;
        }

        public Book() {}
    }

    public static class Channel {
        public ArrayList<Box> boxes = new ArrayList<Box>();
        public String name;
        public int audience;
        public int rating;
        public ArrayList<Show> shows;

        public Channel(String name, int audience, int rating, ArrayList<Show> shows) {
            this.name = name;
            this.audience = audience;
            this.rating = rating;
            this.shows = shows;
        }

        public Channel(String name, ArrayList<Show> shows) {
            this.name = name;
            this.audience = rnd(100)*9852;
            this.rating = rnd(10);
            this.shows = shows;
        }

        public Channel(String name) {
            this.name = name;
            this.audience = rnd(100)*9852;
            this.rating = rnd(10);
            this.shows = new ArrayList<Show>();
        }


        public Channel() {}

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAudience() {
            return audience;
        }

        public void setAudience(int audience) {
            this.audience = audience;
        }

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }

        public ArrayList<Show> getShows() {
            return shows;
        }

        public void setShows(ArrayList<Show> shows) {
            this.shows = shows;
        }
    }

    public static class Show {
        public String name;
        public int genre; //1-новости
        public int rating;

        public Show(String name, int genre, int rating) {
            this.name = name;
            this.genre = genre;
            this.rating = rating;
        }

        public Show() {}

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getGenre() {
            return genre;
        }

        public void setGenre(int genre) {
            this.genre = genre;
        }

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }
    }

    public static class Creation {
        public Creation() {
        }

        private String authorName;
        private String authorSurname;
        private String description;
        private String name;
        private int type;
        private int value;

        public Creation(String authorName, String authorSurname, String name, String description, int value, int type) {
            this.authorName = authorName;
            this.authorSurname = authorSurname;
            this.name = name;
            this.description = description;
            this.value = value;
            this.type = type;
        }

        public String getAuthorName() {
            return authorName;
        }

        public void setAuthorName(String authorName) {
            this.authorName = authorName;
        }

        public String getAuthorSurname() {
            return authorSurname;
        }

        public void setAuthorSurname(String authorSurname) {
            this.authorSurname = authorSurname;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    /*public static class Education {
        private int type; // 1-законченная школа, 2-колледж 3-университет
        private Speciality speciality;
        private String name;

        public Education() {}

        public Education(int type, Speciality specialty) {
            this.type = type;
            this.speciality = specialty;
            this.name = "";
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public Speciality getSpecialty() {
            return speciality;
        }

        public void setSpecialty(Speciality specialty) {
            this.speciality = specialty;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }*/

    public static class Init {
        private int leaderNum;
        private ArrayList<String> members = new ArrayList<String>();
        private String name;
        private int address;
        private int theme;
        private int type;

        public Init() {
        }

        public Init(int leaderNum, ArrayList<String> members, String name, int address, int theme, int type) {
            this.leaderNum = leaderNum;
            this.members = members;
            this.name = name;
            this.address = address;
            this.theme = theme;
            this.type = type;
        }

        public Init(int leaderNum, String name, int theme, int type) {
            this.leaderNum = leaderNum;
            this.name = name;
            this.theme = theme;
            this.type = type;
        }

        public int getLeaderNum() {
            return leaderNum;
        }

        public void setLeaderNum(int leaderNum) {
            this.leaderNum = leaderNum;
        }

        public ArrayList<String> getMembers() {
            return members;
        }

        public void setMembers(ArrayList<String> members) {
            this.members = members;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAddress() {
            return address;
        }

        public void setAddress(int address) {
            this.address = address;
        }

        public int getTheme() {
            return theme;
        }

        public void setTheme(int theme) {
            this.theme = theme;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

    static class Job {
        private String name;
        private int id;
        private int salary;
        private int vacancy;
        private int type; //0-ОФИС, 1-РАБОТА НА ДОМУ
        private int startTime;
        private int hours;
        private SkillsArray skills;

        public Job() {}

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getSalary() {
            return salary;
        }

        public void setSalary(int salary) {
            this.salary = salary;
        }

        public int getVacancy() {
            return vacancy;
        }

        public void setVacancy(int vacancy) {
            this.vacancy = vacancy;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getStartTime() {
            return startTime;
        }

        public void setStartTime(int startTime) {
            this.startTime = startTime;
        }

        public int getHours() {
            return hours;
        }

        public void setHours(int hours) {
            this.hours = hours;
        }

        public SkillsArray getSkills() {
            return skills;
        }

        public void setSkills(SkillsArray skills) {
            this.skills = skills;
        }

        public Job(String name, int id, int salary, int vacancy, int type, int startTime, int hours, SkillsArray skills) {
            this.name = name;
            this.id = id;
            this.salary = salary;
            this.vacancy = vacancy;
            this.type = type;
            this.startTime = startTime;
            this.hours = hours;
            this.skills = skills;
        }
    }

    static class NeedsArray {
        public NeedsArray() {
        }

        private int aesthetics;
        private int bladder;
        private int education;
        private int energy;
        private int environment;
        private int fun;
        private int hunger;
        private int hygiene;
        private int love;
        private int power;
        private int protection;
        private int shopping;
        private int social;
        private int success;

        public NeedsArray(int hunger, int bladder, int hygiene, int energy, int environment, int protection, int social, int love, int success, int shopping, int education, int fun, int aesthetics, int power) {
            this.hunger = hunger;
            this.bladder = bladder;
            this.hygiene = hygiene;
            this.energy = energy;
            this.environment = environment;
            this.protection = protection;
            this.social = social;
            this.love = love;
            this.success = success;
            this.shopping = shopping;
            this.education = education;
            this.fun = fun;
            this.aesthetics = aesthetics;
            this.power = power;
        }

        public int getHunger() {
            return this.hunger;
        }

        public void setHunger(int hunger) {
            this.hunger = hunger;
        }

        public int getBladder() {
            return this.bladder;
        }

        public void setBladder(int bladder) {
            this.bladder = bladder;
        }

        public int getHygiene() {
            return this.hygiene;
        }

        public void setHygiene(int hygiene) {
            this.hygiene = hygiene;
        }

        public int getEnergy() {
            return this.energy;
        }

        public void setEnergy(int energy) {
            this.energy = energy;
        }

        public int getEnvironment() {
            return this.environment;
        }

        public void setEnvironment(int environment) {
            this.environment = environment;
        }

        public int getProtection() {
            return this.protection;
        }

        public void setProtection(int protection) {
            this.protection = protection;
        }

        public int getSocial() {
            return this.social;
        }

        public void setSocial(int social) {
            this.social = social;
        }

        public int getLove() {
            return this.love;
        }

        public void setLove(int love) {
            this.love = love;
        }

        public int getSuccess() {
            return this.success;
        }

        public void setSuccess(int success) {
            this.success = success;
        }

        public int getShopping() {
            return this.shopping;
        }

        public void setShopping(int shopping) {
            this.shopping = shopping;
        }

        public int getEducation() {
            return this.education;
        }

        public void setEducation(int education) {
            this.education = education;
        }

        public int getFun() {
            return this.fun;
        }

        public void setFun(int fun) {
            this.fun = fun;
        }

        public int getAesthetics() {
            return this.aesthetics;
        }

        public void setAesthetics(int aesthetics) {
            this.aesthetics = aesthetics;
        }

        public int getPower() {
            return this.power;
        }

        public void setPower(int power) {
            this.power = power;
        }
    }


    public static class SkillsArray {
        public SkillsArray() {
        }

        private int charm;
        private int creativity;
        private int logic;
        private int it;


        public SkillsArray(int creativity, int logic, int charm) {
            this.creativity = creativity;
            this.logic = logic;
            this.charm = charm;
            it = 0;
        }

        public int getCharm() {
            return charm;
        }

        public void setCharm(int charm) {
            this.charm = charm;
        }

        public int getCreativity() {
            return creativity;
        }

        public void setCreativity(int creativity) {
            this.creativity = creativity;
        }

        public int getLogic() {
            return logic;
        }

        public void setLogic(int logic) {
            this.logic = logic;
        }
    }

    static class SocialLiftsArray {
        public SocialLiftsArray() {
        }

        private boolean appearance;
        private boolean career;
        private boolean education;
        private boolean marriage;
        private int status;

        public SocialLiftsArray(int status, boolean appearance, boolean education, boolean marriage, boolean career) {
            this.status = status;
            this.appearance = appearance;
            this.education = education;
            this.marriage = marriage;
            this.career = career;
        }

        public int getStatus() {
            return this.status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public boolean isAppearance() {
            return this.appearance;
        }

        public void setAppearance(boolean appearance) {
            this.appearance = appearance;
        }

        public boolean isEducation() {
            return this.education;
        }

        public void setEducation(boolean education) {
            this.education = education;
        }

        public boolean isMarriage() {
            return this.marriage;
        }

        public void setMarriage(boolean marriage) {
            this.marriage = marriage;
        }

        public boolean isCareer() {
            return this.career;
        }

        public void setCareer(boolean career) {
            this.career = career;
        }
    }

    public static class TalentsArray {
        public TalentsArray() {
        }

        private int caution; //внимание
        private int memory; //память
        private int speech; //речь
        private int quickness; //быстрота
        private int logic; //логика
        private int imagination; //образы
        private int insight; //понимание, интуиция
        private int influence; //влияние
        private int stamina; //выносливость
        private int immunity; //устойчивость к болезням

        public TalentsArray(int caution, int memory, int speech, int quickness, int logic, int imagination, int insight, int influence, int stamina, int immunity) {
            this.caution = caution;
            this.memory = memory;
            this.speech = speech;
            this.quickness = quickness;
            this.logic = logic;
            this.imagination = imagination;
            this.insight = insight;
            this.influence = influence;
            this.stamina = stamina;
            this.immunity = immunity;
        }

        public int getCaution() {
            return this.caution;
        }

        public void setCaution(int caution) {
            this.caution = caution;
        }

        public int getMemory() {
            return this.memory;
        }

        public void setMemory(int memory) {
            this.memory = memory;
        }

        public int getSpeech() {
            return this.speech;
        }

        public void setSpeech(int speech) {
            this.speech = speech;
        }

        public int getQuickness() {
            return this.quickness;
        }

        public void setQuickness(int quickness) {
            this.quickness = quickness;
        }

        public int getLogic() {
            return this.logic;
        }

        public void setLogic(int logic) {
            this.logic = logic;
        }

        public int getImagination() {
            return this.imagination;
        }

        public void setImagination(int imagination) {
            this.imagination = imagination;
        }

        public int getInsight() {
            return this.insight;
        }

        public void setInsight(int insight) {
            this.insight = insight;
        }

        public int getInfluence() {
            return this.influence;
        }

        public void setInfluence(int influence) {
            this.influence = influence;
        }

        public int getStamina() {
            return this.stamina;
        }

        public void setStamina(int stamina) {
            this.stamina = stamina;
        }

        public int getImmunity() {
            return this.immunity;
        }

        public void setImmunity(int immunity) {
            this.immunity = immunity;
        }
    }

    public static class Hospital {
        ArrayList<Box> boxes;
        ArrayList<Indi> doctors;
        ArrayList<Indi> nurses;
        ArrayList<Indi> patients;
        String name;

        public Hospital(ArrayList<Box> boxes, ArrayList<Indi> doctors, ArrayList<Indi> nurses, ArrayList<Indi> patients, String name) {
            this.boxes = boxes;
            this.doctors = doctors;
            this.nurses = nurses;
            this.patients = patients;
            this.name = name;
        }
    }

    public static class University {
        ArrayList<Speciality> specialities;
        ArrayList<Box> boxes;
        String name;

        public University(ArrayList<Speciality> specialities, ArrayList<Box> boxes, String name) {
            this.specialities = specialities;
            this.boxes = boxes;
            this.name = name;
        }
    }

    public static class Speciality {
        String name;
        int type;
        ArrayList<Discipline> disciplines;
        ArrayList<Indi> students;
        ArrayList<Indi> profs;

        public Speciality(String name, int type, ArrayList<Discipline> disciplines, ArrayList<Indi> indis, ArrayList<Indi> profs) {
            this.name = name;
            this.type = type;
            this.disciplines = disciplines;
            this.students = indis;
            this.profs = profs;
        }
    }

    public static class Discipline {
        String name;
        SkillsArray skills;
        int hours;

        public Discipline(String name, SkillsArray skills, int hours) {
            this.name = name;
            this.skills = skills;
            this.hours = hours;
        }
    }

    static void releaseAction(int num) {
        for (int q=0; q<objects.size(); q++) {
            if (objects.get(q).occ == num) {
                objects.get(q).occ = -1;
            }
        }
    }

    static int actionDelay(int code, int num) {
        switch (code) {
            case 1401: return 60000*society.getJobs().get(society.getIndi(cb, num).getJob()).getHours();
        }
        if (code > 9999) {
            for (int i=0;i<extraActs.size();i++) {
                if (extraActs.get(i).getCODE() == code)
                    return extraActs.get(i).getDelay() * 1000;
            }
        }
        FileHandle file = Gdx.files.internal("json/parameters.txt");
        try {
            jsonStr = new String(file.readString().getBytes(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HashMap<String, HashMap<String, Float>> testActions = json.fromJson(HashMap.class, HashMap.class, jsonStr);
        return Math.round(testActions.get("delay").containsKey("a" + code) ? testActions.get("delay").get("a" + code) : 1)*1000;
    }

    static ArrayList<Action> wait4smth (ArrayList<Action> actions, int myNum) {
        FileHandle file = Gdx.files.internal("json/parameters.txt");
        try {
            jsonStr = new String(file.readString().getBytes(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HashMap<String, HashMap<String, Float>> testActions = json.fromJson(HashMap.class, HashMap.class, jsonStr);
        int a = actions.get(0).action;

        switch (actions.get(0).action) {
            //ВАЖНО: в конце каждого case не забывать писать break
            case 1: {
                ArrayList possibleScenarios = new ArrayList<Integer>(Arrays.asList(1301, 1700, 4001, 4002));
                society.getIndi(cb, myNum).getScenarios().add(new Scenario((Integer) possibleScenarios.get(rnd(possibleScenarios.size())-1)));
                break;
            }
            case 11:
            case 13:
            case 15:
            case 17: {
                society.getIndi(cb, myNum).setWealth(society.getIndi(cb, myNum).getWealth());
                break;
            }
            case 12:
            case 14: {
                if (society.getIndi(cb, myNum).getInterests().get(0).getCulture() < 40)
                    society.getIndi(cb, myNum).getInterests().get(0).setCulture(society.getIndi(cb, myNum).getInterests().get(0).getCulture() + 1);
                break;
            }
            case 18: {
                int n = rnd(6);
                if (n == 1)
                    society.getIndi(cb, myNum).getInterests().get(0).setEconomics(society.getIndi(cb, myNum).getInterests().get(0).getEconomics() + 2);
                if (n == 2)
                    society.getIndi(cb, myNum).getInterests().get(0).setFashion(society.getIndi(cb, myNum).getInterests().get(0).getFashion() + 2);
                if (n == 3)
                    society.getIndi(cb, myNum).getInterests().get(0).setCulture(society.getIndi(cb, myNum).getInterests().get(0).getCulture() + 2);
                if (n == 4)
                    society.getIndi(cb, myNum).getInterests().get(0).setFood(society.getIndi(cb, myNum).getInterests().get(0).getFood() + 2);
                if (n == 5)
                    society.getIndi(cb, myNum).getInterests().get(0).setSport(society.getIndi(cb, myNum).getInterests().get(0).getSport() + 2);
                if (n == 6)
                    society.getIndi(cb, myNum).getInterests().get(0).setTechnics(society.getIndi(cb, myNum).getInterests().get(0).getTechnics() + 2);
                break;
            }
            case 100: {
                if (getIndiActor(myNum).marker != 1) {
                    getIndiActor(myNum).marker = 1;
                    getIndiActor(myNum).markerString = langString.get("iwantaests");}
                break;
            }
            case 200: {
                if (getIndiActor(myNum).marker != 1) {
                    getIndiActor(myNum).marker = 1;
                    getIndiActor(myNum).markerString = langString.get("iwanttopiss");}
                break;
            }
            case 201: {
                society.getIndi(cb, myNum).getNeeds().get(0).setBl(100);
                break;
            }
            case 202: {
                society.getIndi(cb, myNum).getNeeds().get(0).setBl(100);
                society.getIndi(cb, myNum).getNeeds().get(0).setHygiene(0);
                society.getIndi(cb, myNum).getNeeds().get(0).setSuccess(0);
                society.getIndi(cb, myNum).getNeeds().get(0).setFun(society.getIndi(cb, myNum).getNeeds().get(0).getFun() < 50 ? 0 : society.getIndi(cb, myNum).getNeeds().get(0).getFun() - 50);
                getIndiActor(myNum).marker = 1;
                getIndiActor(myNum).markerString = langString.get("ipissedmyself");
                break;
            }
            case 300: {
                if (getIndiActor(myNum).marker != 1) {
                    getIndiActor(myNum).marker = 1;
                    getIndiActor(myNum).markerString = langString.get("iwanttolearn");}
                break;
            }
            case 301: {
                society.getIndi(cb, myNum).getNeeds().get(0).setEducation(society.getIndi(cb, myNum).getNeeds().get(0).getEducation() + 40);
                break;
            }
            case 302: {
                society.getIndi(cb, myNum).getNeeds().get(0).setEducation(society.getIndi(cb, myNum).getNeeds().get(0).getEducation() + 5);
                society.getIndi(cb, myNum).getNeeds().get(0).setAesthetics(society.getIndi(cb, myNum).getNeeds().get(0).getAesthetics() + 10);
                break;
            }
            case 303:
            case 310: {
                if (rnd(3)<3)
                    society.getIndi(cb, myNum).getInterests().get(0).setEconomics(society.getIndi(cb, myNum).getInterests().get(0).getEconomics() + 3);
                break;
            }
            case 304:
            case 311: {
                if (rnd(3)<3)
                    society.getIndi(cb, myNum).getInterests().get(0).setFashion(society.getIndi(cb, myNum).getInterests().get(0).getFashion() + 3);
                break;
            }
            case 305:
            case 312:{
                if (rnd(3)<3)
                    society.getIndi(cb, myNum).getInterests().get(0).setCulture(society.getIndi(cb, myNum).getInterests().get(0).getCulture() + 3);
                break;
            }
            case 306:
            case 313:{
                if (rnd(3)<3)
                    society.getIndi(cb, myNum).getInterests().get(0).setFood(society.getIndi(cb, myNum).getInterests().get(0).getFood() + 3);
                break;
            }
            case 307:
            case 314:{
                if (rnd(3)<3)
                    society.getIndi(cb, myNum).getInterests().get(0).setSport(society.getIndi(cb, myNum).getInterests().get(0).getSport() + 3);
                break;
            }
            case 308:
            case 315:{
                if (rnd(3)<3)
                    society.getIndi(cb, myNum).getInterests().get(0).setTechnics(society.getIndi(cb, myNum).getInterests().get(0).getTechnics() + 3);
                break;
            }
            case 309: {
                int n = reserve(myNum, 8);
                int nn = rnd(6);
                if (n == -1) {
                    n = reserve(myNum, 9);
                }
                if (n != -1) {
                    actions.add(1, new Action(302 + nn, objects.get(n).getOX() + 20, objects.get(n).getOY() - 2, 0));
                }
                else {
                    actions.add(1, new Action(309 + nn, 200, 200, 0));
                }
                break;
            }
            case 400: {
                society.getIndi(cb, myNum).getNeeds().get(0).setEnergy(society.getIndi(cb, myNum).getNeeds().get(0).getEnergy() + rnd(50) +20);
                break;
            }
            case 600: {
                if (getIndiActor(myNum).marker != 1) {
                    getIndiActor(myNum).marker = 1;
                    getIndiActor(myNum).markerString = "мне скучно";}
                break;
            }
            case 603: {
                society.getIndi(cb, myNum).getNeeds().get(0).setFun(society.getIndi(cb, myNum).getNeeds().get(0).getFun() + rnd(5));
                if (rnd(2) == 2) {
                    poolEtt.play(0.4f * VOLUME, 1, 0);
                } else {
                    poolTva.play(0.4f * VOLUME, 1, 0);
                }
                indis.get(myNum).actions.get(0).etape += rnd(3) - 1;
                if (indis.get(myNum).actions.get(0).etape >= 8) {
                    indis.get(myNum).actions.get(0).action = 604;
                    wait4smth(indis.get(myNum).actions, myNum);
                }
                break;
            }
            case 700: {
                society.getIndi(cb, myNum).getNeeds().get(0).setHunger(society.getIndi(cb, myNum).getNeeds().get(0).getHunger() + 25);
                society.getBoxes().get(cb).setFoodRemain(society.getBoxes().get(cb).getFoodRemain() - 1);
                break;
            }
            case 701: {
                society.getIndi(cb, myNum).getNeeds().get(0).setHunger(society.getIndi(cb, myNum).getNeeds().get(0).getHunger() + 45);
                society.getBoxes().get(cb).setFoodRemain(society.getBoxes().get(cb).getFoodRemain() - rnd(3));
                break;
            }
            case 702: {
                if (getIndiActor(myNum).marker != 1) {
                    getIndiActor(myNum).marker = 1;
                    getIndiActor(myNum).markerString = langString.get("iwanttoeat");}
                break;
            }
            case 703: {
                society.getIndi(cb, myNum).getNeeds().get(0).setHunger(society.getIndi(cb, myNum).getNeeds().get(0).getHunger() + rnd(50));
                society.getIndi(cb, myNum).setWealth(society.getIndi(cb, myNum).getWealth() - rnd(50));
                break;
            }
            case 704: {
                getIndiActor(myNum).marker = 1;
                getIndiActor(myNum).markerString = langString.get("insuffunds704") + "(" + 50 + "$)";
                break;
            }
            case 800: {
                if (getIndiActor(myNum).marker != 1) {
                    getIndiActor(myNum).marker = 1;
                    getIndiActor(myNum).markerString = langString.get("iwanttoshower");}
                break;
            }
            case 801: {
                society.getIndi(cb, myNum).getNeeds().get(0).setHygiene(100);
                break;
            }
            case 1101: {
                for (int qq=0; qq<society.getBoxes().get(cb).getObjectsTest().size(); qq++) {
                    if (society.getBoxes().get(cb).getObjectsTest().get(qq).getType() == -3) {
                        society.getBoxes().get(cb).getObjectsTest().get(qq).setType(-20);
                        objects.get(qq).setTexture(SocietyScreen.whichObject(-20, -2));
                        break;
                    }
                }
                break;
            }
            case 1102: {
                for (int qq=0; qq<society.getBoxes().get(cb).getObjectsTest().size(); qq++) {
                    if (society.getBoxes().get(cb).getObjectsTest().get(qq).getType() == -4) {
                        society.getBoxes().get(cb).getObjectsTest().get(qq).setType(-20);
                        objects.get(qq).setTexture(SocietyScreen.whichObject(-20, -2));
                        break;
                    }
                }
                break;
            }
            case 1202: {
                if (society.getIndi(cb, myNum).getWealth() < society.getBoxes().get(cb).getBoxZ() * 20) {
                    getIndiActor(myNum).marker = 1;
                    getIndiActor(myNum).markerString = langString.get("insuffunds") + ":" + society.getBoxes().get(cb).getBoxZ() * 20 + "$";}
                else {
                    society.getIndi(cb, myNum).setWealth(society.getIndi(cb, myNum).getWealth() - society.getBoxes().get(cb).getBoxZ() * 20);
                    society.getBoxes().get(cb).setFoodRemain(society.getBoxes().get(cb).getFoodRemain() + 20);
                }
                break;
            }
            case 1300: {
                if (getIndiActor(myNum).marker != 1) {
                    getIndiActor(myNum).marker = 1;
                    getIndiActor(myNum).markerString = langString.get("imlonely");}
                break;
            }
            case 1301: {
                for (int i = 0; i < actions.get(0).indis.size(); i++) {
                    boolean b = false;
                    for (int j = 0; j < society.getIndi(cb, myNum).getRelations().size(); j++) {
                        if (society.getIndi(cb, myNum).getRelations().get(j).getIndiNum() == actions.get(0).indis.get(i).myNum) {
                            if (rnd(society.getIndi(cb, myNum).getTalents().get(0).getSpeech()) > 30 || society.getIndi(cb, myNum).getScenarioNums().contains(1301)) {
                                society.getIndi(cb, myNum).getRelations().get(j).setLevel(Relation.Levels.Friend);
                            }
                            else {society.getIndi(cb, myNum).getRelations().get(j).setLevel(Relation.Levels.Acquaintance);}
                            b = true;
                            break;
                        }
                    }
                    if (!b) {
                        society.getIndi(cb, myNum).getRelations().add(new Relation(actions.get(0).indis.get(i).myNum, Relation.Levels.Acquaintance));
                    }
                }
                break;
            }
            case 1304:
            case 1306:
            case 1307: {
                for (int i = 0; i < actions.get(0).indis.size(); i++) {
                    boolean b = false;
                    for (int j = 0; j < society.getIndi(cb, myNum).getRelations().size(); j++) {
                        if (society.getIndi(cb, myNum).getRelations().get(j).getIndiNum() == actions.get(0).indis.get(i).myNum) {
                            int probability = testActions.get("probability").containsKey("a" + actions.get(0).action) ? Math.round( testActions.get("probability").get("a" + actions.get(0).action)) : 101;
                            if (rnd(100) > probability & society.getIndi(cb, myNum).getRelations().get(j).getLevel().equals(Relation.Levels.Flirt)) {
                                society.getIndi(cb, myNum).getRelations().get(j).setLevel(SocietyScreen.Relation.Levels.Lover);
                                int loveExtra = testActions.get("loveExtra").containsKey("a" + actions.get(0).action) ? Math.round( testActions.get("loveExtra").get("a" + actions.get(0).action)) : 0;
                                society.getIndi(cb, myNum).setLove(society.getIndi(cb, myNum).getLove() + (int) (loveExtra * society.getIndi(cb, myNum).getSelfEsteem() / 5f));
                            }
                            b = true;
                            break;
                        }
                    }
                    if (!b) {
                        society.getIndi(cb, myNum).getRelations().add(new Relation(actions.get(0).indis.get(i).myNum, SocietyScreen.Relation.Levels.Flirt));
                    }
                }
                break;
            }
            case 1308:
            case 1309:
            case 1310: {
                for (int i = 0; i < actions.get(0).indis.size(); i++) {
                    boolean b = false;
                    for (int j = 0; j < society.getIndi(cb, myNum).getRelations().size(); j++) {
                        if (society.getIndi(cb, myNum).getRelations().get(j).getIndiNum() == actions.get(0).indis.get(i).myNum) {
                            int probability = testActions.get("probability").containsKey("a" + actions.get(0).action) ? Math.round( testActions.get("probability").get("a" + actions.get(0).action)) : 101;
                            if (rnd(100) > probability & society.getIndi(cb, myNum).getRelations().get(j).getLevel().equals(Relation.Levels.Lover)) {
                                society.getIndi(cb, myNum).getRelations().get(j).setLevel(SocietyScreen.Relation.Levels.LoveOfLife);
                                int loveExtra = testActions.get("loveExtra").containsKey("a" + actions.get(0).action) ? Math.round( testActions.get("loveExtra").get("a" + actions.get(0).action)) : 0;
                                society.getIndi(cb, myNum).setLove(society.getIndi(cb, myNum).getLove() + (int) (loveExtra * society.getIndi(cb, myNum).getSelfEsteem() / 3f));
                            }
                            b = true;
                            break;
                        }
                    }
                    if (!b) {
                        society.getIndi(cb, myNum).getRelations().add(new Relation(actions.get(0).indis.get(i).myNum, SocietyScreen.Relation.Levels.Flirt));
                    }
                }
                break;
            }
            case 1315:
            case 1316:
            case 1317:
            case 1318:
            case 1319:
            case 1320:
            case 1321: {
                for (int i = 0; i < actions.get(0).indis.size(); i++) {
                    for (int j = 0; j < society.getIndi(cb, myNum).getRelations().size(); j++) {
                        if (society.getIndi(cb, myNum).getRelations().get(j).getIndiNum() == actions.get(0).indis.get(i).myNum) {
                            if (rnd(society.getIndi(cb, myNum).getTalents().get(0).getSpeech()) > 30 || society.getIndi(cb, myNum).getScenarioNums().contains(1301)) {
                                society.getIndi(cb, myNum).getRelations().get(j).setLevel(Relation.Levels.CloseFriend);
                            }
                            if ((rnd(society.getIndi(cb, myNum).getTalents().get(0).getSpeech()) > 50 || society.getIndi(cb, myNum).getScenarioNums().contains(1301)) && rnd(3) == 1) {
                                society.getIndi(cb, myNum).getRelations().get(j).setLevel(Relation.Levels.BestFriend);
                            }
                            break;
                        }
                    }
                }
                break;
            }
            case 1401: {
                if (!society.getIndi(cb, myNum).getScenarioNums().contains(1401)) {
                    society.getIndi(cb, myNum).setWealth(society.getIndi(cb, myNum).getWealth() + society.getJobs().get(society.getIndi(cb, myNum).getJob()).getSalary());
                }
                else {
                    society.getIndi(cb, myNum).setWealth((int) (society.getIndi(cb, myNum).getWealth() + society.getJobs().get(society.getIndi(cb, myNum).getJob()).getSalary()*0.8));
                }
                break;
            }
            case 1601:
            case 2502:
            {
                getIndiActor(myNum).marker = 1;
                if (rnd(3) != 1) {
                    society.getIndi(cb, myNum).getNeeds().get(0).setFun(society.getIndi(cb, myNum).getNeeds().get(0).getFun() - 10);
                    int i = rnd(actions.get(0).action == 1601 ? society.getIndi(cb, myNum).getInterests().get(0).getEconomics() : society.getIndi(cb, myNum).getInterests().get(0).getTechnics() * 100);
                    society.getIndi(cb, myNum).setWealth(society.getIndi(cb, myNum).getWealth() - i);
                    getIndiActor(myNum).markerString = "not stonks: -"+i+"$";
                }
                else {
                    int i;
                    if (actions.get(0).action == 1601) {
                        society.getIndi(cb, myNum).getInterests().get(0).setEconomics(society.getIndi(cb, myNum).getInterests().get(0).getEconomics() + 1);
                        i = rnd(society.getIndi(cb, myNum).getInterests().get(0).getEconomics() * 100);
                    }
                    else {
                        society.getIndi(cb, myNum).getInterests().get(0).setTechnics(society.getIndi(cb, myNum).getInterests().get(0).getTechnics() + 1);
                        i = rnd(society.getIndi(cb, myNum).getInterests().get(0).getTechnics() * 30);
                    }
                    society.getIndi(cb, myNum).getNeeds().get(0).setFun(society.getIndi(cb, myNum).getNeeds().get(0).getFun() + 15);
                    society.getIndi(cb, myNum).setWealth(society.getIndi(cb, myNum).getWealth() + i);
                    getIndiActor(myNum).markerString = "stonks: +"+i+"$";
                }
                break;
            }
            case 2201: {
                getIndiActor(myNum).marker = 1;
                if (rnd(3) != 1) {
                    society.getIndi(cb, myNum).getNeeds().get(0).setEducation(20);
                    getIndiActor(myNum).markerString = "this articles are too difficult\nto understand\ni got nothing at all";
                }
                else {
                    society.getIndi(cb, myNum).getInterests().get(0).setFashion(society.getIndi(cb, myNum).getInterests().get(0).getFashion() + rnd(2));
                    society.getIndi(cb, myNum).getNeeds().get(0).setEducation(society.getIndi(cb, myNum).getNeeds().get(0).getEducation() + 45);
                }
                break;
            }
            case 2501: {
                getIndiActor(myNum).marker = 1;
                if (rnd(3) != 1) {
                    society.getIndi(cb, myNum).getNeeds().get(0).setFun(20);
                    getIndiActor(myNum).markerString = "this work is awful\ni'm so bad at this";
                    for (int i=0; i < society.getBoxes().get(cb).getIndisTest().size(); i++) {
                        if (society.getIndi(cb, i).getScenarioNums().contains(1601))
                        {
                            society.getIndi(cb, i).getNeeds().get(0).setFun(society.getIndi(cb, i).getNeeds().get(0).getFun() + 10);
                            getIndiActor(i).marker = 1;
                            getIndiActor(i).markerString = "axaxax";
                        }
                    }
                }
                else {
                    society.getIndi(cb, myNum).getInterests().get(0).setTechnics(society.getIndi(cb, myNum).getInterests().get(0).getTechnics() + 1);
                    society.getIndi(cb, myNum).getNeeds().get(0).setFun(society.getIndi(cb, myNum).getNeeds().get(0).getFun() + 45);
                    int i = rnd(society.getIndi(cb, myNum).getInterests().get(0).getTechnics() * 30)/2;
                    society.getIndi(cb, myNum).setWealth(society.getIndi(cb, myNum).getWealth() + i);
                    getIndiActor(myNum).markerString = "stonks: +"+i+"$";
                }
                break;
            }
            case 2901: {
                getIndiActor(myNum).marker = 1;
                if (rnd(3) != 1) {
                    society.getIndi(cb, myNum).getNeeds().get(0).setFun(society.getIndi(cb, myNum).getNeeds().get(0).getFun() - society.getIndi(cb, myNum).getInterests().get(0).getFilms() / 2);
                    getIndiActor(myNum).markerString = rnd(2) == 1 ? "this film is awful\nwhy i watched this?" : "who watches such a \nmisfit?";
                }
                else {
                    society.getIndi(cb, myNum).getInterests().get(0).setFilms(society.getIndi(cb, myNum).getInterests().get(0).getFilms() + 1);
                    society.getIndi(cb, myNum).getNeeds().get(0).setFun(society.getIndi(cb, myNum).getNeeds().get(0).getFun() + 50);
                    getIndiActor(myNum).markerString = "what a masterpiece!";
                }
                break;
            }
            case 3001: {
                society.getIndi(cb, myNum).getInterests().get(0).setMusic(society.getIndi(cb, myNum).getInterests().get(0).getMusic() + 1);
                getIndiActor(myNum).marker = 1;
                if (rnd(2) != 1) {
                    getIndiActor(myNum).markerString = "i'm not good at \nplaying piano today";
                    society.getIndi(cb, myNum).getNeeds().get(0).setFun(society.getIndi(cb, myNum).getNeeds().get(0).getFun() - 10);
                } else {
                    getIndiActor(myNum).markerString = "what a piano session!";
                    society.getIndi(cb, myNum).getInterests().get(0).setMusic(society.getIndi(cb, myNum).getInterests().get(0).getMusic() + 1);
                    society.getIndi(cb, myNum).getNeeds().get(0).setFun(society.getIndi(cb, myNum).getNeeds().get(0).getFun() + 15);
                }
                break;
            }
            default: {
                break;
            }
        }
        if (actions.get(0).action >= 303 && actions.get(0).action <= 308) {
            if (rnd(5)<3)
                society.getIndi(cb, myNum).getInterests().get(0).setBooks(society.getIndi(cb, myNum).getInterests().get(0).getBooks() + 1);
        }
        if (testActions.get("aesthetics").containsKey("a" + actions.get(0).action)) {
            society.getIndi(cb, myNum).getNeeds().get(0).setAesthetics(society.getIndi(cb, myNum).getNeeds().get(0).getAesthetics() + Math.round(testActions.get("aesthetics").get("a" + actions.get(0).action)));
        }
        if (testActions.get("education").containsKey("a" + actions.get(0).action)) {
            society.getIndi(cb, myNum).getNeeds().get(0).setEducation(society.getIndi(cb, myNum).getNeeds().get(0).getEducation() + Math.round(testActions.get("education").get("a" + actions.get(0).action)));
        }
        if (testActions.get("energy").containsKey("a" + actions.get(0).action)) {
            society.getIndi(cb, myNum).getNeeds().get(0).setEnergy(society.getIndi(cb, myNum).getNeeds().get(0).getEnergy() + Math.round(testActions.get("energy").get("a" + actions.get(0).action)));
        }
        if (testActions.get("fun").containsKey("a" + actions.get(0).action)) {
            society.getIndi(cb, myNum).getNeeds().get(0).setFun(society.getIndi(cb, myNum).getNeeds().get(0).getFun() + Math.round(testActions.get("fun").get("a" + actions.get(0).action)));
        }
        if (testActions.get("love").containsKey("a" + actions.get(0).action)) {
            society.getIndi(cb, myNum).getNeeds().get(0).setLove(society.getIndi(cb, myNum).getNeeds().get(0).getLove() + Math.round(testActions.get("love").get("a" + actions.get(0).action)));
        }
        if (testActions.get("lovePts").containsKey("a" + actions.get(0).action)) {
            society.getIndi(cb, myNum).setLove(society.getIndi(cb, myNum).getLove() + (int) (Math.round(testActions.get("lovePts").get("a" + actions.get(0).action)) * society.getIndi(cb, myNum).getSelfEsteem() / 5f));
        }
        if (testActions.get("shopping").containsKey("a" + actions.get(0).action)) {
            society.getIndi(cb, myNum).getNeeds().get(0).setShopping(society.getIndi(cb, myNum).getNeeds().get(0).getShopping() + Math.round(testActions.get("shopping").get("a" + actions.get(0).action)));
        }
        if (testActions.get("selfesteem").containsKey("a" + actions.get(0).action)) {
            society.getIndi(cb, myNum).setSelfEsteem(society.getIndi(cb, myNum).getSelfEsteem() + Math.round(testActions.get("selfesteem").get("a" + actions.get(0).action)) * rnd(4)-2);
        }
        if (testActions.get("social").containsKey("a" + actions.get(0).action)) {
            society.getIndi(cb, myNum).getNeeds().get(0).setSocial(society.getIndi(cb, myNum).getNeeds().get(0).getSocial() + Math.round(testActions.get("social").get("a" + actions.get(0).action)));
        }
        if (testActions.get("success").containsKey("a" + actions.get(0).action)) {
            if (!society.getIndi(cb, myNum).getScenarioNums().contains(1401)) {
                society.getIndi(cb, myNum).getNeeds().get(0).setSuccess(society.getIndi(cb, myNum).getNeeds().get(0).getSuccess() + Math.round(testActions.get("success").get("a" + actions.get(0).action)));
            }
            else
                society.getIndi(cb, myNum).getNeeds().get(0).setSuccess(society.getIndi(cb, myNum).getNeeds().get(0).getSuccess() + Math.round(testActions.get("success").get("a" + actions.get(0).action))/2);
        }
        if (testActions.get("wealth").containsKey("a" + actions.get(0).action)) {
            society.getIndi(cb, myNum).setWealth(society.getIndi(cb, myNum).getWealth() + Math.round(testActions.get("success").get("a" + actions.get(0).action)));
        }
        getIndiActor(myNum).status = "";
        getIndiActor(myNum).delay = 0;
        actions.remove(0);
        turnOn(actions, myNum, false);
        return actions;
    }

    static void turnOn (ArrayList<Action> actions, int myNum, boolean turn) {
        //сюда нужно заполнять все действия, чтобы не допускать дубликатов
        for (int i = 0; i < (turn ? actions.size() : 1); i++) {
            if (new ArrayList<Integer>(Arrays.asList(12, 14, 101, 302)).contains(actions.get(i).action)) {
                getIndiActor(myNum).actNeeds.put("aesthetics", turn);
            }
            if (new ArrayList<Integer>(Arrays.asList(200, 201, 202)).contains(actions.get(i).action)) {
                getIndiActor(myNum).actNeeds.put("bladder", turn);
            }
            if (new ArrayList<Integer>(Arrays.asList(18, 300, 301, 302, 303, 304, 305, 306, 307, 308, 309, 310, 311, 312, 313, 314, 315)).contains(actions.get(i).action)) {
                getIndiActor(myNum).actNeeds.put("education", turn);
            }
            if (new ArrayList<Integer>(Arrays.asList(400, 401, 402)).contains(actions.get(i).action)) {
                getIndiActor(myNum).actNeeds.put("energy", turn);
            }
            if (new ArrayList<Integer>(Arrays.asList(10, 16, 600, 601, 602, 603)).contains(actions.get(i).action)) {
                getIndiActor(myNum).actNeeds.put("fun", turn);
            }
            if (new ArrayList<Integer>(Arrays.asList(700, 701, 702, 703)).contains(actions.get(i).action)) {
                getIndiActor(myNum).actNeeds.put("hunger", turn);
            }
            if (new ArrayList<Integer>(Arrays.asList(800, 801, 802)).contains(actions.get(i).action)) {
                getIndiActor(myNum).actNeeds.put("hygiene", turn);
            }
            //простые действия с success тоже относятся сюда
            if (new ArrayList<Integer>(Arrays.asList(11, 13, 15, 17, 1300, 1301, 1302, 1303, 1304, 1305, 1306, 1307, 1308, 1309, 1310, 1311, 1312, 1313, 1314, 1315, 1316)).contains(actions.get(i).action)) {
                getIndiActor(myNum).actNeeds.put("social", turn);
                getIndiActor(myNum).actNeeds.put("love", turn);
                if (!turn) getIndiActor(myNum).setTargetX(getIndiActor(myNum).getTargetX() + 1);
            }
            if (new ArrayList<Integer>(Arrays.asList(1200, 1201, 1202, 1203, 1204, 1205)).contains(actions.get(i).action)) {
                getIndiActor(myNum).actNeeds.put("shopping", turn);
            }
            if (new ArrayList<Integer>(Arrays.asList(1400, 1401, 1402)).contains(actions.get(i).action)) {
                getIndiActor(myNum).actNeeds.put("working", turn);
            }
            if (new ArrayList<Integer>(Arrays.asList(1601, 1602, 1603)).contains(actions.get(i).action)) {
                getIndiActor(myNum).actNeeds.put("economics", turn);
            }
            if (new ArrayList<Integer>(Arrays.asList(2201, 2202, 2203)).contains(actions.get(i).action)) {
                getIndiActor(myNum).actNeeds.put("science", turn);
            }
            if (new ArrayList<Integer>(Arrays.asList(2501, 2502, 2503)).contains(actions.get(i).action)) {
                getIndiActor(myNum).actNeeds.put("technics", turn);
            }
            if (new ArrayList<Integer>(Arrays.asList(2800, 2801, 2802)).contains(actions.get(i).action)) {
                getIndiActor(myNum).actNeeds.put("books", turn);
            }
            if (new ArrayList<Integer>(Arrays.asList(2900, 2901, 2902)).contains(actions.get(i).action)) {
                getIndiActor(myNum).actNeeds.put("film", turn);
            }
            if (new ArrayList<Integer>(Arrays.asList(3000, 3001, 3002)).contains(actions.get(i).action)) {
                getIndiActor(myNum).actNeeds.put("music", turn);
            }
        }
    }

    static String specialStatus(int action, ArrayList<Action> actions) {
        String status;
        try {
            status = langString.get("status" + action);
        }
        catch (MissingResourceException e) {
            status = action + "";
        }
        if (action == 301) {
            FileHandle file = Gdx.files.internal("json/DEFAULTCITY/books.txt");
            try {
                jsonStr = new String(file.readString().getBytes(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            ArrayList<SocietyScreen.Book> books = json.fromJson(ArrayList.class, SocietyScreen.Book.class, jsonStr);
            return langString.get("status301") + ": " +books.get(rnd(books.size())-1).name;
        }
        else if (new ArrayList<Integer>(Arrays.asList(303, 304, 305, 306, 307, 308, 310, 311, 312, 313, 314, 315)).contains(action)) {
            FileHandle file;
            switch (action) {
                case 303:
                case 310: {
                    file = Gdx.files.internal("json/DEFAULTCITY/booksEconomics.txt");
                    break;
                }
                case 304:
                case 311: {
                    file = Gdx.files.internal("json/DEFAULTCITY/booksScience.txt");
                    break;
                }
                case 305:
                case 312: {
                    file = Gdx.files.internal("json/DEFAULTCITY/booksCulture.txt");
                    break;
                }
                case 306:
                case 313: {
                    file = Gdx.files.internal("json/DEFAULTCITY/booksFood.txt");
                    break;
                }
                case 307:
                case 314: {
                    file = Gdx.files.internal("json/DEFAULTCITY/booksSport.txt");
                    break;
                }
                case 308:
                case 315: {
                    file = Gdx.files.internal("json/DEFAULTCITY/booksTechnics.txt");
                    break;
                }
                default: {
                    file = Gdx.files.internal("json/DEFAULTCITY/books.txt");
                    break;
                }
            }
            try {
                jsonStr = new String(file.readString().getBytes(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                jsonStr = "";
            }
            ArrayList<SocietyScreen.Book> books = json.fromJson(ArrayList.class, SocietyScreen.Book.class, jsonStr);
            return langString.get("status301") + ": " +books.get(rnd(books.size())-1).name;
        }
        else if (action == 602) {
            FileHandle file = Gdx.files.internal("json/DEFAULTCITY/channels.txt");
            try {
                jsonStr = new String(file.readString().getBytes(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            ArrayList<Channel> channels = json.fromJson(ArrayList.class, Channel.class, jsonStr);
            int n = rnd(channels.size())-1;
            return langString.get("status602") + ": " + channels.get(n).name;
        }
        else if (action == 701) return langString.get("status701") + ": " +dishes.get(rnd(8)-1).name;
        else if (action / 100 == 13) {
            String str = "";
            try {
                str = langString.get("status" + action) + ": ";
            }
            catch (MissingResourceException e) {
                str = action + ":";
            }
            try {
                for (int i = 0; i < actions.get(0).indis.size(); i++) {
                    if (i == 0)
                        str += society.getIndi(cb, actions.get(0).indis.get(i).myNum).getName() + " " + society.getIndi(cb, actions.get(0).indis.get(i).myNum).getSurname();
                    else
                        str += ", " + society.getIndi(cb, actions.get(0).indis.get(i).myNum).getName() + " " + society.getIndi(cb, actions.get(0).indis.get(i).myNum).getSurname();
                }
            }
            catch (NullPointerException e) {
                return "NPE with 1st action indis size";
            }
            return str;}
        else if (action > 9999) {
            for (int i=0;i<extraActs.size();i++) {
                if (extraActs.get(i).getCODE() == action)
                    try {
                        return extraActs.get(i).getEn()[0];
                    }
                    catch (IndexOutOfBoundsException e) {
                        return action + "";
                    }
            }
        }
        return status;
    }

    static String specialStatus(int action, Action act) {
        String status;
        try {
            status = langString.get("status" + action);
        }
        catch (MissingResourceException e) {
            status = action + "";
        }
        if (action / 100 == 13) {
            String str;
            try {
                str = langString.get("status" + action) + ": ";
            }
            catch (MissingResourceException e) {
                str = action + ":";
            }
            try {
                for (int i = 0; i < act.indis.size(); i++) {
                    if (i == 0)
                        str += society.getIndi(cb, act.indis.get(i).myNum).getName() + " " + society.getIndi(cb, act.indis.get(i).myNum).getSurname();
                    else
                        str += ", " + society.getIndi(cb, act.indis.get(i).myNum).getName() + " " + society.getIndi(cb, act.indis.get(i).myNum).getSurname();
                }
            }
            catch (NullPointerException e) {
                return "NPE with action indis list.";
            }
            catch (IndexOutOfBoundsException e) {
                return "Index out of bounds exception.";
            }
            return str;
        }
        if (action > 9999) {
            for (int i=0;i<extraActs.size();i++) {
                if (extraActs.get(i).getCODE() == action)
                    try {
                        return extraActs.get(i).getEn()[0];
                    }
                    catch (IndexOutOfBoundsException e) {
                        return action + "";
                    }
            }
        }
        return status;
    }

    static int reserve(int myNum, int type) {
        int n = -1;
        for (int i = 0; i < count(type); i++) {
            if (objects.get(numberM(i, type)).occ == -1) {
                n = numberM(i, type);
                objects.get(n).occ = myNum;
                break;
            }
        }
        return n;
    }

    static int observe(int type) {
        //тот же самый reserve, но без занимания предмета
        int n = -1;
        for (int i = 0; i < count(type); i++) {
            if (objects.get(numberM(i, type)).occ == -1) {
                n = numberM(i, type);
                break;
            }
        }
        return n;
    }

    static int checkUnocc(int type) {
        for (int i = 0; i < count(type); i++) {
            if (objects.get(numberM(i, type)).occ == -1) {
                return 1;
            }
        }
        return 0;
    }
}

