//Logger V1.0.9
package bludiste;
/*
Log
0-Head
---0--Header
------0--Name
------1--Version
------2--CreationTime
------3--RunningTime
------4--InitializeTime
---1--System Resources
------0--"java.home"
------1--"java.version"
------2--"os.arch"
------3--"os.name"
------4--"os.version"
------5--"sun.java2d.opengl"
---2--Config
------0--FileName
------1--MovingSpikesStartRound:
------2--MovingSpikesMaxSpeed:
------3--StartingRound:
------4--ShowPath:
------5--GameMode:
------6--SolveErasing:
------7--TrailErasing:
------8--Steps:
------9--AnimatedSpikes:
------10-SlowSolving:
------11-EndUnlocked:
------12-SpikesEnabled:
------13-DarkMask:
------14-FogMask:
------15-ColorRoad:
------16-ColorErrDebug:
------17-ColorZdiDebug:
------18-ColorSolve:
------19-ColorTrail:
------20-FullScreenStart:
---3--Memory
------0--TotalMemory
------1--MaxMemory
------2--FreeMemory
---4--Errors
------0-X-ErrorX
1--Maze
---0--Param
------0--ID
------1--PoleXY
------2--VelikostXY
------3--Mezera
------4--Kolo
---1--Generace
------0--Typ
------1--Generating times
---2--Coins
------0--Count
------1--Values
---3--Player
------0--PosXY
---4--Spikes
------0--Count
---5--Mask
------0--DarkMask
------1--FogMask
---6--AnimSpikes
------0--Count
------1--MaxCount
------2--Speed
---7--Render
------0--RenderTime
---8--Sum
------0--Blocks
------1--Walls(all)
------2--Paths(all)
------3--CreationTime-gen-render
------4--TotalMemory
------5-X-Rows(W/P)
2--Solve
---0--Param
------0--ID
------1--Typ
---1--Sum
------0--BlocksChanged
------1--SolvingTimes
------2--TotalTime
------3--TotalMemory
*/
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
public class LoggerV1 {
    public static final String Version = "V0.9.3";
    private final String LVNS = ": ";
    private       BufferedWriter BW;
    private final String Name;
    private       String FSS;
    private String LOG = "";
    public LoggerV1(String FileName,String Name,String FSS){this.Name = Name;this.FSS=FSS;BW = getBufferedWriter(FileName);}
    public void setLog(String[][][] Log,int H,int M,int S){int i=0;while(i<H){setHead(Log[i]);i++;}while(i<H+M){setMaze(Log[i]);i++;}while(i<H+M+S){setSolve(Log[i]);i++;}Flush();}
        private void setHead(String[][] Head){setHeadHeader(Head[0]);setHeadSystemResources(Head[1]);setHeadConfig(Head[2]);setHeadMemory(Head[3]);setHeadErrors(Head[4]);}
            private void setHeadHeader(String[] Header){Write("Aplication",Header[0]);Write("Version",Header[1]);Write("Created",Header[2]);Write("Run",Header[3]);Write("Init",Header[4]);Writeln();}
            private void setHeadSystemResources(String[] SystemResources){Write("JavaPath",SystemResources[0]);Write("Java",SystemResources[1]);Write("OS.Arch",SystemResources[2]);Write("OS.Name",SystemResources[3]);Write("OS.Version",SystemResources[4]);Writeln();}
            private void setHeadConfig(String[] Config){String[] ConfigNames = {"Config","MovingSpikesStartRound","MovingSpikesMaxSpeed","StartingRound","ShowPath","GameMode","SolveErasing","TrailEraisng","Steps","AnimatedSpikes","SlowSolving","EndUnlocked","SpikesEnabled","DarkMaskEnabled","FogMaskEnabled","ColorRoad","ColorErrDebug","ColorZdiDebug","ColorSolve","ColorTrail","FillScreenStart"};for(int i = 0;i<Config.length;i++){Write(ConfigNames[i],Config[i]);}Writeln();}
            private void setHeadMemory(String[] Memory){Write("AllocatedMemory",Memory[0]);Write("MaxMemory",Memory[1]);Write("FreeMemory",Memory[2]);Writeln();}
            private void setHeadErrors(String[] Errors){for (String Error : Errors) {Write("Error", Error);}Writeln();}
        private void setMaze(String[][] Maze){setMazeParam(Maze[0]);setMazeGenerace(Maze[1]);setMazeCoins(Maze[2]);setMazePlayer(Maze[3]);setMazeSpikes(Maze[4]);setMazeMask(Maze[5]);setMazeAnimSpikes(Maze[6]);setMazeRender(Maze[7]);setMazeSum(Maze[8]);}
            private void setMazeParam(String[] Param){String[] ParamNames = {"ID","Blocks","Width/Height","Space","Round"};for(int i = 0;i<Param.length;i++){Write(ParamNames[i],Param[i]);}Writeln();}
            private void setMazeGenerace(String[] Generace){String[] GeneraceNames = {"Type","GenTimes"};for(int i = 0;i<Generace.length;i++){Write(GeneraceNames[i],Generace[i]);}Writeln();}
            private void setMazeCoins(String[] Coins){String[] CoinsNames = {"Count","Values"};for(int i = 0;i<Coins.length;i++){Write(CoinsNames[i],Coins[i]);}Writeln();}
            private void setMazePlayer(String[] Player){String[] PlayerNames = {"Coord"};for(int i = 0;i<Player.length;i++){Write(PlayerNames[i],Player[i]);}Writeln();}
            private void setMazeSpikes(String[] Spikes){String[] SpikesNames = {"Count"};for(int i = 0;i<Spikes.length;i++){Write(SpikesNames[i],Spikes[i]);}Writeln();}
            private void setMazeMask(String[] Mask){String[] MaskNames = {"DarkMask","FogMask"};for(int i = 0;i<Mask.length;i++){Write(MaskNames[i],Mask[i]);}Writeln();}
            private void setMazeAnimSpikes(String[] AnimSpikes){String[] AnimSpikesNames = {"Count","MaxCount","Speed"};for(int i = 0;i<AnimSpikes.length;i++){Write(AnimSpikesNames[i],AnimSpikes[i]);}Writeln();}
            private void setMazeRender(String[] Render){String[] RenderNames = {"RenderTime"};for(int i = 0;i<Render.length;i++){Write(RenderNames[i],Render[i]);}Writeln();}
            private void setMazeSum(String[] Sum){String[] SumNames = {"Blocks","WallsTotal","PathsTotal","CreationTime","Rows(W/P)","Collumns(W/P)"};for(int i = 0;i<Sum.length;i++){Write(SumNames[i],Sum[i]);}Writeln();}
        private void setSolve(String[][] Solve){setSolveParam(Solve[0]);setSolveSum(Solve[1]);}
            private void setSolveParam(String[] Param){String[] ParamNames = {"ID","Type"};WriteAll(ParamNames,Param);Writeln();}
            private void setSolveSum(String[] Sum){String[] SumNames = {"BlocksChanged","Times","TotalTime","TotalMemory"};WriteAll(SumNames,Sum);Writeln();}
    private void WriteAll(String[] Names, String[] Values){for(int i = 0; i <Values.length;i++){Write(Names[i],Values[i]);}}
    private void Write(String Name, String Value){LOG+=(Name+LVNS+Value+"::");}
    private void Writeln(String Name, String Value){Write(Name,Value);Writeln();}
    private void Writeln(){LOG+="::";}
    private void Flush(){try(BufferedWriter bw=BW){for(String LOGs : LOG.split("::")){bw.write(LOGs);bw.newLine();}bw.flush();}catch(Exception e){}}
    
private BufferedWriter getBufferedWriter(String text){BufferedWriter bw=null;
try {bw = new BufferedWriter(new FileWriter(System.getProperty("user.dir")+FSS+text,true));} catch (IOException e) {System.out.println("ERRBWNULL");}return bw;}
}