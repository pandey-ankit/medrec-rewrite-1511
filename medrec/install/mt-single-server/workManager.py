# Work QoS

url = 't3://' + sys.argv[1] + ':' + sys.argv[2]
username = sys.argv[3]
password = sys.argv[4]

connect(username, password, url)

edit()
startEdit()

cd('/')
vip=cmo.lookupPartitionWorkManager('vip')
if vip is None:
  vip=cmo.createPartitionWorkManager('vip')
  vip.setSharedCapacityPercent(60)
  vip.setFairShare(50)
  vip.setMinThreadsConstraintCap(100)
  vip.setMaxThreadsConstraint(150);

cd('/')
common=cmo.lookupPartitionWorkManager('common')
if common is None:
  common=cmo.createPartitionWorkManager('common')
  common.setSharedCapacityPercent(20)
  common.setFairShare(20)
  common.setMinThreadsConstraintCap(40)
  common.setMaxThreadsConstraint(60);
  
cd('/Partitions/bayland')
cmo.setPartitionWorkManagerRef(vip)
cd('/Partitions/valley1')
cmo.setPartitionWorkManagerRef(common)
cd('/Partitions/valley2')
cmo.setPartitionWorkManagerRef(common)

activate(block='true')

disconnect()





