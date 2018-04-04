# Contributing
## Commit message format
This is important, so the python script can generate a changelog automatically. If you don't want your commit to show up in the changelog, don't use one of the action keywords (new, fix, chg) at the beginning of your commit message.  
``ACTION: [AUDIENCE:] COMMIT_MSG [!TAG ...]`` (See reference: [gitchangelog config][1])

## Branch name format
Please use branch names according to the table below. Whenever possible delete the source branch after merging.

| Type        | Naming convention       |
| ----------- | ------------------------ |
| Master      | master                   |
| Development | dev **(default)**        |
| Features    | ft-[a-zA-Z0-9_]+-${name} |
| Hotfixes    | hf-[a-zA-Z0-9_]+-${name} |
| Releases    | rl-[0-9]+.[0-9]+.[0-9]+  |

## Version bumping
Given a version number {MAJOR}.{MINOR}.{PATCH}, increment the:

1. MAJOR version in the dev branch after a release (reset MINOR and PATCH to 0),
2. MINOR version when you merge a feature branch into dev (reset PATCH to 0), and
3. PATCH version when you merge a hotfix branch.

Additional labels for pre-release and build metadata are available as extensions to the {MAJOR}.{MINOR}.{PATCH} format. Use the ``beta`` tag after the version number for releases from the ``dev`` branch.
Use ``rc[0-9]+`` tag after the version number for releases from a ``release`` branch.


[1]: https://github.com/vaab/gitchangelog/blob/master/src/gitchangelog/gitchangelog.rc.reference