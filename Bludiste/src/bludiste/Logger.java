//Logger V1.1.2
package bludiste;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
public class Logger {
    public static final String Version = "V0.9.9";
    private final String LVNS = ": ";
    private       BufferedWriter BW;
    private final String Name = "Logger";
    private       String FSS;
    private String LOG = "";
    public Logger(String FileName,String FSS){this.FSS=FSS;BW = getBufferedWriter(FileName);
    Write(Name+LVNS+Version);
    Write(System.getProperty("java.home"));
    Write(System.getProperty("java.version"));
    Write(System.getProperty("os.arch"));
    Write(System.getProperty("os.name"));
    Write(System.getProperty("os.version"));
    Write(System.getProperty("sun.java2d.opengl"));}
    public void setLog(String[] Log){for(String x : Log){Write(x);}Flush();}
    private void Write(String Name){LOG+=(Name+"::");}
    private void Flush(){try(BufferedWriter bw=BW){for(String LOGs : LOG.split("::")){bw.write(LOGs);bw.newLine();}bw.flush();}catch(Exception e){}}
private BufferedWriter getBufferedWriter(String text){BufferedWriter bw=null;
try {bw = new BufferedWriter(new FileWriter(System.getProperty("user.dir")+FSS+text,true));} catch (IOException e) {System.out.println("ERRBWNULL");}return bw;}
}