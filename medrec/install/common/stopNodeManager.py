print 'starting the script ....'

url = 't3://' + sys.argv[1] + ':' + sys.argv[2]
hostname = sys.argv[1]
username = sys.argv[3]
password = sys.argv[4]
domainName = sys.argv[5]

nmConnect(username,password,hostname,'5556',domainName)

stopNodeManager()