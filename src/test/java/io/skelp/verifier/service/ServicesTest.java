/*
 * Copyright (C) 2017 Alasdair Mercer, Skelp
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.skelp.verifier.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.function.Function;
import org.junit.Test;

import io.skelp.verifier.CustomVerifierProvider;
import io.skelp.verifier.DefaultCustomVerifierProvider;
import io.skelp.verifier.TestCustomVerifierProvider;

/**
 * <p>
 * Tests for the {@link Services} class.
 * </p>
 *
 * @author Alasdair Mercer
 */
public class ServicesTest {

    @Test
    public void testConstructor() {
        // Ensure that Services can be instantiated, if required
        new Services();
    }

    @Test
    public void testFindFirstNonNullForService() {
        Object expected = new Object();
        @SuppressWarnings("unchecked")
        Function<CustomVerifierProvider, Object> mockMapper = mock(Function.class);

        when(mockMapper.apply(isA(DefaultCustomVerifierProvider.class))).thenReturn(null);
        when(mockMapper.apply(isA(TestCustomVerifierProvider.class))).thenReturn(expected);

        Object actual = Services.findFirstNonNullForService(CustomVerifierProvider.class, mockMapper);

        assertSame("Returns first non-null result", expected, actual);

        verify(mockMapper).apply(isA(DefaultCustomVerifierProvider.class));
        verify(mockMapper).apply(isA(TestCustomVerifierProvider.class));
    }

    @Test
    public void testFindFirstNonNullForServiceWhenAllReturnNull() {
        Object result = Services.findFirstNonNullForService(CustomVerifierProvider.class, provider -> null);

        assertNull("Returns null when all service results are null", result);
    }

    @Test
    public void testFindFirstNonNullForServiceWhenNotFound() {
        Object result = Services.findFirstNonNullForService(TestService.class, Function.identity());

        assertNull("Returns null when no services are found", result);
    }

    @Test
    public void testFindFirstNonNullForWeightedService() {
        Object expected = new Object();
        @SuppressWarnings("unchecked")
        Function<CustomVerifierProvider, Object> mockMapper = mock(Function.class);

        when(mockMapper.apply(isA(DefaultCustomVerifierProvider.class))).thenReturn(expected);
        when(mockMapper.apply(isA(TestCustomVerifierProvider.class))).thenReturn(null);

        Object actual = Services.findFirstNonNullForWeightedService(CustomVerifierProvider.class, mockMapper);

        assertSame("Returns first non-null result", expected, actual);

        verify(mockMapper).apply(isA(DefaultCustomVerifierProvider.class));
        verify(mockMapper).apply(isA(TestCustomVerifierProvider.class));
    }

    @Test
    public void testFindFirstNonNullForWeightedServiceWhenAllReturnNull() {
        Object result = Services.findFirstNonNullForWeightedService(CustomVerifierProvider.class, provider -> null);

        assertNull("Returns null when all service results are null", result);
    }

    @Test
    public void testFindFirstNonNullForWeightedServiceWhenNotFound() {
        Object result = Services.findFirstNonNullForWeightedService(TestWeightedService.class, Function.identity());

        assertNull("Returns null when no services are found", result);
    }

    @Test
    public void testGetService() {
        CustomVerifierProvider service = Services.getService(CustomVerifierProvider.class);

        assertNotNull("Never returns null", service);
        assertTrue("Returns first registered service", service instanceof DefaultCustomVerifierProvider);
    }

    @Test(expected = ServiceNotFoundException.class)
    public void testGetServiceThrowsWhenNotFound() {
        Services.getService(TestService.class);
    }

    @Test
    public void testGetServices() {
        List<CustomVerifierProvider> services = Services.getServices(CustomVerifierProvider.class);

        assertNotNull("Never returns null", services);
        assertEquals("List contains all registered services", 2, services.size());
        assertTrue("First item should be first registered service", services.get(0) instanceof DefaultCustomVerifierProvider);
        assertTrue("Last item should be last registered service", services.get(1) instanceof TestCustomVerifierProvider);
    }

    @Test
    public void testGetServicesWhenNoneFound() {
        List<TestService> services = Services.getServices(TestService.class);

        assertNotNull("Never returns null", services);
        assertTrue("List is empty", services.isEmpty());
    }

    @Test
    public void testGetWeightedService() {
        CustomVerifierProvider service = Services.getWeightedService(CustomVerifierProvider.class);

        assertNotNull("Never returns null", service);
        assertTrue("Returns most important service", service instanceof TestCustomVerifierProvider);
    }

    @Test(expected = ServiceNotFoundException.class)
    public void testGetWeightedServiceThrowsWhenNotFound() {
        Services.getWeightedService(TestWeightedService.class);
    }

    @Test
    public void testGetWeightedServices() {
        List<CustomVerifierProvider> services = Services.getWeightedServices(CustomVerifierProvider.class);

        assertNotNull("Never returns null", services);
        assertEquals("List contains all registered services", 2, services.size());
        assertTrue("First item should be most important service", services.get(0) instanceof TestCustomVerifierProvider);
        assertTrue("Last item should be least important service", services.get(1) instanceof DefaultCustomVerifierProvider);
    }

    @Test
    public void testGetWeightedServicesWhenNoneFound() {
        List<TestWeightedService> services = Services.getWeightedServices(TestWeightedService.class);

        assertNotNull("Never returns null", services);
        assertTrue("List is empty", services.isEmpty());
    }

    private static class TestService {
    }

    private static class TestWeightedService implements Weighted {

        @Override
        public int getWeight() {
            return 0;
        }
    }
}
