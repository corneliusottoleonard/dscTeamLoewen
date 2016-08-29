package org.vadere.simulator.projects.dataprocessing_mtp;

import org.vadere.simulator.control.SimulationState;

import java.util.List;
import java.util.stream.Collectors;

public class MeanPedestrianEvacuationTimeProcessor extends Processor<NoDataKey, Double> {
    private PedestrianEvacuationTimeProcessor pedEvacTimeProc;

    public MeanPedestrianEvacuationTimeProcessor() {
        super("tmeanevac");
    }

    @Override
    protected void doUpdate(final SimulationState state) {
        // No implementation needed, look at 'postLoop(SimulationState)'
    }

    @Override
    void init(final AttributesProcessor attributes, final ProcessorFactory factory) {
        AttributesMeanPedestrianEvacuationTimeProcessor att = (AttributesMeanPedestrianEvacuationTimeProcessor) attributes;
        this.pedEvacTimeProc = (PedestrianEvacuationTimeProcessor) factory.getProcessor(att.getPedestrianEvacuationTimeProcessorId());
    }

    @Override
    public void postLoop(final SimulationState state) {
        this.pedEvacTimeProc.postLoop(state);

        List<Double> nonNans = this.pedEvacTimeProc.getValues().stream()
                .filter(val -> val != Double.NaN)
                .collect(Collectors.toList());
        int count = nonNans.size();

        this.setValue(NoDataKey.key(), count > 0
                ? nonNans.stream().reduce(0.0, (val1, val2) -> val1 + val2) / count
                : Double.NaN);
    }
}