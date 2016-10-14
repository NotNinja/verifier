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
import io.skelp.verifier.message.DefaultMessageFormatter;
import io.skelp.verifier.message.MessageFormatter;
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
import io.skelp.verifier.verification.DefaultVerification;
import io.skelp.verifier.verification.Verification;

/**
 * TODO: Document
 *
 * @author Alasdair Mercer
 */
public final class Verifier {

  private static MessageFormatter defaultMessageFormatter = new DefaultMessageFormatter();

  /**
   * TODO: Document
   *
   * @param value
   * @param <T>
   * @return
   */
  public static <T> ArrayVerifier<T> verify(final T[] value) {
    return verify(value, null);
  }

  /**
   * TODO: Document
   *
   * @param value
   * @param name
   * @param <T>
   * @return
   */
  public static <T> ArrayVerifier<T> verify(final T[] value, final Object name) {
    return new ArrayVerifier<>(createVerification(value, name, null));
  }

  /**
   * TODO: Document
   *
   * @param value
   * @return
   */
  public static BigDecimalVerifier verify(final BigDecimal value) {
    return verify(value, null);
  }

  /**
   * TODO: Document
   *
   * @param value
   * @param name
   * @return
   */
  public static BigDecimalVerifier verify(final BigDecimal value, final Object name) {
    return new BigDecimalVerifier(createVerification(value, name, null));
  }

  /**
   * TODO: Document
   *
   * @param value
   * @return
   */
  public static BigIntegerVerifier verify(final BigInteger value) {
    return verify(value, null);
  }

  /**
   * TODO: Document
   *
   * @param value
   * @param name
   * @return
   */
  public static BigIntegerVerifier verify(final BigInteger value, final Object name) {
    return new BigIntegerVerifier(createVerification(value, name, null));
  }

  /**
   * TODO: Document
   *
   * @param value
   * @return
   */
  public static BooleanVerifier verify(final Boolean value) {
    return verify(value, null);
  }

  /**
   * TODO: Document
   *
   * @param value
   * @param name
   * @return
   */
  public static BooleanVerifier verify(final Boolean value, final Object name) {
    return new BooleanVerifier(createVerification(value, name, null));
  }

  /**
   * TODO: Document
   *
   * @param value
   * @return
   */
  public static ByteVerifier verify(final Byte value) {
    return verify(value, null);
  }

  /**
   * TODO: Document
   *
   * @param value
   * @param name
   * @return
   */
  public static ByteVerifier verify(final Byte value, final Object name) {
    return new ByteVerifier(createVerification(value, name, null));
  }

  /**
   * TODO: Document
   *
   * @param value
   * @return
   */
  public static CalendarVerifier verify(final Calendar value) {
    return verify(value, null);
  }

  /**
   * TODO: Document
   *
   * @param value
   * @param name
   * @return
   */
  public static CalendarVerifier verify(final Calendar value, final Object name) {
    return new CalendarVerifier(createVerification(value, name, null));
  }

  /**
   * TODO: Document
   *
   * @param value
   * @return
   */
  public static CharacterVerifier verify(final Character value) {
    return verify(value, null);
  }

  /**
   * TODO: Document
   *
   * @param value
   * @param name
   * @return
   */
  public static CharacterVerifier verify(final Character value, final Object name) {
    return new CharacterVerifier(createVerification(value, name, null));
  }

  /**
   * TODO: Document
   *
   * @param value
   * @return
   */
  public static ClassVerifier verify(final Class value) {
    return verify(value, null);
  }

  /**
   * TODO: Document
   *
   * @param value
   * @param name
   * @return
   */
  public static ClassVerifier verify(final Class value, final Object name) {
    return new ClassVerifier(createVerification(value, name, null));
  }

  /**
   * TODO: Document
   *
   * @param value
   * @param <T>
   * @return
   */
  public static <T extends Comparable<? super T>> ComparableVerifier<T> verify(final T value) {
    return verify(value, null);
  }

  /**
   * TODO: Document
   *
   * @param value
   * @param name
   * @param <T>
   * @return
   */
  public static <T extends Comparable<? super T>> ComparableVerifier<T> verify(final T value, final Object name) {
    return new ComparableVerifier<>(createVerification(value, name, null));
  }

  /**
   * TODO: Document
   *
   * @param value
   * @return
   */
  public static DateVerifier verify(final Date value) {
    return verify(value, null);
  }

  /**
   * TODO: Document
   *
   * @param value
   * @param name
   * @return
   */
  public static DateVerifier verify(final Date value, final Object name) {
    return new DateVerifier(createVerification(value, name, null));
  }

  /**
   * TODO: Document
   *
   * @param value
   * @return
   */
  public static DoubleVerifier verify(final Double value) {
    return verify(value, null);
  }

  /**
   * TODO: Document
   *
   * @param value
   * @param name
   * @return
   */
  public static DoubleVerifier verify(final Double value, final Object name) {
    return new DoubleVerifier(createVerification(value, name, null));
  }

  /**
   * TODO: Document
   *
   * @param value
   * @return
   */
  public static FloatVerifier verify(final Float value) {
    return verify(value, null);
  }

  /**
   * TODO: Document
   *
   * @param value
   * @param name
   * @return
   */
  public static FloatVerifier verify(final Float value, final Object name) {
    return new FloatVerifier(createVerification(value, name, null));
  }

  /**
   * TODO: Document
   *
   * @param value
   * @return
   */
  public static IntegerVerifier verify(final Integer value) {
    return verify(value, null);
  }

  /**
   * TODO: Document
   *
   * @param value
   * @param name
   * @return
   */
  public static IntegerVerifier verify(final Integer value, final Object name) {
    return new IntegerVerifier(createVerification(value, name, null));
  }

  /**
   * TODO: Document
   *
   * @param value
   * @return
   */
  public static LocaleVerifier verify(final Locale value) {
    return verify(value, null);
  }

  /**
   * TODO: Document
   *
   * @param value
   * @param name
   * @return
   */
  public static LocaleVerifier verify(final Locale value, final Object name) {
    return new LocaleVerifier(createVerification(value, name, null));
  }

  /**
   * TODO: Document
   *
   * @param value
   * @return
   */
  public static LongVerifier verify(final Long value) {
    return verify(value, null);
  }

  /**
   * TODO: Document
   *
   * @param value
   * @param name
   * @return
   */
  public static LongVerifier verify(final Long value, final Object name) {
    return new LongVerifier(createVerification(value, name, null));
  }

  /**
   * TODO: Document
   *
   * @param value
   * @return
   */
  public static ObjectVerifier verify(final Object value) {
    return verify(value, null);
  }

  /**
   * TODO: Document
   *
   * @param value
   * @param name
   * @return
   */
  public static ObjectVerifier verify(final Object value, final Object name) {
    return new ObjectVerifier(createVerification(value, name, null));
  }

  /**
   * TODO: Document
   *
   * @param value
   * @return
   */
  public static ShortVerifier verify(final Short value) {
    return verify(value, null);
  }

  /**
   * TODO: Document
   *
   * @param value
   * @param name
   * @return
   */
  public static ShortVerifier verify(final Short value, final Object name) {
    return new ShortVerifier(createVerification(value, name, null));
  }

  /**
   * TODO: Document
   *
   * @param value
   * @return
   */
  public static StringVerifier verify(final String value) {
    return verify(value, null);
  }

  /**
   * TODO: Document
   *
   * @param value
   * @param name
   * @return
   */
  public static StringVerifier verify(final String value, final Object name) {
    return new StringVerifier(createVerification(value, name, null));
  }

  /**
   * TODO: Document
   *
   * @param value
   * @return
   */
  public static ThrowableVerifier verify(final Throwable value) {
    return verify(value, null);
  }

  /**
   * TODO: Document
   *
   * @param value
   * @param name
   * @return
   */
  public static ThrowableVerifier verify(final Throwable value, final Object name) {
    return new ThrowableVerifier(createVerification(value, name, null));
  }

  /**
   * TODO: Document
   *
   * @param value
   * @param name
   * @param verifierType
   * @param <T>
   * @param <V>
   * @return
   * @throws VerifierException
   */
  public static <T, V extends CustomVerifier<T, V>> V verify(final T value, final Object name, final Class<V> verifierType) {
    return verify(value, name, verifierType, null);
  }

  /**
   * TODO: Document
   *
   * @param value
   * @param name
   * @param verifierType
   * @param messageFormatter
   * @param <T>
   * @param <V>
   * @return
   * @throws VerifierException
   */
  public static <T, V extends CustomVerifier<T, V>> V verify(final T value, final Object name, final Class<V> verifierType, final MessageFormatter messageFormatter) {
    if (verifierType == null) {
      throw new VerifierException("type must not be null");
    }

    final V verifier;
    try {
      verifier = verifierType.getConstructor(Verification.class).newInstance(createVerification(value, name, messageFormatter));
    } catch (ReflectiveOperationException e) {
      throw new VerifierException("Unable to instantiate TypeVerifier", e);
    }

    return verifier;
  }

  private static <T> Verification<T> createVerification(final T value, final Object name, final MessageFormatter messageFormatter) {
    return new DefaultVerification<>(messageFormatter != null ? messageFormatter : defaultMessageFormatter, value, name);
  }

  /**
   * TODO: Document
   *
   * @return
   */
  public static MessageFormatter getDefaultMessageFormatter() {
    return defaultMessageFormatter;
  }

  /**
   * TODO: Document
   *
   * @param messageFormatter
   */
  public static void setDefaultMessageFormatter(final MessageFormatter messageFormatter) {
    defaultMessageFormatter = messageFormatter != null ? messageFormatter : new DefaultMessageFormatter();
  }

  Verifier() {
  }
}
