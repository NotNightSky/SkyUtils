# Colors

This document lists the color values used throughout the project.

## Format

Colors are represented as ARGB hex values: `0xAARRGGBB`

- `0xFF` = Full opacity (255)
- `0xAA` = Semi-transparent (170)
- `0x80` = Half transparent (128)

## Colors

| Color | Hex | Usage |
|-------|-----|-------|
| Dark Background | `0xFF1A1A1A` | Panel background, button background |
| Darker Background | `0xFF2A2A2A` | Panel item background |
| Hover Background | `0xFF3A3A3A` | Button/item hover state |
| Dark Outline | `0xFF555555` | Panel/button outline |
| Light Outline | `0xFF888888` | Hover outline |
| Panel Background (translucent) | `0xAA1A1A1A` | Side panel background |
| Panel Outline (translucent) | `0xAA555555` | Side panel outline |
| Screen Overlay | `0x80000000` | Semi-transparent black overlay |
| Highlight Border | `0x80FFFFFF` | Element highlight border |
| White | `0xFFFFFFFF` | Hover highlight |
| Orange | `0xFFFFAA00` | Dragging highlight |
| Blue | `0xFF5555FF` | Snap lines |

## ARGB Breakdown

- `0xFF` (alpha) = 255 = fully opaque
- `0xAA` (alpha) = 170 = ~67% opacity
- `0x80` (alpha) = 128 = 50% opacity

### Example
`0xFF1A1A1A`
- `FF` = alpha (opaque)
- `1A` = red (26)
- `1A` = green (26)
- `1A` = blue (26)

Result: Dark gray with full opacity.