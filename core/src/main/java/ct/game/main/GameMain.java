package ct.game.main;

import Player.HudManager;
import Player.InputPlayerManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class GameMain extends Game {
    public final static int WIDTH = 712, HEIGHT = 400;

    PlayerScreen gameMainScreen;
    HudManager gameHudScreen;

    InputPlayerManager keyBoardPlayer;

    @Override
    public void create() {
        gameMainScreen = new PlayerScreen();
        gameHudScreen = new HudManager(gameMainScreen.getEntityPlayer());

        keyBoardPlayer = new InputPlayerManager(gameMainScreen.getEntityPlayer());

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(keyBoardPlayer);
        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void render() {
        refreshScreenGame();

        gameMainScreen.render(Gdx.graphics.getDeltaTime());
        gameHudScreen.render(Gdx.graphics.getDeltaTime());

        keyBoardPlayer.updateMovingPlayer();
    }

    @Override
    public void resize(int width, int height) {
        gameMainScreen.resize(width, height);
        gameHudScreen.resize(width, height);
    }

    @Override
    public void dispose() {
        gameMainScreen.dispose();
        gameHudScreen.dispose();
    }

    private void refreshScreenGame(){
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}
