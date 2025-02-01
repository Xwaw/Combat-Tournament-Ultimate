package Player;

import Animations.ActionState;
import com.badlogic.gdx.math.Rectangle;

public class PlayerAttacksManager {
    private final EntityPlayer animPlayer;
    private final PlayerController playerController;
    private final Rectangle hitBox;

    private ActionState[] comboMoves = {ActionState.ATTACK3, ActionState.ATTACK4, ActionState.ATTACK7};
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

            if (currentComboIndex >= comboMoves.length) {
                currentComboIndex = 0;
            }
            animPlayer.changeState(comboMoves[currentComboIndex]);

            currentComboIndex++;
        }
        else if (!playerController.isComboMove && comboInProgress) {
            comboInProgress = false;
        }
    }
}
