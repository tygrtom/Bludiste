//Stopwatch V1.0.9
package bludiste;
import java.util.ArrayList;
public class Stopwatch {
    public long startTime;
    public long pausedTime;
    public ArrayList<Double> rounds;
    public String label;
    public boolean started;
        public Stopwatch(String label,boolean start){this.label = label;rounds=new ArrayList<>();if(start){Start();}else{started=false;}}
        public Stopwatch(String label,boolean start,int rnds){this.label = label;rounds=new ArrayList<>(rnds);if(start){Start();}else{started=false;}}
        public void Start(){if(!started){startTime=System.nanoTime();started=true;}else{System.err.println("StopwatchRunningError:"+label);}}
        public double measure(){return measureNano()/1000000000.0;}
        public long measureNano(){return System.nanoTime()-startTime;}
        public void Round(){rounds.add(measure());}
        public ArrayList<Double> Rounds(){return rounds;}
        public void Stop(){startTime = measureNano();started=false;}
        public void StopAll(){Stop();rounds.clear();}
        public void Restart(){Stop();Start();}
        public void Pause(){Stop();pausedTime=startTime;}
        public void Resume(){Start();startTime-=pausedTime;}
}