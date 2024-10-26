package Player;

import MapGame.PhysicsManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import ct.game.main.GameMain;

import static ct.game.main.GameMain.PPM;
import static ct.game.main.PlayerCameraMain.offSetX;
import static ct.game.main.PlayerCameraMain.offSetY;

public class EntityPlayer {
    //Hitbox and collision logic
    private final float[] hitboxSize = {44f, 129f};
    private final PhysicsManager physicsManager;
    private Body body;
    private final CollisionChecker collisionChecker;
    private Rectangle hitBox;

    //stats
    private float healthPoints;
    private float specialPoints;

    //speed logic
    private float speed = 8.0f;
    private final float damping = 1.5f;

    //jump logic
    private float jumpStrength = 35;
    public boolean jumpPreparing = false;
    private int airJumps = 2;

    // refreshFrames
    private float stateTime;

    //actions
    private boolean isFlippedHorizontally = false;
    private boolean isDucking = false;

    private final AnimationManager animationsForPlayer;

    //private Characters character = HOMERUNBAT;

    private ActionState currentState = ActionState.STAND;
    private Animation<TextureRegion> currentStateAnimation;

    public EntityPlayer(float playerHP, float playerSP, AnimationManager animationsForPlayer, Vector2 position){
        this.healthPoints = playerHP;
        this.specialPoints = playerSP;

        this.physicsManager = GameMain.getPhysicsManager();
        this.body = physicsManager.createBoxBodyForEntity(position, hitboxSize);
        this.collisionChecker = new CollisionChecker();

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

    public boolean isOnGround(){
        return collisionChecker.isGrounded(physicsManager.getWorld(), body);
    }

    private void flipPlayerOnControl(){
        TextureRegion currentFrame = currentStateAnimation.getKeyFrame(stateTime);
        if(this.isEntityFlipped()){
            if(!currentFrame.isFlipX()){
                currentFrame.flip(true, false);
            }
        }else {
            if(currentFrame.isFlipX()){
                currentFrame.flip(true, false);
            }
        }
    }

    private void performGroundJump(){
        if(isOnGround() && currentState == ActionState.STARTJUMP){
            Vector2 velocity = body.getLinearVelocity();

            if(currentStateAnimation.getKeyFrameIndex(stateTime) <= 3 && !jumpPreparing){
                body.applyLinearImpulse(new Vector2(velocity.x, jumpStrength / 18), body.getWorldCenter(), true);
            } else if (currentStateAnimation.getKeyFrameIndex(stateTime) > 3){
                body.applyLinearImpulse(new Vector2(velocity.x, jumpStrength), body.getWorldCenter(), true);
            }
        }
    }

    public void performAirJump(){
        if(airJumps > 0) {
            Vector2 velocity = body.getLinearVelocity();
            this.setCurrentState(ActionState.JUMP);
            stateTime = 0;

            if (!isOnGround()) {
                body.setLinearVelocity(new Vector2(velocity.x, 0));
                body.applyLinearImpulse(new Vector2(velocity.x, jumpStrength), body.getWorldCenter(), true);

                airJumps -= 1;
            }
        }
    }

    public void renderPlayerGraphics(SpriteBatch batch){
        TextureRegion currentFrame = currentStateAnimation.getKeyFrame(stateTime);
        float wFrame = currentFrame.getRegionWidth();
        float hFrame = currentFrame.getRegionHeight();

        batch.draw(currentFrame, (this.getPositionX() / PPM) + (offSetX / PPM), (this.getPositionY() / PPM) + (offSetY / PPM), (wFrame/2) / PPM, (hFrame/2) / PPM);
    }

    public void updatePlayerComponents(float deltaTime){
        stateTime += deltaTime;

        if(!isOnGround()){
            body.setLinearDamping(damping);
            this.setCurrentState(ActionState.JUMP);
        }else{
            body.setLinearDamping(0.0f);
            airJumps = 2;
        }

        flipPlayerOnControl();

        performGroundJump();
    }

    public void updateMovementEntity(String direction){
        float moveX = 0;
        switch (direction) {
            case "RIGHT" -> {
                if(isOnGround()) {
                    this.flipEntityHorizontally(false);
                }
                moveX = 1;
            }
            case "LEFT" -> {
                if(isOnGround()) {
                    this.flipEntityHorizontally(true);
                }
                moveX = -1;
            }
            case "DOWN" -> {
                if(isOnGround()) {
                    this.setEntityDucking(true);
                }
            }
            case "SPACE" -> {
                if(isOnGround()) {
                    this.setCurrentState(ActionState.STARTJUMP);
                }
            }
            case null, default -> {
                if(isOnGround() && currentState != ActionState.STARTJUMP) {
                    this.setCurrentState(ActionState.STAND);
                }
            }
        }

        if(currentState != ActionState.STARTJUMP) {
            if (moveX != 0) {
                body.setLinearVelocity(new Vector2(moveX * speed, body.getLinearVelocity().y));
                body.setLinearDamping(0);

                if(isOnGround()) {
                    this.setCurrentState(ActionState.RUN);
                }
            } else {
                body.setLinearDamping(damping);
            }
        }else{
            body.setLinearVelocity(0, body.getLinearVelocity().y);
        }
    }
}
