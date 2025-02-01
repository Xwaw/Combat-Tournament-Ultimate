package Animations;

class AnimationData {
    public String getPathJson() {
        return pathJson;
    }

    public String getPathPNG() {
        return pathPNG;
    }

    public ActionState getActionState() {
        return actionState;
    }

    public int getFrameNextMove() {
        return frameNextMove;
    }

    public float[] getOffsets() {
        return offsets;
    }

    private String pathJson;
    private String pathPNG;
    private ActionState actionState;
    private int frameNextMove;
    private float[] offsets;

    public AnimationData(String pathJson, String pathPNG, ActionState actionState, int frameNextMove, float[] offsets){
        this.pathJson = pathJson;
        this.pathPNG = pathPNG;
        this.actionState = actionState;
        this.frameNextMove = frameNextMove;
        this.offsets = offsets;
    }
}
