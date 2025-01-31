# Anigif Kotlin Multiplatform
This library serves as a common ground for various [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html) utitilies. The functionality is originally based on work done in [spilpind/spilpind-app](https://github.com/spilpind/spilpind-app), but I found that I wanted to share that functionality among other projects as well and created this project.

It should be possible to use the library out of the box, but can hopefully also just serve as an inspiration to others.

## Setup
The project is currently only published to GitHub Packages, so the setup in a new project can be slightly tedious - if requested I might consider publishing it elsewhere as well.

How to add the the repository is explained in the [official docs](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-gradle-registry#using-a-published-package) - the `url` parameter should be set to `https://maven.pkg.github.com/anigif/anigif-kmp`.

After the repository has been registered, the dependency can be added as usual - for instace `implementation("dk.anigif:anigif-kmp:<version>")`, where `<version>` is replaced by a [release version](https://github.com/Anigif/anigif-kmp/releases) (remove the prepended `v`- for instance `0.2.0`).
