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
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class MenuScreen implements Screen {
    final Game game;
    OrthographicCamera camera;
    String str = "", lang="ru";
    long delay = 0, sign = 0;
    protected Label label;
    protected BitmapFont font, fontFran, fontOswald;
    private Label crosshair, option;
    private Stage stage;
    private TextButton buttonStart, buttonEnd, buttonLoad, buttonMap;
    private TextureAtlas atlas;
    private Skin skin;
    private Table table, tableTva, tableTre;
    Texture banner;
    double screenCoefW = 1.0;
    double screenCoefH = 1.0;
    SpriteBatch batch = new SpriteBatch();
    AssetManager manager = new AssetManager();
    final String FONT_CHARS = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяabcdefghijklmnopqrstuvwxyzÅÄÖåäöÂâÊêÎîÔôÛûÉéÀàÈèÙùАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"?`'<>";

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

    public MenuScreen(final Game gam) {
        //lang = language;
        screenCoefW = Double.parseDouble(String.valueOf(Gdx.graphics.getWidth())) / 1080;
        screenCoefH = Double.parseDouble(String.valueOf(Gdx.graphics.getHeight())) / 1080;
        game = gam;
        atlas = new TextureAtlas("ui/b.atlas");
        skin = new Skin(atlas);
        //stage = new Stage();
        font = new BitmapFont();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/oswaldb.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 100;
        parameter.characters = FONT_CHARS;
        fontOswald = generator.generateFont(parameter);
        generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/fran.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 60;
        if (Gdx.graphics.getWidth() < 1281) {parameter.size = 54;}
        parameter.characters = FONT_CHARS;
        fontFran = generator.generateFont(parameter);
        generator.dispose(); // don't forget to dispose to avoid memory leaks!
        delay = System.currentTimeMillis() + 4000;
        stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));
        stage.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (sign == 0) {
                    game.setScreen(new MapScreen(game, "ru"));
                    dispose();}
                else if (sign == 1) {
                    game.setScreen(new MapScreen(game, "en"));
                    dispose();
                }
                else if (sign == 2) {
                    game.setScreen(new MapScreen(game, "fr"));
                    dispose();
                }
                else if (sign == 3) {
                    game.setScreen(new MapScreen(game, "sv"));
                    dispose();
                }
                else if (sign == 4) {
                    game.setScreen(new MapScreen(game, "ru"));
                    dispose();
                }
            }
        });
        label = new Label("", new Label.LabelStyle(font, Color.WHITE));
        option = new Label("Язык", new Label.LabelStyle(fontOswald, Color.WHITE));
        option.setPosition(1111, 490);
        stage.addActor(label);
        stage.addActor(option);
        crosshair = new Label("", new Label.LabelStyle(fontFran, Color.WHITE));
        crosshair.setAlignment(Align.center);
        stage.addActor(crosshair);
        crosshair.setPosition(Gdx.graphics.getWidth()/2, 220);
        Gdx.input.setInputProcessor(stage);

        //if (Gdx.graphics.getWidth() < 1281) {crosshair.setPosition(300, 200);}
        stage.addActor(crosshair);
        LogoView myActor = new LogoView(30, 400);
        banner = new Texture(Gdx.files.internal("ui/menuBck.png"));
        if (Gdx.graphics.getWidth() < 1501) {
            banner = new Texture(Gdx.files.internal("ui/menuBck.png"));
            crosshair.setPosition(Gdx.graphics.getWidth()/2, 180);
        }
        if (Gdx.graphics.getWidth() < 1281) {
            banner = new Texture(Gdx.files.internal("ui/menuBck.png"));
            crosshair.setPosition(Gdx.graphics.getWidth()/2, 150);
        }
        //tableTre.setBounds(Gdx.graphics.getWidth()-150, 150, 1200, 80);
        //tableTre.setPosition(600, 150);
        //stage.addActor(myActor);
    }

    private void createInterface() {
        Gdx.input.setInputProcessor(stage);

        table = new Table(skin);
        table.setBounds(15, Gdx.graphics.getHeight()/2, 200, 150);
        TextButton.TextButtonStyle tbs = new TextButton.TextButtonStyle();
        tbs.up = skin.getDrawable("button.9");
        tbs.down = skin.getDrawable("button.9");
        tbs.pressedOffsetX = 1;
        tbs.pressedOffsetY = -1;
        tbs.font = fontFran;
        tbs.fontColor = Color.BLACK;
        buttonStart = new TextButton("  Начать  ", tbs);
        buttonStart.pad(20);
        //buttonStart.getLabel().setFontScale(3, 3);
        buttonStart.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {

            }
        });
        /*buttonLoad = new TextButton("Загрузить", tbs);
        buttonLoad.pad(20);
        buttonLoad.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SocietyTest(game, 1, "ru"));
                dispose();
            }
        });*/
        buttonMap = new TextButton("Открыть карту", tbs);
        buttonMap.pad(20);
        buttonMap.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MapScreen(game, "ru"));
                dispose();
            }
        });
        buttonEnd = new TextButton("  Выйти ", tbs);
        buttonEnd.pad(20);
        //buttonEnd.getLabel().setFontScale(3, 3);
        buttonEnd.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        table.add(buttonStart);
        table.row();
        table.add(buttonLoad);
        table.row();
        table.add(buttonMap);
        table.row();
        table.add(buttonEnd);
        table.row();
        //stage.addActor(table);
    }

    @Override
    public void show() {
        createInterface();
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
        if (System.currentTimeMillis() > delay) {
            delay = System.currentTimeMillis() + 3500;
            sign++;
            if (sign == 5) {sign = 0;}
        }

        if (sign == 0) {crosshair.setText("ПРИКОСНИТЕСЬ, ЧТОБЫ ПРОДОЛЖИТЬ");}
        if (sign == 1) {crosshair.setText("TOUCH THE SCREEN TO CONTINUE");}
        if (sign == 2) {crosshair.setText("APPUYEZ SUR L'ÉCRAN POUR CONTINUER");}
        if (sign == 3) {crosshair.setText("TRYCK PÅ SKÄRMEN FÖR ATT FORTSÄTTA");}
        if (sign == 4) {crosshair.setText("EXTENDED PLAY");}
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        label.setText(" ");
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
