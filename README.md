# dscTeamLoewen

## Building the Python package

For building the Python package, first make sure you have all the required packages from the `requirements.txt` installed. Afterwards, you can build the Cython files using `python setup_conversion.py build_ext --inplace`.

## Build `PotFieldPrediction`
Inside `PotFieldPrediction/`, run

```bash
mvn package
```

to build the package. To make it installable locally, you can execute 

```bash
./local-mvn-install.sh
```

This will store the built JAR in your local Maven repository.

## Running Vadere

For starting Vadere nothing has changed. The only prerequisite is that the `PotFieldPrediction` is available on the local Maven repository. If thats given, vadere can be built using `mvn clean -Dmaven.test.skip=true install` and started using `java -jar VadereGui/target/vadere-gui.jar`.
