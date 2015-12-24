# WorldGuardNoFly
Simple plugin that allows admins to disable flying in certain zones

---

While working on a upcoming Prison server, we noticed there was a lack of proper fly disablers compatible with WorldGuard. I therefore coded this small plugin to allow us to do so, and decided to release it to anyone who might need it.

This plugin requires the following plugins to work: 
* WorldGuard: http://dev.bukkit.org/bukkit-plugins/worldguard/
* WorldEdit: http://dev.bukkit.org/bukkit-plugins/worldedit/

## How to use
WorldGuardNoFly is easy to use, in the config, you just need to add a few lines.
```yaml
worlds: 
  world: # this is the name of the world
  - overworldpvp # this is the name of the WorldGuard region
  - memberarea
  nether:
  - netherpvp
```
Reload, and then to verify if the region was correctly added, just use the following command:
```
/worldguardnofly regions
```
If you want to allow a certain player to bypass the no-fly zone (let him fly inside a restricted region), give the player the following permission:
```
worldguardnofly.bypass
```

### Important notice
Please note that this plugin will not stop people from toggling creative mode, or using /fly. To disable the /fly command, just add "/fly" to the disabled-cmds flag in WorldGuard. This plugin will only disallow people from toggling flight inside a restricted region.

There are a few upgrades I plan to do to this plugin, including better performance and a command to add and remove regions in-game. Please keep updated either on this repository, or on the proper Bukkit or Spigot post.
