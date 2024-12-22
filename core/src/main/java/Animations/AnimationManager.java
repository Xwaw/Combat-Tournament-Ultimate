package Animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Map;

public class AnimationManager {
    private Map<ActionState, Animation<TextureRegion>> animationsMap = new HashMap<>();
    private final Map<ActionState, float[]> animationsOffSets = new HashMap<>();
    private float duration;
    private String animPathJson = "jsonFiles/characterAnimations/HomerunBatCHR";
    private String animPathPng = "pngFiles/animations/HomerunBatCHR";

    private String testPaths = "pngFiles/testUse/animations/";

    // attack1 - [7 - nextAnim]
    // attack2 - [7 - nextAnim]
    // attack3 - [5 - moveAnim *10 speed*], [9 - nextAnim]
    // attack4 - [6 - moveAnim *10 speed*], [9 - nextAnim]
    // attack5 - [10 - nextAnim], [17 - stopAnim if(key left or right pressed)]
    // attack6 - [7 - nextAnim], [4,5,6 - tagged/tp to nextPlayer]
    // attack7 - [13 - nextAnim], [2,3,4,5,6,7,8 - tagged/tp to nextPlayer]
    // attack8 - [8 - nextAnim]
    // attack9 - [13 - nextAnim]
    // attack10 - [finish - nextAnim], [moveAnim *50 speed*/*0 wall to speed*]
    // attack11 - [36 - nextAnim if(jump is pressed)]
    // attack12 - [finish - nextAnim]
    // attack13 - [finish - nextAnim], [everyFrame - speed + 20], [38 - stopMoving], [43 - speed + 40], [51 - nextAnim if moves are connect]
    // attack14 - [9 - nextAnim]
    // attack15 - [finish - nextAnim]
    // attack16 - [7 - nextAnim], [8 frames - speed + 20]
    // attack17 - [finish - nextAnim]
    // attack18 - [10 - nextAnim]
    // attack19 - [4 - speed + 10], [11 - nextAnim]
    // attack20 - [finish - nextAnim], [41 - stopAnim]
    // attack21 - [15 - nextAnim]

    public AnimationManager(float duration){
        this.duration = duration;

        loadAnimationsForPlayer();

        setOffsetsForAnimation(ActionState.STAND, 0, 0);
        setOffsetsForAnimation(ActionState.RUN, 1.7f, 3f);
        setOffsetsForAnimation(ActionState.DUCK, 0, 0);
        setOffsetsForAnimation(ActionState.JUMP, 0.3f, 0.2f);
        setOffsetsForAnimation(ActionState.STARTJUMP, -3.5f, -1.5f);

        setOffsetsForAnimation(ActionState.ATTACK1, 7.8f, -7);
        setOffsetsForAnimation(ActionState.ATTACK2, 13.8f, -0.5f);
        setOffsetsForAnimation(ActionState.ATTACK3, -5.5f, -0.5f);
        setOffsetsForAnimation(ActionState.ATTACK4, 13.8f, -0.5f);
        setOffsetsForAnimation(ActionState.ATTACK5, 0.5f, -1.0f);
        setOffsetsForAnimation(ActionState.ATTACK6, -12.2f, 1f);
        setOffsetsForAnimation(ActionState.ATTACK7, -8.8f, 1.5f);
        setOffsetsForAnimation(ActionState.ATTACK8, 40.5f, -1.0f);
        setOffsetsForAnimation(ActionState.ATTACK9, -22.5f, 1.5f);
        setOffsetsForAnimation(ActionState.ATTACK10, 4.3f, -59f);
        setOffsetsForAnimation(ActionState.ATTACK11, 16f, -0.5f);
        setOffsetsForAnimation(ActionState.ATTACK12, 21.5f, 1.1f);
        setOffsetsForAnimation(ActionState.ATTACK13, 21.5f, -4.5f);
        setOffsetsForAnimation(ActionState.ATTACK14, -13.5f, 3);
        setOffsetsForAnimation(ActionState.ATTACK15, 0.5f, -1.0f);
        setOffsetsForAnimation(ActionState.ATTACK16, -76.8f, 59.5f);
        setOffsetsForAnimation(ActionState.ATTACK17, -59.3f, 40f);
        setOffsetsForAnimation(ActionState.ATTACK18, 21.1f, 7.3f);
        setOffsetsForAnimation(ActionState.ATTACK19, 40, 8);
        setOffsetsForAnimation(ActionState.ATTACK20, 16.5f, 5f);
        setOffsetsForAnimation(ActionState.ATTACK21, -3.8f, 0f);
        setOffsetsForAnimation(ActionState.FLASHCOMBO, 16.2f, 3.5f);

        setOffsetsForAnimation(ActionState.HURT1, -14, 0);
        setOffsetsForAnimation(ActionState.HURT2, -2.8f, -1f);
        setOffsetsForAnimation(ActionState.FLYAWAY, 11, -1f);
        setOffsetsForAnimation(ActionState.LANDH, 5.5f, -2f);
        setOffsetsForAnimation(ActionState.LAND01, 14.2f, -2f);
        setOffsetsForAnimation(ActionState.DEAD, 14.2f, -2f);
        setOffsetsForAnimation(ActionState.DEAD01, 12.8f, -2f);
        setOffsetsForAnimation(ActionState.WALLED, -21.3f, 14.5f);
        setOffsetsForAnimation(ActionState.ROLLFRONT, -1.5f, -21.5f);
        setOffsetsForAnimation(ActionState.ROLLFRONT2, -1.5f, -21.5f);
        setOffsetsForAnimation(ActionState.JUMPOFF, 28f, 29.5f);
        setOffsetsForAnimation(ActionState.UPP, 14.3f, -17.4f);
        setOffsetsForAnimation(ActionState.ROLLBACK, -15f, -35.5f);
        setOffsetsForAnimation(ActionState.ROLLBACK2, -15f, -33.5f);

        setOffsetsForAnimation(ActionState.CHARGING, 5.8f, -3.5f);
        setOffsetsForAnimation(ActionState.BLAST, 23.4f, -3.5f);

        setOffsetsForAnimation(ActionState.TEST1, -16.3f, -41.5f);
        setOffsetsForAnimation(ActionState.TEST2, 12.2f, -21.5f);
    }

    private void setOffsetsForAnimation(ActionState state, float offSetX, float offSetY){
        animationsOffSets.put(state, new float[]{offSetX, offSetY});
    }

    private void loadAnimationsForPlayer() {
        Object[][] animationData = {
            {ActionState.STAND, animPathJson + "/movementJson/anim_stand.json", animPathPng + "/movement/stand.png"},
            {ActionState.RUN, animPathJson + "/movementJson/anim_run.json", animPathPng + "/movement/run.png"},
            {ActionState.DUCK, animPathJson + "/movementJson/anim_duck.json", animPathPng + "/movement/duck.png"},
            {ActionState.STARTJUMP, animPathJson + "/movementJson/anim_startJump.json", animPathPng + "/movement/startJump.png"},
            {ActionState.JUMP, animPathJson + "/movementJson/anim_jump.json", animPathPng + "/movement/jump.png"},

            {ActionState.ATTACK1, animPathJson + "/attackJson/anim_attack1.json", animPathPng + "/attacks/attack1.png"},
            {ActionState.ATTACK2, animPathJson + "/attackJson/anim_attack2.json", animPathPng + "/attacks/attack2.png"},
            {ActionState.ATTACK3, animPathJson + "/attackJson/anim_attack3.json", animPathPng + "/attacks/attack3.png"},
            {ActionState.ATTACK4, animPathJson + "/attackJson/anim_attack4.json", animPathPng + "/attacks/attack4.png"},
            {ActionState.ATTACK5, animPathJson + "/attackJson/anim_attack5.json", animPathPng + "/attacks/attack5.png"},
            {ActionState.ATTACK6, animPathJson + "/attackJson/anim_attack6.json", animPathPng + "/attacks/attack6.png"},
            {ActionState.ATTACK7, animPathJson + "/attackJson/anim_attack7.json", animPathPng + "/attacks/attack7.png"},
            {ActionState.ATTACK8, animPathJson + "/attackJson/anim_attack8.json", animPathPng + "/attacks/attack8.png"},
            {ActionState.ATTACK9, animPathJson + "/attackJson/anim_attack9.json", animPathPng + "/attacks/attack9.png"},
            {ActionState.ATTACK10, animPathJson + "/attackJson/anim_attack10.json", animPathPng + "/attacks/attack10.png"},
            {ActionState.ATTACK11, animPathJson + "/attackJson/anim_attack11.json", animPathPng + "/attacks/attack11.png"},
            {ActionState.ATTACK12, animPathJson + "/attackJson/anim_attack12.json", animPathPng + "/attacks/attack12.png"},
            {ActionState.ATTACK13, animPathJson + "/attackJson/anim_attack13.json", animPathPng + "/attacks/attack13.png"},
            {ActionState.ATTACK14, animPathJson + "/attackJson/anim_attack14.json", animPathPng + "/attacks/attack14.png"},
            {ActionState.ATTACK15, animPathJson + "/attackJson/anim_attack15.json", animPathPng + "/attacks/attack15.png"},
            {ActionState.ATTACK16, animPathJson + "/attackJson/anim_attack16.json", animPathPng + "/attacks/attack16.png"},
            {ActionState.ATTACK17, animPathJson + "/attackJson/anim_attack17.json", animPathPng + "/attacks/attack17.png"},
            {ActionState.ATTACK18, animPathJson + "/attackJson/anim_attack18.json", animPathPng + "/attacks/attack18.png"},
            {ActionState.ATTACK19, animPathJson + "/attackJson/anim_attack19.json", animPathPng + "/attacks/attack19.png"},
            {ActionState.ATTACK20, animPathJson + "/attackJson/anim_attack20.json", animPathPng + "/attacks/attack20.png"},
            {ActionState.ATTACK21, animPathJson + "/attackJson/anim_attack21.json", animPathPng + "/attacks/attack21.png"},
            {ActionState.FLASHCOMBO, animPathJson + "/attackJson/anim_flashCombo.json", animPathPng + "/attacks/flashCombo.png"},

            {ActionState.DEAD, animPathJson + "/otherJson/anim_dead.json", animPathPng + "/other/dead.png"},
            {ActionState.DEAD01, animPathJson + "/otherJson/anim_dead01.json", animPathPng + "/other/dead01.png"}, //
            {ActionState.FLYAWAY, animPathJson + "/otherJson/anim_flyAway.json", animPathPng + "/other/flyAway.png"},
            {ActionState.HURT1, animPathJson + "/otherJson/anim_hurt1.json", animPathPng + "/other/hurt1.png"}, //
            {ActionState.HURT2, animPathJson + "/otherJson/anim_hurt2.json", animPathPng + "/other/hurt2.png"}, //
            {ActionState.JUMPOFF, animPathJson + "/otherJson/anim_jumpOff.json", animPathPng + "/other/jumpOff.png"},
            {ActionState.LAND01, animPathJson + "/otherJson/anim_land01.json", animPathPng + "/other/land01.png"},
            {ActionState.LANDH, animPathJson + "/otherJson/anim_landH.json", animPathPng + "/other/landH.png"},
            {ActionState.WALLED, animPathJson + "/otherJson/anim_walled.json", animPathPng + "/other/walled.png"},
            {ActionState.ROLLBACK, animPathJson + "/otherJson/anim_rollBack.json", animPathPng + "/other/rollBack.png"},
            {ActionState.ROLLBACK2, animPathJson + "/otherJson/anim_rollBack2.json", animPathPng + "/other/rollBack2.png"},
            {ActionState.ROLLFRONT, animPathJson + "/otherJson/anim_rollFront.json", animPathPng + "/other/rollFront.png"},
            {ActionState.ROLLFRONT2, animPathJson + "/otherJson/anim_rollFront2.json", animPathPng + "/other/rollFront2.png"},
            {ActionState.UPP, animPathJson + "/otherJson/anim_upp.json", animPathPng + "/other/upp.png"},

            {ActionState.CHARGING, animPathJson + "/attackJson/anim_charging.json", animPathPng + "/attacks/charging.png"},
            {ActionState.BLAST, animPathJson + "/attackJson/anim_blast.json", animPathPng + "/attacks/blast.png"},

            {ActionState.TEST1, testPaths + "TestOffSetsAttacks.json", testPaths + "TestOffSetsAttacks.png"},
            {ActionState.TEST2, testPaths + "test2animations.json", testPaths + "test2animations.png"}
        };

        for (Object[] data : animationData) {
            ActionState animationType = (ActionState) data[0];
            String jsonPath = (String) data[1];
            String texturePath = (String) data[2];

            animationsMap.put(
                animationType,
                AnimationLoader.createAnimation(jsonPath, duration, new Texture(Gdx.files.internal(texturePath)))
            );
        }
    }

    public float[] getOffSetsAnimations(ActionState currentState){
        return animationsOffSets.get(currentState);
    }

    public Animation<TextureRegion> getCurrentAnimation(ActionState currentState){
        Animation<TextureRegion> animation = animationsMap.get(currentState);

        switch (currentState){
            case DUCK:
            case JUMP:
            case STARTJUMP:
            case ATTACK1:
            case ATTACK2:
            case ATTACK3:
            case ATTACK4:
            case ATTACK5:
            case ATTACK6:
            case ATTACK7:
            case ATTACK8:
            case ATTACK9:
            case ATTACK10:
            case ATTACK11:
            case ATTACK12:
            case ATTACK13:
            case ATTACK14:
            case ATTACK15:
            case ATTACK16:
            case ATTACK17:
            case ATTACK18:
            case ATTACK19:
            case ATTACK20:
            case ATTACK21:
            case FLASHCOMBO:
            case CHARGING:
            case BLAST:
            case ROLLFRONT:
            case ROLLFRONT2:
            case ROLLBACK:
            case ROLLBACK2:
                animation.setPlayMode(Animation.PlayMode.NORMAL);
                break;
        }

        return animation;
    }
}
