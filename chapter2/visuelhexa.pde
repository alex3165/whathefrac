class Visuelhexa {
	
	float n;
    float angle;
    float distribution;
	float px, py;
    float ray;
    int indexvisuelbing;
    float distance;
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

	void dessin(){
        angle = 0;
        smooth(); 
        shapeMode(CENTER);
        fill(255);
        stroke(255,80);
        strokeWeight((ray*0.7));
        beginShape();
        for(int i =0; i<n; i++){ 
          vertex(px + cos(angle)*ray, py+ sin(angle)*ray);
          angle+=distribution;
        }
        endShape(CLOSE);
        // thread("lineartiste()");
	}

    void detection(){
        distance = dist(mouseX, mouseY, px, py);
        if (distance <= ray){
            // println( " BING hexa ");
            bing = true;
        }else {
            bing = false;
        }
    }

}