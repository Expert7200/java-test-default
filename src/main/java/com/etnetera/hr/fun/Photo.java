package com.etnetera.hr.fun;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Bogdan
 *
 */
public class Photo {
	
	private List<String> myPhoto;
	
	
	public Photo() {
		myPhoto = new ArrayList<>();
		myPhoto.add("                       (");
		myPhoto.add("            _           ) )");
		myPhoto.add("         _,(_)._        ((");
		myPhoto.add("    ___,(_______).        )");
		myPhoto.add("  ,'__.   /       \n    /\\_");
		myPhoto.add(" /,' /  |\"\"|       \\  /  /");		
		myPhoto.add("| | |   |__|       |,'  /");
		myPhoto.add(" \\`.|                  /");
		myPhoto.add("  `. :           :    /");
		myPhoto.add("    `.            :.,'");
		myPhoto.add("      `-.________,-'");
	}
	
	public List<String> getMyPhoto() {
		return myPhoto;
	}
	
	public void setMyPhoto(List<String> myPhoto) {
		this.myPhoto = myPhoto;
	}

}
