# Create clean domain
# Author: Xiaojun.wu

domainTemplate = sys.argv[1]
domainName     = sys.argv[2]
domainDir      = sys.argv[3]
listenHost     = sys.argv[4]
listenPort     = sys.argv[5]
adminUser      = sys.argv[6]
adminPassword  = sys.argv[7]
serverName     = sys.argv[8]


print 'Reading template'
readTemplate(domainTemplate)
  
# Create Admin Server
print 'Creating AdminServer'
cd('Servers/AdminServer')
set('Name',serverName)
#set('ListenAddress',listenHost)
set('ListenPort', int(listenPort))
create(serverName,'SSL')
cd('SSL/'+serverName)
set('Enabled', 'False')
set('ListenPort', int(listenPort) + 1)
        
## Set Security ##
print 'Setting Security'
cd('/Security/base_domain/User/weblogic')
set('Name', adminUser)
set('Password', adminPassword)
        
cd('/')
setOption('OverwriteDomain', 'true')
        
## Write Domain ##
print 'Writing Domain'
writeDomain(domainDir)
closeTemplate()