package com.peoplebox;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.utils.Array;

public class GameScreen implements Screen  {
    private static final int WIDTH = 1920;
    private static final int HEIGHT = 1080;
    private OrthographicCamera camera;
    private Music optMusic;
    private Rectangle glViewport;
    private Sprite mapSprite;
    private SpriteBatch batch;
    private Texture img, test1, tableImage, grass, ocean, skyscraper;
    private Vector3 touchPos;
    private int [][] matrixA;
    private int x = 15;
    private int y = 40; // x и y перепутаны местами, слишком лень исправлять
    private int x1 = -16;
    private int y1 = 480;


    public GameScreen(final Game gam)  {
        Array<Button> buttons = new Array<Button>();
        matrixA = new int[x][y];
        for (int i = 0; i < x; i++) {
            matrixA[i][0] = 1;
            matrixA[i][y-1] = 1;
        }
        for (int i = 0; i < y; i++) {
            matrixA[0][i] = 1;
            matrixA[x-1][i] = 1;
        }
        for (int i = 0; i < y; i++) {
            matrixA[2][i] = 2;
        }
        /*mapSprite = new Sprite(new Texture("sc_map.png"));
        mapSprite.setPosition(0, 0);
        mapSprite.setSize(WIDTH, HEIGHT);*/
        //int [][] matrixB = new int[10000][20000];

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        batch = new SpriteBatch();
        grass = new Texture("grass.png");
        ocean = new Texture("ocean.png");
        skyscraper = new Texture("skyscraper1.png");
        Gdx.app.log("GameScreen", "matrixB has created");
        camera = new OrthographicCamera(30, 30 * (h / w));
        camera.position.set(WIDTH / 2, HEIGHT / 2, 0);
        camera.update();
        glViewport = new Rectangle(0, 0, WIDTH/2, HEIGHT/2);
        batch.begin();
        /*for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (matrixA[i][j] == 0) {
                    batch.draw(grass, x1, y1);}
                if (matrixA[i][j] == 1) {batch.draw(ocean, x1, y1);}
                if (matrixA[i][j] == 2) {batch.draw(skyscraper, x1, y1);}
                if (matrixA[i][j] == 3) {createTile(3, x1, y1);}
                x1 = x1 + 32;
            }
            if (i % 2 == 1) {x1 = 0; y1 = y1 - 8;}
            if (i % 2 == 0) {x1 = -16; y1 = y1 - 8;}
        }*/
        //mapSprite.draw(batch);
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (matrixA[i][j] == 0) {
                    batch.draw(grass, x1, y1);}
                if (matrixA[i][j] == 1) {batch.draw(ocean, x1, y1);}
                if (matrixA[i][j] == 2) {batch.draw(skyscraper, x1, y1);}
                if (matrixA[i][j] == 3) {createTile(3, x1, y1);}
                x1 = x1 + 32;
            }
            if (i % 2 == 1) {x1 = 0; y1 = y1 - 8;}
            if (i % 2 == 0) {x1 = -16; y1 = y1 - 8;}
        }
        batch.end();

        //optMusic = Gdx.audio.newMusic(Gdx.files.internal("optical.mp3"));
        //optMusic.setLooping(true);
    }

    public void render(float delta) {
        camera.update();
        handleInput();
        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (matrixA[i][j] == 0) {
                    batch.draw(grass, x1, y1);}
                if (matrixA[i][j] == 1) {batch.draw(ocean, x1, y1);}
                if (matrixA[i][j] == 2) {batch.draw(skyscraper, x1, y1);}
                x1 = x1 + 32;
            }
            if (i % 2 == 1) {x1 = 0; y1 = y1 - 8;}
            if (i % 2 == 0) {x1 = x1 -16; y1 = y1 - 8;}
            Gdx.app.log("MainScreen","OOF");
        }
        batch.end();
    }

    @Override
    public void show() {
        //optMusic.play();
        Gdx.app.log("MainScreen","show");
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
    public void dispose () {
        //mapSprite.getTexture().dispose();
        batch.dispose();
        img.dispose();
        //optMusic.dispose();
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            x1 -= 0.03;
            Gdx.app.log("MainScreen", "LEFT");
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            x1 += 0.03;
            Gdx.app.log("MainScreen", "RIGHT");
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            y1 -= 0.03;
            Gdx.app.log("MainScreen", "UP");
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            y1 += 0.03;
            Gdx.app.log("MainScreen", "DOWN");
        }
    }

    public Tile createTile(int type, int x, int y) {
        Tile tile = new Tile();
        tile.type = type;
        tile.x = x;
        tile.y = y;
        return tile;
    }
    class Tile {
        public int x, y, width, height, type;
        public void draw (SpriteBatch batch, float parentAlpha) { }
    }

}
