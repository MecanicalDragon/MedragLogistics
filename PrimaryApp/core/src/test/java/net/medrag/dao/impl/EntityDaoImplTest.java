package net.medrag.dao.impl;

import net.medrag.domain.entity.Cargo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.SimpleNaturalIdLoadAccess;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class EntityDaoImplTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Query query;

    @Mock
    private SimpleNaturalIdLoadAccess<Cargo> address;

    @Mock
    private NativeQuery nativeQuery;

    private EntityDaoImpl<Cargo> dao;

    private Cargo cargo;

    @Before
    public void setUp() throws Exception {
        dao = new EntityDaoImpl<>();
        dao.setSessionFactory(sessionFactory);
        cargo = new Cargo();
    }

    @After
    public void tearDown() throws Exception {
        dao = null;
        cargo = null;
    }

    @Test
    public void addEntity() throws Exception {
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.save(cargo)).thenReturn(10);
        int i = dao.addEntity(cargo);
        assertEquals(i, 10);
    }

    @Test
    public void updateEntityStatus() throws Exception {
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.merge(any(Cargo.class))).thenReturn(new Object());
        dao.updateEntityStatus(cargo);
        verify(session).merge(cargo);
    }

    @Test
    public void refreshEntity() throws Exception {
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        doNothing().when(session).refresh(any(Cargo.class));
        Cargo refreshedCargo = dao.refreshEntity(cargo);
        verify(session).refresh(cargo);
        assertEquals(refreshedCargo, cargo);
    }

    @Test
    public void removeEntity() throws Exception {
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        doNothing().when(session).remove(any(Cargo.class));
        dao.removeEntity(cargo);
        verify(session).remove(cargo);
    }

    @Test
    public void getEntityById() throws Exception {
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.find(Cargo.class, 1)).thenReturn(cargo);
        Cargo entityById = dao.getEntityById(new Cargo(), 1);
        assertEquals(cargo, entityById);
    }

    @Test
    public void getEntityByNaturalId() throws Exception {
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.bySimpleNaturalId(Cargo.class)).thenReturn(address);
        when(address.load("cargo")).thenReturn(cargo);
        Cargo entityById = dao.getEntityByNaturalId(new Cargo(), "cargo");
        assertEquals(cargo, entityById);
    }

    @Test
    public void getEntityCount() throws Exception {
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createNativeQuery(anyString())).thenReturn(nativeQuery);
        when(nativeQuery.getSingleResult()).thenReturn(1);
        int i = dao.getEntityCount(new Cargo());
        assertEquals(i, 1);
    }

    @Test
    public void getEntityList() throws Exception {
        List<Cargo>list = new ArrayList<>();
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(anyString())).thenReturn(query);
        List<Cargo>cargoes = dao.getEntityList(new Cargo());
        assertEquals(cargoes, list);
    }

    @Test
    public void getLastEntities() throws Exception {
        List<Cargo>list = new ArrayList<>();
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(anyString())).thenReturn(query);
        when(query.setMaxResults(anyInt())).thenReturn(query);
        when(query.list()).thenReturn(list);
        List<Cargo>cargoes = dao.getLastEntities(new Cargo(), 20);
        assertEquals(cargoes, list);
    }

}