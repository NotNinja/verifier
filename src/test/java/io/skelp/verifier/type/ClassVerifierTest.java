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
package io.skelp.verifier.type;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

import io.skelp.verifier.AbstractCustomVerifierTestBase;

/**
 * Tests for the {@link ClassVerifier} class.
 *
 * @author Alasdair Mercer
 */
public class ClassVerifierTest extends AbstractCustomVerifierTestBase<Class, ClassVerifier> {

    private static final Class<?>[] PRIMITIVES = {Boolean.TYPE, Byte.TYPE, Character.TYPE, Double.TYPE, Float.TYPE, Integer.TYPE, Long.TYPE, Short.TYPE, Void.TYPE};
    private static final Class<?>[] PRIMITIVE_WRAPPERS = {Boolean.class, Byte.class, Character.class, Double.class, Float.class, Integer.class, Long.class, Short.class, Void.TYPE};

    @Override
    protected ClassVerifier createCustomVerifier() {
        return new ClassVerifier(getMockVerification());
    }

    @Test
    public void testAnnotatedWhenTypeIsNull() {
        testAnnotatedHelper(TypeWithAnnotationOne.class, null, false);
    }

    @Test
    public void testAnnotatedWhenValueIsAnnotatedWithDifferentAnnotation() {
        testAnnotatedHelper(TypeWithAnnotationOne.class, AnnotationTwo.class, false);
    }

    @Test
    public void testAnnotatedWhenValueIsAnnotatedWithMultipleAnnotations() {
        testAnnotatedHelper(TypeWithAnnotationOneAndTwo.class, AnnotationTwo.class, true);
    }

    @Test
    public void testAnnotatedWhenValueIsAnnotatedWithNoAnnotations() {
        testAnnotatedHelper(TypeWithNoAnnotations.class, AnnotationOne.class, false);
    }

    @Test
    public void testAnnotatedWhenValueIsAnnotatedWithSameAnnotation() {
        testAnnotatedHelper(TypeWithAnnotationOne.class, AnnotationOne.class, true);
    }

    @Test
    public void testAnnotatedWhenValueIsNull() {
        testAnnotatedHelper(null, AnnotationOne.class, false);
    }

    private void testAnnotatedHelper(Class<?> value, Class<? extends Annotation> type, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().annotated(type));

        verify(getMockVerification()).check(eq(expected), eq("be annotated with '%s'"), getArgsCaptor().capture());

        assertSame("Passes type for message formatting", type, getArgsCaptor().getValue());
    }

    @Test
    public void testAnnotationWhenValueIsAnnotation() {
        testAnnotationHelper(AnnotationOne.class, true);
    }

    @Test
    public void testAnnotationWhenValueIsNotAnnotation() {
        testAnnotationHelper(TypeWithAnnotationOne.class, false);
    }

    @Test
    public void testAnnotationWhenValueIsNull() {
        testAnnotationHelper(null, false);
    }

    private void testAnnotationHelper(Class<?> value, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().annotation());

        verify(getMockVerification()).check(expected, "be an annotation");
    }

    @Test
    public void testAnonymousWhenValueIsAnonymous() {
        testAnonymousHelper(new AnInterface() {
        }.getClass(), true);
    }

    @Test
    public void testAnonymousWhenValueIsNotAnonymous() {
        testAnonymousHelper(ClassVerifierTest.class, false);
    }

    @Test
    public void testAnonymousWhenValueIsNull() {
        testAnonymousHelper(null, false);
    }

    private void testAnonymousHelper(Class<?> value, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().anonymous());

        verify(getMockVerification()).check(expected, "be anonymous");
    }

    @Test
    public void testArrayWhenValueIsArray() {
        testArrayHelper(Object[].class, true);
    }

    @Test
    public void testArrayWhenValueIsNotArray() {
        testArrayHelper(ClassVerifierTest.class, false);
    }

    @Test
    public void testArrayWhenValueIsNull() {
        testArrayHelper(null, false);
    }

    private void testArrayHelper(Class<?> value, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().array());

        verify(getMockVerification()).check(expected, "be an array");
    }

    @Test
    public void testAssignableFromWhenTypeIsNull() {
        testAssignableFromHelper(Object.class, null, false);
    }

    @Test
    public void testAssignableFromWhenValueIsAssignableFromType() {
        testAssignableFromHelper(Object.class, ClassVerifierTest.class, true);
    }

    @Test
    public void testAssignableFromWhenValueIsNotAssignableFromType() {
        testAssignableFromHelper(ClassVerifierTest.class, Object.class, false);
    }

    @Test
    public void testAssignableFromWhenValueIsNull() {
        testAssignableFromHelper(null, Object.class, false);
    }

    @Test
    public void testAssignableFromWhenValueIsType() {
        testAssignableFromHelper(ClassVerifierTest.class, ClassVerifierTest.class, true);
    }

    private void testAssignableFromHelper(Class<?> value, Class<?> type, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().assignableFrom(type));

        verify(getMockVerification()).check(eq(expected), eq("be assignable from '%s'"), getArgsCaptor().capture());

        assertSame("Passes type for message formatting", type, getArgsCaptor().getValue());
    }

    @Test
    public void testEnumerationWhenValueIsEnum() {
        testEnumerationHelper(AnEnum.class, true);
    }

    @Test
    public void testEnumerationWhenValueIsNotEnum() {
        testEnumerationHelper(ClassVerifierTest.class, false);
    }

    @Test
    public void testEnumerationWhenValueIsNull() {
        testEnumerationHelper(null, false);
    }

    private void testEnumerationHelper(Class<?> value, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().enumeration());

        verify(getMockVerification()).check(expected, "be an enum");
    }

    @Test
    public void testInterfacingWhenValueIsInterface() {
        testInterfacingHelper(AnInterface.class, true);
    }

    @Test
    public void testInterfacingWhenValueIsNotInterface() {
        testInterfacingHelper(ClassVerifierTest.class, false);
    }

    @Test
    public void testInterfacingWhenValueIsNull() {
        testInterfacingHelper(null, false);
    }

    private void testInterfacingHelper(Class<?> value, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().interfacing());

        verify(getMockVerification()).check(expected, "be an interface");
    }

    @Test
    public void testNestedWhenValueIsNested() {
        testNestedHelper(TypeWithNoAnnotations.class, true);
    }

    @Test
    public void testNestedWhenValueIsNotNested() {
        testNestedHelper(ClassVerifierTest.class, false);
    }

    @Test
    public void testNestedWhenValueIsNull() {
        testNestedHelper(null, false);
    }

    private void testNestedHelper(Class<?> value, boolean expected) {
        setValue(value);

        assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().nested());

        verify(getMockVerification()).check(expected, "be nested");
    }

    @Test
    public void testPrimitiveWhenValueIsNotPrimitiveOrWrapper() {
        testPrimitiveHelper(new Class<?>[]{Object.class}, false);
    }

    @Test
    public void testPrimitiveWhenValueIsNull() {
        testPrimitiveHelper(new Class<?>[]{null}, false);
    }

    @Test
    public void testPrimitiveWhenValueIsPrimitive() {
        testPrimitiveHelper(PRIMITIVES, true);
    }

    @Test
    public void testPrimitiveWhenValueIsPrimitiveWrapper() {
        List<Class<?>> primitiveWrappers = new ArrayList<>(Arrays.asList(PRIMITIVE_WRAPPERS));
        primitiveWrappers.remove(Void.TYPE);

        testPrimitiveHelper(primitiveWrappers.toArray(new Class<?>[0]), false);
    }

    private void testPrimitiveHelper(Class<?>[] values, boolean expected) {
        for (Class<?> value : values) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().primitive());
        }

        verify(getMockVerification(), times(values.length)).check(expected, "be a primitive");
    }

    @Test
    public void testPrimitiveOrWrapperWhenValueIsNotPrimitiveOrWrapper() {
        testPrimitiveOrWrapperHelper(new Class<?>[]{Object.class}, false);
    }

    @Test
    public void testPrimitiveOrWrapperWhenValueIsNull() {
        testPrimitiveOrWrapperHelper(new Class<?>[]{null}, false);
    }

    @Test
    public void testPrimitiveOrWrapperWhenValueIsPrimitive() {
        testPrimitiveOrWrapperHelper(PRIMITIVES, true);
    }

    @Test
    public void testPrimitiveOrWrapperWhenValueIsPrimitiveWrapper() {
        testPrimitiveOrWrapperHelper(PRIMITIVE_WRAPPERS, true);
    }

    private void testPrimitiveOrWrapperHelper(Class<?>[] values, boolean expected) {
        for (Class<?> value : values) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().primitiveOrWrapper());
        }

        verify(getMockVerification(), times(values.length)).check(expected, "be a primitive or primitive wrapper");
    }

    @Test
    public void testPrimitiveWrapperWhenValueIsNotPrimitiveOrWrapper() {
        testPrimitiveWrapperHelper(new Class<?>[]{Object.class}, false);
    }

    @Test
    public void testPrimitiveWrapperWhenValueIsNull() {
        testPrimitiveWrapperHelper(new Class<?>[]{null}, false);
    }

    @Test
    public void testPrimitiveWrapperWhenValueIsPrimitive() {
        List<Class<?>> primitives = new ArrayList<>(Arrays.asList(PRIMITIVES));
        primitives.remove(Void.TYPE);

        testPrimitiveWrapperHelper(primitives.toArray(new Class<?>[0]), false);
    }

    @Test
    public void testPrimitiveWrapperWhenValueIsPrimitiveWrapper() {
        testPrimitiveWrapperHelper(PRIMITIVE_WRAPPERS, true);
    }

    private void testPrimitiveWrapperHelper(Class<?>[] values, boolean expected) {
        for (Class<?> value : values) {
            setValue(value);

            assertSame("Chains reference", getCustomVerifier(), getCustomVerifier().primitiveWrapper());
        }

        verify(getMockVerification(), times(values.length)).check(expected, "be a primitive wrapper");
    }

    @AnnotationOne
    private static class TypeWithAnnotationOne {
    }

    @AnnotationOne
    @AnnotationTwo
    private static class TypeWithAnnotationOneAndTwo {
    }

    private static class TypeWithNoAnnotations {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE})
    private @interface AnnotationOne {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE})
    private @interface AnnotationTwo {
    }

    private enum AnEnum {}

    private interface AnInterface {
    }
}
