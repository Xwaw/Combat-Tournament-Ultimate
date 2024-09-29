package MapGame;

import Player.EntityPlayer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.w3c.dom.Text;

import java.util.logging.Level;

import static ct.game.main.GameMain.HEIGHT;
import static ct.game.main.GameMain.WIDTH;

public class MAP_TestLevel implements Screen {
    private Camera cameraForMap;
    private Viewport viewportMap;

    private SpriteBatch batchForObjectsMap;
    private LevelEditorManager objTestFloor;

    private Texture TESTBLOCK = new Texture("blocks/Coll_BlockFloor1.png");

    public MAP_TestLevel() {
        batchForObjectsMap = new SpriteBatch();

        cameraForMap = new OrthographicCamera();
        cameraForMap.position.set(WIDTH / 2, HEIGHT / 2, 0);
        viewportMap = new StretchViewport(WIDTH, HEIGHT, cameraForMap);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float v) {
        refreshScreen();

        batchForObjectsMap.begin();

        //batchForObjectsMap.draw(TESTBLOCK, 0, 0);

        batchForObjectsMap.end();
    }

    @Override
    public void resize(int w, int h) {
        viewportMap.update(w, h, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batchForObjectsMap.dispose();
        TESTBLOCK.dispose();
    }

    private void refreshScreen() {
        cameraForMap.update();
        batchForObjectsMap.setProjectionMatrix(cameraForMap.combined);
    }
}
