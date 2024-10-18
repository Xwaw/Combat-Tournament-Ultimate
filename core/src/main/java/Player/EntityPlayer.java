package Player;

import MapGame.PhysicsManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import ct.game.main.GameMain;

import static ct.game.main.GameMain.PPM;

public class EntityPlayer {
    private PhysicsManager physicsManager;
    private Body body;

    public static boolean isEntityOnGround;

    private float healthPoints;
    private float specialPoints;

    private float speed = 8.0f;
    private final float maxSpeed = 4;

    private float stateTime;

    private boolean isJumpOnGround = false;

    private int jumps = 2;

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

    public void setJumps(int jumps) {
        this.jumps = jumps;
    }

    public int getJumps() {
        return jumps;
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

    public void setJumpOnGround(boolean jumping){
        isJumpOnGround = jumping;
    }

    public void jump(float jumpStrength){
        if(!(jumps <= 0)) {
            if (isEntityOnGround && currentState != ActionState.JUMP) {
                body.setLinearDamping(0f);
                body.applyLinearImpulse(new Vector2(0, jumpStrength), body.getWorldCenter(), true);
                isJumpOnGround = false;
            } else {
                body.setLinearDamping(0f);
                body.applyLinearImpulse(new Vector2(0, jumpStrength), body.getWorldCenter(), true);

                setCurrentState(ActionState.STAND);
            }
        }
    }

    public void setUpToJump(float deltaTime){
        if(currentState == ActionState.STARTJUMP){
            int indexFrames = currentStateAnimation.getKeyFrameIndex(stateTime + deltaTime);

            if(indexFrames == 3 && !isJumpOnGround){
                jump(7);
            }else if (indexFrames >= 4 && isJumpOnGround){
                jump(28);
            }
        }
    }

    public void updatePlayerComponents(float deltaTime){
        stateTime += deltaTime;

        if(!isEntityOnGround){
            setCurrentState(ActionState.JUMP);
            isJumpOnGround = false;
        }else{
            jumps = 3;
        }

        System.out.println(body.getMass());

        setUpToJump(deltaTime);
    }

    public void updateMovementEntity(String direction){
        float moveX = 0;
        switch (direction) {
            case "RIGHT" -> {
                this.flipEntityHorizontally(false);

                moveX += 1;
            }
            case "LEFT" -> {
                this.flipEntityHorizontally(true);

                moveX -= 1;
            }
            case "DOWN" -> {this.setEntityDucking(true);}
            case "SPACE" -> {
                if(!isJumpOnGround && isEntityOnGround) {
                    isJumpOnGround = true;
                    body.setLinearVelocity(0, 0);
                    this.setCurrentState(ActionState.STARTJUMP);
                }
            }
            case null, default -> {
                if (isEntityOnGround && currentState != ActionState.STARTJUMP) {
                    setCurrentState(ActionState.STAND);
                } else if (!isEntityOnGround && currentState != ActionState.STARTJUMP) {
                    setCurrentState(ActionState.JUMP);
                }
            }
        }

        if(currentState != ActionState.STARTJUMP) {
            if (moveX != 0) {
                if (!isEntityOnGround) {
                    float maxHorizontalSpeed = 3.0f;
                    Vector2 velocity = body.getLinearVelocity();
                    if (Math.abs(velocity.x) < maxHorizontalSpeed) {
                        body.setLinearVelocity(new Vector2(moveX * speed, velocity.y));
                    }
                } else {
                    body.setLinearVelocity(new Vector2(moveX * speed, body.getLinearVelocity().y));
                    body.setLinearDamping(0);
                    this.setCurrentState(ActionState.RUN);
                }
            } else {
                if (!isEntityOnGround) {
                    body.setLinearDamping(0);
                }
                body.setLinearDamping(1.5f);
            }
        }
    }
}
