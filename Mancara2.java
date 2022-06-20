import java.awt.Color;
import java.awt.Graphics;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

class FrameController extends JFrame {
      
    public FrameController(){
      ChangePanel cp = new ChangePanel(); 
       cp.change(new StartPanel(cp));
     
    }
    public static void main(String argv[]) {
        new FrameController();
        
    }
}
class ChangePanel extends JFrame{
Graphics g;
Image img;
private Image image = null;
    public ChangePanel(){
        
        this.setSize(800, 450);
        
      Container contentPane = getContentPane();
      //contentPane.setBackground(Color.ORANGE);
      this.setTitle("StartPanel");
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      //this.pack() ;
      this.setVisible(true);
      repaint();
    }
  	public void change(JPanel panel) {
        getContentPane().removeAll();
        //setContentPane(new JLabel(new ImageIcon("506.jpg")));
        panel.setOpaque(false);
        this.add(panel);
  		validate();
	  	repaint();
	  }
}
class ImagePanel extends JPanel {

    private Image image = null;

    public ImagePanel(String filename) {
        this.image = new ImageIcon(filename).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, image.getWidth(null), image.getHeight(null), null);
    }
}
class StartPanel extends JPanel{
    private JButton b;
    private JPanel p, pb;
    private ButtonListener bl;
    private ChangePanel cp;
    public StartPanel(ChangePanel c) {
      cp = c;
      b  = new JButton("Play");
      bl = new ButtonListener();
      b.addActionListener(bl);
      b.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 32));
      p = new JPanel();
      p.add(b);
      p.setOpaque( false ) ;
      ImageIcon icon = new ImageIcon("506.jpg");
      Image image = icon.getImage(); // transform it 
      Image newimg = image.getScaledInstance(800,  450,  Image.SCALE_SMOOTH); // scale it the smooth way  
      icon = new ImageIcon(newimg);
      JLabel label = new JLabel(icon);
      pb = new JPanel();
      pb.add(label);
      //pb.setPreferredSize( new Dimension( 800, 450 ) ) ;
      pb.add(p);
      this.add(pb);
    }

    class ButtonListener implements ActionListener {
      public void actionPerformed(ActionEvent e){  
        cp.change(new ResultPanel(cp));
      }
    }
}
class ResultPanel extends JPanel{
    private JButton b;
    private JPanel p;
    private ButtonListener bl;
    private ChangePanel cp;
    public ResultPanel(ChangePanel c ) {
      cp = c; 
      b  = new JButton("Back to start");
      bl = new ButtonListener();
      b.addActionListener(bl);
      b.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 32));
      p = new JPanel();
      p.add(b);
      this.add(p);
    }
    class ButtonListener implements ActionListener {
      public void actionPerformed(ActionEvent e){  
        cp.change(new StartPanel(cp));
      }
    }
}
public class Mancara2 extends JPanel implements MouseListener{
    static final int WIDTH = 800;
    static final int HEIGHT = 450;
    static final int box = 8;
    public int i=0;
    JLabel label;
    ChangePanel cp;
    int player = 1;
    int boxes[] = {0,3,3,3,0,3,3,3};
    public Mancara2(ChangePanel c) {
        cp = c;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D)g;
        BasicStroke bs = new BasicStroke(3,BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);

        
        Toolkit toolkit = getToolkit();
		Image img = toolkit.getImage("background.jpg");
        g.drawImage(img, 0, 0, this);
        g2.setStroke(bs);


        g.drawRoundRect(50,   60, 100, 320,40,20);
        g.drawRoundRect(200, 260, 100, 120,40,20);
        g.drawRoundRect(350, 260, 100, 120,40,20);
        g.drawRoundRect(500, 260, 100, 120,40,20);
        g.drawRoundRect(200, 60,  100, 120,40,20);
        g.drawRoundRect(350, 60,  100, 120,40,20);
        g.drawRoundRect(500, 60,  100, 120,40,20);
        g.drawRoundRect(650, 60,  100, 320,40,20);

        g.setColor(new Color(11,130,255));

        if(boxes[1] == 0 && boxes[2] == 0 && boxes[3] == 0){
            boxes[0] += boxes[5];
            boxes[0] += boxes[6];
            boxes[0] += boxes[7];
            if(i==0)end(g);
        }else if(boxes[5] == 0 && boxes[6] == 0 && boxes[7] == 0){
            boxes[4] += boxes[1];
            boxes[4] += boxes[2];
            boxes[4] += boxes[3];
            if(i==0)end(g);
        }

        int x = 55;
        int y = 65;
        int haba = 0;
        int koma = boxes[0];
        g.drawString(Integer.toString(boxes[0],10),70,400);
        if(koma > 10){
            g.setColor(new Color(255,130,255));
            g.fillOval(x, y+haba, 30, 30);
            haba += 40;
            g.fillOval(x, y+haba, 30, 30);
            haba += 40;
            koma -= 10;
            g.setColor(new Color(11,130,255));
        }
        if(koma > 5){
            g.setColor(new Color(255,130,255));
            g.fillOval(x, y+haba, 30, 30);
            haba += 40;
            koma -= 5;
            g.setColor(new Color(11,130,255));
        }
        for(int i = 0; i < koma; ++i){
            g.fillOval(x, y+haba, 30, 30);
            haba += 40;
            if(haba >= 320){
                x += 50;
                haba = 0;
            }
        }

        x = 205;
        y = 265;
        haba = 0;
        koma = boxes[1];
        g.drawString(Integer.toString(boxes[1],10),220,400);
        if(koma > 10){
            g.setColor(new Color(255,130,255));
            g.fillOval(x, y+haba, 30, 30);
            haba += 40;
            g.fillOval(x, y+haba, 30, 30);
            haba += 40;
            koma -= 10;
            g.setColor(new Color(11,130,255));
        }
        if(koma > 5){
            g.setColor(new Color(255,130,255));
            g.fillOval(x, y+haba, 30, 30);
            haba += 40;
            koma -= 5;
            g.setColor(new Color(11,130,255));
        }
        for(int i = 0; i < koma; ++i){
            g.fillOval(x, y+haba, 30, 30);
            haba += 40;
            if(haba >= 120){
                x += 50;
                haba = 0;
            }
        }

        x = 355;
        y = 265;
        haba = 0;
        koma = boxes[2];
        g.drawString(Integer.toString(boxes[2],10),370,400);
        if(koma > 10){
            g.setColor(new Color(255,130,255));
            g.fillOval(x, y+haba, 30, 30);
            haba += 40;
            g.fillOval(x, y+haba, 30, 30);
            haba += 40;
            koma -= 10;
            g.setColor(new Color(11,130,255));
        }
        if(koma > 5){
            g.setColor(new Color(255,130,255));
            g.fillOval(x, y+haba, 30, 30);
            haba += 40;
            koma -= 5;
            g.setColor(new Color(11,130,255));
        }
        for(int i = 0; i < koma; ++i){
            g.fillOval(x, y+haba, 30, 30);
            haba += 40;
            if(haba >= 120){
                x += 50;
                haba = 0;
            }
        }

        x = 505;
        y = 265;
        haba = 0;
        koma = boxes[3];
        g.drawString(Integer.toString(boxes[3],10),520,400);
        if(koma > 10){
            g.setColor(new Color(255,130,255));
            g.fillOval(x, y+haba, 30, 30);
            haba += 40;
            g.fillOval(x, y+haba, 30, 30);
            haba += 40;
            koma -= 10;
            g.setColor(new Color(11,130,255));
        }
        if(koma > 5){
            g.setColor(new Color(255,130,255));
            g.fillOval(x, y+haba, 30, 30);
            haba += 40;
            koma -= 5;
            g.setColor(new Color(11,130,255));
        }
        for(int i = 0; i < koma; ++i){
            g.fillOval(x, y+haba, 30, 30);
            haba += 40;
            if(haba >= 120){
                x += 50;
                haba = 0;
            }
        }

        x = 205;
        y = 65;
        haba = 0;
        koma = boxes[7];
        g.drawString(Integer.toString(boxes[7],10),220,200);
        if(koma > 10){
            g.setColor(new Color(255,130,255));
            g.fillOval(x, y+haba, 30, 30);
            haba += 40;
            g.fillOval(x, y+haba, 30, 30);
            haba += 40;
            koma -= 10;
            g.setColor(new Color(11,130,255));
        }
        if(koma > 5){
            g.setColor(new Color(255,130,255));
            g.fillOval(x, y+haba, 30, 30);
            haba += 40;
            koma -= 5;
            g.setColor(new Color(11,130,255));
        }
        for(int i = 0; i < koma; ++i){
            g.fillOval(x, y+haba, 30, 30);
            haba += 40;
            if(haba >= 120){
                x += 50;
                haba = 0;
            }
        }

        x = 355;
        y = 65;
        haba = 0;
        koma = boxes[6];
        g.drawString(Integer.toString(boxes[6],10),370,200);
        if(koma > 10){
            g.setColor(new Color(255,130,255));
            g.fillOval(x, y+haba, 30, 30);
            haba += 40;
            g.fillOval(x, y+haba, 30, 30);
            haba += 40;
            koma -= 10;
            g.setColor(new Color(11,130,255));
        }
        if(koma > 5){
            g.setColor(new Color(255,130,255));
            g.fillOval(x, y+haba, 30, 30);
            haba += 40;
            koma -= 5;
            g.setColor(new Color(11,130,255));
        }
        for(int i = 0; i < koma; ++i){
            g.fillOval(x, y+haba, 30, 30);
            haba += 40;
            if(haba >= 120){
                x += 50;
                haba = 0;
            }
        }

        x = 505;
        y = 65;
        haba = 0;
        koma = boxes[5];
        g.drawString(Integer.toString(boxes[5],10),520,200);
        if(koma > 10){
            g.setColor(new Color(255,130,255));
            g.fillOval(x, y+haba, 30, 30);
            haba += 40;
            g.fillOval(x, y+haba, 30, 30);
            haba += 40;
            koma -= 10;
            g.setColor(new Color(11,130,255));
        }
        if(koma > 5){
            g.setColor(new Color(255,130,255));
            g.fillOval(x, y+haba, 30, 30);
            haba += 40;
            koma -= 5;
            g.setColor(new Color(11,130,255));
        }
        for(int i = 0; i < koma; ++i){
            g.fillOval(x, y+haba, 30, 30);
            haba += 40;
            if(haba >= 120){
                x += 50;
                haba = 0;
            }
        }

        x = 655;
        y = 65;
        haba = 0;
        koma = boxes[4];
        g.drawString(Integer.toString(boxes[4],10),670,400);
        if(koma > 10){
            g.setColor(new Color(255,130,255));
            g.fillOval(x, y+haba, 30, 30);
            haba += 40;
            g.fillOval(x, y+haba, 30, 30);
            haba += 40;
            koma -= 10;
            g.setColor(new Color(11,130,255));
        }
        if(koma > 5){
            g.setColor(new Color(255,130,255));
            g.fillOval(x, y+haba, 30, 30);
            haba += 40;
            koma -= 5;
            g.setColor(new Color(11,130,255));
        }
        for(int i = 0; i < koma; ++i){
            g.fillOval(x, y+haba, 30, 30);
            haba += 40;
            if(haba >= 320){
                x += 50;
                haba = 0;
            }
        }

		g.drawString("Player:" +Integer.toString(player,10),350, 430);

    }


    public void end(Graphics g) {
        if(boxes[4] > boxes[0]){
            g.drawString("Player1 WIN",300,250);
            cp.change(new ResultPanel(cp)); i++;
        }else if(boxes[4] < boxes[0]){
            g.drawString("Player2 WIN",300,250);
            cp.change(new ResultPanel(cp)); i++;
        }else{
            g.drawString("Draw",300,250);
        }
    }

    

    public void mousePressed(MouseEvent e) {                                //マウス押した晁E
        int x = e.getX();                                                   //座標取征E
        int y = e.getY();
        int bkoma,i,soeji,yo = 0;
        if(player == 1 && x >= 200 && x <= 300 && y >= 260 && y <= 380 && boxes[1] > 0){        //押した場所確俁E
            bkoma = boxes[1];
            boxes[1] = 0;
            soeji = 1;
            for(i = soeji+1 ; bkoma > 0; ++i){
                if(i == 8){
                    i = 1;
                }
                boxes[i] += 1;
                bkoma -= 1;
                yo = i;
            }
            if(boxes[yo] == 1 && yo == 1 && boxes[7] != 0){
                boxes[4] += boxes[1];
                boxes[4] += boxes[7];
                boxes[1] = boxes[7] = 0;
                player = 2;
                repaint();
            }else if(boxes[yo] == 1 && yo == 2 && boxes[6] != 0){
                boxes[4] += boxes[2];
                boxes[4] += boxes[6];
                boxes[2] = boxes[6] = 0;
                player = 2;
                repaint();
            }else if(boxes[yo] == 1 && yo == 3 && boxes[5] != 0){
                boxes[4] += boxes[3];
                boxes[4] += boxes[5];
                boxes[3] = boxes[5] = 0;
                player = 2;
                repaint();
            }else if(yo == 4){
                repaint();
            }else{
                player = 2;
                repaint();
            }
        }
        if(player == 1 && x >= 350 && x <= 450 && y >= 260 && y <= 380 && boxes[2] > 0){
            bkoma = boxes[2];
            boxes[2] = 0;
            soeji = 2;
            for(i = soeji+1 ; bkoma > 0; ++i){
                if(i == 8){
                    i = 1;
                }
                boxes[i] += 1;
                bkoma -= 1;
                yo = i;
            }
            if(boxes[yo] == 1 && yo == 1 && boxes[7] != 0){
                boxes[4] += boxes[1];
                boxes[4] += boxes[7];
                boxes[1] = boxes[7] = 0;
                player = 2;
                repaint();
            }else if(boxes[yo] == 1 && yo == 2 && boxes[6] != 0){
                boxes[4] += boxes[2];
                boxes[4] += boxes[6];
                boxes[2] = boxes[6] = 0;
                player = 2;
                repaint();
            }else if(boxes[yo] == 1 && yo == 3 && boxes[5] != 0){
                boxes[4] += boxes[3];
                boxes[4] += boxes[5];
                boxes[3] = boxes[5] = 0;
                player = 2;
                repaint();
            }else if(yo == 4){
                repaint();
            }else{
                player = 2;
                repaint();
            }
        }
        if(player == 1 && x >= 500 && x <= 600 && y >= 260 && y <= 380 && boxes[3] > 0){
            bkoma = boxes[3];
            boxes[3] = 0;
            soeji = 3;
            for(i = soeji+1 ; bkoma > 0; ++i){
                if(i == 8){
                    i = 1;
                }
                boxes[i] += 1;
                bkoma -= 1;
                yo = i;
            }
            if(boxes[yo] == 1 && yo == 1 && boxes[7] != 0){
                boxes[4] += boxes[1];
                boxes[4] += boxes[7];
                boxes[1] = boxes[7] = 0;
                player = 2;
                repaint();
            }else if(boxes[yo] == 1 && yo == 2 && boxes[6] != 0){
                boxes[4] += boxes[2];
                boxes[4] += boxes[6];
                boxes[2] = boxes[6] = 0;
                player = 2;
                repaint();
            }else if(boxes[yo] == 1 && yo == 3 && boxes[5] != 0){
                boxes[4] += boxes[3];
                boxes[4] += boxes[5];
                boxes[3] = boxes[5] = 0;
                player = 2;
                repaint();
            }else if(yo == 4){
                repaint();
            }else{
                player = 2;
                repaint();
            }
        }
        if(player == 2 && x >= 500 && x <= 600 && y >= 60 && y <= 180 && boxes[5] > 0){
            bkoma = boxes[5];
            boxes[5] = 0;
            soeji = 5;
            for(i = soeji+1 ; bkoma > 0; ++i){
                if(i == 4){
                    i = 5;
                }
                if(i == 8){
                    i = 0;
                }
                boxes[i] += 1;
                bkoma -= 1;
                yo = i;
            }
            if(boxes[yo] == 1 && yo == 5 && boxes[3] != 0){
                boxes[0] += boxes[3];
                boxes[0] += boxes[5];
                boxes[3] = boxes[5] = 0;
                player = 1;
                repaint();
            }else if(boxes[yo] == 1 && yo == 6 && boxes[2] != 0){
                boxes[0] += boxes[2];
                boxes[0] += boxes[6];
                boxes[2] = boxes[6] = 0;
                player = 1;
                repaint();
            }else if(boxes[yo] == 1 && yo == 7 && boxes[1] != 0){
                boxes[0] += boxes[3];
                boxes[0] += boxes[5];
                boxes[3] = boxes[5] = 0;
                player = 1;
                repaint();
            }else if(yo == 0){
                repaint();
            }else{
                player = 1;
                repaint();
            }
        }
        if(player == 2 && x >= 350 && x <= 450 && y >= 60 && y <= 180 && boxes[6] > 0){
            bkoma = boxes[6];
            boxes[6] = 0;
            soeji = 6;
            for(i = soeji+1 ; bkoma > 0; ++i){
                if(i == 4){
                    i = 5;
                }
                if(i == 8){
                    i = 0;
                }
                boxes[i] += 1;
                bkoma -= 1;
                yo = i;
            }
            if(boxes[yo] == 1 && yo == 5 && boxes[3] != 0){
                boxes[0] += boxes[3];
                boxes[0] += boxes[5];
                boxes[3] = boxes[5] = 0;
                player = 1;
                repaint();
            }else if(boxes[yo] == 1 && yo == 6 && boxes[2] != 0){
                boxes[0] += boxes[2];
                boxes[0] += boxes[6];
                boxes[2] = boxes[6] = 0;
                player = 1;
                repaint();
            }else if(boxes[yo] == 1 && yo == 7 && boxes[1] != 0){
                boxes[0] += boxes[3];
                boxes[0] += boxes[5];
                boxes[3] = boxes[5] = 0;
                player = 1;
                repaint();
            }else if(yo == 0){
                repaint();
            }else{
                player = 1;
                repaint();
            } 
        }
        if(player == 2 && x >= 200 && x <= 300 && y >= 60 && y <= 180 && boxes[7] > 0){ 
            bkoma = boxes[7];
            boxes[7] = 0;
            soeji = 7;
            for(i = soeji+1 ; bkoma > 0; ++i){
                if(i == 4){
                    i = 5;
                }
                if(i == 8){
                    i = 0;
                }
                boxes[i] += 1;
                bkoma -= 1;
                yo = i;
            }     
            if(boxes[yo] == 1 && yo == 5 && boxes[3] != 0){
                boxes[0] += boxes[3];
                boxes[0] += boxes[5];
                boxes[3] = boxes[5] = 0;
                player = 1;
                repaint();
            }else if(boxes[yo] == 1 && yo == 6 && boxes[2] != 0){
                boxes[0] += boxes[2];
                boxes[0] += boxes[6];
                boxes[2] = boxes[6] = 0;
                player = 1;
                repaint();
            }else if(boxes[yo] == 1 && yo == 7 && boxes[1] != 0){
                boxes[0] += boxes[3];
                boxes[0] += boxes[5];
                boxes[3] = boxes[5] = 0;
                player = 1;
                repaint();
            }else if(yo == 0){
                repaint();
            }else{
                player = 1;
                repaint();
            }   
        }
    }
    public void mouseClicked(MouseEvent e) { }
    public void mouseReleased(MouseEvent e){ }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e)  { }

}