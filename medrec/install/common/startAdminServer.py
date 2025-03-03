
url = 't3://' + sys.argv[1] + ':' + sys.argv[2]
username = sys.argv[3]
password = sys.argv[4]
serverName = sys.argv[5]
domainName = sys.argv[6]
domainDir  = sys.argv[7]
jvmargs    = sys.argv[8]

## WLST code to start the Administration Server
startServer(serverName, domainName, url, username, password, domainDir, 'true', jvmArgs=jvmargs)