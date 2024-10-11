package Player;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class InputController implements InputProcessor {
    private EntityPlayer player;
    private final boolean[] keys;
    //[0] - left; [1] - right; [2] - down/duck; [3] - space/jump

    public InputController(EntityPlayer player){
        this.player = player;

        keys = new boolean[256];
    }

    public void updateMovingPlayer(){
        if (keys[1] && !keys[2] && !keys[3]) {
            player.updateMovementEntity("RIGHT");
        } else if (keys[0] && !keys[2] && !keys[3]) {
            player.updateMovementEntity("LEFT");
        } else if (keys[2]) {
            player.updateMovementEntity("DOWN");
        } else if (keys[3]) {
            player.updateMovementEntity("SPACE");
        }
        else{
            player.updateMovementEntity(null);
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode){
            case Input.Keys.LEFT:
                keys[0] = true;
                break;
            case Input.Keys.RIGHT:
                keys[1] = true;
                break;
            case Input.Keys.DOWN:
                if(!keys[2]) {
                    keys[2] = true;
                }
                break;
            case Input.Keys.SPACE:
                keys[3] = true;
                break;
        }

        updateMovingPlayer();

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode){
            case Input.Keys.LEFT:
                keys[0] = false;
                break;
            case Input.Keys.RIGHT:
                keys[1] = false;
                break;
            case Input.Keys.DOWN:
                if(keys[2]) {
                    player.setEntityDucking(false);
                    keys[2] = false;
                }
                break;
            case Input.Keys.SPACE:
                keys[3] = false;
                break;
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
