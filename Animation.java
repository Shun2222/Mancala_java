import java.awt.Color;
import java.awt.Graphics;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.awt.Desktop;
import java.net.URI;
import java.io.File;
import javax.sound.sampled.*;

class Mancala extends JFrame {
      
    public Mancala(){
      ChangePanel cp = new ChangePanel(); 
       cp.ChangeLayer(new StartPanel(cp));
    }
    public static void main(String argv[]) {
        new Mancala();
    }
}

class ChangePanel extends JFrame{
protected JLayeredPane p;
public ChangePanel() {
         this.setSize(1250, 450);
         this.setTitle("Mancala");
         this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         //this.pack() ;
         this.setVisible(true);
    }
    
    public void ChangeLayer(JLayeredPane pane) {
          //addしたもの消去
         getContentPane().removeAll();
         //pの更新
         p = pane;
         //paneを加える
         this.add(pane);
         //更新と再描写
         validate();
         repaint();
    }
    public void Change(JPanel panel) {
        //addしたもの消去
         getContentPane().removeAll();
         //JLayeredPaneの最下層にpanel追加
         p = new JLayeredPane(); 
         panel.setBounds(0, 0, 1250, 450);
         p.add(panel); p.setLayer(panel, JLayeredPane.DEFAULT_LAYER, 0);
         this.add(p);
         //更新と再描写
         validate();
         repaint();
    }
    public void addPanel(JPanel panel){
        //setBounds済みのパネルを配置
        p.add(panel); p.setLayer(panel, JLayeredPane.DEFAULT_LAYER, 0);
    }
}
class Animation extends JPanel implements ActionListener{
    ChangePanel cp;
    private JLabel anilabel;
    private javax.swing.Timer timer;
    private int speed = 0, flowy;
    private boolean bool = false; //anilabelがaddされた状態かの判定
    public Animation(ChangePanel c){
        cp = c;
    }
    public void FlowAnimation(String s){
        FlowAnimation(s, 0);
    }
    public void FlowAnimation(String s, int y){
        //文字の流れ始めるflowyの更新
        flowy = y;
        //すでに表示されているかの確認
        if(bool){
            cp.p.remove(anilabel);
            timer.stop();
            speed = 0;
            bool = false;
        }
        //新しいJLabelの設定
        anilabel = new JLabel(s);
        anilabel.setHorizontalAlignment(JLabel.CENTER);
        anilabel.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 48));
        //位置を更新
        timer = new javax.swing.Timer(1, this); //0だと実行環境によって変わる 
        timer.start(); 
        bool = true;
    }
    public void actionPerformed(ActionEvent e){
        //画面外：終了処理　画面内：位置の更新
        if(e.getSource()==timer){
            speed += 2;
            if(2050-speed<=0){                          
                timer.stop();
                speed = 0;
                cp.p.remove(anilabel);
            }else{
                anilabel.setBounds(1250-speed, flowy, 800, 50);
                cp.p.add(anilabel); cp.p.setLayer(anilabel, cp.p.PALETTE_LAYER, 100);            
            }
        }
    }
}
//class soundの参考url http://web.sfc.keio.ac.jp/~wadari/game/kadai/2009/gp04.html
class sound{
  private Clip audio;
  private String fileName;
  public sound(String file){
    this.audio = this.loadSound("./Music/"+file);
  }
  private Clip loadSound(String file){
    Clip val = null;
    try{
      AudioInputStream audioInputStream = null;
      audioInputStream = AudioSystem.getAudioInputStream( this.getClass().getResourceAsStream("/"+file));
      AudioFormat format = audioInputStream.getFormat(); 
      DataLine.Info info = new DataLine.Info( Clip.class , format ); 
      val = ( Clip ) AudioSystem.getLine( info );
      val.open(audioInputStream);
      audioInputStream.close();
    }catch(Exception e){
      System.out.println("loadSound Error "+file+"::"+ e);
      return null;
    }
    return val;
  }
  public void playSound(boolean loop){
    try{
      if(audio.isActive()){
        audio.stop();
      }
      else if(audio.getFrameLength() == audio.getFramePosition()){  
        audio.setFramePosition(0);      
      }
      if(loop){
        audio.loop(Clip. LOOP_CONTINUOUSLY);
      }else{
        audio.start();
      }
    }catch(Exception e){
    }
  }
  public void pauseSound(){
    try{
      audio.stop();
    }catch(Exception e){
    }
  }
  public void changeVolume(int vol){
    if(audio==null)
      return;
    try{
      FloatControl control = (FloatControl)audio.getControl(FloatControl.Type.MASTER_GAIN);
      float range = control.getMaximum() - control.getMinimum();
      control.setValue( range * (float) Math.sqrt(vol / 100.0f) + control.getMinimum() );
    }catch(Exception e){
      e.printStackTrace();
    }
  }
  public void update(){
  }
}

class GetTime{
    GetTime(){}
    //現在時刻を文字列にして返す
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
    private JButton b, b2, b3, b4;
    private JLabel label, timelabel, title;
    private ChangePanel cp;
    private GetTime t;
    private StartSettings st; 
    //private Controller cont;
    private javax.swing.Timer timer;
    private ImageIcon icon, icon2;
    private Image image, newimg;
    private Animation animation;
    public StartPanel(ChangePanel c) {
      cp = c;
      animation = new Animation(c);
      //cont = new Controller();
      //this.addMouseListener(cont);
      t = new GetTime();
      timelabel = new JLabel(t.getValue(),JLabel.CENTER);
      timelabel.setFont(new Font(Font.SANS_SERIF,Font.BOLD,32));  

      timelabel.setBounds(0, 10, 300, 50);

      add(timelabel); setLayer(timelabel, DEFAULT_LAYER, 0);
      timer = new javax.swing.Timer(1000, this); 
      timer.start(); 
    }
    public void actionPerformed(ActionEvent e){
     if(e.getSource()==timer){
            timelabel.setText(t.getValue());
            Random rand = new Random();
            int y = rand.nextInt(450);
            Animation a = new Animation(cp);
            animation.FlowAnimation("Hello!", y);
        }
      }
}
class Howto extends JFrame{
    private JLabel label;
    private ImageIcon icon;
    private Image image, newimg;
    public Howto(){
        this.setSize(450, 450);
        this.setTitle("How to play");
        JPanel p = new JPanel();
        icon = new ImageIcon("./Image/setumei.png");
        // 背景写真の大きさを変更
        image = icon.getImage(); 
        newimg = image.getScaledInstance(450,  450,  Image.SCALE_SMOOTH);  
        icon = new ImageIcon(newimg); // sizeを変更したiconに更新 
        label = new JLabel(icon);
        //パネルにラベルを張り付け
        p.add(label);
        //フレームにパネルを張り付け
        this.add(p);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
class ResultPanel extends JLayeredPane implements ActionListener{
    private JButton b;
    private JLabel winner, plabel1, plabel2;
    private ChangePanel cp;
    private javax.swing.Timer timer;
    private int Maxsize = 48, Minsize = 32, size;
    private float theta;
    public ResultPanel(ChangePanel c, int player, int pocket1, int pocket2) {
      cp = c;
      //結果表示
      if(player == 1){
        winner = new JLabel("Player 1 Win.");
      }else if(player == 2){
        winner = new JLabel("Player 2 Win.");
      }else{
        winner = new JLabel("Draw");
      }
      winner.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 64));
      plabel1 = new JLabel("Player1 : " +(pocket1)); 
      plabel2 = new JLabel("Player2 : " +(pocket2));
      plabel1.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 32));
      plabel2.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 32));
      // ボタンを作る
      b  = new JButton("Back to start");
      b.addActionListener(this);
      b.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 32));
      b.setForeground(Color.GREEN);
      b.setBackground(Color.WHITE);
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
      winner.setBounds(625-300, 50, 600, 100);
      plabel1.setBounds(625-300, 150, 600, 100);
      plabel2.setBounds(625-300, 180, 600, 100);
      b.setBounds(625-150, 225+50, 300, 100);
      //中央に揃える
      winner.setHorizontalAlignment(JLabel.CENTER);
      plabel1.setHorizontalAlignment(JLabel.CENTER);
      plabel2.setHorizontalAlignment(JLabel.CENTER);
      //Layerに加える
      add(label); setLayer(label, DEFAULT_LAYER, 0);
      add(b); setLayer(b, PALETTE_LAYER, 100);
      add(winner); setLayer(winner, PALETTE_LAYER, 100);
      add(plabel1); setLayer(plabel1, PALETTE_LAYER, 100);
      add(plabel2); setLayer(plabel2, PALETTE_LAYER, 100);
      add(b); setLayer(b, PALETTE_LAYER, 100);
      timer = new javax.swing.Timer(0, this); 
      timer.start(); 
    }
    /*public void actionPerformed(ActionEvent e) {
        theta -= 0.1;
        Color color = new Color(0, 0, 0, theta);
        winnersetForeground(color);
    }*/
    public void actionPerformed(ActionEvent e){
        if (e.getSource()==b){cp.ChangeLayer(new StartPanel(cp));}
      }
}
class StartSettings extends JFrame{
    ChangePanel cp;
    public StartSettings(ChangePanel c){
        cp = c;
        this.setSize(450, 450);
        this.setTitle("Settings");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }                                                                             

}
class MancalaController implements MouseListener, KeyListener, MouseMotionListener{
    MancalaModel model; 
    Animation animation;
    public MancalaController(MancalaModel m, Animation a) {
        model = m; 
        animation = a;
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
    public void mouseDragged(MouseEvent e){}
    public void mouseMoved(MouseEvent e) {                                //マウス押した時
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
    }
    public void keyPressed(KeyEvent e){}
    public void keyTyped(KeyEvent e){ 
      int num = -1;
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
     }
     public void keyReleased(KeyEvent e){ }
}



// ↓ 佐藤和磨(ここから)
class PocketData {
  private int stone; // 石の数
  
  public PocketData(int i) {
      if(i == 6 || i == 13) { this.stone = 0;}
      else { this.stone = 4;}
  }

  public int getStone() {
      return this.stone;
  }

  public void setStone(int stone) {
      this.stone = stone;
  }
}

class PocketArray extends Observable {
  protected PocketData arr[]; // ポケットを配列として実装
  protected int player; // プレイヤー番号 1 or 2　or 0 (0はゲーム終了)

  public PocketArray() {             // 石を４つで初期化、プレイヤー１からスタート
      arr = new PocketData[14];
      for(int i = 0; i < 14; i++) {
          arr[i] = new PocketData(i);
      }
      player = 1;
  }

  public PocketData[] getPocketArray() {
      return this.arr;
  }

  public int getPlayer() {
      return this.player;
  }

  public void printPocketArray() { // プログラム確認用に配列の中身を順に出力
      for(int i = 0; i < 14; i++) {
          System.out.printf("%d\n", this.arr[i].getStone());
      }
  }

  public boolean checkPocket(int index, PocketData arr[]) {  // 受け取ったポケット番号が正しいか確かめる
      if(index >= 0 && index <= 5 && this.player == 1 && arr[index].getStone() != 0)  { return true; }
      if(index >= 7 && index <= 12 && this.player == 2 && arr[index].getStone() != 0) { return true; }
      return false;
  }

  public void changePlayer() {  // プレイヤーを交代する
      if(this.player == 1)      { this.player = 2;}
      else if(this.player == 2) { this.player = 1;}
  }
}

class MancalaModel extends PocketArray {
    private Animation animation;
    public MancalaModel(Animation a){
      animation = a;
    }
  public void moveStone(int index) {   // ポケットの番号を受け取って石を動かす
      if(this.checkPocket(index, this.arr)) {
          int x = this.arr[index].getStone();
          this.arr[index].setStone(0);
          for(int c = 0; c < x; c++) {
              index++;
              if(index == 14) { index = 0; }
              int snum = this.arr[index].getStone();
              snum++;
              this.arr[index].setStone(snum);
          }


          x = this.arr[index].getStone();                       // 横取り
          if(x == 1) { 
              yokodoriMove(index); 
            }

          endGame();                                         // 終了条件確認

          if(index != 6 && index != 13) {                    // ぴったりゴール
              this.changePlayer(); 
          }else{
              animation.FlowAnimation("ぴったりゴール");
          }

          setChanged();
          notifyObservers();
      }
  }

  public void yokodoriMove(int index) {                          // 横取り
      if(this.player == 1 && index >= 0 && index <= 5 && this.arr[12-index].getStone() > 0) {
          int t1 = this.arr[12-index].getStone();
          t1++;
          int t2 = this.arr[6].getStone();
          this.arr[index].setStone(0);
          this.arr[12-index].setStone(0);
          this.arr[6].setStone(t1+t2);
          animation.FlowAnimation("横取り");
      } else if(player == 2 && index >= 7 && index <= 12 && this.arr[12-index].getStone() > 0) {
          int t1 = this.arr[12-index].getStone();
          t1++;
          int t2 = this.arr[13].getStone();
          this.arr[index].setStone(0);
          this.arr[12-index].setStone(0);
          this.arr[13].setStone(t1+t2);
          animation.FlowAnimation("横取り");
      }
  }

  public void endGame() {                                       //　終了条件確認
      int flag1 = 1, flag2 = 1, i, tmp;
      for(i = 0; i <= 5; i++) {
          if(this.arr[i].getStone() != 0)   { flag1 = 0; }
          if(this.arr[i+7].getStone() != 0) { flag2 = 0; }
      }
      if(flag1 == 1) {
          for(i = 7; i <= 12; i++) {
              tmp = this.arr[13].getStone();
              this.arr[13].setStone(tmp+this.arr[i].getStone());
              this.arr[i].setStone(0);
          }
          this.player = 0;
      } else if(flag2 == 1) {
          for(i = 0; i <= 5; i++) {
              tmp = this.arr[6].getStone();
              this.arr[6].setStone(tmp+this.arr[i].getStone());
              this.arr[i].setStone(0);
          }
          this.player = 0;
      }
    }
}

  

// ↑ 佐藤和磨(ここまで)

                                                                          //ViewPanel(泉)

class ViewPanel extends JPanel implements Observer{

  private MancalaModel model;
  private MancalaController controller;
  //private PocketArray pa;
  private ChangePanel cp;
  private Animation animation;
  int player;
  PocketData[] b = new PocketData[14];
  int boxes[] = new int[14];

  public ViewPanel(ChangePanel c) {
      cp = c;
      //pa = new PocketArray();
      animation = new Animation(cp);
      model = new MancalaModel(animation);
      controller = new MancalaController(model, animation);
      this.addMouseListener(controller);
      model.addObserver(this);
    }

  public void update(Observable o,Object arg) {
      repaint();
  }

  @Override
  public void paint(Graphics g) {
      super.paint(g);
      //線幅の変更
      Graphics2D g2 = (Graphics2D)g;
      BasicStroke bs = new BasicStroke(3,BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
      g2.setStroke(bs);
      //背景描写
      Toolkit toolkit = getToolkit();                                                           
      Image img = toolkit.getImage("./Image/background.jpg");
      g.drawImage(img, 0, 0, this);
      //駒の画像を取得                        
      Image img1 = toolkit.getImage("./Image/koma1.png");
      //img1 = img1.getScaledInstance(30,  30,  Image.SCALE_SMOOTH);  
      Image img2 = toolkit.getImage("./Image/koma2.png");
      //img2 = img2.getScaledInstance(30,  30,  Image.SCALE_SMOOTH);  

      //駒の配列をモデルから受け取る
      player = model.getPlayer();                                                
      b = model.getPocketArray();
      for(int i = 0; i < 14; i++) {
          boxes[i] = b[i].getStone();
      }

      //終了判定
      if(player == 0) this.end();                                              

       //枠を表示
      //player1のゴール
      g.drawRoundRect(50, 60, 100, 320,40,20);                              
      //player1の陣地
      for(int i = 200; i <= 950 ; i += 150)
        g.drawRoundRect(i, 260, 100, 120,40,20);
      //player2の陣地
      for(int i = 200; i <= 950 ; i += 150)
        g.drawRoundRect(i, 60, 100, 120,40,20);
      //player2の陣地
      g.drawRoundRect(1100, 60,  100, 320,40,20);

      //フォントを設定
      Font font = new Font("創英角",Font.ITALIC,20);
      g.setFont(font);
      g.setColor(new Color(11,130,255));

      //駒の数を表示
      //player1の陣地
      for(int i = 220, j = 0; i <= 970 ; i += 150, j++)
        g.drawString(Integer.toString(boxes[j],10),i,400);

      //player1のゴール
      g.drawString(Integer.toString(boxes[6],10),1115,400);
      
      //player2の陣地
      for(int i = 970, j = 7; i >= 220 ; i -= 150, j++)
        g.drawString(Integer.toString(boxes[j],10),i,200);

      //player2のゴール
      g.drawString(Integer.toString(boxes[13],10),70,400);

      
      //player番号を表示
      Font font2 = new Font("創英角",Font.ITALIC,30);
      g.setColor(new Color(11,130,255));
      g.setFont(font2);
      g.drawString("Player:" +Integer.toString(player,10),550, 420);

    
      //駒を描写
      //player2のゴールを描写。
      int x = 55;                                                                         //駒を表示していく。駒が
      int y = 65;                                                                         //１つの枠における駒は最
      int haba = 0;
      int koma = boxes[13];
      if(koma >= 15){
        g.drawImage(img2,x,y+haba,30,30,this);
          haba += 40;
        g.drawImage(img2,x,y+haba,30,30,this);
          haba += 40;
        g.drawImage(img2,x,y+haba,30,30,this);
          haba += 40;
          koma -= 15;
      }
      if(koma >= 10){
          g.drawImage(img2,x,y+haba,30,30,this);
          haba += 40;
          g.drawImage(img2,x,y+haba,30,30,this);
          haba += 40;
          koma -= 10;
      }
      if(koma >= 5){
          g.drawImage(img2,x,y+haba,30,30,this);
          haba += 40;
          koma -= 5;
      }
      for(int i = 0; i < koma; ++i){
          if(haba >= 320){
            x += 50; 
            haba = 0; 
        }
          g.drawImage(img1,x,y+haba,30,30,this);
          haba += 40;
      }

      //box[0]から[5]までを表示
      x = 200+5;
      y = 260+5;
      for(int i = 0; i <= 5; ++i){
        int tmp = x; 
        haba = 0;
        koma = boxes[i];
        if(koma >= 15){
            g.drawImage(img2,x,y+haba,30,30,this);
            haba += 40;
            g.drawImage(img2,x,y+haba,30,30,this);
            haba += 40;
            g.drawImage(img2,x,y+haba,30,30,this);
            haba += 40;
            koma -= 15;
        }
        if(koma >= 10){
            g.drawImage(img2,x,y+haba,30,30,this);
            haba += 40;
            g.drawImage(img2,x,y+haba,30,30,this);
            haba += 40;
            koma -= 10;
        }
        if(koma >= 5){
            g.drawImage(img2,x,y+haba,30,30,this);
            haba += 40;
            koma -= 5;
        }
        for(int k = 0; k < koma; ++k){
            if(haba >= 120){
                x += 50; 
                haba = 0; 
            }
            g.drawImage(img1,x,y+haba,30,30,this);
            haba += 40;
        }
        x = tmp;
        x += 150;
    }

    //player1のゴールの駒を描写
      x = 1105; 
      y = 65;
      haba = 0;
      koma = boxes[6];
      if(koma >= 15){
        g.drawImage(img2,x,y+haba,30,30,this);
          haba += 40;
        g.drawImage(img2,x,y+haba,30,30,this);
          haba += 40;
        g.drawImage(img2,x,y+haba,30,30,this);
          haba += 40;
          koma -= 15;
      }
      if(koma >= 10){
          g.drawImage(img2,x,y+haba,30,30,this);
          haba += 40;
          g.drawImage(img2,x,y+haba,30,30,this);
          haba += 40;
          koma -= 10;
      }
      if(koma >= 5){
          g.drawImage(img2,x,y+haba,30,30,this);
          haba += 40;
          koma -= 5;
      }
      for(int i = 0; i < koma; ++i){
          if(haba >= 320){
            x += 50; 
            haba = 0; 
        }
          g.drawImage(img1,x,y+haba,30,30,this);
          haba += 40;
      }

      //box[7]から[12]までを表示
      x = 950+5;
      y = 60+5;
      for(int i = 7; i <= 12; ++i){
        int tmp = x; 
        haba = 0;
        koma = boxes[i];
        if(koma >= 15){
            g.drawImage(img2,x,y+haba,30,30,this);
            haba += 40;
            g.drawImage(img2,x,y+haba,30,30,this);
            haba += 40;
            g.drawImage(img2,x,y+haba,30,30,this);
            haba += 40;
            koma -= 15;
        }
        if(koma >= 10){
            g.drawImage(img2,x,y+haba,30,30,this);
            haba += 40;
            g.drawImage(img2,x,y+haba,30,30,this);
            haba += 40;
            koma -= 10;
        }
        if(koma >= 5){
            g.drawImage(img2,x,y+haba,30,30,this);
            haba += 40;
            koma -= 5;
        }
        for(int k = 0; k < koma; ++k){
            if(haba >= 120){ x += 50; haba = 0; }
            g.drawImage(img1,x,y+haba,30,30,this);
            haba += 40;
        }
        x = tmp;
        x -= 150;
    }
  }

  //終了時
  public void end() {                                                                 
      if(boxes[6] > boxes[13]){
          cp.ChangeLayer(new ResultPanel(cp, 1, boxes[6], boxes[13]));
      }else if(boxes[6] < boxes[13]){
          cp.ChangeLayer(new ResultPanel(cp, 2, boxes[6], boxes[13]));
      }else{
          cp.ChangeLayer(new ResultPanel(cp, 0, boxes[6], boxes[13]));
      }
    }
}
 
 