package stats;

import java.awt.image.BufferedImage;
import java.util.Comparator;

public class PlayerStats {
    private String name;
    private BufferedImage icon;
    private int kills;
    private int deaths;

    public PlayerStats(BufferedImage icon, int kills, int deaths){
        this.icon = icon;
        this.kills = kills;
        this.deaths = deaths;
    }

    public String getName() {
        return name;
    }

    public BufferedImage getIcon() {
        return icon;
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }
}
