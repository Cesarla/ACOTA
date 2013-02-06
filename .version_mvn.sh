echo "Type the new version of Acota, followed by [ENTER]:"

read version

#Change the password to yours mysql password
mysql --user="root" --password="root" -e 'SET GLOBAL time_zone = "+00:00";'

mvn -f acota-core/pom.xml versions:set -DnewVersion="$version" clean compile install
mvn -f acota-feedback/pom.xml versions:set -DnewVersion="$version" clean compile install
mvn -f acota-examples/pom.xml versions:set -DnewVersion="$version" clean compile install
mvn -f acota-utils/pom.xml versions:set -DnewVersion="$version" clean compile install

echo "Acota version changed to $version"
