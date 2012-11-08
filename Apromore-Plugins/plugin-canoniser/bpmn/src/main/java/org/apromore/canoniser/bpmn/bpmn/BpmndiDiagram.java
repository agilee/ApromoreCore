package org.apromore.canoniser.bpmn.bpmn;

// Java 2 Standard packages
import java.util.Map;
import java.util.logging.Logger;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.namespace.QName;

// Local packages
import org.apromore.anf.AnnotationType;
import org.apromore.anf.AnnotationsType;
import org.apromore.anf.BaseVisitor;
import org.apromore.anf.DocumentationType;
import org.apromore.anf.GraphicsType;
import org.apromore.anf.SimulationType;
import org.apromore.canoniser.exception.CanoniserException;
import org.omg.spec.bpmn._20100524.di.BPMNDiagram;
import org.omg.spec.bpmn._20100524.di.BPMNPlane;
import org.omg.spec.bpmn._20100524.model.TBaseElement;
import org.omg.spec.bpmn._20100524.model.TDataAssociation;
import org.omg.spec.bpmn._20100524.model.TDataObject;
import org.omg.spec.bpmn._20100524.model.TDataObjectReference;
import org.omg.spec.bpmn._20100524.model.TDataStoreReference;
import org.omg.spec.bpmn._20100524.model.TFlowNode;
import org.omg.spec.bpmn._20100524.model.TLane;
import org.omg.spec.bpmn._20100524.model.TMessageFlow;
import org.omg.spec.bpmn._20100524.model.TParticipant;
import org.omg.spec.bpmn._20100524.model.TProcess;

/**
 * BPMNDI Diagram element with canonisation methods.
 *
 * @author <a href="mailto:simon.raboczi@uqconnect.edu.au">Simon Raboczi</a>
 */
@XmlRootElement(namespace = "http://www.omg.org/spec/BPMN/20100524/DI", name = "BPMNDiagram")
public class BpmndiDiagram extends BPMNDiagram {

    /** Logger.  Named after the class. */
    private final Logger logger = Logger.getLogger(BpmndiDiagram.class.getCanonicalName());

    /** No-arg constructor. */
    public BpmndiDiagram() { }

    /**
     * Construct a BPMNDI Diagram corresponding to an ANF AnnotationsType.
     *
     * @param anf  an ANF model, never <code>null</code>
     * @param initializer  BPMN document construction state
     */
    public BpmndiDiagram(final AnnotationsType anf, final Initializer initializer) {

        final BpmndiObjectFactory bpmndiObjectFactory = new BpmndiObjectFactory();

        // Create BPMNDiagram
        final BPMNDiagram bpmnDiagram = this;
        bpmnDiagram.setId(initializer.newId("diagram"));
        bpmnDiagram.setName(anf.getName());

        // Create BPMNPlane
        final BPMNPlane bpmnPlane = new BPMNPlane();
        bpmnPlane.setId(initializer.newId("plane"));
        assert bpmnDiagram.getBPMNPlane() == null;
        bpmnDiagram.setBPMNPlane(bpmnPlane);

        // Populate the BPMNPlane with elements for each CPF Annotation
        for (final AnnotationType annotation : anf.getAnnotation()) {
            //logger.info("Annotation id=" + annotation.getId() + " cpfId=" + annotation.getCpfId());
            annotation.accept(new BaseVisitor() {
                @Override public void visit(final DocumentationType that) {
                    logger.info("  Documentation");
                }

                @Override public void visit(final GraphicsType graphics) {
                    try {
                        TBaseElement bpmnElement = initializer.findElement(graphics.getCpfId());

                        if (bpmnElement instanceof TDataObject          ||
                            bpmnElement instanceof TDataObjectReference ||
                            bpmnElement instanceof TDataStoreReference  ||
                            bpmnElement instanceof TFlowNode            ||
                            bpmnElement instanceof TLane                ||
                            bpmnElement instanceof TParticipant         ||
                            bpmnElement instanceof TProcess) {

                            // TODO - remove/refine this kludge, which exists to humor the YAWL canoniser
                            if (graphics.getPosition().size() == 0) {
                                initializer.warn("Skipping ANF Graphics with no waypoint, since it's probably the top-level Net");
                                return;  // skip generating a shape for this element
                            }

                            bpmnPlane.getDiagramElement().add(bpmndiObjectFactory.createBPMNShape(new BpmndiShape(graphics, initializer)));

                        } else if (bpmnElement instanceof TDataAssociation ||
                                   bpmnElement instanceof TMessageFlow     ||
                                   bpmnElement instanceof BpmnSequenceFlow) {

                            bpmnPlane.getDiagramElement().add(bpmndiObjectFactory.createBPMNEdge(new BpmndiEdge(graphics, initializer)));

                        } else if (bpmnElement == null) {
                            //throw new CanoniserException("CpfId \"" + annotation.getCpfId() + "\" in ANF document not found in CPF document");
                            initializer.warn("CpfId \"" + annotation.getCpfId() + "\" in ANF document not found in CPF document");
                        } else {
                            throw new CanoniserException("CpfId \"" + annotation.getCpfId() + " has bpmnElement " + bpmnElement);
                        }
                    } catch (CanoniserException e) {
                        throw new RuntimeException(e);  // TODO - remove wrapper hack
                    }
                }

                @Override public void visit(final SimulationType that) {
                    logger.info("  Simulation");
                }
            });

            for (Map.Entry<QName, String> entry : annotation.getOtherAttributes().entrySet()) {
                logger.info("  Annotation attribute " + entry.getKey() + "=" + entry.getValue());
            }
        }
    }
}
