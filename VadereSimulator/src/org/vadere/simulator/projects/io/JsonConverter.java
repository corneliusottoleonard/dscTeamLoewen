package org.vadere.simulator.projects.io;

import java.io.IOException;
import java.util.List;

import org.vadere.simulator.models.MainModel;
import org.vadere.simulator.projects.Scenario;
import org.vadere.simulator.projects.ScenarioStore;
import org.vadere.simulator.projects.dataprocessing.DataProcessingJsonManager;
import org.vadere.state.attributes.Attributes;
import org.vadere.state.attributes.AttributesSimulation;
import org.vadere.state.attributes.ModelDefinition;
import org.vadere.state.scenario.Topography;
import org.vadere.state.util.StateJsonConverter;
import org.vadere.util.reflection.DynamicClassInstantiator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonConverter {
	
	public static Scenario deserializeScenarioRunManager(String json) throws IOException, IllegalArgumentException {
		return deserializeScenarioRunManagerFromNode(StateJsonConverter.readTree(json));
	}
	
	public static Scenario deserializeScenarioRunManagerFromNode(JsonNode node) throws IOException, IllegalArgumentException {
		JsonNode rootNode = node;
		String name = rootNode.get("name").asText();
		JsonNode scenarioNode = rootNode.get(StateJsonConverter.SCENARIO_KEY);
		AttributesSimulation attributesSimulation = StateJsonConverter.deserializeAttributesSimulationFromNode(scenarioNode.get("attributesSimulation"));
		JsonNode attributesModelNode = scenarioNode.get("attributesModel");
		String mainModel = scenarioNode.get(StateJsonConverter.MAIN_MODEL_KEY).isNull() ? null : scenarioNode.get(StateJsonConverter.MAIN_MODEL_KEY).asText();
		List<Attributes> attributesModel = StateJsonConverter.deserializeAttributesListFromNode(attributesModelNode);
		Topography topography = StateJsonConverter.deserializeTopographyFromNode(scenarioNode.get("topography"));
		String description = rootNode.get("description").asText();
		ScenarioStore scenarioStore = new ScenarioStore(name, description, mainModel, attributesModel, attributesSimulation, topography);
		Scenario scenarioRunManager = new Scenario(scenarioStore);

		scenarioRunManager.setDataProcessingJsonManager(DataProcessingJsonManager.deserializeFromNode(rootNode.get(DataProcessingJsonManager.DATAPROCCESSING_KEY)));
		scenarioRunManager.saveChanges();

		return scenarioRunManager;
	}
	
	public static ModelDefinition deserializeModelDefinition(String json) throws Exception {
		JsonNode node = StateJsonConverter.readTree(json);
		StateJsonConverter.checkForTextOutOfNode(json);
		if (!node.has(StateJsonConverter.MAIN_MODEL_KEY))
			throw new Exception("No " + StateJsonConverter.MAIN_MODEL_KEY + "-entry was found.");
		String mainModelString = null;
		JsonNode mainModel = node.get(StateJsonConverter.MAIN_MODEL_KEY);
		if (!mainModel.isNull()) { // avoid test-instantiating when mainModel isn't set, otherwise user has invalid json when creating a new scenario
			DynamicClassInstantiator<MainModel> instantiator = new DynamicClassInstantiator<>();
			mainModelString = mainModel.asText();
			// instantiate to get an error if the string can't be mapped onto a model
			@SuppressWarnings("unused")
			MainModel dummyToProvokeClassCast = instantiator.createObject(mainModelString);
		}
		return new ModelDefinition(mainModelString, StateJsonConverter.deserializeAttributesListFromNode(node.get("attributesModel")));
	}
	


	// used in hasUnsavedChanges, TODO [priority=high] [task=bugfix] check if commitHashIncluded can always be false
	public static String serializeScenarioRunManager(Scenario scenarioRunManager) {
		try {
			return serializeScenarioRunManager(scenarioRunManager, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String serializeScenarioRunManager(Scenario scenarioRunManager, boolean commitHashIncluded)
			throws IOException {
		return StateJsonConverter.writeValueAsString(serializeScenarioRunManagerToNode(scenarioRunManager, commitHashIncluded));
	}

	public static JsonNode serializeScenarioRunManagerToNode(Scenario scenarioRunManager,
			boolean commitHashIncluded) throws IOException {
		ScenarioStore scenarioStore = scenarioRunManager.getScenarioStore();
		ObjectNode rootNode = StateJsonConverter.createObjectNode();
		serializeMeta(rootNode, commitHashIncluded, scenarioStore);
		rootNode.set(DataProcessingJsonManager.DATAPROCCESSING_KEY, scenarioRunManager.getDataProcessingJsonManager().serializeToNode());
		rootNode.set(StateJsonConverter.SCENARIO_KEY, serializeVadereNode(scenarioStore));
		return rootNode;
	}

	private static void serializeMeta(ObjectNode node, boolean commitHashIncluded, ScenarioStore scenarioStore) {
		node.put("name", scenarioStore.getName());
		node.put("description", scenarioStore.getDescription());
		node.put("release", HashGenerator.releaseNumber());
		if (commitHashIncluded)
			node.put("commithash", HashGenerator.commitHash());
	}

	private static ObjectNode serializeVadereNode(ScenarioStore scenarioStore) {
		ObjectNode vadereNode = StateJsonConverter.createObjectNode();

		vadereNode.put(StateJsonConverter.MAIN_MODEL_KEY, scenarioStore.getMainModel());

		// vadere > attributesModel
		ObjectNode attributesModelNode = StateJsonConverter.serializeAttributesModelToNode(scenarioStore.getAttributesList());
		vadereNode.set("attributesModel", attributesModelNode);

		// vadere > attributesSimulation
		vadereNode.set("attributesSimulation", StateJsonConverter.convertValue(scenarioStore.getAttributesSimulation(), JsonNode.class));

		// vadere > topography
		ObjectNode topographyNode = StateJsonConverter.serializeTopographyToNode(scenarioStore.getTopography());
		vadereNode.set("topography", topographyNode);

		// vadere > eventInfos
		// We get a complete tree here and not only a node. Therefore, use "setAll()" instead of "set()".
		ObjectNode eventNode = StateJsonConverter.serializeEventsToNode(scenarioStore.getEventInfoStore());
		vadereNode.setAll(eventNode);

		return vadereNode;
	}

	public static Scenario cloneScenarioRunManager(Scenario original) throws IOException {
		JsonNode clone = serializeScenarioRunManagerToNode(original, false);
		return deserializeScenarioRunManagerFromNode(clone);
	}

	public static ScenarioStore cloneScenarioStore(ScenarioStore scenarioStore) throws IOException {
		JsonNode attributesSimulationNode = StateJsonConverter.convertValue(scenarioStore.getAttributesSimulation(), JsonNode.class);
		ObjectNode attributesModelNode = StateJsonConverter.serializeAttributesModelToNode(scenarioStore.getAttributesList());
		ObjectNode topographyNode = StateJsonConverter.serializeTopographyToNode(scenarioStore.getTopography());
		return new ScenarioStore(scenarioStore.getName(), scenarioStore.getDescription(), scenarioStore.getMainModel(),
				StateJsonConverter.deserializeAttributesListFromNode(attributesModelNode),
				StateJsonConverter.deserializeAttributesSimulationFromNode(attributesSimulationNode),
				StateJsonConverter.deserializeTopographyFromNode(topographyNode));
	}
}
