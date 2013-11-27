int rows = 7, cols = 4788;
float taillevisuels;

String[] liste;
String[][] valeur = new String[cols][rows];
String[][] datas1983 = new String[144][rows];

float[] distdashs = {5, 8, 5, 8};

ArrayList<Visuelhexa> visuels = new ArrayList<Visuelhexa>();
ArrayList<ArrayList<String>> domaine = new ArrayList();
ArrayList<String> domainetemp = new ArrayList();

int linesdatas = 0, saveindex = 0, saveindex2 = 0, count = 0;
boolean pressedbing = false;

// PVector[] posrect = new PVector[datas1983.length];

Visuelhexa visuelpressed, visueldragged;

int indexdrag;



void setup(){
    size(1280, 720);
    background(23, 33, 48);
    parseDatas();
    for (int i = 0; i<datas1983.length; i++){
        // println(datas1983[i][2]); --> print de la colonne des nombres de fois exposés
        taillevisuels = map(float(datas1983[i][2]), 0, 43, 4, 43);
        visuels.add(new Visuelhexa (random(0+43,width-43), random(0+43, height-43), taillevisuels));
    }
}


void draw(){
    background(23, 33, 48);
    for (Visuelhexa monvisuel : visuels){
        monvisuel.dessin();

    }
    lineartiste();
}


void lineartiste(){
        for (int i = 0; i<visuels.size(); i++){
        if (datas1983[i][6].equals("Henri YVERGNIAUX")){
            strokeWeight(1);
            //line(visuels.get(i).px, visuels.get(i).py, visuels.get(saveindex).px, visuels.get(saveindex).py);
            dashline(visuels.get(i).px, visuels.get(i).py, visuels.get(saveindex).px, visuels.get(saveindex).py, distdashs);
            saveindex = i;
        }
        if (datas1983[i][6].equals("Michel DIEUZAIDE")){
            strokeWeight(1);
            //line(visuels.get(i).px, visuels.get(i).py, visuels.get(saveindex2).px, visuels.get(saveindex2).py);
            dashline(visuels.get(i).px, visuels.get(i).py, visuels.get(saveindex2).px, visuels.get(saveindex2).py, distdashs);
            saveindex2 = i;
            // println(i);
        }
    }
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
        visuels.get(i).detection();
        if (visuels.get(i).bing){
            indexdrag = i;
            pressedbing = true;
            break;
        }
    }
    
    /* ------------ DETECTION DU CLIC POUR LA NOTIFICATION ----------- */



}

void mouseReleased(){
    pressedbing = false;
    for (int i = 0; i<visuels.size(); i++){
        if (visuels.get(i).bing){
            fill(255);
            rect(visuels.get(i).px, visuels.get(i).py, 60, 30);
        }
    }
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
    for (int i = 0; i<datas1983.length; i++){
        if (i == 0){
            domainetemp.add(datas1983[i][3]);
            domaine.add(0,domainetemp);// datas1983[i][3]
            println(domaine.get(0));
        }
    }
}

/* ------------------------------------------------------

    Fonction qui permet de faire des traits en pointillé

------------------------------------------------------ */

void dashline(float x0, float y0, float x1, float y1, float[] spacing){ 
  float distance = dist(x0, y0, x1, y1); 
  float [ ] xSpacing = new float[spacing.length]; 
  float [ ] ySpacing = new float[spacing.length]; 
  float drawn = 0.0;  // amount of distance drawn 
 
  if (distance > 0) 
  { 
    int i; 
    boolean drawLine = true; // alternate between dashes and gaps 

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
      /* Add distance "drawn" by this line or gap */ 
      drawn = drawn + mag(xSpacing[i], ySpacing[i]); 
      i = (i + 1) % spacing.length;  // cycle through array 
      drawLine = !drawLine;  // switch between dash and gap 
    } 
  } 
}