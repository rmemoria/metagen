package com.rmemoria.metagen;

/**
 * Entry point of the application
 * Created by ricardo on 11/09/14.
 */
public class Main {
   public static void main(String[] args) {
       System.out.println("metagen v0.1");
       try {
           for (String jarfile: args) {
               System.out.println("Generating meta information for " + jarfile);
               MetaGenerator.generate(jarfile);
           }
           System.out.println("Successfully generated.");
           System.exit(0);
       }
       catch (Exception e) {
           e.printStackTrace();
           System.exit(1);
       }
   }
}
