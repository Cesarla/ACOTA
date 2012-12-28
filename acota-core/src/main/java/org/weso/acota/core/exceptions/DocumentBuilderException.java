package org.weso.acota.core.exceptions;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * This class models an exception occurred during the creation of a DOM.
 */
public class DocumentBuilderException extends Exception {

	private static final long serialVersionUID = 634129594668717812L;

	public DocumentBuilderException(DocumentBuilderException e) {
        super(e);
    }

    public DocumentBuilderException(IOException e) {
        super(e);
    }

    public DocumentBuilderException(ParserConfigurationException e) {
        super(e);
    }

    public DocumentBuilderException(SAXException e) {
        super(e);
    }

}
