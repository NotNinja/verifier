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
 * <p>
 * An implementation of {@link BaseCollectionVerifier} which can be used to verify a {@code Map} value and its own keys
 * and values.
 * </p>
 *
 * @param <K>
 *         the type of the keys contained within the value being verified
 * @param <V>
 *         the type of the values contained within the value being verified
 * @author Alasdair Mercer
 */
public final class MapVerifier<K, V> extends BaseCollectionVerifier<V, Map<K, V>, MapVerifier<K, V>> {

    /**
     * <p>
     * Creates an instance of {@link MapVerifier} based on the {@code verification} provided.
     * </p>
     *
     * @param verification
     *         the {@link Verification} to be used
     */
    public MapVerifier(final Verification<Map<K, V>> verification) {
        super(verification);
    }

    /**
     * <p>
     * Verifies that the value contains <b>all</b> of the {@code keys} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify((Map) null).containAllKeys(*)                                                       => FAIL
     * Verifier.verify(map(new Object[0][])).containAllKeys(*)                                             => FAIL
     * Verifier.verify(map(new Object[][]{*})).containAllKeys((Object[]) null)                             => PASS
     * Verifier.verify(map(new Object[][]{{"a", 123}, {"b", 456}, {"c", 789}})).containAllKeys("d", "e")   => FAIL
     * Verifier.verify(map(new Object[][]{{"a", 123}, {"b", 456}, {"c", 789}})).containAllKeys("a", "d")   => FAIL
     * Verifier.verify(map(new Object[][]{{"a", 123}, {"b", 456}, {"c", 789}})).containAllKeys("c", "b")   => PASS
     * Verifier.verify(map(new Object[][]{{"a", 123}, {"b", 456}, {"c", 789}})).containAllKeys("c", null)  => FAIL
     * Verifier.verify(map(new Object[][]{{"a", 123}, {"b", 456}, {null, 789}})).containAllKeys("a", null) => PASS
     * </pre>
     *
     * @param keys
     *         the keys to check for within the value (may be {@literal null})
     * @return A reference to this {@link MapVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
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
     * <p>
     * Verifies that the value contains <b>any</b> of the {@code keys} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify((Map) null).containAnyKey(*)                                                       => FAIL
     * Verifier.verify(map(new Object[0][])).containAnyKey(*)                                             => FAIL
     * Verifier.verify(map(new Object[][]{*})).containAnyKey((Object[]) null)                             => FAIL
     * Verifier.verify(map(new Object[][]{{"a", 123}, {"b", 456}, {"c", 789}})).containAnyKey("d", "e")   => FAIL
     * Verifier.verify(map(new Object[][]{{"a", 123}, {"b", 456}, {"c", 789}})).containAnyKey("a", "d")   => PASS
     * Verifier.verify(map(new Object[][]{{"a", 123}, {"b", 456}, {"c", 789}})).containAnyKey("c", "b")   => PASS
     * Verifier.verify(map(new Object[][]{{"a", 123}, {"b", 456}, {"c", 789}})).containAnyKey("c", null)  => PASS
     * Verifier.verify(map(new Object[][]{{"a", 123}, {"b", 456}, {null, 789}})).containAnyKey("d", null) => PASS
     * </pre>
     *
     * @param keys
     *         the keys to check for within the value (may be {@literal null})
     * @return A reference to this {@link MapVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
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
     * <p>
     * Verifies that the value contains the {@code key} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify((Map) null).containKey(*)                                                  => FAIL
     * Verifier.verify(map(new Object[0][])).containKey(*)                                        => FAIL
     * Verifier.verify(map(new Object[][]{{"a", 123}, {"b", 456}, {"c", 789}})).containKey("c")   => PASS
     * Verifier.verify(map(new Object[][]{{"a", 123}, {"b", 456}, {"c", 789}})).containKey("d")   => FAIL
     * Verifier.verify(map(new Object[][]{{"a", 123}, {"b", 456}, {"c", 789}})).containKey(null)  => FAIL
     * Verifier.verify(map(new Object[][]{{"a", 123}, {"b", 456}, {null, 789}})).containKey(null) => PASS
     * </pre>
     *
     * @param key
     *         the key to check for within the value (may be {@literal null})
     * @return A reference to this {@link MapVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
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
}
