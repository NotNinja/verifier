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
package io.skelp.verifier.factory;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import io.skelp.verifier.CustomVerifier;
import io.skelp.verifier.verification.Verification;

/**
 * The default implementation of {@link CustomVerifierFactory} which uses reflection to instantiate the {@link
 * CustomVerifier} class while requiring that it has a constructor with a single {@link Verification} argument which can
 * be used to pass the {@link Verification} into the instance at instantiation.
 *
 * @author Alasdair Mercer
 */
public final class DefaultCustomVerifierFactory implements CustomVerifierFactory {

    private static final Map<Class<?>, Constructor<?>> CONSTRUCTOR_CACHE = new HashMap<>();

    private static <T> Constructor<T> getConstructor(final Class<T> cls) throws ReflectiveOperationException {
        @SuppressWarnings("unchecked")
        Constructor<T> constructor = (Constructor<T>) CONSTRUCTOR_CACHE.get(cls);

        if (constructor == null) {
            constructor = cls.getConstructor(Verification.class);

            CONSTRUCTOR_CACHE.put(cls, constructor);
        }

        return constructor;
    }

    @Override
    public <T, V extends CustomVerifier<T, V>> V create(final Class<V> cls, final Verification<T> verification) throws VerifierFactoryException {
        if (cls == null) {
            throw new VerifierFactoryException("cls must not be null");
        }

        try {
            return getConstructor(cls).newInstance(verification);
        } catch (ReflectiveOperationException e) {
            throw new VerifierFactoryException("cls could not be instantiated", e);
        }
    }
}
