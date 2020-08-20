package com.peoplebox;


import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Json;

import java.util.ArrayList;

public class BoxScreen implements Screen {
    final Game game;
    String jsonStr;

    public BoxScreen(Game gam) {
        game = gam;
        /*SocietyTest.SocietyBox society = new SocietyTest.SocietyBox();
        society.setName("testing_ett");
        society.setSizeX(128);
        society.setSizeY(128);
        ArrayList indis = new ArrayList();
        ArrayList inits = new ArrayList();
        ArrayList needs = new ArrayList();
        ArrayList talents = new ArrayList();
        ArrayList skills = new ArrayList();
        ArrayList lifts = new ArrayList();
        ArrayList creations = new ArrayList();
        ArrayList genderProps = new ArrayList();
        ArrayList relations = new ArrayList();
        ArrayList interests = new ArrayList();
        inits.add(new SocietyTest.InitsArray());
        needs.add(new SocietyTest.NeedsArray(100, 100, 100, 50, 20, 100, 70, 42, 52, 83, -16, -56, 99, 3));
        talents.add(new SocietyTest.TalentsArray(4,4,4,4,4,4,4,4,4,4));
        skills.add(new SocietyTest.SkillsArray(2,3,4));
        lifts.add(new SocietyTest.SocialLiftsArray(0, true, false, false, true));
        creations.add(new SocietyTest.CreationsArray());
        //creations.add(new CreationsArray(new Creation("name", "surname", "painting", "test painting", 250, 1)));
        genderProps.add(new SocietyTest.GenderPropsArray(0,1,0));
        relations.add(new SocietyTest.RelationsArray());
        interests.add(new SocietyTest.InterestsArray());
        //indis.add(new SocietyTest.Indi("2253", "Alpha", 1, 25, 1, 100, 0, 3999, 11, 15, 1, inits, needs, talents, skills, lifts, creations, genderProps, relations, interests, 1, 1, 9, 150, 150));
        //indis.add(new SocietyTest.Indi("1400", "Omega", 0, 18, 4, 101, 0, 118, 15, 22, 6, inits, needs, talents, skills, lifts, creations, genderProps, relations, interests, 2, 2, 4, 150,150));
        //SocietyTest.Indi IndiDebug = indis.get(1);

        //indis.set(1, new SocietyTest.Indi("2253", "Alpha", 1, 35, 1, 100, 0, 3999, 11, 15, 1, inits, needs, talents, skills, lifts, creations, genderProps, relations, interests, 1, 1, 9, 150, 150));
        //indis.set(1, SocietyTest.Indi.setName("qwerty"));
        society.setIndis(indis);
        Json json = new Json();
        jsonStr = json.prettyPrint(society);*/
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
