package org.apromore.canoniser.bpmn.cpf;

// Java 2 Standard packages
import javax.xml.namespace.QName;

// Local packages
import org.apromore.canoniser.exception.CanoniserException;
import org.apromore.cpf.SoftType;
import org.omg.spec.bpmn._20100524.model.TDataObject;
import org.omg.spec.bpmn._20100524.model.TDataStoreReference;

/**
 * CPF 1.0 object with convenience methods.
 *
 * @author <a href="mailto:simon.raboczi@uqconnect.edu.au">Simon Raboczi</a>
 */
public class CpfSoftType extends SoftType implements CpfObjectType {

    /** Second superclass. */
    private final CpfObjectType super2;

    // Constructors

    /** No-arg constructor. */
    public CpfSoftType() {
        super2 = new CpfObjectTypeImpl();
    }

    /**
     * Construct a CPF Soft Object corresponding to a BPMN Data Object.
     *
     * @param dataObject  a BPMN Data Object
     * @param parent  the CPF Net this Object will belong to
     * @param initializer  global construction state
     * @throws CanoniserException if construction fails
     */
    public CpfSoftType(final TDataObject dataObject,
                       final CpfNetType  parent,
                       final Initializer initializer) throws CanoniserException {

        super2 = new CpfObjectTypeImpl(dataObject, parent, initializer);
    }

    /**
     * Construct a CPF Soft Object corresponding to a BPMN Data Store Reference.
     *
     * @param dataStoreReference a BPMN Data Store Reference
     * @param parent  the CPF Net this Object will belong to
     * @param initializer  global construction state
     * @throws CanoniserException if construction fails
     */
    public CpfSoftType(final TDataStoreReference dataStoreReference,
                       final CpfNetType          parent,
                       final Initializer         initializer) throws CanoniserException {

        super2 = new CpfObjectTypeImpl(dataStoreReference, parent, initializer);
    }

    // Accessors for CPF extension attributes

    /** {@inheritDoc} */
    public QName getDataStore() {
        return super2.getDataStore();
    }

    /** {@inheritDoc} */
    public void setDataStore(final QName value) {
        super2.setDataStore(value);
    }

    /** {@inheritDoc} */
    public boolean isIsCollection() {
        return super2.isIsCollection();
    }

    /** {@inheritDoc} */
    public void setIsCollection(final Boolean value) {
        super2.setIsCollection(value);
    }

    /** {@inheritDoc} */
    public CpfNetType getNet() {
        return super2.getNet();
    }

    /** {@inheritDoc} */
    public void setNet(final CpfNetType newNet) {
        super2.setNet(newNet);
    }

    /** {@inheritDoc} */
    public String getOriginalName() {
        return super2.getOriginalName();
    }

    /** {@inheritDoc} */
    public void setOriginalName(final String value) {
        super2.setOriginalName(value);
    }
}
