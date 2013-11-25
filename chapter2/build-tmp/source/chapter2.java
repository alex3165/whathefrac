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

int rows = 7, cols = 4788;

String[][] valeur = new String[cols][rows];
String[] liste;

ArrayList<String> datas1983 = new ArrayList<String>();

// Visuelhexa test;

public void setup(){
    size(1280, 720);
    background(23, 33, 48);
    liste = loadStrings("datas.csv");

    for (int i=0; i<liste.length; i++) {
      valeur[i] = split(liste[i], ";");
    }
    println(valeur[4787][6]); // Print derni\u00e8re cellule de la derni\u00e8re colonne

    // test = new Visuelhexa (width/2, height/2, 40);
    for (int i = 1; i<cols-1; i++){
        if (valeur[i][1].equals("1983")){
            for (int j = 1; j<rows-1; j++){
                datas1983.add(valeur[i][j]);
            }
            
        }
    }
    println(datas1983);
}

public void draw(){
    background(23, 33, 48);
    // test.dessin();
}
class Visuelhexa {
	
	float n=6;
    float angle = 0;
    float distribution = TWO_PI/n;
	float px, py, ray;

	Visuelhexa (float posx, float posy, float rayon) {
		px = posx;
		py = posy;
		ray = rayon;
	}

	public void dessin(){
        smooth(); 
        shapeMode(CENTER);
        fill(255);
        stroke(255,80);
        strokeWeight((ray*0.7f));
        beginShape();
        for(int i =0; i<8; i++){ 
          vertex(px + cos(angle)*ray, py+ sin(angle)*ray);
          angle+=distribution;
        }
        endShape(CLOSE);
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
