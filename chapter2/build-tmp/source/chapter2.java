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

PFont font1, font2;

/* ---------------------- DATAS ------------------------- */
int rows = 7, cols = 4788;
String[] liste;
String[][] valeur = new String[cols][rows];
String[][] datas1983 = new String[144][rows];
int linesdatas = 0;

/*--------------------------------------------------------*/

/* ---------------------- HEXAGONES LAYER 3 ------------------------- */

ArrayList<Visuelhexa> visuels = new ArrayList<Visuelhexa>();
ArrayList<Visuelhexa> visuelsafter = new ArrayList<Visuelhexa>();
float hexalayer2cx, hexalayer2cy;
float taillevisuels;
float randx, randy;

/*------------------------------------------------------------------*/


int saveindex = 0;

boolean pressedbing = false;
boolean l1 = false, l2 = true, l3 = false, l4 = false;
boolean do1each = true;
boolean gotoend = false;
boolean do1eachtimer = true;
boolean verif = true, verification = true;
// Visuelhexa visuelpressed, visueldragged;

Visudom[] visuslayer2 = new Visudom[3];
int indexdrag, indexdrag2;
float distance;


/* ---------------------- DOMAINES LAYER 3 ------------------------- */

int[] nbdomaine = new int[8];
String[] labeldomaine = new String[8];
Visudom[] visusdom = new Visudom[8];
float cx, cy, distridomaine, angledom, rayondom = 260, calcrayon;
float posyvisu1, positionvisu1;
float[] xellipsevisu1 = new float[8];
float sensxvisu1 = 1;

/*------------------------------------------------------------------*/

int currentseconde, timer;

float loading;

int points, totalmax, scorefinal;

public void setup(){
    size(1024, 768);
    posyvisu1 = height/9;
    currentseconde = second();
    background(23, 33, 48);
    font1 = loadFont("latolight.vlw");
    font2 = loadFont("canterlight.vlw");
    parseDatas();
    for (int i = 0; i<datas1983.length; i++){
        // println(datas1983[i][2]); --> print de la colonne des nombres de fois expos\u00e9s
        taillevisuels = map(PApplet.parseFloat(datas1983[i][2]), 0, 43, 4, 43);
        randx = random(0+43,width-43);
        randy = random(0+43, height-43);
        if (randx>width-400 && randy<200) {
          randx = random(0+43,width-400);
          randy = random(200, height-43);
        }

        visuels.add(new Visuelhexa (
            randx, 
            randy, 
            taillevisuels, 
            datas1983[i][3], 
            datas1983[i][4], 
            datas1983[i][5],
            datas1983[i][6]
        ));
        //println(datas1983[i][6]);
    }

    calcrayon = rayondom / visuels.size();

    visuslayer2[0] = new Visudom(40,"test1",30,30);
    visuslayer2[1] = new Visudom(100,"test2",30,30);
    visuslayer2[2] = new Visudom(80,"test3",30,30);
}


public void draw(){

  if (l1){
    timing();
    layer1();
  }
  if (l2){
    timing();
    layer2();
  }
  if (l3){
    if (do1eachtimer) {
      timer = 0;
      do1eachtimer = false;
    }
    timing();
    layer3();
  }
  if (l4){
    layer4();
    datavizlayer4();
  }
}


public void timing(){
  if (currentseconde != second()){
      currentseconde = second();
      timer++;
      loading++;
  }
}

public void keyPressed() {
  if (key == 'b' || key == 'B') {
    for (int i = 0; i < visuels.size(); ++i) {
      points = points + PApplet.parseInt(visuels.get(i).ray);
      visuels.remove(i);
      rayondom =  rayondom >= 60 ? rayondom - calcrayon : random(50, 60);
      println(rayondom);
        for (int k = 0; k<visusdom.length; k++){
           visusdom[k].centre.x = width/2 + cos(angledom)*rayondom;
           visusdom[k].centre.y = height/2 + sin(angledom)*rayondom;
           angledom += distridomaine;
         } // on recentre la position des domaines 
         angledom = 0;
    }
    //println(visuels.size());
  }else if (key == 'd' || key == 'D') {
    loading = loading + 10;
  }
}


/* -----------------------------

    Permet de recentrer les visuels des domaines lorsque le drag n drop est bon

------------------------------- */


public void fabricdomaine(){

   distridomaine = TWO_PI/8;
   for (int i = 0; i<nbdomaine.length; i++){
     cx = width/2 + cos(angledom)*rayondom;
     cy = height/2 + sin(angledom)*rayondom;
     angledom += distridomaine;
     nbdomaine[i] = PApplet.parseInt(map(nbdomaine[i], 1, 44, 40, 120));
     visusdom[i] = new Visudom(nbdomaine[i],labeldomaine[i],cx,cy);
   }
   angledom = 0;
    //println("cx : " +visusdom[7].centre.x+ " cy : "+visusdom[7].centre.y);
}


/* -----------------------------

    Fonction qui cr\u00e9\u00e9e une ligne en pointill\u00e9 entre les oeuvres du m\u00eame artiste

------------------------------- */

public void lineartiste(){
    //saveindex = 0;

    /*
        for (int i = 0; i<visuels.size(); i++){
          for (int j = visuels.size()-1; j>=0; j--){
            //println(visuels.get(i).artiste+ "  = ?  " + visuels.get(j).artiste);
            if (visuels.get(i).artiste.equals(visuels.get(j).artiste) && !visuels.get(i).visudashline && !visuels.get(j).visudashline){
              strokeWeight(1);
              println("YES");
              dashline(visuels.get(i).px, visuels.get(i).py, visuels.get(j).px, visuels.get(j).py, distdashs);
              visuels.get(i).visudashline = true;
              visuels.get(j).visudashline = true;
            }
          }
        }
    */
}


public void datavizlayer4(){
  // for (int i = 0; i < visusdom.length; ++i) {
  //   if (visusdom[i].details) {
  //     for (int j = 0; j < visuels.size(); ++j) {
  //       if (visuels.get(j).domaine.equals()) {
          
  //       }
  //     }
  //   }
  // }
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
        //visuels.get(i).detection();
        if (visuels.get(i).detection()){
            visuels.get(i).savex = mouseX;
            visuels.get(i).savey = mouseY;
            indexdrag = i;
            pressedbing = true;
            break;
        }
          
    }

    /* ------------ DETECTION DU CLIC POUR LES DOMAINES ----------- */

    for (int i = 0; i<visusdom.length; i++){
        //visuels.get(i).detection();
        if (visusdom[i].detection()){
            visusdom[i].savex = mouseX;
            visusdom[i].savey = mouseY;
            break;
        }
          
    }

}

public void mouseReleased(){
  pressedbing = false;

  /* ------------ DETECTION DU DOMAINE LORS D'UN DROP D'UNE OEUVRE ----------- */

  for (int i = 0; i<visusdom.length; i++){

    if (visusdom[i].savex == mouseX && visusdom[i].savey == mouseY && l4) {
      visusdom[i].details = visusdom[i].details == true ? false : true;
    }

    for (int j = 0; j<visuels.size(); j++){
      //visusdom[i].detection();
      
      if (PApplet.parseInt(visuels.get(j).savex) == mouseX && PApplet.parseInt(visuels.get(j).savey) == mouseY){
        // AFFICHAGE IMAGE SI POSSIBLE
        visuels.get(j).details = visuels.get(j).details == true ? false : true;
        //println(visuels.get(j) + " " + visuels.get(j).details);
        gotoend = true;
        break;
      }

      if (visuels.get(j).detection() && visusdom[i].detection() && visuels.get(j).domaine.equals(visusdom[i].labeldom)){
        //println(visuels.get(j).domaine + " " + visusdom[i].labeldom);
        saveindex = j;
        visuelsafter.add(visuels.get(j));
        //thread("anim_disparition");
        points += PApplet.parseInt(visuels.get(j).ray);

        visuels.remove(j);

        rayondom =  rayondom >= 50 ? rayondom - calcrayon : random(46, 50);

        for (int k = 0; k<visusdom.length; k++){
           visusdom[k].centre.x = width/2 + cos(angledom)*rayondom;
           visusdom[k].centre.y = height/2 + sin(angledom)*rayondom;
           angledom += distridomaine;
         } // on recentre la position des domaines 
         angledom = 0;
        break;
      }
    }
    if (gotoend){
      break;
    }
  }
  gotoend = false;
}

public void anim_disparition(){
    //println(visuels.get(saveindex).ray);
    // do{
    //     if (visuels.get(saveindex).ray - 0.8 < 0) {
    //         visuels.get(saveindex).ray = 0;
    //         //println(verification);
    //         if (verification) {
    //           visuels.remove(saveindex);
    //           verification = false;
    //         }
    //     }else {
    //       visuels.get(saveindex).ray = visuels.get(saveindex).ray - 0.8;
    //       println(visuels.get(saveindex).ray);
    //       delay(50);
    //     }
    // }while (visuels.get(saveindex).ray >= 0);
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
      l3 = false;
      l4= false;
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
    rect(30, 400, width - 60, 200);
    noStroke();
    fill(23, 33, 48);
    rect(4*width/5, 135, 200, 40);
    rect(4*width/5, 390, 200, 40);
    fill(255);
    textFont(font2);
    textSize(50);
    text("domaines", 4*width/5+80, 163);
    text("oeuvres", 4*width/5+80, 416);
    // if (loading >= width-60){
    //   l1 = false;
    //   l2 = false;
    //   l3 = true;
    //   l4 = false;
    //   loading = 0;
    //   do1each = true;
    // }
    textSize(60);
    textAlign(CENTER);
    fill(255);
    text("Reconstitue la collection du frac".toUpperCase(), width/2, 80);
}

public void layer3(){
    if (do1each){
      loading = 0;
      do1each = false;
    }
    background(23, 33, 48);
    for (Visudom visueldomaine : visusdom){
      visueldomaine.dessin();
    }
    for (Visuelhexa monvisuel : visuels){
      monvisuel.dessin();
      if (verif) {
       totalmax += PApplet.parseInt(monvisuel.ray);   
      }
    }
    verif=false;
    if (visuels.size() == 0 || loading >= width/3) {
        do1each = true;
        l1 = false;
        l2 = false;
        l3 = false;
        l4 = true;
    }
    textAlign(CORNER);
    textFont(font1, 18);
    fill(89, 238, 167);
    text("score :   "+points, 30, 30);

    noStroke();
    fill(255,255);
    rect(30, 50, width/3, 20);
    fill(255,83,66);
    rect(30, 50, loading, 20);
    loading = constrain(loading,0,width/3);
    //lineartiste();
    
    /* ----------------- BARRE DE CHARGEMENT -------------------
    noStroke();
    fill(255);
    rect(30, 30, width/2, 20);
    fill(255,83,66);
    rect(30, 30, loading, 20);
    loading = loading + 0.05;
    loading = constrain(loading,0,width/2);
    ------------------------------------------------------------*/

}

public void layer4(){
    if (do1each) {
        scorefinal = (points*100)/totalmax;
        do1each = false;
    }
    background(23, 33, 48);
    fill(255);
    textFont(font2);
    textSize(60);
    textAlign(CORNER);
    println(scorefinal);
    text("- 1983 -", width/2-40, 80);
    textFont(font1, 18);
    fill(89, 238, 167);
    text(scorefinal+" %", 30, 30);
    for (Visudom visueldomaine : visusdom){
      visueldomaine.dessin();
    }
    for (int i = 0; i < nbdomaine.length; i++) {
        stroke(255);
        positionvisu1 = positionvisu1 + posyvisu1;
        textSize(12);
        text(labeldomaine[i],10,positionvisu1);
        line(160, positionvisu1, 160 + nbdomaine[i], positionvisu1);
    }
    positionvisu1 = 0;
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
	float savex, savey;
	boolean init, bing, details;

	Visudom (float rayon, String labeldom, float centx, float centy) {
		this.labeldom = labeldom;
		n = 4;
		this.rayon = rayon;
		centre = new PVector(centx,centy);
		rayons = new float [n];
		rayons[0] = random(rayon*0.5f, rayon*0.8f);
		rayons[1] = rayons[0] - rayon;
		rayons[2] = random(rayon*0.5f, rayon*0.8f);
		rayons[3] = rayons[2] - rayon;

 		distribution = TWO_PI/n;
		//centre = new PVector (random(100, width-100),random(100, height-100));
		init = true;
	}

	public void dessin(){
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
			textFont(font1);
			textSize(13);
			text(labeldom, centre.x, centre.y);
			if (details) {
				angle = 0;
				noFill();
				stroke(255);
				ellipse(centre.x, centre.y, rayon + 40, rayon + 40);
				// for (Visuelhexa visuafter : visuelsafter) {
				// 	println(visuafter.domaine+" "+labeldom);
    //     			if (visuafter.domaine.equals(labeldom)) {
    //     				visuafter.dessin();
    //     			}
    // 			}
			}
	}

	public boolean detection(){
        distance = dist(mouseX, mouseY, centre.x, centre.y);
        bing = distance <= 30 ? true : false;
        return bing;
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
    boolean bing, details, visudashline, oneach;
    String domaine, photoeuvre, nomoeuvre, artiste;
    float savex, savey;
    PImage imageoeuvre;
    float widthtext;

	Visuelhexa (float posx, float posy, float rayon, String domaine, String photoeuvre, String nomoeuvre, String artiste) {
        n = 6;
        angle = 0;
        this.artiste = artiste;
        this.domaine = domaine;
        this.photoeuvre = photoeuvre;
        this.nomoeuvre = nomoeuvre;
        distribution = TWO_PI/n;
        bing = false;
        details = false;
        visudashline = false;
        oneach = true;
		px = posx;
		py = posy;
		ray = rayon;
	}

	public void dessin(){
        angle = 0;
        smooth(); 
        shapeMode(CENTER);
        fill(255);
        stroke(255,80);
        strokeWeight((ray*0.7f));
        textAlign(CENTER);
        if (details){
            fill(32, 44, 61);
            noStroke();
            rect(4*width/6 - 100, 30, 400, 200);
            stroke(89,239,167,80);
            textAlign(CENTER);
            beginShape();
                for(int i =0; i<n; i++){ 
                  vertex(px + cos(angle)*ray, py+ sin(angle)*ray);
                  angle+=distribution;
                }
            endShape(CLOSE);
            dessinimg();
            fill(255);
            textAlign(CORNER);
            textFont(font1);
            textSize(12);
            widthtext = nomoeuvre.length();
            text(nomoeuvre, 5*width/6-55, 50);
            text("nb expos\u00e9 : "+PApplet.parseInt(ray), 5*width/6-55, 80);
            text("Artiste : "+artiste, 5*width/6-55, 110);
        }else {
            beginShape();
                for(int i =0; i<n; i++){ 
                  vertex(px + cos(angle)*ray, py+ sin(angle)*ray);
                  angle+=distribution;
                }
            endShape(CLOSE);
        }

    }

    public boolean detection(){
        return bing = dist(mouseX, mouseY, px, py) <= ray ? true : false;
    }

    public void dessinimg(){
        if (oneach){
            try {
                imageoeuvre = loadImage(photoeuvre);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        oneach = false;
        if (imageoeuvre != null){
            image(imageoeuvre, 4*width/6 - 100, 30,200,200);
        }else{
            println("IMAGE NON DISPONIBLE");
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
