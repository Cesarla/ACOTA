counter=0

function check_test {
	if [ $1 -ne 0 ]
	then
		let counter+=1
	fi
}

echo "Testing Acota:"

mvn -f acota-core/pom.xml test
check_test $?

mvn -f acota-feedback/pom.xml test
check_test $?

mvn -f acota-examples/pom.xml test
check_test $?

mvn -f acota-utils/pom.xml test
check_test $?

if [ $counter -eq 0 ]
then
	echo "All test passed"
	exit 0
else 
	echo "$counter test failed"
	exit 1
fi
