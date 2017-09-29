package com.sbt.aep.xml;

import com.sbt.aep.xml.sax.Handler;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.util.*;

public class SAXGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(SAXGenerator.class);

    private Set<Variable> vars = new HashSet<>();
    private final int count;
    private final int step;

    public SAXGenerator(int count, int step) {
        this.count = count;
        this.step = step;
    }

    public void reproduce(InputStream in, OutputStream out) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            String inString = IOUtils.toString(new InputStreamReader(in));
            LOGGER.info("Start reproducing");
            Handler handler = performIteration(SAXParserFactory.newInstance(), new ByteArrayInputStream(inString.getBytes()), byteArrayOutputStream);
            incrementVars();
            print("<" + handler.getStartTag() + ">" + byteArrayOutputStream.toString(), out);
            for (int i = 0; i < count - 1; i++) {
                handler = performIteration(SAXParserFactory.newInstance(), new ByteArrayInputStream(inString.getBytes()), out);
                incrementVars();
            }
            LOGGER.info("End reproducing");
            print("</" + handler.getStartTag() + ">", out);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void print(String str, OutputStream outputStream) {
        try {
            outputStream.write(str.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void incrementVars() {
        for (Variable var : vars) {
            var.inc(this.step);
        }
    }

    private Handler performIteration(SAXParserFactory factory, InputStream in, OutputStream out) {
        try {
            Handler handler = new Handler(out, new VariableReplacer(getVars()));
            factory.newSAXParser().parse(in, handler);
            return handler;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Collection<Variable> getVars() {
        return Collections.unmodifiableSet(vars);
    }

    public void setVars(Collection<Variable> vars) {
        this.vars = new HashSet<>(vars);
    }
}
