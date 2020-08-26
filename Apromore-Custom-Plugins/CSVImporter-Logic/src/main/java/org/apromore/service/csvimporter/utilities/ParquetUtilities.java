/*-
 * #%L
 * This file is part of "Apromore Core".
 * %%
 * Copyright (C) 2018 - 2020 Apromore Pty Ltd.
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */
package org.apromore.service.csvimporter.utilities;

import org.apache.parquet.schema.MessageType;
import org.apache.parquet.schema.MessageTypeParser;
import org.apromore.service.csvimporter.model.LogSample;

import java.util.Map;

public class ParquetUtilities {
    public static MessageType createParquetSchema(String[] header, LogSample sample) {
        MessageType schema;
        StringBuilder sb = new StringBuilder();
        sb.append("message EventLog {\n" +
                "required binary caseID;\n" +
                "required binary activity;\n" +
                "required binary endTimestamp;\n" +
                "required binary startTimestamp;\n");

        // Other timestamps
        if (!sample.getOtherTimestamps().isEmpty()) {
            for (Map.Entry<Integer, String> otherTimestamp : sample.getOtherTimestamps().entrySet()) {
                sb.append("optional ");
                sb.append("binary ");
                sb.append(header[otherTimestamp.getKey()].replaceAll("\\s", "").toLowerCase() + ";\n");
            }
        }
        sb.append("required binary resource;\n");


        // Case Attributes
        if (sample.getCaseAttributesPos() != null && !sample.getCaseAttributesPos().isEmpty()) {
            for (int columnPos : sample.getCaseAttributesPos()) {
                sb.append("optional ");
                sb.append("binary ");
                sb.append(header[columnPos].replaceAll("\\s", "").toLowerCase() + ";\n");
            }
        }

        // Event Attributes
        if (sample.getEventAttributesPos() != null && !sample.getEventAttributesPos().isEmpty()) {
            for (int columnPos : sample.getEventAttributesPos()) {
                sb.append("optional ");
                sb.append("binary ");
                sb.append(header[columnPos].replaceAll("\\s", "").toLowerCase() + ";\n");
            }
        }

        sb.append("}");

        System.out.println("schema " + sb);
        schema = MessageTypeParser.parseMessageType(sb.toString());
        return schema;
    }
}