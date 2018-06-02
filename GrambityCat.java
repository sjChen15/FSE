//GrambityCat.java
//Jenny Chen
//package com.company;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/*
things to do
- speed up power up
- make the actual levels
- door to get to the next level

small things to do
- fix walking while flying thing
- fix upsidedown animation
- stopping completely stops in a stand

 */

public class GrambityCat extends JFrame implements ActionListener{
    private javax.swing.Timer myTimer;	//the Timer
    private JPanel cards;	//the cards
    private CardLayout cLayout = new CardLayout();	//the layout
    private JButton playBtn = new JButton("Play");	//the button that starts the game
    private JButton inBtn  = new JButton("Instructions");

    private JButton lv1 = new JButton("1");
    private JButton lv2 = new JButton("2");
    private JButton lv3 = new JButton("3");

    private GamePanel game;	//the game panel

    private int level = 1; //level that player choose
    private int hLevel = 1; //the highest level the player can play

    //the game constructor
    public GrambityCat(){
        super("Grambity Cat");
        setSize(800,600);
        game = new GamePanel(this,level);
        add(game);

        playBtn.addActionListener(this);
        inBtn.addActionListener(this);
        lv1.addActionListener(this);
        lv2.addActionListener(this);
        lv3.addActionListener(this);

        myTimer = new javax.swing.Timer(5, this);

        //mainpage card
        ImageIcon mainBack = new ImageIcon(getClass().getResource("mainback.png"));
        JLabel backLabel = new JLabel(mainBack);
        JLayeredPane mPage = new JLayeredPane();
        mPage.setLayout(null);
        backLabel.setSize(800,600);
        backLabel.setLocation(-10,-15);
        mPage.add(backLabel,1);

        //instruction card
        ImageIcon instructBack = new ImageIcon(getClass().getResource("instructions.png")); //page picture
        JLabel instructLabel = new JLabel(instructBack);
        JLayeredPane iPage= new JLayeredPane();
        iPage.setLayout(null);
        instructLabel.setSize(800,600);
        instructLabel.setLocation(-10,-15);
        iPage.add(instructLabel,1);

        //select level card
        ImageIcon selectBack = new ImageIcon(getClass().getResource("selectBack.jpg"));
        JLabel selectLabel = new JLabel((selectBack));
        JLayeredPane selPage = new JLayeredPane();
        selPage.setLayout(null);
        selectLabel.setSize(800,600);
        selectLabel.setLocation(-10,-15);
        selPage.add(selectLabel,1);

        //play button
        playBtn.setSize(100,30);
        playBtn.setLocation(350,400);
        mPage.add(playBtn,2);

        //instruction button
        inBtn.setSize(150,30);
        inBtn.setLocation(350,500);
        mPage.add(inBtn,2);

        //selection button
        lv1.setSize(50,50);
        lv1.setLocation(150,300);
        selPage.add(lv1,1);

        lv2.setSize(50,50);
        lv2.setLocation(225,300);
        selPage.add(lv2,1);

        lv3.setSize(50,50);
        lv3.setLocation(300,300);
        selPage.add(lv3,1);

        //the magic of adding cards
        cards = new JPanel(cLayout);
        cards.add(mPage, "menu");
        cards.add(selPage,"selection");
        cards.add(game, "game");
        cards.add(iPage,"instructions");
        add(cards);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setResizable(false);
        setVisible(true);
    }
    //starts the Timer
    public void start(){
        myTimer.start();
    }
    //finds the source of the actions and acts accordingly
    public void actionPerformed(ActionEvent evt) {
        Object source = evt.getSource();
        //start the game if play button is pressed
        if(source==playBtn){
            cLayout.show(cards,"selection");
            //cLayout.show(cards,"game");
            //myTimer.start();
            game.requestFocus();
        }
        if(source==inBtn){
            cLayout.show(cards,"instructions");
            game.requestFocus();
        }
        if(source==lv1){
            cLayout.show(cards,"game");
            myTimer.start();
            game.requestFocus();
        }

        if(source==lv2){
            if(hLevel>=2){
                cLayout.show(cards,"game");
                myTimer.start();
            }
            else{
                cLayout.show(cards,"selection");
            }
            game.requestFocus();

        }
        if(source==lv2){
            if(hLevel>=3){
                cLayout.show(cards,"game");
                myTimer.start();
            }
            else{
                cLayout.show(cards,"selection");
            }
            game.requestFocus();
        }
        //if the game is running, run game things
        if(source==myTimer){
            game.checkPlatforms();
            game.checkMoving();
            game.move();	//move the player
            game.updateSaws();
            game.updateLasers();
            game.checkReset();
            game.repaint();
        }
    }

    //start the game
    public static void main(String[] args){
        GrambityCat frame = new GrambityCat();
    }
}

//////////////////////////////////////
class GamePanel extends JPanel implements KeyListener{
    private Cat player; //player
    private GrambityCat mainFrame;
    //platforms
    private ArrayList<Platform> platforms;
    //keys
    private boolean[] keys,oldKeys;	//the state of the keyboard

    //images
    private Image back;	//pictuer of the background of the main page

    //saws
    private Saw s1;

    //lasers
    private Laser l1;

    //breaking plats
    private BreakingPlat bPlat1;
    //ints
    private int opx,opy; //coordinates of where the player started
    private int catV = 4;
    private int gravity = 5; //gravity value
    private int jCounter; //player can only hold jump for so long, jCounter keeps track of that

    //booleans
    private boolean canJump;

    //constructor
    public GamePanel(GrambityCat m,int level){
        keys = new boolean [KeyEvent.KEY_LAST+1]; //make the keyboard list as large as needed
        oldKeys = new boolean [KeyEvent.KEY_LAST+1];
        //images
        back = new ImageIcon(getClass().getResource("background.png")).getImage();	//the background of the game
      
        //cat images
        Image[] nCatsR = new Image[3];
        Image[] nCatsL = new Image[3];
        Image normalC1 = new ImageIcon(getClass().getResource("cat002.png")).getImage();
        Image normalC2 = new ImageIcon(getClass().getResource("cat003.png")).getImage();	//the player's character image
        Image normalC3 = new ImageIcon(getClass().getResource("cat001.png")).getImage();
        nCatsR[0] = normalC1;
        nCatsR[1] = normalC2;
        nCatsR[2] = normalC3;

        normalC1 = new ImageIcon(getClass().getResource("cat012.png")).getImage();
        normalC2 = new ImageIcon(getClass().getResource("cat013.png")).getImage();	//the player's character image
        normalC3 = new ImageIcon(getClass().getResource("cat011.png")).getImage();
        nCatsL[0] = normalC1;
        nCatsL[1] = normalC2;
        nCatsL[2] = normalC3;

        Image[] uCatsR = new Image[3];
        Image[] uCatsL = new Image[3];
        Image upsideDownC1 = new ImageIcon(getClass().getResource("cat002D.png")).getImage();
        Image upsideDownC2 = new ImageIcon(getClass().getResource("cat003D.png")).getImage();	//the player's character image
        Image upsideDownC3 = new ImageIcon(getClass().getResource("cat001D.png")).getImage();
        uCatsR[0] = upsideDownC1;
        uCatsR[1] = upsideDownC2;
        uCatsR[2] = upsideDownC3;

        upsideDownC1 = new ImageIcon(getClass().getResource("cat012D.png")).getImage();
        upsideDownC2 = new ImageIcon(getClass().getResource("cat013D.png")).getImage();	//the player's character image
        upsideDownC3 = new ImageIcon(getClass().getResource("cat011D.png")).getImage();
        uCatsL[0] = upsideDownC1;
        uCatsL[1] = upsideDownC2;
        uCatsL[2] = upsideDownC3;


      //player tings
        opx = 200;
        opy = 300;
        player = new Cat(opx,opy, nCatsR, nCatsL,uCatsR,uCatsL);

      //platform tings
        Image plat = new ImageIcon(getClass().getResource("Platform.png")).getImage();
        Image plat1 = new ImageIcon(getClass().getResource("Platform1.png")).getImage();
        platforms = new ArrayList<>();
        Platform platform = new Platform(5,450,100,20, plat);
        platforms.add(platform);
        Platform platform2 = new Platform(170,500,100,20, plat);
        platforms.add(platform2);
        Platform platform3 = new Platform(300,450,100,20, plat);
        platforms.add(platform3);
        Platform platform4 = new Platform(200,150,100,20, plat);
        platforms.add(platform4);
        Platform platform5 = new Platform(400,400,300,40,plat1);
        platforms.add(platform5);
        Platform platform6 = new Platform(400,0,300,40,plat1);
        platforms.add(platform6);
        //saw tings
        Image[] sawPics = new Image[2];
        Image saw1 = new ImageIcon(getClass().getResource("saw1.png")).getImage();
        sawPics[0] = saw1;
        Image saw2 = new ImageIcon(getClass().getResource("saw2.png")).getImage();
        sawPics[1] = saw2;
        s1 = new Saw(400,360,480,640,sawPics,"right");

        //laser tings
        l1 = new Laser(430,40,430,400,25,450,Color.PINK);

        //breaking plat tings
        Image[] bImages = new Image[3];
        Image b1 = new ImageIcon(getClass().getResource("Platform1.png")).getImage();
        bImages[0] = b1;
        Image b2 = new ImageIcon(getClass().getResource("breakingPlat1.png")).getImage();
        bImages[1] = b2;
        Image b3 = new ImageIcon(getClass().getResource("breakingPlat2.png")).getImage();
        bImages[2] = b3;

        bPlat1 = new BreakingPlat(600,500,300,40,bImages);

        setSize(800,800);
        addKeyListener(this);
        mainFrame = m;    //the main frame
    }

    //starts the game
    public void addNotify(){
        super.addNotify();
        setFocusable(true);
        requestFocus();
        mainFrame.start();
    }

    //move moves the player, makes sure the player doesn't move off screen
    public void move(){
        if(keys[KeyEvent.VK_RIGHT]){
            player.addX(catV);

        }
        if(keys[KeyEvent.VK_LEFT]){
            player.addX(-catV);
        }
        if(keys[KeyEvent.VK_UP]){
            //check normal platforms
            for(Platform platy:platforms){
                if(!oldKeys[KeyEvent.VK_UP]&&platy.onPlat(player)) {
                    jCounter = 10;
                    canJump = true;
                }
            }
            //check breaking plat
            if(!oldKeys[KeyEvent.VK_UP]&&bPlat1.onPlat(player)) {
                jCounter = 10;
                canJump = true;
            }

            if(jCounter>0 &&canJump){
                player.jump();
                player.setOnPlat(platforms.get(0), false);
                jCounter--;
            }
        }
        if(keys[KeyEvent.VK_SPACE]){
            //check normal platforms
            for(Platform platy:platforms){
                if(platy.onPlat(player)){
                    player.setOnPlat(platy,false);
                    if(gravity*-1<0){
                        player.setNormalGravity(false);
                    }
                    else{
                        player.setNormalGravity(true);
                    }
                    gravity = gravity*-1;
                }
            }
            //check breaking plats
            if(bPlat1.onPlat(player)){
                player.setOnPlat(bPlat1,false);
                if(gravity*-1<0){
                    player.setNormalGravity(false);
                }
                else{
                    player.setNormalGravity(true);
                }
                gravity = gravity*-1;
            }
        }
        if(!keys[KeyEvent.VK_UP]){
            canJump = false;
        }
    }

    public void checkPlatforms(){
        boolean onAPlat = false;
        for(Platform platy: platforms){
            if(platy.onPlat(player)){ //check if player is on a platform
                player.setJumping(false);
                player.setFalling(false);
                onAPlat = true;
                player.setOnPlat(platy,true);
            }
        }

        if(bPlat1.onPlat(player)){
            player.setJumping(false);
            player.setFalling(false);
            onAPlat = true;
            player.setOnPlat(bPlat1,true);
        }

        if(!onAPlat){
            player.setFalling(true);
        }

        boolean canRight = true;
        for(Platform platy:platforms){
            if(platy.leftCollidePlat(player)){
                player.setCanGoRight(false);
                canRight = false;
                player.setSideRect(platy,"left");
            }
        }

        if(bPlat1.leftCollidePlat(player)){
            player.setCanGoRight(false);
            canRight = false;
            player.setSideRect(bPlat1,"left");
        }

        if(canRight){
            player.setCanGoRight(true);
        }

        boolean canLeft = true;
        for(Platform platy:platforms){
            if(platy.rightCollidePlat(player)){
                player.setCanGoLeft(false);
                canLeft = false;
                player.setSideRect(platy,"right");
            }
        }
        if(bPlat1.rightCollidePlat(player)){
            player.setCanGoLeft(false);
            canLeft = false;
            player.setSideRect(bPlat1,"right");
        }
        if(canLeft){
            player.setCanGoLeft(true);
        }
        //checks if the platform is broken
        bPlat1.checkBreakingPoint();
    }

    public void checkMoving(){
        if(player.isFalling()){
            player.addY(gravity); //gravity
        }
        if(player.isJumping()){
            player.checkJumpV(); //do jumping things
        }
        if(keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_LEFT]){
            player.setStanding(false);
        }
        else{
            player.setStanding(true);
        }
    }

    public void updateSaws(){
        s1.checkCat(player);
        s1.move();
    }

    public void updateLasers(){
        l1.checkSwitch(player);
        l1.checkCat(player);
    }

    public void checkReset(){
        if(player.isDead()){
            player.setCoords(opx,opy);
            player.setDead(false);
            gravity = 3;
            player.setNormalGravity(true);
        }
    }

    //methods from implementing KeyListener
    public void keyTyped(KeyEvent e) {}

    //if a key is pressed, its position in keys is true
    public void keyPressed(KeyEvent e) {
        oldKeys [e.getKeyCode()] = keys[e.getKeyCode()];
        keys[e.getKeyCode()] = true;
    }
    //if a key is not pressed, its position in keys is false
    public void keyReleased(KeyEvent e) {
        oldKeys [e.getKeyCode()] = keys[e.getKeyCode()];
        keys[e.getKeyCode()] = false;
    }

    //paintComponent draws all the pictures onto the screen
    public void paintComponent(Graphics g){
        g.drawImage(back,0,0,this);  //draw background
        player.draw(g,this);

        for(Platform platy:platforms){
            platy.draw(g,this);
        }
        s1.draw(g,this);
        l1.draw(g,this);
        bPlat1.draw(g,this);

    }
}