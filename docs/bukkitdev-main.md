## SkyWars - Automated minigames!

## Features
* Automatically create new arenas every time enough people join the queue.
* Have *as many arenas* going at the same time as you want!
  Whenever the configured amount of people in the queue (default to 4), a new arena is created automatically!
  There is no limit besides your server's capacity to how many arenas SkyWars can create.
* Teleports people to lobby location when they leave or die.
* When players walk near a set portal, they will be automatically added to the queue.
* Clears player inventories when the join a game and when the leave.

## Basic Installation
* Make sure your server is running Java 7. SkyWars **will not function** with Java 6.
* Download the SkyWars jar file, and put it in your plugins/ directory. SkyWars 1.4.3 supports minecraft 1.5.2, 1.6.4 and 1.7.2.
* Restart your server. SkyWars will automatically create 2 new worlds. SkyWarsBaseWorld and SkyWarsArenaWorld.
* Go to where you want the lobby to be and use **/sw setlobby**.

## Useful links
* [Commands and Permissions](https://github.com/SkyWars/SkyWars/wiki/Commands-and-Permissions)
* [Configuration Guide](https://github.com/SkyWars/SkyWars/wiki/Configuration)
* [Setting up a new Arena](https://github.com/SkyWars/SkyWars/wiki/Setting-up-a-new-arena)
* [Worlds Created](https://github.com/SkyWars/SkyWars/wiki/Worlds)
* [Common Issues](https://github.com/SkyWars/SkyWars/wiki/Common-Issues)
* [GitHub](https://github.com/SkyWars/SkyWars)
* [MCStats Statistics](http://mcstats.org/plugin/SkyWars)
## Development builds
Development builds of this project can be acquired at the provided continuous integration server.
These builds have not been approved by the BukkitDev staff. Use them at your own risk.
[TeamCity Development Builds - CI Server](http://ci.dabo.guru/p/SkyWarsParent)
[Download Latest Snapshot](http://ci.dabo.guru/d/SkyWarsParent_SkyWars_MainBuild/SkyWars.jar)

### MCStats / Plugin metrics
SkyWars uses plugin metrics to keep track of people using the plugin.
All gathered data can be viewed at [http://mcstats.org/plugin/SkyWars](http://mcstats.org/plugin/SkyWars).
To opt out, change **opt-out: false** to **opt-out: true** in **plugins/PluginMetrics/config.yml**

### SkyWars Report
SkyWars has the ability to use gist.github.com to generate a debug report for your server. When you use the **/sw report** command, SkyWars will gather information about your server, post it to gist.github.com, and give you a URL. SkyWars does not communicate with gist.github.com unless an admin uses the **/sw report** command. If you want to make it so that no one can use the **/sw report** command, add the following line to your **main-config.yml** file: **disable-report: true**

## Bug Reports, Feature Requests and General Questions
Please use the SkyWars issue tracker for all bug reports, feature requests and general questions.

See [submitting a ticket](https://github.com/SkyWars/SkyWars/wiki/Submitting-a-ticket) for instructions on how to do this.

## Map Credit
Full credit for the default map, Skyblock Warriors, goes to [SwipeShot](http://www.youtube.com/user/SwipeShot)

### Sponsors/other
This may be an almost empty section, but we would like to thank JetBrains for their support of this project.

[![JetBrains](http://www.jetbrains.com/img/logos/logo_intellij_idea.png)](http://www.jetbrains.com/idea/)

## Translating!
We need help translating SkyWars! To help out, see [SkyWars-Translations/Translating](https://github.com/SkyWars/SkyWars-Translations/wiki/Translating).