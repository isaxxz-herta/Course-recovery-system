package CRS;

import java.io.*;
import java.util.ArrayList;

public class ActivityLogFileHandler {
    private static final String FILE = "ActivityLog.dat";
    
    public static void saveLogs(ArrayList<ActivityLog> logs){
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE))){
            oos.writeObject(logs);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public static ArrayList<ActivityLog> loadLogs(){
        ArrayList<ActivityLog> logs = new ArrayList<>();
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE))){
            logs = (ArrayList<ActivityLog>) ois.readObject();
        }catch(FileNotFoundException e){
            return logs; //first run
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return logs;
    }
    
    public static void saveLog(ActivityLog log){
        ArrayList<ActivityLog> logs = loadLogs();
        logs.add(log);
        saveLogs(logs);
    }
}