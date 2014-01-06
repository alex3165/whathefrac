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
		rayons[0] = random(rayon*0.5, rayon*0.8);
		rayons[1] = rayons[0] - rayon;
		rayons[2] = random(rayon*0.5, rayon*0.8);
		rayons[3] = rayons[2] - rayon;

 		distribution = TWO_PI/n;
		//centre = new PVector (random(100, width-100),random(100, height-100));
		init = true;
	}

	void dessin(){
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

	boolean detection(){
        distance = dist(mouseX, mouseY, centre.x, centre.y);
        bing = distance <= 30 ? true : false;
        return bing;
    }

    void dessinlayer2(float cx, float cy){
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