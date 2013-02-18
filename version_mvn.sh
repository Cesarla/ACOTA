echo "Type the new version of Acota, followed by [ENTER]:"

read version


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
	echo "All test passed, starting version-change"

	mvn -f acota-core/pom.xml versions:set -DnewVersion="$version" clean compile install
	mvn -f acota-feedback/pom.xml versions:set -DnewVersion="$version" clean compile install
	mvn -f acota-examples/pom.xml versions:set -DnewVersion="$version" clean compile install
	mvn -f acota-utils/pom.xml versions:set -DnewVersion="$version" clean compile install

	echo "Acota version changed to $version"
else 
	echo "$counter test failed, Acota version had failed"
	exit 1
fi
