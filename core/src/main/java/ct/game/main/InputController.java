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

    public Boolean getActionKey(ActionInputs input){
        return actions.get(input);
    }
    private void setActionKey(ActionInputs input, boolean key){
        actions.put(input, key);
    }

    public void updateMovingPlayer(){ // function updates everything whenever player hold a key
        if (getActionKey(ActionInputs.KEY_RIGHT)) {
            inputPlayer.setPlayerRun();
        } else if(getActionKey(ActionInputs.KEY_LEFT)) {
            inputPlayer.setPlayerRun();
        }

        if (getActionKey(ActionInputs.KEY_DOWN)) {
            inputPlayer.setPlayerDuck();
        } else if (getActionKey(ActionInputs.KEY_JUMP) && (!getActionKey(ActionInputs.KEY_LEFT) || !getActionKey(ActionInputs.KEY_RIGHT))) {
            if(player.isOnGround()) {
                player.setCurrentState(ActionState.STARTJUMP);
            }
        } else if (getActionKey(ActionInputs.KEY_ATTACK)){

        } else if (getActionKey(ActionInputs.KEY_SPECIAL)){

        } else if (getActionKey(ActionInputs.KEY_DODGE)){

        }
        else{
            if(player.isOnGround() && player.getCurrentState() != ActionState.STARTJUMP && player.getBody().getLinearVelocity().x == 0) {
                player.setCurrentState(ActionState.STAND);
            }
        }
    }

    @Override
    public boolean keyDown(int keycode) { // function works whenever player press key (just once works)
        switch (keycode){
            case Input.Keys.LEFT:
                if(player.isOnGround()){
                    player.flipEntityHorizontally(true);
                    player.setCurrentState(ActionState.RUN);
                }

                setActionKey(ActionInputs.KEY_LEFT, true);
                break;
            case Input.Keys.RIGHT:
                if(player.isOnGround()){
                    player.flipEntityHorizontally(false);
                    player.setCurrentState(ActionState.RUN);
                }

                setActionKey(ActionInputs.KEY_RIGHT, true);
                break;
            case Input.Keys.DOWN:
                player.setCurrentState(ActionState.DUCK);

                setActionKey(ActionInputs.KEY_DOWN, true);
                break;
            case Input.Keys.SPACE:
                if(!getActionKey(ActionInputs.KEY_JUMP)) {
                    if(!player.isOnGround()){
                        player.performAirJump();
                    }else{
                        player.jumpPreparing = true;
                    }
                    setActionKey(ActionInputs.KEY_JUMP, true);
                }
                break;
            case Input.Keys.A:
                if(!getActionKey(ActionInputs.KEY_ATTACK)){
                    setActionKey(ActionInputs.KEY_ATTACK, true);
                }
                break;
            case Input.Keys.S:
                if(!getActionKey(ActionInputs.KEY_SPECIAL)){
                    setActionKey(ActionInputs.KEY_SPECIAL, true);
                }
                break;
            case Input.Keys.D:
                if(!getActionKey(ActionInputs.KEY_DODGE)){
                    if(getActionKey(ActionInputs.KEY_DODGE)){
                        System.out.println("hello");
                    }

                    setActionKey(ActionInputs.KEY_DODGE, true);
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
                inputPlayer.stopPlayer();

                setActionKey(ActionInputs.KEY_LEFT, false);
                break;
            case Input.Keys.RIGHT:
                inputPlayer.stopPlayer();

                setActionKey(ActionInputs.KEY_RIGHT, false);
                break;
            case Input.Keys.DOWN:
                if(getActionKey(ActionInputs.KEY_DOWN)) {
                    setActionKey(ActionInputs.KEY_DOWN, false);
                }
                break;
            case Input.Keys.SPACE:
                if(getActionKey(ActionInputs.KEY_JUMP)) {
                    if(player.isOnGround()) {
                        player.jumpPreparing = false;
                    }
                    setActionKey(ActionInputs.KEY_JUMP, false);
                }
                break;
            case Input.Keys.A:
                if(getActionKey(ActionInputs.KEY_ATTACK)){
                    setActionKey(ActionInputs.KEY_ATTACK, false);
                }
                break;
            case Input.Keys.S:
                if(getActionKey(ActionInputs.KEY_SPECIAL)){
                    setActionKey(ActionInputs.KEY_SPECIAL, false);
                }
                break;
            case Input.Keys.D:
                if(!getActionKey(ActionInputs.KEY_DODGE)){
                    setActionKey(ActionInputs.KEY_DODGE, false);
                }
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
