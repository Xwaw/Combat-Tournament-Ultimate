package Player;

import Animations.ActionState;
import com.badlogic.gdx.math.Rectangle;
import ct.game.main.ActionInputs;

import javax.swing.*;
import java.util.HashMap;

import static ct.game.main.GameMain.PPM;

public class PlayerAttacksManager {
    private final EntityPlayer animPlayer;
    private final Rectangle hitBox;

    public HashMap<ActionState, Integer> indexForNextMove = new HashMap<>();

    public boolean isComboMove = false;
    public boolean isSpecialUsed = false;
    public boolean isSpecial = false;

    public PlayerAttacksManager(EntityPlayer animPlayer, Rectangle hitBox) {
        this.animPlayer = animPlayer;
        this.hitBox = hitBox;
    }

    public void updateHitBoxes(){
        setHitBox(0, 0, 0, 0);
        if(animPlayer.getIndexOfCurrentAnimation() == 7 && isComboMove){
            setHitBox(50,50, 50, 50);
        }
        if(animPlayer.isAnimationFinished() && isComboMove){
            isComboMove = false;
        }
    }

    private void setHitBox(float offsetX, float offsetY, float width, float height){
        if(animPlayer.isFlipped()) {
            hitBox.set(animPlayer.getBodyPositionX() - offsetX, animPlayer.getBodyPositionY() + offsetY, -width, height);
        }else{
            hitBox.set(animPlayer.getBodyPositionX() + offsetX, animPlayer.getBodyPositionY() + offsetY, width, height);
        }
    }

    private void setAspectsAttack(ActionState actionState, int indexFrameNextAnim){
        animPlayer.setCurrentState(actionState);
        if(animPlayer.getIndexOfCurrentAnimation() == indexFrameNextAnim){

        }
    }


    public void doAttack1(){
        setAspectsAttack(ActionState.ATTACK19, 7);
    }
}
