print 'starting the script ....'

url = 't3://' + sys.argv[1] + ':' + sys.argv[2]
hostname = sys.argv[1]
username = sys.argv[3]
password = sys.argv[4]
domainDir = sys.argv[5]

connect(username, password, url)

startNodeManager(verbose='true', NodeManagerHome=domainDir + '/nodemanager', ListenPort='5556', ListenAddress=hostname, NativeVersionEnabled='false')

disconnect()