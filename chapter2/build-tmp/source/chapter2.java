import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class chapter2 extends PApplet {

String[][] valeur = new String[5000][80];
String[] liste;
int j = 0;

public void setup(){
    liste = loadStrings("data.csv");
    for (int i=0; i<liste.length; i++) {
      valeur[i] = split(liste[i], ";");
    }

    for (int i = 0; i<valeur.length; i++){
      //  if (valeur[i][0].equals("Oeuvre en 3 dimensions")){
            // j++;
            // println(j);
        //}
    }
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "chapter2" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
