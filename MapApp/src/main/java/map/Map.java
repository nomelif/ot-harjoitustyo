package map;

public class Map{
    private double[] data;
    private int width;

    public Map(int width, int height){
        data = new double[width*height];
        this.width = width;
    }

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.data.length / this.width;
    }
}
