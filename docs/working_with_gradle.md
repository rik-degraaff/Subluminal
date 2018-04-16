# Working with gradle
Building a good gradle build pipeline for a project can the tricky. Here is some advice and tips on how we did it.

## Task dependency
This image shows the how the default tasks of the java plugin are linked together.
![Gradle task dependencies](https://docs.gradle.org/current/userguide/img/javaPluginTasks.png)

## Commands
- Use ``gradle build -x test -x check --continuous`` during development. This regenerates the jar file automatically when the sources change.
- ``gradle jacocoTestReport`` to generate the test coverage report.
- With ``gradle javadocJar`` and ``gradle sourceJar`` bundled versions of the docs and the source are created in the ``app/build/libs/`` folder.
- Use ``gradle javaDoc`` to output the javadocs in the ``docs/`` folder.