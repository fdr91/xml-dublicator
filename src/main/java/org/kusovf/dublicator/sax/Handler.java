package org.kusovf.dublicator.sax;

import org.kusovf.dublicator.interfaces.Replacer;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Handler extends DefaultHandler {
    private final OutputStream outputStream;
    private String startTag;
    private String startTagFilled;
    private Replacer replacer;
    private int depth;

    public Handler(OutputStream outputStream, Replacer replacer) {
        this.outputStream = outputStream;
        this.replacer = replacer;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        printTag(qName, attributes, this.outputStream);
        depth++;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (depth > 1) {
            print("</", this.outputStream);
            print(qName, outputStream);
            print(">", outputStream);
            depth--;
        }
    }

    private void printTag(String name, Attributes attributes, OutputStream outputStream) {
        if (depth == 0) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            print(name, attributes, baos);
            startTag = name;
            startTagFilled = baos.toString();
        } else {
            print(name, attributes, outputStream);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        print(replacer.process(new String(ch, start, length)),  outputStream);
    }

    private void print(String str, Attributes attributes, OutputStream out) {
        print("<", out);
        print(replace(str), out);
        if (attributes != null) {
            print(attributes, out);
        }
        print(">", out);
    }

    public String getStartTag() {
        return startTag;
    }

    public String getStartTagFilled() {
        return startTagFilled;
    }

    private void print(Attributes attrbutes, OutputStream out) {
        for (int i = 0; i < attrbutes.getLength(); i++) {
            print(attrbutes.getLocalName(i).getBytes(), out);
            print("=\"", out);
            print(replace(attrbutes.getValue(i)), out);
            print("\"", out);
        }
    }

    private void print(String str, OutputStream out) {
        print(str.getBytes(), out);
    }

    private void print(byte[] bytes, OutputStream out) {
        try {
            out.write(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String replace(String value) {
        return replacer.process(value);
    }
}
