package org.msh.metagen;

/**
 * Created by ricardo on 11/09/14.
 */
public class Main {
   public static void main(String[] args) {
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
