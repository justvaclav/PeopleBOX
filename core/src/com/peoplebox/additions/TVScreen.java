package com.peoplebox.additions;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class TVScreen implements Screen {
    final Game game;
    OrthographicCamera camera;
    String str = "";
    long delay = 0, sign = 0;
    protected Label label;
    public int FPS = 0;
    protected BitmapFont font, fontFran, fontFranTva;
    protected Label crosshair;
    static int f =1;
    private Stage stage;
    static I18NBundle langString;
    private TextButton buttonStart, buttonEnd, buttonLoad, buttonMap;
    private TextureAtlas atlas;
    private Skin skin;
    private Table tableEtt, tableTva, table;
    Texture banner;
    double screenCoefW = 1.0;
    double screenCoefH = 1.0;
    SpriteBatch batch = new SpriteBatch();
    AssetManager manager = new AssetManager();
    final String FONT_CHARS = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяabcdefghijklmnopqrstuvwxyzÅÄÖåäöÂâÊêÎîÔôÛûÉéÀàÈèÙùАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"?`'<>";
    String lang = "ru";

    public class ChannelCard extends Actor {
        public int num, price, type, appear;
        public String name, desc;
        public Texture texture;

        public ChannelCard(int num, int price, int type, int appear, String name, String desc, Texture texture) {
            this.num = num;
            this.price = price;
            this.type = type;
            this.appear = appear;
            this.name = name;
            this.desc = desc;
            this.texture = texture;
            setBounds(getX(), getY(), 120, 96);
            if (num == 0) {setBounds(getX(), getY(), 700, 96);}
            f++;
            final int typ = type;
            final int appea = appear;
            final String nam = name;
            addListener(new InputListener() {
                public boolean touchDown(InputEvent event, float x1, float y1, int pointer, int button) {
                    return true;
                }
            });
        }

        public void act(float delta) {}

        public void draw(Batch batch, float delta) {
            batch.draw(texture, getX(), getY(), texture.getWidth()*96/texture.getHeight(), 96);
            fontFranTva.draw(batch, name, getX()+texture.getWidth()/(texture.getHeight()/96+1)+20, getY()+80);
        }
    }

    public TVScreen(final Game gam, String language) {
        lang = language;
        screenCoefW = Double.parseDouble(String.valueOf(Gdx.graphics.getWidth())) / 1080;
        screenCoefH = Double.parseDouble(String.valueOf(Gdx.graphics.getHeight())) / 1080;
        game = gam;
        //stage = new Stage();
        font = new BitmapFont();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/oswaldb.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 48;
        parameter.characters = FONT_CHARS;
        fontFran = generator.generateFont(parameter);
        parameter.characters = FONT_CHARS;
        parameter.size = 40;
        generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/fran.ttf"));
        fontFranTva = generator.generateFont(parameter);
        generator.dispose(); // don't forget to dispose to avoid memory leaks!
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
        createInterface();
        crosshair = new Label("Телевидение", new Label.LabelStyle(fontFran, Color.WHITE));
        crosshair.setPosition(30, Gdx.graphics.getHeight()-70);
        if (Gdx.graphics.getWidth() < 1281) {crosshair.setPosition(300, 200);}
        stage.addActor(crosshair);
        banner = new Texture(Gdx.files.internal("ui/additionBack.png"));
        Gdx.input.setInputProcessor(stage);
        //stage.addActor(myActor);
    }

    private void createInterface() {
        Gdx.input.setInputProcessor(stage);
        atlas = new TextureAtlas("ui/b.atlas");
        skin = new Skin(atlas);
        tableEtt = new Table(skin);
        tableEtt.setBounds(15, Gdx.graphics.getHeight()/2, 200, 150);
        TextButton.TextButtonStyle tbs = new TextButton.TextButtonStyle();
        tbs.up = skin.getDrawable("button.9");
        tbs.down = skin.getDrawable("button.9");
        tbs.pressedOffsetX = 1;
        tbs.pressedOffsetY = -1;
        tbs.font = fontFran;
        tbs.fontColor = Color.BLACK;
        Table table = new Table();
        table.setPosition(50, 50);
        table.setBounds(50, 50, 300, 150);
        //stage.addActor(table);
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

    public void render(float delta) {
        FPS++;
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        if (Gdx.graphics.getWidth() < 1501) {
            batch.draw(banner, -250, -70);
        }
        if (Gdx.graphics.getWidth() < 1281) {
            batch.draw(banner, -350, -120);
        }
        else {
            batch.draw(banner, 0, 0);}
        batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }
}
