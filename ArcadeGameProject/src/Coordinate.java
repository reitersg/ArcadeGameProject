/**
 *Coordinate class, used by everything for Locations.
 *These are coordinates in the map. 
 */
public class Coordinate {
	private int x; 
	private int y; 
	
	public Coordinate (int x, int y){
		this.x = x; 
		this.y = y; 
	}
	
	public int getX(){
		return this.x; 
	}
	
	public int getY(){
		return this.y; 
	}
	
	public Coordinate getLocation(){
		return new Coordinate(this.x, this.y);
		
	}
	
	
	public void setX(int newX){
		this.x = newX; 
	}
	
	public void setY(int newY){
		this.y = newY; 
	}
	
	public void setCoordinate (int newX, int newY){
		this.x = newX; 
		this.y = newY; 
	}
	
	public String toString() {
		return "(" + this.x + "," + this.y + ")";
	}
	


	
	
}
