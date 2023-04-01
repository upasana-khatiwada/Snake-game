import javax.swing.*;

public class GameFrame extends JFrame  {

    GameFrame(){
//        GamePanel panel = new GamePanel();
//        this.add(panel);   //(method 1)

        this.add(new GamePanel());// method 2
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600,600);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.pack();


    }


}
