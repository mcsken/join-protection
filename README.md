# Join Protection
Minecraft server plugin that protects players from damage while loading and waiting for connection to stabilise.

## Intended usage
Designed primarily to help players with a poor connection and/or a slow computer to load into the world safely.
Minecraft by default has an uneditable 5 seconds grace period during loading screens, which sometimes is not enough. Players with a poor connection or slow computer could take up to a minute or more between the time that the server sees the player loaded and the player being able to see and move around properly, leaving the player vulnerable to damage during that time frame, especially if their connection temporarily dropped in a dangerous area, as they try to reconnect.

Alternatively it can also be used as an efficient and clutter-free plugin to protect players from spawn killing.

## Showcase

[![Watch the video](https://i.imgur.com/uxNl7b2.jpeg)](https://youtu.be/q3s1x0CRQi0)

*This video is meant to visualize the plugins features not as an example of intended usage.

## Configuration file
Once installed the plugin will generate a configuration file which you can use to configure various features. Below you can see each option, their description and default values:
```yml
# Enable or Disable Join Protection (true/false)
joinEnableProtection: true

# Join Protection Time in seconds
joinTimeProtection: 20

# Can Player Move during Join Protection Time (true/false)
joinMovementAllow: true

# Enable or Disable Dimension Change Protection (true/false)
dimensionEnableProtection: true

# Dimension Change Protection Time in seconds
dimensionTimeProtection: 10

# Can Player Move during Dimension Change Protection Time (true/false)
dimensionMovementAllow: true

# You can use color codes to color the messages text: https://www.spigotmc.org/wiki/textcosmetics-colors-and-formats/
# You can use %time% to show remaining seconds
Messages:
  joinCooldownStart: "&2You are protected for &6%time% seconds&2!"
  joinCooldownEnded: "&cYou are no longer protected!"
  dimensionCooldownStart: "&2You are protected for &6%time% seconds&2!"
  dimensionCooldownEnded: "&cYou are no longer protected!"
  ```
## Permissions
If you wish to disable/enable certain features for specific users, groups or by default, you can achieve so by using a permission manager plugin such as [LuckPerms](https://luckperms.net/). These are the plugin's available permissions and their descriptions:
```yml
  enableProtection.join:
    description: Enables join protection for specified user/group (given to all by default).
    default: true
  enableProtection.dimension:
    description: Enables dimension protection for specified user/group (given to all by default).
    default: true
  disableProtection.join:
    description: Disables join protection for specified user/group.
    default: false
  disableProtection.dimension:
    description: Disables dimension protection for specified user/group.
    default: false
```