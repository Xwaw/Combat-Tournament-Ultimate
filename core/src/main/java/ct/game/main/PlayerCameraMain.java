package ct.game.main;

import MapGame.Maps.MAP_TestLevel;
import Player.ActionState;
import Player.EntityPlayer;
import Player.AnimationManager;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.*;

import static ct.game.main.GameMain.*;

public class PlayerCameraMain implements Screen {
    private static int offSetX = -42, offSetY = -65;
    // Camera
    private final OrthographicCamera camera;
    private final Viewport viewport;
    // Graphics
    private final SpriteBatch batch;
    // Player
    public EntityPlayer entityPlayer;
    private TextureRegion currentFrame;

    private float wFrame, hFrame;

    private MAP_TestLevel mapTEST;

    public PlayerCameraMain(){
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WIDTH / PPM, HEIGHT / PPM);
        viewport = new StretchViewport(WIDTH / PPM, HEIGHT / PPM, camera);

        camera.position.set((float) WIDTH /PPM, (float) (HEIGHT ) + 100 / PPM, 0);
        camera.zoom = 1.0f;

        float animationFramesSpeed = 0.025f;
        entityPlayer = new EntityPlayer(100, 100, new AnimationManager(animationFramesSpeed), new Vector2(0, 0), new float[]{44f, 130f});

        batch = new SpriteBatch();

        mapTEST = new MAP_TestLevel(batch);
    }

    @Override
    public void render(float deltaTime) {
        refreshScreen();
        entityPlayer.updatePlayerComponents(deltaTime);

        setCameraForEntity(entityPlayer.getPositions());

        currentFrame = entityPlayer.getCurrentFrame();
        wFrame = currentFrame.getRegionWidth(); hFrame = currentFrame.getRegionHeight();

        batch.begin();

        renderPlayerFlipping();

        batch.draw(currentFrame, (entityPlayer.getPositionX() / PPM) + (offSetX / PPM), (entityPlayer.getPositionY() / PPM) + (offSetY / PPM), (wFrame/2) / PPM, (hFrame/2) / PPM);
        loadMap();

        batch.end();

        GameMain.getPhysicsManager().renderHitboxObjects(camera);
    }

    public void setCameraForEntity(Vector2 position){
        camera.position.set(position.x / PPM, position.y / PPM, 0);
    }

    public void checkBars(EntityPlayer player){
        player.setHealthPoints(player.getHealthPoints() - 1f);
        if(player.getHealthPoints() <= -100){
            player.setHealthPoints(100f);
        }

        player.setSpecialPoints(player.getSpecialPoints() - 1f);
        if(player.getSpecialPoints() <= -100){
            player.setSpecialPoints(100f);
        }
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
