package de.hpi.bpmn2_0.factory.edge;

/**
 * Copyright (c) 2009
 * Philipp Giese, Sven Wagner-Boysen
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import de.hpi.bpmn2_0.annotations.StencilId;
import de.hpi.bpmn2_0.factory.AbstractEdgesFactory;
import de.hpi.bpmn2_0.model.BaseElement;
import de.hpi.bpmn2_0.model.FormalExpression;
import de.hpi.bpmn2_0.model.connector.SequenceFlow;
import org.oryxeditor.server.diagram.generic.GenericShape;

/**
 * @author Philipp Giese
 * @author Sven Wagner-Boysen
 */
@StencilId("SequenceFlow")
public class SequenceFlowFactory extends AbstractEdgesFactory {

    /*
      * (non-Javadoc)
      *
      * @seede.hpi.bpmn2_0.factory.AbstractBpmnFactory#createProcessElement(org.
      * oryxeditor.server.diagram.Shape)
      */
    // @Override
    protected BaseElement createProcessElement(GenericShape shape) {
        SequenceFlow seqFlow = new SequenceFlow();
        this.setCommonAttributes(seqFlow, shape);
        seqFlow.setId(shape.getResourceId());
        seqFlow.setName(shape.getProperty("name"));

        String conditionType = shape.getProperty("conditiontype");
        String conditionExpression = shape.getProperty("conditionexpression");

        if (!(conditionType == null || conditionType.equals("Default"))
                && !(conditionExpression == null || conditionExpression
                .length() == 0)) {
            seqFlow.setConditionExpression(new FormalExpression(conditionExpression));
        }

        if (conditionType != null && conditionType.equals("Default")) {
            seqFlow.setDefaultSequenceFlow(true);
        }

        /* Unnecessary since migration is doing the same */
//		/*
//		 * Copy condition expression to name attribute, because many tools only
//		 * display the name property
//		 */
//		if((seqFlow.getName() == null || seqFlow.getName().length() == 0)
//				&& seqFlow.getConditionExpression() != null) {
//			String condition = seqFlow.getConditionExpression().toExportString();
//			if(condition != null)
//				seqFlow.setName(condition);
//		}

        /* IsImmediate Property */
        String isImmediate = shape.getProperty("isimmediate");
        if (isImmediate != null && isImmediate.length() > 0) {
            if (isImmediate.equalsIgnoreCase("false"))
                seqFlow.setIsImmediate(false);
            else if (isImmediate.equalsIgnoreCase("true"))
                seqFlow.setIsImmediate(true);
        }

        return seqFlow;
    }

}
