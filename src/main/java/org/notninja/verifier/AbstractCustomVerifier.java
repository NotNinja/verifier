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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

import org.notninja.verifier.message.MessageKey;
import org.notninja.verifier.service.Services;
import org.notninja.verifier.type.ArrayVerifier;
import org.notninja.verifier.type.BigDecimalVerifier;
import org.notninja.verifier.type.BigIntegerVerifier;
import org.notninja.verifier.type.BooleanVerifier;
import org.notninja.verifier.type.ByteVerifier;
import org.notninja.verifier.type.CalendarVerifier;
import org.notninja.verifier.type.CharacterVerifier;
import org.notninja.verifier.type.ClassVerifier;
import org.notninja.verifier.type.CollectionVerifier;
import org.notninja.verifier.type.ComparableVerifier;
import org.notninja.verifier.type.DateVerifier;
import org.notninja.verifier.type.DoubleVerifier;
import org.notninja.verifier.type.FloatVerifier;
import org.notninja.verifier.type.IntegerVerifier;
import org.notninja.verifier.type.LocaleVerifier;
import org.notninja.verifier.type.LongVerifier;
import org.notninja.verifier.type.MapVerifier;
import org.notninja.verifier.type.ObjectVerifier;
import org.notninja.verifier.type.ShortVerifier;
import org.notninja.verifier.type.StringVerifier;
import org.notninja.verifier.type.ThrowableVerifier;
import org.notninja.verifier.verification.Verification;

/**
 * <p>
 * An abstract implementation of {@link CustomVerifier} that fulfils the contract while also providing useful helper
 * methods for its children to meet similar verification methods and other abstract implementations.
 * </p>
 *
 * @param <T>
 *         the type of the value being verified
 * @param <V>
 *         the type of the {@link AbstractCustomVerifier} for chaining purposes
 * @author Alasdair Mercer
 */
public abstract class AbstractCustomVerifier<T, V extends AbstractCustomVerifier<T, V>> implements CustomVerifier<T, V> {

    /**
     * <p>
     * Returns whether the specified {@code matcher} matches <b>all</b> of the {@code inputs} provided.
     * </p>
     * <p>
     * This method will return {@literal true} if {@code inputs} is {@literal null} and will return {@literal false} if
     * {@code matcher} returns {@literal false} for any {@code input}.
     * </p>
     *
     * @param inputs
     *         the input values to be matched
     * @param matcher
     *         the {@code Function} to be used to match each input value
     * @param <I>
     *         the type of the input values
     * @return {@literal true} if {@code inputs} is {@literal null} or {@code matcher} returns {@literal true} for all
     * input values; otherwise {@literal false}.
     * @see #matchAll(Collection, Function)
     */
    protected static <I> boolean matchAll(final I[] inputs, final Function<I, Boolean> matcher) {
        return inputs == null || matchAll(Arrays.asList(inputs), matcher);
    }

    /**
     * <p>
     * Returns whether the specified {@code matcher} matches <b>all</b> of the {@code inputs} provided.
     * </p>
     * <p>
     * This method will return {@literal true} if {@code inputs} is {@literal null} and will return {@literal false} if
     * {@code matcher} returns {@literal false} for any {@code input}.
     * </p>
     *
     * @param inputs
     *         the input values to be matched
     * @param matcher
     *         the {@code Function} to be used to match each input value
     * @param <I>
     *         the type of the input values
     * @return {@literal true} if {@code inputs} is {@literal null} or {@code matcher} returns {@literal true} for all
     * input values; otherwise {@literal false}.
     * @see #matchAll(Object[], Function)
     */
    protected static <I> boolean matchAll(final Collection<I> inputs, final Function<I, Boolean> matcher) {
        if (inputs == null) {
            return true;
        }

        for (final I input : inputs) {
            if (!matcher.apply(input)) {
                return false;
            }
        }

        return true;
    }

    /**
     * <p>
     * Returns whether the specified {@code matcher} matches <b>any</b> of the {@code inputs} provided.
     * </p>
     * <p>
     * This method will return {@literal false} if {@code inputs} is {@literal null} and will return {@literal true} if
     * {@code matcher} returns {@literal true} for any {@code input}.
     * </p>
     *
     * @param inputs
     *         the input values to be matched
     * @param matcher
     *         the {@code Function} to be used to match each input value
     * @param <I>
     *         the type of the input values
     * @return {@literal true} if {@code inputs} is not {@literal null} and {@code matcher} returns {@literal true} for
     * any input value; otherwise {@literal false}.
     * @see #matchAny(Collection, Function)
     */
    protected static <I> boolean matchAny(final I[] inputs, final Function<I, Boolean> matcher) {
        return inputs != null && matchAny(Arrays.asList(inputs), matcher);
    }

    /**
     * <p>
     * Returns whether the specified {@code matcher} matches <b>any</b> of the {@code inputs} provided.
     * </p>
     * <p>
     * This method will return {@literal false} if {@code inputs} is {@literal null} and will return {@literal true} if
     * {@code matcher} returns {@literal true} for any {@code input}.
     * </p>
     *
     * @param inputs
     *         the input values to be matched
     * @param matcher
     *         the {@code Function} to be used to match each input value
     * @param <I>
     *         the type of the input values
     * @return {@literal true} if {@code inputs} is not {@literal null} and {@code matcher} returns {@literal true} for
     * any input value; otherwise {@literal false}.
     * @see #matchAny(Object[], Function)
     */
    protected static <I> boolean matchAny(final Collection<I> inputs, final Function<I, Boolean> matcher) {
        if (inputs == null) {
            return false;
        }

        for (final I input : inputs) {
            if (matcher.apply(input)) {
                return true;
            }
        }

        return false;
    }

    private final Verification<T> verification;

    /**
     * <p>
     * Creates an instance of {@link AbstractCustomVerifier} based on the {@code verification} provided.
     * </p>
     *
     * @param verification
     *         the {@link Verification} to be used
     */
    public AbstractCustomVerifier(final Verification<T> verification) {
        this.verification = verification;
    }

    @Override
    public <E> ArrayVerifier<E> and(final E[] value) {
        return and(value, null);
    }

    @Override
    public <E> ArrayVerifier<E> and(final E[] value, final Object name) {
        return new ArrayVerifier<>(verification.copy(value, name));
    }

    @Override
    public BigDecimalVerifier and(final BigDecimal value) {
        return and(value, null);
    }

    @Override
    public BigDecimalVerifier and(final BigDecimal value, final Object name) {
        return new BigDecimalVerifier(verification.copy(value, name));
    }

    @Override
    public BigIntegerVerifier and(final BigInteger value) {
        return and(value, null);
    }

    @Override
    public BigIntegerVerifier and(final BigInteger value, final Object name) {
        return new BigIntegerVerifier(verification.copy(value, name));
    }

    @Override
    public BooleanVerifier and(final Boolean value) {
        return and(value, null);
    }

    @Override
    public BooleanVerifier and(final Boolean value, final Object name) {
        return new BooleanVerifier(verification.copy(value, name));
    }

    @Override
    public ByteVerifier and(final Byte value) {
        return and(value, null);
    }

    @Override
    public ByteVerifier and(final Byte value, final Object name) {
        return new ByteVerifier(verification.copy(value, name));
    }

    @Override
    public CalendarVerifier and(final Calendar value) {
        return and(value, null);
    }

    @Override
    public CalendarVerifier and(final Calendar value, final Object name) {
        return new CalendarVerifier(verification.copy(value, name));
    }

    @Override
    public CharacterVerifier and(final Character value) {
        return and(value, null);
    }

    @Override
    public CharacterVerifier and(final Character value, final Object name) {
        return new CharacterVerifier(verification.copy(value, name));
    }

    @Override
    public ClassVerifier and(final Class value) {
        return and(value, null);
    }

    @Override
    public ClassVerifier and(final Class value, final Object name) {
        return new ClassVerifier(verification.copy(value, name));
    }

    @Override
    public <E> CollectionVerifier<E> and(final Collection<E> value) {
        return and(value, null);
    }

    @Override
    public <E> CollectionVerifier<E> and(final Collection<E> value, final Object name) {
        return new CollectionVerifier<>(verification.copy(value, name));
    }

    @Override
    public DateVerifier and(final Date value) {
        return and(value, null);
    }

    @Override
    public DateVerifier and(final Date value, final Object name) {
        return new DateVerifier(verification.copy(value, name));
    }

    @Override
    public DoubleVerifier and(final Double value) {
        return and(value, null);
    }

    @Override
    public DoubleVerifier and(final Double value, final Object name) {
        return new DoubleVerifier(verification.copy(value, name));
    }

    @Override
    public FloatVerifier and(final Float value) {
        return and(value, null);
    }

    @Override
    public FloatVerifier and(final Float value, final Object name) {
        return new FloatVerifier(verification.copy(value, name));
    }

    @Override
    public IntegerVerifier and(final Integer value) {
        return and(value, null);
    }

    @Override
    public IntegerVerifier and(final Integer value, final Object name) {
        return new IntegerVerifier(verification.copy(value, name));
    }

    @Override
    public LocaleVerifier and(final Locale value) {
        return and(value, null);
    }

    @Override
    public LocaleVerifier and(final Locale value, final Object name) {
        return new LocaleVerifier(verification.copy(value, name));
    }

    @Override
    public LongVerifier and(final Long value) {
        return and(value, null);
    }

    @Override
    public LongVerifier and(final Long value, final Object name) {
        return new LongVerifier(verification.copy(value, name));
    }

    @Override
    public <K, U> MapVerifier<K, U> and(final Map<K, U> value) {
        return and(value, null);
    }

    @Override
    public <K, U> MapVerifier<K, U> and(final Map<K, U> value, final Object name) {
        return new MapVerifier<>(verification.copy(value, name));
    }

    @Override
    public ObjectVerifier and(final Object value) {
        return and(value, null);
    }

    @Override
    public ObjectVerifier and(final Object value, final Object name) {
        return new ObjectVerifier(verification.copy(value, name));
    }

    @Override
    public ShortVerifier and(final Short value) {
        return and(value, null);
    }

    @Override
    public ShortVerifier and(final Short value, final Object name) {
        return new ShortVerifier(verification.copy(value, name));
    }

    @Override
    public StringVerifier and(final String value) {
        return and(value, null);
    }

    @Override
    public StringVerifier and(final String value, final Object name) {
        return new StringVerifier(verification.copy(value, name));
    }

    @Override
    public ThrowableVerifier and(final Throwable value) {
        return and(value, null);
    }

    @Override
    public ThrowableVerifier and(final Throwable value, final Object name) {
        return new ThrowableVerifier(verification.copy(value, name));
    }

    @Override
    public <U, C extends CustomVerifier<U, C>> C and(final U value, final Object name, final Class<C> cls) {
        final Verification<U> copy = verification.copy(value, name);
        return Services.findFirstNonNullForWeightedService(CustomVerifierProvider.class, provider -> provider.getCustomVerifier(cls, copy));
    }

    @Override
    public <C extends Comparable<? super C>> ComparableVerifier<C> andComparable(final C value) {
        return andComparable(value, null);
    }

    @Override
    public <C extends Comparable<? super C>> ComparableVerifier<C> andComparable(final C value, final Object name) {
        return new ComparableVerifier<>(verification.copy(value, name));
    }

    /**
     * <p>
     * Returns a reference to this {@link AbstractCustomVerifier} implementation which can be useful for chaining
     * references while avoiding unchecked casting warnings.
     * </p>
     *
     * @return A reference to this {@link AbstractCustomVerifier} implementation.
     */
    protected V chain() {
        @SuppressWarnings("unchecked")
        final V chain = (V) this;
        return chain;
    }

    @Override
    public V equalTo(final Object other) {
        return equalTo(other, other);
    }

    @Override
    public V equalTo(final Object other, final Object name) {
        final T value = verification.getValue();
        final boolean result = isEqualTo(value, other);

        verification.report(result, MessageKeys.EQUAL_TO, name);

        return chain();
    }

    @Override
    public V equalToAny(final Object... others) {
        final T value = verification.getValue();
        final boolean result = matchAny(others, input -> isEqualTo(value, input));

        verification.report(result, MessageKeys.EQUAL_TO_ANY, (Object) others);

        return chain();
    }

    @Override
    public V hashedAs(final int hashCode) {
        final T value = verification.getValue();
        final boolean result = value != null && value.hashCode() == hashCode;

        verification.report(result, MessageKeys.HASHED_AS, hashCode);

        return chain();
    }

    @Override
    public V instanceOf(final Class<?> cls) {
        final boolean result = cls != null && cls.isInstance(verification.getValue());

        verification.report(result, MessageKeys.INSTANCE_OF, cls);

        return chain();
    }

    @Override
    public V instanceOfAll(final Class<?>... classes) {
        final T value = verification.getValue();
        final boolean result = value != null && matchAll(classes, input -> input != null && input.isInstance(value));

        verification.report(result, MessageKeys.INSTANCE_OF_ALL, (Object) classes);

        return chain();
    }

    @Override
    public V instanceOfAny(final Class<?>... classes) {
        final T value = verification.getValue();
        final boolean result = value != null && matchAny(classes, input -> input != null && input.isInstance(value));

        verification.report(result, MessageKeys.INSTANCE_OF_ANY, (Object) classes);

        return chain();
    }

    /**
     * <p>
     * Returns whether the specified {@code value} is equal to the {@code other} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     *
     * @param value
     *         the value being verified (may be {@literal null})
     * @param other
     *         the object to compare against {@code value} (may be {@literal null})
     * @return {@literal true} if {@code value} equals {@code other}; otherwise {@literal false}.
     * @see Object#equals(Object)
     */
    protected boolean isEqualTo(final T value, final Object other) {
        return other == null ? value == null : other.equals(value);
    }

    @Override
    public V not() {
        verification.setNegated(!verification.isNegated());

        return chain();
    }

    @Override
    public V nulled() {
        final boolean result = verification.getValue() == null;

        verification.report(result, MessageKeys.NULLED);

        return chain();
    }

    @Override
    public V sameAs(final Object other) {
        return sameAs(other, other);
    }

    @Override
    public V sameAs(final Object other, final Object name) {
        final boolean result = verification.getValue() == other;

        verification.report(result, MessageKeys.SAME_AS, name);

        return chain();
    }

    @Override
    public V sameAsAny(final Object... others) {
        final T value = verification.getValue();
        final boolean result = matchAny(others, input -> value == input);

        verification.report(result, MessageKeys.SAME_AS_ANY, (Object) others);

        return chain();
    }

    @Override
    public V that(final VerifierAssertion<T> assertion) {
        return that(assertion, (String) null);
    }

    @Override
    public V that(final VerifierAssertion<T> assertion, final MessageKey key, final Object... args) {
        Verifier.verify(assertion, "assertion")
            .not().nulled();

        final boolean result = assertion.verify(verification.getValue());

        verification.report(result, key, args);

        return chain();
    }

    @Override
    public V that(final VerifierAssertion<T> assertion, final String message, final Object... args) {
        Verifier.verify(assertion, "assertion")
            .not().nulled();

        final boolean result = assertion.verify(verification.getValue());

        verification.report(result, message, args);

        return chain();
    }

    @Override
    public T value() {
        return verification.getValue();
    }

    @Override
    public Verification<T> verification() {
        return verification;
    }

    /**
     * <p>
     * The {@link MessageKey MessageKeys} that are used by {@link AbstractCustomVerifier}.
     * </p>
     *
     * @since 0.2.0
     */
    public enum MessageKeys implements MessageKey {

        EQUAL_TO("org.notninja.verifier.AbstractCustomVerifier.equalTo"),
        EQUAL_TO_ANY("org.notninja.verifier.AbstractCustomVerifier.equalToAny"),
        HASHED_AS("org.notninja.verifier.AbstractCustomVerifier.hashedAs"),
        INSTANCE_OF("org.notninja.verifier.AbstractCustomVerifier.instanceOf"),
        INSTANCE_OF_ALL("org.notninja.verifier.AbstractCustomVerifier.instanceOfAll"),
        INSTANCE_OF_ANY("org.notninja.verifier.AbstractCustomVerifier.instanceOfAny"),
        NULLED("org.notninja.verifier.AbstractCustomVerifier.nulled"),
        SAME_AS("org.notninja.verifier.AbstractCustomVerifier.sameAs"),
        SAME_AS_ANY("org.notninja.verifier.AbstractCustomVerifier.sameAsAny");

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
