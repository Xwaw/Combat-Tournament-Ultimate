package ct.game.main;

import Player.GameHUD;
import Player.InputController;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class GameMainScreen extends Game {
    public final static int WIDTH = 712, HEIGHT = 400;

    GameScreen gameMainScreen;
    InputController keyBoardPlayer;
    GameHUD gameHudScreen;

    @Override
    public void create() {
        gameMainScreen = new GameScreen();
        gameHudScreen = new GameHUD(gameMainScreen.getEntityPlayer());


        keyBoardPlayer = new InputController();
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(keyBoardPlayer);
        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void render() {
        refreshScreenGame();
        gameMainScreen.render(Gdx.graphics.getDeltaTime());
        gameHudScreen.render(Gdx.graphics.getDeltaTime());
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
        Gdx.gl.glClearColor(0, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}
