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
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.notninja.verifier.message.MessageKey;
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
 * A custom verifier provides a chain of handy verification methods which can be specific to a data type (e.g. {@code
 * String}) and/or a use case (e.g. email address).
 * </p>
 *
 * @param <T>
 *         the type of the value being verified
 * @param <V>
 *         the type of the {@link CustomVerifier} for chaining purposes
 * @author Alasdair Mercer
 */
public interface CustomVerifier<T, V extends CustomVerifier<T, V>> {

    /**
     * <p>
     * Starts a new chain for verifying the specified {@code value} as an array using an {@link ArrayVerifier}.
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
     * @see #and(Object[], Object)
     * @see ArrayVerifier
     * @since 0.2.0
     */
    <E> ArrayVerifier<E> and(E[] value);

    /**
     * <p>
     * Starts new a chain for verifying the specified {@code value} as an array using an {@link ArrayVerifier} while
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
     * @see #and(Object[])
     * @see ArrayVerifier
     * @since 0.2.0
     */
    <E> ArrayVerifier<E> and(E[] value, Object name);

    /**
     * <p>
     * Starts a new chain for verifying the specified {@code value} as a big decimal using a {@link BigDecimalVerifier}.
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
     * @see #and(BigDecimal, Object)
     * @see BigDecimalVerifier
     * @since 0.2.0
     */
    BigDecimalVerifier and(BigDecimal value);

    /**
     * <p>
     * Starts a new chain for verifying the specified {@code value} as a big decimal using a {@link BigDecimalVerifier}
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
     * @see #and(BigDecimal)
     * @see BigDecimalVerifier
     * @since 0.2.0
     */
    BigDecimalVerifier and(BigDecimal value, Object name);

    /**
     * <p>
     * Starts a new chain for verifying the specified {@code value} as a big integer using a {@link BigIntegerVerifier}.
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
     * @see #and(BigInteger, Object)
     * @see BigIntegerVerifier
     * @since 0.2.0
     */
    BigIntegerVerifier and(BigInteger value);

    /**
     * <p>
     * Starts a new chain for verifying the specified {@code value} as a big integer using a {@link BigIntegerVerifier}
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
     * @see #and(BigInteger)
     * @see BigIntegerVerifier
     * @since 0.2.0
     */
    BigIntegerVerifier and(BigInteger value, Object name);

    /**
     * <p>
     * Starts a new chain for verifying the specified {@code value} as a boolean using a {@link BooleanVerifier}.
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
     * @see #and(Boolean, Object)
     * @see BooleanVerifier
     * @since 0.2.0
     */
    BooleanVerifier and(Boolean value);

    /**
     * <p>
     * Starts a new chain for verifying the specified {@code value} as a boolean using a {@link BooleanVerifier} while
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
     * @see #and(Boolean)
     * @see BooleanVerifier
     * @since 0.2.0
     */
    BooleanVerifier and(Boolean value, Object name);

    /**
     * <p>
     * Starts a new chain for verifying the specified {@code value} as a byte using a {@link ByteVerifier}.
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
     * @see #and(Byte, Object)
     * @see ByteVerifier
     * @since 0.2.0
     */
    ByteVerifier and(Byte value);

    /**
     * <p>
     * Starts a new chain for verifying the specified {@code value} as a byte using a {@link ByteVerifier} while
     * allowing {@code value} to be given an optional friendlier {@code name} for the {@link VerifierException} message
     * in the event that one is thrown.
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
     * @see #and(Byte)
     * @see ByteVerifier
     * @since 0.2.0
     */
    ByteVerifier and(Byte value, Object name);

    /**
     * <p>
     * Starts a new chain for verifying the specified {@code value} as a calendar using a {@link CalendarVerifier}.
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
     * @see #and(Calendar, Object)
     * @see CalendarVerifier
     * @since 0.2.0
     */
    CalendarVerifier and(Calendar value);

    /**
     * <p>
     * Starts a new chain for verifying the specified {@code value} as a calendar using a {@link CalendarVerifier} while
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
     * @see #and(Calendar)
     * @see CalendarVerifier
     * @since 0.2.0
     */
    CalendarVerifier and(Calendar value, Object name);

    /**
     * <p>
     * Starts a new chain for verifying the specified {@code value} as a character using a {@link CharacterVerifier}.
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
     * @see #and(Character, Object)
     * @see CharacterVerifier
     * @since 0.2.0
     */
    CharacterVerifier and(Character value);

    /**
     * <p>
     * Starts a new chain for verifying the specified {@code value} as a character using a {@link CharacterVerifier}
     * while allowing {@code value} to be given an optional friendlier {@code name} for the {@link VerifierException}
     * message in the event that one is thrown.
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
     * @see #and(Character)
     * @see CharacterVerifier
     * @since 0.2.0
     */
    CharacterVerifier and(Character value, Object name);

    /**
     * <p>
     * Starts a new chain for verifying the specified {@code value} as a class using a {@link ClassVerifier}.
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
     * @see #and(Class, Object)
     * @see ClassVerifier
     * @since 0.2.0
     */
    ClassVerifier and(Class value);

    /**
     * <p>
     * Starts a new chain for verifying the specified {@code value} as a class using a {@link ClassVerifier} while
     * allowing {@code value} to be given an optional friendlier {@code name} for the {@link VerifierException} message
     * in the event that one is thrown.
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
     * @see #and(Class)
     * @see ClassVerifier
     * @since 0.2.0
     */
    ClassVerifier and(Class value, Object name);

    /**
     * <p>
     * Starts a new chain for verifying the specified {@code value} as a collection using a {@link CollectionVerifier}.
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
     * @see #and(Collection, Object)
     * @see CollectionVerifier
     * @since 0.2.0
     */
    <E> CollectionVerifier<E> and(Collection<E> value);

    /**
     * <p>
     * Starts a new chain for verifying the specified {@code value} as a collection using a {@link CollectionVerifier}
     * while allowing {@code value} to be given an optional friendlier {@code name} for the {@link VerifierException}
     * message in the event that one is thrown.
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
     * @see #and(Collection)
     * @see CollectionVerifier
     * @since 0.2.0
     */
    <E> CollectionVerifier<E> and(Collection<E> value, Object name);

    /**
     * <p>
     * Starts a new chain for verifying the specified {@code value} as a date using a {@link DateVerifier}.
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
     * @see #and(Date, Object)
     * @see DateVerifier
     * @since 0.2.0
     */
    DateVerifier and(Date value);

    /**
     * <p>
     * Starts a new chain for verifying the specified {@code value} as a date using a {@link DateVerifier} while
     * allowing {@code value} to be given an optional friendlier {@code name} for the {@link VerifierException} message
     * in the event that one is thrown.
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
     * @see #and(Date)
     * @see DateVerifier
     * @since 0.2.0
     */
    DateVerifier and(Date value, Object name);

    /**
     * <p>
     * Starts a new chain for verifying the specified {@code value} as a double using a {@link DoubleVerifier}.
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
     * @see #and(Double, Object)
     * @see DoubleVerifier
     * @since 0.2.0
     */
    DoubleVerifier and(Double value);

    /**
     * <p>
     * Starts a new chain for verifying the specified {@code value} as a double using a {@link DoubleVerifier} while
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
     * @see #and(Double)
     * @see DoubleVerifier
     * @since 0.2.0
     */
    DoubleVerifier and(Double value, Object name);

    /**
     * <p>
     * Starts a new chain for verifying the specified {@code value} as a float using a {@link FloatVerifier}.
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
     * @see #and(Float, Object)
     * @see FloatVerifier
     * @since 0.2.0
     */
    FloatVerifier and(Float value);

    /**
     * <p>
     * Starts a new chain for verifying the specified {@code value} as a float using a {@link FloatVerifier} while
     * allowing {@code value} to be given an optional friendlier {@code name} for the {@link VerifierException} message
     * in the event that one is thrown.
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
     * @see #and(Float)
     * @see FloatVerifier
     * @since 0.2.0
     */
    FloatVerifier and(Float value, Object name);

    /**
     * <p>
     * Starts a new chain for verifying the specified {@code value} as an integer using an {@link IntegerVerifier}.
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
     * @see #and(Integer, Object)
     * @see IntegerVerifier
     * @since 0.2.0
     */
    IntegerVerifier and(Integer value);

    /**
     * <p>
     * Starts a new chain for verifying the specified {@code value} as an integer using an {@link IntegerVerifier} while
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
     * @see #and(Integer)
     * @see IntegerVerifier
     * @since 0.2.0
     */
    IntegerVerifier and(Integer value, Object name);

    /**
     * <p>
     * Starts a new chain for verifying the specified {@code value} as a locale using a {@link LocaleVerifier}.
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
     * @see #and(Locale, Object)
     * @see LocaleVerifier
     * @since 0.2.0
     */
    LocaleVerifier and(Locale value);

    /**
     * <p>
     * Starts a new chain for verifying the specified {@code value} as a locale using a {@link LocaleVerifier} while
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
     * @see #and(Locale)
     * @see LocaleVerifier
     * @since 0.2.0
     */
    LocaleVerifier and(Locale value, Object name);

    /**
     * <p>
     * Starts a new chain for verifying the specified {@code value} as a long using a {@link LongVerifier}.
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
     * @see #and(Long, Object)
     * @see LongVerifier
     * @since 0.2.0
     */
    LongVerifier and(Long value);

    /**
     * <p>
     * Starts a new chain for verifying the specified {@code value} as a long using a {@link LongVerifier} while
     * allowing {@code value} to be given an optional friendlier {@code name} for the {@link VerifierException} message
     * in the event that one is thrown.
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
     * @see #and(Long)
     * @see LongVerifier
     * @since 0.2.0
     */
    LongVerifier and(Long value, Object name);

    /**
     * <p>
     * Starts a new chain for verifying the specified {@code value} as a map using a {@link MapVerifier}.
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
     * @param <U>
     *         the type of the values contained within {@code value}
     * @return A {@link MapVerifier} to be used to verify {@code value}.
     * @throws VerifierException
     *         If a problem occurs while setting up the {@link MapVerifier}.
     * @see #and(Map, Object)
     * @see MapVerifier
     * @since 0.2.0
     */
    <K, U> MapVerifier<K, U> and(Map<K, U> value);

    /**
     * <p>
     * Starts a new chain for verifying the specified {@code value} as a map using a {@link MapVerifier} while allowing
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
     * @param <U>
     *         the type of the values contained within {@code value}
     * @return A {@link MapVerifier} to be used to verify {@code value}.
     * @throws VerifierException
     *         If a problem occurs while setting up the {@link MapVerifier}.
     * @see #and(Map)
     * @see MapVerifier
     * @since 0.2.0
     */
    <K, U> MapVerifier<K, U> and(Map<K, U> value, Object name);

    /**
     * <p>
     * Starts a new chain for verifying the specified {@code value} as an object using an {@link ObjectVerifier}.
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
     * @see #and(Object, Object)
     * @see ObjectVerifier
     * @since 0.2.0
     */
    ObjectVerifier and(Object value);

    /**
     * <p>
     * Starts a new chain for verifying the specified {@code value} as an object using an {@link ObjectVerifier} while
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
     * @see #and(Object)
     * @see ObjectVerifier
     * @since 0.2.0
     */
    ObjectVerifier and(Object value, Object name);

    /**
     * <p>
     * Starts a new chain for verifying the specified {@code value} as a short using a {@link ShortVerifier}.
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
     * @see #and(Short, Object)
     * @see ShortVerifier
     * @since 0.2.0
     */
    ShortVerifier and(Short value);

    /**
     * <p>
     * Starts a new chain for verifying the specified {@code value} as a short using a {@link ShortVerifier} while
     * allowing {@code value} to be given an optional friendlier {@code name} for the {@link VerifierException} message
     * in the event that one is thrown.
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
     * @see #and(Short)
     * @see ShortVerifier
     * @since 0.2.0
     */
    ShortVerifier and(Short value, Object name);

    /**
     * <p>
     * Starts a new chain for verifying the specified {@code value} as a string using a {@link StringVerifier}.
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
     * @see #and(String, Object)
     * @see StringVerifier
     * @since 0.2.0
     */
    StringVerifier and(String value);

    /**
     * <p>
     * Starts a new chain for verifying the specified {@code value} as a string using a {@link StringVerifier} while
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
     * @see #and(String)
     * @see StringVerifier
     * @since 0.2.0
     */
    StringVerifier and(String value, Object name);

    /**
     * <p>
     * Starts a new chain for verifying the specified {@code value} as a throwable using a {@link ThrowableVerifier}.
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
     * @see #and(Throwable, Object)
     * @see ThrowableVerifier
     * @since 0.2.0
     */
    ThrowableVerifier and(Throwable value);

    /**
     * <p>
     * Starts a new chain for verifying the specified {@code value} as a throwable using a {@link ThrowableVerifier}
     * while allowing {@code value} to be given an optional friendlier {@code name} for the {@link VerifierException}
     * message in the event that one is thrown.
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
     * @see #and(Throwable)
     * @see ThrowableVerifier
     * @since 0.2.0
     */
    ThrowableVerifier and(Throwable value, Object name);

    /**
     * <p>
     * Starts a new chain for verifying the specified {@code value} of a type that matches the {@link CustomVerifier}
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
     * @param <U>
     *         the type of {@code value}
     * @param <C>
     *         the type of {@code cls} for chaining purposes
     * @return An instance of {@code cls} to be used to verify {@code value}.
     * @throws VerifierException
     *         If a problem occurs while setting up the {@link CustomVerifier}.
     * @since 0.2.0
     */
    <U, C extends CustomVerifier<U, C>> C and(U value, Object name, Class<C> cls);

    /**
     * <p>
     * Starts a new chain for verifying the specified {@code value} as a comparable object using a
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
     * @param <C>
     *         the {@code Comparable} type of {@code value}
     * @return A {@link ComparableVerifier} to be used to verify {@code value}.
     * @throws VerifierException
     *         If a problem occurs while setting up the {@link ComparableVerifier}.
     * @see #andComparable(Comparable, Object)
     * @see ComparableVerifier
     * @since 0.2.0
     */
    <C extends Comparable<? super C>> ComparableVerifier<C> andComparable(C value);

    /**
     * <p>
     * Starts a new chain for verifying the specified {@code value} as a comparable object using a
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
     * @param <C>
     *         the {@code Comparable} type of {@code value}
     * @return A {@link ComparableVerifier} to be used to verify {@code value}.
     * @throws VerifierException
     *         If a problem occurs while setting up the {@link ComparableVerifier}.
     * @see #andComparable(Comparable)
     * @see ComparableVerifier
     * @since 0.2.0
     */
    <C extends Comparable<? super C>> ComparableVerifier<C> andComparable(C value, Object name);

    /**
     * <p>
     * Verifies that the value is equal to the {@code other} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify(null).equalTo(null)   =&gt; PASS
     * Verifier.verify(null).equalTo("abc")  =&gt; FAIL
     * Verifier.verify("abc").equalTo(null)  =&gt; FAIL
     * Verifier.verify("abc").equalTo("abc") =&gt; PASS
     * Verifier.verify("abc").equalTo("ABC") =&gt; FAIL
     * Verifier.verify("abc").equalTo("def") =&gt; FAIL
     * </pre>
     *
     * @param other
     *         the object to compare against the value (may be {@literal null})
     * @return A reference to this {@link CustomVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #equalTo(Object, Object)
     * @see Object#equals(Object)
     */
    V equalTo(Object other);

    /**
     * <p>
     * Verifies that the value is equal to the {@code other} provided while allowing {@code other} to be given an
     * optional friendlier {@code name} for the {@link VerifierException} message in the event that one is thrown.
     * </p>
     * <p>
     * While {@code name} can be {@literal null} it is recommended instead to either use {@link #equalTo(Object)} or
     * pass {@code other} as {@code name} instead to get a better verification error message.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify(null, *).equalTo(null)   =&gt; PASS
     * Verifier.verify(null, *).equalTo("abc")  =&gt; FAIL
     * Verifier.verify("abc", *).equalTo(null)  =&gt; FAIL
     * Verifier.verify("abc", *).equalTo("abc") =&gt; PASS
     * Verifier.verify("abc", *).equalTo("ABC") =&gt; FAIL
     * Verifier.verify("abc", *).equalTo("def") =&gt; FAIL
     * </pre>
     *
     * @param other
     *         the object to compare against the value (may be {@literal null})
     * @param name
     *         the optional name used to represent {@code other} (may be {@literal null})
     * @return A reference to this {@link CustomVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #equalTo(Object)
     * @see Object#equals(Object)
     */
    V equalTo(Object other, Object name);

    /**
     * <p>
     * Verifies that the value is equal to any of the {@code others} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify(*).equalToAny()                         =&gt; FAIL
     * Verifier.verify(*).equalToAny((Object[]) null)          =&gt; FAIL
     * Verifier.verify(null).equalToAny("ghi", "def", null)    =&gt; PASS
     * Verifier.verify(null).equalToAny("ghi", "def", "abc")   =&gt; FAIL
     * Verifier.verify("abc").equalToAny("ghi", "def", null)   =&gt; FAIL
     * Verifier.verify("abc").equalToAny("ghi", "def", "abc")  =&gt; PASS
     * Verifier.verify("abc").equalToAny("GHI", "DEF", "ABC")  =&gt; FAIL
     * </pre>
     *
     * @param others
     *         the objects to compare against the value (may be {@literal null} or contain {@literal null} references)
     * @return A reference to this {@link CustomVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see Object#equals(Object)
     */
    V equalToAny(Object... others);

    /**
     * <p>
     * Verifies that the value has the {@code hashCode} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify(null).hashedAs(*)   =&gt; FAIL
     * Verifier.verify(123).hashedAs(0)    =&gt; FAIL
     * Verifier.verify(123).hashedAs(321)  =&gt; FAIL
     * Verifier.verify(123).hashedAs(123)  =&gt; PASS
     * Verifier.verify(123).hashedAs(-123) =&gt; FAIL
     * </pre>
     *
     * @param hashCode
     *         the hash code to be compared against that of the value
     * @return A reference to this {@link CustomVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see Object#hashCode()
     */
    V hashedAs(int hashCode);

    /**
     * <p>
     * Verifies that the value is an instance of the class provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify(*).instanceOf(null)                           =&gt; FAIL
     * Verifier.verify(*).instanceOf(Object.class)                   =&gt; PASS
     * Verifier.verify(null).instanceOf(*)                           =&gt; FAIL
     * Verifier.verify(new ArrayList()).instanceOf(Collection.class) =&gt; PASS
     * Verifier.verify(new ArrayList()).instanceOf(Map.class)        =&gt; FAIL
     * </pre>
     *
     * @param cls
     *         the {@code Class} to be checked as a parent of the value (may be {@literal null})
     * @return A reference to this {@link CustomVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see Class#isInstance(Object)
     */
    V instanceOf(Class<?> cls);

    /**
     * <p>
     * Verifies that the value is an instance of <b>all</b> of the {@code classes} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify(*).instanceOfAll()                                                            =&gt; PASS
     * Verifier.verify(*).instanceOfAll((Class[]) null)                                              =&gt; PASS
     * Verifier.verify(*).instanceOfAll(Object.class)                                                =&gt; PASS
     * Verifier.verify(*).instanceOfAll(*, null)                                                     =&gt; FAIL
     * Verifier.verify(null).instanceOfAll(*)                                                        =&gt; FAIL
     * Verifier.verify(new ArrayList()).instanceOfAll(ArrayList.class, List.class, Collection.class) =&gt; PASS
     * Verifier.verify(new ArrayList()).instanceOfAll(ArrayList.class, Map.class, Collection.class)  =&gt; FAIL
     * </pre>
     *
     * @param classes
     *         the classes to be checked as parents of the value (may be {@literal null} or contain {@literal null}
     *         references)
     * @return A reference to this {@link CustomVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see Class#isInstance(Object)
     */
    V instanceOfAll(Class<?>... classes);

    /**
     * <p>
     * Verifies that the value is an instance of <b>any</b> of the {@code classes} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify(*).instanceOfAny()                                                         =&gt; FAIL
     * Verifier.verify(*).instanceOfAny((Class[]) null)                                           =&gt; FAIL
     * Verifier.verify(*).instanceOfAny(*, Object.class)                                          =&gt; PASS
     * Verifier.verify(null).instanceOfAny(*)                                                     =&gt; FAIL
     * Verifier.verify(new ArrayList()).instanceOfAny(Boolean.class, Map.class, Collection.class) =&gt; PASS
     * Verifier.verify(new ArrayList()).instanceOfAny(Boolean.class, Map.class, URI.class)        =&gt; FAIL
     * </pre>
     *
     * @param classes
     *         the classes to be checked as parents of the value (may be {@literal null} or contain {@literal null}
     *         references)
     * @return A reference to this {@link CustomVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see Class#isInstance(Object)
     */
    V instanceOfAny(Class<?>... classes);

    /**
     * <p>
     * Negates the result of the next verification to be checked.
     * </p>
     * <p>
     * There is no limit to how many times this method can be called sequentially and it will simply keep negating
     * itself.
     * </p>
     * <pre>
     * Verifier.verify(null).not().nulled()               =&gt; FAIL
     * Verifier.verify(new Object()).not().nulled()       =&gt; PASS
     * Verifier.verify(null).not().not().nulled()         =&gt; PASS
     * Verifier.verify(new Object()).not().not().nulled() =&gt; FAIL
     * </pre>
     *
     * @return A reference to this {@link CustomVerifier} for chaining purposes.
     */
    V not();

    /**
     * <p>
     * Verifies that the value {@literal null}.
     * </p>
     * <pre>
     * Verifier.verify(null).nulled()         =&gt; PASS
     * Verifier.verify(new Object()).nulled() =&gt; FAIL
     * </pre>
     *
     * @return A reference to this {@link CustomVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    V nulled();

    /**
     * <p>
     * Verifies that the value is the same the {@code other} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify(null).sameAs(null)            =&gt; PASS
     * Verifier.verify(null).sameAs(123)             =&gt; FAIL
     * Verifier.verify(123).sameAs(null)             =&gt; FAIL
     * Verifier.verify(123).sameAs(123)              =&gt; PASS
     * Verifier.verify(213).sameAs(new Integer(123)) =&gt; FAIL
     * </pre>
     *
     * @param other
     *         the object to compare against the value (may be {@literal null})
     * @return A reference to this {@link CustomVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #sameAs(Object, Object)
     */
    V sameAs(Object other);

    /**
     * <p>
     * Verifies that the value is the same as the {@code other} provided while allowing {@code other} to be given an
     * optional friendlier {@code name} for the {@link VerifierException} message in the event that one is thrown.
     * </p>
     * <p>
     * While {@code name} can be {@literal null} it is recommended instead to either use {@link #sameAs(Object)} or
     * pass {@code other} as {@code name} instead to get a better verification error message.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify(null, *).sameAs(null)            =&gt; PASS
     * Verifier.verify(null, *).sameAs(123)             =&gt; FAIL
     * Verifier.verify(123, *).sameAs(null)             =&gt; FAIL
     * Verifier.verify(123, *).sameAs(123)              =&gt; PASS
     * Verifier.verify(213, *).sameAs(new Integer(123)) =&gt; FAIL
     * </pre>
     *
     * @param other
     *         the object to compare against the value (may be {@literal null})
     * @param name
     *         the optional name used to represent {@code other} (may be {@literal null})
     * @return A reference to this {@link CustomVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     * @see #sameAs(Object)
     */
    V sameAs(Object other, Object name);

    /**
     * <p>
     * Verifies that the value is the same as any of the {@code others} provided.
     * </p>
     * <p>
     * {@literal null} references are handled gracefully without exceptions.
     * </p>
     * <pre>
     * Verifier.verify(*).sameAsAny()                                                       =&gt; FAIL
     * Verifier.verify(*).sameAsAny((Object[]) null)                                        =&gt; FAIL
     * Verifier.verify(null).sameAsAny(789, 456, null)                                      =&gt; PASS
     * Verifier.verify(null).sameAsAny(789, 456, 123)                                       =&gt; FAIL
     * Verifier.verify(123).sameAsAny(789, 456, null)                                       =&gt; FAIL
     * Verifier.verify(123).sameAsAny(789, 456, 123)                                        =&gt; PASS
     * Verifier.verify(123).sameAsAny(new Integer(789), new Integer(456), new Integer(123)) =&gt; FAIL
     * </pre>
     *
     * @param others
     *         the objects to compare against the value (may be {@literal null} or contain {@literal null} references)
     * @return A reference to this {@link CustomVerifier} for chaining purposes.
     * @throws VerifierException
     *         If the verification fails while not negated or passes while negated.
     */
    V sameAsAny(Object... others);

    /**
     * <p>
     * Verifies that the value passes the {@code assertion} provided.
     * </p>
     * <pre>
     * Verifier.verify(*).that(null)                                                     =&gt; FAIL
     * Verifier.verify(*).not().that(null)                                               =&gt; FAIL
     * Verifier.verify(null).that(value -&gt; "Mork".equals(value))                      =&gt; FAIL
     * Verifier.verify(new User("Mork")).that(user -&gt; "Mork".equals(user.getName()))  =&gt; PASS
     * Verifier.verify(new User("Mindy")).that(user -&gt; "Mork".equals(user.getName())) =&gt; FAIL
     * </pre>
     *
     * @param assertion
     *         the {@link VerifierAssertion} to be used to verify the value
     * @return A reference to this {@link CustomVerifier} for chaining purposes.
     * @throws VerifierException
     *         If {@code assertion} is {@literal null} or the verification fails while not negated or passes while
     *         negated.
     * @see #that(VerifierAssertion, MessageKey, Object...)
     * @see #that(VerifierAssertion, String, Object...)
     */
    V that(VerifierAssertion<T> assertion);

    /**
     * <p>
     * Verifies that the value passes the {@code assertion} provided while allowing an optional {@code key} and format
     * {@code args} to be specified to enhance the {@link VerifierException} message in the event that one is thrown.
     * </p>
     *
     * @param assertion
     *         the {@link VerifierAssertion} to be used to verify the value
     * @param key
     *         the key {@link MessageKey} which provides a more detailed localized explanation of what is being
     *         verified
     * @param args
     *         the optional format arguments which are only used to format the localized message
     * @return A reference to this {@link CustomVerifier} for chaining purposes.
     * @throws VerifierException
     *         If {@code assertion} is {@literal null} or the verification fails while not negated or passes while
     *         negated.
     * @see #that(VerifierAssertion)
     * @see #that(VerifierAssertion, String, Object...)
     * @since 0.2.0
     */
    V that(VerifierAssertion<T> assertion, MessageKey key, Object... args);

    /**
     * <p>
     * Verifies that the value passes the {@code assertion} provided while allowing an optional {@code message} and
     * format {@code args} to be specified to enhance the {@link VerifierException} message in the event that one is
     * thrown.
     * </p>
     *
     * @param assertion
     *         the {@link VerifierAssertion} to be used to verify the value
     * @param message
     *         the optional message which provides a more detailed explanation of what is being verified
     * @param args
     *         the optional format arguments which are only used to format {@code message}
     * @return A reference to this {@link CustomVerifier} for chaining purposes.
     * @throws VerifierException
     *         If {@code assertion} is {@literal null} or the verification fails while not negated or passes while
     *         negated.
     * @see #that(VerifierAssertion)
     * @see #that(VerifierAssertion, MessageKey, Object...)
     */
    V that(VerifierAssertion<T> assertion, String message, Object... args);

    /**
     * <p>
     * Returns the value being verified by this {@link CustomVerifier}.
     * </p>
     * <pre>
     * Verifier.verify(null).nulled().value()        = null
     * Verifier.verify("abc").not().nulled().value() = "abc"
     * </pre>
     *
     * @return The value.
     */
    T value();

    /**
     * <p>
     * Returns the verification information for this {@link CustomVerifier}.
     * </p>
     * <p>
     * This is not generally useful outside of the library but it is part of the API for extensions and easier
     * testability.
     * </p>
     *
     * @return The {@link Verification}.
     */
    Verification<T> verification();
}
