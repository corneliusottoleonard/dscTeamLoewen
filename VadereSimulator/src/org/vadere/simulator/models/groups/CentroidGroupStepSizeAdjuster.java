package org.vadere.simulator.models.groups;

import org.vadere.simulator.models.StepSizeAdjuster;
import org.vadere.state.scenario.Pedestrian;

public class CentroidGroupStepSizeAdjuster implements StepSizeAdjuster {

	private final CentroidGroupModel groupCollection;

	public CentroidGroupStepSizeAdjuster(CentroidGroupModel groupCollection) {
		this.groupCollection = groupCollection;
	}

	@Override
	public double getAdjustedStepSize(Pedestrian ped, double originalStepSize) {
		double result = 1.0;
		double aheadDistance = 0;

		CentroidGroup group = groupCollection.getGroup(ped);

		if (group != null) {
			aheadDistance = group.getRelativeDistanceCentroid(ped);

			// TODO [priority=low] [task=refactoring] move Parameters to AttributesCGM
			if (!group.isLostMember(ped)) {
				if (aheadDistance > 8) {
					result = Double.MIN_VALUE;
				} else if (aheadDistance >= 1) {
					result /= 1.0 + aheadDistance / 8 - 1 / 8 + 1;
				} else if (aheadDistance >= 0) {
					result /= 1.0 + Math.pow(aheadDistance, 2);
				} else if (aheadDistance >= -1) {
					result /= 0.75;
				} else {
					result /= 0.65;
				}
			}
		}

		return originalStepSize * result;
	}
}
