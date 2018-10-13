package stats;

import java.awt.image.BufferedImage;
import java.util.*;

public class Stats {
    private ArrayList<PlayerStats> playersStats = new ArrayList<>();
    public void addPlayer(BufferedImage icon, int kills, int deaths){
        playersStats.add(new PlayerStats(icon, kills, deaths));
    }
    public ArrayList<PlayerStats> getStats(){
        Collections.sort(playersStats, new KillsComparator());
        Collections.reverse(playersStats);
        return playersStats;
    }



}
class KillsComparator implements Comparator<PlayerStats> {

    @Override
    public int compare(PlayerStats o1, PlayerStats o2) {
        return Integer.compare(o1.getKills(), o2.getKills());
    }
}