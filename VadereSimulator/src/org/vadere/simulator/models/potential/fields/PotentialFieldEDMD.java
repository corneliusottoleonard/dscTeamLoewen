package org.vadere.simulator.models.potential.fields;

import org.jetbrains.annotations.NotNull;
import org.vadere.meshing.mesh.inter.IMesh;
import org.vadere.simulator.projects.Domain;
import org.vadere.state.attributes.Attributes;
import org.vadere.state.attributes.models.AttributesFloorField;
import org.vadere.state.attributes.scenario.AttributesAgent;
import org.vadere.state.scenario.Agent;
import org.vadere.state.scenario.ScenarioElement;
import org.vadere.state.scenario.Target;
import org.vadere.state.scenario.TargetPedestrian;
import org.vadere.state.scenario.Topography;
import org.vadere.util.geometry.shapes.IPoint;
import org.vadere.util.geometry.shapes.VPoint;
import org.vadere.util.geometry.shapes.VShape;
import org.vadere.util.geometry.shapes.Vector2D;
import org.vadere.util.logging.Logger;
import org.vadere.util.math.MathUtil;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.function.Function;

public class PotentialFieldEDMD implements IPotentialFieldTarget {

    private static Logger logger = Logger.getLogger(PotentialFieldTargetGrid.class);

    protected double lastUpdateTimestamp;
    protected final Domain domain;
    private boolean potentialFieldsNeedUpdate;
    private AttributesFloorField attributes;
    private AttributesAgent attributesPedestrian;

    public PotentialFieldEDMD(@NotNull final Domain domain,
                              @NotNull final AttributesAgent attributesPedestrian) {
        this.domain = domain;
        this.attributesPedestrian = attributesPedestrian;

    }

    @Override
    public double getPotential(@NotNull final IPoint pos, final Agent agent) {
        return EDMDpredict(pos, agent);
    }

    @Override
    public double getPotential(@NotNull final IPoint pos, final int targetId) {
        return EDMDpredict(pos, targetId);
    }

    @Override
    public void initialize(List<Attributes> modelAttributesList, Domain domain, AttributesAgent attributesPedestrian, Random random) {}

    @Override
    public boolean needsUpdate() {
        return false;
    }

    @Override
    public Vector2D getTargetPotentialGradient(final VPoint pos, final Agent agent) {
        double eps = Math.max(pos.x, pos.y) * MathUtil.EPSILON;
        double dGradPX = (getPotential(pos.add(new VPoint(eps, 0)), agent) - getPotential(pos.subtract(new VPoint(eps, 0)), agent)) / (2 * eps);
        double dGradPY = (getPotential(pos.add(new VPoint(0, eps)), agent) - getPotential(pos.subtract(new VPoint(0, eps)), agent)) / (2 * eps);
        return new Vector2D(dGradPX, dGradPY);
    }

    @Override
    public IPotentialField getSolution() {
        return new PotentialFieldEDMD(domain, attributesPedestrian);
    }

    @Override
    public Function<Agent, IMesh<?, ?, ?>> getDiscretization() {
        return agent -> null;
    }

    private double EDMDpredict(final IPoint pos, final Object target) {
        // Replace this with EDMD prediction
        return 0.0;
    }

    @Override
    public void update(double simTimeInSec) {}

    @Override
    public void postLoop(double simTimeInSec) {}

    @Override
    public void preLoop(double simTimeInSec) {}
}

