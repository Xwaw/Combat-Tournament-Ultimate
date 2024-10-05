package Player;

import MapGame.PhysicsManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import ct.game.main.GameMain;

import static Player.ActionSpritesAnimations.DUCK;
import static Player.ActionSpritesAnimations.STAND;

public class EntityPlayer {
    private PhysicsManager physicsManager;
    private Body body;

    private float healthPoints;
    private float specialPoints;

    public float speed = 400.0f;
    private float stateTime;

    //actions
    private boolean isFlippedHorizontally = false;
    private boolean isDucking = false;

    private AnimationManager animationsForPlayer;

    //private Characters character = HOMERUNBAT;

    private ActionSpritesAnimations currentState = STAND;
    private Animation<TextureRegion> currentStateAnimation;

    public EntityPlayer(float playerHP, float playerSP, AnimationManager animationsForPlayer, Vector2 position, float[] hitBoxSize){
        this.healthPoints = playerHP;
        this.specialPoints = playerSP;

        this.physicsManager = GameMain.getPhysicsManager();
        this.body = physicsManager.createBody(position, hitBoxSize,false);

        this.animationsForPlayer = animationsForPlayer;
        this.currentStateAnimation = animationsForPlayer.getCurrentAnimation(currentState);
        this.stateTime = 0.0f;
    }

    public void setCurrentState(ActionSpritesAnimations currentState){
        if (this.currentState != currentState) {
            this.currentState = currentState;
            currentStateAnimation = animationsForPlayer.getCurrentAnimation(currentState);

            stateTime = 0.0f;
        }
    }

    public void updatePlayerComponents(float deltaTime){
        stateTime += deltaTime;

        physicsManager.updateWorldComponents();
    }

    public float getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int health) {
        healthPoints = health;
        if(healthPoints >= 100){
            healthPoints = 100;
        }
    }

    public float getSpecialPoints() {
        return specialPoints;
    }

    public TextureRegion getCurrentFrame(){
        return currentStateAnimation.getKeyFrame(stateTime);
    }

    public void setSpecialPoints(float specialPoints){ // xd
        this.specialPoints = specialPoints;
        if(this.specialPoints >= 100) {
            this.specialPoints = 100;
        }
    }

    public boolean isEntityFlipped(){
        return isFlippedHorizontally;
    }

    public void flipEntityHorizontally(boolean flipX){
        isFlippedHorizontally = flipX;
    }

    public boolean isEntityDucking() {
        return isDucking;
    }
    public void setEntityDucking(boolean duck) {
        if(duck) {
            this.setCurrentState(DUCK);
            isDucking = true;
        }else{
            isDucking = false;
        }
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }

    public float getPositionX() {
        return body.getPosition().x;
    }

    public float getPositionY() {
        return body.getPosition().y;
    }

    public void setEntityPosition(float x, float y) {
        body.setTransform(x, y, 0);
    }
}
