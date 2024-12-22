package Player;

import Animations.ActionState;
import com.badlogic.gdx.math.Rectangle;

public class PlayerAttacksManager {
    private final EntityPlayer animPlayer;
    private final PlayerController playerController;
    private final Rectangle hitBox;

    private ActionState[] comboMoves = {ActionState.ATTACK1, ActionState.ATTACK2, ActionState.ATTACK3, ActionState.ATTACK4};
    private int currentComboIndex = 0;
    private boolean comboInProgress = false;

    public PlayerAttacksManager(EntityPlayer animPlayer, PlayerController playerController, Rectangle hitBox) {
        this.animPlayer = animPlayer;
        this.playerController = playerController;
        this.hitBox = hitBox;
    }

    public void setStateCombo() {
        if (animPlayer.getIndexOfCurrentAnimation() >= 7 && playerController.isComboMove) {
            comboInProgress = true;

            currentComboIndex++;
            if (currentComboIndex >= comboMoves.length) {
                currentComboIndex = 0;
            }
            animPlayer.changeState(comboMoves[currentComboIndex]);
        }

        else if (!playerController.isComboMove && comboInProgress) {
            comboInProgress = false;
        }
    }
}
