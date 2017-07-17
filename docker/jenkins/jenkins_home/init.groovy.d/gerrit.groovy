/*** BEGIN META {
"name" : "gerrit.groovy",
"comment" : "This script will configure Gerrit",
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
*  - https://github.com/Accenture/adop-jenkins/blob/master/resources/init.groovy.d/adop_gerrit.groovy
*
*/

import hudson.model.*;
import jenkins.model.*;
import com.sonyericsson.hudson.plugins.gerrit.trigger.PluginImpl;
import com.sonyericsson.hudson.plugins.gerrit.trigger.GerritServer;
import com.sonyericsson.hudson.plugins.gerrit.trigger.config.Config;

def env = System.getenv()

// Variables
def gerrit_host_name = env['GERRIT_HOST_NAME']
def gerrit_front_end_url = env['GERRIT_FRONT_END_URL']
def gerrit_ssh_port = '29418'.toInteger()
def gerrit_username = "jenkins-pict"
def gerrit_profile =  "gerrit-prod"
def gerrit_email = "jenkins-pict@example.com"
def gerrit_ssh_key_file = "/tmp/jenkins-pict_rsa"
def gerrit_ssh_key_password = null

def gateKeeper = "gerrit review <CHANGE>,<PATCHSET> --message 'Build Successful <BUILDS_STATS>' " +
                 "--verified <VERIFIED> --label heimdall=<CODE_REVIEW>"

  // Gerrit
println "--> Configuring Gerrit"

def gerrit_trigger_plugin = PluginImpl.getInstance()

if (! gerrit_trigger_plugin.getServerNames().find { it.equals(gerrit_profile) } ) {
  def server = new GerritServer(gerrit_profile)

  def config = new Config()
  config.setGerritHostName(gerrit_host_name)
  config.setGerritFrontEndURL(gerrit_front_end_url)
  config.setGerritSshPort(gerrit_ssh_port)
  config.setGerritUserName(gerrit_username)
  config.setGerritEMail(gerrit_email)
  config.setGerritAuthKeyFile(new File(gerrit_ssh_key_file))
  config.setGerritAuthKeyFilePassword(gerrit_ssh_key_password)
  config.setGerritVerifiedCmdBuildSuccessful(gateKeeper)

  server.setConfig(config)
  gerrit_trigger_plugin.addServer(server)
  server.start()
  server.startConnection()
}

// Save the state
Jenkins.getInstance().save()

println 'Gerrit configuration done!'
