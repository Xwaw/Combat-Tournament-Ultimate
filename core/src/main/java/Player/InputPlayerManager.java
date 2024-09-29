package Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class InputPlayerManager implements InputProcessor {
    private EntityPlayer player;
    private boolean isPlayerMovingLeft, isPlayerMovingRight, isPlayerDucking;

    public InputPlayerManager(EntityPlayer player){
        this.player = player;
    }

    public void updateMovingPlayer(){
        if (isPlayerMovingRight && !isPlayerDucking) {
            player.setPlayerPosition(player.getPositionX() + player.speed * Gdx.graphics.getDeltaTime(), player.getPositionY());
            player.flipPlayerHorizontally(false);

            player.setCurrentState(ActionSpritesAnimations.RUN);
        } else if (isPlayerMovingLeft && !isPlayerDucking) {
            player.setPlayerPosition(player.getPositionX() - player.speed * Gdx.graphics.getDeltaTime(), player.getPositionY());
            player.flipPlayerHorizontally(true);

            player.setCurrentState(ActionSpritesAnimations.RUN);
        } else if (isPlayerDucking) {
            player.setCurrentState(ActionSpritesAnimations.DUCK);
        }
        else {
            player.setCurrentState(ActionSpritesAnimations.STAND);
        }

        player.getPlayerHitbox().setPosition(player.getPositionX(), player.getPositionY());
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
                    player.setPlayerDucking(true);
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
                    player.setPlayerDucking(false);
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
