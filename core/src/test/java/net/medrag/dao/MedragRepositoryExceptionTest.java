package net.medrag.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MedragRepositoryExceptionTest {

    private MedragRepositoryException exception;

    @Test (expected = MedragRepositoryException.class)
    public void iDontEvenKnowWhatToTestHere() throws Exception{
        exception = new MedragRepositoryException("this is it");
        throw exception;
    }

    @Test (expected = MedragRepositoryException.class)
    public void iDontEvenKnowWhatToTestHereToo() throws Exception{
        exception = new MedragRepositoryException("this is it", new Exception());
        throw exception;
    }

    @Test (expected = MedragRepositoryException.class)
    public void iDontEvenKnowWhatToTestHereTree() throws Exception{
        exception = new MedragRepositoryException(new Exception());
        throw exception;
    }

    @Test
    public void iDontEvenKnowWhatToTestHereFor() throws Exception{
        String s = MedragRepositoryException.OperationType.LIST.toString();
    }


}