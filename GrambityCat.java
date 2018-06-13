//GrambityCat.java
//Jenny Chen
//package com.company;

import sun.audio.*;
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GrambityCat extends JFrame implements ActionListener{
    private javax.swing.Timer myTimer;	//the Timer
    private JPanel cards;	//the cards
    private CardLayout cLayout = new CardLayout();	//the layout

    //buttons
    private JButton playBtn = new JButton("Play");	//the button that starts the game
    private JButton inBtn  = new JButton("Instructions");
    private JButton iBackBtn = new JButton("Back"); //back button for the instruction page
    private JButton sBackBtn = new JButton("Back"); //back button for the selection page

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
        setSize(800,600);   //screen size
        game = new GamePanel(this,level);
        add(game);

        //buttons action listening
        playBtn.addActionListener(this);
        inBtn.addActionListener(this);
        iBackBtn.addActionListener(this);
        sBackBtn.addActionListener(this);
        lv1.addActionListener(this);
        lv2.addActionListener(this);
        lv3.addActionListener(this);

        //timer
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

        //TODO: make buttons invisible after new buttons are put in
        //play button
        playBtn.setSize(100,30);
        playBtn.setLocation(350,400);
        //hideButton(playBtn); //makes button invisible
        mPage.add(playBtn,JLayeredPane.DRAG_LAYER);

        //instruction button
        inBtn.setSize(150,30);
        inBtn.setLocation(350,500);
        //hideButton(inBtn); //makes button invisible
        mPage.add(inBtn,JLayeredPane.DRAG_LAYER);

        //back button
        iBackBtn.setSize(150,30);
        iBackBtn.setLocation(5,5);
        //hideButton(iBackBtn); //makes button invisible
        iPage.add(iBackBtn,JLayeredPane.DRAG_LAYER);

        sBackBtn.setSize(150,30);
        sBackBtn.setLocation(5,5);
        //hideButton(sBackBtn); //makes button invisible
        selPage.add(sBackBtn,JLayeredPane.DRAG_LAYER);

        //selection buttons
        lv1.setSize(50,50);
        lv1.setLocation(150,300);
        //hideButton(lv1); //makes button invisible
        selPage.add(lv1,JLayeredPane.DRAG_LAYER);

        lv2.setSize(50,50);
        lv2.setLocation(225,300);
        //hideButton(lv2); //makes button invisible
        selPage.add(lv2,JLayeredPane.DRAG_LAYER);

        lv3.setSize(50,50);
        lv3.setLocation(300,300);
        //hideButton(lv3); //makes button invisible
        selPage.add(lv3,JLayeredPane.DRAG_LAYER);

        //the magic of adding cards
        cards = new JPanel(cLayout);
        cards.add(mPage, "menu");
        cards.add(selPage,"selection");
        cards.add(game, "game");
        cards.add(iPage,"instructions");
        add(cards);

        music();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setResizable(false);
        setVisible(true);
    }

    //makes the button invisible
    public void hideButton(JButton button){
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setText("");
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
        if(source == sBackBtn || source == iBackBtn){
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
    //from https://stackoverflow.com/questions/20811728/adding-music-sound-to-java-programs
    public void music()
    {
        AudioPlayer MGP = AudioPlayer.player;
        AudioStream BGM;
        AudioData MD;

        ContinuousAudioDataStream loop = null;

        try
        {
            InputStream test = new FileInputStream(getClass().getResource("music.wav").getFile());
            BGM = new AudioStream(test);
            AudioPlayer.player.start(BGM);
            MD = BGM.getData();
            loop = new ContinuousAudioDataStream(MD);

        }
        catch(FileNotFoundException e){
            System.out.print(e.toString());
        }
        catch(IOException error)
        {
            System.out.print(error.toString());
        }
        MGP.start(loop);
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
    private boolean[] keys,oldKeys;	//the state of the keyboard, oldkeys was the keyboard right before the current keyboard

    //images
    private Image back;	//picture of the background of the main page

    //saws
    private Saw s1;

    //lasers
    private Laser l1;

    //breaking plats
    private BreakingPlat bPlat1;

    //speed up power ups
    private PowerUp speed1;

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

        //saw tings
        Image[] sawPics = new Image[2];
        Image saw1 = new ImageIcon(getClass().getResource("saw1.png")).getImage();
        sawPics[0] = saw1;
        Image saw2 = new ImageIcon(getClass().getResource("saw2.png")).getImage();
        sawPics[1] = saw2;
        s1 = new Saw(400,360,480,640,sawPics,"right",player);

        //laser tings
        l1 = new Laser(430,40,430,400,25,450,Color.PINK,player);

        //breaking plat tings
        Image[] bImages = new Image[3];
        Image b1 = new ImageIcon(getClass().getResource("Platform1.png")).getImage();
        bImages[0] = b1;
        Image b2 = new ImageIcon(getClass().getResource("breakingPlat1.png")).getImage();
        bImages[1] = b2;
        Image b3 = new ImageIcon(getClass().getResource("breakingPlat2.png")).getImage();
        bImages[2] = b3;

        bPlat1 = new BreakingPlat(500,500,300,40,bImages,player);

        //PowerUp tings
        speed1 = new PowerUp(375,440,player);
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
            if(!oldKeys[KeyEvent.VK_UP]&&bPlat1.onPlat()) {
                jCounter = 10; //how long the player will receive an upward velocity
                canJump = true;
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
        if(keys[KeyEvent.VK_SPACE]&&!oldKeys[KeyEvent.VK_SPACE]){ //make sure the player can't hold space and continuously change gravity as soon as they hit a new platform
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
            if(bPlat1.onPlat()){
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
    }

    //check if player is on a platform
    public void checkPlatforms(){
        boolean onAPlat = false;
        //check if player is on a platform
        for(Platform platy: platforms){
            if(platy.onPlat()){
                player.setJumping(false);
                player.setFalling(false);
                onAPlat = true;
                player.setOnPlat(platy,true);
            }
        }

        if(bPlat1.onPlat()){
            player.setJumping(false);
            player.setFalling(false);
            onAPlat = true;
            player.setOnPlat(bPlat1,true);
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

        if(bPlat1.bottomCollidePlat()){
            player.setCanGoUp(false);
            underAPlat = true;
            player.setUnderPlat(bPlat1);
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

        if(bPlat1.leftCollidePlat()){
            player.setCanGoRight(false);
            canRight = false;
            player.setSideRect(bPlat1,"left");
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
        if(bPlat1.rightCollidePlat()){
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
        s1.checkCat();
        s1.move();
    }

    //checks if the cat has collided with the laser or the switch
    public void updateLasers(){
        l1.checkSwitch();
        l1.checkCat();
    }

    //checks if the powerup has been picked up
    public void checkPowerUp(){
        speed1.checkPowerUp();
        player.checkSpeedUp();
    }

    //checks if the player is dead
    public void checkReset(){
        if(player.isDead()){ //if so, the level is reset
            gravity  = 3;
            player.reset();
            bPlat1.reset();
            l1.reset();
            speed1.reset();
            s1.reset();
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
        s1.draw(g,this);//draw the saw
        for(Platform platy:platforms){ //draw the platforms
            platy.draw(g,this);
        }

        l1.draw(g,this); //draw the laser
        bPlat1.draw(g,this); //draw the breakingPlat
        speed1.draw(g,this); //draw the PowerUp
        player.draw(g,this); //draw the player
    }
}