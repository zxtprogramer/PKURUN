package com.example.zxt.test5;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.widget.Toast;

import java.util.ArrayList;

public class MyService extends Service {
    public LocationManager lm;
    public String name1,name2;
    public double road_54[][],road_lake[][];
    public double velocity;
    public ArrayList<Double> lats, lngs;
    public int roadNum;//0 is 54; 1 is lake;
    double dt;
    int ifRun;

    public MyBinder binder=new MyBinder();

    public class MyBinder extends Binder {
        public void stopRun(){
            ifRun=0;
        }

        public void startRun(){
            if(ifRun==1)return;
            start();
        }
        public void setPara(int rNum, double v){
            roadNum=rNum;
            velocity=v;
        }
    }

    public void start(){
        ifRun=1;
        lm=(LocationManager)getSystemService(Context.LOCATION_SERVICE);

        name1=LocationManager.NETWORK_PROVIDER;
        name2 = LocationManager.GPS_PROVIDER;

        createRoad();
        //Toast.makeText(getApplicationContext(),lats.get(0)+" "+lngs.get(0),Toast.LENGTH_SHORT).show();

        new Thread() {
            public void run(){

                int index=0, num=lats.size();
                double lat=0, lng=0;

                while(ifRun==1){

                    lat=lats.get(index) -7.2e-3;
                    lng=lngs.get(index) -0.01265;

                    lat=lat + lat*1e-7*(Math.random()-0.5);
                    lng=lng + lng*1e-7*(Math.random()-0.5);

                    Location mL1=new Location(name1);
                    mL1.setLatitude(lat);
                    mL1.setLongitude(lng);
                    mL1.setAltitude(0);
                    mL1.setTime(System.currentTimeMillis());
                    mL1.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
                    mL1.setAccuracy(Criteria.ACCURACY_FINE);
                    mL1.setSpeed(1);

                    Location mL2=new Location(name2);
                    mL2.setLatitude(lat);
                    mL2.setLongitude(lng);
                    mL2.setAltitude(0);
                    mL2.setTime(System.currentTimeMillis());
                    mL2.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
                    mL2.setAccuracy(Criteria.ACCURACY_FINE);
                    mL2.setSpeed(1);

                    lm.addTestProvider(name1, false, false, false, false, true, true, true, 0, Criteria.ACCURACY_FINE);
                    lm.setTestProviderEnabled(name1, true);

                    lm.addTestProvider(name2, false, false, false, false, true, true, true, 0, Criteria.ACCURACY_FINE);
                    lm.setTestProviderEnabled(name2, true);

                    lm.setTestProviderLocation(name1,mL1);
                    lm.setTestProviderLocation(name2,mL2);


                    try{
                        Thread.sleep((int)(1000*dt));

                    }catch(Exception e){}
                    index=(index+1)%num;

                }

            }
        }.start();

    }


    public MyService(){
        road_54=new double[14][2];
        road_54[0][0]=  39.993919 ; road_54[0][1]=  116.319235;
        road_54[1][0]=  39.993073 ; road_54[1][1]=  116.319294;
        road_54[2][0]=  39.992872 ; road_54[2][1]=  116.319406;
        road_54[3][0]=  39.99281  ; road_54[3][1]=  116.319581;
        road_54[4][0]=  39.99279  ; road_54[4][1]=  116.319761;
        road_54[5][0]=  39.992824 ; road_54[5][1]=  116.31999 ;
        road_54[6][0]=  39.992931 ; road_54[6][1]=  116.320129;
        road_54[7][0]=  39.993059 ; road_54[7][1]=  116.320174;
        road_54[8][0]=  39.993988 ; road_54[8][1]=  116.320124;
        road_54[9][0]=  39.994009 ; road_54[9][1]=  116.320129;
        road_54[10][0]= 39.994168 ; road_54[10][1]= 116.319963;
        road_54[11][0]= 39.994234 ; road_54[11][1]= 116.319711;
        road_54[12][0]= 39.994185 ; road_54[12][1]= 116.319428;
        road_54[13][0]= 39.994051 ; road_54[13][1]= 116.319285;

        road_lake=new double[24][2];
        road_lake[0][0]=   40.000939;  road_lake[0][1]=116.318018;
        road_lake[1][0]=   40.001022;  road_lake[1][1]=116.317897;
        road_lake[2][0]=   40.001309;  road_lake[2][1]=116.317668;
        road_lake[3][0]=   40.001502;  road_lake[3][1]=116.317632;
        road_lake[4][0]=   40.001633;  road_lake[4][1]=116.31751;
        road_lake[5][0]=   40.001575;  road_lake[5][1]=116.317245;
        road_lake[6][0]=   40.001571;  road_lake[6][1]=116.316289;
        road_lake[7][0]=   40.001537;  road_lake[7][1]=116.315965;
        road_lake[8][0]=   40.001474;  road_lake[8][1]=116.315759;
        road_lake[9][0]=  40.001412;  road_lake[9][1]=116.315678;
        road_lake[10][0]=  40.001164;  road_lake[10][1]=116.315593;
        road_lake[11][0]=  40.001143;  road_lake[11][1]=116.31526;
        road_lake[12][0]=  40.001046;  road_lake[12][1]=116.315193;
        road_lake[13][0]=  40.000904;  road_lake[13][1]=116.314892;
        road_lake[14][0]=  40.000815;  road_lake[14][1]=116.314443;
        road_lake[15][0]=  40.000815;  road_lake[15][1]=116.314277;
        road_lake[16][0]=  40.000725;  road_lake[16][1]=116.314187;
        road_lake[17][0]=  40.000486;  road_lake[17][1]=116.314546;
        road_lake[18][0]=  40.000068;  road_lake[18][1]=116.315076;
        road_lake[19][0]=  39.999868;  road_lake[19][1]=116.31597;
        road_lake[20][0]=  39.999968;  road_lake[20][1]=116.316536;
        road_lake[21][0]=  39.999999;  road_lake[21][1]=116.316913;
        road_lake[22][0]=  39.999882;  road_lake[22][1]=116.317362;
        road_lake[23][0]=  39.999875;  road_lake[23][1]=116.317771;

        velocity=3;
        roadNum=0;
        dt=0.5;
        ifRun=0;
        lats=new ArrayList(); lngs=new ArrayList();
    }

    public void createPoints(double lat1,double lng1,double lat2,double lng2){
        double len=getDis(lat1,lng1,lat2,lng2);
        int num=(int)(len/(velocity*dt));
        int i=0;
        double lat,lng;
        double dLat=(lat2-lat1)/num; double dLng=(lng2-lng1)/num;

        for(i=0;i<num;i++){
            lat=lat1+i*dLat; lng=lng1+dLng*i;
            lats.add(lat); lngs.add(lng);
        }
    }

    public void createRoad(){
        lats.clear();lngs.clear();
        int i,num;
        double lat1,lat2,lng1,lng2;
        if(roadNum==0){
            num=road_54.length;
            for(i=0;i<num;i++){
                lat1=road_54[i][0];lng1=road_54[i][1];
                lat2=road_54[(i+1)%num][0];lng2=road_54[(i+1)%num][1];
                createPoints(lat1,lng1,lat2,lng2);
            }
        }
        else{
            num=road_lake.length;
            for(i=0;i<num;i++){
                lat1=road_lake[i][0];lng1=road_lake[i][1];
                lat2=road_lake[(i+1)%num][0];lng2=road_lake[(i+1)%num][1];
                createPoints(lat1,lng1,lat2,lng2);
            }

        }
    }

    public double getDis(double lat1, double lng1, double lat2, double lng2){
        double R=6378.137e3;
        double PI=3.1415926;
        double th1=(90-lat1)/180.0*PI; double phi1=(lng1)/180.0*PI;
        double th2=(90-lat2)/180.0*PI; double phi2=(lng2)/180.0*PI;
        double x1=R*Math.sin(th1)*Math.cos(phi1); double y1=R*Math.sin(th1)*Math.sin(phi1); double z1=R*Math.cos(th1);
        double x2=R*Math.sin(th2)*Math.cos(phi2); double y2=R*Math.sin(th2)*Math.sin(phi2); double z2=R*Math.cos(th2);
        double dis=Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2) + (z1 - z2) * (z1 - z2));
        double alpha=Math.asin(dis / 2.0 / R)*2.0;
        return alpha*R;
    }



    @Override
    public void onCreate(){
        super.onCreate();




    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {

        return binder;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}
