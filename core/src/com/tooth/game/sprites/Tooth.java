package com.tooth.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.tooth.game.ToothGame;

public class Tooth {
    private static final int GRAVITY = -8;
    private static final int MOVEMENT = 100;
    private Vector3 position;
    private Vector3 velocity;
    private Texture tooth;

    public Tooth(int x, int y)
    {
        position = new Vector3(x, y, 0);
        velocity = new Vector3(50, 0, 0);
        tooth = new Texture("toothfairymed.png");
    }

    public void update(float dt)
    {
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
        if ((position.y + getTexture().getHeight()) > (ToothGame.HEIGHT / 2))
        {
            position.y = (int)(ToothGame.HEIGHT / 2) - getTexture().getHeight();
        }
        position.add(MOVEMENT * dt, velocity.y, 0);

        velocity.scl(1/dt);

    }

    public Vector3 getPosition() {
        return position;
    }

    public Texture getTexture() {
        return tooth;
    }

    public void jump()
    {
        velocity.y = 200;
    }

    public void dash()
    {
        position.add(getPosition().x + 200);
    }
}
