package deserialisedClasses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class WeatherDetails {
	
	String base;
	
	public WeatherDetails() {}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

}
