package com.edumate;


import com.edumate.controller.AppController;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        AppController appController = new AppController();
        appController.run();
    }
}