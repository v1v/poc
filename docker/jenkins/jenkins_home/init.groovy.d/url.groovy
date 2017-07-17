/*** BEGIN META {
"name" : "git.groovy",
"comment" : "This script will configure our Jenkins instances",
"parameters" : [ ],
"core": "2.19.3",
"authors" : [
{ name : "Victor Martinez" }
]
} END META**/

def env = System.getenv()
def gerrit_hostname = env['GERRIT_HOST_NAME']
jenkins.model.JenkinsLocationConfiguration.get().setUrl("http://${gerrit_hostname}:8081")
println 'URL configuration done!'
