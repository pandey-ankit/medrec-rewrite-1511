url = 't3://' + sys.argv[1] + ':' + sys.argv[2]
username = sys.argv[3]
password = sys.argv[4]

connect(username,password,url)

edit()
startEdit()
    
print 'Deploy MedRec Applications'
deploy(appName='medrec',path='../../modules/medrec/medrec-assembly/target/medrec.ear',
  resourceGroupTemplate='appRGT',deploymentOrder=1,securityModel='DDOnly',planPath='medrec_plan.xml')
deploy(appName='physician',path='../../modules/physician/physician-assembly-coherence/target/physician-coherence.ear',
  resourceGroupTemplate='appRGT',deploymentOrder=50,securityModel='DDOnly',planPath='physician_plan.xml')
deploy(appName='chat',path='../../modules/chat/target/chat.war',
  resourceGroupTemplate='appRGT',deploymentOrder=1,securityModel='DDOnly')
deploy(appName='medrecGAR',path='../../modules/gar/target/medrecGAR.gar',
  resourceGroupTemplate='cacheRGT',deploymentOrder=1,securityModel='DDOnly')

activate()
disconnect()
