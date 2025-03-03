print 'starting the script ....'

url = 't3://' + sys.argv[1] + ':' + sys.argv[2]
username = sys.argv[3]
password = sys.argv[4]

connect(username,password,url)

shutdown(force='true')