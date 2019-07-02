package com.tooth.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tooth.game.ToothGame;

public class MenuState extends State {
    private Texture background;
    private Texture playButton;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("bg_jung.png");
        playButton = new Texture("play_button.png");
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched())
        {
            gsm.set(new PlayState(gsm));
        }

    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0, 0, ToothGame.WIDTH, ToothGame.HEIGHT);
        sb.draw(playButton, (ToothGame.WIDTH / 2) - (playButton.getWidth() / 2), (ToothGame.HEIGHT / 2));
        sb.end();

    }

    @Override
    public void dispose() {
        background.dispose();
        playButton.dispose();
    }
}
