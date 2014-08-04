package org.openmrs.module.freeshr.terminology.util;

import org.openmrs.module.freeshr.terminology.web.api.*;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestUtils {

    public static void assertConcepts(Concept c1, Concept c2) {
        assertNotNull(c1);
        assertNotNull(c2);

        assertEquals(c1.getUuid(), c2.getUuid());
        assertEquals(c1.getVersion(), c2.getVersion());
        assertEquals(c1.getDatatypeName(), c2.getDatatypeName());
        assertEquals(c1.getConceptClass(), c2.getConceptClass());
        assertEquals(c1.isSet(), c2.isSet());
        assertEquals(c1.isRetired(), c2.isRetired());

        assertConceptNames(c1.getFullySpecifiedName(), c2.getFullySpecifiedName());
        assertConceptNameLists(c1.getNames(), c2.getNames());
        assertConceptReferenceTermLists(c1.getReferenceTerms(), c2.getReferenceTerms());
        assertConceptDescriptions(c1.getDescription(), c2.getDescription());
    }

    private static void assertConceptNames(ConceptName n1, ConceptName n2) {
        assertNotNull(n1);
        assertNotNull(n2);

        assertEquals(n1.getConceptName(), n2.getConceptName());
        assertEquals(n1.getConceptNameType(), n2.getConceptNameType());
        assertEquals(n1.getLocale(), n2.getLocale());
    }

    private static void assertConceptDescriptions(ConceptDescription d1, ConceptDescription d2) {
        assertNotNull(d1);
        assertNotNull(d2);

        assertEquals(d1.getDescription(), d2.getDescription());
        assertEquals(d1.getLocale(), d2.getLocale());
    }

    private static void assertConceptNameLists(List<ConceptName> names1, List<ConceptName> names2) {
        assertEquals(names1.size(), names2.size());
        for (ConceptName n1 : names1) {
            for (ConceptName n2 : names2) {
                if (n1.getConceptName().equals(n2.getConceptName())) {
                    assertConceptNames(n1, n2);
                }
            }
        }
    }

    private static void assertConceptReferenceTermLists(List<ConceptReferenceTerm> terms1, List<ConceptReferenceTerm> terms2) {
        assertEquals(terms1.size(), terms2.size());
        for (ConceptReferenceTerm t1 : terms1) {
            for (ConceptReferenceTerm t2 : terms2) {
                if (t1.getUuid().equals(t2.getUuid())) {
                    assertReferenceTerms(t1, t2);
                }
            }
        }
    }

    private static void assertReferenceTerms(ConceptReferenceTerm t1, ConceptReferenceTerm t2) {
        assertNotNull(t1);
        assertNotNull(t2);

        assertEquals(t1.getUuid(), t2.getUuid());
        assertEquals(t1.getName(), t2.getName());
        assertEquals(t1.getCode(), t2.getCode());
        assertEquals(t1.getUri(), t2.getUri());
        assertEquals(t1.getDescription(), t2.getDescription());
        assertEquals(t1.getVersion(), t2.getVersion());
        assertEquals(t1.isRetired(), t2.isRetired());
        assertEquals(t1.getMapType(), t2.getMapType());

        assertConceptSources(t1.getConceptSource(), t2.getConceptSource());
    }

    private static void assertConceptSources(ConceptSource s1, ConceptSource s2) {
        assertNotNull(s1);
        assertNotNull(s2);

        assertEquals(s1.getUuid(), s2.getUuid());
        assertEquals(s1.getName(), s2.getName());
        assertEquals(s1.getDescription(), s2.getDescription());
        assertEquals(s1.getHl7Code(), s2.getHl7Code());
    }
}
