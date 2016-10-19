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
import org.junit.Test;

import io.skelp.verifier.AbstractCustomVerifierTestBase;

/**
 * Tests for the {@link ClassVerifier} class.
 *
 * @author Alasdair Mercer
 */
public class ClassVerifierTest extends AbstractCustomVerifierTestBase<Class, ClassVerifier> {

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
