package com.tooth.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.tooth.game.ToothGame;

public class Tooth {
    private static final int GRAVITY = -8;
    private static final int MOVEMENT = 100;
    private Vector3 position;
    private Vector3 velocity;
    private Rectangle bounds;
    private Texture texture;
    private Animation toothAnimation;

    public Tooth(int x, int y)
    {
        position = new Vector3(x, y, 0);
        velocity = new Vector3(50, 0, 0);
        texture = new Texture("toothfairyanim.png");
        toothAnimation = new Animation(new TextureRegion(texture), 2, 0.5f);
        bounds = new Rectangle(x, y, texture.getWidth() / 2, texture.getHeight());
    }

    public void update(float dt)
    {
        toothAnimation.update(dt);
        if (position.y > 0)
        {
            velocity.add(0, GRAVITY, 0);
        }
        velocity.scl(dt);
        position.add(0, velocity.y, 0);

        if (position.y < 0)
        {
            position.y = 0;
        }
        if ((position.y + getTexture().getRegionHeight()) > (ToothGame.HEIGHT / 2))
        {
            position.y = (int)(ToothGame.HEIGHT / 2) - getTexture().getRegionHeight();
        }
        position.add(MOVEMENT * dt, velocity.y, 0);

        velocity.scl(1/dt);
        bounds.setPosition(position.x, position.y);

    }

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getTexture() {
        return toothAnimation.getFrame();
    }

    public void jump()
    {
        velocity.y = 150;
    }

    public void dash()
    {
        position.add(getPosition().x + 200);
    }

    public Rectangle getBounds()
    {
        return bounds;
    }

    public void dispose()
    {
        texture.dispose();
    }
}
