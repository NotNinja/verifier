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
import io.skelp.verifier.VerifierException;

/**
 * TODO: Document
 *
 * @param <V>
 * @author Alasdair Mercer
 */
public class ComparableVerifier<V extends ComparableVerifier> extends BaseTypeVerifier<V> {

  /**
   * TODO: Document
   *
   * @param verification
   */
  public ComparableVerifier(final Verification verification) {
    super(verification);
  }

  /**
   * TODO: Document
   *
   * @param start
   * @param end
   * @param <T>
   * @return
   * @throws VerifierException
   */
  public <T> V between(final T start, final T end) {
    return between(start, end, start, end);
  }

  /**
   * TODO: Document
   *
   * @param start
   * @param end
   * @param startName
   * @param endName
   * @param <T>
   * @return
   * @throws VerifierException
   */
  public <T> V between(final T start, final T end, final Object startName, final Object endName) {
    return between(start, ComparisonOperator.GREATER_THAN_OR_EQUAL_TO, startName, end, ComparisonOperator.LESS_THAN_OR_EQUAL_TO, endName, "be between '%s' and '%s' (inclusive)");
  }

  /**
   * TODO: Document
   *
   * @param start
   * @param startOperator
   * @param startName
   * @param end
   * @param endOperator
   * @param endName
   * @param message
   * @param <T>
   * @return
   * @throws VerifierException
   */
  <T> V between(final T start, final ComparisonOperator startOperator, final Object startName, final T end, final ComparisonOperator endOperator, final Object endName, final String message) {
    @SuppressWarnings("unchecked")
    final Comparable<T> value = (Comparable<T>) verification.getValue();
    final boolean result = value != null && startOperator.compare(value.compareTo(start)) && endOperator.compare(value.compareTo(end));

    verification.check(result, message, startName, endName);

    return chain();
  }

  /**
   * TODO: Document
   *
   * @param start
   * @param end
   * @param <T>
   * @return
   * @throws VerifierException
   */
  public <T> V betweenExclusive(final T start, final T end) {
    return betweenExclusive(start, end, start, end);
  }

  /**
   * TODO: Document
   *
   * @param start
   * @param end
   * @param startName
   * @param endName
   * @param <T>
   * @return
   * @throws VerifierException
   */
  public <T> V betweenExclusive(final T start, final T end, final Object startName, final Object endName) {
    return between(start, ComparisonOperator.GREATER_THAN, startName, end, ComparisonOperator.LESS_THAN, endName, "be between '%s' and '%s' (exclusive)");
  }

  /**
   * TODO: Document
   *
   * @param operator
   * @param other
   * @param name
   * @param message
   * @param <T>
   * @return
   * @throws VerifierException
   */
  <T> V comparesTo(final ComparisonOperator operator, final T other, final Object name, final String message) {
    @SuppressWarnings("unchecked")
    final Comparable<T> value = (Comparable<T>) verification.getValue();
    final boolean result = value != null && operator.compare(value.compareTo(other));

    verification.check(result, message, name);

    return chain();
  }

  /**
   * TODO: Document
   *
   * @param other
   * @param <T>
   * @return
   * @throws VerifierException
   */
  public <T> V greaterThan(final T other) {
    return greaterThan(other, other);
  }

  /**
   * TODO: Document
   *
   * @param other
   * @param name
   * @param <T>
   * @return
   * @throws VerifierException
   */
  public <T> V greaterThan(final T other, final Object name) {
    return comparesTo(ComparisonOperator.GREATER_THAN, other, name, "be greater than '%s'");
  }

  /**
   * TODO: Document
   *
   * @param other
   * @param <T>
   * @return
   * @throws VerifierException
   */
  public <T> V greaterThanOrEqualTo(final T other) {
    return greaterThanOrEqualTo(other, other);
  }

  /**
   * TODO: Document
   *
   * @param other
   * @param name
   * @param <T>
   * @return
   * @throws VerifierException
   */
  public <T> V greaterThanOrEqualTo(final T other, final Object name) {
    return comparesTo(ComparisonOperator.GREATER_THAN_OR_EQUAL_TO, other, name, "be greater than or equal to '%s'");
  }

  /**
   * TODO: Document
   *
   * @param other
   * @param <T>
   * @return
   * @throws VerifierException
   */
  public <T> V lessThan(final T other) {
    return lessThan(other, other);
  }

  /**
   * TODO: Document
   *
   * @param other
   * @param name
   * @param <T>
   * @return
   * @throws VerifierException
   */
  public <T> V lessThan(final T other, final Object name) {
    return comparesTo(ComparisonOperator.LESS_THAN, other, name, "be less than '%s'");
  }

  /**
   * TODO: Document
   *
   * @param other
   * @param <T>
   * @return
   * @throws VerifierException
   */
  public <T> V lessThanOrEqualTo(final T other) {
    return lessThanOrEqualTo(other, other);
  }

  /**
   * TODO: Document
   *
   * @param other
   * @param name
   * @param <T>
   * @return
   * @throws VerifierException
   */
  public <T> V lessThanOrEqualTo(final T other, final Object name) {
    return comparesTo(ComparisonOperator.LESS_THAN_OR_EQUAL_TO, other, name, "be less than or equal to '%s'");
  }

  /**
   * TODO: Document
   */
  protected interface Comparison {

    /**
     * TODO: Document
     *
     * @param result
     * @return
     */
    boolean compare(int result);
  }

  /**
   * TODO: Document
   */
  protected enum ComparisonOperator implements Comparison {

    GREATER_THAN() {
      @Override
      public boolean compare(final int result) {
        return result > 0;
      }
    },
    GREATER_THAN_OR_EQUAL_TO() {
      @Override
      public boolean compare(final int result) {
        return result >= 0;
      }
    },
    LESS_THAN() {
      @Override
      public boolean compare(final int result) {
        return result < 0;
      }
    },
    LESS_THAN_OR_EQUAL_TO() {
      @Override
      public boolean compare(final int result) {
        return result >= 0;
      }
    }
  }
}
