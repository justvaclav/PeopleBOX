package com.peoplebox

import com.badlogic.gdx.*
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.*
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Json
import com.badlogic.gdx.utils.PerformanceCounter
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport

import java.util.ArrayList
import java.util.Collections
import java.util.Comparator
import kotlin.random.Random

class MapScreen(internal val game: Game, language: String) : Screen, GestureDetector.GestureListener {
    internal val FONT_CHARS = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяabcdefghijklmnopqrstuvwxyzАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"?`'<>"
    internal var objectObs = 0
    internal var FPS = 0
    internal var tapDelay = System.currentTimeMillis()
    internal var showAddress = false
    var dest = "0"
    var address = ""
    private val stage: Stage
    private val zeroStage: Stage
    internal var screenCoef = 1.0
    internal var panelLeft = Custom(Texture("ui/panel7.png"), 0f, 0f)
    internal var skinTva = Skin(Gdx.files.internal("ui/ui.json"))
    private var iconTable: Table? = null
    internal var scrollPane = ScrollPane(iconTable)
    private var outerTable: Table? = null
    internal var buttonBackground: Texture
    private val cam: OrthographicCamera
    private val rotationSpeed: Float
    private val viewport: Viewport
    protected var cityName: Label
    protected var label: Label
    internal var sortList = ArrayList<SocietyScreen.SortCard>()
    var boxes: ArrayList<Box>
    var univers: ArrayList<Addition.University>
    var hospitals: ArrayList<Addition.Hospital>
    var channels: ArrayList<Addition.Channel>? = null
    internal var font = BitmapFont()
    internal var fontFran: BitmapFont
    internal var fontOswaldDeux: BitmapFont
    internal var multiplexer: InputMultiplexer
    internal var lang = "ru"
    var xx: TextField
    var yy: TextField
    var zz: TextField
    private val skin: Skin
    private val atlas: TextureAtlas

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
            /*if(started){
                actorX += 1;
            }*/
        }
    }

    inner class Box {
        var x: Int = 0
        var y: Int = 0
        var z: Int = 0
        var type: Int = 0
        var name = "Box"
        var desc = ""

        constructor(x: Int, y: Int, z: Int, type: Int, name: String, desc: String) {
            this.x = x
            this.y = y
            this.z = z
            this.type = type //1-residential, 2-commercial, 3-univer, 4-hospital, 5-office
            this.name = name
            this.desc = desc
        }

        constructor(x: Int, y: Int, z: Int, type: Int) {
            this.x = x
            this.y = y
            this.z = z
            this.type = type
        }

        constructor() {}
    }


    inner class Custom(tex: Texture, //https://www.gamefromscratch.com/post/2013/11/27/LibGDX-Tutorial-9-Scene2D-Part-1.aspx
                       internal var actorX: Float, internal var actorY: Float) : Actor() {
        var texture = Texture(Gdx.files.internal("pboxlogo1.png"))
        var started = false

        init {
            texture = tex
        }

        override fun draw(batch: Batch, alpha: Float) {
            batch.draw(texture, actorX, actorY)
        }

        override fun act(delta: Float) {
            setBounds(actorX, actorY, texture.width.toFloat(), texture.height.toFloat())
        }
    }

    inner class CustomLift(internal var str: String) : Actor() {
        //https://www.gamefromscratch.com/post/2013/11/27/LibGDX-Tutorial-9-Scene2D-Part-1.aspx
        internal var actorX: Float = 0.toFloat()
        internal var actorY: Float = 0.toFloat()
        internal var texture = Texture("ui/b6.png")
        var started = false

        override fun draw(batch: Batch, alpha: Float) {
            batch.draw(buttonBackground, x, y)
            fontOswaldDeux.draw(batch, str, x + 10, y + 50)
        }

        override fun act(delta: Float) {}
    }

    inner class BuildingActor : Actor {

        var anim = false
        private lateinit var region: TextureRegion
        var texture: Texture? = null
        var ox: Int = 0
        var oy: Int = 0
        var num0: Int = 0
        @Transient
        var isStarted = false
        @Transient
        var bounds = true
        var delay: Long = 0
        var mainDelay: Long = 0

        constructor() {}

        constructor(texture: Texture, x: Int, y: Int, num0: Int) {
            this.texture = texture
            Gdx.app.log("texture", texture.toString())
            this.ox = x
            this.oy = y
            this.num0 = num0
            val num1 = num0
            region = TextureRegion(texture)
            setBounds(region.regionX.toFloat(), region.regionY.toFloat(),
                    region.regionWidth.toFloat(), region.regionHeight.toFloat())
            addListener(object : ClickListener() {
                override fun clicked(event: InputEvent?, x: Float, y: Float) {
                    tapDelay = System.currentTimeMillis()
                    //tableMove!!.isVisible = true
                    objectObs = num1 - 1
                    address = buildings[num1 - 1].address
                    btnGo.setText("Перейти:\n$address")
                    game.screen = SocietyScreen(game, 1, lang, 10, 10, 9)
                    dispose()
                    /*tableMove = new Table(skinTva);
                    tableMove.add(btnUp);
                    tableMove.row();
                    tableMove.add(btnLeft);
                    tableMove.row();
                    tableMove.add(btnRight);
                    tableMove.row();
                    tableMove.add(btnDown);
                    tableMove.row();
                    for (int i = 0; i < buildings.get(num1).boxes.size()-1; i++) {
                        TextButton button = new TextButton(buildings.get(num1).boxes.get(i).x + " " + buildings.get(num1).boxes.get(i).y + " " + buildings.get(num1).boxes.get(i).z, tbs);
                        button.pad(15);
                        final int i2 = i;
                        button.addListener(new ClickListener() {
                            public void clicked(InputEvent event, float x1, float y1) {
                                game.setScreen(new LoadingScreen(game, buildings.get(num1).boxes.get(i2).x, buildings.get(num1).boxes.get(i2).y, buildings.get(num1).boxes.get(i2).z, lang));
                                dispose();
                            }
                        });
                        tableMove.add(button);
                    }
                    tableMove.add(btnRemove);
                    tableMove.row();
                    tableMove.add(btnMoveOk);
                    tableMove.row();*/

                    /*if (objectsTest.get(num1 - 1).type == 1) {
                        final Society.ScenButton s = new Society.ScenButton("Пополнить запасы\nОсталось еды:" + society.getBoxes().get(cb).foodRemain);
                        scenButtons.add(s);
                        s.setPosition(ox, oy + texture.getWidth() + 80);
                        stage.addActor(s);
                        s.addListener(new ClickListener() {
                            public void clicked(InputEvent event, float x, float y) {
                                society.getBoxes().get(cb).foodRemain += 20;
                                s.remove();
                            }
                        });
                    } else if (objectsTest.get(num1 - 1).type == 2) {
                        final Society.ScenButton s = new Society.ScenButton("Спаун тараканов");
                        scenButtons.add(s);
                        s.setPosition(ox, oy + texture.getWidth() - 10);
                        stage.addActor(s);
                        s.addListener(new ClickListener() {
                            public void clicked(InputEvent event, float x, float y) {
                                createObject(0, 5, 0, 0, rnd(80) + 900, rnd(60) + 600);
                                s.remove();
                            }
                        });
                    }*/
                }
            })
        }

        override fun draw(batch: Batch, alpha: Float) {
            batch.draw(texture, ox.toFloat(), oy.toFloat())
            if (showAddress) fontFran.draw(batch, buildings[num0].address, ox.toFloat(), oy.toFloat())
        }

        override fun act(delta: Float) {
            if (bounds) setBounds(ox.toFloat(), oy.toFloat(), texture!!.width.toFloat(), texture!!.height.toFloat())
            /*if (objectsTest.get(num0-1).type == 0 && objectsTest.get(num0-1).appearance == 5 && System.currentTimeMillis() > delay) {
                texture = whichObject(0, rnd(2) + 4);
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
            if (System.currentTimeMillis() > mainDelay) {anim = false;}*/
        }
    }

    class Building {

        var links = ArrayList<String>() // для дополнительных сведений и ссылок этого объекта
        var type: Int = 0
        var appearance: Int = 0
        var x: Int = 0
        var y: Int = 0
        var z = 0
        var address = ""
        var num: Int = 0
        var boxes: ArrayList<Box>? = null

        constructor() {}

        constructor(type: Int, appearance: Int, x: Int, y: Int, address: String, boxes: ArrayList<Box>, num: Int) {
            this.type = type
            this.appearance = appearance
            this.x = x
            this.y = y
            this.address = address
            this.boxes = boxes
            this.num = num
        }

        constructor(type: Int, appearance: Int, x: Int, y: Int, z: Int, address: String, boxes: ArrayList<Box>, num: Int) {
            this.type = type
            this.appearance = appearance
            this.x = x
            this.y = y
            this.z = z
            this.address = address
            this.boxes = boxes
            this.num = num
        }
    }

    init {
        lang = language
        stage = Stage(ExtendViewport(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat()))
        zeroStage = Stage(ExtendViewport(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat()))
        screenCoef = java.lang.Double.parseDouble(Gdx.graphics.height.toString()) / 1080
        font = BitmapFont()
        buttonBackground = Texture("ui/b6.png")
        atlas = TextureAtlas("ui/b.atlas")
        skin = Skin(atlas)
        val pcounter = PerformanceCounter("pcounter")
        pcounter.start()
        var generator = FreeTypeFontGenerator(Gdx.files.internal("ui/fran.ttf"))
        val parameter = FreeTypeFontGenerator.FreeTypeFontParameter()
        parameter.characters = FONT_CHARS
        parameter.size = 48
        generator = FreeTypeFontGenerator(Gdx.files.internal("ui/oswaldb.ttf"))
        fontOswaldDeux = generator.generateFont(parameter)
        fontOswaldDeux.setColor(Color.WHITE)
        parameter.characters = FONT_CHARS
        parameter.size = 24
        generator = FreeTypeFontGenerator(Gdx.files.internal("ui/fran.ttf"))
        fontFran = generator.generateFont(parameter)
        fontFran.setColor(Color.WHITE)
        val txtstyle = TextField.TextFieldStyle()
        txtstyle.font = fontOswaldDeux
        txtstyle.fontColor = Color.WHITE
        val atlas = TextureAtlas("ui/b.atlas")
        val skin = Skin(atlas)
        tbs = TextButton.TextButtonStyle()
        tbs.up = skin.getDrawable("button.9")
        tbs.down = skin.getDrawable("button.9")
        tbs.pressedOffsetX = 1f
        tbs.pressedOffsetY = -1f
        tbs.font = fontFran
        tbs.fontColor = Color.BLACK
        label = Label("FPS", Label.LabelStyle(font, Color.WHITE))
        cityName = Label("Default City", Label.LabelStyle(fontOswaldDeux, Color.WHITE))
        cityName.setPosition(10f, (Gdx.graphics.height - 60).toFloat())
        val culture = Custom(Texture("icons/culture.png"), (Gdx.graphics.width - 170).toFloat(), (Gdx.graphics.height - 170).toFloat())
        culture.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {

            }
        })
        culture.setBounds((Gdx.graphics.width - 170).toFloat(), (Gdx.graphics.height - 170).toFloat(), 150f, 150f)
        xx = TextField("0", txtstyle)
        yy = TextField("0", txtstyle)
        zz = TextField("0", txtstyle)
        xx.setBounds(250f, (Gdx.graphics.height - 80).toFloat(), 100f, 80f)
        yy.setBounds(350f, (Gdx.graphics.height - 80).toFloat(), 100f, 80f)
        zz.setBounds(450f, (Gdx.graphics.height - 80).toFloat(), 100f, 80f)
        zeroStage.addActor(label)
        zeroStage.addActor(cityName)
        //zeroStage.addActor(culture);
        //zeroStage.addActor(xx);
        //zeroStage.addActor(yy);
        //zeroStage.addActor(zz);
        val buttonGo = TextButton("Go", tbs)
        buttonGo.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                game.screen = LoadingScreen(game, Integer.parseInt(xx.text), Integer.parseInt(yy.text), Integer.parseInt(zz.text), lang)
                dispose()
                /*game.setScreen(new Society(gam, 1, lang, Integer.parseInt(xx.getText()), Integer.parseInt(yy.getText()), Integer.parseInt(zz.getText())));
                dispose();*/
            }
        })
        btnGo = TextButton("Go to", tbs)
        btnGo.pad(15f)
        btnGo.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                val addr = address.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                game.screen = SocietyScreen(game, 1, lang, Integer.valueOf(addr[0]), Integer.valueOf(addr[1]), Integer.valueOf(addr[2]))
                dispose()
            }
        })
        buttonGo.setBounds(550f, (Gdx.graphics.height - 80).toFloat(), 100f, 80f)
        //zeroStage.addActor(buttonGo);
        //Tile tile = new Tile(0,0,0);
        boxes = ArrayList()
        univers = ArrayList()
        hospitals = ArrayList()
        val specs = ArrayList<Addition.Speciality>()
        val disps = ArrayList<Addition.Discipline>()
        disps.add(Addition.Discipline("Теоретическая хирургия", Addition.SkillsArray(0, 0, 5), 10))
        specs.add(Addition.Speciality("Прикладная хирурия", 0, disps, ArrayList(), ArrayList()))
        disps.clear()
        disps.add(Addition.Discipline("Терапия", Addition.SkillsArray(0, 0, 5), 5))
        specs.add(Addition.Speciality("Общая терапия", 0, disps, ArrayList(), ArrayList()))
        specs.add(Addition.Speciality("Сестринское дело", 0, disps, ArrayList(), ArrayList()))
        val bb = ArrayList<SocietyScreen.Box>()
        bb.add(SocietyScreen.Box(5, 5, 1))
        univers.add(Addition.University(specs, bb, "First State University"))
        bb.clear()
        bb.add(SocietyScreen.Box(-5, -5, 1))
        hospitals.add(Addition.Hospital(bb, ArrayList(), ArrayList(), ArrayList(), "Sainty Hermes Hospital"))

        val boxEtt = ArrayList<Box>()
        boxEtt.add(Box(1, 1, 1023, 0))
        boxEtt.add(Box(1, 1, 1024, 0))
        boxEtt.add(Box(0, 0, 1, 0))
        boxEtt.add(Box(0, 2, 3, 0))
        boxEtt.add(Box(0, 2, 2, 0))
        boxEtt.add(Box(5, 5, 1, 0))
        boxEtt.add(Box(5, 5, 2, 0))
        boxEtt.add(Box(-5, -5, 1, 0))
        boxEtt.add(Box(-15, -15, 1, 0))
        //createBuilding(0, 1, 416, 460, "1/10/0", boxEtt);
        boxEtt.add(Box(36, 25, 16, 0))
        createBuilding(0, 1, 456, -460, "10/10/9", boxEtt)
        //createBuilding(1, 1, 1216, -80, "", new ArrayList<Box>());
        //createBuilding(2, 1, 2045, 172, "2/10/0", new ArrayList<Box>());
        //createBuilding(3, 1, 1574, 1752, "3/10/0", new ArrayList<Box>());
        //createBuilding(4, 1, 1737, 1072, "4/10/0", new ArrayList<Box>());
        //createBuilding(5, 1, -1254, 688, "5/10/0", new ArrayList<Box>());
        //createBuilding(6, 1, 1337, 339, "6/10/0", new ArrayList<Box>());
        //createBuilding(7, 1, -249, -444, "7/10/0", new ArrayList<Box>());
        //createBuilding(8, 1, 728, 172, "", new ArrayList<Box>());
        //createBuilding(9, 1, 2153, 172, "", new ArrayList<Box>());
        //createBuilding(10, 1, 1189, 1172, "", new ArrayList<Box>());

        val json = Json()
        var jsonStr = ""
        jsonStr = json.prettyPrint(boxes)
        val file = Gdx.files.local("DEFAULTCITY/boxes.txt")
        file.writeString(jsonStr, false)
        //Gdx.app.log("LOG", jsonStr);
        rotationSpeed = 0.5f
        val w = Gdx.graphics.width.toFloat()
        val h = Gdx.graphics.height.toFloat()
        cam = OrthographicCamera(1000f, 1000 * (h / w))
        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0f)
        cam.update()
        viewport = FitViewport(1280f, 720f, cam)
        multiplexer = InputMultiplexer()
        val gd = GestureDetector(this)
        multiplexer.addProcessor(gd)
        multiplexer.addProcessor(zeroStage)
        multiplexer.addProcessor(stage)
        Gdx.input.inputProcessor = multiplexer

    }

    private fun roadEtt(x: Int, y: Int, count: Int) {
        /**Сдвиг по оси х:206, сдвиг по оси у:119  */
        for (i in count downTo 1) {
            stage.addActor(Custom(Texture("city/road1.png"), (x + 206 * i).toFloat(), (y + 119 * i).toFloat()))
        }
    }

    override fun dispose() {
        stage.dispose()
    }

    private fun handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            (stage.camera as OrthographicCamera).zoom += 0.02f
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            (stage.camera as OrthographicCamera).zoom -= 0.02f
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            (stage.camera as OrthographicCamera).translate(-3f, 0f, 0f)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            (stage.camera as OrthographicCamera).translate(3f, 0f, 0f)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            (stage.camera as OrthographicCamera).translate(0f, -3f, 0f)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            (stage.camera as OrthographicCamera).translate(0f, 3f, 0f)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            (stage.camera as OrthographicCamera).rotate(-rotationSpeed, 0f, 0f, 1f)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            (stage.camera as OrthographicCamera).rotate(rotationSpeed, 0f, 0f, 1f)
        }
    }

    fun createInterface() {
        panelLeft.zIndex = 999
        panelLeft.isVisible = false
        iconTable = Table()
        scrollPane = ScrollPane(iconTable)
        outerTable = Table()
        outerTable!!.add(scrollPane).expand().left()
        outerTable!!.setPosition(10f, 30f)
        outerTable!!.setSize(700f, 270 * java.lang.Float.parseFloat(screenCoef.toString()))
        tableMove = Table(skinTva)
        tableMove!!.setBounds((Gdx.graphics.width - 80).toFloat(), 350f, 40f, 10f)
        tableMove!!.zIndex = 200
        zeroStage.addActor(outerTable)



        btnDown = TextButton("Down", tbs)
        btnDown.pad(15f)
        btnDown.addListener(object : ClickListener() {
            override fun touchDown(event: InputEvent?, x1: Float, y1: Float, pointer: Int, button: Int): Boolean {
                dest = "DOWN"
                for (actor in stage.actors) {
                    if (actor.javaClass == SocietyScreen.ScenButton::class.java) {
                        actor.remove()
                    }
                }
                return true
            }

            override fun touchUp(event: InputEvent?, x1: Float, y1: Float, pointer: Int, button: Int) {
                dest = "0"
            }
        })
        btnUp = TextButton("Up", tbs)
        btnUp.pad(15f)
        btnUp.addListener(object : ClickListener() {
            override fun touchDown(event: InputEvent?, x1: Float, y1: Float, pointer: Int, button: Int): Boolean {
                dest = "UP"
                for (actor in stage.actors) {
                    if (actor.javaClass == SocietyScreen.ScenButton::class.java) {
                        actor.remove()
                    }
                }
                return true
            }

            override fun touchUp(event: InputEvent?, x1: Float, y1: Float, pointer: Int, button: Int) {
                dest = "0"
            }
        })
        btnLeft = TextButton("Left", tbs)
        btnLeft.pad(15f)
        btnLeft.addListener(object : ClickListener() {
            override fun touchDown(event: InputEvent?, x1: Float, y1: Float, pointer: Int, button: Int): Boolean {
                dest = "LEFT"
                for (actor in stage.actors) {
                    if (actor.javaClass == SocietyScreen.ScenButton::class.java) {
                        actor.remove()
                    }
                }
                return true
            }

            override fun touchUp(event: InputEvent?, x1: Float, y1: Float, pointer: Int, button: Int) {
                dest = "0"
            }
        })
        btnRight = TextButton("Right", tbs)
        btnRight.pad(15f)
        btnRight.addListener(object : ClickListener() {
            override fun touchDown(event: InputEvent?, x1: Float, y1: Float, pointer: Int, button: Int): Boolean {
                dest = "RIGHT"
                for (actor in stage.actors) {
                    if (actor.javaClass == SocietyScreen.ScenButton::class.java) {
                        actor.remove()
                    }
                }
                return true
            }

            override fun touchUp(event: InputEvent?, x1: Float, y1: Float, pointer: Int, button: Int) {
                dest = "0"
            }
        })
        btnRotate = TextButton("Rotate", tbs)
        btnRotate.pad(15f)
        btnRotate.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                for (actor in stage.actors) {
                    if (actor.javaClass == SocietyScreen.ScenButton::class.java) {
                        actor.remove()
                    }
                }
            }
        })
        btnOverlay = TextButton("Overlay", tbs)
        btnOverlay.pad(15f)
        btnOverlay.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                //objects.get(objectObs).setZIndex(199);
            }
        })
        btnRemove = TextButton("Remove", tbs)
        btnRemove.pad(15f)
        btnRemove.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                buildings[objectObs].type = -2
                for (actor in stage.actors) {
                    if (actor.javaClass == SocietyScreen.ScenButton::class.java) {
                        actor.remove()
                    }
                }
                buildingActors[objectObs].texture = whichBuilding(-2, -2)
            }
        })

        btnMoveOk = TextButton("OK", tbs)
        btnMoveOk.pad(15f)
        btnMoveOk.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                dest = "0"
                for (actor in stage.actors) {
                    if (actor.javaClass == SocietyScreen.ScenButton::class.java) {
                        actor.remove()
                    }
                }
                tableMove!!.isVisible = false
            }
        })
        tableMove!!.add(btnUp)
        tableMove!!.row()
        tableMove!!.add(btnLeft)
        tableMove!!.row()
        tableMove!!.add(btnRight)
        tableMove!!.row()
        tableMove!!.add(btnDown)
        tableMove!!.row()
        /*tableMove.add(btnRotate);
        tableMove.row();
        tableMove.add(btnOverlay);
        tableMove.row();*/
        tableMove!!.add(btnRemove)
        tableMove!!.row()
        tableMove!!.add(btnGo)
        tableMove!!.row()
        tableMove!!.add(btnMoveOk)
        tableMove!!.row()
        //zeroStage.addActor(tableMove)


    }

    internal fun createBuilding(type: Int, appearance: Int, x: Int, y: Int, address: String) {
        when (type) {
            1 -> {
                Gdx.app.log("Выберите", "к существующей больнице или создать новую?")
                Gdx.app.log("Выберите", "к существующему универу или создать новый?")
                Gdx.app.log("Выберите", "к существующему телецентру или создать новый?")
            }
            2 -> {
                Gdx.app.log("Выберите", "к существующему универу или создать новый?")
                Gdx.app.log("Выберите", "к существующему телецентру или создать новый?")
            }
            3 -> Gdx.app.log("Выберите", "к существующему телецентру или создать новый?")
        }
        val boxes = ArrayList<Box>()
        val texture = whichBuilding(type, appearance)
        val myActor = BuildingActor(texture, x, y, buildingActors.size + 1)
        myActor.touchable = Touchable.enabled
        buildings.add(Building(type, appearance, x, y, address, boxes, buildings.size + 1))
        buildingActors.add(myActor)
        myActor.zIndex = 104
        stage.addActor(myActor)
        objectObs = buildings.size - 2
    }

    internal fun createBuilding(type: Int, appearance: Int, x: Int, y: Int, address: String, boxes: ArrayList<Box>) {
        when (type) {
            1 -> {
                Gdx.app.log("Выберите", "к существующей больнице или создать новую?")
                Gdx.app.log("Выберите", "к существующему универу или создать новый?")
                Gdx.app.log("Выберите", "к существующему телецентру или создать новый?")
            }
            2 -> {
                Gdx.app.log("Выберите", "к существующему универу или создать новый?")
                Gdx.app.log("Выберите", "к существующему телецентру или создать новый?")
            }
            3 -> Gdx.app.log("Выберите", "к существующему телецентру или создать новый?")
        }
        val texture = whichBuilding(type, appearance)
        val myActor = BuildingActor(texture, x, y, buildingActors.size + 1)
        myActor.touchable = Touchable.enabled
        buildings.add(Building(type, appearance, x, y, address, boxes, buildings.size + 1))
        buildingActors.add(myActor)
        myActor.zIndex = 104
        stage.addActor(myActor)
        objectObs = buildings.size - 2
    }

    internal fun whichBuilding(type: Int, appearance: Int): Texture {
        var texture0 = Texture("ui/null.png")
        if (type == 0) {
            when (appearance) {
                1 -> texture0 = Texture("city/office6.png")
                2 -> texture0 = Texture("city/office2.png")
                3 -> texture0 = Texture("city/office3.png")
                else -> texture0 = Texture("city/office6.png")
            }
        }
        if (type == 1) {
            when (appearance) {
                1 -> texture0 = Texture("city/hospital1.png")
                2 -> texture0 = Texture("city/hospital2.png")
                3 -> texture0 = Texture("city/hospital3.png")
                else -> texture0 = Texture("city/hospital3.png")
            }
        }
        if (type == 2) {
            when (appearance) {
                1 -> texture0 = Texture("city/university1.png")
                2 -> texture0 = Texture("city/university2.png")
                else -> texture0 = Texture("city/university2.png")
            }
        }
        if (type == 3) {
            when (appearance) {
                else -> texture0 = Texture("city/office4.png")
            }
        }
        if (type == 4) {
            when (appearance) {
                1 -> texture0 = Texture("city/office1.png")
                2 -> texture0 = Texture("city/office2.png")
                3 -> texture0 = Texture("city/office3.png")
                4 -> texture0 = Texture("city/office4.png")
                5 -> texture0 = Texture("city/office5.png")
                6 -> texture0 = Texture("city/office6.png")
                else -> texture0 = Texture("city/office7.png")
            }
        }
        if (type == 5) {
            when (appearance) {
                else -> texture0 = Texture("city/airport1.png")
            }
        }
        if (type == 7) {
            when (appearance) {
                1 -> texture0 = Texture("city/shop1.png")
                2 -> texture0 = Texture("city/shop2.png")
                3 -> texture0 = Texture("city/shop3.png")
                else -> texture0 = Texture("city/shop2.png")
            }
        }
        if (type == 8) {
            when (appearance) {
                1 -> texture0 = Texture("city/school1.png")
                else -> texture0 = Texture("city/library1.png")
            }
        }
        if (type == 9) {
            when (appearance) {
                else -> texture0 = Texture("city/stadium1.png")
            }
        }
        if (type == 10) {
            when (appearance) {
                1 -> texture0 = Texture("city/museum1.png")
                2 -> texture0 = Texture("city/museum2.png")
                else -> texture0 = Texture("city/museum1.png")
            }
        }
        return texture0
    }

    override fun show() {
        createInterface()
    }



    inner class ComparatorUser : Comparator<Any> {
        override fun compare(o1: Any, o2: Any): Int {
            val user0 = o1 as SocietyScreen.SortCard
            val user1 = o2 as SocietyScreen.SortCard

            val flag = -user0.getY().toString().compareTo(user1.getY().toString())
            return if (flag == 0) {
                user0.getX().toString().compareTo(user1.getX().toString())
            } else {
                flag
            }
        }

        /*override fun compare(arg0: Any, arg1: Any): Int {
            val user0 = arg0 as Society.SortCard
            val user1 = arg1 as Society.SortCard

            val flag = -user0.getY().toString().compareTo(user1.getY().toString())
            return if (flag == 0) {
                user0.getX().toString().compareTo(user1.getX().toString())
            } else {
                flag
            }
        }*/
    }

    override fun render(delta: Float) {
        FPS++
        handleInput()
        cam.update()
        if (FPS == 8) {
            if ("0" != dest) {
                if (dest == "UP") {
                    buildings[objectObs].y = buildings[objectObs].y + 3
                    buildingActors[objectObs].oy = buildingActors[objectObs].oy + 3
                } else if (dest == "DOWN") {
                    buildings[objectObs].y = buildings[objectObs].y - 3
                    buildingActors[objectObs].oy = buildingActors[objectObs].oy - 3
                } else if (dest == "LEFT") {
                    buildings[objectObs].x = buildings[objectObs].x - 3
                    buildingActors[objectObs].ox = buildingActors[objectObs].ox - 3
                } else if (dest == "RIGHT") {
                    buildings[objectObs].x = buildings[objectObs].x + 3
                    buildingActors[objectObs].ox = buildingActors[objectObs].ox + 3
                }
            }
            FPS = 0
        }
        if (FPS == 2) {
            sortList.clear()
            for (q in buildingActors.indices) {
                //Gdx.app.log("buildings size", buildings.size() + "  ");
                when (buildings[q].type) {
                    //case 4: sortList.add(new SortCard(buildingActors.get(q).getOX() - buildingActors.get(q).getTexture().getWidth(), buildingActors.get(q).getOY() + 80, buildingActors.get(q).getNum0()));
                    5 -> {
                        run {
                            Gdx.app.log("case 5", buildings[q].num.toString() + " ")
                            sortList.add(SocietyScreen.SortCard((buildingActors[q].ox - buildingActors[q].texture!!.width).toFloat(), (buildingActors[q].oy + 1200).toFloat(), buildingActors[q].num0))
                        }
                        sortList.add(SocietyScreen.SortCard((buildingActors[q].ox - buildingActors[q].texture!!.width).toFloat(), (buildingActors[q].oy + 1200).toFloat(), buildingActors[q].num0))
                        sortList.add(SocietyScreen.SortCard((buildingActors[q].ox - buildingActors[q].texture!!.width).toFloat(), buildingActors[q].oy.toFloat(), buildingActors[q].num0))
                    }
                    6 -> {
                        sortList.add(SocietyScreen.SortCard((buildingActors[q].ox - buildingActors[q].texture!!.width).toFloat(), (buildingActors[q].oy + 1200).toFloat(), buildingActors[q].num0))
                        sortList.add(SocietyScreen.SortCard((buildingActors[q].ox - buildingActors[q].texture!!.width).toFloat(), buildingActors[q].oy.toFloat(), buildingActors[q].num0))
                    }
                    else -> sortList.add(SocietyScreen.SortCard((buildingActors[q].ox - buildingActors[q].texture!!.width).toFloat(), buildingActors[q].oy.toFloat(), buildingActors[q].num0))
                }
            }
            val comparator = ComparatorUser()
            Collections.sort(sortList, comparator)

            if (sortList.size != 0) {
                for (q in sortList.indices) {
                    try {
                        buildingActors[sortList[q].myNum - 1].toFront()
                    } catch (e: IndexOutOfBoundsException) {
                    }

                    //stage.getActors().get(sortList.get(q).myNum).toFront();
                }
            }
        }
        //stage.setViewport(cam.combined);
        //label.setText("FPS:" + Gdx.graphics.getFramesPerSecond());
        label.setText(" ")
        stage.act(Gdx.graphics.deltaTime)
        //Gdx.gl.glClearColor(0.7f, 0.74f, 0.25f, 1);
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        stage.act(Gdx.graphics.deltaTime)
        stage.draw()
        zeroStage.act(Gdx.graphics.deltaTime)
        zeroStage.draw()
    }

    override fun resize(width: Int, height: Int) {
        cam.viewportWidth = 1000f
        cam.viewportHeight = 1000f * height / width
        cam.update()
        stage.viewport.update(width, height, true)
    }

    override fun touchDown(x: Float, y: Float, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun tap(x: Float, y: Float, count: Int, button: Int): Boolean {
        return false
    }

    override fun longPress(x: Float, y: Float): Boolean {
        return false
    }


    override fun fling(velocityX: Float, velocityY: Float, button: Int): Boolean {
        return false
    }


    override fun pan(x: Float, y: Float, deltaX: Float, deltaY: Float): Boolean {
        //(stage.camera as OrthographicCamera).translate(-deltaX / 3, deltaY / 3)
        return true
    }

    override fun panStop(x: Float, y: Float, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun zoom(initialDistance: Float, distance: Float): Boolean {
        (stage.camera as OrthographicCamera).zoom -= (distance - initialDistance) * 0.00001f
        return true
    }

    override fun pinch(initialPointer1: Vector2, initialPointer2: Vector2, pointer1: Vector2, pointer2: Vector2): Boolean {
        return false
    }

    override fun pinchStop() {

    }

    override fun pause() {}

    override fun resume() {}

    override fun hide() {

    }

    fun rnd(i: Int) = Random.nextInt(1, i)

    companion object {
        internal lateinit var btnUp: TextButton
        internal lateinit var btnDown: TextButton
        internal lateinit var btnRight: TextButton
        internal lateinit var btnLeft: TextButton
        internal lateinit var btnMoveOk: TextButton
        internal lateinit var btnOverlay: TextButton
        internal lateinit var btnRotate: TextButton
        internal lateinit var btnRemove: TextButton
        internal lateinit var btnGo: TextButton
        internal lateinit var tbs: TextButton.TextButtonStyle
        private var tableMove: Table? = null
        internal var buildingActors = ArrayList<BuildingActor>()
        internal var buildings = ArrayList<Building>()

        internal fun rnd(i: Int): Int {
            return SocietyScreen.rnd(i)
        }
    }
}