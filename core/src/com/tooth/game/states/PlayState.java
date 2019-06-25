package com.tooth.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tooth.game.ToothGame;
import com.tooth.game.sprites.Tooth;
import com.tooth.game.sprites.Tube;

public class PlayState extends State {

    private Tooth tooth;
    private Texture bg;
    private Tube tube;

    public PlayState(GameStateManager gsm)
    {
        super(gsm);
        tooth = new Tooth(50, 300);
        cam.setToOrtho(false, ToothGame.WIDTH / 2, ToothGame.HEIGHT / 2);
        bg = new Texture("bgnight_trim.png");
        tube = new Tube(100);
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched())
        {
            tooth.jump();
        }

    }

    @Override
    public void update(float dt) {
        handleInput();
        tooth.update(dt);

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg, cam.position.x - (cam.viewportWidth / 2), 0);
        sb.draw(tooth.getTexture(), tooth.getPosition().x, tooth.getPosition().y);
        sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
        sb.draw(tube.getBottomTube(), tube.getPosBottomTube().x, tube.getPosBottomTube().y);
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
