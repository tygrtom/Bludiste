//BludisteController V1.1.1
package bludiste;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.logging.Level;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class BludisteController implements Initializable {
       // <editor-fold defaultstate="collapsed" desc="Promenne">
    // <editor-fold defaultstate="collapsed" desc="Components">
    @FXML
    public Button Generuj;    
    @FXML
    public TextField TFpoleX;
    @FXML
    public TextField TFpoleY;
    @FXML
    public TextField TFvelikostX;
    @FXML
    public TextField TFvelikostY;
    @FXML
    public TextField TFmezera;
    @FXML
    public Canvas CanFront;
    @FXML
    public Canvas CanWall;
    @FXML
    public Canvas CanRes;
    @FXML
    public Canvas CanMask;
    @FXML
    public ImageView IVBack;
    @FXML
    public Label LabelKol;
    @FXML
    public Label LabelTime;
    @FXML
    public Label LabelCoin;
    @FXML
    public Label LabelMask;
    @FXML
    public Label LabelPath;
    @FXML
    public AnchorPane AnchorPane;
    @FXML
    public ComboBox CBgenerace;
    @FXML
    public ComboBox CBreseni;
    // </editor-fold>
    public byte[][] pole;
    public byte[][] poleTrans;
    public byte[][] poleRandom;
    int sirkaX;
    int vyskaY;
    Player player;
    Coin[] coiny;
    Runtime rt = Runtime.getRuntime();
    // <editor-fold defaultstate="collapsed" desc="Param">
    int poleX;int poleY;int velikostX;int velikostY;int mezera;int typG;int typR; int ID = 0;
    String FSS = System.getProperty("file.separator");;
    int Kol; int coinu = 0;Coord PlayerPos;
    int SpikesCount = 0;boolean isFogMask; boolean isDarkMask;
    boolean EndSpawned;int DarkMaskPolomer;boolean Vyreseno = false; boolean DFSsolve = true;
    Timer timer;int timersec=0;int timermin=0;int timerhr=0;boolean AnimRun = false;
    int[] XspikesAnim;int[] YspikesAnim;int[] PuvodnispikesAnim; double speed = 0.20;
    // </editor-fold>
    public Stopwatch perf;
    public Stopwatch run;
    
    GraphicsContext graphicsFront;
    GraphicsContext graphicsWall;
    GraphicsContext graphicsRes;
    GraphicsContext graphicsMask;
    boolean[] isRendered = new boolean[10];
    Renderer renderer;
    Generator generator;
    // <editor-fold defaultstate="collapsed" desc="Sprites">
    static Image Wall;
    static Image DarkMask;
    static Image FogMask;
    static Image Background;
    static Image[] Walls;
    static Image[] Backgrounds;
    static Image[] Players;
    static Image[] Ends;
    static Image[] CoinStars;
    static Image[] CoinCoins;
    static Image[] Spikes;
    static Image Player;
    static Image End;
    // </editor-fold>
    boolean AllNum;
    public Timeline timeLoop;
    public Timeline animLoop;
    // </editor-fold>
       // <editor-fold defaultstate="collapsed" desc="Logger">
   private Logger Log;
   String[][] Head;double CreationTime=0;double RunningTime=0;double InitializeTime=0;
   ArrayList<String> Errors=    new ArrayList<String>(0);
   ArrayList<String[][]> Maze=  new ArrayList<String[][]>(0);
   ArrayList<String[][]> Solve= new ArrayList<String[][]>(0);
   public void SetLog(){Log=new Logger("Log"+CreatedTime+".txt","Logger",FSS);Log.setLog(GetLog(),1,Maze.size(),Solve.size());}
   public String[][][] GetLog(){String[][][] Log = new String[1+Maze.size()+Solve.size()][][];Log[0]=LogSetHead();for(int i=1;i<1+Maze.size();i++){Log[i]=getArray3(Maze)[i-1];}for(int i=1+Maze.size();i<1+Maze.size()+Solve.size();i++){Log[i]=getArray3(Solve)[i-1-Maze.size()];}return Log;}
   public void LogSetHeadSize(){Head=new String[5][];Head[0]=new String[5];Head[1]=new String[6];Head[2]=new String[21];Head[3]=new String[3];Head[4]=new String[Errors.size()];}
   public String[][] LogSetHead(){
       String[][] HeadS={   {"Bludiště "+CHNGLG.Version,Logger.Version,CreationTime+"",RunningTime+"",InitializeTime+""},
                            {System.getProperty("java.home"),System.getProperty("java.version"),System.getProperty("os.arch"),System.getProperty("os.name"),System.getProperty("os.version"),System.getProperty("sun.java2d.opengl")},
                            {ConfigFileName,IntMvngSpksRnd+"",DoubleMaxSpeed+"",IntStartingRnd+"",BoolShowPath+"",BoolGameMode+"",BoolMazaniRes+"",BoolMazaniStopy+"",BoolKroky+"",BoolAnimace+"",BoolPostupRes+"",BoolCilUnlocked+"",BoolSpikesEnabled+"",BoolDarkMask+"",BoolFogMask+"",ColorCesta.toString(),ColorErrDebug.toString(),ColorZdiDebug.toString(),ColorSolve.toString(),ColorTrail.toString(),BoolFullScream+""},
                            {rt.totalMemory()/1024+"K",rt.maxMemory()/1024+"K",rt.freeMemory()/1024+"K"},
                            getArray1(Errors)};return HeadS;}
   public String[] getArray1(ArrayList<String> Array)           {String[] Arr = new String[Array.size()];for(int i =0;i<Array.size();i++){Arr[i]=Array.get(i);}return Arr;}
   public String[][] getArray2(ArrayList<String[]> Array)       {String[][] Arr = new String[Array.size()][];for(int i =0;i<Array.size();i++){Arr[i]=getArray1(getArrayL1(Array.get(i)));}return Arr;}
   public String[][][] getArray3(ArrayList<String[][]> Array)   {String[][][] Arr = new String[Array.size()][][];for(int i =0;i<Array.size();i++){Arr[i]=getArray2(getArrayL2(Array.get(i)));}return Arr;}
   public ArrayList<String> getArrayL1(String[] Array)          {ArrayList<String> Arr = new ArrayList<>();for(int i =0;i<Array.length;i++){Arr.add(Array[i]);}return Arr;}
   public ArrayList<String[]> getArrayL2(String[][] Array)      {ArrayList<String[]> Arr = new ArrayList<>();for(int i =0;i<Array.length;i++){Arr.add(Array[i]);}return Arr;}
   public ArrayList<String[][]> getArrayL3(String[][][] Array)  {ArrayList<String[][]> Arr = new ArrayList<>();for(int i =0;i<Array.length;i++){Arr.add(Array[i]);}return Arr;}
    // </editor-fold>
       // <editor-fold defaultstate="collapsed" desc="CONFIG">
   String       ConfigFileName;
   final        SimpleDateFormat sdf    = new SimpleDateFormat("ddMMHHMMSS");
   String       CreatedTime             = sdf.format(new Date());
   final        String[] ConfigTry      = {"CONFIG.txt","config.txt","Config.txt","Bludiste"+System.getProperty("file.separator")+"CONFIG.txt","Bludiste"+System.getProperty("file.separator")+"Config.txt","Bludiste"+System.getProperty("file.separator")+"config.txt"};
   String[]     CoinMsg                 = {"371","136","363","ALEŠ","Whoops"};
   String[]     SpriteFilesTry          = {"","Sprites","sprites"};
   int          IntStartingRnd          = 0;
   int          IntMvngSpksRnd          = 25;
   double       DoubleMaxSpeed          = 1.0;
   boolean      BoolShowPath            = true;
   boolean      BoolGameMode            = true;
   boolean      BoolMazaniRes           = true;
   boolean      BoolMazaniStopy         = true;
   boolean      BoolKroky               = false;
   boolean      BoolPostupRes           = false;
   boolean      BoolAnimace             = true;
   boolean      BoolCilUnlocked         = false;
   boolean      BoolDarkMask            = true;
   boolean      BoolFogMask             = true;
   boolean      BoolSpikesEnabled       = true;
   Color        ColorCesta              = Color.rgb(1, 1, 1, 0);
   Color        ColorErrDebug           = Color.PINK;
   Color        ColorZdiDebug           = Color.ORANGE;
   Color        ColorSolve              = Color.web("#505050CE");
   Color        ColorTrail              = Color.rgb(0, 0, 0, 0);
   int          IntDefaultPoleX         = 30;
   int          IntDefaultPoleY         = 20;
   int          IntDefaultSizeX         = 32;
   int          IntDefaultSizeY         = 32;
   boolean      BoolFullScream          = true;
   boolean      BoolLogOnExit           = true;
   boolean      BoolSmallSprites        = false;
   boolean      BoolErasableTrail       = true;
   int          IntResDelay             = 20;
   public void ConfigSet(){int line = 0;
try
{
CoinMsg           =                     findConfigStrings("CoinMessages");line++;
SpriteFilesTry    =                     findConfigStrings("SpriteFolderTry");line++;
IntMvngSpksRnd    =     Integer.parseInt(findConfigString("MovingSpikesStartRound"));line++;
IntStartingRnd    =     Integer.parseInt(findConfigString("StartingRound"));line++;
DoubleMaxSpeed    =   Double.parseDouble(findConfigString("MovingSpikesMaxSpeed"));line++;
BoolShowPath      = Boolean.parseBoolean(findConfigString("ShowPath"));line++;
BoolGameMode      = Boolean.parseBoolean(findConfigString("GameMode"));line++;
BoolMazaniRes     = Boolean.parseBoolean(findConfigString("SolveErasing"));line++;
BoolMazaniStopy   = Boolean.parseBoolean(findConfigString("TrailErasing"));line++;
BoolKroky         = Boolean.parseBoolean(findConfigString("Steps"));line++;
BoolPostupRes     = Boolean.parseBoolean(findConfigString("SlowSolving"));line++;
BoolAnimace       = Boolean.parseBoolean(findConfigString("AnimatedSpikes"));line++;
BoolCilUnlocked   = Boolean.parseBoolean(findConfigString("EndUnlocked"));line++;
BoolDarkMask      = Boolean.parseBoolean(findConfigString("DarkMask"));line++;
BoolFogMask       = Boolean.parseBoolean(findConfigString("FogMask"));line++;
BoolSpikesEnabled = Boolean.parseBoolean(findConfigString("SpikesEnabled"));line++;
ColorCesta        =            Color.web(findConfigString("ColorRoad"));line++;
ColorErrDebug     =            Color.web(findConfigString("ColorErrDebug"));line++;
ColorZdiDebug     =            Color.web(findConfigString("ColorZdiDebug"));line++;
ColorSolve        =            Color.web(findConfigString("ColorSolve"));line++;
ColorTrail        =            Color.web(findConfigString("ColorTrail"));line++;
IntDefaultPoleX   =     Integer.parseInt(findConfigString("DefaultPoleX:"));line++;
IntDefaultPoleY   =     Integer.parseInt(findConfigString("DefaultPoleY:"));line++;
IntDefaultSizeX   =     Integer.parseInt(findConfigString("DefaultSizeX:"));line++;
IntDefaultSizeY   =     Integer.parseInt(findConfigString("DefaultSizeY:"));line++;
BoolFullScream    = Boolean.parseBoolean(findConfigString("FullScreenStart"));line++;
BoolLogOnExit     = Boolean.parseBoolean(findConfigString("LogOnExit"));line++;
BoolSmallSprites  = Boolean.parseBoolean(findConfigString("SmallSprites"));line++;
BoolErasableTrail = Boolean.parseBoolean(findConfigString("ErasableTrail"));line++;
}catch (Exception e){Errors.add("ConfigReadError"+line);showMessageBox("Chyba při čtení z Configu. Err: "+line);}
}
public BufferedReader getConfigBR(){
int i = 0;
do{int j = 0;do{
        if(getBufferedReader(SpriteFilesTry[i],ConfigTry[j]) != null){
        ConfigFileName=ConfigTry[j];return getBufferedReader(SpriteFilesTry[i],ConfigTry[j]);}
j++;}while(j < ConfigTry.length);i++;}while(i < SpriteFilesTry.length);
Errors.add("ConfigBRnull");return null;}
public String findConfigString(String nazev) throws IOException{
    String ConfString = null;BufferedReader br = getConfigBR();String s;
        while ((s = br.readLine()) != null)
        {
                if(s.contains(nazev) || s.contains(nazev.toLowerCase()) || s.contains(nazev.toUpperCase()))
                {ConfString = s.substring(s.lastIndexOf(":")+1).trim();break;}
        }
if(ConfString == null){throw new IOException();}br.close();
return ConfString;
}
public String[] findConfigStrings(String nazev) throws IOException{return findConfigString(nazev).split(",");}
   // </editor-fold>
public Image getSprite(String slozka,String text){
Image wall = null;
try {wall = new Image(new FileInputStream(new File(System.getProperty("user.dir")+(FSS+slozka+FSS+text))));} catch (Exception e) {wall = null;}
return wall;
}
public BufferedReader getBufferedReader(String slozka,String text){
BufferedReader br;slozka+=FSS;
try {br = new BufferedReader(new FileReader(System.getProperty("user.dir")+(FSS+slozka+text)));} catch (Exception e) {br = null;}
return br;
}
public int getSpriteCount(String slozka,String nazev,String pripona){
    int SpriteCount = 0;Image x = null;
    do{SpriteCount++;x = getSprite(slozka,nazev + SpriteCount + pripona);}while(x!=null);
    return SpriteCount-1;
}
public Image[] getSprites(String slozka,String nazev,String pripona){
int SpriteCount = getSpriteCount(slozka,nazev,pripona);
Image[] wall = new Image[SpriteCount];
for(int i = 1; i <= SpriteCount;i++){
    String text = nazev+i+pripona;
    try {wall[i-1] = getSprite(slozka,text);} catch (Exception e) {wall[i-1] = null;}
}
return wall;
}
public int getAbsNum(TextField x){ int num; 
try{num = Integer.parseInt(x.getText());}catch(Exception e){num = 0;AllNum=false;}
num = Math.abs(num);return num;
}
public void LogAction(){SetLog();}
@FXML
public void GenerujAction(){
        AllNum = true;
        velikostX = getAbsNum(TFvelikostX);
        velikostY = getAbsNum(TFvelikostY);
        mezera = getAbsNum(TFmezera);
        poleX = getAbsNum(TFpoleX);
        poleY = getAbsNum(TFpoleY);
        typG = CBgenerace.getSelectionModel().getSelectedIndex();
        typR = CBreseni.getSelectionModel().getSelectedIndex();
            if(poleX%2 ==0){poleX++;}
            if(poleY%2 ==0){poleY++;}
            if(poleX ==1){poleX+=2;}
            if(poleY ==1){poleY+=2;}
        poleTrans = new byte[poleX][poleY];
        for (int y = 0; y < poleTrans[0].length; y++){for (int x = 0; x < poleTrans.length; x++){poleTrans[x][y]=(byte)Random(0,Spikes.length-1);}}
        poleRandom = new byte[poleX][poleY];
        for (int y = 0; y < poleRandom[0].length; y++){for (int x = 0; x < poleRandom.length; x++){poleRandom[x][y]=(byte)Random(1,4);}}
        pole = new byte[poleX][poleY];
        sirkaX = poleX*(velikostX+mezera);vyskaY = poleY*(velikostY+mezera);
        if(poleX > 1500 || poleY > 1500 || velikostX > 1500 || velikostY > 1500 || mezera > 1500 || sirkaX > 2000 || vyskaY > 2000 ){AllNum = false;}
        if(AllNum){runApp();}
}
@FXML
public void KeyPressed(KeyEvent evt) {                            
        if(evt.getCode() == KeyCode.ESCAPE){Platform.exit();}
        if(evt.getCode() == KeyCode.ENTER){vyres();}
        if(evt.getCode() == KeyCode.R && Vyreseno ){UnSolve();}
}
@FXML
public void PanelKeyPressed(KeyEvent evt) {                            
        KeyPressed(evt);
        if(player!=null){
        if(evt.getCode() == KeyCode.UP || evt.getCode() == KeyCode.W ){player.Nahoru();}
        if(evt.getCode() == KeyCode.DOWN || evt.getCode() == KeyCode.S ){player.Dolu();}
        if(evt.getCode() == KeyCode.LEFT || evt.getCode() == KeyCode.A ){player.Doleva();}
        if(evt.getCode() == KeyCode.RIGHT || evt.getCode() == KeyCode.D ){player.Doprava();}
        if(evt.getCode() == KeyCode.T){player.setTrail(player.trail==(byte)4);}
        if(evt.getCode() == KeyCode.B){player.breaking = !player.breaking;}
        }
}
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ConfigSet();
        nastavSprites();
    perf = new Stopwatch("Perfomance",true);
    
    if(BoolLogOnExit){Bludiste.stage.setOnCloseRequest((WindowEvent we) -> {LogAction();});}
    if(BoolShowPath){LabelPath.setText(System.getProperty("user.dir"));}
    
    Kol = IntStartingRnd-1;
    TFpoleX.setText(String.format("%d",IntDefaultPoleX));
    TFpoleY.setText(String.format("%d",IntDefaultPoleY));
    TFvelikostX.setText(String.format("%d",IntDefaultSizeX));
    TFvelikostY.setText(String.format("%d",IntDefaultSizeY));
    
    graphicsFront = CanFront.getGraphicsContext2D();
    graphicsWall = CanWall.getGraphicsContext2D(); 
    graphicsRes = CanRes.getGraphicsContext2D(); 
    graphicsMask = CanMask.getGraphicsContext2D();
    
    for(int j = 0;j<10;j++){isRendered[j] = true;}
    
    CBgenerace.getItems().addAll("Perfect","Spletené(U/20)","Spletené(1)","DFS","DFSR","Free","RandomPrim","Spletené2","FreeB" );CBgenerace.getSelectionModel().selectFirst();
    CBreseni.getItems().addAll("4xDEF","DEF","DEF2","Wave");CBreseni.getSelectionModel().selectFirst();
    Bludiste.stage.setMaximized(BoolFullScream);
    InitializeTime=perf.measure();perf.StopAll();
    }    
//--------------------------------------------------------------------------------------------------------------------------------------
public void runApp() {
    renderer = new Renderer(CanFront,CanWall,CanRes,CanMask,graphicsFront,graphicsWall,graphicsRes,graphicsMask,isRendered,velikostX,velikostY,mezera,this);
    nastavTextury();
    PlayerPos = new Coord(1,1);
    if(generator!=null){generator=null;}
    generator = new Generator(typG,pole,this,poleX,poleY,renderer);generator.start();
    synchronized(this){while(!generator.done){
        try {this.wait();} catch (Exception ex) {Errors.add("ErrorWaitRun");}}}
    
    if(BoolGameMode){Naplnit();}else{NaplnitNotGame();}
}
// 1-cesta,2-player,3-cil,4-zed,5-zed2,6-reseni,7-stopa,8-penize,9-spikes,10-chyba
public void Generuj(byte[][] pole){this.pole = pole;if(BoolGameMode){Naplnit();}else{NaplnitNotGame();}}
public void NaplnitNotGame(){
/*Player, cíl===================================================================*/
EndSpawned = false;Vyreseno=false;
player = new Player(PlayerPos);player.Spawn(PlayerPos);
while(!EndSpawned){SpawnEnd();}
/*Labely========================================================================*/
Kol++;LabelKol.setText("Bludiště: "+Kol);
if(BoolKroky){LabelTime.setText("Kroky: "+player.kroky);}
LabelMask.setText("");
/*Timer=========================================================================*/
if(timeLoop !=null){timersec = 0;}
if(!BoolKroky && timeLoop == null){startTime();}
/*Vykresleni====================================================================*/
isRendered[3] = true;isRendered[5] = true;
vykresli();
isRendered[3] = false;isRendered[5] = false;
}
public void Naplnit(){
/*Generace penízků==============================================================*/
coiny = Coiny();
/*Player, cíl===================================================================*/
EndSpawned = false;Vyreseno=false;
player = new Player(PlayerPos);player.Spawn(PlayerPos);
if(BoolCilUnlocked){while(!EndSpawned){SpawnEnd();}}
/*Spikes========================================================================*/
if(BoolSpikesEnabled){SpikesCount = Spikes();}
/*Maska=========================================================================*/
int maska = Random(0,1,1); 
if(Random(0,1,2)==1){
    if(maska==0){if(BoolDarkMask){DarkMaskPolomer = Random(2,6)*3 ;isDarkMask = true;}}
    if(maska==1){if(BoolFogMask){DarkMaskPolomer = Random(2,6)*3 ;isFogMask = true;}}}else{isDarkMask = false;isFogMask = false;}
/*Labely========================================================================*/
Kol++;LabelKol.setText("Kolo: "+Kol);
if(BoolKroky){LabelTime.setText("Kroky: "+player.kroky);}
LabelMask.setText("");
if(isDarkMask == true){LabelMask.setText(LabelMask.getText() + String.format("(Dark %d)",DarkMaskPolomer));}
if(isFogMask == true){LabelMask.setText(LabelMask.getText() + String.format("(Fog)"));}
/*Animace spikes================================================================*/
if(Kol > IntMvngSpksRnd && BoolSpikesEnabled && BoolAnimace){
speed = 0.20+((double)(Kol-IntMvngSpksRnd)/150.0);if(speed>DoubleMaxSpeed){speed = DoubleMaxSpeed;}
int maxAnimSpikes = ((poleX-1)*(poleY-1))/10;
int AnimSpikes = 1+(Kol-IntMvngSpksRnd)/25;if(AnimSpikes >= maxAnimSpikes){AnimSpikes = maxAnimSpikes;}
YspikesAnim = new int[AnimSpikes];XspikesAnim = new int[AnimSpikes];PuvodnispikesAnim = new int[AnimSpikes];
if(animLoop!=null){AnimRun = false;animLoop.stop();animLoop = null;}
startAnim();
}
/*Timer=========================================================================*/
if(timeLoop !=null){timersec = 0;}
if(!BoolKroky && timeLoop == null){startTime();}
/*Vykresleni====================================================================*/
isRendered[3] = true;isRendered[5] = true;
vykresli();
isRendered[3] = false;isRendered[5] = false;
}
public Coin[] Coiny(){
Coin[] coiny;String msg = "";
int maxPenez = ((poleX-1)*(poleY-1))/8;
if(poleX >5 && poleY >5){
    int penizku=1+Random(Kol/10,Kol/6);
    if(penizku>=maxPenez){penizku=maxPenez;}
    coiny=new Coin[penizku];
    for(int i = 0;i < penizku;)
    {
        if(i == 0 && Random(0,1,3)==1){msg = CoinMsg[Random(0,CoinMsg.length-1)];}else{msg = null;}
        int x = Random(1,pole.length-1);int y = Random(1,pole[0].length-1);
        if(x == PlayerPos.x && y == PlayerPos.y){continue;}
        if(pole[x][y]==1){pole[x][y]=8;coiny[i]=new Coin(new Coord(x,y),i,CoinValue(Kol,i),msg);i++;}
    }
}
else{coiny=new Coin[0];}
return coiny;
}
public int Spikes(){int spikes = 0;
    int chance = 10-Kol/20;if(chance<=0){chance=1;}
    for (int j = 0; j < pole[0].length; j++){for (int i = 0; i < pole.length; i++){
      if(pole[i][j]==4){if(Random(0,2,chance)!=0){pole[i][j]=9;spikes++;}}  
    }}return spikes;
}
public void vyres(){
typR = CBreseni.getSelectionModel().getSelectedIndex();
if(AnimRun){DeAnimSpikes();AnimRun = false;}
isRendered[5] = true;
if(typR==0){NaplnitVyresit1();}
if(typR==1){NaplnitVyresit2();}
if(typR==2){NaplnitVyresit3();}
if(typR==3){NaplnitVyresit4();}
if(typR==4){NaplnitVyresit5();}
Vyreseno = true;
vykresli();
isRendered[5] = false;
}
// <editor-fold defaultstate="collapsed" desc="Reseni">
//if(BoolPostupRes){renderer.vykresli(i,j,true);try{Thread.sleep(20);}catch(Exception e){Errors.add("ErrorWait");}}
public void NaplnitVyresit1(){
    MazaniResStop();
    int zmen = 1;
    while(zmen !=0){zmen = 0;
for (int j = 1; j < pole[0].length-1; j++){for (int i = 1; i < pole.length-1; i++){
    if(pole[i][j] ==1){if(pocetZdiKolem(i,j) >=3){pole[i][j]=6;zmen++;}}}}
for (int i = 1; i < pole.length-1; i++){for (int j = 1; j < pole[0].length-1; j++){
    if(pole[i][j] ==1){if(pocetZdiKolem(i,j) >=3){pole[i][j]=6;zmen++;}}}}
for (int j = pole[0].length-2; j > 0; j--){for (int i = pole.length-2; i > 0; i--){
    if(pole[i][j] ==1){if(pocetZdiKolem(i,j) >=3){pole[i][j]=6;zmen++;}}}}  
for (int i = pole.length-2; i > 0; i--){for (int j = pole[0].length-2; j > 0; j--){
    if(pole[i][j] ==1){if(pocetZdiKolem(i,j) >=3){pole[i][j]=6;zmen++;}}}} 
}}
public void NaplnitVyresit2(){
    MazaniResStop();int zmen = 1;
    while(zmen !=0){zmen = 0;for (int j = 1; j < pole[0].length-1; j++){for (int i = 1; i < pole.length-1; i++){
    if(pole[i][j] ==1){if(pocetZdiKolem(i,j) >=3){pole[i][j]=6;zmen++;}}}}}}
public void NaplnitVyresit3(){
    MazaniResStop();int zmen = 1;
    while(zmen !=0){zmen = 0;
for (int j = 1; j < pole[0].length-1; j++){for (int i = 1; i < pole.length-1; i++){
    if(pole[i][j]==1){int x = i;int y=j;
        while(pocetZdiKolem(x,y) >=3){pole[x][y]=6;zmen++;
            if(pole[x+1][y]==1){if(pocetZdiKolem(x+1,y)>=3){x++;continue;}}
            if(pole[x-1][y]==1){if(pocetZdiKolem(x-1,y)>=3){x--;continue;}}
            if(pole[x][y+1]==1){if(pocetZdiKolem(x,y+1)>=3){y++;continue;}}
            if(pole[x][y-1]==1){if(pocetZdiKolem(x,y-1)>=3){y--;continue;}}break;}}}}}}
public void NaplnitVyresit4(){
ArrayList<Coord> prekazek = PrekazekMazaniResStop();
    ArrayList<ArrayList<Coord>> ALc = new ArrayList<ArrayList<Coord>>(prekazek.size());//for(ArrayList<Coord> AL : ALc){AL=new ArrayList<Coord>(poleX*poleY/2);}
    int[][] pole3=new int[pole.length][pole[0].length];
    for(Coord cil : prekazek){
            for (int j = 0; j < pole3[0].length; j++){for (int i = 0; i < pole3.length; i++){pole3[i][j]=0;}}
            pole3[player.coord.x][player.coord.y]=1;int val =1;
        while(pole3[cil.x][cil.y]==0){pole3 = WaveChange(pole,pole3,val);val++;}
            for (int j = 0; j < pole3[0].length; j++){for (int i = 0; i < pole3.length; i++){if(pole3[i][j]==val){pole3[i][j]=0;}}}
            pole3[cil.x][cil.y]=val;ALc.add(getWavePath(pole3,val));     
    }
    for (int j = 0; j < pole[0].length; j++){for (int i = 0; i < pole.length; i++){if(pole[i][j]==1){pole[i][j]=6;}}}
    for(ArrayList<Coord> AL : ALc){for(Coord c : AL){if(pole[c.x][c.y]!=8&&pole[c.x][c.y]!=2&&pole[c.x][c.y]!=3){pole[c.x][c.y]=1;}}}
}
public void NaplnitVyresit5(){    
    ArrayList<Coord> prekazek = PrekazekMazaniResStop();
    for(Coord cd : prekazek){DFSsolve=true;DFSRecursionSolve(player.coord.x,player.coord.y,cd);}
    //for (int j = 0; j < pole[0].length; j++){for (int i = 0; i < pole.length; i++){if(pole[i][j]==5){pole[i][j]=1;}}}
}
public void DFSRecursionSolve(int x, int y,Coord end){
     for (int i = 1; i < 5; i++) {int pX=0,pY=0;
         switch(i){
         case 1: pX=-1;pY=0;break;
         case 2: pX=0;pY=1;break;
         case 3: pX=1;pY=0;break;
         case 4: pX=0;pY=-1;break;
         }
         if (x+pX <= 0 || y+pY >= pole[x].length - 1 || x+pX >= pole.length - 1 || y+pY <= 0) {continue;}
         if (pole[x+pX][y+pY] != 4 &&pole[x+pX][y+pY] != 9) {
         if(end.x == x+pX && end.y == y+pY){continue;}
         if (pole[x+pX][y+pY] == 1){pole[x+pX][y+pY] = 6;}
         else 
         if(pole[x+pX][y+pY] == 6){pole[x+pX][y+pY] = 1;}
         DFSRecursionSolve(x+pX, y+pY,end);}
     }
}
public void tempArrayOut(int[][] arr,int mezera){ for (int j = 0; j < arr[0].length; j++){for (int i = 0; i < arr.length; i++){
    System.out.print(arr[i][j]);String arrl = ""+arr[i][j];for(int x = mezera-arrl.length();x>0;x--){System.out.print(" ");}}System.out.println();}}
public int pocetZdiKolem(int i, int j){
int pocetZdiKolem = 0;
        if(pole[i+1][j]==4||pole[i+1][j]==6||pole[i+1][j]==9||pole[i+1][j]==5){pocetZdiKolem++;}
        if(pole[i-1][j]==4||pole[i-1][j]==6||pole[i-1][j]==9||pole[i-1][j]==5){pocetZdiKolem++;}
        if(pole[i][j+1]==4||pole[i][j+1]==6||pole[i][j+1]==9||pole[i][j+1]==5){pocetZdiKolem++;}
        if(pole[i][j-1]==4||pole[i][j-1]==6||pole[i][j-1]==9||pole[i][j-1]==5){pocetZdiKolem++;}
return pocetZdiKolem;}
public int[][] WaveChange(byte[][] pole1,int[][] pole3,int val){
    for (int j = 1; j < pole3[0].length-1; j++){for (int i = 1; i < pole3.length-1; i++){if(pole1[i][j]!=9&&pole1[i][j]!=4&&pole3[i][j]==val){
        if(pole1[i+1][j]!=2&&pole1[i+1][j]!=4&&pole1[i+1][j]!=9&&pole3[i+1][j]==0){pole3[i+1][j]=pole3[i][j]+1;}
        if(pole1[i-1][j]!=2&&pole1[i-1][j]!=4&&pole1[i-1][j]!=9&&pole3[i-1][j]==0){pole3[i-1][j]=pole3[i][j]+1;}
        if(pole1[i][j+1]!=2&&pole1[i][j+1]!=4&&pole1[i][j+1]!=9&&pole3[i][j+1]==0){pole3[i][j+1]=pole3[i][j]+1;}
        if(pole1[i][j-1]!=2&&pole1[i][j-1]!=4&&pole1[i][j-1]!=9&&pole3[i][j-1]==0){pole3[i][j-1]=pole3[i][j]+1;}
    }}}return pole3;  
}
public ArrayList<Coord> getWavePath(int[][] pole3,int val){
    ArrayList<Coord> CkP = new ArrayList<>(val+1);Coord c=null;
    for (int j = 0; j < pole3[0].length; j++){for (int i = 0; i < pole3.length; i++){if(pole3[i][j]==val){c=new Coord(i,j);}}}
    if(c==null){Errors.add("GetWavePathNull");}CkP.add(c);
    while(pole3[c.x][c.y]!=1){
        if(pole3[c.x+1][c.y]==pole3[c.x][c.y]-1){CkP.add(c);c=new Coord(c.x+1,c.y);}else
        if(pole3[c.x-1][c.y]==pole3[c.x][c.y]-1){CkP.add(c);c=new Coord(c.x-1,c.y);}else
        if(pole3[c.x][c.y+1]==pole3[c.x][c.y]-1){CkP.add(c);c=new Coord(c.x,c.y+1);}else
        if(pole3[c.x][c.y-1]==pole3[c.x][c.y]-1){CkP.add(c);c=new Coord(c.x,c.y-1);}
    }return CkP;}
public void MazaniResStop(){
    for (int j = 0; j < pole[0].length; j++){for (int i = 0; i < pole.length; i++){
        if(BoolMazaniStopy){if(pole[i][j]==7){pole[i][j]=1;}}
        if(BoolMazaniRes){if(pole[i][j]==6){pole[i][j]=1;}}
    }}}
public ArrayList<Coord> PrekazekMazaniResStop(){
        ArrayList<Coord> prekazek = new ArrayList<Coord>(1+Kol/6);
    for (int j = 0; j < pole[0].length; j++){for (int i = 0; i < pole.length; i++){
    if(BoolMazaniStopy){if(pole[i][j]==7){pole[i][j]=1;}}
    if(BoolMazaniRes){if(pole[i][j]==6){pole[i][j]=1;}}
    if(pole[i][j]==3||pole[i][j]==8){prekazek.add(new Coord(i,j));}
    }}return prekazek;
}
public void UnSolve(){
      for (int j = 0; j < pole[0].length; j++){for (int i = 0; i < pole.length; i++){if(pole[i][j]==6){pole[i][j]=1;}}}  
isRendered[5] = true;
Vyreseno = false;
vykresli();
isRendered[5] = false;
}
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Vykreslování">
public void vykresli(){renderer.vykresli();}
public void vykresli(int i,int j){renderer.vykresli(i,j);}
// </editor-fold>
public void nastavTextury(){
CanFront.setHeight(vyskaY);CanFront.setWidth(sirkaX);
CanWall.setHeight(vyskaY);CanWall.setWidth(sirkaX);
CanRes.setHeight(vyskaY);CanRes.setWidth(sirkaX);
CanMask.setHeight(vyskaY);CanMask.setWidth(sirkaX);
IVBack.setFitHeight(vyskaY);IVBack.setFitWidth(sirkaX);
if(BoolGameMode){
Wall = getRandomImage(Walls);
Player = getRandomImage(Players);
Background = getRandomImage(Backgrounds);
End = getRandomImage(Ends);
IVBack.setImage(Background);}
}
public void nastavSprites(){
        int i = 0;
if(!BoolSmallSprites){
do{
    DarkMask =      getSprite(SpriteFilesTry[i],"DarkMask.png");
    FogMask =       getSprite(SpriteFilesTry[i],"FogMask.png");
    Spikes =        getSprites(SpriteFilesTry[i],"Spikes",".png");
    Walls =         getSprites(SpriteFilesTry[i],"Wall",".png");
    Backgrounds =   getSprites(SpriteFilesTry[i],"Background",".png");
    Players =       getSprites(SpriteFilesTry[i],"Player",".png");
    Ends =          getSprites(SpriteFilesTry[i],"End",".png");
    CoinCoins =     getSprites(SpriteFilesTry[i],"CoinCoin",".png");
    CoinStars =     getSprites(SpriteFilesTry[i],"CoinStar",".png");
    if(DarkMask != null &&FogMask != null &&Spikes != null &&Walls != null &&Backgrounds != null &&Players != null &&Ends != null &&CoinCoins != null &&CoinStars != null){break;}
    i++;
}while(i < SpriteFilesTry.length);
}
else{
do{
    DarkMask =      getSprite(SpriteFilesTry[i],"SmallDarkMask.png");
    FogMask =       getSprite(SpriteFilesTry[i],"SmallFogMask.png");
    Spikes =        getSprites(SpriteFilesTry[i],"SmallSpikes",".png");
    Walls =         getSprites(SpriteFilesTry[i],"SmallWall",".png");
    Backgrounds =   getSprites(SpriteFilesTry[i],"SmallBackground",".png");
    Players =       getSprites(SpriteFilesTry[i],"SmallPlayer",".png");
    Ends =          getSprites(SpriteFilesTry[i],"SmallEnd",".png");
    CoinCoins =     getSprites(SpriteFilesTry[i],"SmallCoinCoin",".png");
    CoinStars =     getSprites(SpriteFilesTry[i],"SmallCoinStar",".png");
    if(DarkMask != null &&FogMask != null &&Spikes != null &&Walls != null &&Backgrounds != null &&Players != null &&Ends != null &&CoinCoins != null &&CoinStars != null){break;}
    i++;
}while(i < SpriteFilesTry.length);
}
if(!(DarkMask != null &&FogMask != null &&Spikes != null &&Walls != null &&Backgrounds != null &&Players != null &&Ends != null &&CoinCoins != null &&CoinStars != null)){Errors.add("AllSpritesNull");}
if(!(DarkMask != null ||FogMask != null ||Spikes != null ||Walls != null ||Backgrounds != null ||Players != null ||Ends != null ||CoinCoins != null ||CoinStars != null)){Errors.add("OneSpritesNull");}
}
public static Image getCoinImage(int hodnota){
if(hodnota <=3  ){return CoinCoins[5];}else
if(hodnota <=7  ){return CoinCoins[4];}else
if(hodnota <=15 ){return CoinCoins[3];}else
if(hodnota <=25 ){return CoinCoins[2];}else
if(hodnota <=37 ){return CoinCoins[1];}else
if(hodnota <=50 ){return CoinCoins[0];}else
if(hodnota <=67 ){return CoinStars[2];}else
if(hodnota <=100){return CoinStars[1];}else
                 {return CoinStars[0];}
}
public static Image getRandomImage(Image[] imgs){Image img;
int i = Random(0,imgs.length-1);img = imgs[i];
return img;}
public static int Random(int min, int max){
        int cislo;
        do{
        cislo = (int)Math.round((Math.random()*((max+1)-(min-1)))+(min-1));
        }while(!(cislo<=max&&cislo>=min));
        return cislo;}
public static int Random(int min, int max,int nasob){
        int cislo = Random(min,max);
        for(int i = 1;i<nasob;i++){cislo*=Random(min,max);}
        return cislo;}
public static int CoinValue(int Kol,int i){int Value=Random(1,1+Kol/50);
if(i == 0)      {Value += Random(0,Kol/2)     ;}else
if(i> 0 && i<5) {Value += Random(0,1,4)*Kol/4 ;}else
if(i>=5 && i<10){Value += Random(0,1,3)*Kol/8 ;}else
if(i>=10&& i<15){Value += Random(0,1,2)*Kol/16;}else
                {Value += Random(0,1,1)*Kol/32;}
return Value;}
public void SpawnEnd(){
    int x = 1;int y = 1;int pocetZdiKolem = 4;
    while(pocetZdiKolem >=4){
    pocetZdiKolem = 0;x = Random(1,pole.length-2);y = Random(1,pole[0].length-2);
    if(x == player.coord.x && y== player.coord.y)   {pocetZdiKolem=4;}
    if(pole[x+1][y]==4||pole[x+1][y]==2){pocetZdiKolem++;}
    if(pole[x-1][y]==4||pole[x-1][y]==2){pocetZdiKolem++;}
    if(pole[x][y+1]==4||pole[x][y+1]==2){pocetZdiKolem++;}
    if(pole[x][y-1]==4||pole[x][y-1]==2){pocetZdiKolem++;}
    }
    SpawnEnd(x,y);
}
public void SpawnEnd(int x, int y){
    if(x != player.coord.x && y!= player.coord.y){
    isRendered[3] = true;
    pole[x][y] = 3;
    EndSpawned = true;
    vykresli();
    isRendered[3] = false;}
}
public boolean coinsSebrano(Coin[] coins){if(coins == null){return true;}for(Coin c : coins){if(!c.sebrano){return false;}}return true;}
public void showMessageBox(String msg){
Alert alert = new Alert(AlertType.INFORMATION);
alert.setTitle("InfoBox "+CHNGLG.Version);
alert.setHeaderText(null);
alert.setContentText(msg);
alert.showAndWait();
}

public void startAnim(){
        final Duration oneFrameAmt = Duration.millis(1000 / speed);
        final KeyFrame oneFrame = new KeyFrame(oneFrameAmt, (javafx.event.ActionEvent event) -> {
            if(!Vyreseno){
                if(!AnimRun){AnimSpikes();AnimRun = true;}
                else{DeAnimSpikes();AnimRun = false;}
                isRendered[8] = true;vykresli();isRendered[3] = false;
            }
        });
        animLoop =(TimelineBuilder.create().cycleCount(Animation.INDEFINITE).keyFrames(oneFrame).build());
        animLoop.play();
}
public void startTime() {
        final Duration oneFrameAmt = Duration.millis(1000 / (float)1);
        final KeyFrame oneFrame = new KeyFrame(oneFrameAmt, (javafx.event.ActionEvent event) -> {
            LabelTime.setText("Čas: " + timersec);timersec++;
        });
        timeLoop =(TimelineBuilder.create().cycleCount(Animation.INDEFINITE).keyFrames(oneFrame).build());
        timeLoop.play();
    }
// <editor-fold defaultstate="collapsed" desc="Animace">
public void AnimSpikes(){
for(int i = 0;i < YspikesAnim.length;i++){
int x = Random(1,pole.length-2);
int y = Random(1,pole[0].length-2);
while(pole[x][y]!=1 && pole[x][y]!=7){
x = Random(1,pole.length-2);
y = Random(1,pole[0].length-2);
}
if(pole[x][y]==1){
pole[x][y] = 9;
YspikesAnim[i] = y;
XspikesAnim[i] = x;
PuvodnispikesAnim[i] = 1;
}
if(pole[x][y]==7){
pole[x][y] = 9;
YspikesAnim[i] = y;
XspikesAnim[i] = x;
PuvodnispikesAnim[i] = 7;
}
}}
public void DeAnimSpikes(){
for(int i = 0; i < YspikesAnim.length;i++){
pole[XspikesAnim[i]][YspikesAnim[i]]=(byte)PuvodnispikesAnim[i];}
}
// </editor-fold>
public void LogMaze(){String Cvalues="";for(Coin c:coiny){Cvalues+=c.hodnota;}
    String[][] maze = {{ID+"",poleX+":"+poleY,velikostX+":"+velikostY,mezera+"",Kol+""},
                       {typR+"",""},{coiny.length+"",Cvalues},
                       {player.coord.x+":"+player.coord.y},
                       {SpikesCount+""},
                       {DarkMask+"",FogMask+""},
                       {"","",""},
                       {""},
                       {"","","","","","",""}};
    Maze.add(maze);
}
public class Player {
public Coord coord;public int kroky;public byte trail;public boolean breaking = false;
public Player(Coord pp){
this.coord = pp;kroky = 0;pole[coord.x][coord.y]= 2;setTrail(BoolErasableTrail);
}
public void setTrail(boolean erasable){if(erasable){trail=(byte)7;}else{trail=(byte)4;}}
public void Doleva()    {doStep(-1,0);}
public void Doprava()   {doStep(1,0);}
public void Nahoru()    {doStep(0,-1);}
public void Dolu()      {doStep(0,1);}
public void doStep(int pX, int pY){if(coord.x+pX < 0 || coord.y+pY<0 || coord.x+pX>pole.length || coord.y+pY > pole[0].length){return;}
    kroky++;if(!EndSpawned){if(coinsSebrano(coiny)){while(!EndSpawned){SpawnEnd();}}}
if(breaking){pole[coord.x][coord.y]=trail;
    coord.y+=pY;coord.x+=pX;pole[coord.x][coord.y] = 2;
    Move();return;}

if(pole[coord.x+pX][coord.y+pY] == 9){Death();Move();}
else
if(pole[coord.x+pX][coord.y+pY] != 4)
{
    if(pole[coord.x+pX][coord.y+pY] == 3){runApp();return;}
    else
    if(pole[coord.x+pX][coord.y+pY] == 8){for(Coin c : coiny){if(c.isOnCoord(coord.x+pX,coord.y+pY)&&!c.sebrano){c.seber();}}}
    
    pole[coord.x][coord.y]=trail;renderer.vykresli(coord.x, coord.y, true);
    coord.y+=pY;coord.x+=pX;pole[coord.x][coord.y] = 2;Move();
}
}
public void Move()      {
if(breaking){renderer.vykresliOkoli(coord.x,coord.y,1,true,pole);isRendered[3]=true;}
vykresli();if(breaking){isRendered[3]=false;}
if(BoolGameMode){LabelCoin.setText("Mincí: "+coinu);}
if(BoolKroky){LabelTime.setText("Kroky: "+kroky);}}
public void Death(){pole[coord.x][coord.y]=1;this.coord = new Coord(1,1);pole[coord.x][coord.y]=2;
coinu -= (int)Math.floor(coinu/10.0);renderer.VykresliHrac(coord.x,coord.y);}
public void Spawn(Coord c){coord =c;kroky = 0;Move();}
}
public class Coin {
public Coord coord;public int extra;public boolean sebrano;public int hodnota;public Image img;public String msg;
public Coin(Coord coord,int extra,int hodnota,String msg){this.coord = coord;this.extra = extra;sebrano = false;this.hodnota = hodnota;img = getCoinImage(hodnota);this.msg = msg;}
public void seber(){sebrano=true;coinu+=hodnota;
if(msg!=null){showMessageBox(msg);}}
public boolean isOnCoord(int x,int y){return coord.x==x && coord.y==y;}
public Coin OnCoord(int x,int y){if(isOnCoord(x,y)){return this;}else{return null;}}
@Override
public String toString(){return String.format(""+extra);}
}
public class Coord {
int x; int y;
public Coord(int x, int y){this.x=x;this.y=y;}
public boolean isCoord(int x, int y){return (x==this.x && y==this.y);}
public Coord onCoord(int x, int y){if(isCoord(x,y)){return this;}else{return null;}}
public boolean isCoord(Coord coord){return (coord.x==this.x && coord.y==this.y);}
public Coord onCoord(Coord coord){if(isCoord(coord)){return this;}else{return null;}}
}
/* // <editor-fold defaultstate="collapsed" desc="Spiral">
public void Generace1(int zdi, int pX, int pY, int zX, int zY){
    while(zdi > 0){ 
    int var1=Random(0,zdi);
    //
    boolean pole2[][] = new boolean[pole.length][pole[0].length];
    for (int j = 0; j < pole[0].length; j++){for (int i = 0; i < pole.length; i++){pole2[i][j] = true;}}
    int zkontrolovano = pole[0].length*pole.length;
    int i=0;int j=0;int PX = 0;int PY = 0;int smer = 0;int kontrola =0;boolean next = pole2[i][j];
    while(zkontrolovano > 0){
    smer++;if(smer>=5){smer=1;}if(smer==1){PX=0;PY=1;}else if(smer==2){PX=1;PY=0;}else if(smer==3){PX=0;PY=-1;}else if(smer==4){PX=-1;PY=0;}
    try{next = pole2[i+PX][j+PY];}catch(Exception e){next = false;}
    do{pole2[i][j] = false;
    //
  //Here The Code;
    //
    i+=PX;j+=PY;try{next = pole2[i+PX][j+PY];}catch(Exception e){next = false;}zkontrolovano--;}while(next);
    }}
}
*/ // </editor-fold>
}