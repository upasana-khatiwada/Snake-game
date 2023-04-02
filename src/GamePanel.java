import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;


public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH =600;
    static final int SCREEN_HEIGHT=600;
    static final int UNIT_SIZE = 25;//each item in this game is going to have a dimension of 25 pixels for width and height
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 85;//speed of game where we decrease the delay time then level of game increase
    final int x[ ]= new int[GAME_UNITS] ;//x position of snake
    final int y[]= new int [GAME_UNITS];//y position of the snake
    int bodyParts =6; //initial body part of snake
    int appleEaten;
    int appleX;//X coordinate where apple will be located
    int appleY;//Y coordinate where apple will be located
    char direction = 'R';//direction in which snake will move. For ,initial it will move to right
    boolean running = false;
    Timer timer;
    Random random;




    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);//set the focusable state of this component to the specified value
        this.addKeyListener(new MyKeyAdapter());//Adds the specified key listener to receive key events from this component
        startGame();
    }
    public void startGame(){
        newApple();
        running = true;
        timer= new Timer(DELAY,this);//this dictate how fast the game is running
        timer.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);

    }
    public void draw(Graphics g){

        if(running){
            //to draw the grid to help to understand about pixels

//            for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++)
//            {
//                g.drawLine(i*UNIT_SIZE,0,i*UNIT_SIZE,SCREEN_HEIGHT);
//                g.drawLine(0,i*UNIT_SIZE,SCREEN_WIDTH,i*UNIT_SIZE);
//
//
//            }
            g.setColor(Color.RED);
            g.fillOval(appleX,appleY,UNIT_SIZE,UNIT_SIZE);

            for(int i=0;i<bodyParts;i++){
                if(i==0){
                    g.setColor(Color.orange);
                    g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
                }
                else {
                    g.setColor(new Color(45,180,0 ));
                  //  create a random color for a snake
                  //  g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                    g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
                }
            }
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free",Font.BOLD,40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: "+ appleEaten,(SCREEN_WIDTH-metrics.stringWidth("Score: "+appleEaten))/2,g.getFont().getSize());

        }
        else{
            gameOver(g);
        }
    }
    //draw apple at random place after snake eat
    public void newApple(){
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;

    }

    public void move(){
        //initial bodyParts is 6
        for(int i = bodyParts;i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        switch(direction){
            case 'U':
                y[0]=y[0]-UNIT_SIZE;
                break;
            case 'D':
                y[0]=y[0]+UNIT_SIZE;
                break;
            case 'L':
                x[0]=x[0]-UNIT_SIZE;
                break;
            case 'R':
                x[0]=x[0]+UNIT_SIZE;
                break;

        }

    }
    public void checkApple(){
        if((x[0]==appleX)&&(y[0]==appleY))
        {
            bodyParts++;
            appleEaten++;
            newApple();
        }
    }
    public void checkCollision(){
        //it checks if the head collided with the body
        for(int i=bodyParts;i>0;i--){
            if((x[0]==x[i])&& (y[0]==y[i]) )
            {
                running = false;
            }
        }
        //checks if head touches left border
        if(x[0]<0){
            running = false;
        }
        //check if head touches left border
        if(x[0]>SCREEN_WIDTH){
            running = false;
        }
        //check if head touches top border
        if(y[0]<0){
            running = false;
        }
        //check if head touches bottom border
        if(y[0]>SCREEN_HEIGHT){
            running = false;
        }
        if(!running){
            timer.stop();
        }


    }
    public void gameOver(Graphics g){
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: "+ appleEaten,(SCREEN_WIDTH-metrics1.stringWidth("Score: "+appleEaten))/2,g.getFont().getSize());
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over",(SCREEN_WIDTH-metrics2.stringWidth("Game Over"))/2,SCREEN_HEIGHT/2);

    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if(running){
            move();
            checkApple();
            checkCollision();



        }
        repaint();

    }

//Using a KeyAdapter can simplify the implementation of keyboard event handling in Java
    public class MyKeyAdapter extends KeyAdapter{
//        The switch statement in this method allows you to check which key was pressed by using the getKeyCode() method of the KeyEvent object.
//        The getKeyCode() method returns an integer value that represents the code of the key that was pressed.
//        The switch statement then checks this value against a set of cases.

        @Override
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()){
                //left click gariraxa ani aafu right gairaxa vanye we cant go left because 180 degree hunxa and hami head jata xa tyo direction bata matra hinna milxa

                case KeyEvent.VK_LEFT :
                    if(direction!= 'R'){
                        direction= 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction!= 'L'){
                        direction= 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction!= 'D'){
                        direction= 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction!= 'U'){
                        direction= 'D';
                    }
                    break;
            }

        }

    }
}

