package ct.game.main;

import Player.EntityGraphicsManager;
import Player.PlayerController;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

import java.util.HashMap;

public class InputController implements InputProcessor {
    private final EntityGraphicsManager player;
    private final PlayerController inputPlayer;
    private HashMap<ActionInputs, Boolean> actions;

    public InputController(EntityGraphicsManager player){
        this.player = player;
        this.inputPlayer = player.getPlayerController();

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

    public void updateMovingPlayer(){ // function updates everything whenever player hold a key or not
        inputPlayer.isJumping = player.isCurrentAnimStartJump();
        inputPlayer.isMoving = false;
        inputPlayer.isDucking = false;
        inputPlayer.isA = false;
        inputPlayer.isComboMove = player.isPlayerAttackingMove();
        inputPlayer.isDodge = player.isCurrentAnimDodge();

        if (getActionKey(ActionInputs.KEY_UP)) {

        }
        if (getActionKey(ActionInputs.KEY_ATTACK) && !inputPlayer.isJumping && !inputPlayer.isStartJump && !inputPlayer.isDodge){
            inputPlayer.isA = true;
            inputPlayer.doPlayerAttacking();
        }
        if (getActionKey(ActionInputs.KEY_DOWN) && !inputPlayer.isJumping && !inputPlayer.isMoving && !inputPlayer.isDodge && !inputPlayer.isA && !inputPlayer.isComboMove) {
            inputPlayer.doPlayerDuck();
            if(player.isOnGround()) {
                inputPlayer.isDucking = true;
            }
        }
        if (getActionKey(ActionInputs.KEY_DODGE) && !inputPlayer.isDodge && !inputPlayer.isJumping && !inputPlayer.isStartJump && !inputPlayer.isDucking && !inputPlayer.isA && !inputPlayer.isComboMove) {
            if(getActionKey(ActionInputs.KEY_RIGHT)){
                inputPlayer.isDodge = true;
                inputPlayer.doPlayerDodgeRight();

            } else if(getActionKey(ActionInputs.KEY_LEFT)){
                inputPlayer.isDodge = true;
                inputPlayer.doPlayerDodgeLeft();
            }
        }
        if (getActionKey(ActionInputs.KEY_RIGHT) && !inputPlayer.isJumping && !inputPlayer.isDucking && !inputPlayer.isDodge && !inputPlayer.isA && !inputPlayer.isComboMove) {
            inputPlayer.isMoving = true;

            inputPlayer.doPlayerMoveRight();
        }
        if(getActionKey(ActionInputs.KEY_LEFT) && !inputPlayer.isJumping && !inputPlayer.isDucking && !inputPlayer.isDodge && !inputPlayer.isA && !inputPlayer.isComboMove) {
            inputPlayer.doPlayerMoveLeft();
            inputPlayer.isMoving = true;
        }
        if (getActionKey(ActionInputs.KEY_JUMP) && !inputPlayer.isDodge && !inputPlayer.isDucking && !inputPlayer.isA && !inputPlayer.isComboMove) {
            if(player.isOnGround()){
                inputPlayer.stopPlayer();
                inputPlayer.setGroundJump();
            }
        }
        if (getActionKey(ActionInputs.KEY_SPECIAL)){

        }

        inputPlayer.standPlayer();

        inputPlayer.checkIfPlayerHoldA();

        inputPlayer.updateStateOnDodge();

        inputPlayer.checkAndSetSmallJump();
    }

    @Override
    public boolean keyDown(int keycode) { // function works whenever player press key (just once works)
        switch (keycode){
            case Input.Keys.LEFT:
                inputPlayer.isLeft = true;

                setActionKey(ActionInputs.KEY_LEFT, true);
                break;
            case Input.Keys.RIGHT:
                inputPlayer.isRight = true;

                setActionKey(ActionInputs.KEY_RIGHT, true);
                break;
            case Input.Keys.DOWN:
                inputPlayer.isDown = true;

                setActionKey(ActionInputs.KEY_DOWN, true);
                break;
            case Input.Keys.UP:
                inputPlayer.isUp = true;

                setActionKey(ActionInputs.KEY_UP, true);
                break;
            case Input.Keys.SPACE:
                if(!inputPlayer.isDodge && !inputPlayer.isA) {
                    if (player.isOnGround()) {
                        inputPlayer.isStartJump = true;
                    } else {
                        inputPlayer.doAirJump();
                        player.setAirJumps(player.getAirJumps() - 1);
                    }
                    player.setJumpStyleForCurrentCollision();
                }

                setActionKey(ActionInputs.KEY_JUMP, true);
                break;
            case Input.Keys.A:

                setActionKey(ActionInputs.KEY_ATTACK, true);
                break;
            case Input.Keys.S:

                setActionKey(ActionInputs.KEY_SPECIAL, true);
                break;
            case Input.Keys.D:

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
                if(!player.isOnGround()){
                    inputPlayer.stopPlayer();
                }
                inputPlayer.isLeft = false;

                setActionKey(ActionInputs.KEY_LEFT, false);
                break;
            case Input.Keys.RIGHT:
                if(!player.isOnGround()){
                    inputPlayer.stopPlayer();
                }
                inputPlayer.isRight = false;

                setActionKey(ActionInputs.KEY_RIGHT, false);
                break;
            case Input.Keys.DOWN:
                inputPlayer.isDown = false;

                setActionKey(ActionInputs.KEY_DOWN, false);
                break;
            case Input.Keys.SPACE:
                inputPlayer.isStartJump = false;

                setActionKey(ActionInputs.KEY_JUMP, false);
                break;
            case Input.Keys.UP:
                inputPlayer.isUp = false;

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
