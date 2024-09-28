package Player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class HomerunBatAnimations {
    private AnimationManager ANIM_stand, ANIM_run, ANIM_duck, ANIM_startJump, ANIM_jump;
    private float durationAllAnimations;

    public HomerunBatAnimations(float durationAllAnimations){
        this.durationAllAnimations = durationAllAnimations;

        //movement
        ANIM_stand = new AnimationManager("jsonFiles/anim_stand.json", new Texture("pngFiles/anim_stand.png"));
        ANIM_run = new AnimationManager("jsonFiles/anim_run.json", new Texture("pngFiles/anim_run.png"));
        ANIM_duck = new AnimationManager("jsonFiles/anim_duck.json", new Texture("pngFiles/anim_duck.png"));
        ANIM_startJump = new AnimationManager("jsonFiles/anim_startJump.json", new Texture("pngFiles/anim_startJump.png"));
        ANIM_jump = new AnimationManager("jsonFiles/anim_jump.json", new Texture("pngFiles/anim_jump.png"));
    }

    public Animation<TextureRegion> getCurrentAnimation(ActionSpritesAnimations nameOfAnimation){
        return switch (nameOfAnimation) {
            case STAND -> ANIM_stand.createAnimation(durationAllAnimations);
            case RUN -> ANIM_run.createAnimation(durationAllAnimations);
            case DUCK -> {
                Animation<TextureRegion> duckAnim = ANIM_duck.createAnimation(durationAllAnimations);
                duckAnim.setPlayMode(Animation.PlayMode.NORMAL);
                yield duckAnim;
            }
            case STARTJUMP -> ANIM_startJump.createAnimation(durationAllAnimations);
            case JUMP -> ANIM_jump.createAnimation(durationAllAnimations);
        };
    }
}
