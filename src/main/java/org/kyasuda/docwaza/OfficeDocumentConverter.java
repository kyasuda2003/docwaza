//
// Docwaza - Java OpenDocument Converter
// Copyright 2009 Art of Solving Ltd
// Copyright 2004-2009 Mirko Nasato
//
// Docwaza is free software: you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public License
// as published by the Free Software Foundation, either version 3 of
// the License, or (at your option) any later version.
//
// Docwaza is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General
// Public License along with Docwaza.  If not, see
// <http://www.gnu.org/licenses/>.
//
package org.kyasuda.docwaza;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.kyasuda.docwaza.document.DefaultDocumentFormatRegistry;
import org.kyasuda.docwaza.document.DocumentFormat;
import org.kyasuda.docwaza.document.DocumentFormatRegistry;
import org.kyasuda.docwaza.office.OfficeManager;

public class OfficeDocumentConverter {

    private final OfficeManager officeManager;

    private final DocumentFormatRegistry formatRegistry;

    private Map<String, ?> defaultLoadProperties = createDefaultLoadProperties();

    public OfficeDocumentConverter(OfficeManager officeManager) {
        this(officeManager, new DefaultDocumentFormatRegistry());
    }

    public OfficeDocumentConverter(OfficeManager officeManager,
            DocumentFormatRegistry formatRegistry) {
        this.officeManager = officeManager;
        this.formatRegistry = formatRegistry;
    }

    private Map<String, Object> createDefaultLoadProperties() {
        Map<String, Object> loadProperties = new HashMap<String, Object>();
        loadProperties.put("Hidden", true);
        loadProperties.put("ReadOnly", true);
        return loadProperties;
    }

    public void setDefaultLoadProperties(Map<String, ?> defaultLoadProperties) {
        this.defaultLoadProperties = defaultLoadProperties;
    }

    public DocumentFormatRegistry getFormatRegistry() {
        return formatRegistry;
    }

    public void convert(File inputFile, File outputFile) {
        String outputExtension = FilenameUtils.getExtension(outputFile.getName());
        DocumentFormat outputFormat = formatRegistry.getFormatByExtension(outputExtension);
        convert(inputFile, outputFile, outputFormat);
    }

    public void convert(File inputFile, File outputFile,
            DocumentFormat outputFormat) {
        convert(inputFile, outputFile, outputFormat,
                new HashMap<String, Serializable>());
    }

    public void convert(File inputFile, File outputFile,
            DocumentFormat outputFormat, Map<String, Serializable> params) {
        String inputExtension = FilenameUtils.getExtension(inputFile.getName());
        DocumentFormat inputFormat = formatRegistry.getFormatByExtension(inputExtension);
        StandardConversionTask conversionTask = new StandardConversionTask(
                inputFile, outputFile, outputFormat, params);
        conversionTask.setDefaultLoadProperties(defaultLoadProperties);
        conversionTask.setInputFormat(inputFormat);
        officeManager.execute(conversionTask);
    }

}
