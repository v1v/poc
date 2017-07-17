# Jenkins Job DSL Gradle for PICT

Scripts in Groovy, Shell, Ruby, Python, Groovy DSL, whatever for managing/interacting with Jenkins


Configure your IDE
------------------

```bash
gradlew cleanIdea idea
```

## Testing

* `./gradlew clean test` runs the specs.

[JobScriptsSpec](src/test/groovy/com/king/ess/dsl/JobScriptsSpec.groovy)
will loop through all DSL files and make sure they don't throw any exceptions when processed.

## File structure

    .
    ├── jobs                    # DSL script files
    ├── resources               # resources for DSL scripts
    ├── src
    │   ├── main
    │   │   ├── groovy          # support classes
    │   │   └── resources
    │   │       └── idea.gdsl   # IDE support for IDEA
    │   └── test
    │       └── groovy          # specs
    └── build.gradle            # build file

## Seed Job


Further references
------------------

- [Docs](http://jenkinsci.github.io/job-dsl-plugin/)
- [Playarea](http://job-dsl.herokuapp.com/)
- [Wiki](https://github.com/jenkinsci/job-dsl-plugin/wiki)
