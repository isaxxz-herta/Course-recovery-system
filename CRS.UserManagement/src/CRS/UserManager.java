package CRS;

import java.io.*;
import java.util.ArrayList;

public class UserManager {
    private static final String FILE_PATH = "users.dat";
    
    public static void saveUsers(ArrayList<User> users){
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))){
            oos.writeObject(users);
        }catch(IOException e){
                e.printStackTrace();
        }
    }
    
    public static ArrayList<User> loadUsers(){
        ArrayList<User> users = new ArrayList<>();
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))){
            users = (ArrayList<User>) ois.readObject();
        }catch(FileNotFoundException e){
            System.out.println("Userfile not found, starting fresh...");
        }catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return users;
    }
}
