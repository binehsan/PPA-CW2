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

    };

    public double plantGrowthMultiplier() {
        return 1;
    }

    public double visibilityMultiplier() {
        return 1;
    }
}
