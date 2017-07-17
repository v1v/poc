/*** BEGIN META {
"name" : "markup.groovy",
"comment" : "This script will configure our Jenkins instances",
"parameters" : [ ],
"core": "2.19.3",
"authors" : [
{ name : "Victor Martinez" }
]
} END META**/

Jenkins.instance.setMarkupFormatter(new hudson.markup.RawHtmlMarkupFormatter(false))
Jenkins.instance.save()
println 'Markup formatter configured.'