import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.opengl.*; 
import com.modestmaps.*; 
import com.modestmaps.core.*; 
import com.modestmaps.geo.*; 
import com.modestmaps.providers.*; 
import java.util.Random; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class mmpcv extends PApplet {









InteractiveMap map;
ArrayList<Location> locations = new ArrayList<Location>();

Random randomGenerator = new Random();

public void setup() {
  size(1280, 720, OPENGL);
  smooth();

  String template = "http://{S}.mqcdn.com/tiles/1.0.0/osm/{Z}/{X}/{Y}.png";
  String[] subdomains = new String[] { "otile1", "otile2", "otile3", "otile4" }; // optional
  map = new InteractiveMap(this, new TemplatedMapProvider(template, subdomains));
  parseLocations("coordonnees.csv");
  map.setCenterZoom(new Location(48.11348f, -1.67571f), 8);

  addMouseWheelListener(new java.awt.event.MouseWheelListener() {
    public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
      mouseWheel(evt.getWheelRotation());
    }
  });
  
  parseLocations("coordonnees.csv");
}

public void draw() {
  background(0);
  map.draw();
  handleKeys();
  
  Point2f p = null;

  for (Location loc : locations) {
    p = map.locationPoint(loc);
    if (p != null) {
      //RAINBOWWW HAHAHA
      fill(randomGenerator.nextInt(255), randomGenerator.nextInt(255), randomGenerator.nextInt(255));
      ellipse(p.x, p.y, 10, 10);
    }
  }
}

public void parseLocations(String filename) {
  String[] lines = loadStrings(filename);
  String[] coords;
  String tmp;
  String[] values;

  for (String line : lines) {
    coords = split(line, "|");
    for (String coord : coords) {
      tmp = trim(coord);
      if (!tmp.isEmpty()) {
        values = split(tmp, ",");
        if (values.length == 2 && !values[0].equals("null") && !values[1].equals("null")) {
          locations.add(new Location(Float.parseFloat(values[1]), Float.parseFloat(values[0])));
        }
      }
    }
  }
}

public void handleKeys() {
  if (keyPressed) {
    if (key == CODED) {
      if (keyCode == LEFT) {
        map.tx += 5.0f/map.sc;
      }
      else if (keyCode == RIGHT) {
        map.tx -= 5.0f/map.sc;
      }
      else if (keyCode == UP) {
        map.ty += 5.0f/map.sc;
      }
      else if (keyCode == DOWN) {
        map.ty -= 5.0f/map.sc;
      }
    }
    else if (key == '+' || key == '=') {
      map.sc *= 1.05f;
    }
    else if (key == '_' || key == '-' && map.sc > 2) {
      map.sc *= 1.0f/1.05f;
    }
  }
}

public void mouseDragged() {
    map.mouseDragged();
}

public void keyReleased() {
  if (key == 's' || key == 'S') {
    save("screenshot.png");
  }
  else if (key == ' ') {
    map.sc = 2.0f;
    map.tx = -128;
    map.ty = -128;
  }
}

public void mouseWheel(int delta) {
  float sc = 1.0f;
  if (delta < 0) {
    sc = 1.05f;
  }
  else if (delta > 0) {
    sc = 1.0f/1.05f;
  }
  float mx = mouseX - width/2;
  float my = mouseY - height/2;
  map.tx -= mx/map.sc;
  map.ty -= my/map.sc;
  map.sc *= sc;
  map.tx += mx/map.sc;
  map.ty += my/map.sc;
}

    
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "mmpcv" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
