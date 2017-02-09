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
package io.skelp.verifier;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import io.skelp.verifier.service.Services;
import io.skelp.verifier.type.ArrayVerifier;
import io.skelp.verifier.type.BigDecimalVerifier;
import io.skelp.verifier.type.BigIntegerVerifier;
import io.skelp.verifier.type.BooleanVerifier;
import io.skelp.verifier.type.ByteVerifier;
import io.skelp.verifier.type.CalendarVerifier;
import io.skelp.verifier.type.CharacterVerifier;
import io.skelp.verifier.type.ClassVerifier;
import io.skelp.verifier.type.CollectionVerifier;
import io.skelp.verifier.type.ComparableVerifier;
import io.skelp.verifier.type.DateVerifier;
import io.skelp.verifier.type.DoubleVerifier;
import io.skelp.verifier.type.FloatVerifier;
import io.skelp.verifier.type.IntegerVerifier;
import io.skelp.verifier.type.LocaleVerifier;
import io.skelp.verifier.type.LongVerifier;
import io.skelp.verifier.type.MapVerifier;
import io.skelp.verifier.type.ObjectVerifier;
import io.skelp.verifier.type.ShortVerifier;
import io.skelp.verifier.type.StringVerifier;
import io.skelp.verifier.type.ThrowableVerifier;
import io.skelp.verifier.verification.Verification;
import io.skelp.verifier.verification.VerificationProvider;

/**
 * <p>
 * Verifies and validates values either based on their data type (e.g. {@code String}) or a use case (e.g. email
 * address).
 * </p>
 * <p>
 * Custom implementations of {@link CustomVerifier} can be passed to {@link #verify(Object, Object, Class)} for
 * extending what's included in {@code Verifier} by default, but this class can also be extended for large applications
 * to add more {@code verify} methods for an arguably even cleaner API.
 * </p>
 * <p>
 * {@code Verifier} uses the Java SPI (Service Provider Interfaces) framework to allow for advanced configuration while
 * maintaining a clean and simple API.
 *
 * @author Alasdair Mercer
 */
public class Verifier {

    /**
     * <p>
     * Calls the {@link VerificationProvider VerificationProviders} in order of importance with the specified
     * {@code value} and {@code name} and returns the first non-null {@link Verification} result.
     * </p>
     *
     * @param value
     *         the value being verified
     * @param name
     *         the optional name for the value being verified
     * @param <T>
     *         the type of the value being verified
     * @return The {@link Verification} instance using {@code value} and {@code name}.
     * @throws VerifierException
     *         If any of the {@link VerificationProvider VerificationProviders} called experience a problem occurs while
     *         providing the {@link Verification} instance.
     */
    protected static <T> Verification<T> getVerification(final T value, final Object name) {
        return Services.findFirstNonNullForWeightedService(VerificationProvider.class, provider -> provider.getVerification(value, name));
    }

    /**
     * <p>
     * Starts a chain for verifying the specified {@code value} as an array using an {@link ArrayVerifier}.
     * </p>
     * <p>
     * If {@code value} fails any subsequent verifications within this chain a {@link VerifierException} will be thrown
     * immediately by the method for the offending verification.
     * </p>
     *
     * @param value
     *         the array to be verified (may be {@literal null})
     * @param <E>
     *         the type of the elements contained within {@code value}
     * @return An {@link ArrayVerifier} to be used to verify {@code value}.
     * @throws VerifierException
     *         If a problem occurs while setting up the {@link ArrayVerifier}.
     * @see #verify(Object[], Object)
     * @see ArrayVerifier
     */
    public static <E> ArrayVerifier<E> verify(final E[] value) {
        return verify(value, null);
    }

    /**
     * <p>
     * Starts a chain for verifying the specified {@code value} as an array using an {@link ArrayVerifier} while
     * allowing {@code value} to be given an optional friendlier {@code name} for the {@link VerifierException} message
     * in the event that one is thrown.
     * </p>
     * <p>
     * If {@code value} fails any subsequent verifications within this chain a {@link VerifierException} will be thrown
     * immediately by the method for the offending verification.
     * </p>
     *
     * @param value
     *         the array to be verified (may be {@literal null})
     * @param name
     *         the optional name used to represent {@code value} (may be {@literal null})
     * @param <E>
     *         the type of the elements contained within {@code value}
     * @return An {@link ArrayVerifier} to be used to verify {@code value}.
     * @throws VerifierException
     *         If a problem occurs while setting up the {@link ArrayVerifier}.
     * @see #verify(Object[])
     * @see ArrayVerifier
     */
    public static <E> ArrayVerifier<E> verify(final E[] value, final Object name) {
        return new ArrayVerifier<>(getVerification(value, name));
    }

    /**
     * <p>
     * Starts a chain for verifying the specified {@code value} as a big decimal using a {@link BigDecimalVerifier}.
     * </p>
     * <p>
     * If {@code value} fails any subsequent verifications within this chain a {@link VerifierException} will be thrown
     * immediately by the method for the offending verification.
     * </p>
     *
     * @param value
     *         the {@code BigDecimal} to be verified (may be {@literal null})
     * @return A {@link BigDecimalVerifier} to be used to verify {@code value}.
     * @throws VerifierException
     *         If a problem occurs while setting up the {@link BigDecimalVerifier}.
     * @see #verify(BigDecimal, Object)
     * @see BigDecimalVerifier
     */
    public static BigDecimalVerifier verify(final BigDecimal value) {
        return verify(value, null);
    }

    /**
     * <p>
     * Starts a chain for verifying the specified {@code value} as a big decimal using a {@link BigDecimalVerifier}
     * while allowing {@code value} to be given an optional friendlier {@code name} for the {@link VerifierException}
     * message in the event that one is thrown.
     * </p>
     * <p>
     * If {@code value} fails any subsequent verifications within this chain a {@link VerifierException} will be thrown
     * immediately by the method for the offending verification.
     * </p>
     *
     * @param value
     *         the {@code BigDecimal} to be verified (may be {@literal null})
     * @param name
     *         the optional name used to represent {@code value} (may be {@literal null})
     * @return A {@link BigDecimalVerifier} to be used to verify {@code value}.
     * @throws VerifierException
     *         If a problem occurs while setting up the {@link BigDecimalVerifier}.
     * @see #verify(BigDecimal)
     * @see BigDecimalVerifier
     */
    public static BigDecimalVerifier verify(final BigDecimal value, final Object name) {
        return new BigDecimalVerifier(getVerification(value, name));
    }

    /**
     * <p>
     * Starts a chain for verifying the specified {@code value} as a big integer using a {@link BigIntegerVerifier}.
     * </p>
     * <p>
     * If {@code value} fails any subsequent verifications within this chain a {@link VerifierException} will be thrown
     * immediately by the method for the offending verification.
     * </p>
     *
     * @param value
     *         the {@code BigInteger} to be verified (may be {@literal null})
     * @return A {@link BigIntegerVerifier} to be used to verify {@code value}.
     * @throws VerifierException
     *         If a problem occurs while setting up the {@link BigIntegerVerifier}.
     * @see #verify(BigInteger, Object)
     * @see BigIntegerVerifier
     */
    public static BigIntegerVerifier verify(final BigInteger value) {
        return verify(value, null);
    }

    /**
     * <p>
     * Starts a chain for verifying the specified {@code value} as a big integer using a {@link BigIntegerVerifier}
     * while allowing {@code value} to be given an optional friendlier {@code name} for the {@link VerifierException}
     * message in the event that one is thrown.
     * </p>
     * <p>
     * If {@code value} fails any subsequent verifications within this chain a {@link VerifierException} will be thrown
     * immediately by the method for the offending verification.
     * </p>
     *
     * @param value
     *         the {@code BigInteger} to be verified (may be {@literal null})
     * @param name
     *         the optional name used to represent {@code value} (may be {@literal null})
     * @return A {@link BigIntegerVerifier} to be used to verify {@code value}.
     * @throws VerifierException
     *         If a problem occurs while setting up the {@link BigIntegerVerifier}.
     * @see #verify(BigInteger)
     * @see BigIntegerVerifier
     */
    public static BigIntegerVerifier verify(final BigInteger value, final Object name) {
        return new BigIntegerVerifier(getVerification(value, name));
    }

    /**
     * <p>
     * Starts a chain for verifying the specified {@code value} as a boolean using a {@link BooleanVerifier}.
     * </p>
     * <p>
     * If {@code value} fails any subsequent verifications within this chain a {@link VerifierException} will be thrown
     * immediately by the method for the offending verification.
     * </p>
     *
     * @param value
     *         the {@code Boolean} to be verified (may be {@literal null})
     * @return A {@link BooleanVerifier} to be used to verify {@code value}.
     * @throws VerifierException
     *         If a problem occurs while setting up the {@link BooleanVerifier}.
     * @see #verify(Boolean, Object)
     * @see BooleanVerifier
     */
    public static BooleanVerifier verify(final Boolean value) {
        return verify(value, null);
    }

    /**
     * <p>
     * Starts a chain for verifying the specified {@code value} as a boolean using a {@link BooleanVerifier} while
     * allowing {@code value} to be given an optional friendlier {@code name} for the {@link VerifierException} message
     * in the event that one is thrown.
     * </p>
     * <p>
     * If {@code value} fails any subsequent verifications within this chain a {@link VerifierException} will be thrown
     * immediately by the method for the offending verification.
     * </p>
     *
     * @param value
     *         the {@code Boolean} to be verified (may be {@literal null})
     * @param name
     *         the optional name used to represent {@code value} (may be {@literal null})
     * @return A {@link BooleanVerifier} to be used to verify {@code value}.
     * @throws VerifierException
     *         If a problem occurs while setting up the {@link BooleanVerifier}.
     * @see #verify(Boolean)
     * @see BooleanVerifier
     */
    public static BooleanVerifier verify(final Boolean value, final Object name) {
        return new BooleanVerifier(getVerification(value, name));
    }

    /**
     * <p>
     * Starts a chain for verifying the specified {@code value} as a byte using a {@link ByteVerifier}.
     * </p>
     * <p>
     * If {@code value} fails any subsequent verifications within this chain a {@link VerifierException} will be thrown
     * immediately by the method for the offending verification.
     * </p>
     *
     * @param value
     *         the {@code Byte} to be verified (may be {@literal null})
     * @return A {@link ByteVerifier} to be used to verify {@code value}.
     * @throws VerifierException
     *         If a problem occurs while setting up the {@link ByteVerifier}.
     * @see #verify(Byte, Object)
     * @see ByteVerifier
     */
    public static ByteVerifier verify(final Byte value) {
        return verify(value, null);
    }

    /**
     * <p>
     * Starts a chain for verifying the specified {@code value} as a byte using a {@link ByteVerifier} while allowing
     * {@code value} to be given an optional friendlier {@code name} for the {@link VerifierException} message in the
     * event that one is thrown.
     * </p>
     * <p>
     * If {@code value} fails any subsequent verifications within this chain a {@link VerifierException} will be thrown
     * immediately by the method for the offending verification.
     * </p>
     *
     * @param value
     *         the {@code Byte} to be verified (may be {@literal null})
     * @param name
     *         the optional name used to represent {@code value} (may be {@literal null})
     * @return A {@link ByteVerifier} to be used to verify {@code value}.
     * @throws VerifierException
     *         If a problem occurs while setting up the {@link ByteVerifier}.
     * @see #verify(Byte)
     * @see ByteVerifier
     */
    public static ByteVerifier verify(final Byte value, final Object name) {
        return new ByteVerifier(getVerification(value, name));
    }

    /**
     * <p>
     * Starts a chain for verifying the specified {@code value} as a calendar using a {@link CalendarVerifier}.
     * </p>
     * <p>
     * If {@code value} fails any subsequent verifications within this chain a {@link VerifierException} will be thrown
     * immediately by the method for the offending verification.
     * </p>
     *
     * @param value
     *         the {@code Calendar} to be verified (may be {@literal null})
     * @return A {@link CalendarVerifier} to be used to verify {@code value}.
     * @throws VerifierException
     *         If a problem occurs while setting up the {@link CalendarVerifier}.
     * @see #verify(Calendar, Object)
     * @see CalendarVerifier
     */
    public static CalendarVerifier verify(final Calendar value) {
        return verify(value, null);
    }

    /**
     * <p>
     * Starts a chain for verifying the specified {@code value} as a calendar using a {@link CalendarVerifier} while
     * allowing {@code value} to be given an optional friendlier {@code name} for the {@link VerifierException} message
     * in the event that one is thrown.
     * </p>
     * <p>
     * If {@code value} fails any subsequent verifications within this chain a {@link VerifierException} will be thrown
     * immediately by the method for the offending verification.
     * </p>
     *
     * @param value
     *         the {@code Calendar} to be verified (may be {@literal null})
     * @param name
     *         the optional name used to represent {@code value} (may be {@literal null})
     * @return A {@link CalendarVerifier} to be used to verify {@code value}.
     * @throws VerifierException
     *         If a problem occurs while setting up the {@link CalendarVerifier}.
     * @see #verify(Calendar)
     * @see CalendarVerifier
     */
    public static CalendarVerifier verify(final Calendar value, final Object name) {
        return new CalendarVerifier(getVerification(value, name));
    }

    /**
     * <p>
     * Starts a chain for verifying the specified {@code value} as a character using a {@link CharacterVerifier}.
     * </p>
     * <p>
     * If {@code value} fails any subsequent verifications within this chain a {@link VerifierException} will be thrown
     * immediately by the method for the offending verification.
     * </p>
     *
     * @param value
     *         the {@code Character} to be verified (may be {@literal null})
     * @return A {@link CharacterVerifier} to be used to verify {@code value}.
     * @throws VerifierException
     *         If a problem occurs while setting up the {@link CharacterVerifier}.
     * @see #verify(Character, Object)
     * @see CharacterVerifier
     */
    public static CharacterVerifier verify(final Character value) {
        return verify(value, null);
    }

    /**
     * <p>
     * Starts a chain for verifying the specified {@code value} as a character using a {@link CharacterVerifier} while
     * allowing {@code value} to be given an optional friendlier {@code name} for the {@link VerifierException} message
     * in the event that one is thrown.
     * </p>
     * <p>
     * If {@code value} fails any subsequent verifications within this chain a {@link VerifierException} will be thrown
     * immediately by the method for the offending verification.
     * </p>
     *
     * @param value
     *         the {@code Character} to be verified (may be {@literal null})
     * @param name
     *         the optional name used to represent {@code value} (may be {@literal null})
     * @return A {@link CharacterVerifier} to be used to verify {@code value}.
     * @throws VerifierException
     *         If a problem occurs while setting up the {@link CharacterVerifier}.
     * @see #verify(Character)
     * @see CharacterVerifier
     */
    public static CharacterVerifier verify(final Character value, final Object name) {
        return new CharacterVerifier(getVerification(value, name));
    }

    /**
     * <p>
     * Starts a chain for verifying the specified {@code value} as a class using a {@link ClassVerifier}.
     * </p>
     * <p>
     * If {@code value} fails any subsequent verifications within this chain a {@link VerifierException} will be thrown
     * immediately by the method for the offending verification.
     * </p>
     *
     * @param value
     *         the {@code Class} to be verified (may be {@literal null})
     * @return A {@link ClassVerifier} to be used to verify {@code value}.
     * @throws VerifierException
     *         If a problem occurs while setting up the {@link ClassVerifier}.
     * @see #verify(Class, Object)
     * @see ClassVerifier
     */
    public static ClassVerifier verify(final Class value) {
        return verify(value, null);
    }

    /**
     * <p>
     * Starts a chain for verifying the specified {@code value} as a class using a {@link ClassVerifier} while allowing
     * {@code value} to be given an optional friendlier {@code name} for the {@link VerifierException} message in the
     * event that one is thrown.
     * </p>
     * <p>
     * If {@code value} fails any subsequent verifications within this chain a {@link VerifierException} will be thrown
     * immediately by the method for the offending verification.
     * </p>
     *
     * @param value
     *         the {@code Class} to be verified (may be {@literal null})
     * @param name
     *         the optional name used to represent {@code value} (may be {@literal null})
     * @return A {@link ClassVerifier} to be used to verify {@code value}.
     * @throws VerifierException
     *         If a problem occurs while setting up the {@link ClassVerifier}.
     * @see #verify(Class)
     * @see ClassVerifier
     */
    public static ClassVerifier verify(final Class value, final Object name) {
        return new ClassVerifier(getVerification(value, name));
    }

    /**
     * <p>
     * Starts a chain for verifying the specified {@code value} as a collection using a {@link CollectionVerifier}.
     * </p>
     * <p>
     * If {@code value} fails any subsequent verifications within this chain a {@link VerifierException} will be thrown
     * immediately by the method for the offending verification.
     * </p>
     *
     * @param value
     *         the {@code Collection} to be verified (may be {@literal null})
     * @param <E>
     *         the type of the elements contained within {@code value}
     * @return A {@link CollectionVerifier} to be used to verify {@code value}.
     * @throws VerifierException
     *         If a problem occurs while setting up the {@link CollectionVerifier}.
     * @see #verify(Collection, Object)
     * @see CollectionVerifier
     */
    public static <E> CollectionVerifier<E> verify(final Collection<E> value) {
        return verify(value, null);
    }

    /**
     * <p>
     * Starts a chain for verifying the specified {@code value} as a collection using a {@link CollectionVerifier} while
     * allowing {@code value} to be given an optional friendlier {@code name} for the {@link VerifierException} message
     * in the event that one is thrown.
     * </p>
     * <p>
     * If {@code value} fails any subsequent verifications within this chain a {@link VerifierException} will be thrown
     * immediately by the method for the offending verification.
     * </p>
     *
     * @param value
     *         the {@code Collection} to be verified (may be {@literal null})
     * @param name
     *         the optional name used to represent {@code value} (may be {@literal null})
     * @param <E>
     *         the type of the elements contained within {@code value}
     * @return A {@link CollectionVerifier} to be used to verify {@code value}.
     * @throws VerifierException
     *         If a problem occurs while setting up the {@link CollectionVerifier}.
     * @see #verify(Collection)
     * @see CollectionVerifier
     */
    public static <E> CollectionVerifier<E> verify(final Collection<E> value, final Object name) {
        return new CollectionVerifier<>(getVerification(value, name));
    }

    /**
     * <p>
     * Starts a chain for verifying the specified {@code value} as a date using a {@link DateVerifier}.
     * </p>
     * <p>
     * If {@code value} fails any subsequent verifications within this chain a {@link VerifierException} will be thrown
     * immediately by the method for the offending verification.
     * </p>
     *
     * @param value
     *         the {@code Date} to be verified (may be {@literal null})
     * @return A {@link DateVerifier} to be used to verify {@code value}.
     * @throws VerifierException
     *         If a problem occurs while setting up the {@link DateVerifier}.
     * @see #verify(Date, Object)
     * @see DateVerifier
     */
    public static DateVerifier verify(final Date value) {
        return verify(value, null);
    }

    /**
     * <p>
     * Starts a chain for verifying the specified {@code value} as a date using a {@link DateVerifier} while allowing
     * {@code value} to be given an optional friendlier {@code name} for the {@link VerifierException} message in the
     * event that one is thrown.
     * </p>
     * <p>
     * If {@code value} fails any subsequent verifications within this chain a {@link VerifierException} will be thrown
     * immediately by the method for the offending verification.
     * </p>
     *
     * @param value
     *         the {@code Date} to be verified (may be {@literal null})
     * @param name
     *         the optional name used to represent {@code value} (may be {@literal null})
     * @return A {@link DateVerifier} to be used to verify {@code value}.
     * @throws VerifierException
     *         If a problem occurs while setting up the {@link DateVerifier}.
     * @see #verify(Date)
     * @see DateVerifier
     */
    public static DateVerifier verify(final Date value, final Object name) {
        return new DateVerifier(getVerification(value, name));
    }

    /**
     * <p>
     * Starts a chain for verifying the specified {@code value} as a double using a {@link DoubleVerifier}.
     * </p>
     * <p>
     * If {@code value} fails any subsequent verifications within this chain a {@link VerifierException} will be thrown
     * immediately by the method for the offending verification.
     * </p>
     *
     * @param value
     *         the {@code Double} to be verified (may be {@literal null})
     * @return A {@link DoubleVerifier} to be used to verify {@code value}.
     * @throws VerifierException
     *         If a problem occurs while setting up the {@link DoubleVerifier}.
     * @see #verify(Double, Object)
     * @see DoubleVerifier
     */
    public static DoubleVerifier verify(final Double value) {
        return verify(value, null);
    }

    /**
     * <p>
     * Starts a chain for verifying the specified {@code value} as a double using a {@link DoubleVerifier} while
     * allowing {@code value} to be given an optional friendlier {@code name} for the {@link VerifierException} message
     * in the event that one is thrown.
     * </p>
     * <p>
     * If {@code value} fails any subsequent verifications within this chain a {@link VerifierException} will be thrown
     * immediately by the method for the offending verification.
     * </p>
     *
     * @param value
     *         the {@code Double} to be verified (may be {@literal null})
     * @param name
     *         the optional name used to represent {@code value} (may be {@literal null})
     * @return A {@link DoubleVerifier} to be used to verify {@code value}.
     * @throws VerifierException
     *         If a problem occurs while setting up the {@link DoubleVerifier}.
     * @see #verify(Double)
     * @see DoubleVerifier
     */
    public static DoubleVerifier verify(final Double value, final Object name) {
        return new DoubleVerifier(getVerification(value, name));
    }

    /**
     * <p>
     * Starts a chain for verifying the specified {@code value} as a float using a {@link FloatVerifier}.
     * </p>
     * <p>
     * If {@code value} fails any subsequent verifications within this chain a {@link VerifierException} will be thrown
     * immediately by the method for the offending verification.
     * </p>
     *
     * @param value
     *         the {@code Float} to be verified (may be {@literal null})
     * @return A {@link FloatVerifier} to be used to verify {@code value}.
     * @throws VerifierException
     *         If a problem occurs while setting up the {@link FloatVerifier}.
     * @see #verify(Float, Object)
     * @see FloatVerifier
     */
    public static FloatVerifier verify(final Float value) {
        return verify(value, null);
    }

    /**
     * <p>
     * Starts a chain for verifying the specified {@code value} as a float using a {@link FloatVerifier} while allowing
     * {@code value} to be given an optional friendlier {@code name} for the {@link VerifierException} message in the
     * event that one is thrown.
     * </p>
     * <p>
     * If {@code value} fails any subsequent verifications within this chain a {@link VerifierException} will be thrown
     * immediately by the method for the offending verification.
     * </p>
     *
     * @param value
     *         the {@code Float} to be verified (may be {@literal null})
     * @param name
     *         the optional name used to represent {@code value} (may be {@literal null})
     * @return A {@link FloatVerifier} to be used to verify {@code value}.
     * @throws VerifierException
     *         If a problem occurs while setting up the {@link FloatVerifier}.
     * @see #verify(Float)
     * @see FloatVerifier
     */
    public static FloatVerifier verify(final Float value, final Object name) {
        return new FloatVerifier(getVerification(value, name));
    }

    /**
     * <p>
     * Starts a chain for verifying the specified {@code value} as an integer using an {@link IntegerVerifier}.
     * </p>
     * <p>
     * If {@code value} fails any subsequent verifications within this chain a {@link VerifierException} will be thrown
     * immediately by the method for the offending verification.
     * </p>
     *
     * @param value
     *         the {@code Integer} to be verified (may be {@literal null})
     * @return An {@link IntegerVerifier} to be used to verify {@code value}.
     * @throws VerifierException
     *         If a problem occurs while setting up the {@link IntegerVerifier}.
     * @see #verify(Integer, Object)
     * @see IntegerVerifier
     */
    public static IntegerVerifier verify(final Integer value) {
        return verify(value, null);
    }

    /**
     * <p>
     * Starts a chain for verifying the specified {@code value} as an integer using an {@link IntegerVerifier} while
     * allowing {@code value} to be given an optional friendlier {@code name} for the {@link VerifierException} message
     * in the event that one is thrown.
     * </p>
     * <p>
     * If {@code value} fails any subsequent verifications within this chain a {@link VerifierException} will be thrown
     * immediately by the method for the offending verification.
     * </p>
     *
     * @param value
     *         the {@code Integer} to be verified (may be {@literal null})
     * @param name
     *         the optional name used to represent {@code value} (may be {@literal null})
     * @return An {@link IntegerVerifier} to be used to verify {@code value}.
     * @throws VerifierException
     *         If a problem occurs while setting up the {@link IntegerVerifier}.
     * @see #verify(Integer)
     * @see IntegerVerifier
     */
    public static IntegerVerifier verify(final Integer value, final Object name) {
        return new IntegerVerifier(getVerification(value, name));
    }

    /**
     * <p>
     * Starts a chain for verifying the specified {@code value} as a locale using a {@link LocaleVerifier}.
     * </p>
     * <p>
     * If {@code value} fails any subsequent verifications within this chain a {@link VerifierException} will be thrown
     * immediately by the method for the offending verification.
     * </p>
     *
     * @param value
     *         the {@code Locale} to be verified (may be {@literal null})
     * @return A {@link LocaleVerifier} to be used to verify {@code value}.
     * @throws VerifierException
     *         If a problem occurs while setting up the {@link LocaleVerifier}.
     * @see #verify(Locale, Object)
     * @see LocaleVerifier
     */
    public static LocaleVerifier verify(final Locale value) {
        return verify(value, null);
    }

    /**
     * <p>
     * Starts a chain for verifying the specified {@code value} as a locale using a {@link LocaleVerifier} while
     * allowing {@code value} to be given an optional friendlier {@code name} for the {@link VerifierException} message
     * in the event that one is thrown.
     * </p>
     * <p>
     * If {@code value} fails any subsequent verifications within this chain a {@link VerifierException} will be thrown
     * immediately by the method for the offending verification.
     * </p>
     *
     * @param value
     *         the {@code Locale} to be verified (may be {@literal null})
     * @param name
     *         the optional name used to represent {@code value} (may be {@literal null})
     * @return A {@link LocaleVerifier} to be used to verify {@code value}.
     * @throws VerifierException
     *         If a problem occurs while setting up the {@link LocaleVerifier}.
     * @see #verify(Locale)
     * @see LocaleVerifier
     */
    public static LocaleVerifier verify(final Locale value, final Object name) {
        return new LocaleVerifier(getVerification(value, name));
    }

    /**
     * <p>
     * Starts a chain for verifying the specified {@code value} as a long using a {@link LongVerifier}.
     * </p>
     * <p>
     * If {@code value} fails any subsequent verifications within this chain a {@link VerifierException} will be thrown
     * immediately by the method for the offending verification.
     * </p>
     *
     * @param value
     *         the {@code Long} to be verified (may be {@literal null})
     * @return A {@link LongVerifier} to be used to verify {@code value}.
     * @throws VerifierException
     *         If a problem occurs while setting up the {@link LongVerifier}.
     * @see #verify(Long, Object)
     * @see LongVerifier
     */
    public static LongVerifier verify(final Long value) {
        return verify(value, null);
    }

    /**
     * <p>
     * Starts a chain for verifying the specified {@code value} as a long using a {@link LongVerifier} while allowing
     * {@code value} to be given an optional friendlier {@code name} for the {@link VerifierException} message in the
     * event that one is thrown.
     * </p>
     * <p>
     * If {@code value} fails any subsequent verifications within this chain a {@link VerifierException} will be thrown
     * immediately by the method for the offending verification.
     * </p>
     *
     * @param value
     *         the {@code Long} to be verified (may be {@literal null})
     * @param name
     *         the optional name used to represent {@code value} (may be {@literal null})
     * @return A {@link LongVerifier} to be used to verify {@code value}.
     * @throws VerifierException
     *         If a problem occurs while setting up the {@link LongVerifier}.
     * @see #verify(Long)
     * @see LongVerifier
     */
    public static LongVerifier verify(final Long value, final Object name) {
        return new LongVerifier(getVerification(value, name));
    }

    /**
     * <p>
     * Starts a chain for verifying the specified {@code value} as a map using a {@link MapVerifier}.
     * </p>
     * <p>
     * If {@code value} fails any subsequent verifications within this chain a {@link VerifierException} will be thrown
     * immediately by the method for the offending verification.
     * </p>
     *
     * @param value
     *         the {@code Map} to be verified (may be {@literal null})
     * @param <K>
     *         the type of the keys contained within {@code value}
     * @param <V>
     *         the type of the values contained within {@code value}
     * @return A {@link MapVerifier} to be used to verify {@code value}.
     * @throws VerifierException
     *         If a problem occurs while setting up the {@link MapVerifier}.
     * @see #verify(Map, Object)
     * @see MapVerifier
     */
    public static <K, V> MapVerifier<K, V> verify(final Map<K, V> value) {
        return verify(value, null);
    }

    /**
     * <p>
     * Starts a chain for verifying the specified {@code value} as a map using a {@link MapVerifier} while allowing
     * {@code value} to be given an optional friendlier {@code name} for the {@link VerifierException} message in the
     * event that one is thrown.
     * </p>
     * <p>
     * If {@code value} fails any subsequent verifications within this chain a {@link VerifierException} will be thrown
     * immediately by the method for the offending verification.
     * </p>
     *
     * @param value
     *         the {@code Map} to be verified (may be {@literal null})
     * @param name
     *         the optional name used to represent {@code value} (may be {@literal null})
     * @param <K>
     *         the type of the keys contained within {@code value}
     * @param <V>
     *         the type of the values contained within {@code value}
     * @return A {@link MapVerifier} to be used to verify {@code value}.
     * @throws VerifierException
     *         If a problem occurs while setting up the {@link MapVerifier}.
     * @see #verify(Map)
     * @see MapVerifier
     */
    public static <K, V> MapVerifier<K, V> verify(final Map<K, V> value, final Object name) {
        return new MapVerifier<>(getVerification(value, name));
    }

    /**
     * <p>
     * Starts a chain for verifying the specified {@code value} as an object using an {@link ObjectVerifier}.
     * </p>
     * <p>
     * If {@code value} fails any subsequent verifications within this chain a {@link VerifierException} will be thrown
     * immediately by the method for the offending verification.
     * </p>
     * <p>
     * For a lot of built-in data types other, more specific, {@code verify} methods may also exist which perhaps
     * provide other useful verification methods. However, this method is useful for cases when one doesn't exist and
     * for custom objects, possibly domain-specific.
     * </p>
     *
     * @param value
     *         the {@code Object} to be verified (may be {@literal null})
     * @return An {@link ObjectVerifier} to be used to verify {@code value}.
     * @throws VerifierException
     *         If a problem occurs while setting up the {@link ObjectVerifier}.
     * @see #verify(Object, Object)
     * @see ObjectVerifier
     */
    public static ObjectVerifier verify(final Object value) {
        return verify(value, null);
    }

    /**
     * <p>
     * Starts a chain for verifying the specified {@code value} as an object using an {@link ObjectVerifier} while
     * allowing {@code value} to be given an optional friendlier {@code name} for the {@link VerifierException} message
     * in the event that one is thrown.
     * </p>
     * <p>
     * If {@code value} fails any subsequent verifications within this chain a {@link VerifierException} will be thrown
     * immediately by the method for the offending verification.
     * </p>
     * <p>
     * For a lot of built-in data types other, more specific, {@code verify} methods may also exist which perhaps
     * provide other useful verification methods. However, this method is useful for cases when one doesn't exist and
     * for custom objects, possibly domain-specific.
     * </p>
     *
     * @param value
     *         the {@code Object} to be verified (may be {@literal null})
     * @param name
     *         the optional name used to represent {@code value} (may be {@literal null})
     * @return A {@link ObjectVerifier} to be used to verify {@code value}.
     * @throws VerifierException
     *         If a problem occurs while setting up the {@link ObjectVerifier}.
     * @see #verify(Object)
     * @see ObjectVerifier
     */
    public static ObjectVerifier verify(final Object value, final Object name) {
        return new ObjectVerifier(getVerification(value, name));
    }

    /**
     * <p>
     * Starts a chain for verifying the specified {@code value} as a short using a {@link ShortVerifier}.
     * </p>
     * <p>
     * If {@code value} fails any subsequent verifications within this chain a {@link VerifierException} will be thrown
     * immediately by the method for the offending verification.
     * </p>
     *
     * @param value
     *         the {@code Short} to be verified (may be {@literal null})
     * @return A {@link ShortVerifier} to be used to verify {@code value}.
     * @throws VerifierException
     *         If a problem occurs while setting up the {@link ShortVerifier}.
     * @see #verify(Short, Object)
     * @see ShortVerifier
     */
    public static ShortVerifier verify(final Short value) {
        return verify(value, null);
    }

    /**
     * <p>
     * Starts a chain for verifying the specified {@code value} as a short using a {@link ShortVerifier} while allowing
     * {@code value} to be given an optional friendlier {@code name} for the {@link VerifierException} message in the
     * event that one is thrown.
     * </p>
     * <p>
     * If {@code value} fails any subsequent verifications within this chain a {@link VerifierException} will be thrown
     * immediately by the method for the offending verification.
     * </p>
     *
     * @param value
     *         the {@code Short} to be verified (may be {@literal null})
     * @param name
     *         the optional name used to represent {@code value} (may be {@literal null})
     * @return A {@link ShortVerifier} to be used to verify {@code value}.
     * @throws VerifierException
     *         If a problem occurs while setting up the {@link ShortVerifier}.
     * @see #verify(Short)
     * @see ShortVerifier
     */
    public static ShortVerifier verify(final Short value, final Object name) {
        return new ShortVerifier(getVerification(value, name));
    }

    /**
     * <p>
     * Starts a chain for verifying the specified {@code value} as a string using a {@link StringVerifier}.
     * </p>
     * <p>
     * If {@code value} fails any subsequent verifications within this chain a {@link VerifierException} will be thrown
     * immediately by the method for the offending verification.
     * </p>
     *
     * @param value
     *         the {@code String} to be verified (may be {@literal null})
     * @return A {@link StringVerifier} to be used to verify {@code value}.
     * @throws VerifierException
     *         If a problem occurs while setting up the {@link StringVerifier}.
     * @see #verify(String, Object)
     * @see StringVerifier
     */
    public static StringVerifier verify(final String value) {
        return verify(value, null);
    }

    /**
     * <p>
     * Starts a chain for verifying the specified {@code value} as a string using a {@link StringVerifier} while
     * allowing {@code value} to be given an optional friendlier {@code name} for the {@link VerifierException} message
     * in the event that one is thrown.
     * </p>
     * <p>
     * If {@code value} fails any subsequent verifications within this chain a {@link VerifierException} will be thrown
     * immediately by the method for the offending verification.
     * </p>
     *
     * @param value
     *         the {@code String} to be verified (may be {@literal null})
     * @param name
     *         the optional name used to represent {@code value} (may be {@literal null})
     * @return A {@link StringVerifier} to be used to verify {@code value}.
     * @throws VerifierException
     *         If a problem occurs while setting up the {@link StringVerifier}.
     * @see #verify(String)
     * @see StringVerifier
     */
    public static StringVerifier verify(final String value, final Object name) {
        return new StringVerifier(getVerification(value, name));
    }

    /**
     * <p>
     * Starts a chain for verifying the specified {@code value} as a throwable using a {@link ThrowableVerifier}.
     * </p>
     * <p>
     * If {@code value} fails any subsequent verifications within this chain a {@link VerifierException} will be thrown
     * immediately by the method for the offending verification.
     * </p>
     *
     * @param value
     *         the {@code Throwable} to be verified (may be {@literal null})
     * @return A {@link ThrowableVerifier} to be used to verify {@code value}.
     * @throws VerifierException
     *         If a problem occurs while setting up the {@link ThrowableVerifier}.
     * @see #verify(Throwable, Object)
     * @see ThrowableVerifier
     */
    public static ThrowableVerifier verify(final Throwable value) {
        return verify(value, null);
    }

    /**
     * <p>
     * Starts a chain for verifying the specified {@code value} as a throwable using a {@link ThrowableVerifier} while
     * allowing {@code value} to be given an optional friendlier {@code name} for the {@link VerifierException} message
     * in the event that one is thrown.
     * </p>
     * <p>
     * If {@code value} fails any subsequent verifications within this chain a {@link VerifierException} will be thrown
     * immediately by the method for the offending verification.
     * </p>
     *
     * @param value
     *         the {@code Throwable} to be verified (may be {@literal null})
     * @param name
     *         the optional name used to represent {@code value} (may be {@literal null})
     * @return A {@link ThrowableVerifier} to be used to verify {@code value}.
     * @throws VerifierException
     *         If a problem occurs while setting up the {@link ThrowableVerifier}.
     * @see #verify(Throwable)
     * @see ThrowableVerifier
     */
    public static ThrowableVerifier verify(final Throwable value, final Object name) {
        return new ThrowableVerifier(getVerification(value, name));
    }

    /**
     * <p>
     * Starts a chain for verifying the specified {@code value} of a type that matches the {@link CustomVerifier}
     * {@code cls} provided, which will be instantiated and used while allowing {@code value} to be given an optional
     * friendlier {@code name} for the {@link VerifierException} message in the event that one is thrown.
     * </p>
     * <p>
     * If {@code value} fails any subsequent verifications within this chain a {@link VerifierException} will be thrown
     * immediately by the method for the offending verification.
     * </p>
     *
     * @param value
     *         the value to be verified (may be {@literal null})
     * @param name
     *         the optional name used to represent {@code value} (may be {@literal null})
     * @param cls
     *         the type of {@link CustomVerifier} to be instantiated and used to verify {@code value}
     * @param <T>
     *         the type of {@code value}
     * @param <V>
     *         the type of {@code cls} for chaining purposes
     * @return An instance of {@code cls} to be used to verify {@code value}.
     * @throws VerifierException
     *         If a problem occurs while setting up the {@link CustomVerifier}.
     */
    public static <T, V extends CustomVerifier<T, V>> V verify(final T value, final Object name, final Class<V> cls) {
        final Verification<T> verification = getVerification(value, name);
        return Services.findFirstNonNullForWeightedService(CustomVerifierProvider.class, provider -> provider.getCustomVerifier(cls, verification));
    }

    /**
     * <p>
     * Starts a chain for verifying the specified {@code value} as a comparable object using a
     * {@link ComparableVerifier}.
     * </p>
     * <p>
     * If {@code value} fails any subsequent verifications within this chain a {@link VerifierException} will be thrown
     * immediately by the method for the offending verification.
     * </p>
     * <p>
     * For a lot of built-in {@code Comparable} data types other, more specific, {@code verify} methods may also exist
     * which perhaps provide other useful verification methods. However, this method is useful for cases when one
     * doesn't exist and for custom objects, possibly domain-specific.
     * </p>
     *
     * @param value
     *         the {@code Comparable} object to be verified (may be {@literal null})
     * @param <T>
     *         the {@code Comparable} type of {@code value}
     * @return A {@link ComparableVerifier} to be used to verify {@code value}.
     * @throws VerifierException
     *         If a problem occurs while setting up the {@link ComparableVerifier}.
     * @see #verifyComparable(Comparable, Object)
     * @see ComparableVerifier
     */
    public static <T extends Comparable<? super T>> ComparableVerifier<T> verifyComparable(final T value) {
        return verifyComparable(value, null);
    }

    /**
     * <p>
     * Starts a chain for verifying the specified {@code value} as a comparable object using a
     * {@link ComparableVerifier} while allowing {@code value} to be given an optional friendlier {@code name} for the
     * {@link VerifierException} message in the event that one is thrown.
     * </p>
     * <p>
     * If {@code value} fails any subsequent verifications within this chain a {@link VerifierException} will be thrown
     * immediately by the method for the offending verification.
     * </p>
     * <p>
     * For a lot of built-in {@code Comparable} data types other, more specific, {@code verify} methods may also exist
     * which perhaps provide other useful verification methods. However, this method is useful for cases when one
     * doesn't exist and for custom objects, possibly domain-specific.
     * </p>
     *
     * @param value
     *         the {@code Comparable} object to be verified (may be {@literal null})
     * @param name
     *         the optional name used to represent {@code value} (may be {@literal null})
     * @param <T>
     *         the {@code Comparable} type of {@code value}
     * @return A {@link ComparableVerifier} to be used to verify {@code value}.
     * @throws VerifierException
     *         If a problem occurs while setting up the {@link ComparableVerifier}.
     * @see #verifyComparable(Comparable)
     * @see ComparableVerifier
     */
    public static <T extends Comparable<? super T>> ComparableVerifier<T> verifyComparable(final T value, final Object name) {
        return new ComparableVerifier<>(getVerification(value, name));
    }

    /**
     * <p>
     * Creates an instance of {@link Verifier}.
     * </p>
     * <p>
     * This should <b>not</b> be used for standard programming but is available for cases where an instance is needed
     * for a Java Bean etc.
     * </p>
     */
    public Verifier() {
    }
}
