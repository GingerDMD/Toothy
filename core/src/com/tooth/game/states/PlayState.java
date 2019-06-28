package com.tooth.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.tooth.game.ToothGame;
import com.tooth.game.sprites.Tooth;
import com.tooth.game.sprites.Tube;

public class PlayState extends State {
    private static final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 4;

    private Tooth tooth;
    private Music mus;
    private Texture bg;

    private Array<Tube> tubes;

    public PlayState(GameStateManager gsm)
    {
        super(gsm);
        tooth = new Tooth(50, 300);
        cam.setToOrtho(false, ToothGame.WIDTH / 2, ToothGame.HEIGHT / 2);
        bg = new Texture("blurcolorshift.png");
        tubes = new Array<Tube>();
        for (int i = 1; i <= TUBE_COUNT; i++)
        {
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
        }

        mus = Gdx.audio.newMusic(Gdx.files.internal("music2synth.mp3"));
        Gdx.input.setCatchBackKey(true);
        mus.setVolume(0.4f);
        mus.setLooping(true);
        mus.play();
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
        cam.position.x = tooth.getPosition().x + 80;

        for (Tube tube : tubes)
        {
            if (cam.position.x - (cam.viewportWidth / 2) > tube.getPosTopTube().x + tube.getTopTube().getWidth())
            {
                tube.reposition(tube.getPosTopTube().x + ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
            }
        }
        cam.update();

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg, cam.position.x - (cam.viewportWidth / 2), 0);
        sb.draw(tooth.getTexture(), tooth.getPosition().x, tooth.getPosition().y);
        for (Tube tube : tubes)
        {
            sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            sb.draw(tube.getBottomTube(), tube.getPosBottomTube().x, tube.getPosBottomTube().y);
        }
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
