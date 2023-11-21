---
title: "OpenAPI documentation for your spring boot service"
excerpt_separator: "<!--more-->"
categories:
  - Blog
tags:
  - OpenAPI
  - SpringBoot
---

OpenAPI Specification is a good way to define and describe your API for anyone who is interested in using your apis. So, I found a couple of open source implementation options on the internet:  

1. [Springfox](https://github.com/springfox/springfox) tldr -> unmaintained
2. [Springdoc](https://springdoc.org/) tldr -> good

After checking out springfox, it doesn't work well with newer versions of spring boot, for example spring boot 3. Its github page has a lot of issues and the latest commit to master was 2020! Looks like its not maintained.

On the other hand, Springdoc had great documentation to start with [SpringDoc](https://springdoc.org/#Introduction) and they support reactive and kotlin too.

It's possible to have multiple api docs at once and provide a gradle task to generate your doc, albeit it's quite quirky. Basically it boots up the app to query the configured endpoint doc and created the file for you.
