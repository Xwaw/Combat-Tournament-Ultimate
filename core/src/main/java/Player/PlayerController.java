package Player;

import Animations.ActionState;
import com.badlogic.gdx.math.Vector2;

public class PlayerController {
    private EntityPlayer player;
    private PlayerAttacks playerAttacks; //10, 16

    public boolean isStartJump = false;

    public boolean isMoving = false;
    public boolean isDucking = false;
    public boolean isJumping = false;
    public boolean isDodge = false;

    public PlayerController(EntityPlayer player) {
        this.player = player;
    }
    public void standPlayer(){
        if(player.isOnGround() && !isJumping && !isMoving && !isDucking && !isDodge){
            player.setCurrentState(ActionState.STAND);
        }
    }
    public void movePlayerX(float moveX){
        if (moveX != 0) {
            player.getBody().setLinearVelocity(new Vector2(moveX, player.getBody().getLinearVelocity().y));
        }
    }
    public void movePlayerY(float moveY){
        if (moveY != 0) {
            player.getBody().setLinearVelocity(new Vector2(player.getBody().getLinearVelocity().x, moveY));
        }
    }
    public void doPlayerMoveLeft(){
        movePlayerX(player.speed * -1);
        if (player.isOnGround() && !isJumping && !isDodge) {
            player.flipEntityHorizontally(true);
            if(player.isOnGround()){
                player.setCurrentState(ActionState.RUN);
            }
        }
    }
    public void doPlayerMoveRight(){
        movePlayerX(player.speed);
        if (player.isOnGround() && !isJumping && !isDodge) {
            player.flipEntityHorizontally(false);
            if(player.isOnGround()){
                player.setCurrentState(ActionState.RUN);
            }
        }
    }
    public void stopPlayer(){
        player.getBody().setLinearVelocity(new Vector2(0, player.getBody().getLinearVelocity().y));
    }
    public void jumpPlayer(float jumpHeight){
        float velocityX = player.getBody().getLinearVelocity().x;
        player.getBody().setLinearVelocity(new Vector2(velocityX, 0));
        player.getBody().applyLinearImpulse(new Vector2(velocityX, jumpHeight), player.getBody().getWorldCenter(), true);
    }

    public void setGroundJump(){
        if(isStartJump && player.getCurrentState() == ActionState.STARTJUMP && player.getIndexOfCurrentAnimation() >= 5){
            jumpPlayer(player.getJumpStrength());
            isJumping = false;
            isStartJump = false;
        }
    }

    public void checkAndSetSmallJump(){
        if(player.getCurrentState() == ActionState.STARTJUMP && player.getIndexOfCurrentAnimation() >= 4 && !isStartJump){
            jumpPlayer(player.getJumpStrength() / 3);
        }
    }

    public void doPlayerDuck(){
        if(player.isOnGround()){
            player.setCurrentState(ActionState.DUCK);
        }
        // in future there will be resize of hitbox
    }

    public void doAirJump(){
        if(player.getAirJumps() > 0 && !player.isOnGround()) {
            player.setStateTime(0);

            jumpPlayer(player.getJumpStrength());
        }
    }

    public void updateStateOnDodge(){
        if(player.isAnimationFinished() && player.isCurrentAnimDodge()){
            player.setCurrentState(ActionState.STAND);
        }
        if(player.getIndexOfCurrentAnimation() >= 18 && (player.getCurrentState() == ActionState.ROLLBACK2 || player.getCurrentState() == ActionState.ROLLFRONT2)){
            stopPlayer();
        }else{
            moveEntityOnDodge();
        }
    }
    public void moveEntityOnDodge(){
        if((!player.isFlipped() && player.getCurrentState() == ActionState.ROLLFRONT2) || (player.isFlipped() && player.getCurrentState() == ActionState.ROLLBACK2)){
            movePlayerX(player.speed * 1);
        }else if((player.isFlipped() && player.getCurrentState() == ActionState.ROLLFRONT2) || (!player.isFlipped() && player.getCurrentState() == ActionState.ROLLBACK2)){
            movePlayerX(player.speed * -1);
        }
    }
    public void doPlayerDodgeRight(){
        if(isDodge) {
            player.setRollDodgeRight();
        }
    }
    public void doPlayerDodgeLeft(){
        if(isDodge) {
            player.setRollDodgeLeft();
        }
    }
}
