package com.peoplebox;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;


public class SignsEditor extends InputAdapter implements Screen, ApplicationListener {
    public PerspectiveCamera cam;

    public Model model;
    public ModelInstance instance;
    public ModelBatch modelBatch;
    public DecalBatch decalBatch;
    SpriteBatch batch;
    BitmapFont aFont;
    FrameBuffer lFb;
    ModelInstance ballInstance;
    public Environment environment;

    final float bound = 95f;
    float[] Vpos = new float[3];

    protected Label label;
    protected Label crosshair;
    protected BitmapFont font;
    protected Stage stage;

    protected long startTime;
    protected long hits;

    boolean isUnder = false;
    long underFire;

    final float zone = 12f;
    final float speed = 2f;
    public AssetManager assets;
    public boolean loading;


    public SignsEditor() {
        create();
    }

    @Override
    public void create() {
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, 10f, 10f, 20f));

        modelBatch = new ModelBatch();

        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(0f, 0f, 0f);
        cam.lookAt(0, 0, 0);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();
        /*Decal decal = Decal.newDecal(new TextureRegion(font.getRegion()));
        decal.rotateX(40f);
        decal.rotateY(50f);
        decal.rotateZ(20f);*/

        ModelBuilder modelBuilder = new ModelBuilder();
        model = modelBuilder.createCone(20f, 120f, 20f, 3,
                new Material(ColorAttribute.createDiffuse(Color.GREEN)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        instance = new ModelInstance(model);
        instance.transform.setToRotation(Vector3.Z, 90).translate(-5,0,0);
        // initialize speed
        for (int i = 0; i < 3; i++){
            Vpos[i] = getSpeed();
        }


        font = new BitmapFont();
        label = new Label(" ", new Label.LabelStyle(font, Color.WHITE));
        crosshair = new Label("+", new Label.LabelStyle(font, Color.RED));
        crosshair.setPosition(Gdx.graphics.getWidth() / 2 - 3, Gdx.graphics.getHeight() / 2 - 9);
        Decal decal2 = Decal.newDecal(1, 1,
                new TextureRegion(new Texture(Gdx.files.internal("chess.png"))) );
        decal2.setPosition(10, 10, 10);
        decal2.setScale(3);
        //decals.add(decal);

        stage = new Stage();
        stage.addActor(label);
        stage.addActor(crosshair);


        startTime = System.currentTimeMillis();

        Gdx.input.setInputProcessor(new InputMultiplexer(this));

        assets = new AssetManager();
        assets.load("space_frigate_6.g3db", Model.class);
        loading = true;

        batch = new SpriteBatch();

//Generate font
        FreeTypeFontGenerator generator = new
                FreeTypeFontGenerator(Gdx.files.internal("ui/oswaldb.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new
                FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;
        parameter.color = com.badlogic.gdx.graphics.Color.WHITE;
        parameter.magFilter = Texture.TextureFilter.Linear; // used for resizing quality
        parameter.minFilter = Texture.TextureFilter.Linear;
        generator.scaleForPixelHeight(10);

//Get bitmap font
        aFont = generator.generateFont(parameter);
        aFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear,
                Texture.TextureFilter.Linear);

//Generate new framebuffer object of 128x128 px. This will be our texture
        lFb = new FrameBuffer(Pixmap.Format.RGBA4444,128,128,false);
        lFb.begin();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Matrix4 lm = new Matrix4();
//Set the correct projection for the sprite batch
        lm.setToOrtho2D(0,0,128,128);
        batch.setProjectionMatrix(lm);
        //Generate the material to be applied to the ball
//Notice here how we use the FBO's texture as the material base
        Material lMaterial = new Material(TextureAttribute.createDiffuse(lFb.getColorBufferTexture()));

//Since I do not have a sphere model, I'll just create one with the newly
//generated material
        Model ballModel = modelBuilder.createSphere(1.0f,1.0f,1.0f,8,8,lMaterial,
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);

//Finally instantiate an object of the model
        ballInstance = new ModelInstance(ballModel);
    }

    private float getSpeed(){
        return speed*Math.signum((float) Math.random()-0.5f)*Math.max((float) Math.random(), 0.5f);
    }

    @Override
    public void render() {


    }

    @Override
    public void dispose() {
        model.dispose();
        modelBatch.dispose();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        batch.begin();
        aFont.draw(batch,"Goal!",64,64);
        batch.end();
        lFb.end();


        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        cam.position.set(10f, 10f, 10f);
        cam.update();

        modelBatch.begin(cam);
//        font.draw((Batch) modelBatch, "Testing 1 2 3", 0, 0);
        //modelBatch.render(instance, environment);
        modelBatch.render(ballInstance, environment);
        modelBatch.end();

//Rotate the ball along the y-axis
        ballInstance.transform.rotate(0.0f,1.0f,0.0f,0.5f);

        StringBuilder builder = new StringBuilder();
        builder.append(" FPS: ").append(Gdx.graphics.getFramesPerSecond());
        long time = System.currentTimeMillis() - startTime;
        builder.append("| Game time: ").append(time);
        builder.append("| Hits: ").append(hits);
        builder.append("| Rating: ").append((float) hits / (float) time);
        label.setText(builder);
        stage.draw();
    }


    @Override
    public void resize(int width, int height) {
        //stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (isUnder) {
            underFire = System.currentTimeMillis();
        } else {
            hits /= 2;
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (isUnder && underFire != 0) {
            hits += System.currentTimeMillis() - underFire;
            underFire = 0;
        } else {
            hits /= 2;
        }
        return false;
    }
}