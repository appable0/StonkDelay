# StonkDelay

A simple Forge 1.8.9 mod to delay mined blocks reset time in Hypixel Skyblock. It is in beta, so please report any issues to me at appable#8347.

* Toggle feature: **/stonkdelay**
* Adjust delay time: **/stonkdelay [delay in milliseconds]**
* Make all blocks reappear: **/stonkdelay reset**

This mod has fairly robust detection of server block reset attempts: it should correctly handle single-block changes, multi-block changes, and chunk updates. This means that the delay is correct even in rooms with frequent block updates like trap. 

Delays above 60000 ms (1 minute) are interpreted as infinite delay (i.e. permanent ghost blocks). The reset command will not apply to these blocks.

Thanks to:
* romangraef's Forge 1.8.9 template and contributions (/stonkdelay reset)
* Harry282, for a SkyBlock mod that served as an excellent tutorial for MC modding and Kotlin
