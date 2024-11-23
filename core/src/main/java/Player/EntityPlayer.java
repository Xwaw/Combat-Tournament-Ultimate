package Player;

import Animations.ActionState;
import Animations.AnimationManager;
import MapGame.PhysicsManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import ct.game.main.GameMain;

import static ct.game.main.GameMain.PPM;

public class EntityPlayer {
    //Hitbox and collision logic
    private final float[] hitboxSize = {44f, 129f};
    private final PhysicsManager physicsManager;
    private Body body;
    private final CollisionChecker collisionChecker;
    private Rectangle hitBox;
    private PlayerController playerController;

    //stats
    private float healthPoints; //standard = 100
    private float specialPoints; //standard = 100
    private final float lowDamage = 0.5f, standardDamage = 1f, hardDamage = 2f;
    private boolean isSpecialReady; //
    public boolean isPlayerAttacking; //

    //speed logic
    public float speed = 10.0f;

    //jump logic
    private final float jumpStrength = 25;
    private int maxAirJumps = 2;
    private int airJumps = 2;

    // refreshFrames
    private float stateTime;

    //actions
    private boolean isFlippedHorizontally = false;

    private final AnimationManager animationsForPlayer;
    private final float durationAnimations = 0.025f; //defeault = 0.025f

    //private Characters character = HOMERUNBAT;

    private ActionState currentState = ActionState.STAND;
    private Animation<TextureRegion> currentStateAnimation;

    public EntityPlayer(float playerHP, float playerSP, Vector2 position){
        this.healthPoints = playerHP;
        this.specialPoints = playerSP;

        playerController = new PlayerController(this);

        this.physicsManager = GameMain.getPhysicsManager();
        this.body = physicsManager.createBoxBodyForEntity(position, hitboxSize);
        this.collisionChecker = new CollisionChecker();

        this.animationsForPlayer = new AnimationManager(durationAnimations);
        this.currentStateAnimation = animationsForPlayer.getCurrentAnimation(currentState);

        this.stateTime = 0.0f;
    }

    public PlayerController getPlayerController() {
        return playerController;
    }

    public Body getBody() {
        return body;
    }
    public void setCurrentState(ActionState currentState){
        if (this.currentState != currentState) {
            this.currentState = currentState;
            currentStateAnimation = animationsForPlayer.getCurrentAnimation(currentState);

            stateTime = 0.0f;
        }
    }
    public ActionState getCurrentState() {
        return currentState;
    }
    public void setStateTime(float time){
        stateTime = time;
    }
    public boolean isFlipped(){
        return isFlippedHorizontally;
    }
    public void flipEntityHorizontally(boolean flipX){
        isFlippedHorizontally = flipX;
    }
    private void flipSpriteOnCurrentSide(){
        TextureRegion currentFrame = currentStateAnimation.getKeyFrame(stateTime);
        if(this.isFlipped()){
            if(!currentFrame.isFlipX()){
                currentFrame.flip(true, false);
            }
        }else {
            if(currentFrame.isFlipX()){
                currentFrame.flip(true, false);
            }
        }
    }
    public int getIndexOfCurrentAnimation(){
        return currentStateAnimation.getKeyFrameIndex(stateTime);
    }
    public boolean isAnimationFinished(){
        return currentStateAnimation.isAnimationFinished(stateTime);
    }
    public void setJumpStyleForCurrentCollision(){
        if(isOnGround()){
            this.setCurrentState(ActionState.STARTJUMP);
        }else{
            this.setCurrentState(ActionState.JUMP);
        }
    }

    public boolean isCurrentAnimStartJump(){
        return this.getCurrentState() == ActionState.STARTJUMP;
    }
    public boolean isCurrentAnimDodge() {
        return this.getCurrentState() == ActionState.ROLLBACK ||
            this.getCurrentState() == ActionState.ROLLBACK2 ||
            this.getCurrentState() == ActionState.ROLLFRONT ||
            this.getCurrentState() == ActionState.ROLLFRONT2;
    }

    public void setRollDodgeRight(){
        if(!this.isFlipped()){
            this.setCurrentState(ActionState.ROLLFRONT2);
        } else{
            this.setCurrentState(ActionState.ROLLBACK2);
        }
    }
    public void setRollDodgeLeft(){
        if(this.isFlipped()){
            this.setCurrentState(ActionState.ROLLFRONT2);
        } else{
            this.setCurrentState(ActionState.ROLLBACK2);
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
    private void regenerateSpecialBar(float progressRegen){
        if(specialPoints < 100.0f && !isSpecialReady){
            specialPoints += progressRegen;
        }else{
            isSpecialReady = true;
        }
    }

    public int getAirJumps(){
        return airJumps;
    }
    public float getJumpStrength() {
        return jumpStrength;
    }

    public void setAirJumps(int jumps){
        airJumps = jumps;
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

    public void updateIsPlayerOnAir(){
        if(!isOnGround() && !playerController.isDodge){
            this.setCurrentState(ActionState.JUMP);
        }else{
            setAirJumps(maxAirJumps);
        }
    }

    public void renderPlayerGraphics(SpriteBatch batch){
        TextureRegion currentFrame = currentStateAnimation.getKeyFrame(stateTime);

        float drawX = (this.getPositionX() - ((float) currentFrame.getRegionWidth() / 2) * 0.5f) / PPM;
        float drawY = (this.getPositionY() - (hitboxSize[1]/2)) / PPM;

        float offSetFrameX = this.animationsForPlayer.getOffSetsAnimations(currentState)[0] / PPM;
        float offSetFrameY = this.animationsForPlayer.getOffSetsAnimations(currentState)[1] / PPM;

        if(isFlippedHorizontally){
            offSetFrameX *= -1f;
        }
        offSetFrameY *= 1f;

        batch.draw(currentFrame, drawX + offSetFrameX, drawY + offSetFrameY, (currentFrame.getRegionWidth() * 0.5f) / PPM, (currentFrame.getRegionHeight() * 0.5f) / PPM);
    }

    public void updatePlayerComponents(float deltaTime){
        stateTime += deltaTime;

        updateIsPlayerOnAir();

        flipSpriteOnCurrentSide();

        regenerateSpecialBar(0.005f);
    }
}
