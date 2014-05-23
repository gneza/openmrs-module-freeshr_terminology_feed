package org.openmrs.module.freeshr.terminology.advice;

import org.ict4h.atomfeed.server.service.Event;
import org.ict4h.atomfeed.server.service.EventService;
import org.ict4h.atomfeed.transaction.AFTransactionWork;
import org.ict4h.atomfeed.transaction.AFTransactionWorkWithoutResult;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.openmrs.Concept;
import org.openmrs.api.ConceptService;
import org.openmrs.module.atomfeed.transaction.support.AtomFeedSpringTransactionManager;

import java.lang.reflect.Method;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class ConceptServiceEventInterceptorTest {

    @Mock
    private AtomFeedSpringTransactionManager atomFeedSpringTransactionManager;
    @Mock
    private EventService eventService;

    private ArgumentCaptor<AFTransactionWorkWithoutResult> captor = ArgumentCaptor.forClass(AFTransactionWorkWithoutResult.class);

    private ConceptServiceEventInterceptor publishedFeed;

    private Concept concept;

    @Before
    public void setup() {
        initMocks(this);
        publishedFeed = new ConceptServiceEventInterceptor(atomFeedSpringTransactionManager, eventService);
        concept = new Concept();
        concept.setUuid("uuid");
    }

    @Test
    public void shouldPublishUpdateEventToFeedAfterUpdateConceptOperation() throws Throwable {
        Method method = ConceptService.class.getMethod("updateConcept", Concept.class);
        Object[] objects = new Object[]{concept};

        publishedFeed.afterReturning(null, method, objects, null);
        verify(atomFeedSpringTransactionManager).executeWithTransaction(any(AFTransactionWorkWithoutResult.class));
    }

    @Test
    public void shouldPublishUpdateEventToFeedAfterEveryUpdateConceptOperation() throws Throwable {
        Method method = ConceptService.class.getMethod("updateConcept", Concept.class);
        Object[] objects = new Object[]{concept};
        int updates = 2;
        for (int i = 0; i < updates; i++) {
            publishedFeed.afterReturning(null, method, objects, null);
        }
        verify(atomFeedSpringTransactionManager, times(updates)).executeWithTransaction(any(AFTransactionWorkWithoutResult.class));
    }


    @Test
    public void shouldPublishUpdateEventToFeedAfterSaveConceptOperation() throws Throwable {
        Method method = ConceptService.class.getMethod("saveConcept", Concept.class);
        Object[] objects = new Object[]{concept};

        publishedFeed.afterReturning(null, method, objects, null);
        verify(atomFeedSpringTransactionManager).executeWithTransaction(any(AFTransactionWorkWithoutResult.class));
    }

    @Test
    public void shouldPublishUpdateEventToFeedAfterEverySaveConceptOperation() throws Throwable {
        Method method = ConceptService.class.getMethod("saveConcept", Concept.class);
        Object[] objects = new Object[]{concept};
        int updates = 2;
        for (int i = 0; i < updates; i++) {
            publishedFeed.afterReturning(null, method, objects, null);
        }
        verify(atomFeedSpringTransactionManager, times(updates)).executeWithTransaction(any(AFTransactionWorkWithoutResult.class));
    }

    @Test
    public void shouldSaveEventInTransaction() throws Throwable {
        Method method = ConceptService.class.getMethod("updateConcept", Concept.class);
        Object[] objects = new Object[]{concept};

        publishedFeed.afterReturning(null, method, objects, null);
        verify(atomFeedSpringTransactionManager).executeWithTransaction(captor.capture());

        captor.getValue().execute();
        verify(eventService).notify(any(Event.class));
    }

    @Test
    public void shouldSaveEventInTheSameTransactionAsTheTrigger() throws Throwable {
        Method method = ConceptService.class.getMethod("updateConcept", Concept.class);
        Object[] objects = new Object[]{concept};

        publishedFeed.afterReturning(null, method, objects, null);
        verify(atomFeedSpringTransactionManager).executeWithTransaction(captor.capture());

        assertEquals(AFTransactionWork.PropagationDefinition.PROPAGATION_REQUIRED, captor.getValue().getTxPropagationDefinition());
    }
}