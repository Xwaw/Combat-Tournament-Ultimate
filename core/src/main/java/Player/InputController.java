package Player;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

public class InputController implements InputProcessor {
    private EntityPlayer player;
    private boolean isPlayerMovingLeft, isPlayerMovingRight, isPlayerDucking;

    public InputController(EntityPlayer player){
        this.player = player;
    }

    public void updateMovingPlayer(){
        if (isPlayerMovingRight && !isPlayerDucking) {
            player.updateMovementEntity("RIGHT");
        } else if (isPlayerMovingLeft && !isPlayerDucking) {
            player.updateMovementEntity("LEFT");
        } else if (isPlayerDucking) {
            player.updateMovementEntity("DOWN");
        }
        else{
            player.updateMovementEntity(null);
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode){
            case Input.Keys.LEFT:
                isPlayerMovingLeft = true;

                break;
            case Input.Keys.RIGHT:
                isPlayerMovingRight = true;

                break;
            case Input.Keys.DOWN:
                if(!isPlayerDucking) {
                    isPlayerDucking = true;
                }

                break;
        }

        updateMovingPlayer();

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode){
            case Input.Keys.LEFT:
                isPlayerMovingLeft = false;
                break;
            case Input.Keys.RIGHT:
                isPlayerMovingRight = false;
                break;
            case Input.Keys.DOWN:
                if(isPlayerDucking) {
                    player.setEntityDucking(false);
                    isPlayerDucking = false;
                }
        }

        updateMovingPlayer();

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
