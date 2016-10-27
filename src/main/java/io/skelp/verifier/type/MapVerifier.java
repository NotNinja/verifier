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

import java.util.Collection;
import java.util.Map;

import io.skelp.verifier.VerifierException;
import io.skelp.verifier.type.base.BaseCollectionVerifier;
import io.skelp.verifier.util.Function;
import io.skelp.verifier.verification.Verification;

/**
 * TODO: Document
 *
 * @param <K>
 * @param <V>
 * @author Alasdair Mercer
 */
public final class MapVerifier<K, V> extends BaseCollectionVerifier<V, Map<K, V>, MapVerifier<K, V>> {

    /**
     * TODO: Document
     *
     * @param verification
     */
    public MapVerifier(final Verification<Map<K, V>> verification) {
        super(verification);
    }

    /**
     * TODO: Document
     *
     * @param keys
     * @return
     * @throws VerifierException
     */
    public MapVerifier<K, V> containAllKeys(final K... keys) throws VerifierException {
        final Map<K, V> value = verification().getValue();
        final boolean result = value != null && matchAll(keys, new Function<Boolean, K>() {
            @Override
            public Boolean apply(final K input) {
                return value.containsKey(input);
            }
        });

        verification().check(result, "contain all keys %s", verification().getMessageFormatter().formatArray(keys));

        return this;
    }

    /**
     * TODO: Document
     *
     * @param keys
     * @return
     * @throws VerifierException
     */
    public MapVerifier<K, V> containAnyKey(final K... keys) throws VerifierException {
        final Map<K, V> value = verification().getValue();
        final boolean result = value != null && matchAny(keys, new Function<Boolean, K>() {
            @Override
            public Boolean apply(final K input) {
                return value.containsKey(input);
            }
        });

        verification().check(result, "contain any key %s", verification().getMessageFormatter().formatArray(keys));

        return this;
    }

    /**
     * TODO: Document
     *
     * @param key
     * @return
     * @throws VerifierException
     */
    public MapVerifier<K, V> containKey(final K key) throws VerifierException {
        final Map<K, V> value = verification().getValue();
        final boolean result = value != null && value.containsKey(key);

        verification().check(result, "contain key '%s'", key);

        return this;
    }

    @Override
    protected Collection<V> getCollection(final Map<K, V> value) {
        return value != null ? value.values() : null;
    }

    @Override
    protected int getSize(final Map<K, V> value) {
        return value.size();
    }

    @Override
    protected boolean isEmpty(final Map<K, V> value) {
        return value.isEmpty();
    }
}
