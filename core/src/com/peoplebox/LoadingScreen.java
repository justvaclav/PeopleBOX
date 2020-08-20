package com.peoplebox;

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
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class LoadingScreen implements Screen {
    final Game game;
    OrthographicCamera camera;
    String str = "";
    long delay = 0, sign = 0;
    protected Label label;
    public int FPS = 0;
    protected BitmapFont font, fontFran;
    protected Label crosshair;
    private Stage stage;
    private TextButton buttonStart, buttonEnd, buttonLoad, buttonMap;
    private TextureAtlas atlas;
    private Skin skin;
    private Table table, tableTva;
    Texture banner;
    double screenCoefW = 1.0;
    double screenCoefH = 1.0;
    SpriteBatch batch = new SpriteBatch();
    AssetManager manager = new AssetManager();
    final String FONT_CHARS = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяabcdefghijklmnopqrstuvwxyzÅÄÖåäöÂâÊêÎîÔôÛûÉéÀàÈèÙùАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"?`'<>";
    String lang = "ru";

    public class LogoView extends Actor {
        //https://www.gamefromscratch.com/post/2013/11/27/LibGDX-Tutorial-9-Scene2D-Part-1.aspx
        float actorX, actorY;
        Texture texture = new Texture(Gdx.files.internal("pboxlogo1.png"));
        public boolean started = false;

        public LogoView(float x1, float y1){
            actorX = x1;
            actorY = y1;
            /*setBounds(x1,y1,texture.getWidth(),texture.getHeight());
            addListener(new InputListener(){
                public boolean touchDown (InputEvent event, float x1, float y1, int pointer, int button) {
                    ((Map.Tile)event.getTarget()).started = true;
                    return true;
                }
            });*/
        }

        public void draw(Batch batch, float alpha){
            batch.draw(texture,actorX,actorY);
        }

        public void act(float delta){}
    }

    public LoadingScreen(final Game gam, final int homeX, final int homeY, final int homeZ, String language) {
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
        generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/fran.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 60;
        if (Gdx.graphics.getWidth() < 1281) {parameter.size = 54;}
        parameter.characters = FONT_CHARS;
        fontFran = generator.generateFont(parameter);
        generator.dispose(); // don't forget to dispose to avoid memory leaks!
        delay = System.currentTimeMillis() + 4000;
        stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));

        crosshair = new Label("", new Label.LabelStyle(fontFran, Color.WHITE));
        crosshair.setPosition(375, 220);
        if (Gdx.graphics.getWidth() < 1281) {crosshair.setPosition(300, 200);}
        stage.addActor(crosshair);
        banner = new Texture(Gdx.files.internal("ui/peopleboxbanner3.png"));
        if (Gdx.graphics.getWidth() < 1501) {
            banner = new Texture(Gdx.files.internal("ui/peopleboxbanner5.png"));
            crosshair.setPosition(155, 180);
        }
        if (Gdx.graphics.getWidth() < 1281) {
            banner = new Texture(Gdx.files.internal("ui/peopleboxbanner5.png"));
            crosshair.setPosition(75, 150);
        }
        Gdx.input.setInputProcessor(stage);
        stage.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SocietyScreen(gam, 1, lang, homeX, homeY, homeZ));
                Gdx.app.log(String.valueOf(homeX + " " + homeY + " " + homeZ), "coords");
                dispose();
            }
        });
        //stage.addActor(myActor);
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
        if (FPS > 20) {
            crosshair.setText("НАЖМИТЕ НА ЭКРАН, ЧТОБЫ ПРОДОЛЖИТЬ");
        }
        else {
            crosshair.setText("ЗАГРУЗКА...");
        }

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
