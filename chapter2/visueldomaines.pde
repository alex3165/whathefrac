class Visudom
{
	
	float n; // nombre de segments
	float rayon;
	float angle = 0;
	float distribution;
	PVector centre;
	
	Visudom (float rayon) {
		n = 4;
		distribution = TWO_PI/n;
		this.rayon = rayon;
		centre = new PVector (random(100, width-100),random(100, height-100));;
	}

	void dessin(){
		fill(255,83,66);
		noStroke();
		beginShape();
			for(int i =0; i<n; i++){ 
			  vertex(centre.x + cos(angle)*rayon, centre.y+ sin(angle)*rayon);
			  angle+=distribution;
			}
		endShape(CLOSE);
	}
}