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
./gradlew build                         # compile all modules
./gradlew test                          # run tests (no suite yet — see Testing)
```

## Modules & dependency direction

```
example/*  ─→  core-dsl ─┐
example/*  ─→  runtime-skiko ─→ core
                          └─→ core
```

| Module | Role |
|---|---|
| `core` | Nodes, scene tree, abstract `Renderer`, frame clock, value types (`Vec2`, `Color`, `Rect`, `Size`). Pure `commonMain`. |
| `core-dsl` | Scene-building DSL (`scene { add<...> { } }`). Uses `kotlin-reflect`. |
| `runtime-skiko` | `Renderer` + `Launcher` implementation via Skiko (Skia + Swing). `jvmMain`. |
| `example/hello-world`, `example/bouncing-ball` | Sample apps. |

## Architecture invariants

- **`core` is backend-agnostic.** It never imports Skiko/Swing/AWT — it only draws
  through the `Renderer` interface. `runtime-skiko` is the only module with a backend.
- **`core` logic stays in `commonMain`** (no JVM-only APIs), keeping the multiplatform
  path open even though only the JVM target runs today.
- **Dependency direction is one-way:** `example → core-dsl`/`runtime-skiko → core`.
  `core` knows nothing about the DSL or any runtime.

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
- `onDraw(renderer: Renderer)` — per frame; draw via the `Renderer`.

`SceneTree` walks the tree depth-first for each phase.

## Testing

No test suite exists yet. New non-trivial changes should add tests; place them in
each module's `commonTest`/`jvmTest` source set and run with `./gradlew test`.
