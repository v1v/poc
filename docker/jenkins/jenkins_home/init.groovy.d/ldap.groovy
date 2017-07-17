/*** BEGIN META {
"name" : "ldap.groovy",
"comment" : "This script will configure our Jenkins instances",
"parameters" : [ ],
"core": "2.19.3",
"authors" : [
{ name : "Victor Martinez" }
]
} END META**/

/*
* You can add more admin users and enable different features according to the Permission class.
*
* @param adminUsers list of admin users (ldap based).
* @param server LDAP domain.
*
* Further details:
*  - Credentials plugin: https://github.com/jenkinsci/credentials-plugin/blob/master/src/main/java/com/cloudbees/plugins/credentials/CredentialsProvider.java
*  - LDAP plugin: https://github.com/jenkinsci/ldap-plugin/blob/ldap-1.13/src/main/java/hudson/security/LDAPSecurityRealm.java
*
*  If you need to query any available PERMISSIONS:
*       Jenkins.instance.PERMISSIONS.getAll().each { println it.getPermissions() }
*/

import hudson.security.HudsonPrivateSecurityRealm
import jenkins.model.Jenkins
import jenkins.model.ProjectNamingStrategy
import hudson.security.ProjectMatrixAuthorizationStrategy
import java.util.regex.Pattern

def adminUsers = [
                 'vicmar02',
                 ]

def server = 'universe.arm.com'
def rootDN = 'DC=arm,DC=com'
def userSearch = 'uid={0}'
def displayNameAttributeName = 'displayname'
def mailAddressAttributeName = 'mail'

/**
* Configure authenticated group.
*
* @param instance Jenkins instance.
* @param strategy Strategy instance.
* @return nothing
*/
def configureAuthenticated(instance, strategy) {
  def user = 'authenticated'

  strategy.add(Jenkins.READ, user)

  if (instance.pluginManager.getPlugin('credentials')!=null) {
    strategy.add(com.cloudbees.plugins.credentials.CredentialsProvider.VIEW, user)
  }

  strategy.add(hudson.model.Item.BUILD, user)
  strategy.add(hudson.model.Item.CANCEL, user)
  strategy.add(hudson.model.Item.READ, user)
  strategy.add(hudson.model.Item.WORKSPACE, user)

  strategy.add(hudson.model.Run.DELETE, user)
  strategy.add(hudson.model.Run.UPDATE, user)

  strategy.add(hudson.model.View.CONFIGURE, user)
  strategy.add(hudson.model.View.CREATE, user)
  strategy.add(hudson.model.View.READ, user)
  strategy.add(hudson.model.View.DELETE, user)

  strategy.add(hudson.scm.SCM.TAG, user)
  if (instance.pluginManager.getPlugin('gerrit-trigger')!=null) {
    strategy.add(com.sonyericsson.hudson.plugins.gerrit.trigger.PluginImpl.MANUAL_TRIGGER, user)
    strategy.add(com.sonyericsson.hudson.plugins.gerrit.trigger.PluginImpl.RETRIGGER, user)
  }
}

/**
* Configure anonymous access.
*
* @param instance Jenkins instance.
* @param strategy Strategy instance.
* @return nothing
*/
def configureAnonymous(instance, strategy) {
  def user = 'anonymous'
  strategy.add(Jenkins.ADMINISTER, user)
}


//
// MAIN FUNCTION
//
def instance = Jenkins.getInstance()

// Configure Master slave (projectnamingstrategy)
instance.setProjectNamingStrategy(ProjectNamingStrategy.DEFAULT_NAMING_STRATEGY)
instance.save()
instance.reload()

// Enable privileges
def strategy = new ProjectMatrixAuthorizationStrategy()
adminUsers.each { user ->
  strategy.add(Jenkins.ADMINISTER, user)
}
configureAnonymous(instance, strategy)

configureAuthenticated(instance, strategy)
instance.setAuthorizationStrategy(strategy)

// Active Directory setup
// https://github.com/jenkinsci/ldap-plugin/blob/ldap-1.13/src/main/java/hudson/security/LDAPSecurityRealm.java#L480
securityRealm = new  hudson.security.LDAPSecurityRealm(server,
                      rootDN,
                      "",
                      userSearch,
                      "", //groupSearchBase
                      "", //groupSearchFilter
                      null, //LDAPGroupMembershipStrategy
                      "", //managerDN
                      null, //managerPasswordSecret
                      false, //inhibitInferRootDN
                      false, //disableMailAddressResolver
                      null, //CacheConfiguration
                      null, // EnvironmentProperty[]
                      displayNameAttributeName,
                      mailAddressAttributeName,
                      null, //userIdStrategy
                      null) //groupIdStrategy

instance.setSecurityRealm(securityRealm)
instance.save()

println 'Security configuration done!'
