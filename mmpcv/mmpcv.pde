import com.modestmaps.*;
import com.modestmaps.core.*;
import com.modestmaps.geo.*;
import com.modestmaps.providers.*;

import java.util.*;
import java.io.*;

InteractiveMap map;
Location locationdepart;



void setup(){

    size(1280, 720, OPENGL);
	String mytemplate = "http://{S}.mqcdn.com/tiles/1.0.0/osm/{Z}/{X}/{Y}.png";
    String[] subdomains = new String[] { "otile1", "otile2", "otile3", "otile4" };
    map = new InteractiveMap(this, new TemplatedMapProvider(mytemplate, subdomains));
    locationdepart = new Location(48.11348, -1.67571);
    map.setCenterZoom(locationdepart, 8); 
	
    parseLocations("coordonnees.csv");

}

void draw(){
	map.draw();	

	// for (int i = 0; i<meslocations.size(); i++){
	// 	p2farr.add(map.locationPoint(meslocations.get(i)));
	// 	fill(0);
	// 	ellipse(p2farr.get(i).x, p2farr.get(i).y, 2, 2);
	// }
}

void parseLocations(String filename){

	String[] coordstext = loadStrings(filename);
	int filelong = coordstext.length;

	ArrayList<String> malistcorrect = new ArrayList<String>();
	ArrayList<Float> mesnumbers = new ArrayList<Float>();

	String[] temporaire;
	float[] tempfloat;

	String temp2;
	float temp3;

	float [] coords;

	int a = 0;

	for (int i = 0; i<filelong; i++){

		temporaire = split(coordstext[i],"|");

		for (int j = 0; j<temporaire.length; j++){
			temp2 = temporaire[j];
			if (temp2 != null){
				malistcorrect.add(trim(temp2));
			}
		}
	}

	for (int i = 0; i<malistcorrect.size(); i++){

		tempfloat = float(split(malistcorrect.get(i),","));
		// if (Float.isNaN(tempfloat[1])){
		// 		break;
		// }
		for (int j = 0; j<tempfloat.length; j++){
			temp3 = tempfloat[j];
			// mesnumbers.add(temp3);
				mesnumbers.add(temp3);
		}
	}

	// for (int i = 0; i<mesnumbers.size(); i++){
	// 	if (Float.isNaN(mesnumbers.get(i))){
	// 		mesnumbers.remove(i);
	// 		mesnumbers.remove(i-1);
	// 	}
	// }

	// for (int i = 0; i<malistcorrect.size(); i++){
 //    	if (malistcorrect.get(i) == null){
 //    		malistcorrect.remove(i);
 //    	}
    	// println(malistcorrect.get(i).compareTo("null"));
    		// println(malistcorrect.get(i));
    //}


    // for (int i = 0; i<mesnumbers.size(); i++){
    // 	println(mesnumbers.get(i));
    // }


    // Location test = new Location (malistcorrect.get(0));
    ArrayList<Location> meslocations = new ArrayList<Location>();
    ArrayList<Point2f> p2farr = new ArrayList<Point2f>();

	for (int i = 0; i<mesnumbers.size()-1; i++){
		meslocations.add(new Location(mesnumbers.get(i), mesnumbers.get(i+1)));
	}
	// for (int i = 0; i<meslocations.size(); i++){
	// 	p2farr.add(map.locationPoint(meslocations.get(i)));
	// }
	// return meslocations;
	for (int i = 0; i<meslocations.size(); i++){
		p2farr.add(map.locationPoint(meslocations.get(i)));
		fill(0);
		ellipse(p2farr.get(i).x, p2farr.get(i).y, 2, 2);
	}
}

	
	// location = new Location(51.500, -0.126);
    // Point2f p = map.locationPoint(location);

