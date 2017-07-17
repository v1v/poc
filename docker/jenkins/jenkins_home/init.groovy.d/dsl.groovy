/*** BEGIN META {
"name" : "dsl.groovy",
"comment" : "This script will disable DSL Security. https://groups.google.com/forum/#!topic/job-dsl-plugin/BT8nEeLoBps",
"parameters" : [ ],
"core": "2.19.3",
"authors" : [
{ name : "Victor Martinez" }
]
} END META**/

import javaposse.jobdsl.plugin.GlobalJobDslSecurityConfiguration
import jenkins.model.GlobalConfiguration

GlobalConfiguration.all().get(GlobalJobDslSecurityConfiguration.class).useScriptSecurity=false