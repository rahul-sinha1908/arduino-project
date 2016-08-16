import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.serial.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class TextRead extends PApplet {

/*CHANGES TO BE DONE ON
  1.In void setup()-DEFINE THE COMMUNICATION PORT
  2. IN sendLinNum() FUNCTION- DEFINE THE LINE TO GIVE INPUT TO ARDUINO
  3. IN draw() FUNCTION- IN THE FALSE CASE OF SIMILARITY ADD COMPORT STATEMENT
  4. IN draw() FUNCTION-SHORT OUT TH PROBLEM OR CHECKING EQUALITY OF TWO ARRAY
*/



Serial comPort; //The com port used between the computer and Arduino
String comPortString;
int counter=0; // Helps to keep track of values sent.
int numItems=0; //Keep track of the number of values in text file
String[] txtLines= loadStrings("F:/Rahul/ScienceExhibution/Data.txt");
int numLines=txtLines.length;
String[][] txt=new String[numLines][];
int lineN=0;

public void setup(){
  //comPort = new Serial(this, Serial.list()[0], 9600);
  //comPort.bufferUntil('\n'); //Generate a SerialEvent when a newline is received
  size(500,500);
  background(100,180,200); //Start with a Red background
  int x=0;
  for(int i=0;i<numLines;i++){
    txt[i] = splitTokens(txtLines[i], ",");
    int len = txt[i].length;
    for(int j=0; j<len; j++){
      textSize(16);
      fill(0,0,0);
      text(txt[i][j],10+10*j,16+x); 
    }
    x=x+16;
  }
  sendLineNum(lineN);
}
public void draw(){
  boolean ch=true;
  
  String[] ln= loadStrings("F:/Rahul/ScienceExhibution/Data.txt");
  int n=ln.length;
  String[][] txtt=new String[n][];

  for(int i=0;i<n;i++)
    txtt[i] = splitTokens(ln[i], ",");
    
  if(n!=numLines)
    ch=false;
  else if(n==numLines){
    outer:for(int i=0;i<n;i++){
      int len=txt[i].length;
      for(int j=0; j<len; j++){
        if(!txtt[i][j].equals(txt[i][j].trim())){
          ch=false;
          break outer;
        }
      }
    }
  }
    
  
  if(ch==false){
    background(100,180,200);
    txt=txtt;
    txtLines=ln;
    numLines=n;
    //text("Yes,It's Done",100,20);
    int x=0;
    for(int i=0;i<numLines;i++){
      txt[i] = splitTokens(txtLines[i], ",");
      int len = txt[i].length;
      for(int j=0; j<len; j++){
        textSize(16);
        fill(0,0,0);
        text(txt[i][j],10+10*j,16+x); 
      }
    x=x+16;
    }
    //comPort.write('R');
    lineN=0;
    sendLineNum(lineN);
    
  }
    
}

public void serialEvent(Serial cPort){
  comPortString = cPort.readStringUntil('\n');
  if(comPortString != null) {
  comPortString=trim(comPortString);
  if(comPortString.equals("S")){
  lineN++;
  sendLineNum(lineN);
  }
  }
}

public void sendLineNum(int n){
  for(int i=0;i<txt[n].length;i++){
    //comPort.write(txt[n][i]);
  }
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "TextRead" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
