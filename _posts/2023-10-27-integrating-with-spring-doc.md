
Today, I was tasked with introducing api documentation for our new REST API, and since it is implemented in a spring boot app, the obvious is to look for an already implemented libraries that do it for us already.

I stumbled upon 2 options which are
1. Springfox https://github.com/springfox/springfox
2. Springdoc https://springdoc.org/

I started with fiddling with springfox and then realized that it doesn't work well with newer versions of spring boot, for example spring boot 3. It's github page has a lot of issues and the latest commit to master was 2020! Looks like its not maintained.

On the other hand, springdoc had great documentation to start with https://springdoc.org/#Introduction and since our service is reactive and written in kotlin, they support that too! You can have multiple api docs at once and also acquire the doc by running a gradle task.
