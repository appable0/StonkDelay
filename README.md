# StonkDelay

A simple Forge 1.8.9 mod to delay mined blocks reset time in Hypixel Skyblock. It is in beta, so please report any issues to me at appable#8347.

* Toggle feature: **/stonkdelay**
* Adjust delay time: **/stonkdelay [delay in milliseconds]**
* Make all blocks reappear: **/stonkdelay reset**

This mod has fairly robust detection of server block reset attempts: it should correctly handle single-block changes, multi-block changes, and chunk updates. This means that the delay is correct even in rooms with frequent block updates like trap. 

Delays above 10000 ms are interpreted as infinite delay (i.e. permanent ghost blocks).

Thanks to:
* romangraef's Forge 1.8.9 template 
* Harry282, for a SkyBlock mod that served as an excellent tutorial for MC modding and Kotlin
