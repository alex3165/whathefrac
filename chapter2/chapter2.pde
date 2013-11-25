int rows = 7, cols = 4788;

String[][] valeur = new String[cols][rows];
String[] liste;

String[][] datas1983;

// Visuelhexa test;

void setup(){
    size(1280, 720);
    background(23, 33, 48);
    liste = loadStrings("datas.csv");

    for (int i=0; i<liste.length; i++) {
      valeur[i] = split(liste[i], ";");
    }
    println(valeur[4787][6]); // Print dernière cellule de la dernière colonne

    // test = new Visuelhexa (width/2, height/2, 40);
    for (int i = 1; i<cols-1; i++){
        if (valeur[i][1].equals("1983")){
            for (int j = 1; j<rows-1; j++){
                datas1983.add(valeur[i][j]);
            }
            
        }
    }
    println(datas1983);
}

void draw(){
    background(23, 33, 48);
    // test.dessin();
}