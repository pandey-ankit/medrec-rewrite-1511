print 'starting the script ....'

url = 't3://' + sys.argv[1] + ':' + sys.argv[2]
username = sys.argv[3]
password = sys.argv[4]
realmName = sys.argv[5]

def addUser(realm,username,password,description):
  print 'Prepare User',username,'...'
  if realm is not None:
    atnr=realm.lookupAuthenticationProvider('ATN')
    if atnr.userExists(username)==1:
      print '[Warning]User',username,'has been existed.'
    else:
      atnr.createUser(username,password,description)
      print '[INFO]User',username,'has been created successfully'


connect(username,password,url)

security=getMBean('/').getSecurityConfiguration()
realm=security.lookupRealm(realmName)
addUser(realm,'administrator','administrator123','MedRec Administrator')

disconnect()

