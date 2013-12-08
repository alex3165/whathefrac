
int a = 40;
PVector mycentre;
boolean numbers = true;


void setup() {

size(1000, 640);
background(0,29,58);
mycentre = new PVector(random(0, width-180),random(100, height-100));
}

void draw () {

}




void visueldom(){
	float n=4;// nombre de segments
	float rayon = 40;
	float angle = 0;
	float distribution = TWO_PI/n;
	float x= 50;
	float y= 50;
	smooth();
	beginShape();
	for(int i =0; i<9; i++){ 
	  vertex(x + cos(angle)*rayon, y+ sin(angle)*rayon);
	  angle+=distribution;
	}
	endShape(CLOSE);
}



void carre(int aire, PVector centre) {

	
		float xvertex = random(centre.x-aire, centre.x);
		float x2vertex = random(centre.x, centre.x+aire);
		float d1length = x2vertex - xvertex;
		float d2length = (2*aire)/d1length;
		float yvertex = random(0, d2length*0.8);
		float yvertex2 = d2length - yvertex;
	

		noStroke();

		fill(255,83,66);

		beginShape();
		vertex(centre.x, yvertex);
		vertex(xvertex, centre.y);
		vertex(centre.x, yvertex2);
		vertex(x2vertex, centre.y);
		endShape();

}