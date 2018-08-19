package net.medrag.service;

import net.medrag.dao.MedragRepositoryException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class MedragServiceExceptionTest {

    private MedragServiceException exception;

    @Test(expected = MedragServiceException.class)
    public void nowIKnowWhatToTestHere() throws Exception{
        exception = new MedragServiceException("this is it");
        throw exception;
    }

    @Test(expected = MedragServiceException.class)
    public void nowIKnowWhatToTestHereToo() throws Exception{
        exception = new MedragServiceException(new Exception());
        throw exception;
    }

    @Test(expected = MedragServiceException.class)
    public void nowIKnowWhatToTestHereTree() throws Exception{
        exception = new MedragServiceException("this is it", new Exception());
        throw exception;
    }

}