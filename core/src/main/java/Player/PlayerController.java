package Player;

import Animations.ActionState;
import com.badlogic.gdx.math.Vector2;

public class PlayerController {
    private EntityPlayer player;

    private boolean isDucking = false;

    private boolean jumpPreparing = false;

    private float moveX;

    public PlayerController(EntityPlayer player) {
        this.player = player;
    }

    public void movePlayer(float moveX){
        if (moveX != 0 || player.getBody().getLinearVelocity().x != 0) {
            player.getBody().setLinearVelocity(new Vector2(moveX, player.getBody().getLinearVelocity().y));
        }
    }

    public void stopPlayer(){
        player.getBody().setLinearVelocity(new Vector2(0, player.getBody().getLinearVelocity().y));
    }

    public void setPlayerRun(){
        float moveX = 0;
        if(player.isOnGround()) {
            if(!player.isEntityFlipped()){
                moveX = 1;
            }else{
                moveX = -1;
            }
        }
        movePlayer(moveX * player.speed);
    }

    public void setPlayerDuck(){
        stopPlayer();
        // in future there will be resize of hitbox
    }
}
