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

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.skelp.verifier.AbstractCustomVerifier;
import io.skelp.verifier.VerifierException;
import io.skelp.verifier.util.Function;
import io.skelp.verifier.verification.Verification;

/**
 * <p>
 * An implementation of {@link AbstractCustomVerifier} which can be used to verify a {@code Class} value.
 * </p>
 *
 * @author Alasdair Mercer
 */
public final class ClassVerifier extends AbstractCustomVerifier<Class, ClassVerifier> {

    private static final Set<Class<?>> PRIMITIVE_WRAPPERS;

    static {
        final List<Class<?>> primitiveWrappers = Arrays.asList(Boolean.class, Byte.class, Character.class, Double.class, Float.class, Integer.class, Long.class, Short.class, Void.TYPE);
        PRIMITIVE_WRAPPERS = Collections.unmodifiableSet(new HashSet<>(primitiveWrappers));
    }

    /**
     * <p>
     * Creates an instance of {@link ClassVerifier} based on the {@code verification} provided.
     * </p>
     *
     * @param verification
     *         the {@link Verification} to be used
     */
    public ClassVerifier(final Verification<Class> verification) {
        super(verification);
    }

    /**
     * <p>
     * Verifies that the value has at least one annotation of any type.
     * </p>
     * <pre>
     * Verifier.verify((Class) null).annotated()               => FAIL
     * Verifier.verify(MyAnnotatedObject.class).annotated()    => PASS
     * Verifier.verify(MyNonAnnotatedObject.class).annotated() => FAIL
     * </pre>
     *
     * @return A reference to this {@link ClassVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public ClassVerifier annotated() throws VerifierException {
        final Class<?> value = verification().getValue();
        final boolean result = value != null && value.getAnnotations().length > 0;

        verification().check(result, "be annotated");

        return this;
    }

    /**
     * <p>
     * Verifies that the value is annotated with the {@code type} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify(*).annotatedWith(null)                          => FAIL
     * Verifier.verify((Class) null).annotatedWith(*)                  => FAIL
     * Verifier.verify(Object.class).annotatedWith(*)                  => FAIL
     * Verifier.verify(Override.class).annotatedWith(Documented.class) => FAIL
     * Verifier.verify(Override.class).annotatedWith(Retention.class)  => PASS
     * </pre>
     *
     * @param type
     *         the annotation to be checked for on the value (may be {@literal null})
     * @return A reference to this {@link ClassVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public ClassVerifier annotatedWith(final Class<? extends Annotation> type) throws VerifierException {
        final Class<?> value = verification().getValue();
        final boolean result = value != null && type != null && value.isAnnotationPresent(type);

        verification().check(result, "be annotated with '%s'", type);

        return this;
    }

    /**
     * <p>
     * Verifies that the value is annotated with <b>all</b> of the {@code types} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify(*).annotatedWithAll()                                               => PASS
     * Verifier.verify(*).annotatedWithAll((Class[]) null)                                 => FAIL
     * Verifier.verify(*).annotatedWithAll(*, null)                                        => FAIL
     * Verifier.verify((Class) null).annotatedWithAll(*)                                   => FAIL
     * Verifier.verify(Object.class).annotatedWithAll(*)                                   => FAIL
     * Verifier.verify(Override.class).annotatedWithAll(Retention.class, Documented.class) => FAIL
     * Verifier.verify(Override.class).annotatedWithAll(Retention.class, Target.class)     => PASS
     * </pre>
     *
     * @param types
     *         the annotations to be checked for on the value (may be {@literal null})
     * @return A reference to this {@link ClassVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public ClassVerifier annotatedWithAll(final Class<? extends Annotation>... types) throws VerifierException {
        final Class<?> value = verification().getValue();
        final boolean result = value != null && matchAll(types, new Function<Boolean, Class<? extends Annotation>>() {
            @Override
            public Boolean apply(Class<? extends Annotation> input) {
                return input != null && value.isAnnotationPresent(input);
            }
        });

        verification().check(result, "be annotated with all %s", verification().getMessageFormatter().formatArray(types));

        return this;
    }

    /**
     * <p>
     * Verifies that the value is annotated with <b>any</b> of the {@code types} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify(*).annotatedWithAny()                                               => FAIL
     * Verifier.verify(*).annotatedWithAny((Class[]) null)                                 => FAIL
     * Verifier.verify((Class) null).annotatedWithAny(*)                                   => FAIL
     * Verifier.verify(Object.class).annotatedWithAny(*)                                   => FAIL
     * Verifier.verify(Override.class).annotatedWithAny(Retention.class, Documented.class) => PASS
     * Verifier.verify(Override.class).annotatedWithAny(Retention.class, Target.class)     => PASS
     * </pre>
     *
     * @param types
     *         the annotations to be checked for on the value (may be {@literal null})
     * @return A reference to this {@link ClassVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public ClassVerifier annotatedWithAny(final Class<? extends Annotation>... types) throws VerifierException {
        final Class<?> value = verification().getValue();
        final boolean result = value != null && matchAny(types, new Function<Boolean, Class<? extends Annotation>>() {
            @Override
            public Boolean apply(Class<? extends Annotation> input) {
                return input != null && value.isAnnotationPresent(input);
            }
        });

        verification().check(result, "be annotated with any %s", verification().getMessageFormatter().formatArray(types));

        return this;
    }

    /**
     * <p>
     * Verifies that the value is an annotation.
     * </p>
     * <pre>
     * Verifier.verify((Class) null).annotation()   => FAIL
     * Verifier.verify(Override.class).annotation() => PASS
     * Verifier.verify(Verifier.class).annotation() => FAIL
     * </pre>
     *
     * @return A reference to this {@link ClassVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public ClassVerifier annotation() throws VerifierException {
        final Class<?> value = verification().getValue();
        final boolean result = value != null && value.isAnnotation();

        verification().check(result, "be an annotation");

        return this;
    }

    /**
     * <p>
     * Verifies that the value is an anonymous type.
     * </p>
     * <pre>
     * Verifier.verify((Class) null).anonymous()                    => FAIL
     * Verifier.verify(Object.class).anonymous()                    => FAIL
     * Verifier.verify(new Serializable(){}.getClass()).anonymous() => PASS
     * </pre>
     *
     * @return A reference to this {@link ClassVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public ClassVerifier anonymous() throws VerifierException {
        final Class<?> value = verification().getValue();
        final boolean result = value != null && value.isAnonymousClass();

        verification().check(result, "be anonymous");

        return this;
    }

    /**
     * <p>
     * Verifies that the value is an array.
     * </p>
     * <pre>
     * Verifier.verify((Class) null).array()   => FAIL
     * Verifier.verify(Object.class).array()   => FAIL
     * Verifier.verify(Object[].class).array() => PASS
     * </pre>
     *
     * @return A reference to this {@link ClassVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public ClassVerifier array() throws VerifierException {
        final Class<?> value = verification().getValue();
        final boolean result = value != null && value.isArray();

        verification().check(result, "be an array");

        return this;
    }

    /**
     * <p>
     * Verifies that the value is either the same as, or is a superclass or superinterface of, the class or interface
     * represented by the {@code type} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify(*).assignableFrom(null)                      => FAIL
     * Verifier.verify((Class) null).assignableFrom(*)              => FAIL
     * Verifier.verify(Object.class).assignableFrom(Verifier.class) => PASS
     * Verifier.verify(Verifier.class).assignableFrom(Object.class) => FAIL
     * </pre>
     *
     * @param type
     *         the {@code Class} to be checked as assignable from the value (may be {@literal null})
     * @return A reference to this {@link ClassVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public ClassVerifier assignableFrom(final Class<?> type) throws VerifierException {
        final Class<?> value = verification().getValue();
        final boolean result = value != null && type != null && value.isAssignableFrom(type);

        verification().check(result, "be assignable from '%s'", type);

        return this;
    }

    /**
     * <p>
     * Verifies that the value is an enum.
     * </p>
     * <pre>
     * Verifier.verify((Class) null).enumeration()    => FAIL
     * Verifier.verify(Object.class).enumeration()    => FAIL
     * Verifier.verify(DayOfWeek.class).enumeration() => PASS
     * </pre>
     *
     * @return A reference to this {@link ClassVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public ClassVerifier enumeration() throws VerifierException {
        final Class<?> value = verification().getValue();
        final boolean result = value != null && value.isEnum();

        verification().check(result, "be an enum");

        return this;
    }

    /**
     * <p>
     * Verifies that the value is an interface.
     * </p>
     * <pre>
     * Verifier.verify((Class) null).interfacing()       => FAIL
     * Verifier.verify(Object.class).interfacing()       => FAIL
     * Verifier.verify(Serializable.class).interfacing() => PASS
     * </pre>
     *
     * @return A reference to this {@link ClassVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public ClassVerifier interfacing() throws VerifierException {
        final Class<?> value = verification().getValue();
        final boolean result = value != null && value.isInterface();

        verification().check(result, "be an interface");

        return this;
    }

    /**
     * <p>
     * Verifies that the value is nested.
     * </p>
     * <pre>
     * Verifier.verify((Class) null).nested()           => FAIL
     * Verifier.verify(Calendar.class).nested()         => FAIL
     * Verifier.verify(Calendar.Builder.class).nested() => PASS
     * </pre>
     *
     * @return A reference to this {@link ClassVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public ClassVerifier nested() throws VerifierException {
        final Class<?> value = verification().getValue();
        final boolean result = value != null && value.getEnclosingClass() != null;

        verification().check(result, "be nested");

        return this;
    }

    /**
     * <p>
     * Verifies that the value is a primitive.
     * </p>
     * <pre>
     * Verifier.verify((Class) null).primitive()  => FAIL
     * Verifier.verify(Object.class).primitive()  => FAIL
     * Verifier.verify(Boolean.class).primitive() => FAIL
     * Verifier.verify(Boolean.TYPE).primitive()  => PASS
     * </pre>
     *
     * @return A reference to this {@link ClassVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public ClassVerifier primitive() throws VerifierException {
        final Class<?> value = verification().getValue();
        final boolean result = value != null && value.isPrimitive();

        verification().check(result, "be a primitive");

        return this;
    }

    /**
     * <p>
     * Verifies that the value is a primitive or primitive wrapper.
     * </p>
     * <pre>
     * Verifier.verify((Class) null).primitiveOrWrapper()  => FAIL
     * Verifier.verify(Object.class).primitiveOrWrapper()  => FAIL
     * Verifier.verify(Boolean.class).primitiveOrWrapper() => PASS
     * Verifier.verify(Boolean.TYPE).primitiveOrWrapper()  => PASS
     * </pre>
     *
     * @return A reference to this {@link ClassVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public ClassVerifier primitiveOrWrapper() throws VerifierException {
        final Class<?> value = verification().getValue();
        final boolean result = value != null && (value.isPrimitive() || PRIMITIVE_WRAPPERS.contains(value));

        verification().check(result, "be a primitive or primitive wrapper");

        return this;
    }

    /**
     * <p>
     * Verifies that the value is a primitive wrapper.
     * </p>
     * <pre>
     * Verifier.verify((Class) null).primitiveWrapper()  => FAIL
     * Verifier.verify(Object.class).primitiveWrapper()  => FAIL
     * Verifier.verify(Boolean.class).primitiveWrapper() => PASS
     * Verifier.verify(Boolean.TYPE).primitiveWrapper()  => FAIL
     * </pre>
     *
     * @return A reference to this {@link ClassVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public ClassVerifier primitiveWrapper() throws VerifierException {
        final Class<?> value = verification().getValue();
        final boolean result = PRIMITIVE_WRAPPERS.contains(value);

        verification().check(result, "be a primitive wrapper");

        return this;
    }
}
