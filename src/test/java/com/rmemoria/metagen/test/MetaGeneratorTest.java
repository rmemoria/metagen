package com.rmemoria.metagen.test;

import com.rmemoria.metagen.MetaGenerator;
import org.junit.Test;

import java.io.*;
import static org.junit.Assert.*;

/**
 * This is a test of the {@link com.rmemoria.metagen.MetaGenerator} class
 *
 * Created by Ricardo Memoria on 15/09/14.
 */
public class MetaGeneratorTest {

    public static final String jarfilename = "target/test-classes/test.jar";

    @Test
    public void xmlGeneratorTest() throws Exception {
        MetaGenerator.generate(jarfilename);

        File xmlfile = new File(jarfilename + ".xml");
        assertTrue(xmlfile.exists());
    }

}
