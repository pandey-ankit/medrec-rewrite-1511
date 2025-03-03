# Functions of this script:
# * connect WLST to the Admin Server
# * start an edit session
# * create a cluster with 2 managed servers
# * save and activate editor
# * disconnect WLST from the Admin Server

print 'starting the script ....'

# connect WLST to the Admin Examples Server
url = 't3://' + sys.argv[1] + ':' + sys.argv[2]
hostName = sys.argv[1]
username = sys.argv[3]
password = sys.argv[4]
domainDir= sys.argv[5]
nmDir    = sys.argv[6]
coherenceOverrideFile = sys.argv[7]

appServersTemplateName = 'app-server-template'
cacheServersTemplateName= 'cache-server-template'
appServersStartPort    = 7020
cacheServersStartPort  = 7050
applicationClusterName = 'app_cluster'
cacheClusterName       = 'cache_cluster'

# Coherence Cluster Name
cohClusterName = 'CoherenceCluster'
cohClusterPort = 2105

def __createServerTemplate(serverTemplateName, startPort):
  dynamicServerTemplate=cmo.createServerTemplate(serverTemplateName)
  dynamicServerTemplate.setAcceptBacklog(2000)
  dynamicServerTemplate.setAutoRestart(true)
  dynamicServerTemplate.setRestartMax(10)
  dynamicServerTemplate.setListenPort(startPort)
  dynamicServerTemplate.setStartupTimeout(600)
  #dynamicServerTemplate.getServerStart().setArguments("-Dtest.cachestore.write-delay=120s")
  return dynamicServerTemplate

def __createDynamicCluster(clusterName, dynamicServerTemplate):
  dynCluster=cmo.createCluster(clusterName)
  dynCluster.setClusterMessagingMode('unicast')
  dynServers=dynCluster.getDynamicServers()
  dynServers.setMaximumDynamicServerCount(2)
  dynServers.setServerTemplate(dynamicServerTemplate)
  dynServers.setServerNamePrefix(clusterName+"-")
  dynServers.setCalculatedMachineNames(true)
  return dynCluster

connect(username, password, url)
edit()
startEdit()

print 'Enrolling this domain with node manager'
nmEnroll(domainDir, nmDir)

# create machine
cd('/')
machine=cmo.createMachine('machine')
nm=machine.getNodeManager()
nm.setListenAddress(hostName)

# Create Server Template for Cluster
cd('/')
appServersTemplate = __createServerTemplate(appServersTemplateName, appServersStartPort)
cacheServersTemplate= __createServerTemplate(cacheServersTemplateName, cacheServersStartPort)
    
# Create a Dynamic WebLogic cluster for app tier
app_cluster = __createDynamicCluster(applicationClusterName, appServersTemplate)
cache_cluster = __createDynamicCluster(cacheClusterName, cacheServersTemplate)

# Create the Coherence cluster
cd('/')
cohSR      = create(cohClusterName, 'CoherenceClusterSystemResource')
# Set the operational configuration file for this Coherence cluster
print 'Setting the custom cluster configuration file'
cohSR.importCustomClusterConfigurationFile(coherenceOverrideFile)
cohBean    = cohSR.getCoherenceClusterResource()
cohCluster = cohBean.getCoherenceClusterParams()
cohCluster.setClusterListenPort(cohClusterPort)
cohSR.addTarget(app_cluster)
cohSR.addTarget(cache_cluster)

app_cluster.setCoherenceClusterSystemResource(cohSR)
cohTier = app_cluster.getCoherenceTier()
cohTier.setLocalStorageEnabled(false)

cache_cluster.setCoherenceClusterSystemResource(cohSR)

cmo.createResourceGroupTemplate('appRGT')
cmo.createResourceGroupTemplate('cacheRGT')

activate()

disconnect()
