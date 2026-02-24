/**
 * Immutable configuration values for a species' life-cycle traits.
 *
 * @author Faisal AlKhalifa and Muhammad Amen bin Ehsan
 * @version 7.0
 */
public record SpeciesConfig(
        int breedingAge,
        int maxAge,
        double breedingProbability,
        int maxOffspring,
        int visibility,
        int maxEnergy,
        int restThreshold,
        int breedingThreshold
) {
}
