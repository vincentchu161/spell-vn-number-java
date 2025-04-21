package work.vincentchu.libs.spellvnnumber.type;

/**
 * Implementation of NumberData interface
 */
public class NumberData implements INumberData {
    private final boolean isNegative;
    private final String integralPart;
    private final String fractionalPart;

    public NumberData(boolean isNegative, String integralPart, String fractionalPart) {
        this.isNegative = isNegative;
        this.integralPart = integralPart;
        this.fractionalPart = fractionalPart;
    }

    @Override
    public boolean isNegative() {
        return isNegative;
    }

    @Override
    public String getIntegralPart() {
        return integralPart;
    }

    @Override
    public String getFractionalPart() {
        return fractionalPart;
    }
} 