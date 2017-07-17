/*** BEGIN META {
"name" : "git.groovy",
"comment" : "This script will configure our Jenkins instances",
"parameters" : [ ],
"core": "2.19.3",
"authors" : [
{ name : "Victor Martinez" }
]
} END META**/

def desc = jenkins.model.Jenkins.getInstance().getDescriptor("hudson.plugins.git.GitSCM")

desc.setGlobalConfigEmail("Jenkins.PICT@example.com")
desc.setGlobalConfigName("Jenkins PICT")

println 'GIT configuration done!'
