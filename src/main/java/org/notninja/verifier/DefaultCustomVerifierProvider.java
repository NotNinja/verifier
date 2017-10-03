/*
 * Copyright (C) 2017 Alasdair Mercer, !ninja
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
package org.notninja.verifier;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import org.notninja.verifier.verification.Verification;

/**
 * <p>
 * The default implementation of {@link CustomVerifierProvider} which uses reflection to instantiate the {@link
 * CustomVerifier} class while requiring that it has a constructor with a single {@link Verification} argument which can
 * be used to pass the current {@link Verification} into the instance upon instantiation.
 * </p>
 *
 * @author Alasdair Mercer
 * @since 0.2.0
 */
public final class DefaultCustomVerifierProvider implements CustomVerifierProvider {

    private static final Map<Class<?>, Constructor<?>> constructors = new HashMap<>();

    private static <T> Constructor<T> getConstructor(final Class<T> cls) throws ReflectiveOperationException {
        synchronized (constructors) {
            @SuppressWarnings("unchecked")
            Constructor<T> constructor = (Constructor<T>) constructors.get(cls);

            if (constructor == null) {
                constructor = cls.getConstructor(Verification.class);

                constructors.put(cls, constructor);
            }

            return constructor;
        }
    }

    @Override
    public <T, V extends CustomVerifier<T, V>> V getCustomVerifier(final Class<V> cls, final Verification<T> verification) {
        Verifier.verify(cls, "cls")
            .not().nulled();

        try {
            return getConstructor(cls).newInstance(verification);
        } catch (ReflectiveOperationException e) {
            throw new VerifierException("cls could not be instantiated", e);
        }
    }

    @Override
    public int getWeight() {
        return DEFAULT_IMPLEMENTATION_WEIGHT;
    }
}
