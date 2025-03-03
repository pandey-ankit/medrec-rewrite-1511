"""
This script configures JDBC data source MedRecGlobalDataSourceXA and redeploys it
to the server
"""

driverUrl=sys.argv[5]
driverName=sys.argv[6]
driverUser=sys.argv[7]
driverPassword=sys.argv[8]
testSql=sys.argv[9]

url='t3://'+sys.argv[3]+':'+sys.argv[4]
print 'connect to the server' 
try:
    connect(sys.argv[1], sys.argv[2], url)
except WLSTException:
    print "*******************************************************************************************"
    print "*** Reason: The server is off now, please start up the server and run the command again ***"
    print "*******************************************************************************************"
    # exits system, and tell ant the error code
    exit(exitcode=101)

edit()
startEdit()
# start edit
cd("JDBCSystemResources/MedRecGlobalDataSourceXA/JDBCResource/MedRecGlobalDataSourceXA/JDBCConnectionPoolParams/MedRecGlobalDataSourceXA")
poolParams=cmo
cd("../..")
cd("JDBCDriverParams/MedRecGlobalDataSourceXA")
jdbcDriverParams=cmo
cd("Properties/MedRecGlobalDataSourceXA/Properties/user")
user=cmo
print 'set test table name: ' + testSql
poolParams.setTestTableName('SQL ' + testSql)
print 'set driver name: ' + driverName
jdbcDriverParams.setDriverName(driverName)
print 'set driver url: ' + driverUrl
jdbcDriverParams.setUrl(driverUrl)
print 'set driver password: ' + driverPassword
jdbcDriverParams.setPassword(driverPassword)
print 'set driver user: ' + driverUser
user.setValue(driverUser)

save()
activate(block="true")

print 'Done modifying the data source'

