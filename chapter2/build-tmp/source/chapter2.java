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
float taillevisuels;


String[] liste;
String[][] valeur = new String[cols][rows];
String[][] datas1983 = new String[144][rows];

float[] distdashs = {5, 8, 5, 8};

ArrayList<Visuelhexa> visuels = new ArrayList<Visuelhexa>();

int linesdatas = 0, saveindex = 0, saveindex2 = 0, count = 0;
boolean pressedbing = false;

// PVector[] posrect = new PVector[datas1983.length];

Visuelhexa visuelpressed, visueldragged;
Visudom[] visusdom = new Visudom[8];
int indexdrag;


public void setup(){
    size(1280, 720,P2D);
    background(23, 33, 48);
    parseDatas();
    
    for (int i = 0; i<datas1983.length; i++){
        // println(datas1983[i][2]); --> print de la colonne des nombres de fois expos\u00e9s
        taillevisuels = map(PApplet.parseFloat(datas1983[i][2]), 0, 43, 4, 43);
        visuels.add(new Visuelhexa (random(0+43,width-43), random(0+43, height-43), taillevisuels));
    }
}


public void draw(){
    background(23, 33, 48);
    for (Visudom visueldomaine : visusdom){
      visueldomaine.dessin();
    }
    for (Visuelhexa monvisuel : visuels){
        monvisuel.dessin();
    }
    lineartiste();
}


public void lineartiste(){
        for (int i = 0; i<visuels.size(); i++){
        if (datas1983[i][6].equals("Henri YVERGNIAUX")){
            strokeWeight(1);
            dashline(visuels.get(i).px, visuels.get(i).py, visuels.get(saveindex).px, visuels.get(saveindex).py, distdashs);
            saveindex = i;
        }
        if (datas1983[i][6].equals("Michel DIEUZAIDE")){
            strokeWeight(1);
            dashline(visuels.get(i).px, visuels.get(i).py, visuels.get(saveindex2).px, visuels.get(saveindex2).py, distdashs);
            saveindex2 = i;
        }
          // for (int j = visuels.size()-1; j>=0; j--){
          //   if (datas1983[i][6].equals(datas1983[j][6])){
          //     strokeWeight(1);
          //     dashline(visuels.get(i).px, visuels.get(i).py, visuels.get(j).px, visuels.get(j).py, distdashs);
          //   }
          // }
        }
}


public void mouseDragged(){
    if (pressedbing){
        visuels.get(indexdrag).px = mouseX;
        visuels.get(indexdrag).py = mouseY;
    }
}

public void mousePressed(){

    /* ------------ DETECTION DU CLIC POUR LE DRAG N DROP ----------- */

    for (int i = 0; i<visuels.size(); i++){
        visuels.get(i).detection();
        if (visuels.get(i).bing){
            indexdrag = i;
            pressedbing = true;
            break;
        }
    }
    
    /* ------------ DETECTION DU CLIC POUR LA NOTIFICATION ----------- */



}

public void mouseReleased(){
    pressedbing = false;
}


/* -----------------------------

    ICI ON PARSE LES DONNEES

------------------------------- */


public void parseDatas(){


    liste = loadStrings("datas.csv");

    for (int i=0; i<liste.length; i++) {
      valeur[i] = split(liste[i], ";");
    }

    for (int i = 1; i<valeur.length; i++){
        if (valeur[i][1].equals("1983")){
            for (int j = 1; j<valeur[i].length; j++){
                datas1983[linesdatas][j] = valeur[i][j];
            }
            linesdatas++;
        }
    }

/* ---------------- Fabrication du tableau de domaines ---------------- */

    int peinture = 0, estampe = 0, sculpture = 0, photographie = 0, plr = 0, dessin = 0, o3d = 0, objd = 0;
    int[] nbdomaine = new int[8];

     for (int i = 0; i<datas1983.length; i++){
        if (datas1983[i][3].equals("Peinture")){
            peinture++;
        }else if (datas1983[i][3].equals("Estampe")) {
            estampe++;
        }else if (datas1983[i][3].equals("Sculpture")) {
            sculpture++;
        }else if (datas1983[i][3].equals("Photographie")) {
            photographie++;
        }else if (datas1983[i][3].equals("Publication, livre, reliure")) {
            plr++;
        }else if (datas1983[i][3].equals("Dessin")) {
            dessin++;
        }else if (datas1983[i][3].equals("Oeuvre en 3 dimensions")) {
            o3d++;
        }else if (datas1983[i][3].equals("Objet/Design")) {
            objd++;
        }

        // switch (datas1983[i][3].equals("")){
          
        // }
     }
     nbdomaine[0] = peinture;
     nbdomaine[1] = estampe;
     nbdomaine[2] = sculpture;
     nbdomaine[3] = photographie;
     nbdomaine[4] = plr;
     nbdomaine[5] = dessin;
     nbdomaine[6] = o3d;
     nbdomaine[7] = objd;

     for (int i = 0; i<nbdomaine.length; i++){
       nbdomaine[i] = PApplet.parseInt(map(nbdomaine[i], 1, 44, 20, 100));
       visusdom[i] = new Visudom(nbdomaine[i]);
     }
}

/* ------------------------------------------------------

    Fonction qui permet de faire des traits en pointill\u00e9

------------------------------------------------------ */

public void dashline(float x0, float y0, float x1, float y1, float[] spacing){
  float distance = dist(x0, y0, x1, y1);
  float [ ] xSpacing = new float[spacing.length];
  float [ ] ySpacing = new float[spacing.length];
  float drawn = 0.0f;

  if (distance > 0)
  {
    int i;
    boolean drawLine = true;

    for (i = 0; i < spacing.length; i++)
    {
      xSpacing[i] = lerp(0, (x1 - x0), spacing[i] / distance);
      ySpacing[i] = lerp(0, (y1 - y0), spacing[i] / distance);
    }

    i = 0;
    while (drawn < distance)
    {
      if (drawLine)
      {
        line(x0, y0, x0 + xSpacing[i], y0 + ySpacing[i]);
      }
      x0 += xSpacing[i];
      y0 += ySpacing[i];
    
      drawn = drawn + mag(xSpacing[i], ySpacing[i]);
      i = (i + 1) % spacing.length;
      drawLine = !drawLine;
    }
  }
}
class Visudom
{
	
	float n; // nombre de segments
	float rayon;
	float angle = 0;
	float distribution;
	PVector centre;
	
	Visudom (float rayon) {
		n = 4;
		distribution = TWO_PI/n;
		this.rayon = rayon;
		centre = new PVector (random(100, width-100),random(100, height-100));;
	}

	public void dessin(){
		fill(255,83,66);
		noStroke();
		beginShape();
			for(int i =0; i<n; i++){ 
			  vertex(centre.x + cos(angle)*rayon, centre.y+ sin(angle)*rayon);
			  angle+=distribution;
			}
		endShape(CLOSE);
	}
}
class Visuelhexa {
	
	float n;
    float angle;
    float distribution;
	float px, py, ray;
    int indexvisuelbing;

    boolean bing;

	Visuelhexa (float posx, float posy, float rayon) {
        n = 6;
        angle = 0;
        distribution = TWO_PI/n;
        bing = false;
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
        for(int i =0; i<n; i++){ 
          vertex(px + cos(angle)*ray, py+ sin(angle)*ray);
          angle+=distribution;
        }
        endShape(CLOSE);
        // thread("lineartiste()");
	}

    public void detection(){
        float distance = dist(mouseX, mouseY, px, py);
        if (distance <= ray){
            // println( " BING ");
            bing = true;
        }else {
            bing = false;
        }
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
