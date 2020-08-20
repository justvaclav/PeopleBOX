package com.peoplebox.additions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

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