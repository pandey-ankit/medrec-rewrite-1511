print 'starting the script ....'

url = 't3://' + sys.argv[1] + ':' + sys.argv[2]
hostname = sys.argv[1]
username = sys.argv[3]
password = sys.argv[4]
partitionName = sys.argv[5]

connect(username, password, url)

domainRuntime()
partDomainRuntime=cmo.lookupDomainPartitionRuntime(partitionName)

task=partDomainRuntime.getPartitionLifeCycleRuntime().start()

# Do interesting work that does not depend on the partition being started yet.

while task.isRunning():
  os.time.sleep(1)

disconnect()