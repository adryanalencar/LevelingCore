package com.azuredoom.hyleveling.level;

/**
 * Implementation of the LevelFormula interface that calculates XP and level
 * values using an exponential formula. This class models the XP progression
 * required to reach higher levels, which grows exponentially based on the
 * provided base XP and exponent values.
 */
public class ExponentialLevelFormula implements LevelFormula {

    private final double baseXp;

    private final double exponent;

    public ExponentialLevelFormula(double baseXp, double exponent) {
        this.baseXp = baseXp;
        this.exponent = exponent;
    }

    /**
     * Calculates and returns the total experience points (XP) required to reach the specified level
     * based on an exponential formula using the base XP and exponent values.
     *
     * @param level The target level for which the required experience points should be calculated.
     *              It must be a positive integer.
     * @return The total XP required to reach the given level as a long value.
     */
    @Override
    public long getXpForLevel(int level) {
        return (long) (baseXp * Math.pow(level, exponent));
    }

    /**
     * Determines the level corresponding to the given amount of total experience points (XP).
     * The level is calculated based on the XP thresholds defined by the implementation of
     * {@link #getXpForLevel(int)}.
     *
     * @param xp The total experience points for which the corresponding level needs to be determined.
     * @return The level associated with the given amount of XP.
     */
    @Override
    public int getLevelForXp(long xp) {
        var level = 1;
        while (getXpForLevel(level + 1) <= xp) {
            level++;
        }
        return level;
    }
}
