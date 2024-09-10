package ct.game.main;

import Player.AnimationController;
import Player.EntityPlayer;
import Player.InputController;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.*;

public class GameScreen implements Screen {
    // Camera
    private final Camera camera;
    private final Viewport viewport;
    // Graphics
    private final SpriteBatch batch;
    private EntityPlayer player;
    // ScreenSize
    private final int WIDTH = 712; private final int HEIGHT = 400;
    // Test
    Sprite playerSprite;

    InputController playerController;
    EntityPlayer entityPlayer;

    BitmapFont font;

    AnimationController standObjectAnimation;
    Animation<TextureRegion> standAnimation;

    float elapsedTime = 0f;

    public GameScreen(){
        camera = new OrthographicCamera();
        viewport = new StretchViewport(WIDTH, HEIGHT, camera);

        Texture standAnim = new Texture(Gdx.files.internal("pngFiles/anim_stand.png"));

        standObjectAnimation = new AnimationController("jsonFiles/anim_stand.json", standAnim);
        standAnimation = standObjectAnimation.createAnimation(0.025f);

        Texture imagePlayerTest = new Texture("pngFiles/TEST_PLAYER_SpritePositions.png");
        playerSprite = new Sprite(imagePlayerTest);
        entityPlayer = new EntityPlayer(100, 0, playerSprite, new Vector2(0, 0));

        playerController = new InputController();
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(playerController);
        Gdx.input.setInputProcessor(multiplexer);

        font = new BitmapFont();
        batch = new SpriteBatch();
    }

    private void refreshScreen(){
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void showFPSCounter(SpriteBatch batchFont){
        font.draw(batchFont, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20);
    }

    private void updateMovementPlayer(EntityPlayer player, float deltaTime){
        if(playerController.isPressedA()){
           if(player.isPlayerRotatedRight()){
               player.setPlayerRotationIntoRight(false);
           }
           player.setPlayerPosition(player.getPositionX() - player.speed * deltaTime, player.getPositionY());
        }
        if(playerController.isPressedD()){
            if(!player.isPlayerRotatedRight()){
                player.setPlayerRotationIntoRight(true);
            }
            player.setPlayerPosition(player.getPositionX() + player.speed * deltaTime, player.getPositionY());
        }
    }

    @Override
    public void render(float deltaTime) {
        refreshScreen();
        elapsedTime += deltaTime;

        batch.begin();

        updateMovementPlayer(entityPlayer, deltaTime);
        entityPlayer.drawSprite(batch);

        TextureRegion animTEST = standAnimation.getKeyFrame(elapsedTime, true);
        batch.draw(animTEST, 400, 100);

        showFPSCounter(batch);

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
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
}