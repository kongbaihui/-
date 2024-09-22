
public class Chess {


    /*
    判断是否胜利
    */
    public static boolean WinLose(int[][] SaveGame,int x,int y) {

        //输赢
        boolean flag = false;

        //相连数
        int count = 1;

        //记录棋子颜色
        int color = SaveGame[x][y];

        //判断横向棋子是否相连
        int i = 1;

        while (x + i < 15 && color == SaveGame[x + i][y]) {

            count++;

            i++;

            if(x+i<0||x+i>=15||y<0||y>=15){
                break;
            }
        }

        i = 1;

        while ( x - i >= 0 && color == SaveGame[x - i][y]) {

            count++;

            i++;

            if(x-i<0||x-i>=15||y<0||y>=15){
                break;
            }
        }
        if (count >= 5) {

            flag = true;

        }



        //判断纵向棋子是否相连
        count = 1;

        i = 1;

        while (y + i < 15 && color == SaveGame[x][y + i] ) {

            count++;

            i++;

            if(x<0||x>=15||y+i<0||y+i>=15){
                break;
            }
        }

        i = 1;

        while (y - i >= 0 && color == SaveGame[x][y - i]) {

            count++;

            i++;

            if(x<0||x>=15||y-i<0||y-i>=15){
                break;
            }
        }
        if (count >= 5) {

            flag = true;

        }



        //判断斜向棋子是否相连（\）

        count = 1;

        i = 1;

        while (x - i >= 0 && y - i >= 0&&color == SaveGame[x - i][y - i]) {

            count++;

            i++;

            if(x-i<0||x-i>=15||y-i<0||y-i>=15){
                break;
            }
        }

        i = 1;

        while (x + i < 15 && y + i < 15 && color == SaveGame[x + i][y + i]) {

            count++;

            i++;

            if(x+i<0||x+i>=15||y+i<0||y+i>=15){
                break;
            }
        }
        if (count >= 5) {

            flag = true;

        }



        //判断斜向棋子是否相连（/）

        count = 1;

        i = 1;

        while (x + i < 15 && y - i >= 0 && color == SaveGame[x + i][y - i]) {

            count++;

            i++;

            if(x+i<0||x+i>=15||y-i<0||y-i>=15){
                break;
            }
        }

        i = 1;

        while (x - i >= 0&& y + i < 15 && color == SaveGame[x - i][y + i]) {

            count++;

            i++;

            if(x-i<0||x-i>=15||y+i<0||y+i>=15){
                break;
            }
        }
        if (count >= 5) {

            flag = true;

        }

        return flag;
    }

}
