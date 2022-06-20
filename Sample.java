import java.awt.Color;

import java.awt.Dimension;

import java.awt.event.ActionEvent;

import java.util.Random;



import javax.swing.AbstractAction;

import javax.swing.JButton;

import javax.swing.JDialog;

import javax.swing.JFrame;

import javax.swing.JPanel;







public class Sample {



 public static void main(String[] args) {



 final Random r = new Random( ) ; // パネル背景の色を適当に作成するためのランダムインスタンス



 // 下側のパネル

 final JPanel panel_base = new JPanel( ) ;

 panel_base.setBackground( Color.black ) ;// わかりやすいように黒描画

 panel_base.setPreferredSize( new Dimension( 400, 600 ) ) ;// 適当な大きさにセット



 // 透過する上のパネル

 final JPanel panel_upper = new JPanel( ) ;

 panel_upper.setOpaque( false ) ;

 panel_upper.setBackground( Color.black ) ;

 panel_upper.setPreferredSize( new Dimension( 200, 200 ) ) ;// 適当な大きさにセット



 // テスト用ボタンを上パネルに追加

 JButton btn = new JButton( new AbstractAction( "下パネルの色変更" ) {

 private static final long serialVersionUID = 1L ;

 public void actionPerformed( ActionEvent e ) {

 // 下側のパネルの色を適当に変更

 panel_base.setBackground( new Color( r.nextInt(255), r.nextInt(255), r.nextInt(255) ) ) ;

 }

 } ) ;

 panel_upper.add( btn ) ;



 // テスト用ボタンを上パネルに追加

 JButton btn2 = new JButton( new AbstractAction( "上パネルのOpaque切り替え" ) {

 private static final long serialVersionUID = 1L ;

 public void actionPerformed( ActionEvent e ) {

 panel_upper.setOpaque( !panel_upper.isOpaque( ) ) ;

 panel_upper.repaint( ) ;

 }

 } ) ;

 panel_upper.add( btn2 ) ;



 // パネルを重ねる？

 panel_base.add( panel_upper ) ;



 // パネルの親にするフレーム

 JFrame frame = new JFrame( "test" ) ;

 // フレームにパネルをセット

 frame.getContentPane( ).add( panel_base ) ;

 frame.pack( ) ;



 frame.setVisible( true ) ;

 }

}