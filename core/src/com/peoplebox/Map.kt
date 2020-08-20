package com.peoplebox

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.*
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.ObjectMap
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport

class Map(gam: Game) : Screen {
    //https://www.gamefromscratch.com/post/2013/11/27/LibGDX-Tutorial-9-Scene2D-Part-1.aspx - Scene
    //https://github.com/libgdx/libgdx/wiki/Orthographic-camera - Camera
    var matrixA: Array<IntArray>
    private val stage: Stage
    private val cam: OrthographicCamera
    private val rotationSpeed: Float
    private val viewport: Viewport
    protected var label: Label
    protected var font: BitmapFont

    inner class Tile(type: Int, //float actorX = 50, actorY = 50;
                     internal var actorX: Float, internal var actorY: Float) : Actor() {
        internal var texture = Texture(Gdx.files.internal("happycat1.jpg"))
        var started = false

        init {
            setBounds(actorX, actorY, texture.width.toFloat(), texture.height.toFloat())
            if (type == 1) {
                texture = Texture(Gdx.files.internal("grass2.png"))
            }
            if (type == 2) {
                texture = Texture(Gdx.files.internal("ocean2.png"))
            }
            if (type == 3) {
                texture = Texture(Gdx.files.internal("skyscraper2.png"))
            }
            if (type == 4) {
                texture = Texture(Gdx.files.internal("house1.png"))
            }
            if (type == 5) {
                texture = Texture(Gdx.files.internal("house2.png"))
            }
            if (type == 6) {
                texture = Texture(Gdx.files.internal("house3.png"))
            }
            if (type == 7) {
                texture = Texture(Gdx.files.internal("tree1.png"))
            }
            if (type == 8) {
                texture = Texture(Gdx.files.internal("cartile1.png"))
            }
            addListener(object : InputListener() {
                override fun touchDown(event: InputEvent?, x1: Float, y1: Float, pointer: Int, button: Int): Boolean {
                    (event!!.target as Tile).started = true
                    return true
                }
            })
        }

        override fun draw(batch: Batch, alpha: Float) {
            batch.draw(texture, actorX, actorY)
        }

        override fun act(delta: Float) {
            if (started) {
                actorX += 1f
            }
        }
    }

    init {
        stage = Stage(ExtendViewport(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat()))
        font = BitmapFont()
        label = Label("FPS", Label.LabelStyle(font, Color.WHITE))
        Gdx.input.inputProcessor = stage
        stage.addActor(label)
        rotationSpeed = 0.5f
        val w = Gdx.graphics.width.toFloat()
        val h = Gdx.graphics.height.toFloat()
        cam = OrthographicCamera(30f, 30 * (h / w))
        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0f)
        cam.update()
        viewport = FitViewport(1280f, 720f, cam)

        /*Tile myActor = new Tile(3, 160, 450);
        myActor.setTouchable(Touchable.enabled);
        stage.addActor(myActor);*/
        val x0 = 40
        val y0 = 30
        var x1 = 400
        var y1 = 750
        matrixA = Array(x0) { IntArray(y0) }
        for (i in 0 until x0) {
            matrixA[i][0] = 1
            matrixA[i][y0 - 1] = 1
        }
        for (i in 0 until y0) {
            matrixA[0][i] = 1
            matrixA[x0 - 1][i] = 1
        }
        for (i in 0 until y0) {
            matrixA[2][i] = 2
        }
        for (i in 0 until y0) {
            matrixA[6][i] = 3
        }
        run {
            var i = 0
            while (i < x0) {
                matrixA[i][4] = 3
                i += 2
            }
        }
        run {
            var i = 0
            while (i < x0) {
                matrixA[i][11] = 5
                i += 2
            }
        }
        for (i in 0 until y0) {
            matrixA[20][i] = 6
        }
        for (i in 0 until x0) {
            matrixA[i][24] = 7
        }
        matrixA[17][18] = 8
        matrixA[9][29] = 8
        matrixA[24][11] = 8
        matrixA[16][22] = 8
        matrixA[34][5] = 8
        for (i in 0 until x0) {
            if (i % 2 == 1) {
                x1 = 0
                y1 -= 16
            }
            if (i % 2 == 0) {
                x1 = -32
                y1 -= 16
            }
            for (j in 0 until y0) {
                if (matrixA[i][j] == 0) {
                    val myActor = Tile(1, x1.toFloat(), y1.toFloat())
                    myActor.touchable = Touchable.enabled
                    stage.addActor(myActor)
                }
                if (matrixA[i][j] == 1) {
                    val myActor = Tile(2, x1.toFloat(), y1.toFloat())
                    myActor.touchable = Touchable.enabled
                    stage.addActor(myActor)
                }
                if (matrixA[i][j] == 2) {
                    val myActor = Tile(3, x1.toFloat(), y1.toFloat())
                    myActor.touchable = Touchable.enabled
                    stage.addActor(myActor)
                }
                if (matrixA[i][j] == 3) {
                    val myActor = Tile(4, x1.toFloat(), y1.toFloat())
                    myActor.touchable = Touchable.enabled
                    stage.addActor(myActor)
                }
                if (matrixA[i][j] == 5) {
                    val myActor = Tile(5, x1.toFloat(), y1.toFloat())
                    myActor.touchable = Touchable.enabled
                    stage.addActor(myActor)
                }
                if (matrixA[i][j] == 6) {
                    val myActor = Tile(6, x1.toFloat(), y1.toFloat())
                    myActor.touchable = Touchable.enabled
                    stage.addActor(myActor)
                }
                if (matrixA[i][j] == 7) {
                    val myActor = Tile(7, x1.toFloat(), y1.toFloat())
                    myActor.touchable = Touchable.enabled
                    stage.addActor(myActor)
                }
                if (matrixA[i][j] == 8) {
                    val myActor = Tile(8, x1.toFloat(), y1.toFloat())
                    myActor.touchable = Touchable.enabled
                    stage.addActor(myActor)
                }
                x1 = x1 + 64
            }
        }
    }

    override fun dispose() {
        stage.dispose()
    }

    private fun handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            cam.zoom += 0.02f
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            cam.zoom -= 0.02f
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            cam.translate(-3f, 0f, 0f)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            cam.translate(3f, 0f, 0f)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            cam.translate(0f, -3f, 0f)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            cam.translate(0f, 3f, 0f)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            cam.rotate(-rotationSpeed, 0f, 0f, 1f)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            cam.rotate(rotationSpeed, 0f, 0f, 1f)
        }

        cam.zoom = MathUtils.clamp(cam.zoom, 0.1f, 100 / cam.viewportWidth)

        val effectiveViewportWidth = cam.viewportWidth * cam.zoom
        val effectiveViewportHeight = cam.viewportHeight * cam.zoom

        cam.position.x = MathUtils.clamp(cam.position.x, effectiveViewportWidth / 2f, 100 - effectiveViewportWidth / 2f)
        cam.position.y = MathUtils.clamp(cam.position.y, effectiveViewportHeight / 2f, 100 - effectiveViewportHeight / 2f)
    }


    override fun show() {

    }

    override fun render(delta: Float) {
        handleInput()
        cam.update()
        //stage.setViewport(cam.combined);
        label.setText("FPS:" + Gdx.graphics.framesPerSecond)
        stage.act(Gdx.graphics.deltaTime)
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        stage.act(Gdx.graphics.deltaTime)
        stage.draw()
    }

    override fun resize(width: Int, height: Int) {
        cam.viewportWidth = 30f
        cam.viewportHeight = 30f * height / width
        cam.update()
        stage.viewport.update(width, height, true)
    }

    override fun pause() {}

    override fun resume() {}

    override fun hide() {

    }
}