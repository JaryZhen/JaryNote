package headfirst.test.observer;


import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by Jary on 2017/7/18 0018.
 */
public class WeatherData extends Observable {
    private ArrayList observers;

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    private float temperature;
    private float humidity;
    private float pressure;
    public WeatherData(){
        observers = new ArrayList();
    }


    public void measurementsChanged(){
        setChanged();
        notifyObservers();
    }
    public void setMeasurements(float temperature,float humilty,float pressure){
        this.temperature = temperature;
        this.humidity = humilty;
        this.pressure = pressure;
        measurementsChanged();
    }

}
