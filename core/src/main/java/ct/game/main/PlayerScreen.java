package ct.game.main;

import MapGame.Maps.MAP_TestLevel;
import Player.EntityPlayer;
import Player.HomerunBatAnimations;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.*;
import org.w3c.dom.css.Rect;

import static ct.game.main.GameMain.HEIGHT;
import static ct.game.main.GameMain.WIDTH;

public class PlayerScreen implements Screen {
    // Camera
    private final Camera camera;
    private final Viewport viewport;
    // Graphics
    private final SpriteBatch batch;
    private final ShapeRenderer shapeRenderer;

    public EntityPlayer entityPlayer;
    private HomerunBatAnimations homerunBatAnimations;
    private float durationOfAnimations = 0.025f;

    private TextureRegion currentFrame;

    private MAP_TestLevel mapTEST;

    public PlayerScreen(){
        camera = new OrthographicCamera();
        viewport = new StretchViewport(WIDTH, HEIGHT, camera);
        camera.position.set((float) WIDTH / 2, (float) (HEIGHT / 2 ) + 100, 0);

        homerunBatAnimations = new HomerunBatAnimations(durationOfAnimations);
        entityPlayer = new EntityPlayer(100, 0, homerunBatAnimations, new Vector2(0, 0));

        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void render(float deltaTime) {
        refreshScreen();
        entityPlayer.updatePlayerComponents(deltaTime);
        camera.position.set(entityPlayer.getPositionX() + 60, entityPlayer.getPositionY() + 150, 0);

        currentFrame = entityPlayer.getCurrentFrame();

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        Rectangle rectangle = entityPlayer.getPlayerHitbox();
        shapeRenderer.setColor(155, 0, 0, 1);
        shapeRenderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        shapeRenderer.end();


        batch.begin();

        if(entityPlayer.isPlayerFlipped()){
            if(!currentFrame.isFlipX()){
                currentFrame.flip(true, false);
            }
        }else {
            if(currentFrame.isFlipX()){
                currentFrame.flip(true, false);
            }
        }

        batch.draw(currentFrame, entityPlayer.getPositionX(), entityPlayer.getPositionY(), 100, 100);

        loadMap();

        batch.end();

        entityPlayer.setPlayerSP(entityPlayer.getPlayerSP() + 1 * deltaTime);
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
        mapTEST = new MAP_TestLevel(batch);
        mapTEST.render();
    }

    private void refreshScreen(){
        camera.update();
        batch.setProjectionMatrix(camera.combined);
    }

    public EntityPlayer getEntityPlayer() {
        return entityPlayer;
    }
}
