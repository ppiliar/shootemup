package state_manager;


import drawable.DrawablePanel;
import drawable.Frame;
import game.*;
import graphics.LabelGraphics;
import game_objects.Player;
import graphics.PlayerGraphics;
import graphics.RadioButtonGraphics;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import stats.PlayerStats;
import stats.Stats;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;

public class GuiManager implements ActionListener,Runnable {
    protected static final Logger LOGGER = Logger.getLogger( GuiManager.class.getName() );

    Frame frame = new Frame();
    XmlReader xmlReader = new XmlReader();
    LoggerConfiguration lc = new LoggerConfiguration();
    //Window window = new Window();
    public void Start(){

    }

    public static void main(String[] args) {
        GuiManager g = new GuiManager();
        try {
            g.launch();
            g.menu();
            //g.playMenu();
            //g.live();
            // g.menu();
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }

    }

    public void launch() throws ParserConfigurationException, SAXException, IOException, InterruptedException {
        LOGGER.info("Gui manager start");

        final int LOGO_TIME = 1000;

        //XmlReader xmlReader = new XmlReader();

        BufferedImage backgroundImage=new BufferedImage(800, 480, BufferedImage.TYPE_INT_RGB);
        Graphics backgroundGraphics = backgroundImage.createGraphics();
        backgroundGraphics.setColor(Color.WHITE);
        backgroundGraphics.fillRect(0, 0, 800, 480);
        //backgroundGraphics.dispose();

        Document startData = xmlReader.readFile("data/start.xml");
        Node launch = startData.getElementsByTagName("launch").item(0);
        String logoFile =((Element) launch).getElementsByTagName("background").item(0).getTextContent();
        //String logoFile = startData.getElementsByTagName("background").item(0).getTextContent();
        BufferedImage logoImage = ImageIO.read(this.getClass().getResourceAsStream("/"+logoFile));
        int x= (backgroundImage.getWidth()-logoImage.getWidth())/2;
        int y = (backgroundImage.getHeight()-logoImage.getHeight())/2;
        backgroundGraphics.drawImage(logoImage, x, y,  null);
        DrawablePanel panel = new DrawablePanel(backgroundImage);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

        sleep(LOGO_TIME);

        backgroundGraphics.fillRect(0, 0, 800, 480);

        logoFile =((Element) launch).getElementsByTagName("background").item(1).getTextContent();
        logoImage = ImageIO.read(this.getClass().getResourceAsStream("/"+logoFile));
        x= (backgroundImage.getWidth()-logoImage.getWidth())/2;
        y = (backgroundImage.getHeight()-logoImage.getHeight())/2;
        backgroundGraphics.drawImage(logoImage, x, y, null );
        panel.setImg(backgroundImage);
        frame.repaint();

        sleep(LOGO_TIME);
            /*window.add(panel);
            window.pack();*/

    }

    public void menu() throws ParserConfigurationException, SAXException, IOException, FontFormatException {
        LOGGER.info("Gui manager menu");

        //XmlReader xmlReader = new XmlReader();

        // Nacitanie pozadia

        BufferedImage backgroundImage=new BufferedImage(800, 480, BufferedImage.TYPE_INT_RGB);
        Graphics backgroundGraphics = backgroundImage.createGraphics();
        backgroundGraphics.setColor(Color.WHITE);
        backgroundGraphics.fillRect(0, 0, 800, 480);
        //backgroundGraphics.dispose();

        Document startData = xmlReader.readFile("data/start.xml");
        Node start = startData.getElementsByTagName("menu").item(0);
        String logoFile =((Element) start).getElementsByTagName("background").item(0).getTextContent();
        BufferedImage logoImage = ImageIO.read(this.getClass().getResourceAsStream("/"+logoFile));
        // Zmena velkosti obrazka
        Image logoImageScaled = logoImage.getScaledInstance(800, 480, Image.SCALE_SMOOTH);
        logoImage.getGraphics().drawImage(logoImageScaled, 0, 0, null );

        backgroundGraphics.drawImage(logoImage, 0, 0,  null);
        DrawablePanel dpanel = new DrawablePanel(backgroundImage);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.PAGE_AXIS));
        panel.setOpaque(false);
        dpanel.add(panel);

        // vykreslenie tlacidiel, ktorych nastavenia sa nacitavaju z xml suboru

        Node rootNode = startData.getElementsByTagName("startData").item(0);
        Element element = (Element) rootNode;
        Element menu = (Element)element.getElementsByTagName("menu").item(0);
        NodeList buttons = menu.getElementsByTagName("button");

        for(int i=0; i< buttons.getLength();i++){
            Element buttonE = (Element)buttons.item(i);

            JButton btn = new JButton();
            btn.setForeground(Color.WHITE);
            btn.setHorizontalTextPosition(JButton.CENTER);
            btn.setVerticalTextPosition(JButton.CENTER);
            btn.setBorder(BorderFactory.createEmptyBorder());
            btn.setContentAreaFilled(false);
            btn.setFocusable(false);
            //btn.setPreferredSize(new Dimension(300,200));

            NodeList images = buttonE.getElementsByTagName("image");
            for(int i1=0;i1<images.getLength();i1++){
                Element image = (Element)images.item(i1);

                switch (image.getAttribute("state")){
                    case "start":
                        btn.setIcon(new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/"+image.getTextContent()))));
                        break;
                    case "pressed":
                        btn.setRolloverIcon(new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/"+image.getTextContent()))));
                        break;
                    case "highlighted":
                        btn.setPressedIcon(new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/"+image.getTextContent()))));
                        break;
                    default:break;
                }
            }
            // nacita text a font tlacitka
            Element button = (Element) buttonE.getElementsByTagName("text").item(0);
            btn.setText(buttonE.getElementsByTagName("text").item(0).getTextContent());
            InputStream fontFile = this.getClass().getResourceAsStream("/fonts/"+button.getAttribute("font"));
            if(fontFile!=null) {
                btn.setFont(Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(Float.parseFloat(button.getAttribute("size"))));
            }

            btn.setActionCommand(buttonE.getAttribute("action"));
            btn.addActionListener(this);

            panel.add(btn);
        }


        frame.getContentPane().removeAll();
        frame.add(dpanel);
        frame.revalidate();
        frame.pack();
    }

    public void live(int time, int kills) {

        LOGGER.info("Gui manager live");


        frame.getContentPane().removeAll();
        frame.revalidate();
        frame.requestFocus();
        // Vlakno potrebne aby mohol gameloop ziskavat znaky z klavesnice
        Thread t = new Thread() {
            @Override
            public void run() {
                super.run();

                // Vlakno pre samotny beh gameloopu
                GameGraphics game = new GameGraphics();
                GameLoop gameLoop = new GameLoop(frame, game, time, kills);
                Thread thread = new Thread(gameLoop);
                thread.start();
                LOGGER.info("Gameloop listener thread started");
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                stats(game);
                /*try {
                    stats(game);
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                }catch (IOException e) {
                    e.printStackTrace();
                } catch (FontFormatException e) {
                    e.printStackTrace();
                }*/

            }
        };
        t.start();

    }
    public void settings(){
        BufferedImage backgroundImage=new BufferedImage(800, 480, BufferedImage.TYPE_INT_RGB);
        Graphics backgroundGraphics = backgroundImage.createGraphics();
        backgroundGraphics.setColor(Color.WHITE);
        backgroundGraphics.fillRect(0, 0, 800, 480);
        //backgroundGraphics.dispose();

        Document startData = xmlReader.readFile("data/start.xml");
        Node start = startData.getElementsByTagName("menu").item(0);
        String logoFile =((Element) start).getElementsByTagName("background").item(0).getTextContent();
        BufferedImage logoImage = null;
        try {
            logoImage = ImageIO.read(this.getClass().getResourceAsStream("/"+logoFile));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        // Zmena velkosti obrazka
        Image logoImageScaled = logoImage.getScaledInstance(800, 480, Image.SCALE_SMOOTH);
        logoImage.getGraphics().drawImage(logoImageScaled, 0, 0, null );

        backgroundGraphics.drawImage(logoImage, 0, 0,  null);
        DrawablePanel dpanel = new DrawablePanel(backgroundImage);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.PAGE_AXIS));
        panel.setOpaque(false);
        dpanel.add(panel);


        String[] nameList;
        Document playersData = xmlReader.readFile("data/players.xml");
        NodeList playerList = playersData.getElementsByTagName("name");
        nameList = new String[playerList.getLength()];
        for(int i=0; i<playerList.getLength();i++){
            nameList[i] = playerList.item(i).getTextContent();
        }

        for (String name:nameList) {
            JTextField tf = new JTextField(name);
            //LabelGraphics label = new LabelGraphics(name);
            panel.add(tf);
        }
        JButton exit = new JButton("Spat");
        /*exit.setForeground(Color.WHITE);
        exit.setHorizontalTextPosition(JButton.CENTER);
        exit.setVerticalTextPosition(JButton.CENTER);
        exit.setBorder(BorderFactory.createEmptyBorder());
        exit.setContentAreaFilled(false);
        exit.setFocusable(false);*/

        exit.addActionListener(e -> {
            try {
                menu();
            } catch (ParserConfigurationException e1) {
                e1.printStackTrace();
            } catch (SAXException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (FontFormatException e1) {
                e1.printStackTrace();
            }
        });
        panel.add(exit);

        frame.getContentPane().removeAll();
        frame.add(dpanel);
        frame.revalidate();
        frame.pack();
    }

    public void playMenu() {
        LOGGER.info("Gui manager playMenu");

        //nacitanie pozadia

        BufferedImage backgroundImage=new BufferedImage(800, 480, BufferedImage.TYPE_INT_RGB);
        Graphics backgroundGraphics = backgroundImage.createGraphics();
        backgroundGraphics.setColor(Color.WHITE);
        backgroundGraphics.fillRect(0, 0, 800, 480);
        //backgroundGraphics.dispose();

        Document startData = xmlReader.readFile("data/start.xml");
        Node start = startData.getElementsByTagName("menu").item(0);
        String logoFile =((Element) start).getElementsByTagName("background").item(0).getTextContent();
        BufferedImage logoImage = null;
        try {
            logoImage = ImageIO.read(this.getClass().getResourceAsStream("/"+logoFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Zmena velkosti obrazka
        Image logoImageScaled = logoImage.getScaledInstance(800, 480, Image.SCALE_SMOOTH);
        logoImage.getGraphics().drawImage(logoImageScaled, 0, 0, null );

        backgroundGraphics.drawImage(logoImage, 0, 0,  null);
        DrawablePanel dpanel = new DrawablePanel(backgroundImage);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.PAGE_AXIS));
        panel.setOpaque(false);
        dpanel.add(panel);
        /*JPanel borderPanel = new JPanel(new BorderLayout());
        borderPanel.add(panel,BorderLayout.CENTER);
        dpanel.add(borderPanel);*/

        //Vyber poctu hracov
        RadioButtonGraphics onePlayer = new RadioButtonGraphics("Jeden hrac");
        onePlayer.setActionCommand("1");
        RadioButtonGraphics twoPlayers = new RadioButtonGraphics("Dvaja hraci");
        twoPlayers.setActionCommand("2");
        RadioButtonGraphics threePlayers = new RadioButtonGraphics("Traja hraci");
        threePlayers.setActionCommand("3");
        //radioButton.setText("Ano");
        onePlayer.setSelected(true);
        ButtonGroup g1 = new ButtonGroup();
        g1.add(onePlayer);
        g1.add(twoPlayers);
        g1.add(threePlayers);
        panel.add(new LabelGraphics("Pocet Hracov"));
        panel.add(onePlayer);
        panel.add(twoPlayers);
        panel.add(threePlayers);
        //panel.setOpaque(false);
        //dpanel.add(panel);

        //Vyber typu hry
        JPanel timePanel = new JPanel();
        timePanel.setOpaque(false);
        RadioButtonGraphics time = new RadioButtonGraphics("Čas v sekundách");
        time.setActionCommand("time");
        SpinnerModel timeSpinnerModel = new SpinnerNumberModel(120,1,600,1);
        JSpinner timeSpinner = new JSpinner(timeSpinnerModel);
        ///timeSpinner.setVisible(false);
        //time.addActionListener(e -> timeSpinner.setVisible(true));
        timePanel.add(time);
        timePanel.add(timeSpinner);

        JPanel killsPanel = new JPanel();
        killsPanel.setOpaque(false);
        RadioButtonGraphics kills = new RadioButtonGraphics("Počet zabití");
        kills.setActionCommand("kills");
        SpinnerModel killsSpinnerModel = new SpinnerNumberModel(10,1,50,1);
        JSpinner killsSpinner = new JSpinner(killsSpinnerModel);
        //killsSpinner.setVisible(false);
        //kills.addActionListener(e -> killsSpinner.setVisible(true));
        killsPanel.add(kills);
        killsPanel.add(killsSpinner);

        time.setSelected(true);
        ButtonGroup g2 = new ButtonGroup();
        g2.add(time);
        g2.add(kills);
        panel.add(new LabelGraphics("Typ hry"));
        panel.add(timePanel);
        panel.add(killsPanel);

        //Vyber mapy
        panel.add(new LabelGraphics("Mapa"));

        DefaultListModel listModel = new DefaultListModel();
        listModel.addElement("map1");
        listModel.addElement("5030");
        listModel.addElement("cars");

        JList list = new JList(listModel);
        list.setOpaque(false);

        list.setSelectedIndex(0);
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        list.setVisibleRowCount(-1);
        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setPreferredSize(new Dimension(250, 80));
        panel.add(list);
        //Tlacidlo
        JButton go = new JButton("Hrat");
        //go.setActionCommand("live");
        go.addActionListener(e -> {
            Properties prop = new Properties();
            prop.setProperty("no_players", g1.getSelection().getActionCommand());
            prop.setProperty("type", g2.getSelection().getActionCommand());
            prop.setProperty("map", list.getSelectedValue().toString());
            try {
                FileOutputStream fos = new FileOutputStream("live.properties");
                prop.store(fos,"live settings");
                fos.close();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            if(time.isSelected()){
                live((int)timeSpinner.getValue(),0);
            }else{live(0,(int)killsSpinner.getValue());}
        });

        panel.add(go);

        frame.getContentPane().removeAll();
        frame.add(dpanel);
        frame.revalidate();
        frame.pack();
        frame.setVisible(true);
    }

    public void stats(Game game) {

        BufferedImage backgroundImage=new BufferedImage(800, 480, BufferedImage.TYPE_INT_RGB);
        Graphics backgroundGraphics = backgroundImage.createGraphics();
        backgroundGraphics.setColor(Color.WHITE);
        backgroundGraphics.fillRect(0, 0, 800, 480);
        //backgroundGraphics.dispose();

        Document startData = xmlReader.readFile("data/start.xml");
        Node start = startData.getElementsByTagName("menu").item(0);
        String logoFile =((Element) start).getElementsByTagName("background").item(0).getTextContent();
        BufferedImage logoImage = null;
        try {
            logoImage = ImageIO.read(this.getClass().getResourceAsStream("/"+logoFile));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        // Zmena velkosti obrazka
        Image logoImageScaled = logoImage.getScaledInstance(800, 480, Image.SCALE_SMOOTH);
        logoImage.getGraphics().drawImage(logoImageScaled, 0, 0, null );

        backgroundGraphics.drawImage(logoImage, 0, 0,  null);
        DrawablePanel dpanel = new DrawablePanel(backgroundImage);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.PAGE_AXIS));
        panel.setOpaque(false);
        dpanel.add(panel);

        List<Player> playerList =  game.getPlayers();
        Stats stats = new Stats();
        for (Player player:playerList) {
            BufferedImage pIcon = ((PlayerGraphics) player).getIcon();
            stats.addPlayer(pIcon, player.getKills(), player.getDeaths());
        }
        List<PlayerStats> playerStats = stats.getStats();
        int pos = 1;
        for(PlayerStats pStat:playerStats) {
            JPanel playerStatPanel = new JPanel();
            playerStatPanel.setOpaque(false);
            playerStatPanel.add(new LabelGraphics(pos+". miesto "));
            pos++;
            playerStatPanel.add(new DrawablePanel(pStat.getIcon()));
            playerStatPanel.add(new LabelGraphics("Kills: " + pStat.getKills()));
            playerStatPanel.add(new LabelGraphics("Deaths: " + pStat.getDeaths()));
            panel.add(playerStatPanel);
        }

        JButton exit = new JButton("Menu");
        /*exit.setForeground(Color.WHITE);
        exit.setHorizontalTextPosition(JButton.CENTER);
        exit.setVerticalTextPosition(JButton.CENTER);
        exit.setBorder(BorderFactory.createEmptyBorder());
        exit.setContentAreaFilled(false);
        exit.setFocusable(false);*/

        exit.addActionListener(e -> {
            try {
                menu();
            } catch (ParserConfigurationException e1) {
                e1.printStackTrace();
            } catch (SAXException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (FontFormatException e1) {
                e1.printStackTrace();
            }
        });
        panel.add(exit);
        frame.getContentPane().removeAll();
        frame.add(dpanel);
        frame.revalidate();
        frame.pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "live":
                LOGGER.fine("Live button pressed");
                playMenu();
                break;
            case "settings":
                LOGGER.fine("Settings button pressed");
                settings();break;
            default:break;
        }
    }

    @Override
    public void run() {

    }
}

