package com.peoplebox;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.peoplebox.additions.Action;
import com.peoplebox.additions.Custom;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.MissingResourceException;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.peoplebox.Addition.specialStatus;
import static com.peoplebox.ScreenshotFactory.saveScreenshot;
import static com.peoplebox.SocietyScreen.whichObject;

public class ActionRedactorScreen implements Screen, GestureDetector.GestureListener {
    final Game game;
    Custom indiActor;
    ArrayList<Action> extraActs = new ArrayList<>();
    Action action = new Action(19999, 3, 1, 0, 0,
            new SocietyScreen.NeedsArray(), new SocietyScreen.NeedsArray(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
            new SocietyScreen.InterestsArray(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
            new SocietyScreen.InterestsArray(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
            new SocietyScreen.TalentsArray(), new SocietyScreen.TalentsArray(0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
            new ArrayList<Integer>(), "");
    OrthographicCamera camera;
    String str = "";
    long delay = 0, sign = 0;
    protected Label label;
    protected BitmapFont font, fontFran;
    static I18NBundle langString;
    static Label labelGirl, labelAppuyez, labelHint, labelHintTva, labelHintFyra, labelCenter, labelDebug, labelControl, nameView,
            labelEvent, labelTime, labelDay, labelGamePts, labelReset, labelRandomScenario;
    static Label labelBladder, labelEnergy, labelHunger, labelEducation, labelEnv, labelFun, labelHygiene, labelLove, labelPower, labelSafety, labelShopping,
            labelSocial, labelAesthetics, labelSuccess;
    static Label labelPolitics, labelEconomics, labelHealth, labelCrimes, labelScience, labelCulture, labelFood, labelFashion, labelSport, labelTechnics,
            labelTravel, labelJob, labelAnimals, labelBooks, labelFilms, labelMusic, labelHistory, labelMystic;
    static Label labelCaution, labelImagination, labelImmunity, labelInfluence, labelInsight, labelLogic, labelMemory, labelQuickness, labelSpeech, labelStamina, labelTemperature,
            labelWifi, labelWater, labelElectricity, labelCellular, labelCelsius, labelAudience, labelRating, labelBroadcastStart, labelBroadcastFinish;
    Texture banner;
    double screenCoefW = 1.0;
    double screenCoefH = 1.0;
    final String FONT_CHARS = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяabcdefghijklmnopqrstuvwxyzÅÄÖåäöÂâÊêÎîÔôÛûÉéÀàÈèÙùАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"?`'<>";
    String lang;
    ArrayList<SocietyScreen.SortCard> sortList = new ArrayList<SocietyScreen.SortCard>();
    private static ArrayList<SocietyScreen.FurnCard> furn = new ArrayList<SocietyScreen.FurnCard>();
    ArrayList<SocietyScreen.Marker> markers = new ArrayList<SocietyScreen.Marker>();
    ArrayList<SocietyScreen.Dish> dishes = new ArrayList<SocietyScreen.Dish>(Arrays.asList(new SocietyScreen.Dish("Макароны с сыром", 10, 2), new SocietyScreen.Dish("Пирог с картофелем", 6, 4), new SocietyScreen.Dish("Панкейки", 7, 4), new SocietyScreen.Dish("Брауни", 12, 6), new SocietyScreen.Dish("Лазанья", 10, 5), new SocietyScreen.Dish("Пицца \"Маргарита\"", 8, 3), new SocietyScreen.Dish("Гаспачо", 15, 9), new SocietyScreen.Dish("Яблочный пирог", 7, 5)));
    ArrayList<SocietyScreen.ScenButton> scenButtons = new ArrayList<SocietyScreen.ScenButton>();
    int plus = 0, dd=1, mm=5, xxxx = 700, yyyy=100, hh=21, min=50, cardType = 0, wwww = rnd(4), speed = 1000, timeFPS = 0, cardCount = 1, VOLUME = 1;
    int neededY = 0; //по дефолту равна половине высоты экрана, переопределяется в рендере с каждой прорисовкой
    static long uiDelay, tapDelay, currentTimeMil = System.currentTimeMillis();
    DecimalFormat df = new DecimalFormat("#.##");
    static TextField codeField, surnameField, labelHintTre;
    enum HoldObject { Knife, Book, Spray, Mixer, Pan, Extinguisher, Phone1, Phone2, None };
    public static Stage stage, cardStage, girlStage;
    public ExtendViewport stageViewport, girlStageViewport;
    Texture t2, btnPreview, buttonBackground, NIMBUS, talkCloudEtt, talkCloudTva;
    static Sound talkEtt, talkTva, talkTre, poolEtt, poolTva, uiNo = Gdx.audio.newSound(Gdx.files.internal("sound/no.mp3")), oof= Gdx.audio.newSound(Gdx.files.internal("sound/oof.mp3"));
    int control = 0, indisN = 0, clickMode = 1, girlCard = 1, homeZZ = 0, y = -900, num = 0, num0 = 0, jobN = 0, indiObs = 0,
            objectObs = -1, adaptResTre = 4, card = 0;
    static int mode = 1, adaptRes = 5, popAct = 0, indisTestN = 0, FPS = 0, furnBefore = 0, ff=1, gamePts = 0;
    final static int cb = 0;
    static int GAMEMODE = 0;
    ArrayList needsOnly = new ArrayList<Integer>(Arrays.asList(3));
    float rotationSpeed = 0.5f;
    static double screenCoef = 1.0;
    static SocietyScreen.CoreFunc coreFunc;
    static Card cardEtt, cardTva;
    static Music bckMusic = Gdx.audio.newMusic(Gdx.files.internal("sound/oof.mp3"));
    static boolean LOADING, DEBUG = false, girl = false;
    static BitmapFont fontFranTva;
    BitmapFont fontFranTre;
    BitmapFont smallFontFran;
    BitmapFont smallFontFranRed;
    BitmapFont fontOswald;
    static BitmapFont fontOswaldFurnPrice;
    BitmapFont fontOswaldTre;
    BitmapFont fontOldGirl;
    BitmapFont fontOswaldBlack;
    static InputMultiplexer multiplexer;
    static ScrollPane scrollPane, scrollPaneTva, scrollPaneFurn, scrollPaneTre, scrollPaneRelations, scrollPaneIndis, scrollPaneActNums;
    static TextButton.TextButtonStyle tbsFyra, tbsFem, tbsTre, tbs;
    SocietyScreen.Custom panelLeft, panelEvent, iconEvent, arrowHint, panelRight, start, finish;
    CustomIcon btnRandomScenario, btnTrash;
    static TextButton applyCode, btnHelpEtt, btnHelpTva, girlYes, girlNo, btnUp, btnDown, btnRight, btnLeft,
            btnMoveOk, btnOverlay, btnRotate, btnMore, btnRemove, buttonBack, buttonRemove, buttonAdd;
    private TextureAtlas atlas, atlasTre;
    private Skin skin, skinTre;
    TextField.TextFieldStyle txtStyleTre;
    private static Table tableMove, tableDebugActions, tableNeeds, tableInterests, tableRoom, tableTalents, outerTable, outerTableTva, outerTableNeeds, iconTable,
            furnTable, outerTableFurn, tableRelations, outerTableRelations, tableRelationsChoose, tableIndis, outerTableIndis,
            tableActNums, outerTableActNums;
    private Group groupBuild = new Group();
    static SocietyScreen.Society society = new SocietyScreen.Society();
    Json json = new Json();
    static ArrayList<SocietyScreen.IndiActor> indis = new ArrayList<SocietyScreen.IndiActor>();
    static ArrayList<SocietyScreen.Indi> indisTest = new ArrayList<SocietyScreen.Indi>();
    static ArrayList<SocietyScreen.Box> boxes = new ArrayList<SocietyScreen.Box>();
    static ArrayList<SocietyScreen.ObjectActor> objects = new ArrayList<SocietyScreen.ObjectActor>();
    static ArrayList<SocietyScreen.ObjectTest> objectsTest = new ArrayList<SocietyScreen.ObjectTest>();
    private String jsonStr;

    public ActionRedactorScreen(final Game gam, String language) {
        lang = language;
        screenCoefW = Double.parseDouble(String.valueOf(Gdx.graphics.getWidth())) / 1080;
        screenCoefH = Double.parseDouble(String.valueOf(Gdx.graphics.getHeight())) / 1080;
        game = gam;

        font = new BitmapFont();
        FreeTypeFontGenerator generator;
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.characters = FONT_CHARS;
        parameter.size = 45;
        generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/fran.ttf"));
        fontFran = generator.generateFont(parameter);
        fontFran.setColor(Color.BLACK);

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
        parameter.size = 40;
        generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/fran.ttf"));
        fontFranTva = generator.generateFont(parameter);


        generator.dispose(); // don't forget to dispose to avoid memory leaks!

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

        if (lang == "ru") {
            langString = I18NBundle.createBundle(Gdx.files.internal("strings/strings_ru"));
        }
        else if (lang == "en") {
            langString = I18NBundle.createBundle(Gdx.files.internal("strings/strings_en"));
        }
        else if (lang == "fr") {
            langString = I18NBundle.createBundle(Gdx.files.internal("strings/strings_fr"));
        }
        else if (lang == "sv") {
            langString = I18NBundle.createBundle(Gdx.files.internal("strings/strings_sv"));
        }
        else {
            langString = I18NBundle.createBundle(Gdx.files.internal("strings/strings"));
        }
        delay = System.currentTimeMillis() + 4000;
        stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));
        banner = new Texture(Gdx.files.internal("ui/additionBack.png"));
        Gdx.input.setInputProcessor(stage);
        if (Gdx.graphics.getHeight() < 500 || Gdx.graphics.getWidth() < 1000) {
            cardStage = new Stage(new ScreenViewport());
            ((OrthographicCamera) cardStage.getCamera()).zoom = (((float) Gdx.graphics.getHeight() / 1080 + (float) Gdx.graphics.getWidth() / 1920) / 2);

        } else {
            cardStage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        }
        cardStage.getViewport().setScreenSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        FileHandle file = Gdx.files.local("E/json/extraactions.txt");
        if (file.exists()) {
            jsonStr = file.readString();
            extraActs = json.fromJson(ArrayList.class, jsonStr);
        } else {
            file = Gdx.files.internal("json/extraactions.txt");
            if (file.exists()) {
                jsonStr = file.readString();
                extraActs = json.fromJson(ArrayList.class, jsonStr);
            } else {
                extraActs.add(new com.peoplebox.additions.Action(10000, 15, 2, 0, 0,
                        new SocietyScreen.NeedsArray(), new SocietyScreen.NeedsArray(-5, -5, -15, -15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                        new SocietyScreen.InterestsArray(0, 0, 0, 0, 0, 0, 0, 0, 30, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                        new SocietyScreen.InterestsArray(0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                        new SocietyScreen.TalentsArray(), new SocietyScreen.TalentsArray(0, 0, 0, 0, 0, 0, 0, 0, 1, 0),
                        new ArrayList<Integer>(Arrays.asList(new Integer[]{22})), ""));
                extraActs.add(extraActs.get(0));

                extraActs.get(0).setRu(new String[]{"бегает на дорожке"});
                extraActs.get(0).setEn(new String[]{"runs on treadmill"});
            }
        }
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
        panelLeft = new SocietyScreen.Custom(new Texture("ui/null.png"), 0, 0);
        panelLeft.setVisible(true);
        cardStage.addActor(panelLeft);
        panelRight = new SocietyScreen.Custom(new Texture("ui/null.png"), Gdx.graphics.getWidth()-160, 0);
        panelRight.setVisible(false);
        cardStage.addActor(panelRight);
        cardEtt = new Card(0, langString.get("redactor"), 10, Gdx.graphics.getHeight() - 70);
        cardTva = new Card(0, "", 450, Gdx.graphics.getHeight() - 175);
        cardStage.addActor(cardEtt);
        cardStage.addActor(cardTva);
        cardTva.setVisible(false);
        createInterfacePeopleBOX();
    }

    void createInterfacePeopleBOX() {
        int a = rnd(9999);
        action.setEn(new String[1]);
        action.getEn()[0] = "test action " + a;
        action.setRu(new String[1]);
        action.getRu()[0] = "тестовое действие " + a;
        FileHandle fileTva = Gdx.files.internal("json/furn.txt");
        jsonStr = fileTva.readString();
        furn = json.fromJson(ArrayList.class, jsonStr);
        furnTable = new Table().left();
        ArrayList<Integer> types = new ArrayList<>();
        for (int q=0; q<furn.size(); q++) {
            furn.get(q).price = 0;
            if (!types.contains(furn.get(q).type)) {
                furnTable.add(furn.get(q)).left();
                furnTable.row();
            }
            types.add(furn.get(q).type);
        }
        for (int i = 0; i < furn.size(); i++) {
            if (i == 0)
                furn.get(i).setBounds(furn.get(i).getX(), furn.get(i).getY(), 550, 96);
            else
                furn.get(i).setBounds(furn.get(i).getX(), furn.get(i).getY(), 120, 96);
            final int ii = i;
            furn.get(i).addListener(new InputListener() {
                public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                    createObject(furn.get(ii).type, furn.get(ii).appear);
                    return true;
                }
            });
        }
        arrowHint = new SocietyScreen.Custom(new Texture("ui/arrows.png"), 540, Gdx.graphics.getHeight() / 2 - 122);
        arrowHint.setVisible(false);
        cardStage.addActor(arrowHint);
        start = new SocietyScreen.Custom(new Texture("ui/start.png"), 200, Gdx.graphics.getHeight() - 270);
        start.setBounds(200, Gdx.graphics.getHeight() - 270, 64, 64);
        start.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                recolorFields(Color.CYAN);
                if (cardTva.type >= 71 && cardTva.type <= 76)
                    cardTva.type = cardTva.type + cardTva.type % 2 - 1;
                return true;
            }
        });
        start.setVisible(false);
        cardStage.addActor(start);
        finish = new SocietyScreen.Custom(new Texture("ui/finish.png"), 300, Gdx.graphics.getHeight() - 270);
        finish.setBounds(300, Gdx.graphics.getHeight() - 270, 64, 64);
        finish.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                recolorFields(Color.WHITE);
                if (cardTva.type >= 71 && cardTva.type <= 76)
                    cardTva.type = cardTva.type + cardTva.type % 2;
                return true;
            }
        });
        finish.setVisible(false);
        cardStage.addActor(finish);
        multiplexer = new InputMultiplexer();
        GestureDetector gd = new GestureDetector(this);
        multiplexer.addProcessor(gd);
        multiplexer.addProcessor(cardStage);
        multiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(multiplexer);
        /*jsonStr = json.toJson(boxes.get(cb));
        fileTva.writeString(jsonStr, false);*/
        //final Custom panel = new Custom(new Texture("ui/wavypatternett.png"), Gdx.graphics.getWidth() - 900, 0);
        //talkEtt = Gdx.audio.newSound(Gdx.files.internal("sound/whitenoise.wav"));
        //talkTva = Gdx.audio.newSound(Gdx.files.internal("sound/pinknoise.wav"));
        screenCoef = Double.parseDouble(String.valueOf(Gdx.graphics.getHeight())) / 1080;
        Gdx.app.log("screenCoef", String.valueOf(screenCoef));
        TextField.TextFieldStyle txtstyle = new TextField.TextFieldStyle();
        txtstyle.font = fontOswald;
        txtstyle.fontColor = Color.WHITE;
        txtStyleTre = new TextField.TextFieldStyle();
        txtStyleTre.font = fontOswaldBlack;
        txtStyleTre.fontColor = Color.CYAN;
        codeField = new TextField("X", txtstyle);
        surnameField = new TextField("Y", txtstyle);
        codeField.setBounds(100, Gdx.graphics.getHeight() - 270, 300, 80);
        surnameField.setBounds(300, Gdx.graphics.getHeight() - 270, 200, 80);
        applyCode = new TextButton("OK", tbs);
        applyCode.setBounds(500, Gdx.graphics.getHeight() - 250, 50, 50);
        applyCode.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                try {
                    int a = Integer.valueOf(codeField.getText());
                    codeField.setText(a+"");
                    action.setCODE(a);
                }
                catch (NumberFormatException e) {
                    uiNo.play();
                }
                return true;
            }
        });
        applyCode.setVisible(false);
        cardStage.addActor(codeField);
        cardStage.addActor(surnameField);
        cardStage.addActor(applyCode);
        codeField.setVisible(false);
        surnameField.setVisible(false);
        iconTable = new Table();
        tableRelations = new Table().left();
        tableActNums = new Table().left();
        tableIndis = new Table().left();
        tableNeeds = new Table();
        indiActor = new Custom(new Texture("indis/woman3/woman3stands.png"));
        indiActor.setX(Gdx.graphics.getWidth()*2/3);
        indiActor.setY(Gdx.graphics.getHeight()/2);
        cardStage.addActor(indiActor);
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
        CustomIcon iconAest = new CustomIcon(new Texture("icons2/aesthetics.png"), langString.get("aesthetics"));
        tableNeeds.add(iconAest);
        labelAesthetics = new Label("0", new Label.LabelStyle(fontOswaldTre, Color.CYAN));
        labelAesthetics.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 26; neededY = (int) labelAesthetics.getY();
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
                //return true;
            }
        });
        tableNeeds.add(labelAesthetics);
        CustomIcon iconBladder = new CustomIcon(new Texture("icons2/bladder.png"), langString.get("bladder"));
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
        labelBladder = new Label("0", new Label.LabelStyle(fontOswaldTre, Color.CYAN));
        labelBladder.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 27; neededY = (int) labelBladder.getY();
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
                //return true;
            }
        });
        tableNeeds.add(labelBladder);
        CustomIcon iconEducation = new CustomIcon(new Texture("icons2/education.png"), langString.get("education"));
        tableNeeds.add(iconEducation);
        labelEducation = new Label("0", new Label.LabelStyle(fontOswaldTre, Color.CYAN));
        labelEducation.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 28; neededY = (int) labelEducation.getY();
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
                //return true;
            }
        });
        tableNeeds.add(labelEducation);
        tableNeeds.row();
        CustomIcon iconEnergy = new CustomIcon(new Texture("icons2/energy.png"), langString.get("energy"));
        tableNeeds.add(iconEnergy);
        labelEnergy = new Label("0", new Label.LabelStyle(fontOswaldTre, Color.CYAN));
        labelEnergy.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 29; neededY = (int) labelEnergy.getY();
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
                //return true;
            }
        });
        tableNeeds.add(labelEnergy);
        CustomIcon iconEnv = new CustomIcon(new Texture("icons2/env.png"), langString.get("environment"));
        //tableNeeds.add(iconEnv);
        labelEnv = new Label("0", new Label.LabelStyle(fontOswaldTre, Color.CYAN));
        labelEnv.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 30; neededY = (int) labelEnv.getY();
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
                //return true;
            }
        });
        //tableNeeds.add(labelEnv);
        CustomIcon iconFun = new CustomIcon(new Texture("icons2/fun.png"), langString.get("fun"));
        tableNeeds.add(iconFun);
        labelFun = new Label("0", new Label.LabelStyle(fontOswaldTre, Color.CYAN));
        labelFun.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 31; neededY = (int) labelFun.getY();
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
                //return true;
            }
        });
        tableNeeds.add(labelFun);
        CustomIcon iconHunger = new CustomIcon(new Texture("icons2/hunger.png"), langString.get("hunger"));
        tableNeeds.add(iconHunger);
        labelHunger = new Label("0", new Label.LabelStyle(fontOswaldTre, Color.CYAN));
        labelHunger.setBounds(labelHunger.getX(), labelHunger.getY(), 40, 40);
        labelHunger.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 32; neededY = (int) labelHunger.getY();
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
                //return true;
            }
        });
        tableNeeds.add(labelHunger);
        tableNeeds.row();



        CustomIcon iconHygiene = new CustomIcon(new Texture("icons2/hygiene.png"), langString.get("hygiene"));
        tableNeeds.add(iconHygiene);
        labelHygiene = new Label("0", new Label.LabelStyle(fontOswaldTre, Color.CYAN));
        labelHygiene.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 33; neededY = (int) labelHygiene.getY();
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
                //return true;
            }
        });
        tableNeeds.add(labelHygiene);
        CustomIcon iconLove = new CustomIcon(new Texture("icons2/love.png"), langString.get("love"));
        tableNeeds.add(iconLove);
        labelLove = new Label("0", new Label.LabelStyle(fontOswaldTre, Color.CYAN));
        labelLove.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 34; neededY = (int) labelLove.getY()+64;
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
                //return true;
            }
        });
        tableNeeds.add(labelLove);

        CustomIcon iconPower = new CustomIcon(new Texture("icons2/power.png"), langString.get("power"));
        //tableNeeds.add(iconPower);
        labelPower = new Label("0", new Label.LabelStyle(fontOswaldTre, Color.CYAN));
        labelPower.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 35; neededY = (int) labelPower.getY()+64;
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
                //return true;
            }
        });
        //tableNeeds.add(labelPower);
        CustomIcon iconSafety = new CustomIcon(new Texture("icons2/safety.png"), langString.get("protection"));
        tableNeeds.add(iconSafety);
        labelSafety = new Label("0", new Label.LabelStyle(fontOswaldTre, Color.CYAN));
        labelSafety.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 36; neededY = (int) labelSafety.getY()+64;
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
                //return true;
            }
        });
        tableNeeds.add(labelSafety);
        tableNeeds.row();
        CustomIcon iconShopping = new CustomIcon(new Texture("icons2/shopping.png"), langString.get("shopping"));
        tableNeeds.add(iconShopping);
        labelShopping = new Label("0", new Label.LabelStyle(fontOswaldTre, Color.CYAN));
        labelShopping.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 37; neededY = (int) labelShopping.getY()+64;
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
                //return true;
            }
        });
        tableNeeds.add(labelShopping);
        //tableNeeds.row();
        CustomIcon iconSocial = new CustomIcon(new Texture("icons2/social.png"), langString.get("social"));
        tableNeeds.add(iconSocial);
        labelSocial = new Label("0", new Label.LabelStyle(fontOswaldTre, Color.CYAN));
        labelSocial.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 38; neededY = (int) labelSocial.getY()+124;
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
                //return true;
            }
        });
        tableNeeds.add(labelSocial);
        CustomIcon iconSuccess = new CustomIcon(new Texture("icons2/success.png"), langString.get("success"));
        tableNeeds.add(iconSuccess);
        labelSuccess = new Label("0", new Label.LabelStyle(fontOswaldTre, Color.CYAN));
        labelSuccess.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 39; neededY = (int) labelSuccess.getY()+124;
                return true;
            }
            public void touchUp(InputEvent event, float x1, float y1, int pointer, int button) {
                cardType = 0;
                //return true;
            }
        });
        tableNeeds.add(labelSuccess);

        tableNeeds.row();

        //scrollPaneNeeds = new ScrollPane(tableNeeds);
        //outerTableNeeds = new Table();
        //outerTableNeeds.add(scrollPaneNeeds).expand().left().top();

        tableInterests = new Table().top().left();
        CustomIcon iconPolitics = new CustomIcon(new Texture("icons2/politics.png"), langString.get("politics"));
        tableInterests.add(iconPolitics);
        labelPolitics = new Label("0", new Label.LabelStyle(fontOswaldTre, needsOnly.contains(GAMEMODE) ? Color.SCARLET : needsOnly.contains(GAMEMODE) ? Color.SCARLET : Color.CYAN));
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
        CustomIcon iconEconomics = new CustomIcon(new Texture("icons2/economics.png"), langString.get("economics"));
        tableInterests.add(iconEconomics);
        labelEconomics = new Label("0", new Label.LabelStyle(fontOswaldTre, needsOnly.contains(GAMEMODE) ? Color.SCARLET : Color.CYAN));
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
        CustomIcon iconHealth = new CustomIcon(new Texture("icons2/health.png"), langString.get("health"));
        tableInterests.add(iconHealth);
        labelHealth = new Label("0", new Label.LabelStyle(fontOswaldTre, needsOnly.contains(GAMEMODE) ? Color.SCARLET : Color.CYAN));
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
        CustomIcon iconCrimes = new CustomIcon(new Texture("icons2/crime.png"), langString.get("crimes"));
        tableInterests.add(iconCrimes);
        labelCrimes = new Label("0", new Label.LabelStyle(fontOswaldTre, needsOnly.contains(GAMEMODE) ? Color.SCARLET : Color.CYAN));
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
        CustomIcon iconScience = new CustomIcon(new Texture("icons2/science.png"), langString.get("science"));
        tableInterests.add(iconScience);
        labelScience = new Label("0", new Label.LabelStyle(fontOswaldTre, needsOnly.contains(GAMEMODE) ? Color.SCARLET : Color.CYAN));
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
        CustomIcon iconCulture = new CustomIcon(new Texture("icons2/culture.png"), langString.get("culture"));
        tableInterests.add(iconCulture);
        labelCulture = new Label("0", new Label.LabelStyle(fontOswaldTre, needsOnly.contains(GAMEMODE) ? Color.SCARLET : Color.CYAN));
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
        CustomIcon iconFood = new CustomIcon(new Texture("icons2/food.png"), langString.get("food"));
        tableInterests.add(iconFood);
        labelFood = new Label("0", new Label.LabelStyle(fontOswaldTre, needsOnly.contains(GAMEMODE) ? Color.SCARLET : Color.CYAN));
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
        CustomIcon iconFashion = new CustomIcon(new Texture("icons2/fashion.png"), langString.get("fashion"));
        tableInterests.add(iconFashion);
        labelFashion = new Label("0", new Label.LabelStyle(fontOswaldTre, needsOnly.contains(GAMEMODE) ? Color.SCARLET : Color.CYAN));
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
        CustomIcon iconSport = new CustomIcon(new Texture("icons2/sport.png"), langString.get("sport"));
        tableInterests.add(iconSport);
        labelSport = new Label("0", new Label.LabelStyle(fontOswaldTre, needsOnly.contains(GAMEMODE) ? Color.SCARLET : Color.CYAN));
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
        CustomIcon iconTravel = new CustomIcon(new Texture("icons2/travel.png"), langString.get("travel"));
        tableInterests.add(iconTravel);
        labelTravel = new Label("0", new Label.LabelStyle(fontOswaldTre, needsOnly.contains(GAMEMODE) ? Color.SCARLET : Color.CYAN));
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
        CustomIcon iconTechnics = new CustomIcon(new Texture("icons2/technics.png"), langString.get("technics"));
        tableInterests.add(iconTechnics);
        labelTechnics = new Label("0", new Label.LabelStyle(fontOswaldTre, needsOnly.contains(GAMEMODE) ? Color.SCARLET : Color.CYAN));
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
        CustomIcon iconJob = new CustomIcon(new Texture("icons2/work.png"), langString.get("work"));
        tableInterests.add(iconJob);
        labelJob = new Label("0", new Label.LabelStyle(fontOswaldTre, needsOnly.contains(GAMEMODE) ? Color.SCARLET : Color.CYAN));
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
        CustomIcon iconAnimals = new CustomIcon(new Texture("icons2/animals.png"), langString.get("animals"));
        tableInterests.add(iconAnimals);
        labelAnimals = new Label("0", new Label.LabelStyle(fontOswaldTre, needsOnly.contains(GAMEMODE) ? Color.SCARLET : Color.CYAN));
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
        CustomIcon iconBooks = new CustomIcon(new Texture("icons2/books.png"), langString.get("books"));
        tableInterests.add(iconBooks);
        labelBooks = new Label("0", new Label.LabelStyle(fontOswaldTre, needsOnly.contains(GAMEMODE) ? Color.SCARLET : Color.CYAN));
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
        CustomIcon iconFilms = new CustomIcon(new Texture("icons2/films.png"), langString.get("films"));
        tableInterests.add(iconFilms);
        labelFilms = new Label("0", new Label.LabelStyle(fontOswaldTre, needsOnly.contains(GAMEMODE) ? Color.SCARLET : Color.CYAN));
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
        CustomIcon iconMusic = new CustomIcon(new Texture("icons2/music.png"), langString.get("music"));
        tableInterests.add(iconMusic);
        labelMusic = new Label("0", new Label.LabelStyle(fontOswaldTre, needsOnly.contains(GAMEMODE) ? Color.SCARLET : Color.CYAN));
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
        CustomIcon iconHistory = new CustomIcon(new Texture("icons2/hourglass.png"), langString.get("history"));
        tableInterests.add(iconHistory);
        labelHistory = new Label("0", new Label.LabelStyle(fontOswaldTre, needsOnly.contains(GAMEMODE) ? Color.SCARLET : Color.CYAN));
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
        CustomIcon iconMystic = new CustomIcon(new Texture("icons2/mystic.png"), langString.get("mystic"));
        tableInterests.add(iconMystic);
        labelMystic = new Label("0", new Label.LabelStyle(fontOswaldTre, needsOnly.contains(GAMEMODE) ? Color.SCARLET : Color.CYAN));
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
        CustomIcon iconCaution = new CustomIcon(new Texture("icons2/caution.png"), langString.get("caution"));
        tableTalents.add(iconCaution);
        labelCaution = new Label("0", new Label.LabelStyle(fontOswaldTre, Color.CYAN));
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
        CustomIcon iconImagination = new CustomIcon(new Texture("icons2/imagination.png"), langString.get("imagination"));
        tableTalents.add(iconImagination);
        labelImagination = new Label("0", new Label.LabelStyle(fontOswaldTre, Color.CYAN));
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
        CustomIcon iconImmunity = new CustomIcon(new Texture("icons2/immunity.png"), langString.get("immunity"));
        tableTalents.add(iconImmunity);
        labelImmunity = new Label("0", new Label.LabelStyle(fontOswaldTre, Color.CYAN));
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
        CustomIcon iconInfluence = new CustomIcon(new Texture("icons2/influence.png"), langString.get("influence"));
        tableTalents.add(iconInfluence);
        labelInfluence = new Label("0", new Label.LabelStyle(fontOswaldTre, Color.CYAN));
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
        CustomIcon iconInsight = new CustomIcon(new Texture("icons2/insight.png"), langString.get("insight"));
        tableTalents.add(iconInsight);
        labelInsight = new Label("0", new Label.LabelStyle(fontOswaldTre, Color.CYAN));
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
        CustomIcon iconLogic = new CustomIcon(new Texture("icons2/logic.png"), langString.get("logic"));
        tableTalents.add(iconLogic);
        labelLogic = new Label("0", new Label.LabelStyle(fontOswaldTre, Color.CYAN));
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
        CustomIcon iconMemory = new CustomIcon(new Texture("icons2/memory.png"), langString.get("memory"));
        tableTalents.add(iconMemory);
        labelMemory = new Label("0", new Label.LabelStyle(fontOswaldTre, Color.CYAN));
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
        CustomIcon iconQuickness = new CustomIcon(new Texture("icons2/quickness.png"), langString.get("quickness"));
        tableTalents.add(iconQuickness);
        labelQuickness = new Label("0", new Label.LabelStyle(fontOswaldTre, Color.CYAN));
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
        CustomIcon iconSpeech = new CustomIcon(new Texture("icons2/speech.png"), langString.get("speech"));
        tableTalents.add(iconSpeech);
        labelSpeech = new Label("0", new Label.LabelStyle(fontOswaldTre, Color.CYAN));
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
        CustomIcon iconStamina = new CustomIcon(new Texture("icons2/stamina.png"), langString.get("stamina"));
        tableTalents.add(iconStamina);
        labelStamina = new Label("0", new Label.LabelStyle(fontOswaldTre, Color.CYAN));
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
        CustomIcon iconWater = new CustomIcon(new Texture("icons2/water.png"), "On");
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
        CustomIcon iconElectricity = new CustomIcon(new Texture("icons2/electricity.png"), "On");
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
        CustomIcon iconTelephone = new CustomIcon(new Texture("icons2/telephone.png"), "On");
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
        CustomIcon iconWifi = new CustomIcon(new Texture("icons2/wifi.png"), "On");
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
        CustomIcon iconTemp = new CustomIcon(new Texture("icons2/thermometer.png"), "");
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
        //cardStage.addActor(girlYes);
        //cardStage.addActor(girlNo);

        //final SocietyTest.Indi deadIndi = new SocietyTest.Indi("", "", 1, 0, 0, -1, -1, -1, 0, 0, 0, indiObs+1, "");
        ArrayList<SocietyScreen.NeedsArray> needs = new ArrayList<SocietyScreen.NeedsArray>();
        needs.add(new SocietyScreen.NeedsArray(100,100,100,100,100,100,100,100,100,100,100,100,100,100));


        Skin skinTva = new Skin(Gdx.files.internal("ui/ui.json"));


        //skinTva.add("default-horizontal", new Slider.SliderStyle());
        //skinTva.add("default-slider", new Texture("ui/default-slider.9.png"));
        //skinTva.add("default-slider-knob", new Texture("ui/default-slider-knob.png"));

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
        labelCenter.setPosition(660, 80);

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
        /*labelHintTva = new Label("new action", new Label.LabelStyle(fontOswaldBlack, Color.CYAN));
        labelHintTva.setPosition(10, Gdx.graphics.getHeight()-135);
        cardStage.addActor(labelHintTva);*/
        labelHintFyra = new Label("", new Label.LabelStyle(fontOswald, new Color(0xff341cff)));
        labelHintFyra.setPosition(4, Gdx.graphics.getHeight()-155);
        cardStage.addActor(labelHintFyra);
        labelHintTre = new TextField("new action", txtStyleTre);
        //labelHintTre.setPosition(400, Gdx.graphics.getHeight()-175);
        labelHintTre.setBounds(10, Gdx.graphics.getHeight()-135, 400, 80);
        //labelHintTre.setVisible(false);
        cardStage.addActor(labelHintTre);
        iconTable.row();
        labelControl = new Label("Control Panel", new Label.LabelStyle(fontOswald, Color.WHITE));
        labelControl.setText(langString.get("controlPanel"));
        labelControl.setPosition(Gdx.graphics.getWidth()-635, Gdx.graphics.getHeight()-65);
        labelControl.setVisible(false);
        Slider slider = new Slider(0, 100, 1, false, skinTva);
        slider.setBounds(0,0, 200, 40);


        final Group groupGender = new Group();
        //final Table tableNeeds = new Table();


        CustomIcon btnMain = new CustomIcon(new Texture("uiTva/main.png"));
        btnMain.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (indiObs > -1) {
                    hideTables(false);
                    outerTable.setSize(580, 680*Float.parseFloat(String.valueOf(screenCoef * screenCoef)));
                    cardTva.setType(18); codeField.setVisible(true); applyCode.setVisible(true);
                    outerTable.setVisible(true);
                    codeField.setText(action.getCODE() + "");
                    codeField.setVisible(true);
                }
                else uiNo.play();
            }
        });
        iconTable.add(btnMain);
        iconTable.row();
        CustomIcon btnStrings = new CustomIcon(new Texture("uiTva/strings.png"));
        btnStrings.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (indiObs > -1) {
                    hideTables(false);
                    outerTable.setSize(580, 680*Float.parseFloat(String.valueOf(screenCoef * screenCoef)));
                    cardTva.setType(18); codeField.setVisible(true); applyCode.setVisible(true);
                    outerTable.setVisible(true);
                    labelReset.setVisible(true);
                }
                else uiNo.play();
            }
        });
        iconTable.add(btnStrings);
        iconTable.row();
        labelReset = new Label("RESET ACTIONS", new Label.LabelStyle(fontOswald, Color.WHITE));
        labelReset.setPosition(170, Gdx.graphics.getHeight() - 340);
        labelRandomScenario = new Label(langString.get("newScenario"), new Label.LabelStyle(fontOswald, Color.WHITE));
        labelRandomScenario.setPosition(170, Gdx.graphics.getHeight() - 270);
        btnRandomScenario = new CustomIcon(new Texture("uiTva/reset.png"));
        btnRandomScenario.setBounds(100, Gdx.graphics.getHeight() - 275, 64, 64);
        btnRandomScenario.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {

            }
        });
        //cardStage.addActor(labelReset);
        //btnReset.setVisible(false);
        cardStage.addActor(btnRandomScenario);
        cardStage.addActor(labelRandomScenario);
        btnRandomScenario.setVisible(false);
        labelRandomScenario.setVisible(false);
        labelReset.setVisible(false);
        CustomIcon btnNeeds = new CustomIcon(new Texture("uiTva/needs.png"));
        btnNeeds.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                int a = cardTva.type;
                hideTables(true);
                if (a >= 71 && a <= 76) {
                    a = a % 2;
                    cardTva.type = 71 - a + 1;
                }
                else cardTva.type = 71;
                outerTableNeeds.setVisible(true);
                start.setVisible(true);
                finish.setVisible(true);
            }
        });
        iconTable.add(btnNeeds);
        iconTable.row();
        CustomIcon btnTalents = new CustomIcon(new Texture("uiTva/talents.png"));
        btnTalents.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                int a = cardTva.type;
                hideTables(true);
                if (a >= 71 && a <= 76) {
                    a = a % 2;
                    cardTva.type = 73 - a + 1;
                }
                else cardTva.type = 73;
                tableTalents.setVisible(true);
                start.setVisible(true);
                finish.setVisible(true);
            }
        });
        iconTable.add(btnTalents);
        iconTable.row();
        CustomIcon btnInterests = new CustomIcon(new Texture("uiTva/interests.png"));
        btnInterests.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                int a = cardTva.type;
                hideTables(true);
                if (a >= 71 && a <= 76) {
                    a = a % 2;
                    cardTva.type = 75 - a + 1;
                }
                else cardTva.type = 75;
                tableInterests.setVisible(true);
                start.setVisible(true);
                finish.setVisible(true);
            }
        });
        iconTable.add(btnInterests);
        iconTable.row();/*
        final CustomIcon btnRels = new CustomIcon(new Texture("uiTva/relations.png"));
        btnRels.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                hideTables(true);
                cardTva.setType(21);
                outerTableRelations.setVisible(true);
            }
        });
        iconTable.add(btnRels);
        iconTable.row();*/
        CustomIcon btnGenderTva = new CustomIcon(new Texture("uiTva/loverel.png"));
        btnGenderTva.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                hideTables(false);
                cardTva.setType(43);
            }
        });
        //iconTable.add(btnGenderTva);
        //iconTable.row();
        CustomIcon btnLifts = new CustomIcon(new Texture("uiTva/sociallifts.png"));
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
        CustomIcon btnJob = new CustomIcon(new Texture("uiTva/career.png"));
        btnJob.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                hideTables(false);
                cardTva.setType(41);
            }
        });
        //iconTable.add(btnJob);
        //iconTable.row();
        CustomIcon btnInits = new CustomIcon(new Texture("uiTva/inits.png"));
        btnInits.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                hideTables(false);
                cardTva.setType(25);
            }
        });
        //iconTable.add(btnInits);
        //iconTable.row();
        CustomIcon btnScen = new CustomIcon(new Texture("uiTva/scenario.png"));
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
        //iconTable.add(btnScen);
        //iconTable.row();
        CustomIcon btnRoomTre = new CustomIcon(new Texture("uiTva/room.png"));
        btnRoomTre.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                hideTables(true);
                outerTableFurn.setVisible(true);
            }
        });
        iconTable.add(btnRoomTre);
        iconTable.row();
        btnTrash = new CustomIcon(new Texture("uiTva/trash.png"));
        btnTrash.setBounds(570, Gdx.graphics.getHeight()-255, 64, 64);
        btnTrash.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {

            }
        });
        btnTrash.setVisible(false);
        cardStage.addActor(btnTrash);
        CustomIcon btnControl = new CustomIcon(new Texture("uiTva/pause.png"));
        btnControl.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                hideTables(true);
                panelLeft.setTexture(new Texture(Gdx.files.internal("ui/null.png")));
                control = 1;
                groupBuild.setVisible(true);
                outerTableIndis.setVisible(true);
                panelRight.setVisible(true);
            }
        });
        iconTable.add(btnControl);
        iconTable.row();
        CustomIcon btnHelp = new CustomIcon(new Texture("uiTva/help2.png"));
        btnHelp.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                for (int i=0; i <girlStage.getActors().size; i++) {
                    girlStage.getActors().get(i).remove();
                }
                Gdx.gl.glClearColor(1, 1, 1, 0);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                stage.draw();
                saveScreenshot();
                SocietyScreen.Custom metromap = new SocietyScreen.Custom(new Texture(Gdx.files.local("E/json/DEFAULTCITY/screenshot.png")), 2500, 0);
                girlStage.addActor(metromap);
                girl = !girl;
            }
        });
        //iconTable.add(btnHelp);
        //iconTable.row();






        labelTime = new Label("", new Label.LabelStyle(fontOswaldBlack, Color.CYAN));
        labelTime.setPosition(Gdx.graphics.getWidth() - 138, Gdx.graphics.getHeight() - 30);
        labelTime.setBounds(Gdx.graphics.getWidth() - 138, Gdx.graphics.getHeight() - 70, 150, 70);
        labelGamePts = new Label("", new Label.LabelStyle(fontOswaldBlack, Color.CYAN));
        labelGamePts.setBounds(Gdx.graphics.getWidth() - 208, Gdx.graphics.getHeight() - 70, 150, 70);
        labelDay = new Label("", new Label.LabelStyle(fontFran, Color.CYAN));
        labelDay.setPosition(Gdx.graphics.getWidth() - 150, Gdx.graphics.getHeight() - 70);
        scrollPane = new ScrollPane(iconTable);
        outerTable = new Table();
        scrollPaneFurn = new ScrollPane(furnTable);
        scrollPaneRelations = new ScrollPane(tableRelations);
        scrollPaneActNums = new ScrollPane(tableActNums);
        scrollPaneIndis = new ScrollPane(tableIndis);
        outerTableFurn = new Table();
        outerTableFurn.add(scrollPaneFurn).expand().left();
        outerTableFurn.setVisible(false);
        outerTableRelations = new Table();
        outerTableRelations.add(scrollPaneRelations).expand().left();
        outerTableActNums = new Table();
        outerTableActNums.add(scrollPaneActNums).expand().left();
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
        outerTableNeeds.setPosition(90, 90);
        outerTableNeeds.setSize(550, 720);
        if (Gdx.graphics.getHeight() < 1079) {
            outerTableNeeds.setSize(550, 420);
        }
        else if (Gdx.graphics.getHeight() < 759) {
            outerTableNeeds.setSize(550, 250);
        }
        tableInterests.setPosition(90, 90);
        tableInterests.setSize(550, 720);
        if (Gdx.graphics.getHeight() < 1079) {
            tableInterests.setSize(550, 420);
        }
        else if (Gdx.graphics.getHeight() < 759) {
            tableInterests.setSize(550, 250);
        }
        tableTalents.setPosition(90, 90);
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
        outerTableActNums.setPosition(100, 40);
        outerTableActNums.setSize(600, 820*Float.parseFloat(String.valueOf(screenCoef)));
        outerTableIndis.setPosition(Gdx.graphics.getWidth()-100, 40);
        outerTableIndis.setSize(170, 850*Float.parseFloat(String.valueOf(screenCoef)));
        //outerTable.add(labelHint);


        cardStage.addActor(outerTable);
        cardStage.addActor(outerTableTva);
        cardStage.addActor(outerTableFurn);
        cardStage.addActor(outerTableRelations);
        cardStage.addActor(outerTableActNums);
        cardStage.addActor(outerTableIndis);
        cardStage.addActor(outerTableNeeds);
        cardStage.addActor(tableInterests);
        cardStage.addActor(tableTalents);
        //cardStage.addActor(tableRoom);
        //tableNeeds.setPosition(300, Gdx.graphics.getHeight()-100);
        //tableNeeds.setBounds(300, Gdx.graphics.getHeight()-300, 100, 100);





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
                action.setEn(new String[1]);
                action.getEn()[0] = "test action " + action.getCODE();
                action.setRu(new String[1]);
                action.getRu()[0] = "тестовое действие " + action.getCODE();
                FileHandle file = Gdx.files.local("E/json/extraactions.txt");
                Json json = new Json();
                jsonStr = json.prettyPrint(extraActs);
                file.writeString(jsonStr, false);
                uiDelay = System.currentTimeMillis() + 3000;
                labelCenter.setText(langString.get("saveSaved"));
            }});
        Label labelSaveButton = new Label(langString.get("saveButton"), new Label.LabelStyle(fontOswald, Color.WHITE));
        labelSaveButton.setPosition(100, Gdx.graphics.getHeight()-420);
        SocietyScreen.Custom loadButton = new SocietyScreen.Custom(new Texture("ui/loadButtonTva.png"), 100, Gdx.graphics.getHeight()-580);
        loadButton.setBounds(100, Gdx.graphics.getHeight()-580, 165, 165);
        loadButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                outerTableActNums.setVisible(true);
                FileHandle file = Gdx.files.local("E/json/extraactions.txt");
                if (file.exists()) {
                    jsonStr = file.readString();
                    extraActs = json.fromJson(ArrayList.class, jsonStr);
                } else {
                    file = Gdx.files.internal("json/extraactions.txt");
                    if (file.exists()) {
                        jsonStr = file.readString();
                        extraActs = json.fromJson(ArrayList.class, jsonStr);
                    } else {
                        extraActs.add(new com.peoplebox.additions.Action(10000, 15, 2, 0, 0,
                                new SocietyScreen.NeedsArray(), new SocietyScreen.NeedsArray(-5, -5, -15, -15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                                new SocietyScreen.InterestsArray(0, 0, 0, 0, 0, 0, 0, 0, 30, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                                new SocietyScreen.InterestsArray(0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                                new SocietyScreen.TalentsArray(), new SocietyScreen.TalentsArray(0, 0, 0, 0, 0, 0, 0, 0, 1, 0),
                                new ArrayList<Integer>(Arrays.asList(new Integer[]{22})), ""));
                        extraActs.add(extraActs.get(0));

                        extraActs.get(0).setRu(new String[]{"бегает на дорожке"});
                        extraActs.get(0).setEn(new String[]{"runs on treadmill"});
                    }
                }
                tableActNums.clear();
                for (int i = 0; i < extraActs.size(); i++) {
                    tableActNums.add(new ActCard(i));
                    tableActNums.row();
                }
                tableActNums.add(new ActCard(-1));
                groupBuild.setVisible(false);
            }});
        Label labelLoadButton = new Label(langString.get("loadButton"), new Label.LabelStyle(fontOswald, Color.WHITE));
        labelLoadButton.setPosition(100, Gdx.graphics.getHeight()-625);

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
        groupBuild.setVisible(false);
        cardStage.addActor(groupBuild);



        buttonRemove = new TextButton("Remove Indi", tbs);
        buttonRemove.pad(adaptRes);
        buttonRemove.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                //indis.set(indiObs, deadIndi);
                //indi.updateAppearance();
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
        label = new Label("", new Label.LabelStyle(fontFran, Color.WHITE));
        cardStage.addActor(this.label);
        cardStage.addActor(this.labelDebug);
        nameView = new Label("", new Label.LabelStyle(font, Color.CYAN));
        cardStage.addActor(this.nameView);
        label.setPosition(0, 10);
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

        cardStage.addActor(labelControl);
        for (int i = 0; i < SocietyScreen.Relation.Levels.values().length; i++) {
            TextButton btn = new TextButton(SocietyScreen.Relation.Levels.values()[i].name(), tbs);
            btn.pad(15);
            final int finalI = i;
            btn.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    society.getIndi(cb, indiObs).getRelations().get(ff).setLevel(SocietyScreen.Relation.Levels.values()[finalI]);
                    tableRelationsChoose.setVisible(false);
                }
            });
            tableRelationsChoose.add(btn).width(200).height(40);
            tableRelationsChoose.row();
        }
        tableRelationsChoose.setVisible(false);
        cardStage.addActor(tableRelationsChoose);
        cardStage.addActor(labelCenter);
        tableMove.setVisible(false);
        //labelInter.remove();

        //tableDebugActions.setVisible(false);
        scrollPane.setVisible(true);
        cardTva.listen();
    }

    public class ActCard extends Actor {
        public TextureRegion texture = new TextureRegion(new Texture(Gdx.files.internal("uiTva/scenario.png")));
        int ii, act;
        String s1, s2;

        public ActCard(int act) {
            this.s2 = s2;
            this.act = act;
            this.ii = ii;
            setBounds(getX(), getY(), 600, 96);
            addListener(new InputListener() {
                public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                    if (act == -1) {
                        action = action = new Action(19999, 3, 1, 0, 0,
                                new SocietyScreen.NeedsArray(), new SocietyScreen.NeedsArray(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                                new SocietyScreen.InterestsArray(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                                new SocietyScreen.InterestsArray(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                                new SocietyScreen.TalentsArray(), new SocietyScreen.TalentsArray(0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                                new ArrayList<Integer>(), "");
                        int a = rnd(9999);
                        action.setEn(new String[1]);
                        action.getEn()[0] = "test action " + a;
                        action.setRu(new String[1]);
                        action.getRu()[0] = "тестовое действие " + a;
                        extraActs.add(action);
                        Gdx.app.error("EXTRAACTS SIZE AFTER LOADING", extraActs.size() + "");
                    }
                    else
                        action = extraActs.get(act);
                    hideTables(false);
                    cardTva.type = 18;
                    if (act > 0)
                        labelHintTre.setText(extraActs.get(act).getEn().length == 0 ? "empty action" : extraActs.get(act).getEn()[0]);
                    else
                        labelHintTre.setText("new action");
                    xxxx=700; yyyy=100;
                    for (int i = 0; i<action.getItems().size(); i++) {
                        Texture texture0 = whichObject(action.getItems().get(i), 1);
                        ObjectActor myActorTva = new ObjectActor(texture0, xxxx, yyyy, action.getItems().get(i));
                        myActorTva.setTouchable(Touchable.enabled);
                        stage.addActor(myActorTva);
                        yyyy += texture0.getHeight() + 20;
                        if (yyyy > Gdx.graphics.getHeight() -100) {
                            xxxx += 300;
                            yyyy = 100;
                        }
                    }
                    return true;
                }
            });
        }

        public void act(float delta) {}

        public void draw(Batch batch, float delta) {
            if (act > -1) {
                fontFranTva.draw(batch, extraActs.get(act).getEn().length == 0 ? "empty action" : extraActs.get(act).getEn()[0], getX() + 110, getY() + 80);
                fontOswald.draw(batch, extraActs.get(act).getCODE() + " ", getX(), getY() + 80);
            }
            else fontFranTva.draw(batch, "new empty action", getX() + 110, getY() + 80);
        }
    }

    @Override
    public void show() {
        //createInterface();
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

    static int rnd(int i) {
        long sec = System.currentTimeMillis();
        sec = sec + random(sec % 10000);
        long j = sec % i;
        return (int) j + 1;
    }

    void createObject(int type, int appearance) {
        //1-fridges 2-beds 3-lamps 4-tables 5-TVs 6-PCs 7-deco 8-armchairs 9-chairs 11-shelves
        Texture texture0 = whichObject(type, appearance);
        ObjectActor myActorTva = new ObjectActor(texture0, xxxx, yyyy, type);
        myActorTva.setTouchable(Touchable.enabled);
        stage.addActor(myActorTva);
        yyyy += texture0.getHeight() + 20;
        if (yyyy > Gdx.graphics.getHeight() -100) {
            xxxx += 300;
            yyyy = 100;
        }
        action.getItems().add(type);
    }

    public class ObjectActor extends Actor {
        public ObjectActor() {
        }

        public boolean anim = false;
        private TextureRegion region;
        private Texture texture;
        private int ox, oy;
        public int type;
        public int occ = -1;
        public int vac = 1;
        public transient boolean started = false;
        public transient boolean bounds = true;
        public long delay, mainDelay;

        public ObjectActor(final Texture texture, int x, int y, int type) {
            this.texture = texture;
            Gdx.app.log("texture", String.valueOf(texture));
            this.ox = x;
            this.oy = y;
            this.type = type;
            occ = -1;
            region = new TextureRegion(texture);
            setBounds(region.getRegionX(), region.getRegionY(),
                    region.getRegionWidth(), region.getRegionHeight());
            addListener(new InputListener() {
                public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                    for (int i=0; i<action.getItems().size(); i++) {
                        if (action.getItems().get(i) == type) {
                            action.getItems().remove(i);
                        }
                    }
                    remove();
                    ObjectActor.this.remove();
                    return true;
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

        public void draw(Batch batch, float alpha) {
            batch.draw(texture, ox, oy);
        }

        public void act(float delta) {
            if (bounds) setBounds(ox, oy, texture.getWidth(), texture.getHeight());
        }
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

    void hideTables(boolean labelHintHide) {
        panelLeft.setTexture(new Texture(Gdx.files.internal("ui/null.png")));
        cardTva.setType(0);
        start.setVisible(false);
        finish.setVisible(false);
        codeField.setVisible(false);
        surnameField.setVisible(false);
        outerTableActNums.setVisible(false);
        applyCode.setVisible(false);
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

    public void removeExtraActors() {
        for(int i = cardStage.getActors().size; i > 0; i--)
        {
            try {if (cardStage.getActors().get(i).getClass() == SocietyScreen.ScenButton.class) {
                cardStage.getActors().get(i).remove();
            }}
            catch (IndexOutOfBoundsException e) {}
        }
        FileHandle fileTva = Gdx.files.internal("json/furn.txt");
        jsonStr = fileTva.readString();
    }

    public void updateInterface(int type) {
        int[] arr = new int[] {18, 42};
        if (type == 18) {
            String str = " ";
            for (int i=0; i<action.getItems().size(); i++) {
                str += action.getItems().get(i) + ", ";
            }
            labelHint.setText("items:"+ str+"\n"+"en:"+(action.getEn().length == 0 ? "empty action" : action.getEn()[0])+"\nru:" + (action.getRu().length == 0 ? "пустое действие" : action.getRu()[0]) + "\ndelay:" + action.getDelay());
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
                    }
                }
                labelHint.setText(str);
            }
        }

        if (cardTva.type % 2 == 1) {
            labelAesthetics.setText(action.getStartNeeds().getAesthetics() + " ");
            labelBladder.setText(action.getStartNeeds().getBl() + " ");
            labelEducation.setText(action.getStartNeeds().getEducation() + " ");
            labelEnergy.setText(action.getStartNeeds().getEnergy() + " ");
            labelEnv.setText(action.getStartNeeds().getEnvironment() + " ");
            labelFun.setText(action.getStartNeeds().getFun() + " ");
            labelHunger.setText(action.getStartNeeds().getHunger() + " ");
            labelHygiene.setText(action.getStartNeeds().getHygiene() + " ");
            labelLove.setText(action.getStartNeeds().getLove() + " ");
            labelPower.setText(action.getStartNeeds().getPower() + " ");
            labelSafety.setText(action.getStartNeeds().getProtection() + " ");
            labelShopping.setText(action.getStartNeeds().getShopping() + " ");
            labelSocial.setText(action.getStartNeeds().getSocial() + " ");
            labelSuccess.setText(action.getStartNeeds().getSuccess() + " ");
            labelPolitics.setText(action.getStartInterests().getPolitics() + " ");
            labelEconomics.setText(action.getStartInterests().getEconomics() + " ");
            labelHealth.setText(action.getStartInterests().getHealth() + " ");
            labelCrimes.setText(action.getStartInterests().getCrimes() + " ");
            labelFashion.setText(action.getStartInterests().getFun() + " ");
            labelCulture.setText(action.getStartInterests().getCulture() + " ");
            labelFood.setText(action.getStartInterests().getFood() + " ");
            labelScience.setText(action.getStartInterests().getFashion() + " ");
            labelSport.setText(action.getStartInterests().getSport() + " ");
            labelTravel.setText(action.getStartInterests().getTravel() + " ");
            labelTechnics.setText(action.getStartInterests().getTechnics() + " ");
            labelJob.setText(action.getStartInterests().getWork() + " ");
            labelAnimals.setText(action.getStartInterests().getAnimals() + " ");
            labelBooks.setText(action.getStartInterests().getBooks() + " ");
            labelFilms.setText(action.getStartInterests().getFilms() + " ");
            labelMusic.setText(action.getStartInterests().getMusic() + " ");
            labelHistory.setText(action.getStartInterests().getHistory() + " ");
            labelMystic.setText(action.getStartInterests().getMystic() + " ");
            labelCaution.setText(action.getStartTalents().getCaution() + " ");
            labelImagination.setText(action.getStartTalents().getImagination() + " ");
            labelInfluence.setText(action.getStartTalents().getInfluence() + " ");
            labelImmunity.setText(action.getStartTalents().getImmunity() + " ");
            labelInsight.setText(action.getStartTalents().getInsight() + " ");
            labelLogic.setText(action.getStartTalents().getLogic() + " ");
            labelMemory.setText(action.getStartTalents().getMemory() + " ");
            labelQuickness.setText(action.getStartTalents().getQuickness() + " ");
            labelSpeech.setText(action.getStartTalents().getSpeech() + " ");
            labelStamina.setText(action.getStartTalents().getStamina() + " ");
        }
        else {
            labelAesthetics.setText(action.getAddNeeds().getAesthetics() + " ");
            labelBladder.setText(action.getAddNeeds().getBl() + " ");
            labelEducation.setText(action.getAddNeeds().getEducation() + " ");
            labelEnergy.setText(action.getAddNeeds().getEnergy() + " ");
            labelEnv.setText(action.getAddNeeds().getEnvironment() + " ");
            labelFun.setText(action.getAddNeeds().getFun() + " ");
            labelHunger.setText(action.getAddNeeds().getHunger() + " ");
            labelHygiene.setText(action.getAddNeeds().getHygiene() + " ");
            labelLove.setText(action.getAddNeeds().getLove() + " ");
            labelPower.setText(action.getAddNeeds().getPower() + " ");
            labelSafety.setText(action.getAddNeeds().getProtection() + " ");
            labelShopping.setText(action.getAddNeeds().getShopping() + " ");
            labelSocial.setText(action.getAddNeeds().getSocial() + " ");
            labelSuccess.setText(action.getAddNeeds().getSuccess() + " ");
            labelPolitics.setText(action.getAddInterests().getPolitics() + " ");
            labelEconomics.setText(action.getAddInterests().getEconomics() + " ");
            labelHealth.setText(action.getAddInterests().getHealth() + " ");
            labelCrimes.setText(action.getAddInterests().getCrimes() + " ");
            labelFashion.setText(action.getAddInterests().getFun() + " ");
            labelCulture.setText(action.getAddInterests().getCulture() + " ");
            labelFood.setText(action.getAddInterests().getFood() + " ");
            labelScience.setText(action.getAddInterests().getFashion() + " ");
            labelSport.setText(action.getAddInterests().getSport() + " ");
            labelTravel.setText(action.getAddInterests().getTravel() + " ");
            labelTechnics.setText(action.getAddInterests().getTechnics() + " ");
            labelJob.setText(action.getAddInterests().getWork() + " ");
            labelAnimals.setText(action.getAddInterests().getAnimals() + " ");
            labelBooks.setText(action.getAddInterests().getBooks() + " ");
            labelFilms.setText(action.getAddInterests().getFilms() + " ");
            labelMusic.setText(action.getAddInterests().getMusic() + " ");
            labelHistory.setText(action.getAddInterests().getHistory() + " ");
            labelMystic.setText(action.getAddInterests().getMystic() + " ");
            labelCaution.setText(action.getAddTalents().getCaution() + " ");
            labelImagination.setText(action.getAddTalents().getImagination() + " ");
            labelInfluence.setText(action.getAddTalents().getInfluence() + " ");
            labelImmunity.setText(action.getAddTalents().getImmunity() + " ");
            labelInsight.setText(action.getAddTalents().getInsight() + " ");
            labelLogic.setText(action.getAddTalents().getLogic() + " ");
            labelMemory.setText(action.getAddTalents().getMemory() + " ");
            labelQuickness.setText(action.getAddTalents().getQuickness() + " ");
            labelSpeech.setText(action.getAddTalents().getSpeech() + " ");
            labelStamina.setText(action.getAddTalents().getStamina() + " ");
        }
    }

    private void recolorFields(Color color) {
        labelAesthetics.setStyle(new Label.LabelStyle(fontOswaldTre, color));
        labelBladder.setStyle(new Label.LabelStyle(fontOswaldTre, color));
        labelEducation.setStyle(new Label.LabelStyle(fontOswaldTre, color));
        labelEnergy.setStyle(new Label.LabelStyle(fontOswaldTre, color));
        labelEnv.setStyle(new Label.LabelStyle(fontOswaldTre, color));
        labelFun.setStyle(new Label.LabelStyle(fontOswaldTre, color));
        labelHunger.setStyle(new Label.LabelStyle(fontOswaldTre, color));
        labelHygiene.setStyle(new Label.LabelStyle(fontOswaldTre, color));
        labelLove.setStyle(new Label.LabelStyle(fontOswaldTre, color));
        labelPower.setStyle(new Label.LabelStyle(fontOswaldTre, color));
        labelSafety.setStyle(new Label.LabelStyle(fontOswaldTre, color));
        labelShopping.setStyle(new Label.LabelStyle(fontOswaldTre, color));
        labelSocial.setStyle(new Label.LabelStyle(fontOswaldTre, color));
        labelSuccess.setStyle(new Label.LabelStyle(fontOswaldTre, color));
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
                (cardTva.type % 2 == 0 ? action.getAddInterests() : action.getStartInterests()).setPolitics((cardTva.type % 2 == 0 ? action.getAddInterests() : action.getStartInterests()).getPolitics() + cardCount);
            }
            if (cardType == 2) {
                (cardTva.type % 2 == 0 ? action.getAddInterests() : action.getStartInterests()).setEconomics((cardTva.type % 2 == 0 ? action.getAddInterests() : action.getStartInterests()).getEconomics() + cardCount);
            }
            if (cardType == 3) {
                (cardTva.type % 2 == 0 ? action.getAddInterests() : action.getStartInterests()).setHealth((cardTva.type % 2 == 0 ? action.getAddInterests() : action.getStartInterests()).getHealth() + cardCount);
            }
            if (cardType == 4) {
                (cardTva.type % 2 == 0 ? action.getAddInterests() : action.getStartInterests()).setCrimes((cardTva.type % 2 == 0 ? action.getAddInterests() : action.getStartInterests()).getCrimes() + cardCount);
            }
            if (cardType == 5) {
                (cardTva.type % 2 == 0 ? action.getAddInterests() : action.getStartInterests()).setFashion((cardTva.type % 2 == 0 ? action.getAddInterests() : action.getStartInterests()).getFashion() + cardCount);
            }
            if (cardType == 6) {
                (cardTva.type % 2 == 0 ? action.getAddInterests() : action.getStartInterests()).setCulture((cardTva.type % 2 == 0 ? action.getAddInterests() : action.getStartInterests()).getCulture() + cardCount);
            }
            if (cardType == 7) {
                (cardTva.type % 2 == 0 ? action.getAddInterests() : action.getStartInterests()).setFood((cardTva.type % 2 == 0 ? action.getAddInterests() : action.getStartInterests()).getFood() + cardCount);
            }
            if (cardType == 8) {
                (cardTva.type % 2 == 0 ? action.getAddInterests() : action.getStartInterests()).setFun((cardTva.type % 2 == 0 ? action.getAddInterests() : action.getStartInterests()).getFun() + cardCount);
            }
            if (cardType == 9) {
                (cardTva.type % 2 == 0 ? action.getAddInterests() : action.getStartInterests()).setSport((cardTva.type % 2 == 0 ? action.getAddInterests() : action.getStartInterests()).getSport() + cardCount);
            }
            if (cardType == 10) {
                (cardTva.type % 2 == 0 ? action.getAddInterests() : action.getStartInterests()).setTravel((cardTva.type % 2 == 0 ? action.getAddInterests() : action.getStartInterests()).getTravel() + cardCount);
            }
            if (cardType == 11) {
                (cardTva.type % 2 == 0 ? action.getAddInterests() : action.getStartInterests()).setTechnics((cardTva.type % 2 == 0 ? action.getAddInterests() : action.getStartInterests()).getTechnics() + cardCount);
            }
            if (cardType == 12) {
                (cardTva.type % 2 == 0 ? action.getAddInterests() : action.getStartInterests()).setWork((cardTva.type % 2 == 0 ? action.getAddInterests() : action.getStartInterests()).getWork() + cardCount);
            }
            if (cardType == 13) {
                (cardTva.type % 2 == 0 ? action.getAddInterests() : action.getStartInterests()).setAnimals((cardTva.type % 2 == 0 ? action.getAddInterests() : action.getStartInterests()).getAnimals() + cardCount);
            }
            if (cardType == 14) {
                (cardTva.type % 2 == 0 ? action.getAddInterests() : action.getStartInterests()).setBooks((cardTva.type % 2 == 0 ? action.getAddInterests() : action.getStartInterests()).getBooks() + cardCount);
            }
            if (cardType == 15) {
                (cardTva.type % 2 == 0 ? action.getAddInterests() : action.getStartInterests()).setFilms((cardTva.type % 2 == 0 ? action.getAddInterests() : action.getStartInterests()).getFilms() + cardCount);
            }
            if (cardType == 16) {
                (cardTva.type % 2 == 0 ? action.getAddInterests() : action.getStartInterests()).setMusic((cardTva.type % 2 == 0 ? action.getAddInterests() : action.getStartInterests()).getMusic() + cardCount);
            }
            if (cardType == 26) {
                (cardTva.type % 2 == 0 ? action.getAddNeeds() : action.getStartNeeds()).setAesthetics((cardTva.type % 2 == 0 ? action.getAddNeeds() : action.getStartNeeds()).getAesthetics() + cardCount);
            }
            if (cardType == 27) {
                (cardTva.type % 2 == 0 ? action.getAddNeeds() : action.getStartNeeds()).setBl((cardTva.type % 2 == 0 ? action.getAddNeeds() : action.getStartNeeds()).getBl() + cardCount);
            }
            if (cardType == 28) {
                (cardTva.type % 2 == 0 ? action.getAddNeeds() : action.getStartNeeds()).setEducation((cardTva.type % 2 == 0 ? action.getAddNeeds() : action.getStartNeeds()).getEducation() + cardCount);
            }
            if (cardType == 29) {
                (cardTva.type % 2 == 0 ? action.getAddNeeds() : action.getStartNeeds()).setEnergy((cardTva.type % 2 == 0 ? action.getAddNeeds() : action.getStartNeeds()).getEnergy() + cardCount);
            }
            if (cardType == 30) {
                (cardTva.type % 2 == 0 ? action.getAddNeeds() : action.getStartNeeds()).setEnvironment((cardTva.type % 2 == 0 ? action.getAddNeeds() : action.getStartNeeds()).getEnvironment() + cardCount);
            }
            if (cardType == 31) {
                (cardTva.type % 2 == 0 ? action.getAddNeeds() : action.getStartNeeds()).setFun((cardTva.type % 2 == 0 ? action.getAddNeeds() : action.getStartNeeds()).getFun() + cardCount);
            }
            if (cardType == 32) {
                (cardTva.type % 2 == 0 ? action.getAddNeeds() : action.getStartNeeds()).setHunger((cardTva.type % 2 == 0 ? action.getAddNeeds() : action.getStartNeeds()).getHunger() + cardCount);
            }
            if (cardType == 33) {
                (cardTva.type % 2 == 0 ? action.getAddNeeds() : action.getStartNeeds()).setHygiene((cardTva.type % 2 == 0 ? action.getAddNeeds() : action.getStartNeeds()).getHygiene() + cardCount);
            }
            if (cardType == 34) {
                (cardTva.type % 2 == 0 ? action.getAddNeeds() : action.getStartNeeds()).setLove((cardTva.type % 2 == 0 ? action.getAddNeeds() : action.getStartNeeds()).getLove() + cardCount);
            }
            if (cardType == 35) {
                (cardTva.type % 2 == 0 ? action.getAddNeeds() : action.getStartNeeds()).setPower((cardTva.type % 2 == 0 ? action.getAddNeeds() : action.getStartNeeds()).getPower() + cardCount);
            }
            if (cardType == 36) {
                (cardTva.type % 2 == 0 ? action.getAddNeeds() : action.getStartNeeds()).setProtection((cardTva.type % 2 == 0 ? action.getAddNeeds() : action.getStartNeeds()).getProtection() + cardCount);
            }
            if (cardType == 37) {
                (cardTva.type % 2 == 0 ? action.getAddNeeds() : action.getStartNeeds()).setShopping((cardTva.type % 2 == 0 ? action.getAddNeeds() : action.getStartNeeds()).getShopping() + cardCount);
            }
            if (cardType == 38) {
                (cardTva.type % 2 == 0 ? action.getAddNeeds() : action.getStartNeeds()).setSocial((cardTva.type % 2 == 0 ? action.getAddNeeds() : action.getStartNeeds()).getSocial() + cardCount);
            }
            if (cardType == 39) {
                (cardTva.type % 2 == 0 ? action.getAddNeeds() : action.getStartNeeds()).setSuccess((cardTva.type % 2 == 0 ? action.getAddNeeds() : action.getStartNeeds()).getSuccess() + cardCount);
            }
            if (cardType == 44) {
                (cardTva.type % 2 == 0 ? action.getAddTalents() : action.getStartTalents()).setCaution((cardTva.type % 2 == 0 ? action.getAddTalents() : action.getStartTalents()).getCaution() + cardCount);
            }
            if (cardType == 45) {
                (cardTva.type % 2 == 0 ? action.getAddTalents() : action.getStartTalents()).setImagination((cardTva.type % 2 == 0 ? action.getAddTalents() : action.getStartTalents()).getImagination() + cardCount);
            }
            if (cardType == 46) {
                (cardTva.type % 2 == 0 ? action.getAddTalents() : action.getStartTalents()).setImmunity((cardTva.type % 2 == 0 ? action.getAddTalents() : action.getStartTalents()).getImmunity() + cardCount);
            }
            if (cardType == 47) {
                (cardTva.type % 2 == 0 ? action.getAddTalents() : action.getStartTalents()).setInfluence((cardTva.type % 2 == 0 ? action.getAddTalents() : action.getStartTalents()).getInfluence() + cardCount);
            }
            if (cardType == 48) {
                (cardTva.type % 2 == 0 ? action.getAddTalents() : action.getStartTalents()).setInsight((cardTva.type % 2 == 0 ? action.getAddTalents() : action.getStartTalents()).getInsight() + cardCount);
            }
            if (cardType == 49) {
                (cardTva.type % 2 == 0 ? action.getAddTalents() : action.getStartTalents()).setLogic((cardTva.type % 2 == 0 ? action.getAddTalents() : action.getStartTalents()).getLogic() + cardCount);
            }
            if (cardType == 50) {
                (cardTva.type % 2 == 0 ? action.getAddTalents() : action.getStartTalents()).setMemory((cardTva.type % 2 == 0 ? action.getAddTalents() : action.getStartTalents()).getMemory() + cardCount);
            }
            if (cardType == 51) {
                (cardTva.type % 2 == 0 ? action.getAddTalents() : action.getStartTalents()).setQuickness((cardTva.type % 2 == 0 ? action.getAddTalents() : action.getStartTalents()).getQuickness() + cardCount);
            }
            if (cardType == 52) {
                (cardTva.type % 2 == 0 ? action.getAddTalents() : action.getStartTalents()).setSpeech((cardTva.type % 2 == 0 ? action.getAddTalents() : action.getStartTalents()).getSpeech() + cardCount);
            }
            if (cardType == 53) {
                (cardTva.type % 2 == 0 ? action.getAddTalents() : action.getStartTalents()).setStamina((cardTva.type % 2 == 0 ? action.getAddTalents() : action.getStartTalents()).getStamina() + cardCount);
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
            }
            if (cardType == 61) {
                (cardTva.type % 2 == 0 ? action.getAddInterests() : action.getStartInterests()).setHistory((cardTva.type % 2 == 0 ? action.getAddInterests() : action.getStartInterests()).getHistory() + cardCount);
            }
            if (cardType == 62) {
                (cardTva.type % 2 == 0 ? action.getAddInterests() : action.getStartInterests()).setMystic((cardTva.type % 2 == 0 ? action.getAddInterests() : action.getStartInterests()).getMystic() + cardCount);
            }
        }
        else if (cardType == 0) {
            arrowHint.setVisible(false);
        }
    }

    public class Card extends Actor{
        //1-Indi, 2-concept, 3-ObjectTest
        public Card() {

        }
        private int type, ox, oy;
        private String name;
        Texture texture = new Texture("ui/actPreview.png");
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
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }

    public void render(float delta) {
        FPS++;
        Gdx.gl.glClearColor(0, 0, 150 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        cardStage.act(Gdx.graphics.getDeltaTime());
        cardStage.draw();
        if (FPS == 8) {
            neededY = Gdx.graphics.getHeight() / 2;
            updateInterface(cardTva.type);
            addingParameters();
            FPS = 0;
            labelHintFyra.setText(cardTva.type+"");
        }
    }
}
