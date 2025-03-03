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
domainName = sys.argv[5]
nmDir  = sys.argv[6]

connect(username, password, url)

print 'Connect nodemanager'
nmConnect(username, password,hostName,'5556',domainName)
      
print 'Starting Cluster'
shutdown('cache_cluster', 'Cluster', force='true')
shutdown('app_cluster', 'Cluster', force='true')

disconnect()
