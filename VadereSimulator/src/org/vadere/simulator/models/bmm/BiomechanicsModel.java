package org.vadere.simulator.models.bmm;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.vadere.simulator.models.MainModel;
import org.vadere.simulator.models.Model;
import org.vadere.state.attributes.Attributes;
import org.vadere.state.attributes.models.AttributesBHM;
import org.vadere.state.attributes.models.AttributesBMM;
import org.vadere.state.attributes.scenario.AttributesAgent;
import org.vadere.state.scenario.DynamicElement;
import org.vadere.state.scenario.Pedestrian;
import org.vadere.state.scenario.Topography;
import org.vadere.util.geometry.shapes.VPoint;

/**
 * 
 * MainModel READY!
 * 
 *
 */
public class BiomechanicsModel implements MainModel {

	private List<Model> models = new LinkedList<>();

	private AttributesBMM attributesBMM;
	private AttributesBHM attributesBHM;
	private AttributesAgent attributesPedestrian;
	private Random random;
	private Topography topography;
	private int pedestrianIdCounter;
	private List<PedestrianBMM> pedestriansBMM;
	protected double lastSimTimeInSec;

	public BiomechanicsModel() {
		this.pedestrianIdCounter = 0;
		this.pedestriansBMM = new LinkedList<>();
	}

	@Override
	public void initialize(List<Attributes> modelAttributesList, Topography topography,
			AttributesAgent attributesPedestrian, Random random) {
		this.attributesBHM = Model.findAttributes(modelAttributesList, AttributesBHM.class);
		this.attributesBMM = Model.findAttributes(modelAttributesList, AttributesBMM.class);
		this.attributesPedestrian = attributesPedestrian;
		this.topography = topography;
		this.random = random;
		this.models.add(this);
	}

	@Override
	public <T extends DynamicElement> PedestrianBMM createElement(VPoint position, int id, Class<T> type) {
		if (!Pedestrian.class.isAssignableFrom(type))
			throw new IllegalArgumentException("BMM cannot initialize " + type.getCanonicalName());

		pedestrianIdCounter++;
		AttributesAgent pedAttributes = new AttributesAgent(
				this.attributesPedestrian, id > 0 ? id : pedestrianIdCounter);

		PedestrianBMM pedestrian = new PedestrianBMM(position, topography, pedAttributes,
				attributesBMM, attributesBHM, random);

		this.pedestriansBMM.add(pedestrian);

		return pedestrian;
	}

	@Override
	public void preLoop(final double simTimeInSec) {
		this.lastSimTimeInSec = simTimeInSec;
	}


	@Override
	public void postLoop(double simTimeInSec) {}

	@Override
	public void update(final double simTimeInSec) {
		double deltaTime = simTimeInSec - lastSimTimeInSec;

		for (PedestrianBMM agent : pedestriansBMM) {
			agent.update(simTimeInSec, deltaTime);
		}

		for (PedestrianBMM agent : pedestriansBMM) {
			agent.move(simTimeInSec, deltaTime);
		}

		for (PedestrianBMM agent : pedestriansBMM) {
			agent.reverseCollisions();
		}

		this.lastSimTimeInSec = simTimeInSec;
	}

	@Override
	public List<Model> getSubmodels() {
		return models;
	}

}