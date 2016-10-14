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
import java.util.Date;
import java.util.Locale;
import io.skelp.verifier.factory.DefaultVerifierFactoryProvider;
import io.skelp.verifier.factory.VerifierFactoryException;
import io.skelp.verifier.factory.VerifierFactoryProvider;
import io.skelp.verifier.type.ArrayVerifier;
import io.skelp.verifier.type.BigDecimalVerifier;
import io.skelp.verifier.type.BigIntegerVerifier;
import io.skelp.verifier.type.BooleanVerifier;
import io.skelp.verifier.type.ByteVerifier;
import io.skelp.verifier.type.CalendarVerifier;
import io.skelp.verifier.type.CharacterVerifier;
import io.skelp.verifier.type.ClassVerifier;
import io.skelp.verifier.type.ComparableVerifier;
import io.skelp.verifier.type.DateVerifier;
import io.skelp.verifier.type.DoubleVerifier;
import io.skelp.verifier.type.FloatVerifier;
import io.skelp.verifier.type.IntegerVerifier;
import io.skelp.verifier.type.LocaleVerifier;
import io.skelp.verifier.type.LongVerifier;
import io.skelp.verifier.type.ObjectVerifier;
import io.skelp.verifier.type.ShortVerifier;
import io.skelp.verifier.type.StringVerifier;
import io.skelp.verifier.type.ThrowableVerifier;
import io.skelp.verifier.verification.Verification;

/**
 * TODO: Document
 *
 * @author Alasdair Mercer
 */
public final class Verifier {

  private static VerifierFactoryProvider factoryProvider = new DefaultVerifierFactoryProvider();

  /**
   * TODO: Document
   *
   * @param value
   * @param <T>
   * @return
   * @throws VerifierFactoryException
   */
  public static <T> ArrayVerifier<T> verify(final T[] value) throws VerifierFactoryException {
    return verify(value, null);
  }

  /**
   * TODO: Document
   *
   * @param value
   * @param name
   * @param <T>
   * @return
   * @throws VerifierFactoryException
   */
  public static <T> ArrayVerifier<T> verify(final T[] value, final Object name) throws VerifierFactoryException {
    return new ArrayVerifier<>(createVerification(value, name));
  }

  /**
   * TODO: Document
   *
   * @param value
   * @return
   * @throws VerifierFactoryException
   */
  public static BigDecimalVerifier verify(final BigDecimal value) throws VerifierFactoryException {
    return verify(value, null);
  }

  /**
   * TODO: Document
   *
   * @param value
   * @param name
   * @return
   * @throws VerifierFactoryException
   */
  public static BigDecimalVerifier verify(final BigDecimal value, final Object name) throws VerifierFactoryException {
    return new BigDecimalVerifier(createVerification(value, name));
  }

  /**
   * TODO: Document
   *
   * @param value
   * @return
   * @throws VerifierFactoryException
   */
  public static BigIntegerVerifier verify(final BigInteger value) throws VerifierFactoryException {
    return verify(value, null);
  }

  /**
   * TODO: Document
   *
   * @param value
   * @param name
   * @return
   * @throws VerifierFactoryException
   */
  public static BigIntegerVerifier verify(final BigInteger value, final Object name) throws VerifierFactoryException {
    return new BigIntegerVerifier(createVerification(value, name));
  }

  /**
   * TODO: Document
   *
   * @param value
   * @return
   * @throws VerifierFactoryException
   */
  public static BooleanVerifier verify(final Boolean value) throws VerifierFactoryException {
    return verify(value, null);
  }

  /**
   * TODO: Document
   *
   * @param value
   * @param name
   * @return
   * @throws VerifierFactoryException
   */
  public static BooleanVerifier verify(final Boolean value, final Object name) throws VerifierFactoryException {
    return new BooleanVerifier(createVerification(value, name));
  }

  /**
   * TODO: Document
   *
   * @param value
   * @return
   * @throws VerifierFactoryException
   */
  public static ByteVerifier verify(final Byte value) throws VerifierFactoryException {
    return verify(value, null);
  }

  /**
   * TODO: Document
   *
   * @param value
   * @param name
   * @return
   * @throws VerifierFactoryException
   */
  public static ByteVerifier verify(final Byte value, final Object name) throws VerifierFactoryException {
    return new ByteVerifier(createVerification(value, name));
  }

  /**
   * TODO: Document
   *
   * @param value
   * @return
   * @throws VerifierFactoryException
   */
  public static CalendarVerifier verify(final Calendar value) throws VerifierFactoryException {
    return verify(value, null);
  }

  /**
   * TODO: Document
   *
   * @param value
   * @param name
   * @return
   * @throws VerifierFactoryException
   */
  public static CalendarVerifier verify(final Calendar value, final Object name) throws VerifierFactoryException {
    return new CalendarVerifier(createVerification(value, name));
  }

  /**
   * TODO: Document
   *
   * @param value
   * @return
   * @throws VerifierFactoryException
   */
  public static CharacterVerifier verify(final Character value) throws VerifierFactoryException {
    return verify(value, null);
  }

  /**
   * TODO: Document
   *
   * @param value
   * @param name
   * @return
   * @throws VerifierFactoryException
   */
  public static CharacterVerifier verify(final Character value, final Object name) throws VerifierFactoryException {
    return new CharacterVerifier(createVerification(value, name));
  }

  /**
   * TODO: Document
   *
   * @param value
   * @return
   * @throws VerifierFactoryException
   */
  public static ClassVerifier verify(final Class value) throws VerifierFactoryException {
    return verify(value, null);
  }

  /**
   * TODO: Document
   *
   * @param value
   * @param name
   * @return
   * @throws VerifierFactoryException
   */
  public static ClassVerifier verify(final Class value, final Object name) throws VerifierFactoryException {
    return new ClassVerifier(createVerification(value, name));
  }

  /**
   * TODO: Document
   *
   * @param value
   * @param <T>
   * @return
   * @throws VerifierFactoryException
   */
  public static <T extends Comparable<? super T>> ComparableVerifier<T> verify(final T value) throws VerifierFactoryException {
    return verify(value, null);
  }

  /**
   * TODO: Document
   *
   * @param value
   * @param name
   * @param <T>
   * @return
   * @throws VerifierFactoryException
   */
  public static <T extends Comparable<? super T>> ComparableVerifier<T> verify(final T value, final Object name) throws VerifierFactoryException {
    return new ComparableVerifier<>(createVerification(value, name));
  }

  /**
   * TODO: Document
   *
   * @param value
   * @return
   * @throws VerifierFactoryException
   */
  public static DateVerifier verify(final Date value) throws VerifierFactoryException {
    return verify(value, null);
  }

  /**
   * TODO: Document
   *
   * @param value
   * @param name
   * @return
   * @throws VerifierFactoryException
   */
  public static DateVerifier verify(final Date value, final Object name) throws VerifierFactoryException {
    return new DateVerifier(createVerification(value, name));
  }

  /**
   * TODO: Document
   *
   * @param value
   * @return
   * @throws VerifierFactoryException
   */
  public static DoubleVerifier verify(final Double value) throws VerifierFactoryException {
    return verify(value, null);
  }

  /**
   * TODO: Document
   *
   * @param value
   * @param name
   * @return
   * @throws VerifierFactoryException
   */
  public static DoubleVerifier verify(final Double value, final Object name) throws VerifierFactoryException {
    return new DoubleVerifier(createVerification(value, name));
  }

  /**
   * TODO: Document
   *
   * @param value
   * @return
   * @throws VerifierFactoryException
   */
  public static FloatVerifier verify(final Float value) throws VerifierFactoryException {
    return verify(value, null);
  }

  /**
   * TODO: Document
   *
   * @param value
   * @param name
   * @return
   * @throws VerifierFactoryException
   */
  public static FloatVerifier verify(final Float value, final Object name) throws VerifierFactoryException {
    return new FloatVerifier(createVerification(value, name));
  }

  /**
   * TODO: Document
   *
   * @param value
   * @return
   * @throws VerifierFactoryException
   */
  public static IntegerVerifier verify(final Integer value) throws VerifierFactoryException {
    return verify(value, null);
  }

  /**
   * TODO: Document
   *
   * @param value
   * @param name
   * @return
   * @throws VerifierFactoryException
   */
  public static IntegerVerifier verify(final Integer value, final Object name) throws VerifierFactoryException {
    return new IntegerVerifier(createVerification(value, name));
  }

  /**
   * TODO: Document
   *
   * @param value
   * @return
   * @throws VerifierFactoryException
   */
  public static LocaleVerifier verify(final Locale value) throws VerifierFactoryException {
    return verify(value, null);
  }

  /**
   * TODO: Document
   *
   * @param value
   * @param name
   * @return
   * @throws VerifierFactoryException
   */
  public static LocaleVerifier verify(final Locale value, final Object name) throws VerifierFactoryException {
    return new LocaleVerifier(createVerification(value, name));
  }

  /**
   * TODO: Document
   *
   * @param value
   * @return
   * @throws VerifierFactoryException
   */
  public static LongVerifier verify(final Long value) throws VerifierFactoryException {
    return verify(value, null);
  }

  /**
   * TODO: Document
   *
   * @param value
   * @param name
   * @return
   * @throws VerifierFactoryException
   */
  public static LongVerifier verify(final Long value, final Object name) throws VerifierFactoryException {
    return new LongVerifier(createVerification(value, name));
  }

  /**
   * TODO: Document
   *
   * @param value
   * @return
   * @throws VerifierFactoryException
   */
  public static ObjectVerifier verify(final Object value) throws VerifierFactoryException {
    return verify(value, null);
  }

  /**
   * TODO: Document
   *
   * @param value
   * @param name
   * @return
   * @throws VerifierFactoryException
   */
  public static ObjectVerifier verify(final Object value, final Object name) throws VerifierFactoryException {
    return new ObjectVerifier(createVerification(value, name));
  }

  /**
   * TODO: Document
   *
   * @param value
   * @return
   * @throws VerifierFactoryException
   */
  public static ShortVerifier verify(final Short value) throws VerifierFactoryException {
    return verify(value, null);
  }

  /**
   * TODO: Document
   *
   * @param value
   * @param name
   * @return
   * @throws VerifierFactoryException
   */
  public static ShortVerifier verify(final Short value, final Object name) throws VerifierFactoryException {
    return new ShortVerifier(createVerification(value, name));
  }

  /**
   * TODO: Document
   *
   * @param value
   * @return
   * @throws VerifierFactoryException
   */
  public static StringVerifier verify(final String value) throws VerifierFactoryException {
    return verify(value, null);
  }

  /**
   * TODO: Document
   *
   * @param value
   * @param name
   * @return
   * @throws VerifierFactoryException
   */
  public static StringVerifier verify(final String value, final Object name) throws VerifierFactoryException {
    return new StringVerifier(createVerification(value, name));
  }

  /**
   * TODO: Document
   *
   * @param value
   * @return
   * @throws VerifierFactoryException
   */
  public static ThrowableVerifier verify(final Throwable value) throws VerifierFactoryException {
    return verify(value, null);
  }

  /**
   * TODO: Document
   *
   * @param value
   * @param name
   * @return
   * @throws VerifierFactoryException
   */
  public static ThrowableVerifier verify(final Throwable value, final Object name) throws VerifierFactoryException {
    return new ThrowableVerifier(createVerification(value, name));
  }

  /**
   * TODO: Document
   *
   * @param value
   * @param name
   * @param cls
   * @param <T>
   * @param <V>
   * @return
   * @throws VerifierFactoryException
   */
  public static <T, V extends CustomVerifier<T, V>> V verify(final T value, final Object name, final Class<V> cls) throws VerifierFactoryException {
    return getFactoryProvider().getCustomVerifierFactory().create(cls, createVerification(value, name));
  }

  private static <T> Verification<T> createVerification(final T value, final Object name) throws VerifierFactoryException {
    return getFactoryProvider().getVerificationFactory().create(getFactoryProvider().getMessageFormatterFactory(), value, name);
  }

  /**
   * TODO: Document
   *
   * @return
   */
  public static VerifierFactoryProvider getFactoryProvider() {
    return factoryProvider;
  }

  /**
   * TODO: Document
   *
   * @param factoryProvider
   */
  public static void setFactoryProvider(final VerifierFactoryProvider factoryProvider) {
    Verifier.factoryProvider = factoryProvider != null ? factoryProvider : new DefaultVerifierFactoryProvider();
  }

  Verifier() {
  }
}
