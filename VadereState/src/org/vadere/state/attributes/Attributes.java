package org.vadere.state.attributes;

/**
 * Abstract base class for all static simulation attributes.
 * 
 * Implementations must provide a no-arg default contstructor (either implicitly
 * or explicitly) to enable deserialization from JSON.
 *
 * Attributes are "static" parameters i.e. they are immutable while a simulation
 * is running. This is implemented by deriving from the
 * {@link org.apache.commons.attributes.Sealable} interface. Attributes can have
 * setters, but the setters must call the {@code checkSealed()} method before
 * changing a value! In addition, if an attributes class contains other
 * attributes classes as fields, it must override {@link #seal()} to also seal
 * these objects. All other fields must be immutable (e.g. String, Double,
 * VPoint,...).
 * 
 */
public abstract class Attributes extends DefaultSealable implements Cloneable {
	/** Used for default ID values of some scenario elements. */
	protected static final int ID_NOT_SET = -1;

	public Attributes() {}
	
	/**
	 * Standard shallow clone of attributes. The shallow clone is sufficient
	 * because (as noted above) all fields must be immutable anyway.
	 */
	@Override
	public Attributes clone() {
		try {
			return (Attributes) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException("This should never happen because the base class is Cloneable.", e);
		}
	}

}
