package com.example.finalproject_varquez;

import java.io.*;

public class SessionSerializer {
    private static final String SESSION_FILE = "session.dat";

    public static void saveSession(UserSessionData data){
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SESSION_FILE))){
            oos.writeObject(data);
        } catch (IOException e){
            System.err.println("Failed to serialize user session: " + e.getMessage());
        }
    }

    public static UserSessionData loadSession() {
        File file = new File(SESSION_FILE);
        if(!file.exists()) return null;

        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))){
            return (UserSessionData)  ois.readObject();
        } catch (IOException | ClassNotFoundException e){
            System.err.println("Failed to deserialize user session: "+ e.getMessage());
            return  null;
        }
    }

    // delete the file when a user hit logout
    public  static void deleteSessionFile(){
        File file = new File(SESSION_FILE);
        if(file.exists()){
            file.delete();
        }
    }
}
