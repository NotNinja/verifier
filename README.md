# Verifier

[Verifier](https://github.com/Skelp/verifier) is a Java library for validation which concentrates on providing a simple
API with useful (and readable!) error messages, all while being highly configurable so that it's useful in your
application code.

[![Chat](https://img.shields.io/gitter/room/skelpuk/verifier.svg?style=flat-square)](https://gitter.im/skelpuk/verifier)
[![Build](https://img.shields.io/travis/Skelp/verifier/develop.svg?style=flat-square)](https://travis-ci.org/Skelp/verifier)
[![License](https://img.shields.io/github/license/Skelp/verifier.svg?style=flat-square)](https://github.com/Skelp/verifier/blob/master/LICENSE.md)
[![Maven Central](https://img.shields.io/maven-central/v/io.skelp/verifier.svg?style=flat-square)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22io.skelp%22%20AND%20a%3A%22verifier%22)

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
    <groupId>io.skelp</groupId>
    <artifactId>verifier</artifactId>
    <version>0.2.0-SNAPSHOT</version>
</dependency>
```

**Grails:**
``` groovy
compile 'io.skelp:verifier:0.2.0-SNAPSHOT'
```

That's it! You'll need to have Java 8 or above though.

## API

Verifier offers validation methods for a lot of standard Java data types:

``` java
package com.example.form;

import io.skelp.verifier.Verifier;

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

import io.skelp.verifier.Verifier;

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
[here](https://github.com/Skelp/verifier/issues).

## Contributors

If you want to contribute, you're a legend! Information on how you can do so can be found in
[CONTRIBUTING.md](https://github.com/Skelp/verifier/blob/master/CONTRIBUTING.md). We want your suggestions and pull
requests!

A list of Verifier contributors can be found in [AUTHORS.md](https://github.com/Skelp/verifier/blob/master/AUTHORS.md).

## License

See [LICENSE.md](https://github.com/Skelp/verifier/raw/master/LICENSE.md) for more information on our MIT license.

© 2017 [Skelp](https://skelp.io)
<img align="right" width="16" height="16" src="https://cdn.rawgit.com/Skelp/skelp-branding/master/assets/logo/base/skelp-logo-16x16.png">
