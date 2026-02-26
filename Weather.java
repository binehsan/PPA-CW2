/**
 * Weather conditions that affect visibility and plant growth in the simulation.
 *
 * @author Faisal AlKhalifa and Muhammad Amen bin Ehsan
 * @version 1.0
 */
public enum Weather {
    RAIN {
        @Override
        public double plantGrowthMultiplier() {
            return 1.5;
        }
    },
    SANDSTORM {
        @Override
        public double visibilityMultiplier() {
            return 0.5;
        }

        @Override
        public double plantGrowthMultiplier() {
            return 0.5;
        }
    },
    NORMAL {
        // Uses default multipliers.
    };

    /**
     * @return Multiplier applied to plant regrowth chance.
     */
    public double plantGrowthMultiplier() {
        return 1;
    }

    /**
     * @return Multiplier applied to animal visibility.
     */
    public double visibilityMultiplier() {
        return 1;
    }
}
