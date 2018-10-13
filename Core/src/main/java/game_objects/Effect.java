package game_objects;

public interface Effect {
    public void effect(Player p);
    public void update(Collectable c);
    public void end();
}
