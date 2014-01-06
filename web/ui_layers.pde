/* -----------------------------

    Layers de l'application

------------------------------- */


void layer1(){
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

void layer2(){
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
    text("La taille varie en fonction du nombre de fois exposés", 340, 510);
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
    if (loading >= width-60){
      l1 = false;
      l2 = false;
      l3 = true;
      l4 = false;
      loading = 0;
      do1each = true;
    }
    textSize(60);
    textAlign(CENTER);
    fill(255);
    text("Reconstitue la collection du frac".toUpperCase(), width/2, 80);
}

void layer3(){
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
       totalmax += int(monvisuel.ray);   
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

void layer4(){
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