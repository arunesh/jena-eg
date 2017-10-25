package com.inceptai.test;

import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.*;

public class JenaTutorial {
    public static String personURI = "http://sanmateo/jack_britt";
    public static String givenName = "Jack";
    public static String familyName = "Britt";
    public static String fullName = givenName + " " + familyName;

    public static void main(String args[]) {
        Model model = ModelFactory.createDefaultModel();

        Resource resource = model.createResource(personURI);
        resource.addProperty(VCARD.FN, fullName);

        Resource blankResource = model.createResource()
                .addProperty(VCARD.Given, givenName)
                .addProperty(VCARD.Family, familyName);
        resource.addProperty(VCARD.N, blankResource);
    }
}
