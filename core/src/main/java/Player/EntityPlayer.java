package Player;

import MapGame.PhysicsManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import ct.game.main.GameMain;

import static ct.game.main.GameMain.PPM;

public class EntityPlayer {
    private PhysicsManager physicsManager;
    private Body body;

    public static boolean isOnGround;

    private float healthPoints;
    private float specialPoints;

    private float speed = 4.5f;
    private final float maxSpeed = 4;

    private float stateTime;

    private float jumpStrength = 0.015f;
    private final float maxJumpStrength = 0.015f;
    private int jumps = 3;

    //actions
    private boolean isFlippedHorizontally = false;
    private boolean isDucking = false;

    private final AnimationManager animationsForPlayer;

    //settingControlsFor Player

    //private Characters character = HOMERUNBAT;

    private ActionState currentState = ActionState.STAND;
    private Animation<TextureRegion> currentStateAnimation;

    public EntityPlayer(float playerHP, float playerSP, AnimationManager animationsForPlayer, Vector2 position, float[] hitBoxSize){
        this.healthPoints = playerHP;
        this.specialPoints = playerSP;

        this.physicsManager = GameMain.getPhysicsManager();
        this.body = physicsManager.createBoxBodyForEntity(position, hitBoxSize);

        this.animationsForPlayer = animationsForPlayer;
        this.currentStateAnimation = animationsForPlayer.getCurrentAnimation(currentState);

        this.stateTime = 0.0f;
    }

    public void setCurrentState(ActionState currentState){
        if (this.currentState != currentState) {
            this.currentState = currentState;
            currentStateAnimation = animationsForPlayer.getCurrentAnimation(currentState);

            stateTime = 0.0f;
        }
    }

    public float getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(float healthPoints) {
        this.healthPoints = Math.min(healthPoints, 100);
    }

    public float getSpecialPoints() {
        return specialPoints;
    }

    public void setSpecialPoints(float specialPoints){
        this.specialPoints = Math.min(specialPoints, 100);
    }

    public TextureRegion getCurrentFrame(){
        return currentStateAnimation.getKeyFrame(stateTime);
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
            this.setCurrentState(ActionState.DUCK);
            isDucking = true;
        }else{
            isDucking = false;
        }
    }

    public float getPositionX(){
        return body.getPosition().x * PPM;
    }
    public float getPositionY(){
        return body.getPosition().y * PPM;
    }
    public Vector2 getPositions(){
        return new Vector2(body.getPosition().x * PPM, body.getPosition().y * PPM);
    }

    public void updatePlayerComponents(float deltaTime){
        stateTime += deltaTime;

        if(!isOnGround){
            setCurrentState(ActionState.JUMP);
        }
    }

    private void jump(){
        if (currentStateAnimation.isAnimationFinished(stateTime) && isOnGround) {
            body.applyLinearImpulse(new Vector2(0, jumpStrength), body.getWorldCenter(), true);
        }
    }

    public void updateMovementEntity(String direction){
        float moveX = 0;
        switch (direction) {
            case "RIGHT" -> {
                if(isOnGround) {
                    this.flipEntityHorizontally(false);
                    this.setCurrentState(ActionState.RUN);
                }

                moveX += 1;
            }
            case "LEFT" -> {
                if(isOnGround) {
                    this.flipEntityHorizontally(true);
                    this.setCurrentState(ActionState.RUN);
                }

                moveX -= 1;
            }
            case "DOWN" -> {this.setEntityDucking(true);}
            case "SPACE" -> {
                if(isOnGround){
                    this.setCurrentState(ActionState.STARTJUMP);
                }
                jump();
            }
            case null, default -> {
                if(!isOnGround){
                    this.setCurrentState(ActionState.JUMP);
                }else {
                    this.setCurrentState(ActionState.STAND);
                }
            }
        }

        if(moveX != 0) {
            if (!isOnGround) {
                float maxHorizontalSpeed = 3.0f;
                Vector2 velocity = body.getLinearVelocity();

                if (Math.abs(velocity.x) < maxHorizontalSpeed) {
                    body.setLinearVelocity(new Vector2(moveX * speed, velocity.y));
                }
            } else {
                body.setLinearVelocity(new Vector2(moveX * speed, body.getLinearVelocity().y));
                body.setLinearDamping(0);
            }
        }
        else {
            if(!isOnGround){
                body.setLinearDamping(0);
            }
            body.setLinearDamping(1.5f);
        }
    }
}
