/*
 * Copyright (C) 2016 Alasdair Mercer, Skelp
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
package io.skelp.verifier;

import static org.mockito.Mockito.*;

import java.lang.reflect.Array;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.skelp.verifier.message.MessageSource;
import io.skelp.verifier.message.ResourceBundleMessageSource;
import io.skelp.verifier.message.locale.LocaleContext;
import io.skelp.verifier.message.locale.SimpleLocaleContext;
import io.skelp.verifier.verification.SimpleVerification;
import io.skelp.verifier.verification.TestVerificationProvider;
import io.skelp.verifier.verification.Verification;
import io.skelp.verifier.verification.VerificationProvider;
import io.skelp.verifier.verification.report.DefaultReportExecutorProvider;
import io.skelp.verifier.verification.report.ReportExecutor;

/**
 * <p>
 * Base for test cases for {@link CustomVerifier} implementation classes.
 * </p>
 *
 * @param <T>
 *         the value type for the {@link CustomVerifier} being tested
 * @param <V>
 *         the type of the {@link CustomVerifier} being tested
 * @author Alasdair Mercer
 */
@RunWith(MockitoJUnitRunner.class)
public abstract class CustomVerifierTestCaseBase<T, V extends CustomVerifier<T, V>> {

    /**
     * <p>
     * Creates a typed array containing the {@code elements} provided.
     * </p>
     *
     * @param elements
     *         the elements for the array
     * @param <T>
     *         the type of elements to be contained within the array
     * @return An array containing {@code elements}.
     */
    protected static <T> T[] createArray(T... elements) {
        return elements;
    }

    /**
     * <p>
     * Creates an empty typed array based on the specified class.
     * </p>
     *
     * @param cls
     *         the class for the array type
     * @param <T>
     *         the type of the array
     * @return An empty array.
     */
    protected static <T> T[] createEmptyArray(Class<T> cls) {
        @SuppressWarnings("unchecked")
        T[] array = (T[]) Array.newInstance(cls, 0);
        return array;
    }

    /**
     * <p>
     * Creates a map populated with the specified {@code keys} and their corresponding {@code values}.
     * </p>
     *
     * @param keys
     *         the keys to be mapped to {@code values}
     * @param values
     *         the corresponding values to be mapped to {@code keys}
     * @param <K>
     *         the type of the keys
     * @param <V>
     *         the type of the values
     * @return A {@code Map} containing all of the key/value pairs.
     */
    protected static <K, V> Map<K, V> createMap(K[] keys, V[] values) {
        Map<K, V> map = new LinkedHashMap<>(keys.length);

        for (int i = 0; i < keys.length; i++) {
            map.put(keys[i], values[i]);
        }

        return map;
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Captor
    private ArgumentCaptor<Object> argsCaptor;
    @Mock
    private LocaleContext mockLocaleContext;
    @Mock
    private MessageSource mockMessageSource;
    @Mock
    private ReportExecutor mockReportExecutor;
    @Mock
    private Verification<T> mockVerification;
    @Mock
    private VerificationProvider mockVerificationProvider;

    private T value;

    private V customVerifier;

    @Before
    public void setUp() throws Exception {
        when(mockVerificationProvider.getVerification(any(), any())).thenAnswer(invocation -> new SimpleVerification<>(new SimpleLocaleContext(), new ResourceBundleMessageSource(), new DefaultReportExecutorProvider().getReportExecutor(), invocation.getArguments()[0], invocation.getArguments()[1]));

        TestVerificationProvider.setDelegate(mockVerificationProvider);

        when(mockVerification.getLocaleContext()).thenReturn(mockLocaleContext);
        when(mockVerification.getMessageSource()).thenReturn(mockMessageSource);
        when(mockVerification.getReportExecutor()).thenReturn(mockReportExecutor);
        when(mockVerification.getValue()).thenAnswer(invocation -> value);

        value = null;

        customVerifier = createCustomVerifier();
    }

    @After
    public void tearDown() throws Exception {
        TestVerificationProvider.setDelegate(null);
    }

    /**
     * <p>
     * Creates an instance of the custom verifier test subject to be tested.
     * </p>
     *
     * @return The {@link AbstractCustomVerifier} to be tested.
     */
    protected abstract V createCustomVerifier();

    /**
     * <p>
     * Returns an argument captor to be be used to capture any varargs that are passed to {@link Verification#report}.
     * </p>
     *
     * @return An {@code ArgumentCaptor} to be used to capture optional format arguments.
     */
    protected ArgumentCaptor<Object> getArgsCaptor() {
        return argsCaptor;
    }

    /**
     * <p>
     * Returns the custom verifier test subject that is being tested.
     * </p>
     *
     * @return The {@link AbstractCustomVerifier} being tested.
     */
    protected V getCustomVerifier() {
        return customVerifier;
    }

    /**
     * <p>
     * Returns the mock locale context being used to test the subject.
     * </p>
     *
     * @return The mock {@link LocaleContext}.
     */
    protected LocaleContext getMockLocaleContext() {
        return mockLocaleContext;
    }

    /**
     * <p>
     * Returns the mock message source being used to test the subject.
     * </p>
     *
     * @return The mock {@link MessageSource}.
     */
    protected MessageSource getMockMessageSource() {
        return mockMessageSource;
    }

    /**
     * <p>
     * Returns the mock report executor being used to test the subject.
     * </p>
     *
     * @return The mock {@link ReportExecutor}.
     */
    protected ReportExecutor getMockReportExecutor() {
        return mockReportExecutor;
    }

    /**
     * <p>
     * Returns the mock verification being used to test the subject.
     * </p>
     *
     * @return The mock {@link Verification}.
     */
    protected Verification<T> getMockVerification() {
        return mockVerification;
    }

    /**
     * <p>
     * Returns the value being used to test the subject.
     * </p>
     *
     * @return The value.
     */
    protected T getValue() {
        return value;
    }

    /**
     * <p>
     * Sets the value being used to test the subject to {@code value}.
     * </p>
     *
     * @param value
     *         the value to be set
     */
    protected void setValue(T value) {
        this.value = value;
    }
}
