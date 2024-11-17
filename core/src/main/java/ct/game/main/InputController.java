package ct.game.main;

import Animations.ActionState;
import Player.EntityPlayer;
import Player.PlayerController;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

import java.util.HashMap;

public class InputController implements InputProcessor {
    private final EntityPlayer player;
    private final PlayerController inputPlayer;
    private HashMap<ActionInputs, Boolean> actions;

    public InputController(EntityPlayer player){
        this.player = player;
        this.inputPlayer = new PlayerController(player);

        actions = new HashMap<>();
        for(ActionInputs state : ActionInputs.values()){
            actions.put(state, false);
        }
    }

    private Boolean getActionKey(ActionInputs input){
        return actions.get(input);
    }
    private void setActionKey(ActionInputs input, boolean key){
        actions.put(input, key);
    }

    public void updateMovingPlayer(){ // function updates everything whenever player hold a key
        inputPlayer.isJumping = player.isPlayerJumping();
        inputPlayer.isMoving = false;
        inputPlayer.isDucking = false;

        if (getActionKey(ActionInputs.KEY_DOWN) && !inputPlayer.isJumping && !inputPlayer.isMoving) {
            inputPlayer.doPlayerDuck();
            inputPlayer.isDucking = true;
        }
        if (getActionKey(ActionInputs.KEY_RIGHT) && !inputPlayer.isJumping && !inputPlayer.isDucking) {
            inputPlayer.doPlayerMoveRight();
            inputPlayer.isMoving = true;
        }
        if(getActionKey(ActionInputs.KEY_LEFT) && !inputPlayer.isJumping && !inputPlayer.isDucking) {
            inputPlayer.doPlayerMoveLeft();
            inputPlayer.isMoving = true;
        }
        if (getActionKey(ActionInputs.KEY_UP)) {

        }
        if (getActionKey(ActionInputs.KEY_JUMP)) {
            if(player.isOnGround()){
                inputPlayer.stopPlayer();
                inputPlayer.setGroundJump();
            }
        }
        if (getActionKey(ActionInputs.KEY_ATTACK)){

        }
        if (getActionKey(ActionInputs.KEY_SPECIAL)){

        }
        if (getActionKey(ActionInputs.KEY_DODGE) && inputPlayer.isMoving){
            player.setCurrentState(ActionState.ROLLFRONT2);
        }

        inputPlayer.standPlayer();

        inputPlayer.checkAndDoDodgeFront();
        inputPlayer.checkAndSetSmallJump();
    }

    @Override
    public boolean keyDown(int keycode) { // function works whenever player press key (just once works)
        switch (keycode){
            case Input.Keys.LEFT:

                setActionKey(ActionInputs.KEY_LEFT, true);
                break;
            case Input.Keys.RIGHT:

                setActionKey(ActionInputs.KEY_RIGHT, true);
                break;
            case Input.Keys.DOWN:

                setActionKey(ActionInputs.KEY_DOWN, true);
                break;
            case Input.Keys.UP:

                setActionKey(ActionInputs.KEY_UP, true);
                break;
            case Input.Keys.SPACE:
                if(player.isOnGround()) {
                    inputPlayer.isStartJump = true;
                }else{
                    inputPlayer.doAirJump();
                    player.setAirJumps(player.getAirJumps() - 1);
                }
                player.setJumpStyleForCurrentCollision();

                setActionKey(ActionInputs.KEY_JUMP, true);
                break;
            case Input.Keys.A:

                setActionKey(ActionInputs.KEY_ATTACK, true);
                break;
            case Input.Keys.S:

                setActionKey(ActionInputs.KEY_SPECIAL, true);
                break;
            case Input.Keys.D:
                inputPlayer.isDoge = true;

                setActionKey(ActionInputs.KEY_DODGE, true);
                break;
        }

        updateMovingPlayer();

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode){
            case Input.Keys.LEFT:

                setActionKey(ActionInputs.KEY_LEFT, false);
                break;
            case Input.Keys.RIGHT:

                setActionKey(ActionInputs.KEY_RIGHT, false);
                break;
            case Input.Keys.DOWN:

                setActionKey(ActionInputs.KEY_DOWN, false);
                break;
            case Input.Keys.SPACE:
                inputPlayer.isStartJump = false;

                setActionKey(ActionInputs.KEY_JUMP, false);

                break;
            case Input.Keys.UP:

                setActionKey(ActionInputs.KEY_UP, false);
                break;
            case Input.Keys.A:

                setActionKey(ActionInputs.KEY_ATTACK, false);
                break;
            case Input.Keys.S:

                setActionKey(ActionInputs.KEY_SPECIAL, false);
                break;
            case Input.Keys.D:

                setActionKey(ActionInputs.KEY_DODGE, false);
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
