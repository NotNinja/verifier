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

import java.util.Arrays;
import io.skelp.verifier.Verification;
import io.skelp.verifier.VerifierException;

/**
 * TODO: Document
 *
 * @param <T>
 * @param <V>
 * @author Alasdair Mercer
 */
public class ArrayVerifier<T, V extends ArrayVerifier<T, V>> extends BaseTypeVerifier<T[], V> {

  /**
   * TODO: Document
   *
   * @param verification
   */
  public ArrayVerifier(final Verification<T[]> verification) {
    super(verification);
  }

  /**
   * TODO: Document
   *
   * @param item
   * @return
   * @throws VerifierException
   */
  public V contain(final T item) {
    final T[] value = verification.getValue();
    final boolean result = value != null && Arrays.asList(value).contains(item);

    verification.check(result, "contain '%s'", item);

    return chain();
  }

  /**
   * TODO: Document
   *
   * @return
   * @throws VerifierException
   */
  public V empty() {
    final T[] value = verification.getValue();
    final boolean result = value == null || value.length == 0;

    verification.check(result, "be empty");

    return chain();
  }

  /**
   * TODO: Document
   *
   * @param length
   * @return
   * @throws VerifierException
   */
  public V length(final int length) {
    final T[] value = verification.getValue();
    final boolean result = value != null && value.length == length;

    verification.check(result, "have length of '%d'", length);

    return chain();
  }
}
