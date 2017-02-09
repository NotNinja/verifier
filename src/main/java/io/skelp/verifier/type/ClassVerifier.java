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
import io.skelp.verifier.message.MessageKey;
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
     * Verifier.verify((Class) null).annotated()               =&gt; FAIL
     * Verifier.verify(MyAnnotatedObject.class).annotated()    =&gt; PASS
     * Verifier.verify(MyNonAnnotatedObject.class).annotated() =&gt; FAIL
     * </pre>
     *
     * @return A reference to this {@link ClassVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public ClassVerifier annotated() {
        final Class<?> value = verification().getValue();
        final boolean result = value != null && value.getAnnotations().length > 0;

        verification().report(result, MessageKeys.ANNOTATED);

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
     * Verifier.verify(*).annotatedWith(null)                          =&gt; FAIL
     * Verifier.verify((Class) null).annotatedWith(*)                  =&gt; FAIL
     * Verifier.verify(Object.class).annotatedWith(*)                  =&gt; FAIL
     * Verifier.verify(Override.class).annotatedWith(Documented.class) =&gt; FAIL
     * Verifier.verify(Override.class).annotatedWith(Retention.class)  =&gt; PASS
     * </pre>
     *
     * @param type
     *         the annotation to be checked for on the value (may be {@literal null})
     * @return A reference to this {@link ClassVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public ClassVerifier annotatedWith(final Class<? extends Annotation> type) {
        final Class<?> value = verification().getValue();
        final boolean result = value != null && type != null && value.isAnnotationPresent(type);

        verification().report(result, MessageKeys.ANNOTATED_WITH, type);

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
     * Verifier.verify(*).annotatedWithAll()                                               =&gt; PASS
     * Verifier.verify(*).annotatedWithAll((Class[]) null)                                 =&gt; FAIL
     * Verifier.verify(*).annotatedWithAll(*, null)                                        =&gt; FAIL
     * Verifier.verify((Class) null).annotatedWithAll(*)                                   =&gt; FAIL
     * Verifier.verify(Object.class).annotatedWithAll(*)                                   =&gt; FAIL
     * Verifier.verify(Override.class).annotatedWithAll(Retention.class, Documented.class) =&gt; FAIL
     * Verifier.verify(Override.class).annotatedWithAll(Retention.class, Target.class)     =&gt; PASS
     * </pre>
     *
     * @param types
     *         the annotations to be checked for on the value (may be {@literal null})
     * @return A reference to this {@link ClassVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public ClassVerifier annotatedWithAll(final Class<? extends Annotation>... types) {
        final Class<?> value = verification().getValue();
        final boolean result = value != null && matchAll(types, input -> input != null && value.isAnnotationPresent(input));

        verification().report(result, MessageKeys.ANNOTATED_WITH_ALL, (Object) types);

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
     * Verifier.verify(*).annotatedWithAny()                                               =&gt; FAIL
     * Verifier.verify(*).annotatedWithAny((Class[]) null)                                 =&gt; FAIL
     * Verifier.verify((Class) null).annotatedWithAny(*)                                   =&gt; FAIL
     * Verifier.verify(Object.class).annotatedWithAny(*)                                   =&gt; FAIL
     * Verifier.verify(Override.class).annotatedWithAny(Retention.class, Documented.class) =&gt; PASS
     * Verifier.verify(Override.class).annotatedWithAny(Retention.class, Target.class)     =&gt; PASS
     * </pre>
     *
     * @param types
     *         the annotations to be checked for on the value (may be {@literal null})
     * @return A reference to this {@link ClassVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public ClassVerifier annotatedWithAny(final Class<? extends Annotation>... types) {
        final Class<?> value = verification().getValue();
        final boolean result = value != null && matchAny(types, input -> input != null && value.isAnnotationPresent(input));

        verification().report(result, MessageKeys.ANNOTATED_WITH_ANY, (Object) types);

        return this;
    }

    /**
     * <p>
     * Verifies that the value is an annotation.
     * </p>
     * <pre>
     * Verifier.verify((Class) null).annotation()   =&gt; FAIL
     * Verifier.verify(Override.class).annotation() =&gt; PASS
     * Verifier.verify(Verifier.class).annotation() =&gt; FAIL
     * </pre>
     *
     * @return A reference to this {@link ClassVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public ClassVerifier annotation() {
        final Class<?> value = verification().getValue();
        final boolean result = value != null && value.isAnnotation();

        verification().report(result, MessageKeys.ANNOTATION);

        return this;
    }

    /**
     * <p>
     * Verifies that the value is an anonymous type.
     * </p>
     * <pre>
     * Verifier.verify((Class) null).anonymous()                    =&gt; FAIL
     * Verifier.verify(Object.class).anonymous()                    =&gt; FAIL
     * Verifier.verify(new Serializable(){}.getClass()).anonymous() =&gt; PASS
     * </pre>
     *
     * @return A reference to this {@link ClassVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public ClassVerifier anonymous() {
        final Class<?> value = verification().getValue();
        final boolean result = value != null && value.isAnonymousClass();

        verification().report(result, MessageKeys.ANONYMOUS);

        return this;
    }

    /**
     * <p>
     * Verifies that the value is an array.
     * </p>
     * <pre>
     * Verifier.verify((Class) null).array()   =&gt; FAIL
     * Verifier.verify(Object.class).array()   =&gt; FAIL
     * Verifier.verify(Object[].class).array() =&gt; PASS
     * </pre>
     *
     * @return A reference to this {@link ClassVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public ClassVerifier array() {
        final Class<?> value = verification().getValue();
        final boolean result = value != null && value.isArray();

        verification().report(result, MessageKeys.ARRAY);

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
     * Verifier.verify(*).assignableFrom(null)                      =&gt; FAIL
     * Verifier.verify((Class) null).assignableFrom(*)              =&gt; FAIL
     * Verifier.verify(Object.class).assignableFrom(Verifier.class) =&gt; PASS
     * Verifier.verify(Verifier.class).assignableFrom(Object.class) =&gt; FAIL
     * </pre>
     *
     * @param type
     *         the {@code Class} to be checked as assignable from the value (may be {@literal null})
     * @return A reference to this {@link ClassVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public ClassVerifier assignableFrom(final Class<?> type) {
        final Class<?> value = verification().getValue();
        final boolean result = value != null && type != null && value.isAssignableFrom(type);

        verification().report(result, MessageKeys.ASSIGNABLE_FROM, type);

        return this;
    }

    /**
     * <p>
     * Verifies that the value is an enum.
     * </p>
     * <pre>
     * Verifier.verify((Class) null).enumeration()    =&gt; FAIL
     * Verifier.verify(Object.class).enumeration()    =&gt; FAIL
     * Verifier.verify(DayOfWeek.class).enumeration() =&gt; PASS
     * </pre>
     *
     * @return A reference to this {@link ClassVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public ClassVerifier enumeration() {
        final Class<?> value = verification().getValue();
        final boolean result = value != null && value.isEnum();

        verification().report(result, MessageKeys.ENUMERATION);

        return this;
    }

    /**
     * <p>
     * Verifies that the value is an interface.
     * </p>
     * <pre>
     * Verifier.verify((Class) null).interfacing()       =&gt; FAIL
     * Verifier.verify(Object.class).interfacing()       =&gt; FAIL
     * Verifier.verify(Serializable.class).interfacing() =&gt; PASS
     * </pre>
     *
     * @return A reference to this {@link ClassVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public ClassVerifier interfacing() {
        final Class<?> value = verification().getValue();
        final boolean result = value != null && value.isInterface();

        verification().report(result, MessageKeys.INTERFACING);

        return this;
    }

    /**
     * <p>
     * Verifies that the value is nested.
     * </p>
     * <pre>
     * Verifier.verify((Class) null).nested()           =&gt; FAIL
     * Verifier.verify(Calendar.class).nested()         =&gt; FAIL
     * Verifier.verify(Calendar.Builder.class).nested() =&gt; PASS
     * </pre>
     *
     * @return A reference to this {@link ClassVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public ClassVerifier nested() {
        final Class<?> value = verification().getValue();
        final boolean result = value != null && value.getEnclosingClass() != null;

        verification().report(result, MessageKeys.NESTED);

        return this;
    }

    /**
     * <p>
     * Verifies that the value is a primitive.
     * </p>
     * <pre>
     * Verifier.verify((Class) null).primitive()  =&gt; FAIL
     * Verifier.verify(Object.class).primitive()  =&gt; FAIL
     * Verifier.verify(Boolean.class).primitive() =&gt; FAIL
     * Verifier.verify(Boolean.TYPE).primitive()  =&gt; PASS
     * </pre>
     *
     * @return A reference to this {@link ClassVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public ClassVerifier primitive() {
        final Class<?> value = verification().getValue();
        final boolean result = value != null && value.isPrimitive();

        verification().report(result, MessageKeys.PRIMITIVE);

        return this;
    }

    /**
     * <p>
     * Verifies that the value is a primitive or primitive wrapper.
     * </p>
     * <pre>
     * Verifier.verify((Class) null).primitiveOrWrapper()  =&gt; FAIL
     * Verifier.verify(Object.class).primitiveOrWrapper()  =&gt; FAIL
     * Verifier.verify(Boolean.class).primitiveOrWrapper() =&gt; PASS
     * Verifier.verify(Boolean.TYPE).primitiveOrWrapper()  =&gt; PASS
     * </pre>
     *
     * @return A reference to this {@link ClassVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public ClassVerifier primitiveOrWrapper() {
        final Class<?> value = verification().getValue();
        final boolean result = value != null && (value.isPrimitive() || PRIMITIVE_WRAPPERS.contains(value));

        verification().report(result, MessageKeys.PRIMITIVE_OR_WRAPPER);

        return this;
    }

    /**
     * <p>
     * Verifies that the value is a primitive wrapper.
     * </p>
     * <pre>
     * Verifier.verify((Class) null).primitiveWrapper()  =&gt; FAIL
     * Verifier.verify(Object.class).primitiveWrapper()  =&gt; FAIL
     * Verifier.verify(Boolean.class).primitiveWrapper() =&gt; PASS
     * Verifier.verify(Boolean.TYPE).primitiveWrapper()  =&gt; FAIL
     * </pre>
     *
     * @return A reference to this {@link ClassVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public ClassVerifier primitiveWrapper() {
        final Class<?> value = verification().getValue();
        final boolean result = PRIMITIVE_WRAPPERS.contains(value);

        verification().report(result, MessageKeys.PRIMITIVE_WRAPPER);

        return this;
    }

    /**
     * <p>
     * The {@link MessageKey MessageKeys} that are used by {@link ClassVerifier}.
     * </p>
     *
     * @since 0.2.0
     */
    enum MessageKeys implements MessageKey {

        ANNOTATED("io.skelp.verifier.type.ClassVerifier.annotated"),
        ANNOTATED_WITH("io.skelp.verifier.type.ClassVerifier.annotatedWith"),
        ANNOTATED_WITH_ALL("io.skelp.verifier.type.ClassVerifier.annotatedWithAll"),
        ANNOTATED_WITH_ANY("io.skelp.verifier.type.ClassVerifier.annotatedWithAny"),
        ANNOTATION("io.skelp.verifier.type.ClassVerifier.annotation"),
        ANONYMOUS("io.skelp.verifier.type.ClassVerifier.anonymous"),
        ARRAY("io.skelp.verifier.type.ClassVerifier.array"),
        ASSIGNABLE_FROM("io.skelp.verifier.type.ClassVerifier.assignableFrom"),
        ENUMERATION("io.skelp.verifier.type.ClassVerifier.enumeration"),
        INTERFACING("io.skelp.verifier.type.ClassVerifier.interfacing"),
        NESTED("io.skelp.verifier.type.ClassVerifier.nested"),
        PRIMITIVE("io.skelp.verifier.type.ClassVerifier.primitive"),
        PRIMITIVE_OR_WRAPPER("io.skelp.verifier.type.ClassVerifier.primitiveOrWrapper"),
        PRIMITIVE_WRAPPER("io.skelp.verifier.type.ClassVerifier.primitiveWrapper");

        private final String code;

        MessageKeys(final String code) {
            this.code = code;
        }

        @Override
        public String code() {
            return code;
        }
    }
}
