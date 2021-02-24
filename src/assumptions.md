# T09A pink assumptions

### Name of class
 - assumptions in class

### faction
 - Factions will be uniquely identified by their name. There will only be one faction with that particular name.
 - Only factions named Celtic Briton, Gaul and Germania can recruit berserker units

### province
 - Provinces will be uniquely identified by their name. There will only be one province with that particular name.
 - Cannot recruit more soldiers into that province if they are already training at maximum 2 soliders.

### unit
 - The spec seems to use heavy cavalry and melee cavary interchangeably, so we chose to turn those into a single type of unit, melee cavalry.
 - The spec seems to use heavy infantry and melee infantry interchangeably, so we chose to turn those into a single type of unit, melee infantry.
 - Given that the spec did not specify, we chose to treat Hoplite, Spearman, Pikeman, Berserker, Javelin-Skirmisher and Druid as members of the infantry.
 - The spec was unclear - so we assumed that all cavalry have the charge statistic.
 - We assumed that only ranged units have a missle attack damage value. Ranged units being: harse archers, missile infantry and artillery.
 
 - We assumed that only Gallic, Celtic Briton and Germanic factions can have Beserker units based on what was contained within the spec.

### battle resolver
 - When calculating the casualties suffered by each side in an engagement, to follow the spec saying 10%, we multiplied by 0.1.
 - Since the spec did not stipulate clearly, we assumed that when in a skirmish, if an army tries to flee but does not succeed, the units will reengage and both will have an opportunity to inflict damage on each other again.

### special abilities
 - When calculating the "Druidic Fervour" special ability, if both armies have druids, then we have taken the difference in the number of druid units between each army as the number of units belonging to the army with more druids. This was to ensure that there was no discrepency depending on which unit the special ability was appleid to first.
 