package org.kusovf.dublicator.xml;


import com.google.common.collect.ImmutableSet;
import org.kusovf.dublicator.SAXGenerator;
import org.kusovf.dublicator.Variable;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static org.junit.Assert.assertEquals;

public class Tests {

    @Test
    public void replaceContent() {
        String in = "<start><someTag>content ${var}</someTag></start>";
        SAXGenerator saxGenerator = new SAXGenerator(1, 1);
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        saxGenerator.reproduce(new ByteArrayInputStream("<start><someTag>content ${var}</someTag></start>".getBytes()), result);
        assertEquals(in, result.toString());
    }

    @Test
    public void dublicate() {
        String in = "<start><someTag>content ${var}</someTag></start>";
        SAXGenerator saxGenerator = new SAXGenerator(2, 1);
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        saxGenerator.reproduce(new ByteArrayInputStream(in.getBytes()), result);
        assertEquals("<start><someTag>content ${var}</someTag><someTag>content ${var}</someTag></start>", result.toString());
    }

    @Test
    public void replaceVar() {
        String in = "<start><someTag>content ${var}</someTag></start>";
        SAXGenerator saxGenerator = new SAXGenerator(1, 1);
        saxGenerator.setVars(ImmutableSet.of(new Variable("var", 0)));
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        saxGenerator.reproduce(new ByteArrayInputStream(in.getBytes()), result);
        assertEquals("<start><someTag>content 0</someTag></start>", result.toString());
    }

    @Test
    public void replaceAndVar() {
        String in = "<start><someTag>content ${var}</someTag></start>";
        SAXGenerator saxGenerator = new SAXGenerator(2, 1);
        saxGenerator.setVars(ImmutableSet.of(new Variable("var", 0)));
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        saxGenerator.reproduce(new ByteArrayInputStream(in.getBytes()), result);
        assertEquals("<start><someTag>content 0</someTag><someTag>content 1</someTag></start>", result.toString());
    }

    @Test
    public void replaceTwoVars() {
        String in = "<start><someTag>content ${var1} ${var2=3}</someTag></start>";
        SAXGenerator saxGenerator = new SAXGenerator(1, 1);
        saxGenerator.setVars(ImmutableSet.of(new Variable("var1", 0), new Variable("var2", 3)));
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        saxGenerator.reproduce(new ByteArrayInputStream(in.getBytes()), result);
        assertEquals("<start><someTag>content 0 3</someTag></start>", result.toString());
    }
}
