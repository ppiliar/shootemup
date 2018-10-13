package game_objects;

public class CollectableModel {
    private final String imgName;
    private final Effect effect;

    public CollectableModel(String imgName, Effect effect){
        this.imgName = imgName;
        this.effect = effect;
    }
    public String getImgName(){
        return imgName;
    }

    public Effect getEffect() {
        return effect;
    }
}
