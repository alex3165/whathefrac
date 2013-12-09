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

//import java.awt.Polygon;
// import java.awt.*;

int rows = 7, cols = 4788;
float taillevisuels;

PFont font1, font2;

String[] liste;
String[][] valeur = new String[cols][rows];
String[][] datas1983 = new String[144][rows];

float[] distdashs = {5, 8, 5, 8};

ArrayList<Visuelhexa> visuels = new ArrayList<Visuelhexa>();

int linesdatas = 0, saveindex = 0, saveindex2 = 0, count = 0;

boolean pressedbing = false;
boolean l1 = true, l2 = false, l3 = false;
boolean do1each = true;
boolean gotoend = false;
// Visuelhexa visuelpressed, visueldragged;
Visudom[] visusdom = new Visudom[8];
Visudom[] visuslayer2 = new Visudom[3];
int indexdrag;
float distance;

int[] nbdomaine = new int[8];
String[] labeldomaine = new String[8];

float cx, cy, angledom = 45;
float hexalayer2cx, hexalayer2cy;

int currentseconde, timer;

int loading;


public void setup(){
    size(1024, 768);
    currentseconde = second();
    background(23, 33, 48);
    font1 = loadFont("latolight.vlw");
    font2 = loadFont("canterlight.vlw");
    parseDatas();
    for (int i = 0; i<datas1983.length; i++){
        // println(datas1983[i][2]); --> print de la colonne des nombres de fois expos\u00e9s
        taillevisuels = map(PApplet.parseFloat(datas1983[i][2]), 0, 43, 4, 43);
        visuels.add(new Visuelhexa (
            random(0+43,width-43), 
            random(0+43, height-43), 
            taillevisuels, 
            datas1983[i][3], 
            datas1983[i][4], 
            datas1983[i][5]
        ));
    }
    visuslayer2[0] = new Visudom(40,"test1");
    visuslayer2[1] = new Visudom(100,"test2");
    visuslayer2[2] = new Visudom(80,"test3");
}


public void draw(){
  // On fait le timer de secondes.
  if (currentseconde != second()){
      currentseconde = second();
      timer++;
  }
  if (l1){
    layer1();
  }
  if (l2){
    layer2();
  }
  if (l3){
    layer3();
  }
  
}


/* -----------------------------

    Permet de recentrer les visuels des domaines lorsque le drag n drop est bon

------------------------------- */


public void fabricdomaine(){
   
   cx = width/2;
   cy = height/2;
   for (int i = 0; i<nbdomaine.length; i++){
     nbdomaine[i] = PApplet.parseInt(map(nbdomaine[i], 1, 44, 40, 120));
     visusdom[i] = new Visudom(nbdomaine[i],labeldomaine[i]);
     if (angledom<=360){
       angledom += angledom;
     }else{
       angledom = 0;
     }
   }
}


/* -----------------------------

    Fonction qui cr\u00e9\u00e9e une ligne en pointill\u00e9 entre les oeuvres du m\u00eame artiste

------------------------------- */

public void lineartiste(){
    saveindex = 0;
        // for (int i = 0; i<visuels.size(); i++){
        //   if (datas1983[i][6].equals("Henri YVERGNIAUX")){
        //       strokeWeight(1);
        //       dashline(visuels.get(i).px, visuels.get(i).py, visuels.get(saveindex).px, visuels.get(saveindex).py, distdashs);
        //       saveindex = i;
        //   }
        //   if (datas1983[i][6].equals("Michel DIEUZAIDE")){
        //       strokeWeight(1);
        //       dashline(visuels.get(i).px, visuels.get(i).py, visuels.get(saveindex2).px, visuels.get(saveindex2).py, distdashs);
        //       saveindex2 = i;
        //   }
          // for (int j = visuels.size()-1; j>=0; j--){
          //   if (datas1983[i][6].equals(datas1983[j][6])){
          //     strokeWeight(1);
          //     dashline(visuels.get(i).px, visuels.get(i).py, visuels.get(j).px, visuels.get(j).py, distdashs);
          //   }
          // }
        //}
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
            visuels.get(i).savex = mouseX;
            visuels.get(i).savey = mouseY;
            indexdrag = i;
            pressedbing = true;
            break;
        }
          
    }

}

public void mouseReleased(){
  pressedbing = false;

  /* ------------ DETECTION DU DOMAINE LORS D'UN DROP D'UNE OEUVRE ----------- */

  for (int i = 0; i<visusdom.length; i++){
    for (int j = 0; j<visuels.size(); j++){
      visusdom[i].detection();
      visuels.get(j).detection();
      
      if (PApplet.parseInt(visuels.get(j).savex) == mouseX && PApplet.parseInt(visuels.get(j).savey) == mouseY){
       // println(int(visuels.get(j).savex) +" "+ mouseX +"   "+ int(visuels.get(j).savey) +" "+ mouseY);
        // image(visuels.get(j).imageoeuvre, visuels.get(j).px, visuels.get(j).py);
        // println(int(visuels.get(j).savex) +" "+ mouseX +"   "+ int(visuels.get(j).savey) +" "+ mouseY);
        // if (visuels.get(j).details){
        //   visuels.get(j).details = false;
        // }else {
        //   visuels.get(j).details = true;
        // }
        visuels.get(j).details = visuels.get(j).details == true ? false : true;

        println(visuels.get(j) + " " + visuels.get(j).details);
        gotoend = true;
        break;
      }

      if (visuels.get(j).bing && visusdom[i].bing && visuels.get(j).domaine.equals(visusdom[i].labeldom)){ // && datas1983[j][3] == visusdom[i].labeldom
        println(visuels.get(j).domaine + " " + visusdom[i].labeldom);
        visuels.remove(j);
        break;
      }
    }
    if (gotoend){
      break;
    }
  }
  gotoend = false;
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

     }
     nbdomaine[0] = peinture;
     nbdomaine[1] = estampe;
     nbdomaine[2] = sculpture;
     nbdomaine[3] = photographie;
     nbdomaine[4] = plr;
     nbdomaine[5] = dessin;
     nbdomaine[6] = o3d;
     nbdomaine[7] = objd;


     labeldomaine[0] = "Peinture";
     labeldomaine[1] = "Estampe";
     labeldomaine[2] = "Sculpture";
     labeldomaine[3] = "Photographie";
     labeldomaine[4] = "Publication, livre, reliure";
     labeldomaine[5] = "Dessin";
     labeldomaine[6] = "Oeuvre en 3 dimensions";
     labeldomaine[7] = "Objet/Design";

     fabricdomaine();

}

/* ------------------------------------------------------

    Fonction qui permet de faire des traits en pointill\u00e9

------------------------------------------------------ */

public void dashline(float x0, float y0, float x1, float y1, float[] spacing){
  float distance = dist(x0, y0, x1, y1);
  float [] xSpacing = new float[spacing.length];
  float [] ySpacing = new float[spacing.length];
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
/* -----------------------------

    Layers de l'application

------------------------------- */


public void layer1(){
    background(23, 33, 48);
    textFont(font2);
    textAlign(RIGHT);
    fill(224, 83, 72);
    text("make the frac", width/2+120, height/3 + 60);
    textSize(100);
    textAlign(CENTER);
    fill(255);
    text("- 1983 -", width/2+60, height/2 + 60);
    if (timer>=5){
      l2 = true;
      l1 = false;
    }
}

public void layer2(){
    if (do1each){
      loading = 0;
      do1each = false;
    }
    background(23, 33, 48);
    textFont(font1);
    fill(255,255);
    textSize(20);
    textAlign(CORNER);
    text("La taille varie en fonction du nombre d'oeuvres dans le domaine", 340, 250);
    text("La taille varie en fonction du nombre de fois expos\u00e9s", 340, 510);
    rect(30, height-50, width - 60, 20);
    fill(255,83,66);
    rect(30, height-50, loading, 20);
    loading++;
    loading = constrain(loading,0,width-60);
    visuslayer2[0].dessinlayer2(180,210);
    visuslayer2[1].dessinlayer2(200,250);
    visuslayer2[2].dessinlayer2(260,220);
    visuels.get(0).px = 180;
    visuels.get(0).py = 485;
    visuels.get(0).dessin();
    visuels.get(1).px = 180;
    visuels.get(1).py = 525;
    visuels.get(1).dessin();
    visuels.get(2).px = 150;
    visuels.get(2).py = 505;
    visuels.get(2).dessin();
    noFill();
    strokeWeight(1);
    stroke(255);
    rect(30, 145, width - 60, 200);
    rect(30, 380, width - 60, 200);
    noStroke();
    fill(23, 33, 48);
    rect(4*width/5, 135, 200, 40);
    rect(4*width/5, 360, 200, 40);
    fill(255);
    textFont(font2);
    textSize(50);
    text("domaines", 4*width/5+50, 163);
    text("oeuvres", 4*width/5+50, 400);
    if (loading >= width-60){
      l1 = false;
      l2 = false;
      l3 = true;
    }
    textSize(60);
    textAlign(CENTER);
    fill(255);
    text("Reconstitue la collection du frac".toUpperCase(), width/2, 80);
}

public void layer3(){
    background(23, 33, 48);
    for (Visudom visueldomaine : visusdom){
      visueldomaine.dessin();
    }
    for (Visuelhexa monvisuel : visuels){
      monvisuel.dessin();
    }
}
class Visudom {
	
	int n; // nombre de segments
	float rayon;
	float angle = 0;
	float distribution;
	PVector centre;
	float [] rayons;
	String labeldom;
	float distance;
	boolean init, bing;

	Visudom (float rayon, String labeldom) {
		this.labeldom = labeldom;
		n = 4;
		this.rayon = rayon;

		rayons = new float [n];

		rayons[0] = random(rayon*0.5f, rayon);
		rayons[1] = rayons[0] - rayon;
		rayons[2] = random(rayon*0.5f, rayon);
		rayons[3] = rayons[2] - rayon;

 		distribution = TWO_PI/n;
		centre = new PVector (random(100, width-100),random(100, height-100));
		init = true;
	}

	public void initialisation(){
		if (init){
			init = false;
		}
	}

	public void dessin(){
		//initialisation();
		angle = 0;
		noStroke();
		fill(255,83,66);
		beginShape();
			for(int i =0; i<n; i++){
			  vertex(centre.x + cos(angle)*rayons[i], centre.y+ sin(angle)*rayons[i]);
			  angle+=distribution;
			}
		endShape(CLOSE);
		fill(255);
		text(labeldom, centre.x, centre.y);
	}

	public void detection(){
        distance = dist(mouseX, mouseY, centre.x, centre.y);
        if (distance <= 30){
            // print( " BING domaine ");
            bing = true;
        }else {
            bing = false;
        }
    }

    public void dessinlayer2(float cx, float cy){
    	angle = 0;
		noStroke();
		fill(255,83,66);
		beginShape();
			for(int i =0; i<n; i++){
			  vertex(cx + cos(angle)*rayons[i], cy+ sin(angle)*rayons[i]);
			  angle+=distribution;
			}
		endShape(CLOSE);
    }
}
class Visuelhexa {
	
	float n;
    float angle;
    float distribution;
	float px, py;
    float ray;
    int indexvisuelbing;
    float distance;
    boolean bing, details;
    String domaine;
    String photoeuvre;
    String nomoeuvre;
    float savex, savey;
    PImage imageoeuvre;
    float widthtext;

	Visuelhexa (float posx, float posy, float rayon, String domaine, String photoeuvre, String nomoeuvre) {
        n = 6;
        angle = 0;
        this.domaine = domaine;
        this.photoeuvre = photoeuvre;
        this.nomoeuvre = nomoeuvre;
        distribution = TWO_PI/n;
        bing = false;
        details = false;
		px = posx;
		py = posy;
		ray = rayon;
        // imageoeuvre = loadImage(photoeuvre);
	}

	public void dessin(){
        angle = 0;
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
        if (details){
            beginShape();
                for(int i =0; i<n; i++){ 
                  vertex(px + cos(angle)*ray*3, py+ sin(angle)*ray*3);
                  angle+=distribution;
                }
            endShape(CLOSE);
            fill(23, 33, 48);
            textAlign(CENTER);
            widthtext = textWidth(nomoeuvre);
            text(nomoeuvre, px, py);
        }
	}

    public void detection(){
        distance = dist(mouseX, mouseY, px, py);
        if (distance <= ray){
            // println( " BING hexa ");
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
