package lifeexpectancy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

public class LifeExpectancy extends PApplet {
	
	UnfoldingMap map;
	
	//List of features of countries which will be added from json file
	List<Feature> countries;
	
	//and list of markers from the above countries
	List<Marker> countryMarkers;
	
	public void setup(){
		size(800, 600, OPENGL);
		map = new UnfoldingMap(this, 50, 50, 700, 500, new Google.GoogleMapProvider());
		MapUtils.createDefaultEventDispatcher(this, map);
	}
	
	public void draw(){
		map.draw();
	}
	
	//helper method to read data
	//from csv file and
	//store in the map data structure
	//the helper method is private
	//it returns the map data structure
	//the argument passed in this method is the filename
	private Map<String, Float> loadLifeExpectancyFromCSV(String filename){
		
		//first create the map data structure
		Map<String, Float> lifeExpMap = new HashMap<String, Float>();
		
		//string array of data related to each country
		//each countries data form a row
		String[] rows = loadStrings(filename);
		
		for(String row: rows){
			
			String[] column = row.split(",");// not understood
			
			if(column.length==6 && !column[5].equals("..")){
				lifeExpMap.put(column[4], Float.parseFloat(column[5]));
			}
		}	
		return lifeExpMap;	
	}

}
