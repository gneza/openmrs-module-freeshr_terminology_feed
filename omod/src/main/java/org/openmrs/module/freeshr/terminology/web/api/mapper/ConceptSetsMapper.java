package org.openmrs.module.freeshr.terminology.web.api.mapper;

import org.openmrs.ConceptSet;
import org.openmrs.module.freeshr.terminology.web.api.Concept;
import org.openmrs.module.freeshr.terminology.web.api.SimpleConceptRepresentation;
import org.openmrs.module.freeshr.terminology.web.config.TrServerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

@Component
public class ConceptSetsMapper implements ConceptMappingCommons {

    private TrServerProperties properties;

    @Autowired
    public ConceptSetsMapper(TrServerProperties properties) {
        this.properties = properties;
    }

    @Override
    public Concept map(Concept concept, org.openmrs.Concept openmrsConcept) {
        concept.setSetMembers(mapSetMembers(openmrsConcept.getSetMembers()));
        return concept;
    }

    private List<SimpleConceptRepresentation> mapSetMembers(List<org.openmrs.Concept> members) {
        List<SimpleConceptRepresentation> conceptSetMembers = new ArrayList<>();
        for (org.openmrs.Concept member : members) {
            conceptSetMembers.add(getSimplifiedConcept(member));
        }
        return conceptSetMembers;
    }

    private SimpleConceptRepresentation getSimplifiedConcept(org.openmrs.Concept concept) {
        SimpleConceptRepresentation simpleConceptRepresentation = new SimpleConceptRepresentation();
        simpleConceptRepresentation.setDisplay(concept.getName(Locale.ENGLISH).getName());
        simpleConceptRepresentation.setUuid(concept.getUuid());
        simpleConceptRepresentation.setUri(getConceptUri(concept.getUuid()));
        return simpleConceptRepresentation;
    }

    private String getConceptUri(String uuid) {
        return properties.getConceptUri() + uuid;
    }
}
