package com.hjss.modelrepository;

import com.hjss.models.Identifiable;
import com.hjss.utilities.IdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ModelRegisterTest {
    private ModelRegister<MockIdentifiable> register;

    @BeforeEach
    public void setUp() {
        register = new ModelRegister<>();
    }

    @Test
    public void testAddAndRetrieveObject() {
        MockIdentifiable obj = new MockIdentifiable(IdGenerator.generateRandomSequence(8));
        String key = register.add(obj);
        assertAll("Ensure the object is added and retrievable correctly",
                () -> assertNotNull(key, "Key should not be null"),
                () -> assertSame(obj, register.get(key), "Retrieved object should match the added object")
        );
    }

    @Test
    public void testRemoveObject() {
        MockIdentifiable obj = new MockIdentifiable(IdGenerator.generateRandomSequence(8));
        String key = register.add(obj);
        register.remove(key);
        assertNull(register.get(key), "Object should be removed from register");
    }

    @Test
    public void testGetAndRotate() {
        MockIdentifiable first = new MockIdentifiable("1");
        MockIdentifiable second = new MockIdentifiable("2");
        register.add(first);
        register.add(second);

        assertSame(first, register.getAndRotate(), "First call should return first object");
        assertSame(second, register.getAndRotate(), "Second call should return second object");
        assertSame(first, register.getAndRotate(), "Third call should wrap to first object");
    }

    @Test
    public void testGetAndIncrement() {
        MockIdentifiable first = new MockIdentifiable("1");
        MockIdentifiable second = new MockIdentifiable("2");
        register.add(first);
        register.add(second);

        assertSame(first, register.getAndIncrement(), "First call should return first object and move to second");
        assertSame(second, register.getAndIncrement(), "Second call should return second object and then the next key should be null");
        assertNull(register.getAndIncrement(), "Using getAndIncrement after retrieving the last object in the register should return null");
    }

    @Test
    public void testEmptyRegister() {
        assertNull(register.get("nonexistent"), "Get on empty register should return null");
        assertNull(register.getAndRotate(), "GetAndRotate on empty register should return null");
        assertNull(register.getAndIncrement(), "GetAndIncrement on empty register should return null");
    }
}

class MockIdentifiable implements Identifiable {
    private final String id;

    public MockIdentifiable(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }
}
