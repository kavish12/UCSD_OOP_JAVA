package lifeexpectancy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

public class LifeExpectancy extends PApplet {
	
	UnfoldingMap map;
	
	//adt map to store the data from world bank
	Map<String, Float> lifeExpByCountry;
	
	//List of features of countries which will be added from json file
	List<Feature> countries;
	
	//and list of markers from the above countries
	List<Marker> countryMarkers;
	
	public void setup(){
		size(800, 600, OPENGL);
		map = new UnfoldingMap(this, 50, 50, 700, 500, new Google.GoogleMapProvider());
		MapUtils.createDefaultEventDispatcher(this, map);
		
		//Load the life expectancy data from world bank into the map declared above 'lifeExpByCountry'
		//the data will be loaded using the helper method 'loadLifeExpectancyFromCSV'
		lifeExpByCountry = loadLifeExpectancyFromCSV("LifeExpectancyWorldBankModule3.csv");
		
		//helper method to create features and marker for the list of countries we have
		//created above
		countries = GeoJSONReader.loadData(this, "countries.geo.json");
		countryMarkers = MapUtils.createSimpleMarkers(countries);
		
		//now the markers are created, or loaded with each feature location
		//we
		//add markers to the map
		map.addMarkers(countryMarkers);
		
		//now after adding marker
		//it should be viewed differently as shades of color
		//so, manipulate markers
		//shade them
		//and shade the countries only once, so it is called in setup() method
		shadeCountries();
		
	}
	
	public void draw(){
		map.draw();
	}
	
	//helper method
	//to color each country according to their life expectancy
	private void shadeCountries(){
		
		for(Marker marker: countryMarkers){
			String countryID = marker.getId();
			
			if(lifeExpByCountry.containsKey(countryID)){//here checking if the country in our marker has data in world bank hash map or not
				float lifeExp = lifeExpByCountry.get(countryID);
				//encode value as brightness(40-90)
				int colorlevel = (int) map(lifeExp, 40, 90, 10, 255);
				marker.setColor(color(255-colorlevel, 100, colorlevel));
			}else{
				marker.setColor(color(150, 150, 150));
			}
		}
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
