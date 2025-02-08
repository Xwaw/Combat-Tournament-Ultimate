package Player;

import Animations.ActionState;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.HashMap;
import java.util.Map;

public class SequenceCombosLoader {
    private final String jsonFilePath = "jsonFiles/comboStylesScenarios/homeRunBatCharacterMoves/cpuHardScenario.json";

    private Map<ActionState, HashMap<String, ActionState>> transitions = new HashMap<>();

    private Map<ActionState, String> attackTypes = new HashMap<>();

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
    }

    public SequenceCombosLoader() {
        parseJsonIntoComboSequence(jsonFilePath);
    }

    public void setAttackSequence(){

    }
}
