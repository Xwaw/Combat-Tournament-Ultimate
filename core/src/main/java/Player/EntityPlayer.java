package Player;

import MapGame.PhysicsManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import ct.game.main.GameMain;

import java.util.Objects;

public class EntityPlayer {
    private PhysicsManager physicsManager;
    private Body body;

    private float healthPoints;
    private float specialPoints;

    public float speed = 100f;
    private float stateTime;

    //actions
    private boolean isFlippedHorizontally = false;
    private boolean isDucking = false;

    private AnimationManager animationsForPlayer;

    //settingControlsFor Player

    //private Characters character = HOMERUNBAT;

    private ActionFlags currentState = ActionFlags.STAND;
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

    public void setCurrentState(ActionFlags currentState){
        if (this.currentState != currentState) {
            this.currentState = currentState;
            currentStateAnimation = animationsForPlayer.getCurrentAnimation(currentState);

            stateTime = 0.0f;
        }
    }

    public void updatePlayerComponents(float deltaTime){
        stateTime += deltaTime;
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
            this.setCurrentState(ActionFlags.DUCK);
            isDucking = true;
        }else{
            isDucking = false;
        }
    }

    public float getPositionX(){
        return body.getPosition().x;
    }
    public float getPositionY(){
        return body.getPosition().y;
    }

    public void updateMovementEntity(String direction){
        Vector2 velocity = body.getLinearVelocity();

        switch (direction) {
            case "RIGHT" -> {
                this.flipEntityHorizontally(false);
                this.setCurrentState(ActionFlags.RUN);

                body.setLinearVelocity(new Vector2(speed, velocity.y));
            }
            case "LEFT" -> {
                this.flipEntityHorizontally(true);
                this.setCurrentState(ActionFlags.RUN);

                body.setLinearVelocity(new Vector2(-speed, velocity.y));
            }
            case "DOWN" -> {
                this.setEntityDucking(true);

                body.setLinearVelocity(new Vector2(0, velocity.y));
            }
            case null, default -> {
                this.setCurrentState(ActionFlags.STAND);

                body.setLinearVelocity(new Vector2(0, velocity.y));
            }
        }
    }
}
