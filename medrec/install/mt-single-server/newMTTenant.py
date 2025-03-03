# Add new MedRec Tenant

print 'Start configuring new MedRec Tenant...'

# connect WLST to the Admin Examples Server
url = 't3://' + sys.argv[1] + ':' + sys.argv[2]
username = sys.argv[3]
password = sys.argv[4]
serverName = sys.argv[5]
partitionName = sys.argv[6]
dbURL  = sys.argv[7]
dbUser = sys.argv[8]
dbPwd  = sys.argv[9]
realmName = sys.argv[10]
virtualHostName = sys.argv[11]

vtName   = 'VT-' + partitionName
appdsName='MedRecGlobalDataSourceXA'
rgtName = 'MedRecRGT'

def configureSecurityRealmWithDefaults(name):
  print 'Prepare Realm',name,'...'
  cd('/')
  security=getMBean('/').getSecurityConfiguration()
  realm=security.lookupRealm(name)
  if realm is None:
    # Create Realm
    realm=security.createRealm(name)
    # ATN
    realm.createAuthenticationProvider('ATN','weblogic.security.providers.authentication.DefaultAuthenticator')
    # IA
    ia=realm.createAuthenticationProvider('IA','weblogic.security.providers.authentication.DefaultIdentityAsserter')
    ia.setActiveTypes(['AuthenticatedUser'])
    #ATZ/Role
    realm.createRoleMapper('Role','weblogic.security.providers.xacml.authorization.XACMLRoleMapper')
    realm.createAuthorizer('ATZ','weblogic.security.providers.xacml.authorization.XACMLAuthorizer')
    # Adjudicator
    realm.createAdjudicator('ADJ','weblogic.security.providers.authorization.DefaultAdjudicator')
    # Cred Mapper
    realm.createCredentialMapper('CM','weblogic.security.providers.credentials.DefaultCredentialMapper')
    # Cert Path
    realm.setCertPathBuilder(realm.createCertPathProvider('CP','weblogic.security.providers.pk.WebLogicCertPathProvider'))
    # Password Validator
    pv=realm.createPasswordValidator('PV','com.bea.security.providers.authentication.passwordvalidator.SystemPasswordValidator')
    pv.setMinPasswordLength(8)
    pv.setMinNumericOrSpecialCharacters(1)
    print '[INFO]Realm',name,'has been created successfully'
  else:
    print '[Warning]Realm',name,'has been existed.'
  return realm

def createVirtualTarget():
  cd('/')
  vt = cmo.createVirtualTarget(vtName)
  vt.setHostNames(jarray.array([String(virtualHostName),String('localhost')],String))
  vt.setUriPrefix('/'+partitionName)
  cd('/VirtualTargets/'+vtName)
  set('Targets',jarray.array([ObjectName('com.bea:Name=' + serverName + ',Type=Server')], ObjectName))
  return vt

def createPartition(rgt,vt,realm):
  cd('/')
  partition=cmo.createPartition(partitionName)
  rg=partition.createResourceGroup(partitionName+'RG')
  rg.setResourceGroupTemplate(rgt)
  partition.setAvailableTargets(vt)
  partition.setDefaultTargets(vt)
  partition.setRealm(realm)

def setJDBCOverrides(partitionPDBName, dsName):
  cd('/Partitions/' + partitionName)
  info=cmo.createJDBCSystemResourceOverride(partitionPDBName)
  info.setDataSourceName(dsName)
  info.setURL(dbURL)
  info.setUser(dbUser)
  info.setPassword(dbPwd)

connect(username, password, url)
                                  
edit()
startEdit()

realm = configureSecurityRealmWithDefaults(realmName)
vt = createVirtualTarget()
cd('/')
rgt = getMBean('/ResourceGroupTemplates/' + rgtName)
createPartition(rgt,[vt],realm)

setJDBCOverrides(partitionName+'MedRecPDB',appdsName)

activate(block='true')

disconnect()
