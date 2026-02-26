import java.util.HashMap;


/**
 * A storage container to store the history of averages using a HashMap.
 * Provisions an interface to interact with it.
 */
public class PopulationHistory {
    private HashMap<Class<? extends Species>, Double> avgPopulationMap;

    public PopulationHistory() {
        avgPopulationMap = new HashMap<>();
    }

    /**
     * @param animalType
     * @return Average population for the given animal type. If the animal type is not present, returns 0.0.
     */
    public Double getAveragePopulation(Class<? extends Species> animalType) {
        return avgPopulationMap.getOrDefault(animalType, 0.0);
    }


    /**
     * @param animalType
     * @param step
     * @param newPopulation
     */
    public void updateAveragePopulation(Class<? extends Species> animalType, int step, int newPopulation){
        double newAverage = ((step-1) * getAveragePopulation(animalType) + newPopulation) / step;
        avgPopulationMap.put(animalType, newAverage);
    }


    /**
     * @return the averagePopulation hashmap.
     */
    public HashMap<Class<? extends Species>, Double> getAvgPopulationMap() {
        return avgPopulationMap;
    }


    /**
     * @param animalType
     */
    public void addAnimalType(Class<? extends Species> animalType) {
        avgPopulationMap.putIfAbsent(animalType, 0.0);
    }

    /**
     * @return A String representation of the average population for each animal type, formatted to one decimal place.
     */
    public String getAvgString(){
        StringBuilder toreturn = new StringBuilder();
        toreturn.append("Averages: ");
        for (Class<? extends Species> animalType : avgPopulationMap.keySet()) {
            toreturn.append(animalType.getSimpleName()).append(": ").append(String.format("%.1f", avgPopulationMap.get(animalType))).append(" ");
        }
        return toreturn.toString();
    }



}
