---
layout: post
title:  "Replace springfox with a better alternative"
---

OpenAPI is a good way to define and describe your API. I found a couple of open source implementation options on the internet:  

1. [Springfox](https://github.com/springfox/springfox)
2. [Springdoc](https://springdoc.org/)

After attempting using springfox, I realized that it doesn't work well with newer versions of spring boot, for example spring boot 3. It's github page has a lot of issues and the latest commit to master was 2020! Looks like its not maintained.

Springdoc had great documentation to start with [SpringDoc](https://springdoc.org/#Introduction) and they support reactive and kotlin too.

You can have multiple api docs at once and provide a gradle task to generate your doc.
