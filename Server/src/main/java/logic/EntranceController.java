package logic;

import database.Database;

public class EntranceController {

    public boolean isInputValidLogin(String username, String password){
        boolean flag = false;
        try {
            if (Database.getUser(username).getPassword().equals(password)) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("returning");
            return false;
        }
        System.out.println("returning");
        return flag;
    }

}