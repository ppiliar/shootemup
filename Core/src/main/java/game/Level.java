package game;

import game_objects.Npc;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Trieda Level obsahuje informacie o leveli, ktore nacita zo suboru
 */
public class Level {

    private String map;
    private String playerSkin;
    private List<Npc> npc = new ArrayList<>();

    public Level(String data){
        Properties prop = new Properties();
        InputStream input = null;

        try {

            String filename = data;
            input = getClass().getClassLoader().getResourceAsStream(filename);
            if (input == null) {
                System.out.println("Sorry, unable to find " + filename);
                return;
            }

            prop.load(input);

            this.map=prop.getProperty("map");
            this.playerSkin=prop.getProperty("playerSkin");

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getMap() {
        return map;
    }

    public String getPlayerSkin(){
        return playerSkin;
    }
}
