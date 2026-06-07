package com.crs.persistence;

import com.crs.service.UserManager;
import java.io.*;

public class PersistenceService {
    private final File storageFile;

    public PersistenceService(String path) {
        this.storageFile = new File(path);
    }

    public boolean save(UserManager manager) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storageFile))) {
            oos.writeObject(manager);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public UserManager load() {
        if (!storageFile.exists()) return null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storageFile))) {
            Object obj = ois.readObject();
            if (obj instanceof UserManager) {
                return (UserManager) obj;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
