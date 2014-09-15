package com.rmemoria.metagen;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;

/**
 * Generates meta-information about a jar file
 * Created by Ricardo Memoria on 11/09/14.
 */
public class MetaGenerator {

    public static final String METAINF_BUILDNUMBER = "Build-Number";
    public static final String METAINF_BUILDDATE = "Built-Date";
    public static final String METAINF_VERSION = "Implementation-Version";

    /**
     * Generate XML file with meta information about the jarfile in the
     * same folder where the jar is
     * @param jarfile name of jar file
     * @throws IOException
     */
    public static void generate(String jarfile) throws Exception {
        File file = new File(jarfile);
        if (!file.exists()) {
            throw new RuntimeException("File not found: " + jarfile);
        }
        FileInputStream f = new FileInputStream(file);
        JarInputStream jar = new JarInputStream(f);
        Manifest mf = jar.getManifest();

        Attributes attributes = mf.getMainAttributes();
        Map<String, String> values = new HashMap<String, String>();

        values.put("file-name", file.getName());
        values.put("build-number", attributes.getValue(METAINF_BUILDNUMBER));
        values.put("build-date", attributes.getValue(METAINF_BUILDDATE));
        values.put("version", attributes.getValue(METAINF_VERSION));

        String checksum = calcFileMD5(jarfile);
        values.put("MD5", checksum);

        writeValues(jarfile + ".xml", values);
    }

    protected static void writeValues(String metafile, Map<String, String> values) throws Exception {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        // root elements
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("package");
        doc.appendChild(rootElement);

        for (String key : values.keySet()) {
            String value = values.get(key);
            Element elem = doc.createElement(key);
            if (value != null) {
                elem.appendChild(doc.createTextNode(value));
                rootElement.appendChild(elem);
            }
        }

        // write the content into xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(metafile));

        // Output to console for testing
        // StreamResult result = new StreamResult(System.out);

        transformer.transform(source, result);
    }


    protected static String calcFileMD5(String filename) throws Exception{
        byte[] b = createChecksum(filename);
        String result = "";

        for (int i=0; i < b.length; i++) {
            result += Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
        }
        return result;
    }

    protected static byte[] createChecksum(String filename) throws Exception {
        InputStream fis =  new FileInputStream(filename);

        byte[] buffer = new byte[1024];
        MessageDigest complete = MessageDigest.getInstance("MD5");
        int numRead;

        do {
            numRead = fis.read(buffer);
            if (numRead > 0) {
                complete.update(buffer, 0, numRead);
            }
        } while (numRead != -1);

        fis.close();
        return complete.digest();
    }
}
