Akka HTTP Webpack Seed
======================

Tech Stack:
* [Akka HTTP](http://doc.akka.io/docs/akka-http/current/scala.html) (Scala 2.12)
* [RxJS 5](http://reactivex.io/rxjs/)
* [TypeScript 2](https://www.typescriptlang.org/)
* [Webpack 1](https://webpack.github.io/) within [GulpJS 3](http://gulpjs.com/)
* [WebSocket](https://www.w3.org/TR/websockets/)


# TODO

* Include example JavaSCript using Custom Elements v1 for frontend once available as polyfill  
* log4j 2 async logging
* ES2016 target for TypeScript compiler (UglifyJS does not support it yet)
* See other TODO Comments in source


# Development Environment

Terminal 1 - start Akka HTTP Server:

    sbt run

Terminal 2 - start WebPack Dev Server:

    npm run webpack-dev-server


# Release Command

    sbt clean assembly
