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

	void dessin(){
        angle = 0;
        smooth(); 
        shapeMode(CENTER);
        fill(255);
        stroke(255,80);
        strokeWeight((ray*0.7));
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
            text("nb exposé : "+int(ray), 5*width/6-55, 80);
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

    boolean detection(){
        return bing = dist(mouseX, mouseY, px, py) <= ray ? true : false;
    }

    void dessinimg(){
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