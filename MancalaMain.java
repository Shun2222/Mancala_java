import java.awt.Color;
import java.awt.Graphics;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class MancalaMain extends JFrame {
      
    public MancalaMain(){
      ChangePanel cp = new ChangePanel(); 
       cp.ChangeLayer(new StartPanel(cp));
     
    }
    public static void main(String argv[]) {
        new MancalaMain();
        
    }
}
class ChangePanel extends JFrame{
Graphics g;
Image img;

public ChangePanel() {
         this.setSize(1250, 450);
         this.setTitle("Mancala");
         this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         //this.pack() ;
         this.setVisible(true);
    }
  	public void ChangeLayer(JLayeredPane pane) {
         getContentPane().removeAll();
         this.add(pane);
         validate();
         repaint();
    }
    public void Change(JPanel panel) {
         getContentPane().removeAll();
         this.add(panel);
         validate();
         repaint();
	}
}
class GetTime{
    GetTime(){}
    public String getValue() {
      Calendar cal = Calendar.getInstance(); 
      int hour= (cal.get(Calendar.HOUR_OF_DAY)+24)%24; 
      int min = cal.get(Calendar.MINUTE); 
      int sec = cal.get(Calendar.SECOND); 
      String current_time = String.format("%02d:%02d:%02d",hour,min,sec);
      return current_time;
    }
}
class StartPanel extends JLayeredPane implements ActionListener{
    private JButton b;
    private JLabel label, timelabel, title;
    private ButtonListener bl;
    private ChangePanel cp;
    private GetTime t;
    private Controller cont;
    private javax.swing.Timer timer;
    public StartPanel(ChangePanel c) {
      cp = c;
      //cont = new Controller();
      //this.addMouseListener(cont);
      t = new GetTime();
      // ボタンを作る
      b  = new JButton("Play");
      bl = new ButtonListener();
      b.addActionListener(bl);
      b.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 32));
      b.setForeground(Color.GREEN);
      b.setBackground(Color.WHITE);
      // title
      title = new JLabel("Mancala");
      title.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 64));
      // 背景写真を読み取る
      ImageIcon icon = new ImageIcon("./Image/back1.jpg");
      // 背景写真の大きさを変更
      Image image = icon.getImage(); 
      Image newimg = image.getScaledInstance(1250,  450,  Image.SCALE_SMOOTH);  
      // sizeを変更したiconに更新
      icon = new ImageIcon(newimg);
      label = new JLabel(icon);
      //時刻用JLabel作成
      timelabel = new JLabel(t.getValue(),JLabel.CENTER);
      timelabel.setFont(new Font(Font.SANS_SERIF,Font.BOLD,32));  
      //位置・サイズの決定
      title.setBounds(625-150, 50-50, 300, 100);
      label.setBounds(0, 0, 1250, 450);
      b.setBounds(625-75, 225-50, 150, 100);
      timelabel.setBounds(0, 10, 300, 50);
      //Layerに加える
      add(label); setLayer(label, DEFAULT_LAYER, 0);
      add(title); setLayer(title, PALETTE_LAYER, 100);
      add(b); setLayer(b, PALETTE_LAYER, 100);
      add(timelabel); setLayer(timelabel, PALETTE_LAYER, 100);
      // 1秒毎にactionPerformedを呼び出し
      timer = new javax.swing.Timer(1000, this); 
      timer.start(); 
    }
    public void actionPerformed(ActionEvent e) {
        timelabel.setText(t.getValue());
    }

    class ButtonListener implements ActionListener {
      public void actionPerformed(ActionEvent e){  
        cp.ChangeLayer(new ResultPanel(cp));
      }
    }
}
class ResultPanel extends JLayeredPane{
    private JButton b;
    private ButtonListener bl;
    private ChangePanel cp;
    public ResultPanel(ChangePanel c) {
      cp = c;
      // ボタンを作る
      b  = new JButton("Back to start");
      bl = new ButtonListener();
      b.addActionListener(bl);
      b.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 32));
      b.setForeground(Color.GREEN);
      b.setBackground(Color.BLACK);
      // 背景写真を読み取る
      ImageIcon icon = new ImageIcon("./Image/back1.jpg");
      // 背景写真の大きさを変更
      Image image = icon.getImage(); 
      Image newimg = image.getScaledInstance(1250,  450,  Image.SCALE_SMOOTH);
      // sizeを変更したiconに更新
      icon = new ImageIcon(newimg);
      JLabel label = new JLabel(icon);
      //位置・サイズの決定
      label.setBounds(0, 0, 1250, 450);
      b.setBounds(625-150, 225-50, 300, 100);
      //Layerに加える
      add(label); setLayer(label, DEFAULT_LAYER, 0);
      add(b); setLayer(b, PALETTE_LAYER, 100);
    }
    class ButtonListener implements ActionListener {
      public void actionPerformed(ActionEvent e){  
        cp.ChangeLayer(new StartPanel(cp));
      }
    }
}
class MancalaController implements MouseListener, KeyListener{
    MancalaModel model; 
    public MancalaController(MancalaModel a) {
        model = a;
    }
    public void mousePressed(MouseEvent e) {                                //マウス押した時
        int x = e.getX();                                                   //座標取得
        int y = e.getY();
        int num = -1; //ポケットの添え字
        //初めに決めた座標内に入っているか判定し、座標あったポケットの添え字をmodelに送る
        if( 200 <= x && x <= 300 ){
            if(60 <= y && y <= 180){
                num = 12;
            }
            else if(260 <= y && y <=380){
                num = 0;
            }
        }
        else if(350 <= x && x <= 450){
            if(60 <= y && y <= 180){
                num = 11;
            }
            else if(260 <= y && y <=380){
                num = 1;
            }
        }
        else if(500 <= x && x <= 600){
            if(60 <= y && y <= 180){
                num = 10;
            }
            else if(260 <= y && y <=380){
                num = 2;
            }

        }
        else if(650<= x && x <=  750){
            if(60 <= y && y <= 180){
                num = 9;
            }
            else if(260 <= y && y <=380){
                num = 3;
            }

        }
        else if(800 <= x && x <= 900){
            if(60 <= y && y <= 180){
                num = 8;
            }
            else if(260 <= y && y <=380){
                num = 4;
            }

        }
        else if(950 <= x && x <= 1050){
            if(60 <= y && y <= 180){
                num = 7;
            }
            else if(260 <= y && y <=380){
                num = 5;
            }
        }
        if( num!=-1 ) model.moveStone(num);
        num = -1;
    }
    public void mouseClicked(MouseEvent e) { }
    public void mouseReleased(MouseEvent e){ }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e)  { }
    public void keyPressed(KeyEvent e){}
    public void keyTyped(KeyEvent e){ 
      char c = e.getKeyChar();
      switch(c){
        case '1':
          num = 0;
          if(model.getPlayer() == 2){ 
              num = 12 - num;
            }
          break;
        case '2': 
          num = 1;
          if(model.getPlayer() == 2){
              num = 12 - num;
            }
          break;
        case '3':
          num = 2;
          if(model.getPlayer() == 2){
              num = 12 - num;
            }
          break;
        case '4':
          num = 3;
          if(model.getPlayer() == 2){ 
              num = 12 - num;
            }
          break;
        case '5':
          num = 4;
          if(model.getPlayer() == 2){ 
              num = 12 - num;
            }
          break;
        case '6':
          num = 5;
          if(model.getPlayer() == 2){ 
              num = 12 - num;
            }
          break;
       }
       if( num!=-1 ) model.moveStone(num);
       num = -1;
     }
     public void keyReleased(KeyEvent e){ }
}