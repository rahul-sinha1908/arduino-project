
int pin[]={6,7,8,9,10,11,12,13};
int row=100;
int txt[100][8];
int dTime=1000/row;
int cnt1=0,cnt2=0;

void setup(){
  Serial.begin(9600);
  for(int i=0;i<8;i++){
    pinMode(pin[i],OUTPUT);
    digitalWrite(pin[i],LOW);
  }
  for(int i=0;i<row;i++){
    for(int j=0;j<8;j++)
      txt[i][j]=10;
  }
}
void loop(){
  initialize();
  LEDdisplay();
}
void initialize(){
  if(Serial.available()){
    byte inp=Serial.read();
    switch(inp) {
        case 'N':
        cnt1++;
        cnt2=0;
        Serial.println('S');
        break;
        
        case 'S':
        cnt1+=4;
        cnt2=0;
        Serial.println('S');
        break;
        
        case 'R':
        reset();
        break;
        
        default:
        if(inp>0 && inp<10 && cnt1<row){
        txt[cnt1][cnt2]=(int)inp;
        cnt2++;
        }
        break;
    }
  }
}

void reset(){
  for(int i=0;i<8;i++)
    digitalWrite(pin[i],LOW);
  for(int i=0;i<row;i++){
    for(int j=0;j<8;j++)
      txt[i][j]=10;
  }
}

void LEDdisplay(){
  for(int i=0;i<8;i++)
    digitalWrite(pin[i],LOW);
    
  for(int i=0;i<row;i++){
    for(int j=0;j<8;j++){
      int x=txt[i][j]-1;
      if(x>=0 && x<=7)
        digitalWrite(pin[x],HIGH);
    }
    delay(dTime);
  }
}
