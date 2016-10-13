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

import java.util.ArrayList;
import java.util.List;
import io.skelp.verifier.Verification;
import io.skelp.verifier.VerifierException;

/**
 * TODO: Document
 *
 * @param <V>
 * @author Alasdair Mercer
 */
public class ThrowableVerifier<V extends ThrowableVerifier> extends BaseTypeVerifier<V> {

  private static List<Throwable> getThrowables(Throwable throwable) {
    final List<Throwable> throwables = new ArrayList<>();
    while (throwable != null && !throwables.contains(throwable)) {
      throwables.add(throwable);
      throwable = throwable.getCause();
    }

    return throwables;
  }

  /**
   * TODO: Document
   *
   * @param verification
   */
  public ThrowableVerifier(final Verification verification) {
    super(verification);
  }

  /**
   * TODO: Document
   *
   * @return
   * @throws VerifierException
   */
  public V checked() {
    final Throwable value = (Throwable) verification.getValue();
    final boolean result = value != null && !(value instanceof RuntimeException);
    verification.check(result, "checked");

    return chain();
  }

  /**
   * TODO: Document
   *
   * @param type
   * @return
   * @throws VerifierException
   */
  public V causedBy(final Class<?> type) {
    final Throwable value = (Throwable) verification.getValue();
    boolean result = false;
    for (final Throwable throwable : getThrowables(value)) {
      if (type.isAssignableFrom(throwable.getClass())) {
        result = true;
        break;
      }
    }
    verification.check(result, "been caused by %s", type);

    return chain();
  }

  /**
   * TODO: Document
   *
   * @param cause
   * @return
   * @throws VerifierException
   */
  public V causedBy(final Throwable cause) {
    final Throwable value = (Throwable) verification.getValue();
    final boolean result = getThrowables(value).contains(cause);
    verification.check(result, "been caused by %s", cause);

    return chain();
  }

  /**
   * TODO: Document
   *
   * @return
   * @throws VerifierException
   */
  public V unchecked() {
    final Throwable value = (Throwable) verification.getValue();
    final boolean result = value instanceof RuntimeException;
    verification.check(result, "unchecked");

    return chain();
  }
}
