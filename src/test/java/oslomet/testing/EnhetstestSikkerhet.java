package oslomet.testing;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;

import org.mockito.stubbing.Answer;
import org.springframework.mock.web.MockHttpSession;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestSikkerhet {

    @InjectMocks
    private Sikkerhet sikkerhet;

    @Mock
    private BankRepository repository;

    @Mock
    MockHttpSession session;

    @Before
    public void initSession() {
        Map<String, Object> attributes = new HashMap<String, Object>();

        doAnswer(new Answer<Object>(){
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String key = (String) invocation.getArguments()[0];
                return attributes.get(key);
            }
        }).when(session).getAttribute(anyString());

        // Slett under!!
        doAnswer(new Answer<Object>(){
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String key = (String) invocation.getArguments()[0];
                Object value = invocation.getArguments()[1];
                attributes.put(key, value);
                return null;
            }
        }).when(session).setAttribute(anyString(), any());
    }

    @Test
    public void test_sjekkLoggInn() {
        // arrange
        when(repository.sjekkLoggInn(anyString(), anyString())).thenReturn("OK");

        //session.setAttribute("Innlogget", "01010110523");

        // act
        String resultat = sikkerhet.sjekkLoggInn("01010110523","damni!");
        // assert
        assertEquals("OK", resultat);
    }

    @Test
    public void test_loggetInn() {
        // arrange
        session.setAttribute("Innlogget","01010110523");

        // act
        String resultat = sikkerhet.loggetInn();

        // Assert
        assertEquals("01010110523", resultat);
    }

    @Test
    public void resr_loggetInn_IkkeLoggetInn() {
        //  arrange
        session.setAttribute("Innlogget", null);

        // act
        String resultat = sikkerhet.loggetInn();

        // assert
        assertNull(resultat);
    }

    @Test
    public void loggUt() {
        // Arrange
        session.setAttribute("Innlogget", "01010110523");

        // Act
        sikkerhet.loggUt();

        // Assert
        session.getAttribute("Innlogget");
    }

    @Test
    public void test_loggInnAdmin() {
        // act
        String resultat = sikkerhet.loggInnAdmin("Admin", "Admin");

        // assert
        assertEquals("Logget inn", resultat);
    }

    @Test
    public void test_loggInnAdmin_IkkeLoggetInn() {
        // act
        String resultat = sikkerhet.loggInnAdmin("Admin0123", "blehhh");

        // assert
        assertEquals("Ikke logget inn", resultat);
    }
}
