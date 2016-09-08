package org.vadere.simulator.control;

import org.vadere.simulator.projects.ScenarioStore;
import org.vadere.simulator.projects.dataprocessing.ProcessorManager;
import org.vadere.state.scenario.Car;
import org.vadere.state.scenario.Pedestrian;
import org.vadere.state.scenario.Topography;
import org.vadere.util.geometry.shapes.VPoint;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SimulationState {
	private final Topography topography;
	private final Map<Integer, VPoint> pedestrianPositionMap;
	private Map<Class<? extends ActiveCallback>, OutputGenerator> outputGeneratorMap;
	private final double simTimeInSec;
	private final ScenarioStore scenarioStore;
	private final int step;
	private final String name;
	private ProcessorManager processorManager;

	protected SimulationState(final String name,
							  final Topography topography,
							  final ScenarioStore scenarioStore,
							  final double simTimeInSec,
							  final int step,
							  final ProcessorManager processorManager) {
		this.name = name;
		this.topography = topography;
		this.simTimeInSec = simTimeInSec;
		this.step = step;
		this.pedestrianPositionMap = new HashMap<>();
		this.outputGeneratorMap = new HashMap<>();
		this.scenarioStore = scenarioStore;
		this.processorManager = processorManager;

		for (Pedestrian pedestrian : topography.getElements(Pedestrian.class)) {
			pedestrianPositionMap.put(pedestrian.getId(), pedestrian.getPosition());
		}
		for (Car car : topography.getElements(Car.class)) {
			pedestrianPositionMap.put(car.getId(), car.getPosition());
		}
	}

	@Deprecated
	public SimulationState(final Map<Integer, VPoint> pedestrianPositionMap, final Topography topography,
			final double simTimeInSec, final int step) {
		this.name = "";
		this.topography = topography;
		this.simTimeInSec = simTimeInSec;
		this.step = step;
		this.outputGeneratorMap = new HashMap<>();
		this.pedestrianPositionMap = pedestrianPositionMap;
		this.scenarioStore = null;
	}

	protected void registerOutputGenerator(final Class<? extends ActiveCallback> modelType,
			final ActiveCallback model) {
		outputGeneratorMap.put(modelType, model);
	}

	/** Call this only to do cloning. */
	protected void setOutputGeneratorMap(final Map<Class<? extends ActiveCallback>, OutputGenerator> map) {
		this.outputGeneratorMap = map;
	}

	protected Map<Class<? extends ActiveCallback>, OutputGenerator> getOutputGeneratorMap() {
		return outputGeneratorMap;
	}

	// public access to getters

	public Topography getTopography() {
		return topography;
	}

	public double getSimTimeInSec() {
		return simTimeInSec;
	}

	public int getStep() {
		return step;
	}

	public VPoint getPedestrianPosition(final int pedId) {
		return pedestrianPositionMap.get(pedId);
	}

	public Collection<VPoint> getPedestrianPositions() {
		return pedestrianPositionMap.values();
	}

	public Map<Integer, VPoint> getPedestrainPositionMap() {
		return pedestrianPositionMap;
	}

	public boolean isModelTypeRegistered(final Class<? extends ActiveCallback> type) {
		return outputGeneratorMap.containsKey(type);
	}

	public Optional<OutputGenerator> getOutputGenerator(final Class<? extends ActiveCallback> type) {
		return Optional.ofNullable(outputGeneratorMap.get(type));
	}

	public ScenarioStore getScenarioStore() {
		return scenarioStore;
	}

	public String getName() {
		return name;
	}

	public ProcessorManager getProcessorManager() {
		return this.processorManager;
	}
}
