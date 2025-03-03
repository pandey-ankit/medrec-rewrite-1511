# Add new MedRec Tenant

print 'Start configuring new MedRec Tenant...'

# connect WLST to the Admin Examples Server
url = 't3://' + sys.argv[1] + ':' + sys.argv[2]
vtHostName = sys.argv[3]
username = sys.argv[4]
password = sys.argv[5]
partition=sys.argv[6]
dbURL=sys.argv[7]
dbUser=sys.argv[8]
dbPwd=sys.argv[9]

# WebLogic Cluster Names
applicationClusterName = 'app_cluster'
cacheClusterName = 'cache_cluster'

# Multi-tenant
virtualTargetPrefixApp = 'VT-App-'
virtualTargetPrefixCache = 'VT-Cache-'
resourceGroupTemplateApp  = 'appRGT'
resourceGroupTemplateCache  = 'cacheRGT'
virtualTarget1=virtualTargetPrefixApp+partition
virtualTarget2=virtualTargetPrefixCache+partition
appdsName='MedRecGlobalDataSourceXA'

def __createVirtualTarget():
  cd('/')
  cmo.createVirtualTarget(virtualTarget1)
  cd('/VirtualTargets/' + virtualTarget1)
  set('HostNames',jarray.array([String(vtHostName)], String))
  set('Targets',jarray.array([ObjectName('com.bea:Name=' + applicationClusterName + ',Type=Cluster')], ObjectName))

  cd('/')
  cmo.createVirtualTarget(virtualTarget2)
  cd('/VirtualTargets/' + virtualTarget2)
  set('HostNames',jarray.array([String(partition+'.cache')], String))
  set('Targets',jarray.array([ObjectName('com.bea:Name=' + cacheClusterName + ',Type=Cluster')], ObjectName))

def __createPartition():
  cd('/')
  cmo.createPartition(partition)

  cd('/Partitions/' + partition)
  cmo.createResourceGroup(partition + '_appRG')
  cmo.createResourceGroup(partition + '_cacheRG')

  cmo.setRealm(None)
  set('AvailableTargets',jarray.array([ObjectName('com.bea:Name=' + virtualTarget1 + ',Type=VirtualTarget'), ObjectName('com.bea:Name=' + virtualTarget2 + ',Type=VirtualTarget')], ObjectName))

def __associateRGT():
  cd('/Partitions/' + partition + '/ResourceGroups/' + partition +'_appRG')
  cmo.setResourceGroupTemplate(getMBean('/ResourceGroupTemplates/' + resourceGroupTemplateApp))
  set('Targets',jarray.array([ObjectName('com.bea:Name=' + virtualTarget1 +',Type=VirtualTarget')], ObjectName))

  cd('/Partitions/' + partition + '/ResourceGroups/' + partition + '_cacheRG')
  cmo.setResourceGroupTemplate(getMBean('/ResourceGroupTemplates/' + resourceGroupTemplateCache ))
  set('Targets',jarray.array([ObjectName('com.bea:Name=' + virtualTarget2 + ',Type=VirtualTarget')], ObjectName))

def __setJDBCOverrides(partitionPDBName, dsName):
  cd('/Partitions/' + partition)
  info=cmo.createJDBCSystemResourceOverride(partitionPDBName)
  info.setDataSourceName(dsName)
  info.setURL(dbURL)
  info.setUser(dbUser)
  info.setPassword(dbPwd)

def __createCacheInfo(cacheName, isShared = false):
  cd('/Partitions/' + partition)
  cmo.createCoherencePartitionCacheConfig(cacheName)
  cd('CoherencePartitionCacheConfigs/' + cacheName)
  cmo.setShared(isShared)
  cmo.setCacheName(cacheName)
  cmo.setApplicationName('medrecGAR')

connect(username, password, url)
edit()
startEdit()

print 'Creating virtual target'
__createVirtualTarget()

print 'Creating partition'
__createPartition()

print 'Creating cache config beans on caches'
__createCacheInfo('SharedCache', true)
__createCacheInfo('MethodCache', false)

print 'Setting JDBC overrides for each partition'
__setJDBCOverrides(partition+'MedRecPDB',appdsName)
__associateRGT()

activate(block='true')

disconnect()
