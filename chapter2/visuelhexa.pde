class Visuelhexa {
	
	float n=6;
    float angle = 0;
    float distribution = TWO_PI/n;
	float px, py, ray;
    int indexvisuelbing;

    boolean bing = false, notif = false;

	Visuelhexa (float posx, float posy, float rayon) {
		px = posx;
		py = posy;
		ray = rayon;
	}

	void dessin(){
        smooth(); 
        shapeMode(CENTER);
        fill(255);
        stroke(255,80);
        strokeWeight((ray*0.7));
        beginShape();
        for(int i =0; i<8; i++){ 
          vertex(px + cos(angle)*ray, py+ sin(angle)*ray);
          angle+=distribution;
        }
        endShape(CLOSE);
        notification();
	}

    void detection(){
        float distance = dist(mouseX, mouseY, px, py);
        if (distance <= ray){
            // println( " BING ");
            bing = true;
        }else {
            bing = false;
        }
    }

    void notification(){
        if (mousePressed && bing){
            notif = true;
        }
    }
}