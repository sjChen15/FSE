//GrambityCat.java
//Jenny Chen
package com.company;

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

    private String map;

    //the game constructor
    public GrambityCat(){
        super("Grambity Cat");
        setSize(1330,630);   //screen size
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

        //TODO: make buttons invisible after new buttons are put in
        //play button
        playBtn.setSize(160,66);
        playBtn.setLocation(619,457);
        hideButton(playBtn); //makes button invisible
        mPage.add(playBtn,JLayeredPane.DRAG_LAYER);

        //instruction button
        inBtn.setSize(283,66);
        inBtn.setLocation(557,530);
        hideButton(inBtn); //makes button invisible
        mPage.add(inBtn,JLayeredPane.DRAG_LAYER);

        //back buttons
        iBackBtn.setSize(155,63);
        iBackBtn.setLocation(18,17);
        hideButton(iBackBtn); //makes button invisible
        iPage.add(iBackBtn,JLayeredPane.DRAG_LAYER);

        sBackBtn.setSize(155,63);
        sBackBtn.setLocation(15,20);
        hideButton(sBackBtn); //makes button invisible
        selPage.add(sBackBtn,JLayeredPane.DRAG_LAYER);

        //selection buttons
        lv1.setSize(70,80);
        lv1.setLocation(185,260);
        hideButton(lv1); //makes button invisible
        selPage.add(lv1,JLayeredPane.DRAG_LAYER);

        lv2.setSize(70,80);
        lv2.setLocation(398,260);
        hideButton(lv2); //makes button invisible
        selPage.add(lv2,JLayeredPane.DRAG_LAYER);

        lv3.setSize(70,80);
        lv3.setLocation(623,260);
        hideButton(lv3); //makes button invisible
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
            System.out.println("set level");
            game.setlevel(1);
            cLayout.show(cards,"game");
            myTimer.start();
            game.requestFocus();
        }

        if(source==lv2){
            game.setlevel(2);
            level = 2;
            if(hLevel>=2){

            }

            else{
                //cLayout.show(cards,"selection");
            }
            cLayout.show(cards,"game");
            myTimer.start();
            game.requestFocus();

        }
        if(source==lv3){
            game.setlevel(3);
            level = 3;
            if(hLevel>=3){

            }
            else{
                //cLayout.show(cards,"selection");
            }
            cLayout.show(cards,"game");
            myTimer.start();
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
            if(game.CheckDoor()){
                if(game.getlevel()<=3){
                    System.out.println("hi");
                    game.setlevel(game.getlevel()+1);
                }
            }
            game.repaint(); //draw everything

        }
    }
    public void addlevel(){
        level++;
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
    private ArrayList<Platform> platforms = new ArrayList<>();

    //keys
    private boolean[] keys,oldKeys;	//the state of the keyboard, oldkeys was the keyboard right before the current keyboard

    //images
    private Image back;	//picture of the background of the main page

    //saws
    private ArrayList<Saw> saws = new ArrayList<>();

    //lasers
    private ArrayList<Laser> lasers = new ArrayList<>();

    //breaking plats
    private ArrayList<BreakingPlat> BPlatforms = new ArrayList<>();

    //speed up power ups
    private ArrayList<PowerUp> PUps = new ArrayList<>();


    //ints
    private int opx,opy; //coordinates of where the player started
    private int gravity = 3; //gravity value
    private int jCounter; //player can only hold jump for so long, jCounter keeps track of that

    private ArrayList<Door> doors = new ArrayList<>();

    private int levels = 1;

    private Image[] nCatsR;
    private Image[] nCatsL;
    private Image[] uCatsR;
    private Image[] uCatsL;


    //booleans
    private boolean canJump; //makes sure the player can jump, ensures there is no double jumping
    //constructor
    public GamePanel(GrambityCat m,int level){
        keys = new boolean [KeyEvent.KEY_LAST+1]; //make the keyboard list as large as needed
        oldKeys = new boolean [KeyEvent.KEY_LAST+1];

        //images
        back = new ImageIcon(getClass().getResource("BG.png")).getImage();	//the background of the game


      
        //cat images
        nCatsR = new Image[3];
        nCatsL = new Image[3];
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

        uCatsR = new Image[3];
        uCatsL = new Image[3];
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
        opx = 70;
        opy = 490;
        player = new Cat(opx,opy, nCatsR, nCatsL,uCatsR,uCatsL);

        //MakeMap(saws, platforms,BPlatforms,lasers,PUps,LevelChoice(levels),doors);

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
    public int getlevel(){
        return levels;
    }
    public void setlevel(int lvl){
        levels = lvl;
        MakeMap(saws, platforms,BPlatforms,lasers,PUps,LevelChoice(levels),doors);
    }
    public String LevelChoice(int lvl){
        if (lvl == 1){
            return "Map1.txt";
        }
        else if (lvl == 2){
            return "Map2.txt";
        }
        else{
            System.out.println("hiiiii");
            System.out.println(lvl);
            return "Map3.txt";
        }
    }
    public boolean CheckDoor(){
        System.out.println(saws.size());
        if(doors.size()>0){
            if (doors.get(0).nextLevel()) {
                System.out.println("refresh");
                saws.removeAll(saws);
                platforms.removeAll(platforms);
                BPlatforms.removeAll(BPlatforms);
                lasers.removeAll(lasers);
                PUps.removeAll(PUps);
                doors.removeAll(doors);
                player = new Cat(opx,opy, nCatsR, nCatsL,uCatsR,uCatsL);
                return true;
            }
        }
        else{
        }
        return false;
    }
    public void MakeMap(ArrayList<Saw> saws, ArrayList<Platform> platforms, ArrayList<BreakingPlat> BPlatforms, ArrayList<Laser> lasers, ArrayList<PowerUp> PUps, String map, ArrayList<Door> doory){
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
        //saw tings
        Image[] sawPics = new Image[2];
        Image saw1 = new ImageIcon(getClass().getResource("Saw1.0.png")).getImage();
        sawPics[0] = saw1;
        Image saw2 = new ImageIcon(getClass().getResource("Saw2.0.png")).getImage();
        sawPics[1] = saw2;

        Image DoorImage = new ImageIcon(getClass().getResource("Door.png")).getImage();
        try{
            Scanner inFile = new Scanner ( new File(getClass().getResource(map).getFile()));
            while(inFile.hasNextLine()){
                String n = inFile.nextLine();
                String[] stuff = n.split(",");
                if (stuff.length == 3){
                    if (stuff[2].contains("GTile"))
                        platforms.add(new Platform(Integer.parseInt(stuff[0])*35,Integer.parseInt(stuff[1])*35,35,35,new ImageIcon(getClass().getResource(stuff[2])).getImage(),player));
                    else if (stuff[2].contains("Door")){
                        System.out.println("hi");
                        doory.add( new Door(Integer.parseInt(stuff[0])*35,Integer.parseInt(stuff[1])*35,DoorImage, player));
                    }
                    else{
                        BPlatforms.add(new BreakingPlat(Integer.parseInt(stuff[0])*35,Integer.parseInt(stuff[1])*35,35,35,BPlats.get(Integer.parseInt(stuff[2])),player));
                    }
                }
                else if (stuff.length == 5){
                    saws.add(new Saw(Integer.parseInt(stuff[0])*35,Integer.parseInt(stuff[1])*35,Integer.parseInt(stuff[2])*35,Integer.parseInt(stuff[3])*35,sawPics,stuff[4], player));
                }
                else if (stuff.length == 9){
                    lasers.add(new Laser(Integer.parseInt(stuff[0])*35,Integer.parseInt(stuff[1])*35,Integer.parseInt(stuff[2])*35,Integer.parseInt(stuff[3])*35,Integer.parseInt(stuff[4])*35,Integer.parseInt(stuff[5])*35,new Color(Integer.parseInt(stuff[5]),Integer.parseInt(stuff[6]),Integer.parseInt(stuff[7])) , player));
                }
                else if (stuff.length == 2){
                    PUps.add(new PowerUp(Integer.parseInt(stuff[0])*35,Integer.parseInt(stuff[1])*35, player));
                }
            }
        }
        catch(IOException ex){
            System.out.println("Dude, did you misplace Map1.txt?");
        }
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
        if(keys[KeyEvent.VK_SPACE] && !oldKeys[KeyEvent.VK_SPACE]){ //make sure the player can't hold space and continuously change gravity as soon as they hit a new platform
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
        for(Door door : doors) {
            door.draw(g, this); //draw the breakingPlat
        }
        player.draw(g,this); //draw the player
    }
}