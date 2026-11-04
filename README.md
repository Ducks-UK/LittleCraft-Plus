# LittleCraft Plus

A separate NeoForge addon for LittleCraft, targeting Minecraft 26.3 / NeoForge 26.3.0.1-beta. LittleCraft is a required runtime dependency; this project intentionally uses its documented datapack integration for food effects rather than copying its player-data systems.

## Build

Use Java 25 and run `./gradlew build`. For local game testing, put a compatible LittleCraft jar in `run/mods` and use `./gradlew runClient` or `./gradlew runServer`.

## Current implementation status

This is the initial implementation phase, not a finished release. The first commit registers roleplay food/drink consumables, rubber duck and toy/plushie items, and adds LittleCraft-compatible food-effect data. Their interactions are server-side item use and use LittleCraft's `data/<namespace>/littlecraft/food_effects/` loader. More complex block entities, player-consent workflows, textures, and rendering are being developed in subsequent phases.

Compatibility target: LittleCraft 3.2.5 / Minecraft 26.3, subject to runtime verification against the released jar.
