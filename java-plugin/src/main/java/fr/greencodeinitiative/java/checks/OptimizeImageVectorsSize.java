package fr.greencodeinitiative.java.checks;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.ForEachStatement;
import org.sonar.plugins.java.api.tree.LiteralTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.rmi.UnmarshalException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Rule(
        key = "EC53",
        name = "Developpement",
        description = OptimizeImageVectorsSize.MESSAGERULE,
        priority = Priority.MINOR,
        tags = {"bug"})
public class OptimizeImageVectorsSize extends IssuableSubscriptionVisitor {
    protected final String SVG_BEGIN = "<svg";
    protected final String[] SUPERFLUOUS_ATTRIBUTES = new String[]{"xmlns:dc", "xmlns:cc", "xmlns:rdv", "xmlns:svg", "xmlns", "xmlns:sodipodi", "xmlns:inkscape", "inkscape:version", "inkscape:label", "inkscape:groupmode", "sodipodi:docname"};
    protected static final String MESSAGERULE = "Optimize your svg by using open source tools such as SVGGO, Compressor.io or SVG Cleaner";
    // COMPLETE MESSAGE:
    // "Optimize your svg by using open source tools such as SVGGO, Compressor.io or SVG Cleaner. The following should be avoided: The attribute value length can be reduced, e.g: id=\"filter4210\" to id=\"a\" (the same goes for xlink:href attribute). The attributes' value should be approximated: stdDeviation=\"0.57264819\" to stdDeviation=\"0.573\". Duplicated tags. Superfluous tags, e.g: <sodipodi:namedview>, <metadata>. Redundant tags. Superfluous attributes (xmlns:dc, xmlns:cc, xmlns:rdv, xmlns: svg, xmlns, xmlns:sodipodi, xmlns:inkscape, most of id attributes, version, inkscape:version, inkscape:label, inkscape:groupmode, sodipodi:docname)";

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return List.of(Tree.Kind.STRING_LITERAL);
    }

    @Override
    public void visitNode(Tree tree) {

        String value = ((LiteralTree) tree).value();
        // convert to lower case (not case sensitve anymore)
        value = value.toLowerCase();
        // trim a beginning and ending double quote (")
        value = value.replaceAll("^\"|\"$", "");
        // stop escaping double quotes
        value = value.replaceAll("\\\\\"", "\"");;

        // search for svg beginning tag
        int beginIndex = value.indexOf(SVG_BEGIN);
        if (beginIndex < 0) {
            // the string doesn't contain any svg
            return;
        }

        // Parse svg as xml and explore its tree
        try {
            // Build the doc from the XML file
            InputSource source = new InputSource(new StringReader(value));

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(source);

            // the string is a valid xml file

            XPath xpath = XPathFactory.newInstance().newXPath();

            // Note to developers: check xpath expressions by using xpather: http://xpather.com/

            // Superfluous tags, e.g: <sodipodi:namedview>, <metadata>
            if (((Double) xpath.evaluate("count(*//sodipodi:namedview)",
                    doc, XPathConstants.NUMBER)).intValue() > 0 || ((Double) xpath.evaluate("count(*//metadata)",
                    doc, XPathConstants.NUMBER)).intValue() > 0) {
                reportIssue(tree, MESSAGERULE);
                return;
            }

            int nbId = ((Double) xpath.evaluate("count(//@id)", doc, XPathConstants.NUMBER)).intValue();
            int nbDistinctId = ((Double) xpath.evaluate("count(//@id[not(. = preceding::*/@id)])", doc, XPathConstants.NUMBER)).intValue();
            int nbHref = ((Double) xpath.evaluate("count(//@xlink:href)", doc, XPathConstants.NUMBER)).intValue();
            int nbDistinctHref = ((Double) xpath.evaluate("count(//@xlink:href[not(. = preceding::*/@xlink:href)])", doc, XPathConstants.NUMBER)).intValue();

            // Duplicated tags (tags with the same xlink:href or same id attributes)
            if (nbId != nbDistinctId || nbHref != nbDistinctHref) {
                // count(//@id) returns the number of elements having an attribute "id"
                // count(//@id[not(. = preceding::*/@id)]) returns the number of unique "id" attribute values
                // if the number of "id" attributes and the number of unique "id" attributes values
                // aren't equal, that means at least two elements have an "id" attribute.
                reportIssue(tree, MESSAGERULE);
                return;
            }

            // The attribute value length can be reduced
            if ((boolean) xpath.evaluate("boolean(//@id[string-length() > 3])",
                    doc, XPathConstants.BOOLEAN)) {
                reportIssue(tree, MESSAGERULE);
                return;
            }

            // The attributes' value should be approximated (there are attributes with a numeric value that have more than 3 decimals)
            // @* selects all the attributes of the document
            // [number(.) = .] is a predicate that filters all attributes whose value can be converted to a numeric one.
            if ((boolean) xpath.evaluate("boolean(//@*[number(.) = . and contains(., '.') and string-length(substring-after(., '.')) > 3])", doc, XPathConstants.BOOLEAN)) {
                reportIssue(tree, MESSAGERULE);
                return;
            }

            // Avoid superfluous attributes
            // TODO: make it work with namespaces (xmlns).
            for (String superfluousAttribute : SUPERFLUOUS_ATTRIBUTES) {
                String expression = String.format("boolean(//@*[name()=%s])\n", superfluousAttribute);

                if ((boolean) xpath.evaluate(expression, doc, XPathConstants.BOOLEAN)) {
                    reportIssue(tree, MESSAGERULE);
                    return;
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException | XPathExpressionException e) {
            throw new RuntimeException(e);
        }
    }
}
