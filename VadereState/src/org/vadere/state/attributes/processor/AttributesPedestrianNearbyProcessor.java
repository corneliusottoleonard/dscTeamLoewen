package org.vadere.state.attributes.processor;

/**
 * @author Maxim Dudin
 *
 */

public class AttributesPedestrianNearbyProcessor extends AttributesProcessor {
    private double maxDistanceForANearbyPedestrian = 1.5;
    private int sampleEveryNthStep = 1;
    private int allowedAbsenceTimestepsIfContactReturns = 0;
    private int minTimespanOfContactTimesteps = 1;
    private boolean printContactTrajectories = false;
    private boolean printForPostVis = false;


    public double getMaxDistanceForANearbyPedestrian() {
        return maxDistanceForANearbyPedestrian;
    }

    public int getSampleEveryNthStep() {
        return sampleEveryNthStep;
    }

    public int getAllowedAbsenceTimestepsIfContactReturns() {
        return allowedAbsenceTimestepsIfContactReturns;
    }

    public int getMinTimespanOfContactTimesteps() {
        return minTimespanOfContactTimesteps;
    }

    public boolean getPrintContactTrajectories() {
        return printContactTrajectories;
    }

    public boolean getPrintForPostVis() {
        return printForPostVis;
    }
}
