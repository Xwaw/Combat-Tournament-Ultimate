package ct.game.main;

import MapGame.Maps.MAP_TestLevel;
import Player.EntityPlayer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.*;

import static ct.game.main.GameMain.*;

public class PlayerCameraMain implements Screen {
    public static int offSetX = -42, offSetY = -65;
    //batch.draw(currentFrame, (entityPlayer.getPositionX() / PPM) + (offSetX / PPM), (entityPlayer.getPositionY() / PPM) + (offSetY / PPM), (wFrame/2) / PPM, (hFrame/2) / PPM);
    // Camera
    private final OrthographicCamera camera;
    private final Viewport viewport;
    // Graphics
    private final SpriteBatch batch;
    // Player
    public static EntityPlayer entityPlayer;

    private final MAP_TestLevel mapTEST;

    public PlayerCameraMain(){
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WIDTH / PPM, HEIGHT / PPM);
        viewport = new StretchViewport(WIDTH / PPM, HEIGHT / PPM, camera);

        setZoomCamera(camera, 1.00f);
        entityPlayer = new EntityPlayer(100, 0, new Vector2(0, -240));

        batch = new SpriteBatch();

        mapTEST = new MAP_TestLevel(batch);
    }

    @Override
    public void render(float deltaTime) {
        refreshScreen();
        setCameraPositions(entityPlayer.getPositions());

        entityPlayer.updatePlayerComponents(deltaTime);
        GameMain.getPhysicsManager().renderHitboxObjects(camera);

        batch.begin();

        loadMap();
        entityPlayer.renderPlayerGraphics(batch);

        batch.end();
    }

    public void setCameraPositions(Vector2 position){
        camera.position.set(position.x / PPM, (position.y) / PPM, 0);
    }

    public void setZoomCamera(OrthographicCamera camera, float zoom){
        camera.zoom = zoom;
    }

    @Override
    public void dispose() {
        batch.dispose();
        mapTEST.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void show() {

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

    private void loadMap(){
        mapTEST.render();
    }

    private void refreshScreen(){
        camera.update();
        batch.setProjectionMatrix(camera.combined);
    }

    public EntityPlayer getEntityPlayer() {
        return entityPlayer;
    }

    public Camera getCamera() {
        return camera;
    }
}
