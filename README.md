# Botanicapp
## Video Demo:  https://youtu.be/SPHivvmVerY
## Description:
'BotanicApp' is Android application implemented with Kotlin programming language.
It can can help you maintain your plants.

After adding plant to database (with name, photo and description) you can review it and remember how to take care of it.
You can always edit plant inforamtion - for example provide more details about watering or change photo when plant has grown.

App could still be improved - visual layer is not final, these are basic settings and I have idea to implement watering schedule which will send notifications with watering minders!

### Features:
* CRUD application to maintan your plants
* You can name your plant, add photo and description (e. g. watering schedule)
* You can edit or delete persisted plants
* App uses device's camera and requires permission

### Built with:
* Kotlin
* Room persistence library (SQLite)
* Gradle
* Android Studio

### Technical details:
* project/app/<b>build.gradle</b> - Gradle is build system and dependency management system. This file is its configuration. The most attention should be paid to <i>dependencies</i> paragraph - here you declare used dependencies (i.e. used libraries or frameworks)
* project/app/src/main/<b>AndroidManifest.xml</b> - application configuration file. Here you declare: used device's features, required permissions, main activity, structure and basic settings.
* project/app/src/main/res/<b>layout/(...).xml</b> - this folder contains .xml files with visual layouts of application parts. Each file consist of given elements with specified options such as size, position, color etc.
* project/app/src/main/java/com/arje/botanicapp/<b>fragments/.../....kt</b> - here are kotlin classes responsible for layouts behavior. These classes provides code which is executed: on application pages startup, on button click, window closing etc.
* project/app/src/main/java/com/arje/botanicapp/<b>data/.../....kt</b> - these classes are responsible for database maintenance
* project/app/src/main/java/com/arje/botanicapp/<b>MainActivity.kt</b> - this is main part of application. This class consist of companion object which contains variables available in global scope, onCreate() function provides code executed on app startup and there is onSupportNavigateUp() function which determines behavior of 'go back button, in application.
* project/app/src/main/java/com/arje/botanicapp/permissions/<b>PermissionManager.kt</b> - this is class responsible for permission management. Applicatrion requires camera permission so in this file there is code allowing for verification if user has granted this permission and if not it prompt user to do this.
* project/app/src/main/res/<b>drawable/....xml</b> - here are stored vector assets used in application. In my case there are only few icons.
