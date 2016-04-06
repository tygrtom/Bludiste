//Generator V1.1.0b
package bludiste;
import bludiste.BludisteController.Coord;
import java.util.ArrayList;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.util.Duration;
public class Generator extends Thread{
    public boolean done=false;
    public byte[][] pole;
    public int typ;
    public int poleX;
    public int poleY;
    public BludisteController Blud;
    public Renderer renderer;
    public Timeline renderLoop;
    public byte[][] getPole(){return this.pole;}
    public Generator(int typ,byte[][] pole,BludisteController Blud,int poleX, int poleY, Renderer renderer){
        this.pole=pole;this.typ = typ;this.Blud = Blud;this.poleX=poleX;this.poleY=poleY;this.renderer=renderer;}
    @Override
    public void run(){synchronized(Blud){
        done=false;
    if(typ == 0){Generace1(Zaklad1(0));}
    if(typ == 1){Generace2(Zaklad1(0));}
    if(typ == 2){Generace3(Zaklad1(0));}
    if(typ == 3){Generace4();}
    if(typ == 4){Generace5();}
    if(typ == 5){Generace6();}
    if(typ == 6){Generace7();}
    if(typ == 7){Generace8();}
    if(typ == 8){Generace9();}
    if(typ == 9){Generace10();}
    //Blud.Generuj(getPole());
    
    done=true;
    Blud.notify();}
}
// <editor-fold defaultstate="collapsed" desc="Bludiste">
public int Zaklad1(int zdi){  
for (int j = 0; j < pole[0].length; j++){for (int i = 0; i < pole.length; i++){
    if(i == 0 || i == pole.length-1 || j == 0 || j == pole[0].length-1){pole[i][j] = 4;}
    else
    if(i%2 == 0 && j%2 ==0){pole[i][j] = 5;zdi++;}
    else{pole[i][j] = 1;}}}
    return zdi;
}
public void Zaklad2(byte num){  
for (int j = 0; j < pole[0].length; j++){for (int i = 0; i < pole.length; i++){pole[i][j] = num;}}
}
public void Zaklad3(){  
for (int j = 0; j < pole[0].length; j++){for (int i = 0; i < pole.length; i++){if(i ==0||j ==0||i ==pole.length-1||j ==pole[0].length-1){pole[i][j] = 4;}else{pole[i][j] = 1;}}}
}
public void Generace1(int zdi){
    while(zdi > 0){ 
    int var1=Random(0,zdi);
    MainLoop:for (int j = 0; j < pole[0].length; j++){for (int i = 0; i < pole.length; i++){
    if(pole[i][j] == 5){if(var1 <= 0){
    int smer=Random(1,4);
    int px = 0;int py = 0;int offset = 0;
    if (smer == 1){px=1;}else if (smer == 2){py=1;}else if (smer == 3){px=-1;}else if (smer == 4){py=-1;}
    while(pole[i +(px*offset)][j+(py*offset)] != 4){
    if(pole[i +(px*offset)][j+(py*offset)] == 5){zdi--;}pole[i +(px*offset)][j+(py*offset)] = 4;offset++;}break MainLoop;
    }else{var1--;}}}}}
}
public void Generace2(int zdi){
    while(zdi > 0){ 
    int var1=Random(0,zdi);
for (int j = 0; j < pole[0].length; j++){for (int i = 0; i < pole.length; i++){
    if(pole[i][j] == 5){if(var1 <= 0){
    int smer=Random(1,4);int length = 0;
    int px = 0;int py = 0;int offset = 0;
    if (smer == 1){px=1;}else if (smer == 2){py=1;}else if (smer == 3){px=-1;}else if (smer == 4){py=-1;}
    while(pole[i +(px*offset)][j+(py*offset)] != 4&&length<=(Math.ceil(Math.pow(pole.length*pole[0].length,1/2.0)/20.0))){length++;
    if(pole[i +(px*offset)][j+(py*offset)] == 5){zdi--;}pole[i +(px*offset)][j+(py*offset)] = 4;offset++;}break;
    }else{var1--;}}}}}
}
public void Generace3(int zdi){
    while(zdi > 0){ 
    int var1=Random(0,zdi);
for (int j = 0; j < pole[0].length; j++){for (int i = 0; i < pole.length; i++){
    if(pole[i][j] == 5){if(var1 <= 0){
    int smer=Random(1,4);int length = 0;
    int px = 0;int py = 0;int offset = 0;
    if (smer == 1){px=1;}else if (smer == 2){py=1;}else if (smer == 3){px=-1;}else if (smer == 4){py=-1;}
    while(pole[i +(px*offset)][j+(py*offset)] != 4&&length<=1){length++;
    if(pole[i +(px*offset)][j+(py*offset)] == 5){zdi--;}pole[i +(px*offset)][j+(py*offset)] = 4;offset++;}break;
    }else{var1--;}}}}}
}
public void Generace4(){Zaklad2((byte)4);DFSRecursion(Blud.PlayerPos.x,Blud.PlayerPos.y,pole);}
public void Generace5(){Zaklad2((byte)1);DFSRecursionReversed(Blud.PlayerPos.x,Blud.PlayerPos.y,pole);
for (int j = 0; j < pole[0].length; j++){for (int i = 0; i < pole.length; i++){if(pole[i][j]==1){pole[i][j] = 4;}else {if(pole[i][j]==4){pole[i][j] = 1;}}}}}
public void Generace6(){Zaklad2((byte)1);}
public void Generace7(){
    Zaklad2((byte)4);Coord start;int pX=0;int pY=0;
    do{start = Blud.new Coord(Random(1,poleX-1),Random(1,poleY-1));}while(start.x%2==0||start.y%2==0);pole[start.x][start.y]=1;
    ArrayList<Coord> walllist = new ArrayList<>(poleX*poleY/6);walllist.addAll(MarkWallsOf(start));
    while(!walllist.isEmpty()){start = walllist.remove(Random(0,walllist.size()-1));
        if(pocetZdiKolem(start.x,start.y)==3){
        if(pole[start.x+1][start.y]==1){pX=-1;pY=0;}else
        if(pole[start.x-1][start.y]==1){pX=1;pY=0;}else
        if(pole[start.x][start.y+1]==1){pX=0;pY=-1;}else
        if(pole[start.x][start.y-1]==1){pX=0;pY=1;}
        pole[start.x][start.y]=1;pole[start.x+pX][start.y+pY]=1;
        walllist.addAll(MarkWallsOf(Blud.new Coord(start.x+pX,start.y+pY)));
        }else {pole[start.x][start.y]=4;}}}
public void Generace8(){Zaklad3();
for(int i = 2; i < pole.length-2; i+=2){for(int j = 2; j < pole[0].length-2; j+=2){
    int smer=Random(1,4);pole[i][j] = 4;
    if(smer==1){pole[i+1][j]=4;}else if(smer==2){pole[i][j+1]=4;}else if(smer==3){pole[i-1][j]=4;}else if(smer==4){pole[i][j-1]=4;}
    }}}
public void Generace9(){Zaklad3();}
public void Generace10(){
}
public ArrayList<Coord> MarkWallsOf(Coord x){ArrayList<Coord> walllist = new ArrayList<Coord>(3);
    if(pole[x.x+1][x.y]==4&&!(x.x+1>=pole.length-1))     {pole[x.x+1][x.y]=5;walllist.add(Blud.new Coord(x.x+1,x.y));}
    if(pole[x.x-1][x.y]==4&&!(x.x-1<=0))                 {pole[x.x-1][x.y]=5;walllist.add(Blud.new Coord(x.x-1,x.y));}
    if(pole[x.x][x.y+1]==4&&!(x.y+1>=pole[0].length-1))  {pole[x.x][x.y+1]=5;walllist.add(Blud.new Coord(x.x,x.y+1));}
    if(pole[x.x][x.y-1]==4&&!(x.y-1<=0))                 {pole[x.x][x.y-1]=5;walllist.add(Blud.new Coord(x.x,x.y-1));}
return walllist;}
public void DFSRecursion(int x, int y,byte[][] pole2){
     int[] randDirs = RandomDirections();
     for (int i = 0; i < randDirs.length; i++) {
         switch(randDirs[i]){
         case 1: // Up
             if (x - 2 <= 0) {continue;}
             if (pole2[x - 2][y] != 1) {pole2[x-2][y] = 1;pole2[x-1][y] = 1;DFSRecursion(x - 2, y,pole2);}
             break;
         case 2: // Right
             if (y + 2 >= pole2[x].length - 1) {continue;}
             if (pole2[x][y + 2] != 1) {pole2[x][y + 2] = 1;pole2[x][y + 1] = 1;DFSRecursion(x, y + 2,pole2);}
             break;
         case 3: // Down
             if (x + 2 >= pole2.length - 1) {continue;}
             if (pole2[x + 2][y] != 1) {pole2[x+2][y] = 1;pole2[x+1][y] = 1;DFSRecursion(x + 2, y,pole2);}
             break;
         case 4: // Left
             if (y - 2 <= 0) {continue;}
             if (pole2[x][y - 2] != 1) {pole2[x][y - 2] = 1;pole2[x][y - 1] = 1;DFSRecursion(x, y - 2,pole2);}
             break;
         }}}
public void DFSRecursionReversed(int x, int y,byte[][] pole2){
     int[] randDirs = RandomDirections();
     for (int i = 0; i < randDirs.length; i++) {
         switch(randDirs[i]){
         case 1: // Up
             if (x - 2 <= 0) {continue;}
             if (pole2[x - 2][y] != 4) {pole2[x-1][y] = 4;pole2[x-2][y] = 4;DFSRecursionReversed(x - 2, y,pole2);}
             break;
         case 2: // Right
             if (y + 2 >= pole2[x].length - 1) {continue;}
             if (pole2[x][y + 2] != 4) {pole2[x][y + 1] = 4;pole2[x][y + 2] = 4;DFSRecursionReversed(x, y + 2,pole2);}
             break;
         case 3: // Down
             if (x + 2 >= pole2.length - 1) {continue;}
             if (pole2[x + 2][y] != 4) {pole2[x+1][y] = 4;pole2[x+2][y] = 4;DFSRecursionReversed(x + 2, y,pole2);}
             break;
         case 4: // Left
             if (y - 2 <= 0) {continue;}
             if (pole2[x][y - 2] != 4) {pole2[x][y - 1] = 4;pole2[x][y - 2] = 4;DFSRecursionReversed(x, y - 2,pole2);}
             break;
         }}}
public int[] RandomDirections(){
    int[] rnds= new int[4];
    for(int x =0; x < rnds.length;x++){while(true){int z = Random(1,4);if(!ColContains(rnds,z)){rnds[x]=z;break;}}}
    return rnds;
}
public boolean ColContains(int[] Col, int x){for(int z : Col){if(z==x){return true;}}return false;}
// </editor-fold>
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
public int pocetZdiKolem(int i, int j){
int pocetZdiKolem = 0;
        if(pole[i+1][j]==4||pole[i+1][j]==6||pole[i+1][j]==9||pole[i+1][j]==5){pocetZdiKolem++;}
        if(pole[i-1][j]==4||pole[i-1][j]==6||pole[i-1][j]==9||pole[i-1][j]==5){pocetZdiKolem++;}
        if(pole[i][j+1]==4||pole[i][j+1]==6||pole[i][j+1]==9||pole[i][j+1]==5){pocetZdiKolem++;}
        if(pole[i][j-1]==4||pole[i][j-1]==6||pole[i][j-1]==9||pole[i][j-1]==5){pocetZdiKolem++;}
return pocetZdiKolem;}
public void VykresliBod(int i, int j){if(Blud.BoolPostupRes){try{Thread.sleep(Blud.IntResDelay);}catch(Exception e){Blud.Errors.add("ErrorWait");}}}
public void VykresliOkoli(int i, int j,int pol){for(int x = j-pol; x < j+pol; x++){for(int y = j-pol; y < j+pol; y++){VykresliBod(i,j);}}}
public void startRender() {
        final Duration oneFrameAmt = Duration.millis(Blud.IntResDelay);
        final KeyFrame oneFrame = new KeyFrame(oneFrameAmt, (javafx.event.ActionEvent event) -> {
            if(Blud.BoolPostupRes){renderer.vykresli(pole);}
        });
        renderLoop =(TimelineBuilder.create().cycleCount(Animation.INDEFINITE).keyFrames(oneFrame).build());
        renderLoop.play();
    }
public void VykresliPole(byte[][] pole){
    renderer.polealt=pole;renderer.start();
}
}