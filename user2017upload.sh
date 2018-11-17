#!/bin/bash

source config/environment.sh

echo "Luodaan projektikansio..."

# Luodaan projektin kansio
ssh $USERNAME@users2017.cs.helsinki.fi "
cd htdocs
touch favicon.ico
mkdir $PROJECT_FOLDER
cd $PROJECT_FOLDER
touch .htaccess
echo 'RewriteEngine On
RewriteCond %{REQUEST_FILENAME} !-f
RewriteRule ^ index.php [QSA,L]' > .htaccess
exit"

echo "Valmis!"

echo "Siirretään tiedostot users-palvelimelle..."

# Siirretään tiedostot palvelimelle
scp -r src gradle .classpath .project build.gradle  gradlew gradlew.bat settings.gradle $USERNAME@users2017.cs.helsinki.fi:htdocs/$PROJECT_FOLDER

echo "Valmis!"


# Asetetaan oikeudet ja asennetaan Composer
ssh $USERNAME@users2017.cs.helsinki.fi "
chmod a+x htdocs/$PROJECT_FOLDER
chmod a+r htdocs/$PROJECT_FOLDER/.htaccess
exit"

echo "Valmis! Sovelluksesi on nyt valmiina osoitteessa $USERNAME.users.cs.helsinki.fi/$PROJECT_FOLDER"
