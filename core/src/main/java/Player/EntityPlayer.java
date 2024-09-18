package Player;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import static Player.ActionSpritesAnimations.STAND;

public class EntityPlayer {
    private float playerHP;
    private float playerSP;
    private final Animation<TextureRegion> animationPlayer;
    private Vector2 position;

    public float speed = 200.0f;

    //actions
    private boolean rotationRight = true;
    private boolean ducking = false;

    //private Characters character = HOMERUNBAT;

    private ActionSpritesAnimations currentState = STAND;

    public EntityPlayer(float playerHP, float playerSP, Animation<TextureRegion> animationPlayer, Vector2 position){
        this.playerHP = playerHP;
        this.playerSP = playerSP;
        this.animationPlayer = animationPlayer;
        this.position = position;
    }

    public float getPlayerHP() {
        return playerHP;
    }

    public void setPlayerHP(int health) {
        playerHP = health;
        if(playerHP >= 100){
            playerHP = 100;
        }
    }

    public float getPlayerSP() {
        return playerSP;
    }
    public void setPlayerSP(int specialPoints){ // xd
        playerSP = specialPoints;
        if(playerSP >= 100) {
            playerSP = 100;
        }
    }

    public void setPlayerPosition(float x, float y)
    {
        position = new Vector2(x, y);
    }

    public boolean isPlayerRotatedRight(){
        return rotationRight;
    }

    public boolean isPlayerDucking() {
        return ducking;
    }
    public void setPlayerDucking(boolean duck) {
        ducking = duck;
    }

    public void getPlayerPosition() {
        position = new Vector2(position.x, position.y);
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getPositionX() {
        return position.x;
    }

    public float getPositionY() {
        return position.y;
    }
}
