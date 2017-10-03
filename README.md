# Verifier

[Verifier](https://github.com/NotNinja/verifier) is a Java library for validation which concentrates on providing a
simple API with useful (and readable!) error messages, all while being highly configurable so that it's useful in your
application code.

[![Build](https://img.shields.io/travis/NotNinja/verifier/develop.svg)](https://travis-ci.org/NotNinja/verifier)
[![Coverage](https://img.shields.io/codecov/c/github/NotNinja/verifier/develop.svg)](https://codecov.io/gh/NotNinja/verifier)
[![Translations](https://d322cqt584bo4o.cloudfront.net/verifier/localized.svg)](https://crowdin.com/project/verifier)
[![JavaDoc](https://www.javadoc.io/badge/org.notninja/verifier.svg)](https://www.javadoc.io/doc/org.notninja/verifier)
[![License](https://img.shields.io/github/license/NotNinja/verifier.svg)](https://github.com/NotNinja/verifier/blob/master/LICENSE.md)
[![Maven Central](https://img.shields.io/maven-central/v/org.notninja/verifier.svg)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22org.notninja%22%20AND%20a%3A%22verifier%22)

* [Install](#install)
* [API](#api)
* [Bugs](#bugs)
* [Contributors](#contributors)
* [License](#license)

## Install

To install Verifier, simply add it as a dependency to your project:

**Maven:**
``` xml
<dependency>
    <groupId>org.notninja</groupId>
    <artifactId>verifier</artifactId>
    <version>0.3.0</version>
</dependency>
```

**Gradle:**
``` groovy
compile 'org.notninja:verifier:0.3.0'
```

That's it! You'll need to have Java 8 or above though.

## API

Verifier offers validation methods for a lot of standard Java data types:

``` java
package com.example.form;

import org.notninja.verifier.Verifier;

public class LoginForm implements Form {

    UserService userService;

    @Override
    public void handle(Map<String, String> data) {
        Verifier.verify(data)
            .containAllKeys("username", "password");
            .and(data.get("username"), "username")
                .not().blank();
            .and(data.get("password"), "password")
                .not().empty()
                .alphanumeric();

        userService.login(data);
    }
}
```

However, you can also provide implementations of `CustomVerifier` to support more specific use cases instead of just
data types.

``` java
package com.example.form;

import com.example.verifier.PasswordVerifier;

import org.notninja.verifier.Verifier;

public class RegistrationForm implements Form {

    UserService userService;

    @Override
    public void handle(Map<String, String> data) {
        Verifier.verify(data)
            .containAllKeys("username", "password");
            .and(data.get("username"), "username")
                .not().blank()
                .that((value) -> userService.isAvailable(value));
            .and(data.get("password"), "password", PasswordVerifier.class)
                .not().nulled()
                .strong();

        userService.register(data);
    }
}
```

The best way to learn about the API is to simply use it and explore. It's designed to be very natural and simple. Each
verification method is documented with examples to help explain exactly what's being verified.

Here's a list of the data types supported by Verifier already:

* Array
* BigDecimal
* BigInteger
* Boolean
* Byte
* Calendar
* Character
* Class
* Collection
* Comparable
* Date
* Double
* Float
* Integer
* Locale
* Long
* Map
* Object
* Short
* String
* Throwable

If a data type is missing that you'd like to see supported by Verifier, please take a look at the
[Contributors](#contributors) section below.

## Bugs

If you have any problems with Verifier or would like to see changes currently in development you can do so
[here](https://github.com/NotNinja/verifier/issues).

## Contributors

If you want to contribute, you're a legend! Information on how you can do so can be found in
[CONTRIBUTING.md](https://github.com/NotNinja/verifier/blob/master/CONTRIBUTING.md). We want your suggestions and pull
requests!

A list of Verifier contributors can be found in[AUTHORS.md](https://github.com/NotNinja/verifier/blob/master/AUTHORS.md).

## License

See [LICENSE.md](https://github.com/NotNinja/verifier/raw/master/LICENSE.md) for more information on our MIT license.

[![Copyright !ninja](https://cdn.rawgit.com/NotNinja/branding/master/assets/copyright/base/not-ninja-copyright-186x25.png)](https://not.ninja)
