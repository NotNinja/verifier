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
import io.skelp.verifier.verification.Verification;

/**
 * TODO: Document
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
     * TODO: Document
     *
     * @param verification
     */
    public ClassVerifier(final Verification<Class> verification) {
        super(verification);
    }

    /**
     * TODO: Document
     *
     * @param type
     * @return
     * @throws VerifierException
     */
    public ClassVerifier annotated(final Class<? extends Annotation> type) throws VerifierException {
        final Class<?> value = getVerification().getValue();
        final boolean result = value != null && type != null && value.isAnnotationPresent(type);

        getVerification().check(result, "be annotated with '%s'", type);

        return this;
    }

    /**
     * TODO: Document
     *
     * @return
     * @throws VerifierException
     */
    public ClassVerifier annotation() throws VerifierException {
        final Class<?> value = getVerification().getValue();
        final boolean result = value != null && value.isAnnotation();

        getVerification().check(result, "be an annotation");

        return this;
    }

    /**
     * TODO: Document
     *
     * @return
     * @throws VerifierException
     */
    public ClassVerifier anonymous() throws VerifierException {
        final Class<?> value = getVerification().getValue();
        final boolean result = value != null && value.isAnonymousClass();

        getVerification().check(result, "be anonymous");

        return this;
    }

    /**
     * TODO: Document
     *
     * @return
     * @throws VerifierException
     */
    public ClassVerifier array() throws VerifierException {
        final Class<?> value = getVerification().getValue();
        final boolean result = value != null && value.isArray();

        getVerification().check(result, "be an array");

        return this;
    }

    /**
     * TODO: Document
     *
     * @param type
     * @return
     * @throws VerifierException
     */
    public ClassVerifier assignableFrom(final Class<?> type) throws VerifierException {
        final Class<?> value = getVerification().getValue();
        final boolean result = value != null && type != null && value.isAssignableFrom(type);

        getVerification().check(result, "be assignable from '%s'", type);

        return this;
    }

    /**
     * TODO: Document
     *
     * @return
     * @throws VerifierException
     */
    public ClassVerifier enumeration() throws VerifierException {
        final Class<?> value = getVerification().getValue();
        final boolean result = value != null && value.isEnum();

        getVerification().check(result, "be an enum");

        return this;
    }

    /**
     * TODO: Document
     *
     * @return
     * @throws VerifierException
     */
    public ClassVerifier interfacing() throws VerifierException {
        final Class<?> value = getVerification().getValue();
        final boolean result = value != null && value.isInterface();

        getVerification().check(result, "be an interface");

        return this;
    }

    /**
     * TODO: Document
     *
     * @return
     * @throws VerifierException
     */
    public ClassVerifier nested() throws VerifierException {
        final Class<?> value = getVerification().getValue();
        final boolean result = value != null && value.getEnclosingClass() != null;

        getVerification().check(result, "be nested");

        return this;
    }

    /**
     * TODO: Document
     *
     * @return
     * @throws VerifierException
     */
    public ClassVerifier primitive() throws VerifierException {
        final Class<?> value = getVerification().getValue();
        final boolean result = value != null && value.isPrimitive();

        getVerification().check(result, "be a primitive");

        return this;
    }

    /**
     * TODO: Document
     *
     * @return
     * @throws VerifierException
     */
    public ClassVerifier primitiveOrWrapper() throws VerifierException {
        final Class<?> value = getVerification().getValue();
        final boolean result = value != null && (value.isPrimitive() || PRIMITIVE_WRAPPERS.contains(value));

        getVerification().check(result, "be a primitive or primitive wrapper");

        return this;
    }

    /**
     * TODO: Document
     *
     * @return
     * @throws VerifierException
     */
    public ClassVerifier primitiveWrapper() throws VerifierException {
        final Class<?> value = getVerification().getValue();
        final boolean result = PRIMITIVE_WRAPPERS.contains(value);

        getVerification().check(result, "be a primitive wrapper");

        return this;
    }
}
