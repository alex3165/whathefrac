String[][] valeur = new String[5000][80];
String[] liste;
int j = 0;

void setup(){
    liste = loadStrings("data.csv");
    for (int i=0; i<liste.length; i++) {
      valeur[i] = split(liste[i], ";");
    }

    for (int i = 0; i<valeur.length; i++){
        if (valeur[i][0].equals("Oeuvre en 3 dimensions")){
            // j++;
            // println(j);
        }
    }
}