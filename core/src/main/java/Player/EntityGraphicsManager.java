package Player;

import Animations.ActionState;
import Animations.AnimationManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import static ct.game.main.GameMain.PPM;

public class EntityGraphicsManager {
    //Hitbox and collision logic
    private final float[] hitboxSize = {44f, 129f};
    private final CollisionChecker collisionChecker;
    private PlayerController playerController;

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

    private ActionState currentState = ActionState.STAND;
    private Animation<TextureRegion> currentStateAnimation;

    public EntityGraphicsManager(){
        this.animationsForPlayer = new AnimationManager(durationAnimations);
        this.currentStateAnimation = animationsForPlayer.getCurrentAnimation(currentState);

        this.playerController = new PlayerController(this);

        this.stateTime = 0.0f;
    }
    public void changeState(ActionState currentState){
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
            this.changeState(ActionState.STARTJUMP);
        }else{
            this.changeState(ActionState.JUMP);
        }
    }

    public boolean isCurrentAnimStartJump(){
        return this.getCurrentState() == ActionState.STARTJUMP;
    }
    public boolean isCurrentAnimDodge() {
        return currentState == ActionState.ROLLBACK ||
            currentState == ActionState.ROLLBACK2 ||
            currentState == ActionState.ROLLFRONT ||
            currentState == ActionState.ROLLFRONT2;
    }
    public boolean isPlayerAttackingMove(){
        String nameAttack = currentState.name();
        return nameAttack.startsWith("ATTACK");
    }

    public void setRollDodgeRight(){
        if(!this.isFlipped()){
            this.changeState(ActionState.ROLLFRONT2);
        } else{
            this.changeState(ActionState.ROLLBACK2);
        }
    }
    public void setRollDodgeLeft(){
        if(this.isFlipped()){
            this.changeState(ActionState.ROLLFRONT2);
        } else{
            this.changeState(ActionState.ROLLBACK2);
        }
    }

    public void updateIsPlayerOnAir(){
        if(!isOnGround() && !playerController.isDodge && !playerController.isComboMove){
            this.changeState(ActionState.JUMP);
        }else{
            setAirJumps(maxAirJumps);
        }
    }

    public void renderPlayerHitBox(SpriteBatch batch){
        Texture hitBoxImage = new Texture("pngFiles/testUse/testPngs/HitBox.png");

        batch.draw(hitBoxImage, hitBox.x / PPM, hitBox.y / PPM, hitBox.width / PPM, hitBox.height / PPM);
    }

    public void renderPlayerGraphics(SpriteBatch batch){
        TextureRegion currentFrame = currentStateAnimation.getKeyFrame(stateTime);

        float drawX = (this.getBodyPositionX() - ((float) currentFrame.getRegionWidth() / 2) * 0.5f) / PPM;
        float drawY = (this.getBodyPositionY() - (hitboxSize[1]/2)) / PPM;

        float offSetFrameX = this.animationsForPlayer.getOffSetsAnimations(currentState)[0] / PPM;
        float offSetFrameY = this.animationsForPlayer.getOffSetsAnimations(currentState)[1] / PPM;

        if(isFlippedHorizontally){
            offSetFrameX *= -1f;
        }
        offSetFrameY *= 1f;

        batch.draw(currentFrame, drawX + offSetFrameX, drawY + offSetFrameY, (currentFrame.getRegionWidth() * 0.5f) / PPM, (currentFrame.getRegionHeight() * 0.5f) / PPM);
    }

    public void updatePlayerGraphicsComponents(float deltaTime){
        stateTime += deltaTime;

        updateIsPlayerOnAir();

        flipSpriteOnCurrentSide();
    }

    public PlayerController getPlayerController() {
        return playerController;
    }
}
