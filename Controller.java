class Controller implements MouseListener{
    Model model; 
    public DrawController(Model a) {
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
        if( num!=-1 ) model.func(num);
    }
    public void mouseClicked(MouseEvent e) { }
    public void mouseReleased(MouseEvent e){ }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e)  { }
}
