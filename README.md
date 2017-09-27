Demo Code for adaptTo() 2017 talk
=================================

This repository contains the demo code for my talk [Developers' Dues For Successful DevOps](https://adapt.to/2017/en/schedule/developers--dues-for-successful-devops.html) at the __adaptTo() 2017__ conference.

The code is designed to demonstrate the socket timeout configuration of an `HttpClient` instance provided by the [`httpclient-configuration-support`](https://github.com/code-distillery/httpclient-configuration-support) bundle.
  
The bundle provides two servlets, one that is slow (using a random delay), and another one that uses an injected `HttpClient` instance to call the slow servlet (on http://localhost:4502). It renders the content retrieved from the slow servlet and the time it took. If there is an exception during the call, the exception is rendered instead of the returned content.

Demo
----

1. Install an AEM 6.3 author instance on port 4502.
2. Install the [httpclient-configuration-support version 1.1.0](https://repo1.maven.org/maven2/net/distilledcode/httpclient-configuration-support/1.1.0/) bundle.
3. Install this example bundle by cloning the repo and running `mvn package -P installBundle`
4. Call `http://localhost:4502/bin/adaptTo/2017` multiple times in order to see the how long it takes (should be between 500 and 1500ms).
5. Set a socket timeout of `1000` using the [Apache HTTP Components Default HTTP Client Configuration](http://localhost:4502/system/console/configMgr/net.distilledcode.httpclient.Configuration.default)
6. Call `http://localhost:4502/bin/adaptTo/2017` multiple times and verify a SocketTimeoutException is rendered in the cases where a duration of 1000ms is exceeded.
7. (optional) refer to the documentation in the README of [`httpclient-configuration-support`](https://github.com/code-distillery/httpclient-configuration-support) in order to experiment with named configurations.

Disclaimer
----------

This is not production ready code and is probably not very useful except for the demo. Also, it hard-codes a reference to `http://localhost:4502`.  
   