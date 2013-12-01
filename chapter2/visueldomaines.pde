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
	
	Visudom (float rayon, String labeldom) {
		this.labeldom = labeldom;
		n = 4;
		this.rayon = rayon;
		rayons = new float [n];
		rayons[0] = random(rayon*0.2, rayon);
		rayons[1] = rayon - rayons[0];
		rayons[2] = random(rayon*0.2, rayon);
		rayons[3] = rayon - rayons[2];
 		distribution = TWO_PI/n;
		centre = new PVector (random(100, width-100),random(100, height-100));
	}

	void dessin(){
		
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
}