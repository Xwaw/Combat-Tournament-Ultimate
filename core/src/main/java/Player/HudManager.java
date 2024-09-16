package Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static ct.game.main.GameMain.HEIGHT;
import static ct.game.main.GameMain.WIDTH;

public class HudManager implements Screen {
    //Player HUD and other huds of Game
    private final Camera camera;
    private final Viewport viewport;

    private EntityPlayer playerHUD;

    private SpriteBatch batch;
    // Images of player HUD
    private BitmapFont fpsText;

    private TextureRegion MainTextureHud;
    private TextureRegion HealthPointsBar;
    private TextureRegion SpecialPointsBar;

    private final int maxHP = 100, maxSP = 100;

    public HudManager(EntityPlayer Player) {
        this.playerHUD = Player;

        camera = new OrthographicCamera();
        viewport = new StretchViewport(WIDTH, HEIGHT, camera);

        MainTextureHud = new TextureRegion(new Texture(Gdx.files.internal("pngFiles/HUD_ImagePlayerHud.png")), 3, 54, 314, 85);
        HealthPointsBar = new TextureRegion(new Texture(Gdx.files.internal("pngFiles/HUD_ImagePlayerHud.png")), 0, 211, 208, 13);
        SpecialPointsBar = new TextureRegion(new Texture(Gdx.files.internal("pngFiles/HUD_ImagePlayerHud.png")), 0, 224, 223, 8);

        fpsText = new BitmapFont();
        batch = new SpriteBatch();
    }

    @Override
    public void show() {

    }

    private void showPlayerHud(SpriteBatch batch){
        float barWidthByHP = Math.max((playerHUD.getPlayerHP()/maxHP) * HealthPointsBar.getRegionWidth(), 0);
        float barWidthBySP = Math.max((playerHUD.getPlayerSP()/maxSP) * SpecialPointsBar.getRegionWidth(), 0);

        batch.draw(HealthPointsBar, 54, 352, barWidthByHP, 13);
        batch.draw(MainTextureHud, 3, 315);
        batch.draw(SpecialPointsBar, 86, 339, barWidthBySP, 6);
    }

    @Override
    public void render(float delta) {
        refreshScreen();

        batch.begin();

        showPlayerHud(batch);

        showFPSCounter(batch, fpsText);

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

    private void showFPSCounter(SpriteBatch batchFont, BitmapFont font){
        font.draw(batchFont, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20);
    }

    private void refreshScreen(){
        camera.update();
        batch.setProjectionMatrix(camera.combined);
    }
}
