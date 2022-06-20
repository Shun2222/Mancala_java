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