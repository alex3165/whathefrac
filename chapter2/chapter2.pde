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
boolean l1 = false, l2 = false, l3 = true, l4 = false;
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

void setup(){
    size(1024, 768);
    posyvisu1 = height/9;
    currentseconde = second();
    background(23, 33, 48);
    font1 = loadFont("latolight.vlw");
    font2 = loadFont("canterlight.vlw");
    parseDatas();
    for (int i = 0; i<datas1983.length; i++){
        // println(datas1983[i][2]); --> print de la colonne des nombres de fois exposés
        taillevisuels = map(float(datas1983[i][2]), 0, 43, 4, 43);
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


void draw(){

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


void timing(){
  if (currentseconde != second()){
      currentseconde = second();
      timer++;
      loading++;
  }
}

void keyPressed() {
  if (key == 'b' || key == 'B') {
    for (int i = 0; i < visuels.size(); ++i) {
      points = points + int(visuels.get(i).ray);
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


void fabricdomaine(){

   distridomaine = TWO_PI/8;
   for (int i = 0; i<nbdomaine.length; i++){
     cx = width/2 + cos(angledom)*rayondom;
     cy = height/2 + sin(angledom)*rayondom;
     angledom += distridomaine;
     nbdomaine[i] = int(map(nbdomaine[i], 1, 44, 40, 120));
     visusdom[i] = new Visudom(nbdomaine[i],labeldomaine[i],cx,cy);
   }
   angledom = 0;
    //println("cx : " +visusdom[7].centre.x+ " cy : "+visusdom[7].centre.y);
}


/* -----------------------------

    Fonction qui créée une ligne en pointillé entre les oeuvres du même artiste

------------------------------- */

void lineartiste(){
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


void datavizlayer4(){
  // for (int i = 0; i < visusdom.length; ++i) {
  //   if (visusdom[i].details) {
  //     for (int j = 0; j < visuels.size(); ++j) {
  //       if (visuels.get(j).domaine.equals()) {
          
  //       }
  //     }
  //   }
  // }
}


void mouseDragged(){
    if (pressedbing){
        visuels.get(indexdrag).px = mouseX;
        visuels.get(indexdrag).py = mouseY;
    }
}

void mousePressed(){

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

void mouseReleased(){
  pressedbing = false;

  /* ------------ DETECTION DU DOMAINE LORS D'UN DROP D'UNE OEUVRE ----------- */

  for (int i = 0; i<visusdom.length; i++){

    if (visusdom[i].savex == mouseX && visusdom[i].savey == mouseY && l4) {
      visusdom[i].details = visusdom[i].details == true ? false : true;
    }

    for (int j = 0; j<visuels.size(); j++){
      //visusdom[i].detection();
      
      if (int(visuels.get(j).savex) == mouseX && int(visuels.get(j).savey) == mouseY){
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
        points += int(visuels.get(j).ray);

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

void anim_disparition(){
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

void parseDatas(){


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

    Fonction qui permet de faire des traits en pointillé

------------------------------------------------------ */

void dashline(float x0, float y0, float x1, float y1, float[] spacing){
  float distance = dist(x0, y0, x1, y1);
  float [] xSpacing = new float[spacing.length];
  float [] ySpacing = new float[spacing.length];
  float drawn = 0.0;

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