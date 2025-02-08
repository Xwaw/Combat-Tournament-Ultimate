package Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static ct.game.main.GameMain.HEIGHT;
import static ct.game.main.GameMain.WIDTH;

public class  HudManager implements Screen {
    //Player HUD and other huds of Game
    private final Camera cameraHUD;
    private final Viewport viewport;

    private final EntityPlayer playerHUD;
    private final PlayerController inputHUD;

    private final SpriteBatch batch;
    // Images of player HUD
    private final BitmapFont fpsText;
    private final BitmapFont positionsText;
    private final BitmapFont eventFlagsText;

    private final TextureRegion MainTextureHud;
    private final TextureRegion HealthPointsBar;
    private final TextureRegion SpecialPointsBar;

    private final int maxHP = 100, maxSP = 100;

    public HudManager(EntityPlayer playerHUD) {
        this.playerHUD = playerHUD;
        this.inputHUD = playerHUD.getPlayerController();

        cameraHUD = new OrthographicCamera();
        viewport = new StretchViewport(WIDTH, HEIGHT, cameraHUD);

        MainTextureHud = new TextureRegion(new Texture(Gdx.files.internal("pngFiles/Hud/imagePlayerHud.png")), 3, 54, 314, 85);
        HealthPointsBar = new TextureRegion(new Texture(Gdx.files.internal("pngFiles/Hud/imagePlayerHud.png")), 0, 211, 208, 13);
        SpecialPointsBar = new TextureRegion(new Texture(Gdx.files.internal("pngFiles/Hud/imagePlayerHud.png")), 0, 224, 223, 8);

        positionsText = new BitmapFont();
        fpsText = new BitmapFont();
        eventFlagsText = new BitmapFont();

        batch = new SpriteBatch();
    }

    @Override
    public void show() {

    }

    private void showPlayerHud(SpriteBatch batch){
        float barWidthByHP = Math.max((playerHUD.getHealthPoints()/maxHP) * HealthPointsBar.getRegionWidth(), 0);
        float barWidthBySP = Math.max((playerHUD.getSpecialPoints()/maxSP) * SpecialPointsBar.getRegionWidth(), 0);

        batch.draw(HealthPointsBar, 80,  HEIGHT * 0.875f, barWidthByHP * 1.49f, 14 * 1.46f);
        batch.draw(MainTextureHud, 3, HEIGHT * 0.8f, MainTextureHud.getRegionWidth() * 1.5f, MainTextureHud.getRegionHeight() * 1.5f);
        batch.draw(SpecialPointsBar, 127, HEIGHT * 0.85f, barWidthBySP * 1.49f, 6 * 1.5f);
    }

    @Override
    public void render(float delta) {
        refreshScreen();

        batch.begin();
            showPlayerHud(batch);

            showFPSCounter(batch, fpsText);
            showInfoAboutPlayer(batch, positionsText);
            showEventFlagsPlayer(batch, eventFlagsText);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
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
        batch.dispose();
    }

    private void showFPSCounter(SpriteBatch batchFont, BitmapFont text){
        text.draw(batchFont, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20);
    }

    private void showInfoAboutPlayer(SpriteBatch batchFont, BitmapFont text){
        text.draw(batchFont, "X: " + playerHUD.getBodyPositionX() + " | Y: " + playerHUD.getBodyPositionY(), 10, 40);
    }

    private void showEventFlagsPlayer(SpriteBatch batchFont, BitmapFont text){
        text.draw(batchFont,
                "ActionState: " + playerHUD.getCurrentState() + "\n" +
                "isMoving: " + inputHUD.isMoving + "\n" +
                "isDucking: " + inputHUD.isDucking + "\n" +
                "isJumping: " + inputHUD.isJumping + "\n" +
                "isStartJumping: " + inputHUD.isStartJump + "\n" +
                "isDodge: " + inputHUD.isDodge + "\n" +
                "isComboMove: " + inputHUD.isComboMove + "\n" +
                "isRight: " + inputHUD.isRight + "\n" +
                "isLeft: " + inputHUD.isLeft+ "\n" +
                "isUp: " + inputHUD.isUp + "\n" +
                "isDown: " + inputHUD.isDown + "\n" +
                "isSpecialUsed: " + "\n" +
                "isSpecial: "
            , 1120, 215
            );
    }

    private void refreshScreen(){
        cameraHUD.update();
        batch.setProjectionMatrix(cameraHUD.combined);
    }
}
