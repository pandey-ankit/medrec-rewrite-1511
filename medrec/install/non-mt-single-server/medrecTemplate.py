# install medrec WLS template

medrecTemplate = sys.argv[1]
domainDir      = sys.argv[2]

print 'Reading template'
readDomain(domainDir)

setOption('ReplaceDuplicates','false')
#setOption('AppDir','@WL_HOME/samples/server/medrec')
setOption('BackupFiles','false')

addTemplate(medrecTemplate)

updateDomain()
closeDomain()
exit()