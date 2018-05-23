//GrambityCat.java
//Jenny Chen
/*
need to do
- jumping
- gravity mechanic
- saw object
- switch object
- animation
- all graphics
*/
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GrambityCat extends JFrame implements ActionListener{
    private javax.swing.Timer myTimer;	//the Timer
    private JPanel cards;	//the cards
    private CardLayout cLayout = new CardLayout();	//the layout
    private JButton playBtn = new JButton("Play");	//the button that starts the game
    private GamePanel game;	//the game panel

    //the game constructor
    public GrambityCat(){
        super("Grambity Cat");
        setSize(600,600);
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
            game.move();	//move the player

            game.repaint();
            game.checkPlayer();
        }

    }

    //start the game
    public static void main(String[] args){
        GrambityCat frame = new GrambityCat();
    }

}

//////////////////////////////////////
class GamePanel extends JPanel implements KeyListener{
    private Cat player; //player's coords
    private Platform platform; //put in txt file soon
    private boolean[] keys;	//the state of the keyboard
    private Image back;	//pictuer of the background of the main page
    private Image cat;
    private Image plat;
    private GrambityCat mainFrame; //the game's frame
    private boolean[] oldKeys;

    private int jumpTimer;

    //constructor
    public GamePanel(GrambityCat m){
        keys = new boolean [KeyEvent.KEY_LAST+1]; //make the keyboard list as large as needed
        oldKeys = new boolean [KeyEvent.KEY_LAST+1];
        back = new ImageIcon(getClass().getResource("background.jpg")).getImage();	//the background of the game
        cat = new ImageIcon(getClass().getResource("cat005.png")).getImage();	//the player's character image
        plat  = new ImageIcon(getClass().getResource("Platform.png")).getImage();
        mainFrame = m;	//the main frame

        player = new Cat(200,100,cat);
        platform = new Platform(190,250,100,20, plat);
        plat = new ImageIcon(getClass().getResource("Platform.png")).getImage();

        setSize(800,800);
        addKeyListener(this);
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
            player.addX(2);

        }
        if(keys[KeyEvent.VK_LEFT]){
            player.addX(-2);
        }
        if(keys[KeyEvent.VK_UP]){
            if(!oldKeys[KeyEvent.VK_UP]){
                player.jump();
                player.setOntop(platform, false);
            }

        }


    }


    public void checkPlayer(){

        if(platform.ontop(player)){
            player.setFalling(false);
            player.setOntop(platform,true);
        }
        else{
            player.setFalling(true);
        }
        if(player.isFalling()){
            player.addY(2);
        }
        player.checkJumpV();


    }
    /*
    //moveBad moves the enemies
    public void moveBad(){
        int interval = 40-baddies.get(0).down()*(level)*2; //the interval of loops at which they moove at
        if(interval <=0){
            interval =1;
        }
        if(counter==interval){
            counter = 0;
            for(Badguy b : baddies){
                b.shift();
            }

        }
    }
    */
    /*
    //nextLevel returns true if the next level needs to be made
    //checks if there are any Badguys left
    //if not, it returns true
    public boolean nextLevel(){
        if(baddies.size()==0){
            return true;
        }
        return false;
    }
*/
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
        platform.draw(g,this);
        // g.drawImage(plat,190,300,this);
    }
}