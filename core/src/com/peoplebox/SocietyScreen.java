package com.peoplebox;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.lwjgl.Sys;

import java.lang.StringBuilder;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;
import java.util.Comparator;
import java.util.Collections;
import java.util.ArrayList;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.peoplebox.Addition.*;
import static com.peoplebox.ProfitEnum.*;
import static com.peoplebox.ScreenshotFactory.saveScreenshot;
import static com.peoplebox.SocietyScreen.Box.Property.Private;

public class SocietyScreen extends ApplicationAdapter implements Screen, GestureDetector.GestureListener {
    ArrayList<SortCard> sortList = new ArrayList<SortCard>();
    private static ArrayList<FurnCard> furn = new ArrayList<FurnCard>();
    ArrayList<Marker> markers = new ArrayList<Marker>();
    ArrayList<Dish> dishes = new ArrayList<Dish>(Arrays.asList(new Dish("Макароны с сыром", 10, 2), new Dish("Пирог с картофелем", 6, 4), new Dish("Панкейки", 7, 4), new Dish("Брауни", 12, 6), new Dish("Лазанья", 10, 5), new Dish("Пицца \"Маргарита\"", 8, 3), new Dish("Гаспачо", 15, 9), new Dish("Яблочный пирог", 7, 5)));
    ArrayList<ScenButton> scenButtons = new ArrayList<ScenButton>();
    int dd=1, mm=5, yyyy=2015, hh=21, min=50, dw=0, cardType = 0, wwww = rnd(4), speed = 1000, timeFPS = 0, VOLUME = 1, currentGlobalNum = -2;
    int neededY = 0; //по дефолту равна половине высоты экрана, переопределяется в рендере с каждой прорисовкой
    static long uiDelay, tapDelay, currentTimeMil = System.currentTimeMillis();
    DecimalFormat df = new DecimalFormat("#.##");
    final Game game;
    final String FONT_CHARS = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяåäöÀàÂâÇçÉéÈèÊêÎîÔôÙùÛûŸÿÜüŒœÏïÆæabcdefghijklmnopqrstuvwxyzАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯÅÄÖABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"?`'<>°";
    static I18NBundle langString;
    public String str, dest, jsonStr, lang, globalListString = "";
    static Label label, labelGirl, labelAppuyez, labelHint, labelHintTva, labelHintTre, labelHintFyra, labelCenter, labelDebug, labelControl, nameView,
            labelEvent, labelTime, labelDay, labelGamePts, labelReset, labelRandomScenario;
    static Label labelBladder, labelEnergy, labelHunger, labelEducation, labelEnv, labelFun, labelHygiene, labelLove, labelPower, labelSafety, labelShopping,
            labelSocial, labelAesthetics, labelSuccess;
    static Label labelPolitics, labelEconomics, labelHealth, labelCrimes, labelScience, labelCulture, labelFood, labelFashion, labelSport, labelTechnics,
            labelTravel, labelJob, labelAnimals, labelBooks, labelFilms, labelMusic, labelHistory, labelMystic;
    static Label labelCaution, labelImagination, labelImmunity, labelInfluence, labelInsight, labelLogic, labelMemory, labelQuickness, labelSpeech, labelStamina, labelTemperature,
            labelWifi, labelWater, labelElectricity, labelCellular, labelCelsius, labelAudience, labelRating, labelBroadcastStart, labelBroadcastFinish;
    static TextField nameField, surnameField, channelField;
    enum HoldObject { Knife, Book, Spray, Mixer, Pan, Extinguisher, Phone1, Phone2, None }
    public static Stage stage, cardStage, girlStage;
    public ExtendViewport stageViewport, girlStageViewport;
    Texture btnPreview, buttonBackground, NIMBUS, talkCloudEtt, talkCloudTva;
    static Sound talkEtt, talkTva, talkTre, poolEtt, poolTva, uiNo, oof= Gdx.audio.newSound(Gdx.files.internal("sound/oof.mp3"));
    //clickMode: 1-движение камеры 2-движение мебели
    int homex, homey, homez, control = 0, indisN = 0, clickMode = 1, girlCard = 1, homeZZ = 0, y = -900, num = 0, num0 = 0, jobN = 0, indiObs = -2, groupObs = -1,
            objectObs = -1, adaptResTre = 4, card = 0;
    static int currentFloor = 1, adaptRes = 5, popAct = 0, indisTestN = 0, FPS = 0, furnBefore = 0, ff=1, gamePts = 0;
    final static int cb = 0;
    static int GAMEMODE = 0;
    ArrayList needsOnly = new ArrayList<Integer>(Arrays.asList(3));
    float rotationSpeed = 0.5f;
    static double screenCoef = 1.0;
    static CoreFunc coreFunc;
    static Card cardEtt, cardTva;
    static Music bckMusic = Gdx.audio.newMusic(Gdx.files.internal("sound/oof.mp3"));
    static boolean LOADING, DEBUG = false, girl = false;
    static BitmapFont fontFranTva, fontOswaldFurnPrice;
    BitmapFont fontFran, fontFranTre, smallFontFran, smallFontFranRed, fontOswald, fontOswaldTre, fontOldGirlStatus, fontOldGirl, fontOswaldBlack, font = new BitmapFont();
    static InputMultiplexer multiplexer;
    static ScrollPane scrollPane, scrollPaneTva, scrollPaneFurn, scrollPaneTre, scrollPaneRelations, scrollPaneIndis, scrollPaneSystem;
    static TextButton.TextButtonStyle tbsFyra, tbsFem, tbsTre, tbs;
    Custom background, walls, panelLeft, panelEvent, iconEvent, oldGirl, arrowHint, clock, frameBorder, panelRight;
    CustomIcon btnReset, btnRandomScenario, btnTrash;
    static TextButton applyName, btnHelpEtt, btnHelpTva, girlYes, girlNo, btnUp, btnDown, btnRight, btnLeft,
            btnMoveOk, btnOverlay, btnRotate, btnMore, btnRemove, buttonBack, buttonRemove, buttonAdd;
    private TextureAtlas atlas, atlasTre;
    private Skin skin, skinTre;
    private static Table tableMove, tableDebugActions, tableNeeds, tableInterests, tableRoom, tableTalents, outerTable, outerTableTva, outerTableNeeds, iconTable,
            iconTableSystem, furnTable, outerTableFurn, tableBroadcast, tableRelations, outerTableRelations, tableRelationsChoose, tableIndis, outerTableIndis,
            systemTable, outerSystemTable;
    private Group groupBuild = new Group();
    static Society society = new Society();
    RoomController controller = new RoomController(0,0);
    Json json = new Json();
    List<String> list4check;

    static ArrayList<com.peoplebox.additions.Action> extraActs = new ArrayList<com.peoplebox.additions.Action>();
    static ArrayList<IndiActor> indis = new ArrayList<IndiActor>();
    static ArrayList<Indi> indisTest = new ArrayList<Indi>();
    static ArrayList<Box> boxes = new ArrayList<Box>();
    static ArrayList<ObjectActor> objects = new ArrayList<ObjectActor>();
    static ArrayList<ObjectTest> objectsTest = new ArrayList<ObjectTest>();
    private static ArrayList<Relation> relations = new ArrayList<Relation>();
    transient static ArrayList<SocialAct> socialActivities = new ArrayList<SocialAct>();
    private static ArrayList<Addition.Channel> channels = new ArrayList<Addition.Channel>();
    static ArrayList<Addition.University> univers = new ArrayList<Addition.University>();
    ArrayList<Addition.Speciality> specs = new ArrayList<Addition.Speciality>();
    ArrayList<Addition.Discipline> disps = new ArrayList<Addition.Discipline>();

    //ТОЧКА ВХОДА
    public SocietyScreen(Game gam, int load, String language, int homeX, int homeY, int homeZ) {
        game = gam;
        lang = language;
        homex = homeX;
        homey = homeY;
        homez = homeZ;
        indiObs = -1;
        stageViewport = new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stageViewport.apply();
        stage = new Stage(stageViewport);
        girlStageViewport = new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        girlStageViewport.apply();
        girlStage = new Stage(girlStageViewport);
        //Custom metromap = new Custom(new Texture(Gdx.files.local("E/json/DEFAULTCITY/screenshot.png")), 2500, 0);
        //girlStage.addActor(metromap);
        dest = "0";
        Gdx.app.log("address", homex + " " + homey + " " + homez);
        if (Gdx.graphics.getHeight() < 500 || Gdx.graphics.getWidth() < 1000) {
            cardStage = new Stage(new ScreenViewport());
            ((OrthographicCamera) cardStage.getCamera()).zoom = (((float) Gdx.graphics.getHeight() / 1080 + (float) Gdx.graphics.getWidth() / 1920) / 2);

        } else {
            cardStage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        }
        cardStage.getViewport().setScreenSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        PerformanceCounter pcounter = new PerformanceCounter("pcounter");
        pcounter.start();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/fran.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.characters = FONT_CHARS;
        parameter.size = 24;
        fontFran = generator.generateFont(parameter);
        fontFran.setColor(Color.BLACK);

        if (lang == "ru") {
            langString = I18NBundle.createBundle(Gdx.files.internal("strings/strings_ru"));
        } else if (lang == "en") {
            langString = I18NBundle.createBundle(Gdx.files.internal("strings/strings_en"));
        } else if (lang == "fr") {
            langString = I18NBundle.createBundle(Gdx.files.internal("strings/strings_fr"));
        } else if (lang == "sv") {
            langString = I18NBundle.createBundle(Gdx.files.internal("strings/strings_sv"));
        } else {
            langString = I18NBundle.createBundle(Gdx.files.internal("strings/strings"));
        }

        coreFunc = new CoreFunc();

        coreFunc.load = 0;
        //screenCoef = Double.parseDouble(String.valueOf(Gdx.graphics.getHeight())) / 1080;

        parameter.characters = FONT_CHARS;
        parameter.size = 16;
        generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/fran.ttf"));
        smallFontFran = generator.generateFont(parameter);
        smallFontFran.setColor(Color.BLACK);

        parameter.characters = FONT_CHARS;
        parameter.size = 16;
        generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/fran.ttf"));
        smallFontFranRed = generator.generateFont(parameter);
        smallFontFranRed.setColor(Color.CYAN);

        parameter.characters = FONT_CHARS;
        parameter.size = 28;
        generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/fran.ttf"));
        fontFranTre = generator.generateFont(parameter);
        fontFranTre.setColor(Color.WHITE);

        parameter.characters = FONT_CHARS;
        parameter.size = 40;
        generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/oswaldb.ttf"));
        fontOswald = generator.generateFont(parameter);
        fontOswald.setColor(Color.BLACK);

        parameter.characters = FONT_CHARS;
        parameter.size = 28;
        generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/oswaldb.ttf"));
        fontOswaldFurnPrice = generator.generateFont(parameter);
        fontOswaldFurnPrice.setColor(Color.CYAN);

        parameter.characters = FONT_CHARS;
        parameter.size = 60;
        if (Gdx.graphics.getHeight() > 799) {
            parameter.size = 64;
        }
        generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/oswaldb.ttf"));
        fontOswaldTre = generator.generateFont(parameter);
        fontOswaldTre.setColor(Color.BLACK);

        parameter.characters = FONT_CHARS;
        parameter.size = 54;
        generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/oswaldb.ttf"));
        fontOswaldBlack = generator.generateFont(parameter);
        fontOswaldBlack.setColor(Color.BLACK);

        parameter.characters = FONT_CHARS;
        parameter.size = 20;
        generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/fran.ttf"));
        fontOldGirl = generator.generateFont(parameter);
        fontOldGirl.setColor(Color.WHITE);

        parameter.characters = FONT_CHARS;
        parameter.size = 14;
        generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/fran.ttf"));
        fontOldGirlStatus = generator.generateFont(parameter);
        fontOldGirlStatus.setColor(Color.WHITE);

        parameter.characters = FONT_CHARS;
        parameter.size = 40;
        generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/fran.ttf"));
        fontFranTva = generator.generateFont(parameter);


        generator.dispose(); // don't forget to dispose to avoid memory leaks!

        btnPreview = new Texture("ui/buttonPreview.png");
        buttonBackground = new Texture("ui/b4.png");
        NIMBUS = new Texture("nimbusDeux.png");
        talkCloudEtt = new Texture("talks/talkcloud1.png");
        talkCloudTva = new Texture("talks/talkcloud2.png");
        talkEtt = Gdx.audio.newSound(Gdx.files.internal("sound/whitenoise.wav"));
        talkTva = Gdx.audio.newSound(Gdx.files.internal("sound/pinknoise.wav"));
        talkTre = Gdx.audio.newSound(Gdx.files.internal("sound/morse.wav"));
        poolEtt = Gdx.audio.newSound(Gdx.files.internal("sound/pool1.wav"));
        poolTva = Gdx.audio.newSound(Gdx.files.internal("sound/pool2.wav"));
        uiNo = Gdx.audio.newSound(Gdx.files.internal("sound/no.mp3"));
        panelLeft = new SocietyScreen.Custom(new Texture("ui/panel7.png"), 0, 0);
        panelLeft.setVisible(true);
        cardStage.addActor(panelLeft);
        panelRight = new SocietyScreen.Custom(new Texture("ui/panel7.png"), Gdx.graphics.getWidth()-160, 0);
        panelRight.setVisible(false);
        cardStage.addActor(panelRight);
        cardEtt = new Card(0, "", 10, Gdx.graphics.getHeight() - 70);
        cardTva = new Card(0, "", 450, Gdx.graphics.getHeight() - 175);
        cardStage.addActor(cardEtt);
        cardStage.addActor(cardTva);

        cardTva.setVisible(false);
        //background.setZIndex(0);

        atlas = new TextureAtlas("ui/b.atlas");
        skin = new Skin(atlas);
        tbs = new TextButton.TextButtonStyle();
        tbs.up = skin.getDrawable("button.9");
        tbs.down = skin.getDrawable("button.9");
        tbs.pressedOffsetX = 1;
        tbs.pressedOffsetY = -1;
        tbs.font = fontFran;
        tbs.fontColor = Color.BLACK;

        atlasTre = new TextureAtlas("ui/b2.atlas");
        skinTre = new Skin(atlasTre);
        tbsTre = new TextButton.TextButtonStyle();
        tbsTre.up = skinTre.getDrawable("button.9");
        tbsTre.down = skinTre.getDrawable("button.9");
        tbsTre.pressedOffsetX = 1;
        tbsTre.pressedOffsetY = -1;
        tbsTre.font = fontOswald;
        tbsTre.fontColor = Color.BLACK;
        tbsTre.downFontColor = Color.DARK_GRAY;

        tbsFem = new TextButton.TextButtonStyle();
        tbsFem.up = skin.getDrawable("button.9");
        tbsFem.down = skin.getDrawable("button.9");
        tbsFem.pressedOffsetX = 1;
        tbsFem.pressedOffsetY = -1;
        tbsFem.font = fontOswaldBlack;
        tbsFem.fontColor = Color.BLACK;

        furnBefore = objects.size() / 2 - 1;
        oldGirl = new Custom(new Texture("ui/oldgirl5.png"), 400, Gdx.graphics.getHeight() - 450);
        arrowHint = new Custom(new Texture("ui/arrows.png"), 540, Gdx.graphics.getHeight() / 2 - 122);
        arrowHint.setVisible(false);
        labelGirl = new Label("Привет, я твоя проводница в этой игре. \nЗдесь много всего, очень большая комната, правда?", new LabelStyle(fontOldGirl, Color.WHITE));
        labelGirl.setPosition(530, Gdx.graphics.getHeight() - 370);
        labelAppuyez = new Label("НАЖМИТЕ, ЧТОБЫ ПРОДОЛЖИТЬ", new LabelStyle(fontOldGirl, Color.CYAN));
        labelAppuyez.setPosition(720, Gdx.graphics.getHeight() - 435);
        girlYes = new TextButton("Да", tbsTre);
        girlNo = new TextButton("Нет", tbsTre);
        girlYes.setPosition(550, Gdx.graphics.getHeight() - 450);
        girlNo.setPosition(600, Gdx.graphics.getHeight() - 450);
        girlYes.setBounds(550, Gdx.graphics.getHeight() - 450, 50, 50);
        girlNo.setBounds(600, Gdx.graphics.getHeight() - 450, 50, 50);
        girlYes.pad(15);
        girlNo.pad(15);
        labelAppuyez.setVisible(false);
        labelGirl.setVisible(false);
        oldGirl.setVisible(false);

        girlYes.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                girlCard = 1;
            }
        });

        girlNo.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                girlCard = 0;
            }
        });


        FileHandle fileTva = Gdx.files.local("E/json/DEFAULTCITY/channels.txt");
        if (!fileTva.exists()) {
            fileTva = Gdx.files.internal("json/DEFAULTCITY/channels.txt");
        }
        jsonStr = fileTva.readString();
        channels = json.fromJson(ArrayList.class, jsonStr);
        frameBorder = new Custom(new Texture("ui/null.png"), 0, 0);









        //furn.add(new FurnCard(f, 400, 1, 1, "Холодильник Hanco Freeze", "Холодильник Ханко Фриз всегда был довольно экономичным вариантом. Его особенность в очень долгом сроке службы. Гарантия на 40 лет вас устроит?", new Texture("kitchen/fridge11.png")));
        //furn.add(new FurnCard(f, 400, 15, 1, 2006, 2029, "Буфет \"Недешёвый\"", "", new Texture("comm/buffet12.png")));
        //furn.add(new FurnCard(f, 400, 3, 1, "Бабушкина лампа", "", new Texture("lamps/lamp1.png")));
        //furn.add(new FurnCard(f, 400, 3, 2, "Параллелепипедовидная лампа", "", new Texture("lamps/lamp2.png")));
        //furn.add(new FurnCard(f, 400, 4, 5, "Кухонный стол", "", new Texture("tables/table41.png")));
        //furn.add(new FurnCard(f, 400, 5, 5, "Телевизор \"Протон\"", "", new Texture("tvs/tvset21.png")));
        //furn.add(new FurnCard(f, 400, 5, 9, 1981, 1989,"Телевизор Hanco \nProVision", "", new Texture("tvs/tvset32.png")));
        //furn.add(new FurnCard(f, 400, 7, 2, "Книга", "", new Texture("deco/book11.png")));
        //furn.add(new FurnCard(f, 400, 7, 4, "Квадрат в квадрате", "", new Texture("deco/painting11.png")));
        //furn.add(new FurnCard(f, 400, 7, 10, "Не самая античная статуя", "", new Texture("deco/statue11.png")));
        //furn.add(new FurnCard(f, 400, 8, 1, "Кресло 1", "", new Texture("armchairs/armchair11.png")));
        //furn.add(new FurnCard(f, 400, 8, 5, "Кресло 2", "", new Texture("armchairs/armchair21.png")));
        //furn.add(new FurnCard(f, 400, 17, 1, "Динамик", "", new Texture("audios/speaker11.png")));
        //furn.add(new FurnCard(f, 400, 18, 1, "Отдельная комната", "", new Texture("walls1.png")));
        //furn.add(new FurnCard(f, 400, 21, 1, "Швейная машинка", "", new Texture("skills/sewing11.png")));
        //furn.add(new FurnCard(0, 400, 7, 14, 1987, 1991, "Antenne 2", "", new Texture("debugItems/antenne2.png")));
        //furn.add(new FurnCard(400, 23, 1, "Я человек занятой", "", new Texture("office/officecorner11.png")));
        //furn.add(new FurnCard(400, 23, 5, "I'm busy AF", "", new Texture("office/officecorner21.png")));
        //furn.add(new FurnCard(f, 400, 7, 52, "Суфлёр", "", new Texture("tvstation/prompter12.png")));
        //furn.add(new FurnCard(f, 400, 3, 5, "Осветитель", "", new Texture("tvstation/light12.png")));





        indis.clear();
        objects.clear();
        stage.clear();
        indisTest.clear();
        objectsTest.clear();
        Box boxTva;
        society.setName("testing_tva");
        society.setSizeX(128);
        society.setSizeY(128);
        society.setJobs(new ArrayList<Job>());
        society.setInits(new ArrayList<Init>());
        createJob("Ничегонеделатель", 400, 0, new SkillsArray(0,0,0));
        createJob("Копирайтер", 250, 1, new SkillsArray(0, 0, 0));
        createJob("Веб-дизайнер", 750, 1, new SkillsArray(4, 0, 0));
        createJob("Посудомойщик", 200, 0, new SkillsArray(0, 0, 0));
        createJob("Врач", 2500, 0, new SkillsArray(0, 0, 10));
        createJob("Учитель", 600, 0, new SkillsArray(0, 5, 0));
        if (currentGlobalNum == -2) {
            Json json = new Json();
            Gdx.app.log("local storage path", Gdx.files.getLocalStoragePath());
            FileHandle file = Gdx.files.local("E/json/globalnumcounter.txt");
            if (file.exists()) {
                jsonStr = file.readString();
                Gdx.app.log("JSON currentGlobalNum", jsonStr);
                currentGlobalNum = json.fromJson(Integer.class, jsonStr);
            } else {
                file = Gdx.files.internal("json/globalnumcounter.txt");
                if (file.exists()) {
                    jsonStr = file.readString();
                    Gdx.app.log("JSON currentGlobalNum", jsonStr);
                    currentGlobalNum = json.fromJson(Integer.class, jsonStr);
                } else {
                    currentGlobalNum = 0;
                }
            }
        }
        disps.add(new Addition.Discipline("Теоретическая хирургия", new Addition.SkillsArray(0,0,5), 10));
        specs.add(new Addition.Speciality("Прикладная хирурия", 0, disps, new ArrayList<Indi>(), new ArrayList<Indi>()));
        disps.clear();
        disps.add(new Addition.Discipline("Терапия", new Addition.SkillsArray(0,0,5), 5));
        specs.add(new Addition.Speciality("Общая терапия", 0, disps, new ArrayList<Indi>(), new ArrayList<Indi>()));
        specs.add(new Addition.Speciality("Сестринское дело", 0, disps, new ArrayList<Indi>(), new ArrayList<Indi>()));
        ArrayList<SocietyScreen.Box> bb = new ArrayList<SocietyScreen.Box>();
        bb.add(new SocietyScreen.Box(5,5,1));
        univers.add(new Addition.University(specs, bb, "First State University"));
        if (socialActivities.size() == 0) {
            for (int i0=1302; i0<1318; i0++) {
                socialActivities.add(new SocialAct(i0));
            }
            socialActivities.add(new SocialAct(602));
        }
        Json json = new Json();
        Gdx.app.log("local storage path", Gdx.files.getLocalStoragePath());
        try {
            FileHandle file = Gdx.files.local("E/json/room"+homex+"-"+homey+"-"+homez+".txt");
            jsonStr = file.readString();
            boxTva = json.fromJson(Box.class, jsonStr.replace("SocietyTest", "SocietyScreen"));
            Gdx.app.error("JSON", jsonStr);
        }
        catch (Exception ee) {
            boxTva = new Box(homex, homey, homez);
        }
        boxes.clear();
        boxes.add(0, boxTva);
        society.setBoxes(boxes);
        society.getBoxes().get(cb).setIndisTest(boxTva.getIndisTest());
        society.getBoxes().get(cb).setObjectsTest(boxTva.getObjectsTest());
        yyyy = boxTva.yyyy; mm = boxTva.mm; dd = boxTva.dd; hh = boxTva.hh; min = boxTva.min;
        objectsTest = boxTva.getObjectsTest();
        indisTest = society.getBoxes().get(cb).getIndisTest();
        indis.clear();
        objects.clear();
        stage.clear();
        createInterfacePeopleBOX();
        uiDelay = System.currentTimeMillis() + 3000;
        labelCenter.setText(homex+"/"+homey+"/"+homez);

        background = new Custom(new Texture("background8.png"), -150, -270);
        walls = new Custom(new Texture("city/office1/office1r4.png"), 150, 770);
        if (homex == 10 && homey == 10) {
            Pixmap pixmap200 = new Pixmap(Gdx.files.internal("city/office1/office1-9.png"));
            if (homez == 9)
                pixmap200 = new Pixmap(Gdx.files.internal("city/office1/office1-9.png"));
            if (homez == 8)
                pixmap200 = new Pixmap(Gdx.files.internal("city/office1/office1-8.png"));
            if (homez == 7)
                pixmap200 = new Pixmap(Gdx.files.internal("city/office1/office1-7.png"));
            if (homez == 6)
                pixmap200 = new Pixmap(Gdx.files.internal("city/office1/office1-6.png"));
            if (homez == 5)
                pixmap200 = new Pixmap(Gdx.files.internal("city/office1/office1-5.png"));
            if (homez == 4)
                pixmap200 = new Pixmap(Gdx.files.internal("city/office1/office1-4.png"));
            if (homez == 3)
                pixmap200 = new Pixmap(Gdx.files.internal("city/office1/office1-3.png"));
            if (homez == 2)
                pixmap200 = new Pixmap(Gdx.files.internal("city/office1/office1-2.png"));
            Pixmap pixmap100 = new Pixmap(pixmap200.getWidth()*2, pixmap200.getHeight()*2, pixmap200.getFormat());
            pixmap100.drawPixmap(pixmap200,
                    0, 0, pixmap200.getWidth(), pixmap200.getHeight(),
                    0, 0, pixmap100.getWidth(), pixmap100.getHeight()
            );
            Texture texture = new Texture(pixmap100);
            background = new Custom(texture, -400,
                    -1800+((9-homez)*180));
            pixmap200 = new Pixmap(Gdx.files.internal("city/office1/office1r4.png"));
            pixmap100 = new Pixmap(pixmap200.getWidth()*2, pixmap200.getHeight()*2, pixmap200.getFormat());
            pixmap100.drawPixmap(pixmap200,
                    0, 0, pixmap200.getWidth(), pixmap200.getHeight(),
                    0, 0, pixmap100.getWidth(), pixmap100.getHeight()
            );
            texture = new Texture(pixmap100);
            walls = new Custom(texture, 414, 84);
            pixmap200.dispose();
            pixmap100.dispose();
        }
        else {
            String[] arr = new String[] {"background11.png", "background15.png", "background14.png", "background9.png"};
            background = new Custom(new Texture(arr[rnd(4)-1]), -150, -270);
        }
        stage.addActor(background);
        for (int i=0; i<boxes.size(); i++) {
            for (int j=0; j<indisTest.size(); j++) {
                loadIndi(i, j);
            }
        }
        for (int i=0; i<boxes.size(); i++) {
            for (int j=0; j<objectsTest.size(); j++) {
                loadObject(i, j);
            }
        }
        while (true) {
            int i = 0;
            int i2 = i;
            if (i2 < indis.size()) {
                IndiActor myActor = indis.get(i2);
                myActor.setTouchable(Touchable.enabled);
                myActor.setZIndex(100);
                stage.addActor(myActor);
                i = i2 + 1;
            }
            pcounter.stop();
            return;
        }
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        if (x>650.0) {
            if (objectObs != -1) {
                objects.get(objectObs).ox -= (int) (deltaX / 3);
                objects.get(objectObs).oy += (int) (deltaY / 3);
                society.getObjectsTest(cb).get(objectObs).x -= (int) (deltaX / 3);
                society.getObjectsTest(cb).get(objectObs).setFixity(society.getObjectsTest(cb).get(objectObs).getFixity() + (int) (deltaY / 3));
            }
            ((OrthographicCamera) stage.getCamera()).translate(-deltaX / 3, deltaY / 3);
            ((OrthographicCamera) girlStage.getCamera()).translate(deltaX / 3*2, -deltaY / 3*2);
        }
        return true;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        ((OrthographicCamera) stage.getCamera()).zoom -= (distance - initialDistance) * 0.00001f;
        ((OrthographicCamera) girlStage.getCamera()).zoom -= (distance - initialDistance) * 0.00001f;
        return true;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        cardStage.getViewport().update(width, height, true);
        girlStage.getViewport().update(width, height, true);
    }

    public void pause() {
    }

    public void resume() {
    }


    public class CoreFunc {
        public int load;
    }

    public class RoomController extends Actor {
        int count = 0;
        long delay = 0, delayArg = 0, delayNeeds = 0;
        boolean argue = false, systemChecked = false;
        String[] politics = {"Коммунисты", "Либералы", "Социалисты", "Демократы"};
        String[] economics = {"Макроэкономика", "Малый бизнес", "Коропорации"};
        String[] health = {"Витамины", "Прививки", "Хирургия", "ЗОЖ"};
        String[] crimes = {"", "", ""};
        String[] fashion = {"Очки", "Украшения", "Шляпы"};
        String[] arts = {"Арт-хаус", "Классицизм", "Рококо"};
        String[] food = {"Блины", "Жареное мясо", "Здоровая пища"};
        String[] science = {"Астрономия", "Биоинженерия", "Атомная физика"};
        String[] sport = {"Футбол", "Баскетбол", "Хоккей"};
        String[] travel = {"Горные походы", "Пляжи"};
        float actorX, actorY;
        Texture texture = new Texture(Gdx.files.internal("ui/null.png"));

        public void checkActivities() {
            for (int i = 0; i<socialActivities.size(); i++) {
                if (socialActivities.get(i).getMembers().size() > socialActivities.get(count).getMembers().size() && socialActivities.get(i).getMembers().size() > 2) {
                    count = i;
                }
                else {socialActivities.get(i).setPopular(false);}
            }
            if (!argue && socialActivities.size() != 0 && count != 0 && delay < System.currentTimeMillis()) {
                if(socialActivities.get(count).getMembers().size() != 2) {socialActivities.get(count).setPopular(true);
                    Gdx.app.log("the most popular action", String.valueOf(socialActivities.get(count).getAction()));
                    delayArg = System.currentTimeMillis() + 40000;
                    popAct = socialActivities.get(count).getAction();
                    giveItName(count);
                    checkTheMost(count);
                    iconEvent.setVisible(true);
                    panelEvent.setVisible(true);
                    labelEvent.setVisible(true);
                    labelEvent.setText("Спор на тему: " + popAct);
                    if (popAct == 1302) {
                        labelEvent.setText("Спор на тему: " + langString.get("politics"));
                        iconEvent.setTexture(new Texture("icons2/politics.png"));
                    }
                    else if (popAct == 1303) {
                        labelEvent.setText("Спор на тему: " + langString.get("economics"));
                        iconEvent.setTexture(new Texture("icons2/economics.png"));
                    }
                    else if (popAct == 1304) {
                        labelEvent.setText("Спор на тему: " + langString.get("health"));
                        iconEvent.setTexture(new Texture("icons2/health.png"));
                    }
                    else if (popAct == 1305) {
                        labelEvent.setText("Спор на тему: " + langString.get("crimes"));
                        iconEvent.setTexture(new Texture("icons2/crime.png"));
                    }
                    else if (popAct == 1306) {
                        labelEvent.setText("Спор на тему: " + langString.get("funInterest"));
                        iconEvent.setTexture(new Texture("icons2/fashion.png"));
                    }
                    else if (popAct == 1307) {
                        labelEvent.setText("Спор на тему: " + langString.get("culture"));
                        iconEvent.setTexture(new Texture("icons2/culture.png"));
                    }
                    else if (popAct == 1308) {
                        labelEvent.setText("Спор на тему: " + langString.get("food"));
                        iconEvent.setTexture(new Texture("icons2/food.png"));
                    }
                    else if (popAct == 1309) {
                        labelEvent.setText("Спор на тему: " + "Наука");
                        iconEvent.setTexture(new Texture("icons2/science.png"));
                    }
                    else if (popAct == 1310) {
                        labelEvent.setText("Спор на тему: " + langString.get("sport"));
                        iconEvent.setTexture(new Texture("icons2/sport.png"));
                    }
                    else if (popAct == 1311) {
                        labelEvent.setText("Спор на тему: " + langString.get("travel"));
                        iconEvent.setTexture(new Texture("icons2/travel.png"));
                    }
                    else if (popAct == 1312) {
                        labelEvent.setText("Спор на тему: " + langString.get("technics"));
                        iconEvent.setTexture(new Texture("icons2/technics.png"));
                    }
                    else if (popAct == 1313) {
                        labelEvent.setText("Спор на тему: " + langString.get("work"));
                        iconEvent.setTexture(new Texture("icons2/work.png"));
                    }
                    else if (popAct == 1314) {
                        labelEvent.setText("Спор на тему: " + langString.get("animals"));
                        iconEvent.setTexture(new Texture("icons2/animals.png"));
                    }
                    else if (popAct == 1315) {
                        labelEvent.setText("Спор на тему: " + langString.get("books"));
                        iconEvent.setTexture(new Texture("icons2/books.png"));
                    }
                    else if (popAct == 1316) {
                        labelEvent.setText("Спор на тему: " + langString.get("films"));
                        iconEvent.setTexture(new Texture("icons2/films.png"));
                    }
                    else if (popAct == 1317) {
                        labelEvent.setText("Спор на тему: " + langString.get("music"));
                        iconEvent.setTexture(new Texture("icons2/music.png"));
                    }
                    else if (popAct == 602) {
                        labelEvent.setText("Время танцев");
                        iconEvent.setTexture(new Texture("icons2/disco.png"));
                    }
                    else {
                        iconEvent.setTexture(new Texture("ui/null.png"));
                        labelEvent.setText("Популярное действие: " + String.valueOf(popAct));
                    }
                    argue = true;
                    for (int q=0; q<socialActivities.get(count).getMembers().size(); q++) {
                        indis.get(Integer.valueOf(socialActivities.get(count).getMembers().get(q))-1).delay = System.currentTimeMillis() + 40000;
                    }
                }
                else {}}
            else {}
        }

        public void checkTruth() {
            for (int i = 0; i<socialActivities.size(); i++) {
                list4check = new ArrayList<String>(new LinkedHashSet<String>(socialActivities.get(i).getMembers()));
                socialActivities.get(i).setMembers(new ArrayList<String>(list4check));
                socialActivities.get(i).setAmount(socialActivities.get(i).getMembers().size());
                list4check.clear();
            }
            //Gdx.app.log("RoomController", "all members were checked");
        }

        public void checkTheMost(int max) {
            for (int q=0; q<socialActivities.get(0).getMembers().size(); q++) {
                if (society.getIndi(cb, Integer.valueOf(socialActivities.get(max).getMembers().get(q))).getInterests().get(0).getPolitics() > socialActivities.get(max).indiNumEtt) {
                    socialActivities.get(max).indiNumEtt = Integer.valueOf(socialActivities.get(max).getMembers().get(q));
                }
                else if (society.getIndi(cb, Integer.valueOf(socialActivities.get(max).getMembers().get(q))).getInterests().get(0).getPolitics() > socialActivities.get(max).indiNumTva) {
                    socialActivities.get(max).indiNumTva = Integer.valueOf(socialActivities.get(max).getMembers().get(q));
                }
            }
            society.getInits().add(new Init(socialActivities.get(max).indiNumEtt, "За " + socialActivities.get(max).getParties().get(0)+"!", max, 1));
            society.getInits().add(new Init(socialActivities.get(max).indiNumTva, "Долой " + socialActivities.get(max).getParties().get(1)+"!", max, 1));
        }

        public void giveItName(int i) {
            if (socialActivities.get(i).getAction() == 1302) {
                for (int ii = 0; ii<2; ii++) {
                    socialActivities.get(i).getParties().add(politics[rnd(politics.length-1)]);
                }
            }
            if (socialActivities.get(i).getAction() == 1303) {
                socialActivities.get(i).getParties().add(economics[rnd(economics.length-1)]);
                socialActivities.get(i).getParties().add(economics[rnd(economics.length-1)]);
            }
            if (socialActivities.get(i).getAction() == 1304) {
                socialActivities.get(i).getParties().add(health[rnd(health.length-1)]);
                socialActivities.get(i).getParties().add(health[rnd(health.length-1)]);
            }
            if (socialActivities.get(i).getAction() == 1305) {
                socialActivities.get(i).getParties().add(crimes[rnd(crimes.length-1)]);
                socialActivities.get(i).getParties().add(crimes[rnd(crimes.length-1)]);
            }
            if (socialActivities.get(i).getAction() == 1306) {
                socialActivities.get(i).getParties().add(fashion[rnd(fashion.length-1)]);
                socialActivities.get(i).getParties().add(fashion[rnd(fashion.length-1)]);
            }
            if (socialActivities.get(i).getAction() == 1307) {
                socialActivities.get(i).getParties().add(arts[rnd(arts.length-1)]);
                socialActivities.get(i).getParties().add(arts[rnd(arts.length-1)]);
            }
            if (socialActivities.get(i).getAction() == 1308) {
                socialActivities.get(i).getParties().add(food[rnd(food.length-1)]);
                socialActivities.get(i).getParties().add(food[rnd(food.length-1)]);
            }
            if (socialActivities.get(i).getAction() == 1309) {
                socialActivities.get(i).getParties().add(science[rnd(science.length-1)]);
                socialActivities.get(i).getParties().add(science[rnd(science.length-1)]);
            }
            if (socialActivities.get(i).getAction() == 1310) {
                socialActivities.get(i).getParties().add(sport[rnd(sport.length-1)]);
                socialActivities.get(i).getParties().add(sport[rnd(sport.length-1)]);
            }
            if (socialActivities.get(i).getAction() == 1311) {
                socialActivities.get(i).getParties().add(travel[rnd(travel.length-1)]);
                socialActivities.get(i).getParties().add(travel[rnd(travel.length-1)]);
            }
            if (socialActivities.get(i).getAction() == 1312) {
                socialActivities.get(i).getParties().add(travel[rnd(travel.length-1)]);
                socialActivities.get(i).getParties().add(travel[rnd(travel.length-1)]);
            }
            if (socialActivities.get(i).getAction() == 1313) {
                socialActivities.get(i).getParties().add(travel[rnd(travel.length-1)]);
                socialActivities.get(i).getParties().add(travel[rnd(travel.length-1)]);
            }
            if (socialActivities.get(i).getAction() == 1314) {
                socialActivities.get(i).getParties().add(travel[rnd(travel.length-1)]);
                socialActivities.get(i).getParties().add(travel[rnd(travel.length-1)]);
            }
            if (socialActivities.get(i).getAction() == 1315) {
                socialActivities.get(i).getParties().add(travel[rnd(travel.length-1)]);
                socialActivities.get(i).getParties().add(travel[rnd(travel.length-1)]);
            }
            if (socialActivities.get(i).getAction() == 1316) {
                socialActivities.get(i).getParties().add(travel[rnd(travel.length-1)]);
                socialActivities.get(i).getParties().add(travel[rnd(travel.length-1)]);
            }
            if (socialActivities.get(i).getAction() == 1317) {
                socialActivities.get(i).getParties().add(travel[rnd(travel.length-1)]);
                socialActivities.get(i).getParties().add(travel[rnd(travel.length-1)]);
            }
            if (socialActivities.get(i).getAction() == 1318) {
                socialActivities.get(i).getParties().add(travel[rnd(travel.length-1)]);
                socialActivities.get(i).getParties().add(travel[rnd(travel.length-1)]);
            }
            if (socialActivities.get(i).getAction() == 1319) {
                socialActivities.get(i).getParties().add(travel[rnd(travel.length-1)]);
                socialActivities.get(i).getParties().add(travel[rnd(travel.length-1)]);
            }
            if (socialActivities.get(i).getAction() == 602) {
                socialActivities.get(i).getParties().add("");
                socialActivities.get(i).getParties().add("");
            }
        }

        public void draw(Batch batch, float alpha)
        {batch.draw(texture,actorX,actorY);}

        public void act(float delta){
            if (FPS == 1) {
                if (System.currentTimeMillis() > delayArg && delayArg !=0) {
                    for (int q=0; q<indis.size(); q++) {
                        if (indis.get(q).action == popAct) {
                            indis.get(q).action = 0;
                        }
                    }
                    popAct = 0;
                    //Gdx.app.log(String.valueOf(popAct), "");
                    for (int q = 0; q<socialActivities.size(); q++) {
                        socialActivities.get(q).setPopular(false);
                        socialActivities.get(q).getMembers().clear();
                        socialActivities.get(q).setAmount(0);
                    }
                    delayArg = 0;
                    delay = System.currentTimeMillis() + 10000;
                    argue = false;
                    iconEvent.setVisible(false);
                    panelEvent.setVisible(false);
                    labelEvent.setVisible(false);
                }
                checkTruth();
                checkActivities();
                if (delayNeeds < System.currentTimeMillis()) {
                    delayNeeds = System.currentTimeMillis() + 10000;
                    for (int q = 0; q<indisTest.size(); q++) {
                        if (society.getIndi(cb, q).alive) {
                            try {
                                society.getIndi(cb, q).getNeeds().get(0).setEducation(society.getIndi(cb, q).getNeeds().get(0).getEducation() - 2);
                                society.getIndi(cb, q).getNeeds().get(0).setEnergy(society.getIndi(cb, q).getNeeds().get(0).getEnergy() - 2);
                                society.getIndi(cb, q).getNeeds().get(0).setAesthetics(society.getIndi(cb, q).getNeeds().get(0).getAesthetics() - 1);
                                society.getIndi(cb, q).getNeeds().get(0).setFun(society.getIndi(cb, q).getNeeds().get(0).getFun() - 2);
                                society.getIndi(cb, q).getNeeds().get(0).setHunger(society.getIndi(cb, q).getNeeds().get(0).getHunger() - 3);
                                society.getIndi(cb, q).getNeeds().get(0).setSocial(society.getIndi(cb, q).getNeeds().get(0).getSocial() - 2);
                                society.getIndi(cb, q).getNeeds().get(0).setSuccess(society.getIndi(cb, q).getNeeds().get(0).getSuccess() - 2);
                                society.getIndi(cb, q).getNeeds().get(0).setBl(society.getIndi(cb, q).getNeeds().get(0).getBl() - 2);
                                society.getIndi(cb, q).getNeeds().get(0).setLove(society.getIndi(cb, q).getNeeds().get(0).getLove() - 1);
                                society.getIndi(cb, q).getNeeds().get(0).setHygiene(society.getIndi(cb, q).getNeeds().get(0).getHygiene() - 2);
                                society.getIndi(cb, q).getNeeds().get(0).setShopping(society.getIndi(cb, q).getNeeds().get(0).getShopping() - 1);
                                for (int qq = 0; qq < society.getIndi(cb, q).getScenarios().size(); qq++) {
                                    if (society.getIndi(cb, q).getScenarios().get(qq).getCode() == 1701) {
                                        society.getIndi(cb, q).getNeeds().get(0).setHunger(society.getIndi(cb, q).getNeeds().get(0).getHunger() - 3);
                                    }
                                }
                                for (int qq = 0; qq < society.getBoxes().get(cb).getObjectsTest().size(); qq++) {
                                    if (society.getBoxes().get(cb).getObjectsTest().get(qq).type == -3) {
                                        society.getIndi(cb, q).getNeeds().get(0).setProtection(society.getIndi(cb, q).getNeeds().get(0).getProtection() - 4);
                                        break;
                                    }
                                    if (society.getBoxes().get(cb).getObjectsTest().get(qq).type == -4) {
                                        society.getIndi(cb, q).getNeeds().get(0).setProtection(society.getIndi(cb, q).getNeeds().get(0).getProtection() - 30);
                                        break;
                                    }
                                }
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }
                            if (society.getIndi(cb, q).getNeeds().get(0).getHunger() < 0) {
                                society.getIndi(cb, q).alive = false;
                                indis.get(q).actions.clear();
                                indis.get(q).updateAppearance(1, HoldObject.None);
                                indis.get(q).theme = new Texture(Gdx.files.internal("ui/null.png"));
                                indis.get(q).targetY = 3000;
                                indis.get(q).status = "is dead from hunger since: " + dd + "/" + mm + "/" + yyyy + ", " + hh + "." + min;
                            }
                            String[] cloudThemes = new String[] {"icons2/aesthetics.png", "icons2/animals.png","icons2/aesthetics.png","icons2/bladder.png","icons2/books.png","icons2/caution.png","icons2/crime.png","icons2/culture.png","icons2/disco.png","icons2/economics.png","icons2/education.png","icons2/env.png","icons2/fashion.png","icons2/films.png","icons2/food.png","icons2/fun.png","icons2/health.png","icons2/history.png","icons2/hobbies.png","icons2/hourglass.png","icons2/hygiene.png","icons2/hunger.png","icons2/imagination.png","icons2/immunity.png","icons2/influence.png","icons2/insight.png","icons2/interests.png","icons2/logic.png","icons2/love.png","icons2/main.png","icons2/memory.png","icons2/music.png","icons2/mystic.png","icons2/politics.png","icons2/power.png","icons2/quickness.png","icons2/safety.png","icons2/science.png","icons2/shopping.png","icons2/social.png","icons2/speech.png","icons2/sport.png","icons2/stamina.png","icons2/star.png","icons2/success.png","icons2/technics.png","icons2/travel.png","icons2/witch.png","icons2/work.png"};
                            for (int i=0; i<indis.size();i++) {
                                getIndiActor(i).theme.dispose();
                                getIndiActor(i).theme = new Texture(Gdx.files.internal(cloudThemes[rnd(cloudThemes.length)-1]));
                            }
                        }
                    }
                }
                HashMap<Integer, Integer> indisList = new HashMap<>();
                for (Indi indi : indisTest) {
                    indisList.put(indi.globalNum, indi.myNum);
                }
                if (min == 55 && !systemChecked) {
                    systemChecked = true;
                    for (int i=0; i<society.getSocialSystems().size(); i++) {
                        for (int j=0; j<3; j++) { //по количеству уровней в системе
                            for (int k=0; k<society.getSocialSystems().get(i).screws.get(j).size(); k++) {
                                for (int l=0; l < society.getSocialSystems().get(i).screws.get(j).get(k).hours.size(); l++) {
                                    if (society.getSocialSystems().get(i).screws.get(j).get(k).hours.get(l).get(0) == dw && //совпадение дня недели
                                            society.getSocialSystems().get(i).screws.get(j).get(k).hours.get(l).get(1) == hh+1) { //работа начнется через 5 минут
                                        int num = society.getIndiNumByGlobal(society.getSocialSystems().get(i).screws.get(j).get(k).id);
                                        List<Integer> etiquette = society.getSocialSystems().get(i).screws.get(j).get(k).etiquette;
                                        if (etiquette.size() == 0) {
                                            indis.get(num).markerString = "it is my work hour now\nbut there is no etiquettes for me";
                                            indis.get(num).marker = 1;
                                        }
                                        else {
                                            int type = -1;
                                            for (int m=0; m<society.getSocialSystems().get(i).screws.get(j).get(k).receive.size(); m++) {
                                                int amount = society.getSocialSystems().get(i).screws.get(j).get(k).receive.get(m).amount;
                                                switch (society.getSocialSystems().get(i).screws.get(j).get(k).receive.get(m).type) {
                                                    case ObjectNeeded:
                                                        type = amount;
                                                        break;
                                                    case Money:
                                                        society.getIndi(cb, num).wealth += amount;
                                                        society.getIndi(cb, society.getIndiNumByGlobal(society.getSocialSystems().get(i).screws.get(0).get(0).id)).wealth -= amount;
                                                        break;
                                                    case AgeMoreThan:
                                                        if (society.getIndi(cb, num).age < amount) {
                                                            indis.get(num).markerString = "i'm too young for this\n"+"AgeMoreThan:"+type;
                                                            indis.get(num).marker = 1;
                                                            return;
                                                        }
                                                        break;
                                                    case AgeLessThan:
                                                        if (society.getIndi(cb, num).age > amount) {
                                                            indis.get(num).markerString = "i'm too old for this\n"+"AgeLessThan:"+type;
                                                            indis.get(num).marker = 1;
                                                            return;
                                                        }
                                                        break;
                                                    default:
                                                        break;
                                                }
                                            }
                                            int n = -2;
                                            if (type != -1)
                                                n = reserve(num, type);
                                            if (n == -2)
                                                indis.get(num).actions.add(0, new Action(etiquette.get(rnd(etiquette.size())-1), (int)indis.get(num).actorX + rnd(100)-50, (int)indis.get(num).actorY + rnd(100)-50, homez));
                                            else if (n != -1)
                                                indis.get(num).actions.add(0, new Action(etiquette.get(rnd(etiquette.size())-1), objects.get(n).ox + offset(true, objectsTest.get(n).type, objectsTest.get(n).appearance), objects.get(n).oy + offset(false, objectsTest.get(n).type, objectsTest.get(n).appearance), homez));
                                            else {
                                                indis.get(num).markerString = "there is no free object needed for my role\n"+"ObjectNeeded:"+type;
                                                indis.get(num).marker = 1;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (min == 54 || min == 56)
                    systemChecked = false;
                if (System.currentTimeMillis() > uiDelay) {
                    labelCenter.setText("");
                }
                //checkTheMost();
                //Gdx.app.log("RoomController", "checking everything");
            }
        }

        public RoomController(float x1, float y1){
            actorX = x1;
            actorY = y1;
        }
    }

    public class CustomC extends Actor {
        //https://www.gamefromscratch.com/post/2013/11/27/LibGDX-Tutorial-9-Scene2D-Part-1.aspx
        float actorX, actorY;
        Texture texture = new Texture(Gdx.files.internal("pboxlogo1.png"));
        public boolean started = false;

        public CustomC(float x1, float y1){
            actorX = x1;
            actorY = y1;
        }

        public void draw(Batch batch, float alpha){
            batch.draw(texture,actorX,actorY);
        }

        public void act(float delta){}
    }

    public class ScenButton extends Actor {
        String str;
        int idScen;
        Texture t1 = new Texture(Gdx.files.internal("ui/panel8.png"));
        Texture t2 = new Texture(Gdx.files.internal("uiTva/scenario.png"));

        public ScenButton(String str, Texture t) {
            this.str = str;
            this.t1 = t;
            setBounds(getX(), getY(), 400, 50);
        }
        public ScenButton(String str) {
            this.str = str;
            idScen = 0;
            setBounds(getX(), getY(), 400, 50);
        }

        public void draw(Batch batch, float alpha){
            batch.draw(t1, getX(), getY());
            batch.draw(t2, getX(), getY()-8);
            fontFran.setColor(Color.WHITE);
            fontFran.draw(batch, str, getX()+64, getY()+46);
        }

        public void act(float delta){}
    }

    public static class Custom extends Actor {
        //https://www.gamefromscratch.com/post/2013/11/27/LibGDX-Tutorial-9-Scene2D-Part-1.aspx
        float actorX, actorY;
        Texture texture = new Texture(Gdx.files.internal("pboxlogo1.png"));
        public boolean started = false;

        public Custom(Texture tex, float x1, float y1){
            texture = tex;
            actorX = x1;
            actorY = y1;
            setBounds(getX(), getY(), texture.getWidth(), texture.getHeight());
        }

        public Texture getTexture() {
            return texture;
        }

        public void setTexture(Texture texture) {
            this.texture = texture;
        }

        public void draw(Batch batch, float alpha){
            batch.draw(texture,actorX,actorY);
        }

        public void act(float delta){}
    }

    public class CustomScrew extends Actor {
        //https://www.gamefromscratch.com/post/2013/11/27/LibGDX-Tutorial-9-Scene2D-Part-1.aspx
        Texture texture = new Texture(Gdx.files.internal("uiTva/inits.png"));
        public boolean started = false;
        int numero = 0;
        Screw screw;

        public CustomScrew(int i, int j, int k, int num){
            setBounds(getX(), getY(), 170, 64);
            screw = society.getSocialSystems().get(i).screws.get(j).get(k);
            numero = num;
        }

        public Texture getTexture() {
            return texture;
        }

        public void setTexture(Texture texture) {
            this.texture = texture;
        }

        public void draw(Batch batch, float alpha){
            batch.draw(new TextureRegion(indis.get(society.getIndiGlobal(numero).myNum).texture, 0, 0, indis.get(society.getIndiGlobal(numero).myNum).texture.getWidth(), 64), getX(), getY());
            fontFran.draw(batch, df.format(screw.efficiency*100)+"%\nH:" + screw.hours.size(), getX()+64, getY()+64);
        }

        public void act(float delta){
        }
    }

    public static class SystemCard extends Actor {
        //https://www.gamefromscratch.com/post/2013/11/27/LibGDX-Tutorial-9-Scene2D-Part-1.aspx
        Texture texIndi, texTransmit, texReceive;
        public boolean started = false;

        public SystemCard(Texture tex){
            texIndi = tex;
            setBounds(getX(), getY(), 128, 152);
        }

        public void draw(Batch batch, float alpha){
            batch.draw(texIndi, getX(), getY());
        }

        public void act(float delta){}
    }

    public class CustomIcon extends Actor {
        //https://www.gamefromscratch.com/post/2013/11/27/LibGDX-Tutorial-9-Scene2D-Part-1.aspx
        float actorX, actorY;
        Texture texture;
        String str = "";
        public boolean started = false;

        public CustomIcon(Texture tex){
            texture = tex;
            setBounds(getX(), getY(), texture.getWidth(), texture.getHeight());
        }

        public CustomIcon(Texture tex, String s) {
            texture = tex;
            str = s;
            setBounds(getX(), getY(), texture.getWidth(), texture.getHeight());
        }

        public void draw(Batch batch, float alpha){
            //batch.draw(buttonBackground, getX(), getY());
            batch.draw(texture, getX(), getY());
            fontOldGirl.draw(batch, str, getX(), getY()+16);
        }

        public void act(float delta){        }
    }

    public static class FurnCard extends Actor {
        public int price, type, appear, prodStart, prodEnd, targetX=0, targetY=0;
        public String name, desc, texturePath;
        public Texture texture;

        public FurnCard() {}

        public FurnCard(int price, int type, int appear, int prodStart, int prodEnd, String name, String desc, String texturePath) {
            this.price = price;
            this.type = type;
            this.appear = appear;
            this.prodStart = prodStart;
            this.prodEnd = prodEnd;
            this.name = name;
            this.desc = desc;
            this.texturePath = texturePath;
            texture = new Texture(texturePath);
        }

        public FurnCard(int price, int type, int appear, int prodStart, int prodEnd, int targetX, int targetY, String name, String desc, String texturePath, Texture texture) {
            this.price = price;
            this.type = type;
            this.appear = appear;
            this.prodStart = prodStart;
            this.prodEnd = prodEnd;
            this.targetX = targetX;
            this.targetY = targetY;
            this.name = name;
            this.desc = desc;
            this.texturePath = texturePath;
            this.texture = texture;
        }

        public void act(float delta) {}

        public void draw(Batch batch, float delta) {
            try {
                batch.draw(texture, 50 - texture.getWidth() * 96 / texture.getHeight() / 2, getY(), texture.getWidth() * 96 / texture.getHeight(), 96);
                fontFranTva.draw(batch, name, getX() + 100, getY() + 80);
                fontOswaldFurnPrice.draw(batch, price+" ", getX(), getY() + 30);
            }
            catch (NullPointerException e) {
                texture = new Texture(texturePath);
            }
        }
    }

    public class RelCard extends Actor {
        public TextureRegion texture;
        String name, surname;
        Relation.Levels level;

        public RelCard(Texture texture, String name, String surname, Relation.Levels level, final int num) {
            this.texture = new TextureRegion(texture, 0, 0, 48, 48);
            this.name = name;
            this.surname = surname;
            this.level = level;
            setBounds(getX(), getY(), 600, 96);
            addListener(new InputListener() {
                public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                    tableRelationsChoose.setVisible(true);
                    ff = num;
                    return true;
                }
            });
        }

        public void act(float delta) {}

        public void draw(Batch batch, float delta) {
            batch.draw(texture, getX()+15, getY()+15, 64, 64);
            fontFranTva.draw(batch, name + " " + surname, getX()+100, getY()+80);
            fontFranTva.draw(batch, level.name(), getX()+100, getY()+40);
        }
    }

    public class IndiCard extends Actor {
        public TextureRegion texture;
        String name, surname;

        public IndiCard(Texture texture, String name, String surname, final int num) {
            this.texture = new TextureRegion(texture, 0, 0, 48, 48);
            this.name = name;
            this.surname = surname;
            setBounds(getX(), getY(), 150, 48);
            addListener(new InputListener() {
                public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                    indiObs = num;
                    cardEtt.texture = new Texture("ui/indiPreview.png");
                    outerTableTva.setVisible(true);
                    indis.get(num).holdCard();
                    updateRelations(num);
                    return true;
                }
            });
        }

        public void act(float delta) {}

        public void draw(Batch batch, float delta) {
            batch.draw(texture, getX()+15, getY()+4, 32, 32);
            fontOldGirl.draw(batch, name/*.substring(0,1) + "." */ + " " + surname, getX()+36, getY()+18);
        }
    }

    void hideTables(boolean labelHintHide) {
        removeExtraActors();
        panelLeft.setTexture(new Texture(Gdx.files.internal(groupObs > -1 ? "ui/socialsystembck.png" : "ui/panel7.png")));
        cardTva.setType(0);
        tableBroadcast.setVisible(false);
        nameField.setVisible(false);
        surnameField.setVisible(false);
        //btnReset.setVisible(false);
        applyName.setVisible(false);
        outerTableNeeds.setVisible(false);
        tableInterests.setVisible(false);
        tableTalents.setVisible(false);
        btnRandomScenario.setVisible(false);
        labelRandomScenario.setVisible(false);
        cardEtt.update(); cardTva.update();
        outerTableFurn.setVisible(false);
        outerTableRelations.setVisible(false);
        tableRelationsChoose.setVisible(false);
        outerTableIndis.setVisible(false);
        groupBuild.setVisible(false);
        tableRoom.setVisible(false);
        panelRight.setVisible(false);
        outerSystemTable.setVisible(false);
        btnTrash.setVisible(!labelHintHide);
        if (labelHintHide) {
            labelHint.setVisible(false);
            outerTable.setSize(580, 0);
        }
        else {
            labelHint.setVisible(true);
            outerTable.setSize(580, 750*Float.parseFloat(String.valueOf(screenCoef * screenCoef)));
        }
    }

    void createInterfacePeopleBOX() {
        FileHandle fileTva = Gdx.files.internal("json/furn.txt");
        jsonStr = fileTva.readString();
        furn = json.fromJson(ArrayList.class, jsonStr);
        furnTable = new Table().left();
        for (int q=0; q<furn.size(); q++) {
            Json json = new Json();
            FileHandle file = Gdx.files.local("E/json/DEFAULTCITY/priceprops.txt");
            if (!file.exists()) {
                file= Gdx.files.internal("json/DEFAULTCITY/priceprops.txt");
            }
            jsonStr = file.readString();
            PriceProps prices = json.fromJson(PriceProps.class, jsonStr);
            furn.get(q).price = (int) (furn.get(q).price * prices.furnMarkup /* * Math.pow(0.9, yyyy - furn.get(q).prodStart)*/);
            furnTable.add(furn.get(q)).left();
            furnTable.row();
        }
        int e = 0;
        fileTva = Gdx.files.local("E/json/roompatterns/pattern"+e+".txt");
        for (int i = 0; i < furn.size(); i++) {
            if (i == 0)
                furn.get(i).setBounds(furn.get(i).getX(), furn.get(i).getY(), 550, 96);
            else
                furn.get(i).setBounds(furn.get(i).getX(), furn.get(i).getY(), 120, 96);
            final int ii = i;
            furn.get(i).addListener(new InputListener() {
                public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                    if (furn.get(ii).type != 9000 && indiObs != -1) {
                        if (society.getIndi(cb, indiObs).getWealth() > furn.get(ii).price) {
                            createObject(furn.get(ii).type, furn.get(ii).appear, 0, 0, 500, 500);
                            society.getIndi(cb, indiObs).setWealth(society.getIndi(cb, indiObs).getWealth() - furn.get(ii).price);
                        }
                        else {
                            uiNo.play();
                        }
                    }
                    else if (furn.get(ii).type != 9000 && indiObs == -1) {
                        uiNo.play();
                    }
                    else {
                        coreFunc.load = 1;
                        Gdx.app.log("MODEFROMLOAD", String.valueOf(coreFunc.load));
                        for (int i = 1; i < indis.size() + 1; i++) {
                            IndiActor ii = indis.get(i - 1);
                            ii.setStarted(false);
                            indis.get(i - 1).setStarted(false);
                        }
                        indis.clear();
                        objects.clear();
                        stage.clear();
                        objectsTest.clear();
                        indisTest.clear();
                        Json json = new Json();
                        Gdx.app.log("local storage path", Gdx.files.getLocalStoragePath());
                        FileHandle file = Gdx.files.local("E/json/roompatterns/pattern" + furn.get(ii).appear + ".txt");
                        Box boxTva;
                        if (file.exists()) {
                            jsonStr = file.readString();
                            boxTva = json.fromJson(Box.class, jsonStr.replace("SocietyTest", "SocietyScreen"));
                            uiDelay = System.currentTimeMillis() + 3000;
                            labelCenter.setText(langString.get("saveLoaded"));
                        } else {
                            file = Gdx.files.internal("json/room" + homex + "-" + homey + "-" + homez + ".txt");
                            if (file.exists()) {
                                jsonStr = file.readString();
                                boxTva = json.fromJson(Box.class, jsonStr.replace("SocietyTest", "SocietyScreen"));
                                uiDelay = System.currentTimeMillis() + 3000;
                                labelCenter.setText(langString.get("saveLoaded"));
                            } else {
                                boxTva = society.getBoxes().get(cb);
                                uiNo.play();
                            }
                        }
                        boxes.set(cb, boxTva);
                        society.getBoxes().set(cb, boxTva);
                        society.getBoxes().get(cb).setIndisTest(boxTva.getIndisTest());
                        society.getBoxes().get(cb).setObjectsTest(boxTva.getObjectsTest());
                        yyyy = boxTva.yyyy;
                        mm = boxTva.mm;
                        dd = boxTva.dd;
                        hh = boxTva.hh;
                        min = boxTva.min;
                        objectsTest = boxTva.getObjectsTest();
                        indisTest = society.getBoxes().get(cb).getIndisTest();
                        SocietyScreen.Custom background = new Custom(new Texture("background8.png"), -150, -270);
                        if (homex == 10 && homey == 10) {
                            Pixmap pixmap200 = new Pixmap(Gdx.files.internal("city/office1/office1-9.png"));
                            if (homez == 9)
                                pixmap200 = new Pixmap(Gdx.files.internal("city/office1/office1-9.png"));
                            if (homez == 8)
                                pixmap200 = new Pixmap(Gdx.files.internal("city/office1/office1-8.png"));
                            if (homez == 7)
                                pixmap200 = new Pixmap(Gdx.files.internal("city/office1/office1-7.png"));
                            if (homez == 6)
                                pixmap200 = new Pixmap(Gdx.files.internal("city/office1/office1-6.png"));
                            if (homez == 5)
                                pixmap200 = new Pixmap(Gdx.files.internal("city/office1/office1-5.png"));
                            if (homez == 4)
                                pixmap200 = new Pixmap(Gdx.files.internal("city/office1/office1-4.png"));
                            if (homez == 3)
                                pixmap200 = new Pixmap(Gdx.files.internal("city/office1/office1-3.png"));
                            if (homez == 2)
                                pixmap200 = new Pixmap(Gdx.files.internal("city/office1/office1-2.png"));
                            Pixmap pixmap100 = new Pixmap(pixmap200.getWidth() * 2, pixmap200.getHeight() * 2, pixmap200.getFormat());
                            pixmap100.drawPixmap(pixmap200,
                                    0, 0, pixmap200.getWidth(), pixmap200.getHeight(),
                                    0, 0, pixmap100.getWidth(), pixmap100.getHeight()
                            );
                            Texture texture = new Texture(pixmap100);
                            pixmap200.dispose();
                            pixmap100.dispose();
                            background = new Custom(texture, -400 + ((9 - homez) * 130),
                                    -1800);
                        } else {
                            String[] arr = new String[]{"background11.png", "background15.png", "background14.png", "background9.png"};
                            background = new Custom(new Texture(arr[rnd(4) - 1]), -150, -270);
                        }
                        stage.addActor(background);
                        for (int i = 0; i < boxes.size(); i++) {
                            for (int j = 0; j < objectsTest.size(); j++) {
                                loadObject(i, j);
                            }
                        }
                        for (int i = 0; i < boxes.size(); i++) {
                            for (int j = 0; j < indisTest.size(); j++) {
                                loadIndi(i, j);
                            }
                        }
                        indiObs = -1;
                        coreFunc.load = 0;
                        tableMove.setZIndex(200);
                        buttonAdd.setZIndex(200);
                    }
                    return true;
                }
            });
        }
        multiplexer = new InputMultiplexer();
        GestureDetector gd = new GestureDetector(this);
        multiplexer.addProcessor(gd);
        multiplexer.addProcessor(cardStage);
        multiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(multiplexer);
        fileTva = Gdx.files.local("json/room"+homex+"-"+homey+"-"+homez+".txt");
        fileTva.writeString("My god, it's full of stars", false);
        screenCoef = Double.parseDouble(String.valueOf(Gdx.graphics.getHeight())) / 1080;
        Gdx.app.log("screenCoef", String.valueOf(screenCoef));
        stage.addActor(controller);
        TextField.TextFieldStyle txtstyle = new TextField.TextFieldStyle();
        txtstyle.font = fontOswald;
        txtstyle.fontColor = Color.WHITE;
        nameField = new TextField("X", txtstyle);
        surnameField = new TextField("Y", txtstyle);
        nameField.setBounds(100, Gdx.graphics.getHeight() - 270, 200, 80);
        surnameField.setBounds(300, Gdx.graphics.getHeight() - 270, 200, 80);
        applyName = new TextButton("OK", tbs);
        applyName.setBounds(500, Gdx.graphics.getHeight() - 250, 50, 50);
        applyName.setVisible(false);
        applyName.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                society.getIndi(cb, indiObs).setName(nameField.getText());
                society.getIndi(cb, indiObs).setSurname(surnameField.getText());
                indis.get(indiObs).setName(nameField.getText());
                indis.get(indiObs).setSurname(surnameField.getText());
                cardTva.setType(18); nameField.setVisible(true); surnameField.setVisible(true); applyName.setVisible(true);
                outerTableNeeds.setVisible(false); tableInterests.setVisible(false); tableTalents.setVisible(false); cardEtt.update(); cardTva.update();
                cardEtt.setName(indis.get(indiObs).name + " " + indis.get(indiObs).surname);
            }
        });
        cardStage.addActor(nameField);
        cardStage.addActor(surnameField);
        cardStage.addActor(applyName);
        nameField.setVisible(false);
        surnameField.setVisible(false);
        iconTable = new Table();
        iconTableSystem = new Table();
        tableRelations = new Table().left();
        tableIndis = new Table().left();
        tableNeeds = new Table();
        systemTable = new Table().left();

        //tableNeeds.setBounds(90,130, 580, 420);
        /*if (Gdx.graphics.getWidth() < 2001) {
            tableNeeds.setSize(580, 750);
            //tableNeeds.setBounds(90,130, 580, 750);
        }
        if (Gdx.graphics.getWidth() < 1281) {
            tableNeeds.setSize(580, 420);
            //tableNeeds.setBounds(90,130, 580, 420);
        }*/
        TextField.TextFieldStyle style = new TextField.TextFieldStyle();
        style.font = new BitmapFont();
        style.fontColor = Color.CHARTREUSE;
        //tableNeeds.add(nameField).width(200);
        //tableNeeds.row();
        SocietyScreen.CustomIcon iconAest = new SocietyScreen.CustomIcon(new Texture("icons2/aesthetics.png"), langString.get("aesthetics"));
        tableNeeds.add(iconAest);
        labelAesthetics = new Label("105", new Label.LabelStyle(fontOswaldTre, Color.CYAN));
        labelAesthetics.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 26; neededY = (int) labelAesthetics.getY();
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
            }
        });
        tableNeeds.add(labelAesthetics);
        SocietyScreen.CustomIcon iconBladder = new SocietyScreen.CustomIcon(new Texture("icons2/bladder.png"), langString.get("bladder"));
        iconBladder.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                labelAesthetics.setText("80085");
                Gdx.app.log("click on", "iconBladder");
                labelCenter.setText("clicking on iconBladder");
                uiDelay = System.currentTimeMillis() + 3000;
            }
        });
        tableNeeds.add(iconBladder);
        labelBladder = new Label("105", new Label.LabelStyle(fontOswaldTre, Color.CYAN));
        labelBladder.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 27; neededY = (int) labelBladder.getY();
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
            }
        });
        tableNeeds.add(labelBladder);
        SocietyScreen.CustomIcon iconEducation = new SocietyScreen.CustomIcon(new Texture("icons2/education.png"), langString.get("education"));
        tableNeeds.add(iconEducation);
        labelEducation = new Label("105", new Label.LabelStyle(fontOswaldTre, Color.CYAN));
        labelEducation.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 28; neededY = (int) labelEducation.getY();
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
            }
        });
        tableNeeds.add(labelEducation);
        tableNeeds.row();
        SocietyScreen.CustomIcon iconEnergy = new SocietyScreen.CustomIcon(new Texture("icons2/energy.png"), langString.get("energy"));
        tableNeeds.add(iconEnergy);
        labelEnergy = new Label("105", new Label.LabelStyle(fontOswaldTre, Color.CYAN));
        labelEnergy.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 29; neededY = (int) labelEnergy.getY();
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
            }
        });
        tableNeeds.add(labelEnergy);
        SocietyScreen.CustomIcon iconEnv = new SocietyScreen.CustomIcon(new Texture("icons2/env.png"), langString.get("environment"));
        //tableNeeds.add(iconEnv);
        labelEnv = new Label("105", new Label.LabelStyle(fontOswaldTre, Color.CYAN));
        labelEnv.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 30; neededY = (int) labelEnv.getY();
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
            }
        });
        //tableNeeds.add(labelEnv);
        SocietyScreen.CustomIcon iconFun = new SocietyScreen.CustomIcon(new Texture("icons2/fun.png"), langString.get("fun"));
        tableNeeds.add(iconFun);
        labelFun = new Label("105", new Label.LabelStyle(fontOswaldTre, Color.CYAN));
        labelFun.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 31; neededY = (int) labelFun.getY();
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
            }
        });
        tableNeeds.add(labelFun);
        SocietyScreen.CustomIcon iconHunger = new SocietyScreen.CustomIcon(new Texture("icons2/hunger.png"), langString.get("hunger"));
        tableNeeds.add(iconHunger);
        labelHunger = new Label("105", new Label.LabelStyle(fontOswaldTre, Color.CYAN));
        labelHunger.setBounds(labelHunger.getX(), labelHunger.getY(), 40, 40);
        labelHunger.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 32; neededY = (int) labelHunger.getY();
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
            }
        });
        tableNeeds.add(labelHunger);
        tableNeeds.row();



        SocietyScreen.CustomIcon iconHygiene = new SocietyScreen.CustomIcon(new Texture("icons2/hygiene.png"), langString.get("hygiene"));
        tableNeeds.add(iconHygiene);
        labelHygiene = new Label("105", new Label.LabelStyle(fontOswaldTre, Color.CYAN));
        labelHygiene.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 33; neededY = (int) labelHygiene.getY();
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
            }
        });
        tableNeeds.add(labelHygiene);
        SocietyScreen.CustomIcon iconLove = new SocietyScreen.CustomIcon(new Texture("icons2/love.png"), langString.get("love"));
        tableNeeds.add(iconLove);
        labelLove = new Label("105", new Label.LabelStyle(fontOswaldTre, Color.CYAN));
        labelLove.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 34; neededY = (int) labelLove.getY()+64;
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
            }
        });
        tableNeeds.add(labelLove);

        SocietyScreen.CustomIcon iconPower = new SocietyScreen.CustomIcon(new Texture("icons2/power.png"), langString.get("power"));
        //tableNeeds.add(iconPower);
        labelPower = new Label("105", new Label.LabelStyle(fontOswaldTre, Color.CYAN));
        labelPower.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 35; neededY = (int) labelPower.getY()+64;
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
            }
        });
        //tableNeeds.add(labelPower);
        SocietyScreen.CustomIcon iconSafety = new SocietyScreen.CustomIcon(new Texture("icons2/safety.png"), langString.get("protection"));
        tableNeeds.add(iconSafety);
        labelSafety = new Label("105", new Label.LabelStyle(fontOswaldTre, Color.CYAN));
        labelSafety.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 36; neededY = (int) labelSafety.getY()+64;
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
            }
        });
        tableNeeds.add(labelSafety);
        tableNeeds.row();
        SocietyScreen.CustomIcon iconShopping = new SocietyScreen.CustomIcon(new Texture("icons2/shopping.png"), langString.get("shopping"));
        tableNeeds.add(iconShopping);
        labelShopping = new Label("105", new Label.LabelStyle(fontOswaldTre, Color.CYAN));
        labelShopping.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 37; neededY = (int) labelShopping.getY()+64;
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
            }
        });
        tableNeeds.add(labelShopping);
        //tableNeeds.row();
        SocietyScreen.CustomIcon iconSocial = new SocietyScreen.CustomIcon(new Texture("icons2/social.png"), langString.get("social"));
        tableNeeds.add(iconSocial);
        labelSocial = new Label("105", new Label.LabelStyle(fontOswaldTre, Color.CYAN));
        labelSocial.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 38; neededY = (int) labelSocial.getY()+124;
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
            }
        });
        tableNeeds.add(labelSocial);
        SocietyScreen.CustomIcon iconSuccess = new SocietyScreen.CustomIcon(new Texture("icons2/success.png"), langString.get("success"));
        tableNeeds.add(iconSuccess);
        labelSuccess = new Label("105", new Label.LabelStyle(fontOswaldTre, Color.CYAN));
        labelSuccess.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 39; neededY = (int) labelSuccess.getY()+124;
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
            }
        });
        tableNeeds.add(labelSuccess);

        tableNeeds.row();

        //scrollPaneNeeds = new ScrollPane(tableNeeds);
        //outerTableNeeds = new Table();
        //outerTableNeeds.add(scrollPaneNeeds).expand().left().top();

        tableInterests = new Table().top().left();
        SocietyScreen.CustomIcon iconPolitics = new SocietyScreen.CustomIcon(new Texture("icons2/politics.png"), langString.get("politics"));
        tableInterests.add(iconPolitics);
        labelPolitics = new Label("105", new Label.LabelStyle(fontOswaldTre, needsOnly.contains(GAMEMODE) ? Color.SCARLET : needsOnly.contains(GAMEMODE) ? Color.SCARLET : Color.CYAN));
        labelPolitics.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                if (new ArrayList<Integer>(Arrays.asList(0,1,2)).contains(GAMEMODE))
                    cardType = 1;
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
            }
        });
        tableInterests.add(labelPolitics);
        SocietyScreen.CustomIcon iconEconomics = new SocietyScreen.CustomIcon(new Texture("icons2/economics.png"), langString.get("economics"));
        tableInterests.add(iconEconomics);
        labelEconomics = new Label("105", new Label.LabelStyle(fontOswaldTre, needsOnly.contains(GAMEMODE) ? Color.SCARLET : Color.CYAN));
        labelEconomics.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                if (new ArrayList<Integer>(Arrays.asList(0,1,2)).contains(GAMEMODE))
                    cardType = 2;
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
            }
        });
        tableInterests.add(labelEconomics);
        SocietyScreen.CustomIcon iconHealth = new SocietyScreen.CustomIcon(new Texture("icons2/health.png"), langString.get("health"));
        tableInterests.add(iconHealth);
        labelHealth = new Label("105", new Label.LabelStyle(fontOswaldTre, needsOnly.contains(GAMEMODE) ? Color.SCARLET : Color.CYAN));
        labelHealth.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                if (new ArrayList<Integer>(Arrays.asList(0,1,2)).contains(GAMEMODE))
                    cardType = 3;
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
            }
        });
        tableInterests.add(labelHealth);
        tableInterests.row();
        SocietyScreen.CustomIcon iconCrimes = new SocietyScreen.CustomIcon(new Texture("icons2/crime.png"), langString.get("crimes"));
        tableInterests.add(iconCrimes);
        labelCrimes = new Label("105", new Label.LabelStyle(fontOswaldTre, needsOnly.contains(GAMEMODE) ? Color.SCARLET : Color.CYAN));
        labelCrimes.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                if (new ArrayList<Integer>(Arrays.asList(0,1,2)).contains(GAMEMODE))
                    cardType = 4;
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
            }
        });
        tableInterests.add(labelCrimes);
        SocietyScreen.CustomIcon iconScience = new SocietyScreen.CustomIcon(new Texture("icons2/science.png"), langString.get("science"));
        tableInterests.add(iconScience);
        labelScience = new Label("105", new Label.LabelStyle(fontOswaldTre, needsOnly.contains(GAMEMODE) ? Color.SCARLET : Color.CYAN));
        labelScience.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                if (new ArrayList<Integer>(Arrays.asList(0,1,2)).contains(GAMEMODE))
                    cardType = 5;
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
            }
        });
        tableInterests.add(labelScience);
        SocietyScreen.CustomIcon iconCulture = new SocietyScreen.CustomIcon(new Texture("icons2/culture.png"), langString.get("culture"));
        tableInterests.add(iconCulture);
        labelCulture = new Label("105", new Label.LabelStyle(fontOswaldTre, needsOnly.contains(GAMEMODE) ? Color.SCARLET : Color.CYAN));
        labelCulture.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                if (new ArrayList<Integer>(Arrays.asList(0,1,2)).contains(GAMEMODE))
                    cardType = 6;
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
            }
        });
        tableInterests.add(labelCulture);
        tableInterests.row();
        SocietyScreen.CustomIcon iconFood = new SocietyScreen.CustomIcon(new Texture("icons2/food.png"), langString.get("food"));
        tableInterests.add(iconFood);
        labelFood = new Label("105", new Label.LabelStyle(fontOswaldTre, needsOnly.contains(GAMEMODE) ? Color.SCARLET : Color.CYAN));
        labelFood.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                if (new ArrayList<Integer>(Arrays.asList(0,1,2)).contains(GAMEMODE))
                    cardType = 7;
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
            }
        });
        tableInterests.add(labelFood);
        SocietyScreen.CustomIcon iconFashion = new SocietyScreen.CustomIcon(new Texture("icons2/fashion.png"), langString.get("fashion"));
        tableInterests.add(iconFashion);
        labelFashion = new Label("105", new Label.LabelStyle(fontOswaldTre, needsOnly.contains(GAMEMODE) ? Color.SCARLET : Color.CYAN));
        labelFashion.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                if (new ArrayList<Integer>(Arrays.asList(0,1,2)).contains(GAMEMODE))
                    cardType = 8;
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
            }
        });
        tableInterests.add(labelFashion);
        SocietyScreen.CustomIcon iconSport = new SocietyScreen.CustomIcon(new Texture("icons2/sport.png"), langString.get("sport"));
        tableInterests.add(iconSport);
        labelSport = new Label("105", new Label.LabelStyle(fontOswaldTre, needsOnly.contains(GAMEMODE) ? Color.SCARLET : Color.CYAN));
        labelSport.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                if (new ArrayList<Integer>(Arrays.asList(0,1,2)).contains(GAMEMODE))
                    cardType = 9;
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
            }
        });
        tableInterests.add(labelSport);
        tableInterests.row();
        SocietyScreen.CustomIcon iconTravel = new SocietyScreen.CustomIcon(new Texture("icons2/travel.png"), langString.get("travel"));
        tableInterests.add(iconTravel);
        labelTravel = new Label("105", new Label.LabelStyle(fontOswaldTre, needsOnly.contains(GAMEMODE) ? Color.SCARLET : Color.CYAN));
        labelTravel.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                if (new ArrayList<Integer>(Arrays.asList(0,1,2)).contains(GAMEMODE))
                    cardType = 10;
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
            }
        });
        tableInterests.add(labelTravel);
        SocietyScreen.CustomIcon iconTechnics = new SocietyScreen.CustomIcon(new Texture("icons2/technics.png"), langString.get("technics"));
        tableInterests.add(iconTechnics);
        labelTechnics = new Label("105", new Label.LabelStyle(fontOswaldTre, needsOnly.contains(GAMEMODE) ? Color.SCARLET : Color.CYAN));
        labelTechnics.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                if (new ArrayList<Integer>(Arrays.asList(0,1,2)).contains(GAMEMODE))
                    cardType = 11;
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
            }
        });
        tableInterests.add(labelTechnics);
        SocietyScreen.CustomIcon iconJob = new SocietyScreen.CustomIcon(new Texture("icons2/work.png"), langString.get("work"));
        tableInterests.add(iconJob);
        labelJob = new Label("105", new Label.LabelStyle(fontOswaldTre, needsOnly.contains(GAMEMODE) ? Color.SCARLET : Color.CYAN));
        labelJob.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                if (new ArrayList<Integer>(Arrays.asList(0,1,2)).contains(GAMEMODE))
                    cardType = 12;
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
            }
        });
        tableInterests.add(labelJob);
        tableInterests.row();
        SocietyScreen.CustomIcon iconAnimals = new SocietyScreen.CustomIcon(new Texture("icons2/animals.png"), langString.get("animals"));
        tableInterests.add(iconAnimals);
        labelAnimals = new Label("105", new Label.LabelStyle(fontOswaldTre, needsOnly.contains(GAMEMODE) ? Color.SCARLET : Color.CYAN));
        labelAnimals.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                if (new ArrayList<Integer>(Arrays.asList(0,1,2)).contains(GAMEMODE))
                    cardType = 13;
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
            }
        });
        tableInterests.add(labelAnimals);
        SocietyScreen.CustomIcon iconBooks = new SocietyScreen.CustomIcon(new Texture("icons2/books.png"), langString.get("books"));
        tableInterests.add(iconBooks);
        labelBooks = new Label("105", new Label.LabelStyle(fontOswaldTre, needsOnly.contains(GAMEMODE) ? Color.SCARLET : Color.CYAN));
        labelBooks.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                if (new ArrayList<Integer>(Arrays.asList(0,1,2)).contains(GAMEMODE))
                    cardType = 14;
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
            }
        });
        tableInterests.add(labelBooks);
        SocietyScreen.CustomIcon iconFilms = new SocietyScreen.CustomIcon(new Texture("icons2/films.png"), langString.get("films"));
        tableInterests.add(iconFilms);
        labelFilms = new Label("105", new Label.LabelStyle(fontOswaldTre, needsOnly.contains(GAMEMODE) ? Color.SCARLET : Color.CYAN));
        labelFilms.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                if (new ArrayList<Integer>(Arrays.asList(0,1,2)).contains(GAMEMODE))
                    cardType = 15;
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
            }
        });
        tableInterests.add(labelFilms);
        tableInterests.row();
        SocietyScreen.CustomIcon iconMusic = new SocietyScreen.CustomIcon(new Texture("icons2/music.png"), langString.get("music"));
        tableInterests.add(iconMusic);
        labelMusic = new Label("105", new Label.LabelStyle(fontOswaldTre, needsOnly.contains(GAMEMODE) ? Color.SCARLET : Color.CYAN));
        labelMusic.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                if (new ArrayList<Integer>(Arrays.asList(0,1,2)).contains(GAMEMODE))
                    cardType = 16;
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
            }
        });
        tableInterests.add(labelMusic);
        SocietyScreen.CustomIcon iconHistory = new SocietyScreen.CustomIcon(new Texture("icons2/hourglass.png"), langString.get("history"));
        tableInterests.add(iconHistory);
        labelHistory = new Label("105", new Label.LabelStyle(fontOswaldTre, needsOnly.contains(GAMEMODE) ? Color.SCARLET : Color.CYAN));
        labelHistory.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                if (new ArrayList<Integer>(Arrays.asList(0,1,2)).contains(GAMEMODE))
                    cardType = 61;
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
            }
        });
        tableInterests.add(labelHistory);
        SocietyScreen.CustomIcon iconMystic = new SocietyScreen.CustomIcon(new Texture("icons2/mystic.png"), langString.get("mystic"));
        tableInterests.add(iconMystic);
        labelMystic = new Label("105", new Label.LabelStyle(fontOswaldTre, needsOnly.contains(GAMEMODE) ? Color.SCARLET : Color.CYAN));
        labelMystic.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                if (new ArrayList<Integer>(Arrays.asList(0,1,2)).contains(GAMEMODE))
                    cardType = 62;
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
            }
        });
        tableInterests.add(labelMystic);
        tableInterests.row();
        tableInterests.setVisible(false);

        tableTalents = new Table().top().left();
        SocietyScreen.CustomIcon iconCaution = new SocietyScreen.CustomIcon(new Texture("icons2/caution.png"), langString.get("caution"));
        tableTalents.add(iconCaution);
        labelCaution = new Label("105", new Label.LabelStyle(fontOswaldTre, Color.CYAN));
        labelCaution.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                if (new ArrayList<Integer>(Arrays.asList(0,1)).contains(GAMEMODE))
                    cardType = 44;
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
            }
        });
        tableTalents.add(labelCaution);
        SocietyScreen.CustomIcon iconImagination = new SocietyScreen.CustomIcon(new Texture("icons2/imagination.png"), langString.get("imagination"));
        tableTalents.add(iconImagination);
        labelImagination = new Label("105", new Label.LabelStyle(fontOswaldTre, Color.CYAN));
        labelImagination.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                if (new ArrayList<Integer>(Arrays.asList(0,1)).contains(GAMEMODE))
                    cardType = 45;
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
            }
        });
        tableTalents.add(labelImagination);
        tableTalents.row();
        SocietyScreen.CustomIcon iconImmunity = new SocietyScreen.CustomIcon(new Texture("icons2/immunity.png"), langString.get("immunity"));
        tableTalents.add(iconImmunity);
        labelImmunity = new Label("105", new Label.LabelStyle(fontOswaldTre, Color.CYAN));
        labelImmunity.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                if (new ArrayList<Integer>(Arrays.asList(0,1)).contains(GAMEMODE))
                    cardType = 46;
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
            }
        });
        tableTalents.add(labelImmunity);
        SocietyScreen.CustomIcon iconInfluence = new SocietyScreen.CustomIcon(new Texture("icons2/influence.png"), langString.get("influence"));
        tableTalents.add(iconInfluence);
        labelInfluence = new Label("105", new Label.LabelStyle(fontOswaldTre, Color.CYAN));
        labelInfluence.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                if (new ArrayList<Integer>(Arrays.asList(0,1)).contains(GAMEMODE))
                    cardType = 47;
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
            }
        });
        tableTalents.add(labelInfluence);
        tableTalents.row();
        SocietyScreen.CustomIcon iconInsight = new SocietyScreen.CustomIcon(new Texture("icons2/insight.png"), langString.get("insight"));
        tableTalents.add(iconInsight);
        labelInsight = new Label("105", new Label.LabelStyle(fontOswaldTre, Color.CYAN));
        labelInsight.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                if (new ArrayList<Integer>(Arrays.asList(0,1)).contains(GAMEMODE))
                    cardType = 48;
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
            }
        });
        tableTalents.add(labelInsight);
        SocietyScreen.CustomIcon iconLogic = new SocietyScreen.CustomIcon(new Texture("icons2/logic.png"), langString.get("logic"));
        tableTalents.add(iconLogic);
        labelLogic = new Label("105", new Label.LabelStyle(fontOswaldTre, Color.CYAN));
        labelLogic.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                if (new ArrayList<Integer>(Arrays.asList(0,1)).contains(GAMEMODE))
                    cardType = 49;
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
            }
        });
        tableTalents.add(labelLogic);
        tableTalents.row();
        SocietyScreen.CustomIcon iconMemory = new SocietyScreen.CustomIcon(new Texture("icons2/memory.png"), langString.get("memory"));
        tableTalents.add(iconMemory);
        labelMemory = new Label("105", new Label.LabelStyle(fontOswaldTre, Color.CYAN));
        labelMemory.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                if (new ArrayList<Integer>(Arrays.asList(0,1)).contains(GAMEMODE))
                    cardType = 50;
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
            }
        });
        tableTalents.add(labelMemory);
        SocietyScreen.CustomIcon iconQuickness = new SocietyScreen.CustomIcon(new Texture("icons2/quickness.png"), langString.get("quickness"));
        tableTalents.add(iconQuickness);
        labelQuickness = new Label("105", new Label.LabelStyle(fontOswaldTre, Color.CYAN));
        labelQuickness.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                if (new ArrayList<Integer>(Arrays.asList(0,1)).contains(GAMEMODE))
                    cardType = 51;
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
            }
        });
        tableTalents.add(labelQuickness);
        tableTalents.row();
        SocietyScreen.CustomIcon iconSpeech = new SocietyScreen.CustomIcon(new Texture("icons2/speech.png"), langString.get("speech"));
        tableTalents.add(iconSpeech);
        labelSpeech = new Label("105", new Label.LabelStyle(fontOswaldTre, Color.CYAN));
        labelSpeech.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                if (new ArrayList<Integer>(Arrays.asList(0,1)).contains(GAMEMODE))
                    cardType = 52;
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
            }
        });
        tableTalents.add(labelSpeech);
        SocietyScreen.CustomIcon iconStamina = new SocietyScreen.CustomIcon(new Texture("icons2/stamina.png"), langString.get("stamina"));
        tableTalents.add(iconStamina);
        labelStamina = new Label("105", new Label.LabelStyle(fontOswaldTre, Color.CYAN));
        labelStamina.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                if (new ArrayList<Integer>(Arrays.asList(0,1)).contains(GAMEMODE))
                    cardType = 53;
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
            }
        });
        tableTalents.add(labelStamina);
        tableTalents.row();
        tableTalents.setVisible(false);

        tableRoom = new Table().top().left();
        SocietyScreen.CustomIcon iconWater = new SocietyScreen.CustomIcon(new Texture("icons2/water.png"), "On");
        tableRoom.add(iconWater);
        labelWater = new Label("On", new Label.LabelStyle(fontOswaldTre, Color.CYAN));
        labelWater.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 55; neededY = (int) labelWater.getY();
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
                //return true;
            }
        });
        tableRoom.add(labelWater);
        SocietyScreen.CustomIcon iconElectricity = new SocietyScreen.CustomIcon(new Texture("icons2/electricity.png"), "On");
        tableRoom.add(iconElectricity);
        labelElectricity = new Label("On", new Label.LabelStyle(fontOswaldTre, Color.CYAN));
        labelElectricity.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 56; neededY = (int) labelElectricity.getY();
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
                //return true;
            }
        });
        tableRoom.add(labelElectricity);
        SocietyScreen.CustomIcon iconTelephone = new SocietyScreen.CustomIcon(new Texture("icons2/telephone.png"), "On");
        //tableRoom.add(iconTelephone);
        labelCellular = new Label("On", new Label.LabelStyle(fontOswaldTre, Color.CYAN));
        labelCellular.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 57; neededY = (int) labelCellular.getY();
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
                //return true;
            }
        });
        //tableRoom.add(labelCellular);
        SocietyScreen.CustomIcon iconWifi = new SocietyScreen.CustomIcon(new Texture("icons2/wifi.png"), "On");
        tableRoom.add(iconWifi);
        labelWifi = new Label("On", new Label.LabelStyle(fontOswaldTre, Color.CYAN));
        labelWifi.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 58; neededY = (int) labelWifi.getY();
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
                //return true;
            }
        });
        tableRoom.add(labelWifi);
        SocietyScreen.CustomIcon iconTemp = new SocietyScreen.CustomIcon(new Texture("icons2/thermometer.png"), "");
        tableRoom.add(iconTemp);
        labelTemperature = new Label("0", new Label.LabelStyle(fontOswaldTre, Color.CYAN));
        labelTemperature.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 54; neededY = (int) labelTemperature.getY()+30;
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
            }
        });
        tableRoom.add(labelTemperature);
        labelCelsius = new Label("°C", new Label.LabelStyle(fontOswaldTre, Color.CYAN));
        labelCelsius.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 59; neededY = (int) labelTemperature.getY()+30;
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
            }
        });
        tableRoom.add(labelCelsius);
        tableRoom.row();
        tableRoom.setVisible(false);

        Label labelZeroLevel = new Label("0", new Label.LabelStyle(fontOswaldTre, Color.CYAN));
        systemTable.add(labelZeroLevel);
        systemTable.row();


        scrollPaneSystem = new ScrollPane(iconTableSystem);
        outerSystemTable = new Table().left();
        outerSystemTable.add(scrollPaneSystem).expand().left().top();
        cardStage.addActor(outerSystemTable);
        outerSystemTable.setVisible(false);


        //furnTable.setVisible(false);
        //cardStage.addActor(furnTable);

        panelEvent = new SocietyScreen.Custom(new Texture("ui/panel6.png"), Gdx.graphics.getWidth()-550, 15);
        panelEvent.setZIndex(999);
        panelEvent.setVisible(false);
        iconEvent = new SocietyScreen.Custom(new Texture("icons2/aesthetics.png"), Gdx.graphics.getWidth()-540, 20);
        iconEvent.setZIndex(999);
        iconEvent.setVisible(false);
        labelEvent = new Label("", new Label.LabelStyle(fontFran, Color.WHITE));
        labelEvent.setPosition(Gdx.graphics.getWidth()-480, 70);
        labelEvent.setVisible(false);
        cardStage.addActor(panelEvent);
        cardStage.addActor(labelEvent);
        cardStage.addActor(iconEvent);
        cardStage.addActor(oldGirl);
        cardStage.addActor(arrowHint);
        cardStage.addActor(labelGirl);
        cardStage.addActor(labelAppuyez);
        //cardStage.addActor(girlYes);
        //cardStage.addActor(girlNo);

        //final SocietyTest.Indi deadIndi = new SocietyTest.Indi("", "", 1, 0, 0, -1, -1, -1, 0, 0, 0, indiObs+1, "");
        ArrayList<SocietyScreen.NeedsArray> needs = new ArrayList<SocietyScreen.NeedsArray>();
        needs.add(new SocietyScreen.NeedsArray(100,100,100,100,100,100,100,100,100,100,100,100,100,100));


        Skin skinTva = new Skin(Gdx.files.internal("ui/ui.json"));

        TextButton.TextButtonStyle tbsTva = new TextButton.TextButtonStyle();
        tbsTva.up = skin.getDrawable("button.9");
        tbsTva.down = skin.getDrawable("button.9");
        tbsTva.pressedOffsetX = 1;
        tbsTva.pressedOffsetY = -1;
        tbsTva.font = fontFranTva;
        tbsTva.fontColor = Color.BLACK;

        //tbsTre.overFontColor = Color.YELLOW;
        tbsFyra = new TextButton.TextButtonStyle();
        tbsFyra.up = skinTre.getDrawable("button.9");
        tbsFyra.down = skinTre.getDrawable("button.9");
        tbsFyra.pressedOffsetX = 1;
        tbsFyra.pressedOffsetY = -1;
        tbsFyra.font = fontFranTva;
        tbsFyra.fontColor = Color.BLACK;







        labelCenter = new Label("", new Label.LabelStyle(fontOswald, Color.DARK_GRAY));
        labelCenter.setPosition(660, 120);

        tableMove = new Table(skinTva);
        tableMove.setBounds(650, 10, Gdx.graphics.getWidth()-770, 100);
        tableRelationsChoose = new Table(skinTva);
        tableRelationsChoose.setBounds(670, 550, 40, 10);
        tableDebugActions = new Table(skinTva);
        tableDebugActions.setBounds(-500, 350, 40, 10);
        labelDebug = new Label("", new Label.LabelStyle(font, Color.WHITE));
        labelDebug.setPosition(0, 2000);
        labelHint = new Label("", new Label.LabelStyle(fontFranTre, Color.WHITE));
        labelHint.setPosition(700, Gdx.graphics.getHeight()-150);
        labelHint.setAlignment(Align.topLeft);
        labelHintTva = new Label(" ", new Label.LabelStyle(fontOswaldBlack, Color.CYAN));
        labelHintTva.setPosition(10, Gdx.graphics.getHeight()-135);
        cardStage.addActor(labelHintTva);
        labelHintFyra = new Label("", new Label.LabelStyle(fontOswald, new Color(0xff341cff)));
        labelHintFyra.setPosition(4, Gdx.graphics.getHeight()-155);
        cardStage.addActor(labelHintFyra);
        labelHintTre = new Label("", new Label.LabelStyle(fontOswaldBlack, Color.CYAN));
        labelHintTre.setPosition(400, Gdx.graphics.getHeight()-175);
        labelHintTre.setVisible(false);
        cardStage.addActor(labelHintTre);
        labelControl = new Label("Control Panel", new Label.LabelStyle(fontOswald, Color.WHITE));
        labelControl.setText(langString.get("controlPanel"));
        labelControl.setPosition(Gdx.graphics.getWidth()-635, Gdx.graphics.getHeight()-65);
        labelControl.setVisible(false);
        Slider slider = new Slider(0, 100, 1, false, skinTva);
        slider.setBounds(0,0, 200, 40);


        final Group groupGender = new Group();
        //final Table tableNeeds = new Table();


        SocietyScreen.CustomIcon btnMain = new SocietyScreen.CustomIcon(new Texture("uiTva/main.png"));
        btnMain.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (indiObs > -1) {
                    hideTables(false);
                    outerTable.setSize(580, 680*Float.parseFloat(String.valueOf(screenCoef * screenCoef)));
                    cardTva.setType(18); nameField.setVisible(true); surnameField.setVisible(true); applyName.setVisible(true);
                    outerTable.setVisible(true);
                    nameField.setText(indis.get(indiObs).name); surnameField.setText(indis.get(indiObs).surname);
                    btnReset.setVisible(true);
                    labelReset.setVisible(true);
                }
                else uiNo.play();
            }
        });
        SocietyScreen.CustomIcon btnMainSystem = new SocietyScreen.CustomIcon(new Texture("uiTva/main.png"));
        btnMainSystem.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (groupObs > -1) {
                    hideTables(false);
                    outerTable.setSize(580, 680*Float.parseFloat(String.valueOf(screenCoef * screenCoef)));
                    cardTva.setType(63);
                    outerTable.setVisible(true);
                }
                else uiNo.play();
            }
        });
        iconTable.add(btnMain);
        iconTable.row();
        iconTableSystem.add(btnMainSystem);
        iconTableSystem.row();
        labelReset = new Label("RESET ACTIONS", new Label.LabelStyle(fontOswald, Color.WHITE));
        labelReset.setPosition(170, Gdx.graphics.getHeight() - 340);
        labelRandomScenario = new Label(langString.get("newScenario"), new Label.LabelStyle(fontOswald, Color.WHITE));
        labelRandomScenario.setPosition(170, Gdx.graphics.getHeight() - 270);
        btnRandomScenario = new SocietyScreen.CustomIcon(new Texture("uiTva/reset.png"));
        btnRandomScenario.setBounds(100, Gdx.graphics.getHeight() - 275, 64, 64);
        btnRandomScenario.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                removeExtraActors();
                final ScenButton s = new ScenButton("Душа компании");
                final ScenButton ss = new ScenButton(langString.get("randomScenario"));
                final ScenButton sss = new ScenButton(langString.get("scenario1701"));
                final ScenButton ssss = new ScenButton(langString.get("scenario1401"));
                final ScenButton s1601 = new ScenButton(langString.get("scenario1601"));
                final ScenButton s1602 = new ScenButton(langString.get("scenario1602"));
                final ScenButton stv = new ScenButton("Add TV Indi system");
                final ScenButton sdoc = new ScenButton("Add Doctor system");
                scenButtons.add(s);
                s.setPosition(Gdx.graphics.getWidth() - 420, 170);
                cardStage.addActor(s);
                scenButtons.add(ss);
                ss.setPosition(Gdx.graphics.getWidth() - 420, 120);
                cardStage.addActor(ss);
                scenButtons.add(sss);
                sss.setPosition(Gdx.graphics.getWidth() - 420, 220);
                cardStage.addActor(sss);
                ssss.setPosition(Gdx.graphics.getWidth() - 420, 270);
                cardStage.addActor(ssss);
                s1601.setPosition(Gdx.graphics.getWidth() - 420, 320);
                cardStage.addActor(s1601);
                s1602.setPosition(Gdx.graphics.getWidth() - 420, 370);
                cardStage.addActor(s1602);
                stv.setPosition(Gdx.graphics.getWidth() - 420, 420);
                cardStage.addActor(stv);
                sdoc.setPosition(Gdx.graphics.getWidth() - 420, 470);
                cardStage.addActor(sdoc);
                s.addListener(new ClickListener() {
                    public void clicked(InputEvent event, float x, float y) {
                        society.getIndi(cb, indiObs).getScenarios().add(new Scenario(1301));
                        removeExtraActors();
                    }});
                ss.addListener(new ClickListener() {
                    public void clicked(InputEvent event, float x, float y) {
                        indis.get(indiObs).actions.add(0, new Action(1, 1050, 490, homez));
                        removeExtraActors();
                    }});
                sss.addListener(new ClickListener() {
                    public void clicked(InputEvent event, float x, float y) {
                        society.getIndi(cb, indiObs).getScenarios().add(new Scenario(1701));
                        removeExtraActors();
                    }});
                ssss.addListener(new ClickListener() {
                    public void clicked(InputEvent event, float x, float y) {
                        society.getIndi(cb, indiObs).getScenarios().add(new Scenario(1401));
                        removeExtraActors();
                    }});
                s1601.addListener(new ClickListener() {
                    public void clicked(InputEvent event, float x, float y) {
                        society.getIndi(cb, indiObs).getScenarios().add(new Scenario(1601));
                        removeExtraActors();
                    }});
                s1602.addListener(new ClickListener() {
                    public void clicked(InputEvent event, float x, float y) {
                        society.getIndi(cb, indiObs).getScenarios().add(new Scenario(1602));
                        removeExtraActors();
                    }});
                stv.addListener(new ClickListener() {
                    public void clicked(InputEvent event, float x, float y) {
                        LinkedList<ArrayList<Screw>> newSystem = new LinkedList<ArrayList<Screw>>();
                        ArrayList<Screw> stageScrew = new ArrayList<Screw>();
                        int id = society.getSocialSystems().size();
                        createRandomIndi("Producer", "TV", 1);
                        stageScrew.add(new Screw(num,
                                Arrays.asList(new Profit(Money, 30000)),
                                Arrays.asList(new Profit(Money, 5000)), Arrays.asList(0)));
                        newSystem.add((ArrayList<Screw>) stageScrew.clone());
                        stageScrew.clear();
                        createRandomIndi("Reporteur", "TV", 1);
                        stageScrew.add(new Screw(currentGlobalNum,
                                Arrays.asList(new Profit(Money, 1500)),
                                Arrays.asList(new Profit(Footage, 2), new Profit(ReportLead, 4)), Arrays.asList(0)));
                        createRandomIndi("Presentatrice", "TV", 0);
                        stageScrew.add(new Screw(currentGlobalNum,
                                Arrays.asList(new Profit(Money, 1800), new Profit(ObjectNeeded, 31), new Profit(ReportLead, 10)),
                                Arrays.asList(new Profit(ConsumerPercent, 5), new Profit(CameraEvent, 1)), Arrays.asList(4001),
                                Arrays.asList(Arrays.asList(0,19,2), Arrays.asList(1,19,2), Arrays.asList(2,19,2), Arrays.asList(3,19,2))));
                        createRandomIndi("Editeur", "TV", 0);
                        stageScrew.add(new Screw(currentGlobalNum,
                                Arrays.asList(new Profit(Money, 2000)),
                                Arrays.asList(new Profit(ReportLead, 15)), Arrays.asList(0)));
                        createRandomIndi("Cameraman", "TV", 1);
                        stageScrew.add(new Screw(currentGlobalNum,
                                Arrays.asList(new Profit(Money, 1000), new Profit(CameraEvent, 1)),
                                Arrays.asList(new Profit(Footage, 5)), Arrays.asList(4000),
                                Arrays.asList(Arrays.asList(0,18,3), Arrays.asList(1,18,3), Arrays.asList(2,18,3), Arrays.asList(3,18,3))));
                        createRandomIndi("Montageur", "TV", 0);
                        stageScrew.add(new Screw(currentGlobalNum,
                                Arrays.asList(new Profit(Money, 1500), new Profit(Footage, 5)),
                                Arrays.asList(new Profit(Montage, 1)), Arrays.asList(0)));
                        createRandomIndi("Pub agent", "TV", 0);
                        stageScrew.add(new Screw(currentGlobalNum,
                                Arrays.asList(new Profit(Money, 2500)),
                                Arrays.asList(new Profit(Ads, 5)), Arrays.asList(0)));
                        newSystem.add((ArrayList<Screw>) stageScrew.clone());
                        stageScrew.clear();
                        createRandomIndi("Technicien", "TV", 1);
                        stageScrew.add(new Screw(currentGlobalNum,
                                Arrays.asList(new Profit(Money, 1500), new Profit(Montage, 1), new Profit(Ads, 4)),
                                Arrays.asList(new Profit(Emission, 1)), Arrays.asList(0)));
                        newSystem.add((ArrayList<Screw>) stageScrew.clone());
                        stageScrew.clear();
                        newSystem.add((ArrayList<Screw>) stageScrew.clone());
                        stageScrew.clear();
                        stageScrew.add(new Screw(-1000,
                                Arrays.asList(new Profit(Emission, 1)),
                                Arrays.asList(new Profit(Money, 30000)), Arrays.asList(0)));
                        newSystem.add((ArrayList<Screw>) stageScrew.clone());
                        stageScrew.clear();
                        society.getSocialSystems().add(new SocialSystem(0, "Le journal de La Cinq", newSystem, SocialSystemType.Channel, society.getIndi(cb, num-6).getInterests().get(0), society.getIndi(cb, num-6).getTalents().get(0)));
                        checkEfficiency(id);
                        Gdx.app.error("social systems", society.getSocialSystems().toString());
                        removeExtraActors();
                    }});
                sdoc.addListener(new ClickListener() {
                    public void clicked(InputEvent event, float x, float y) {
                        LinkedList<ArrayList<Screw>> newSystem = new LinkedList<ArrayList<Screw>>();
                        ArrayList<Screw> stageScrew = new ArrayList<Screw>();
                        int id = society.getSocialSystems().size();
                        createRandomIndi(rnd(90000)+"", "Doctor", 1);
                        stageScrew.add(new Screw(num, Arrays.asList(new Profit(Money, 1500), new Profit(ObjectNeeded, 1500)),
                                Arrays.asList(new Profit(Money, 5000)), Arrays.asList(0)));
                        newSystem.add((ArrayList<Screw>) stageScrew.clone());
                        stageScrew.clear();
                        society.getSocialSystems().add(new SocialSystem(id, "Le journal de La Cinq", newSystem, SocialSystemType.Hospital, society.getIndi(cb, num-6).getInterests().get(0), society.getIndi(cb, num-6).getTalents().get(0)));
                        checkEfficiency(id);
                        Gdx.app.error("social systems", society.getSocialSystems().toString());
                        removeExtraActors();
                    }});
            }
        });
        btnReset = new SocietyScreen.CustomIcon(new Texture("uiTva/reset.png"));
        //btnReset = new SocietyTest.Custom(new Texture("uiTva/reset.png"), 520, Gdx.graphics.getHeight() - 75);
        //btnReset.setBounds(540, Gdx.graphics.getHeight() - 75, 64, 64);
        btnReset.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                indis.get(indiObs).delay = 0;
                indis.get(indiObs).status = "";
                indis.get(indiObs).actions.clear();
                indis.get(indiObs).actNeeds.put("economics", false);
                indis.get(indiObs).actNeeds.put("hunger", false);
                indis.get(indiObs).actNeeds.put("bladder", false);
                indis.get(indiObs).actNeeds.put("energy", false);
                indis.get(indiObs).actNeeds.put("hygiene", false);
                indis.get(indiObs).actNeeds.put("aesthetics", false);
                indis.get(indiObs).actNeeds.put("fun", false);
                indis.get(indiObs).actNeeds.put("education", false);
                indis.get(indiObs).actNeeds.put("social", false);
                indis.get(indiObs).actNeeds.put("working", false);
                indis.get(indiObs).actNeeds.put("music", false);
                indis.get(indiObs).actNeeds.put("technics", false);
                indis.get(indiObs).actNeeds.put("tvstation", false);
                indis.get(indiObs).actNeeds.put("books", false);
                indis.get(indiObs).actNeeds.put("film", false);
                indis.get(indiObs).actNeeds.put("shopping", false);
                indis.get(indiObs).actNeeds.put("love", false);
                indis.get(indiObs).setVisible(true);
                indis.get(indiObs).updateAppearance(1, HoldObject.None);
                for (int i = 0; i < objects.size(); i++) {
                    if (objects.get(i).occ == indiObs) {
                        objects.get(i).occ = -1;
                        objects.get(i).texture = whichObject(objectsTest.get(i).getType(), objectsTest.get(i).getAppearance());
                    }
                }
            }
        });
        cardStage.addActor(btnReset);
        //cardStage.addActor(labelReset);
        //btnReset.setVisible(false);
        cardStage.addActor(btnRandomScenario);
        cardStage.addActor(labelRandomScenario);
        btnRandomScenario.setVisible(false);
        labelRandomScenario.setVisible(false);
        labelReset.setVisible(false);
        iconTable.add(btnReset);
        iconTable.row();
        SocietyScreen.CustomIcon btnNeeds = new SocietyScreen.CustomIcon(new Texture("uiTva/needs.png"));
        btnNeeds.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (indiObs > -1) {
                    hideTables(true);
                    outerTableNeeds.setVisible(true);
                }
                else uiNo.play();
            }
        });
        iconTable.add(btnNeeds);
        iconTable.row();
        SocietyScreen.CustomIcon btnTalents = new SocietyScreen.CustomIcon(new Texture("uiTva/talents.png"));
        btnTalents.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (indiObs > -1) {
                    hideTables(true);
                    tableTalents.setVisible(true);
                }
                else uiNo.play();
            }
        });
        iconTable.add(btnTalents);
        iconTable.row();
        SocietyScreen.CustomIcon btnTalentsSystem = new SocietyScreen.CustomIcon(new Texture("uiTva/talents.png"));
        btnTalentsSystem.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (groupObs > -1) {
                    hideTables(true);
                    tableTalents.setVisible(true);
                }
                else uiNo.play();
            }
        });
        iconTableSystem.add(btnTalentsSystem);
        iconTableSystem.row();
        SocietyScreen.CustomIcon btnInterests = new SocietyScreen.CustomIcon(new Texture("uiTva/interests.png"));
        btnInterests.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (indiObs > -1) {
                    hideTables(true);
                    tableInterests.setVisible(true);
                }
                else uiNo.play();
            }
        });
        iconTable.add(btnInterests);
        iconTable.row();
        SocietyScreen.CustomIcon btnInterestsSystem = new SocietyScreen.CustomIcon(new Texture("uiTva/interests.png"));
        btnInterestsSystem.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (groupObs > -1) {
                    hideTables(true);
                    tableInterests.setVisible(true);
                }
                else uiNo.play();
            }
        });
        iconTableSystem.add(btnInterestsSystem);
        iconTableSystem.row();
        final SocietyScreen.CustomIcon btnRels = new SocietyScreen.CustomIcon(new Texture("uiTva/relations.png"));
        btnRels.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (indiObs > -1) {
                    hideTables(true);
                    cardTva.setType(21);
                    outerTableRelations.setVisible(true);
                    updateRelations(indiObs);
                }
                else uiNo.play();

            }
        });
        iconTable.add(btnRels);
        iconTable.row();
        SocietyScreen.CustomIcon btnGenderTva = new SocietyScreen.CustomIcon(new Texture("uiTva/loverel.png"));
        btnGenderTva.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (indiObs > -1) {
                    hideTables(false);
                    cardTva.setType(43);
                }
                else uiNo.play();
            }
        });
        //iconTable.add(btnGenderTva);
        //iconTable.row();
        SocietyScreen.CustomIcon btnLifts = new SocietyScreen.CustomIcon(new Texture("uiTva/sociallifts.png"));
        btnLifts.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                hideTables(false);
                cardTva.setType(22);
            }
        });
        //iconTable.add(btnLifts);
        //iconTable.row();
        TextButton btnDebug = new TextButton("Debug Mode", tbsTre);
        btnDebug.pad(adaptResTre);
        btnDebug.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                tableNeeds.setVisible(true);
            }
        });
        /*iconTable.add(btnDebug).colspan(6).left();
        iconTable.row();*/
        TextButton btnEntropy = new TextButton("Энтропия", tbsTre);
        btnEntropy.pad(adaptResTre);
        btnEntropy.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                cardTva.setType(24); cardEtt.update(); cardTva.update();
            }
        });
        //iconTable.add(btnEntropy).colspan(6).left();
        //iconTable.row();
        SocietyScreen.CustomIcon btnJob = new SocietyScreen.CustomIcon(new Texture("uiTva/career.png"));
        btnJob.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (indiObs > -1) {
                    hideTables(false);
                    cardTva.setType(41);
                }
                else uiNo.play();
            }
        });
        //iconTable.add(btnJob);
        //iconTable.row();
        SocietyScreen.CustomIcon btnInits = new SocietyScreen.CustomIcon(new Texture("uiTva/inits.png"));
        btnInits.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                hideTables(false);
                outerSystemTable.setVisible(true);
                cardTva.setType(63);
                final ScenButton s = new ScenButton("Закрыть это меню");
                scenButtons.add(s);
                s.setPosition(Gdx.graphics.getWidth() - 420, 120);
                cardStage.addActor(s);
                s.addListener(new ClickListener() {
                    public void clicked(InputEvent event, float x, float y) {
                        labelHintTva.setText("");
                        groupObs = -1;
                        cardEtt.texture = new Texture("ui/indiPreview.png");
                        panelLeft.texture = new Texture("ui/socialsystembck.png");
                        hideTables(false);
                        iconTable.setVisible(true);
                        iconTableSystem.setVisible(false);
                        tableBroadcast.setVisible(false);
                        labelHint.setText("");
                        s.remove();
                    }});
                cardEtt.texture = new Texture("ui/socialSystemPreview.png");
                outerTableTva.setVisible(false);
                indiObs = -1;
                groupObs = 0;
                objectObs = -1;
                tableMove.setVisible(false);
                labelHintTva.setText(langString.get("socialSystem"));
                labelHintTre.setText("");
                cardEtt.setName(society.getSocialSystems().get(0).name);
                iconTableSystem.clear();
                for (int i=0; i<5; i++) {
                    iconTableSystem.add(new Label(i+"", new Label.LabelStyle(fontOswaldTre, Color.CYAN)));
                    for (int j=0; j<society.getSocialSystems().get(0).screws.get(i).size(); j++) {
                        iconTableSystem.add(new CustomScrew(0, i, j, society.getSocialSystems().get(0).screws.get(i).get(j).id));
                    }
                    iconTableSystem.row();
                }
                panelLeft.texture = new Texture("ui/socialsystembck.png");
                iconTable.setVisible(false);
                iconTableSystem.setVisible(true);
                tableBroadcast.setVisible(true);
            }
        });
        iconTable.add(btnInits);
        iconTable.row();
        SocietyScreen.CustomIcon btnScen = new SocietyScreen.CustomIcon(new Texture("uiTva/scenario.png"));
        btnScen.addListener(new ClickListener() {
            //он еще не об сценарии
            public void clicked(InputEvent event, float x, float y) {
                if (indiObs > -1) {
                    hideTables(false);
                    cardTva.setType(42);
                    outerTable.setSize(580, 680*Float.parseFloat(String.valueOf(screenCoef * screenCoef)));
                    btnRandomScenario.setVisible(true);
                    labelRandomScenario.setVisible(true);
                }
                else uiNo.play();
            }
        });
        iconTable.add(btnScen);
        iconTable.row();
        SocietyScreen.CustomIcon btnRoomTre = new SocietyScreen.CustomIcon(new Texture("uiTva/room.png"));
        btnRoomTre.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                hideTables(true);
                tableRoom.setVisible(true);
                control = 1;
                outerTableFurn.setVisible(true);
                final ScenButton s = new ScenButton(langString.get("pricesUp"));
                final ScenButton ss = new ScenButton(langString.get("pricesDown"));
                scenButtons.add(s);
                s.setPosition(Gdx.graphics.getWidth() - 420, 120);
                cardStage.addActor(s);
                scenButtons.add(ss);
                ss.setPosition(Gdx.graphics.getWidth() - 420, 170);
                cardStage.addActor(ss);
                s.addListener(new ClickListener() {
                    public void clicked(InputEvent event, float x, float y) {
                        Json json = new Json();
                        FileHandle file = Gdx.files.local("E/json/DEFAULTCITY/priceprops.txt");
                        if (!file.exists()) {
                            file= Gdx.files.internal("json/DEFAULTCITY/priceprops.txt");
                        }
                        jsonStr = file.readString();
                        PriceProps prices = json.fromJson(PriceProps.class, jsonStr);
                        prices.furnMarkup += 0.05;
                        String jsonStr = json.toJson(prices);
                        file = Gdx.files.local("E/json/DEFAULTCITY/priceprops.txt");
                        file.writeString(jsonStr, false);
                        removeExtraActors();
                    }});
                ss.addListener(new ClickListener() {
                    public void clicked(InputEvent event, float x, float y) {
                        Json json = new Json();
                        FileHandle file = Gdx.files.local("E/json/DEFAULTCITY/priceprops.txt");
                        if (!file.exists()) {
                            file= Gdx.files.internal("json/DEFAULTCITY/priceprops.txt");
                        }
                        jsonStr = file.readString();
                        PriceProps prices = json.fromJson(PriceProps.class, jsonStr);
                        prices.furnMarkup -= 0.05;
                        String jsonStr = json.toJson(prices);
                        file = Gdx.files.local("E/json/DEFAULTCITY/priceprops.txt");
                        file.writeString(jsonStr, false);
                        removeExtraActors();
                    }});
            }
        });
        iconTable.add(btnRoomTre);
        iconTable.row();
        btnTrash = new SocietyScreen.CustomIcon(new Texture("uiTva/trash2.png"));
        btnTrash.setBounds(570, Gdx.graphics.getHeight()-255, 64, 64);
        btnTrash.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                //removeIndi(indiObs);
                society.getIndi(cb, indiObs).alive = false;
                indis.get(indiObs).actions.clear();
                indis.get(indiObs).updateAppearance(1, HoldObject.None);
                indis.get(indiObs).theme = new Texture(Gdx.files.internal("ui/null.png"));
                indis.get(indiObs).targetY = 3000;
                indis.get(indiObs).status = "is dead since: " + dd + "/" + mm + "/" + yyyy + ", " + hh + "." + min;
            }
        });
        btnTrash.setVisible(false);
        cardStage.addActor(btnTrash);
        SocietyScreen.CustomIcon btnControl = new SocietyScreen.CustomIcon(new Texture("uiTva/pause.png"));
        btnControl.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                hideTables(true);
                panelLeft.setTexture(new Texture(Gdx.files.internal("ui/panel9.png")));
                control = 1;
                groupBuild.setVisible(true);
                outerTableIndis.setVisible(true);
                panelRight.setVisible(true);
            }
        });
        iconTable.add(btnControl);
        iconTable.row();
        SocietyScreen.CustomIcon btnHelp = new SocietyScreen.CustomIcon(new Texture("uiTva/help2.png"));
        btnHelp.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                for (int i=0; i <girlStage.getActors().size; i++) {
                    girlStage.getActors().get(i).remove();
                }
                Gdx.gl.glClearColor(1, 1, 1, 0);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                stage.draw();
                saveScreenshot();
                Custom metromap = new Custom(new Texture(Gdx.files.local("E/json/DEFAULTCITY/screenshot.png")), 2500, 0);
                girlStage.addActor(metromap);
                girl = !girl;
            }
        });
        //iconTable.add(btnHelp);
        //iconTable.row();





        clock = new Custom(new Texture("ui/clock.png"), Gdx.graphics.getWidth()/2-256, Gdx.graphics.getHeight()/2-256);
        clock.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                dest = "CLOCK";
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                speed = 1000;
                dest = "0";
                clock.setVisible(false);
            }
        });
        clock.setVisible(false);
        clock.setBounds(Gdx.graphics.getWidth()/2-256, Gdx.graphics.getHeight()/2-256, 512, 512);
        labelTime = new Label("", new Label.LabelStyle(fontOswaldBlack, Color.CYAN));
        labelTime.setPosition(Gdx.graphics.getWidth() - 138, Gdx.graphics.getHeight() - 30);
        labelTime.setBounds(Gdx.graphics.getWidth() - 138, Gdx.graphics.getHeight() - 70, 150, 70);
        labelGamePts = new Label("", new Label.LabelStyle(fontOswaldBlack, Color.CYAN));
        labelGamePts.setBounds(Gdx.graphics.getWidth() - 208, Gdx.graphics.getHeight() - 70, 150, 70);
        labelGamePts.setVisible(false);
        labelTime.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                clock.setVisible(true);
            }
        });
        labelDay = new Label("", new Label.LabelStyle(fontFran, Color.CYAN));
        labelDay.setPosition(Gdx.graphics.getWidth() - 150, Gdx.graphics.getHeight() - 90);
        cardStage.addActor(labelTime);
        cardStage.addActor(labelGamePts);
        cardStage.addActor(labelDay);
        cardStage.addActor(clock);
        scrollPane = new ScrollPane(iconTable);
        outerTable = new Table();
        scrollPaneFurn = new ScrollPane(furnTable);
        scrollPaneRelations = new ScrollPane(tableRelations);
        scrollPaneIndis = new ScrollPane(tableIndis);
        outerTableFurn = new Table();
        outerTableFurn.add(scrollPaneFurn).expand().left();
        outerTableFurn.setVisible(false);
        outerTableRelations = new Table();
        outerTableRelations.add(scrollPaneRelations).expand().left();
        outerTableIndis = new Table();
        outerTableIndis.add(scrollPaneIndis).expand().left();
        outerTableRelations.setVisible(false);
        outerTableIndis.setVisible(false);


        scrollPaneTva = new ScrollPane(labelHint);
        //scrollPaneTva.layout();
        outerTableTva = new Table().top().left();
        scrollPaneTre = new ScrollPane(tableNeeds);
        //scrollPaneTre.layout();
        outerTableNeeds = new Table();
        outerTableNeeds.add(scrollPaneTre).expand().left().top();
        outerTableNeeds.setPosition(90, 170);
        outerTableNeeds.setSize(550, 720);
        if (Gdx.graphics.getHeight() < 1079) {
            outerTableNeeds.setSize(550, 420);
        }
        else if (Gdx.graphics.getHeight() < 759) {
            outerTableNeeds.setSize(550, 250);
        }
        tableInterests.setPosition(90, 170);
        tableInterests.setSize(550, 720);
        if (Gdx.graphics.getHeight() < 1079) {
            tableInterests.setSize(550, 420);
        }
        else if (Gdx.graphics.getHeight() < 759) {
            tableInterests.setSize(550, 250);
        }
        tableTalents.setPosition(90, 170);
        tableTalents.setSize(550, 720);
        if (Gdx.graphics.getHeight() < 1079) {
            tableTalents.setSize(550, 420);
        }
        else if (Gdx.graphics.getHeight() < 759) {
            tableTalents.setSize(550, 250);
        }
        tableRoom.setPosition(70, 170);
        tableRoom.setSize(550, 720);
        if (Gdx.graphics.getHeight() < 1079) {
            tableRoom.setSize(550, 420);
        }
        else if (Gdx.graphics.getHeight() < 759) {
            tableRoom.setSize(550, 250);
        }
        outerTableTva.setPosition(10, 30);
        outerTableTva.setSize(64, 850*Float.parseFloat(String.valueOf(screenCoef)));
        outerSystemTable.setPosition(500, 100);
        outerSystemTable.setSize(Gdx.graphics.getWidth()-630, Gdx.graphics.getHeight()-492);
        Gdx.app.log("screenCoef", String.valueOf(300*Float.parseFloat(String.valueOf(screenCoef))));
        outerTable.setPosition(90, 130);
        outerTable.setSize(310, 750*Float.parseFloat(String.valueOf(screenCoef * screenCoef)));
        outerTable.setTouchable(Touchable.enabled);
        outerTable.add(scrollPaneTva).fill().expand();
        outerTableTva.add(scrollPane).expand().left().top();
        outerTableFurn.setPosition(100, 40);
        outerTableFurn.setSize(700, 820*Float.parseFloat(String.valueOf(screenCoef)));
        outerTableRelations.setPosition(100, 40);
        outerTableRelations.setSize(600, 820*Float.parseFloat(String.valueOf(screenCoef)));
        outerTableIndis.setPosition(Gdx.graphics.getWidth()-170, 40);
        outerTableIndis.setSize(170, 850*Float.parseFloat(String.valueOf(screenCoef)));



        iconTableSystem.setVisible(false);
        cardStage.addActor(outerSystemTable);
        cardStage.addActor(outerTable);
        cardStage.addActor(outerTableTva);
        cardStage.addActor(outerTableFurn);
        cardStage.addActor(outerTableRelations);
        cardStage.addActor(outerTableIndis);
        cardStage.addActor(outerTableNeeds);
        cardStage.addActor(tableInterests);
        cardStage.addActor(tableTalents);
        //cardStage.addActor(tableRoom);
        //tableNeeds.setPosition(300, Gdx.graphics.getHeight()-100);
        //tableNeeds.setBounds(300, Gdx.graphics.getHeight()-300, 100, 100);


        tableBroadcast = new Table().top().left();
        channelField = new TextField("Antenne 2", txtstyle);
        channelField.setBounds(100, Gdx.graphics.getHeight() - 270, 200, 80);
        tableBroadcast.add(channelField).colspan(4).left();
        tableBroadcast.row();
        TextButton applyChannelName = new TextButton("Save", tbs);
        applyChannelName.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                channels.get(Integer.parseInt(society.getBoxes().get(cb).getObjectsTest().get(objectObs).links.get(0))).setName(channelField.getText());
                cardEtt.setName(society.getBoxes().get(cb).getObjectsTest().get(objectObs).links.get(0) + ": " +channels.get(Integer.parseInt(society.getBoxes().get(cb).getObjectsTest().get(objectObs).links.get(0))).getName());
                String jsonStr = json.toJson(channels);
                FileHandle file = Gdx.files.local("E/json/DEFAULTCITY/channels.txt");
                file.writeString(jsonStr, false);
            }
        });
        SocietyScreen.CustomIcon iconAudience = new SocietyScreen.CustomIcon(new Texture("icons2/audience.png"), "Аудитория");
        //tableBroadcast.add(iconAudience);
        labelAudience = new Label("100K", new Label.LabelStyle(fontOswaldTre, Color.ROYAL));
        //tableBroadcast.add(labelAudience);
        SocietyScreen.CustomIcon iconRating = new SocietyScreen.CustomIcon(new Texture("icons2/star.png"), "Рейтинг");
        //tableBroadcast.add(iconRating);
        labelRating = new Label("8", new Label.LabelStyle(fontOswaldTre, Color.CYAN));
        labelRating.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 60; neededY = (int) labelRating.getY();
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
                //return true;
            }
        });
        //tableBroadcast.add(labelRating);
        //tableBroadcast.row();
        tableBroadcast.add(applyChannelName.pad(10));
        tableBroadcast.setPosition(90, 170);
        tableBroadcast.setSize(550, 720);
        cardStage.addActor(tableBroadcast);
        tableBroadcast.setVisible(false);




        SocietyScreen.Custom buildButton = new SocietyScreen.Custom(new Texture("ui/boxButton2.png"), 300, Gdx.graphics.getHeight()-520);
        buildButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                //groupBuildTva.setVisible(true);
                outerTableFurn.setVisible(true);
                groupBuild.setVisible(false);
                labelControl.setText("Обустройство");
            }
        });
        Label labelBuildButton = new Label(langString.get("boxButton"), new Label.LabelStyle(fontOswald, Color.WHITE));
        labelBuildButton.setPosition(300, Gdx.graphics.getHeight()-580);
        //labelBuildButton.setRotation(-30);
        buildButton.setBounds(300, Gdx.graphics.getHeight()-520, 328, 328);
        SocietyScreen.Custom wallsButtonEtt = new SocietyScreen.Custom(new Texture("ui/wallButtonEtt.png"), 550, Gdx.graphics.getHeight()-750);
        wallsButtonEtt.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                for (int q = 0; q<objectsTest.size(); q++) {
                    if (objectsTest.get(q).getType() == 18) {
                        objectsTest.get(q).setAppearance(1);
                        objects.get(q).setTexture(whichObject(objectsTest.get(q).getType(), objectsTest.get(q).getAppearance()));
                        //objects.get(q).setBounds(objects.get(q).getOX(), objects.get(q).getOY(), 527,363);
                        objects.get(q).bounds = true;
                    }
                }
            }
        });
        Label labelWallsButtonEtt = new Label("ПОДНЯТЬ СТЕНЫ", new Label.LabelStyle(fontOswald, Color.WHITE));
        labelWallsButtonEtt.setPosition(250, Gdx.graphics.getHeight()-620);
        //labelBuildButton.setRotation(-30);
        wallsButtonEtt.setBounds(250, Gdx.graphics.getHeight()-750, 165, 165);
        SocietyScreen.Custom wallsButtonTva = new SocietyScreen.Custom(new Texture("ui/wallButtonTva.png"), 870, Gdx.graphics.getHeight()-750);
        wallsButtonTva.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                for (int q = 0; q<objectsTest.size(); q++) {
                    if (objectsTest.get(q).getType() == 18) {
                        objectsTest.get(q).setAppearance(2);
                        objects.get(q).setTexture(whichObject(objectsTest.get(q).getType(), objectsTest.get(q).getAppearance()));
                        objects.get(q).setBounds(objects.get(q).getOX(), objects.get(q).getOY(), 0, 0);
                        objects.get(q).bounds = false;
                    }
                }
            }
        });
        Label labelWallsButtonTva = new Label("ОПУСТИТЬ СТЕНЫ", new Label.LabelStyle(fontOswald, Color.WHITE));
        labelWallsButtonTva.setPosition(330, Gdx.graphics.getHeight()-620);
        //labelBuildButton.setRotation(-30);
        wallsButtonTva.setBounds(330, Gdx.graphics.getHeight()-745, 165, 165);
        SocietyScreen.Custom cityButton = new SocietyScreen.Custom(new Texture("ui/cityButton.png"), 85, Gdx.graphics.getHeight()-795);
        cityButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                society.getBoxes().clear();
                objectsTest.clear();
                objects.clear();
                indis.clear();
                indisTest.clear();
                game.setScreen(new MainMenuScreen(game, lang));
                dispose();
                bckMusic.stop();
                bckMusic.dispose();
            }
        });
        Label labelCityButton = new Label(langString.get("toMenu"), new Label.LabelStyle(fontOswald, Color.WHITE));
        labelCityButton.setPosition(100, Gdx.graphics.getHeight()-835);
        cityButton.setBounds(100, Gdx.graphics.getHeight()-785, 165, 165);
        SocietyScreen.Custom saveButton = new SocietyScreen.Custom(new Texture("ui/saveButtonTva.png"), 100, Gdx.graphics.getHeight()-370);
        saveButton.setBounds(100, Gdx.graphics.getHeight()-370, 165, 165);
        saveButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                FileHandle fileTva = Gdx.files.local("json/room"+boxes.get(cb).boxX+"-"+boxes.get(cb).boxY+"-"+boxes.get(cb).boxZ+".txt");
                boxes.get(cb).yyyy = yyyy; boxes.get(cb).mm = mm; boxes.get(cb).dd = dd; boxes.get(cb).hh = hh; boxes.get(cb).min = min;
                jsonStr = json.prettyPrint(boxes.get(cb));
                fileTva.writeString(jsonStr, false);
                fileTva = Gdx.files.local("E/json/room"+homex+"-"+homey+"-"+homez+".txt");
                fileTva.writeString(jsonStr, false);
                int i = 0;
                fileTva = Gdx.files.local("E/json/roompatterns/pattern"+i+".txt");
                while (fileTva.exists()) {
                    fileTva = Gdx.files.local("E/json/roompatterns/pattern"+i+".txt");
                    i++;
                }
                fileTva.writeString(jsonStr, false);
                FileHandle file = Gdx.files.external("jsonpbox.txt");
                file.writeString(jsonStr, false);
                file = Gdx.files.local("E/json/globalnumcounter.txt");
                String jsonStr = json.toJson(currentGlobalNum);
                file.writeString(jsonStr, false);
                file = Gdx.files.local("E/json/globalnumlist.txt");
                file.writeString(globalListString, true);
                Gdx.app.error("currentGlobalNum", currentGlobalNum+"");
                Gdx.app.error("globalIndi adding", file.readString());
                uiDelay = System.currentTimeMillis() + 3000;
                labelCenter.setText(langString.get("saveSaved"));
            }
        });
        Label labelSaveButton = new Label(langString.get("saveButton"), new Label.LabelStyle(fontOswald, Color.WHITE));
        labelSaveButton.setPosition(100, Gdx.graphics.getHeight()-420);
        SocietyScreen.Custom loadButton = new SocietyScreen.Custom(new Texture("ui/loadButtonTva.png"), 100, Gdx.graphics.getHeight()-580);
        loadButton.setBounds(100, Gdx.graphics.getHeight()-580, 165, 165);
        loadButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                loadGame(homex, homey, homez);
            }});
        Label labelLoadButton = new Label(langString.get("loadButton"), new Label.LabelStyle(fontOswald, Color.WHITE));
        labelLoadButton.setPosition(100, Gdx.graphics.getHeight()-625);
        Label labelUpStairs = new Label(langString.get("up"), new Label.LabelStyle(fontOswald, Color.WHITE));
        labelUpStairs.setPosition(100, Gdx.graphics.getHeight()-1040);
        SocietyScreen.Custom upStairsButton = new SocietyScreen.Custom(new Texture("ui/upstairs.png"), 100, Gdx.graphics.getHeight()-980);
        upStairsButton.setBounds(100, Gdx.graphics.getHeight()-980, 122, 192);
        upStairsButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                loadGame(homex, homey, homez+1);
            }});
        Label labelDownStairs = new Label(langString.get("down"), new Label.LabelStyle(fontOswald, Color.WHITE));
        labelDownStairs.setPosition(250, Gdx.graphics.getHeight()-1040);
        SocietyScreen.Custom downStairsButton = new SocietyScreen.Custom(new Texture("ui/downstairs.png"), 250, Gdx.graphics.getHeight()-980);
        downStairsButton.setBounds(250, Gdx.graphics.getHeight()-980, 122, 192);
        downStairsButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                loadGame(homex, homey, homez-1);
            }});

        //groupBuild.addActor(buildButton);
        //groupBuild.addActor(labelBuildButton);
        /*groupBuild.addActor(wallsButtonEtt);
        groupBuild.addActor(labelWallsButtonEtt);
        groupBuild.addActor(wallsButtonTva);
        groupBuild.addActor(labelWallsButtonTva);*/
        groupBuild.addActor(cityButton);
        groupBuild.addActor(labelCityButton);
        groupBuild.addActor(saveButton);
        groupBuild.addActor(labelSaveButton);
        groupBuild.addActor(loadButton);
        groupBuild.addActor(labelLoadButton);
        groupBuild.addActor(upStairsButton);
        groupBuild.addActor(labelUpStairs);
        groupBuild.addActor(downStairsButton);
        groupBuild.addActor(labelDownStairs);
        groupBuild.setVisible(false);
        cardStage.addActor(groupBuild);


        buttonAdd = new TextButton("+", tbs);
        buttonAdd.pad(30);
        buttonAdd.setBounds(Gdx.graphics.getWidth() - 120, 12, 100,100);
        buttonAdd.getLabel().setFontScale(5, 5);
        buttonAdd.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                for (int q=0; q<1; q++) {
                    //createIndi("1500", "Delta", 0, 18, 4, 1, 0, 118, 15, 22, 6, inits, talents, skills, lifts, creations, genderProps, relations, interests, rnd(8), 2, 4, 0, 150, 150);
                    int gender = 0;
                    gender = rnd(2)-1;
                    if (gender == 1 ) {
                        createRandomIndi(String.valueOf(rnd(9000)+1000), rndSurnameM(), 1,  380, 280);
                    }
                    else {createRandomIndi(String.valueOf(rnd(9000)+1000), rndSurnameF(), 0,  380, 280);}
                    if (socialActivities.size() == 0) {
                        for (int i0=1302; i0<1318; i0++) {
                            socialActivities.add(new SocialAct(i0));
                        }
                        socialActivities.add(new SocialAct(602));
                    }
                    uiDelay = System.currentTimeMillis() + 1000;
                    labelCenter.setText(indisTest.size() + "");
                }
            }
        });
        btnHelpEtt = new TextButton("Помощь", tbs);
        btnHelpEtt.pad(30);
        btnHelpEtt.setBounds(Gdx.graphics.getWidth()-330, 12, 120, 100);
        btnHelpEtt.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                labelGirl.setText("Привет, я твоя проводница в этой игре. \nЗдесь много всего, очень большая комната, правда?");
                girlCard = 0;
                labelAppuyez.setVisible(true);
                labelGirl.setVisible(true);
                oldGirl.setVisible(true);
                btnHelpEtt.setVisible(false);
                btnHelpTva.setVisible(true);
            }
        });
        btnHelpEtt.setVisible(false);
        btnHelpTva = new TextButton("Убрать", tbs);
        btnHelpTva.pad(30);
        btnHelpTva.setBounds(Gdx.graphics.getWidth()-330, 12, 120, 100);
        btnHelpTva.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                labelGirl.setText("Привет, я твоя проводница в этой игре. \nЗдесь много всего, очень большая комната, правда?");
                labelAppuyez.setVisible(false);
                labelGirl.setVisible(false);
                oldGirl.setVisible(false);
                btnHelpEtt.setVisible(false); //was true before changing to UI4
                btnHelpTva.setVisible(false);
            }
        });
        buttonRemove = new TextButton("Remove Indi", tbs);
        buttonRemove.pad(adaptRes);
        buttonRemove.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {

            }
        });

        buttonBack = new TextButton("Menu", tbs);
        buttonBack.pad(15);
        buttonBack.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                game.setScreen(new MainMenuScreen(game, lang));
                dispose();
            }
        });
        btnMore = new TextButton("...", tbs);
        btnMore.setBounds(Gdx.graphics.getWidth() - 220, 12, 100,100);
        btnMore.pad(15);
        btnMore.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                control = 1;
                labelControl.setVisible(false); //was true
                btnMore.setVisible(false);
                groupBuild.setVisible(true);
                labelControl.setText(langString.get("controlPanel"));
            }
        });
        btnMore.setVisible(false);
        //table.add(buttonSave);
        //table.row();
        //table.add(buttonLoad);
        //table.row();
        /*if (0 == 0) {
            table.add(btnZoom1);
            table.row();
        }*/
        cardStage.addActor(buttonAdd);
        cardStage.addActor(btnHelpEtt);
        btnHelpTva.setVisible(false);
        cardStage.addActor(btnHelpTva);
        cardStage.addActor(btnMore);
        label = new Label("", new Label.LabelStyle(fontFran, Color.WHITE));
        cardStage.addActor(this.label);
        cardStage.addActor(this.labelDebug);
        nameView = new Label("", new Label.LabelStyle(font, Color.CYAN));
        cardStage.addActor(this.nameView);
        label.setPosition(0, 10);
        btnDown = new TextButton("Down", tbs);
        btnDown.pad(15);
        btnDown.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                dest = "DOWN";
                removeExtraActors();
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                dest = "0";
                //return true;
            }
        });
        btnUp = new TextButton("Up", tbs);
        btnUp.pad(15);
        btnUp.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                dest = "UP";
                removeExtraActors();
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                dest = "0";
                //return true;
            }
        });
        btnLeft = new TextButton("Left", tbs);
        btnLeft.pad(15);
        btnLeft.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                dest = "LEFT";
                removeExtraActors();
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                dest = "0";
                //return true;
            }
        });
        btnRight = new TextButton("Right", tbs);
        btnRight.pad(15);
        btnRight.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                dest = "RIGHT";
                removeExtraActors();
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                dest = "0";
                //return true;
            }
        });
        btnRotate = new TextButton("Rotate", tbs);
        btnRotate.pad(15);
        btnRotate.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                removeExtraActors();
                if (objectsTest.get(objectObs).getAppearance() % 2 == 1) {
                    if (objectsTest.get(objectObs).getType() == 7 || objectsTest.get(objectObs).getType() == 29) {
                        objectsTest.get(objectObs).setAppearance(objectsTest.get(objectObs).getAppearance() - 1);
                        objects.get(objectObs).setTexture(whichObject(objectsTest.get(objectObs).getType(), objectsTest.get(objectObs).getAppearance()));
                    }
                    else {
                        objectsTest.get(objectObs).setAppearance(objectsTest.get(objectObs).getAppearance() + 1);
                        objects.get(objectObs).setTexture(whichObject(objectsTest.get(objectObs).getType(), objectsTest.get(objectObs).getAppearance()));}
                }
                else {
                    if (objectsTest.get(objectObs).getType() == 7 || objectsTest.get(objectObs).getType() == 29) {
                        objectsTest.get(objectObs).setAppearance(objectsTest.get(objectObs).getAppearance() + 1);
                        objects.get(objectObs).setTexture(whichObject(objectsTest.get(objectObs).getType(), objectsTest.get(objectObs).getAppearance()));
                    }
                    else {
                        objectsTest.get(objectObs).setAppearance(objectsTest.get(objectObs).getAppearance() - 1);
                        objects.get(objectObs).setTexture(whichObject(objectsTest.get(objectObs).getType(), objectsTest.get(objectObs).getAppearance()));}
                }
            }
        });
        btnRemove = new TextButton("Remove", tbs);
        btnRemove.pad(15);
        btnRemove.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                int profit = 0;
                if (objectsTest.get(objectObs).getAppearance() % 2 == 1) {
                    if (objectsTest.get(objectObs).getType() == 7 || objectsTest.get(objectObs).getType() == 29) {
                        objectsTest.get(objectObs).setAppearance(objectsTest.get(objectObs).getAppearance() - 1);
                    }
                }
                for (int i=0; i < furn.size(); i++) {
                    if (furn.get(i).type == objectsTest.get(objectObs).getType() && furn.get(i).appear == objectsTest.get(objectObs).getAppearance()) {
                        profit = (int) (furn.get(i).price * (0.6 + rnd(30) / 100.0));
                    }
                }
                getIndiActor(indiObs).marker = 1;
                getIndiActor(indiObs).markerString = "+"+profit+"$";
                society.getIndi(cb, indiObs).setWealth(society.getIndi(cb, indiObs).getWealth() + profit);
                objectsTest.get(objectObs).setType(-20);
                removeExtraActors();
                society.getBoxes().get(cb).getObjectsTest().get(objectObs).setType(-20);
                objects.get(objectObs).setTexture(whichObject(-20, -2));
            }
        });

        btnMoveOk = new TextButton("OK", tbs);
        btnMoveOk.pad(15);
        btnMoveOk.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                dest = "0";
                removeExtraActors();
                tableMove.setVisible(false);
                objectObs = -1;
            }
        });
        /*tableMove.add(btnUp);
        tableMove.row();
        tableMove.add(btnLeft);
        tableMove.row();
        tableMove.add(btnRight);
        tableMove.row();
        tableMove.add(btnDown);
        tableMove.row();*/
        tableMove.add(btnRotate).width((Gdx.graphics.getWidth()-800)/4);
        //tableMove.row();
        /*tableMove.add(btnOverlay);
        tableMove.row();*/
        tableMove.add(btnRemove).width((Gdx.graphics.getWidth()-800)/4);
        //tableMove.row();
        tableMove.add(btnMoveOk).width((Gdx.graphics.getWidth()-800)/2);
        //tableMove.row();
        buttonAdd.setZIndex(1000);
        cardStage.addActor(labelControl);
        cardStage.addActor(tableMove);
        for (int i = 0; i < Relation.Levels.values().length; i++) {
            TextButton btn = new TextButton(Relation.Levels.values()[i].name(), tbs);
            btn.pad(15);
            final int finalI = i;
            btn.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    society.getIndi(cb, indiObs).getRelations().get(ff).setLevel(Relation.Levels.values()[finalI]);
                    updateRelations(indiObs);
                    tableRelationsChoose.setVisible(false);
                }
            });
            tableRelationsChoose.add(btn).width(200).height(80);
            tableRelationsChoose.row();
        }
        tableRelationsChoose.setVisible(false);
        cardStage.addActor(tableRelationsChoose);
        cardStage.addActor(labelCenter);
        tableMove.setVisible(false);
        //labelInter.remove();


        tableDebugActions.setZIndex(200);
        TextButton mode0 = new TextButton("set gamemode to 0", tbs);
        mode0.pad(15);
        mode0.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                GAMEMODE = 0;
                labelGamePts.setVisible(false);
            }
        });
        TextButton mode2 = new TextButton("set gamemode to 2", tbs);
        mode2.pad(15);
        mode2.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                GAMEMODE = 2;
                labelGamePts.setVisible(true);
            }
        });
        TextButton mode3 = new TextButton("set gamemode to 3", tbs);
        mode3.pad(15);
        mode3.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                GAMEMODE = 3;
                labelGamePts.setVisible(true);
            }
        });
        TextButton mode4 = new TextButton("A11", tbs);
        mode4.pad(15);
        mode4.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                indis.get(indiObs).actions.add(0, new Action(11, 300, 500, homez));
            }
        });
        tableDebugActions.add(mode0);
        tableDebugActions.row();
        tableDebugActions.add(mode2);
        tableDebugActions.row();
        tableDebugActions.add(mode3);
        tableDebugActions.row();
        tableDebugActions.add(mode4);
        tableDebugActions.row();
        stage.addActor(tableDebugActions);
        tableDebugActions.setVisible(false);
        scrollPane.setVisible(true);
        //СКРЫТИЕ СЛУЖЕБНЫХ ЭЛЕМЕНТОВ
        cardTva.listen();
    }

    public void show() {
        //createInterfacePeopleBOX();
    }

    public void createRandomIndi(String name, String surname, int gender) {
        createRandomIndi(name, surname, gender, 1000, 500);
    }

    public void createRandomIndi(String name, String surname, int gender, float actorX, float actorY) {
        coreFunc.load = 1;
        if (indis.size() < 128) {
            try {
                currentGlobalNum += 1;
            }
            catch (NullPointerException e) {
                Json json = new Json();
                Gdx.app.log("local storage path", Gdx.files.getLocalStoragePath());
                FileHandle file = Gdx.files.local("E/json/globalnumcounter.txt");
                if (file.exists()) {
                    jsonStr = file.readString();
                    Gdx.app.log("JSON currentGlobalNum", jsonStr);
                    currentGlobalNum = json.fromJson(Integer.class, jsonStr);
                } else {
                    file = Gdx.files.internal("json/globalnumcounter.txt");
                    if (file.exists()) {
                        jsonStr = file.readString();
                        Gdx.app.log("JSON currentGlobalNum", jsonStr);
                        currentGlobalNum = json.fromJson(Integer.class, jsonStr);
                    } else {
                        currentGlobalNum = 0;
                    }
                }
            }
            int classes = 0;
            int iq = 0;
            int job = rnd(society.getJobs().size())-1;
            int appearance = rnd(3);
            int age = yyyy-(rnd(70)+18);
            int wealth = rnd(800)*rnd(80);
            ArrayList<NeedsArray> needs = new ArrayList<NeedsArray>();
            ArrayList<TalentsArray> talents = new ArrayList<TalentsArray>();
            ArrayList<SkillsArray> skills = new ArrayList<SkillsArray>();
            ArrayList<GenderProps> genderProps = new ArrayList<GenderProps>();
            ArrayList<InterestsArray> interests = new ArrayList<InterestsArray>();
            ArrayList<Addition.Creation> creations = new ArrayList<Addition.Creation>();
            ArrayList<Relation> relations = new ArrayList<Relation>();
            ArrayList<Init> inits = new ArrayList<Init>();
            ArrayList<SocialLiftsArray> lifts = new ArrayList<SocialLiftsArray>();
            num = indis.size();

            switch (society.getBoxes().get(cb).getProperty())
            {
                case Museum: needs.add(new NeedsArray(rnd(70)+30, rnd(100), rnd(100), rnd(100), 100, 100, rnd(100), rnd(100), rnd(100), 100, rnd(20), rnd(100), rnd(20), 100));
                default: needs.add(new NeedsArray(rnd(70)+30, rnd(100), rnd(100), rnd(100), 100, 100, rnd(100), rnd(100), rnd(100), 100, rnd(100), rnd(100), rnd(100), 100));
            }
            talents.add(new TalentsArray(rnd(100), rnd(100), rnd(30), rnd(100), rnd(100), rnd(100), rnd(100), rnd(100), rnd(100), rnd(100)));
            skills.add(new SkillsArray(rnd(10), rnd(10), rnd(10)));interests.add(new InterestsArray(rnd(100), rnd(100), rnd(100), rnd(100), rnd(100), rnd(100), rnd(100), rnd(100), rnd(100), rnd(100), rnd(100), rnd(100), rnd(100), rnd(100), rnd(100), rnd(100), rnd(100), rnd(100), rnd(100), rnd(100), rnd(100), rnd(10)));
            genderProps.add(new GenderProps(rnd(200)-100, rnd(200)-100, rnd(200)-100));
            lifts.add(new SocialLiftsArray(0, false, false, false, false));
            Indi ii = new Indi(name, surname, currentGlobalNum, gender, age, classes, iq, job, wealth, homex, homey, homez, inits, needs, talents, skills, lifts, creations, genderProps, relations, interests, appearance, rnd(4), rnd(10), rnd(4), indisTest.size());
            indisTest.add(ii);
            IndiActor myActor = new IndiActor(name, surname, gender, age, wealth, appearance, actorX, actorY, indis.size());
            for (int i = 0; i < objectsTest.size(); i++) {
                if (objectsTest.get(i).type == -2) {
                    myActor.setX(objectsTest.get(i).getX() + rnd(40));
                    myActor.setX(objectsTest.get(i).getFixity() - rnd(40));
                }
            }
            indis.add(myActor);
            myActor.setTouchable(Touchable.enabled);
            myActor.setZIndex(102);
            stage.addActor(myActor);
            GlobalNumIndi globalIndi = new GlobalNumIndi(name, surname, homex, homey, homez, num, currentGlobalNum);
            jsonStr = json.toJson(globalIndi);
            globalListString += jsonStr;
        }
        else {uiNo.play();
            uiDelay = System.currentTimeMillis() + 3000;
            labelCenter.setText(langString.get("indiLimit"));
        }
        coreFunc.load = 0;
    }

    void createObject(int type, int appearance, int cleanliness0, int fixity, int x, int y) {
        //1-fridges 2-beds 3-lamps 4-tables 5-TVs 6-PCs 7-deco 8-armchairs 9-chairs 11-shelves
        num0 = objectsTest.size() + 1;
        Gdx.app.error("num0: "+ num0, "objectsTest.size: "+ objectsTest.size() + ", objects.size:" + objects.size());
        Texture texture0 = whichObject(type, appearance);
        ObjectActor myActorTva =  new ObjectActor(texture0, x, y, num0);
        myActorTva.setTouchable(Touchable.enabled);
        //if (type == 0) {myActorTva.setTouchable(Touchable.disabled);}
        society.getBoxes().get(cb).getObjectsTest().add(new ObjectTest(type, appearance, cleanliness0, x, y, fixity, whatTileSizeX(type), whatTileSizeY(type), num0));
        objects.add(myActorTva);
        // оставил только точную адресацию, иначе оно дублируется и добавляет два объекта и одного актера
        myActorTva.setZIndex(104);
        stage.addActor(myActorTva);
        //objectObs = num0-1;
        if (type == 25) {
            String[] arr = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "1", "2", "3", "4", "5"};
            String name = arr[rnd(arr.length - 1)] + arr[rnd(arr.length - 1)] + arr[rnd(arr.length - 1)];
            channels.add(new Addition.Channel(name));
            String jsonStr = json.toJson(channels);
            FileHandle file = Gdx.files.local("E/json/DEFAULTCITY/channels.txt");
            file.writeString(jsonStr, false);
            objectsTest.get(objects.size()-1).links.add(String.valueOf(channels.size()-1)); //добавляем индекс канала из channels, который привязан к данной аппаратной
        }
    }

    void createObject(int type, int appearance, int x, int y) {
        createObject(type, appearance, 0, 0, x, y);
    }

    public void createJob(String name, int salary, int type, SkillsArray skills) {
        jobN++;
        Job job = new Job(name, jobN, salary, 10, type, 9, 8, skills);
        society.getJobs().add(job);
    }

    public void loadIndi(int i, int j) {
        try {
            Indi indiTest = society.getIndi(cb, j);
            IndiActor myActor = new IndiActor(indiTest.getName(), indiTest.getSurname(), indiTest.getGender(), indiTest.getGender(), indiTest.getWealth(), indiTest.getAppearance(), 150+(j+20)*6, 150+(20+j)*6, indiTest.getmyNum());
            indis.add(myActor);
            myActor.setTouchable(Touchable.enabled);
            myActor.setZIndex(110);
            myActor.setVisible(indiTest.alive);
            stage.addActor(myActor);
        }
        catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            indis.remove(indis.size()-1);
        }
    }

    public void loadObject(int i, int j) {
        try {
            ObjectTest objectTest = society.getBoxes().get(i).getObjectsTest().get(j);
            Texture texture = whichObject(objectTest.getType(), objectTest.getAppearance());
            ObjectActor objectActor = new ObjectActor(texture, objectTest.getX(), objectTest.getFixity(), objectTest.getNum0());
            objects.add(objectActor);
            objectActor.setZIndex(10);
            objectActor.setTouchable(Touchable.enabled);
            objectActor.setOX(objectTest.getX());
            objectActor.setOY(objectTest.getFixity());
            stage.addActor(objectActor);
        }
        catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            //society.getBoxes().get(i).getObjectsTest().remove(j);
            //objectsTest.remove(j);
        }
    }

    public boolean touchMarker(int x, int y) {
        //x, y - положение актера на данный момент
        boolean bool = true;
        for (int q=0; q<markers.size(); q++) {
            if (y<markers.get(q).y3 && y>markers.get(q).y4 && y>0.5*x && x>markers.get(q).x1 && x<markers.get(q).x2) {bool = true;}
            else if (y>markers.get(q).y2 && y<markers.get(q).y1 && y>-0.5*x && x>markers.get(q).x1 && x<markers.get(q).x2) {bool = true;}
            else if (y<markers.get(q).y3 && y>markers.get(q).y4 && y<-0.5*x && x>markers.get(q).x3 && x<markers.get(q).x4) {bool = true;}
            else if (y>markers.get(q).y2 && y<markers.get(q).y1 && y<0.5*x && x>markers.get(q).x3 && x<markers.get(q).x4) {bool = true;}
            else if (x<markers.get(q).x1 || x>markers.get(q).x4 || y<markers.get(q).y1 || y>markers.get(q).y4) {bool = true;}
            else {bool = false;}
        }
        return bool;
    }

    public void autoCreatingMarkers() {
        for (int q = 0; q<objects.size(); q++) {
            try {
                int x = objects.get(q).getOX();
                int y = objects.get(q).getOY();
                if (objectsTest.get(q) != null && objectsTest.get(q).getType() == 19 && objectsTest.get(q).getAppearance() == 1) {
                    markers.add(new Marker(x, x+102, x+102, x+256, y, y+84, y+84, y+184));
                }
                else if (objectsTest.get(q) != null && objectsTest.get(q).getType() == 19 && objectsTest.get(q).getAppearance() == 2) {
                    markers.add(new Marker(x, x+160, x+160, x+256, y, y+100, y+100, y+184));
                }
                if (objectsTest.get(q) != null && objectsTest.get(q).getType() == 15 && objectsTest.get(q).getAppearance() == 1) {
                    markers.add(new Marker(x+5, x+210, x+210, x+252, y+116, y, y, y+24));
                }
                else if (objectsTest.get(q) != null && objectsTest.get(q).getType() == 15 && objectsTest.get(q).getAppearance() == 2) {
                    markers.add(new Marker(x+252, x+210, x+210, x+5, y+24, y, y, y+116));
                }
                if (objectsTest.get(q) != null && objectsTest.get(q).getType() == -15 && objectsTest.get(q).getAppearance() == 1) {
                    markers.add(new Marker(x+5, x+210, x+210, x+252, y+116, y, y, y+24));
                }
                else if (objectsTest.get(q) != null && objectsTest.get(q).getType() == -15 && objectsTest.get(q).getAppearance() == 2) {
                    markers.add(new Marker(x+252, x+210, x+210, x+5, y+24, y, y, y+116));
                }
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
    }

    Texture whichAnimation(int action, int type, int appearance) {
        if (type == 5) {
            if (action == 602 || action == 2901) {
                switch(appearance) {
                    case 1: return new Texture("tvs/tvset12.png");
                    case 2: return new Texture("tvs/tvset11.png");
                    case 5: return new Texture("tvs/tvset22.png");
                    case 6: return new Texture("tvs/tvset21.png");
                    case 9: return new Texture("tvs/tvset32.png");
                    case 10: return new Texture("tvs/tvset31.png");
                    case 13: return new Texture("tvs/tvset43.png");
                    case 14: return new Texture("tvs/tvset44.png");
                    case 17: return new Texture("tvs/tvset53.png");
                    case 18: return new Texture("tvs/tvset54.png");
                    default: return new Texture("tvs/tvset12.png");
                }
            }
        }
        if (type == 6) {
            if (action == 601) {
                switch(appearance) {
                    case 1: return new Texture("pcs/pc11.png");
                    case 2: return new Texture("pcs/pc12.png");
                    case 3: return new Texture("pcs/pc21.png");
                    case 4: return new Texture("pcs/pc22.png");
                    case 5: return new Texture("pcs/mac11.png");
                    case 6: return new Texture("pcs/mac12.png");
                    default: return new Texture("pcs/pc12.png");
                }
            }
            else return whichObject(type, appearance);
        }
        else return whichObject(type, appearance);
    }

    static Texture whichObject(int type, int appearance) { //★★★★★ 26.03.2020
        if (type == -15) {
            switch(appearance) {
                case 1: return new Texture("comm/buffet13.png");
                case 2: return new Texture("comm/buffet14.png");
                default: return new Texture("comm/buffet13.png");
            }
        }
        if (type == -4) {
            switch (appearance) {
                case 1:
                    return new Texture("deco/fire1.png");
                case 2:
                    return new Texture("deco/fire2.png");
                default:
                    return new Texture("deco/fire1.png");
            }
        }
        if (type == -3) {
            switch (appearance) {
                case 1:
                    return new Texture("deco/cockroaches1.png");
                case 2:
                    return new Texture("deco/cockroaches2.png");
                default:
                    return new Texture("deco/cockroaches1.png");
            }
        }
        if (type == -2) {
            switch(appearance) {
                case 1: return new Texture("comm/door11.png");
                case 2: return new Texture("comm/door12.png");
                default: return new Texture("comm/door11.png");
            }
        }
        if (type == -1) {
            switch(appearance) {
                case 1: return new Texture("comm/toiletdoor11.png");
                case 2: return new Texture("comm/toiletdoor12.png");
                default: return new Texture("comm/toiletdoor11.png");
            }
        }
        if (type == 0) {
            switch(appearance) {
                case 1: return new Texture("deco/painting11.png");
                case 2: return new Texture("deco/painting12.png");
                case 3: return new Texture("comm/toiletdoor11.png");
                case 4: return new Texture("comm/toiletdoor12.png");
                case 5: return new Texture("deco/cockroaches1.png");
                case 6: return new Texture("deco/cockroaches2.png");
                case 7: return new Texture("comm/door11.png");
                case 8: return new Texture("comm/door12.png");
                default: return new Texture("debugItems/aestheticsBox.png");
            }
        }
        if (type == 1) {
            switch(appearance) {
                case 1: return new Texture("kitchen/fridge11.png");
                case 2: return new Texture("kitchen/fridge12.png");
                case 3: return new Texture("kitchen/fridge21.png");
                case 4: return new Texture("kitchen/fridge22.png");
                case 5: return new Texture("kitchen/fridge31.png");
                case 6: return new Texture("kitchen/fridge32.png");
                case 7: return new Texture("kitchen/fridge41.png");
                case 8: return new Texture("kitchen/fridge42.png");
                default: return new Texture("kitchen/fridge11.png");
            }
        }
        if (type == 2) {
            switch(appearance) {
                case 1: return new Texture("beds/bed11.png");
                case 2: return new Texture("beds/bed12.png");
                case 5: return new Texture("beds/bed21.png");
                case 6: return new Texture("beds/bed22.png");
                case 9: return new Texture("beds/bed31.png");
                case 10: return new Texture("beds/bed32.png");
                case 13: return new Texture("beds/bed41.png");
                case 14: return new Texture("beds/bed42.png");
                default: return new Texture("beds/bed11.png");
            }
        }
        if (type == 3) {
            switch(appearance) {
                case 1: return new Texture("lamps/lamp1.png");
                case 2: return new Texture("lamps/lamp2.png");
                case 3: return new Texture("lamps/lamp31.png");
                case 4: return new Texture("lamps/lamp32.png");
                case 5: return new Texture("tvstation/light11.png");
                case 6: return new Texture("tvstation/light12.png");
                case 7: return new Texture("lamps/lamp41.png");
                case 8: return new Texture("lamps/lamp42.png");
                default: return new Texture("lamps/lamp1.png");
            }
        }
        if (type == 4) {
            switch(appearance) {
                case 1: return new Texture("tables/table12.png");
                case 2: return new Texture("tables/table11.png");
                case 3: return new Texture("tables/table31.png");
                case 4: return new Texture("tables/table32.png");
                case 5: return new Texture("tables/table41.png");
                case 6: return new Texture("tables/table42.png");
                case 7: return new Texture("tables/table51.png");
                case 8: return new Texture("tables/table52.png");
                case 9: return new Texture("tables/table61.png");
                case 10: return new Texture("tables/table62.png");
                case 11: return new Texture("tables/table71.png");
                case 12: return new Texture("tables/table72.png");
                case 13: return new Texture("tables/table81.png");
                case 14: return new Texture("tables/table82.png");
                case 15: return new Texture("tables/table91.png");
                case 16: return new Texture("tables/table92.png");
                default: return new Texture("tables/table12.png");
            }
        }
        if (type == 5) {
            switch(appearance) {
                case 1: return new Texture("tvs/tvset11.png");
                case 2: return new Texture("tvs/tvset12.png");
                case 5: return new Texture("tvs/tvset21.png");
                case 6: return new Texture("tvs/tvset22.png");
                case 9: return new Texture("tvs/tvset31.png");
                case 10: return new Texture("tvs/tvset32.png");
                case 13: return new Texture("tvs/tvset41.png");
                case 14: return new Texture("tvs/tvset42.png");
                case 17: return new Texture("tvs/tvset51.png");
                case 18: return new Texture("tvs/tvset52.png");
                case 21: return new Texture("tvs/tvset61.png");
                case 22: return new Texture("tvs/tvset62.png");
                default: return new Texture("tvs/tvset11.png");
            }
        }
        if (type == 6) {
            switch(appearance) {
                case 1: return new Texture("pcs/pc12.png");
                case 2: return new Texture("pcs/pc11.png");
                case 3: return new Texture("pcs/pc22.png");
                case 4: return new Texture("pcs/pc21.png");
                case 5: return new Texture("pcs/mac12.png");
                case 6: return new Texture("pcs/mac11.png");
                case 7: return new Texture("pcs/pc41.png");
                case 8: return new Texture("pcs/pc42.png");
                default: return new Texture("pcs/pc11.png");
            }
        }
        if (type == 7 || type == 29) {
            switch(appearance) {
                case 1: return new Texture("deco/bush2.png");
                case 2: return new Texture("deco/book11.png");
                case 3: return new Texture("deco/book12.png");
                case 4: return new Texture("deco/painting11.png");
                case 5: return new Texture("deco/painting12.png");
                case 6: return new Texture("deco/painting13.png");
                case 7: return new Texture("deco/painting14.png");
                case 8: return new Texture("deco/pool1.png");
                case 9: return new Texture("deco/pool2.png");
                case 10: return new Texture("deco/statue11.png");
                case 11: return new Texture("deco/statue12.png");
                case 12: return new Texture("deco/statue13.png");
                case 13: return new Texture("deco/statue14.png");
                case 14: return new Texture("debugItems/antenne1.png");
                case 15: return new Texture("debugItems/antenne2.png");
                case 16: return new Texture("deco/bush3.png");
                case 17: return new Texture("deco/vase1.png");
                case 20: return new Texture("kitchen/counter13.png");
                case 21: return new Texture("kitchen/counter14.png");
                case 22: return new Texture("kitchen/counter17.png");
                case 23: return new Texture("kitchen/counter18.png");
                case 24: return new Texture("kitchen/counter11.png");
                case 25: return new Texture("kitchen/counter12.png");
                case 26: return new Texture("deco/painting11.png");
                case 27: return new Texture("deco/painting12.png");
                case 28: return new Texture("deco/column11.png");
                case 29: return new Texture("deco/column12.png");
                case 30: return new Texture("deco/column21.png");
                case 31: return new Texture("deco/column22.png");
                case 32: return new Texture("deco/statue21.png");
                case 33: return new Texture("deco/statue22.png");
                case 34: return new Texture("deco/plant2.png");
                case 35: return new Texture("deco/plant2.png");
                case 36: return new Texture("deco/statue31.png");
                case 37: return new Texture("deco/statue32.png");
                case 38: return new Texture("deco/statue41.png");
                case 39: return new Texture("deco/statue42.png");
                case 40: return new Texture("deco/statue51.png");
                case 41: return new Texture("deco/statue52.png");
                case 42: return new Texture("deco/flamingo11.png");
                case 43: return new Texture("deco/flamingo12.png");
                case 44: return new Texture("deco/painting21.png");
                case 45: return new Texture("deco/painting22.png");
                case 46: return new Texture("deco/dolphin11.png");
                case 47: return new Texture("deco/dolphin12.png");
                case 48: return new Texture("tvstation/glasswall11.png");
                case 49: return new Texture("tvstation/glasswall12.png");
                case 50: return new Texture("tvstation/glasswall21.png");
                case 51: return new Texture("tvstation/glasswall22.png");
                case 52: return new Texture("tvstation/prompter11.png");
                case 53: return new Texture("tvstation/prompter12.png");
                case 54: return new Texture("deco/aquarium11.png");
                case 55: return new Texture("deco/aquarium12.png");
                default: return new Texture("deco/painting11.png");
            }
        }
        if (type == 8) {
            switch(appearance) {
                case 1: return new Texture("armchairs/armchair11.png");
                case 2: return new Texture("armchairs/armchair12.png");
                case 3: return new Texture("armchairs/armchair13.png");
                case 4: return new Texture("armchairs/armchair14.png");
                case 5: return new Texture("armchairs/armchair21.png");
                case 6: return new Texture("armchairs/armchair22.png");
                case 7: return new Texture("armchairs/armchair23.png");
                case 8: return new Texture("armchairs/armchair24.png");
                case 9: return new Texture("armchairs/armchair31.png");
                case 10: return new Texture("armchairs/armchair32.png");
                case 11: return new Texture("armchairs/armchair41.png");
                case 12: return new Texture("armchairs/armchair42.png");
                case 13: return new Texture("armchairs/armchair51.png");
                case 14: return new Texture("armchairs/armchair52.png");
                case 15: return new Texture("armchairs/armchair61.png");
                case 16: return new Texture("armchairs/armchair62.png");
                default: return new Texture("armchairs/armchair11.png");
            }
        }
        if (type == 9) {
            switch(appearance) {
                case 2: return new Texture("chairs/chair12.png");
                case 5: return new Texture("chairs/chair51.png");
                case 6: return new Texture("chairs/chair52.png");
                case 7: return new Texture("chairs/chair61.png");
                case 8: return new Texture("chairs/chair62.png");
                case 9: return new Texture("chairs/chair31.png");
                case 10: return new Texture("chairs/chair32.png");
                case 11: return new Texture("chairs/chair71.png");
                case 12: return new Texture("chairs/chair72.png");
                case 13: return new Texture("chairs/chair81.png");
                case 14: return new Texture("chairs/chair82.png");
                case 15: return new Texture("chairs/chair91.png");
                case 16: return new Texture("chairs/chair92.png");
                case 17: return new Texture("chairs/barstool11.png");
                case 18: return new Texture("chairs/barstool12.png");
                case 19: return new Texture("chairs/barstool21.png");
                case 20: return new Texture("chairs/barstool22.png");
                default: return new Texture("chairs/chair12.png");
            }
        }
        if (type == 10) {
            switch(appearance) {
                case 1: return new Texture("skills/piano31.png");
                case 2: return new Texture("skills/piano32.png");
                case 5: return new Texture("skills/piano21.png");
                case 6: return new Texture("skills/piano22.png");
                default: return new Texture("skills/piano31.png");
            }
        }
        if (type == 11) {
            switch(appearance) {
                case 1: return new Texture("shelves/bookshelf1.png");
                case 2: return new Texture("shelves/bookshelf2.png");
                case 5: return new Texture("shelves/bookshelf31.png");
                case 6: return new Texture("shelves/bookshelf32.png");
                case 7: return new Texture("shelves/bookshelf41.png");
                case 8: return new Texture("shelves/bookshelf42.png");
                case 9: return new Texture("shelves/bookshelf51.png");
                case 10: return new Texture("shelves/bookshelf52.png");
                case 11: return new Texture("shelves/bookshelf61.png");
                case 12: return new Texture("shelves/bookshelf62.png");
                default: return new Texture("shelves/bookshelf2.png");
            }
        }
        if (type == 12) {
            switch(appearance) {
                case 1: return new Texture("kitchen/oven11.png");
                case 2: return new Texture("kitchen/oven12.png");
                case 3: return new Texture("kitchen/counter15.png");
                case 4: return new Texture("kitchen/counter16.png");
                default: return new Texture("kitchen/oven11.png");
            }
        }
        if (type == 13) {
            switch(appearance) {
                case 1: return new Texture("hospital/dropper1.png");
                case 5: return new Texture("hospital/bunk11.png");
                case 6: return new Texture("hospital/bunk12.png");
                case 9: return new Texture("hospital/counter11.png");
                case 10: return new Texture("hospital/counter12.png");
                case 13: return new Texture("hospital/cross1.png");
                case 14: return new Texture("hospital/cross2.png");
                default: return new Texture("hospital/dropper1.png");
            }
        }
        if (type == 14) {
            switch(appearance) {
                case 1: return new Texture("univer/whiteboard11.png");
                case 2: return new Texture("univer/whiteboard12.png");
                default: return new Texture("univer/whiteboard11.png");
            }
        }
        if (type == 15) {
            switch(appearance) {
                case 1: return new Texture("comm/buffet11.png");
                case 2: return new Texture("comm/buffet12.png");
                default: return new Texture("comm/buffet11.png");
            }
        }
        if (type == 16) {
            switch(appearance) {
                case 1: return new Texture("coaches/coach11.png");
                case 2: return new Texture("coaches/coach12.png");
                case 3: return new Texture("coaches/coach13.png");
                case 4: return new Texture("coaches/coach14.png");
                case 5: return new Texture("coaches/couch31.png");
                case 6: return new Texture("coaches/couch32.png");
                case 7: return new Texture("coaches/couch41.png");
                case 8: return new Texture("coaches/couch42.png");
                case 9: return new Texture("coaches/couch51.png");
                case 10: return new Texture("coaches/couch52.png");
                case 11: return new Texture("coaches/couch61.png");
                case 12: return new Texture("coaches/couch62.png");
                default: return new Texture("coaches/coach11.png");
            }
        }
        if (type == 17) {
            switch(appearance) {
                case 1: return new Texture("audios/speaker11.png");
                case 2: return new Texture("audios/speaker12.png");
                default: return new Texture("audios/speaker11.png");
            }
        }
        if (type == 18) {
            switch(appearance) {
                case 1: return new Texture("walls1.png");
                case 2: return new Texture("walls2.png");
                default: return new Texture("walls1.png");
            }
        }
        if (type == 19) {
            switch(appearance) {
                case 1: return new Texture("deco/pool21.png");
                case 2: return new Texture("deco/pool22.png");
                default: return new Texture("deco/pool21.png");
            }
        }
        if (type == 20) {
            switch(appearance) {
                case 1: return new Texture("skills/bike11.png");
                case 2: return new Texture("skills/bike12.png");
                default: return new Texture("skills/bike11.png");
            }
        }
        if (type == 21) {
            switch(appearance) {
                case 1: return new Texture("skills/sewing11.png");
                case 2: return new Texture("skills/sewing12.png");
                default: return new Texture("skills/sewing11.png");
            }
        }
        if (type == 22) {
            switch(appearance) {
                case 1: return new Texture("skills/treadmill11.png");
                case 2: return new Texture("skills/treadmill12.png");
                default: return new Texture("skills/treadmill11.png");
            }
        }
        if (type == 23) {
            switch(appearance) {
                case 1: return new Texture("office/officecorner11.png");
                case 2: return new Texture("office/officecorner12.png");
                case 3: return new Texture("office/drawers11.png");
                case 4: return new Texture("office/drawers12.png");
                case 5: return new Texture("office/officecorner22.png");
                case 6: return new Texture("office/officecorner22.png");
                case 7: return new Texture("office/printer11.png");
                case 8: return new Texture("office/printer12.png");
                default: return new Texture("office/officecorner11.png");
            }
        }
        if (type == 24) {
            switch(appearance) {
                case 1: return new Texture("science/sciencecorner11.png");
                case 2: return new Texture("science/sciencecorner12.png");
                case 3: return new Texture("science/sciencecorner21.png");
                case 4: return new Texture("science/sciencecorner22.png");
                default: return new Texture("science/sciencecorner11.png");
            }
        }
        if (type == 25) {
            switch(appearance) {
                case 1: return new Texture("tvstation/tvstation11.png");
                case 2: return new Texture("tvstation/tvstation12.png");
                default: return new Texture("tvstation/tvstation11.png");
            }
        }
        if (type == 30) {
            switch(appearance) {
                case 1: return new Texture("tvstation/greenscreen11.png");
                case 2: return new Texture("tvstation/greenscreen12.png");
                case 3: return new Texture("tvstation/greenscreen13.png");
                case 4: return new Texture("tvstation/greenscreen14.png");
                default: return new Texture("tvstation/greenscreen11.png");
            }
        }
        if (type == 31) {
            switch(appearance) {
                case 1: return new Texture("tvstation/newsstand11.png");
                case 2: return new Texture("tvstation/newsstand12.png");
                default: return new Texture("tvstation/newsstand11.png");
            }
        }
        if (type == 32) {
            switch(appearance) {
                case 1: return new Texture("tvstation/camera11.png");
                case 2: return new Texture("tvstation/camera12.png");
                default: return new Texture("tvstation/camera11.png");
            }
        }
        if (type == 33) {
            switch(appearance) {
                case 1: return new Texture("tvstation/instrumentroom11.png");
                case 2: return new Texture("tvstation/instrumentroom12.png");
                default: return new Texture("tvstation/instrumentroom11.png");
            }
        }
        if (type == 34)
            return new Texture("deco/statue11.png");
        if (type == 35) {
            switch(appearance) {
                case 1: return new Texture("deco/dresser11.png");
                case 2: return new Texture("deco/dresser12.png");
                case 3: return new Texture("deco/dresser21.png");
                case 4: return new Texture("deco/dresser22.png");
                default: return new Texture("deco/dresser11.png");
            }
        }
        return new Texture("ui/null.png");
    }

    int whatTileSizeX(int type) { //★★★★★ 26.03.2020
        if (type == 4 || type == 11)
            return 2;
        if (type == 2)
            return 3;
        return 1;
    }

    int whatTileSizeY(int type) {
        return 1;
    }

    static int rnd(int i) {
        long sec = System.currentTimeMillis();
        sec = sec + random(sec % 10000);
        long j = sec % i;
        return (int) j + 1;
    }

    static int count(int type) {
        //подсчитывает количество предметов мебели определнного типа в одной комнате
        int count = 0;
        for (ObjectTest j : objectsTest) {
            int k = j.getType();
            if (k == type) {
                count += 1;
            }
        }
        return count;
    }

    static int count(ArrayList<ObjectTest> objectsTest, int type) {
        //подсчитывает количество предметов мебели определнного типа в одной комнате
        int count = 0;
        for (int i=0; i<objectsTest.size(); i++) {
            ObjectTest j = objectsTest.get(i);
            int k = j.getType();
            if (k == type) {count += 1;}
        }
        return count;
    }

    static int numberM(int count0, int type) {
        int number0 = -1;
        int e = -1;

        for (ObjectTest j : objectsTest) {
            if (j.getType() == type) {
                e += 1;
                if (e == count0) {
                    number0 = j.getNum0() - 1;
                    break;
                }
            }
        }
        return number0;
    }

    public static int numberM(ArrayList<ObjectTest> objectsTest, int count0, int type) {
        int number0 = 0;
        int e = 0;

        for (int i = 1; i < objectsTest.size(); i++) {
            ObjectTest j = objectsTest.get(i);
            if(j.getType() == type)
            {
                e += 1;
                if(e == count0) {
                    j = objectsTest.get(i-1);
                    number0 = j.getNum0();
                    break;
                }
            }
        }
        return number0;
    }

    public static int numberM(int type) {
        int number0 = 0;
        int e = 0;
        int count0 = rnd(count(type));

        for (int i = 1; i < objectsTest.size(); i++) {
            ObjectTest j = objectsTest.get(i);
            if(j.getType() == type)
            {
                e += 1;
                if(e == count0) {
                    j = objectsTest.get(i-1);
                    number0 = j.getNum0();
                    break;
                }
            }
        }
        return number0;
    }

    static int number(int count0, int type) {
        int number = 0;
        //count0 -= 1;
        for (int e = 0; e < count0; e++) {
            for (int i = 0; i < objectsTest.size(); i++) {
                ObjectTest j = objectsTest.get(i);
                int k = j.getType();
                if (k == type) {
                    e += 1;
                    number = j.getNum0();
                }
                if (e == count0 - 1) {
                    number = j.getNum0();
                    break;
                }
            }
        }
        return number;
    }



    public class Card extends Actor{
        //1-Indi, 2-concept, 3-ObjectTest
        public Card() {

        }
        private int type, ox, oy;
        private String name;
        Texture texture = new Texture("ui/indiPreview.png");
        private TextureRegion region;

        public Card(int type, String name, int ox, int oy) {
            this.type = type;
            this.name = name;
            this.ox = ox;
            this.oy = oy;
            region = new TextureRegion(texture);
            setBounds(region.getRegionX(), region.getRegionY(),
                    region.getRegionWidth(), region.getRegionHeight());
            addListener(new InputListener() {
                public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                    clickMode = 1;
                    return true;
                }
            });
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public void listen() {
            type = 0;
            name = " ";
            if (card == 0) {
                scrollPane.setVisible(true); //doesn't use
                outerTableNeeds.setVisible(false);
            }
            //Gdx.app.log("listen", "listen");
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public void setName(String name) {
            this.name = name;
        }

        public void update() {        }

        public void draw(Batch batch, float alpha) {
            batch.draw(texture, ox, oy);
            fontOswald.setColor(Color.WHITE);
            fontOswald.draw(batch, name.toUpperCase(), ox+55, oy+40);
        }

        public void act(float delta) {
            setBounds(ox - 20, oy - 20, texture.getWidth() + 40, texture.getHeight() + 35);
            //setBounds(0,0,1000,1000);
        }
    }

    public static class Action {
        public enum Levels {Action, ContAction, ScenAction, IndiAction};
        Levels level;
        ArrayList<IndiActor> indis;
        int etape, floor, action, targetX, targetY;

        public Action() {}

        public Action(ArrayList<IndiActor> indis, int etape, int action, int targetX, int targetY, int floor) {
            level = Levels.IndiAction;
            this.indis = indis;
            this.etape = etape;
            this.floor = floor;
            this.action = action;
            this.targetX = targetX;
            this.targetY = targetY;
        }

        public Action(boolean isCont, int etape, int action, int targetX, int targetY, int floor) {
            level = isCont? Levels.ContAction: Levels.ScenAction;
            this.etape = etape;
            this.floor = floor;
            this.action = action;
            this.targetX = targetX;
            this.targetY = targetY;
        }

        public Action(int action, int targetX, int targetY, int floor) {
            level = Levels.Action;
            this.floor = floor;
            this.action = action;
            this.targetX = targetX;
            this.targetY = targetY;
        }
    }

    public static class SocialAct {
        private int action;
        private ArrayList<String> parties = new ArrayList<String>();
        private int amount = 0;
        private boolean popular = false;
        private ArrayList<String> members = new ArrayList<String>();
        int indiNumEtt;
        int indiNumTva;
        //private Set<String> members = new HashSet<String>();

        public SocialAct() {
        }

        /*public SocialAct(int action, ArrayList<String> members) {
            this.action = action;
            this.members = members;
        }*/

        public SocialAct(int action) {
            this.action = action;
        }

        public int getAction() {
            return action;
        }

        public void setAction(int action) {
            this.action = action;
        }

        public ArrayList<String> getParties() {
            return parties;
        }

        public void setParties(ArrayList<String> parties) {
            this.parties = parties;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public boolean isPopular() {
            return popular;
        }

        public void setPopular(boolean popular) {
            this.popular = popular;
        }

        public ArrayList<String> getMembers() {
            return members;
        }

        public void setMembers(ArrayList<String> members) {
            this.members = members;
        }
    }

    public static class Education {
        private int type; // 1-законченная школа, 2-колледж 3-университет
        private Addition.Speciality speciality;
        private Addition.University university;

        public Education() {}

        public Education(int type, Addition.University university, Addition.Speciality specialty) {
            this.type = type;
            this.university = university;
            this.speciality = specialty;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public Addition.Speciality getSpeciality() {
            return speciality;
        }

        public void setSpeciality(Addition.Speciality specialty) {
            this.speciality = specialty;
        }

        public Addition.University getUniversity() {
            return university;
        }

        public void setUniversity(Addition.University university) {
            this.university = university;
        }
    }

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

    public static class InterestsArray {
        public InterestsArray() {
        }
        private int politics;
        private int economics;
        private int health;
        private int crimes;
        private int fun; //fashion
        private int culture;
        private int food;
        private int fashion; //science
        private int sport;
        private int travel;
        private int technics;
        private int work;
        private int animals;
        private int books;
        private int films;
        private int music;
        private int history;
        private int mystic;
        private int autos;
        private int games;
        private int design;
        private int photos;

        public InterestsArray(int politics, int economics, int health, int crimes, int fun, int culture, int food, int fashion, int sport, int travel, int technics, int work, int animals, int books, int films, int music, int history, int mystic, int autos, int games, int design, int photos) {
            this.politics = politics;
            this.economics = economics;
            this.health = health;
            this.crimes = crimes;
            this.fun = fun;
            this.culture = culture;
            this.food = food;
            this.fashion = fashion;
            this.sport = sport;
            this.travel = travel;
            this.technics = technics;
            this.work = work;
            this.animals = animals;
            this.books = books;
            this.films = films;
            this.music = music;
            this.history = history;
            this.mystic = mystic;
            this.autos = autos;
            this.games = games;
            this.design = design;
            this.photos = photos;
        }

        public int getAutos() {
            return autos;
        }

        public void setAutos(int autos) {
            this.autos = autos;
        }

        public int getGames() {
            return games;
        }

        public void setGames(int games) {
            this.games = games;
        }

        public int getDesign() {
            return design;
        }

        public void setDesign(int design) {
            this.design = design;
        }

        public int getPhotos() {
            return photos;
        }

        public void setPhotos(int photos) {
            this.photos = photos;
        }

        public int getPolitics() {
            return politics;
        }

        public void setPolitics(int politics) {
            this.politics = politics;
        }

        public int getEconomics() {
            return economics;
        }

        public void setEconomics(int economics) {
            this.economics = economics;
        }

        public int getHealth() {
            return health;
        }

        public void setHealth(int health) {
            this.health = health;
        }

        public int getCrimes() {
            return crimes;
        }

        public void setCrimes(int crimes) {
            this.crimes = crimes;
        }

        public int getFun() {
            return fun;
        }

        public void setFun(int fun) {
            this.fun = fun;
        }

        public int getCulture() {
            return culture;
        }

        public void setCulture(int culture) {
            this.culture = culture;
        }

        public int getFood() {
            return food;
        }

        public void setFood(int food) {
            this.food = food;
        }

        public int getFashion() {
            return fashion;
        }

        public void setFashion(int fashion) {
            this.fashion = fashion;
        }

        public int getSport() {
            return sport;
        }

        public void setSport(int sport) {
            this.sport = sport;
        }

        public int getTravel() {
            return travel;
        }

        public void setTravel(int travel) {
            this.travel = travel;
        }

        public int getTechnics() {
            return technics;
        }

        public void setTechnics(int technics) {
            this.technics = technics;
        }

        public int getWork() {
            return work;
        }

        public void setWork(int work) {
            this.work = work;
        }

        public int getAnimals() {
            return animals;
        }

        public void setAnimals(int animals) {
            this.animals = animals;
        }

        public int getBooks() {
            return books;
        }

        public void setBooks(int books) {
            this.books = books;
        }

        public int getFilms() {
            return films;
        }

        public void setFilms(int films) {
            this.films = films;
        }

        public int getMusic() {
            return music;
        }

        public void setMusic(int music) {
            this.music = music;
        }

        public int getHistory() {
            return history;
        }

        public void setHistory(int history) {
            this.history = history;
        }

        public int getMystic() {
            return mystic;
        }

        public void setMystic(int mystic) {
            this.mystic = mystic;
        }
    }

    public static class NeedsArray {
        public NeedsArray() {
        }

        private int aesthetics;
        private int bl;
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

        public NeedsArray(int hunger, int bl, int hygiene, int energy, int environment, int protection, int social, int love, int success, int shopping, int education, int fun, int aesthetics, int power) {
            this.hunger = hunger;
            this.bl = bl;
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

        public int getBl() {
            return this.bl;
        }

        public void setBl(int bl) {
            this.bl = bl;
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

    public static class Dish {
        public String name;
        public int taste; // количество пунктов сытости
        public int diff; //количество продуктов, которые будут уходить на приготовление

        public Dish() {}

        public Dish(String name, int taste, int diff) {
            this.name = name;
            this.taste = taste;
            this.diff = diff;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getTaste() {
            return taste;
        }

        public void setTaste(int taste) {
            this.taste = taste;
        }

        public int getDiff() {
            return diff;
        }

        public void setDiff(int diff) {
            this.diff = diff;
        }
    }

    public static class Relation {
        //УРОВНИ ЗНАКОМСТВА: 0-НЕЗНАКОМЫЙ 1-ЗНАКОМЫЙ 2-ДРУГ1 3-ДРУГ2 4-ДРУГ3 5-ДЕЛОВОЙ ПАРТНЕР 6-ВЛЮБЛ 7-ВЗАИМ ЛЮБОВЬ 8-ФРЕНДЗОНА
        public enum Levels {Stranger, Acquaintance, Friend, CloseFriend, BestFriend, Dislike, /*Enemy, Ex, ExSpouse,*/ Friendzone, Flirt, Lover, LoveOfLife, Spouse, Partner, /*Colleague, RightHand, NastyColleague, PosChild, NeuChild, NegChild, PosRelative, NeuRelative, NegRelative, ForgottenRelative*/};
        private Levels level;
        private int indiNum;

        public Relation() {
        }

        public Relation(int indiNum, int level) {
            this.indiNum = indiNum;
            switch (level) {
                case -1: this.level = Levels.Dislike;
                case 0: this.level = Levels.Stranger;
                case 1: this.level = Levels.Acquaintance;
                case 2: this.level = Levels.Friend;
                case 3: this.level = Levels.CloseFriend;
                case 4: this.level = Levels.BestFriend;
                case 5: this.level = Levels.Partner;
                case 6: this.level = Levels.Flirt;
                case 7: this.level = Levels.Lover;
                case 8: this.level = Levels.Friendzone;
                default: this.level = Levels.Stranger;
            }
        }

        public Relation(int indiNum, Levels level) {
            this.indiNum = indiNum;
            this.level = level;
        }

        public int getIndiNum() {
            return indiNum;
        }

        public void setIndiNum(int indiNum) {
            this.indiNum = indiNum;
        }

        public Levels getLevel() {
            return level;
        }

        public void setLevel(Levels level) {
            this.level = level;
        }
    }

    public static class Scenario {
        int code;
        private int st; //стадия
        private String str;

        public Scenario() {
        }

        public Scenario(int code) {
            this.code = code;
            st = 1;
            str = "";
        }

        public Scenario(int code, int stage) {
            this.code = code;
            this.st = stage;
            str = "";
        }

        public Scenario(int code, int stage, String str) {
            this.code = code;
            this.st = stage;
            this.str = str;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public int getSt() {
            return st;
        }

        public void setSt(int stage) {
            this.st = stage;
        }

        public String getStr() {
            return str;
        }

        public void setStr(String str) {
            this.str = str;
        }
    }

    public static class SystemScenario extends Scenario {
        private int systemNum;
        private ArrayList<Integer> startHours;
        private ArrayList<Integer> durationHours;

        public SystemScenario(int code, int stage, int systemNum, String str, ArrayList<Integer> startHours, ArrayList<Integer> durationHours) {
            super(code, stage, str);
            this.systemNum = systemNum;
            this.startHours = startHours;
            this.durationHours = durationHours;
        }
    }

    public static class Box {
        private ArrayList<Indi> indisTest;
        private ArrayList<ObjectTest> objectsTest;
        int roomsCount = 1;
        enum Property {Private, CityHall, Hospital, School, University, FireStation, Police, Laboratory, Theatre, Museum, Stadium, Airport, Office, Business, Library, Club, Factory, Channel};
        private HashMap<String, Object> mainFunc = new HashMap<String, Object>();
        private Property property;
        private transient boolean water = true, electricity = true, cellular = true, wifi = true;
        private transient float temperature = 22;
        private int boxX, boxY, boxZ, walls, floor;
        private int foodRemain;
        private int min, hh, dd, mm, yyyy;
        private String description;

        public Box() {
            this.property = Property.Office;
        }

        public Box(ArrayList<Indi> indisTest, ArrayList<ObjectTest> objectsTest, int boxX, int boxY, int boxZ, int walls, int floor, int foodRemain, int min, int hh, int dd, int mm, int yyyy, String description) {
            this.indisTest = indisTest;
            this.objectsTest = objectsTest;
            this.boxX = boxX;
            this.boxY = boxY;
            this.boxZ = boxZ;
            this.walls = walls;
            this.floor = floor;
            this.foodRemain = foodRemain;
            this.min = min;
            this.hh = hh;
            this.dd = dd;
            this.mm = mm;
            this.yyyy = yyyy;
            this.description = description;
            this.property = Private;
        }

        public Box(ArrayList<Indi> indisTest, ArrayList<ObjectTest> objectsTest, int boxX, int boxY, int boxZ, int walls, int floor, int foodRemain, int min, int hh, int dd, int mm, int yyyy, String description, Property prop, HashMap<String, Object> map) {
            this.indisTest = indisTest;
            this.objectsTest = objectsTest;
            this.boxX = boxX;
            this.boxY = boxY;
            this.boxZ = boxZ;
            this.walls = walls;
            this.floor = floor;
            this.foodRemain = foodRemain;
            this.min = min;
            this.hh = hh;
            this.dd = dd;
            this.mm = mm;
            this.yyyy = yyyy;
            this.description = description;
            this.property = prop;
            mainFunc = map;
        }

        public Box(int boxX, int boxY, int boxZ) {
            indisTest = new ArrayList<Indi>();
            objectsTest = new ArrayList<ObjectTest>();
            this.boxX = boxX;
            this.boxY = boxY;
            this.boxZ = boxZ;
            foodRemain = 54;
            min = 0; hh = 12; dd = 1; mm = 5; yyyy = 2015; description = "";
            property = Property.Office;
        }

        public ArrayList<Indi> getIndisTest() {
            return indisTest;
        }

        public ArrayList<ObjectTest> getObjectsTest() {
            return objectsTest;
        }

        public void setObjectsTest(ArrayList<ObjectTest> objectsTest) {
            this.objectsTest = objectsTest;
        }

        public void setIndisTest(ArrayList<Indi> indisTest) {
            this.indisTest = indisTest;
        }

        public int getBoxX() {
            return boxX;
        }

        public void setBoxX(int boxX) {
            this.boxX = boxX;
        }

        public int getBoxY() {
            return boxY;
        }

        public void setBoxY(int boxY) {
            this.boxY = boxY;
        }

        public int getBoxZ() {
            return boxZ;
        }

        public void setBoxZ(int boxZ) {
            this.boxZ = boxZ;
        }

        public int getWalls() {
            return walls;
        }

        public void setWalls(int walls) {
            this.walls = walls;
        }

        public int getFloor() {
            return floor;
        }

        public void setFloor(int floor) {
            this.floor = floor;
        }

        public int getFoodRemain() {
            return foodRemain;
        }

        public void setFoodRemain(int foodRemain) {
            this.foodRemain = foodRemain;
        }

        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }

        public int getHh() {
            return hh;
        }

        public void setHh(int hh) {
            this.hh = hh;
        }

        public int getDd() {
            return dd;
        }

        public void setDd(int dd) {
            this.dd = dd;
        }

        public int getMm() {
            return mm;
        }

        public void setMm(int mm) {
            this.mm = mm;
        }

        public int getYyyy() {
            return yyyy;
        }

        public void setYyyy(int yyyy) {
            this.yyyy = yyyy;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public boolean isWater() {
            return water;
        }

        public void setWater(boolean water) {
            this.water = water;
        }

        public boolean isElectricity() {
            return electricity;
        }

        public void setElectricity(boolean electricity) {
            this.electricity = electricity;
        }

        public boolean isCellular() {
            return cellular;
        }

        public void setCellular(boolean cellular) {
            this.cellular = cellular;
        }

        public boolean isWifi() {
            return wifi;
        }

        public void setWifi(boolean wifi) {
            this.wifi = wifi;
        }

        public float getTemperature() {
            return temperature;
        }

        public void setTemperature(float temperature) {
            this.temperature = temperature;
        }

        public HashMap<String, Object> getMainFunc() {
            return mainFunc;
        }

        public void setMainFunc(HashMap<String, Object> mainFunc) {
            this.mainFunc = mainFunc;
        }

        public Property getProperty() {
            return property;
        }

        public void setProperty(Property property) {
            this.property = property;
        }
    }



    public static class Society {
        public Society() {
        }

        private ArrayList<Box> boxes;
        private ArrayList<Init> inits;
        private ArrayList<Addition.Job> jobs;
        private String name;
        private ArrayList<SocialSystem> socialSystems = new ArrayList<SocialSystem>();
        private int sizeX;
        private int sizeY;

        public ArrayList<Box> getBoxes() {
            return boxes;
        }

        public void setBoxes(ArrayList<Box> boxes) {
            this.boxes = boxes;
        }

        public ArrayList<Init> getInits() {
            return inits;
        }

        public void setInits(ArrayList<Init> inits) {
            this.inits = inits;
        }

        public ArrayList<Job> getJobs() {
            return jobs;
        }

        public void setJobs(ArrayList<Job> jobs) {
            this.jobs = jobs;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSizeX() {
            return sizeX;
        }

        public void setSizeX(int sizeX) {
            this.sizeX = sizeX;
        }

        public int getSizeY() {
            return sizeY;
        }

        public void setSizeY(int sizeY) {
            this.sizeY = sizeY;
        }

        public ArrayList<Indi> getIndisTest(int box) {
            return society.getBoxes().get(box).getIndisTest();
        }

        public ArrayList<ObjectTest> getObjectsTest(int box) {
            return society.getBoxes().get(box).getObjectsTest();
        }

        public ArrayList<SocialSystem> getSocialSystems() {
            return socialSystems;
        }

        public void setSocialSystems(ArrayList<SocialSystem> socialSystems) {
            this.socialSystems = socialSystems;
        }

        public Indi getIndi(int box, int obs) {
            /** Вызывает indi по номеру в indisTest*/
            return society.getBoxes().get(box).getIndisTest().get(obs);
        }

        public Indi getIndiGlobal(int obs) {
            /** Вызывает indi по номеру в globalNum*/
            for (int i = 0; i < society.getBoxes().get(0).getIndisTest().size(); i++) {
                if (society.getBoxes().get(0).getIndisTest().get(i).globalNum == obs) {
                    return society.getBoxes().get(0).getIndisTest().get(i);
                }
            }
            return society.getBoxes().get(0).getIndisTest().get(0);
        }

        public int getIndiNumByGlobal(int obs) {
            /** Выдает indiNum по номеру в globalNum*/
            for (int i = 0; i < society.getBoxes().get(0).getIndisTest().size(); i++) {
                if (society.getBoxes().get(0).getIndisTest().get(i).globalNum == obs) {
                    return society.getBoxes().get(0).getIndisTest().get(i).myNum;
                }
            }
            return 0;
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
        private int imagination; //воображение
        private int insight; //сообразительность
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

    public static IndiActor getIndiActor(int obs) {
        /** Вызывает indiActor по номеру в indis*/
        return indis.get(obs);
    }

    public class IndiActor extends Actor {
        transient ArrayList<Action> actions = new ArrayList<Action>();
        transient HashMap<String, Boolean> actNeeds = new HashMap<String, Boolean>();
        int action;
        int contAction;
        transient float actorX;
        transient float actorY;
        boolean alive = true;
        boolean isTalking = false;
        transient int waiting;
        private int age;
        private int appearance;
        private int gender;
        public transient int marker = 0;
        public transient int markerType = 0;
        public transient String markerString = "";
        transient String markerStringType = "";
        int myNum;
        private String name;
        transient String status = "";
        private transient String entropy = "";
        transient int talking;
        public transient boolean started = true;
        private String surname;
        transient Texture texture = new Texture(Gdx.files.internal("indi1.png"));
        transient Texture cloudRight = new Texture(Gdx.files.internal("talks/talkcloud2.png"));
        transient Texture cloudLeft = new Texture(Gdx.files.internal("talks/talkcloud1.png"));
        transient Texture theme = new Texture(Gdx.files.internal("ui/null.png"));
        transient Texture subtexture = new Texture(Gdx.files.internal("ui/null.png"));
        private int wealth;
        transient float targetX = 300;
        transient float targetY = 300;
        transient long delay;
        TextureRegion region;

        public IndiActor() {
            texture = new Texture(Gdx.files.internal("ui/null.png"));
            name = "";
            surname = "";
        }

        public IndiActor(String name, final String surname, int gender, int age, int wealth, int appearance, float actorX, float actorY, final int myNum) {
            indisN++;
            contAction = 0;
            markerStringType = "";
            this.name = name;
            this.surname = surname;
            this.gender = gender;
            this.age = age;
            this.wealth = wealth;
            this.appearance = appearance;
            this.actorX = actorX;
            this.actorY = actorY;
            this.myNum = myNum;
            action = 0;
            waiting = 0;
            talking = 0;
            updateAppearance(1, HoldObject.None);
            addListener(new InputListener() {
                public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                    indiObs = myNum;
                    cardEtt.texture = new Texture("ui/indiPreview.png");
                    holdCard();
                    outerTableTva.setVisible(true);
                    updateRelations(myNum);
                    return true;
                }
            });
            actNeeds.put("hunger", false);
            actNeeds.put("bladder", false);
            actNeeds.put("energy", false);
            actNeeds.put("hygiene", false);
            actNeeds.put("aesthetics", false);
            actNeeds.put("fun", false);
            actNeeds.put("education", false);
            actNeeds.put("social", false);
            actNeeds.put("working", false);
            actNeeds.put("tvstation", false);
            actNeeds.put("economics", false);
            actNeeds.put("technics", false);
            actNeeds.put("music", false);
            actNeeds.put("books", false);
            actNeeds.put("film", false);
            actNeeds.put("shopping", false);
            actNeeds.put("love", false);
        }


        public int getAppearance() {
            return this.appearance;
        }

        public long getDelay() {
            return delay;
        }

        public void setDelay(long delay) {
            this.delay = delay;
        }

        public String getName() {
            return this.name;
        }

        public int getMyNum() {
            return myNum;
        }

        public void setmyNum(int myNum) {
            this.myNum = myNum;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getWealth() {
            return this.wealth;
        }

        public void setWealth(int wealth) {
            this.wealth = wealth;
        }

        public boolean isStarted() {
            return started;
        }

        public void setStarted(boolean started) {
            this.started = started;
        }

        public int getTalking() {
            return talking;
        }

        public void setTalking(int talking) {
            this.talking = talking;
        }

        public Texture getTexture() {
            return texture;
        }

        public void setTexture(Texture texture) {
            this.texture = texture;
        }

        public float getTargetX() {
            return targetX;
        }

        public void setTargetX(float targetX) {
            for (int q = 0; q<indis.size(); q++) {
                if (indis.get(q).getTargetX() == targetX) {
                    targetX += rnd(40) - 15;
                }
            }
            this.targetX = targetX;
        }

        public float getTargetY() {
            return targetY;
        }

        public void setTargetY(float targetY) {
            for (int q = 0; q<indis.size(); q++) {
                if (indis.get(q).getTargetY() == targetY) {
                    targetY += rnd(40) - 15;
                }
            }
            this.targetY = targetY;
        }

        public String getEntropy() {
            return entropy;
        }

        public void setEntropy(String entropy) {
            this.entropy = entropy;
        }

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public void holdCard() {
            if (card == 0) {
                nameField.setText(name);
                surnameField.setText(surname);
                cardEtt.setVisible(true);
                cardEtt.setName(name + " " + surname);
                labelHint.setVisible(true);
                scrollPane.setVisible(true);
                labelHintTva.setVisible(true);
                panelLeft.setVisible(true);
                labelHintTre.setVisible(true);
                labelHintFyra.setVisible(true);
            }
        }

        public void updateAppearance(int sub, HoldObject object) {
            String str = "indis/";
            str += gender == 1 ? "man" : "woman";
            str += appearance + "/";
            str += gender == 1 ? "man" : "woman";
            str += appearance;
            if (sub == 1)
                str += "stands";
            if (sub == 2)
                str += "holds";
            if (sub == 3)
                str += "sits";
            try {
                texture = new Texture(str+".png");
            }
            catch (Exception e) {
                texture = new Texture("indi1.png");
            }
            region = new TextureRegion(texture);

            if (object == HoldObject.Book)
                subtexture = new Texture("objects/book/book"+ (region.isFlipX() ? "1" : "2") + ".png");
            if (object == HoldObject.Extinguisher)
                subtexture = new Texture("objects/extinguisher/extinguisher"+ (region.isFlipX() ? "1" : "2") + ".png");
            if (object == HoldObject.Knife)
                subtexture = new Texture("objects/knife/knife"+ (region.isFlipX() ? "1" : "2") + ".png");
            if (object == HoldObject.Mixer)
                subtexture = new Texture("objects/mixer/mixer"+ (region.isFlipX() ? "1" : "2") + ".png");
            if (object == HoldObject.Pan)
                subtexture = new Texture("objects/pan/pan"+ (region.isFlipX() ? "1" : "2") + ".png");
            if (object == HoldObject.Spray)
                subtexture = new Texture("objects/spray/spray"+ (region.isFlipX() ? "1" : "2") + ".png");
            if (object == HoldObject.Phone1)
                subtexture = new Texture("objects/phone1/phone1"+ (region.isFlipX() ? "1" : "2") + ".png");
            if (object == HoldObject.Phone2)
                subtexture = new Texture("objects/phone2/phone2"+ (region.isFlipX() ? "1" : "2") + ".png");
            if (object == HoldObject.None)
                subtexture = new Texture("ui/null.png");

            name = society.getIndi(cb, myNum).getName();
            surname = society.getIndi(cb, myNum).getSurname();
            //myNum = society.getIndiTest(cb, myNum).getmyNum();
            setBounds(region.getRegionX(), region.getRegionY(), region.getRegionWidth(), region.getRegionHeight());
        }


        public void draw(Batch batch, float alpha) {
            if (!LOADING) {
                if (started) {
                    (actions.size() == 0 ? smallFontFranRed : smallFontFran).draw(batch, name + " " + surname  + " " + myNum + "/" + society.getIndi(cb, myNum).globalNum, actorX, actorY - 10);
                    if (indiObs+1 == myNum)
                        fontOswald.draw(batch, markerStringType, actorX+20, actorY+180);
                    else
                        fontOswald.draw(batch, markerStringType, actorX+20, actorY+160);
                    if (targetX < getX() && !region.isFlipX() /*&& !isTalking*/)
                        region.flip(true, false);
                    if (targetX > getX() && region.isFlipX() /*&& !isTalking*/)
                        region.flip(true, false);
                    else {
                        if (society.getIndi(cb, myNum).alive)
                            batch.draw(region, actorX, actorY, getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
                        else {
                            Color color = getColor();
                            batch.setColor(color.r, color.g, color.b, 0.4f);
                            batch.draw(region, actorX, actorY, getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
                            batch.setColor(color.r, color.g, color.b, 1f);
                        }
                    }
                    int n = 0;
                    try {
                        n = actions.get(0).action / 100;
                    }
                    catch (IndexOutOfBoundsException ignored) {}
                    if (region.isFlipX()) {
                        if (isTalking) {
                            batch.draw(cloudLeft, actorX + 26, actorY + 110);
                            batch.draw(theme, actorX + 38, actorY + 124, theme.getWidth() * 20 / theme.getHeight(), 20);
                        }
                        batch.draw(subtexture, actorX+12, gender == 0 ? actorY+58 : actorY+66);
                    }
                    else {
                        if (isTalking) {
                            batch.draw(cloudRight, actorX-16, actorY+110);
                            batch.draw(theme, actorX-8, actorY+124, theme.getWidth()*20/theme.getHeight(), 20);
                        }
                        batch.draw(subtexture, actorX+texture.getWidth()-12, gender == 0 ? actorY+54 : actorY+70);
                    }
                    if (marker != 0 && marker<180 && markerType == 0) {marker++;
                        smallFontFran.draw(batch, markerString, actorX, actorY-20+marker*1.5f);
                    }
                    else if (marker != 0 && marker<180 && markerType == 1) {marker++;
                        smallFontFran.draw(batch, markerString, actorX+15, actorY+130);
                    }
                    else {marker = 0; markerString = ""; markerType = 0;}
                    //smallFontFran.draw(batch, cloudStatus, actorX, actorY + 170);
                }
            }

            if (indiObs == myNum) {
                batch.draw(NIMBUS, actorX+6, actorY+124);
            }
        }



        public void act(float delta) {
            setBounds(actorX, actorY, texture.getWidth(), texture.getHeight());
            if (!LOADING && started && !society.getIndi(cb, myNum).alive) {
                if (!(touchMarker((int) actorX, (int) actorY))) {
                    actorY -= 1;
                    actorX -= 1;
                    setTargetX(actorX - rnd(25));
                    setTargetX(actorY - rnd(25));
                }
                if (actorX < targetX) {
                    actorX += 1;
                    if (!touchMarker((int) actorX, (int) actorY)) {
                        actorY -= 1;
                        actorX -= 1;
                        setTargetX(actorX - rnd(25));
                    }
                } else if (actorX > targetX) {
                    actorX -= 1;
                    if (!touchMarker((int) actorX, (int) actorY)) {
                        actorY += 1;
                        actorX -= 1;
                        setTargetX(actorX + rnd(25));
                    }
                } else if (actorX == targetX) {
                    actorX += 0;
                    if (!touchMarker((int) actorX, (int) actorY)) {
                        setTargetX(actorX + rnd(25));
                    }
                }/*else if (actorX > Gdx.graphics.getWidth()) {
                    actorX -= 1;
                }*/
                if (actorY < targetY) {
                    actorY += 1;
                    if (!touchMarker((int) actorX, (int) actorY)) {
                        actorY -= 1;
                        actorX += 1;
                        setTargetY(actorY - rnd(25));
                    }
                } else if (actorY > targetY) {
                    actorY -= 1;
                    if (!touchMarker((int) actorX, (int) actorY)) {
                        actorY += 1;
                        actorX += 1;
                        setTargetY(actorY + rnd(25));
                    }
                } else if (actorY == targetY) {
                    actorY += 0;
                    if (!touchMarker((int) actorX, (int) actorY)) {
                        setTargetY(actorY + rnd(25));
                    }
                }
                if (!alive) {
                    appearance = 0;
                    society.getIndi(cb, myNum).setAppearance(0);
                }
            }
            else if (!LOADING && started && society.getIndi(cb, myNum).alive) {
                if (!(touchMarker((int) actorX, (int) actorY))) {
                    actorY-=3;
                    actorX-=3;
                    setTargetX(actorX - rnd(25));
                    setTargetX(actorY - rnd(25));
                }
                if (actorX < targetX) {
                    actorX += 1;
                    if (!touchMarker((int) actorX, (int) actorY)) {
                        actorY-=2;
                        actorX-=4;
                        setTargetX(actorX - rnd(25));
                    }
                } else if (actorX > targetX) {
                    actorX -= 1;
                    if (!touchMarker((int) actorX, (int) actorY)) {
                        actorY+=2;
                        actorX-=4;
                        setTargetX(actorX + rnd(25));
                    }
                } else if (actorX == targetX) {
                    actorX += 0;
                    if (!touchMarker((int) actorX, (int) actorY)) {
                        setTargetX(actorX + rnd(25));
                    }
                }/*else if (actorX > Gdx.graphics.getWidth()) {
                    actorX -= 1;
                }*/
                if (actorY < targetY) {
                    actorY += 1;
                    if (!touchMarker((int) actorX, (int) actorY)) {
                        actorY-=4;
                        actorX+=2;
                        setTargetY(actorY - rnd(25));
                    }
                } else if (actorY > targetY) {
                    actorY -= 1;
                    if (!touchMarker((int) actorX, (int) actorY)) {
                        actorY+=4;
                        actorX+=2;
                        setTargetY(actorY + rnd(25));
                    }
                } else if (actorY == targetY) {
                    actorY += 0;
                    if (!touchMarker((int) actorX, (int) actorY)) {
                        setTargetY(actorY + rnd(25));
                    }
                }
                setBounds(actorX, actorY, texture.getWidth(), texture.getHeight());
                if (!alive) {
                    appearance = 0;
                    society.getIndi(cb, myNum).setAppearance(0);
                }




                //ДЕЙСТВИЯ РАССТАВЛЯЮТСЯ В ОБРАТНОМ ПОРЯДКЕ: САМЫЕ ПРИОРИТЕТНЫЕ В КОНЕЦ
                //ОБУЧЕНИЕ
                ArrayList<Integer> avail = new ArrayList<Integer>();
                if (society.getIndi(cb, myNum).getNeeds().get(0).getEducation() < society.getIndi(cb, myNum).getTalents().get(0).getMemory() * 0.7 && !(actNeeds.get("education") || actNeeds.get("music") || actNeeds.get("technics") || actNeeds.get("film"))) {
                    avail.add(18); //лекции в телефоне
                    if (society.getIndi(cb, myNum).getInterests().get(0).getBooks() > 40 && checkUnocc(11) != 0) {
                        avail.add(309);
                    }
                    if (society.getIndi(cb, myNum).getInterests().get(0).getFilms() > 40 && checkUnocc(5) != 0) {
                        avail.add(2901);
                    }
                }
                //ИНТЕРЕСЫ ПО ВООБРАЖЕНИЮ
                if (society.getIndi(cb, myNum).getNeeds().get(0).getEducation() < society.getIndi(cb, myNum).getTalents().get(0).getImagination() * 0.7 && !(actNeeds.get("music"))) {
                    //МУЗЫКА
                    if (society.getIndi(cb, myNum).getInterests().get(0).getMusic() > 40 && checkUnocc(10) != 0)
                        avail.add(3001);
                }
                //ИНТЕРЕСЫ ПО ЛОГИКЕ
                if (society.getIndi(cb, myNum).getNeeds().get(0).getEducation() < society.getIndi(cb, myNum).getTalents().get(0).getLogic() * 0.7 && !(actNeeds.get("technics"))) {
                    //ТЕХНИКА
                    if (society.getIndi(cb, myNum).getInterests().get(0).getTechnics() > 40 && checkUnocc(6) != 0 || checkUnocc(34) != 0) {
                        if (checkUnocc(6) != 0)
                            avail.add(2501);
                        if (checkUnocc(34) != 0)
                            avail.add(2502);
                    }
                }
                int ac;
                try {
                    ac = avail.get(rnd(avail.size()) - 1);
                } catch (ArithmeticException e) {
                    ac = 0;
                }
                switch (ac) {
                    case 18: {
                        actions.add(new Action(18, (int) actorX + 1, (int) actorY + 1, homez));
                        break;
                    }
                    case 309: {
                        int n = observe(11);
                        if (n != -1)
                            actions.add(0, new Action(309, objects.get(n).ox + 20, objects.get(n).oy - 2, homez));
                        break;
                    }
                    case 2501: {
                        int n = observe(6);
                        if (n != -1)
                            actions.add(0, new Action(2501, objects.get(n).ox, objects.get(n).oy - 50, homez));
                        break;
                    }
                    case 2502: {
                        int n = observe(34);
                        if (n != -1)
                            actions.add(0, new Action(2502, objects.get(n).ox + 10, objects.get(n).oy - 40, homez));
                        break;
                    }
                    case 2901: {
                        int n = reserve(myNum, 5);
                        if (n != -1)
                            actions.add(0, new Action(2901, objects.get(n).ox + 50, objects.get(n).oy - 140, homez));
                        break;
                    }
                    case 3001: {
                        int n = reserve(myNum, 10);
                        if (n != -1)
                            actions.add(0, new Action(3001, objects.get(n).ox, objects.get(n).oy - 50, homez));
                        break;
                    }
                    default: {
                    }
                }
                //ЭСТЕТИКА
                if (society.getIndi(cb, myNum).getNeeds().get(0).getAesthetics() < society.getIndi(cb, myNum).getTalents().get(0).getImagination() * 0.7 && !actNeeds.get("aesthetics") && checkUnocc(29) != 0) {
                    if (checkUnocc(29) != 0) {
                        int n = observe(29);
                        if (n != -1) {
                            actions.add(0, new Action(302, objects.get(n).ox+10, objects.get(n).oy-60, homez));
                        }
                    }
                    else if (society.getIndi(cb, myNum).getNeeds().get(0).getAesthetics() < 30 && !actNeeds.get("aesthetics")) {
                        actions.add(new Action(rnd(2) == 2 ? 12 : 14, (int)actorX+1, (int)actorY+1, homez));
                    }
                }
                /*else if (society.getIndiTest(cb, myNum).getNeeds().get(0).getAesthetics() < 50 &&
                        !actNeeds.get("aesthetics") && checkUnocc(29) == 0 && checkUnocc(7) == 0
                        && society.getBoxes().get(cb).getProperty() == Private
                        && getCheapestItem(7) < society.getIndiTest(cb, myNum).getWealth())
                {
                    actions.add(new Action(101, (int)actorX, (int)actorY, homez)); //покупка чего-то декоративного
                }*/
                //ПОКУПКИ
                if (society.getIndi(cb, myNum).getNeeds().get(0).getShopping() < society.getIndi(cb, myNum).getTalents().get(0).getCaution() * 0.5 && !actNeeds.get("shopping")) {
                    int n = observe(-2);
                    if (n != -1) {
                        if (society.getBoxes().get(cb).foodRemain < 5) {
                            actions.add(0, new Action(1202, objects.get(n).ox, objects.get(n).oy - 50, homez));
                        }
                        else {
                            actions.add(0, new Action((new ArrayList<Integer>(Arrays.asList(1201, 1203, 1204)).get(rnd(3) - 1)), objects.get(n).ox, objects.get(n).oy - 50, homez));
                        }
                    }
                }
                //УСПЕХ
                if (society.getIndi(cb, myNum).getNeeds().get(0).getSuccess() < society.getIndi(cb, myNum).getTalents().get(0).getInfluence() * 0.5 || (society.getIndi(cb, myNum).getScenarioNums().contains(1401) & society.getIndi(cb, myNum).getNeeds().get(0).getSuccess() < 0)) {
                    //ЭКОНОМИКА
                    if (society.getIndi(cb, myNum).getInterests().get(0).getEconomics() > 50 && !actNeeds.get("economics") && checkUnocc(6) != 0 && society.getIndi(cb, myNum).getWealth() > 1000) {
                        int n = reserve(myNum, 6);
                        if (n != -1) {
                            actions.add(0, new Action(1601, objects.get(n).ox, objects.get(n).oy-120, homez));
                        }
                    }
                    else if (society.getIndi(cb, myNum).getNeeds().get(0).getSuccess() < 20 && !actNeeds.get("social")) {
                        actions.add(new Action(rnd(2) == 2 ? 11 : 15, (int)actorX+1, (int)actorY+1, homez));
                    }
                }
                //ЛЮБОВЬ
                if ((society.getIndi(cb, myNum).getNeeds().get(0).getLove() < society.getIndi(cb, myNum).getTalents().get(0).getInfluence() * 0.7) && !actNeeds.get("love") && indis.size() > 1) {
                    ArrayList<Integer> lovers = new ArrayList<Integer>();
                    for (int i = 0; i < society.getIndi(cb, myNum).getRelations().size(); i++) {
                        int num = society.getIndi(cb, myNum).getRelations().get(i).getIndiNum()-1;
                        SocietyScreen.Relation.Levels level = society.getIndi(cb, myNum).getRelations().get(i).getLevel();
                        if (Arrays.asList(Relation.Levels.Lover, Relation.Levels.LoveOfLife, Relation.Levels.Flirt, Relation.Levels.Spouse).contains(level) && isAlive(num)) {
                            lovers.add(society.getIndi(cb, myNum).getRelations().get(i).indiNum);
                        }
                    }
                    if (lovers.size() != 0) {
                        int n = rnd(lovers.size()) - 1;
                        boolean b = false;
                        try {
                            b = Arrays.asList(2, 4, 7).contains(indis.get(n).actions.get(0).action / 100);
                        }
                        catch (IndexOutOfBoundsException e) {}
                        if (n != myNum & !b & (Math.abs(indis.get(n).delay - delay) <= 3000 || indis.get(n).delay == 0)) {
                            int nn = rnd(9) + 1301;
                            switch (nn) {
                                case 1302: {
                                    indis.get(n).actions.add(0, new Action(new ArrayList<IndiActor>(Arrays.asList(indis.get(myNum))), 1, 1302, (int) indis.get(n).actorX, (int) indis.get(n).actorY, homez));
                                    actions.add(0, new Action(new ArrayList<IndiActor>(Arrays.asList(indis.get(n))), 1, 1302, (int) indis.get(n).actorX + 30, (int) indis.get(n).actorY, homez));
                                    break;
                                }
                                case 1303: {
                                    indis.get(n).actions.add(0, new Action(new ArrayList<IndiActor>(Arrays.asList(indis.get(myNum))), 1, 1303, (int) indis.get(n).actorX, (int) indis.get(n).actorY, homez));
                                    actions.add(0, new Action(new ArrayList<IndiActor>(Arrays.asList(indis.get(n))), 1, 1303, (int) indis.get(n).actorX + 30, (int) indis.get(n).actorY, homez));
                                    break;
                                }
                                case 1304:
                                case 1305:
                                {
                                    indis.get(n).actions.add(0, new Action(new ArrayList<IndiActor>(Arrays.asList(indis.get(myNum))), 1, nn, (int) indis.get(n).actorX, (int) indis.get(n).actorY, homez));
                                    actions.add(0, new Action(new ArrayList<IndiActor>(Arrays.asList(indis.get(n))), 1, nn, (int) indis.get(n).actorX + 30, (int) indis.get(n).actorY, homez));
                                    break;
                                }
                                case 1306: {
                                    indis.get(n).actions.add(0, new Action(new ArrayList<IndiActor>(Arrays.asList(indis.get(myNum))), 1, 1306, (int) indis.get(n).actorX, (int) indis.get(n).actorY, homez));
                                    actions.add(0, new Action(new ArrayList<IndiActor>(Arrays.asList(indis.get(n))), 1, 1306, (int) indis.get(n).actorX + 30, (int) indis.get(n).actorY, homez));
                                    break;
                                }
                                case 1307: {
                                    indis.get(n).actions.add(0, new Action(new ArrayList<IndiActor>(Arrays.asList(indis.get(myNum))), 1, 1307, (int) indis.get(n).actorX, (int) indis.get(n).actorY, homez));
                                    actions.add(0, new Action(new ArrayList<IndiActor>(Arrays.asList(indis.get(n))), 1, 1307, (int) indis.get(n).actorX + 30, (int) indis.get(n).actorY, homez));
                                    society.getIndi(cb, myNum).setWealth(society.getIndi(cb, myNum).getWealth() - rnd(50));
                                    break;
                                }
                                case 1308: {
                                    if (rnd(100) < 101) {//сюда потом вставить отбор по уровню отношений, см. А:1308
                                        indis.get(n).actions.add(0, new Action(new ArrayList<IndiActor>(Arrays.asList(indis.get(myNum))), 1, 1308, (int) indis.get(n).actorX, (int) indis.get(n).actorY, homez));
                                        actions.add(0, new Action(new ArrayList<IndiActor>(Arrays.asList(indis.get(n))), 1, 1308, (int) indis.get(n).actorX + 10, (int) indis.get(n).actorY, homez));
                                    }
                                    break;
                                }
                                case 1309: {
                                    if (rnd(100) < 101) {//сюда потом вставить отбор по уровню отношений, см. А:1308
                                        indis.get(n).actions.add(0, new Action(new ArrayList<IndiActor>(Arrays.asList(indis.get(myNum))), 1, 1309, (int) indis.get(n).actorX, (int) indis.get(n).actorY, homez));
                                        actions.add(0, new Action(new ArrayList<IndiActor>(Arrays.asList(indis.get(n))), 1, 1309, (int) indis.get(n).actorX + 10, (int) indis.get(n).actorY, homez));
                                    }
                                    break;
                                }
                                case 1310: {
                                    if (checkUnocc(2) != 0) {
                                        int m = reserve(myNum, 2);
                                        if (m != -1) {
                                            indis.get(n).actions.add(0, new Action(new ArrayList<IndiActor>(Arrays.asList(indis.get(myNum))), 1, 1310, objects.get(m).ox+50, objects.get(m).oy+10, homez));
                                            actions.add(0, new Action(new ArrayList<IndiActor>(Arrays.asList(indis.get(n))), 1, 1310, objects.get(m).ox+50, objects.get(m).oy+10, homez));
                                        }
                                    }
                                }
                            }
                            turnOn(actions, myNum, true);
                            turnOn(actions, n, true);
                        }
                    }
                    else if (indis.size() > 1) {
                        int n = rnd(indis.size())-1;
                        boolean b = false;
                        try {
                            b = Arrays.asList(2, 4, 7).contains(indis.get(n).actions.get(0).action / 100);
                        }
                        catch (IndexOutOfBoundsException e) {}
                        if (n != myNum & (Math.abs(indis.get(n).delay - delay) <= 15000 || indis.get(n).delay == 0) & !b & isAlive(n)) {
                            indis.get(n).actions.add(0, new Action(new ArrayList<IndiActor>(Arrays.asList(indis.get(myNum))), 1, 1301, (int) indis.get(n).actorX, (int) indis.get(n).actorY, homez));
                            actions.add(0, new Action(new ArrayList<IndiActor>(Arrays.asList(indis.get(n))), 1, 1301, (int) indis.get(n).actorX+30, (int) indis.get(n).actorY, homez));
                        }
                    }
                }
                //ВЕСЕЛЬЕ
                if (society.getIndi(cb, myNum).getNeeds().get(0).getFun() < 50 && !actNeeds.get("fun") && (checkUnocc(6) != 0 || checkUnocc(5) != 0 || checkUnocc(19) != 0)) {
                    if (checkUnocc(6) != 0) {
                        int n = reserve(myNum, 6);
                        if (n != -1) {
                            actions.add(0, new Action(601, objects.get(n).ox, objects.get(n).oy-120, homez));
                        }
                    }
                    else if (checkUnocc(5) != 0) {
                        int n = reserve(myNum, 5);
                        if (n != -1) {
                            actions.add(0, new Action(602, objects.get(n).ox+50, objects.get(n).oy-140, homez));
                        }
                    }
                    else if (checkUnocc(19) != 0 & observe(19) != -1) {
                        int n = reserve(myNum, 19);
                        ArrayList<Integer> friends = new ArrayList<Integer>();
                        ArrayList<Integer> acq = new ArrayList<Integer>();
                        ArrayList<Integer> colleagues = new ArrayList<Integer>();
                        for (int i = 0; i < society.getIndi(cb, myNum).getRelations().size(); i++) {
                            int num = society.getIndi(cb, myNum).getRelations().get(i).getIndiNum()-1;
                            SocietyScreen.Relation.Levels level = society.getIndi(cb, myNum).getRelations().get(i).getLevel();
                            if (Arrays.asList(Relation.Levels.Friend, Relation.Levels.BestFriend, Relation.Levels.CloseFriend).contains(level) && isAlive(num)) {
                                friends.add(society.getIndi(cb, myNum).getRelations().get(i).indiNum);
                            }
                            /*else if (Arrays.asList(Relation.Levels.Colleague, Relation.Levels.Partner, Relation.Levels.RightHand).contains(level) && isAlive(num)) {
                                colleagues.add(society.getIndiTest(cb, myNum).getRelations().get(i).indiNum);
                            }*/
                            else if (Arrays.asList(Relation.Levels.Acquaintance).contains(level) && isAlive(num)) {
                                acq.add(society.getIndi(cb, myNum).getRelations().get(i).indiNum);
                            }
                            if (friends.size() != 0) {
                                int nn = friends.get(rnd(friends.size())-1);
                                actions.add(0, new Action(true, 0, 603, objects.get(n).ox+50, objects.get(n).oy-100, homez));
                                indis.get(nn).actions.add(0, new Action(true, 0, 603, objects.get(n).ox+50, objects.get(n).oy-100, homez));
                            }
                            if (colleagues.size() != 0) {
                                int nn = colleagues.get(rnd(friends.size())-1);
                                actions.add(0, new Action(true, 0, 603, objects.get(n).ox+50, objects.get(n).oy-100, homez));
                                indis.get(nn).actions.add(0, new Action(true, 0, 603, objects.get(n).ox+50, objects.get(n).oy-100, homez));
                            }
                            /*if (acq.size() != 0) {
                                int nn = acq.get(rnd(friends.size())-1);
                                actions.add(0, new Action(true, 0, 603, 5000, objects.get(n).ox+50, objects.get(n).oy-100, homez));
                                indis.get(nn).actions.add(0, new Action(true, 0, 603, 10000, objects.get(n).ox+50, objects.get(n).oy-100, homez));
                            }*/
                        }
                        //actions.add(0, new Action(603, 5000, objects.get(n).ox+50, objects.get(n).oy-100, homez));
                    }
                    else if (society.getIndi(cb, myNum).getNeeds().get(0).getFun() < 20 && !actNeeds.get("fun")) {
                        actions.add(new Action(rnd(2) == 2 ? 10 : rnd(2) == 2 ? 19 : 16, (int)actorX+1, (int)actorY+1, homez));
                    }
                }
                //ОБЩЕНИЕ
                if ((society.getIndi(cb, myNum).getNeeds().get(0).getSocial() < society.getIndi(cb, myNum).getTalents().get(0).getSpeech() * 0.7 || (society.getIndi(cb, myNum).getTalents().get(0).getSpeech() > 50 && rnd(2) == 1)
                        || (society.getIndi(cb, myNum).getScenarioNums().contains(1301) & society.getIndi(cb, myNum).getNeeds().get(0).getSocial() < 80))
                        && !actNeeds.get("social") && indis.size() > 1) {
                    ArrayList<Integer> friends = new ArrayList<Integer>();
                    ArrayList<Relation.Levels> friendsLevels = new ArrayList<Relation.Levels>();
                    ArrayList<Integer> lovers = new ArrayList<Integer>();
                    ArrayList<Relation.Levels> loversLevels = new ArrayList<Relation.Levels>();
                    ArrayList<Integer> relatives = new ArrayList<Integer>();
                    ArrayList<Integer> relativesKids = new ArrayList<Integer>();
                    ArrayList<Integer> colleagues = new ArrayList<Integer>();
                    for (int i = 0; i < society.getIndi(cb, myNum).getRelations().size(); i++) {
                        int num = society.getIndi(cb, myNum).getRelations().get(i).getIndiNum()-1;
                        SocietyScreen.Relation.Levels level = society.getIndi(cb, myNum).getRelations().get(i).getLevel();
                        if (Arrays.asList(Relation.Levels.Lover, Relation.Levels.LoveOfLife, Relation.Levels.Flirt, Relation.Levels.Spouse).contains(level) && isAlive(num)) {
                            lovers.add(society.getIndi(cb, myNum).getRelations().get(i).indiNum);
                            loversLevels.add(society.getIndi(cb, myNum).getRelations().get(i).level);
                        }
                        else if (Arrays.asList(Relation.Levels.Friend, Relation.Levels.BestFriend, Relation.Levels.CloseFriend).contains(level) && isAlive(num)) {
                            friends.add(society.getIndi(cb, myNum).getRelations().get(i).indiNum);
                            friendsLevels.add(society.getIndi(cb, myNum).getRelations().get(i).level);
                        }
                        /*else if (Arrays.asList(Relation.Levels.PosRelative, Relation.Levels.NeuRelative).contains(level) && isAlive(num)) {
                            relatives.add(society.getIndiTest(cb, myNum).getRelations().get(i).indiNum);
                        }
                        else if (Arrays.asList(Relation.Levels.Colleague, Relation.Levels.Partner, Relation.Levels.RightHand).contains(level) && isAlive(num)) {
                            colleagues.add(society.getIndiTest(cb, myNum).getRelations().get(i).indiNum);
                        }*/
                    }
                    if (lovers.size() != 0) {
                        int n = lovers.get(rnd(lovers.size()) - 1);
                        boolean b = false;
                        try {
                            b = Arrays.asList(2, 4, 7).contains(indis.get(n).actions.get(0).action / 100);
                        }
                        catch (IndexOutOfBoundsException e) {}
                        if (n != myNum & !b & (Math.abs(indis.get(n).delay - delay) <= 3000 || indis.get(n).delay == 0)) {
                            int nn = rnd(9) + 1301;
                            switch (nn) {
                                case 1302: {
                                    indis.get(n).actions.add(0, new Action(new ArrayList<IndiActor>(Arrays.asList(indis.get(myNum))), 1, 1302, (int) indis.get(n).actorX, (int) indis.get(n).actorY, homez));
                                    actions.add(0, new Action(new ArrayList<IndiActor>(Arrays.asList(indis.get(n))), 1, 1302, (int) indis.get(n).actorX + 30, (int) indis.get(n).actorY, homez));
                                    break;
                                }
                                case 1303: {
                                    indis.get(n).actions.add(0, new Action(new ArrayList<IndiActor>(Arrays.asList(indis.get(myNum))), 1, 1303, (int) indis.get(n).actorX, (int) indis.get(n).actorY, homez));
                                    actions.add(0, new Action(new ArrayList<IndiActor>(Arrays.asList(indis.get(n))), 1, 1303, (int) indis.get(n).actorX + 30, (int) indis.get(n).actorY, homez));
                                    break;
                                }
                                case 1304:
                                case 1305:
                                {
                                    indis.get(n).actions.add(0, new Action(new ArrayList<IndiActor>(Arrays.asList(indis.get(myNum))), 1, nn, (int) indis.get(n).actorX, (int) indis.get(n).actorY, homez));
                                    actions.add(0, new Action(new ArrayList<IndiActor>(Arrays.asList(indis.get(n))), 1, nn, (int) indis.get(n).actorX + 30, (int) indis.get(n).actorY, homez));
                                    break;
                                }
                                case 1306: {
                                    indis.get(n).actions.add(0, new Action(new ArrayList<IndiActor>(Arrays.asList(indis.get(myNum))), 1, 1306, (int) indis.get(n).actorX, (int) indis.get(n).actorY, homez));
                                    actions.add(0, new Action(new ArrayList<IndiActor>(Arrays.asList(indis.get(n))), 1, 1306, (int) indis.get(n).actorX + 30, (int) indis.get(n).actorY, homez));
                                    break;
                                }
                                case 1307: {
                                    indis.get(n).actions.add(0, new Action(new ArrayList<IndiActor>(Arrays.asList(indis.get(myNum))), 1, 1307, (int) indis.get(n).actorX, (int) indis.get(n).actorY, homez));
                                    actions.add(0, new Action(new ArrayList<IndiActor>(Arrays.asList(indis.get(n))), 1, 1307, (int) indis.get(n).actorX + 30, (int) indis.get(n).actorY, homez));
                                    society.getIndi(cb, myNum).setWealth(society.getIndi(cb, myNum).getWealth() - rnd(50));
                                    break;
                                }
                                case 1308: {
                                    if (rnd(100) < 101) {//сюда потом вставить отбор по уровню отношений, см. А:1308
                                        indis.get(n).actions.add(0, new Action(new ArrayList<IndiActor>(Arrays.asList(indis.get(myNum))), 1, 1308, (int) indis.get(n).actorX, (int) indis.get(n).actorY, homez));
                                        actions.add(0, new Action(new ArrayList<IndiActor>(Arrays.asList(indis.get(n))), 1, 1308, (int) indis.get(n).actorX + 10, (int) indis.get(n).actorY, homez));
                                    }
                                    break;
                                }
                                case 1309: {
                                    if (rnd(100) < 101) {//сюда потом вставить отбор по уровню отношений, см. А:1308
                                        indis.get(n).actions.add(0, new Action(new ArrayList<IndiActor>(Arrays.asList(indis.get(myNum))), 1, 1309, (int) indis.get(n).actorX, (int) indis.get(n).actorY, homez));
                                        actions.add(0, new Action(new ArrayList<IndiActor>(Arrays.asList(indis.get(n))), 1, 1309, (int) indis.get(n).actorX + 10, (int) indis.get(n).actorY, homez));
                                    }
                                    break;
                                }
                                case 1310: {
                                    if (checkUnocc(2) != 0) {
                                        int m = reserve(myNum, 2);
                                        if (m != -1) {
                                            indis.get(n).actions.add(0, new Action(new ArrayList<IndiActor>(Arrays.asList(indis.get(myNum))), 1, 1310, objects.get(m).ox+50, objects.get(m).oy+10, homez));
                                            actions.add(0, new Action(new ArrayList<IndiActor>(Arrays.asList(indis.get(n))), 1, 1310, objects.get(m).ox+50, objects.get(m).oy+10, homez));
                                        }
                                    }
                                }
                            }
                            turnOn(actions, myNum, true);
                            turnOn(actions, n, true);
                        }
                    }
                    else if (friends.size() != 0) {
                        int number = rnd(friends.size()) - 1;
                        int n = friends.get(number)-1;
                        Relation.Levels level = friendsLevels.get(number);
                        boolean b = false;
                        try {
                            b = Arrays.asList(2, 4, 7).contains(indis.get(n).actions.get(0).action / 100);
                        }
                        catch (IndexOutOfBoundsException e) {}
                        ArrayList<Integer> available = new ArrayList<Integer>();
                        available.add(1315);
                        available.add(1316);
                        available.add(1321);
                        if (level.equals(Relation.Levels.CloseFriend) || level.equals(Relation.Levels.BestFriend)) {
                            available.add(1317);
                            available.add(1320);
                        }
                        if (level.equals(Relation.Levels.BestFriend)) {
                            available.add(1318);
                            available.add(1319);
                        }

                        int act;
                        try {
                            act = available.get(rnd(available.size())-1);
                        }
                        catch (ArithmeticException e) {
                            act = 0;
                        }
                        if (n != myNum & !b & (Math.abs(indis.get(n).delay - delay) <= 3000 || indis.get(n).delay == 0)) {
                            switch (act) {
                                case 1315: {
                                    indis.get(n).actions.add(0, new Action(new ArrayList<IndiActor>(Arrays.asList(indis.get(myNum))), 1, 1315, (int) indis.get(n).actorX, (int) indis.get(n).actorY, homez));
                                    actions.add(0, new Action(new ArrayList<IndiActor>(Arrays.asList(indis.get(n))), 1, 1315, (int) indis.get(n).actorX + 30, (int) indis.get(n).actorY, homez));
                                    break;
                                }
                                case 1316: {
                                    indis.get(n).actions.add(0, new Action(new ArrayList<IndiActor>(Arrays.asList(indis.get(myNum))), 1, 1316, (int) indis.get(n).actorX, (int) indis.get(n).actorY, homez));
                                    actions.add(0, new Action(new ArrayList<IndiActor>(Arrays.asList(indis.get(n))), 1, 1316, (int) indis.get(n).actorX + 30, (int) indis.get(n).actorY, homez));
                                    break;
                                }
                                case 1317: {
                                    indis.get(n).actions.add(0, new Action(new ArrayList<IndiActor>(Arrays.asList(indis.get(myNum))), 1, 1317, (int) indis.get(n).actorX, (int) indis.get(n).actorY, homez));
                                    actions.add(0, new Action(new ArrayList<IndiActor>(Arrays.asList(indis.get(n))), 1, 1317, (int) indis.get(n).actorX + 30, (int) indis.get(n).actorY, homez));
                                    break;
                                }
                                case 1318: {
                                    indis.get(n).actions.add(0, new Action(new ArrayList<IndiActor>(Arrays.asList(indis.get(myNum))), 1, 1318, (int) indis.get(n).actorX, (int) indis.get(n).actorY, homez));
                                    actions.add(0, new Action(new ArrayList<IndiActor>(Arrays.asList(indis.get(n))), 1, 1318, (int) indis.get(n).actorX + 30, (int) indis.get(n).actorY, homez));
                                    break;
                                }
                                case 1319: {
                                    indis.get(n).actions.add(0, new Action(new ArrayList<IndiActor>(Arrays.asList(indis.get(myNum))), 1, 1319, (int) indis.get(n).actorX, (int) indis.get(n).actorY, homez));
                                    actions.add(0, new Action(new ArrayList<IndiActor>(Arrays.asList(indis.get(n))), 1, 1319, (int) indis.get(n).actorX + 30, (int) indis.get(n).actorY, homez));
                                    break;
                                }
                                case 1320: {
                                    indis.get(n).actions.add(0, new Action(new ArrayList<IndiActor>(Arrays.asList(indis.get(myNum))), 1, 1320, (int) indis.get(n).actorX, (int) indis.get(n).actorY, homez));
                                    actions.add(0, new Action(new ArrayList<IndiActor>(Arrays.asList(indis.get(n))), 1, 1320, (int) indis.get(n).actorX + 30, (int) indis.get(n).actorY, homez));
                                    break;
                                }
                                case 1321: {
                                    indis.get(n).actions.add(0, new Action(new ArrayList<IndiActor>(Arrays.asList(indis.get(myNum))), 1, 1321, (int) indis.get(n).actorX, (int) indis.get(n).actorY, homez));
                                    actions.add(0, new Action(new ArrayList<IndiActor>(Arrays.asList(indis.get(n))), 1, 1321, (int) indis.get(n).actorX + 30, (int) indis.get(n).actorY, homez));
                                    break;
                                }
                            }
                            turnOn(actions, myNum, true);
                            turnOn(actions, n, true);
                        }
                        else {
                            actions.add(new Action(rnd(2) == 2 ? 13 : 17, (int)actorX+1, (int)actorY+1, homez));
                        }
                    }
                    else if (indis.size() > 1) {
                        int n = rnd(indis.size())-1;
                        boolean b = false;
                        try {
                            b = Arrays.asList(2, 4, 7).contains(indis.get(n).actions.get(0).action / 100);
                        }
                        catch (IndexOutOfBoundsException e) {}
                        if (n != myNum & (Math.abs(indis.get(n).delay - delay) <= 15000 || indis.get(n).delay == 0) & !b & isAlive(n)) {
                            indis.get(n).actions.add(0, new Action(new ArrayList<IndiActor>(Arrays.asList(indis.get(myNum))), 1, 1301, (int) indis.get(n).actorX, (int) indis.get(n).actorY, homez));
                            actions.add(0, new Action(new ArrayList<IndiActor>(Arrays.asList(indis.get(n))), 1, 1301, (int) indis.get(n).actorX+30, (int) indis.get(n).actorY, homez));
                        }
                    }
                    else if (society.getIndi(cb, myNum).getNeeds().get(0).getSocial() < 20 && !actNeeds.get("social")) {
                        actions.add(new Action(rnd(2) == 2 ? 13 : 17, (int)actorX+1, (int)actorY+1, homez));
                    }
                }

                //ГИГИЕНА
                if (society.getIndi(cb, myNum).getNeeds().get(0).getHygiene() < 50 - society.getIndi(cb, myNum).getTalents().get(0).getImmunity() * 0.5 && !actNeeds.get("hygiene") && checkUnocc(-1) != 0) {
                    if (checkUnocc(-1) != 0) {
                        int n = reserve(myNum, -1);
                        if (n != -1) {
                            actions.add(0, new Action(801, objects.get(n).ox, objects.get(n).oy, homez));
                        }
                    }
                    else {
                        actions.add(0, new Action(800, (int)actorX, (int)actorY, homez));
                    }
                }
                //БОДРОСТЬ
                if (society.getIndi(cb, myNum).getNeeds().get(0).getEnergy() < 30 - society.getIndi(cb, myNum).getTalents().get(0).getImmunity() * 0.3 && !actNeeds.get("energy")) {
                    if (checkUnocc(2) != 0) {
                        int n = reserve(myNum, 2);
                        if (n != -1) {
                            actions.add(0, new Action(400, objects.get(n).ox, objects.get(n).oy, homez));
                        }
                    }
                    else if (checkUnocc(16) != 0) {
                        int n = reserve(myNum, 16);
                        if (n != -1) {
                            actions.add(0, new Action(402, objects.get(n).ox, objects.get(n).oy, homez));
                        }
                    }
                    else {
                        actions.add(0, new Action(401, (int)actorX, (int)actorY, homez));
                    }
                }
                //ГОЛОД
                if (society.getIndi(cb, myNum).getNeeds().get(0).getHunger() < 50 - society.getIndi(cb, myNum).getTalents().get(0).getImmunity() * 0.3 && !actNeeds.get("hunger")) {
                    if (society.getBoxes().get(cb).foodRemain > 0) {
                        if (checkUnocc(12) != 0) {
                            int n = reserve(myNum, 12);
                            if (n != -1) {
                                actions.add(0, new Action(701, objects.get(n).ox, objects.get(n).oy, homez));
                            }
                        }
                        if (checkUnocc(1) != 0) {
                            int n = reserve(myNum, 1);
                            if (n != -1) {
                                actions.add(0, new Action(700, objects.get(n).ox, objects.get(n).oy, homez));
                            }
                        }
                        if (checkUnocc(15) != 0) {
                            int n = observe(15);
                            if (n != -1) {
                                if (society.getIndi(cb, myNum).getWealth() > 50) actions.add(0, new Action(703, objects.get(n).ox+60+rnd(40), objects.get(n).oy-rnd(20), homez));
                                else actions.add(0, new Action(704, (int)actorX, (int)actorY, homez));
                            }
                        }
                    }
                    else {
                        int n = observe( -2);
                        if (n != -1 && !actNeeds.get("shopping")) {
                            actions.add(0, new Action(1202, objects.get(n).ox, objects.get(n).oy-50, homez));
                        }
                    }
                }
                //УХОД НА РАБОТУ
                /*if (society.getJobs().get(society.getIndiTest(cb, myNum).getJob()).getStartTime() == hh && !actNeeds.get("working")) {
                    if (checkUnocc(-2) != 0) {
                        int n = observe(-2);
                        if (n != -1) {
                            actions.add(0, new Action(1401, objects.get(n).ox, objects.get(n).oy, homez));
                        }
                    }
                }*/
                //БЕЗОПАСНОСТЬ
                boolean b = true;
                try {b = actions.get(0).action / 100 != 11;}
                catch (IndexOutOfBoundsException ignored) {}
                if (checkUnocc(-4) != 0 && b) {
                    talkTre.play(0.05f * VOLUME, Float.valueOf(rnd(4)) / 4, 0);
                    int n = observe(-4);
                    if (n != -1) {
                        actions.add(0, new Action(1102, objects.get(n).ox+rnd(50), objects.get(n).oy-20, homez));
                    }
                }
                else if (checkUnocc(-3) != 0 && b) {
                    talkTre.play(0.05f * VOLUME, Float.valueOf(rnd(4)) / 4, 0);
                    int n = observe(-3);
                    if (n != -1)
                        actions.add(0, new Action(1101, objects.get(n).ox, objects.get(n).oy, homez));
                }
                //МАЛАЯ НУЖДА
                if (society.getIndi(cb, myNum).getNeeds().get(0).getBl() < 20 && !actNeeds.get("bladder") && checkUnocc(-1) != 0){
                    if (checkUnocc(-1) != 0) {
                        int n = reserve(myNum, -1);
                        if (n != -1) {
                            actions.add(0, new Action(201, objects.get(n).ox, objects.get(n).oy, homez));
                        }
                    }
                    else {
                        actions.add(0, new Action(200, (int)actorX, (int)actorY, homez));
                    }
                }
                if (society.getIndi(cb, myNum).getNeeds().get(0).getBl() <= 0 && !actNeeds.get("bladder")) {
                    actions.add(0, new Action(202, (int)actorX, (int)actorY, homez));
                }
                //СРОЧНЫЕ СЦЕНАРИИ
                if (society.getIndi(cb, myNum).getScenarios().size() > 0) {
                    for (int i = 0; i < society.getIndi(cb, myNum).getScenarios().size(); i++) {
                        ArrayList<Integer> news = new ArrayList<Integer>();
                        if (society.getBoxes().get(cb).mainFunc.containsKey("newsTime"))
                            news = (ArrayList<Integer>) society.getBoxes().get(cb).mainFunc.get("newsTime");
                        if (society.getIndi(cb, myNum).getScenarios().get(i).getCode() == 4002 && !actNeeds.get("tvstation") && news.contains(hh) && min < 40) {
                            int n = observe(32);
                            if (n != -1) {
                                actions.clear();
                                actions.add(0, new Action(4002, objects.get(n).ox + 60, objects.get(n).oy - 20, homez));
                            }
                        }
                        if (society.getIndi(cb, myNum).getScenarios().get(i).getCode() == 4001 && !actNeeds.get("tvstation") && news.contains(hh) && min < 40) {
                            int n = observe(31);
                            if (n != -1) {
                                actions.clear();
                                actions.add(0, new Action(4001, objects.get(n).ox + 100, objects.get(n).oy + 120 + rnd(20), homez));
                            }
                        }
                    }
                }
                if (actions.size() == 0) {
                    actions.add(new Action(new ArrayList<Integer>(Arrays.asList(10,11,12,13,14,15,16,17,18,19)).get(rnd(10)-1), (int)actorX+1, (int)actorY+1, homez));
                }



                try {
                    targetX = actions.get(0).targetX;
                    targetY = actions.get(0).targetY;
                    if (status.equals("")) {status = specialStatus(actions.get(0).action, actions);}
                    if (delay == 0 && actorX == actions.get(0).targetX && actorY == actions.get(0).targetY && !(new ArrayList<Integer>(Arrays.asList(1307, 1308, 1309, 1310)).contains(actions.get(0).action))) {
                        delay = System.currentTimeMillis() + actionDelay(actions.get(0).action, myNum);
                        if (new ArrayList<Integer>(Arrays.asList(201, 400, 402, 801, 802, 1201, 1202, 1203, 1204, 1310, 1401)).contains(actions.get(0).action)) {
                            setVisible(false);
                        }
                        if (actions.get(0).action / 100 == 13) {
                            if (actions.get(0).indis.get(0).getTargetX() < getTargetX()) {
                                region.flip(true, false);
                                isTalking = true;
                            }
                        }
                        if (new ArrayList<Integer>(Arrays.asList(600, 601, 602, 2901)).contains(actions.get(0).action)) {
                            for (int i = 0; i < objects.size(); i++) {
                                if (objects.get(i).occ == myNum) {
                                    objects.get(i).texture = whichAnimation(actions.get(0).action, objectsTest.get(i).type, objectsTest.get(i).appearance);
                                    updateAppearance(2, HoldObject.None);
                                    break;
                                }
                            }
                        }
                        if (new ArrayList<Integer>(Arrays.asList(10,11,12,13,14,15,16,17,18,19)).contains(actions.get(0).action)) {
                            updateAppearance(2, HoldObject.Phone1);
                        }
                        if (new ArrayList<Integer>(Arrays.asList(303, 304, 305, 306, 307, 308)).contains(actions.get(0).action)) {
                            updateAppearance(3, HoldObject.Book);
                        }
                        if (new ArrayList<Integer>(Arrays.asList(310, 311, 312, 313, 314, 315)).contains(actions.get(0).action)) {
                            updateAppearance(2, HoldObject.Book);
                        }
                        if (actions.get(0).action == 1101) {
                            updateAppearance(2, HoldObject.Spray);
                        }
                        if (actions.get(0).action == 1102) {
                            updateAppearance(2, HoldObject.Extinguisher);
                        }
                        if (new ArrayList<Integer>(Arrays.asList(603, 604)).contains(actions.get(0).action)) {
                            markerStringType = actions.get(0).etape + "";
                            markerType = 1;
                        }
                        if (actions.get(0).action / 100 == 13) {
                            if (rnd(2) == 1) {
                                talkEtt.play(0.05f * VOLUME, Float.valueOf(rnd(4)) / 4, 0);
                            } else {
                                talkTva.play(0.05f * VOLUME, Float.valueOf(rnd(4)) / 4, 0);
                            }
                            updateAppearance(2, HoldObject.None);
                        }
                    }
                    else if (delay == 0 && actorX == actions.get(0).targetX && actorY == actions.get(0).targetY && Math.abs(actions.get(0).indis.get(0).getX() - getX()) < 30 && Math.abs(actions.get(0).indis.get(0).getY() - getY()) < 30) {
                        delay = System.currentTimeMillis() + actionDelay(actions.get(0).action, myNum);
                        if (new ArrayList<Integer>(Arrays.asList(1301, 1302, 1303, 1304, 1305, 1315, 1316, 1317, 1318, 1319, 1320)).contains(actions.get(0).action)) {
                            if (rnd(2) == 1) {
                                talkEtt.play(0.05f * VOLUME, Float.valueOf(rnd(4)) / 4, 0);
                            } else {
                                talkTva.play(0.05f * VOLUME, Float.valueOf(rnd(4)) / 4, 0);
                            }
                        }
                    }
                    else if (delay < System.currentTimeMillis() && actorX == actions.get(0).targetX && actorY == actions.get(0).targetY) {
                        isTalking = false;
                        updateAppearance(1, HoldObject.None);
                        if (actions.get(0).action == 4002) {frameBorder.remove();}
                        if (new ArrayList<Integer>(Arrays.asList(1201, 1203, 1204)).contains(actions.get(0).action)) {
                            FurnCard f = furn.get(0);
                            if (actions.get(0).action == 1201) {
                                while (f.type != 7) {
                                    f = furn.get(rnd(furn.size())-1);
                                }
                            }
                            else if (actions.get(0).action == 1203) {
                                while (!(new ArrayList<Integer>(Arrays.asList(1, 2, 3, 5, 6, 8, 9, 12, 16)).contains(f.type))) {
                                    f = furn.get(rnd(furn.size())-1);
                                }
                            }
                            else if (actions.get(0).action == 1204) {
                                while (!(new ArrayList<Integer>(Arrays.asList(10, 11, 20, 22, 24)).contains(f.type))) {
                                    f = furn.get(rnd(furn.size())-1);
                                }
                            }
                            else if (actions.get(0).action == 701) {
                                if (society.getIndi(cb, myNum).getInterests().get(0).getFood() < 30 && rnd(2) == 1) {
                                    createObject(-4, 1, 0, 0, (int)targetX-20, (int)targetY-30);
                                }
                            }
                            if (society.getIndi(cb, myNum).getWealth() < society.getBoxes().get(cb).getBoxZ() * f.price) {
                                getIndiActor(myNum).marker = 1;
                                getIndiActor(myNum).markerString = langString.get("insuffunds") + ":" + society.getBoxes().get(cb).getBoxZ() * f.price + "$";}
                            else {
                                society.getIndi(cb, myNum).setWealth(society.getIndi(cb, myNum).getWealth() - 9000/*society.getBoxes().get(cb).getBoxZ() * f.price*/);
                                createObject(f.type, f.appear, 850, 700);
                            }
                        }
                        actions = wait4smth(actions, myNum);
                        delay = 0;
                        setVisible(true);
                        for (int i = 0; i < objects.size(); i++) {
                            if (objects.get(i).occ == myNum) {
                                objects.get(i).occ = -1;
                                objects.get(i).texture = whichObject(objectsTest.get(i).getType(), objectsTest.get(i).getAppearance());
                            }
                        }
                        if (actions.size() == 0) {
                            targetX += rnd(40);
                            targetY -= rnd(40);
                        }
                        if (actions.size() > 10) {
                            actions.subList(10, actions.size() - 1).clear();
                        }
                    }
                    else if (delay > System.currentTimeMillis() && (actorX != actions.get(0).targetX || actorY != actions.get(0).targetY)) {
                        //нужно адекватно затирать в actNeeds true на false
                        delay = 0;
                        status = "";
                        setVisible(true);
                        for (int i = 0; i < objects.size(); i++) {
                            if (objects.get(i).occ == myNum) {
                                objects.get(i).occ = -1;
                                objects.get(i).texture = whichObject(objectsTest.get(i).getType(), objectsTest.get(i).getAppearance());
                            }
                        }
                    }
                    turnOn(actions, myNum, true);
                }
                catch (IndexOutOfBoundsException e) {}
            }
        }

        public void setAppearance(int appearance) {
            this.appearance = appearance;
        }
    }

    public static class Indi {

        private int age;
        boolean alive = true;
        private int appearance;
        private int classes;
        private ArrayList<Addition.Creation> creations;
        private boolean doing;
        private ArrayList<Education> educations = new ArrayList<Education>();
        private int gender;
        private ArrayList<GenderProps> genderProps;
        private final int globalNum;
        private int homeX;
        private int homeY;
        private int homeZ;
        private int homeZZ = 1;
        private ArrayList<Init> inits;
        private ArrayList interests;
        private int iq;
        int job;
        private transient int lifePurpose;
        private int love;
        private int myNum;
        private String name;
        private ArrayList<NeedsArray> needs;
        private int popularity;
        private ArrayList<Relation> relations;
        private ArrayList<Scenario> scenarios = new ArrayList<>();
        private int selfEsteem;
        private ArrayList skills;
        private ArrayList socialLifts;
        public transient boolean started = true;
        private String surname;
        private ArrayList<TalentsArray> talents;
        private int wealth;
        private transient float targetX = 600;
        private transient float targetY = 450;
        private transient int indiChat;

        public Indi()
        {
            globalNum = -1;
        }

        public Indi(String name, String surname, int globalNum, int gender, int age, int classes, int iq, int job, int wealth, int homeX, int homeY, int homeZ, ArrayList inits, ArrayList needs, ArrayList talents, ArrayList skills, ArrayList socialLifts, ArrayList creations, ArrayList genderProps, ArrayList relations, ArrayList interests, int appearance, int lifePurpose, int selfEsteem, int popularity, int myNum) {
            indisTestN++;
            this.name = name;
            this.surname = surname;
            this.gender = gender;
            this.age = age;
            this.classes = classes;
            this.iq = iq;
            this.job = job;
            this.wealth = wealth;
            this.homeX = homeX;
            this.homeY = homeY;
            this.homeZ = homeZ;
            this.inits = inits;
            this.needs = needs;
            this.talents = talents;
            this.skills = skills;
            this.socialLifts = socialLifts;
            this.creations = creations;
            this.genderProps = genderProps;
            this.relations = relations;
            this.interests = interests;
            this.appearance = appearance;
            this.lifePurpose = lifePurpose;
            this.selfEsteem = selfEsteem;
            this.popularity = popularity;
            this.myNum = myNum;
            doing = false;
            love = 0;
            this.globalNum = globalNum;

            if (educations.size() > 0) {
                educations.add(new Education(3, univers.get(0), univers.get(0).specialities.get(rnd(univers.get(0).specialities.size() - 1))));
            }
        }

        public int getAppearance() {
            return this.appearance;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public void setAppearance(int appearance) {
            this.appearance = appearance;
        }

        public int getClasses() {
            return classes;
        }

        public void setClasses(int classes) {
            this.classes = classes;
        }

        public ArrayList<Addition.Creation> getCreations() {
            return creations;
        }

        public void setCreations(ArrayList creations) {
            this.creations = creations;
        }

        public boolean isDoing() {
            return doing;
        }

        public void setDoing(boolean doing) {
            this.doing = doing;
        }

        public ArrayList<Education> getEducations() {
            return educations;
        }

        public void setEducations(ArrayList<Education> educations) {
            this.educations = educations;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public ArrayList<GenderProps> getGenderProps() {
            return genderProps;
        }

        public void setGenderProps(ArrayList genderProps) {
            this.genderProps = genderProps;
        }

        public int getHomeX() {
            return homeX;
        }

        public void setHomeX(int homeX) {
            this.homeX = homeX;
        }

        public int getHomeY() {
            return homeY;
        }

        public void setHomeY(int homeY) {
            this.homeY = homeY;
        }

        public int getHomeZ() {
            return homeZ;
        }

        public void setHomeZ(int homeZ) {
            this.homeZ = homeZ;
        }

        public ArrayList<Init> getInits() {
            return inits;
        }

        public void setInits(ArrayList<Init> inits) {
            this.inits = inits;
        }

        public ArrayList<InterestsArray> getInterests() {
            return interests;
        }

        public void setInterests(ArrayList interests) {
            this.interests = interests;
        }

        public int getIq() {
            return iq;
        }

        public void setIq(int iq) {
            this.iq = iq;
        }

        public int getJob() {
            return job;
        }

        public void setJob(int job) {
            this.job = job;
        }

        public int getLifePurpose() {
            return lifePurpose;
        }

        public void setLifePurpose(int lifePurpose) {
            this.lifePurpose = lifePurpose;
        }

        public int getLove() {
            return love;
        }

        public void setLove(int love) {
            this.love = love;
        }

        public int getmyNum() {
            return myNum;
        }

        public void setmyNum(int myNum) {
            this.myNum = myNum;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ArrayList<NeedsArray> getNeeds() {
            return needs;
        }

        public void setNeeds(ArrayList<NeedsArray> needs) {
            this.needs = needs;
        }

        public int getPopularity() {
            return popularity;
        }

        public void setPopularity(int popularity) {
            this.popularity = popularity;
        }

        public ArrayList<Relation> getRelations() {
            return relations;
        }

        public void setRelations(ArrayList relations) {
            this.relations = relations;
        }

        public ArrayList<Scenario> getScenarios() {
            return scenarios;
        }

        public ArrayList<Integer> getScenarioNums() {
            ArrayList<Integer> scens = new ArrayList<Integer>();
            for (int i = 0; i < scenarios.size(); i++) {
                scens.add(scenarios.get(i).code);
            }
            return scens;
        }

        public void setScenarios(ArrayList<Scenario> scenarios) {
            this.scenarios = scenarios;
        }

        public int getSelfEsteem() {
            return selfEsteem;
        }

        public void setSelfEsteem(int selfEsteem) {
            this.selfEsteem = selfEsteem;
        }

        public ArrayList<SkillsArray> getSkills() {
            return skills;
        }

        public void setSkills(ArrayList skills) {
            this.skills = skills;
        }

        public ArrayList<SocialLiftsArray> getSocialLifts() {
            return socialLifts;
        }

        public void setSocialLifts(ArrayList socialLifts) {
            this.socialLifts = socialLifts;
        }

        public boolean isStarted() {
            return started;
        }

        public void setStarted(boolean started) {
            this.started = started;
        }

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public ArrayList<TalentsArray> getTalents() {
            return talents;
        }

        public void setTalents(ArrayList<TalentsArray> talents) {
            this.talents = talents;
        }

        public int getWealth() {
            return wealth;
        }

        public void setWealth(int wealth) {
            this.wealth = wealth;
        }

        public float getTargetX() {
            return targetX;
        }

        public void setTargetX(float targetX) {
            this.targetX = targetX;
        }

        public float getTargetY() {
            return targetY;
        }

        public void setTargetY(float targetY) {
            this.targetY = targetY;
        }

        public int getIndiChat() {
            return indiChat;
        }

        public void setIndiChat(int indiChat) {
            this.indiChat = indiChat;
        }
    }

    public class ObjectActor extends Actor {
        public ObjectActor() {
        }

        public boolean anim = false;
        private TextureRegion region;
        private Texture texture;
        private int ox, oy;
        public int num0;
        public int occ = -1;
        public int vac = 1;
        public transient boolean started = false;
        public transient boolean bounds = true;
        public long delay, mainDelay;

        public ObjectActor(final Texture texture, int x, int y, int num0) {
            this.texture = texture;
            Gdx.app.log("texture", String.valueOf(texture));
            this.ox = x;
            this.oy = y;
            this.num0 = num0;
            final int num1 = num0;
            occ = -1;
            region = new TextureRegion(texture);
            setBounds(region.getRegionX(), region.getRegionY(),
                    region.getRegionWidth(), region.getRegionHeight());
            //if (objectsTest.get(num1-1).type == 19) {vac =2;}
            addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    removeExtraActors();
                    tapDelay = System.currentTimeMillis();
                    tableMove.setVisible(true);
                    objectObs = num1 - 1;
                    if (objectsTest.get(num1-1).type == 1) {
                        final ScenButton s = new ScenButton(langString.get("restock") + "\n" + langString.get("foodRemain") + ": " + society.getBoxes().get(cb).foodRemain);
                        final ScenButton ss = new ScenButton(langString.get("defrost"));
                        scenButtons.add(s);
                        s.setPosition(Gdx.graphics.getWidth() - 420, 120);
                        cardStage.addActor(s);
                        scenButtons.add(ss);
                        ss.setPosition(Gdx.graphics.getWidth() - 420, 170);
                        cardStage.addActor(ss);
                        s.addListener(new ClickListener() {
                            public void clicked(InputEvent event, float x, float y) {
                                society.getBoxes().get(cb).foodRemain += 20;
                                s.remove();
                                ss.remove();
                            }});
                        ss.addListener(new ClickListener() {
                            public void clicked(InputEvent event, float x, float y) {
                                society.getBoxes().get(cb).foodRemain = 0;
                                s.remove();s.remove();
                                ss.remove();
                            }});
                    }
                    else if (objectsTest.get(num1-1).type == 2) {
                        final ScenButton s = new ScenButton(langString.get("addCockroaches"));
                        final ScenButton ss = new ScenButton(langString.get("makeFire"));
                        scenButtons.add(s);
                        s.setPosition(Gdx.graphics.getWidth() - 420, 120);
                        cardStage.addActor(s);
                        scenButtons.add(ss);
                        ss.setPosition(Gdx.graphics.getWidth() - 420, 170);
                        cardStage.addActor(ss);
                        s.addListener(new ClickListener() {
                            public void clicked(InputEvent event, float x, float y) {
                                createObject(-3, 1, 0, 0, rnd(80)+900, rnd(60)+600);
                                s.remove();
                                ss.remove();
                            }});
                        ss.addListener(new ClickListener() {
                            public void clicked(InputEvent event, float x, float y) {
                                createObject(-4, 1, 0, 0, ox-20, oy-30);
                                s.remove();
                                ss.remove();
                            }});
                    }
                    else if (objectsTest.get(num1-1).type == 6) {
                        final ScenButton s = new ScenButton(langString.get("makeFire"));
                        scenButtons.add(s);
                        s.setPosition(Gdx.graphics.getWidth() - 420, 120);
                        cardStage.addActor(s);
                        s.addListener(new ClickListener() {
                            public void clicked(InputEvent event, float x, float y) {
                                createObject(-4, 1, 0, 0, ox-20, oy-30);
                                s.remove();
                            }});
                    }
                    else if (objectsTest.get(num1-1).type == 7) {
                        final ScenButton s = new ScenButton(langString.get("makeDecor"));
                        final ScenButton ss = society.getBoxes().get(cb).getProperty() == Box.Property.Museum ? new ScenButton(langString.get("makeRoomNormal")) : new ScenButton(langString.get("makeRoomMuseum"));
                        scenButtons.add(s);
                        s.setPosition(Gdx.graphics.getWidth() - 420, 120);
                        cardStage.addActor(s);
                        scenButtons.add(ss);
                        ss.setPosition(Gdx.graphics.getWidth() - 420, 170);
                        cardStage.addActor(ss);
                        s.addListener(new ClickListener() {
                            public void clicked(InputEvent event, float x, float y) {
                                occ = -1;
                                objectsTest.get(num1-1).type = 29;
                                objects.get(objectObs).setTexture(whichObject(objectsTest.get(objectObs).getType(), objectsTest.get(objectObs).getAppearance()));
                                s.remove();
                                ss.remove();
                            }});
                        ss.addListener(new ClickListener() {
                            public void clicked(InputEvent event, float x, float y) {
                                if (society.getBoxes().get(cb).getProperty() == Box.Property.Museum) {
                                    society.getBoxes().get(cb).setProperty(Private);
                                } else {
                                    society.getBoxes().get(cb).setProperty(Box.Property.Museum);
                                }
                                s.remove();
                                ss.remove();
                            }});
                    }
                    else if (objectsTest.get(num1-1).type == 15 || objectsTest.get(num1-1).type == -15) {
                        final ScenButton ss = objectsTest.get(num1-1).type == 15 ? new ScenButton(langString.get("buffetClose")) : new ScenButton(langString.get("buffetOpen"));
                        scenButtons.add(ss);
                        ss.setPosition(Gdx.graphics.getWidth() - 420, 120);
                        cardStage.addActor(ss);
                        ss.addListener(new ClickListener() {
                            public void clicked(InputEvent event, float x, float y) {
                                if (objectsTest.get(num1-1).type == 15) {
                                    objectsTest.get(num1-1).setType(-15);
                                    objects.get(num1-1).setTexture(whichObject(-15, objectsTest.get(num1-1).getAppearance()));
                                } else {
                                    objectsTest.get(num1-1).setType(15);
                                    objects.get(num1-1).setTexture(whichObject(15, objectsTest.get(num1-1).getAppearance()));
                                }
                                ss.remove();
                            }});
                        objects.get(num1-1).setTexture(whichObject(objectsTest.get(num1-1).getType(), objectsTest.get(num1-1).getAppearance()));
                    }
                    else if (objectsTest.get(num1-1).type == 23 && !objectsTest.get(num1-1).getExtra().containsKey("occ")) {
                        final ScenButton s = new ScenButton("Сделать местом PR-менеджера");
                        final ScenButton ss = new ScenButton("Сделать местом редактора \nновостей");
                        final ScenButton sss = new ScenButton("Сделать местом копирайтера");
                        scenButtons.add(s);
                        s.setPosition(Gdx.graphics.getWidth() - 420, 120);
                        cardStage.addActor(s);
                        scenButtons.add(ss);
                        ss.setPosition(Gdx.graphics.getWidth() - 420, 170);
                        cardStage.addActor(ss);
                        scenButtons.add(sss);
                        sss.setPosition(Gdx.graphics.getWidth() - 420, 220);
                        cardStage.addActor(sss);
                        s.addListener(new ClickListener() {
                            public void clicked(InputEvent event, float x, float y) {
                                s.remove();
                                ss.remove();
                                sss.remove();
                            }});
                        ss.addListener(new ClickListener() {
                            public void clicked(InputEvent event, float x, float y) {
                                s.remove();
                                ss.remove();
                                sss.remove();
                            }});
                        sss.addListener(new ClickListener() {
                            public void clicked(InputEvent event, float x, float y) {
                                s.remove();
                                ss.remove();
                                sss.remove();
                            }});
                    }
                    else if (objectsTest.get(num1-1).type == 25) {
                        final ScenButton s = new ScenButton("Закрыть это меню");
                        scenButtons.add(s);
                        s.setPosition(Gdx.graphics.getWidth() - 420, 120);
                        cardStage.addActor(s);
                        final ScenButton ss = new ScenButton("Открыть меню системы");
                        scenButtons.add(ss);
                        ss.setPosition(Gdx.graphics.getWidth() - 420, 170);
                        cardStage.addActor(ss);
                        s.addListener(new ClickListener() {
                            public void clicked(InputEvent event, float x, float y) {
                                labelHintTva.setText("");
                                groupObs = -1;
                                cardEtt.texture = new Texture("ui/indiPreview.png");
                                panelLeft.texture = new Texture("ui/socialsystembck.png");
                                hideTables(false);
                                iconTable.setVisible(true);
                                iconTableSystem.setVisible(false);
                                tableBroadcast.setVisible(false);
                                labelHint.setText("");
                                s.remove();
                            }});
                        ss.addListener(new ClickListener() {
                            public void clicked(InputEvent event, float x, float y) {
                                cardEtt.texture = new Texture("ui/socialSystemPreview.png");
                                outerTableTva.setVisible(false);
                                indiObs = -1;
                                groupObs = 0;
                                objectObs = -1;
                                tableMove.setVisible(false);
                                labelHintTva.setText(langString.get("socialSystem"));
                                labelHintTre.setText("");
                                cardEtt.setName(society.getSocialSystems().get(0).name);
                                channelField.setText(channels.get(Integer.parseInt(society.getBoxes().get(cb).getObjectsTest().get(num1-1).links.get(0))).getName());
                                hideTables(true);
                                panelLeft.texture = new Texture("ui/socialsystembck.png");
                                iconTable.setVisible(false);
                                iconTableSystem.setVisible(true);
                                tableBroadcast.setVisible(true);
                                ss.remove();
                            }});
                    }
                    else if (objectsTest.get(num1-1).type == 29) {
                        final ScenButton s = new ScenButton(langString.get("makeRegular"));
                        final ScenButton ss = society.getBoxes().get(cb).getProperty() == Box.Property.Museum ? new ScenButton(langString.get("makeRoomNormal")) : new ScenButton(langString.get("makeRoomMuseum"));
                        scenButtons.add(s);
                        s.setPosition(Gdx.graphics.getWidth() - 420, 120);
                        cardStage.addActor(s);
                        scenButtons.add(ss);
                        ss.setPosition(Gdx.graphics.getWidth() - 420, 170);
                        cardStage.addActor(ss);
                        s.addListener(new ClickListener() {
                            public void clicked(InputEvent event, float x, float y) {
                                occ = -1;
                                objectsTest.get(num1-1).type = 7;
                                objects.get(objectObs).setTexture(whichObject(objectsTest.get(objectObs).getType(), objectsTest.get(objectObs).getAppearance()));
                                s.remove();
                                ss.remove();
                            }});
                        ss.addListener(new ClickListener() {
                            public void clicked(InputEvent event, float x, float y) {
                                if (society.getBoxes().get(cb).getProperty() == Box.Property.Museum) {
                                    society.getBoxes().get(cb).setProperty(Private);
                                } else {
                                    society.getBoxes().get(cb).setProperty(Box.Property.Museum);
                                }
                                s.remove();
                                ss.remove();
                            }});
                    }
                    else if (objectsTest.get(num1-1).type == 31 && indiObs > -1) {
                        final ScenButton ss = society.getIndi(cb, indiObs).getScenarios().contains(new Scenario(4001)) ? new ScenButton("Уволить " + indis.get(indiObs).name + " " + indis.get(indiObs).surname) : new ScenButton("Сделать " + indis.get(indiObs).name + " " + indis.get(indiObs).surname + "\nведущим новостей");
                        scenButtons.add(ss);
                        ss.setPosition(Gdx.graphics.getWidth() - 420, 120);
                        cardStage.addActor(ss);
                        ss.addListener(new ClickListener() {
                            public void clicked(InputEvent event, float x, float y) {
                                society.getIndi(cb, indiObs).getScenarios().add(new Scenario(4001));
                                ss.remove();
                            }});
                    }
                    else if (objectsTest.get(num1-1).type == 32 && indiObs > -1) {
                        final ScenButton ss = society.getIndi(cb, indiObs).getScenarios().contains(new Scenario(4002)) ? new ScenButton("Уволить " + indis.get(indiObs).name + " " + indis.get(indiObs).surname) : new ScenButton("Сделать " + indis.get(indiObs).name + " " + indis.get(indiObs).surname + "\nоператором");
                        scenButtons.add(ss);
                        ss.setPosition(Gdx.graphics.getWidth() - 420, 120);
                        cardStage.addActor(ss);
                        ss.addListener(new ClickListener() {
                            public void clicked(InputEvent event, float x, float y) {
                                society.getIndi(cb, indiObs).getScenarios().add(new Scenario(4002));
                                ss.remove();
                            }});
                    }
                    else if (objectsTest.get(num1-1).type == 35 && indiObs > -1) {
                        final ScenButton s = new ScenButton("Сменить внешний вид");
                        scenButtons.add(s);
                        s.setPosition(Gdx.graphics.getWidth() - 420, 120);
                        cardStage.addActor(s);
                        s.addListener(new ClickListener() {
                            public void clicked(InputEvent event, float x, float y) {
                                indis.get(indiObs).appearance = indis.get(indiObs).appearance == 1 ? 2 : 1;
                                indis.get(indiObs).updateAppearance(1, HoldObject.None);
                                removeExtraActors();
                            }});
                    }
                }
            });
        }

        public Texture getTexture() {
            return texture;
        }

        public void setTexture(Texture texture) {
            this.texture = texture;
        }

        public int getOX() {
            return ox;
        }

        public void setOX(int x) {
            this.ox = x;
        }

        public int getOY() {
            return oy;
        }

        public void setOY(int y) {
            this.oy = y;
        }

        public boolean isStarted() {
            return started;
        }

        public void setStarted(boolean started) {
            this.started = started;
        }

        public int getNum0() {
            return num0;
        }

        public void setNum0(int num0) {
            this.num0 = num0;
        }

        public void draw(Batch batch, float alpha) {
            try {
                if (society.getObjectsTest(cb).get(num0-1).getType() == 32 && ((ArrayList<Integer>) society.getBoxes().get(cb).mainFunc.get("newsTime")).contains(hh) && min < 40) {
                    batch.draw(new Texture(society.getObjectsTest(cb).get(num0-1).getAppearance() % 2 == 0 ? "tvstation/frameborder11.png" : "tvstation/frameborder12.png"), objects.get(num0-1).ox-150, objects.get(num0-1).oy + 50);
                }
            }
            catch (NullPointerException e) {}
            batch.draw(texture, ox, oy);
            //smallFontFran.draw(batch, globalString, -1000, 1000);
            //smallFontFran.draw(batch, "occ: " + occ, ox, oy-10);
            //fontFran.draw(batch, String.valueOf(num0)+"/"+String.valueOf(objectObs)+" x:"+String.valueOf(ox)+" y:"+String.valueOf(oy), ox, oy - 10);
        }

        public void act(float delta) {
            if (bounds) setBounds(ox, oy, texture.getWidth(), texture.getHeight());
            if (objectsTest.get(num0-1).type == -3 || objectsTest.get(num0-1).type == -4 && System.currentTimeMillis() > delay) {
                texture = whichObject(objectsTest.get(num0-1).type, rnd(2));
                delay = System.currentTimeMillis() + 500;
            }
            if (anim) {
                if (objectsTest.get(num0-1).type == 5 && objectsTest.get(num0-1).appearance == 13 && System.currentTimeMillis() > delay) {
                    texture = whichObject(5, 13+(rnd(2)/2));
                    delay = System.currentTimeMillis() + 500;
                }
                else if (objectsTest.get(num0-1).type == 5 && objectsTest.get(num0-1).appearance == 14 && System.currentTimeMillis() > delay) {
                    texture = whichObject(5, 15+(rnd(2)/2));
                    delay = System.currentTimeMillis() + 500;
                }
            }
            if (System.currentTimeMillis() > mainDelay) {anim = false;}
        }
    }

    public static class ObjectTest {
        public ObjectTest() {
        }

        private ArrayList<String> links = new ArrayList<String>(); // для дополнительных сведений и ссылок этого объекта
        private HashMap<String, Object> extra = new HashMap<String, Object>();
        private int type;
        private int appearance;
        private int cleanliness4;
        private int floor;
        private int fixity;
        private int x;
        private int y4;
        private int tileX;//на самом деле это cleanliness
        private int tileY;//на самом деле это y
        private int num0;
        private int z;
        private transient int action = 0;

        public ObjectTest(int type, int appearance, int cleanliness4, int fixity, int x, int y4, int tileX, int tileY, int num0) {
            this.type = type;
            this.appearance = appearance;
            this.cleanliness4 = cleanliness4;
            this.fixity = fixity;
            this.x = x;
            this.y4 = y4;
            this.tileX = tileX;
            this.tileY = tileY;
            this.num0 = num0;
            floor = currentFloor;
            if (type == 1) {society.getBoxes().get(cb).setFoodRemain(society.getBoxes().get(cb).getFoodRemain() + 5);
                labelCenter.setText("Осталось еды: " + society.getBoxes().get(cb).getFoodRemain()); uiDelay = System.currentTimeMillis() + 3000;}
            extra.put("occ", -1);
        }

        public int getAppearance() {
            return appearance;
        }

        public void setAppearance(int appearance) {
            this.appearance = appearance;
        }

        public int getFloor() {
            return floor;
        }

        public void setFloor(int floor) {
            this.floor = floor;
        }

        public HashMap<String, Object> getExtra() {
            return extra;
        }

        public void setExtra(HashMap<String, Object> extra) {
            this.extra = extra;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y4;
        }

        public void setY(int y) {
            this.y4 = y4;
        }

        public int getZ() {
            return z;
        }

        public void setZ(int z) {
            this.z = z;
        }

        public int getTileX() {
            return tileX;
        }

        public void setTileX(int tileX) {
            this.tileX = tileX;
        }

        public int getTileY() {
            return tileY;
        }

        public void setTileY(int tileY) {
            this.tileY = tileY;
        }

        public int getAction() {
            return action;
        }

        public int getCleanliness4() {
            return cleanliness4;
        }

        public void setCleanliness4(int cleanliness) {
            this.cleanliness4 = cleanliness4;
        }

        public int getFixity() {
            return fixity;
        }

        public void setFixity(int fixity) {
            this.fixity = fixity;
        }

        public int getNum0() {
            return num0;
        }

        public void setNum0(int num0) {
            this.num0 = num0;
        }

        public void setAction(int action) {
            this.action = action;
        }
    }



    public void addingParameters() {
        int counter = 1;
        if (Gdx.graphics.getFramesPerSecond() < 20) {counter = 3;}
        if (Gdx.graphics.getFramesPerSecond() > 20) {counter = 2;}
        if (Gdx.graphics.getFramesPerSecond() > 40) {counter = 1;}
        //int cardCount = Gdx.graphics.getHeight() / 2 * (Gdx.input.getY() - (Gdx.graphics.getHeight() / 2)) / (Gdx.graphics.getHeight() / 10);
        int cardCount = counter;//(Gdx.input.getY() - Gdx.graphics.getHeight() / 2) / (3);
        if (cardType != 0) {
            if (Gdx.input.getY() > neededY) {
                cardCount = -counter;
            }
            arrowHint.setVisible(true);
            if (cardType == 1) {
                society.getIndi(cb, indiObs).getInterests().get(0).setPolitics(society.getIndi(cb, indiObs).getInterests().get(0).getPolitics() + cardCount);
            }
            if (cardType == 2) {
                society.getIndi(cb, indiObs).getInterests().get(0).setEconomics(society.getIndi(cb, indiObs).getInterests().get(0).getEconomics() + cardCount);
            }
            if (cardType == 3) {
                society.getIndi(cb, indiObs).getInterests().get(0).setHealth(society.getIndi(cb, indiObs).getInterests().get(0).getHealth() + cardCount);
            }
            if (cardType == 4) {
                society.getIndi(cb, indiObs).getInterests().get(0).setCrimes(society.getIndi(cb, indiObs).getInterests().get(0).getCrimes() + cardCount);
            }
            if (cardType == 5) {
                society.getIndi(cb, indiObs).getInterests().get(0).setFashion(society.getIndi(cb, indiObs).getInterests().get(0).getFashion() + cardCount);
            }
            if (cardType == 6) {
                society.getIndi(cb, indiObs).getInterests().get(0).setCulture(society.getIndi(cb, indiObs).getInterests().get(0).getCulture() + cardCount);
            }
            if (cardType == 7) {
                society.getIndi(cb, indiObs).getInterests().get(0).setFood(society.getIndi(cb, indiObs).getInterests().get(0).getFood() + cardCount);
            }
            if (cardType == 8) {
                society.getIndi(cb, indiObs).getInterests().get(0).setFun(society.getIndi(cb, indiObs).getInterests().get(0).getFun() + cardCount);
            }
            if (cardType == 9) {
                society.getIndi(cb, indiObs).getInterests().get(0).setSport(society.getIndi(cb, indiObs).getInterests().get(0).getSport() + cardCount);
            }
            if (cardType == 10) {
                society.getIndi(cb, indiObs).getInterests().get(0).setTravel(society.getIndi(cb, indiObs).getInterests().get(0).getTravel() + cardCount);
            }
            if (cardType == 11) {
                society.getIndi(cb, indiObs).getInterests().get(0).setTechnics(society.getIndi(cb, indiObs).getInterests().get(0).getTechnics() + cardCount);
            }
            if (cardType == 12) {
                society.getIndi(cb, indiObs).getInterests().get(0).setWork(society.getIndi(cb, indiObs).getInterests().get(0).getWork() + cardCount);
            }
            if (cardType == 13) {
                society.getIndi(cb, indiObs).getInterests().get(0).setAnimals(society.getIndi(cb, indiObs).getInterests().get(0).getAnimals() + cardCount);
            }
            if (cardType == 14) {
                society.getIndi(cb, indiObs).getInterests().get(0).setBooks(society.getIndi(cb, indiObs).getInterests().get(0).getBooks() + cardCount);
            }
            if (cardType == 15) {
                society.getIndi(cb, indiObs).getInterests().get(0).setFilms(society.getIndi(cb, indiObs).getInterests().get(0).getFilms() + cardCount);
            }
            if (cardType == 16) {
                society.getIndi(cb, indiObs).getInterests().get(0).setMusic(society.getIndi(cb, indiObs).getInterests().get(0).getMusic() + cardCount);
            }
            if (cardType == 26) {
                society.getIndi(cb, indiObs).getNeeds().get(0).setAesthetics(society.getIndi(cb, indiObs).getNeeds().get(0).getAesthetics() + cardCount);
            }
            if (cardType == 27) {
                society.getIndi(cb, indiObs).getNeeds().get(0).setBl(society.getIndi(cb, indiObs).getNeeds().get(0).getBl() + cardCount);
            }
            if (cardType == 28) {
                society.getIndi(cb, indiObs).getNeeds().get(0).setEducation(society.getIndi(cb, indiObs).getNeeds().get(0).getEducation() + cardCount);
            }
            if (cardType == 29) {
                society.getIndi(cb, indiObs).getNeeds().get(0).setEnergy(society.getIndi(cb, indiObs).getNeeds().get(0).getEnergy() + cardCount);
            }
            if (cardType == 30) {
                society.getIndi(cb, indiObs).getNeeds().get(0).setEnvironment(society.getIndi(cb, indiObs).getNeeds().get(0).getEnvironment() + cardCount);
            }
            if (cardType == 31) {
                society.getIndi(cb, indiObs).getNeeds().get(0).setFun(society.getIndi(cb, indiObs).getNeeds().get(0).getFun() + cardCount);
            }
            if (cardType == 32) {
                society.getIndi(cb, indiObs).getNeeds().get(0).setHunger(society.getIndi(cb, indiObs).getNeeds().get(0).getHunger() + cardCount);
            }
            if (cardType == 33) {
                society.getIndi(cb, indiObs).getNeeds().get(0).setHygiene(society.getIndi(cb, indiObs).getNeeds().get(0).getHygiene() + cardCount);
            }
            if (cardType == 34) {
                society.getIndi(cb, indiObs).getNeeds().get(0).setLove(society.getIndi(cb, indiObs).getNeeds().get(0).getLove() + cardCount);
            }
            if (cardType == 35) {
                society.getIndi(cb, indiObs).getNeeds().get(0).setPower(society.getIndi(cb, indiObs).getNeeds().get(0).getPower() + cardCount);
            }
            if (cardType == 36) {
                society.getIndi(cb, indiObs).getNeeds().get(0).setProtection(society.getIndi(cb, indiObs).getNeeds().get(0).getProtection() + cardCount);
            }
            if (cardType == 37) {
                society.getIndi(cb, indiObs).getNeeds().get(0).setShopping(society.getIndi(cb, indiObs).getNeeds().get(0).getShopping() + cardCount);
            }
            if (cardType == 38) {
                society.getIndi(cb, indiObs).getNeeds().get(0).setSocial(society.getIndi(cb, indiObs).getNeeds().get(0).getSocial() + cardCount);
            }
            if (cardType == 39) {
                society.getIndi(cb, indiObs).getNeeds().get(0).setSuccess(society.getIndi(cb, indiObs).getNeeds().get(0).getSuccess() + cardCount);
            }
            if (cardType == 44) {
                society.getIndi(cb, indiObs).getTalents().get(0).setCaution(society.getIndi(cb, indiObs).getTalents().get(0).getCaution() + cardCount);
            }
            if (cardType == 45) {
                society.getIndi(cb, indiObs).getTalents().get(0).setImagination(society.getIndi(cb, indiObs).getTalents().get(0).getImagination() + cardCount);
            }
            if (cardType == 46) {
                society.getIndi(cb, indiObs).getTalents().get(0).setImmunity(society.getIndi(cb, indiObs).getTalents().get(0).getImmunity() + cardCount);
            }
            if (cardType == 47) {
                society.getIndi(cb, indiObs).getTalents().get(0).setInfluence(society.getIndi(cb, indiObs).getTalents().get(0).getInfluence() + cardCount);
            }
            if (cardType == 48) {
                society.getIndi(cb, indiObs).getTalents().get(0).setInsight(society.getIndi(cb, indiObs).getTalents().get(0).getInsight() + cardCount);
            }
            if (cardType == 49) {
                society.getIndi(cb, indiObs).getTalents().get(0).setLogic(society.getIndi(cb, indiObs).getTalents().get(0).getLogic() + cardCount);
            }
            if (cardType == 50) {
                society.getIndi(cb, indiObs).getTalents().get(0).setMemory(society.getIndi(cb, indiObs).getTalents().get(0).getMemory() + cardCount);
            }
            if (cardType == 51) {
                society.getIndi(cb, indiObs).getTalents().get(0).setQuickness(society.getIndi(cb, indiObs).getTalents().get(0).getQuickness() + cardCount);
            }
            if (cardType == 52) {
                society.getIndi(cb, indiObs).getTalents().get(0).setSpeech(society.getIndi(cb, indiObs).getTalents().get(0).getSpeech() + cardCount);
            }
            if (cardType == 53) {
                society.getIndi(cb, indiObs).getTalents().get(0).setStamina(society.getIndi(cb, indiObs).getTalents().get(0).getStamina() + cardCount);
            }
            if (cardType == 54) {
                society.getBoxes().get(cb).setTemperature(society.getBoxes().get(cb).getTemperature() + 0.5f);
            }
            if (cardType == 55) {
                society.getBoxes().get(cb).setWater(true);
                labelWater.setText("On");
            }
            if (cardType == 56) {
                society.getBoxes().get(cb).setElectricity(true);
                labelElectricity.setText("On");
            }
            if (cardType == 57) {
                society.getBoxes().get(cb).setCellular(true);
                labelCellular.setText("On");
            }
            if (cardType == 58) {
                society.getBoxes().get(cb).setWifi(true);
                labelWifi.setText("On");
            }
            if (cardType == 59) {
                labelCelsius.setText("°C");
            }
            if (cardType == 60) {
                channels.get(Integer.parseInt(society.getBoxes().get(cb).getObjectsTest().get(objectObs).links.get(0))).setRating(channels.get(Integer.parseInt(society.getBoxes().get(cb).getObjectsTest().get(objectObs).links.get(0))).getRating() + 1);
                if (channels.get(Integer.parseInt(society.getBoxes().get(cb).getObjectsTest().get(objectObs).links.get(0))).getRating() > 10)
                {
                    channels.get(Integer.parseInt(society.getBoxes().get(cb).getObjectsTest().get(objectObs).links.get(0))).setRating(10);
                    uiNo.play();
                }
                labelRating.setText(channels.get(Integer.parseInt(society.getBoxes().get(cb).getObjectsTest().get(objectObs).links.get(0))).getRating()+"");
            }
            if (cardType == 61) {
                society.getIndi(cb, indiObs).getInterests().get(0).setHistory(society.getIndi(cb, indiObs).getInterests().get(0).getHistory() + cardCount);
            }
            if (cardType == 62) {
                society.getIndi(cb, indiObs).getInterests().get(0).setMystic(society.getIndi(cb, indiObs).getInterests().get(0).getMystic() + cardCount);
            }
        }
        else if (cardType == 0) {
            arrowHint.setVisible(false);
        }

        if (!"0".equals(dest)) {
            if (dest.equals("UP")) {
                society.getBoxes().get(cb).getObjectsTest().get(objectObs).setX(society.getBoxes().get(cb).getObjectsTest().get(objectObs).getX() - counter);
                society.getBoxes().get(cb).getObjectsTest().get(objectObs).setFixity(society.getBoxes().get(cb).getObjectsTest().get(objectObs).getFixity() + 2*counter);
                objects.get(objectObs).setOX(objects.get(objectObs).getOX() - counter);
                objects.get(objectObs).setOY(objects.get(objectObs).getOY() + 2*counter);
            } else if (dest.equals("DOWN")) {
                society.getBoxes().get(cb).getObjectsTest().get(objectObs).setX(society.getBoxes().get(cb).getObjectsTest().get(objectObs).getX() + counter);
                society.getBoxes().get(cb).getObjectsTest().get(objectObs).setFixity(society.getBoxes().get(cb).getObjectsTest().get(objectObs).getFixity() - 2*counter);
                objects.get(objectObs).setOX(objects.get(objectObs).getOX() + counter);
                objects.get(objectObs).setOY(objects.get(objectObs).getOY() - 2*counter);
            } else if (dest.equals("LEFT")) {
                society.getBoxes().get(cb).getObjectsTest().get(objectObs).setX(society.getBoxes().get(cb).getObjectsTest().get(objectObs).getX() - 2*counter);
                society.getBoxes().get(cb).getObjectsTest().get(objectObs).setFixity(society.getBoxes().get(cb).getObjectsTest().get(objectObs).getFixity() - counter);
                objects.get(objectObs).setOX(objects.get(objectObs).getOX() - 2*counter);
                objects.get(objectObs).setOY(objects.get(objectObs).getOY() - counter);
            } else if (dest.equals("RIGHT")) {
                society.getBoxes().get(cb).getObjectsTest().get(objectObs).setX(society.getBoxes().get(cb).getObjectsTest().get(objectObs).getX() + 2*counter);
                society.getBoxes().get(cb).getObjectsTest().get(objectObs).setFixity(society.getBoxes().get(cb).getObjectsTest().get(objectObs).getFixity() + counter);
                objects.get(objectObs).setOX(objects.get(objectObs).getOX() + 2*counter);
                objects.get(objectObs).setOY(objects.get(objectObs).getOY() + counter);
            }
        }
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            ((OrthographicCamera)stage.getCamera()).zoom += 0.02f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            ((OrthographicCamera)stage.getCamera()).zoom -= 0.02f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            ((OrthographicCamera)stage.getCamera()).translate(-3, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            ((OrthographicCamera)stage.getCamera()).translate(3, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            ((OrthographicCamera)stage.getCamera()).translate(0, -3, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            ((OrthographicCamera)stage.getCamera()).translate(0, 3, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            ((OrthographicCamera)stage.getCamera()).rotate(-rotationSpeed, 0, 0, 1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            ((OrthographicCamera)stage.getCamera()).rotate(rotationSpeed, 0, 0, 1);
        }
    }

    public static class Marker {
        //x1/y1 - координаты, дальше которых наступать уже нельзя (обычно равны начальным), х2/х3/у2/у3 - координаты крайних точек прямых-границ, х4/у4 - координаты, внутрь которых наступать нельзя (обычно конечные координаты)
        int x1, x2, x3, x4, y1, y2, y3, y4;

        public Marker(int x1, int x2, int x3, int x4, int y1, int y2, int y3, int y4) {
            this.x1 = x1;
            this.x2 = x2;
            this.x3 = x3;
            this.x4 = x4;
            this.y1 = y1;
            this.y2 = y2;
            this.y3 = y3;
            this.y4 = y4;
        }
    }

    public static class SortCard {
        public float y;
        public float x;
        public int myNum;

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public int getmyNum() {
            return myNum;
        }

        public void setmyNum(int myNum) {
            this.myNum = myNum;
        }

        public SortCard() {
        }

        public SortCard(float x, float y, int myNum) {
            this.x = x;
            this.y = y;
            this.myNum = myNum;
        }
    }

    public class ComparatorUser implements Comparator {

        public int compare(Object arg0, Object arg1) {
            SortCard user0 = (SortCard) arg0;
            SortCard user1 = (SortCard) arg1;

            int flag = -(String.valueOf(user0.getY()).compareTo(String.valueOf(user1.getY())));
            if (flag == 0) {
                return String.valueOf(user0.getX()).compareTo(String.valueOf(user1.getX()));
            }
            return flag;
        }
    }

    public void updateInterface(int type) {
        int[] arr = new int[] {18, 22, 41, 42, 43, 63, 64, 65}; //63,64,65 are for groups
        if (type == 18) {
            java.lang.StringBuilder s = new StringBuilder(langString.get("actionsCount") + ": " +
                    indis.get(indiObs).actions.size() + "\n");
            int sum = 0;
            if (indis.get(indiObs).delay != 0)
                sum = (int) (System.currentTimeMillis() - (int) indis.get(indiObs).delay);
            for (int i = 0; i < indis.get(indiObs).actions.size(); i++) {
                s.append(indis.get(indiObs).actions.get(i).action).append(", ")/*.append(indis.get(indiObs).actions.get(i).toString()).append(", ")*/.append(specialStatus(indis.get(indiObs).actions.get(i).action, indis.get(indiObs).actions.get(i))).append(", ").append(sum == 0 ? "0000" : sum).append("\n");
                sum -= actionDelay(indis.get(indiObs).actions.get(i).action, indiObs);
            }
            labelHint.setText("Global num:" + society.getIndi(cb, indiObs).globalNum
                    + "\n" + langString.get("genderSex") + ":" + ((society.getIndi(cb, indiObs).getGender() == 1) ? langString.get("male") : langString.get("female"))
                    + "\n" + langString.get("age") + ":" + society.getIndi(cb, indiObs).getAge()
                    + ", " + langString.get("yearBirth") + ": " + String.valueOf(yyyy - society.getIndi(cb, indiObs).getAge())
                    + "\n" + langString.get("budget") + ":" + society.getIndi(cb, indiObs).getWealth()
                    + "\n" + langString.get("address") + ":" + society.getIndi(cb, indiObs).getHomeX() + "/" + society.getIndi(cb, indiObs).getHomeY() + "/" + society.getIndi(cb, indiObs).getHomeZ()
                    + /*"\n" + "Жизненная цель:" + society.getIndiTest(cb, indiObs).getLifePurpose()
                        + */ "\n" + langString.get("selfEsteem") + ":" + society.getIndi(cb, indiObs).getSelfEsteem()
                    +  "\n" + langString.get("lovePts") + ":" + society.getIndi(cb, indiObs).getLove()
                    +  "\n" + "Тип помещения:" + society.getBoxes().get(cb).getProperty()
                    +  "\n\n" + s
            );
        }
        if (type == 22) {
            labelHint.setText("Внешность:" + society.getIndi(cb, indiObs).getSocialLifts().get(0).isAppearance()
                    + "\n" + "Карьера:" + society.getIndi(cb, indiObs).getSocialLifts().get(0).isCareer()
                    + "\n" + "Образование:" + society.getIndi(cb, indiObs).getSocialLifts().get(0).isEducation()
                    + "\n" + "Семья:" + society.getIndi(cb, indiObs).getSocialLifts().get(0).isMarriage());
        }
        /*length = String.valueOf(labelHint.getText());
        if (length.length() < 16) {
            labelHintTre.setStyle(new LabelStyle(fontOswaldBlack, Color.CYAN));
            labelHintTre.setPosition(400, Gdx.graphics.getHeight() - 175);
            //outerTableTva.setPosition(700, Gdx.graphics.getHeight()-175-outerTableTva.getWidth());
        } else {
            blockCount = String.valueOf(labelHint.getText()).split("\n").length;
            labelHint.setStyle(new LabelStyle(fontFran, Color.WHITE));
            labelHint.setPosition(20, Gdx.graphics.getHeight() - 550 - (blockCount * 12));
            //outerTableTva.setPosition(10, 280);
        }*/
        if (type == 41) {
            labelHint.setText(society.getJobs().get(society.getIndi(cb, indiObs).getJob()).getName() + "\n3арплата:" + society.getJobs().get(society.getIndi(cb, indiObs).getJob()).getSalary() +
                    "\nУдалёнка"
                    //"\nЧасы работы:" + (society.getJobs().get(society.getIndiTest(cb, indiObs).getJob()).getStartTime() + society.getJobs().get(society.getIndiTest(cb, indiObs).getJob()).getHours() >= 24 ? society.getJobs().get(society.getIndiTest(cb, indiObs).getJob()).getStartTime() + ":00 - " + (society.getJobs().get(society.getIndiTest(cb, indiObs).getJob()).getStartTime() + society.getJobs().get(society.getIndiTest(cb, indiObs).getJob()).getHours() - 24) + ":00" : society.getJobs().get(society.getIndiTest(cb, indiObs).getJob()).getStartTime() + ":00 - " + (society.getJobs().get(society.getIndiTest(cb, indiObs).getJob()).getStartTime() + society.getJobs().get(society.getIndiTest(cb, indiObs).getJob()).getHours()) + ":00")
                    + "\n");
            if (society.getIndi(cb, indiObs).getEducations().size() != 0) {
                labelHint.setText(labelHint.getText() + "\n\nОбразование:" + society.getIndi(cb, indiObs).getEducations().get(0).getSpeciality().name + ", " + society.getIndi(cb, indiObs).getEducations().get(0).getUniversity().name);
            }
            else {
                labelHint.setText(labelHint.getText() + "\n\nОбразования нет");
            }
        }
        if (type == 42) {
            str = "";
            if (society.getIndi(cb, indiObs).getScenarios().size() == 0) {labelHint.setText(langString.get("noScenarios"));}
            else {
                for (int q = 0; q<society.getIndi(cb, indiObs).getScenarios().size(); q++) {
                    try {
                        str = str + langString.get("scenario" + society.getIndi(cb, indiObs).getScenarios().get(q).getCode()) + ", стадия " + society.getIndi(cb, indiObs).getScenarios().get(q).getSt() + "\n";
                    }
                    catch (MissingResourceException e) {
                        str = str + "scenario " + society.getIndi(cb, indiObs).getScenarios().get(q).getCode();
                        if (society.getIndi(cb, indiObs).getScenarios().get(q).getClass() == SystemScenario.class) {
                            str = str + "\n  " + society.getIndi(cb, indiObs).getScenarios().get(q).getStr();
                        }
                    }
                }
                labelHint.setText(str);
            }
        }
        if (type == 43) {
            labelHint.setText("Влечение к мужчинам:" + society.getIndi(cb, indiObs).getGenderProps().get(0).getMaleAppetence()
                    + "\n" + "Влечение к женщинам:" + society.getIndi(cb, indiObs).getGenderProps().get(0).getFemaleAppetence()
                    + "\n" + "Желание иметь детей:" + society.getIndi(cb, indiObs).getGenderProps().get(0).getKidsWant()
            );
        }
        if (type == 63) {
            String str = "";
            Gdx.app.log("social systems size", society.getSocialSystems().size()+"");
            for (int i = 0; i < society.getSocialSystems().get(groupObs).screws.size(); i++) {
                str+=langString.get("level") + " " + i + ":\n";
                for (int j=0; j< society.getSocialSystems().get(groupObs).screws.get(i).size(); j++) {
                    int n = society.getSocialSystems().get(groupObs).screws.get(i).get(j).id;
                    try {
                        str += "  " + society.getIndi(cb, n-1).name + " " + society.getIndi(cb, n-1).surname + ", " + df.format(society.getSocialSystems().get(groupObs).screws.get(i).get(j).efficiency * 100) + "%\n";
                    }
                    catch (IndexOutOfBoundsException e) {
                        str += "  " + langString.get("consumers") + "\n";
                    }
                    for (int k=0; k<society.getSocialSystems().get(groupObs).screws.get(i).get(j).receive.size(); k++)
                        str+="    R:"+society.getSocialSystems().get(groupObs).screws.get(i).get(j).receive.get(k).type + ": " + society.getSocialSystems().get(groupObs).screws.get(i).get(j).receive.get(k).amount + "\n";
                    for (int k=0; k<society.getSocialSystems().get(groupObs).screws.get(i).get(j).transmit.size(); k++)
                        str+="    T:"+society.getSocialSystems().get(groupObs).screws.get(i).get(j).transmit.get(k).type + ": " + society.getSocialSystems().get(groupObs).screws.get(i).get(j).transmit.get(k).amount + "\n";
                    for (int k=0; k<society.getSocialSystems().get(groupObs).screws.get(i).get(j).etiquette.size(); k++)
                        str+="    E:"+society.getSocialSystems().get(groupObs).screws.get(i).get(j).etiquette.get(k) + "\n";
                    for (int k=0; k<society.getSocialSystems().get(groupObs).screws.get(i).get(j).hours.size(); k++)
                        str+="    H:"+society.getSocialSystems().get(groupObs).screws.get(i).get(j).hours.get(k).get(0) + ", hh:" +
                                society.getSocialSystems().get(groupObs).screws.get(i).get(j).hours.get(k).get(1) + ":00, for " +
                                society.getSocialSystems().get(groupObs).screws.get(i).get(j).hours.get(k).get(2) + " h.\n";
                }
            }
            labelHint.setText(str);
        }

        if (indiObs > -1) {labelAesthetics.setText(society.getIndi(cb, indiObs).getNeeds().get(0).getAesthetics() + " ");
            labelBladder.setText(society.getIndi(cb, indiObs).getNeeds().get(0).getBl() + " ");
            labelEducation.setText(society.getIndi(cb, indiObs).getNeeds().get(0).getEducation() + " ");
            labelEnergy.setText(society.getIndi(cb, indiObs).getNeeds().get(0).getEnergy() + " ");
            labelEnv.setText(society.getIndi(cb, indiObs).getNeeds().get(0).getEnvironment() + " ");
            labelFun.setText(society.getIndi(cb, indiObs).getNeeds().get(0).getFun() + " ");
            labelHunger.setText(society.getIndi(cb, indiObs).getNeeds().get(0).getHunger() + " ");
            labelHygiene.setText(society.getIndi(cb, indiObs).getNeeds().get(0).getHygiene() + " ");
            labelLove.setText(society.getIndi(cb, indiObs).getNeeds().get(0).getLove() + " ");
            labelPower.setText(society.getIndi(cb, indiObs).getNeeds().get(0).getPower() + " ");
            labelSafety.setText(society.getIndi(cb, indiObs).getNeeds().get(0).getProtection() + " ");
            labelShopping.setText(society.getIndi(cb, indiObs).getNeeds().get(0).getShopping() + " ");
            labelSocial.setText(society.getIndi(cb, indiObs).getNeeds().get(0).getSocial() + " ");
            labelSuccess.setText(society.getIndi(cb, indiObs).getNeeds().get(0).getSuccess() + " ");
            labelPolitics.setText(society.getIndi(cb, indiObs).getInterests().get(0).getPolitics() + " ");
            labelEconomics.setText(society.getIndi(cb, indiObs).getInterests().get(0).getEconomics() + " ");
            labelHealth.setText(society.getIndi(cb, indiObs).getInterests().get(0).getHealth() + " ");
            labelCrimes.setText(society.getIndi(cb, indiObs).getInterests().get(0).getCrimes() + " ");
            labelFashion.setText(society.getIndi(cb, indiObs).getInterests().get(0).getFun() + " ");
            labelCulture.setText(society.getIndi(cb, indiObs).getInterests().get(0).getCulture() + " ");
            labelFood.setText(society.getIndi(cb, indiObs).getInterests().get(0).getFood() + " ");
            labelScience.setText(society.getIndi(cb, indiObs).getInterests().get(0).getFashion() + " ");
            labelSport.setText(society.getIndi(cb, indiObs).getInterests().get(0).getSport() + " ");
            labelTravel.setText(society.getIndi(cb, indiObs).getInterests().get(0).getTravel() + " ");
            labelTechnics.setText(society.getIndi(cb, indiObs).getInterests().get(0).getTechnics() + " ");
            labelJob.setText(society.getIndi(cb, indiObs).getInterests().get(0).getWork() + " ");
            labelAnimals.setText(society.getIndi(cb, indiObs).getInterests().get(0).getAnimals() + " ");
            labelBooks.setText(society.getIndi(cb, indiObs).getInterests().get(0).getBooks() + " ");
            labelFilms.setText(society.getIndi(cb, indiObs).getInterests().get(0).getFilms() + " ");
            labelMusic.setText(society.getIndi(cb, indiObs).getInterests().get(0).getMusic() + " ");
            labelHistory.setText(society.getIndi(cb, indiObs).getInterests().get(0).getHistory() + " ");
            labelMystic.setText(society.getIndi(cb, indiObs).getInterests().get(0).getMystic() + " ");
            labelCaution.setText(society.getIndi(cb, indiObs).getTalents().get(0).getCaution() + " ");
            labelImagination.setText(society.getIndi(cb, indiObs).getTalents().get(0).getImagination() + " ");
            labelInfluence.setText(society.getIndi(cb, indiObs).getTalents().get(0).getInfluence() + " ");
            labelImmunity.setText(society.getIndi(cb, indiObs).getTalents().get(0).getImmunity() + " ");
            labelInsight.setText(society.getIndi(cb, indiObs).getTalents().get(0).getInsight() + " ");
            labelLogic.setText(society.getIndi(cb, indiObs).getTalents().get(0).getLogic() + " ");
            labelMemory.setText(society.getIndi(cb, indiObs).getTalents().get(0).getMemory() + " ");
            labelQuickness.setText(society.getIndi(cb, indiObs).getTalents().get(0).getQuickness() + " ");
            labelSpeech.setText(society.getIndi(cb, indiObs).getTalents().get(0).getSpeech() + " ");
            labelStamina.setText(society.getIndi(cb, indiObs).getTalents().get(0).getStamina() + " ");
        }
        if (labelCelsius.getText().toString().equals("°C")) {
            labelTemperature.setText(society.getBoxes().get(cb).getTemperature() + "");
        }
        else labelTemperature.setText(df.format(society.getBoxes().get(cb).getTemperature() * 1.8f + 32));
    }

    public void render(float delta) {
        FPS++;
        if (FPS == 5) {
            markers.clear();
            updateIndisList();
            autoCreatingMarkers();
            addingParameters();
            updateInterface(cardTva.type);
            FPS = 0;
            neededY = Gdx.graphics.getHeight() / 2;
            if (!bckMusic.isPlaying())
                changeMusic();
            switch (GAMEMODE) {
                case 2: {
                    recolorInterests(Color.CYAN);
                    recolorTalents(Color.SCARLET);
                    break;
                }
                case 3: {
                    recolorInterests(Color.SCARLET);
                    recolorTalents(Color.SCARLET);
                    break;
                }
            }
        }
        if (FPS == 1) {
            controller.act(delta);
            timeCount();
            timeFPS++;
        }
        if (timeFPS == 3) {
            if (dest == "CLOCK") {
                if (Gdx.input.getX() > 0 && Gdx.input.getX() < Gdx.graphics.getWidth()/6) dd--;
                else if (Gdx.input.getX() > Gdx.graphics.getWidth()/6 && Gdx.input.getX() < Gdx.graphics.getWidth()/6*2) hh--;
                else if (Gdx.input.getX() > Gdx.graphics.getWidth()/6*2 && Gdx.input.getX() < Gdx.graphics.getWidth()/6*3) min--;
                else if (Gdx.input.getX() > Gdx.graphics.getWidth()/6*3 && Gdx.input.getX() < Gdx.graphics.getWidth()/6*4) {min++;
                    for (int i = 0; i < indis.size(); i++) {
                        if (indis.get(i).delay > 1) indis.get(i).delay -= 1000;
                    }
                }
                else if (Gdx.input.getX() > Gdx.graphics.getWidth()/6*4 && Gdx.input.getX() < Gdx.graphics.getWidth()/6*5) {hh++;
                    for (int i = 0; i < indis.size(); i++) {
                        if (indis.get(i).delay > 1) indis.get(i).delay -= 60000;
                    }
                }
                else if (Gdx.input.getX() > Gdx.graphics.getWidth()/6*5 && Gdx.input.getX() < Gdx.graphics.getWidth()) dd++;
            }
            timeOutput();
            timeFPS = 0;}

        handleInput();
        if (!girl) {
            if (hh < 5 || hh > 22) {
                Gdx.gl.glClearColor(0, 0, 150 / 255f, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            } else if (hh == 5) {
                if (min < 31) {
                    Gdx.gl.glClearColor(min * 6 / 255f, (min * 3) / 255f, 150 / 255f, 1);
                    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                } else {
                    Gdx.gl.glClearColor((240 - (min - 30) * 3.3f) / 255f, min * 3 / 255f, (min * 6) / 255f, 1);
                    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                }
            } else if (hh == 22) {
                if (min < 31) {
                    Gdx.gl.glClearColor((140 + min * 3.5f) / 255f, (190 - min * 2.8f) / 255f, (255 - min * 8) / 255f, 1);
                    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                } else {
                    Gdx.gl.glClearColor((255 - ((min - 30) * 8f)) / 255f, (105 - (min - 30) * 2.8f) / 255f, (min - 30) * 4.8f / 255f, 1);
                    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                }
            } else {
                Gdx.gl.glClearColor(140 / 255f, 190 / 255f, 255 / 255f, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            }
        }
        else {
            Gdx.gl.glClearColor(1, 1, 1, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        }
        label.setText("FPS:" + Gdx.graphics.getFramesPerSecond() + ", " + String.valueOf(Gdx.app.getJavaHeap()) + ", " + String.valueOf(Gdx.app.getNativeHeap()));
        //label.setText(label.getText() + ", " + dd + "." + mm + "." + yyyy + ", " + hh + ":" + min);
        labelDebug.setPosition(0, y);
        if (girl) {
            girlStage.act(Gdx.graphics.getDeltaTime());
            girlStage.draw();
        }
        if (coreFunc.load != 1) {
            stage.act(Gdx.graphics.getDeltaTime());
            stage.draw();
        }
        //ПОПЫТКА СДЕЛАТЬ МЕТОД ДЛЯ НОРМАЛЬНОЙ ОТРИСОВКИ ПО СЛОЯМ
        if (FPS == 2) {
            sortList.clear();
            for (int q = 0; q < objects.size(); q++) {
                try {
                    switch (objectsTest.get(q).getType()) {
                        //case 4: sortList.add(new SortCard(objects.get(q).getOX() - objects.get(q).getTexture().getWidth(), objects.get(q).getOY() + 80, objects.get(q).getNum0()));
                        case 5: {
                            sortList.add(new SortCard(objects.get(q).getOX() - objects.get(q).getTexture().getWidth(), objects.get(q).getOY() + 1200, objects.get(q).getNum0()));
                            break;
                        }
                        case 6:
                            sortList.add(new SortCard(objects.get(q).getOX() - objects.get(q).getTexture().getWidth(), objects.get(q).getOY() + 1200, objects.get(q).getNum0()));
                            break;
                        case 7: {
                            if (new ArrayList<Integer>(Arrays.asList(48, 49, 50, 51)).contains(objectsTest.get(q).getAppearance())) {
                                sortList.add(new SortCard(objects.get(q).getOX() - objects.get(q).getTexture().getWidth(), objects.get(q).getOY() + 300, objects.get(q).getNum0()));
                                break;
                            }
                        }
                        default:
                            sortList.add(new SortCard(objects.get(q).getOX() - objects.get(q).getTexture().getWidth(), objects.get(q).getOY(), objects.get(q).getNum0()));

                    }
                } catch (IndexOutOfBoundsException ignored) {
                }
            }
            for (IndiActor indi : indis) {
                //try {
                    sortList.add(new SortCard(indi.getX(), indi.getY(), -indi.getMyNum()));
                /*} catch (NullPointerException ignored) {
                }*/
            }
            ComparatorUser comparator = new ComparatorUser();
            Collections.sort(sortList, comparator);

            if (sortList.size() != 0) {
                for (int q = 0; q < sortList.size(); q++) {
                    try {
                        if (sortList.get(q).myNum <= 0) {
                            indis.get(-(sortList.get(q).myNum)).toFront();
                        } else {
                            objects.get(sortList.get(q).myNum - 1).toFront();
                        }
                    } catch (IndexOutOfBoundsException ignored) {
                    }
                    //stage.getActors().get(sortList.get(q).myNum).toFront();
                }
            }
        }
        if (FPS == 1) {
            cardStage.act(Gdx.graphics.getDeltaTime());
        }
        if (indiObs!=-1 && indisTest.size() > 0) {
            if (indis.get(indiObs).status.split(":").length > 1) {
                labelHintTva.setText(indis.get(indiObs).status.split(":")[0]);
                labelHintFyra.setText(indis.get(indiObs).status.split(":")[1]);
            }
            else {
                labelHintTva.setText(indis.get(indiObs).status);
                labelHintFyra.setText("");
            }
        }
        cardStage.draw();
    }

    public void hide() {
    }

    public void dispose() {
    }

    public String rndSurnameM() {
        String[] arr = {"Alpha", "Beta", "Gamma", "Delta", "Epsilon", "Zeta", "Eta", "Theta", "Iota", "Kappa", "Lambda", "Mu", "Nu", "Omikron", "Pi", "Rho", "Sigma", "Tau", "Upsilon", "Phi", "Psi", "Omega"};
        return arr[rnd(arr.length - 1)];
    }

    public String rndSurnameF() {
        String[] arr = {"Takahashi", "Tanaka", "Akiyama", "Adachi", "Aokawa", "Chiba", "Enomoto", "Hiraoka", "Ohashi", "Funai"};
        return arr[rnd(arr.length - 1)];
    }

    public void timeCount() {
        if (System.currentTimeMillis() > currentTimeMil + speed) {
            currentTimeMil = System.currentTimeMillis();
            min++;
            if (min > 59) {min = 0; hh++;
                if (hh == 22 || hh == 5) {
                    bckMusic = Gdx.audio.newMusic(Gdx.files.internal("music/night.mp3"));
                    bckMusic.setVolume(1.5f * VOLUME);
                    bckMusic.play();
                }
            }
            if (hh>23) {hh = 0; dd++;
                GregorianCalendar date = new GregorianCalendar(yyyy, mm, dd);
                dw = date.get(GregorianCalendar.DAY_OF_WEEK)-1;
            }
            if (dd>31) {dd = 1; mm++;}
            if (mm>12) {mm = 1; yyyy++;}
            if (min<0) {min = 59; hh--;}
            if (dd<1) {dd = 31; mm--;}
            if (hh<0) {hh = 23; dd--;}
            if (mm<1) {mm = 12; yyyy--;}
            if (yyyy<1945) {yyyy = 1945;
                uiNo.play();
                uiDelay = System.currentTimeMillis() + 3000;
                labelCenter.setText(langString.get("stoneage"));
            }
            timeOutput();
        }
    }

    public void timeOutput() {
        labelGamePts.setText(gamePts+"");
        labelTime.setText("");
        if (hh < 10) {
            labelTime.setText("0");
        }
        labelTime.setText(labelTime.getText() + String.valueOf(hh) + ":");
        if (min < 10) {
            labelTime.setText(labelTime.getText() + "0");
        }
        labelTime.setText(labelTime.getText() + String.valueOf(min));
        labelDay.setText("\n");
        if (dd < 10) {
            labelDay.setText("0");
        }
        labelDay.setText(labelDay.getText() + String.valueOf(dd) + " ");
        if (mm == 1) {
            labelDay.setText(labelDay.getText() + "Jan");
        }
        if (mm == 2) {
            labelDay.setText(labelDay.getText() + "Feb");
        }
        if (mm == 3) {
            labelDay.setText(labelDay.getText() + "Mar");
        }
        if (mm == 4) {
            labelDay.setText(labelDay.getText() + "Apr");
        }
        if (mm == 5) {
            labelDay.setText(labelDay.getText() + "May");
        }
        if (mm == 6) {
            labelDay.setText(labelDay.getText() + "Jun");
        }
        if (mm == 7) {
            labelDay.setText(labelDay.getText() + "Jul");
        }
        if (mm == 8) {
            labelDay.setText(labelDay.getText() + "Aug");
        }
        if (mm == 9) {
            labelDay.setText(labelDay.getText() + "Sep");
        }
        if (mm == 10) {
            labelDay.setText(labelDay.getText() + "Oct");
        }
        if (mm == 11) {
            labelDay.setText(labelDay.getText() + "Nov");
        }
        if (mm == 12) {
            labelDay.setText(labelDay.getText() + "Dec");
        }
        labelDay.setText(labelDay.getText() + " " + yyyy+"\n");
        if (dw == 0) {
            labelDay.setText(labelDay.getText() + "Monday");
        }
        if (dw == 1) {
            labelDay.setText(labelDay.getText() + "Tuesday");
        }
        if (dw == 2) {
            labelDay.setText(labelDay.getText() + "Wednesday");
        }
        if (dw == 3) {
            labelDay.setText(labelDay.getText() + "Thursday");
        }
        if (dw == 4) {
            labelDay.setText(labelDay.getText() + "Friday");
        }
        if (dw == 5) {
            labelDay.setText(labelDay.getText() + "Saturday");
        }
        if (dw == 6) {
            labelDay.setText(labelDay.getText() + "Sunday");
        }
        if (dw == 7) {
            labelDay.setText(labelDay.getText() + "Eighth");
        }
    }

    private void changeMusic() {
        bckMusic.dispose();
        switch (rnd(5)) {
            case 1:
                bckMusic = Gdx.audio.newMusic(Gdx.files.internal("music/Chad_Crouch_-_Single-player.mp3"));
                break;
            case 2:
                bckMusic = Gdx.audio.newMusic(Gdx.files.internal("music/Phillip_Gross_-_02_-__la_carte.mp3"));
                break;
            case 3:
                bckMusic = Gdx.audio.newMusic(Gdx.files.internal("music/Phillip_Gross_-_03_-_Cest_la_vie.mp3"));
                break;
            case 4:
                bckMusic = Gdx.audio.newMusic(Gdx.files.internal("music/Phillip_Gross_-_04_-_Chapeau.mp3"));
                break;
            case 5:
                bckMusic = Gdx.audio.newMusic(Gdx.files.internal("music/Ryan_Andersen_-_Nah.mp3"));
                break;
            default:
                bckMusic = Gdx.audio.newMusic(Gdx.files.internal("music/Chad_Crouch_-_Single-player.mp3"));
                break;
        }
        bckMusic.play();
        bckMusic.setVolume(0.4f * VOLUME);
    }

    private void recolorTalents(Color color) {
        labelStamina.setStyle(new Label.LabelStyle(fontOswaldTre, color));
        labelCaution.setStyle(new Label.LabelStyle(fontOswaldTre, color));
        labelImagination.setStyle(new Label.LabelStyle(fontOswaldTre, color));
        labelImmunity.setStyle(new Label.LabelStyle(fontOswaldTre, color));
        labelInfluence.setStyle(new Label.LabelStyle(fontOswaldTre, color));
        labelInsight.setStyle(new Label.LabelStyle(fontOswaldTre, color));
        labelLogic.setStyle(new Label.LabelStyle(fontOswaldTre, color));
        labelMemory.setStyle(new Label.LabelStyle(fontOswaldTre, color));
        labelQuickness.setStyle(new Label.LabelStyle(fontOswaldTre, color));
        labelSpeech.setStyle(new Label.LabelStyle(fontOswaldTre, color));
    }

    private void recolorInterests(Color color) {
        labelPolitics.setStyle(new Label.LabelStyle(fontOswaldTre, color));
        labelEconomics.setStyle(new Label.LabelStyle(fontOswaldTre, color));
        labelHealth.setStyle(new Label.LabelStyle(fontOswaldTre, color));
        labelCrimes.setStyle(new Label.LabelStyle(fontOswaldTre, color));
        labelScience.setStyle(new Label.LabelStyle(fontOswaldTre, color));
        labelCulture.setStyle(new Label.LabelStyle(fontOswaldTre, color));
        labelFood.setStyle(new Label.LabelStyle(fontOswaldTre, color));
        labelFashion.setStyle(new Label.LabelStyle(fontOswaldTre, color));
        labelSport.setStyle(new Label.LabelStyle(fontOswaldTre, color));
        labelTechnics.setStyle(new Label.LabelStyle(fontOswaldTre, color));
        labelTravel.setStyle(new Label.LabelStyle(fontOswaldTre, color));
        labelJob.setStyle(new Label.LabelStyle(fontOswaldTre, color));
        labelAnimals.setStyle(new Label.LabelStyle(fontOswaldTre, color));
        labelBooks.setStyle(new Label.LabelStyle(fontOswaldTre, color));
        labelFilms.setStyle(new Label.LabelStyle(fontOswaldTre, color));
        labelMusic.setStyle(new Label.LabelStyle(fontOswaldTre, color));
        labelHistory.setStyle(new Label.LabelStyle(fontOswaldTre, color));
        labelMystic.setStyle(new Label.LabelStyle(fontOswaldTre, color));
    }

    public void removeExtraActors() {
        for(int i = cardStage.getActors().size; i > 0; i--)
        {
            try {if (cardStage.getActors().get(i).getClass() == ScenButton.class) {
                cardStage.getActors().get(i).remove();
            }}
            catch (IndexOutOfBoundsException e) {}
        }
        FileHandle fileTva = Gdx.files.internal("json/furn.txt");
        jsonStr = fileTva.readString();
        furn = json.fromJson(ArrayList.class, jsonStr);
        furnTable = new Table().left();
        for (int q=0; q<furn.size(); q++) {
            Json json = new Json();
            FileHandle file = Gdx.files.local("E/json/DEFAULTCITY/priceprops.txt");
            if (!file.exists()) {
                file= Gdx.files.internal("json/DEFAULTCITY/priceprops.txt");
            }
            jsonStr = file.readString();
            PriceProps prices = json.fromJson(PriceProps.class, jsonStr);
            furn.get(q).price = (int) (furn.get(q).price * prices.furnMarkup /* * Math.pow(0.9, yyyy - furn.get(q).prodStart)*/);
            furnTable.add(furn.get(q)).left();
            furnTable.row();
        }
    }

    public void loadGame(int x, int y, int z) {
        homex = x;
        homey = y;
        homez = z;
        coreFunc.load = 1;
        /*final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {*/
        Gdx.app.log("MODEFROMLOAD", String.valueOf(coreFunc.load));
        for (int i = 1; i < indis.size() + 1; i++) {
            IndiActor ii = indis.get(i - 1);
            ii.setStarted(false);
            indis.get(i - 1).setStarted(false);
        }
        indis.clear();
        objects.clear();
        stage.clear();
        objectsTest.clear();
        indisTest.clear();
        Json json = new Json();
        Gdx.app.log("local storage path", Gdx.files.getLocalStoragePath());
        FileHandle file = Gdx.files.local("E/json/room" + homex + "-" + homey + "-" + homez + ".txt");
        Box boxTva;
        if (file.exists()) {
            jsonStr = file.readString();
            Gdx.app.log("JSON", jsonStr);
            boxTva = json.fromJson(Box.class, jsonStr.replace("SocietyTest", "SocietyScreen"));
            uiDelay = System.currentTimeMillis() + 3000;
            labelCenter.setText(langString.get("saveLoaded"));
        } else {
            file = Gdx.files.internal("json/room" + homex + "-" + homey + "-" + homez + ".txt");
            if (file.exists()) {
                jsonStr = file.readString();
                boxTva = json.fromJson(Box.class, jsonStr.replace("SocietyTest", "SocietyScreen"));
                uiDelay = System.currentTimeMillis() + 3000;
                labelCenter.setText(langString.get("saveLoaded"));
            } else {
                boxTva = society.getBoxes().get(cb);
                uiNo.play();
            }
        }
        file = Gdx.files.local("E/json/extraactions.txt");
        if (file.exists()) {
            jsonStr = file.readString();
            extraActs = json.fromJson(ArrayList.class, jsonStr);
        } else {
            file = Gdx.files.internal("json/extraactions.txt");
            if (file.exists()) {
                jsonStr = file.readString();
                extraActs = json.fromJson(ArrayList.class, jsonStr);
            } else {
                extraActs.add(new com.peoplebox.additions.Action(15, 2, 0, 0,
                        new NeedsArray(), new NeedsArray(-5, -5, -15, -15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                        new InterestsArray(0, 0, 0, 0, 0, 0, 0, 0, 30, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                        new InterestsArray(0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                        new TalentsArray(), new TalentsArray(0, 0, 0, 0, 0, 0, 0, 0, 1, 0),
                        new ArrayList<Integer>(Arrays.asList(new Integer[]{22})), ""));
                extraActs.get(0).setRu(new String[]{"бегает на дорожке"});
                extraActs.get(0).setEn(new String[]{"runs on treadmill"});
                Gdx.app.error("EXTRAACTS", json.prettyPrint(extraActs));
            }
        }
        boxes.set(cb, boxTva);
        society.getBoxes().set(cb, boxTva);
        society.getBoxes().get(cb).setIndisTest(boxTva.getIndisTest());
        society.getBoxes().get(cb).setObjectsTest(boxTva.getObjectsTest());
        yyyy = boxTva.yyyy;
        mm = boxTva.mm;
        dd = boxTva.dd;
        hh = boxTva.hh;
        min = boxTva.min;
        objectsTest = boxTva.getObjectsTest();
        indisTest = society.getBoxes().get(cb).getIndisTest();
        SocietyScreen.Custom background;
        if (homex == 10 && homey == 10) {
            Pixmap pixmap200 = new Pixmap(Gdx.files.internal("city/office1/office1-9.png"));
            /*if (homez <= 9 && homez >= 2)
                pixmap200 = new Pixmap(Gdx.files.internal("city/office1/office1-" + homez + ".png"));*/
            Pixmap pixmap100 = new Pixmap(pixmap200.getWidth() * 2, pixmap200.getHeight() * 2, pixmap200.getFormat());
            pixmap100.drawPixmap(pixmap200,
                    0, 0, pixmap200.getWidth(), pixmap200.getHeight(),
                    0, 0, pixmap100.getWidth(), pixmap100.getHeight()
            );
            Texture texture = new Texture(pixmap100);
            background = new Custom(texture, -400,
                    -1800 + ((9 - homez) * 180));
            pixmap200 = new Pixmap(Gdx.files.internal("city/office1/office1r1.png"));
            pixmap100 = new Pixmap(pixmap200.getWidth() * 2, pixmap200.getHeight() * 2, pixmap200.getFormat());
            pixmap100.drawPixmap(pixmap200,
                    0, 0, pixmap200.getWidth(), pixmap200.getHeight(),
                    0, 0, pixmap100.getWidth(), pixmap100.getHeight()
            );
            texture = new Texture(pixmap100);
            walls = new Custom(texture, 414, 84);
            pixmap200.dispose();
            pixmap100.dispose();
        } else {
            String[] arr = new String[]{"background11.png", "background15.png", "background14.png", "background9.png"};
            background = new Custom(new Texture(arr[rnd(4) - 1]), -150, -270);
        }
        stage.addActor(background);
        //stage.addActor(walls);
        for (int i = 0; i < boxes.size(); i++) {
            for (int j = 0; j < objectsTest.size(); j++) {
                loadObject(i, j);
            }
        }
        for (int i = 0; i < boxes.size(); i++) {
            for (int j = 0; j < indisTest.size(); j++) {
                loadIndi(i, j);
            }
        }
        indiObs = -1;
        coreFunc.load = 0;
        tableMove.setZIndex(200);
        buttonAdd.setZIndex(200);
        json = new Json();
        Gdx.app.log("local storage path", Gdx.files.getLocalStoragePath());
        file = Gdx.files.local("E/json/globalnumcounter.txt");
        if (file.exists()) {
            jsonStr = file.readString();
            Gdx.app.log("JSON currentGlobalNum", jsonStr);
            currentGlobalNum = json.fromJson(Integer.class, jsonStr);
        } else {
            file = Gdx.files.internal("json/globalnumcounter.txt");
            if (file.exists()) {
                jsonStr = file.readString();
                Gdx.app.log("JSON currentGlobalNum", jsonStr);
                currentGlobalNum = json.fromJson(Integer.class, jsonStr);
            } else {
                currentGlobalNum = 0;
            }
        }
                /*try {
                    this.finalize();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });
        thread.start();*/
    }

    public void removeIndi(int n) {
        for (int i=1; i<indis.size()+1; i++) {
            IndiActor ii = indis.get(i-1);
            ii.setStarted(false);
            indis.get(i-1).setStarted(false);
        }
        for (int i=0; i<society.getIndisTest(cb).size(); i++) {
            ArrayList<Relation> relations = society.getIndi(cb, i).getRelations();
            for (int j=0; j<relations.size(); j++) {
                if (relations.get(j).getIndiNum() > n+1) {
                    relations.get(j).setIndiNum(relations.get(j).getIndiNum() - 1);
                }
                else if (relations.get(j).getIndiNum() == n+1) {
                    relations.remove(j);
                }
            }
        }
        for (int i=n+1; i<society.getIndisTest(cb).size(); i++) {
            society.getIndi(cb, i).setmyNum(i-1);
            indis.get(i).setmyNum(i-1);
        }
        society.getIndisTest(cb).remove(n);
        indis.remove(n);
        if (indiObs != 0) indiObs -= 1;
        for (int i=1; i<indis.size()+1; i++) {
            IndiActor ii = indis.get(i-1);
            ii.setStarted(true);
            indis.get(i-1).setStarted(true);
        }
    }

    static int getCheapestItem(int type) {
        int a = Integer.MAX_VALUE;
        for (int i = 0; i < furn.size(); i++) {
            if (furn.get(i).type == type && furn.get(i).price < a) {
                a = furn.get(i).price;
            }
        }
        return a;
    }

    static boolean isAlive(int num) {
        return society.getIndi(cb, num).alive;
    }

    void updateRelations(int myNum) {
        tableRelations.clear();
        for (int i = 0; i < society.getIndi(cb, myNum).getRelations().size(); i++) {
            int indiNum = society.getIndi(cb, myNum).getRelations().get(i).indiNum;
            tableRelations.add(new RelCard(indis.get(indiNum).region.getTexture(), society.getIndi(cb, indiNum).getName(), society.getIndi(cb, indiNum).getSurname(),
                    society.getIndi(cb, myNum).getRelations().get(i).getLevel(), i)).top().left();
            tableRelations.row();
        }
    }

    void updateIndisList() {
        if (coreFunc.load == 0) {
            tableIndis.clear();
            for (int i = 0; i < society.getIndisTest(cb).size(); i++) {
                tableIndis.add(new IndiCard(indis.get(i).region.getTexture(), society.getIndi(cb, i).getName(), society.getIndi(cb, i).getSurname(), i)).top().left();
                tableIndis.row();
            }
        }
    }

    void checkEfficiency(int a) {
        for (int i=0; i<4; i++)
            checkEfficiency(a, i);
    }

    void checkEfficiency(int a, int b) {
        HashMap<Integer, Integer> indisList = new HashMap<>();
        for (Indi indi : indisTest) {
            indisList.put(indi.globalNum, indi.myNum);
        }
        for (int i=0; i<society.getSocialSystems().get(a).screws.get(b).size(); i++) {
            double eff = 0;
            InterestsArray system = society.getSocialSystems().get(a).interests;
            InterestsArray indi = society.getIndi(cb, indisList.get(society.getSocialSystems().get(a).screws.get(b).get(i).id)).getInterests().get(0);
            TalentsArray systemT = society.getSocialSystems().get(a).talents;
            TalentsArray indiT = society.getIndi(cb, indisList.get(society.getSocialSystems().get(a).screws.get(b).get(i).id)).getTalents().get(0);
            eff += indi.getAnimals() / system.getAnimals();
            eff += indi.getAutos() / system.getAutos();
            eff += indi.getBooks() / system.getBooks();
            eff += indi.getCrimes() / system.getCrimes();
            eff += indi.getCulture() / system.getCulture();
            eff += indi.getDesign() / system.getDesign();
            eff += indi.getEconomics() / system.getEconomics();
            eff += indi.getFashion() / system.getFashion();
            eff += indi.getFilms() / system.getFilms();
            eff += indi.getFood() / system.getFood();
            eff += indi.getFun() / system.getFun();
            eff += indi.getGames() / system.getGames();
            eff += indi.getHealth() / system.getHealth();
            eff += indi.getHistory() / system.getHistory();
            eff += indi.getMusic() / system.getMusic();
            eff += indi.getMystic() / system.getMystic();
            eff += indi.getPhotos() / system.getPhotos();
            eff += indi.getPolitics() / system.getPolitics();
            eff += indi.getSport() / system.getSport();
            eff += indi.getTechnics() / system.getTechnics();
            eff += indi.getTravel() / system.getTravel();
            eff += indi.getWork() / system.getWork();
            eff += indiT.getCaution() / systemT.getCaution();
            eff += indiT.getImagination() / systemT.getImagination();
            eff += indiT.getImmunity() / systemT.getImmunity();
            eff += indiT.getInfluence() / systemT.getInfluence();
            eff += indiT.getInsight() / systemT.getInsight();
            eff += indiT.getLogic() / systemT.getLogic();
            eff += indiT.getMemory() / systemT.getMemory();
            eff += indiT.getQuickness() / systemT.getQuickness();
            eff += indiT.getSpeech() / systemT.getSpeech();
            eff += indiT.getStamina() / systemT.getStamina();
            society.getSocialSystems().get(a).screws.get(b).get(i).efficiency = eff / 31;
            for (int j=0; j<society.getSocialSystems().get(a).screws.get(b).get(i).transmit.size(); j++) {
                society.getSocialSystems().get(a).screws.get(b).get(i).transmit.get(j).amount *= society.getSocialSystems().get(a).screws.get(b).get(i).efficiency;
            }
        }
    }

    void addScrew(int id, int level, int num, Screw screw) {
        society.getSocialSystems().get(id).screws.get(level).add(screw);
        society.getIndi(cb, num).getScenarios().add(new SystemScenario(10, 1, id, society.getSocialSystems().get(id).name, new ArrayList<>(), new ArrayList<>()));
    }

    void addScrew(int id, int level, int num, Screw screw, ArrayList<Integer> startHours, ArrayList<Integer> durationHours) {
        society.getSocialSystems().get(id).screws.get(level).add(screw);
        society.getIndi(cb, num).getScenarios().add(new SystemScenario(10, 1, id, society.getSocialSystems().get(id).name, startHours, durationHours));
    }

    int offset(boolean x, int type, int app) {
        for (int i = 0; i < furn.size(); i++) {
            if (furn.get(i).appear == app && furn.get(i).type == type) {
                try {
                    return x ? furn.get(i).targetX : furn.get(i).targetY;
                }
                catch (NullPointerException e) {
                    return 0;
                }
            }
        }
        return 0;
    }

    ArrayList<Integer> lookExtraActs(String start, int num) {
        ArrayList<Integer> suit = new ArrayList<Integer>();
        Method method = null, methodIndi = null, methodAct = null;
        try {
            method = NeedsArray.class.getMethod("get" + start);
            methodIndi = Indi.class.getMethod("getNeeds");
            methodAct = com.peoplebox.additions.Action.class.getMethod("getStartNeeds");
        }
        catch (NoSuchMethodException e) { }
        try {
            method = InterestsArray.class.getMethod("get" + start);
            methodIndi = Indi.class.getMethod("getInterests");
            methodAct = com.peoplebox.additions.Action.class.getMethod("getStartInterests");
        }
        catch (NoSuchMethodException e) {}
        try {
            method = TalentsArray.class.getMethod("get" + start);
            methodIndi = Indi.class.getMethod("getTalents");
            methodAct = com.peoplebox.additions.Action.class.getMethod("getStartTalents");
        }
        catch (NoSuchMethodException e) {}
        Gdx.app.error("METHOD INVOCATIONS", method + " " + methodIndi + " " + methodAct);
        for (int i = 0; i<extraActs.size(); i++) {
            try {
                if ((Integer)method.invoke(methodAct.invoke(extraActs.get(i))) != 0
                        && (Integer)method.invoke(((ArrayList<Object>)methodIndi.invoke(society.getIndi(cb, num))).get(0))
                        >= (Integer)method.invoke(methodAct.invoke(extraActs.get(i)))) {
                    for (int j = 0; j < extraActs.get(i).getItems().size(); i++) {
                        if (checkUnocc(extraActs.get(i).getItems().get(j)) != 0) {
                            suit.add(i);
                            break;
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return suit;
    }
}