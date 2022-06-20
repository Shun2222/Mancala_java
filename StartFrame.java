import java.awt.Color;
import java.awt.Graphics;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class FrameController extends JFrame {
    public JFrame frame;
        private JButton b;
    private JPanel p;
    private ButtonListener bl;
    public FrameController(){
      frame = new JFrame();
      frame.setSize(800, 450);
      frame.setBackground(Color.black);
      frame.setTitle("StartFrame");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
      b  = new JButton("Game Start");
      bl = new ButtonListener();
      b.addActionListener(bl);
      b.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 32));
      p = new JPanel();
      p.add(b);
      frame.add(p);
    }
        class ButtonListener implements ActionListener {
      public void actionPerformed(ActionEvent e){  
      p.removeAll(); 
      frame.repaint();
        new ResultFrame(frame);
        frame.repaint();
      }
    }
  	public void change(JPanel panel) {

		  getContentPane().removeAll();
	  	this.add(panel);
  		validate();
	  	repaint();
	  }
    public static void main(String argv[]) {
        new FrameController();
        
    }
}
class StartFrame extends JFrame{
    private JButton b;
    private JPanel p;
    private ButtonListener bl;
    public JFrame frame;
    public StartFrame(JFrame f) {
      frame = f;
      b  = new JButton("Game Start");
      bl = new ButtonListener();
      b.addActionListener(bl);
      b.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 32));
      p = new JPanel();
      p.add(b);
      frame.add(p);
    }
    class ButtonListener implements ActionListener {
      public void actionPerformed(ActionEvent e){  
      getContentPane().removeAll();
        new ResultFrame(frame);
      }
    }
}
class ResultFrame extends JFrame{
    private JButton b;
    private JPanel p;
    private ButtonListener bl;
    public JFrame frame;
    public ResultFrame(JFrame f) {
      frame = f;
      b  = new JButton("Back to start");
      bl = new ButtonListener();
      b.addActionListener(bl);
      b.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 32));
      p = new JPanel();
      p.add(b);
      frame.add(p);
      p.repaint();
    }
    class ButtonListener implements ActionListener {
      public void actionPerformed(ActionEvent e){  
      p.removeAll(); 
      p.repaint();
        new StartFrame(frame);
      }
    }
}
