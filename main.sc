using import glm
import .bottle

@@ 'on bottle.config
fn (c)
    c.window.title = "uhhh something top down I guess?"
    c.window.width = 800
    c.window.height = 600
    ;

from bottle.graphics let Sprite
global player-sprite : Sprite
global player-dir : i32
global player-position : vec2

@@ 'on bottle.load
fn ()
    player-sprite = (Sprite "sprites/characters/eggboy/SpriteSheet.png")

# to pick sprites in sprite sheets with equally sized quads
fn pick-sprite (sheet qw qh qx qy)
    let sz = sheet.size
    let sw sh = (sz.x // qw) (sz.y // qh)
    let x0 y0 = (qx * sw) (qy * sh)
    ivec4 x0 y0 (x0 + sw) (y0 + sh)

@@ 'on bottle.update
fn (dt)
    let btn = bottle.input.down?

    local dir : vec2
    if (btn 'Right) 
        player-dir = 0
        dir += (vec2 1 0)
    if (btn 'Up) 
        player-dir = 1
        dir += (vec2 0 1)
    if (btn 'Left) 
        player-dir = 2
        dir += (vec2 -1 0)
    if (btn 'Down) 
        player-dir = 3
        dir += (vec2 0 -1)

    if (dir != 0)
        dir = (normalize dir)
    player-position += dir * 150 * (dt as f32)

@@ 'on bottle.draw
fn ()
    local player-fdirs = (arrayof i32 3 1 2 0)
    column := player-fdirs @ player-dir
    bottle.graphics.sprite player-sprite player-position
        scale = (vec2 4 4)
        quad = (pick-sprite player-sprite 4 7 column 0)

bottle.run;
