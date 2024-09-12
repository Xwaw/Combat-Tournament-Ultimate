package Player;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import static Player.ActionSpritesAnimations.STAND;

public class EntityPlayer {
    private float playerHP;
    private float playerSP;
    private final Sprite sprite;
    private Vector2 position;

    public float speed = 200.0f;

    //actions
    private boolean rotationRight = true;
    private boolean ducking = false;

    private ActionSpritesAnimations currentState = STAND;

    public EntityPlayer(float playerHP, float playerSP, Sprite sprite, Vector2 position){
        this.playerHP = playerHP;
        this.playerSP = playerSP;
        this.sprite = sprite;
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
        moveSprite();
    }

    public boolean isPlayerRotatedRight(){
        return rotationRight;
    }

    public void setPlayerRotationIntoRight(boolean rotation){
        rotationRight = rotation;
        sprite.setScale(-1, 1);
        if(rotation) {
            sprite.setScale(1, 1);
        }
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

    public Sprite getSprite() {return sprite;}
    public void drawSprite(SpriteBatch batch){sprite.draw(batch);}
    private void moveSprite(){sprite.setPosition(position.x, position.y);}
}
