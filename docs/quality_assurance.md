# Quality assurance concept

## Basics
1. Branching model (git-flow)
2. Issues and merge requests
3. Code style guides using [Google CodeStyle][1] enforces with gradle task and IntelliJ.  
4. Unit tests for through JUnit and Mockito.
5. Code metrics (test coverage via jacoco)
6. Play testing (functionality is by team on different physical machines)
7. Automatic testing  
  7.1 Continuous integration  
  7.2  Continuous deployment (via Docker -> [Image][2])

## Workflow
1. After release, pull the ``master`` branch back into ``dev`` to get the latest hotfixes.
2. Create your new feature branch from ``dev``.
3. Once you're done, create a merge request from your feature branch in to ``dev``.
4. Assign a colleague to review your code and complete the merge request.
5. Once all features for the next version are done, create a new ``release`` branch.
6. Now you need to update the version number, docs, write the changelog, play test, check the staging environment for bugs, etc.
7. Lastly merge the ``release`` branch into master and tag the commit with the version number.
8. Go back to step 1.


## Quality characteristics
### 1. Efficiency / Latency
To ensure a fluid gaming experience, the server and client need to be able to compute the data inside the game loop in a certain time frame. This is called the server- respectively the client tick rate. As outlined in out [milestone 1 presentation](../milestone/m1/slides/cs108-FS18-meilenstein1-gruppe11.pdf), we'd like to achieve a tick rate of 20 *Hz* per hosted game on the server and a tick rate of 60 *Hz* on the client (fixed and locked with framerate).  
The second performance feature we will monitor is the average roundtrip time of a package from client to server (both in the same local network). We estimate this should be no more than **150 *ms***. This can be tracked with an internal timer and a simple logging statement.

### 2. Reliability / Error tolerance
The software is rigorously tested with unit tests to cover all edge cases. As a metric we use the code coverage percentage (``"degree to which the source code of a program is executed when a particular test suite runs".`` [Wikipedia][6]). Initial goal shall be to achieve a coverage of close to 50 *%*.


## Merge requirements

| Branch          | Merge target        | Merge requirements         | Deployed to      |
| :-------------- | :------------------ | :------------------------- | :--------------- |
| ``master``    : | -                   | -                          | [Production][3]  |
| ``release`` :   | ``️master``         | ✔️ Tests, ✔️ Build, ✔️ Doc | [Staging][4]     |
| ``hotfix`` :    | ``release, master`` | ✔️ Tests, ✔️ Build, ✔️ Doc | -                |
| ``dev``       : | ``release``         | ✔️ Tests, ✔️ Build, ✔️ Doc | [Development][5] |
| ``feature`` :   | ``dev``             | ✔️ Tests, ❌ Build, ❌ Doc   | -                |


[1]: https://github.com/google/styleguide/blob/gh-pages/intellij-java-google-style.xml
[2]: https://hub.docker.com/u/subluminalthegame/
[3]: subluminal.tech:1790
[4]: staging.subluminal.tech:1790
[5]: dev.subluminal.tech:1790
[6]: https://en.wikipedia.org/w/index.php?title=Code_coverage&oldid=831669504
 