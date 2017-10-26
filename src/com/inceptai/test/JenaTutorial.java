package com.inceptai.test;

import org.apache.jena.rdf.model.*;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.*;

import javax.swing.plaf.nimbus.State;
import java.io.InputStream;

public class JenaTutorial {
    public static String personURI = "http://sanmateo/jack_britt";
    public static String givenName = "Jack";
    public static String familyName = "Britt";
    public static String fullName = givenName + " " + familyName;

    public static void main(String args[]) {

        Model model = loadExample1();
        Model model2 = loadExample2();
        // dumpModel(model);

    }

    private static void dumpModel(Model model) {
        StmtIterator iter = model.listStatements();

        while (iter.hasNext()) {
            Statement stmt = iter.nextStatement();
            Resource res = stmt.getSubject();
            Property predicate = stmt.getPredicate();
            RDFNode rdfNode = stmt.getObject();
            System.out.print("NEW STATEMENT: ");
            System.out.print("FROM Resource: " + res.toString());
            System.out.print(" WITH Property: " + predicate.toString());
            if (rdfNode instanceof Resource) {
                System.out.print (" TO Object resource: " + rdfNode.toString());
            } else {
                System.out.print( " TO Literal resource: " + rdfNode.toString());
            }
            System.out.println("  END STATEMENT.");
        }

        System.out.println("RDF Dump.");
        model.write(System.out);
        System.out.println("XML/ABBREV DUMP");
        model.write(System.out, "RDF/XML-ABBREV");
        System.out.println("N-TRIPLES");
        model.write(System.out, "N-TRIPLES");
        // readFromFile("vc-db-1.rdf");
        loadExample2();
    }

    private static Model readFromFile(String filename) {
        Model model = ModelFactory.createDefaultModel();

        InputStream in = FileManager.get().open(filename);
        if (in == null) {
            throw new IllegalArgumentException("File " + filename + " not found.");
        }
        model.read(in, null);
        System.out.println("Dumping model read from file: " + filename);
        model.write(System.out);
        return model;
    }

    private static Model loadExample1() {
        Model model = ModelFactory.createDefaultModel();

        Resource resource = model.createResource(personURI);
        resource.addProperty(VCARD.FN, fullName);

        Resource blankResource = model.createResource()
                .addProperty(VCARD.Given, givenName)
                .addProperty(VCARD.Family, familyName);
        resource.addProperty(VCARD.N, blankResource);
        return model;
    }

    private static Model loadExample2() {
        Model m = ModelFactory.createDefaultModel();
        String nsA = "http://somewhere/else#";
        String nsB = "http://nowhere/else#";
        Resource root = m.createResource( nsA + "root" );
        Property P = m.createProperty( nsA + "P" );
        Property Q = m.createProperty( nsB + "Q" );
        Resource x = m.createResource( nsA + "x" );
        Resource y = m.createResource( nsA + "y" );
        Resource z = m.createResource( nsA + "z" );
        m.add( root, P, x ).add( root, P, y ).add( y, Q, z );
        System.out.println( "# -- no special prefixes defined" );
        m.write( System.out );
        System.out.println( "# -- nsA defined" );
        m.setNsPrefix( "nsA", nsA );
        m.write( System.out );
        System.out.println( "# -- nsA and cat defined" );
        m.setNsPrefix( "cat", nsB );
        m.write( System.out );
        return m;
    }
}
