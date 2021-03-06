package com.cdevtech.conversion;

import java.text.DecimalFormat;

/**
 * Created by bills on 2/27/2016.
 */
public class Quantity {

    // Each object will have both a value and a unit of measure
    final double value;
    final Unit unit;

    // Enum types use a constant key to represent a value
    // They allow us to easily define how to convert all the other types
    // of measurements to convert from teaspoon to anything. Then to make
    // any conversion we convert from the starting type to teaspoon and then
    // to the final required type.
    public static enum Unit {
        tsp(1.0d),              // calls constructor with value 1.0d
        tbs(0.3333d),
        cup(0.0208d),
        oz(0.1666d),
        pint(0.104d),
        quart(0.0052d),
        gallon(0.0013),
        pound(0.0125d),
        ml(4.9289d),
        liter(0.0049d),
        mg(5687.5d),
        kg(0.0057d);            // calls constructor with value 0.0057d

        // We define that tsp will the the base unit of measure that we will
        // convert to and then convert from.
        final static Unit baseUnit = tsp;

        // Will hold the number of tsp the original unit converts to
        final double byBaseUnit;

        // Constructor that receives the number of tsps the starting unit equals
        private Unit(double inTsp) {
            byBaseUnit = inTsp;
        }

        // Converts any other unit value to the number of tsps
        public double toBaseUnits(double value) {
            return value / byBaseUnit;
        }

        // We convert to another unit by using the teaspoon conversion percent
        // defined in the enum
        public double fromBaseUnit(double value) {
            return value * byBaseUnit;
        }
    }

    // The constructor that receives the value and unit of measure
    public Quantity(double value, Unit unit) {
        super();

        this.value = value;
        this.unit = unit;
    }

    // Converts from tsp to the desired unit type
    // First convert the old value to 'number of teaspoons' based on old unit
    // Next convert 'number of teaspoons' to new value based on new unit
    public Quantity to(Unit newUnit) {
        Unit oldUnit = this.unit;
        return new Quantity(
                newUnit.fromBaseUnit(oldUnit.toBaseUnits(value)),
                newUnit);
    }

    // Prints out to screen the unit amount and the unit type
    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.0000");

        return df.format(value) + " " + unit.name();
    }
}
