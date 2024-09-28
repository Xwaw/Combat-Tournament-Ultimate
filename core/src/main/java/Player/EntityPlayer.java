package Player;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import static Player.ActionSpritesAnimations.STAND;

public class EntityPlayer {
    private float playerHP;
    private float playerSP;
    private Vector2 position;

    public float speed = 270.0f;
    private float stateTime;

    //actions
    private boolean isFlippedHorizontally = false;
    private boolean ducking = false;

    private HomerunBatAnimations homerunBatAnimations;

    //private Characters character = HOMERUNBAT;

    private ActionSpritesAnimations currentState = STAND;
    private Animation<TextureRegion> currentAnimation;

    public EntityPlayer(float playerHP, float playerSP, HomerunBatAnimations homerunBatAnimations,Vector2 position){
        this.playerHP = playerHP;
        this.playerSP = playerSP;
        this.position = position;
        this.homerunBatAnimations = homerunBatAnimations;
        this.currentAnimation = homerunBatAnimations.getCurrentAnimation(ActionSpritesAnimations.STAND);

        this.stateTime = 0.0f;
    }

    public void setCurrentState(ActionSpritesAnimations currentState){
        if (this.currentState != currentState) {
            this.currentState = currentState;
            currentAnimation = homerunBatAnimations.getCurrentAnimation(currentState);
        }
    }

    public void updatePlayerElements(float deltaTime){
        stateTime += deltaTime;
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

    public TextureRegion getCurrentFrame(){
        return currentAnimation.getKeyFrame(stateTime);
    }

    public void setPlayerSP(float specialPoints){ // xd
        playerSP = specialPoints;
        if(playerSP >= 100) {
            playerSP = 100;
        }
    }

    public void setPlayerPosition(float x, float y)
    {
        position = new Vector2(x, y);
    }

    public boolean isPlayerFlipped(){
        return isFlippedHorizontally;
    }

    public void flipPlayerHorizontally(boolean flipX){
        isFlippedHorizontally = flipX;
    }

    public boolean isPlayerDucking() {
        return ducking;
    }
    public void setPlayerDucking(boolean duck) {
        stateTime = 0.0f;

        ducking = duck;
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
