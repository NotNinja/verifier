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

import io.skelp.verifier.Verification;
import io.skelp.verifier.VerifierAssertion;
import io.skelp.verifier.VerifierException;
import io.skelp.verifier.message.ArrayFormatter;
import io.skelp.verifier.util.Function;

/**
 * TODO: Document
 *
 * @param <V>
 * @author Alasdair Mercer
 */
public class BaseTypeVerifier<V extends BaseTypeVerifier> implements TypeVerifier<V> {

  private static <T> boolean matchAny(final T[] inputs, final Function<Boolean, T> matcher) {
    for (final T input : inputs) {
      if (matcher.apply(input)) {
        return true;
      }
    }

    return false;
  }

  final Verification verification;

  /**
   * TODO: Document
   *
   * @param verification
   */
  public BaseTypeVerifier(final Verification verification) {
    this.verification = verification;
  }

  /**
   * TODO: Document
   *
   * @return
   */
  @SuppressWarnings("unchecked")
  V chain() {
    return (V) this;
  }

  @Override
  public V equalTo(final Object other) {
    return equalTo(other, other);
  }

  @Override
  public V equalTo(final Object other, final Object name) {
    final Object value = verification.getValue();
    final boolean result = other == null ? value == null : other.equals(value);

    verification.check(result, "be equal to '%s'", name);

    return chain();
  }

  @Override
  public V equalToAny(final Object... others) {
    final Object value = verification.getValue();
    final boolean result = matchAny(others, new Function<Boolean, Object>() {
      @Override
      public Boolean apply(final Object input) {
        return input == null ? value == null : input.equals(value);
      }
    });

    verification.check(result, "be equal to any %s", new ArrayFormatter(others));

    return chain();
  }

  @Override
  public V instanceOf(final Class<?> cls) {
    final boolean result = cls.isInstance(verification.getValue());

    verification.check(result, "be an instance of '%s'", cls);

    return chain();
  }

  @Override
  public V instanceOfAny(final Class<?>... classes) {
    final Object value = verification.getValue();
    final boolean result = matchAny(classes, new Function<Boolean, Class<?>>() {
      @Override
      public Boolean apply(final Class<?> input) {
        return input.isInstance(value);
      }
    });

    verification.check(result, "be an instance of any %s", new ArrayFormatter(classes));

    return chain();
  }

  @Override
  public V not() {
    verification.setNegated(!verification.isNegated());

    return chain();
  }

  @Override
  public V nulled() {
    final boolean result = verification.getValue() == null;

    verification.check(result, "be null");

    return chain();
  }

  @Override
  public V sameAs(final Object other) {
    return sameAs(other, other);
  }

  @Override
  public V sameAs(final Object other, final Object name) {
    final boolean result = verification.getValue() == other;

    verification.check(result, "be same as %s", name);

    return chain();
  }

  @Override
  public V sameAsAny(final Object... others) {
    final Object value = verification.getValue();
    final boolean result = matchAny(others, new Function<Boolean, Object>() {
      @Override
      public Boolean apply(final Object input) {
        return value == input;
      }
    });

    verification.check(result, "be same as any %s", new ArrayFormatter(others));

    return chain();
  }

  @Override
  public V that(final VerifierAssertion assertion) {
    return that(assertion, null);
  }

  @Override
  public V that(final VerifierAssertion assertion, final String message, final Object... args) {
    if (assertion == null) {
      throw new VerifierException("assertion must not be null");
    }

    final boolean result = assertion.verify(verification.getValue());

    verification.check(result, message, args);

    return chain();
  }
}
