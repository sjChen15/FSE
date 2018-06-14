package com.company;

//GrambityCat.java
//Jenny Chen
//package com.company;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import static java.awt.Color.PINK;

public class GrambityCat extends JFrame implements ActionListener{
    private javax.swing.Timer myTimer;	//the Timer
    private JPanel cards;	//the cards
    private CardLayout cLayout = new CardLayout();	//the layout

    //buttons
    private JButton playBtn = new JButton("Play");	//the button that starts the game
    private JButton inBtn  = new JButton("Instructions");
    private JButton backBtn = new JButton("Back");

    //the 3 buttons to choose the levels
    private JButton lv1 = new JButton("1");
    private JButton lv2 = new JButton("2");
    private JButton lv3 = new JButton("3");

    private GamePanel game;	//the game panel

    private int level = 1; //level that player chose
    private int hLevel = 1; //the highest level the player can play

    //the game constructor
    public GrambityCat(){
        super("Grambity Cat");
        setSize(1330,630);   //screen size
        game = new GamePanel(this,level);
        add(game);

        //buttons action listening
        playBtn.addActionListener(this);
        inBtn.addActionListener(this);
        backBtn.addActionListener(this);
        lv1.addActionListener(this);
        lv2.addActionListener(this);
        lv3.addActionListener(this);

        //timer
        myTimer = new javax.swing.Timer(5, this);

        //mainpage card
        ImageIcon mainBack = new ImageIcon(getClass().getResource("BACK.png"));
        JLabel backLabel = new JLabel(mainBack);
        JLayeredPane mPage = new JLayeredPane();
        mPage.setLayout(null);
        backLabel.setSize(1330,630);
        backLabel.setLocation(-10,-15);
        mPage.add(backLabel,1);

        //instruction card
        ImageIcon instructBack = new ImageIcon(getClass().getResource("instructions.png")); //page picture
        JLabel instructLabel = new JLabel(instructBack);
        JLayeredPane iPage= new JLayeredPane();
        iPage.setLayout(null);
        instructLabel.setSize(1330,630);
        instructLabel.setLocation(-10,-15);
        iPage.add(instructLabel,1);

        //select level card
        ImageIcon selectBack = new ImageIcon(getClass().getResource("lvlSelect.png"));
        JLabel selectLabel = new JLabel((selectBack));
        JLayeredPane selPage = new JLayeredPane();
        selPage.setLayout(null);
        selectLabel.setSize(1330,630);
        selectLabel.setLocation(-10,-15);
        selPage.add(selectLabel,1);

        //play button
        playBtn.setSize(100,30);
        playBtn.setLocation(350,400);
        mPage.add(playBtn,2);

        //instruction button
        inBtn.setSize(150,30);
        inBtn.setLocation(350,500);
        mPage.add(inBtn,1);

        //back button
        backBtn.setSize(150,30);
        backBtn.setLocation(10,10);
        iPage.add(backBtn,1);
        backBtn.setSize(150,30);
        backBtn.setLocation(10,10);
        selPage.add(backBtn,1);

        //selection buttons
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
        //show selection screen
        if(source==playBtn){
            cLayout.show(cards,"selection");
            game.requestFocus();
        }
        //show instruction page
        if(source==inBtn){
            cLayout.show(cards,"instructions");
            game.requestFocus();
        }
        if(source == backBtn){
            cLayout.show(cards,"menu");
            game.requestFocus();
        }
        //this is where we can choose different levels
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
            game.checkPlatforms(); //check player colliding with platforms
            game.checkMoving(); //check things to do with moving, like jumping
            game.move();	//move the player
            game.updateSaws();  //move the saws
            game.updateLasers();    //check colliding of lasers
            game.checkPowerUp();    //check colliding of the powerups
            game.checkReset();  //check if player died and game needs to be reset
            game.repaint(); //draw everything
        }
    }

    //start the game
    public static void main(String[] args){
        //new Sound().setVisible(true);
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
    private boolean[] keys,oldKeys;	//the state of the keyboard, oldkeys was the keyboard right before the current keyboard

    //images
    private Image back;	//picture of the background of the main page

    //saws
    private Saw s1;
    private ArrayList<Saw> saws = new ArrayList<>();

    //lasers
    private Laser l1;
    private ArrayList<Laser> lasers = new ArrayList<>();

    //breaking plats
    //private BreakingPlat bPlat1;
    private ArrayList<BreakingPlat> BPlatforms = new ArrayList<>();

    //speed up power ups
    private PowerUp speed1;
    private ArrayList<PowerUp> PUps = new ArrayList<>();

    //ints
    private int opx,opy; //coordinates of where the player started
    private int gravity = 3; //gravity value
    private int jCounter; //player can only hold jump for so long, jCounter keeps track of that

    //booleans
    private boolean canJump; //makes sure the player can jump, ensures there is no double jumping
    //constructor
    public GamePanel(GrambityCat m,int level){
        keys = new boolean [KeyEvent.KEY_LAST+1]; //make the keyboard list as large as needed
        oldKeys = new boolean [KeyEvent.KEY_LAST+1];
        //images
        back = new ImageIcon(getClass().getResource("BG.png")).getImage();	//the background of the game
      
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

        //map things
        /*
        Image[] GTiles = new Image[3];
        GTiles[0] = new ImageIcon(getClass().getResource("GTile1.png")).getImage();
        GTiles[1] = new ImageIcon(getClass().getResource("GTile2.png")).getImage();
        GTiles[2] = new ImageIcon(getClass().getResource("GTile3.png")).getImage();
        */

        ArrayList<String[]> map1 = new ArrayList<String[]>();

      //player tings
        opx = 70;
        opy = 490;
        player = new Cat(opx,opy, nCatsR, nCatsL,uCatsR,uCatsL);

      //platform tings
        Image plat = new ImageIcon(getClass().getResource("Platform.png")).getImage();
        Image plat1 = new ImageIcon(getClass().getResource("Platform1.png")).getImage();

        platforms = new ArrayList<>();
        /*
        Platform platform = new Platform(5,450,100,20, plat,player);
        platforms.add(platform);
        Platform platform2 = new Platform(170,500,100,20, plat,player);
        platforms.add(platform2);
        Platform platform3 = new Platform(300,450,100,20, plat,player);
        platforms.add(platform3);
        Platform platform4 = new Platform(200,150,100,20, plat,player);
        platforms.add(platform4);
        Platform platform5 = new Platform(400,400,300,40,plat1,player);
        platforms.add(platform5);
        Platform platform6 = new Platform(400,0,300,40,plat1,player);
        platforms.add(platform6);
        Platform platform7 = new Platform(5,300,100,20, plat,player);
        platforms.add(platform7);
        */

        //saw tings
        Image[] sawPics = new Image[2];
        Image saw1 = new ImageIcon(getClass().getResource("Saw1.0.png")).getImage();
        sawPics[0] = saw1;
        Image saw2 = new ImageIcon(getClass().getResource("Saw2.0.png")).getImage();
        sawPics[1] = saw2;
        //s1 = new Saw(400,360,480,640,sawPics,"right",player);

        //laser tings
        //l1 = new Laser(430,40,430,400,25,450, PINK,player);

        //breaking plat tings
        ArrayList<Image[]> BPlats = new ArrayList<>();
        Image[] bImages1 = new Image[3];
        bImages1[0] = new ImageIcon(getClass().getResource("GTile1.png")).getImage();
        bImages1[1] = new ImageIcon(getClass().getResource("Breaking1.png")).getImage();
        bImages1[2] = new ImageIcon(getClass().getResource("BreakingLast1.png")).getImage();
        BPlats.add(bImages1);

        Image[] bImages2 = new Image[3];
        bImages2[0] = new ImageIcon(getClass().getResource("GTile2.png")).getImage();
        bImages2[1] = new ImageIcon(getClass().getResource("Breaking2.png")).getImage();
        bImages2[2] = new ImageIcon(getClass().getResource("BreakingLast2.png")).getImage();
        BPlats.add(bImages2);

        Image[] bImages3 = new Image[3];
        bImages3[0] = new ImageIcon(getClass().getResource("GTile3.png")).getImage();
        bImages3[1] = new ImageIcon(getClass().getResource("Breaking3.png")).getImage();
        bImages3[2] = new ImageIcon(getClass().getResource("BreakingLast2.png")).getImage();
        BPlats.add(bImages3);


        //mapcode

        try{

            Scanner inFile = new Scanner (new File("Map3.txt"));
            while(inFile.hasNextLine()){
                String n = inFile.nextLine();
                String[] stuff = n.split(",");
                if (stuff.length == 3){
                    if (stuff[2].contains("GTile"))
                        platforms.add(new Platform(Integer.parseInt(stuff[0])*35,Integer.parseInt(stuff[1])*35,35,35,new ImageIcon(getClass().getResource(stuff[2])).getImage(),player));
                    else{
                        BPlatforms.add(new BreakingPlat(Integer.parseInt(stuff[0])*35,Integer.parseInt(stuff[1])*35,35,35,BPlats.get(Integer.parseInt(stuff[2])),player));
                    }
                }
                else if (stuff.length == 5){
                    saws.add(new Saw(Integer.parseInt(stuff[0])*35,Integer.parseInt(stuff[1])*35,Integer.parseInt(stuff[2])*35,Integer.parseInt(stuff[3])*35,sawPics,stuff[4], player));
                }
                else if (stuff.length == 6){
                    lasers.add(new Laser(Integer.parseInt(stuff[0])*35,Integer.parseInt(stuff[1])*35,Integer.parseInt(stuff[2])*35,Integer.parseInt(stuff[3])*35,Integer.parseInt(stuff[4])*35,Integer.parseInt(stuff[5])*35,PINK , player));
                }
                else if (stuff.length == 2){
                    PUps.add(new PowerUp(Integer.parseInt(stuff[0])*35,Integer.parseInt(stuff[1])*35, player));
                }



            }
        }
        catch(IOException ex){
            System.out.println("Dude, did you misplace Map1.txt?");
        }

        //PowerUp tings
        //speed1 = new PowerUp(375,440,player);

        //frame stuffs
        setSize(1330,630);
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

    //moves the player
    public void move(){
        //if left or right are pressed the player is moved in that direction
        if(keys[KeyEvent.VK_RIGHT]){
            player.addX(player.getCatV());
        }
        if(keys[KeyEvent.VK_LEFT]){
            player.addX(-player.getCatV());
        }
        //if up is pressed, the player jumps
        if(keys[KeyEvent.VK_UP]){
            //the player can only change jump if they are on a platform

            //check if player is on a normal platform and is not jumping
            for(Platform platy:platforms){
                if(!oldKeys[KeyEvent.VK_UP]&&platy.onPlat()) {
                    jCounter = 10; //how long the player will receive an upward velocity
                    canJump = true;
                }
            }
            //check the same things with the breaking platform objects
            for (BreakingPlat bplat:BPlatforms){
                if(!oldKeys[KeyEvent.VK_UP]&&bplat.onPlat()) {
                    jCounter = 10; //how long the player will receive an upward velocity
                    canJump = true;
                }
            }

            //if the player still gets jump velocity, add it on
            if(jCounter>0 && canJump){
                player.jump();
                player.setOnPlat(platforms.get(0),false); //set it off a platform
                jCounter--;
            }

        }
        //if the player is no longer holding up they can't have more upwards velocity
        if(!keys[KeyEvent.VK_UP]){
            canJump = false;
        }
        //if space is pressed, the gravity is changed
        if(keys[KeyEvent.VK_SPACE]){
            //check normal platforms
            for(Platform platy:platforms){
                if(platy.onPlat()){
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
            for (BreakingPlat bplat: BPlatforms){
                if(bplat.onPlat()){
                    player.setOnPlat(bplat,false);
                    if(gravity*-1<0){
                        player.setNormalGravity(false);
                    }
                    else{
                        player.setNormalGravity(true);
                    }
                    gravity = gravity*-1;
                }
            }
        }
    }

    public void checkPlatforms(){

        //check if player is on a platform
        boolean onAPlat = false;
        for(Platform platy: platforms){
            if(platy.onPlat()){ //check if player is on a platform
                player.setJumping(false);
                player.setFalling(false);
                onAPlat = true;
                player.setOnPlat(platy,true);
            }
        }

        for(BreakingPlat bplat: BPlatforms){
            if(bplat.onPlat()){
                player.setJumping(false);
                player.setFalling(false);
                onAPlat = true;
                player.setOnPlat(bplat,true);
            }
        }

        if(!onAPlat){
            player.setFalling(true);
        }
        //check if player is colliding the bottom of a rectangle
        boolean underAPlat = false;
        for(Platform platy: platforms){
            if(platy.bottomCollidePlat()){ //check if player is on a platform
                player.setCanGoUp(false);
                underAPlat = true;
                player.setUnderPlat(platy);
            }
        }
        for(BreakingPlat bplat: BPlatforms){
            if(bplat.bottomCollidePlat()){
                System.out.println("heyo");
                player.setCanGoUp(false);
                underAPlat = true;
                player.setUnderPlat(bplat);
            }
        }

        if(!underAPlat){
            player.setCanGoUp(true);
        }

        //check if a player is colliding on the left side of a platform
        boolean canRight = true;
        for(Platform platy:platforms){
            if(platy.leftCollidePlat()){
                player.setCanGoRight(false);
                canRight = false;
                player.setSideRect(platy,"left");
            }
        }
        for(BreakingPlat bplat : BPlatforms){
            if(bplat.leftCollidePlat()){
                player.setCanGoRight(false);
                canRight = false;
                player.setSideRect(bplat,"left");
            }
        }

        if(canRight){
            player.setCanGoRight(true);
        }

        //check if a player is colliding on the right side of a platform
        boolean canLeft = true;
        for(Platform platy:platforms){
            if(platy.rightCollidePlat()){
                player.setCanGoLeft(false);
                canLeft = false;
                player.setSideRect(platy,"right");
            }
        }
        for(BreakingPlat bplat: BPlatforms) {
            if (bplat.rightCollidePlat()) {
                player.setCanGoLeft(false);
                canLeft = false;
                player.setSideRect(bplat, "right");
            }
        }
        if(canLeft){
            player.setCanGoLeft(true);
        }


        //checks if the platform is broken
        for(BreakingPlat bplat: BPlatforms) {
            bplat.checkBreakingPoint();
        }
    }

    //check various aspects of the cat movin
    public void checkMoving(){
        if(player.isFalling()){ //add gravity if the cat is falling
            player.addY(gravity);
        }
        if(player.isJumping()){ //do jumping things
            player.checkJumpV();
        }
        if(keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_LEFT]){ //check if the cat is standing still or not
            player.setStanding(false);
        }
        else{
            player.setStanding(true);
        }
    }

    //move the saw and check if the cat has collided with it
    public void updateSaws(){
        for(Saw saw: saws){
            saw.checkCat();
            saw.move();
        }
    }

    //checks if the cat has collided with the laser or the switch
    public void updateLasers(){
        for (Laser laser: lasers) {
            laser.checkSwitch();
            laser.checkCat();
        }
    }

    //checks if the powerup has been picked up
    public void checkPowerUp(){
        for(PowerUp pup: PUps){
            pup.checkPowerUp();
            player.checkSpeedUp();
        }
    }

    //checks if the player is dead
    public void checkReset(){
        if(player.isDead()){ //if so, the level is reset
            gravity  = 3;
            player.reset();
            for(BreakingPlat bplat : BPlatforms) {
                bplat.reset();
            }
            for(Laser laser: lasers){
                laser.reset();
            }
            for(PowerUp pup: PUps){
                pup.reset();
            }
            for(Saw saws: saws){
                saws.reset();
            }
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
        for (Saw saw: saws){
            saw.draw(g,this);
        }
        for(Platform platy:platforms){ //draw the platforms
            platy.draw(g,this);
        }
        for (Laser laser: lasers){
            laser.draw(g,this);
        }
        for(PowerUp pup: PUps){
            pup.draw(g,this);
        }
        for(BreakingPlat bplat : BPlatforms) {
            bplat.draw(g, this); //draw the breakingPlat
        }
        player.draw(g,this); //draw the player
    }
}