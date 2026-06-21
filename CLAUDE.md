# KMP Engine

2D game engine in Kotlin Multiplatform. Educational study project, Godot-inspired
(scene graph of nodes). The core is backend-agnostic and draws against an abstract
`Renderer`; Skiko is the default runtime. Targets JVM (desktop) and wasmJs (browser);
the `pong` example runs on both.

User-facing docs: see `README.md`.

## Commands

Requires JDK 21 (Kotlin/Gradle via wrapper).

```bash
./gradlew :example:hello-world:run      # run the basic example
./gradlew :example:bouncing-ball:run    # run the physics + delta-time example
./gradlew :example:colliding-balls:run  # run the ball-to-ball elastic collision example
./gradlew :example:keyboard-input:run   # run the keyboard input example
./gradlew :example:pong:run             # run the two-player Pong example (desktop/JVM)
./gradlew :example:pong:wasmJsBrowserDevelopmentRun  # run Pong in the browser (wasmJs)
./gradlew build                         # compile all modules + run tests (check)
./gradlew :core:jvmTest                 # run a module's tests (no root `test` task — KMP)
```

Only `pong` targets the browser so far; the other examples are JVM-only.

## Modules & dependency direction

```
example/*  ─→  core-dsl ──────→ core
example/*  ─→  runtime-skiko ─→ core-dsl ─→ core
```

| Module | Role |
|---|---|
| `core` | Nodes, scene tree, `SceneManager` (named scenes + runtime switching), abstract `Renderer`, frame clock, value types (`Vec2`, `Color`, `Rect`, `Size`). Pure `commonMain`; targets `jvm` + `wasmJs`. |
| `core-dsl` | Scene-building DSL via `ScenesBuilder`. `commonMain` exposes a reflection-free builder (`add(::Node) { }`); `jvmMain` adds the reflection-based `add<Node>()` overload (`kotlin-reflect`, JVM-only). |
| `runtime-skiko` | `Renderer` + window implementation via Skiko. Shared Skia drawing (`SkikoRenderer`, `SceneRenderDelegate`) lives in `commonMain`; the window/keyboard layer is per-target: `jvmMain` (Swing/AWT `SkikoWindow`), `wasmJsMain` (`SkikoCanvas`: a `SkiaLayer` on an HTML `<canvas>` + DOM key events). Exposes `runSkikoWindow { scene(...) { } }` (JVM) / `runSkikoCanvas { scene(...) { } }` (wasm). |
| `example/hello-world`, `example/bouncing-ball`, `example/colliding-balls`, `example/keyboard-input` | JVM-only sample apps. |
| `example/pong` | Sample app on both `jvm` and `wasmJs`; nodes + scenes in `commonMain`, platform `Main.kt` per target. |

## Architecture invariants

- **`core` is backend-agnostic.** It never imports Skiko/Swing/AWT — it only draws
  through the `Renderer` interface. `runtime-skiko` is the only module with a backend.
- **`core`/`core-dsl` logic stays in `commonMain`** (no JVM-only APIs). `core-dsl`
  keeps `kotlin-reflect` confined to `jvmMain` (it's unavailable on wasmJs); common
  code uses the factory-based `add(::Node)` overload.
- **Windowing/input is per-target in `runtime-skiko`,** never in `core`. The shared
  Skia drawing is in `commonMain`; the JVM uses Swing/AWT and wasmJs uses an HTML
  `<canvas>` + DOM events. Cross-thread input uses the `InputQueue` expect/actual
  (`ConcurrentLinkedQueue` on JVM, `ArrayDeque` on the single-threaded browser).
- **Dependency direction is one-way:** `example → runtime-skiko → core-dsl → core`
  (examples also depend on `core-dsl` directly). `core` knows nothing about the DSL
  or any runtime.

## Conventions

- Code, comments and KDoc in **English**. Markdown docs may be in Portuguese.
- The `core`/`core-dsl`/`runtime-skiko` build files enable the experimental
  `-XXLanguage:+ExplicitBackingFields` flag (used e.g. by `Node.children`). Keep it
  when adding source sets that rely on `field =` backing properties.
- Skiko resolves a per-OS/arch native artifact at configuration time
  (`runtime-skiko/build.gradle.kts`) — don't hardcode the platform suffix.
- The wasmJs Skiko klib doesn't bundle the Skia `.wasm` binary: the `pong` build
  unpacks `skiko-js-wasm-runtime` into the wasm resources (`unpackSkikoWasmRuntime`
  task) so the browser can load it. The `<canvas>` id in `index.html` (`SkikoTarget`)
  must match the `runSkikoCanvas(canvasElementId = ...)` call, and the webpack
  `outputFileName` (`pong.js`) must match the `<script src>`.
- DSL: JVM scenes can use `add<Node>()` (reflection); cross-platform/wasm scenes use
  `add(::Node) { }`. The `pong` scenes use the factory form so they compile everywhere.

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
