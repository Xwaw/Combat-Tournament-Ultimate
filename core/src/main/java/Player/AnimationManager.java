package Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Map;

public class AnimationManager {
    private Map<ActionSpritesAnimations, Animation<TextureRegion>> animationsMap = new HashMap<>();;
    private float duration;

    public AnimationManager(float duration){
        this.duration = duration;

        loadAnimationsFromAssets();
    }

    private void loadAnimationsFromAssets() {
        Object[][] animationData = {
            {ActionSpritesAnimations.STAND, "jsonFiles/anim_stand.json", "pngFiles/animations/anim_stand.png"},
            {ActionSpritesAnimations.RUN, "jsonFiles/anim_run.json", "pngFiles/animations/anim_run.png"},
            {ActionSpritesAnimations.DUCK, "jsonFiles/anim_duck.json", "pngFiles/animations/anim_duck.png"},
            {ActionSpritesAnimations.STARTJUMP, "jsonFiles/anim_startJump.json", "pngFiles/animations/anim_startJump.png"},
            {ActionSpritesAnimations.JUMP, "jsonFiles/anim_jump.json", "pngFiles/animations/anim_jump.png"}
        };

        for (Object[] data : animationData) {
            ActionSpritesAnimations animationType = (ActionSpritesAnimations) data[0];
            String jsonPath = (String) data[1];
            String texturePath = (String) data[2];

            animationsMap.put(
                animationType,
                AnimationLoader.createAnimation(jsonPath, duration, new Texture(Gdx.files.internal(texturePath)))
            );
        }
    }

    public Animation<TextureRegion> getCurrentAnimation(ActionSpritesAnimations currentState){
        Animation<TextureRegion> animation = animationsMap.get(currentState);
        if(currentState == ActionSpritesAnimations.DUCK){
            animation.setPlayMode(Animation.PlayMode.NORMAL);
        }

        return animation;
    }
}
