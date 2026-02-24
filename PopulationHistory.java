import java.util.HashMap;

public class PopulationHistory {
    private HashMap<Class<? extends Species>, Double> avgPopulationMap;

    public PopulationHistory() {
        avgPopulationMap = new HashMap<>();
    }

    public Double getAveragePopulation(Class<? extends Species> animalType) {
        return avgPopulationMap.getOrDefault(animalType, 0.0);
    }


    public void updateAveragePopulation(Class<? extends Species> animalType, int step, int newPopulation){
        double newAverage = ((step-1) * getAveragePopulation(animalType) + newPopulation) / step;
        avgPopulationMap.put(animalType, newAverage);
    }


    public HashMap<Class<? extends Species>, Double> getAvgPopulationMap() {
        return avgPopulationMap;
    }

    public void addAnimalType(Class<? extends Species> animalType) {
        avgPopulationMap.putIfAbsent(animalType, 0.0);
    }

    public String getAvgString(){
        StringBuilder toreturn = new StringBuilder();
        toreturn.append("Averages: ");
        for (Class<? extends Species> animalType : avgPopulationMap.keySet()) {
            toreturn.append(animalType.getSimpleName()).append(": ").append(String.format("%.1f", avgPopulationMap.get(animalType))).append(" ");
        }
        return toreturn.toString();
    }



}
