package mock;

import java.util.ArrayList;
import java.util.List;

public class FieldSequence implements FieldSequenceI {

    public List<FieldI> fields = new ArrayList<FieldI>();

    @Override
    public List<FieldI> getFields() {
        return fields;
    }

    @Override
    public void addField(String filePath) {
        fields.add(new Field(filePath));
    }
}
