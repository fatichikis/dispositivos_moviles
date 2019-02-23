package com.example.dispositivos2;

import android.widget.ImageView;

public class User {
    public String name;
    public String email;
    public String password;
    public String phone;

//   public User()
//   {
//
//   }
    public User(String name, String email,String password, String phone) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

}

//public class User {
//     String email;
//     String password;
//     String cellphone;
//
//    public User(String displayName, String userEmail, long time)
//    {
//    }
//    public User(String email, String password, String cellphone){
//        this.password = password;
//        this.email = email;
//        this.cellphone = cellphone;
//    }
//    public String getCellphone(){
//        return cellphone;
//    }
//    public String getPassword()
//    {
//        return password;
//    }
//    public void setPassword(String password)
//    {
//        this.password= password;
//    }
//    public String getEmail()
//    {
//        return email;
//    }
//    public void setEmail(String email)
//    {
//        this.email= email;
//    }
//
//}
