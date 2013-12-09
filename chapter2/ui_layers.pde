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

void layer3(){
    background(23, 33, 48);
    for (Visudom visueldomaine : visusdom){
      visueldomaine.dessin();
    }
    for (Visuelhexa monvisuel : visuels){
      monvisuel.dessin();
    }
}