# License management tool V2

This is the second version from the license management tool. It's now programmed in Kotlin and uses Gradle as it's build system.

## Commands
* `/installkey <Key>` - *Installs a new License Key to the system*
* `/key` - *Displays the current installed key*
* `/setservername <servername>` - *Sets the servername to find the corresponding License Key in the Database*
* `/servername` - *Displays the current set server name*
* `/validate` - *Displays if the pro version is activated or not*
* `/togglenotifications` - *Toggles every notifications from the License manager*
* `/activatelite` - *Activates the lite version*

## Permissions
* `License.key.install` - *Allow to use `/installkey`*
* `License.key.show` - *Allow to use `/key`*
* `License.name.change` - *Allow to use `/setservername`*
* `License.name.show` - *Allow to use `/servername`*
* `License.validate` - *Allow to use `/validate`*
* `License.notifications.toggle` - *Allow to use `/togglenotifications`*
* `License.lite.activate` - *Allow to use `/activatelite`*

****

## Requirements

* MongoDB Database with a collection called `Verification`
* In the collection `Verification` you have to store your LicenseKeys
* The LicenseKeys can be stored in the following format:
    * Create a new Document
    * Make a Field called `Key` with your License key. You can put anything you want in it.
    * Add another Field named `ServerName` and input your ServerName. The Key above can only be used if the correct ServerName is entered.
    * You are done!, just install the values on your Minecraft Server.

![alt text](https://i.imgur.com/Ngtvx68.png)

Optionally you can use MongoDB Compass to easily manage your database.

[Here](https://asmax.me/keys) are some Example Keys that you can use, feel free to use your own.
