void setup() {

size(1000, 640, P3D);
background(0,29,58);

}

void draw () {
carre1();
carre2();
//ellipse(500, 320, 55, 55);

}

void carre1() {
noStroke();
beginShape();
fill(255,83,66);
vertex(460, 200, 0, 0);
vertex(590, 195, 100, 0);
vertex(540, 300, 100, 100);
vertex(470, 300, 0, 100);
endShape();
}

void carre2() {
noStroke();
beginShape();
fill(255,83,66);
vertex(400, 220, 0, 0);
vertex(330, 240, 100, 0);
vertex(440, 320, 100, 100);
vertex(450, 310, 0, 100);
endShape();
}