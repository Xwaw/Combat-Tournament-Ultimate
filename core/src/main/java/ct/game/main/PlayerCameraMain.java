package ct.game.main;

import MapGame.Maps.MAP_TestLevel;
import MapGame.PhysicsManager;
import Player.EntityPlayer;
import Player.AnimationManager;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.viewport.*;

import static ct.game.main.GameMain.HEIGHT;
import static ct.game.main.GameMain.WIDTH;

public class PlayerCameraMain implements Screen {
    private static int offSetX = -50, offSetY = -55;
    // Camera
    private final OrthographicCamera camera;
    private final Viewport viewport;
    // Graphics
    private final SpriteBatch batch;
    // Player
    public EntityPlayer entityPlayer;
    private TextureRegion currentFrame;

    private MAP_TestLevel mapTEST;

    public PlayerCameraMain(){
        camera = new OrthographicCamera();
        viewport = new StretchViewport(WIDTH, HEIGHT, camera);
        camera.position.set((float) WIDTH / 2, (float) (HEIGHT / 2 ) + 100, 0);

        float animationFramesSpeed = 0.025f;
        entityPlayer = new EntityPlayer(100, 0, new AnimationManager(animationFramesSpeed), new Vector2(0, 0), new float[]{27, 54});

        batch = new SpriteBatch();

        mapTEST = new MAP_TestLevel(batch);
    }

    @Override
    public void render(float deltaTime) {
        refreshScreen();
        entityPlayer.updatePlayerComponents(deltaTime);

        camera.position.set(entityPlayer.getPositionX() , entityPlayer.getPositionY(), 0);

        currentFrame = entityPlayer.getCurrentFrame();

        batch.begin();

        renderPlayerFlipping();

        batch.draw(currentFrame, entityPlayer.getPositionX() + offSetX, entityPlayer.getPositionY() + offSetY, 100, 100);
        loadMap();

        batch.end();

        GameMain.getPhysicsManager().renderHitboxObjects(camera);
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

    private void renderPlayerFlipping(){
        if(entityPlayer.isEntityFlipped()){
            if(!currentFrame.isFlipX()){
                currentFrame.flip(true, false);
            }
        }else {
            if(currentFrame.isFlipX()){
                currentFrame.flip(true, false);
            }
        }
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
