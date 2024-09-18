package Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class InputPlayerManager implements InputProcessor {
    private EntityPlayer player;
    private boolean isPlayerMovingLeft, isPlayerMovingRight;

    public InputPlayerManager(EntityPlayer player){
        this.player = player;
    }

    public void updateMovingPlayer(){
        if (isPlayerMovingRight){
            player.setPlayerPosition(player.getPositionX() + player.speed * Gdx.graphics.getDeltaTime(), player.getPositionY());
        }
        if (isPlayerMovingLeft){
            player.setPlayerPosition(player.getPositionX() - player.speed * Gdx.graphics.getDeltaTime(), player.getPositionY());
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        updateMovingPlayer();

        switch (keycode){
            case Input.Keys.A:
                isPlayerMovingLeft = true;
                break;
            case Input.Keys.D:
                isPlayerMovingRight = true;
                break;
        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode){
            case Input.Keys.A:
                isPlayerMovingLeft = false;
                break;
            case Input.Keys.D:
                isPlayerMovingRight = false;
                break;
        }

        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
