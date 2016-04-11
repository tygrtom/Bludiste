//Renderer V1.1.1
package bludiste;

import bludiste.BludisteController.Coin;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Renderer extends Thread{
    public Canvas CanFront;
    public Canvas CanWall;
    public Canvas CanRes;
    public Canvas CanMask;
    public GraphicsContext graphicsFront;
    public GraphicsContext graphicsWall;
    public GraphicsContext graphicsRes;
    public GraphicsContext graphicsMask;
    public boolean[] isRendered;
    public int velikostY;
    public int velikostX;
    public int mezera;
    public BludisteController Bludiste;
    byte[][] polealt;
    public Renderer(Canvas CFront,Canvas CWall,Canvas CRes,Canvas CMask,GraphicsContext Front,GraphicsContext Wall,GraphicsContext Res,GraphicsContext Mask,boolean[] isRendered,int velikostX,int velikostY,int mezera,BludisteController Bludiste){
    CanFront=CFront;CanWall=CWall;CanRes=CRes;CanMask=CMask;graphicsFront=Front;graphicsWall=Wall;graphicsRes=Res;graphicsMask=Mask;        
    this.isRendered=isRendered;this.velikostY=velikostY;this.velikostX=velikostX;this.mezera=mezera;this.Bludiste=Bludiste;}
    @Override
    public void run(){
    }
    
public void vykresli(){
if(isRendered[5]){graphicsRes.clearRect(0, 0, CanRes.getWidth(), CanRes.getHeight());}
if(isRendered[3]){graphicsWall.clearRect(0, 0, CanWall.getWidth(), CanWall.getHeight());}
graphicsFront.clearRect(0, 0, CanFront.getWidth(), CanFront.getHeight());
for (int j = 0; j < Bludiste.pole[0].length; j++){for (int i = 0; i < Bludiste.pole.length; i++){vykresli(i,j);}}
VykresliMasku(Bludiste.player.coord.x,Bludiste.player.coord.y,Bludiste.DarkMaskPolomer);}
public void vykresli(int i,int j){
    if (Bludiste.pole[i][j] == 1){if(isRendered[0]){VykresliCesta(i,j);       }}else
    if (Bludiste.pole[i][j] == 2){if(isRendered[1]){VykresliHrac(i,j);        }}else
    if (Bludiste.pole[i][j] == 3){if(isRendered[2]){VykresliCil(i,j);         }}else
    if (Bludiste.pole[i][j] == 4){if(isRendered[3]){VykresliZdi(i,j);         }}else
    if (Bludiste.pole[i][j] == 5){if(isRendered[4]){VykresliZdiDebug(i,j);    }}else
    if (Bludiste.pole[i][j] == 6){if(isRendered[5]){VykresliCestaVyres(i,j);  }}else
    if (Bludiste.pole[i][j] == 7){if(isRendered[6]){VykresliStopa(i,j);       }}else
    if (Bludiste.pole[i][j] == 8){if(isRendered[7]){VykresliCoin(i,j);        }}else
    if (Bludiste.pole[i][j] == 9){if(isRendered[3]){VykresliSpikes(i,j);      }}else//---Spikes/Walls
                                 {if(isRendered[9]){VykresliErrDebug(i,j);    }}
}
public void vykresli(int i,int j,boolean render){
    if (Bludiste.pole[i][j] == 1){if(render){VykresliCesta(i,j);       }}else
    if (Bludiste.pole[i][j] == 2){if(render){VykresliHrac(i,j);        }}else
    if (Bludiste.pole[i][j] == 3){if(render){VykresliCil(i,j);         }}else
    if (Bludiste.pole[i][j] == 4){if(render){VykresliZdi(i,j);         }}else
    if (Bludiste.pole[i][j] == 5){if(render){VykresliZdiDebug(i,j);    }}else
    if (Bludiste.pole[i][j] == 6){if(render){VykresliCestaVyres(i,j);  }}else
    if (Bludiste.pole[i][j] == 7){if(render){VykresliStopa(i,j);       }}else
    if (Bludiste.pole[i][j] == 8){if(render){VykresliCoin(i,j);        }}else
    if (Bludiste.pole[i][j] == 9){if(render){VykresliSpikes(i,j);      }}else
                                 {if(render){VykresliErrDebug(i,j);    }}
}
// <editor-fold defaultstate="collapsed" desc="Kreslení">
public void VykresliCesta(int i,int j){
    if(Bludiste.ColorCesta.getOpacity() != 0){
    graphicsFront.setFill(Bludiste.ColorCesta);
    graphicsFront.fillRect(i * (velikostX + mezera), j * (velikostY + mezera), velikostX, velikostY);
    }}
public void VykresliHrac(int i,int j){VykresliStopa(i,j);
    if(Bludiste.BoolGameMode){graphicsFront.drawImage(BludisteController.Player,i * (velikostX + mezera), j * (velikostY + mezera), velikostX, velikostY);}
    else{graphicsFront.setFill(Color.GREEN);
    graphicsFront.fillRect(i * (velikostX + mezera), j * (velikostY + mezera), velikostX, velikostY);}
}
public void VykresliCil(int i,int j){
    if(Bludiste.BoolGameMode){graphicsFront.drawImage(BludisteController.End,i * (velikostX + mezera), j * (velikostY + mezera), velikostX, velikostY);}
    else{graphicsFront.setFill(Color.RED);
    graphicsFront.fillRect(i * (velikostX + mezera), j * (velikostY + mezera), velikostX, velikostY);}
}
public void VykresliZdi(int i,int j){
    if(Bludiste.BoolGameMode){graphicsWall.drawImage(BludisteController.Wall,i * (velikostX + mezera), j * (velikostY + mezera), velikostX, velikostY);}
    else{
    graphicsWall.setFill(Color.BLACK);
    graphicsWall.fillRect(i * (velikostX + mezera), j * (velikostY + mezera), velikostX, velikostY);}
}
public void VykresliZdiDebug(int i,int j){graphicsFront.setFill(Bludiste.ColorZdiDebug);
    graphicsFront.fillRect(i * (velikostX + mezera), j * (velikostY + mezera), velikostX, velikostY);}
public void VykresliCestaVyres(int i,int j){if(Bludiste.ColorSolve.getOpacity() != 0){
    graphicsRes.setFill(Bludiste.ColorSolve);
    graphicsRes.fillRect(i * (velikostX + mezera), j * (velikostY + mezera), velikostX, velikostY);}}
public void VykresliStopa(int i,int j){if(Bludiste.ColorTrail.getOpacity() != 0){
    graphicsFront.setFill(Bludiste.ColorTrail);
    graphicsFront.fillRect(i * (velikostX + mezera), j * (velikostY + mezera), velikostX, velikostY);}}
public void VykresliCoin(int i,int j){VykresliCesta(i,j);if(!Bludiste.BoolGameMode){Bludiste.LogTXT.add("CoinRenderTryNotGame");}
    for(Coin c : Bludiste.coiny){if(c.isOnCoord(i, j)){
    graphicsFront.drawImage(c.img,i * (velikostX + mezera), j * (velikostY + mezera), velikostX, velikostY);
    graphicsFront.setFont(Font.font("Purisa", velikostY/2));graphicsFront.setFill(Color.BLACK);graphicsFront.setStroke(Color.BLACK);
    graphicsFront.fillText(""+c.hodnota, (i+0.1) * (velikostX + mezera),  (j+0.9) * (velikostY + mezera), velikostX-1);
    }}}
public void VykresliSpikes(int i,int j){if(!Bludiste.BoolGameMode){Bludiste.LogTXT.add("SpikesRenderTryNotGame");}
if(Bludiste.Vyreseno){VykresliCestaVyres(i,j);}else{VykresliCesta(i,j);}
graphicsWall.drawImage(BludisteController.Spikes[(int)Bludiste.poleTrans[i][j]],i * (velikostX + mezera), j * (velikostY + mezera), velikostX, velikostY);
}
public void VykresliErrDebug(int i,int j){
    graphicsFront.setFill(Bludiste.ColorErrDebug);graphicsFront.fillRect(i * (velikostX + mezera), j * (velikostY + mezera), velikostX, velikostY);}
public void VykresliMasku(int ii, int ij,int polomer){
    double j = ij; double i = ii;
graphicsMask.clearRect(0, 0, CanMask.getWidth(), CanMask.getHeight());
if(Bludiste.isDarkMask){
graphicsMask.setFill(Color.BLACK);
graphicsMask.fillRect(0, 0, CanMask.getWidth(), CanMask.getHeight());
graphicsMask.clearRect((i-polomer/2.0+0.55) * (velikostX + mezera), (j-polomer/2.0+0.55) * (velikostY + mezera), velikostX*(polomer-0.1), velikostY*(polomer-0.1));
graphicsMask.drawImage(BludisteController.DarkMask,(i-polomer/2.0+0.40) * (velikostX + mezera), (j-polomer/2.0+0.40) * (velikostY + mezera), velikostX*(polomer+0.15), velikostY*(polomer+0.15));
}
if(Bludiste.isFogMask){
graphicsMask.drawImage(BludisteController.FogMask,0, 0, CanMask.getWidth(), CanMask.getHeight());    
}
}
// </editor-fold>
public void vykresliOkoli(int i, int j, int pol, boolean render){
for(int x=i-pol;x<=i+pol;x++){for(int y=j-pol;y<=j+pol;y++){
    if(x < 0 || y<0 || x>=Bludiste.pole.length || y >= Bludiste.pole[0].length){continue;}
    vykresli(x,y,render);}}}
public void vykresliOkoli(int i, int j, int pol){
for(int x=i-pol;x<=i+pol;x++){for(int y=j-pol;y<=j+pol;y++){
    if(x < 0 || y<0 || x>=Bludiste.pole.length || y >= Bludiste.pole[0].length){continue;}
    vykresli(x,y);}}}
public void vykresliOkoli(int i, int j, int pol, boolean render,byte[][] pole){
for(int x=i-pol;x<=i+pol;x++){for(int y=j-pol;y<=j+pol;y++){
    if(x < 0 || y<0 || x>=pole.length || y >=pole[0].length){continue;}
    vykresli(x,y,render,pole);}}}
public void vykresliOkoli(int i, int j, int pol,byte[][] pole){
for(int x=i-pol;x<=i+pol;x++){for(int y=j-pol;y<=j+pol;y++){
    if(x < 0 || y<0 || x>=pole.length || y >= pole[0].length){continue;}
    vykresli(x,y,pole);}}}
public void vykresli(byte[][] pole){
if(isRendered[5]){graphicsRes.clearRect(0, 0, CanRes.getWidth(), CanRes.getHeight());}
if(isRendered[3]){graphicsWall.clearRect(0, 0, CanWall.getWidth(), CanWall.getHeight());}
graphicsFront.clearRect(0, 0, CanFront.getWidth(), CanFront.getHeight());
for (int j = 0; j < pole[0].length; j++){for (int i = 0; i < pole.length; i++){vykresli(i,j,pole);}}
VykresliMasku(Bludiste.player.coord.x,Bludiste.player.coord.y,Bludiste.DarkMaskPolomer);}
public void vykresli(int i,int j,byte[][] pole){
    if (pole[i][j] == 1){if(isRendered[0]){VykresliCesta(i,j);       }}else
    if (pole[i][j] == 2){if(isRendered[1]){VykresliHrac(i,j);        }}else
    if (pole[i][j] == 3){if(isRendered[2]){VykresliCil(i,j);         }}else
    if (pole[i][j] == 4){if(isRendered[3]){VykresliZdi(i,j);         }}else
    if (pole[i][j] == 5){if(isRendered[4]){VykresliZdiDebug(i,j);    }}else
    if (pole[i][j] == 6){if(isRendered[5]){VykresliCestaVyres(i,j);  }}else
    if (pole[i][j] == 7){if(isRendered[6]){VykresliStopa(i,j);       }}else
    if (pole[i][j] == 8){if(isRendered[7]){VykresliCoin(i,j);        }}else
    if (pole[i][j] == 9){if(isRendered[3]){VykresliSpikes(i,j);      }}else//---Spikes/Walls
                                 {if(isRendered[9]){VykresliErrDebug(i,j);    }}
}
public void vykresli(int i,int j,boolean render,byte[][] pole){
    if (pole[i][j] == 1){if(render){VykresliCesta(i,j);       }}else
    if (pole[i][j] == 2){if(render){VykresliHrac(i,j);        }}else
    if (pole[i][j] == 3){if(render){VykresliCil(i,j);         }}else
    if (pole[i][j] == 4){if(render){VykresliZdi(i,j);         }}else
    if (pole[i][j] == 5){if(render){VykresliZdiDebug(i,j);    }}else
    if (pole[i][j] == 6){if(render){VykresliCestaVyres(i,j);  }}else
    if (pole[i][j] == 7){if(render){VykresliStopa(i,j);       }}else
    if (pole[i][j] == 8){if(render){VykresliCoin(i,j);        }}else
    if (pole[i][j] == 9){if(render){VykresliSpikes(i,j);      }}else
                        {if(render){VykresliErrDebug(i,j);    }}
}
}
