package Player;

import Animations.ActionState;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import javax.swing.*;
import javax.swing.text.html.parser.Entity;
import java.util.HashMap;
import java.util.Map;

public class SequenceCombosLoader {
    private final String jsonFilePath = "jsonFiles/comboStylesScenarios/homeRunBatCharacterMoves/cpuHardScenario.json";
    private final EntityPlayer player;

    private Map<ActionState, HashMap<String, ActionState>> transitions = new HashMap<>();

    private Map<ActionState, String> attackTypes = new HashMap<>();

    private boolean isSpecialUsed = false;
    private boolean isSpecial = false;
    private boolean isComboMove;

    public SequenceCombosLoader(EntityPlayer player) {
        parseJsonIntoComboSequence(jsonFilePath);
        this.player = player;
        this.isComboMove = player.isPlayerAttackingMove();
    }

    public boolean isComboMove() {
        return isComboMove;
    }
    public boolean isSpecial() {
        return isSpecial;
    }
    public boolean isSpecialUsed() {
        return isSpecialUsed;
    }
    public void setSpecialUsed(boolean specialUsed) {
        isSpecialUsed = specialUsed;
    }
    public void setSpecial(boolean special) {
        isSpecial = special;
    }
    public void setComboMove(boolean comboMove) {
        isComboMove = comboMove;
    }

    public void parseJsonIntoComboSequence(String path){
        FileHandle file = Gdx.files.internal(path);
        JsonReader jsonReader = new JsonReader();
        JsonValue fileJSON = jsonReader.parse(file);

        for(JsonValue jsonValue : fileJSON){
            HashMap<String, ActionState> sequanceInput = new HashMap<>();
            JsonValue jsonValueTransitions = jsonValue.get("transitions");

            for(JsonValue transitionAttack : jsonValueTransitions){
                String input = transitionAttack.name();
                ActionState nextStateName;

                try {
                    nextStateName = ActionState.valueOf(transitionAttack.asString());
                }catch(IllegalArgumentException e){
                    nextStateName = null;
                }

                sequanceInput.put(input, nextStateName);
            }

            transitions.put(ActionState.valueOf(jsonValue.name()), sequanceInput);
        }

        for(JsonValue jsonValue : fileJSON){
            attackTypes.put(ActionState.valueOf(jsonValue.name()) ,String.valueOf(jsonValue.get("type")));
        }
    }
    public void setAttackSequence(String input){
        ActionState currentAnim = player.getCurrentState();
        HashMap<String, ActionState> possibleTransitions = transitions.get(currentAnim);
        String typeOfCurrentAttack = attackTypes.get(currentAnim);

        if (possibleTransitions == null) {
            player.changeState(ActionState.STAND);
            return;
        }

        ActionState nextState = possibleTransitions.get(input);
        System.out.println(nextState);

        if (nextState == null) {
            return;
        }

        if(player.getCurrentState() == ActionState.STAND || player.getCurrentState() == ActionState.RUN) {
            player.changeState(nextState);
        } else if(player.isAnimationFinished()){
            player.changeState(nextState);
        }
    }
}
