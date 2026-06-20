# KMP Engine

2D game engine in Kotlin Multiplatform. Educational study project, Godot-inspired
(scene graph of nodes). The core is backend-agnostic and draws against an abstract
`Renderer`; Skiko is the default runtime. JVM-only for now.

User-facing docs: see `README.md`.

## Commands

Requires JDK 21 (Kotlin/Gradle via wrapper).

```bash
./gradlew :example:hello-world:run      # run the basic example
./gradlew :example:bouncing-ball:run    # run the physics + delta-time example
./gradlew :example:colliding-balls:run  # run the ball-to-ball elastic collision example
./gradlew :example:keyboard-input:run   # run the keyboard input example
./gradlew :example:pong:run             # run the two-player Pong example
./gradlew build                         # compile all modules + run tests (check)
./gradlew :core:jvmTest                 # run a module's tests (no root `test` task — KMP)
```

## Modules & dependency direction

```
example/*  ─→  core-dsl ──────→ core
example/*  ─→  runtime-skiko ─→ core-dsl ─→ core
```

| Module | Role |
|---|---|
| `core` | Nodes, scene tree, `SceneManager` (named scenes + runtime switching), abstract `Renderer`, frame clock, value types (`Vec2`, `Color`, `Rect`, `Size`). Pure `commonMain`. |
| `core-dsl` | Scene-building DSL (`scene("name") { add<...> { } }` via `ScenesBuilder`). Uses `kotlin-reflect`. |
| `runtime-skiko` | `Renderer` + desktop window (`SkikoWindow`) implementation via Skiko (Skia + Swing). Depends on `core-dsl` to expose the `runSkikoWindow { scene(...) { } }` scene-builder. `jvmMain`. |
| `example/hello-world`, `example/bouncing-ball`, `example/colliding-balls`, `example/keyboard-input`, `example/pong` | Sample apps. |

## Architecture invariants

- **`core` is backend-agnostic.** It never imports Skiko/Swing/AWT — it only draws
  through the `Renderer` interface. `runtime-skiko` is the only module with a backend.
- **`core` logic stays in `commonMain`** (no JVM-only APIs), keeping the multiplatform
  path open even though only the JVM target runs today.
- **Dependency direction is one-way:** `example → runtime-skiko → core-dsl → core`
  (examples also depend on `core-dsl` directly). `core` knows nothing about the DSL
  or any runtime. The desktop window lives in `runtime-skiko` (`SkikoWindow`), never
  in `core` — windowing is a desktop-only concern, keeping the path open for Android.

## Conventions

- Code, comments and KDoc in **English**. Markdown docs may be in Portuguese.
- The `core`/`core-dsl`/`runtime-skiko` build files enable the experimental
  `-XXLanguage:+ExplicitBackingFields` flag (used e.g. by `Node.children`). Keep it
  when adding source sets that rely on `field =` backing properties.
- Skiko resolves a per-OS/arch native artifact at configuration time
  (`runtime-skiko/build.gradle.kts`) — don't hardcode the platform suffix.

## Node lifecycle

Nodes extend `Node` (or `Node2D`) and override:

- `onReady()` — once, after the node is attached to the `SceneTree` (`tree` is set).
- `onProcess(delta: Float)` — per frame; `delta` is seconds since last frame
  (use it for frame-rate-independent movement).
- `onInput(event: InputEvent)` — per input event (e.g. `KeyEvent`). The runtime
  captures backend events, enqueues them, and `SceneTree.dispatchInput(event)`
  propagates each one through the tree at the start of a frame, before `onProcess`.
  For continuous input, poll the derived state instead: `tree.input.isPressed(key)` /
  `tree.input.isJustPressed(key)` (read inside `onProcess`).
- `onDraw(renderer: Renderer)` — per frame; draw via the `Renderer`.

`SceneTree` walks the tree depth-first for each phase.

## Scenes & scene switching

An app registers one or more named scenes; the **first** one registered is the
starting scene by convention:

```kotlin
runSkikoWindow(title = "pong") {
    scene("menu") { add<...>() }   // first → initial scene
    scene("pong") { add<...>() }
}
```

`SceneManager` (in `core`) holds the named `SceneFactory` entries and the active
`SceneTree` (`manager.current`); it starts on the first registered scene. A node
switches scenes via `tree?.changeScene("name")` (delegates to `SceneManager.change`).
Each `SceneFactory.create()` rebuilds its scene from scratch on every switch, so
re-entering a scene starts it clean. The runtime snapshots `manager.current` at the
start of each frame, so a `changeScene` during `onProcess`/`onInput` takes effect on
the next frame.

## Testing

Tests live in each module's `commonTest`/`jvmTest` source set. The root `test` task
doesn't exist (KMP); run `./gradlew build` (runs `check`/`allTests`) or a module task
like `./gradlew :core:jvmTest`. New non-trivial changes should add tests.
