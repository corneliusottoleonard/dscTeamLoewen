package org.vadere.gui.topographycreator.control.attribtable.tree;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

/**
 * A field node represents an object field. It additionally stores a value node.
 */
public class FieldNode extends AttributeTree.TreeNode {
    private ValueNode node;
    private Field field;

    public FieldNode(AttributeTree.TreeNode parent,@NotNull Field field) {
        super(parent, field);
        this.node = new ValueNode(getParent(), field.getName(), field.getType(), null);
        this.field = field;
    }

    //This constructor is used for ArrayNodes to construct custom named FieldNodes e.g "0","1",...
    public FieldNode(AttributeTree.TreeNode parent, String fieldName, Class fieldType, ValueNode node) {
        super(parent, fieldName, fieldType);
        this.node = node;
    }
    @Override
    public void updateValues(Object obj) throws IllegalAccessException, TreeException {
        setReference(obj);
        notifyValueListeners();
    }

    @Override
    public void updateParentsFieldValue(String fieldName, Object object) throws NoSuchFieldException, IllegalAccessException {
        // should it be called ?
        getFieldType().getDeclaredField(fieldName).set(getReference(), object);
        getParent().updateParentsFieldValue(getFieldName(), getReference());
        throw new UnsupportedOperationException();
    }

    public Field getField() {
        return this.field;
    }

    public ValueNode getValueNode() {
        return node;
    }

    public void setValueNode(ValueNode node){ this.node = node;}
}
