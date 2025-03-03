print 'Start configuring RCM...'

url = 't3://' + sys.argv[1] + ':' + sys.argv[2]
username = sys.argv[3]
password = sys.argv[4]

connect(username, password, url)

edit()
startEdit()

rmt=cd('/').getResourceManagement()

# vip resource manager
rm=rmt.lookupResourceManager('vip')
if rm is None:
  rm=rmt.createResourceManager('vip')
  fo=rm.createFileOpen('vip-fopen')
  fo.createTrigger('vip-fopen-slow',30,'slow')
  cu=rm.createCpuUtilization('vip-cpu')
  cu.createTrigger('vip-cpu-slow',70,'slow')
  cu.createTrigger('vip-cpu-shutdown',90,'shutdown')
  hr=rm.createHeapRetained('vip-hr')
  hr.createTrigger('vip-hr-slow',256000,'slow')
  hr.createTrigger('vip-hr-shutdown',400000,'shutdown')

cd('/Partitions/bayland')
cmo.setResourceManagerRef(rm)

# common resource manager
rm=rmt.lookupResourceManager('common')
if rm is None:
  rm=rmt.createResourceManager('common')
  fo=rm.createFileOpen('cm-fopen')
  fo.createTrigger('cm-fopen-slow',15,'slow')
  cu=rm.createCpuUtilization('cm-cpu')
  cu.createTrigger('cm-cpu-slow',30,'slow')
  cu.createTrigger('cm-cpu-shutdown',50,'shutdown')
  hr=rm.createHeapRetained('cm-hr')
  hr.createTrigger('cm-hr-slow',128000,'slow')
  hr.createTrigger('cm-hr-shutdown',256000,'shutdown')

cd('/Partitions/valley1')
cmo.setResourceManagerRef(rm)
cd('/Partitions/valley2')
cmo.setResourceManagerRef(rm)

activate()

print 'Installation completed.'

disconnect()
