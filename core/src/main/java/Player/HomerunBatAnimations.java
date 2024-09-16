package Player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class HomerunBatAnimations {
    private AnimationManager ANIM_stand, ANIM_run, ANIM_duck, ANIM_startJump, ANIM_jump;
    private float durationAllAnimations;

    public HomerunBatAnimations(float durationAllAnimations){
        this.durationAllAnimations = durationAllAnimations;

        ANIM_stand = new AnimationManager("jsonFiles/anim_stand.json", new Texture("pngFiles/anim_stand.png"));
        ANIM_run = new AnimationManager("jsonFiles/anim_stand.json", new Texture("pngFiles/anim_stand.png"));
        ANIM_duck = new AnimationManager("jsonFiles/anim_stand.json", new Texture("pngFiles/anim_stand.png"));
        ANIM_startJump = new AnimationManager("jsonFiles/anim_stand.json", new Texture("pngFiles/anim_stand.png"));
        ANIM_jump = new AnimationManager("jsonFiles/anim_stand.json", new Texture("pngFiles/anim_stand.png"));
    }

    public Animation<TextureRegion> getCurrentAnimation(ActionSpritesAnimations nameOfAnimation){
        return switch (nameOfAnimation) {
            case STAND -> ANIM_stand.createAnimation(durationAllAnimations);
            case RUN -> ANIM_run.createAnimation(durationAllAnimations);
            case DUCK -> ANIM_duck.createAnimation(durationAllAnimations);
            case STARTJUMP -> ANIM_startJump.createAnimation(durationAllAnimations);
            case JUMP -> ANIM_jump.createAnimation(durationAllAnimations);
        };
    }
}
