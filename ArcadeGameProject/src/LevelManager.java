import java.io.File;
import java.util.ArrayList;


/** 
 *This class holds an ArrayList of the level files and sends the right file 
 *to the level class when called. 
 */
public class LevelManager {
	
	public ArrayList<File> levelFiles;
	private int currentLevel;

	public LevelManager() {
		
		this.currentLevel = 0;
		this.levelFiles = new ArrayList<File>();
		this.levelFiles.add(new File("Level1.txt"));
		this.levelFiles.add(new File("Level2.txt"));
		this.levelFiles.add(new File("Level3.txt"));
		this.levelFiles.add(new File("Level4.txt"));
	}

	public File initialize() {
		return this.levelFiles.get(this.currentLevel);
	}

	public File nextLevel() {
		if (this.levelFiles.size() < this.currentLevel + 1) {
			return null;
		}
		this.currentLevel += 1;
		return this.levelFiles.get(this.currentLevel);
	}

	public File previousLevel() {
		if ((this.currentLevel - 1) < 0) {
			return null;
		}
		this.currentLevel = this.currentLevel - 1;
		return this.levelFiles.get(this.currentLevel);

	}
	
	public File getBonusLevel(){
		this.currentLevel = this.currentLevel; 
		return (new File("Bonus Level"));
	}

}
