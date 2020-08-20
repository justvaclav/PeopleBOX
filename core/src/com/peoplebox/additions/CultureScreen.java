package com.peoplebox.additions;

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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.peoplebox.Game;
import com.peoplebox.additions.*;

public class CultureScreen implements Screen {
    final Game game;
    OrthographicCamera camera;
    String str = "";
    long delay = 0, sign = 0;
    protected Label label;
    public int FPS = 0;
    protected BitmapFont font, fontFran;
    protected Label crosshair;
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

    public class Custom extends Actor {
        //https://www.gamefromscratch.com/post/2013/11/27/LibGDX-Tutorial-9-Scene2D-Part-1.aspx
        Texture texture = new Texture(Gdx.files.internal("pboxlogo1.png"));
        public boolean started = false;

        public Custom(Texture tex){
            texture = tex;
            setBounds(getX(), getY(), texture.getWidth(), texture.getHeight());
        }

        public Texture getTexture() {
            return texture;
        }

        public void setTexture(Texture texture) {
            this.texture = texture;
        }

        public void draw(Batch batch, float alpha){
            batch.draw(texture, getX(), getY());
        }

        public void act(float delta){
        }
    }

    public CultureScreen(final Game gam, String language) {
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
        crosshair = new Label(langString.get("civilization"), new Label.LabelStyle(fontFran, Color.WHITE));
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
        Custom bookButton = new Custom(new Texture("icons/books.png"));
        bookButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("bookButton", "bookScreen");
            }
        });
        table.add(bookButton);
        Custom tvButton = new Custom(new Texture("icons/films.png"));
        tvButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new TVScreen(game, lang));
                dispose();
            }
        });
        table.add(tvButton);
        stage.addActor(table);
        buttonMap = new TextButton("Открыть карту", tbs);
        buttonMap.pad(20);
        buttonMap.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                //game.setScreen(new MapScreen(game));
                dispose();
            }
        });
        buttonEnd = new TextButton("  Выйти ", tbs);
        buttonEnd.pad(20);
        buttonEnd.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
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
