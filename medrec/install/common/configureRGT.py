print 'starting the script ....'

url = 't3://' + sys.argv[1] + ':' + sys.argv[2]
username = sys.argv[3]
password = sys.argv[4]
dbDriver = sys.argv[5]
rgtName  = sys.argv[6]

#========================== User ===============================
def addUserToDefaultRealm(username,password,description):
  print 'Prepare User',username,'...'
  cd('/')
  realm=cmo.getSecurityConfiguration().getDefaultRealm()
  atnr=realm.lookupAuthenticationProvider('DefaultAuthenticator')
  if atnr.userExists(username)==1:
    print '[Warning]User',username,'has been existed.'
  else:
    atnr.createUser(username,password,description)
    print '[INFO]User',username,'has been created successfully'

#=========================== Mail =============================
def configureMailSession(rgt,name,jndiName,properties):
  print 'Prepare Mail Session',name,'...'
  if rgt.lookupMailSession(name) is None:
    mailSessionMB=rgt.createMailSession(name)
    mailSessionMB.setJNDIName(jndiName)
    if properties is not None:
      mailSessionMB.setProperties(properties)
    print '[INFO]MailSession',name,'has been created successfully.'
  else:
    print '[Warning]MailSession',name,'has been existed.'

#======================== DataSource ==========================
def configureDataSource(rgt,name,driver):
  print 'Prepare data resource',name,'...'
  if rgt.lookupJDBCSystemResource(name) is None:
    dataSourceMB = rgt.createJDBCSystemResource(name)
    jdbcResource = dataSourceMB.getJDBCResource()
    jdbcResource.setName(name)
    jdbcDataSourceParams = jdbcResource.getJDBCDataSourceParams()
    jdbcDataSourceParams.addJNDIName('jdbc/'+name)
    jdbcDataSourceParams.setGlobalTransactionsProtocol('TwoPhaseCommit')
    jdbcDriverParams = jdbcResource.getJDBCDriverParams()
    jdbcDriverParams.setDriverName(driver)
    print '[INFO]Data Source',name,'has been created successfully.'
  else:
    print '[Warning]Data Source',name,'has been existed.'

#============================ JMS ==================================
def configureJMSServer(rgt,name):
  print 'Prepare JMS Server',name,'...'
  jmsServerMB=rgt.lookupJMSServer(name)
  if jmsServerMB is None:
    return rgt.createJMSServer(name)
  else:
    print '[Warning]JMS Server',name,'has been existed.'

def configureStore(rgt,name):
  print 'Prepare File Store',name,'...'
  storeMB=rgt.lookupFileStore(name)
  if storeMB is None:
    return rgt.createFileStore(name)
  else:
    print '[Warning] File Store',name,'has been existed.'

def configureJMSModule(rgt,name,subName,jmsServer):
  print 'Prepare JMS Module',name,'...'
  jmsSR=rgt.lookupJMSSystemResource(name)
  if jmsSR is None:
    jmsSR=rgt.createJMSSystemResource(name)
    subD=jmsSR.createSubDeployment(subName)
    subD.addTarget(jmsServer)
  else:
    print '[Warning]JMS Module',name,'has been existed.'
  return jmsSR.getJMSResource()

def configureJMSConnectionFactory(jmsResource,name,jndiName,subName):
  print 'Prepare JMS Connection Factory',name,'...'
  if jmsResource.lookupConnectionFactory(name) is None:
    jcfMB=jmsResource.createConnectionFactory(name)
    jcfMB.setJNDIName(jndiName)
    jcfMB.setSubDeploymentName(subName)
    clientParams=jcfMB.getClientParams()
    clientParams.setClientIdPolicy('Restricted')
    clientParams.setSubscriptionSharingPolicy('Exclusive')
    xnParams=jcfMB.getTransactionParams()
    xnParams.setXAConnectionFactoryEnabled(True)
    secParams=jcfMB.getSecurityParams()
    secParams.setAttachJMSXUserId(False)
    print '[INFO]JMS Connection Factory',name,'has been created successfully.'
  else:
    print '[Warning]JMS Connection Factory',name,'has been existed.'

def configureJMSQueue(jmsResource,name,jndiName,subName):
  print 'Prepare JMS Queue',name,'...'
  if jmsResource.lookupUniformDistributedQueue(name) is None:
    q=jmsResource.createUniformDistributedQueue(name)
    q.setJNDIName(jndiName)
    q.setSubDeploymentName(subName)
    print '[INFO]JMS Queue',name,'has been created successfully.'
  else:
    print '[Warning]JMS Queue',name,'has been existed.'


connect(username,password,url)

addUserToDefaultRealm('administrator','administrator123','MedRec Admin')

edit()
startEdit()
print 'Prepare Resource Group Template',rgtName,'...'
cd('/ResourceGroupTemplates')
rgt=cmo.lookupResourceGroupTemplate(rgtName)
if rgt is None:
  rgt=cmo.createResourceGroupTemplate(rgtName)
  print '[INFO]Resource Group Template',rgtName,'has been created successfully.'
else:
  print '[Warning]Resource Group Template',rgtName,'has been existed.'

configureMailSession(rgt,'MedRecMailSession','mail/MedRecMailSession',None)
configureDataSource(rgt,'MedRecGlobalDataSourceXA',dbDriver)
jmsServer = configureJMSServer(rgt,'MedRecJMSServer')
store = configureStore(rgt,'MedRecFileStore')
jmsServer.setPersistentStore(store)
jmsResource = configureJMSModule(rgt,'MedRecModule','MedRecJMS',jmsServer)
configureJMSConnectionFactory(jmsResource,'MedRecConnectionFactory','com.oracle.medrec.jms.connectionFactory','MedRecJMS')
configureJMSQueue(jmsResource,'PatientNotificationQueue','com.oracle.medrec.jms.PatientNotificationQueue','MedRecJMS')

activate()

disconnect()
