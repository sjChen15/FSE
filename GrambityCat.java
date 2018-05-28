//GrambityCat.java
//Jenny Chen

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/*
things to do
- side collision
- make razer classes
- make laser classes

small things to do
- fix walking while flying thing
- fix upsidedown animation

 */

public class GrambityCat extends JFrame implements ActionListener{
    private javax.swing.Timer myTimer;	//the Timer
    private JPanel cards;	//the cards
    private CardLayout cLayout = new CardLayout();	//the layout
    private JButton playBtn = new JButton("Play");	//the button that starts the game
    private GamePanel game;	//the game panel

    //the game constructor
    public GrambityCat(){
        super("Grambity Cat");
        setSize(800,600);
        game = new GamePanel(this);
        add(game);

        playBtn.addActionListener(this);
        myTimer = new javax.swing.Timer(10, this);

        //mainpage card
        ImageIcon mainBack = new ImageIcon(getClass().getResource("mainback.jpg"));
        JLabel backLabel = new JLabel(mainBack);
        JLayeredPane mPage = new JLayeredPane();
        mPage.setLayout(null);
        backLabel.setSize(400,400);
        backLabel.setLocation(0,0);
        mPage.add(backLabel,1);

        //play button
        playBtn.setSize(100,30);
        playBtn.setLocation(350,400);
        mPage.add(playBtn,2);

        //the magic of adding cards
        cards = new JPanel(cLayout);
        cards.add(mPage, "menu");
        cards.add(game, "game");
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
            cLayout.show(cards,"game");
            myTimer.start();
            game.requestFocus();
        }
        //if the game is running, run game things
        if(source==myTimer){
            game.checkPlayer();
            game.move();	//move the player
            game.updateSaws();
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
    private Image back,normalC1,normalC2,upsideDC,plat;	//pictuer of the background of the main page

    //saws
    private Saw s1;
    //ints
    private int opx,opy; //coordinates of where the player started
    private int gravity = 5; //gravity value
    private int jCounter; //player can only hold jump for so long, jCounter keeps track of that

    //booleans
    private boolean canJump;
    private ArrayList<Image> nCatsR = new ArrayList<Image>();
    private ArrayList<Image> nCatsL = new ArrayList<Image>();

    //constructor
    public GamePanel(GrambityCat m){
        keys = new boolean [KeyEvent.KEY_LAST+1]; //make the keyboard list as large as needed
        oldKeys = new boolean [KeyEvent.KEY_LAST+1];
        //images
        back = new ImageIcon(getClass().getResource("background.jpg")).getImage();	//the background of the game
        upsideDC = new ImageIcon(getClass().getResource("cat002D.png")).getImage();
        plat  = new ImageIcon(getClass().getResource("Platform.png")).getImage();
      
        //cat images
        normalC1 = new ImageIcon(getClass().getResource("cat002.png")).getImage();
        normalC2 = new ImageIcon(getClass().getResource("cat003.png")).getImage();	//the player's character image
        nCatsR.add(normalC1);
        nCatsR.add(normalC2);
        normalC1 = new ImageIcon(getClass().getResource("cat012.png")).getImage();
        normalC2 = new ImageIcon(getClass().getResource("cat013.png")).getImage();	//the player's character image
        nCatsL.add(normalC1);
        nCatsL.add(normalC2);
      
      //player tings
        opx = 200;
        opy = 300;
        player = new Cat(opx,opy,upsideDC, nCatsR, nCatsL);

      //platform tings
        plat = new ImageIcon(getClass().getResource("Platform.png")).getImage();
        platforms = new ArrayList<Platform>();
        Platform platform = new Platform(5,450,100,20, plat);
        platforms.add(platform);
        Platform platform2 = new Platform(170,500,100,20, plat);
        platforms.add(platform2);
        Platform platform3 = new Platform(300,450,100,20, plat);
        platforms.add(platform3);
        Platform platform4 = new Platform(200,150,100,20, plat);
        platforms.add(platform4);

        //saw tings
        s1 = new Saw(300,450,300,400,"right");


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
            player.addX(4);
        }
        if(keys[KeyEvent.VK_LEFT]){
            player.addX(-4);
        }
        if(keys[KeyEvent.VK_UP]){
            for(Platform platy:platforms){
                if(!oldKeys[KeyEvent.VK_UP]&&platy.onPlat(player)) {
                    jCounter = 10;
                    canJump = true;
                }
            }
            if(jCounter>0 &&canJump){
                player.jump();
                player.setOnPlat(platforms.get(0), false);
                jCounter--;
            }
        }
        if(keys[KeyEvent.VK_SPACE]){
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
        }
        if(!keys[KeyEvent.VK_UP]){
            canJump = false;
        }
    }

    public void checkPlayer(){
        boolean onAPlat = false;
        for(Platform platy: platforms){
            if(platy.onPlat(player)){ //check if player is on a platform
                player.setJumping(false);
                player.setFalling(false);
                onAPlat = true;
                player.setOnPlat(platy,true);
            }
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
        if(canLeft){
            player.setCanGoLeft(true);
        }

        if(player.isFalling()){
            player.addY(gravity); //gravity
        }
        if(player.isJumping()){
            player.checkJumpV(); //do jumping things
        }
        if(player.isDead()){
            player.setCoords(opx,opy);
            player.setDead(false);
            gravity = 3;
            player.setNormalGravity(true);
        }
        player.updateCollideRects();
    }

    public void updateSaws(){
        s1.move();
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
    }
}