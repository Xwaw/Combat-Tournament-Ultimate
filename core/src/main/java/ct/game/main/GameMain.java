package ct.game.main;

import MapGame.PhysicsManager;
import Player.HudManager;
import Player.InputController;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class GameMain extends Game {
    public final static int WIDTH = 1280, HEIGHT = 720;
    public final static float PPM = 50f;

    private PlayerCameraMain gameMainScreen;
    private HudManager gameHudScreen;

    private InputController keyBoardPlayer;

    private static PhysicsManager worldPhysicsManager;

    @Override
    public void create() {
        worldPhysicsManager = new PhysicsManager(new Vector2(0, -9.8f));

        gameMainScreen = new PlayerCameraMain();
        gameHudScreen = new HudManager(gameMainScreen.getEntityPlayer());

        keyBoardPlayer = new InputController(gameMainScreen.getEntityPlayer());

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(keyBoardPlayer);
        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void render() {
        worldPhysicsManager.updateWorldComponents();

        keyBoardPlayer.updateMovingPlayer();

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

        worldPhysicsManager.dispose();
    }

    public static PhysicsManager getPhysicsManager(){
        return worldPhysicsManager;
    }

    private void refreshScreenGame(){
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}
