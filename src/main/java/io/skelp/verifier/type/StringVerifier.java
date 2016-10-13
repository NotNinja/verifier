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

import java.util.regex.Pattern;
import io.skelp.verifier.Verification;
import io.skelp.verifier.VerifierException;
import io.skelp.verifier.util.Function;

/**
 * TODO: Document
 *
 * @param <V>
 * @author Alasdair Mercer
 */
public class StringVerifier<V extends StringVerifier> extends ComparableVerifier<V> implements TruthVerifier<V> {

  private static boolean matchCharacters(final String value, final Function<Boolean, Character> matcher) {
    if (value == null) {
      return false;
    }

    final int length = value.length();
    for (int i = 0; i < length; i++) {
      if (!matcher.apply(value.charAt(i))) {
        return false;
      }
    }

    return true;
  }

  private static boolean regionMatches(final String value, final boolean ignoreCase, final int offset, final CharSequence charSequence, final int start, final int length) {
    if (value == null) {
      return false;
    }

    if (charSequence instanceof String) {
      return value.regionMatches(ignoreCase, offset, (String) charSequence, start, length);
    }

    int index1 = offset;
    int index2 = start;
    int tempLength = length;

    while (tempLength-- > 0) {
      final char ch1 = value.charAt(index1++);
      final char ch2 = charSequence.charAt(index2++);

      if (ch1 == ch2) {
        continue;
      }

      if (!ignoreCase) {
        return false;
      }

      if (Character.toUpperCase(ch1) != Character.toUpperCase(ch2) && Character.toLowerCase(ch1) != Character.toLowerCase(ch2)) {
        return false;
      }
    }

    return true;
  }

  /**
   * TODO: Document
   *
   * @param verification
   */
  public StringVerifier(final Verification verification) {
    super(verification);
  }

  /**
   * TODO: Document
   *
   * @return
   * @throws VerifierException
   */
  public V alpha() {
    final String value = (String) verification.getValue();
    final boolean result = matchCharacters(value, new Function<Boolean, Character>() {
      @Override
      public Boolean apply(final Character character) {
        return Character.isLetter(character);
      }
    });

    verification.check(result, "contain only letters");

    return chain();
  }

  /**
   * TODO: Document
   *
   * @return
   * @throws VerifierException
   */
  public V alphaSpace() {
    final String value = (String) verification.getValue();
    final boolean result = matchCharacters(value, new Function<Boolean, Character>() {
      @Override
      public Boolean apply(final Character character) {
        return Character.isLetter(character) || character == ' ';
      }
    });

    verification.check(result, "contain only letters or space");

    return chain();
  }

  /**
   * TODO: Document
   *
   * @return
   * @throws VerifierException
   */
  public V alphanumeric() {
    final String value = (String) verification.getValue();
    final boolean result = matchCharacters(value, new Function<Boolean, Character>() {
      @Override
      public Boolean apply(final Character character) {
        return Character.isLetterOrDigit(character);
      }
    });

    verification.check(result, "contain only letters or digits");

    return chain();
  }

  /**
   * TODO: Document
   *
   * @return
   * @throws VerifierException
   */
  public V alphanumericSpace() {
    final String value = (String) verification.getValue();
    final boolean result = matchCharacters(value, new Function<Boolean, Character>() {
      @Override
      public Boolean apply(final Character character) {
        return Character.isLetterOrDigit(character) || character == ' ';
      }
    });

    verification.check(result, "contain only letters or digits or space");

    return chain();
  }

  /**
   * TODO: Document
   *
   * @return
   * @throws VerifierException
   */
  public V asciiPrintable() {
    final String value = (String) verification.getValue();
    final boolean result = matchCharacters(value, new Function<Boolean, Character>() {
      @Override
      public Boolean apply(final Character character) {
        return character >= 32 && character < 127;
      }
    });

    verification.check(result, "contain only ASCII printable characters");

    return chain();
  }

  /**
   * TODO: Document
   *
   * @return
   * @throws VerifierException
   */
  public V blank() {
    final String value = (String) verification.getValue();
    final boolean result = value == null || value.trim().isEmpty();

    verification.check(result, "be blank");

    return chain();
  }

  /**
   * TODO: Document
   *
   * @param charSequence
   * @return
   * @throws VerifierException
   */
  public V contain(final CharSequence charSequence) {
    final String value = (String) verification.getValue();
    final boolean result = value != null && value.contains(charSequence);

    verification.check(result, "contain '%s'", charSequence);

    return chain();
  }

  /**
   * TODO: Document
   *
   * @param charSequence
   * @return
   * @throws VerifierException
   */
  public V containIgnoreCase(final CharSequence charSequence) {
    final String value = (String) verification.getValue();
    boolean result = false;
    if (value != null) {
      final int length = charSequence.length();
      final int maximum = value.length() - length;
      for (int i = 0; i <= maximum; i++) {
        if (regionMatches(value, true, i, charSequence, 0, length)) {
          result = true;
          break;
        }
      }
    }

    verification.check(result, "contain '%s' (ignore case)", charSequence);

    return chain();
  }

  /**
   * TODO: Document
   *
   * @return
   * @throws VerifierException
   */
  public V empty() {
    final String value = (String) verification.getValue();
    final boolean result = value == null || value.isEmpty();

    verification.check(result, "be empty");

    return chain();
  }

  /**
   * TODO: Document
   *
   * @param charSequence
   * @return
   * @throws VerifierException
   */
  public V endWith(final CharSequence charSequence) {
    final String value = (String) verification.getValue();
    final boolean result = regionMatches(value, false, value.length() - charSequence.length(), charSequence, 0, charSequence.length());

    verification.check(result, "end with '%s'", charSequence);

    return chain();
  }

  /**
   * TODO: Document
   *
   * @param charSequence
   * @return
   * @throws VerifierException
   */
  public V endWithIgnoreCase(final CharSequence charSequence) {
    final String value = (String) verification.getValue();
    final boolean result = regionMatches(value, true, value.length() - charSequence.length(), charSequence, 0, charSequence.length());

    verification.check(result, "end with '%s' (ignore case)", charSequence);

    return chain();
  }

  /**
   * TODO: Document
   *
   * @param other
   * @return
   * @throws VerifierException
   */
  public V equalToIgnoreCase(final CharSequence other) {
    final String value = (String) verification.getValue();
    final boolean result = other == null ? value == null : regionMatches(value, true, 0, other, 0, value.length());

    verification.check(result, "be equal to '%s' (ignore case)", other);

    return chain();
  }

  @Override
  public V falsehood() {
    final String value = (String) verification.getValue();
    final boolean result = Boolean.FALSE.toString().equalsIgnoreCase(value);

    verification.check(result, "be false");

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
    final String value = (String) verification.getValue();
    final boolean result = value != null && value.length() == length;

    verification.check(result, "have length of '%d'", length);

    return chain();
  }

  /**
   * TODO: Document
   *
   * @return
   * @throws VerifierException
   */
  public V lowerCase() {
    final String value = (String) verification.getValue();
    final boolean result = matchCharacters(value, new Function<Boolean, Character>() {
      @Override
      public Boolean apply(final Character character) {
        return Character.isLowerCase(character);
      }
    });

    verification.check(result, "be all lower case");

    return chain();
  }

  /**
   * TODO: Document
   *
   * @param charSequence
   * @return
   * @throws VerifierException
   */
  public V match(final CharSequence charSequence) {
    final String value = (String) verification.getValue();
    final boolean result = value != null && value.matches(charSequence.toString());

    verification.check(result, "match '%s'", charSequence);

    return chain();
  }

  /**
   * TODO: Document
   *
   * @param pattern
   * @return
   * @throws VerifierException
   */
  public V match(final Pattern pattern) {
    final String value = (String) verification.getValue();
    final boolean result = value != null && pattern.matcher(value).matches();

    verification.check(result, "match '%s'", pattern);

    return chain();
  }

  /**
   * TODO: Document
   *
   * @return
   * @throws VerifierException
   */
  public V numeric() {
    final String value = (String) verification.getValue();
    final boolean result = matchCharacters(value, new Function<Boolean, Character>() {
      @Override
      public Boolean apply(final Character character) {
        return Character.isDigit(character);
      }
    });

    verification.check(result, "contain only digits");

    return chain();
  }

  /**
   * TODO: Document
   *
   * @return
   * @throws VerifierException
   */
  public V numericSpace() {
    final String value = (String) verification.getValue();
    final boolean result = matchCharacters(value, new Function<Boolean, Character>() {
      @Override
      public Boolean apply(final Character character) {
        return Character.isDigit(character) || character == ' ';
      }
    });

    verification.check(result, "contain only digits or space");

    return chain();
  }

  /**
   * TODO: Document
   *
   * @param charSequence
   * @return
   * @throws VerifierException
   */
  public V startWith(final CharSequence charSequence) {
    final String value = (String) verification.getValue();
    final boolean result = regionMatches(value, false, 0, charSequence, 0, charSequence.length());

    verification.check(result, "start with '%s'", charSequence);

    return chain();
  }

  /**
   * TODO: Document
   *
   * @param charSequence
   * @return
   * @throws VerifierException
   */
  public V startWithIgnoreCase(final CharSequence charSequence) {
    final String value = (String) verification.getValue();
    final boolean result = regionMatches(value, true, 0, charSequence, 0, charSequence.length());

    verification.check(result, "start with '%s' (ignore case)", charSequence);

    return chain();
  }

  @Override
  public V truth() {
    final String value = (String) verification.getValue();
    final boolean result = Boolean.TRUE.toString().equalsIgnoreCase(value);

    verification.check(result, "be true");

    return chain();
  }

  /**
   * TODO: Document
   *
   * @return
   * @throws VerifierException
   */
  public V upperCase() {
    final String value = (String) verification.getValue();
    final boolean result = matchCharacters(value, new Function<Boolean, Character>() {
      @Override
      public Boolean apply(final Character character) {
        return Character.isUpperCase(character);
      }
    });

    verification.check(result, "be all upper case");

    return chain();
  }

  /**
   * TODO: Document
   *
   * @return
   * @throws VerifierException
   */
  public V whitespace() {
    final String value = (String) verification.getValue();
    final boolean result = matchCharacters(value, new Function<Boolean, Character>() {
      @Override
      public Boolean apply(final Character character) {
        return Character.isWhitespace(character);
      }
    });
    verification.check(result, "contain only whitespace");

    return chain();
  }
}
