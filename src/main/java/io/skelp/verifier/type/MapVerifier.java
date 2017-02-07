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
import io.skelp.verifier.message.MessageKey;
import io.skelp.verifier.type.base.BaseCollectionVerifier;
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
     * Verifier.verify((Map) null).containAllKeys(*)                                                       =&gt; FAIL
     * Verifier.verify(map(new Object[0][])).containAllKeys(*)                                             =&gt; FAIL
     * Verifier.verify(map(new Object[][]{*})).containAllKeys((Object[]) null)                             =&gt; PASS
     * Verifier.verify(map(new Object[][]{{"a", 123}, {"b", 456}, {"c", 789}})).containAllKeys("d", "e")   =&gt; FAIL
     * Verifier.verify(map(new Object[][]{{"a", 123}, {"b", 456}, {"c", 789}})).containAllKeys("a", "d")   =&gt; FAIL
     * Verifier.verify(map(new Object[][]{{"a", 123}, {"b", 456}, {"c", 789}})).containAllKeys("c", "b")   =&gt; PASS
     * Verifier.verify(map(new Object[][]{{"a", 123}, {"b", 456}, {"c", 789}})).containAllKeys("c", null)  =&gt; FAIL
     * Verifier.verify(map(new Object[][]{{"a", 123}, {"b", 456}, {null, 789}})).containAllKeys("a", null) =&gt; PASS
     * </pre>
     *
     * @param keys
     *         the keys to check for within the value (may be {@literal null})
     * @return A reference to this {@link MapVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public MapVerifier<K, V> containAllKeys(final K... keys) {
        final Map<K, V> value = verification().getValue();
        final boolean result = value != null && matchAll(keys, value::containsKey);

        verification().check(result, MessageKeys.CONTAIN_ALL_KEYS, (Object) keys);

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
     * Verifier.verify((Map) null).containAnyKey(*)                                                       =&gt; FAIL
     * Verifier.verify(map(new Object[0][])).containAnyKey(*)                                             =&gt; FAIL
     * Verifier.verify(map(new Object[][]{*})).containAnyKey((Object[]) null)                             =&gt; FAIL
     * Verifier.verify(map(new Object[][]{{"a", 123}, {"b", 456}, {"c", 789}})).containAnyKey("d", "e")   =&gt; FAIL
     * Verifier.verify(map(new Object[][]{{"a", 123}, {"b", 456}, {"c", 789}})).containAnyKey("a", "d")   =&gt; PASS
     * Verifier.verify(map(new Object[][]{{"a", 123}, {"b", 456}, {"c", 789}})).containAnyKey("c", "b")   =&gt; PASS
     * Verifier.verify(map(new Object[][]{{"a", 123}, {"b", 456}, {"c", 789}})).containAnyKey("c", null)  =&gt; PASS
     * Verifier.verify(map(new Object[][]{{"a", 123}, {"b", 456}, {null, 789}})).containAnyKey("d", null) =&gt; PASS
     * </pre>
     *
     * @param keys
     *         the keys to check for within the value (may be {@literal null})
     * @return A reference to this {@link MapVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public MapVerifier<K, V> containAnyKey(final K... keys) {
        final Map<K, V> value = verification().getValue();
        final boolean result = value != null && matchAny(keys, value::containsKey);

        verification().check(result, MessageKeys.CONTAIN_ANY_KEY, (Object) keys);

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
     * Verifier.verify((Map) null).containKey(*)                                                  =&gt; FAIL
     * Verifier.verify(map(new Object[0][])).containKey(*)                                        =&gt; FAIL
     * Verifier.verify(map(new Object[][]{{"a", 123}, {"b", 456}, {"c", 789}})).containKey("c")   =&gt; PASS
     * Verifier.verify(map(new Object[][]{{"a", 123}, {"b", 456}, {"c", 789}})).containKey("d")   =&gt; FAIL
     * Verifier.verify(map(new Object[][]{{"a", 123}, {"b", 456}, {"c", 789}})).containKey(null)  =&gt; FAIL
     * Verifier.verify(map(new Object[][]{{"a", 123}, {"b", 456}, {null, 789}})).containKey(null) =&gt; PASS
     * </pre>
     *
     * @param key
     *         the key to check for within the value (may be {@literal null})
     * @return A reference to this {@link MapVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    public MapVerifier<K, V> containKey(final K key) {
        final Map<K, V> value = verification().getValue();
        final boolean result = value != null && value.containsKey(key);

        verification().check(result, MessageKeys.CONTAIN_KEY, key);

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

    /**
     * <p>
     * The {@link MessageKey MessageKeys} that are used by {@link MapVerifier}.
     * </p>
     *
     * @since 0.2.0
     */
    enum MessageKeys implements MessageKey {

        CONTAIN_ALL_KEYS("io.skelp.verifier.type.MapVerifier.containAllKeys"),
        CONTAIN_ANY_KEY("io.skelp.verifier.type.MapVerifier.containAnyKey"),
        CONTAIN_KEY("io.skelp.verifier.type.MapVerifier.containKey");

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
