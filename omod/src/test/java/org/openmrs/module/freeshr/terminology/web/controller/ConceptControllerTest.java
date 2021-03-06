package org.openmrs.module.freeshr.terminology.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import org.openmrs.api.ConceptService;
import org.openmrs.module.freeshr.terminology.exception.ConceptNotFoundException;
import org.openmrs.module.freeshr.terminology.web.api.mapper.ConceptMapper;

import java.io.IOException;

public class ConceptControllerTest {

    private ConceptController conceptController;
    @Mock
    private ConceptService conceptService;
    @Mock
    private ConceptMapper conceptMapper;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        conceptController = new ConceptController(conceptMapper, conceptService);
    }

    @Test
    public void shouldReturnConcept() throws IOException {
        final String uuid = "216c8246-202c-4376-bfa8-3278d1049630";
        final org.openmrs.Concept openmrsConcept = new org.openmrs.Concept();
        when(conceptService.getConceptByUuid(uuid)).thenReturn(openmrsConcept);
        conceptController.getConcept(uuid);
        verify(conceptMapper).map(openmrsConcept);
    }

    @Test(expected = ConceptNotFoundException.class)
    public void shouldThrowExceptionWhenConceptNotFound() {
        final String uuid = "216c8246-202c-4376-bfa8-3278d1049630";
        when(conceptService.getConceptByUuid(uuid)).thenReturn(null);
        conceptController.getConcept(uuid);
    }
}