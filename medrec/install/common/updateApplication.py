print 'starting the script ....'

url = 't3://' + sys.argv[1] + ':' + sys.argv[2]
hostname  = sys.argv[1]
username  = sys.argv[3]
password  = sys.argv[4]
appName   = sys.argv[5]  #  application name
filePath  = sys.argv[6]  #  deployment plan file path
partition = sys.argv[7]  #  partition name

connect(username, password, url)

edit()
startEdit()

updateApplication(appName, filePath, partition=partition)

save()
activate()

disconnect()