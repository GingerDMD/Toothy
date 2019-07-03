package com.tooth.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.tooth.game.ToothGame;
import com.tooth.game.sprites.Tooth;
import com.tooth.game.sprites.Tube;

public class PlayState extends State {
    private static final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 4;
    private static final int GROUND_OFFSET = -50;

    private Tooth tooth;
    private Music mus;
    private Texture bg;
    private Texture ground;

    private Vector2 groundPos1, groundPos2;

    private Array<Tube> tubes;

    public PlayState(GameStateManager gsm)
    {
        super(gsm);
        tooth = new Tooth(50, 300);
        cam.setToOrtho(false, ToothGame.WIDTH / 2, ToothGame.HEIGHT / 2);
        bg = new Texture("blurcolorshift.png");
        ground = new Texture("groundcandy2.png");
        groundPos1 = new Vector2(cam.position.x - cam.viewportWidth / 2, GROUND_OFFSET);
        groundPos2 = new Vector2((cam.position.x - cam.viewportWidth / 2) + ground.getWidth(), GROUND_OFFSET);
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
        updateGround();
        tooth.update(dt);
        cam.position.x = tooth.getPosition().x + 80;

        for (int i = 0; i < tubes.size; i++)
        {
            Tube tube = tubes.get(i);
            if (cam.position.x - (cam.viewportWidth / 2) > tube.getPosTopTube().x + tube.getTopTube().getWidth())
            {
                tube.reposition(tube.getPosTopTube().x + ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
            }
            if (tube.collides(tooth.getBounds()))
            {
                setNewPS();
            }
        }
        if (tooth.getPosition().y <= ground.getHeight() + GROUND_OFFSET)
        {
            setNewPS();
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
        sb.draw(ground, groundPos1.x, groundPos1.y);
        sb.draw(ground, groundPos2.x, groundPos2.y);

        sb.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        tooth.dispose();
        ground.dispose();
        for (Tube tube : tubes)
        {
            tube.dispose();
        }

        //System.out.println("dispose ps");

    }

    private void updateGround()
    {
        if (cam.position.x - (cam.viewportWidth / 2) > groundPos1.x + ground.getWidth())
        {
            groundPos1.add(ground.getWidth() * 2, 0);
        }
        if (cam.position.x - (cam.viewportWidth / 2) > groundPos2.x + ground.getWidth())
        {
            groundPos2.add(ground.getWidth() * 2, 0);
        }
    }

    public void setNewPS()
    {
        mus.stop();
        gsm.set(new PlayState(gsm));
    }
}
