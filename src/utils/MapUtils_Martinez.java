package utils;

import java.util.Map;
import java.util.Set;

public class MapUtils_Martinez
{
	private MapUtils_Martinez()
	{
		throw new RuntimeException("DO NOT INSTANTIATE!");
	}
	
	public static <A, B> boolean noMapValueIsNull(Map<A, B> map)
	{
		assert map != null : "map is null!";
		//... 
		boolean noMapValueIsNull = true;
		
		for (A key: map.keySet()) {
			if (key == null) {
				noMapValueIsNull = false;
			}
		}
		
		for (B value: map.values()) {
			if (value == null) {
				noMapValueIsNull = false;
			}
		}
		
		return noMapValueIsNull;
	}
	
	public static <A, B> int sumAllSetSizes(Map<A, Set<B>> map)
	{
		assert map != null : "map is null!";
		assert noMapValueIsNull(map) : "map contains a null value! : map = " + map;
		//...
		int sumAllSetSizes = 0;
		
		for (Set<B> value: map.values()) {
			sumAllSetSizes += value.size();
		}
		
		return sumAllSetSizes;
	}
	
	public static <E> boolean isSymmetric(Map<E, Set<E>> elementToSetMap)
	{
		assert elementToSetMap != null : "elementToSetMap is null!";
		assert noMapValueIsNull(elementToSetMap) : "elementToSetMap contains a null value! : elementToSetMap = " + elementToSetMap;
		//...
		boolean isSymmetric = true;
		
		for (E key: elementToSetMap.keySet()) {
			Set<E> tempValues = elementToSetMap.get(key);
			
			if (elementToSetMap.keySet().containsAll(tempValues)) {
				
				for (E key2: tempValues) {
					isSymmetric = elementToSetMap.get(key2).contains(key);
				}
				
			} else {
				isSymmetric = false;
			}
			
		}
		
		return isSymmetric;
	}
}
