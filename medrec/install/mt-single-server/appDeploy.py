url = 't3://' + sys.argv[1] + ':' + sys.argv[2]
username = sys.argv[3]
password = sys.argv[4]

connect(username,password,url)

edit()
startEdit()
    
print 'Deploy MedRec Applications'
deploy(appName='medrec',path='../../modules/medrec/medrec-assembly/target/medrec.ear',
    resourceGroupTemplate='MedRecRGT',deploymentOrder=1,securityModel='DDOnly')
deploy(appName='chat',path='../../modules/chat/target/chat.war',
    resourceGroupTemplate='MedRecRGT',deploymentOrder=50,securityModel='DDOnly')
deploy(appName='physician',path='../../modules/physician/physician-assembly/target/physician.ear',
    resourceGroupTemplate='MedRecRGT',deploymentOrder=50,securityModel='DDOnly')

deploy(appName='docApp',path='../../../docs',deploymentOrder=100,securityModel='DDOnly')

activate()
disconnect()
