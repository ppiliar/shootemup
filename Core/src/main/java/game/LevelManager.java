package game;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Trieda LevelManager nacita konfiguracny subor, ktory obsahuje zoznam levelov
 *        Nacitava aktualne potrebny gameLevel
 */
public class LevelManager {

    Level level;
    public LevelManager(){
        Properties prop = new Properties();
        InputStream input = null;

        try {

            String filename = "lvlManager.properties";
            input = getClass().getClassLoader().getResourceAsStream(filename);
            if (input == null) {
                System.out.println("Sorry, unable to find " + filename);
                return;
            }

            prop.load(input);

            level=new Level(prop.getProperty("level1"));

            //System.out.println(level.getMap());
            /*Enumeration<?> e = prop.propertyNames();
            while (e.hasMoreElements()) {
                String key = (String) e.nextElement();
                String value = prop.getProperty(key);
                System.out.println("Key : " + key + ", Value : " + value);
            }*/

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



    public void getNextLevel(){}


    public Level getCurrentLevel(){
    return level;
    }
}