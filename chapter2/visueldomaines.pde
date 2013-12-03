class Visudom
{
	
	int n; // nombre de segments
	float rayon;
	float angle = 0;
	float distribution;
	PVector centre;
	float [] rayons;
	float rayonrandx, rayonrandy;
	String labeldom;
	float distance;
	boolean init, bing;
	
	Visudom (float rayon, String labeldom) {
		this.labeldom = labeldom;
		n = 4;
		this.rayon = rayon;
		rayons = new float [n];
		rayons[0] = random(rayon*0.2, rayon);
		rayons[1] = rayons[0] - rayon;
		rayons[2] = random(rayon*0.2, rayon);
		rayons[3] = rayons[2] - rayon;
 		distribution = TWO_PI/n;
		centre = new PVector (random(100, width-100),random(100, height-100));
		init = true;
	}

	void initialisation(){
		if (init){
			init = false;
		}
	}

	void dessin(){
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

	void detection(){
        distance = dist(mouseX, mouseY, centre.x, centre.y);
        if (distance <= 30){
            // print( " BING domaine ");
            bing = true;
        }else {
            bing = false;
        }
    }
}